/*
    This class is an example of getting a factorial using recursion
 */
public class Factoral {

    private static long factorial(int n) {

        if(n == 1) // base case
            return 1;
        else
            return n * factorial( n - 1 );
    }

    private static String s(String n) {
        // recursive function call that apends "i"
        // until string until string is length of 5 chars

        if(n.length() == 5) return n;

        else return s( n + "i" );
    }


    public static void print() {

        System.out.println( s( "i" ) );
    }
}