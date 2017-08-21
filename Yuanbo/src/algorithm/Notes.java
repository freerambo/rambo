package algorithm;

/*
 * toCharyArray() // 获得字符串对应的char数组

 Arrays.sort()  // 数组排序

 Arrays.toString(char[] a) // 数组转成字符串

 charAt(int x) // 获得某个索引处的字符

 length() // 字符串长度

 length // 数组大小
 */

/**
 * 链表 节点
 * 
 * @description
 * @version currentVersion(1.0)
 * @author Rambo
 * @createtime 2013年12月3日 下午3:43:51
 */
class Node {

	int val;

	Node next;

	Node(int x) {

		val = x;

		next = null;

	}
}

// stack
class Stack {

	Node top;

	public Node peek() {

		if (top != null) {

			return top;

		}

		return null;

	}

	public Node pop() {

		if (top == null) {

			return null;

		} else {

			Node temp = new Node(top.val);

			top = top.next;

			return temp;

		}

	}

	public void push(Node n) {

		if (n != null) {

			n.next = top;

			top = n;

		}

	}
}

class Queue {

	Node first, last;

	public void enqueue(Node n) {

		if (first == null) {

			first = n;

			last = first;

		} else {

			last.next = n;

			last = n;

		}

	}

	public Node dequeue() {

		if (first == null) {

			return null;

		} else {

			Node temp = new Node(first.val);

			first = first.next;

			return temp;

		}

	}
}

class TreeNode {

	int value;

	TreeNode left;

	TreeNode right;
}

/*
 * 下面是与树相关的一些概念：
 * 
 * 平衡 vs. 非平衡：平衡二叉树中，每个节点的左右子树的深度相差至多为1（1或0）。 满二叉树（Full Binary
 * Tree）：除叶子节点以为的每个节点都有两个孩子。 完美二叉树（Perfect Binary
 * Tree）：是具有下列性质的满二叉树：所有的叶子节点都有相同的深度或处在同一层次，且每个父节点都必须有两个孩子。
 * 完全二叉树（Complete
 * Binary Tree）：二叉树中，可能除了最后一个，每一层都被完全填满，且所有节点都必须尽可能想左靠。
 * 
 */

