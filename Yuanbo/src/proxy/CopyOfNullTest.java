package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ��̬������ʹ�õ���һ���ӿ�InvocationHandler��һ��������Proxy �������������ʹ��ʵ���˶�̬����Ĺ��ܡ�
 * ��ôʲô�Ƕ�̬�����أ�
 * ����ƽ��˵�Ĵ�������ָ�� ��ÿ��������дһ�������࣬�Ժ�Ҫʹ��ĳ��������ʱ��ֻҪ�������Ĵ�����Ķ���Ȼ����ô�����ķ����Ϳ����ˡ�
 * ����������������ľ����࣬�Ǿ���Ҫ�����Ĵ�����ſ��ԣ���������Ȼ�����ʡ����Զ�̬�����Ӧ�˶����ˣ�����ֻҪдһ����ʵ��
 * InvocationHandler ��ʵ������invoke������Ȼ������Proxy�Ĺ�������newProxyInstance��������һ����������������ͬ������ʵ�ֶԾ�����Ĵ����ܡ�
 * ����������ĸ������ֻ࣬Ҫ��Handler�����´����е�Invoker���Ĺ��������������������ʵ���Ϳ����ˡ��о��ǲ����Լ�Ϊ�þ���������һ���������أ��Ǻ�~
 */

//�ӿ���
interface AbstractClass {

	public void show();

}

// ������A
class ClassA implements AbstractClass {

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("����A�࣡");
	}
}

// ������B
class ClassB implements AbstractClass {

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("����B�࣡");
	}
}
//��̬�����࣬ʵ��InvocationHandler�ӿ�
class Invoker implements InvocationHandler {
	AbstractClass ac;

	public Invoker(AbstractClass ac) {
		this.ac = ac;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arg)
			throws Throwable {
		//����֮ǰ������һЩ����
		method.invoke(ac, arg);
		//����֮��Ҳ������һЩ����
		return null;
	}
}

/**
 * ������
 * @author С·
 */
public class CopyOfNullTest {

	public static void main(String[] args) {
		//����������ClassB�Ĵ������
		Invoker invoker1=new Invoker(new ClassA());
		//��þ�����ClassA�Ĵ���
		AbstractClass ac1 = (AbstractClass) Proxy.newProxyInstance(
				AbstractClass.class.getClassLoader(),
				new Class[] { AbstractClass.class }, invoker1);
		//����ClassA��show������
		ac1.show();
		
		
		//����������ClassB�Ĵ������
		Invoker invoker2=new Invoker(new ClassB());
		//��þ�����ClassB�Ĵ���
		AbstractClass ac2 = (AbstractClass) Proxy.newProxyInstance(
				AbstractClass.class.getClassLoader(),
				new Class[] { AbstractClass.class }, invoker2);
		//����ClassB��show������
		ac2.show();

	}
}
