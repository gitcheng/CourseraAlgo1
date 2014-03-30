// Fast way of finding collinear points
//----------------------------------------------------------------
import java.util.Arrays;

public class Fast {

    private static void print_colinear(Point[] a) {
	Arrays.sort(a);
	for (int i = 0; i < a.length; i++) {
	    if (i > 0)
                StdOut.print(" -> ");
            StdOut.print(a[i].toString());
        }
        StdOut.println();
    }

    private static void print_points(Point[] a) {
	for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i].toString());
        }
        StdOut.println();
    }

    private static void print_slopes(Point[] a, int j) {
	for (int i = 0; i < a.length; i++) {
            StdOut.print(a[j].slopeTo(a[i]) + " ");
        }
        StdOut.println();
    }

    private static void draw_points(Point[] a) {
	for (int i = 0; i < a.length; i++)
	    a[i].draw();
    }

    private static void draw_collinear(Point[] a) {
        a[0].drawTo(a[a.length-1]);
    }

    private static void find_collinear(Point[] a, int i) {
	int j = i+1;
	int k = j;
	while (k < a.length) {
	    if (a[i].slopeTo(a[j]) != a[i].slopeTo(a[k])) {
		j++;
		k++;
	    } else {
		while (a[i].slopeTo(a[j]) == a[i].slopeTo(a[k])) {
		    k++;
		    if (k >= a.length) break;
		}
	    }
	    // Now a[j] to a[k-1] are the same
	    if (k-j >= 3) {
		Point[] p4 = new Point[k-j+1];
		p4[0] = a[i];
		for (int m = j; m < k; m++) {
		    p4[m-j+1] = a[m];
		}
		// Print and draw
		print_colinear(p4);
		draw_collinear(p4);
	    }
	    j = k-1;
	}
    }

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] parr = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            parr[i] = p;
        }

	draw_points(parr);
	
	for (int i = 0; i < N-3; i++) {
	    // Sort from i+1 to N-1 by slope to i-th point
	    Arrays.sort(parr, i+1, N, parr[i].SLOPE_ORDER);
	    find_collinear(parr, i);
	}
        StdDraw.show(0);
    }

}
