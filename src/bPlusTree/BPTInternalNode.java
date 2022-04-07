package bPlusTree;

public class BPTInternalNode<K extends Comparable<K>> extends BPTNode<K>
{
	public BPTInternalNode(int degree) 
    {
		super(degree);
	}

	public BPTInternalNode(BPTInternalNode<K> node) 
    {
		super(node);
	}

	/**
	 * Get the child node can contain specified key
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BPTNode<K> searchKey(K key) 
    {
		int uIndex = getGEKeyIndex(key); 
		if (uIndex < 0) 
        {
			return (BPTNode<K>) this.lstPointers[this.uCurDegree];
		} 
        else if (key.compareTo(this.lstKeys[uIndex]) == 0) 
        {
			return (BPTNode<K>) this.lstPointers[uIndex + 1];
		} 
		return (BPTNode<K>) this.lstPointers[uIndex];
	}
	
	/**
	 * Get the index of first key greater than or equal to given key 
	 * @param key
	 * @return
	 */
	protected int getGEKeyIndex(K key) 
    {
        if(key != null)
        {
            for (int i = 0; i < this.uCurDegree; i++) 
            {
                if (this.lstKeys[i].compareTo(key) >= 0)
                {
                    return i;
                }
            }
        }
		return -1;
	}

	/**
	 * Insert specified key and new child after current child node
	 * @param key
	 * @param oNewChild
	 * @param oChild
	 */
	protected void insertAfterChild(K key, BPTNode<K> oNewChild, BPTNode<K> oChild) 
    {
		int i = this.uCurDegree;
		while (this.lstPointers[i] != oChild) 
        {
			this.lstKeys[i] = this.lstKeys[i - 1];
			this.lstPointers[i + 1] = this.lstPointers[i];
			i--;
		}
		this.lstKeys[i] = key;
		this.lstPointers[i + 1] = oNewChild;
		this.uCurDegree++;
	}

	/**
	 * Copy node keys and pointer to given node from beginIndex to endIndex
	 * @param node
	 * @param beginIndex
	 * @param endIndex
	 */
	public void copyNode(BPTInternalNode<K> node, int beginIndex, int endIndex) 
    {
		super.copyNode(node, beginIndex, endIndex);
		this.lstPointers[this.uCurDegree] = node.lstPointers[this.uCurDegree + beginIndex];
	}

	/**
	 * Replace old key with new key
	 * @param oldKey
	 * @param newKey
	 */
	public void replaceKey(K oldKey, K newKey)
	{
		for(int i=0; i<this.lstKeys.length; i++)
		{
			if(this.lstKeys[i] == oldKey)
			{
				this.lstKeys[i] = newKey;
			}
		}
	}
}