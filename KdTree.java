/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 03/03/2024
 *  Description: Kd-Tree Assingment for week 5 Princeton DSA course
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private class Node {
        Point2D data;
        RectHV rect;
        Node right;
        Node left;

        public Node(Point2D d, RectHV r) {
            right = null;
            left = null;
            rect = r;
            data = d;
        }

    }

    private Node first;
    private int size;
    private ArrayList<Point2D> points;
    private Point2D near;

    public KdTree() {
        first = null;
        size = 0;
        points = new ArrayList<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                    // is the set empty?

    public int size() {
        return size;
    }                      // number of points in the set

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Inserting a null point");
        if (first == null) {
            first = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }
        Node curr = first;
        Node parent = null;
        int count = 0;

        while (curr != null) {
            if (curr.data.x() == p.x() && curr.data.y() == p.y()) return;

            parent = curr;

            if (count % 2 == 0) {
                if (p.x() < curr.data.x()) {
                    curr = curr.left;
                }
                else {
                    curr = curr.right;
                }
            }
            else {
                if (p.y() < curr.data.y()) {
                    curr = curr.left;
                }
                else {
                    curr = curr.right;
                }
            }
            count++;
        }
        count--;
        if (count % 2 == 0) {
            if (p.x() < parent.data.x()) {
                parent.left = new Node(p, new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                                     parent.data.x(), parent.rect.ymax()));
            }
            else {
                parent.right = new Node(p, new RectHV(parent.data.x(), parent.rect.ymin(),
                                                      parent.rect.xmax(), parent.rect.ymax()));
            }
        }
        else {
            if (p.y() < parent.data.y()) {
                parent.left = new Node(p, new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                                     parent.rect.xmax(), parent.data.y()));
            }
            else {
                parent.right = new Node(p, new RectHV(parent.rect.xmin(), parent.data.y(),
                                                      parent.rect.xmax(), parent.rect.ymax()));
            }
        }
        size++;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Inserting a null point");

        Node curr = first;
        int count = 0;

        while (curr != null) {
            if (curr.data.x() == p.x() && curr.data.y() == p.y())
                return true;

            if (count % 2 == 0) {
                if (p.x() < curr.data.x())
                    curr = curr.left;
                else {
                    curr = curr.right;
                }
            }
            else {
                if (p.y() < curr.data.y())
                    curr = curr.left;
                else {
                    curr = curr.right;
                }
            }
            count++;
        }

        return false;
    }          // does the set contain point p?

    private void draw(Node current) {
        if (current == null)
            return;

        current.rect.draw();
        current.data.draw();

        draw(current.right);
        draw(current.left);
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);
        if (first != null) {
            draw(first);
        }
        StdDraw.show();
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Inserting a null rectangle");
        points.clear();

        if (first == null)
            return points;

        Node curr = first;
        if (rect.contains(curr.data)) {
            points.add(curr.data);
        }

        if (curr.right != null && curr.left == null) {
            if (rect.intersects(curr.right.rect)) {
                range(rect, curr.right);
            }
        }
        else if (curr.right == null && curr.left != null) {
            if (rect.intersects(curr.left.rect)) {
                range(rect, curr.left);
            }
        }
        else if (curr.right != null) {
            range(rect, curr.left);
            range(rect, curr.right);
        }

        return points;
    }             // all points that are inside the rectangle (or on the boundary)


    private void range(RectHV rect, Node curr) {
        if (curr == null) return;

        if (rect.contains(curr.data)) {
            points.add(curr.data);
        }

        if (curr.right != null && curr.left == null) {
            if (rect.intersects(curr.right.rect)) {
                range(rect, curr.right);
            }
        }
        else if (curr.right == null && curr.left != null) {
            if (rect.intersects(curr.left.rect)) {
                range(rect, curr.left);
            }
        }
        else if (curr.right != null) {
            range(rect, curr.left);
            range(rect, curr.right);
        }
    }


    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Inserting a null point");

        if (first == null)
            return null;
        near = first.data;
        nearest(p, first.right);
        nearest(p, first.left);
        return near;

    }             // a nearest neighbor in the set to point p; null if the set is empty

    private void nearest(Point2D p, Node curr) {
        if (curr == null) return;

        if (curr.data.distanceSquaredTo(p) < near.distanceSquaredTo(p))
            near = curr.data;

        if (curr.right != null && near.distanceSquaredTo(p) > curr.right.rect.distanceSquaredTo(p))
            nearest(p, curr.right);

        if (curr.left != null && near.distanceSquaredTo(p) > curr.left.rect.distanceSquaredTo(p))
            nearest(p, curr.left);
    }


    public static void main(String[] args) {
        // KdTree test = new KdTree();
        // PointSET o = new PointSET();
        // double x = Math.random();
        // double y = Math.random();
        // Point2D min = new Point2D(x, y);
        // In in = new In(args[0]);
        // while (!in.isEmpty()) {
        //     String f = in.readLine();
        //     if (f.equals("")) {
        //         break;
        //     }
        //     String[] tr = f.split(" ");
        //     test.insert(new Point2D(Double.parseDouble(tr[0]), Double.parseDouble(tr[1])));
        //     o.insert(new Point2D(Double.parseDouble(tr[0]), Double.parseDouble(tr[1])));
        // }
        //
        // // Iterable<Point2D> z = test.range(new RectHV(0.2, 0.4, 0.5, 0.9));
        // // Iterable<Point2D> k = o.range(new RectHV(0.2, 0.4, 0.5, 0.9));
        // Point2D near = o.nearest(min);
        // Point2D hear = test.nearest(min);
        //
        // // StdOut.println(z.toString().length() == k.toString().length());
        // StdOut.println(near.equals(hear));
        // StdOut.println(near);
        // StdOut.println(hear);
        // // near.drawTo(min);
        // // hear.drawTo(min);
        // // test.draw();
        // // o.draw();
    }
}
