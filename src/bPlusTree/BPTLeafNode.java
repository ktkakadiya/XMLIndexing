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
	 * Get pointer associated to given key
	 * @param key
	 * @return
	 */
	public V getKeyPointer(K key)
	{
		int idx = this.getKeyIndex(0, this.uCurDegree - 1, key);
		if(idx != -1)
		{
			return (V) this.lstPointers[idx];
		}
		return null;
	}

	/**
	 * Get index of given key in range
	 * @param left
	 * @param right
	 * @param key
	 * @return
	 */
	int getKeyIndex(int left, int right, K key)
    {
        if (right >= left) 
		{
            int mid = left + (right - left) / 2;
			int uComp = this.lstKeys[mid].compareTo(key);
            if (uComp == 0)
			{
                return mid;
			}
            else if (uComp > 0)
			{
                return this.getKeyIndex(left, mid - 1, key);
			}
			else
			{
            	return this.getKeyIndex(mid + 1, right, key);
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