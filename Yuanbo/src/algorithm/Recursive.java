package algorithm;

public class Recursive {

	public Recursive() {
		// TODO Auto-generated constructor stub
	}


	/*5. ����
	�����ǲ�ͬ�����㷨��ʱ�临�Ӷȣ������ȥwiki��һ����Щ�㷨�Ļ���˼�롣

	Algorithm	Average Time	Worst Time	Space
	ð������	n^2	n^2	1
	ѡ������	n^2	n^2	1
	Counting Sort	n+k	n+k	n+k
	Insertion sort	n^2	n^2	 
	Quick sort	n log(n)	n^2	 
	Merge sort	n log(n)	n log(n)	depends
	*/


	/*6. �ݹ� vs. ����
	�Գ���Ա��˵���ݹ�Ӧ����һ������������˼�루a built-in thought��������ͨ��һ���򵥵�������˵����

	���⣺ ��n��̨�ף�һ��ֻ����1����2�������ж������߷���

	����1:�ҵ�����ǰn��̨�׺�ǰn-1��̨��֮��Ĺ�ϵ��

	Ϊ������n��̨�ף�ֻ�����ַ�������n-1��̨����1���ߵ����n-2��̨�״���2���ߵ������f(n)��������n��̨�׵ķ���������ôf(n) = f(n-1) + f(n-2)��

	����2: ȷ����ʼ��������ȷ�ġ�

	f(0) = 0;
	f(1) = 1;*/

	public static int f(int n){

	    if(n <= 2) return n;

	    int x = f(n-1) + f(n-2);

	    return x;
	} 
/*	�ݹ鷽����ʱ�临�Ӷ���n��ָ��������Ϊ�кܶ�����ļ��㣬���£�

	f(5)
	f(4) + f(3)
	f(3) + f(2) + f(2) + f(1)
	f(2) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)
	f(1) + f(0) + f(1) + f(1) + f(0) + f(1) + f(0) + f(1)*/

//	ֱ�ӵ��뷨�ǽ��ݹ�ת��Ϊ������
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
//	��������Ӷ��ԣ��������ѵ�ʱ�����
	/*	7. ��̬�滮
	��̬�滮�ǽ��������Щ����������ļ�����

	һ���������ͨ����С������Ľ�������������
	��Щ������Ľ������Ҫ�����Ρ�
	������Ľ�洢��һ�ű�������ÿ��������ֻ�ü���һ�Ρ�
	��Ҫ����Ŀռ��Խ�ʡʱ�䡣
	��̨��������ȫ����������������ʣ���˿����ö�̬�滮���������*/
	
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

	
/*	8. λ����
	λ��������

	OR (|)	AND (&)	XOR (^)	Left Shift (<<)	Right Shift (>>)	Not (~)
	1|0=1	1&0=0	1^0=1	0010<<2=1000	1100>>2=0011	~1=0
	��ø�������n�ĵ�iλ��(i��0���������ұ߿�ʼ)*/
	public static boolean getBit(int num, int i){

	    int result = num & (1<<i);
	 

	    if(result == 0){

	        return false;

	    }else{

	        return true;

	    }
	}

	
	/*	���磬�������10�ĵ�2λ��

	i=1, n=10
	1<<1= 10
	1010&10=10
	10 is not 0, so return true;

	9. ��������
	���������ص�����ͨ����Ҫ�ܺõĹ滮�˽����⣨formatting the problem��������պ���һ����������ļ����ӣ�

	һ����������50���ˣ���ô������������������ͬ�ĸ����Ƕ��٣��������������ʵ��Ҳ����һ��365�죩

	�� ��ĳЩ����ĸ��ʺܶ�ʱ�򶼿���ת�����ȼ���������档�������������ǿ��Լ������������ն�������ͬ�ĸ��ʣ�Ҳ���ǣ�365/365 + 364/365 + 363/365 + 365-n/365 + 365-49/365����������������������ͬ�ĸ��ʾ���1 �C ���ֵ��
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

/*	10. �������
	��Ϻ����е��������ڴ����Ƿ�ؼ���*/
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(caculateProbability(50));
//	}
	}
