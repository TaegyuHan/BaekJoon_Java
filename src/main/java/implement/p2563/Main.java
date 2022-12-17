package implement.p2563;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 색종이".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2563">Link</a>
 * 작성자: gksxorb159
 * BigO: O(2N^2 + N) : 20000 + 100
 */
public class Main {

    final static String TEST_PATH = "/p2563/input/1.txt";
    public static int[][] board = new int[100][100];

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int inputCount = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < inputCount; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            putBoard(row, col);
        }
        System.out.println(answer());
    }

    public static void putBoard(int row, int col) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[row + i][col + j] = 1;
            }
        }
    }

    public static int answer() {
        int tmpAnswer = 0;
        for (int[] tmpRow : board) {
            for (int data : tmpRow) {
                if (data == 0) continue;
                tmpAnswer += 1;
            }
        }
        return tmpAnswer;
    }
}