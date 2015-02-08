package ed.x07;

import java.util.LinkedList;
import java.util.Queue;



/**
 * Implementación parcial de árboles.
 * 
 * Algunas operaciones del TAD árbol pueden implementarse ya aquí,
 * con independencia del tipo específico de árbol, binario,
 * de expresión, n-ario, etc. Se usan únicamente operaciones
 * comunes a todos los árboles, i.e. las de {@link Tree}
 * 
 * También hay datos que hay que almacenar para cualquier
 * tipo o implementación, como la información en cada nodo.
 * 
 * @author profesor
 *
 * @param <T> tipo de la información almacenada en cada nodo.
 */
public abstract class AbstractTree<T> implements Tree<T> {

	//	Marca el árbol como vacíos
	protected boolean empty;
	
	//	Información en este nodo de un árbol
	protected T content;
	
	@Override
	public void setContent(T content) {
		this.content = content;
	}
	
	@Override
	public T getContent() {
		return content;
	}
	
	@Override
	public int getNumberOfLeaves() {
		//	Si no es árbol vacío, cuenta el número de hojas
		//	usando recursividad.
		if (! isEmpty()) {
			//	Si es hoja, el este árbol tiene "1" hoja
			//	(caso base)
			if (isLeaf()) {
				return 1;
			} else {
				//	Si no es hoja, sumar el número de hojas
				//	de cada uno de sus sub-árboles
				int result = 0;
				
				for (int i = 0; i < getMaxDegree(); i++) {
					result += getSubtree(i).getNumberOfLeaves();
				}
				return result;
			}
		} else {
			//	Los árboles vacíos tienen "0" hojas
			return 0;
		}
	}

	@Override
	public int getNumberOfNodes() {
		//	Cuenta el número de nodos usando recursividad
		if (! isEmpty()) {
			//	Si no es vacío, este árbol al menos tiene "1" nodo (la raíz).
			int result = 1;
			//	Además puede tener más nodos en sus sub-árboles
			for (int i = 0; i < getMaxDegree(); i++) {
				result += getSubtree(i).getNumberOfNodes();
			}
			return result;
		} else {
			//	Un árbol vacío tiene "0" nodos
			return 0;
		}		
	}

	@Override
	public boolean isLeaf() {
		//	Para la implementación con árboles vacíos, será
		//	hoja si todos sus sub-árboles son vacíos.
		for (int i = 0; i < getMaxDegree(); i++) {
			//	Al menos uno no es vacío, entonces éste no es hoja
			if (! getSubtree(i).isEmpty()) { return false; }
		}
		//	Todos son vacíos, éste es hoja
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		//	El atributo marca el árbol como vacío
		return empty;
	}

	@Override
	public int getDegree() {
		//	Hay que contar el número de sub-árboles no vacíos
		if (! isEmpty()) {
			int result = 0;
			for (int i = 0; i < getMaxDegree(); i++) {
				//	Un sub-árbol más no vacío
				if (! getSubtree(i).isEmpty()) { result++; }
			}
			return result;
			
		} else {
			//	Un árbol vacío tiene grado "0"
			return 0;
		}
	}
	
	@Override
	public void preOrder(Visitor<T> visitor) {
		//	Recorrido en pre-orden
		//
		//	Los árboles vacíos no se recorren
		if (! isEmpty()) {
			//	Primero se visita este nodo (raíz)
			visitor.visiting(content);
			//	Luego se recorre en pre-orden cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				getSubtree(i).preOrder(visitor);
			}
		}
	}
	
	@Override
	public void postOrder(Visitor<T> visitor) {
		//	Un árbol no vacío se recorre
		if (! isEmpty()) {
			//	Primero sus sub-árboles
			for (int i = 0; i < getMaxDegree(); i++) {
				getSubtree(i).postOrder(visitor);
			}
			//	Luego el nodo raíz
			visitor.visiting(content);			
		}
	}
	
	@Override
	public void breadthOrder(Visitor<T> visitor) {
		
		//	LinkedList es del JDK e implementa Queue
		final Queue<Tree<T>> Q1 = new LinkedList<Tree<T>>();
		
		//	Inicialmente, se encola el propio árbol
		Q1.add(this);
		//	Mientras queden árboles por recorrer
		while (! Q1.isEmpty()) {
			//	Saca de la cola el siguiente a procesar
			Tree<T> currentlyAt = Q1.poll();
			//	Si no es vacío, se visita y se encolan sus sub-árboles
			if (! currentlyAt.isEmpty()) {
				//	Visita el nodo raíz
				visitor.visiting(currentlyAt.getContent());
				//	Los sub-árboles quedan pendientes de ser visitados: se encolan en la cola
				for (int i = 0; i < currentlyAt.getMaxDegree(); i++) {
					Q1.add(currentlyAt.getSubtree(i));
				}
			}
		}
	}
	
	@Override 
	public void inOrder(Visitor<T> visitor) {
		if (! isEmpty()) {
			//	Primero se recorre el primer sub-árbol
			getSubtree(0).inOrder(visitor);
			//	Luego este nodo (raiz)
			visitor.visiting(content);
			//	Y el rsto de sub-árboles
			for (int i = 1; i < getMaxDegree(); i++) {
				getSubtree(i).inOrder(visitor);
			}
		}
	}
	
	//	El código fuente está en UTF-8, debería ser el símbolo de
	//	conjunto vacío. Si aparecen caracteres "raros", es porque
	//	el proyecto no está bien configurado en Eclipse para
	//	usar esa codificación de caracteres.
	//
	public static final String EMPTY_TREE_MARK = "∅";

	/* 
	 * Representa un árbol como string.
	 * 
	 * Un árbol vacío se representa como "∅". Un árbol no vacío
	 * como "{(información raíz), sub-árbol 1, sub-árbol 2, ...}".
	 * 
	 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un árbol binarios con raíz "A" y
	 * un único sub-árbol, a su izquierda, con raíz "B".
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (! isEmpty()) {
			//	Construye el resultado de forma eficiente
			StringBuffer result = new StringBuffer();
				
			//	Raíz
			result.append("{" + content.toString());
			//	Y cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				result.append(", " + getSubtree(i).toString());
			}
			//	Cierra la "}" de este árbol
			result.append("}");
			
			return result.toString();
		} else {
			return AbstractTree.EMPTY_TREE_MARK;
		}
	}
	
	/**
	 * Implementación para mostrar un árbol con niveles.
	 * 
	 * @param t (sub-)árbol a mostrar.
	 * @param bx buffer donde acumular el resultado.
	 * @param depth profundidad de t en el árbol total.
	 */
	private void render(Tree<T> t, StringBuffer bx, int depth) {
		
		//	Muestra el nodo raíz; tantos espacios como profundo sea
		for (int i = 0; i < depth; i++) { bx.append("|  "); }
		
		//	Y después de los espacios, la información
		if (! t.isEmpty()) {
			bx.append(t.getContent());
		} else {
			bx.append(AbstractTree.EMPTY_TREE_MARK);
		}
		bx.append("\n");
		
		//	Muestra sus sub-árboles (recursividad)
		for (int i = 0; i < t.getMaxDegree(); i++) {
			//	Que están a mayor profundidad que éste
			render(t.getSubtree(i), bx, depth + 1);
		}
	}
	
	/**
	 * Devuelve una representación en niveles del árbol.
	 * 
	 * @return cada nodo se muestra según su profundidad.
	 */
	public String render() {
		//	Acumula el resultado
		StringBuffer rx = new StringBuffer();
		//	Genera usando recursividad
		render(this, rx, 0);
		//	
		return rx.toString();
	}
}
