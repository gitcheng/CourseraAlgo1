// Test time to build a 2d tree using KdTree on N random points, and
// test time to search for nearest points.
//------------------------------------------------------------------------
import java.util.TreeSet;

public class TimeTest {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
	int max = 10000000;
	if (args.length > 1)
	    max = Integer.parseInt(args[1]);

	TreeSet<Point2D> points = new TreeSet<Point2D>();
	while (!in.isEmpty() && max > 0) {
            double x = in.readDouble();
            double y = in.readDouble();
            points.add(new Point2D(x, y));
	    max--;
        }

        KdTree kdtree = new KdTree();
	PointSET brute = new PointSET();

	// Test time of building a KdTree
	Stopwatch time = new Stopwatch();
	for (Point2D p : points)
            kdtree.insert(p);
	double t1 = time.elapsedTime();
	StdOut.println("Build 2d-tree : " + t1 + " s" );
	// Brute force set
	for (Point2D p : points)
            brute.insert(p);
	double t2 = time.elapsedTime();
	StdOut.println("Fill a set : " + (t2-t1) + " s" );

	StdOut.println("Query nearest points using KdTree");
	double t3 = time.elapsedTime();
	int nq = 0;
	while (true) {
	    double x = StdRandom.uniform();
	    double y = StdRandom.uniform();
	    Point2D p = new Point2D(x, y);
	    Point2D b = kdtree.nearest(p);
	    nq++;
	    if (nq % 10000 == 0) {
		double elt = time.elapsedTime() - t3;
		StdOut.println("Queried " + nq + " points. Time = " + elt);
		if (elt > 1.0) break;
	    }
	}

	StdOut.println("Query nearest points using brute force");
	double t4 = time.elapsedTime();
	nq = 0;
	while (true) {
	    double x = StdRandom.uniform();
	    double y = StdRandom.uniform();
	    Point2D p = new Point2D(x, y);
	    Point2D b = brute.nearest(p);
	    nq++;
	    if (nq % 1000 == 0) {
		double elt = time.elapsedTime() - t4;
		StdOut.println("Queried " + nq + " points. Time = " + elt);
		if (elt > 1.0) break;
	    }
	}


    }

}
