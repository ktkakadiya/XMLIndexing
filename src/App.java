import bPlusTree.BPlusTree;
import parser.XMLDocumentParser;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        XMLDocumentParser docParser = new XMLDocumentParser();
        docParser.parseDocument("data/bookStore.xml");

        /*BPlusTree<Integer, Integer> tree = new BPlusTree<Integer, Integer>(3);
        for(int i=1; i<=4; i++)
        {
            tree.insert(i, i);
        }
        tree.printTree();*/
    }
}
