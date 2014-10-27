package algorithm;

public class Recursive {

	public Recursive() {
		// TODO Auto-generated constructor stub
	}


	/*5. 排序
	下面是不同排序算法的时间复杂度，你可以去wiki看一下这些算法的基本思想。

	Algorithm	Average Time	Worst Time	Space
	冒泡排序	n^2	n^2	1
	选择排序	n^2	n^2	1
	Counting Sort	n+k	n+k	n+k
	Insertion sort	n^2	n^2	 
	Quick sort	n log(n)	n^2	 
	Merge sort	n log(n)	n log(n)	depends
	*/


	/*6. 递归 vs. 迭代
	对程序员来说，递归应该是一个与生俱来的思想（a built-in thought），可以通过一个简单的例子来说明。

	问题： 有n步台阶，一次只能上1步或2步，共有多少种走法。

	步骤1:找到走完前n步台阶和前n-1步台阶之间的关系。

	为了走完n步台阶，只有两种方法：从n-1步台阶爬1步走到或从n-2步台阶处爬2步走到。如果f(n)是爬到第n步台阶的方法数，那么f(n) = f(n-1) + f(n-2)。

	步骤2: 确保开始条件是正确的。

	f(0) = 0;
	f(1) = 1;*/

	public static int f(int n){

	    if(n <= 2) return n;

	    int x = f(n-1) + f(n-2);

	    return x;
	} 
/*	递归方法的时间复杂度是n的指数级，因为有很多冗余的计算，如下：

	f(5)
	f(4) + f(3)
	f(3) + f(2) + f(2) + f(1)
	f(2) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)
	f(1) + f(0) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)*/

//	直接的想法是将递归转换为迭代：
	public static int fno(int n) {
		 

	    if (n <= 2){

	        return n;

	    }
	 

	    int first = 1, second = 2;

	    int third = 0;
	 

	    for (int i = 3; i <= n; i++) {

	        third = first + second;

	        first = second;

	        second = third;

	    }
	 

	    return third;
	}
//	对这个例子而言，迭代花费的时间更少
	/*	7. 动态规划
	动态规划是解决下面这些性质类问题的技术：

	一个问题可以通过更小子问题的解决方法来解决。
	有些子问题的解可能需要计算多次。
	子问题的解存储在一张表格里，这样每个子问题只用计算一次。
	需要额外的空间以节省时间。
	爬台阶问题完全符合上面的四条性质，因此可以用动态规划法来解决。*/
	
	public static int[] A = new int[100];
	 

	public static int f3(int n) {

	    if (n <= 2)

	        A[n]= n;
	 

	    if(A[n] > 0)

	        return A[n];

	    else

	        A[n] = f3(n-1) + f3(n-2);//store results so only calculate once!

	    return A[n];
	}

	
/*	8. 位操作
	位操作符：

	OR (|)	AND (&)	XOR (^)	Left Shift (<<)	Right Shift (>>)	Not (~)
	1|0=1	1&0=0	1^0=1	0010<<2=1000	1100>>2=0011	~1=0
	获得给定数字n的第i位：(i从0计数并从右边开始)*/
	public static boolean getBit(int num, int i){

	    int result = num & (1<<i);
	 

	    if(result == 0){

	        return false;

	    }else{

	        return true;

	    }
	}

	
	/*	例如，获得数字10的第2位：

	i=1, n=10
	1<<1= 10
	1010&10=10
	10 is not 0, so return true;

	9. 概率问题
	解决概率相关的问题通常需要很好的规划了解问题（formatting the problem），这里刚好有一个这类问题的简单例子：

	一个房间里有50个人，那么至少有两个人生日相同的概率是多少？（忽略闰年的事实，也就是一年365天）

	计 算某些事情的概率很多时候都可以转换成先计算其相对面。在这个例子里，我们可以计算所有人生日都互不相同的概率，也就是：365/365 + 364/365 + 363/365 + 365-n/365 + 365-49/365，这样至少两个人生日相同的概率就是1 – 这个值。
*/
	
	
	public static double caculateProbability(int n){

	    double x = 1; 
	 

	    for(int i=0; i<n; i++){

	        x *=  (365.0-i)/365.0;

	    }
	 

	    double pro = Math.round((1-x) * 100);

	    return pro/100;
	}
//	calculateProbability(50) = 0.97

/*	10. 排列组合
	组合和排列的区别在于次序是否关键。*/
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(caculateProbability(50));
//	}
	}
