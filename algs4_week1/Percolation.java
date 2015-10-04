/**
 *  The <tt>Percolation</tt> class represents a percolation system data structure.
 *  It supports the <em>percolates</em> and <em>open</em> operations, 
 *  for opening sites and determning percolation properties of a grid, as 
 *  well as supporting methods. It also relies on the WeightedQuickFindUF class which
 *  is included in the zip file. 
 *  <p>
 *  This implementation uses weighted quick union by size (without path compression).
 *  Initializing a data structure with <em>N^2</em> objects takes linear time.
 *  Afterwards, <em>isFull</em> and <em>percolates</em> and <em>connectNeighbors</em> take
 *  logarithmic time (in the worst case).
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *     
 *  @author Martin Allen
 */


public class Percolation {

    /**
     * Initializes an NxN grid of boolean values initialized to false, representing that all sites are closed.
     * Also initializes an empty WeightedQuickUnion data structure, and creats virtual top and bottom nodes. 
     * @throws java.lang.IllegalArgumentException if N < 1
     * @param N sets the grid to size NxN
     */
    
    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF graph;
    private int top;
    private int bottom;

    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException("Must have N > 0");
        }
        this.N = N;
        this.grid = new boolean[N+2][N+2];
        this.graph = new WeightedQuickUnionUF((N*N) + 2);
        this.top = N*N;
        this.bottom = N*N + 1;
    }

    /**
     * Opens the specified site, i.e. sets its value to true, and connects all opening neighboring sites.
     * @throws java.lang.IndexOutOfBoundsException if site indices not in {1, ..., N}.
     * @param i, j the index of the site to open.
     */
   
    public void open(int i, int j) {
        if (i < 1 || i > this.N || j < 1 || j > this.N) {
            throw new IndexOutOfBoundsException("index out of bound!");
        } else if (!this.grid[i][j]) {
            this.grid[i][j] = true;
            this.connectNeighbors(i, j);
        }
    }

    /**
     * Checks to see if the specified site is open.
     * @throws java.lang.IndexOutOfBoundsException if site indices not in {1, ..., N}.
     * @param i, j the index of the site to check.
     * @return true is the site is open, false otherwise. 
     */
 
    public boolean isOpen(int i, int j) {
         if (i < 1 || i > this.N || j < 1 || j > this.N) {
             throw new IndexOutOfBoundsException("index out of bounds!");
        } else {
             return this.grid[i][j];
        }
    }

    /**
     * Checks to see if the specified site is connected to an open top site, i.e. is "full".
     * @throws java.lang.IndexOutOfBoundsException if site indices not in {1, ..., N}.
     * @param i, j the index of the site to check.
     * @return true if full, false otherwise.
     */

    public boolean isFull(int i, int j) {
        if (i < 1 || i > this.N || j < 1 || j > this.N) {
            throw new IndexOutOfBoundsException("index out of bounds!");
        } else {  
            return graph.connected(matrixToList(i, j), this.top);
        }
    }

    /**
     * Checks to see if the system percolates.
     * @return true if the system percolates, false otherwise. 
     */

    public boolean percolates() {
        for (int j = 1; j < this.N+1; j++) {
            if (grid[this.N][j]) {
                if (this.isFull(this.N, j)) {
                    graph.union(matrixToList(this.N, j), this.bottom);
                }
            }
        }  
        return graph.connected(this.top, this.bottom);
    }

    /**
     * Used to convert between the percolation system, which is a grid, and a graph with
     * vertices indexed by the sites represented by the WeightedUnionFindUF structure.  
     * @param i, j the index of the site.
     * @return the index of the corresponding vertex. 
     */

    private int matrixToList(int i, int j) {
        return (i-1)*this.N + j-1;
    }

    /**
     * Connects all neighboring open sites to a given site, only called when a new site is opened.
     * @throws java.lang.IndexOutOfBoundsException if site indices not in {1, ..., N}.
     * @param p, q the central site to which we connect other open sites.
     */

    private void connectNeighbors(int p, int q) {
        if (p < 1 || p > this.N || q < 1 || q > this.N) {
            throw new IndexOutOfBoundsException("index out of bounds!");
        } else {
            for (int i = p-1; (i < p+2); i++) {
                if (grid[i][q]) {
                    graph.union(matrixToList(p, q), matrixToList(i, q));
                }
            }
            for (int j = q-1; j < q+2; j++) {
                if (grid[p][j]) {
                    graph.union(matrixToList(p, q), matrixToList(p, j));
                }
            }
            if (p == 1) {
                graph.union(this.top, matrixToList(p, q));
            }
        }
    }
    public static void main(String[] args) { };
}
