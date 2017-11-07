package interview;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

/**
 * description： 
 * 代码题： 
 * 输入：1000万行整数型乱序排列文件 
 * 输出：1000万行整数型升序排列文件
 * 
 * 要求： 
 * 
 * 1、使用java语言 
 * 2、假定该数据文件无法一次性加载到内存中处理 
 * 3、关键处理逻辑部分不允许使用三方包 
 * 4、注意代码的可读性、严谨性、可扩展性
 * 
 * 分析过程： java中整数 32 bit -> 4 byte 1000万行整数型 --> 约占内存 40 MB
 * 
 * 根据要求2： 该数据文件无法一次性加载到内存中处理 假设内存为 2MB
 * 
 * 1MB 用来临时数据存储， 32KB 用来IO 缓存
 * 存储方式选择 byte 数组，数组的索引用来表示数据，数组的值零表示不存在，非零表示存在的次数
 * 1 MB 可以存放  2 ^ 21 - 1,048,576 个 byte
 * byte 数组 SIZE --> 1,048,576
 * 
 * Solution 1:  K-Min & BitMap： 
 * 
 * 1. 从 base(初始为零) 开始，  用32KB的Buffer遍历文件
 * 2. 只记录（当前值 -base） 的值 在 0 到  1,048,577数据， 并根据byte数组的下标记录元素的个数
 * 3. 根据byte数据的状态，写入记录的数据 同时加回base，
 * 4. 如果存在多个相同数据，则写入多次，直到byte数组的值减为零
 * 5. 然后base 增加  SIZE大小，
 * 6. 重复步骤 1-6，直到数据超出Integer的范围  
 * 
 * 说明：
 * 此方法在严格内存2MB的条件下，测试样例运行时间约 9 秒；
 * 此方法结合了 K-Min 和 bitmap 
 * 能够处理所有正整数
 * 能够处理重复数据
 * 算法时间复杂度稳定 -不随数据量增大而变化，在全部正整数范围内需执行2047次 即 Integer.MAX_VALUE / SIZE
 * 
 * Integer File Sort completed. It took 8907 milliseconds
 * 
 * Solution 2:  K-Min & InsertSort： 
 * 使用int 数组保存数据，因此数组大小 变为 2^19  -> 524288
 * 
 * 1. 从 base(初始为零) 开始，  用32KB的Buffer遍历文件
 * 2. 大于base插入排序最小的在524288数据
 * 3. 记录int 数组的最大值即最后一个元素，如果为负值 则结束本次循环后终止
 * 4. 将记录的524288个有序数据写入文件，同时int数组置 -1
 * 5. 重复步骤 1-4，直到循环终止  
 * 
 * 说明：
 * 此方法在严格内存2MB的条件下，测试样例运行时间约 150秒；
 * 此方法结合了 K-Min 和 insert sort 
 * 能够处理所有正整数
 * 算法时间复杂度随着数据增大而增大
 * 
 * Integer File Insert Sort completed. It took 150502 milliseconds
 * 不足： 重复数据可能会丢失
 */
public class BigFileBatchSort {

	//used for TopK & Bitmap 
	final static int SIZE = 1 << 21;  // 1,048,576 
	static byte[] bts = new byte[SIZE];  // size 1MB  0 - 1,048,575  for TopK & Bitmap 
	
	final static int BUFFER_SIZE = 1 << 13;  // 8 * 1024 
	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu 6 Nov 2017 6:22:29 pm
	 */
	public static void main(String[] args) {
		// 1 call the K-Min & BitMap sort method  
		new BigFileBatchSort().sort(bts, "C:/Users/zhuyb/input.txt", "C:/Users/zhuyb/outputBit.txt");
		
		// 2 call the K-Min & Insert sort method  
//		new BigFileBatchSort().insertSort(ints, "C:/Users/zhuyb/input.txt", "C:/Users/zhuyb/outputInt.txt");
	}

