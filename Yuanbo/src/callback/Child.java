package callback;

public class Child implements Hitable {
	String name;

	public Child(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void beHit(BadBoy boy) {
		System.out.println("child " + this.getName() + "" + boy.getName()
				+ "");
	}

}
