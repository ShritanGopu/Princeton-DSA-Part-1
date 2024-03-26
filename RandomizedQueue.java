/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 12/16/2022
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int size;


    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot input null into queue.");
        }
        if (size == q.length) resize(2 * size);
        q[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot retrieve element where queue is empty.");
        }


        int rand = StdRandom.uniformInt(size);
        Item val = q[rand];
        q[rand] = q[size - 1];
        q[size - 1] = null;

        if (size == q.length / 4) resize(size / 2);
        size--;
        return val;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot retrieve element where queue is empty.");
        }
        return q[StdRandom.uniformInt(size)];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity + 1];
        for (int i = 0; i < size; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int curr = 0;
        private Item[] items;

        public RandomIterator() {
            items = (Item[]) new Object[size];

            for (int i = 0; i < size; i++) {
                items[i] = q[i];
            }
            StdRandom.shuffle(items);

        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty.");
            }

            return items[curr++];
        }

        public boolean hasNext() {
            return curr < size;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "Remove is not a function allowed by RandomIterator.");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> d = new RandomizedQueue<String>();

        try {
            d.enqueue(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("PASS: CANNOT ADD NULL");
            System.out.println("-----------------------------------");
        }

        d.enqueue("first");
        d.enqueue("second");
        d.enqueue("third");
        d.enqueue("fourth");
        System.out.println(d.size());
        System.out.println(d.dequeue());
        System.out.println(d.dequeue());
        System.out.println(d.dequeue());
        System.out.println(d.dequeue());
        System.out.println("-----------------------------------");

        try {
            d.dequeue();
        }
        catch (NoSuchElementException e) {
            System.out.println("PASS: CANNOT REMOVE FROM EMPTY QUEUE");

            System.out.println("-----------------------------------");
        }

        try {
            d.sample();
        }
        catch (NoSuchElementException e) {
            System.out.println("PASS: CANNOT SAMPLE FROM EMPTY QUEUE");
            System.out.println("-----------------------------------");
        }


        d.enqueue("first");
        d.enqueue("second");
        d.enqueue("third");


        Iterator<String> iterator = d.iterator();
        for (int i = 0; i < d.size(); i++) {
            StdOut.println(iterator.next());
        }

        System.out.println("-----------------------------------");

        try {
            iterator.next();
        }
        catch (NoSuchElementException e) {
            StdOut.println(
                    "PASS: throw NoSuchElementException when calling next() on iterator when there is no more items.");

            System.out.println("-----------------------------------");
        }

        try {
            iterator.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println(
                    "PASS: throw UnsupportedOperationException when calling remove() on DequeIterator.");

            System.out.println("-----------------------------------");
        }

    }
}
