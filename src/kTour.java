import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/** implement action listener the start screen will prompt the user to press the space bar
 *  when the space bar is pressed the thread will begin to run.
 *
 * */
public class kTour extends JPanel {

//    private static int BOARD_DIMENSIONS = 8; // hold dimensions of the board
    private final int BOARD_DIMENSIONS;

    private int ctr = 0; // will count moves and add data to squares
    private int[] c_loc = { 0, 0 };  // array that will hold knights current location
    private int[] lastMove = { 0, 0 }; // used to reset location after calling getAccess method
    private int[][] testBoard;
    // will be removed after object -> int[][] problem is solved
    private int[][] access;

    private final int[][] MOVES = { { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, 2 },
            { -2, 1 }, { -2, -1 }, { -1, -2 } }; // all possible moves
    private boolean error = false; // in case everything isn't perfect

    /** for GUI */
    private static JLabel[][] board;
//    private JPanel boardPanel;

    /** threading */
    private Runnable tourThread;
    private boolean tourComplete = false;

    public kTour(int BOARD_DIMENSIONS) {
//        setLayout(new BorderLayout());
        setSize(new Dimension(500, 500));

        this.BOARD_DIMENSIONS = BOARD_DIMENSIONS;
        setLayout(new GridLayout(BOARD_DIMENSIONS, BOARD_DIMENSIONS));
        board     = new JLabel[BOARD_DIMENSIONS][BOARD_DIMENSIONS];
        testBoard = new int[BOARD_DIMENSIONS][BOARD_DIMENSIONS]; // this is a temporary board size 8X8
        access    = new int[BOARD_DIMENSIONS][BOARD_DIMENSIONS]; // accessibility matrix

        // create that checker board pattern
        Color c;
        for(int i = 0; i < BOARD_DIMENSIONS; i++) {
            for(int k = 0; k < BOARD_DIMENSIONS; k++) {

                board[i][k] = new JLabel( "", SwingConstants.CENTER );

                if(i % 2 == 0) {
                    if (k % 2 != 0)
                        c = Color.WHITE;
                    else
                        c = Color.GRAY;
                } else {
                    if (k % 2 == 0)
                        c = Color.WHITE;
                    else
                        c = Color.GRAY;
                }

                board[i][k].setOpaque( true ); // Make the label opaque
                board[i][k].setBackground( c ); // set label color
                add( board[i][k] );
            }
        }

        testBoard[0][0] = ++ctr; // assigns starting position with 1

        tourThread = new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        };

        tourThread.run();

        fillGUI(); // copies contents of testboard to GUI
    }

    private void fillGUI() {
        // after tour is run this method copies results to GUI template
        for(int i = 0; i < BOARD_DIMENSIONS; i++) {
            for(int j = 0; j < BOARD_DIMENSIONS; j++) {
                // if board is not complete
                if(testBoard[i][j] == 0)
                    // do not add 0 to GUI
                    continue;
                board[i][j].setText( Integer.toString( testBoard[i][j] ) );
            }
        }
    }

    private  void start () throws InterruptedException {
        // This method gets a legal move for each square
        // if no legal move error becomes true loop ends

        while( !error ) {
            getAccess();        // adjust access matrix
            setLoc( lastMove ); // undo the change to location getAccess made
            knightsMove();      // find and set move
            Thread.sleep(1);
        }
    }

    private void getAccess() {
        /*
        starting Board Accessibility
          2 3 4 4 4 4 3 2
          3 4 6 6 6 6 4 3
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          4 6 8 8 8 8 6 4
          3 4 6 6 6 6 4 3
          2 3 4 4 4 4 3 2
        */
        // compute access for each square

        for(int i = 0; i < BOARD_DIMENSIONS; i++) {
            for(int j = 0; j < BOARD_DIMENSIONS; j++) {
                if(testBoard[i][j] == 0) { // 0 meaning empty
                    setLoc( i, j );
                    computeAccess(); // compute access for location set above
                } else {
                    access[i][j] = 0; // -1 for not accessible
                }
            }
        }
    }

    private void computeAccess() {
        // This method computes how many moves each square can legally make
        // square -> combine with legal MOVES -> check is legal -> increment square access
        int m = 0; // m for amount of moves
        for(int i = 0; i < 8; i++) // 8 being the max combinations of moves
            if(isLegal( combine( MOVES[i] ) )) // if legal move is found
                m++; // increment

        access[c_loc[0]][c_loc[1]] = m; // sets current square to m
    }

    // ++++++++++++++++ overloaded methods for setAccess method ++++++++++++++++++++

    private void setLoc(int x, int y) {
        c_loc[0] = x;
        c_loc[1] = y;
    }
    // +++++++++++ end of overloaded methods +++++++++++++++

    private void knightsMove() {
        // legalSquares is an array that holds the position of possible move(s)
        // potential moves are then compared
        // and the best move ( or first move in case of a tie ) is made

        boolean found = false;
        int choice[]  = { 0, 0 }; // will hold x, y of best move
        int choice2[]; // used to compare with choice

        for(int i = 0; i < 8; i++) { // 8 being the max combinations of moves
            if(isLegal( combine( MOVES[i] ) )) {   // if legal move is found
                if(choice[0] == 0 && choice[1] == 0) { // assume first choice is the best
                    choice = combine( MOVES[i] );
                    found = true; // only necessary for first iteration
                } else { // set legal move to choice 2 if 1st choice contains data
                    choice2 = combine( MOVES[i] );
                    choice = compare( choice, choice2 ); // best choice equals value returned
                }
            }
            if(( i >= 7 ) && ( !found )) {
                error = true;
                return;
            }
        }
        setLoc( choice ); // set choice
        testBoard[c_loc[0]][c_loc[1]] = ++ctr; // assign counter to given square
    }

    private int[] compare(int[] a, int[] b) {
        // this is wrong but it works when change < to > program does not finish
        if(access[a[0]][a[1]] >= access[b[0]][b[1]]) {
            return b;
        }
        return a;
    }

    private boolean isLegal(int move[]) {
        // This method first checks if move is greater than or equal to 0 and less than 8 (is within grid)
        // Than checks if that square already contains data
        if(( move[0] >= 0 && move[0] < BOARD_DIMENSIONS) && ( move[1] >= 0 && move[1] < BOARD_DIMENSIONS)) { // within bounds?
            if(testBoard[move[0]][move[1]] == 0) // is empty
                return true;
        }
        return false;
    }

    private void setLoc(int[] loc) {
        // assigns current location and last move to location passed to method
        c_loc = Arrays.copyOf( loc, loc.length );
        lastMove = Arrays.copyOf( loc, loc.length );
    }

    private int[] combine(int move[]) {
        // This method will take current location and combine it with potential move
        int[] a = { c_loc[0] + move[0],
                c_loc[1] + move[1] };
        return a;
    }
}