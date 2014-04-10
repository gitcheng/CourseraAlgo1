public class NullTest {

    public static void main(String[] args) {

	KdTree kdtree = new KdTree();
	StdOut.println("isEmpty() " + kdtree.isEmpty());
	StdOut.println("size() " + kdtree.size());
	StdOut.println("contains() " + kdtree.contains(new Point2D(0.5, 0.5)));
	// StdOut.println("range() " + kdtree.());
	Point2D np = kdtree.nearest(new Point2D(0.5, 0.5));
	if (np == null) 
	    StdOut.println("nearest: set is empty");
	else
	    StdOut.println("nearest() " + np.toString());
    }
}
