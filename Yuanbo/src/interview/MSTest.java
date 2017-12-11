package interview;



public class MSTest {
	
	static class Node{
		public int data;
		public Node next;
		Node(){}
		Node(int val){this.data = val;}
	}
	/** 
	 * Time Complexity O(n) Space complexity O(1)
	 * DENOTES: do not use recursion. 
	 * @function: merge two already sorted linked lists, duplicate allowed 
	 * @param head1
	 * @param head2
	 * @return
	 * @author: Rambo Zhu     3 Nov 2017 8:21:42 pm
	 */
	public static Node merge(Node head1, Node head2){
		Node n = new Node(), head = n; 
		Node cur1 = head1, cur2 = head2;
		while(cur1 != null && cur2 != null){
			if(cur1.data <= cur2.data){
				n.next = cur1;
				n = n.next;
				cur1 = cur1.next;
			}
			if(cur1 != null && cur2.data < cur1.data){
				n.next = cur2;
				n = n.next;
				cur2 = cur2.next;
			}
		}
		if(cur1 != null) {
			n.next = cur1;
		}
		if(cur2 != null) {
			n.next = cur2;
		}
		return head.next;
	}

    public static void main(String[] args) {
    	// initialize Node1 in ascendant order
		Node n1 = new Node(1);
		n1.next = new Node(3);
		n1.next.next = new Node(5);
		n1.next.next.next = new Node(7);
		// initialize Node2 in ascendant order
		Node n2 = new Node(2);
		n2.next = new Node(4);
		n2.next.next = new Node(7);
		n2.next.next.next = new Node(8);
		n2.next.next.next.next = new Node(9);

		// merge two node list and return the head
		Node n = merge(n1,n2);
		// out put the results
		while(n != null){
			System.out.print(n.data + "\t");
			n = n.next;
		}
		
    } // expected output: 1	2	4	7	8	9	 
}
