package binary;

/**
 * 二进制练习
 * 
 * @description
 * @version currentVersion(1.0)
 * @author Rambo
 * @createtime 2013年12月6日 下午2:39:19
 */
public class BinaryDemo {

	public BinaryDemo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 二进制字符串转int
		int bi = 0b1010;
		System.out.println(bi); // 10
		System.out.println(Integer.parseInt("1010", 2));
	}

}
