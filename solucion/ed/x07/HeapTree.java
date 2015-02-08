package ed.x07;

/**
 *
 * @author dorian
 */
public class HeapTree<T extends Comparable> extends BinaryTree<T> implements Heap<T>{
    private HeapTree<T> parent;

    /**
     * Builder with an element
     * @param content 
     */
    public HeapTree(T content) {
        this.parent=null;
        this.content=content;
        this.empty=false;
        this.left=new HeapTree<>();
        this.right=new HeapTree<>();
    }

    /**
     * Default builder
     */
    public HeapTree() {
        this.parent=null;
        this.content=null;
        this.empty=true;
        this.left=null;
        this.right=null;
    }

    /**
     * 
     * @see ed.x07.Heap#addElement(java.lang.Comparable) 
     */
    @Override
    public void addElement(T element) {
        //if the node is empty, set this node with the element
        if(this.isEmpty()){
            this.content=element;
            this.empty=false;
            this.left=new HeapTree<>();
            this.right=new HeapTree<>();
            
        }else{
            //get the height of the heap
            int level=this.height();
            //get the number of nodes of the last level of the heap
            int numberOfNodes=this.countElemsLevel(level);
            //calculate the maximum of nodes of the last level
            double maxNodes=Math.pow(2, level-1);
            
            //if the last level has free nodes...
            if(maxNodes>numberOfNodes){
                HeapTree<T> subtree;
                
                //calculate if i must go to the left or right node
                if(numberOfNodes>=(maxNodes/2)){
                    subtree=(HeapTree<T>)this.right;
                }else{
                    subtree=(HeapTree<T>)this.left;
                }
 
                //set the parent of the node selected (not always necessary, but
                //to simplify the code, make it always)
                subtree.parent=this;
                //add the element recursively
                subtree.addElement(element);
                
            //if the number of nodes is the same as the maximun of the level...
            }else{
                //I must go all the time to the left until reach an empty node
                // make it recursively
                HeapTree<T> subtree;
                //get the child
                subtree=(HeapTree<T>)this.left;
                //set its parent
                subtree.parent=this;
                //add the element
                subtree.addElement(element);
                
                
            }
        }
        
        //after add the element and after return the call in the heap of calls
        //check if the parent is different of null (to know if is not the root node)
        if(parent!=null){
            //compare the contents of this node and its parent
            if(parent.content.compareTo(this.content)>0){
                //if is not in order, switch the content
                T contentBuffer=this.content;
                this.content=parent.content;
                parent.content=contentBuffer;
            }
        }
    }

    /**
     * 
     * @see ed.x07.Heap#removeMin() 
     */
    @Override
    public T removeMin() {
        if(this.isEmpty()){
            throw new EmptyTreeException("Heap Tree empty");
        }
        
        //the minimum is always the root (this)
        T element=this.content;
        //search the last node and remove it, setting the content of this node
        //with that element
        this.content=removeLastNode();
        //reorder the tree
        orderTree();
        
        return element;
    }
    
    /**
     * Reorder the subtree under this node
     */
    private void orderTree(){
        //if is empty, nothing to do
        if(this.isEmpty()){
            return;
        }
        
        //check the smaller child
        //if left's child is empty, has no childs and nothign to do
        //if not...
        if(!left.isEmpty()){
            HeapTree small;
            //if has no right's node, the smaller child is left
            if(right.isEmpty()){
                small=(HeapTree)left;
                
            }else{
                //if have... compare and take the smaller child
                if(right.content.compareTo(left.content)>0){
                    small=(HeapTree)left;
                }else{
                    small=(HeapTree)right;
                }
            }
            
            //check the this content with the content of its smaller child,
            //if this is higher, switch contents and reorder the subtree recursively
            if(this.content.compareTo(small.content)>0){
                //switch
                T auxiliar=this.content;
                this.content=(T)small.content;
                small.content=auxiliar;
                //reorder the subtree
                small.orderTree();
            }
        }
    }
    
    /**
     * Find and remove the last node added
     * @return the last node added
     */
    private T removeLastNode(){
        T result;
        
        //if is leaf, i found the element and delete it
        if(this.isLeaf()){
            result=this.content;
            this.parent=null;
            this.content=null;
            this.empty=true;
            this.left=null;
            this.right=null;
            
            
        }else{
            //get the height of the heap
            int level=this.height();
            //get the number of nodes of the last level of the heap
            //decrease one to get the last position inserted
            int numberOfNodes=this.countElemsLevel(level)-1;
            //calculate the maximum of nodes of the last level
            double maxNodes=Math.pow(2, level-1);

            //if the last level has free nodes...
            if(maxNodes>=numberOfNodes){
                HeapTree<T> subtree;

                //calculate if i must go to the left or right node
                if(numberOfNodes>=(maxNodes/2)){
                    subtree=(HeapTree<T>)this.right;
                }else{
                    subtree=(HeapTree<T>)this.left;
                }
                //recursively search the position of the node
                result=subtree.removeLastNode();
                
                
            //if the number of nodes is the same as the maximun of the level...
            }else{
                //I must go all the time to the left until reach a leaf node
                // make it recursively
                HeapTree<T> subtree;
                subtree=(HeapTree<T>)this.left;
                result=subtree.removeLastNode();
            }
        }
        return result;
    }

    /**
     * 
     * @see ed.x07.Heap#findMin()  
     */
    @Override
    public T findMin() {
        if(this.isEmpty()){
            throw new EmptyTreeException("Heap Tree empty");
        }
        
        //the minimum element is always the root
        return this.content;
    }
    
}