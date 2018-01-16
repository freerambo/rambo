/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * visa -> dfd.java
 * Created on 15 Jan 2018-9:08:04 pm
 */
package visa;

import java.io.InputStream;
import java.net.URL;
import java.util.*;  
public class MainTest{  
	
    public static void main(String[] args){  
        Scanner in=new Scanner(System.in);  
            String str1="";  
            while(in.hasNext()){  
                str1=in.nextLine();  
                System.out.println(parse(str1));  
            }  
    }  
    public static boolean parse(String s){  
        ArrayList<Character> result=new ArrayList<Character>();  
        if (s.contains("(")||s.contains("{")||s.contains("[")||s.contains("]")||s.contains("}")||s.contains(")")) {  
            for(int i=0;i<s.length();i++){  
                if (s.charAt(i)=='('||s.charAt(i)=='{'||s.charAt(i)=='['||s.charAt(i)==']'||s.charAt(i)=='}'||s.charAt(i)==')') {  
                    result.add(s.charAt(i));  
                }  
            }  
            if (result.size()%2!=0) {  
                return false;  
            }else{  
                int pairNum=result.size()/2;  
                int count=result.size()/2;  
                while(pairNum>0&&count>0){  
                    for(int i=0;i<result.size()-1;i++){  
                        if (result.get(i)=='('&&result.get(i+1)==')') {  
                            result.remove(i);  
                            result.remove(i);  
                            pairNum--;  
                            break;  
                        }  
                        if (result.get(i)=='['&&result.get(i+1)==']') {  
                            result.remove(i);  
                            result.remove(i);  
                            pairNum--;  
                            break;  
                        }  
                        if (result.get(i)=='{'&&result.get(i+1)=='}') {  
                            result.remove(i);  
                            result.remove(i);  
                            pairNum--;  
                            break;  
                        }  
                    }  
                    count--;  
                }  
                if (result.size()==0) {  
                    return true;  
                }else {  
                    return false;  
                }  
            }  
        }  
        return false;  
    }  
}  


class Point2D{
    public int x;
    public int y;
    Point2D(){
    }
    Point2D(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public double dist2D(Point2D p){
    	int x1 = this.x - p.x;
    	int y1 = this.y - p.y;
       return  Math.ceil(Math.sqrt(x1*x1 + y1*y1));
    }
    
    public void printDistance(double d){
    	System.out.println(d);
    }
}

class Point3D extends Point2D{
	
    int z;
    Point3D(int x, int y, int z){
    	super(x,y);
        this.z = z;
    }
    
    public double dist3D(Point3D p){
    	int x1 = this.x - p.x;
    	int y1 = this.y - p.y;
    	int z1 = this.z - p.z;
       return  Math.ceil(Math.sqrt(x1*x1 + y1*y1 + z1*z1));
    }

}

