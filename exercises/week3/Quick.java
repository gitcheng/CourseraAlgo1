public class Quick {

    private static boolean less(Comparable v, Comparable w) {
	return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
	Comparable swap = a[i];
	a[i] = a[j];
	a[j] = swap;
    }

    private static int partition(Comparable[] a, int lo, int hi) {
	int i = lo, j = hi+1;
	while (true) {
	    while (less(a[++i], a[lo]))
		if (i == hi) break;

	    while (less(a[lo], a[--j]))
		if (j == lo) break;

	    if (i >= j) break;
	    exch(a, i, j);
	}
	exch(a, lo, j);
	return j;
    }


    private static void sort(Comparable[] a, int lo, int hi) {
	if (hi <= lo) return;
	int j = partition(a, lo, hi);
	printarray(a);
	sort(a, lo, j-1);
	sort(a, j+1, hi);
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
