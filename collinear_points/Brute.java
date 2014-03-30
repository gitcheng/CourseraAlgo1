// Brute force way of finding collinear points
//----------------------------------------------------------------
import java.util.Arrays;

public class Brute {

    private static Point[] p4 = new Point[4];

    private static boolean collinear(Point p, Point q, Point r, Point s) {
        if (p.slopeTo(q) != p.slopeTo(r)) return false;
        if (p.slopeTo(q) != p.slopeTo(s)) return false;
        return true;
    }

     private static void draw_points(Point[] a) {
	for (int i = 0; i < a.length; i++)
	    a[i].draw();
    }

    private static void print_draw(Point p, Point q, Point r, Point s) {
        p4[0] = p;
        p4[1] = q;
        p4[2] = r;
        p4[3] = s;
        // sort
        Arrays.sort(p4);

        for (int i = 0; i < 4; i++) {
            if (i > 0)
                StdOut.print(" -> ");
            StdOut.print(p4[i].toString());
        }
        StdOut.println();
        p4[0].drawTo(p4[3]);

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

        // Loop over all combinations of 4 points
        for (int i = 0; i < parr.length-3; i++) {
            for (int j = i+1; j < parr.length-2; j++) {
                for (int k = j+1; k < parr.length-1; k++) {
                    for (int n = k+1; n < parr.length; n++) {
                        if (collinear(parr[i],parr[j],parr[k],parr[n]))
                            print_draw(parr[i],parr[j],parr[k],parr[n]);
                    }
                }
            }
        }
        StdDraw.show(0);
    }

}
