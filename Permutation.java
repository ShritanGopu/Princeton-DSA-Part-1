/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 12/19/2022
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> f = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            f.enqueue(StdIn.readString());
        }

        if (Integer.parseInt(args[0]) > f.size() || 0 > Integer.parseInt(args[0])) {
            throw new IllegalArgumentException("Input k is not within range.");
        }

        for (int i = 0; i <= Integer.parseInt(args[0]); i++) {
            System.out.println(f.dequeue());
        }

    }
}
