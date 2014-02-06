// Coursera/Algorithm I Programming Assignment 1: Percolation
// http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
// 
public class PercolationStats {

    // fraction of open sites
    private double[] xf;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0)
            throw new IllegalArgumentException("Invalid "+N);
        if (T <= 0)
            throw new IllegalArgumentException("Invalid "+T);
        xf = new double[T];
        // Trying T times
        for (int t = 0; t < xf.length; t++) {
            Percolation perc = new Percolation(N);
            double prob = 0.0;
            // Each time, keep opening up sites until it percolates
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    prob = prob+1;
                }
            }
            xf[t] = prob/(N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xf);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xf);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        double s = stddev();
        return m - 1.96*s/Math.sqrt(xf.length);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        double s = stddev();
        return m + 1.96*s/Math.sqrt(xf.length);
    }
    
    // test client, described below
    public static void main(String[] args) {
        StdRandom.setSeed(0);
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        if (N <= 0)
            throw new IllegalArgumentException("Invalid "+N);
        if (T <= 0)
            throw new IllegalArgumentException("Invalid "+T);
        Stopwatch sw = new Stopwatch();
        PercolationStats pstats = new PercolationStats(N, T);
        StdOut.println("Elapsed time "+sw.elapsedTime()+" s");
        StdOut.printf("mean                    = %f\n", pstats.mean());
        StdOut.printf("stddev                  = %f\n", pstats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", 
                      pstats.confidenceLo(), pstats.confidenceHi());
    }
}
