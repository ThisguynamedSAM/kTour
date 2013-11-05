
public class Board {

    protected int[][] b; // 2d array that holds dimensions of the board

    public Board(int m) {

        b = new int[m][m];
        reset( b ); // fills board with zero's
//        kTour.start( this ); // calls start method and passes object b
    }

    public static void reset(int[][] b) {

        for(int i = 0; i < b[0].length; i++)
            for(int j = 0; j < b[0].length; j++) {
                b[i][j] = 0;
            }
    }

    public static void reset(int[][] b, int v) {

        for(int i = 0; i < b[0].length; i++)
            for(int j = 0; j < b[0].length; j++) {
                b[i][j] = v;
            }
    }

    public static void printBoard(int b[][]) {
        // This method will print the board once the knights tour has been run

        for(int i = 0; i < b[0].length; i++) {
            System.out.println( " --------------------------------- " );
            for(int j = 0; j < b[0].length; j++) {
                if(j == 0) System.out.print( " | " );
                System.out.print( b[i][j] + " | " );
            }
            System.out.print( "\n" );
        }
        System.out.println( " --------------------------------- " );
    }

    public static boolean isFull(int b[][]) {

        boolean isFull = true;
        while(isFull) {
            // this method checks if the board is full
            // does not contain a zero
            for(int i = 0; i < b[0].length; i++)
                for(int j = 0; j < b[0].length; j++)
                    if(b[i][j] == 0) {
                        isFull = false;
                        break;
                    }
        }
        return isFull;
    }
}