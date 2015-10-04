import java.util.*;

// This program takes from standard input a file containing  (1) an integer N and (2) a list
// of N points x y in the plane separated by white space. The return is a print statement for every maximal subset 
// of points lying on a line if there are at least four points and the line is drawn using StdDraw. 

public class Fast {

    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        int num_points = in.readInt();
        Point[] points = new Point[num_points];
        
        int xMin = 0;
        int xMax = 0;
        int yMin = 0;
        int yMax = 0;

        //construct an array containing all the points
        //and set the scale of the window to draw everything
        for (int i = 0; i < num_points; i++) {
            int x = in.readInt();
            if (x < xMin) {
                xMin = x;
            } else if (x > xMax) {
                xMax = x;
            }
            int y = in.readInt();
            if (y < yMin) {
                yMin = y;
            } else if (y > yMax) {
                yMax = y;
            }
            points[i] = new Point(x,y);
            points[i].draw();
        } 
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);

        // create deep copy of the point list because the original 
        // list will be forced out of order several times
        Point[] points_original = points.clone();

        // iterate over points in the list and sort by slope order
        // relative to the current point (see Point.java)
        for (int i = 0; i < num_points; i++) {
            new QuickSort(points, points_original[i].SLOPE_ORDER);
            for (int k = 1; k < num_points; k++) {
                Queue<Point> lineQ = new LinkedList<Point>();
                lineQ.add(points_original[i]);
                double test_slope = points_original[i].slopeTo(points[k]);
                // push all points with the same relative slope as current point to a queue, and
                // break when reaching a different slope value
	        while (points_original[i].slopeTo(points[k]) == test_slope) {      
                    lineQ.add(points[k]);
                    k++;
                    if (k >= points.length) {
                        break;
                    }
                }
                k--;
                // if we found at least three points with the same slope, we have 
                // to print and draw.
                int N = lineQ.size();
                if (4 <= N) {
                    Point[] orderedLine = lineQ.toArray(new Point[N]);
                    new QuickSort(orderedLine, Point.Y_ORDER);
                    // this conditional ensures we only will print and draw each line once, 
                    // because it is only drawn if the current point is the first among all
                    // points on the line with respect to the Y_ORDER. 
                    if (points_original[i] == orderedLine[0]) {
                        String lineString = orderedLine[0].toString();
                        for (int m = 1; m < orderedLine.length; m++) {
                            lineString += " -> ";
                            lineString += orderedLine[m].toString();
                        }
                        System.out.println(lineString);
                        orderedLine[0].drawTo(orderedLine[orderedLine.length - 1]);
		    }
	        }                  
	    }
        }   
    }
}

