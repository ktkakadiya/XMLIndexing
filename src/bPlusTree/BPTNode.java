package bPlusTree;

public abstract class BPTNode<K extends Comparable<K>>
{
    /**
     * List of keys
     */
    protected K[] lstKeys;

    /**
     * List of pointers
     */
    protected Object[] lstPointers;

    /**
     * Current degree of node
     */
    protected int uCurDegree;

    @SuppressWarnings("unchecked")
    public BPTNode(int uDegree)
    {
        lstKeys = (K[]) new Comparable[uDegree - 1];
        lstPointers = new Object[uDegree];
        uCurDegree = 0;
    }

    @SuppressWarnings("unchecked")
    public BPTNode(BPTNode<K> node) 
    {
		this.uCurDegree = node.uCurDegree;

		lstKeys = (K[]) new Comparable[node.lstKeys.length];
		System.arraycopy(node.lstKeys, 0, lstKeys, 0, node.lstKeys.length);

		lstPointers = new Object[node.lstPointers.length];
		for (int i = 0; i < node.lstPointers.length; i++) 
        {
			Object oChildPtr = node.lstPointers[i];
			if (oChildPtr instanceof BPTLeafNode)
            {
                lstPointers[i] = new BPTLeafNode((BPTLeafNode) oChildPtr); // copy construct the node.
            }
			else if (oChildPtr instanceof BPTInternalNode)
            {
				lstPointers[i] = new BPTInternalNode<K>((BPTInternalNode<K>) oChildPtr); // copy construct the node.
            }
			else
            {
				lstPointers[i] = oChildPtr;
            }
		}
	}

	/**
	 * Insert given key and pointer object to specified index
	 * @param key
	 * @param object
	 * @param uIndex
	 */
	protected void insert(K key, Object object, int uIndex) 
    {
		for (int i = this.uCurDegree; i > uIndex; i--) 
        {
			this.lstKeys[i] = this.lstKeys[i - 1];
			this.lstPointers[i] = this.lstPointers[i - 1];
		}
		this.lstKeys[uIndex] = key;
		this.lstPointers[uIndex] = object;
		this.uCurDegree++;
	}

    /**
     * Get current degree of the node
     * @return
     */
	public int getCurDegree() 
    {
		return this.uCurDegree;
	}

    /**
     * Get first key of current node
     */
	public K getFirstKey() 
    {
		return this.lstKeys[0];
	}
	
    /**
     * Get first pointer of current node
     */
	public Object getFirstPointer() 
    {
		return this.lstPointers[0];
	}

    /**
     * Get last key of current node
     */
	public K getLastKey() 
    {
		return this.lstKeys[this.uCurDegree - 1];
	}
	
    /**
     * Get last pointer of current node
     */
	public Object getLastPointer() 
    {
		return this.lstPointers[this.uCurDegree - 1];
	}

    /**
     * Get second key of current node
     */
	public K getSecondKey() 
    {
		return this.lstKeys[1];
	}

	/**
	 * Check whether the node has room to insert item
	 * @return
	 */
	public boolean hasRoom() 
    {
		return this.uCurDegree < this.lstKeys.length;
	}

	/**
	 * Copy node keys and pointer to given node from beginIndex to endIndex
	 * @param node
	 * @param beginIndex
	 * @param endIndex
	 */
	public void copyNode(BPTNode<K> node, int beginIndex, int endIndex)
    {
		this.uCurDegree = 0;
		while (this.uCurDegree < endIndex - beginIndex) 
        {
			this.lstKeys[this.uCurDegree] = node.lstKeys[this.uCurDegree + beginIndex];
			this.lstPointers[this.uCurDegree] = node.lstPointers[this.uCurDegree + beginIndex];
            this.uCurDegree++;
		}
	}
	
	/**
	 * Reset current node
	 */
	public void resetNode() 
    {
		this.uCurDegree = 0;
		for (int i = 0; i < this.lstKeys.length; i++)
        {
			this.lstKeys[i] = null;
        }
		for (int i = 0; i < this.lstPointers.length; i++)
        {
            this.lstPointers[i] = null;
        }
	}
}
