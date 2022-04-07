package bPlusTree;

public class BPTLeafNode<K extends Comparable<K>, V> extends BPTNode<K> 
{
	public BPTLeafNode(int degree) 
    {
		super(degree);
	}

	public BPTLeafNode(BPTLeafNode<K, V> node) 
    {
		super(node);
	}

	/**
	 * Insert given key and value 
	 * @param key
	 * @param object
	 */
	public void insert(K key, V value) 
    {
		if (this.uCurDegree == 0 || key.compareTo(this.lstKeys[0]) < 0) 
        {
			this.insert(key, value, 0);
		} 
        else 
        {
			this.insert(key, value, getLKeyIndex(key) + 1);
		}
	}

	/**
	 * Get largest index of key less that given key
	 * @param key
	 * @return
	 */
	protected int getLKeyIndex(K key) 
    {
		if(key != null)
        {
            for (int i = this.uCurDegree - 1; i >= 0; i--) 
            {
                if (this.lstKeys[i].compareTo(key) < 0)
                {
                    return i;
                }
            }
        }
		return -1;
	}
	
	/**
	 * Get successor node of current node
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BPTLeafNode<K, V> getSuccessorNode() 
    {
		return (BPTLeafNode<K, V>) this.lstPointers[this.lstPointers.length - 1];
	}

	/**
	 * Set successor node of current node
	 * @param successor
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public BPTLeafNode<K, V> setSuccessorNode(BPTLeafNode<K, V> successor) 
    {
		BPTLeafNode<K, V> oNode = (BPTLeafNode<K, V>) this.lstPointers[this.lstPointers.length - 1];
		this.lstPointers[this.lstPointers.length - 1] = successor;
		return oNode;
	}
}