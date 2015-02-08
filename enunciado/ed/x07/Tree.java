package ed.x07;

/**
 * Interfaz de operaciones del TAD árbol.
 * 
 * Permite consultar propiedades básicas del árbol, como
 * su grado, si es árbol vacío, si es hoja, etc.
 * 
 * También da acceso a los sub-árboles que pueda tener, e 
 * implementa diversos recorridos.
 * 
 * Por último, maneja un dato de tipo T en el nodo raíz.
 * 
 * @author profesor
 *
 * @param <T> tipo de dato almacenado en cada nodo del árbol.
 */
public interface Tree<T> {

	/**
	 * Cierto si el árbol es "árbol vacío"
	 * 
	 * @return cierto para árboles vacíos
	 */
	boolean isEmpty();
	
	/**
	 * Indica si el árbol es hoja (todos sus hijos vacíos)
	 * 
	 * @return cierto si todos los hijos son vacíos
	 */
	boolean isLeaf();
	
	/**
	 * Devuelve el número de hojas en el árbol
	 * 
	 * @return número de hojas en el árbol
	 */
	int getNumberOfLeaves();
	
	/**
	 * Devuelve el grado de la raíz
	 * 
	 * @return número de hijos no vacíos
	 */
	int getDegree();
	
	/**
	 * Devuelve el máximo grado de la raíz
	 * 
	 * @return número máximo posible de hijos
	 */
	int getMaxDegree();

	/**
	 * Devuelve el número de nodos en el árbol
	 * 
	 * @return número de nodos en el árbol
	 */
	int getNumberOfNodes();
	
	/**
	 * Devuelve el sub-árbol n-ésimo, puede ser vacío
	 * 
	 * @param n índice del sub-árbol, empiezan en "0"
	 * 
	 * @return árbol n-ésimo
	 */
	Tree<T> getSubtree(int n);
	
	/**
	 * Hace que el objeto dado visite cada nodo del árbol en
	 * pre-orden.
	 * 
	 * @param visitor objeto que visita los nodos.
	 */	
	void preOrder(Visitor<T> visitor);
	
	/**
	 * Hace que el objeto dado visite cada nodo del árbol en
	 * in-orden.
	 * 
	 * @param visitor objeto que visita los nodos.
	 */
	void inOrder(Visitor<T> visitor);
	
	/**
	 * Visita los nodos del árbol en post-orden.
	 * 
	 * @param visitor objeto que visita los nodos.
	 */
	void postOrder(Visitor<T> visitor);

	/**
	 * Recorre el árbol en anchura.
	 * 
	 * @param visitor objeto que visitará los nodos.
	 */
	void breadthOrder(Visitor<T> visitor);

	
	/**
	 * Devuelve la información en el nodo raíz.
	 * 
	 * @return información en el nodo raíz.
	 */
	T getContent();
	
	
	/**
	 * Cambia la información en el nodo raíz.
	 * 
	 * @param content información para el nodo raíz.
	 */
	void setContent(T content);
}
