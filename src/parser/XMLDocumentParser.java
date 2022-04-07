package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

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
    /**
     * Parse xml document from given path
     * @param strFilePath
     * @return
     */
    public Document parseDocument(String strFilePath)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try 
        {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            doc = docBuilder.parse(new File(strFilePath));
            doc.getDocumentElement().normalize();
        } 
        catch (ParserConfigurationException | SAXException | IOException e) 
        {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * Get dewey id hash table of given xml document
     * @param xmlDoc
     * @return
     */
    public Hashtable<String, ArrayList<XMLDeweyIdData>> getDeweyIds(Document xmlDoc)
    {
        Hashtable<String, ArrayList<XMLDeweyIdData>> table = new Hashtable<>();
        if(xmlDoc != null)
        {
            String strRootId = "1.0";
            Node oRootNode = xmlDoc.getFirstChild();
            this.assignNodeDeweyIds(oRootNode, strRootId, table);
        }
        return table;
    }

    /**
     * Assign dewey ids to node
     * @param oNode
     * @param table
     */
    private void assignNodeDeweyIds(Node oNode, String strDeweyId, Hashtable<String, ArrayList<XMLDeweyIdData>> table)
    {
        if(oNode == null)
            return;
        
        if(oNode.getNodeType() == Node.ELEMENT_NODE)
        {
            Element oElement = (Element)oNode;
            String strTagName = oElement.getTagName();

            NodeList lstNodes = oNode.getChildNodes();
            String strContent = this.getNodeListTextContent(lstNodes);
            
            XMLDeweyIdData oData = new XMLDeweyIdData(strDeweyId, strContent);
            if(table.containsKey(strTagName))
            {
                ArrayList<XMLDeweyIdData> lstList = table.get(strTagName);
                lstList.add(oData);
            }
            else
            {
                ArrayList<XMLDeweyIdData> lstList = new ArrayList<>();
                lstList.add(oData);
                table.put(strTagName, lstList);
            }

            int uCurId = 0;
            for(int i=0; i<lstNodes.getLength(); i++)
            {
                Node curNode = lstNodes.item(i);
                if(curNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    String strChildId = strDeweyId + "." + Integer.toString(uCurId);
                    assignNodeDeweyIds(curNode, strChildId, table);
                    uCurId++;
                }
            }
        }
    }

    /**
     * Get text content from given list of nodes
     * @param lstNodes
     * @return
     */
    private String getNodeListTextContent(NodeList lstNodes)
    {
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<lstNodes.getLength(); i++)
        {
            Node curChild = lstNodes.item(i);
            if(curChild.getNodeType() == Node.TEXT_NODE)
            {
                strBuilder.append(curChild.getTextContent().trim());
            }
        }
        String strContent = strBuilder.toString();
        if(strContent.trim().isEmpty())
        {
            strContent = null;
        }
        return strContent;
    }
}
