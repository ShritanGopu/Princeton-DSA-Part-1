/*****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 1/20/23
 *  Description: This java program takes in input and gives a summary of the
 * numbers which it has
 ****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int hamming;
    private int manhattan;

    private int rowOfZero;
    private int colOfZero;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    rowOfZero = i;
                    colOfZero = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }


        // HAMMING INITIALIZATION
        int count = 0;
        // MANHATTAN INITIZALTION
        int total = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0 && (tiles[i][j] != i * tiles.length + j + 1)) {
                    count++;
                    total += Math.abs(i - (tiles[i][j] / this.tiles.length)) + Math.abs(
                            j - (tiles[i][j] / this.tiles.length));
                }
            }
        }
        hamming = count;
        manhattan = total;
    }

    // string representation of this board
    public String toString() {
        String res = tiles.length + "\n ";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                res += tiles[i][j] + " ";
            }
            res += "\n ";
        }
        return res;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0 && hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }

        if (y.getClass() != getClass()) {
            return false;
        }
        else {
            if (((Board) y).dimension() != dimension() || ((Board) y).manhattan() != manhattan
                    || ((Board) y).hamming() != hamming) {
                return false;
            }

            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if (((Board) y).tiles[r][c] != tiles[r][c]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void exchange(int r1, int c1, int r2, int c2) {
        int temp = tiles[r1][c1];
        tiles[r1][c1] = tiles[r2][c2];
        tiles[r2][c2] = temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        if (rowOfZero + 1 < tiles.length) {
            exchange(rowOfZero, colOfZero, rowOfZero + 1, colOfZero);
            neighbors.add(new Board(tiles));
            exchange(rowOfZero, colOfZero, rowOfZero + 1, colOfZero);
        }

        if (rowOfZero - 1 >= 0) {
            exchange(rowOfZero, colOfZero, rowOfZero - 1, colOfZero);
            neighbors.add(new Board(tiles));
            exchange(rowOfZero, colOfZero, rowOfZero - 1, colOfZero);
        }

        if (colOfZero - 1 >= 0) {
            exchange(rowOfZero, colOfZero, rowOfZero, colOfZero - 1);
            neighbors.add(new Board(tiles));
            exchange(rowOfZero, colOfZero, rowOfZero, colOfZero - 1);
        }

        if (colOfZero + 1 < tiles.length) {
            exchange(rowOfZero, colOfZero, rowOfZero, colOfZero + 1);
            neighbors.add(new Board(tiles));
            exchange(rowOfZero, colOfZero, rowOfZero, colOfZero + 1);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        twinExch();
        Board first = new Board(tiles);
        twinExch();
        return first;
    }

    private void twinExch() {
        int r1 = 1;
        int c1 = 0;
        int r2 = 1;
        int c2 = 1;

        if (tiles[r1][c1] == 0) {
            r1--;
            c1 = tiles.length - 1;
        }
        else if (tiles[r2][c2] == 0) {
            if (c2 < tiles.length - 1) {
                c2++;
            }
            else if ((c2 == tiles.length - 1) && (r2 == tiles.length - 1)) {
                r2 = 0;
                c2 = 0;
            }
            else {
                r2++;
                c2 = 0;
            }
        }

        exchange(r1, c1, r2, c2);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            int[][] copy = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    copy[i][j] = tiles[i][j];
                }
            }
            Board copu = new Board(copy);

            // solve the slider puzzle
            Board initial = new Board(tiles);
            StdOut.println(initial);
            Iterable<Board> neigh = initial.neighbors();


            // StdOut.println(copu.equals(initial));
            // Solver solver = new Solver(initial);
            // StdOut.println(filename + ": " + solver.moves());
        }

    }
}
