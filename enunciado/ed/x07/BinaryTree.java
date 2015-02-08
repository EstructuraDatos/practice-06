package ed.x07;

/**
 * Árbol binario.
 * 
 * @author profesor
 *
 * @param <T>
 */
public class BinaryTree<T> extends AbstractTree<T> {
	
	//	Como árbol binario, tiene dos sub-árboles binarios
	//	"izquierdo" y "derecho"
	//
	//	Podrían ser vacíos
	protected BinaryTree<T> left;
	protected BinaryTree<T> right;
	
	/**
	 * Construye un árbol binario vacío
	 */
	public BinaryTree() {
		this.content = null;		
		this.empty = true;
		//	Nunca se usarán, porque se comprobará primero si es vacío
		//
		//	i.e. if (! isEmpty()) { left.abc(); }
		this.left = null;
		this.right = null;
	}

	/**
	 * Construye un árbol binario hoja con
	 * la información dada en su nodo raíz.
	 * 
	 * @param content información para el nodo raíz.
	 */
	public BinaryTree(T content) {
		this.content = content;
		this.empty = false;
		//	Como nodo hoja, sus sub-árboles son vacíos
		this.left = new BinaryTree<T>();
		this.right = new BinaryTree<T>();
	}

	/**
	 * Construye un árbol binario no hoja.
	 * 
	 * @param content información en el nodo raíz.
	 * @param left sub-árbol izquierdo
	 * @param right sub-árbol derecho
	 */
	public BinaryTree(T content, BinaryTree<T> left, BinaryTree<T> right) {
		this.content = content;		
		this.empty = false;
		
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int getMaxDegree() {
		if (! isEmpty()) {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public Tree<T> getSubtree(int n) {
		//	El sub-árbol izquierdo es el "0"
		switch (n) {
		case 0:
			return left;
		case 1:
			return right;
		}
		
		return null;
	}
	
	 /**
     * Returns the height of the tree, which the first level (root) begin in 1
     * @return the height of the tree
     */
    public int height(){
        int level=0;
        
        //if is empty, we finish
        if(this.isEmpty()){
            return level;
            
            
        }else{
            //count this level and check the level under its childs recursively
            int levelSubTree1,levelSubTree2;
            
            levelSubTree1=1 + this.right.height();
            levelSubTree2=1 + this.left.height();
            
            //get the higher height
            if(levelSubTree1> levelSubTree2){
                level=levelSubTree1;
            }else{
                level=levelSubTree2;
            }
        }
        return level;
    }
    
    /**
     * Check if both trees are equals
     * @param tree the other tree
     * @return true if they are equals (same elements, same order)
     */
    public boolean isEquals(BinaryTree<T> tree){
        //if both are empty, are equals
        if(this.isEmpty() && tree.isEmpty()){
            return true;
            
        //if this is empty (and the other not, because don't get the if before)
            //is false
        }else if(this.isEmpty()){
            return false;
            
        //if the content of both nodes are equals...
        }else if(this.content.equals(tree.content)){
            //check recursively their childs
            if(this.left.isEquals(tree.left) 
                    && this.right.isEquals(tree.right)){
                return false;
            }else{
                return true;
            }
            
        //if not one of the before ifs, aren't equals    
        }else{
            return false;
        }
    }
    
    /**
     * Recursively count the elements of a determinate level (where level one is the root)
     * @param level the level of tree
     * @return the number of nodes of this level on tree
     */
    public int countElemsLevel(int level){
        //if is empty, has no elements
        if(this.isEmpty()){
            return 0;
            
        }else{
            //if is the level where i want to count, count this node
            if(level==1){
                return 1;
                
            }else{
                //decrease the level and recursively count the element of the childs
                level--;
                return left.countElemsLevel(level) + right.countElemsLevel(level);
            }
        }
    }

}
