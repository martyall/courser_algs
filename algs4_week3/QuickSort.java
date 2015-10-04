import java.util.*;

public class QuickSort
{
    Comparator c;
    public QuickSort(Comparable[] a, Comparator c)
    {
        this.c = c;
        sort(a, 0, a.length - 1);
    }
    private void sort(Comparable[] a, int lo, int hi)
    {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        // Sort left part a[lo .. j-1].
        sort(a, j+1, hi);
        // Sort right part a[j+1 .. hi].
    }
    private int partition(Comparable[] a, int lo, int hi)
    { // Partition into a[lo..i-1], a[i], a[i+1..hi].
        int i = lo, j = hi+1;
        // left and right scan indices
        Comparable v = a[lo];
        // partitioning item
        while (true)
        { // Scan right, scan left, check for scan complete, and exchange.
            while (c.compare(a[++i], v) < 0) if (i == hi) break;
            while (c.compare(v, a[--j]) < 0) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        // Put v = a[j] into position
        return j;
    }
        
    //echanges two elmts in array
    private void exch(Object[] a, int i, int j)
    { Object t = a[i]; a[i] = a[j]; a[j] = t; }

    public static void main(String[] args) {
	Point[] l = {new Point(3,5), new Point(43,99), new Point(12,66)};
        System.out.println(Arrays.toString(l));
        new QuickSort(l, l[1].SLOPE_ORDER);
        System.out.println(Arrays.toString(l));
    }
}
