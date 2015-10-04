import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;       // x coordinate
    private final int y;       // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new slopeOrder(); 

    private class slopeOrder implements Comparator<Point> {
   
        public int compare(Point a, Point b) {
            if (slopeTo(a) < slopeTo(b)) {
                return -1;
            } else if (slopeTo(a) > slopeTo(b)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
	    if (this.y == that.y & this.x > that.x) {
                return -0.0;
            } else {
	        return ((double) that.y - this.y)/((double) that.x - this.x);
            }
	}
    }


    // compare points by left most y coordinate
    public static Comparator<Point> Y_ORDER = new yOrder();

    private static class yOrder implements Comparator<Point> {

        public int compare(Point a, Point b) {
        return a.compareTo(b);
        }
     }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else if (this.x < that.x) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
	} else {
            return 0;
        }
    } 
 
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
 
}

