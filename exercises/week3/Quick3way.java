public class Quick3way {

    private static boolean less(Comparable v, Comparable w) {
	return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
	Comparable swap = a[i];
	a[i] = a[j];
	a[j] = swap;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
	if (hi <= lo) return;
	int lt = lo, gt = hi;
	Comparable v = a[lo];
	int i = lo;
	while (i <= gt) {
	    int cmp = a[i].compareTo(v);
	    if      (cmp < 0) exch(a, lt++, i++);
	    else if (cmp > 0) exch(a, i, gt--);
	    else              i++;
	}
	printarray(a);

	sort(a, lo, lt-1);
	sort(a, gt+1, hi);
    }

    public static void sort(Comparable[] a) {
	sort(a, 0, a.length - 1);
    }


    private static void printarray(Comparable[] a) {
	for (int i = 0; i < a.length; i++) {
	    StdOut.print(a[i]+" ");
	}
	StdOut.println();
    }

    // -----unit test-------------
    public static void main(String[] args) {
	int N = args.length;
	Integer[] a = new Integer[N];
	for (int i = 0; i < N; i++) {
	    a[i] = Integer.parseInt(args[i]);
	}

	sort(a);
	printarray(a);

    }

}
