package ed.x07;

/**
 *
 * @author dorian
 */
public interface Heap<T extends Comparable> {
    /**
     * Add the element to a heap
     * @param element 
     */
    public void addElement(T element);
    /**
     * Remove the minimum element of the heap
     * @return 
     */
    public T removeMin();
    /**
     * Find the minimum element of the heap
     * @return 
     */
    public T findMin();
}
