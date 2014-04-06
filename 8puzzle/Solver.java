// Client solver for 8-puzzle
//--------------------------------------------------------
import java.util.Comparator;

public class Solver {

    private MinPQ<Node> solveq1, solveq2;
    private boolean solved, finished;

    private final Comparator<Node> HAMMING_ORDER = new ByHamming();
    private final Comparator<Node> MANHATTAN_ORDER = new ByManhattan();

    private Node current1, current2;

    private class Node {
        private final Board board;
        private final Node previous;
        private final int moves;

        public Node(Board bd, Node prev, int mv) {
            board = bd;
            previous = prev;
            moves = mv;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        // A priority queue to store search nodes
        solveq1 = new MinPQ<Node>(MANHATTAN_ORDER);
        // A priority queue to store search nodes from a twin
        solveq2 = new MinPQ<Node>(MANHATTAN_ORDER);

        // Initial node
        Node iniNode = new Node(initial, null, 0);
        solveq1.insert(iniNode);
        Node twinNode = new Node(initial.twin(), null, 0);
        solveq2.insert(twinNode);

        solved = false;
        finished = false;
    }

    // Comparator by Hamming distance + moves
    private class ByHamming implements Comparator<Node> {
        public int compare(Node v, Node w) {
            int pv = v.board.hamming() + v.moves;
            int pw = w.board.hamming() + w.moves;
            if (pv < pw) return -1;
            if (pv > pw) return +1;
            return 0;
        }
    }
    // Comparator by Manhattan distance + moves
    private class ByManhattan implements Comparator<Node> {
        public int compare(Node v, Node w) {
            int pv = v.board.manhattan() + v.moves;
            int pw = w.board.manhattan() + w.moves;
            if (pv < pw) return -1;
            if (pv > pw) return +1;
            return 0;
        }
    }

    // Insert neighbors
    private void insertNeighbors(MinPQ<Node> q, Node current) {
        for (Board neighbor : current.board.neighbors()) {
            if (current.previous != null)
                if (neighbor.equals(current.previous.board)) 
                    continue; // Skip the one that was the previous board

            Node nnode = new Node(neighbor, current, current.moves + 1);
            q.insert(nnode);
        }
    }

    // to solve
    private void toSolve() {
        // Do not repeat
        if (finished) return;
        // Start solving it
        // dequeue the minimum
        current1 = solveq1.delMin();
        current2 = solveq2.delMin();
 
        while (!current1.board.isGoal() && !current2.board.isGoal()) {
            insertNeighbors(solveq1, current1);
            insertNeighbors(solveq2, current2);
            // dequeue the minimum
            current1 = solveq1.delMin();
            current2 = solveq2.delMin();
        }

        solved = current1.board.isGoal();
        finished = true;
        return;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (!finished) toSolve();
        return solved;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (!finished) return -1;
        if (isSolvable())
            return current1.moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!solved) return null;
        Stack<Board> q = new Stack<Board>();
        Node c = current1;
        while (c != null) {
            q.push(c.board);
            c = c.previous;
        }
        return q;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {
            
            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            
            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
        }
    }
}




