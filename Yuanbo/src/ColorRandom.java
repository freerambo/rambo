import java.util.Random;
/**
 * color random selection 
 * @author rambo 2014-10-27 17:20:25
 *
 */
public class ColorRandom {

	public static void main(String[] args) {
		int a[] = new int[3];
		String[] colors = { "red", "green", "black", "blue", "white" };
		Random rand = new Random();
		
		outter: while (true) {
			a[0] = rand.nextInt(5);
			a[1] = rand.nextInt(5);
			a[2] = rand.nextInt(5);
			while (a[0] != a[1] && a[0] != a[2] && a[1] != a[2]) {
				System.out.println(colors[a[0]] + " , "
						+ colors[a[1]] + " , " + colors[a[2]]);
				break outter;
			}

		}

	}
}
