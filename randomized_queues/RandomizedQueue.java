/*****************************************************************
Coursera/Algorithm I Programming Assignment 2: Randomized Queue
http://coursera.cs.princeton.edu/algs4/assignments/queues.html
******************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // Use resizable array
    private Item[] a;
    private int N;

    // Initialize an empty queue
    public RandomizedQueue() { // construct an empty randomized queue
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() { // is the queue empty?
        return N == 0;
    }

    public int size() { // return the number of items on the queue
        return N;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) throw new NullPointerException();
        if (N == a.length) resize(2*a.length);
        a[N++] = item;
    }

    public Item dequeue() { // delete and return a random item
        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        int k = StdRandom.uniform(N);
        Item item = a[k];
        if (k < N-1) {
            // copy the last item to this hole
            a[k] = a[N-1];
        }
        a[N-1] = null;
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;    
    }

    public Item sample() {  // return (but do not delete) a random item
        if (isEmpty()) throw new NoSuchElementException("queue underflow");
        return a[StdRandom.uniform(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int i;
        private int[] idx;

        public RandomizedIterator() {
            i = N;
            idx = new int[N];
            for (int j = 0; j < N; j++) {
                idx[j] = j;
            }
            StdRandom.shuffle(idx);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[idx[--i]];
        }
    }

    public static void main(String[] args) {  // unit testing

        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        StdOut.print("Unit test. Create a RandomizedQueue and enqueue");
        StdOut.println("some elements.");
        // Add something
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }
        StdOut.println("Randomized iterator");
        for (int i: q) StdOut.print(i+" ");
        StdOut.println("  size= " + q.size());
        StdOut.println("Randomized iterator again");
        for (int i: q) StdOut.print(i+" ");
        StdOut.println("  size= " + q.size());

        StdOut.println("Sample a few");
        for (int i = 0; i < 4; i++) 
            StdOut.println(q.sample());
        StdOut.println("Look at the queue again");
        for (int i: q) StdOut.print(i+" ");
        StdOut.println("  size= " + q.size());

        StdOut.println("Deque a few");
        for (int i = 0; i < 4; i++) 
            StdOut.println(q.dequeue());
        StdOut.println("Look at the queue again");
        for (int i: q) StdOut.print(i+" ");
        StdOut.println("  size= " + q.size());
    }
}
