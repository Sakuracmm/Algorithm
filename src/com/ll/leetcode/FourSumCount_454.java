package com.ll.leetcode;

import com.ll.muke.queue.Array;
import com.ll.utils.TimeOut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
 *
 * 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -228 到 228 - 1 之间，最终结果不会超过 231 - 1 。
 *
 * 例如:
 *
 * 输入:
 * A = [ 1, 2]
 * B = [-2,-1]
 * C = [-1, 2]
 * D = [ 0, 2]
 *
 * 输出:
 * 2
 *
 * 解释:
 * 两个元组如下:
 * 1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
 *
 *
 * [-6,-11,10,-3,-18,-31,1,-7,-23,3,-27,-16,6,9,-4,-19,-9,3,-3,-21,-24,-29,3,-28,-13,-15,2,-23,-3,0,-5,9,-16,-28,7,-26,-11,-25,-14,-32,-24,-16,-21,-23,7,-25,-13,-13,-2,-22,-13,-6,-18,3,-17,-17,10,-4,-9,-27,-30,0,-14,-9,6,-11,-20,5,7,-8,8,-27,-2,-14,-2,-32,7,-25,-31,-17,-4,8,-28,-15,-4,-6,-19,-14,-23,1,-26,-11,-27,-21,-26,6,-28,-1,-6,8]
 * [-27,1,-23,-4,-5,-25,-21,4,-5,10,10,1,-17,-20,-22,-24,-14,1,-3,-13,-15,-15,-24,-17,-31,-12,-14,-16,-29,-4,-16,-26,-5,7,1,-21,-18,-24,1,-27,-31,-12,1,5,-13,10,-28,-1,-4,-2,10,-3,-20,-29,-7,-14,-21,-31,-1,-24,-5,-8,-2,5,-7,-26,-27,-19,8,-25,-25,-1,-20,-9,-13,-22,-10,-4,-14,-20,-10,5,-23,-3,-17,-3,-8,-16,-10,-29,-24,9,-11,8,-4,-5,-11,-27,-25,-26]
 * [-4,-7,-27,-30,-26,4,-3,-30,7,-25,5,-11,-25,-28,6,3,7,-12,-21,0,-24,-24,-11,-17,-14,-7,-21,-2,-6,7,-29,-32,-11,6,-11,-22,9,-13,-18,-24,-8,10,-27,-18,-26,3,-15,-11,-11,4,-6,9,-12,10,9,8,-14,3,-28,-14,-15,6,-24,-22,-10,9,-1,-26,-16,-6,-17,-19,10,-30,-20,-7,-32,1,-31,3,-17,2,-26,-30,10,8,-9,-2,-8,7,-29,3,-14,-16,9,-28,-18,-30,9,-19]
 * [3,-24,5,-32,-18,0,-2,2,-6,-3,-28,-10,3,-20,-27,-2,8,7,-9,-1,-25,7,-6,-25,-30,-13,4,-21,7,-22,-1,-6,-8,3,-26,-8,-26,3,9,-32,-2,-14,8,-30,4,-22,7,6,-3,0,-19,-27,-31,-2,-7,3,-6,0,6,-19,-32,-23,-20,-31,-16,-21,2,-12,4,-10,-27,-26,-32,-11,-8,-17,8,-3,-15,-23,-11,-4,-27,-9,-2,-3,-5,-19,-1,5,-3,-22,-21,-4,1,-19,-29,-3,-11,-5]
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FourSumCount_454 {

    int res = 0;
    @TimeOut
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int[][] grid = new int[][]{A, B, C, D};

        boolean[] visited = new boolean[4];
        help(0, grid, visited, 0);
        return res;
    }

    private void help(int curRow, int[][] grid, boolean[] visited, int sum) {
        if (curRow == 4) {
            if (sum == 0) {
                res += 1;
            }
            return;
        }

        for (int i = 0; i < grid[0].length; i++) {
            if (visited[curRow]) {
                continue;
            }
            visited[curRow] = true;
            help(curRow + 1, grid, visited, sum + grid[curRow][i]);
            visited[curRow] = false;
        }
    }

    public int fourSumCount_2(int[] A, int[] B, int[] C, int[] D) {
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (!map.containsKey(- (A[i] + B[j]))) {
                    map.put(- (A[i] + B[j]), 0);
                }
                map.put(-(A[i] + B[j]), map.get(-(A[i] + B[j])) + 1);
            }
        }

        for (int m = 0; m < C.length; m++) {
            for (int n = 0; n < D.length; n++) {
                if (map.containsKey(C[m] + D[n])) {
                    ans += map.get(C[m] + D[n]);
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        FourSumCount_454 dto = new FourSumCount_454();
        int[] A = new int[]{-6,-11,10,-3,-18,-31,1,-7,-23,3,-27,-16,6,9,-4,-19,-9,3,-3,-21,-24,-29,3,-28,-13,-15,2,-23,-3,0,-5,9,-16,-28,7,-26,-11,-25,-14,-32,-24,-16,-21,-23,7,-25,-13,-13,-2,-22,-13,-6,-18,3,-17,-17,10,-4,-9,-27,-30,0,-14,-9,6,-11,-20,5,7,-8,8,-27,-2,-14,-2,-32,7,-25,-31,-17,-4,8,-28,-15,-4,-6,-19,-14,-23,1,-26,-11,-27,-21,-26,6,-28,-1,-6,8};
        int[] B = new int[]{-27,1,-23,-4,-5,-25,-21,4,-5,10,10,1,-17,-20,-22,-24,-14,1,-3,-13,-15,-15,-24,-17,-31,-12,-14,-16,-29,-4,-16,-26,-5,7,1,-21,-18,-24,1,-27,-31,-12,1,5,-13,10,-28,-1,-4,-2,10,-3,-20,-29,-7,-14,-21,-31,-1,-24,-5,-8,-2,5,-7,-26,-27,-19,8,-25,-25,-1,-20,-9,-13,-22,-10,-4,-14,-20,-10,5,-23,-3,-17,-3,-8,-16,-10,-29,-24,9,-11,8,-4,-5,-11,-27,-25,-26};
        int[] C = new int[]{-4,-7,-27,-30,-26,4,-3,-30,7,-25,5,-11,-25,-28,6,3,7,-12,-21,0,-24,-24,-11,-17,-14,-7,-21,-2,-6,7,-29,-32,-11,6,-11,-22,9,-13,-18,-24,-8,10,-27,-18,-26,3,-15,-11,-11,4,-6,9,-12,10,9,8,-14,3,-28,-14,-15,6,-24,-22,-10,9,-1,-26,-16,-6,-17,-19,10,-30,-20,-7,-32,1,-31,3,-17,2,-26,-30,10,8,-9,-2,-8,7,-29,3,-14,-16,9,-28,-18,-30,9,-19};
        int[] D = new int[]{3,-24,5,-32,-18,0,-2,2,-6,-3,-28,-10,3,-20,-27,-2,8,7,-9,-1,-25,7,-6,-25,-30,-13,4,-21,7,-22,-1,-6,-8,3,-26,-8,-26,3,9,-32,-2,-14,8,-30,4,-22,7,6,-3,0,-19,-27,-31,-2,-7,3,-6,0,6,-19,-32,-23,-20,-31,-16,-21,2,-12,4,-10,-27,-26,-32,-11,-8,-17,8,-3,-15,-23,-11,-4,-27,-9,-2,-3,-5,-19,-1,5,-3,-22,-21,-4,1,-19,-29,-3,-11,-5};
        int sum = dto.fourSumCount(A, B, C, D);
        int sum2 = dto.fourSumCount_2(A, B, C, D);
        System.out.println(sum);
        System.out.println(sum2);
    }
}
