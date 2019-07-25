package array;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayCopyTest {

	public static void main(String[] args) {
		// �1�7�1�7�0�6�1�7�1�7�1�7�`�1�7�0�8�1�7�1�7�1�7�1�7�1�7�1�7�1�7
		String[] arr ={"james", "dajun", "tom", "Lily"};
		arr =Arrays.copyOf(arr, 10);
		System.out.println(Arrays.toString(arr));
		System.out.println("The length of new array is:" + arr.length);
		
		// �1�7�1�7�0�6�1�7�1�7�1�7�`�1�7�0�1�1�7�0�7�1�7�1�7�1�7�1�7�1�71�7�1�7�1�7�1�7�1�7�0�2�1�7�1�7�1�7�1�7�0�1�0�5�1�7�1�7�1�7
		String[] dest = new String[5];
		System.arraycopy(arr, 0, dest, 0, dest.length);
		System.out.println(Arrays.toString(dest));
		
		// �0�0�1�7�1�7goodArrayCopy�1�7�1�7�1�7�1�7,�1�7�1�7�1�7�1�7�1�7�0�2�1�7�1�7�1�7�1�7�1�7�1�7�1�7, dest�1�7�0�5�1�7�1�7�0�9�1�75�1�7�1�7�1�7�1�710
		dest = (String[])goodArrayGrow(dest, 10);
		System.out.println(Arrays.toString(dest));
	}
	    // �1�7�0�4�1�7�1�7�1�7�1�7�1�7�1�7�1�7�1�7���1�7�1�7�1�7�1�7Object�1�7�1�7�1�7�0�3newLength�1�7�1�7�1�7�1�7�1�7�1�7�1�7�0�9�1�7�1�7�1�7�1�7�1�7�A�1�7�1�7
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
