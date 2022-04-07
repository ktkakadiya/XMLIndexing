import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.function.IntToDoubleFunction;

import org.w3c.dom.Document;

import bPlusTree.BPlusTree;
import cBPlusTree.CompressedBPTree;
import parser.XMLDeweyIdData;
import parser.XMLDocumentParser;
import splayTree.SplayTree;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        XMLDocumentParser docParser = new XMLDocumentParser();
        Document xmlDoc = docParser.parseDocument("data/bookStore.xml");
        Hashtable<String, ArrayList<XMLDeweyIdData>> deweyTable = docParser.getDeweyIds(xmlDoc);

        Set<Map.Entry<String, ArrayList<XMLDeweyIdData>>> entries = deweyTable.entrySet();
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            System.out.println(entry.getKey() + "->");
            ArrayList<XMLDeweyIdData> lstValue = entry.getValue();
            for(XMLDeweyIdData oData : lstValue)
            {
                System.out.println(oData.GetDeweyId() + " -> " + oData.GetContent());
            }
            System.out.println();
        }

        //BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(4);
        //CompressedBPTree<Integer, Integer> tree = new CompressedBPTree<Integer, Integer>(4);
        /*for(int i=1; i<=9; i++)
        {
            tree.insert(i, i);
        }*/

        /*SplayTree<Integer, Integer> tree = new SplayTree<Integer, Integer>();
        for(int i=1; i<=10; i++)
        {
            tree.insertElement(i, i);
        }
        tree.findElement(5);
        tree.printTree();*/
    }
}
