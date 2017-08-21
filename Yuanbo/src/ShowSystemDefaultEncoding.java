/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 *  -> gf.java
 * Created on 11 Aug 2017-8:15:28 pm
 */

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  11 Aug 2017 8:15:28 pm
 */
public class ShowSystemDefaultEncoding {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     11 Aug 2017 8:15:29 pm
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String encoding = System.getProperty("file.encoding");
		System.out.println(encoding);
	}

}
