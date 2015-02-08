package ed.x07b;

import java.util.LinkedList;

import ed.x07.AbstractTree;

/**
 * Árbol general, donde cada nodo puede tener un número cualquiera de sub-árboles.
 * 
 * Cada sub-árbol no tiene asignada una posición fija, dependerá de
 * cuántos sub-árboles tenga la raíz.
 * 
 * @author profesor
 *
 * @param <T> tipo de información en cada nodo.
 */
public class GeneralTree<T> extends AbstractTree<T> {

	//	Sub-árboles
	protected LinkedList<GeneralTree<T>> subtrees = new LinkedList<GeneralTree<T>>();

	/**
	 * Construye un árbol, hoja, con el contenido dado en la raíz.
	 * 
	 * @param content información en la raíz.
	 */
	public GeneralTree(T content) {
		this.content = content;
		//	Lista de sub-árboles vacía
	}
	
	/**
	 * Añade un sub-árbol al final de la lista de hijos.
	 * 
	 * @param t sub-árbol a añadir.
	 */
	public void addSubtree(GeneralTree<T> t) {
		subtrees.add(t);
	}
	
	@Override
	public int getMaxDegree() {
		return subtrees.size();
	}

	@Override
	public GeneralTree<T> getSubtree(int n) {
		//	Depende de la configuración de la lista en cada momento
		return subtrees.get(n);
	}
	
}