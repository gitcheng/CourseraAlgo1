// Fast way of finding collinear points
//----------------------------------------------------------------
import java.util.Arrays;

public class Fast {

    private static void printPoints(Point[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0)
                StdOut.print(" -> ");
            StdOut.print(a[i].toString());
        }
        StdOut.println();
    }

    private static void drawPoints(Point[] a) {
        for (int i = 0; i < a.length; i++)
            a[i].draw();
    }

    private static void drawCollinear(Point[] a) {
        a[0].drawTo(a[a.length-1]);
    }

    private static void findCollinear(Point[] a) {
        Point pivot = a[0];
        int j = 1;
        int k = 1;
        while (k < a.length) {
            if (pivot.slopeTo(a[j]) != pivot.slopeTo(a[k])) {
                j++;
                k++;
            } else {
                while (pivot.slopeTo(a[j]) == pivot.slopeTo(a[k])) {
                    k++;
                    if (k >= a.length) break;
                }
            }
            // Now a[j] to a[k-1] are the same
            if (k-j >= 3) {
                Point[] p4 = new Point[k-j+1];
                p4[0] = pivot;
                for (int m = j; m < k; m++) {
                    p4[m-j+1] = a[m];
                }
                // Sort points
                Arrays.sort(p4);
                // Print and draw only if the smallest Point is the pivot
                if (pivot.slopeTo(p4[0]) == Double.NEGATIVE_INFINITY) {
                    printPoints(p4);
                    drawCollinear(p4);
                }
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
        Point[] aux = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            parr[i] = p;
            aux[i] = p;
        }
        // Draw all points
        drawPoints(parr);
        
        for (int i = 0; i < N; i++) {
            // Sort by slope
            Point pivot = parr[i];
            Arrays.sort(aux, pivot.SLOPE_ORDER);
            // pivot will be sorted to the very first because slopeTo is -inf
            assert pivot.slopeTo(aux[0]) == Double.NEGATIVE_INFINITY;
            findCollinear(aux);
        }
        StdDraw.show(0);
    }

}
