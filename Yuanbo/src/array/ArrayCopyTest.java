package array;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayCopyTest {

	public static void main(String[] args) {
		// ��Դ���鸴�Ʋ�������
		String[] arr ={"james", "dajun", "tom", "Lily"};
		arr =Arrays.copyOf(arr, 10);
		System.out.println(Arrays.toString(arr));
		System.out.println("The length of new array is:" + arr.length);
		
		// ��Դ���鸴�Ƶ�Ŀ�����飬�����ƶ����Ƶĳ���
		String[] dest = new String[5];
		System.arraycopy(arr, 0, dest, 0, dest.length);
		System.out.println(Arrays.toString(dest));
		
		// ʹ��goodArrayCopy����,�����ƶ�������, dest�ĳ��ȴ�5����10
		dest = (String[])goodArrayGrow(dest, 10);
		System.out.println(Arrays.toString(dest));
	}
	    // �˴��������鱻����Object���룬newLength�������ԭ�����鳤��
	public static Object goodArrayGrow(Object srcArr, int newLength){
		Class arrClass = srcArr.getClass();
		if (!arrClass.isArray())
			return null;
		Class arrType = arrClass.getComponentType();
		int length = Array.getLength(srcArr);
		
		Object newArray = Array.newInstance(arrType, newLength);
		System.arraycopy(srcArr, 0, newArray, 0, length);
		return newArray;		
	}

}
