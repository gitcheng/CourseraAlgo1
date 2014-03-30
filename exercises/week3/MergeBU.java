public class MergeBU {

    private static boolean less(Comparable v, Comparable w) {
	return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
	for (int i = lo; i < a.length && i <= hi; i++) {
	    if (less(a[i], a[i-1])) return false; 
	}
	return true;
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
	assert isSorted(a, lo, mid);    // precondition: a[lo..mid]   sorted
	assert isSorted(a, mid+1, hi);  // precondition: a[mid+1..hi] sorted
	for (int k = lo; k <= hi; k++)
	    aux[k] = a[k];    // copy
	int i = lo, j = mid+1;
	for (int k = lo; k <= hi; k++) {
	    if      (i > mid)              a[k] = aux[j++];
	    else if (j > hi)               a[k] = aux[i++];
	    else if (less(aux[j], aux[i])) a[k] = aux[j++];
	    else                           a[k] = aux[i++];
	}
	assert isSorted(a, lo, hi);
    }

    public static void sort(Comparable[] a) {
	int N = a.length;
	Comparable[] aux = new Comparable[N];
	for (int sz = 1; sz < N; sz = sz+sz)
	    for (int lo = 0; lo < N-sz; lo += sz+sz) {
		merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
		printarray(a);
	    }
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
