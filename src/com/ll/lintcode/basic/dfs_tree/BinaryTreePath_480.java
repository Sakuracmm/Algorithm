package com.ll.lintcode.basic.dfs_tree;

import com.ll.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreePath_480 {

    public List<String> binaryTreePath(TreeNode root){
        List<String> res = new ArrayList<>();

        if(root == null){
            return  res;
        }
        if(root.left == null && root.right == null){
            res.add(root.val + "");
            return res;
        }

        List<String> leftRes = binaryTreePath(root.left);
        List<String> rightRes = binaryTreePath(root.right);
        for(String left : leftRes){
            res.add(root.val + "->" +left);
        }
        for(String right : rightRes){
            res.add(root.val + "->");
        }
        return res;
    }

}
