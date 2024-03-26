/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 12/28/2022
 *  Description:
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private Point[] p;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("point in points is null");
        }
        p = points;

        Arrays.sort(p);
        for (int i = 1; i < p.length; i++) {
            if (p[i].compareTo(p[i - 1]) == 0)
                throw new IllegalArgumentException("duplicate point in points");
        }
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        ArrayList<LineSegment> res = new ArrayList<LineSegment>();

        for (int i = 0; i < p.length - 3; i++) {
            Point p1 = p[i];
            for (int j = i + 1; j < p.length - 2; j++) {
                Point p2 = p[j];
                for (int x = j + 1; x < p.length - 1; x++) {
                    Point p3 = p[x];
                    for (int z = x + 1; z < p.length; z++) {
                        Point p4 = p[z];
                        List<Point> pointList = new ArrayList<Point>();
                        pointList.add(p1);
                        pointList.add(p2);
                        pointList.add(p3);
                        pointList.add(p4);

                        pointList.sort(Point::compareTo);

                        if (p1.slopeTo(p2) == p2.slopeTo(p3) && p3.slopeTo(p4) == p2.slopeTo(p3)) {
                            res.add(new LineSegment(pointList.get(0),
                                                    pointList.get(pointList.size() - 1)));
                        }
                    }
                }
            }
        }
        removeDupes(res);
        return res.toArray(new LineSegment[res.size()]);
    }


    private int makeX(String curr) {
        return Integer.parseInt(curr.substring(curr.indexOf("(") + 1, curr.indexOf(",")));
    }

    private int makeY(String curr) {
        return Integer.parseInt(
                curr.substring(curr.indexOf(", ") + 2, curr.indexOf(")")));
    }

    private Point makePoint(String curr) {
        return new Point(makeX(curr), makeY(curr));
    }


    private void removeDupes(ArrayList<LineSegment> vals) {

        for (int i = 0; i < vals.size(); i++) {
            String curr = vals.get(i).toString();

            String firstPoint = curr.substring(curr.indexOf("("), curr.indexOf(")") + 1);
            String secondPoint = curr.substring(curr.lastIndexOf("("),
                                                curr.length());


            Point first = makePoint(firstPoint);
            Point second = makePoint(secondPoint);

            double currSlope = first.slopeTo(second);

            for (int j = i + 1; j < vals.size(); j++) {
                String curr2 = vals.get(j).toString();

                String first2Point = curr2.substring(curr2.indexOf("(") + 1,
                                                     curr2.indexOf(")") + 1);
                String second2Point = curr2.substring(curr2.lastIndexOf("(") + 1,
                                                      curr2.lastIndexOf(")") + 1);


                Point one2 = makePoint(first2Point);
                Point two2 = makePoint(second2Point);

                double slope2 = one2.slopeTo(two2);

                // checks the vertical lines
                if (currSlope == Double.POSITIVE_INFINITY && currSlope == slope2) {
                    // checks if they are the same
                    if (makeX(firstPoint) == makeX(second2Point)) {

                        if (first.compareTo(one2) > 0) {

                            vals.remove(i);
                            i--;
                            continue;
                        }
                        else {
                            vals.remove(j);
                            j--;
                            continue;
                        }
                    }
                }

                if (currSlope == slope2) {
                    if (first.slopeTo(two2) != currSlope) {

                        continue;
                    }
                    if (first.compareTo(one2) > 0) {

                        vals.remove(i);
                        i--;
                        continue;
                    }
                    vals.remove(j);
                    j--;
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(-500, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if (segment == null)
                break;
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
