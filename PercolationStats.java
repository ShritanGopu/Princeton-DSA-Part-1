/* *****************************************************************************
 *  Name:              Shritan Gopu
 *  Coursera User ID:  123456
 *  Last modified:     12/7/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] fracs;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0.");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials <= 0.");
        }

        fracs = new double[trials];

        for (int i = 0; i < trials; i++) {
            int sites = 0;
            Percolation board = new Percolation(n);
            while (!board.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                board.open(row, col);
                sites = board.numberOfOpenSites();
            }

            fracs[i] = (sites * 1.0) / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fracs);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fracs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.95 * stddev())) / (Math.sqrt(fracs.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.95 * stddev())) / (Math.sqrt(fracs.length));
    }

    public String toString() {
        return "Mean                = " + mean() +
                "\nStandard Deviation  = " + stddev() +
                "\n95% Confidence      = [" + confidenceLo() + ", " + confidenceHi() + "]";
    }

    // test client (see below)
    public static void main(String[] args) {
        try {
            PercolationStats running = new PercolationStats(Integer.parseInt(args[0]),
                                                            Integer.parseInt(args[1]));
            System.out.println(running);
        }
        catch (IllegalArgumentException e) {
            System.out.println("arguments are not integers");
        }
    }

}
