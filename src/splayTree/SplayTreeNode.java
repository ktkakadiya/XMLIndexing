package splayTree;

public class SplayTreeNode<K extends Comparable<K>, V> 
{
    /**
     * Key of node
     */
    public K key;

    /**
     * Value of node
     */
    public V value;

    /**
     * Left child, right child and parent node of current node
     */
    public SplayTreeNode<K,V> oLeftChild, oRightChild, oParent;
    
    public SplayTreeNode(K key, V value) 
    {
      this.key = key;
      this.value = value;

      this.oLeftChild = null;
      this.oRightChild = null;
      this.oParent = null;
    }

    /**
     * Get key
     * @return
     */
    public K getKey()
    {
        return this.key;
    }
    
    /**
     * Get value
     * @return
     */
    public V getValue()
    {
        return this.value;
    }
}
