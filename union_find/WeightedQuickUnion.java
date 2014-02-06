public class WeightedQuickUnion
{
    private int[] id;
    private int[] sz;

    public WeightedQuickUnion(int N) {
        id = new int[N];
	sz = new int[N];
	// Initialization
        for (int i=0; i<N; i++) {
	    id[i]= i;
	    sz[i]= 1;
	}
    }
    
    private int root(int i) {
	while ( i != id[i] ) i= id[i];
	return i;
    }

    public boolean connected(int p, int q) { 
	return root(p) == root(q); 
    }
    
    public void union(int p, int q) {
	int i= root(p);
	int j= root(q);
	if ( sz[i] < sz[j] ) {
	    id[i]= j;
	    sz[j]+= sz[i];
	} else {
	    id[j]= i;
	    sz[i]+= sz[j];
	}
    }
    
    public void showid() {
	for (int i=0; i< id.length; i++) {
	    StdOut.print(id[i]+" ");
	}
	StdOut.println();
    }
    
    public static void main(String[] args) {
        int N = StdIn.readInt();
        WeightedQuickUnion uf = new WeightedQuickUnion(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p,q)) {
                uf.union(p,q);
                StdOut.println("connecting  " + p + " " + q);
		uf.showid();
            } else {
		StdOut.println("already connected  " + p + " " + q);
	    }
        }
	uf.showid();
    }
}


    
