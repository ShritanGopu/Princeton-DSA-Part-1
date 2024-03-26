/* *****************************************************************************
 *  Name: Shritan Gopu
 *  Date: 12/27/2022
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private double[] p;
    private Point[] points;

    private LineSegment[] segs;


    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point f : points) {
            if (f == null)
                throw new IllegalArgumentException("point in points is null");
        }

        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);

        for (int i = 1; i < this.points.length; i++) {
            if (this.points[i].compareTo(this.points[i - 1]) == 0)
                throw new IllegalArgumentException("duplicate point in points");
        }
        segs = segs();
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

    public int numberOfSegments() {
        return segs.length;
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

    private LineSegment[] segs() {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length - 3; i++) {

            Point ori = points[i];


            List<Point> f = new ArrayList<>();

            List<Double> sap = new ArrayList<>();
            for (int j = i; j < points.length; j++) {
                sap.add(points[j].slopeTo(ori));
                f.add(points[j]);
            }
            
            // sort by slope order
            f.sort(ori.slopeOrder());
            sap.sort(Comparator.naturalOrder());

            // iterates through points
            for (int j = 1; j < f.size(); j++) {
                // current slope that it is checking for
                double curr = sap.get(j);

                // to keep track of which points are collinear and how many of them there ar
                int count = 0;

                // curr points
                List<Point> currs = new ArrayList<Point>();

                // loop accumulates same values
                while (curr == sap.get(j)) {
                    currs.add(f.get(j));
                    count++;
                    j++;
                    if (j == f.size()) {
                        break;
                    }
                }
                j--;

                if (count > 2) {
                    currs.sort(Point::compareTo);
                    // adds the segments which have three or more points in a row
                    segs.add(new LineSegment(f.get(0), currs.get(currs.size() - 1)));
                }
            }
        }
        removeDupes(segs);
        return segs.toArray(new LineSegment[segs.size()]);
    }

    public LineSegment[] segments() {
        return segs;
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
        for (int i = 0; i < points.length; i++) {
            points[i].draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
