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
 * 根据要求2： 该数据文件无法一次性加载到内存中处理 假设内存为 2MB 约load 20次 才可以完成
 * 
 * Method 1： 
 * 每次 50万条数据， 50 * 10000 * 4 / 1024 / 1024 约为 2 MB 
 * 每次排序，共需二十次。
 * 同样的方式在归并排序 直到全体有序 
 * 虽然使用的内存较小，但每次都要比较， 代码过于复杂，效率不够高
 * 
 * Method 2：
 * 
 * 在不使用第三方类库做数据处理的前提下， 可以简单考虑利用数据库工具 
 * 每次将文件读取的内容写入数据库
 * 利用数据库的排序功能，并每次读取如1MB左右数据并写入外部文件 
 * 循环读取数据库直到全部写入文件
 * 
 * 好处是处理简单，代码清晰。 缺点需要数据库支持
 * 
 * 
 * Method 3 
 * 位图法 Bitmap. 1000 万整数 每个数用bit存储，0和1来判定书否存在。 所需内存空间 1000 * 10000 /
 * 1024 / 1024 /8 = 1.2 MB 最后遍历bitmap， 为1的记录并写入文件
 * 
 * 下文利用Java中的Bitset给出方法3的实现过程。
 * 
 * 改进之处： 
 * BitSet 此方法排序默认无重复数据
 * 在有重复数据情况下，可以采用 byte 数组，在每次set时，对byte 数组值+1，
 * 在写入文件时，写入byte[index] 个 index 数据
 * 
 * Create: 6 Nov 2017 6:02:29 pm
 */
public class BigFileSort {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu 6 Nov 2017 6:22:29 pm
	 */
	public static void main(String[] args) {
		// Java utils,Since JDK1.0 a vector of bits that grows as needed
		// I deem BitSet as a part of JDK not a third-party
		// otherwise a byte array will help
		BitSet bs = new BitSet(32); // 32 bit is enough for int
		// call the sort method  
		new BigFileSort().sort(bs, "C:/Users/zhuyb/input.txt", "C:/Users/zhuyb/output.txt");

	}

	/**
	 * 
	 * @function: A method exposed to client to sort a big file in Java
	 * @param bs
	 * @param inputFile    input file full path 
	 * @param outputFile 	output file full path 
	 * @author: Rambo Zhu     6 Nov 2017 7:00:31 pm
	 */
	public void sort(BitSet bs, String inputFile, String outputFile) {
		if(bs == null || inputFile == null || outputFile == null) // input check 
			throw new IllegalArgumentException("非法参数");
		
		long startTime = System.currentTimeMillis();
		readFile(inputFile, bs);
		writeFile(outputFile, bs);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Integer File Sort completed. It took " + (endTime - startTime) + " milliseconds");

	} // out put Integer File Sort completed. It took  94 milliseconds

//	read data from file with buffer size 2MB,
//	I made this method private and didn't exposed it to client
	private void readFile(String inputFile, BitSet bs) {

		BufferedInputStream fis = null;
		BufferedReader reader = null;
		try {

			fis = new BufferedInputStream(new FileInputStream(inputFile));
			reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 2 * 1024 * 1024);
			String line = null;
			while ((line = reader.readLine()) != null) {
				Integer i = Integer.valueOf(line);
				bs.set(i);
			}
			reader.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用2M的缓冲读取文本文件

	}
//	write data to file with buffer size 2MB
//	I made this method private and didn't exposed it to client
	
	private void writeFile(String outputFile, BitSet bs) {

		BufferedWriter writer = null;
		try {
			// Construct the BufferedWriter object, set append to true
			writer = new BufferedWriter(new FileWriter(outputFile, true), 2 * 1024 * 1024);
			// Start writing to the output stream
			for (int i = 0; i < bs.size(); i++) {
				if (bs.get(i)) {
					writer.write(i + "\r\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} // 用2M的缓冲写入文本文件
	}
}
