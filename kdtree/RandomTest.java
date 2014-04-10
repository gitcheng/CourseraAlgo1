public class RandomTest {

    public static void main(String[] args) {

	KdTree kdtree = new KdTree();
	PointSET brute = new PointSET();
	for (int j = 0; j < 100000; j++) {
	    double x = StdRandom.uniform(10)/10.0;
	    double y = StdRandom.uniform(10)/10.0;
	    Point2D p = new Point2D(x, y);
	    //StdOut.println(p.toString());
	    kdtree.insert(p);
	    brute.insert(p);
	    if (kdtree.size() != brute.size())
		StdOut.println("Failed at trial " + j);
}
    }
}
