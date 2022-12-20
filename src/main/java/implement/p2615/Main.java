package implement.p2615;


import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 오목".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2615">Link</a>
 * 작성자: gksxorb159
 * BigO: O(9(N^2)) :
 */
public class Main {
    public static final String TEST_PATH = "/p2615/input/4.txt";
    public static StringTokenizer st;
    public static final int N = 20;
    public static int[][] board = new int[N][N];

    public static final int[][] MOVE = {
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1},
    };

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int row = 1; row < N; row++) {  // N^2 = 361
            st = inputLine(br);
            for (int col = 1; col < N; col++) {
                board[row][col] = inputInt(st);
            }
        }
        br.close();

        StringBuilder sb = new StringBuilder();
        boolean answer = false;

        loop:
        for (int col = 1; col < N; col++) { // N^2 * 25 = 361 * 25
            for (int row = 1; row < N; row++) {
                boolean check = winCheck(row, col);
                if (check) {
                    answer = true;
                    sb.append(board[row][col]).append("\n");
                    sb.append(row).append(" ").append(col);
                    break loop;
                }
            }
        }

        if (answer) {
            System.out.print(sb);
        } else {
            System.out.print(0);
        }
    }

    public static boolean winCheck(int row, int col) {
        // 빈 공간이면 반환
        if (board[row][col] == 0) {
            return false;
        }
//        System.out.printf("%d %d\n", row, col);
        int startColor = board[row][col];
        boolean[] check = new boolean[8];
//        System.out.println(Arrays.toString(check));

        for (int tmp = 1; tmp <= 5; tmp++) {
            for (int i = 0; i < MOVE.length; i++) {
                if (check[i]) continue;
                int nrow = row + (MOVE[i][0] * tmp);
                int ncol = col + (MOVE[i][1] * tmp);

                // 처음 시작할 떄 뒤에 같은 것이 있나 확인
                if (tmp == 1) {
                    int brow = row - (MOVE[i][0] * tmp);
                    int bcol = col - (MOVE[i][1] * tmp);
                    if ((brow < N && bcol < N)) {
                        if (board[brow][bcol] == startColor) {
                            check[i] = true;
                        }
                    }
                }

                // 6개
                if (tmp == 5) {
                    if (!(nrow < N && ncol < N))
                        continue;
                    if (board[nrow][ncol] == startColor)
                        check[i] = true;
                } else { // 5개 초과
                    if (!(nrow < N && ncol < N)
                            || board[nrow][ncol] != startColor) {
                        check[i] = true;
                    }
                }
            }
        }
//        System.out.println(Arrays.toString(check));
        for (boolean noWin : check) {
            if (!noWin) return true;
        }
        return false;
    }

    public static void showBoard() {
        for (int[] rowData : board) {
            System.out.println(Arrays.toString(rowData));
        }
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}