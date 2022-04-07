package cBPlusTree;

import bPlusTree.*;

public class CompressedBPTree<K extends Comparable<K>, V> extends BPlusTree<K, V> 
{
    public CompressedBPTree(int uTreeOrder) 
    {
        super(uTreeOrder);
    }

    /**
     * Insert key and value pair into current tree
     * @param key
     * @param value
     */
	public void insert(K key, V value) 
    {
		CBPTLeafNode<K, V> oLeafNode;
		if (root == null) 
        {
			oLeafNode = new CBPTLeafNode<K, V>(this.uTreeOrder);
			root = oLeafNode;
		} 
        else 
        {
			oLeafNode = (CBPTLeafNode<K, V>) this.searchKey(key);
		}

		if (oLeafNode.hasRoom()) 
        {
			oLeafNode.insert(key, value);
		}
        else if (this.hasRightSpace(oLeafNode))
        {
            this.rightShift(oLeafNode, key, value);
        } 
        else if (this.hasLeftSpace(oLeafNode))
        {
            this.leftShift(oLeafNode, key, value);
        } 
        else 
        {
			CBPTLeafNode<K, V> tempLeaf = new CBPTLeafNode<K, V>(this.uTreeOrder + 1);
			tempLeaf.copyNode(oLeafNode, 0, oLeafNode.getCurDegree());
			tempLeaf.insert(key, value);
			
			int m = (int) Math.ceil(this.uTreeOrder / 2.0);

            CBPTLeafNode<K, V> newLeaf = new CBPTLeafNode<K, V>(this.uTreeOrder);
            CBPTLeafNode<K, V> oSuccessor = (CBPTLeafNode<K, V>) oLeafNode.getSuccessorNode();
			newLeaf.setSuccessorNode(oSuccessor);
            if(oSuccessor != null)
                oSuccessor.setPredecessorNode(newLeaf);
			newLeaf.copyNode(tempLeaf, m, tempLeaf.getCurDegree());

			oLeafNode.resetNode();
			oLeafNode.setSuccessorNode(newLeaf);
            newLeaf.setPredecessorNode(oLeafNode);
			oLeafNode.copyNode(tempLeaf, 0, m);

			this.insertInParent(newLeaf.getFirstKey(), oLeafNode, newLeaf);
		}
	}

    /**
     * Check whether there is space in right of current node
     * @param oNode
     * @return
     */
    public boolean hasRightSpace(CBPTLeafNode<K, V> oNode)
    {
        BPTLeafNode<K, V> oCurNode = oNode.getSuccessorNode();
        while(oCurNode != null)
        {
            boolean bHasRoom = oCurNode.hasRoom();
            if(bHasRoom)
                return true;
            oCurNode = oCurNode.getSuccessorNode();
        }
        return false;
    }
    
    /**
     * Add key into the node and perform right shift
     * @param oLeafNode
     * @param key
     * @param value
     */
    public void rightShift(CBPTLeafNode<K, V> oLeafNode, K key, V value)
    {
        CBPTLeafNode<K, V> tempLeaf = new CBPTLeafNode<K, V>(this.uTreeOrder + 1);
        tempLeaf.copyNode(oLeafNode, 0, oLeafNode.getCurDegree());
        tempLeaf.insert(key, value);

        K oldFirstKey = oLeafNode.getFirstKey();
        K newFirstKey = tempLeaf.getFirstKey();

        oLeafNode.copyNode(tempLeaf, 0, tempLeaf.getCurDegree()-1);  
        BPTInternalNode<K> oParent = this.getParent(oLeafNode);
        oParent.replaceKey(oldFirstKey, newFirstKey);

        K lastKey = tempLeaf.getLastKey();
        Object lastObj = tempLeaf.getLastPointer();
        this.insert(lastKey, (V)lastObj);
    }

    /**
     * Check whether there is space in left of current node
     * @param oNode
     * @return
     */
    public boolean hasLeftSpace(CBPTLeafNode<K, V> oNode)
    {
        CBPTLeafNode<K, V> oCurNode = oNode.getPredecessorNode();
        while(oCurNode != null)
        {
            boolean bHasRoom = oCurNode.hasRoom();
            if(bHasRoom)
                return true;
            oCurNode = oCurNode.getPredecessorNode();
        }
        return false;
    }

    /**
     * Add key into the node and perform left shift
     * @param oLeafNode
     * @param key
     * @param value
     */
    public void leftShift(CBPTLeafNode<K, V> oLeafNode, K key, V value)
    {
        CBPTLeafNode<K, V> tempLeaf = new CBPTLeafNode<K, V>(this.uTreeOrder + 1);
        tempLeaf.copyNode(oLeafNode, 0, oLeafNode.getCurDegree());
        tempLeaf.insert(key, value);

        K oldFirstKey = oLeafNode.getFirstKey();
        K newFirstKey = tempLeaf.getFirstKey();
        K newSecondKey = tempLeaf.getSecondKey();

        oLeafNode.copyNode(tempLeaf, 1, tempLeaf.getCurDegree());  
        BPTInternalNode<K> oParent = this.getParent(oLeafNode);
        oParent.replaceKey(oldFirstKey, newSecondKey);

        Object firstObj = tempLeaf.getFirstPointer();
        this.insert(newFirstKey, (V)firstObj);
    }
}
