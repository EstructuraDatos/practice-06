package ed.x07;

import java.util.LinkedList;
import java.util.List;

/**
 * Almacena la información que va encontrando al visitar nodos
 * en una estructura de datos.
 * 
 * Por ejemplo, al terminar su recorrido por un árbol 
 * tendría una lista con la información en todos los nodos del árbol,
 * en el orden de la visita.
 * 
 * @author profesor
 *
 * @param <T>
 */
public class GatheringVisitor<T> implements Visitor<T> {

	public List<T> result = new LinkedList<T>();

	@Override
	public void visiting(T info) {
		//	Visitar un nodo aquí implica almacenar su información
		//	en una lista
		result.add(info);
	}
	
	@Override
	public String toString() {
		return result.toString();
	}
}
