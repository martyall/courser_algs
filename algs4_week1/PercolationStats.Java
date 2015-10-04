/**
 *  The <tt>PercolationStats</tt> class is used to test for critical percolation properties of the
 *  Percolation data type of a given size and to run statistical analysis. 
 *  <p>
 *  @author Martin Allen
 */

public class PercolationStats {

    /** 
     * Creates a Percolation data type and randomly open sites until the system percolates, keeping
     * track of the proportion of open sites for percolation of a given trial. Runs a number of trials specified 
     * by input on a Percolation data type of a size dicatated by input. 
     * @thrown IllegalArgumentException with N or T is not a positive integer. 
     * @param N the size of the Percolation grid.
     * @param T the number of trials to run.
    */

    private int N;
    private int T;
    private double[] openSitesFraction;  

    public PercolationStats(int N, int T) {
        this.N = N;
        this.T = T;
        this.openSitesFraction = new double[T];
        if (T < 1 || N < 1) {
            throw new IllegalArgumentException("N,T must be positive integers.");
        }
        for (int i = 0; i < T; i++) {
            Percolation g = new Percolation(N);
            int n = N*N;
            double k = 0;
            int[] tempClosed = new int[N*N];
            for (int l = 0; l < N*N; l++) {
                tempClosed[l] = l;
            }
            while (!g.percolates()) {
                int randInt = StdRandom.uniform(n);
                g.open(listToMatrix(tempClosed[randInt])[0], listToMatrix(tempClosed[randInt])[1]);
                int temp = tempClosed[n - 1];
                tempClosed[randInt] = temp;
                tempClosed[n - 1] = randInt;
                k++;
                n--;
            }
            this.openSitesFraction[i] = k/(N*N);
        }
    }

    /**
    * Gives the coordinates for the vertex corresponding to an index in the list
    * representation of the grid.  
    * @param m the list index.
    */

    private int[] listToMatrix(int m) {
        int col = (m % this.N) + 1;
        int row = (m - (col - 1))/this.N + 1;
        return new int[] {row, col};
    }
   
    /** gives the average proportion of open sites needed for percolation based on trials.
    */

    public double mean() {
        return StdStats.mean(this.openSitesFraction);
    }
   
    /** gives the standard deviation of proportion of open sites needed for percolation based on trials.
    */

    public double stddev() {
        return Math.sqrt(this.variance());
    }

    /** gives the variance of proportion of open sites needed for percolation based on trials.
    */

    private double variance() {
        return StdStats.var(openSitesFraction);
    }
 
    /** gives the lower bound of 95% confidence interval.
    */
 
    public double confidenceLo() {
        return this.mean() - (1.96*this.stddev())/Math.sqrt(T);
    }

    /** gives upper bound of 95% confidence interval. 
    */
   
    public double confidenceHi() {
        return this.mean() + (1.96*this.stddev())/Math.sqrt(T);
    }
   
    /** 
    * takes integers T and N from user input and runs the statistical analysis and prints 
    * the mean, std deviation, and confidence interval. 
    */
       
    public static void main(String[] args) {
        int a1 = Integer.parseInt(args[0]);
        int a2 = Integer.parseInt(args[1]);
        PercolationStats a = new PercolationStats(a1, a2);
        System.out.println("mean = " + a.mean());
        System.out.println("stddev = " +  a.stddev());
        System.out.println("95% confidence interval = " + a.confidenceLo() + ", " + a.confidenceHi());
    }
}
