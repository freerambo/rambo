package Test.src;

import java.io.*;

public class KeyBoad {

	public static void main(String[] args) {
		try {
			String line = "";
			InputStreamReader ir = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(ir);
			line = br.readLine();
			while (line != null) {
				System.out.println("Read  :" + line);
				if (line.equals("exit")) {
					System.exit(0);
				}
				line = br.readLine();
			}
			ir.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
