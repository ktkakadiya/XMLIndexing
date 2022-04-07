package bPlusTree;

public class BPlusTree<K extends Comparable<K>, V> 
{
    /**
     * Tree order i.e, the maximum number of child pointers internal node can have
     */
	protected int uTreeOrder;

    /**
     * Root node of the tree
     */
	protected BPTNode<K> root;

	public BPlusTree(int uTreeOrder) 
    {
		this.uTreeOrder = uTreeOrder;
	}

	@SuppressWarnings("unchecked")
	public BPlusTree(BPlusTree<K, V> tree) 
    {
		this.uTreeOrder = tree.uTreeOrder;
		if (tree.root instanceof BPTLeafNode)
        {
            this.root = new BPTLeafNode<K, V>((BPTLeafNode<K, V>) tree.root);
        }
		else
        {
            this.root = new BPTInternalNode<K>((BPTInternalNode<K>) tree.root);
        }
	}

    /**
     * Get the leaf node containing given search key
     * @param key
     * @return
     */
	@SuppressWarnings("unchecked")
	public BPTLeafNode<K, V> searchKey(K key) 
    {
		BPTNode<K> curNode = root;
		while (curNode instanceof BPTInternalNode) 
        {
			curNode = ((BPTInternalNode<K>) curNode).searchKey(key);
		}
		return (BPTLeafNode<K, V>) curNode;
	}

    /**
     * Get parent node of current node
     * @param node
     * @return
     */
	public BPTInternalNode<K> getParent(BPTNode<K> node) 
    {
		BPTNode<K> oParent = root;
		while (oParent != null) 
        {
			K key = node.getFirstKey();
			BPTNode<K> oChild = ((BPTInternalNode<K>)oParent).searchKey(key);
			if (node == oChild) 
            {
				return (BPTInternalNode<K>)oParent;
			}
			oParent = oChild;
		}
		return null;
	}

    /**
     * Insert key and value pair into current tree
     * @param key
     * @param value
     */
	public void insert(K key, V value) 
    {
		BPTLeafNode<K, V> oLeafNode;
		if (root == null) 
        {
			oLeafNode = new BPTLeafNode<K, V>(this.uTreeOrder);
			root = oLeafNode;
		} 
        else 
        {
			oLeafNode = this.searchKey(key);
		}

		if (oLeafNode.hasRoom()) 
        {
			oLeafNode.insert(key, value);
		} 
        else 
        {
			BPTLeafNode<K, V> tempLeaf = new BPTLeafNode<K, V>(this.uTreeOrder + 1);
			tempLeaf.copyNode(oLeafNode, 0, oLeafNode.getCurDegree());
			tempLeaf.insert(key, value);
			
			int m = (int) Math.ceil(this.uTreeOrder / 2.0);

            BPTLeafNode<K, V> newLeaf = new BPTLeafNode<K, V>(this.uTreeOrder);
			newLeaf.setSuccessorNode(oLeafNode.getSuccessorNode());
			newLeaf.copyNode(tempLeaf, m, tempLeaf.getCurDegree());

			oLeafNode.resetNode();
			oLeafNode.setSuccessorNode(newLeaf);
			oLeafNode.copyNode(tempLeaf, 0, m);

			this.insertInParent(newLeaf.getFirstKey(), oLeafNode, newLeaf);
		}
	}

    /**
     * Insert given key and child pointers into parent node of given node
     * @param key
     * @param oChild
     * @param oNewChild
     */
	void insertInParent(K key, BPTNode<K> oChild, BPTNode<K> oNewChild) 
    {
        //If splitted node was root node
		if (oChild == root) 
        {
			root = new BPTInternalNode<K>(this.uTreeOrder);
			root.insert(key, oChild, 0);
			root.lstPointers[1] = oNewChild;
			return;
		}

		BPTInternalNode<K> oParent = this.getParent(oChild);
		if (oParent.hasRoom()) 
        {
			oParent.insertAfterChild(key, oNewChild, oChild);
		} 
        else 
        {
			BPTInternalNode<K> tempParent = new BPTInternalNode<K>(this.uTreeOrder + 1);
			tempParent.copyNode(oParent, 0, oParent.getCurDegree());
			tempParent.insertAfterChild(key, oNewChild, oChild);

			int m = (int) Math.ceil(this.uTreeOrder / 2.0);
			oParent.resetNode();
			oParent.copyNode(tempParent, 0, m - 1);

			BPTInternalNode<K> newParent = new BPTInternalNode<K>(this.uTreeOrder);
			newParent.copyNode(tempParent, m, tempParent.getCurDegree());
			insertInParent(tempParent.lstKeys[m - 1], oParent, newParent);
		}
	}

    /**
     * Print tree
     */
    public void printTree() 
    {
        printNode(root);  
    }
    
    /**
     * Print current node and its pointers
     * @param node
     */
    private void printNode(BPTNode node) 
    {
        assert (node == null);
        if (node instanceof BPTLeafNode)
        {
            System.out.print("Leaf - ");
        }
        else
        {
            System.out.print("Internal - ");
        }

        for(int i = 0; i < node.uCurDegree; i++) 
        {
            System.out.print(node.lstKeys[i] + " ");
        }
        System.out.println();

        if(!(node instanceof BPTLeafNode)) 
        {
            for (int i = 0; i < node.uCurDegree + 1; i++) 
            {
                printNode((BPTNode)node.lstPointers[i]);
            }
        }
    }
}