/****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 1/20/23
 *  Description: This java program solves 8 puzzle boards given an initial board.
 ****************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private MinPQ<Node> game;
    private MinPQ<Node> twin;

    private Stack<Board> sols;

    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        sols = new Stack<Board>();

        if (initial.isGoal()) {
            sols.push(initial);
            solvable = true;
        }
        else {
            game = new MinPQ<Node>(nodeOrder());
            twin = new MinPQ<Node>(nodeOrder());
            game.insert(new Node(initial, 0, initial.manhattan(), null));
            twin.insert(new Node(initial.twin(), 0, initial.twin().manhattan(), null));
            solve();
        }

    }

    private void solve() {

        while (!game.min().data.isGoal() && !twin.min().data.isGoal()) {
            Node nod = game.delMin();
            Iterable<Board> neighbors = nod.data.neighbors();
            for (Board i : neighbors) {
                if (nod.previous == null || !i.equals(nod.previous.data)) {
                    game.insert(new Node(i, nod.moves + 1, i.manhattan(), nod));
                }
            }

            Node sec = twin.delMin();
            Iterable<Board> twins = sec.data.neighbors();
            for (Board i : twins) {
                if (sec.previous == null || !i.equals(sec.previous.data)) {
                    twin.insert(new Node(i, sec.moves + 1, i.manhattan(), sec));
                }
            }
        }

        if (game.min().data.isGoal()) {
            Node st = game.min();
            solvable = true;
            while (st.previous != null) {
                sols.push(st.data);
                st = st.previous;
            }
            sols.push(st.data);
        }
        else {
            solvable = false;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? sols.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? sols : null;
    }

    private Comparator<Node> nodeOrder() {
        return new NodeComparator();
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node first, Node other) {
            return Integer.compare(first.manhattan + first.moves, other.manhattan + other.moves);
        }
    }

    private class Node {
        Board data;
        Node previous;
        int moves;
        int manhattan;

        private Node(Board data, int moves, int manhattan, Node previous) {
            this.data = data;
            this.moves = moves;
            this.manhattan = manhattan;
            this.previous = previous;
        }

        public int compareTo(Node that) {
            if (data.equals(that.data)) {
                return 0;
            }
            return Double.compare(manhattan + moves, that.moves + that.manhattan);
        }

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
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

