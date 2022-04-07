package cBPlusTree;

import bPlusTree.BPTLeafNode;

public class CBPTLeafNode<K extends Comparable<K>, V> extends BPTLeafNode<K, V>
{
    /**
     * Pointer to previous child node
     * @param degree
     */
    CBPTLeafNode<K, V> oPrevPtr;

    public CBPTLeafNode(int degree) 
    {
		super(degree);
        oPrevPtr = null;
	}
    	
	/**
	 * Get predecessor node of current node
	 * @return
	 */
	public CBPTLeafNode<K, V> getPredecessorNode() 
    {
		return this.oPrevPtr;
	}

	/**
	 * Set predecessor node of current node
	 * @param oPrevPtr
	 * @return
	 */
	public void setPredecessorNode(CBPTLeafNode<K, V> oPrevPtr) 
    {
        this.oPrevPtr = oPrevPtr;
	}
}
