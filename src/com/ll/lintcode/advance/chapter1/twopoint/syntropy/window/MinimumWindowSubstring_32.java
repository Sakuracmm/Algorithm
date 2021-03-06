package com.ll.lintcode.advance.chapter1.twopoint.syntropy.window;

/**
 * 给定两个字符串 source 和 target. 求 source 中最短的包含 target 中每一个字符的子串.
 *
 * 样例
 * 样例 1:
 *
 * 输入: source = "abc", target = "ac"
 * 输出: "abc"
 * 样例 2:
 *
 * 输入: source = "adobecodebanc", target = "abc"
 * 输出: "banc"
 * 解释: "banc" 是 source 的包含 target 的每一个字符的最短的子串.
 * 样例 3:
 *
 * 输入: source = "abc", target = "aa"
 * 输出: ""
 * 解释: 没有子串包含两个 'a'.
 * 挑战
 * O(n) 时间复杂度
 *
 * 注意事项
 * 如果没有答案, 返回 "".
 * 保证答案是唯一的.
 * target 可能包含重复的字符, 而你的答案需要包含至少相同数量的该字符.
 */
public class MinimumWindowSubstring_32 {

    public String minWindow(String source, String target){
        int ans = Integer.MAX_VALUE;
        String minStr = "";

        int[] sourceHash = new int[256];
        int[] targetHash = new int[256];

        initTargetHash(targetHash, target);
        int i = 0, j = 0;
        for (i = 0; i < source.length(); i++){
            while (!valid(sourceHash, targetHash) && j < source.length()){
                sourceHash[source.charAt(j++)] ++;
            }
            if (valid(sourceHash, targetHash)){
                if (ans > j - i){
                    ans = Math.min(ans, j - i);
                    minStr = source.substring(i, j);
                }
            }
            sourceHash[source.charAt(i)] --;
        }

        return minStr;
    }
    private int initTargetHash(int[] targethash, String target){
        int targetNum = 0;
        for (char ch : target.toCharArray()){
            targetNum ++;
            targethash[ch] ++;
        }

        return targetNum;
    }

    private boolean valid(int[] sourceHash, int[] targetHash){
        for (int i = 0; i < 256; i++){
            if (targetHash[i] > sourceHash[i]){
                return false;
            }
        }

        return true;
    }


    public String minWindow2(String s, String t) {
        if(s == null || t == null || s.length() < t.length()) {
            return "";
        }

        int[] mapS = new int[256];
        int[] mapT = new int[256];
        for(char ch : t.toCharArray()) {
            mapT[ch]++;
        }
        char[] chars = s.toCharArray();
        int j = 0, ans = Integer.MAX_VALUE;
        String res = "";
        for(int i = 0; i < chars.length; i++) {
            while(j < chars.length && !isValid2(mapS, mapT)) {
                mapS[chars[j]]++;
                j++;
            }
            if(isValid2(mapS, mapT)) {
                if(ans > j - i) {
                    ans = Math.min(ans, j - i);
                    res = s.substring(i, j);
                }
            }
            mapS[chars[i]]--;
        }

        return res;
    }

    private boolean isValid2(int[] A, int[] B) {
        for(int i = 0; i < 256; i++) {
            if(B[i] > 0 && A[i] != B[i]) {
                return false;
            }
        }

        return true;
    }



    public static void main(String[] args) {
        MinimumWindowSubstring_32 dto = new MinimumWindowSubstring_32();
        String source = "ab";
        String target = "a";
        String s = dto.minWindow2(source, target);
        System.out.println(s);
    }
}
