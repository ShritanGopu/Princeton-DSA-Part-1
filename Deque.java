/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 12/15/2022
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item data;
        Node prev;
        Node next;

        public Node(Item item) {
            data = item;
            prev = null;
            next = null;
        }

        public Node() {
            data = null;
            prev = null;
            next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        head = new Node();
        tail = head;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null cannot be added to the deque.");
        }
        if (this.isEmpty()) {
            head.data = item;
            size++;
        }
        else {
            Node ad = new Node(item);
            Node temp = head;

            temp.prev = ad;
            ad.next = temp;

            head = ad;

            size++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null cannot be added to the deque.");
        }
        if (this.isEmpty()) {
            tail.data = item;
            size++;
        }
        else {
            Node ad = new Node(item);
            Node temp = tail;

            temp.next = ad;
            ad.prev = temp;

            tail = ad;

            size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove element from an empty deque.");
        }
        Item removed = head.data;
        if (size == 1) {
            head.data = null;
            size--;
        }
        else {
            head = head.next;
            head.prev = null;
            size--;
        }
        return removed;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove element from an empty deque.");
        }
        Item removed = tail.data;
        if (size == 1) {
            tail.data = null;
            size--;
        }
        else {
            tail = tail.prev;
            tail.next = null;
            size--;
        }
        return removed;
    }

    private class DequeIterator implements Iterator<Item> {

        Node curr = head;

        @Override
        public boolean hasNext() {
            return curr != tail.next;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty.");
            }

            Item currD = curr.data;
            curr = curr.next;
            return currD;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can not remove on deque iterator.");
        }
    }

    // // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        try {
            d.addFirst(null);
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASS: throw IllegalArgumentException when add null element to deque.");
        }

        d.addFirst("abc");
        d.addLast("xyz");

        try {
            d.removeFirst();
            d.removeLast();
            d.removeLast();
        }
        catch (NoSuchElementException e) {
            StdOut.println("PASS: throw NoSuchElementException when remove from empty deque.");
        }

        d.addLast("last");
        d.addFirst("middle");
        d.addFirst("first");

        Iterator<String> iterator = d.iterator();
        for (int i = 0; i < d.size(); i++) {
            StdOut.println(iterator.next());
        }

        try {
            iterator.next();
        }
        catch (NoSuchElementException e) {
            StdOut.println(
                    "PASS: throw NoSuchElementException when calling next() on iterator when there is no more items.");
        }

        try {
            iterator.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println(
                    "PASS: throw UnsupportedOperationException when calling remove() on DequeIterator.");
        }
    }
}
