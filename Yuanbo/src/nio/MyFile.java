package nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;


/**
 * 继承SimpleFileVisitor类实现
 * **/
public class MyFile<Path> extends SimpleFileVisitor<Path>{
	
	/**
	 * 访问该文件时，触发的方法
	 * */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		//打印文件名
		System.out.println("文件:  "+file);
//		if(file.toString().endsWith("docx")){
//		
//			System.out.println("找到文件，停止检索!");
//			return FileVisitResult.TERMINATE;
//		}
		
		
		return FileVisitResult.CONTINUE;
	};
	
	/**
	 * 访问此目录前，触发的方法
	 * 
	 * **/
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		System.out.println("目录:  "+dir +" start");
		
		return FileVisitResult.CONTINUE;
	};
	/**后前，触发的方法
	 * 
	 * **/
	 public FileVisitResult postVisitDirectory(Path dir, IOException exc)
		        throws IOException
		    {
		        Objects.requireNonNull(dir);
		        if (exc != null)
		            throw exc;
		        System.out.println("目录:  "+dir +" end");
		        return FileVisitResult.CONTINUE;
		    }
	
	/**
	 * D:\
	 *   D:\\\
	 * @description   
	 * @version currentVersion  
	 * @author Rambo  
	 * @createtime 2013年11月26日 上午10:42:28
	 */
	public static void showAllFile(){
		
		try{
			Files.walkFileTree(Paths.get("D:", "360Downloads","技术积累","spring"),new MyFile() );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		showAllFile();
	}
		
}

