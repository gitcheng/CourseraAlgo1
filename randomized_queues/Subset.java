/********************************************************
Coursera/Algorithm I Programming Assignment 2: Deque/RandomizedQueue
Client class.
http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 ********************************************************/
import java.util.Iterator;

public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Need exactly one argument");
        }
        int k = Integer.parseInt(args[0]);
        if (k < 0) throw new IllegalArgumentException("Argument must be >=0");

        // Create a RandomizedQueue
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
            //StdOut.println(s);
        }

        Iterator<String> i = q.iterator();
        while (i.hasNext() && k > 0) {
            String s = i.next();
            StdOut.println(s);
            k--;
        }

    }

}
