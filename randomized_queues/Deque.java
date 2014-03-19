/********************************************************
Coursera/Algorithm I Programming Assignment 2: Deque
http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 ********************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int N;    // size of the deque
    // first and last nodes
    private Node first;
    private Node last;
    
    // doubly linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // Initialize an empty deque
    public Deque() {             // construct an empty deque
        first = null;
        last = null;
        N = 0;
        assert check();
    }

    public boolean isEmpty() {    // is the deque empty?
        return first == null;
    }

    public int size() {      // return the number of items on the deque
        return N;
    }

    public void addFirst(Item item) {      // insert the item at the front
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst == null) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
        N++;
        assert check();
    }

    public void addLast(Item item) {   // insert the item at the end
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (oldlast == null) {
            first = last;
        } else {
            oldlast.next = last;
        }
        N++;
        assert check();
    }

    public Item removeFirst() {  // delete and return the item at the front
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;    // save item to return
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        N--;
        assert check();
        return item;
    }

    public Item removeLast() {  // delete and return the item at the end
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;    // save item to return
        last = last.prev;
        if (last == null) { // There is only one item.
            first = null;
        } else {
            last.next = null;
        }
        N--;
        assert check();
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null; }
        public void remove()   { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null || last != null) return false;
        }
        else if (N == 1) {
            if (first != last) return false;
            if (first == null) return false;
            if (first.next != null) return false;
            if (first.prev != null) return false;
        }
        else {
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.prev == null) return false;
        }
        if (first.prev != null) return false;
        if (last.next != null) return false;
        
        return true;
    }


    // unit testing
    public static void main(String[] args) {
        Deque<String> s = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.addFirst(item);
            else if (!s.isEmpty()) StdOut.print(s.removeLast() + " ");
        }
        StdOut.println("(" + s.size() + " left in queue)");
    }
}

