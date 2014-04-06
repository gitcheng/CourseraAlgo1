// 8puzzle Board
//------------------------------------------------------------
public class Board {

    private final int[][] tiles;
    private final int iblank, jblank, N;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = new int[N][N];
        assert N == blocks[0].length;  // make sure it is square
        int i0 = -1, j0 = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }
        iblank = i0;
        jblank = j0;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i*N + j + 1)
                    h++;
        return h;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int ni = tiles[i][j];
                if (ni != 0) {
                    // where it should be
                    int i2 = (ni-1)/N;
                    int j2 = (ni-1) % N;
                    m += Math.abs(i-i2) + Math.abs(j-j2);
                }
            }
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] tilesTwin = new int[N][N];
        int i0 = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tilesTwin[i][j] = tiles[i][j];
                if (tiles[i][j] == 0)
                    i0 = i;  // the row with the empty tile
            }
        }
        // use the next row (or the zero-th row), and swap 0 and 1 column
        i0 = (i0 + 1) % N;
        tilesTwin[i0][0] = tiles[i0][1];
        tilesTwin[i0][1] = tiles[i0][0];
        return new Board(tilesTwin);
    }


    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }
 
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> bstack = new Stack<Board>();
        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tmp[i][j] = tiles[i][j];
                
        // top neighbor
        if (iblank > 0) {
            tmp[iblank][jblank] = tiles[iblank-1][jblank];
            tmp[iblank-1][jblank] = 0;
            bstack.push(new Board(tmp));
            tmp[iblank][jblank] = 0;
            tmp[iblank-1][jblank] = tiles[iblank-1][jblank];
        }
        // bottom neighbor
        if (iblank < N - 1) {
            tmp[iblank][jblank] = tiles[iblank+1][jblank];
            tmp[iblank+1][jblank] = 0;
            bstack.push(new Board(tmp));
            tmp[iblank][jblank] = 0;
            tmp[iblank+1][jblank] = tiles[iblank+1][jblank];
        }
        // left neighbor
        if (jblank > 0) {
            tmp[iblank][jblank] = tiles[iblank][jblank-1];
            tmp[iblank][jblank-1] = 0;
            bstack.push(new Board(tmp));
            tmp[iblank][jblank] = 0;
            tmp[iblank][jblank-1] = tiles[iblank][jblank-1];
        }
        // right neighbor
        if (jblank < N - 1) {
            tmp[iblank][jblank] = tiles[iblank][jblank+1];
            tmp[iblank][jblank+1] = 0;
            bstack.push(new Board(tmp));
            tmp[iblank][jblank] = 0;
            tmp[iblank][jblank+1] = tiles[iblank][jblank+1];
        }
        return bstack;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }




    // Unit tests
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println("Initial board");
        StdOut.println(initial);
        StdOut.println("Hamming = " + initial.hamming());
        StdOut.println("Manhattan = " + initial.manhattan());
        StdOut.println("Twin");
        StdOut.println(initial.twin());

        StdOut.println("Neighbors");
        for (Board neighbor : initial.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("equal ? " + initial.equals(neighbor));
            StdOut.println("Hamming = " + neighbor.hamming());
            StdOut.println("Manhattan = " + neighbor.manhattan());
        }

    }

}
