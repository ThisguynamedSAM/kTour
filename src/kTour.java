import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class kTour extends JFrame{

    private static int[] c_loc = { 0, 0 };  // array that will hold knights current location
    private static int[] lastMove = { 0, 0 }; // used to reset location after calling getAccess method
    private final static int[][] MOVES = { { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, 2 },
            { -2, 1 }, { -2, -1 }, { -1, -2 } }; // all possible moves
    private static int[][] testBoard = new int[8][8]; // this is a temporary board size 8X8
    // will be removed after object -> int[][] problem is solved
    private static int[][] access = new int[8][8]; // accessibility matrix
    private static int ctr = 0; // will count moves and add data to squares
    private static int dim = 8; // hold dimensions of the board
    private static boolean error = false; // in case everything isn't perfect

    /* for GUI */
    private Container pane;
    private static JLabel[][] board;
    private JPanel boardPanel;

    /*
        add method that would look ahead in case of a tie in adj matrix
            this method will make a new adj matrix for each possible move
     */

    // default constructor
    kTour() {
        // c_loc initialization can be change for user input
        c_loc = new int[] { 0, 0};
        Board b = new Board(8); // creates object of
        Board.reset( access ); // reset the board before using
        getAccess(); // computes access for each square
        setLoc( 0, 0 ); // returns back to starting position

        setTitle( "Knights Tour" );
        setLayout( new BorderLayout() );
        setSize( 500, 500 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // stops program on close

        pane = getContentPane();
        boardPanel = new JPanel();
        boardPanel.setLayout( new GridLayout( dim, dim ) );
        board = new JLabel[dim][dim];

        // color the labels accordingly
        Color c;
        for(int i = 0; i < dim; i++)
            for(int k = 0; k < dim; k++) {
                board[i][k] = new JLabel( "", SwingConstants.CENTER );
                if(i % 2 == 0) // even rows
                    if(k % 2 != 0) // odd cell
                        c = Color.WHITE;
                    else          // even cell
                        c = Color.GRAY;
                    // For even rows, set even cells to white, and odd cells to black.
                else // odd rows
                    if(k % 2 == 0) // even cell
                        c = Color.WHITE;
                    else          // odd cell
                        c = Color.GRAY;

                board[i][k].setOpaque( true ); // Make the label opaque
                board[i][k].setBackground( c ); // set label color
                boardPanel.add( board[i][k] );
            }
        // resets the board to 0
        Board.reset( testBoard ); // assigns 0's to the board throughout the grid
        testBoard[c_loc[0]][c_loc[1]] = ++ctr; // assigns first square with data
        start();
        fillGUI(); // copies contents of testboard to GUI
        // combines all layers to container
        pane.add( boardPanel, BorderLayout.CENTER );
        // Display and lock the window.
        setVisible( true );
        setResizable( false );
    }

    private static void fillGUI() {
        // after tour is run this method copies results to GUI template
        for ( int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j].setText( Integer.toString( testBoard[i][j] ) );
    }


    private static void start() {
        // This method gets a legal move for each square
        // if no legal move error becomes true loop ends

        while(!error) {
            getAccess(); // adjust access matrix
            setLoc( lastMove ); // undo the change to location getAccess made
            knightsMove(); // find and set move
        }

        /*       for debugging
         * Board.printBoard( testBoard );
         * Board.printBoard( access );
         * if(Board.isFull( testBoard )) // if solved
         *     System.out.println( "Your primitive puzzle was no match for my (Insert processing power here) brain!" );
         * else
         *     System.out.println( "I- I- I.....just woke from sleep mode..." );
         * Board.printBoard( testBoard );
         * Board.printBoard( access );  */
    }

    private static void getAccess() {
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
        // compute access for each square
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                if(testBoard[i][j] == 0) {
                    setLoc( i, j );
                    computeAccess(); // compute access for location set above
                } else {
                    access[i][j] = -1;
                }
            }
        }
    }

    private static void computeAccess() {
        // This method computes how many moves each square can legally make
        // square -> combine with legal MOVES -> check is legal -> increment square access
        int m = 0; // m for amount of moves
        for(int i = 0; i < 8; i++) // 8 being the max combinations of moves
            if(isLegal( combine( MOVES[i] ) )) // if legal move is found
                ++m; // increment

        access[c_loc[0]][c_loc[1]] = m; // sets current square to m
    }

    // ++++++++++++++++ overloaded methods for setAccess method ++++++++++++++++++++

    private static void setLoc(int x, int y) {

        c_loc[0] = x;
        c_loc[1] = y;
    }
    // +++++++++++ end of overloaded methods +++++++++++++++

    private static void knightsMove() {
        // legalSquares is an array that holds the position of possible move(s)
        // potential moves are then compared
        // and the best move ( or first move in case of a tie ) is made

        boolean found = false;
        int choice[] = { 0, 0 }; // will hold x, y of best move
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

    private static int[] compare(int[] a, int[] b) {
    // this is wrong but it works when change < to > program does not finish
        if ( access[a[0]][a[1]] <= access[b[0]][b[1]] ) {
            return b;
        }
        return a;
    }

    private static boolean isLegal(int move[]) {
        // This method first checks if move is greater than or equal to 0 and less than 8 (is within grid)
        // Than checks if that square already contains data
        if(( move[0] >= 0 && move[0] < 8 ) && ( move[1] >= 0 && move[1] < 8 )) { // within bounds?
            if(testBoard[move[0]][move[1]] == 0) // is empty
                return true;
        }
        return false;
    }

    private static void setLoc(int[] loc) {
        // assigns current location and last move to location passed to method
        c_loc = Arrays.copyOf( loc, loc.length );
        lastMove = Arrays.copyOf( loc, loc.length );
    }

    private static int[] combine(int move[]) {
        // This method will take current location and combine it with potential move
        // Method will be ambiguous if their already exist a method in the jdk that accomplishes this.
        int[] a = { c_loc[0] + move[0],
                c_loc[1] + move[1] };
        return a;
    }

    public static void main(String[] args) {

        new kTour(); // calls constructor of kTour
    }
}