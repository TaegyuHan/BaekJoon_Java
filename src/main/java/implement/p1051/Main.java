package implement.p1051;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 정사각형".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1051">Link</a>
 * 작성자: gksxorb159
 * BigO: O(M * N) : 2500
 */
public class Main {
    public static String TEST_PATH = "/p1051/input/5.txt";
    public static StringTokenizer st;
    public static int ROW, COL;
    public static char[][] BOARD;
    public static int[][] TMP = {
            {0, 1},
            {1, 1},
            {1, 0}
    };

    public static void main(String[] arg) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = readLine(br);
        ROW = readInt(st);
        COL = readInt(st);

        BOARD = new char[ROW][COL];
        for (int row = 0; row < ROW; row++) {  // N
            BOARD[row] = br.readLine().toCharArray();
        }
        br.close();

        int answer = 1;
        int maxSize = Math.min(ROW, COL);
        for (int row = 0; row < ROW; row++) { // N * M
            for (int col = 0; col < COL; col++) {
                answer = Math.max(answer, checkSize(row, col, maxSize));
            }
        }
        System.out.print(answer);
    }

    public static int checkSize(int row, int col, int maxSize) {
        int size = 0;
        char check = BOARD[row][col];

        checkLoop:
        for (int i = 0; i < maxSize; i++) {
            for (int[] tmp : TMP) {
                int nrow = row + (tmp[0] * i);
                int ncol = col + (tmp[1] * i);
                if (!(nrow < ROW && ncol < COL)) {
                    continue checkLoop;
                }
                if (check != BOARD[nrow][ncol]) {
                    continue checkLoop;
                }
            }
            size = i;
        }
        return (size + 1) * (size + 1);
    }

    public static void showBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static Integer readInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }

    public static StringTokenizer readLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }
}