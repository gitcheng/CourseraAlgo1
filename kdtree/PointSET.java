// Programming Assignment for Coursera.org Algorithm I week 5: Kd-Trees.
// Data type PointSET.java that will be used in brute-force implementation
// for geometric queries of points and rectangles.
// http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
//----------------------------------------------------------------------
import java.util.TreeSet;

public class  PointSET {

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return points.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : points)
            p.draw();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> inrange = new TreeSet<Point2D>();
        for (Point2D p : points)
            if (rect.contains(p))
                inrange.add(p);
        return inrange;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        double min = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D q : points) {
            if (p.distanceTo(q) < min) {
                nearest = q;
                min = p.distanceTo(q);
            }
        }
        return nearest;
    }

    // unit test: read in coordinates from a file and draw points
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show();

        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        StdDraw.setPenRadius(0.01);
        brute.draw();

        // Generate a few random points and find the nearest to each.
        for (int i = 0; i < 6; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D query = new Point2D(x, y);

            StdOut.println("Nearst to " + query.toString() + " is ");
            Point2D p = brute.nearest(query);
            StdOut.println(p.toString());
            
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), p.y(), query.x(), query.y());

            StdDraw.setPenRadius(0.01);
            query.draw();
            StdDraw.show(1000);
        }
    }

}
