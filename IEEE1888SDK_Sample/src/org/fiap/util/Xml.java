/*
 * Copyright (c) 2013 Hideya Ochiai, the University of Tokyo,  All rights reserved.
 * 
 * Permission of redistribution and use in source and binary forms, 
 * with or without modification, are granted, free of charge, to any person 
 * obtaining the copy of this software under the following conditions:
 * 
 *  1. Any copies of this source code must include the above copyright notice,
 *  this permission notice and the following statement without modification 
 *  except possible additions of other copyright notices. 
 * 
 *  2. Redistributions of the binary code must involve the copy of the above 
 *  copyright notice, this permission notice and the following statement 
 *  in documents and/or materials provided with the distribution.  
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.fiap.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Xml Processing Utility
 * 1. Create & Parse
 *  1.1. Create XML Document
 *  1.2. Parse string into XML Document
 *  1.3. Parse URL into XML Document
 * 2. Generate String from XML DOM
 * @author Hideya Ochiai, The University of Tokyo
 * @version 1.0
 * Created: before 2008-01-01
 */
public class Xml {

	/**
	 * creates new XML Document;
	 * @return XML Document
	 */
	public static Document newDocument(){
		try{
			DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
			f.setNamespaceAware(true);
			DocumentBuilder b=f.newDocumentBuilder();
			return b.newDocument();
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * Parse string into XML Document
	 * @param xml_string String that contains XML
	 * @return XML Document
	 */
	public static Document parseXml(String xml_string){
		try{
			DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
			f.setNamespaceAware(true);
			DocumentBuilder b=f.newDocumentBuilder();
			return b.parse(new InputSource(new StringReader(xml_string)));
		}
		catch(org.w3c.dom.DOMException e){
			// System.err.println("The XML is broken");
			// e.printStackTrace();
		}
		catch(Exception e){
		//	e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * Parse URL into XML Document
	 * @param url URL that points to XML contents
	 * @return XML Document
	 */
	public static Document parseXmlFile(String url){
		try{
			DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
			f.setNamespaceAware(true);
			DocumentBuilder b=f.newDocumentBuilder();
			return b.parse(new java.io.File(url));
		}
		catch(java.io.FileNotFoundException e){
			System.err.println("File Not Found: "+url);
		}
		catch(java.io.IOException ioe){
			System.err.println("File IO Error");
			ioe.printStackTrace();
		}
		catch(org.w3c.dom.DOMException e){
			System.err.println("The XML is broken: "+url);
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		return null;
	}
	
	/**
	 * Generate String from XML DOM
	 * @param root
	 * @return String that contains the XML
	 */
	public static String toString(Element root){
		
		String xml_string=null;
		try{
			// Xml Transformer
			TransformerFactory t_factory=TransformerFactory.newInstance();
			Transformer trans=t_factory.newTransformer();
		
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
			trans.setOutputProperty(OutputKeys.INDENT,"yes");
		
			// XMLDocument -> String
			DOMSource src=new DOMSource();
			src.setNode(root);
			StringWriter sw=new StringWriter();
			StreamResult s_result=new StreamResult();
			s_result.setWriter(sw);
			trans.transform(src,s_result);
		
			xml_string=sw.getBuffer().toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return xml_string;		
	}

	
}
