package com.ll.leetcode;

import java.util.Stack;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * 示例 2：
 *
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * 示例 3：
 *
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * 示例 4：
 *
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/decode-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DecodeString_394 {
    public String decodeString(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }

        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        StringBuilder resSb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ']') {
                stack.push(chars[i]);
            } else {
                StringBuilder subSb = new StringBuilder();
                while (stack.peek() != '['){
                    subSb.append(stack.pop());
                }
                StringBuilder revSubSb = subSb.reverse();
                String subStr = revSubSb.toString();
                stack.pop(); // pop '['
                StringBuilder subTimes = new StringBuilder();
                while (!stack.isEmpty() && stack.peek() >= '0' && stack.peek() <= '9') {
                    subTimes.append(stack.pop());
                }
                StringBuilder revSubTimes = subTimes.reverse();
                int time = Integer.parseInt(String.valueOf(revSubTimes));
                while (time > 1) {  //本身就有一次
                    revSubSb.append(subStr);
                    time--;
                }

                for (Character ele : revSubSb.toString().toCharArray()) {
                    stack.push(ele);
                }
            }
        }

        while (!stack.isEmpty()) {
            resSb.append(stack.pop());
        }

        return resSb.reverse().toString();
    }


    public static void main(String[] args) {
        DecodeString_394 dto = new DecodeString_394();
        String sourceData = "3[a]2[bc]";
        System.out.println(dto.decodeString(sourceData));
    }
}
