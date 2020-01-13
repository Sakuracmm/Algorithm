package com.ll.lintCode.dfs_tree;

import com.ll.lintCode.utils.TreeNode;

public class FlattenBinaryTree2LinkedList_453 {
    //1. traverse
    private TreeNode lastNode = null;

    public void flatten(TreeNode root){
        if(root == null){
            return;
        }

        if(lastNode != null){
            lastNode.left = null;
            lastNode.right = null;
        }

        lastNode = root;
        TreeNode right = root.right;
        flatten(root.left);
        flatten(right);
    }




}