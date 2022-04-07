package parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLDocumentParser 
{
    public void parseDocument(String strFilePath)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try 
        {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(strFilePath));
            doc.getDocumentElement().normalize();

            NodeList bookList = doc.getElementsByTagName("book");
            for(int i=0; i<bookList.getLength(); i++)
            {
                Node book = bookList.item(i);
                if(book.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element bookElement = (Element) book;
                    System.out.println("Book category : " + bookElement.getAttribute("category"));

                    NodeList bookDetails = book.getChildNodes();
                    for(int j=0; j<bookDetails.getLength(); j++)
                    {
                        Node details = bookDetails.item(j);
                        if(details.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element detailsElement = (Element) details;
                            System.out.println(detailsElement.getTagName() + " -> " + detailsElement.getTextContent());
                        }
                    }
                }
                System.out.println();
            }
        } 
        catch (ParserConfigurationException | SAXException | IOException e) 
        {
            e.printStackTrace();
        }
    }
}
