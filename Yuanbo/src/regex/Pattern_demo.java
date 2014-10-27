package regex;

public class Pattern_demo
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		if(args.length==0)
		{
			System.err.println("ÇëÊäÈëÏàÓ¦×Ö·û´®£¡£¡£¡");
			System.exit(0);
		}
		String regex = "a+";
		String str = args[0];
		str = str.replaceAll(regex, "a");
		System.out.println(str);
	}
}
