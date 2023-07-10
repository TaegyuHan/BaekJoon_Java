package implement.p2167;

import java.io.*;
import java.util.Arrays;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 2차원 배열의 합".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2167">Link</a>
 * 작성자: gksxorb147
 * BigO: O() : N * M * K = 60_000 * 10_000
 */
class Board {
    private final int[][] board;
    private final int row;
    private final int col;

    Board (int row, int col) {
        this.row = row;
        this.col = col;
        this.board = new int[row][col];  // 300 * 300 = 60_000
    }

    public void setRowData(int rowIdx, int[] rowData) {
        board[rowIdx] = rowData;
    }

    public void showBoard() {
        for (int i = 0; i < row; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }

    public int boardSum(int x1, int y1, int x2, int y2) {
        int sumInt = 0;

        int startX = x1 - 1;
        int endX = x2 - 1;

        int startY = y1 - 1;
        int endY = y2 - 1;

        for (int row = startX; row <= endX; row++) {
            for (int col = startY; col <= endY; col++) {
                sumInt += board[row][col];
            }
        }
        return sumInt;
    }
}

public class Main {

    private final static String TEST_PATH = "/p2167/input/1.txt";

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] BoardSize = br.readLine().split(" ");

        int row = Integer.parseInt(BoardSize[0]); // 300
        int col = Integer.parseInt(BoardSize[1]); // 300

        Board board = new Board(row, col);

        for (int rdix = 0; rdix < row; rdix++) {
            String[] rowData = br.readLine().split(" ");
            int[] intRowData = new int[col];
            for (int cdix = 0; cdix < col; cdix++) {
                intRowData[cdix] = Integer.parseInt(rowData[cdix]);
            }
            board.setRowData(rdix, intRowData);
        }

        int checkCount = Integer.parseInt(br.readLine());  // K = 10_000
        for (int count = 0; count < checkCount; count++) {
            String[] range = br.readLine().split(" ");
            int[] intRange = new int[4];
            for (int i = 0; i < 4; i++) {
                intRange[i] = Integer.parseInt(range[i]);
            }

            System.out.println(board.boardSum(intRange[0], intRange[1] ,intRange[2], intRange[3]));
        }
        br.close();
    }
}