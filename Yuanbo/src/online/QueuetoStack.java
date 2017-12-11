/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * online -> QueuetoStack.java
 * Created on 26 Nov 2017-9:56:33 pm
 */
package online;

import java.util.Queue;
import java.util.Stack;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  26 Nov 2017 9:56:33 pm
 */
public class QueuetoStack {
	
	
static class MyStack{
		
		Integer[] a = null;
		int size = -1;
		int top = -1;
		
		MyStack(int size){
			a = new Integer[size];
			this.size = size;
		}
		
		public void push(int val){
			if(top < size){
				a[++top] = val;
			}
		}
		
		public Integer pop(){
			if(top >= 0){
				int temp = a[top];
				top--;
				return temp;
			}
			return null;
			
		}
	}
	

	static class MyQueue{
		
		Integer[] a = null;
		int size = -1;
		int tail = -1;
		
		MyQueue(int size){
			a = new Integer[size];
			this.size = size;
		}
		
		public void add(int val){
			if(tail < size){
				a[++tail] = val;
			}
		}
		
		public Integer pop(){
			if(size > 0){
				int temp = a[0];
				for(int i = 0; i < tail; i++)
					a[i] = a[i+1];
				tail--;
				return temp;
			}
			return null;
			
		}
	}
	
	public static void main(String[] args){
		
		
		MyQueue q = new MyQueue(5);
		
		q.add(1);
		q.add(2);
		q.add(3);
		q.add(4);
		q.add(5);
		System.out.println(q.pop());
		System.out.println(q.pop());
		System.out.println(q.pop());
		System.out.println(q.pop());
		System.out.println(q.pop());

		

		 int[] data1 = {1, 2, 3, 4, 5};
      int[] data2 = {4, 5, 3, 2, 1};
      int[] data3 = {4, 5, 2, 3, 1};
		  
      System.out.println(compareTwoStack(data1, data2));
      System.out.println(compareTwoStack(data1, data3));
	}
	/**
	 *  
	 *  判断栈的push和pop序列是否一致：

    通俗一点讲：已知一组数据1、2、3、4、5依次进栈，那么它的出栈方式有很多种，请判断一下给出的出栈方式是否是正确的？
	 * @function:
	 * @param a1
	 * @param a2
	 * @return
	 * @author: Rambo Zhu     27 Nov 2017 12:25:59 am
	 */
	
	static boolean compareTwoStack(int a1[], int[] a2){

		Stack<Integer> s = new Stack<Integer>();
		int j = 0;
		for(int i : a1){
			s.push(i);
			while(s.size() > 0 && s.peek() == a2[j]){
				j++;
				s.pop();
			}
		}
		return s.size() == 0;
	}
	
	
	

}
