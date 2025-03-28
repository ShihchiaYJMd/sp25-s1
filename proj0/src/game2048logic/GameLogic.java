package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        /* Task 2
        // save the original block value
        int tileValue = board[r][c];
        // move the tile up until the boundary or collision
        while (r > 0 && board[r-1][c] == 0) {
            board[r-1][c] = tileValue;
            board[r][c] = 0;
            r--;
        }
        */
        // -------------------------------------------------------- //
        /* Task 3: Merging Tiles
        int tileValue = board[r][c];
        int coeff = 0;
        int rowNum = 0;
        while (r > 0) {
            if (board[r-1][c] == tileValue) {
                tileValue *= 2;
                board[r-1][c] = tileValue;
                board[r][c] = 0;
                coeff = 1;
                rowNum = r - 1;
                r--;
            }
            else if (board[r-1][c] == 0) {
                board[r - 1][c] = tileValue;
                board[r][c] = 0;
                r--;
            }
            else {
                break;
            }
        }
        return coeff + rowNum;
        */
        /* Solution 3
        int tileValue = board[r][c];
        if (tileValue == 0) {
            return 0;
        }
        while (r > 0 && board[r-1][c] == 0) {
            board[r-1][c] = tileValue;
            board[r][c] = 0;
            r--;
        }
        if (r > 0 && board[r-1][c] == tileValue) {
            board[r-1][c] *= 2;
            board[r][c] = 0;
            return r;
        }
        return 0;
        */
        // -------------------------------------------------------- //
        /* Task 4: Merging Tiles up to MinR */
        int tileValue = board[r][c];
        if (tileValue == 0) {
            return 0;
        }
        while (r > minR) {
            // merge happens only once and jumps out of the loop
            if (board[r - 1][c] == tileValue) {
                tileValue *= 2;
                board[r - 1][c] = tileValue;
                board[r][c] = 0;
                return r;
            }
            if (board[r - 1][c] != 0) {
                break;
            }

            board[r - 1][c] = tileValue;
            board[r--][c] = 0;
        }
        return 0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        int minR = 0;
        for (int r = 1; r < board.length; r++) {
            if (board[r][c] != 0) {
                int result = moveTileUpAsFarAsPossible(board, r, c, minR);
                if (result > 0) {
                    minR = result;
                }
            }
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        for (int col = 0; col < board.length; col++) {
            tiltColumn(board, col);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        } else if (side == Side.SOUTH) {
            rotateRight(board);
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            rotateLeft(board);
        } else {
            tiltUp(board);
        }
    }
}
