
/**
 * Group project 2
 * Dr. Meyers
 * Laguardia Community College
 *
 * This program completes the knights tour and prints the results
 */


public class kTour {

    private static int[] c_loc = { 0, 0 }; // array that will hold knights current location, starting at 0,0
    private final static int[][] MOVES = { { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, 2 },
            { -2, 1 }, { -2, -1 }, { -1, -2 } }; // all possible moves
    private static boolean error = false; // in case everything isn't perfect
    private static int[][] testBoard = new int[8][8]; // this is a temporary board size 8X8
    // will be removed after object -> int[][] problem is solved

    private static void access() { // This method is being ignored for now
        /*
          Board Accessibility
          2 3 4 4 4 4 3 2
          3 4 6 6 6 6 4 3
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          3 4 6 6 6 6 4 3
          2 3 4 4 4 4 3 2
        */
    }

    private static void knightsMove() {
        // This method calls the combine method with a potential move the result is sent to the isLegal method
        // if move is legal that move will be made
        // otherwise it will try all possible combinations
        // if no legal move is made error = true
        int i = 0; // subscript to iterate through array MOVES
        while(true) {
            if(i > 7) {
                error = true; // if no moves are available something went wrong
                break; // why can't you be smarter computer?
            }
            if(isLegal( combine( MOVES[i] ) )) // if legal move is found
                break;

            i++; // move was not legal increment possible moves array
        }
    }

    private static boolean isLegal(int move[]) {
        // This method first checks if move is greater than or equal to 0 and less than 8 (is within grid)
        // Than checks if that square already contains data
        if(( move[0] >= 0 && move[0] < 8 ) && ( move[1] >= 0 && move[1] < 8 )) {
            if(testBoard[move[0]][move[1]] == 0) { // does square contain data?
                setLoc( move );
                return true;
            }
        }
        return false;
    }

    private static void setLoc(int[] loc) {

        c_loc[0] = loc[0];
        c_loc[1] = loc[1];
    }

    private static int[] combine(int move[]) {
        // This method will take current location and combine it with potential move
        // Method will be ambiguous if their already exist a method in the jdk that accomplishes this.
        int[] a = { c_loc[0] + move[0],
                c_loc[1] + move[1] };
        return a;
    }

    public static void start() {
        // This method gets a legal move for each of the 64 squares and prints the results
        // If board cannot be complete loop is stopped
        // board is printing any way
        Board.reset( testBoard ); // assigns 0's to the board throughout the grid
        int ctr = 0; // will count moves and add data to squares
        testBoard[c_loc[0]][c_loc[1]] = ++ctr; // assigns first square with data

        while(ctr < 64) {
            // total of 64 squares
            knightsMove();
            if(error)
                break; // if error end tour and print
            testBoard[c_loc[0]][c_loc[1]] = ++ctr; // assigns counter to new location
        }

        if ( Board.isFull( testBoard ) ) // if solved
            System.out.println("Your primitive puzzle was no match for my (Insert processing power here) brain!");
        else
            System.out.println("I- I- I.....just woke from sleep mode...");
        Board.printBoard( testBoard );
    }

    public static void main(String[] args) {

//        Board board = new Board(M); // creates an object of class Board

        start();
        System.exit( 0 );
    }
}