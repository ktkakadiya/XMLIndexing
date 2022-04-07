import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.w3c.dom.Document;

import bPlusTree.BPTLeafNode;
import bPlusTree.BPlusTree;
import cBPlusTree.CompressedBPTree;
import parser.XMLDeweyIdData;
import parser.XMLDocumentParser;
import splayTree.SplayTree;
import splayTree.SplayTreeNode;

public class Driver 
{
    BPlusTree<String, ArrayList<XMLDeweyIdData>> bpTree;
    CompressedBPTree<String, ArrayList<XMLDeweyIdData>> cbpTree;
    SplayTree<String, ArrayList<XMLDeweyIdData>> splayTree;
    Hashtable<String, BPlusTree<String, String>> conHashBPTree;
    Hashtable<String, CompressedBPTree<String, String>> conHashCBPTree;
    Hashtable<String, SplayTree<String, String>> conHashSplayTree;

    /**
     * Tree order
     */
    int uTreeOrder;

    public Driver(int uTreeOrder)
    { 
        this.uTreeOrder = uTreeOrder;
    }

    /**
     * Run tag name experiments on given file
     * @param strInputPath
     */
    public void runTagExperiments(String strInputPath, boolean bNS)
    {
        String strFilePath = null, strOutputFilePath = null;
        ArrayList<String> lstTagQuery = new ArrayList<>();

        try 
        {
            File oFile = new File(strInputPath);
            Scanner oFileReader = new Scanner(oFile);

            strFilePath = oFileReader.nextLine();
            strOutputFilePath = oFileReader.nextLine();
            while (oFileReader.hasNextLine())
            {
                lstTagQuery.add(oFileReader.nextLine());
            } 
            oFileReader.close();
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }

        XMLDocumentParser docParser = new XMLDocumentParser();
        Document xmlDoc = docParser.parseDocument(strFilePath);

        Hashtable<String, ArrayList<XMLDeweyIdData>> deweyTable = docParser.getDeweyIds(xmlDoc);
        Set<Map.Entry<String, ArrayList<XMLDeweyIdData>>> entries = deweyTable.entrySet();
        //this.printDeweyTable(deweyTable);

        //Construct B+ tree index
        long uStart = this.getTime(bNS);
        this.bpTree = new BPlusTree<String, ArrayList<XMLDeweyIdData>>(this.uTreeOrder);
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstValue = entry.getValue();
            this.bpTree.insert(strKey, lstValue);
        }
        long uEnd = this.getTime(bNS);
        long bpConTime = uEnd - uStart;

