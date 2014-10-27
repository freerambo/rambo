package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestPing {

	public TestPing() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String cmd = "cmd.exe ping ";
		String ipprefix = "192.168.121.";
		int begin = 110;
		int end = 111;
		
		Process p = null;
		BufferedReader reader =  null;
		for (int i = begin; i < end; i++) {
			try {
				Runtime.getRuntime().exec("mkdir D:\\MyDir");
				String comand = cmd +ipprefix + i;
				System.out.println(comand);
				p = Runtime.getRuntime().exec(comand);

			
				
				String line = null;
				reader = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					// Handling line , may logs it.
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p.destroy();
			}
		}
	}
}
