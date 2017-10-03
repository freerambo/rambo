//https://www.dotnetperls.com/format-java
public class StringFormart {

	/**
	 * 
		If we observe the given function, we can see a pattern
		G(a, b, 0) =  a
		G(a, b, 1) =  b
		G(a, b, 2) =  a +  b
		G(a, b, 3) =  a + 2b
		G(a, b, 4) = 2a + 3b
		G(a, b, 5) = 3a + 5b
		G(a, b, 6) = 5a + 8b
		G(a, b, 7) = 8a + 13b
		... and so on.
		
		So if we consider the Fibonacci series as 0,1,1,2,3,5,8,13... with F(0) = 0, 
		 F(N) Fibonacci numbers， then
		
		G(A, B, N)  = A                      for N = 0
		            = F(N-1)*A + F(N)*B      for all N > 0 

	 */
	public static int solution(int A, int B, int N) {
		// write your code in Java SE 8
		// in case of negtive number of A, B or N return an error code -1
		if(A < 0 || B < 0 || N < 0) return -1;
		if (N == 0)
			return A;
		return fbnc(N - 1) * A + fbnc(N) * B;
	}

	int MOD = 1000000007;
	int MAX_N = 1000000000;
	static int MAX = 1000;

	// Create a fuibonacci array
	static int[] f = new int[MAX];

	// Returns n'th fuibonacci number using table f[]
	static int fbnc(int n) {
		// Base cases
		if (n == 0)
			return 0;
		if (n == 1 || n == 2)
			return (f[n] = 1);

		// If fib(n) is already computed
		if (f[n] != 0)
			return f[n];

		int k = (n & 1) != 0 ? (n + 1) / 2 : n / 2;

		// Applyting above formula [Note value n&1 is 1
		// if n is odd, else 0.
		f[n] = (n & 1) != 0 ? (fbnc(k) * fbnc(k) + fbnc(k - 1) * fbnc(k - 1)) : (2 * fbnc(k - 1) + fbnc(k)) * fbnc(k);
		return f[n];
	}


    
	public static void main(String[] args) {

  /*      String first = "Marcus";
        String last = "Aurelius";

        // Use simple string format.
        String value = String.format("%s %s", first, last);
        System.out.println(value);

        // Use indexes before simple string format.
        value = String.format("%1s %2s", first, last);
        System.out.println(value);

        // Use $ symbol before string character.
        value = String.format("%1$s %2$s", first, last);
        System.out.println(value);*/
        
        System.out.println(solution(3,4,9));

    }

}

