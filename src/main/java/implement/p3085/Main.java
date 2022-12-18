package implement.p3085;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 사탕 게임".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/3085">Link</a>
 * 작성자: gksxorb159
 * BigO: O(4(n^4))
 */
public class Main {

    public static String TEST_PATH = "/p3085/input/7.txt";

    public static int[][] MOVE = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public static char[][] board;

    public static void main(String[] args) throws IOException {

        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = inputLine(br);
        int N = inputInt(st);
        board = new char[N][N];
        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }

        int nrow, ncol, answer;
        answer = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                for (int[] move : MOVE) {
                    nrow = row + move[0];
                    ncol = col + move[1];
                    if (!(((0 <= nrow) && (nrow < N)) && (0 <= ncol) && (ncol < N))) continue;

                    candyChange(row, col, nrow, ncol);
                    answer = Math.max(answer, candyCheck());
                    candyChange(row, col, nrow, ncol);
                }
            }
        }
        System.out.println(answer);
    }

    public static int candyCheck() {

//        showBoard();

        int max, rowTmp, colTmp;
        char rowChar, colChar;

        max = 0;
        for (int row = 0; row < board.length; row++) {
            rowTmp = 0;
            colTmp = 0;
            colChar = 0;
            rowChar = 0;
            for (int col = 0; col < board.length; col++) {
                if (col == 0) {
                    colChar = board[row][col];
                    colTmp = 1;
                } else if (colChar == board[row][col]) {
                    colTmp += 1;
                } else {
                    colChar = board[row][col];
                    colTmp = 1;
                }

                if (row == 0) {
                    rowChar = board[col][row];
                    rowTmp = 1;
                } else if (rowChar == board[col][row]) {
                    rowTmp += 1;
                } else {
                    rowChar = board[col][row];
                    rowTmp = 1;
                }

                max = Math.max(max, rowTmp);
                max = Math.max(max, colTmp);
            }
        }
        return max;
    }

    public static void showBoard() {
        for (char[] rowData : board) {
            System.out.println(Arrays.toString(rowData));
        }

    }

    public static void candyChange(int row1, int col1, int row2, int col2) {
        char tmp = board[row2][col2];
        board[row2][col2] = board[row1][col1];
        board[row1][col1] = tmp;
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }
}