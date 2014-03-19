// Client class for testing Deque
//-------------------------------------------------------------
public class TestDeque {

    private static void printAndSize(Deque<Integer> dqs) {
	StdOut.println("deque has " + dqs.size() + " entries");
	// Iterator
	for (Integer i: dqs) StdOut.println(i);
	StdOut.println("deque has " + dqs.size() + " entries");
    }

    public static void main(String[] args) {
	//StdOut.println(args.length);
	//StdOut.println(args[0]);

	// Constructor
	Deque<Integer> dqs = new Deque<Integer>();

	// addFirst
	StdOut.println("Test addFirst");
	for (int i = 0; i < 5; i++) {
	    dqs.addFirst(i);
	}
	printAndSize(dqs);

	// addLast
	StdOut.println("\nTest addLast");
	for (int i = 0; i < 5; i++) {
	    dqs.addLast(i);
	}
	printAndSize(dqs);

	// addLast
	StdOut.println("\nTest addLast");
	Deque<Integer> dqs2= new Deque<Integer>();
	for (int i = 0; i < 5; i++) {
	    dqs2.addLast(i);
	}
	printAndSize(dqs2);

	// addLast
	StdOut.println("\nTest addFirst");
	for (int i = 10; i < 14; i++) {
	    dqs2.addFirst(i);
	}
	printAndSize(dqs2);

	// removeFirst
	StdOut.println("\nTest removeFirst");
	for (int i = 0; i < 3; i++) {
	    int k= dqs2.removeFirst();
	    StdOut.println(k);
	}
	printAndSize(dqs2);
	// removeLast
	StdOut.println("\nTest removeLast");
	for (int i = 0; i < 3; i++) {
	    int k= dqs2.removeLast();
	    StdOut.println(k);
	}
	printAndSize(dqs2);

	// exceptions
	//dqs2.addFirst(null);

	StdOut.println("\nTest addFirst then removeLast");
	Deque<Integer> dqs3= new Deque<Integer>();
	dqs3.addFirst(5);
	int k = dqs3.removeLast();
	printAndSize(dqs3);
	StdOut.println("isEmpty is " + dqs3.isEmpty());

	StdOut.println("\nTest addLast then removeFirst");
	dqs3.addLast(9);
        k = dqs3.removeFirst();
	printAndSize(dqs3);
	StdOut.println("isEmpty is " + dqs3.isEmpty());

	StdOut.println("\nTest addLast then removeLast");
	dqs3.addLast(9);
        k = dqs3.removeLast();
	printAndSize(dqs3);
	StdOut.println("isEmpty is " + dqs3.isEmpty());

	StdOut.println("\nTest addFirst then removeFirst");
	dqs3.addFirst(9);
        k = dqs3.removeFirst();
	printAndSize(dqs3);
	StdOut.println("isEmpty is " + dqs3.isEmpty());


    }
}

