/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // Comparator by slope
    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double sv = slopeTo(v);
            double sw = slopeTo(w);
            if (sv < sw) return -1;
            if (sv > sw) return +1;
            return 0;
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // Same point: -infinity
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        // Horizontal line: +0
        if (this.y == that.y)
            return +0.0;
        // Vertical line: +infinity
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;

        return 1.0 * (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        // Compare y
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return +1;
        else { // y is tied, compare x
            if (this.x < that.x)
                return -1;
            else if (this.x > that.x)
                return +1;
        }
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p = new Point(1, 2);
        Point o = new Point(0, 0);
        StdOut.println(o.toString() + "->" + p.toString());
        StdOut.println("slope= " + o.slopeTo(p));

        Point p2 = new Point(1, 2);
        Point q2 = new Point(1, 6);
        StdOut.println(q2.toString() + "->" + p2.toString());
        StdOut.println("slope= " + q2.slopeTo(p2));

        Point p3 = new Point(1000, 18000);
        Point q3 = new Point(23000, 16000);
        StdOut.println(q3.toString() + "->" + p3.toString());
        StdOut.println("slope= " + q3.slopeTo(p3));
    }
}
