package splayTree;

public class SplayTree<K extends Comparable<K>, V> 
{
    /**
     * Root node of current tree
     */
    public SplayTreeNode<K,V> root;
    
    public SplayTree() 
    {
        this.root = null;
    }

    /**
     * Insert key value pair in element
     * @param key
     * @param value
     */
    public void insertElement(K key, V value)
    {
        SplayTreeNode<K,V> oNode = new SplayTreeNode<K,V>(key, value);
        this.insertNode(oNode);
    }

    /**
     * Insert node into tree
     * @param oNode
     */
    private void insertNode(SplayTreeNode<K,V> oNode) 
    {
        SplayTreeNode<K,V> oCurNode = this.root, oParent = null;
        while(oCurNode != null) 
        {
            oParent = oCurNode;
            if(oNode.key.compareTo(oCurNode.key) < 0)
            {
                oCurNode = oCurNode.oLeftChild;
            }
            else
            {
                oCurNode = oCurNode.oRightChild;
            }
        }
        oNode.oParent = oParent;
      
        if(oParent == null)
        {
            this.root = oNode;
        }
        else if(oNode.key.compareTo(oParent.key) < 0)
        {
            oParent.oLeftChild = oNode;
        }
        else
        {
            oParent.oRightChild = oNode;
        }
      
        this.splay(oNode);
    }

    /**
     * Insert key value pair in element
     * @param key
     * @param value
     * @return
     */
    public SplayTreeNode<K,V>  findElement(K key)
    {
        return this.findKeyNode(this.root, key);
    }
    
    /**
     * Find node containing given key from given node
     * @param oNode
     * @param key
     * @return
     */
    private SplayTreeNode<K,V> findKeyNode(SplayTreeNode<K,V> oNode, K key) 
    {
        int uComp = key.compareTo(oNode.key);
        if(uComp == 0) 
        {
            this.splay(oNode);
            return oNode;
        }

        if(uComp < 0)
        {
            return this.findKeyNode(oNode.oLeftChild, key);
        }
        else
        {
            return this.findKeyNode(oNode.oRightChild, key);
        }
    }

    /**
     * Insert key value pair in element
     * @param key
     * @param value
     */
    public void deleteElement(K key)
    {
        SplayTreeNode<K,V> oNode = this.findElement(key);
        this.deleteNode(oNode);
    }
      
    /**
     * Delete given node
     * @param oNode
     */
    public void deleteNode(SplayTreeNode<K,V> oNode) 
    {
        this.splay(oNode);

        SplayTree<K,V> leftSubtree = new SplayTree<K,V>();
        leftSubtree.root = this.root.oLeftChild;
        if(leftSubtree.root != null)
        {
            leftSubtree.root.oParent = null;
        }
    
        SplayTree<K,V> rightSubtree = new SplayTree<K,V>();
        rightSubtree.root = this.root.oRightChild;
        if(rightSubtree.root != null)
        {
            rightSubtree.root.oParent = null;
        }
    
        if(leftSubtree.root != null) 
        {
            SplayTreeNode<K,V> oMaxNode = leftSubtree.getMaxValue(leftSubtree.root);
            leftSubtree.splay(oMaxNode);
            leftSubtree.root.oRightChild = rightSubtree.root;
            this.root = leftSubtree.root;
        }
        else 
        {
            this.root = rightSubtree.root;
        }
    }

    /**
     * Get Maximum valule below given node
     * @param oNode
     * @return
     */
    public SplayTreeNode<K,V> getMaxValue(SplayTreeNode<K,V> oNode) 
    {
        while(oNode.oRightChild != null)
        {
            oNode = oNode.oRightChild;
        }
        return oNode;
    }    
    
    /**
     * Perform splay operation for given node
     * @param oNode
     */
    public void splay(SplayTreeNode<K,V> oNode) 
    {
        while(oNode.oParent != null) 
        {
            if(oNode.oParent == this.root) 
            {
                if(oNode == oNode.oParent.oLeftChild) 
                {
                    this.rightRotate(oNode.oParent);
                }
                else 
                {
                    this.leftRotate(oNode.oParent);
                }
            }
            else 
            {
                SplayTreeNode<K,V> oParent = oNode.oParent;
                SplayTreeNode<K,V> oGrandParent = oParent.oParent;
        
                if(oParent.oLeftChild == oNode && oGrandParent.oLeftChild == oParent) 
                {
                    this.rightRotate(oGrandParent);
                    this.rightRotate(oParent);
                }
                else if(oParent.oRightChild == oNode && oGrandParent.oRightChild == oParent) 
                {
                    this.leftRotate(oGrandParent);
                    this.leftRotate(oParent);
                }
                else if(oParent.oRightChild == oNode && oGrandParent.oLeftChild == oParent) 
                {
                    this.leftRotate(oParent);
                    this.rightRotate(oGrandParent);
                }
                else if(oParent.oLeftChild == oNode && oGrandParent.oRightChild == oParent) 
                {
                    this.rightRotate(oParent);
                    this.leftRotate(oGrandParent);
                }
            }
        }
    }

    /**
     * Left rotate given node
     * @param oNode
     */
    public void leftRotate(SplayTreeNode<K,V> oNode) 
    {
        SplayTreeNode<K,V> oRightChild = oNode.oRightChild;
        oNode.oRightChild = oRightChild.oLeftChild;
        if(oRightChild.oLeftChild != null) 
        {
            oRightChild.oLeftChild.oParent = oNode;
        }

        oRightChild.oParent = oNode.oParent;
        if(oNode.oParent == null) 
        {
            this.root = oRightChild;
        }
        else if(oNode == oNode.oParent.oLeftChild) 
        {
            oNode.oParent.oLeftChild = oRightChild;
        }
        else 
        {
            oNode.oParent.oRightChild = oRightChild;
        }

        oRightChild.oLeftChild = oNode;
        oNode.oParent = oRightChild;
    }
    
    /**
     * Right rotate given node
     * @param oNode
     */
    public void rightRotate(SplayTreeNode<K,V> oNode) 
    {
        SplayTreeNode<K,V> oLeftChild = oNode.oLeftChild;
        oNode.oLeftChild = oLeftChild.oRightChild;
        if(oLeftChild.oRightChild != null) 
        {
            oLeftChild.oRightChild.oParent = oNode;
        }

        oLeftChild.oParent = oNode.oParent;
        if(oNode.oParent == null) 
        {
            this.root = oLeftChild;
        }
        else if(oNode == oNode.oParent.oRightChild) 
        {
            oNode.oParent.oRightChild = oLeftChild;
        }
        else 
        {
            oNode.oParent.oLeftChild = oLeftChild;
        }

        oLeftChild.oRightChild = oNode;
        oNode.oParent = oLeftChild;
    }
    
    /**
     * Print tree
     */
    public void printTree()
    {
        this.printNode(this.root);
    }

    /**
     * Print give node
     * @param oNode
     */
    public void printNode(SplayTreeNode<K,V> oNode) 
    {
        if(oNode != null) 
        {
            System.out.println(oNode.key);
            this.printNode(oNode.oLeftChild);
            this.printNode(oNode.oRightChild);
        }
    }
}
    