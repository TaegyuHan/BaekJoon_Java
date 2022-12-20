package implement.p14719;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 빗물".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/14719">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2 + N) : 250_500
 */
public class Main {
    public static final String TEST_PATH = "/p14719/input/3.txt";
    public static StringTokenizer st;
    public static int[] board;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = inputLine(br);
        int H = inputInt(st);
        int W = inputInt(st);
        board = new int[W];
        st = inputLine(br);
        for (int col = 0; col < W; col++) {
            board[col] = inputInt(st);
        }
        br.close();

        int water = 0;
        for (int row = H - 1; 0 <= row; row--) {
            int wallStart = -1;
            int checkHeight = H - row;
            for (int col = 0; col < W - 1; col++) {
                int current = board[col];
                int next = board[col + 1];

                if (checkHeight <= current && next < current) { // 벽에서 물을 만난 경우
                    wallStart = col;
                }

                if (wallStart != -1 // 벽을 만난적이 있고
                        && (current < checkHeight && checkHeight <= next)) { // 물에서 벽을 만난경우
                    water += col - wallStart;
                }
            }
        }
        System.out.print(water);
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}
