
public class Board {

    protected int [] [] b; // 2d array that holds demensions of the board

    public Board(int m) {
        b = new int[m][m];
        reset( b );
        printBoard( b );
    }

    public static void reset(int b[][]) {
        for (int i = 0; i < b[0].length; i ++)
            for (int j = 0; j < b[0].length; j++) {
                b[i][j] = 0;
            }
    }

    public static boolean isFull(int b[][]) {
        boolean isFull = true;
        while(isFull) {
            // this method checks if the board is full
            // does not contain a zero
            for (int i = 0; i < b[0].length; i ++)
                for (int j = 0; j < b[0].length; j++)
                    if (b[i][j] == 0) {
                        isFull = false;
                        break;
                    }
        }
        return isFull;
    }

    public static void printBoard(int b[][]) {
        // This method will print the board once the knights tour has been run
        // It has been modified to print 1 in the first block and 64 in the last

        for (int i = 0; i < b[0].length; i ++) {
            System.out.println( " --------------------------------- " );
            for (int j = 0; j < b[0].length; j++) {
                if( j == 0) System.out.print( " | " );

                if ( j == 0 && i == 0) {
                    System.out.print("1 | ");
                    continue;
                }
                if ( j == 7 && i == 7) {
                    System.out.print("64|");
                    continue;
                }
                System.out.print(b[i][j] + " | " );
            }
            System.out.print( "\n" );
        }
        System.out.println( " --------------------------------- " );
    }
}