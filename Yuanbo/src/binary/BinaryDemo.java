package binary;

/**
 * 
 * @description  change the binary to integer
 * @version currentVersion(1.0)  
 * @author Yuanbo Zhu  
 * @createtime Aug 28, 2015 5:20:04 PM
 */
public class BinaryDemo {

	public BinaryDemo() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// change the binary to int
		int bi = 0b1011;
		System.out.println(bi); // 10
		System.out.println(Integer.parseInt("1010", 8));
	}

}
