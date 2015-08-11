package xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes; 
import org.xml.sax.Locator;
import org.xml.sax.SAXException; 
import org.xml.sax.XMLReader; 
import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.helpers.XMLReaderFactory; 

 public class SAXParser { 

  /* class BookHandler extends DefaultHandler { 
      private List<String> nameList; 
      private boolean title = false; 
   
      public List<String> getNameList() { 
         return nameList; 
      } 
      // Called at start of an XML document 
      @Override 
      public void startDocument() throws SAXException { 
         System.out.println("Start parsing document..."); 
         nameList = new ArrayList<String>(); 
      } 
      // Called at end of an XML document 
      @Override 
      public void endDocument() throws SAXException {  
         System.out.println("End");  
      } 
      
      *//** 
       * Start processing of an element. 
       * @param namespaceURI  Namespace URI 
       * @param localName  The local name, without prefix 
       * @param qName  The qualified name, with prefix 
       * @param atts  The attributes of the element 
       *//* 
      @Override 
      public void startElement(String uri, String localName, String qName, 
	     Attributes atts) throws SAXException { 
         // Using qualified name because we are not using xmlns prefixes here. 
         if (qName.equals("dvcIdN")) { 
            title = true; 
         } 
      } 
   
      @Override 
      public void endElement(String namespaceURI, String localName, String qName) 
         throws SAXException { 
         // End of processing current element 
         if (title) { 
            title = false; 
         } 
      } 
   			
      @Override 
      public void characters(char[] ch, int start, int length) { 
         // Processing character data inside an element 
         if (title) { 
            String bookTitle = new String(ch, start, length); 
            System.out.println("Book title: " + bookTitle); 
            nameList.add(bookTitle); 
         } 
      } 
			
   } */
	 
	 class BookHandler extends DefaultHandler {

		   private Locator locator;

		 

		   private List<Book> books;

		   private Book currentBook;

		   private String preTag;

		 

		   @Override

		   public void setDocumentLocator(Locator locator) {

		       this.locator = locator;

		   }

		 

		   @Override

		   public void startDocument() throws SAXException {

		       books = new ArrayList<Book>();

		       currentBook = null;

		       preTag = null;

		   }

		 

		   @Override

		   public void startElement(String uri, String localName, String qName,

		           Attributes attributes) throws SAXException {

		       if("book".equals(qName)) {

		           currentBook = new Book();

		           currentBook.setId(attributes.getValue("id"));

		       }

		       preTag = qName;

		   }

		 

		   public void endElement(String uri, String localName, String qName)

		           throws SAXException {

		       if("book".equals(qName)) {

		           books.add(currentBook);

		           currentBook = null;

		       }

		       preTag = null;

		   }

		 

		   public void characters(char ch[], int start, int length)

		           throws SAXException {

		       if(preTag != null && currentBook != null) {

		           String value = new String(ch, start, length);

		           if("name".equals(preTag)) {

		               currentBook.setName(value);

		           } else if("price".equals(preTag)) {

		               currentBook.setPrice(Double.parseDouble(value));

		           }

		       }

		   }

		
	 }
	
   public static void main(String[] args) throws SAXException, IOException { 
      XMLReader parser = XMLReaderFactory.createXMLReader(); 
      BookHandler bookHandler = (new SAXParser()).new BookHandler(); 
      parser.setContentHandler(bookHandler); 
//      parser.parse("C:\\Users\\User1\\Desktop\\NMS_STD_20141105060713973.xml"); 
//      System.out.println(bookHandler.getNameList()); 
      
      parser.parse("C:\\Users\\User1\\Desktop\\books.xml"); 
    System.out.println(bookHandler.books.size()); 
    
   } 
 }
 
 
 class Book {

	   private String id;

	   private String name;

	   private double price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	   
	   
	   

	}
 
 