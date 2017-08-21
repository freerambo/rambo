/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * interview -> BTree.java
 * Created on 7 Aug 2017-11:27:47 am
 */
package interview;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  7 Aug 2017 11:27:47 am
 */

public class BTree {

	private Node root;

	/**
	 * 创建一个空的二叉树
	 */
	public BTree() {
		root = null;
	}
	
	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     7 Aug 2017 11:27:47 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BTree b = new BTree();
		int[] data = { 2, 8, 7, 4, 9, 3, 1, 6, 7, 5 };
		b.root = b.buildTree(data);
		
		System.out.print( " - " + b.root.data);
 
	}
	
	public Node buildTree(int[] data) {
		for(int i : data){
			root = insert(root, i);
		}
		return root;
	}
	
	private Node insert(Node node, int data) {
		if(node == null) node = new Node(data);
		else {
			if(data <= node.data)
				node.left = insert(node.left,data);
			else
				node.right = insert(node.right,data);
		}
		return node;
	}
	
	private void print(Node node) {
		if(node != null){
			print(node.left);
			System.out.print(node.data + " - ");
			print(node.right);
		}
		
	}
	

}