	/**
	 * 
	 * @function: A method exposed to client to sort a big file in Java
	 * @param bs
	 * @param inputFile    input file full path 
	 * @param outputFile 	output file full path 
	 * @author: Rambo Zhu     6 Nov 2017 7:00:31 pm
	 */
	public void sort(byte[] bts, String inputFile, String outputFile) {
		if(bts == null || inputFile == null || outputFile == null) // input check 
			throw new IllegalArgumentException("非法参数");
	
		long startTime = System.currentTimeMillis();

		int start = SIZE;
		int base = 0;
		while(start > 0){ // util the start over the Max value in int  2047 times
			readFile(inputFile, bts, base);
			writeFile(outputFile, bts, base);
			base = start;
			start += SIZE;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Integer File Sort completed. It took " + (endTime - startTime) + " milliseconds");

	} // out put Integer File Sort completed. It took 8789 milliseconds

	//	read data from file with buffer size 1MB,
	//	I made this method private and didn't exposed it to client
	private void readFile(String inputFile, byte[] bts, int base) {

		BufferedInputStream fis = null;
		BufferedReader reader = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(inputFile));
			reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), BUFFER_SIZE);
			String line = null;
			while ((line = reader.readLine()) != null) {
				Integer i = Integer.valueOf(line);
				i -= base;
				if(i >= 0 && i < SIZE)
					bts[i]++;
			}
			reader.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用32KB的缓冲读取文本文件

	}
	//	write data to file with buffer size 1MB
	//	I made this method private and didn't exposed it to client
	private void writeFile(String outputFile, byte[] bts2, int base) {
		BufferedWriter writer = null;
		try {
			// Construct the BufferedWriter object, set append to true
			writer = new BufferedWriter(new FileWriter(outputFile, true), BUFFER_SIZE);
			// Start writing to the output stream
			for (int i = 0; i < bts2.length; i++) {
				while(bts2[i] > 0) {  // add data to output file including the duplicate data 
					int val = i + base; // add back the base 
					writer.write(val + "\r\n");
					bts2[i]--;  // set bts[index] to zero
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用16KB的缓冲写入文本文件
	}
	
	
	// used for insert sort 
	final static int INT_SIZE = 1 << 19;  // 524288 
	static int[] ints = new int[SIZE];  // size 1MB  0 - 524288 
	static{
		for (int i = 0; i < ints.length; i++) {
			ints[i] = -1; // reset the highest value 
		}
	}	
		
	public void insertSort(int[] ints, String inputFile, String outputFile) {
		
		if(ints == null || inputFile == null || outputFile == null) // input check 
			throw new IllegalArgumentException("非法参数");
	
		long startTime = System.currentTimeMillis();

		boolean run = true;
		int base = 0;
		while(run){ // util the start over the Max value in int  2047 times
			readFileInsert(inputFile, ints, base);
			base = ints[bts.length - 1];
			if(base < 0) run = false; 
			writeFileInsert(outputFile, ints, base);
			for (int i = 0; i < ints.length; i++) {
				ints[i] = -1; // reset to highest value 
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Integer File Insert Sort completed. It took " + (endTime - startTime) + " milliseconds");
	}
	
	/**
	 * insertion sort
	 */
	public static boolean insertionSort(int array[], int val) {
		int n = array.length-1;
		if(array[n] != -1 && array[n] <= val) return false;
		int j = n-1;
		while(j >= 0 && (array[j] == -1 || array[j] > val)){
			array[j + 1] = array[j];
			j--;
		}
		array[j + 1] = val;
		return true;
	}
	
	private void readFileInsert(String inputFile, int[] ints, int base) {
		BufferedInputStream fis = null;
		BufferedReader reader = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(inputFile));
			reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), BUFFER_SIZE);
			String line = null;
			while ((line = reader.readLine()) != null) {
				Integer i = Integer.valueOf(line);
				if(i > base){
					boolean run = insertionSort(ints, i);
					if(!run) break;
				}
			}
			reader.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用32KB的缓冲读取文本文件
	}
	//	write data to file with buffer size 1MB
	//	I made this method private and didn't exposed it to client
	private void writeFileInsert(String outputFile, int[] ints, int base) {
		BufferedWriter writer = null;
		try {
			// Construct the BufferedWriter object, set append to true
			writer = new BufferedWriter(new FileWriter(outputFile, true), BUFFER_SIZE);
			// Start writing to the output stream
			for (int i = 0; i < ints.length; i++) {
					if(ints[i] >= 0){
						writer.write(ints[i] + "\r\n");
						ints[i] = -1; // reset the highest value 
					}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用16KB的缓冲写入文本文件
	}
	
}
