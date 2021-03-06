package com.ll.lintcode.basic.twopoint;

/**
 * 给出一个整数数组 nums 和一个整数 k。划分数组（即移动数组 nums 中的元素），使得：
 *
 * 所有小于k的元素移到左边
 * 所有大于等于k的元素移到右边
 * 返回数组划分的位置，即数组中第一个位置 i，满足 nums[i] 大于等于 k。
 *
 * 样例
 * 例1:
 *
 * 输入:
 * [],9
 * 输出:
 * 0
 *
 * 例2:
 *
 * 输入:
 * [3,2,2,1],2
 * 输出:1
 * 解释:
 * 真实的数组为[1,2,2,3].所以返回 1
 * 挑战
 * 使用 O(n) 的时间复杂度在数组上进行划分。
 *
 * 注意事项
 * 你应该真正的划分数组 nums，而不仅仅只是计算比 k 小的整数数，如果数组 nums 中的所有元素都比 k 小，则返回 nums.length。
 */
public class PartitionArray_31 {

    public int partitionArray(int[] nums, int k){
        if(nums == null || nums.length < 1){
            return 0;
        }

        int l = 0;
        int r = nums.length - 1;
        while (l < r){
            while (l < r && nums[l] < k){
                l ++;
            }
            while (l < r && nums[r] >= k){
                r --;
            }
            if (l < r){
                swap(nums, l++, r--);
            }
        }
        if(nums[r] < k){
            return r + 1;
        }
        return r;
    }
    private void swap(int[] nums, int x, int y){
        int tmp = nums[x];
        nums[x] = nums[y];
        nums[y] = tmp;
    }
}
