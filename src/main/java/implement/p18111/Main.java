package implement.p18111;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;


/**
 * Solution code for "BaekJoon 마인크래프트".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/마인크래프트">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N*M*256 + M*N) : 66,500,000
 */
public class Main {
    public static String TEST_PATH = "/p18111/input/10.txt";

    public static int ROW, COL, boxCount;
    public static int[][] board;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = inputLine(br);

        int answer = Integer.MAX_VALUE;
        int answerHeight = 256;
        int max = 0;
        int min = 256;
        ROW = inputInt(st);
        COL = inputInt(st);
        boxCount = inputInt(st);
        board = new int[ROW][COL];
        for (int row = 0; row < ROW; row++) { // 500^2
            st = inputLine(br);
            for (int col = 0; col < COL; col++) {
                board[row][col] = inputInt(st);
                min = Math.min(min, board[row][col]);
                max = Math.max(max, board[row][col]);
            }
        }
        br.close();

        for (int height = min; height <= max; height++) { // 256*500*500
            int time = checkTime(height, boxCount);
            if (time == -1)
                continue;

            if (time <= answer) {
                answer = time;
                answerHeight = height;
            }
        }
        System.out.printf("%d %d", answer, answerHeight);
    }

    public static int checkTime(int height, int boxCount) {
        int time = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                int currentHeight = board[row][col];
                int need = height - currentHeight;
                int absNeed = Math.abs(need);

                if (need < 0) {
                    boxCount += absNeed;
                    time += absNeed * 2;
                } else {
                    boxCount -= absNeed;
                    time += absNeed;
                }
            }
        }
        if (boxCount < 0)
            return -1;
        return time;
    }

    public static void showBoard(int[][] board) {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }
}
