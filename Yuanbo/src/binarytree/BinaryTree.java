/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * binarytree -> BinaryTree.java
 * Created on 10 Aug 2017-11:01:55 am
 */
package binarytree;

import java.util.ArrayList;
import java.util.Queue;

/**
 * function description：
 *
 *　二叉树的构建　遍历　
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  10 Aug 2017 11:01:55 am
 */
public class BinaryTree<T> {

	T data;
	BinaryTree<T> left;
	BinaryTree<T> right;
	
	
	BinaryTree(T d){
		this.data = d;
		left=null;
		right=null;
	}
	
	BinaryTree(){
	}
	
	

	public void buildTree(int[] data) {
		if(data != null && data.length > 0) {
			root = new BinaryTree<Integer>(data[0]);
			for (int i = 1; i < data.length; i++) {
				insert(root, data[i]);
			}
		}
	}
	
	/**
	 * 将数值插入到二叉树中，比当前结点小或等于当前结点的插在当前结点的左侧，比当前结点大的数插在当前结点的右侧，每次从根结点开始递归比较
	 * 
	 * @param node
	 *            当前的结点，就是根结点，只是每次根结点的左右子孙更新
	 * @param data
	 *            要插入的数值
	 * @return 新排好的二叉树
	 */

	private BinaryTree<Integer> insert(BinaryTree<Integer> node, int data) {


		if(node == null)
			node = new BinaryTree<Integer>(data);
		else if(data <= (int) node.data )	
			node.left = insert(node.left, data);
		else
			node.right = insert(node.right, data);
		return node;
	}
	
	// left root right 
	private static void printTree(BinaryTree<Integer> node) {
		if (node != null) {
			// left, node itself, right
			printTree(node.left);
			System.out.print(node.data + "  ");
			printTree(node.right);
		}
	}
	static BinaryTree<Integer> root = null;
	
	public static void main(String[] args){
		
		
		BinaryTree<Integer> bTree = new BinaryTree<Integer>();
		int[] data = { 2, 8, 7, 4, 9, 3, 1, 6, 7, 5 };
//		int[] data = { 1,5, 3, 4, 5, 4, 7};
		
		bTree.buildTree(data);
		System.out.println("mid: ");
		printTree(root);
		System.out.println("\npre: ");
		preOrder(root);
		System.out.println("\npost:");
		postOrder(root);
		System.out.println("\nlayer:");
		layerOrder(root);

	}
	//root left right 
	static void preOrder(BinaryTree<Integer> node){
		
		if(node != null){
			
			System.out.print(node.data + "  ");
			preOrder(node.left);
			preOrder(node.right);
		}
		
	}
	//left right root
	static void postOrder(BinaryTree<Integer> node){
		
		if(node != null){
			postOrder(node.left);
			postOrder(node.right);
			System.out.print(node.data + "  ");
		}
		
	}
	//left right root
	static void layerOrder(BinaryTree<Integer> node){
		
		if(node != null){
			ArrayList<BinaryTree<Integer>> list = new ArrayList<BinaryTree<Integer>>();
			list.add(node);
			while(!list.isEmpty()){
				BinaryTree<Integer> n = list.remove(0);
				System.out.print(n.data + "  ");
				if(n.left != null) list.add(n.left);
				if(n.right != null) list.add(n.right);
			}
		}
		
	}
	
}
