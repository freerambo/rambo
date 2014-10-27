package httpsimulation;

import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
/** *//**
* 提交参数演示
* 该程序连接到一个用于查询手机号码所属地的页面
* 以便查询号码段1330227所在的省份以及城市
* @author Liudong
*/

public class SimpleHttpClient {
   public static void main(String[] args) throws IOException
   {
       HttpClient client = new HttpClient();
       client.getHostConfiguration().setHost("www.showji.com", 80, "http");
       HttpMethod method = getGetMethod();//使用POST方式提交数据
    client.executeMethod(method); 
      //打印服务器返回的状态 http://www.showji.com/search.htm?m=15311770561
    System.out.println(method.getStatusLine());
//    new String("测试".getBytes("GB2312"), "ISO-8859-1")
      //打印结果页面
   String response =  new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"),"utf-8");
      //打印返回的信息
    System.out.println(response);
       method.releaseConnection();
   }
   
   /** *//**
    * 使用GET方式提交数据
  * @return
    */
   private static HttpMethod getGetMethod(String s){
       return new GetMethod(s);
   }
   /** *//**
    * 使用GET方式提交数据
  * @return
    */
   private static HttpMethod getGetMethod(){
       return new GetMethod("/search.htm?m=15311770561");
   }
   /** *//**
    * 使用POST方式提交数据
  * @return
    */
   private static HttpMethod getPostMethod(){
       PostMethod post = new PostMethod("/search.htm");
       NameValuePair simcard = new NameValuePair("m","15311770561");
       post.setRequestBody(new NameValuePair[] { simcard});
       return post;
   }
   
   
   
   
} 