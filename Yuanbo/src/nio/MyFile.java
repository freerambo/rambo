package nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;


/**
 * �̳�SimpleFileVisitor��ʵ��
 * **/
public class MyFile<Path> extends SimpleFileVisitor<Path>{
	
	/**
	 * ���ʸ��ļ�ʱ�������ķ���
	 * */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		//��ӡ�ļ���
		System.out.println("�ļ�:  "+file);
//		if(file.toString().endsWith("docx")){
//		
//			System.out.println("�ҵ��ļ���ֹͣ����!");
//			return FileVisitResult.TERMINATE;
//		}
		
		
		return FileVisitResult.CONTINUE;
	};
	
	/**
	 * ���ʴ�Ŀ¼ǰ�������ķ���
	 * 
	 * **/
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		System.out.println("Ŀ¼:  "+dir +" start");
		
		return FileVisitResult.CONTINUE;
	};
	/**��ǰ�������ķ���
	 * 
	 * **/
	 public FileVisitResult postVisitDirectory(Path dir, IOException exc)
		        throws IOException
		    {
		        Objects.requireNonNull(dir);
		        if (exc != null)
		            throw exc;
		        System.out.println("Ŀ¼:  "+dir +" end");
		        return FileVisitResult.CONTINUE;
		    }
	
	/**
	 * D:\
	 *   D:\\\
	 * @description   
	 * @version currentVersion  
	 * @author Rambo  
	 * @createtime 2013��11��26�� ����10:42:28
	 */
	public static void showAllFile(){
		
		try{
			Files.walkFileTree(Paths.get("D:", "360Downloads","��������","spring"),new MyFile() );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		showAllFile();
	}
		
}

