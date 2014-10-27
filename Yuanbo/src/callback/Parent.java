package callback;

/* ���̣߳�ʵ�ֻص��ӿ� */
public class Parent implements ICallBack {
	public static void main(String[] args) {
		Parent parent = new Parent();
		Thread son = new Son(parent);
		son.start();
	}

	public void output(String str) {
		System.out.println(str);
	}
}

/* �ڲ��߳��� */
class Son extends Thread {
	private ICallBack event;

	public Son(ICallBack callback) {
		event = callback;
	}

	public void run() {
		try {
			java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			while (true) {
				Thread.currentThread().sleep(3000);
				event.output(fmt.format(new java.util.Date()));
				Thread.currentThread().sleep(3000);
			}
		} catch (Exception e) {
		}
	}
}

/* �ص��ӿ� */
interface ICallBack {
	public void output(String str);
}
