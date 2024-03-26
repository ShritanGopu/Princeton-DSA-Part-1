/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 03/03/2024
 *  Description: PointSET Assingment for week 5 Princeton DSA course
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;
    private int size;

    public PointSET() {
        set = new TreeSet<>();
        size = 0;
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                    // is the set empty?

    public int size() {
        return size;
    }                      // number of points in the set

    public void insert(Point2D p) {
        if (set.add(p)) {
            size++;
        }
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Argument Is Null");
        }
        return set.contains(p);
    }          // does the set contain point p?

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);
        for (Point2D p : set) {
            p.draw();
        }
        StdDraw.show();
    }                   // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Inserting a null rectangle");
        // get close to rectangle first
        List<Point2D> ans = new ArrayList<Point2D>();
        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();

        for (Point2D p : set) {
            if (p.x() <= xmax && p.x() >= xmin) {
                if (p.y() <= ymax && p.y() >= ymin) {
                    ans.add(p);
                }
            }
        }

        return ans;
    }          // all points that are inside the rectangle (or on the boundary)

    //
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Inserting a null point");

        if (set.isEmpty()) {
            return null;
        }

        Point2D max = set.first();
        double dist = p.distanceSquaredTo(max);

        for (Point2D x : set) {
            if (p.distanceSquaredTo(x) < dist) {
                max = x;
                dist = x.distanceSquaredTo(p);
            }
        }

        return max;
    }           // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        // PointSET o = new PointSET();
        //
        // In in = new In(args[0]);
        // while (!in.isEmpty()) {
        //     String f = in.readLine();
        //     if (f.equals("")) {
        //         break;
        //     }
        //     String[] tr = f.split(" ");
        //     o.insert(new Point2D(Double.parseDouble(tr[0]), Double.parseDouble(tr[1])));
        // }
        //
        // Iterable<Point2D> z = o.range(new RectHV(0.2, 0.4, 0.5, 0.9));
        // Point2D near = o.nearest(new Point2D(0.3, 0.3));
        // o.draw();
        // StdDraw.show();
        // // StdOut.println(z);
    }
}
