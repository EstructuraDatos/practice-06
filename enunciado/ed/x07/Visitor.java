package ed.x07;

/**
 * Cualquier clase que implemente esta interfaz sirve para
 * visitar una estructura de datos que ofrezca el patrón.
 * 
 * Por ejemplo, un árbol ofrece el método {@link Tree#preOrder(Visitor)}
 * que recorre el árbol en pre-orden usando el objeto dado. El método
 * {@link Visitor#visiting(Object)} será invocado con la información
 * de cada nodo, según vayan siendo visitados.
 * 
 * En el caso de una lista enlazada ["1","2","3"], si admitiese un recorrido de
 * principio a fin, el método sería invocado consecutivamente con
 * la información "1", "2" y "3". 
 * 
 * @author profesor
 *
 * @param <T>
 */
public interface Visitor<T> {

	/**
	 * Será invocado por la estructura de datos cuando se visite
	 * uno de sus nodos.
	 * 
	 * @param info la información almacenada en ese nodo.
	 */
	public void visiting(T info);
	
}
