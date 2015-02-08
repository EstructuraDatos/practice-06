package ed.x07;


import java.util.NoSuchElementException;

import ed.x07.BinaryTree;

/**
 * Árbol binario de búsqueda (binary search tree, BST).
 * 
 * @author profesor
 * 
 * @param <T>
 *            tipo de la información en cada nodo, comparable.
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends
		BinaryTree<T> {

	/**
	 * Devuelve el árbol binario de búsqueda izquierdo.
	 * 
	 * @return
	 */
	private BinarySearchTree<T> getLeftBST() {
		return (BinarySearchTree<T>) left;
	}

	/**
	 * Devuelve el árbol binario de búsqueda derecho.
	 * 
	 * @return
	 */
	private BinarySearchTree<T> getRightBST() {
		return (BinarySearchTree<T>) right;
	}

	/**
	 * Árbol BST vacío
	 */
	public BinarySearchTree() {
	}

	/**
	 * Inserta todos los elementos de un array en el árbol.
	 * 
	 * @param elements
	 *            elementos a insertar.
	 */
	public void insert(T... elements) {
		for (T e : elements) {
			insert(e);
		}
	}

	/**
	 * Inserta un nuevo elemento en el árbol.
	 * 
	 * @param element
	 *            valor a insertar.
	 */
	public void insert(T element) {
		if (isEmpty()) {
			// Ya no es vacío, ahora es hoja (se añaden dos nodos vacíos en las ramas left y right
			content = element;
			empty = false;

			left = new BinarySearchTree<T>();
			right = new BinarySearchTree<T>();
		} else {
			// Compara con el valor de la raíz
			int diff = element.compareTo(content);

			if (diff == 0) {
				// Ya está en el árbol
				return;
			}

			if (diff < 0) {
				// Es menor, irá en el sub-árbol izquierdo
				getLeftBST().insert(element);
			} else {
				// Es mayor, irá al sub-árbol derecho
				getRightBST().insert(element);
			}
		}
	}

	/**
	 * Elimina los valores en un array del árbol.
	 * 
	 * @param elements
	 *            valores a eliminar.
	 */
	public void withdraw(T... elements) {
		for (T e : elements) {
			withdraw(e);
		}
	}

	/**
	 * Elimina un elemento del árbol.
	 * 
	 * @param element
	 *            elemento a eliminar.
	 */

	public void withdraw(T element) {
		if (!isEmpty()) {
			// Compara con la información en la raíz
			int diff = element.compareTo(content);

			if (diff == 0) {
				// Eliminar este nodo

				// Si tiene sub-árbol izquierdo y sub-árbol derecho
				if ((!getLeftBST().isEmpty()) && (!getRightBST().isEmpty())) {
					// Sustituir por máximo del izquierdo; encontrarlo
					T max = getLeftBST().findMax();
					// Guardarlo aquí (raíz)
					content = max;
					// Y eliminar el máximo del sub-árbol
					getLeftBST().withdraw(max);
				} else {
					// Si tiene sub-árbol derecho
					if (!getRightBST().isEmpty()) {
						// Copiar el nodo del hijo derecho en el this
						this.content = getRightBST().getContent();
						this.left = getRightBST().getLeftBST();
						this.right = getRightBST().getRightBST();

					} else {
						// Si tiene sub-árbol izquierdo
						if (!getLeftBST().isEmpty()) {
							// Copiar el nodo del hijo derecho en el this
							this.content = getLeftBST().getContent();
							this.right = getLeftBST().getRightBST();
							this.left = getLeftBST().getLeftBST();
							
						   // OJO!  Tiene que ser en este orden (1º el hijo derecho y luego el izquierdo )
						} else {
							// Es hoja, se convierte en vacío
							content = null;
							empty = true;

							left = null;
							right = null;
						}
					}
				}

			} else {
				// No está en la raíz, mirar en qué sub-árbol está
				if (diff < 0) {
					// Y eliminarlo de él, izquierdo
					getLeftBST().withdraw(element);
				} else {
					// Ó derecho
					getRightBST().withdraw(element);
				}
			}
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Indica si el elemento está en el árbol.
	 * 
	 * @param element
	 *            información a localizar.
	 * @return referencia al elemento si está en el árbol, null si no está.
	 */
	public T find(T element) {
		// Si tiene nodos, buscar
		if (!isEmpty()) {
			// Comparar con el elemento en la raíz del árbol actual
			// (estamos en recursividad)
			int diff = content.compareTo(element);

			if (diff < 0) {
				// No está en la raíz, pero podría estar en el sub-árbol
				// izquierdo
				return getLeftBST().find(element);
			}
			if (diff > 0) {
				// No está en la raíz, podría estar en el derecho
				return getRightBST().find(element);
			}
			// Está en la raíz
			return content;
		} else {
			// Es vacío, no está aquí
			return null;
		}
	}

	/**
	 * Devuelve el mínimo del árbol.
	 * 
	 * @return mínimo valor en el árbol.
	 */
	public T findMin() {
		// Si tiene nodos
		if (!isEmpty()) {
			// Podría haber menores en el izquierdo, si no es vacío
			if (getLeftBST().isEmpty()) {
				// No los hay, éste es el menor (raíz)
				return content;
			} else {
				// Buscar en el izquierdo (son menores que la raíz)
				return getLeftBST().findMin();
			}
		} else {
			// Vacío
			return null;
		}
	}

	/**
	 * Devuelve el máximo en el árbol.
	 * 
	 * @return máximo valor en el árbol.
	 */
	public T findMax() {
		// Inspeccionar con recursividad, bajando por la derecha
		if (!isEmpty()) {
			if (getRightBST().isEmpty()) {
				return content;
			} else {
				return getRightBST().findMax();
			}
		} else {
			return null;
		}
	}
	
	 /**
     * Delete the maximum element of the tree and return it
     * @return the maximum T element of the tree
     */
    public T deleteMax(){
        //the element to return
        T target=null;
        
        //if empty, throw exception
        if(isEmpty()){
            throw new EmptyTreeException("No nodes on tree");
            
        }else{
            //if right node is empty, has no more elements, take this
            if (getRightBST().isEmpty()) {
                //take this element
                target=this.getContent();
                
                //if has not a left node, delete this node
                if(getLeftBST().isEmpty()){
                    this.setContent(null);
                    this.empty=true;
                    
                //if has a child node, set this node as the child
                }else{
                    //get the child node
                    BinarySearchTree<T> nodoHijo=getLeftBST();
                    //set the content of the child to his parent (this)
                    this.setContent(nodoHijo.content);
                    //set the node's child
                    this.left=nodoHijo.getLeftBST();
                    this.right=nodoHijo.getRightBST();
                }
                
            //if has a right node, delete the maximum recursively
            } else {
                target=getRightBST().deleteMax();
            }   
        }
        
        return target;
    }
    
    /**
     * Delete the minimum element of the tree and return it
     * @return the minimun element of the tree
     */
    public T deleteMin(){
        //element to return
        T target=null;
        
        //if is empty, throw an exception
        if(isEmpty()){
            throw new EmptyTreeException("No nodes on tree");
            
        }else{
            //if has not a left child, the minimun element is this
            if (getLeftBST().isEmpty()) {
                //get the element
                target=this.getContent();
                //if has not more child, delete the node
                if(getRightBST().isEmpty()){
                    this.setContent(null);
                    this.empty=true;
                    
                //if has a child (right node) set this node as the child
                }else{
                    //get the child
                    BinarySearchTree<T> nodoHijo=getRightBST();
                    //set the content of this with the content of his child
                    this.setContent(nodoHijo.content);
                    //set the node's child
                    this.left=nodoHijo.getLeftBST();
                    this.right=nodoHijo.getRightBST();
                }
                
            //if has a right node, delete the minimun recursively 
            } else {
                target=getLeftBST().deleteMin();
            }   
        }
        
        return target;
    }
    
    /**
     * Count the number of elements smaller than n
     * @param n the number limit (not include)
     * @return the number of nodes smaller than n in the tree
     */
    public int smallerCount(T n){
        int target=0;
        
        //if not empty...
        if(!isEmpty()){
            //if the node is smaller than n...
            if(content.compareTo(n)<0){
                //increase the counter
                target++;
                //and count all the elements under this node
                target +=getLeftBST().smallerCount(n);
                target +=getRightBST().smallerCount(n);
                
            //if not smaller...
            }else{
                //search and count in the nodes under the left node
                target +=getLeftBST().smallerCount(n);
            }
        }
        
        return target;
    }
    
    /**
     * Count the number of elements higher than n
     * @param n the number limit (not include)
     * @return the number of nodes higher than n in the tree
     */
    public int olderCount(T n){
        int target=0;
        
        //if not empty...
        if(!isEmpty()){
            //if this node is higher than the limit...
            if(content.compareTo(n)>0){
                //increase counter
                target++;
                //count all the nodes under this node
                target+=getLeftBST().olderCount(n);
                target+=getRightBST().olderCount(n);
                
            //if not, search under its right node
            }else{
                target+=getRightBST().olderCount(n);
            }
        }
        
        return target;
    }

}
