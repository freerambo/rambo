/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * algorithm -> df.java
 * Created on 23 Oct 2017-9:26:02 pm
 */
package algorithm;

/**
 * * Java program to find all permutations of a given String using recursion. *
 * For example, given a String "XYZ", this program will print all 6 possible
 * permutations of * input e.g. XYZ, XZY, YXZ, YZX, ZXY, XYX * * @author Javin
 * Paul
 */
public class StringPermutations {
	public static void main(String args[]) {
//		permutation("123");
		
		permutations("", "abc");
	}

	/*
	 * * A method exposed to client to calculate permutation of String in Java.
	 */
	public static void permutation(String input) {
		permutation("", input);
	}

	/*
	 * * Recursive method which actually prints all permutations * of given
	 * String, but since we are passing an empty String * as current permutation
	 * to start with, * I have made this method private and didn't exposed it to
	 * client.
	 */
	private static void permutation(String perm, String word) {
		if (word.isEmpty()) {
			System.err.println(perm + word);
		} else {
			for (int i = 0; i < word.length(); i++) {
				permutation(perm + word.charAt(i), word.substring(0, i) + word.substring(i + 1, word.length()));
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * fix one char and combined others  
	 * @function:
	 * @param fix
	 * @param word
	 * @author: Rambo Zhu     24 Oct 2017 4:20:08 pm
	 */
	public static void permutations(String fix, String word){
		
		if(word.isEmpty())
			System.out.println(fix+word);
		else 
			for(int i =0; i < word.length(); i++){
				permutations(fix + word.charAt(i), word.substring(0,i)+word.substring(i+1,word.length()));
			}
		
	}
	
	
	
	
	
	
	
}
