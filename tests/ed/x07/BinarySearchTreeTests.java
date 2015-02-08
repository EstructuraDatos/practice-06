package ed.x07;

import junit.framework.Assert;

import org.junit.Test;

import ed.x07.BinarySearchTree;
import ed.x07.GatheringVisitor;

public class BinarySearchTreeTests {
	
	@Test
	public void inOrdenBinarySearch() {
		//	Recorrido en In-orden, valores de menor a mayor
		BinarySearchTree<String> T1 = new BinarySearchTree<String>();
		//	Inserta algunos elementos no ordenados
		T1.insert("A","D","B","C");
		System.out.println(T1.toString());
		
		//	Visita los nodos del árbol en In-orden (menores primero)
		GatheringVisitor<String> V1 = new GatheringVisitor<String>();
		
		T1.inOrder(V1);
		
		//	V1 ha almacenado en una lista los valores visitados
		Assert.assertEquals("[A, B, C, D]", V1.toString());
	}
	
	@Test
	public void basicBinarySearch() {
		
		BinarySearchTree<String> T1 = new BinarySearchTree<String>();
		
		T1.insert("A","B","C","D");
		System.out.println(T1.toString());
		
		Assert.assertEquals("A", T1.findMin());
		Assert.assertEquals("D", T1.findMax());
		
		T1.withdraw("A","B","C","D");
		
		Assert.assertTrue(T1.isEmpty());
		System.out.println(T1.toString());
		
		T1.insert("D","C","B","A");
		System.out.println(T1.toString());
		
		Assert.assertEquals("A", T1.findMin());
		Assert.assertEquals("D", T1.findMax());
		
		T1.withdraw("A","B","C","D");
		
		Assert.assertTrue(T1.isEmpty());
		
		T1.insert("B","A","D","C");
		System.out.println(T1.toString());
		
		Assert.assertEquals("A", T1.findMin());
		Assert.assertEquals("D", T1.findMax());
		
		T1.withdraw("A","B","C","D");
		
		Assert.assertTrue(T1.isEmpty());		
	}
	
	@Test
	public void testWithdraw() {
		
		BinarySearchTree<Integer> T1 = new BinarySearchTree<Integer>();
		
		T1.insert(10, 5, 8, 3);

		
		//	10 es raíz
		Assert.assertEquals("{10, {5, {3, ∅, ∅}, {8, ∅, ∅}}, ∅}", T1.toString());
		System.out.println(T1.toString());
		
		T1.withdraw(10);
		Assert.assertEquals("{5, {3, ∅, ∅}, {8, ∅, ∅}}", T1.toString());
		System.out.println(T1.toString());
		
	}	
	
	@Test
	public void testWithdrawRoot() {
		
		BinarySearchTree<String> T1 = new BinarySearchTree<String>();
		
		T1.insert("C", "D", "A", "B");
		
		//	"C" es raíz
		Assert.assertEquals("{C, {A, ∅, {B, ∅, ∅}}, {D, ∅, ∅}}", T1.toString());
		System.out.println(T1.toString());
		
		T1.withdraw("C");
		
		//	Ahora "B" es raíz, porque era el menor del sub-árbol izquierdo
		Assert.assertEquals("{B, {A, ∅, ∅}, {D, ∅, ∅}}", T1.toString());
		System.out.println(T1.toString());
		
		//	Vacía T1
		T1.withdraw("B", "D", "A");
		System.out.println(T1.toString());
		
		//	Casos especiales; único nodo
		T1.insert("S");
		System.out.println(T1.toString());
		
		Assert.assertEquals("{S, ∅, ∅}", T1.toString());
		
		
		//	Vacía T1
		T1.withdraw("S");
		
		Assert.assertEquals("∅", T1.toString());
		
		T1.insert("A", "B", "C");
		
		//	"A" es raíz, es un caso pésimo
		Assert.assertEquals("{A, ∅, {B, ∅, {C, ∅, ∅}}}", T1.toString());
		
		T1.withdraw("A");
		
		//	Ahora "B" es raíz, porque era el menor del sub-árbol izquierdo
		Assert.assertEquals("{B, ∅, {C, ∅, ∅}}", T1.toString());
		
	}
}
