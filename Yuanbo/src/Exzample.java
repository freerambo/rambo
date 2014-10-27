
public class Exzample {

	/**
	 * @param args
	 */
	int k;
//	int i;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Exzample e = new Exzample();
		int i = 0;
		i = i++;
		System.out.println(i);//0 or 1  result 0
		e.test();
	}
	public void test(){
		System.out.println(k);
	}
}
