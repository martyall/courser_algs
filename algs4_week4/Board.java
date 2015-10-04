/*************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    java Board < input.txt
 *  
 *  Creates a Board class representing an n-puzzle state. 
 *  (see http://en.wikipedia.org/wiki/15_puzzle)
 *  
 *************************************************************************/

import java.util.*;

/**
 *  The <tt>Board</tt> class represents the state of an n-puzzle.
 *  It supports methods for viewing infromation about the board, 
 *  like <em>toString</em> as well as its hamming and manhattan norm, 
 *  along with methods for creating a "twin" board and generating all 
 *  "neighboring boards" in the game tree. Note that here 0 represents
 *  the missing tile in the puzzle.
 */

public class Board {

    private int N;
    private int[][] board;    

    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++) { 
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];  
            }
        }
    }

    // @return int the dimension of the board
    public int dimension() {
        return N;    
    }       
 
    // @return int the hamming distance from the 
    // board to the solution
    public int hamming() {
        int M = 0;
        for (int i = 0; i < N; i++) { 
            for (int j = 0; j < N; j++) {
                if ((board[i][j] != N*(i) + j + 1) & (board[i][j] != 0)) {
                    M++;
                }
            }
	}
        return M;
    }   
      
    // @return int the manhattan distance from the 
    // board to the solution
    public int manhattan() {
        int M = 0;
        for (int i = 0; i < N; i++) { 
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 0) {
                    int temp = board[i][j];
                    int tempRow = (temp-1)/N;
                    int tempCol = (temp-1) % N;
                    M += Math.abs(i - tempRow) + Math.abs(j - tempCol);
                } else {
                    continue;
                }
	    }
	}
        return M;
    }

    // @return bool the board is the solution board
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // @return Board return the "twin" board, given
    // by swapping two non-empty neighboring spaces
    // in the board. It is a theorem that either a 
    // board is solvable, or a twin but not both.
    public Board twin() {
        Board twinBoard = new Board(this.board); 
        int M = nonZeroRow();
        int temp = twinBoard.board[M][1];
        twinBoard.board[M][1] = twinBoard.board[M][0];
        twinBoard.board[M][0] = temp;
        return twinBoard;
    }
  
    // @return int the first row which does not contain 0 (i.e.
    // either the first or second row. 
    private int nonZeroRow() {
        int row = findZero()[0];
        int i = 0;
        while (true) {
            if (i != row) { 
                return i;
            }
            i++;
        }
    }

    // @return true if the board = y, else false
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
	    }
	}
        return true;
    }

    // exchanges space (i1,j1) with (i2,j2) in the board
    private Board exchange(int i1, int j1, int i2, int j2) {
        Board newBoard = new Board(this.board);
        int temp = newBoard.board[i1][j1];
	newBoard.board[i1][j1] = this.board[i2][j2];
        newBoard.board[i2][j2] = temp;
        return newBoard;
    }
    
    // @return int the row number where the empty (=0) space
    // is located
    private int[] findZero(){
        int[] temp = new int[2]; 
        for (int i=0; i<N; i++) {
            for (int j =0; j< N; j++) {
                if (board[i][j] == 0) {
		    temp[0] = i;
                    temp[1] = j;
                }
	    }
	}
        return temp;
    }		
       
    // @return Iterable<Board> returns all the neighbors
    // of the board in the game tree.
    public Iterable<Board> neighbors() {
        Stack<Board> neighboringBoards= new Stack<Board>();
        int[] x = findZero();
        if (x[0] == 0) {
            if (x[1] == 0) {
                neighboringBoards.push(exchange(0,0,0,1));
                neighboringBoards.push(exchange(0,0,1,0));
            } else if (x[1] == N-1) {
                neighboringBoards.push(exchange(0,N-1,1,N-1));
                neighboringBoards.push(exchange(0,N-1,0,N-2));
            } else {
                neighboringBoards.push(exchange(0,x[1],0,x[1]-1));
                neighboringBoards.push(exchange(0,x[1],0,x[1]+1));
                neighboringBoards.push(exchange(0,x[1],1,x[1]));

            }        
	} else if  (x[0] == N-1) {
            if (x[1] == N-1) {
                neighboringBoards.push(exchange(N-1,N-1,N-1,N-2));
                neighboringBoards.push(exchange(N-1,N-1,N-2,N-1));
            } else if (x[1] == 0) {
                neighboringBoards.push(exchange(N-1,0,N-1,1));
                neighboringBoards.push(exchange(N-1,0,N-2,0));
            } else {
                neighboringBoards.push(exchange(N-1,x[1],N-1,x[1]-1));
                neighboringBoards.push(exchange(N-1,x[1],N-1,x[1]+1));
                neighboringBoards.push(exchange(N-1,x[1],N-2,x[1]));
	    }
        } else if (x[1] == 0) {
	    neighboringBoards.push(exchange(x[0],0,x[0]-1,0));
            neighboringBoards.push(exchange(x[0],0,x[0]+1,0));
            neighboringBoards.push(exchange(x[0],0,x[0],1));
        } else if (x[1] == N-1) {
	    neighboringBoards.push(exchange(x[0],N-1,x[0]-1,N-1));
            neighboringBoards.push(exchange(x[0],N-1,x[0]+1,N-1));
            neighboringBoards.push(exchange(x[0],N-1,x[0],N-2));
	} else {
            for (int i = x[0] - 1; i <= x[0] + 1; i++) {
                for (int j = x[1] - 1; j <= x[1] + 1; j++) {
		    if ((i == x[0] & j != x[1]) || (i != x[0] & j == x[1])) {
                        neighboringBoards.push(exchange(x[0],x[1],i,j));
                    }
                }
            }
        }
        return neighboringBoards;
    }

 
    // @return String returns a string representation of the board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        
        String fileName = args[0];
        In in = new In(fileName);
        int d = in.readInt();
        int[][] boardInts = new int[d][d];
        
        for (int i = 0; i < d; i++) { 
            for (int j = 0; j < d; j++) {
		boardInts[i][j] = in.readInt();
            }
        } 

        Board B = new Board(boardInts);
        System.out.print(B.toString());
        int[] foo = B.findZero();
        System.out.println(B.equals(B));
        System.out.println(B.hamming());
        System.out.println(B.manhattan());

    }
}
