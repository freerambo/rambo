package callback;

public class CallBackTest {
	public static void main(String[] args) {
		BadBoy badboy = new BadBoy("Tom");
		Hitable child = new Child("Cat");
		Hitable bigman = new BigMan("Boris");
		badboy.hit(child);
		badboy.hit(bigman);
	}
}
