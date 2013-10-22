/**
 This is program is an algorithm to solve the knights tour problem
 It will show the starting position, and print the entire board with each move
 */

public class Main {

    private final static byte M = 8; // amount of squares in each row and column

    public static void main(String[] args) {

        Board board = new Board(M); // creates an object of class Board

        System.out.println(board);
    }



}