package callback;

class BadBoy {
	String name;

	public BadBoy(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        //����
	public void hit(Hitable hitable) {
		System.out.println("----------------BEGIN----------------");
		System.out.println("badboy " + this.getName() + "����"
				+ hitable.getName() + "һȭ");
		hitable.beHit(this); // callback method
		System.out.println("-----------------END----------------");
	}

}
