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
//		int bi = 0b1011;
		int bi = 0x131;

		System.out.println(bi); // 10
		System.out.println(Integer.parseInt("1010", 8));
	}
	


static int binaryToInt (String binary){
    char []cA = binary.toCharArray();
    int result = 0;
    for (int i = cA.length-1;i>=0;i--){
        //111 , length = 3, i = 2, 2^(3-3) + 2^(3-2)
        //                    0           1  
        if(cA[i]=='1') result+=Math.pow(2, cA.length-i-1);
    }
    return result;
}


}
