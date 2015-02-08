package ed.x07;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dorian
 */
public class HeapTreeTest {
    HeapTree<Integer> heap;
    HeapTree<Integer> heapEmpty;
    
    @Before
    public void setUp() {
        heap=new HeapTree<>(0);
        for (int i = 11; i >0; i--) {
            heap.addElement(i);
        }
        heapEmpty=new HeapTree<>();
    }
    
    @Test
    public void addCompleteTest(){
        for (int i = 0; i <10; i++) {
            heapEmpty.addElement(i);
        }
        assertEquals("{0, {1, {3, {7, ∅, ∅}, {8, ∅, ∅}}, {4, {9, ∅, ∅}, ∅}},"
                + " {2, {5, ∅, ∅}, {6, ∅, ∅}}}", heapEmpty.toString());
    }
    
    @Test
    public void addOrderTest(){
        heapEmpty=new HeapTree<>(0);
        for (int i = 21; i >0; i--) {
            heapEmpty.addElement(i);
        }
        assertEquals("{0, {1, {5, {12, {21, ∅, ∅}, {15, ∅, ∅}}, {6, {18, ∅, ∅}, "
                + "{7, ∅, ∅}}}, {2, {4, {19, ∅, ∅}, {13, ∅, ∅}}, {3, {14, ∅, ∅}, ∅}}},"
                + " {8, {11, {20, ∅, ∅}, {16, ∅, ∅}}, {9, {17, ∅, ∅}, {10, ∅, ∅}}}}", heapEmpty.toString());
    }
    
    @Test (expected = EmptyTreeException.class)
    public void removeMinEmptyTest(){
        heapEmpty.removeMin();
    }
    
    @Test
    public void removeMinTest(){
        assertEquals((Integer)0, heap.removeMin());
        assertEquals((Integer)1, heap.removeMin());
        assertEquals("{2, {3, {5, {11, ∅, ∅}, {8, ∅, ∅}}, {4, {9, ∅, ∅}, ∅}}, {6, {10, ∅, ∅}, {7, ∅, ∅}}}", heap.toString());
        assertEquals((Integer)2, heap.removeMin());
        assertEquals("{3, {4, {5, {11, ∅, ∅}, {8, ∅, ∅}}, {9, ∅, ∅}}, {6, {10, ∅, ∅}, {7, ∅, ∅}}}", heap.toString());
        assertEquals((Integer)3, heap.removeMin());
        assertEquals("{4, {5, {8, {11, ∅, ∅}, ∅}, {9, ∅, ∅}}, {6, {10, ∅, ∅}, {7, ∅, ∅}}}", heap.toString());
        assertEquals((Integer)4, heap.removeMin());
        assertEquals("{5, {8, {11, ∅, ∅}, {9, ∅, ∅}}, {6, {10, ∅, ∅}, {7, ∅, ∅}}}", heap.toString());
        assertEquals((Integer)5, heap.removeMin());
        assertEquals("{6, {8, {11, ∅, ∅}, {9, ∅, ∅}}, {7, {10, ∅, ∅}, ∅}}", heap.toString());
        assertEquals((Integer)6, heap.removeMin());
        assertEquals("{7, {8, {11, ∅, ∅}, {9, ∅, ∅}}, {10, ∅, ∅}}", heap.toString());
        assertEquals((Integer)7, heap.removeMin());
        assertEquals("{8, {9, {11, ∅, ∅}, ∅}, {10, ∅, ∅}}", heap.toString());
        assertEquals((Integer)8, heap.removeMin());
        assertEquals("{9, {11, ∅, ∅}, {10, ∅, ∅}}", heap.toString());
        assertEquals((Integer)9, heap.removeMin());
        assertEquals("{10, {11, ∅, ∅}, ∅}", heap.toString());
        assertEquals((Integer)10, heap.removeMin());
        assertEquals("{11, ∅, ∅}", heap.toString());
        assertEquals((Integer)11, heap.removeMin());
        assertEquals("∅", heap.toString());
    }
  
    @Test
    public void findMinTest(){
        assertEquals((Integer)0, heap.findMin());
        assertEquals((Integer)0, heap.removeMin());
        assertEquals((Integer)1, heap.removeMin());
        assertEquals((Integer)2, heap.findMin());
    }
    
    @Test (expected = EmptyTreeException.class)
    public void findMinEmptyTest(){
        heapEmpty.findMin();
    }
}