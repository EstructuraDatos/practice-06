package ed.x07b;

import java.util.ArrayList;

import ed.x07.AbstractTree;
import ed.x07.Tree;

/**
 * Árbol de grado N.
 * 
 * Los sub-árboles tienen una posición asignada, de igual forma
 * que en árboles binarios pero con N sub-árboles.
 * 
 * @author profesor
 *
 * @param <T> tipo de información en cada nodo.
 */
public class NaryTree<T> extends AbstractTree<T> {

	//	Grado del árbol
	protected int degree;
	
	//	Array de sub-árboles
	protected ArrayList<NaryTree<T>> subtrees;
	
	
	/**
	 * Construye un árbol de grado dado, vacío.
	 * 
	 * @param degree grado del árbol.
	 */
	public NaryTree(int degree) {
		this.content = null;
		this.empty = true;
		
		this.subtrees = null;
		this.degree = degree;
	}
	
	/**
	 * Construye un árbol de grado dado, hoja.
	 * 
	 * @param content información para la raíz.
	 * @param degree grado del árbol.
	 */
	public NaryTree(T content, int degree) {
		this.content = content;
		this.empty = false;
		
		this.degree = degree;
		this.subtrees = new ArrayList<NaryTree<T>>(degree);
		
		//	Como hoja, todos sus sub-árboles son vacíos
		for (int i = 0; i < degree; i++) {
			subtrees.add(new NaryTree<T>(degree));
		}
	}
	
	@Override
	public int getMaxDegree() {
		if (! isEmpty()) {
			return degree;
		} else {
			//	Árbol vacío, grado cero
			return 0;
		}
	}

	@Override
	public Tree<T> getSubtree(int n) {
		return subtrees.get(n);
	}

	/**
	 * Genera un árbol aleatorio de grado y altura máxima dados.
	 * 
	 * @param mark prefijo (cadena) para la información de cada nodo.
	 * @param degree grado del árbol.
	 * @param height altura máxima.
	 * @return árbol aleatorio con esos parámetros.
	 */
	public static NaryTree<String> randomTree(String mark, int degree, int height) {
		if (height > 0) {
			NaryTree<String> root = new NaryTree<String>(mark, degree);
			for (int i = 0; i < degree; i++) {
				if (Math.random() < 0.750) {
					root.subtrees.set(i, (NaryTree<String>) randomTree(mark + "." + Integer.toString(i + 1), degree, height - 1));
				}
			}
			
			return root;
		} else {
			return new NaryTree<String>(degree);
		}
	}
	
}
