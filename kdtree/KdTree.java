// Programming Assignment for Coursera.org Algorithm I week 5: Kd-Trees.
// Data type KdTree.java that will be used in kd-tree implementation
// for geometric queries of points and rectangles.
// http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
//----------------------------------------------------------------------
import java.util.TreeSet;

public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;

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

    // Helper insert function
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
        if (q.orient == VERTICAL) {
            if (p.x() < q.lb.rect.xmax())
                q.lb = insert(q.lb, p);
            else
                q.rt = insert(q.rt, p);
        } else { // HORIZONTAL
            if (p.y() < q.lb.rect.ymax())
                q.lb = insert(q.lb, p);
            else
                q.rt = insert(q.rt, p);
        }
        q.N = q.lb.N + q.rt.N + 1;
        return q;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (!root.rect.contains(p)) return false;
        return contains(root, p);
    }

    // helper contains function
    private boolean contains(Node q, Point2D p) {
        if (p == null) return false;
        if (q.p == null) return false;
        if (q.lb == null) return false;
        if ((q.orient == VERTICAL && p.x() < q.lb.rect.xmax())
            || (q.orient == HORIZONTAL && p.y() < q.lb.rect.ymax()))
            return contains(q.lb, p);
        else
            return contains(q.rt, p);
    }
    
    // draw all of the points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(StdDraw.BLACK);
        if (root != null) root.rect.draw();
        draw(root);
    }

    private void draw(Node q) {
        if (q == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        if (q.p != null) q.p.draw();
        if (q.lb == null) return;

        StdDraw.setPenRadius(0.002);
        double x1 = 0, y1 = 0;
        double y2 = q.lb.rect.ymax();
        double x2 = q.lb.rect.xmax();
        if (q.orient == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            x1 = q.lb.rect.xmax();
            y1 = q.lb.rect.ymin();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            x1 = q.lb.rect.xmin();
            y1 = q.lb.rect.ymax();
        }
        StdDraw.line(x1, y1, x2, y2);
        if (q.lb != null) draw(q.lb);
        if (q.rt != null) draw(q.rt);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> inrange = new TreeSet<Point2D>();
        range(rect, root, inrange);
        return inrange;
    }
    
    private void range(RectHV rect, Node q, TreeSet<Point2D> ps) {
        if (q == null) return;
        if (q.p == null) return;
        assert q.lb != null;  // If a node has a point, it has partitions.
        if (rect.contains(q.p)) ps.add(q.p);
        if (q.orient == VERTICAL) {
            if (rect.xmin() < q.lb.rect.xmax())
                range(rect, q.lb, ps);
            if (rect.xmax() >= q.lb.rect.xmax())
                range(rect, q.rt, ps);
        } else { // HORIZONTAL
            if (rect.ymin() < q.lb.rect.ymax())
                range(rect, q.lb, ps);
            if (rect.ymax() >= q.lb.rect.ymax())
                range(rect, q.rt, ps);
        }
    }


    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Node best = null;
        best = nearest(p, root, best);
        return best.p;
    }
    // helper private function
    private Node nearest(Point2D p, Node q, Node tbest) {
        if (q == null) return tbest;
        if (q.p == null) return tbest;
        Node best = tbest;
        if (best == null || q.p.distanceSquaredTo(p) < best.p.distanceSquaredTo(p))
            best = q;

        if (q.orient == VERTICAL) {
            if (p.x() < q.lb.rect.xmax()) {
                // update best looking at the left-bottom partition
                best = nearest(p, q.lb, best);
                // is right-top partition still viable? If yes, look at it.
                if (best.p.distanceSquaredTo(p) > q.rt.rect.distanceSquaredTo(p))
                    best = nearest(p, q.rt, best);
            } else {
                best = nearest(p, q.rt, best);
                if (best.p.distanceSquaredTo(p) > q.lb.rect.distanceSquaredTo(p))
                    best = nearest(p, q.lb, best);
            }
        } else { // HORIZONTAL
            if (p.y() < q.lb.rect.ymax()) {
                best = nearest(p, q.lb, best);
                if (best.p.distanceSquaredTo(p) > q.rt.rect.distanceSquaredTo(p))
                    best = nearest(p, q.rt, best);
            } else {
                best = nearest(p, q.rt, best);
                if (best.p.distanceSquaredTo(p) > q.lb.rect.distanceSquaredTo(p))
                    best = nearest(p, q.lb, best);
            }
        }
        return best;
    }


    // unit test: read in coordinates from a file and draw points
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int max = 10000000;
        if (args.length > 1) {
            max = Integer.parseInt(args[1]);
        }

        StdDraw.show();

        KdTree kdt = new KdTree();
        Point2D plast = null;
        while (!in.isEmpty() && max > 0) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdt.insert(p);
            plast = p;
            max--;
        }

        StdOut.println("Is empty? " + kdt.isEmpty());
        StdOut.println("Size = " + kdt.size());
        StdOut.println("Contain test " + kdt.contains(plast));

        StdDraw.setPenRadius(0.01);
        kdt.draw();
    }
}
