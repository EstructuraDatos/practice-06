package ed.x07;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dorian
 */
public class ejerciciosBST1Test {
    private BinarySearchTree<Integer> arbol;
    private BinarySearchTree<Integer> arbolVacio;
    
    @Before
    public void setUp() {
        arbolVacio=new BinarySearchTree<>();
        arbol=new BinarySearchTree<>();
        arbol.insert(3, 2, 4, 1);
    }
    
    @Test
    public void deleteMax(){
        assertEquals("{3, {2, {1, ∅, ∅}, ∅}, {4, ∅, ∅}}", arbol.toString());
        Integer result=arbol.deleteMax();
        assertEquals(4, (int)result);
        assertEquals("{3, {2, {1, ∅, ∅}, ∅}, ∅}", arbol.toString());
        result=arbol.deleteMax();
        assertEquals(3, (int)result);
        assertEquals("{2, {1, ∅, ∅}, ∅}", arbol.toString());
        result=arbol.deleteMax();
        assertEquals(2, (int)result);
        assertEquals("{1, ∅, ∅}", arbol.toString());
        result=arbol.deleteMax();
        assertEquals(1, (int)result);
        assertEquals("∅", arbol.toString());
    }
    
    @Test
    (expected = EmptyTreeException.class)
    public void errorDeleteMax(){
        arbol.deleteMax();
        arbol.deleteMax();
        arbol.deleteMax();
        arbol.deleteMax();
        arbol.deleteMax();
    }
    
    @Test
    (expected = EmptyTreeException.class)
    public void errorDeleteMin(){
        arbol.deleteMin();
        arbol.deleteMin();
        arbol.deleteMin();
        arbol.deleteMin();
        arbol.deleteMin();
    }
    
    @Test
    public void deleteMin(){
        assertEquals("{3, {2, {1, ∅, ∅}, ∅}, {4, ∅, ∅}}", arbol.toString());
        int result=arbol.deleteMin();
        assertEquals(1, result);
        assertEquals("{3, {2, ∅, ∅}, {4, ∅, ∅}}", arbol.toString());
        result=arbol.deleteMin();
        assertEquals(2, result);
        assertEquals("{3, ∅, {4, ∅, ∅}}", arbol.toString());
    }
    
    @Test
    public void smallerCount(){
        assertEquals(0, arbol.smallerCount(0));
        assertEquals(0, arbol.smallerCount(1));
        assertEquals(1, arbol.smallerCount(2));
        assertEquals(4, arbol.smallerCount(15));
        assertEquals(0, arbolVacio.smallerCount(15));
    }
    
    @Test
    public void olderCount(){
        assertEquals(0, arbol.olderCount(15));
        assertEquals(0, arbol.olderCount(4));
        assertEquals(1, arbol.olderCount(3));
        assertEquals(4, arbol.olderCount(0));
        assertEquals(0, arbolVacio.olderCount(0));
    }
    
    @Test
    public void height(){
        assertEquals(3, arbol.height());
        arbol.insert(5,6);
        assertEquals(4, arbol.height());
        assertEquals(0, arbolVacio.height());
    }

    @Test
    public void isEquals(){
        assertFalse(arbol.isEquals(arbolVacio));
        assertTrue(arbol.isEquals(arbol));
        BinarySearchTree<Integer> arbol2=new BinarySearchTree<>();
        arbol2.insert(3, 2, 4, 1);
        arbol2.insert(5);
        assertFalse(arbol.isEquals(arbol2));
    }

    @Test
    public void countElemsLevel(){
        assertEquals(0, arbolVacio.countElemsLevel(0));
        assertEquals(0, arbolVacio.countElemsLevel(1));
        assertEquals(0, arbolVacio.countElemsLevel(2));
        
        assertEquals(0, arbol.countElemsLevel(0));
        assertEquals(1, arbol.countElemsLevel(1));
        assertEquals(2, arbol.countElemsLevel(2));
        assertEquals(1, arbol.countElemsLevel(3));
        arbol.insert(5);
        assertEquals(2, arbol.countElemsLevel(3));
    }
}