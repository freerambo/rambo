package array;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayCopyTest {

	public static void main(String[] args) {
		// 将源数组复制并且扩容
		String[] arr ={"james", "dajun", "tom", "Lily"};
		arr =Arrays.copyOf(arr, 10);
		System.out.println(Arrays.toString(arr));
		System.out.println("The length of new array is:" + arr.length);
		
		// 将源数组复制到目标数组，并且制定复制的长度
		String[] dest = new String[5];
		System.arraycopy(arr, 0, dest, 0, dest.length);
		System.out.println(Arrays.toString(dest));
		
		// 使用goodArrayCopy复制,并且制定新容量, dest的长度从5扩大到10
		dest = (String[])goodArrayGrow(dest, 10);
		System.out.println(Arrays.toString(dest));
	}
	    // 此处整个数组被当成Object传入，newLength必须大于原先数组长度
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
