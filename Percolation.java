/* *****************************************************************************
 *  Name:              Shritan Gopu
 *  Coursera User ID:  123456
 *  Last modified:     12/06/2022
 **************************************************************************** */

/* use a one dimensional graph approach */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF qf;
    private final int topVN;
    private final int bottomVN;
    private final boolean[] percolate;
    private int counter;
    private final int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        qf = new WeightedQuickUnionUF(n * n + 2);
        topVN = 0;
        bottomVN = n * n + 1;
        percolate = new boolean[n * n + 2];
        percolate[0] = true;
        percolate[n * n + 1] = true;
        counter = 0;
    }

    // finds the index for the one dimensional array
    private int indexOf(int row, int col) {
        return (row - 1) * size + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!percolate[indexOf(row, col)]) {
            percolate[indexOf(row, col)] = true;
            counter++;
        }

        // union the other sites
        if (row - 1 == 0) {
            qf.union(topVN, indexOf(row, col));
        }

        if (row == size) {
            qf.union(bottomVN, indexOf(row, col));
        }

        if (row - 1 > 0 && percolate[indexOf(row - 1, col)]) {
            qf.union(indexOf(row, col), indexOf(row - 1, col));
        }

        if (row + 1 <= size && percolate[indexOf(row + 1, col)]) {
            qf.union(indexOf(row, col), indexOf(row + 1, col));
        }

        if (col - 1 > 0 && percolate[indexOf(row, col - 1)]) {
            qf.union(indexOf(row, col), indexOf(row, col - 1));
        }

        if (col + 1 <= size && percolate[indexOf(row, col + 1)]) {
            qf.union(indexOf(row, col), indexOf(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return percolate[indexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return qf.find(topVN) == qf.find(indexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(topVN) == qf.find(bottomVN);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
