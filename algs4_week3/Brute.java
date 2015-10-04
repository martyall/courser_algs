import java.util.*;

// This program accepts from standard input (1) an integer N denoting the number of points
// to be given and (2) N points with coordinates (x,y). It prints all 4-tuples of points 
// which lie on the same line and draws the lines using StdDraw. The method is by brute force. 
// Depends on class Point from Point.java. 

public class Brute {

    // implementation of classical quicksort 
    private static void quickSort(Point[] a)
    {
    int N = a.length;
    for (int i = 1; i < N; i++)
	for (int j = i; j > 0 && a[j].compareTo(a[j-1]) < 0 ; j--)
    exch(a, j, j-1);
    }

    // swaps two elmts in anrray
    private static void exch(Object[] a, int i, int j)
    { Object t = a[i]; a[i] = a[j]; a[j] = t; }


    public static void main(String[] args) {

        String fileName = args[0];
        In in = new In(fileName);
        int num_points = in.readInt();
        Point[] points = new Point[num_points];
        int xMin = 0;
        int xMax = 0;
        int yMin = 0;
        int yMax = 0;

        // collect points in an array
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

        // set the scale for StdDraw
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);

        quickSort(points);
   

        //By brute force check all 4 tuples to see if they lie on a line.
        //if so print and draw.
        for (int i = 0; i < num_points; i++) {
            for (int j = i+1; j < num_points; j++) {
                for (int k = j+1; k < num_points; k++) {
                    for (int l = k+1; l < num_points; l++) {
                        if (points[i].slopeTo(points[j]) ==  points[i].slopeTo(points[k]) && 
                            points[i].slopeTo(points[k]) ==  points[i].slopeTo(points[l])) {
                            points[i].drawTo(points[l]);
                            System.out.format("%s -> %s -> %s -> %s", points[i].toString(),points[j].toString(),points[k].toString(),points[l].toString());
                            System.out.println(" ");
                        }
		    }
                }
	    }
        }
    }
}
    
