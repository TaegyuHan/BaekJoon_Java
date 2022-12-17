package implement.p2167;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 2차원 배열의 합".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2167">Link</a>
 * 작성자: gksxorb159
 * BigO: O(10_000 * N^2)
 */
public class Main {

    public final static String TSET_PATH = "/p2167/input/1.txt";
    public static int[][] board;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TSET_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());
        board = new int[row][col];

        for (int i = 0; i < row; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < col; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        int sumCount = Integer.parseInt(st.nextToken());
        for (int i = 0; i < sumCount; i++) {
            st = new StringTokenizer(br.readLine());
            int rowStart = Integer.parseInt(st.nextToken());
            int colStart = Integer.parseInt(st.nextToken());
            int rowEnd = Integer.parseInt(st.nextToken());
            int colEnd = Integer.parseInt(st.nextToken());
            System.out.println(sum(rowStart, colStart, rowEnd, colEnd));
        }
        br.close();
    }

    public static int sum(int rowStart, int colStart, int rowEnd, int colEnd) {
        int tmp = 0;
        for (int i = rowStart - 1; i <= rowEnd - 1; i++) {
            for (int j = colStart - 1; j <= colEnd - 1; j++) {
                tmp += board[i][j];
            }
        }
        return tmp;
    }
}