        //Construct Compressed B+ tree index
        uStart = this.getTime(bNS);
        this.cbpTree = new CompressedBPTree<String, ArrayList<XMLDeweyIdData>>(this.uTreeOrder);
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstValue = entry.getValue();
            this.cbpTree.insert(strKey, lstValue);
        }
        uEnd = this.getTime(bNS);
        long cbpConTime = uEnd - uStart;
        
        //Construct splay tree index
        uStart = this.getTime(bNS);
        this.splayTree = new SplayTree<String, ArrayList<XMLDeweyIdData>>();
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstValue = entry.getValue();
            this.splayTree.insert(strKey, lstValue);
        }
        uEnd = this.getTime(bNS);
        long splayConTime = uEnd - uStart;

        /*//Construct content hash B+ tree
        uStart = this.getTime(bNS);
        this.conHashBPTree = new Hashtable<>();
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstData = entry.getValue();

            BPlusTree<String, String> oContentTree = new BPlusTree<>(this.uTreeOrder);
            for(XMLDeweyIdData oData : lstData)
            {
                String strDataKey = oData.GetContent();
                if(strDataKey == null)
                    strDataKey = oData.GetDeweyId();
                oContentTree.insert(strDataKey, oData.GetDeweyId());
            }
            this.conHashBPTree.put(strKey, oContentTree);
        }
        uEnd = this.getTime(bNS);
        long hbpConTime = uEnd - uStart;

        //Construct content hash Compressed B+ tree
        uStart = this.getTime(bNS);
        this.conHashCBPTree = new Hashtable<>();
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstData = entry.getValue();

            CompressedBPTree<String, String> oContentTree = new CompressedBPTree<>(this.uTreeOrder);
            for(XMLDeweyIdData oData : lstData)
            {
                String strDataKey = oData.GetContent();
                if(strDataKey == null)
                    strDataKey = oData.GetDeweyId();
                oContentTree.insert(strDataKey, oData.GetDeweyId());
            }
            this.conHashCBPTree.put(strKey, oContentTree);
        }
        uEnd = this.getTime(bNS);
        long hcbpConTime = uEnd - uStart;

        //Construct content hash splay tree
        uStart = this.getTime(bNS);
        this.conHashSplayTree= new Hashtable<>();
        for(Map.Entry<String, ArrayList<XMLDeweyIdData>> entry : entries)
        {
            String strKey = entry.getKey();
            ArrayList<XMLDeweyIdData> lstData = entry.getValue();

            SplayTree<String, String> oContentTree = new SplayTree<>();
            for(XMLDeweyIdData oData : lstData)
            {
                String strDataKey = oData.GetContent();
                if(strDataKey == null)
                    strDataKey = oData.GetDeweyId();
                oContentTree.insert(strDataKey, oData.GetDeweyId());
            }
            this.conHashSplayTree.put(strKey, oContentTree);
        }
        uEnd = this.getTime(bNS);
        long hsplayConTime = uEnd - uStart;*/

        //Create output file
        File oOutFile = new File(strOutputFilePath);
        try 
        {
            oOutFile.createNewFile();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        try 
        {
            FileWriter oWriteFile = new FileWriter(oOutFile);
            oWriteFile.write("B+ tree construction time : " + bpConTime + this.getTimeUnit(bNS) + "\n");
            oWriteFile.write("Compressed B+ tree construction time : " + cbpConTime + this.getTimeUnit(bNS) + "\n");
            oWriteFile.write("Splay tree construction time : " + splayConTime + this.getTimeUnit(bNS) + "\n");
            //oWriteFile.write("Content Hash B+ tree construction time : " + hbpConTime + this.getTimeUnit(bNS) + "\n");
            //oWriteFile.write("Content Hash Compressed B+ tree construction time : " + hcbpConTime + this.getTimeUnit(bNS) + "\n");
            //oWriteFile.write("Content Hash Splay tree construction time : " + hsplayConTime + this.getTimeUnit(bNS) + "\n");
            oWriteFile.write("-----------------------------\n\n");

            for(String strTagQuery : lstTagQuery)
            {
                String strContent = null;
                if(strTagQuery.contains("/"))
                {
                    int uConIdx = strTagQuery.indexOf("/");
                    strContent = strTagQuery.substring(uConIdx+1, strTagQuery.length());
                    strTagQuery = strTagQuery.substring(0, uConIdx);
                }

                oWriteFile.write(strTagQuery + "\n");
                
                oWriteFile.write("(1) B+ tree : \n");
                uStart = this.getTime(bNS);
                BPTLeafNode<String, ArrayList<XMLDeweyIdData>> node = this.bpTree.searchKey(strTagQuery);
                if(node != null)
                {
                    ArrayList<XMLDeweyIdData> lstValue = node.getKeyPointer(strTagQuery);
                    for(XMLDeweyIdData oData : lstValue)
                    {
                        oWriteFile.write(oData.GetDeweyId() + " -> " + oData.GetContent() + "\n");
                    }
                }
                uEnd = this.getTime(bNS);
                long uSearchTime = uEnd - uStart;
                oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                oWriteFile.write("-----------------------------\n");

                oWriteFile.write("(2) Compressed B+ tree : \n");
                uStart = this.getTime(bNS);
                node = this.cbpTree.searchKey(strTagQuery);
                if(node != null)
                {
                    ArrayList<XMLDeweyIdData> lstValue = node.getKeyPointer(strTagQuery);
                    for(XMLDeweyIdData oData : lstValue)
                    {
                        oWriteFile.write(oData.GetDeweyId() + " -> " + oData.GetContent() + "\n");
                    }
                }
                uEnd = this.getTime(bNS);
                uSearchTime = uEnd - uStart;
                oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                oWriteFile.write("-----------------------------\n");

                oWriteFile.write("(3) Splay tree : \n");
                uStart = this.getTime(bNS);
                SplayTreeNode<String, ArrayList<XMLDeweyIdData>> sNode = this.splayTree.searchKey(strTagQuery);
                if(sNode != null)
                {
                    ArrayList<XMLDeweyIdData> lstValue = sNode.getValue();
                    for(XMLDeweyIdData oData : lstValue)
                    {
                        oWriteFile.write(oData.GetDeweyId() + " -> " + oData.GetContent() + "\n");
                    }
                }
                uEnd = this.getTime(bNS);
                uSearchTime = uEnd - uStart;
                oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                oWriteFile.write("-----------------------------\n\n");

                /*if(strContent != null)
                {
                    oWriteFile.write(strTagQuery + " - " + strContent + "\n");
                
                    oWriteFile.write("(1) Hash Content B+ tree : \n");
                    uStart = this.getTime(bNS);
                    BPlusTree<String, String> oContentTree = this.conHashBPTree.get(strTagQuery);
                    if(oContentTree != null)
                    {
                        BPTLeafNode<String, String> oConNode = oContentTree.searchKey(strContent);
                        if(oConNode != null)
                        {
                            String strDeweyId  = oConNode.getKeyPointer(strContent);
                            oWriteFile.write(strDeweyId + "\n");
                        }
                    }
                    uEnd = this.getTime(bNS);
                    uSearchTime = uEnd - uStart;
                    oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                    oWriteFile.write("-----------------------------\n");
    
                    oWriteFile.write("(2) Hash Compressed B+ tree : \n");
                    uStart = this.getTime(bNS);
                    CompressedBPTree<String, String> oCompContentTree = this.conHashCBPTree.get(strTagQuery);
                    if(oCompContentTree != null)
                    {
                        BPTLeafNode<String, String> oConNode = oCompContentTree.searchKey(strContent);
                        if(oConNode != null)
                        {
                            String strDeweyId  = oConNode.getKeyPointer(strContent);
                            oWriteFile.write(strDeweyId + "\n");
                        }
                    }
                    uEnd = this.getTime(bNS);
                    uSearchTime = uEnd - uStart;
                    oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                    oWriteFile.write("-----------------------------\n");
    
                    oWriteFile.write("(3) Hash Splay tree : \n");
                    uStart = this.getTime(bNS);
                    SplayTree<String, String> oSplayContentTree = this.conHashSplayTree.get(strTagQuery);
                    if(oSplayContentTree != null)
                    {
                        SplayTreeNode<String, String> oConNode = oSplayContentTree.searchKey(strContent);
                        if(oConNode != null)
                        {
                            String strDeweyId  = oConNode.getValue();
                            oWriteFile.write(strDeweyId + "\n");
                        }
                    }
                    uEnd = this.getTime(bNS);
                    uSearchTime = uEnd - uStart;
                    oWriteFile.write("Search time taken : " + uSearchTime + this.getTimeUnit(bNS) + "\n");
                    oWriteFile.write("-----------------------------\n\n");
                }*/
            }
            oWriteFile.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Print dewey hash table
     * @param deweyTable
     */
    private void printDeweyTable(Hashtable<String, ArrayList<XMLDeweyIdData>> deweyTable)
    {
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
    }

    private long getTime(boolean bNS)
    {
        if(!bNS)
        {
            return System.currentTimeMillis();
        }
        else
        {
            return System.nanoTime();
        }
    }

    private String getTimeUnit(boolean bNS)
    {
        if(!bNS)
        {
            return "ms";
        }
        else
        {
            return "ns";
        }
    }
}
