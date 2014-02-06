// Coursera/Algorithm I Programming Assignment 1: Percolation
// http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
public class Percolation {
    // Data members. 
    private boolean[] openSites;
    private int[] bottomSites;
    private int nD;
    // The union find algorithm
    private WeightedQuickUnionUF uf;

    // Constructor
    public Percolation(int N)  {
        // Initialize data members
        nD = N;
        openSites = new boolean[N*N+1];  // Including a virtual sites on top
        bottomSites = new int[N*N+1];   // Each node is a possible root.
        for (int i = 0; i < openSites.length; i++) {
            openSites[i] = false;
            bottomSites[i] = i;
        }
        // Open the virtual site
        openSites[0] = true;

        uf = new WeightedQuickUnionUF(openSites.length);
    }

    // Translate i,j to UF 1-d index. Remember the 0-th index is the top
    // virtual site, and the last index is the bottom one.
    private int xyTo1D(int i, int j) {
        return (i-1)*nD + j;
    }
    // Check i,j range. If out of bound, throw an exception
    private void checkRange(int i, int j, String name) {
        if (i < 1 || i > nD || j < 1 || j > nD)
            throw new IndexOutOfBoundsException(name+"() :indices "+i+","+j);
    }
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        checkRange(i, j, "open");
        if (!isOpen(i, j)) {  // If it is not already open.
            int k = xyTo1D(i, j);
            openSites[k] = true;        // Open it up.

            int b = k;

            // If the site is on the top row, connect it to the top virtual site
            if (i == 1) {
                b = Math.max(b, bottomSites[uf.find(0)]);
                uf.union(0, k);
            }
            // Connect to the neighbors
            if (i > 1  && isOpen(i-1, j)) {
                b = Math.max(b, bottomSites[uf.find(xyTo1D(i-1, j))]);
                uf.union(k, xyTo1D(i-1, j));
            }
            if (i < nD && isOpen(i+1, j)) {
                b = Math.max(b, bottomSites[uf.find(xyTo1D(i+1, j))]);
                uf.union(k, xyTo1D(i+1, j));
            }
            if (j > 1  && isOpen(i, j-1)) {
                b = Math.max(b, bottomSites[uf.find(xyTo1D(i, j-1))]);
                uf.union(k, xyTo1D(i, j-1));
            }
            if (j < nD && isOpen(i, j+1)) {
                b = Math.max(b, bottomSites[uf.find(xyTo1D(i, j+1))]);
                uf.union(k, xyTo1D(i, j+1));
            }
            // Find the new root
            int root = uf.find(k);
            bottomSites[root] = b; // bottomest node of this root.
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)  {
        checkRange(i, j, "isOpen");
        return openSites[xyTo1D(i, j)];
    }
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)  {
        checkRange(i, j, "isFull");
        if (!isOpen(i, j))
            return false;
        return uf.connected(0, xyTo1D(i, j));
    }

    // does the system percolate?
    public boolean percolates()  {
        // If the bottom of the tree with top site's root is at the last
        //  row, then percolates.
        return bottomSites[uf.find(0)] >= xyTo1D(nD, 1);
    }
}

