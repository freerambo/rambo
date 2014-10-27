package callback;
public class BigMan implements Hitable {
	String name;

	public BigMan(String name) {
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
		System.out.println("bigman " + this.getName() + "∞—" + boy.getName()
				+ "…±À¿¡À");
	}

}


