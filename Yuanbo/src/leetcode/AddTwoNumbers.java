/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * leetcode -> AddTwoNumbers.java
 * Created on 18 Jul 2017-11:21:02 am
 */
package leetcode;

/**
 * 
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8

 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Jul 2017 11:21:02 am
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class AddTwoNumbers {


	 
	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     18 Jul 2017 11:21:02 am
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);
		ListNode l2  = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(9);
		
		
		new AddTwoNumbers().addTwoNumbers(l1,l2);
	}
	

	 
	 
    public  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        
    	if(l1 == null) return l2;
    	if(l2 == null) return l1;
    	
    	ListNode ln = new ListNode(0);
    	ListNode next = ln;
    	int carry = 0;
    	while((l1 != null || l2 != null)){
    		int val1 = l1!= null? l1.val : 0;
    		int val2 = l2!= null? l2.val : 0;

    		int value = val1 + val2 + carry; 
        	carry = value/10; 
        	int val = value%10;
        	
        	next.next = new ListNode(val);
        	
        	System.out.println(val);
        	next = next.next;
        	
    	  if (l1 != null) l1 = l1.next;
          if (l2 != null) l2 = l2.next;
        	
    	}
    	if(carry != 0){
    		System.out.println(carry);
        	next.next = new ListNode(carry);
    	}
    	return ln.next;
	
    	
    }

}
