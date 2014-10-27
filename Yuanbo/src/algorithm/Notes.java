package algorithm;

/*
 * toCharyArray() // ����ַ�����Ӧ��char����

 Arrays.sort()  // ��������

 Arrays.toString(char[] a) // ����ת���ַ���

 charAt(int x) // ���ĳ�����������ַ�

 length() // �ַ�������

 length // �����С
 */

/**
 * ���� �ڵ�
 * 
 * @description
 * @version currentVersion(1.0)
 * @author Rambo
 * @createtime 2013��12��3�� ����3:43:51
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
 * ������������ص�һЩ���
 * 
 * ƽ�� vs. ��ƽ�⣺ƽ��������У�ÿ���ڵ����������������������Ϊ1��1��0���� ����������Full Binary
 * Tree������Ҷ�ӽڵ���Ϊ��ÿ���ڵ㶼���������ӡ� ������������Perfect Binary
 * Tree�����Ǿ����������ʵ��������������е�Ҷ�ӽڵ㶼����ͬ����Ȼ���ͬһ��Σ���ÿ�����ڵ㶼�������������ӡ� ��ȫ��������Complete
 * Binary Tree�����������У����ܳ������һ����ÿһ�㶼����ȫ�����������нڵ㶼���뾡�������󿿡�
 */

