
public class kTour {

    private static int[] c_loc = { 0,0 }; // array that will hold knights current position, starting at 0,0
    private final static int[][] MOVES = { {1,-2},{2,-1},{2,1},{1,2},{-1,2},
            {-2,1},{-2,-1},{-1,-2} }; // all possible moves

    private static void access() {
    /*
      board accessibility
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
    /*
      -run through list of moves
      -check if moves are
           a: out of bounds
           b: if square is empty
       out of available moves check
           which move has the least availability
               choose least available move
           if ( equal availability )
              choose move at random
    */



    }

    private static void isLegal() {



    }

}