package fileHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {

	// sum(IF(meter_id='DA3509748',value,0)) AS DA3509748,

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.print(fileRead("D:/meters.txt"));
	}
/**
 * file reader 
 * @param file
 * @return
 */
	static String fileRead(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				line = sqlProcess(line);
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	final static String prefix = "sum(IF(meter_id=\'";
	final static String subfix = "\',value,0)) AS ";
	/**
	 * 
	 * @param merterId
	 */
	static String sqlProcess(String meterId){
		
	
		return prefix +meterId+subfix + meterId + ",";
	}
}
