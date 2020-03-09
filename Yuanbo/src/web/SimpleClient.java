/*
package web;


import java.io.IOException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
*/
/** *//*
*/
/**
* ��򵥵�HTTP�ͻ���,������ʾͨ��GET����POST��ʽ����ĳ��ҳ��
* @author Liudong
*//*

public class SimpleClient{
   public static void main(String[] args) throws IOException
     {
       HttpClient client = new HttpClient();   
       //���ô����������ַ�Ͷ˿�     
    //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
       //ʹ��GET�����������������Ҫͨ��HTTPS���ӣ���ֻ��Ҫ������URL�е�http����https
       HttpMethod method = new GetMethod("http://java.sun.com"); 
       //ʹ��POST����
    //HttpMethod method = new PostMethod("http://java.sun.com";); 
       client.executeMethod(method);
       //��ӡ���������ص�״̬
    System.out.println(method.getStatusLine());
      //��ӡ���ص���Ϣ
    System.out.println(method.getResponseBodyAsString());
      //�ͷ�����
    method.releaseConnection();
   }
} */
