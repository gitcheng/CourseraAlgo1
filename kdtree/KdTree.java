// Programming Assignment for Coursera.org Algorithm I week 5: Kd-Trees.
// Data type KdTree.java that will be used in kd-tree implementation
// for geometric queries of points and rectangles.
// http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
//----------------------------------------------------------------------
public class KdTree {

    private Node root;
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private static class Node {
	private Point2D p;
	private RectHV rect;
	private Node lb;
	private Node rt;
	private boolean orient;
	private int N;

	public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean vh, int N) {
	    this.p = p;
	    this.rect = rect;
	    this.lb = lb;
	    this.rt = rt;
	    this.orient = vh;
	    this.N = N;
	}
    }


    // constructor
    public KdTree() {
	root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
	return size() == 0;
    }
    
    // number of points in the set
    public int size() {
	if (root == null)
	    return 0;
	else 
	    return root.N;
    }
    
    // Left-bottom rectangle
    private RectHV lbrect(RectHV rect, Point2D p, boolean orient) {
	if (orient == VERTICAL)
	    return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
	else
	    return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
    }
    // Right-top rectangle
    private RectHV rtrect(RectHV rect, Point2D p, boolean orient) {
	if (orient == VERTICAL)
	    return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
	else
	    return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
    }


    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
	if (p.x() < 0 || p.x() > 1 || p.y() < 0 || p.y() > 1)
	    throw new IllegalArgumentException("Invalid cooridnate");

	// create a skeleton for root if it is null
	if (root == null) {
	    RectHV r0 = new RectHV(0, 0, 1, 1);
	    root = new Node(null, r0, null, null, VERTICAL, 0);
	}

	root = insert(root, p);
    }


    private Node insert(Node q, Point2D p) {

	assert q != null;
	assert q.rect != null;

	if (q.lb == null) {
	    assert q.rt == null;
	    // add the point
	    q.p = p;
	    // add the partitions
	    RectHV rlb = lbrect(q.rect, p, q.orient);
	    RectHV rrt = rtrect(q.rect, p, q.orient);
	    q.lb = new Node(null, rlb, null, null, !q.orient, 0);
	    q.rt = new Node(null, rrt, null, null, !q.orient, 0);
	    q.N = 1;
	    return q;
	}

	assert q.rt != null;
	if (q.lb.rect.contains(p))
	    q.lb = insert(q.lb, p);
	else if (q.rt.rect.contains(p))
	    q.rt = insert(q.rt, p);
	else
	    throw new IllegalArgumentException("node does not contain p");

	q.N = q.lb.N + q.rt.N + 1;
	return q;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
    }
    
    public void draw()                              // draw all of the points to standard draw
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
}
