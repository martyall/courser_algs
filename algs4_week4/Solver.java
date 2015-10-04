/*************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver  input.txt
 *  
 *  Solves the "n-puzzle" (see http://en.wikipedia.org/wiki/15_puzzle)
 *  
 *  The solution uses the A* search algorithm on the game tree, which 
 *  makes use of the priority queue implemented in MinPQ.java
 *
 *  Default weights are calculated using the Manhattan metric. Implementation
 *  also allows use of the hamming metric.
 *
 *************************************************************************/

import java.util.*;


/**
 *  The <tt>Solver</tt> class is given a Board (Board is the class 
 *  which represents the physical board of the puzzle) and solves it 
 *  at the time of construction, or proves it is not solvable. 
 *  It supports <em>moves</em> which gives the minimal number of 
 *  moves needed to solve the puzzle (if possible), <em>isSolvable<\em>
 *  which states whether a given puzzle is solvable, and <em>solution<\em>
 *  which returns an iterable containing the sequence of moves solving the
 *  puzzle (if possible)

 */
public class Solver {

    private int moves;
    private MinPQ<Node> nearNodes = new MinPQ();
    private MinPQ<Node> nearNodesTwin = new MinPQ();
    private Stack<Board> solutionStack;
    private Iterable<Board> moveSequence;
    private boolean isSolvableBool;

    public Solver(Board initial) {
        this.nearNodes.insert(new Node(initial, 0, null));
        this.nearNodesTwin.insert(new Node(initial.twin(), 0, null));
        this.isSolvableBool = false;
        this.solutionStack = solve();
        this.moveSequence = steps();
    }

    // Internal class used for nodes in the game tree
    private class Node implements Comparable<Node>{
        
        private Board board;
        private int numMoves;
        private Node lastNode;
        private int manhattanWeight;
        private int hammingWeight;

        private Node(Board board, int numMoves, Node lastNode) { 
            this.board = board;
            this.numMoves = numMoves;
            this.lastNode = lastNode;
            this.manhattanWeight = this.board.manhattan();
            this.hammingWeight = this.board.hamming();         
        }
        
        // Weights are calculated using the Manhattan metric of a node
        // plus the number of moves needed to arrive at this node
        public int compareTo(Node b) {
            if (this.manhattanWeight + numMoves < b.manhattanWeight + b.numMoves) {
                return -1;
            } else {
                return 1;
            }
	}
        
        // Can swith to hamming metric if desired
	private Comparator<Node> HAMMING = new Hamming();
	
        private class Hamming implements Comparator<Node> {
            public int compare(Node a, Node b) {
                if (a.hammingWeight  + numMoves <  b.hammingWeight + b.numMoves) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
         
    //@return bool true if the puzzle can be solved, else false
    public boolean isSolvable(){;
        return isSolvableBool;
    }
	
    //@return int the minimal number of moves needed to solve the puzzle             
    public int moves(){
        if (isSolvable()) { 
            return moves;
        } else {
            return -1;
        }
    }

    // implements A* algorithm on the game tree with root the 
    // Board given at construction. Also solves the "twin" of 
    // the Board given at construction. Mathematically only one of
    // the Board or it's twin can be solved. When one of them is solved
    // the fields are updated accordingly. 
    private Stack<Board> solve() {
        Node current;
	Node currentTwin;
 
        while (true) {
            current = nearNodes.delMin();
            currentTwin = nearNodesTwin.delMin();

	    for (Board b : current.board.neighbors()) {
                if (current.lastNode == null) {
                    nearNodes.insert(new Node(b, current.numMoves + 1, current)); 
                } else if (!b.equals(current.lastNode.board)) {
                    nearNodes.insert(new Node(b, current.numMoves + 1, current));
                }
	    }

            for (Board b : currentTwin.board.neighbors()) {
	        
                if (currentTwin.lastNode == null) {
                    nearNodesTwin.insert(new Node(b, currentTwin.numMoves + 1, currentTwin));   
                } else if (!b.equals(currentTwin.lastNode.board)) {
                    nearNodesTwin.insert(new Node(b, currentTwin.numMoves + 1, currentTwin));
                }
	    }

            if (current.board.isGoal()) {
                Stack<Board> sequence = new Stack<Board>();
                while (current != null) {
                    sequence.push(current.board);
                    current = current.lastNode;
                    if (current != null) {
                        moves += 1;
                    }
                }
                isSolvableBool = true;
                return sequence;
	    } else if (currentTwin.board.isGoal()) {
                isSolvableBool = false;
                return null;
	    }
        }
    }
   
    // @return Iterable<Board> returns a list containing the 
    // sequence of moves leading to the solution
    private Iterable<Board> steps() {
        List<Board> solutionList = new ArrayList<Board>();
	    while (!solutionStack.isEmpty()) {
                solutionList.add(solutionStack.pop()); 
            }
        return solutionList;
    } 

    // @return Iterable<Board> reutrns the solution sequence
    public Iterable<Board> solution() {
        return this.moveSequence;
    }
        
    
    public static void main(String[] args) {
    // create initial board from file

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
