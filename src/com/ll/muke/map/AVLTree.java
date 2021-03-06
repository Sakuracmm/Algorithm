package com.ll.muke.map;

import com.ll.muke.set.FileOperation;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;


        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    //判断这棵树是否仍然是二分搜索树
    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;
        }
        return true;
    }

    //利用中序遍历判断
    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null)
            return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    //判断该二叉树是否是一棵平衡二叉树
    public boolean isBalanced() {
        return isBalanced(root);
    }

    //判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced(Node node) {

        if (node == null)
            return true;
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);

    }

    //获得结点node的高度
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    //获得结点node的平衡因子
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    //向二分搜索树中添加新的元素（key, value)
    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    //对结点y进行向右旋转操作，返回旋转后新的结点x
    //             y                                     x
    //           /  \                                 /    \
    //         x     T4           向右旋转（y）     z       y
    //       /  \           --------------------> /  \    /  \
    //     z     T3                             T1   T2  T3   T4
    //    / \
    //  T1   T2
    private Node rightRotate(Node y) {

        Node x = y.left;
        Node T3 = x.right;

        //向右旋转过程
        x.right = y;
        y.left = T3;

        //更新Height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // 对结点y进行向左旋转操作，返回旋转后新的根结点x
    //      y                                               x
    //    /  \                                            /  \
    //  T1    x        向左旋转（y)                     y     z
    //      /  \    --------------------------->      /  \   /  \
    //     T2   z                                    T1  T2 T3  T4
    //        /  \
    //      T3   T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        //向左旋转过程
        x.left = y;
        y.right = T2;

        //更新heigh
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    //向以node为根的二分搜索树中插入元素（key, value）递归算法
    //返回插入新节点后二分搜索树的根
    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }

        //更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
//        if(Math.abs(balanceFactor) > 1)
//            System.out.println("unbalanced : " + balanceFactor);

        //平衡维护
        //LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);
        //RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);
        //LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        return node;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    //删除掉以node为根的二分搜索树中key值为key的结点，递归算法
    //返回删除节点后新的二叉树的根结点
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }

        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {  //e.compareTo(node.e) == 0
            //待删除结点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }
            //待删除结点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            }
            //待删除结点左右子树都不为空的情况
            else {
                Node successor = minmum(node.right); //找到要删除结点右子树中最小节点
                //successor.right = removeMin(node.right);    //删除掉结点右子树中最小结点并且返回这个右子树的根结点，总体上来看是当前操作结点的右子树的根结点
                successor.right = remove(node.right, successor.key);
                successor.left = node.left; //将新节点的左孩子指向当前节点的左孩子
                retNode = successor;
            }
        }

        if(retNode == null)
            return null;

        //更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        //平衡维护
        //LL
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0){
            return rightRotate(retNode);
        }
        //RR
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0){
            return leftRotate(retNode);
        }
        //LR
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) < 0){
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) > 0){
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }


        return retNode;
    }


    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }

        node.value = newValue;

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //返回以node为根结点的二分搜索树中，key所在的结点
    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.equals(node.key)) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            return getNode(node.right, key);
        }

    }

    //返回以node为根的二分搜索树为根的最小值所在的结点
    private Node minmum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minmum(node.left);
    }

    //删除掉以node为根的二分搜索树中的最小结点
    //返回删除结点后新的二分搜索树的根
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    //返回以node为根的二分搜索树为根的最大值所在的结点
    private Node maxmum(Node node) {
        if (node == null) {
            return null;
        }
        return maxmum(node.right);
    }

    //删除掉以node为根结点的二分搜索树中的最大结点
    //返回删除结点后新的二叉搜索树的根
    private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.left = removeMin(node.right);
        return node;
    }

    public static void main(String[] args) {
        System.out.println("pride and prejudice");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("C:\\Users\\Administrator\\Workspaces\\IDEA\\Algorithm\\src\\pride-and-prejudice.txt", words)) {
            System.out.println("total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String str : words) {
                if (map.contains(str))
                    map.set(str, map.get(str) + 1);
                else
                    map.add(str, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalanced());

            for(String word : words){
                map.remove(word);
                if(!map.isBST() || !map.isBalanced()){
                    throw new RuntimeException("Error!");
                }
            }

        }
        System.out.println();
    }
}
