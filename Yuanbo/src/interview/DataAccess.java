package interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  18 Nov 2017 1:31:12 pm
 */
public class DataAccess {

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu     18 Nov 2017 1:31:12 pm
	 */
	public static void main(String[] args) {

		// use the 1st element in args, otherwise a given default string 
		String text= args.length > 0? args[0]:"key1=value1;key2=value2\nkeyA=valueA\nkeyB=valueB;keyC=valueC\n";
		
		// load string to map
		Map<String,String>[] maps = load(text);

		// store a map into string (file) 
		text = store(maps);
		
		System.out.println("-------------------------print text string-------------------------");
		System.out.println(text);
		
	}
	
/* output
-------------------------print text string-------------------------
key1=value1;key2=value2
keyA=valueA
keyB=valueB;keyC=valueC
*/
	
	public static String store(final Map<String,String>[] maps){
		
		if(maps == null || maps.length == 0) return null;

		StringBuilder sb = new StringBuilder();
		for(Map<String, String> map : maps){
			Iterator<Entry<String, String>> it = map.entrySet().iterator();   
			while (it.hasNext()) {   
			    Entry<String, String> entry = it.next();   
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				if(it.hasNext()) sb.append(";");
			}  
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	public static Map<String,String>[] load(final String text){
		
		if(text == null || text.isEmpty()) return null;
		
		String[] ss = text.split("\n");
		Map<String, String>[] maps = new HashMap[ss.length];
		for(int i = 0; i < ss.length; i++){
			
			String[] se = ss[i].split(";");
			Map<String, String> map = new HashMap<String, String>();
			for(String s : se){
				String[] skv = s.split("=");
				if(skv != null && skv.length == 2)
					map.put(skv[0], skv[1]);
				else throw new IllegalArgumentException("Invalid element format!!！");
			}
			maps[i] = map;
		}
		return maps;
	}
	
}
