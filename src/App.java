import bPlusTree.BPlusTree;
import cBPlusTree.CompressedBPTree;
import parser.XMLDocumentParser;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        //XMLDocumentParser docParser = new XMLDocumentParser();
        //docParser.parseDocument("data/bookStore.xml");

        //BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(4);
        CompressedBPTree<Integer, Integer> tree = new CompressedBPTree<Integer, Integer>(4);
        for(int i=1; i<=9; i++)
        {
            tree.insert(i, i);
        }
        tree.printTree();
    }
}
