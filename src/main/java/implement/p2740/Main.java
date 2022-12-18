package implement.p2740;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 행렬 곱셈".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2740">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^3 + 2N) :
 */
public class Main {

    public final static String TEST_PATH = "/p2740/input/3.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = inputLine(br);

        // 첫번째 행렬
        int row1 = inputInt(st);
        int col1 = inputInt(st);
        int[][] board1 = new int[row1][col1];
        for (int i = 0; i < row1; i++) {
            st = inputLine(br);
            for (int j = 0; j < col1; j++) {
                board1[i][j] = inputInt(st);
            }
        }

        // 두번째 행렬
        st = inputLine(br);

        int row2 = inputInt(st);
        int col2 = inputInt(st);
        int[][] board2 = new int[row2][col2];
        for (int i = 0; i < row2; i++) {
            st = inputLine(br);
            for (int j = 0; j < col2; j++) {
                board2[i][j] = inputInt(st);
            }
        }
        br.close();

        // 행렬 덧셈
        StringBuilder sb = new StringBuilder();
        for (int x1 = 0; x1 < row1; x1++) {
            for (int r1 = 0; r1 < col2; r1++) {
                int tmp = 0;
                for (int y1 = 0; y1 < col1; y1++) {
                    tmp += board1[x1][y1] * board2[y1][r1];
//                    System.out.printf("%d %d %d %d\n", x1, y1, y1, r1);
                }
                sb.append(tmp);
                if (r1 == col2 - 1 && x1 != row1 - 1) {
                    sb.append("\n");
                } else {
                    sb.append(" ");
                }
            }
        }
        System.out.println(sb);
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        // 1라인 데이터 받기
        return new StringTokenizer(br.readLine());
    }

    public static int inputInt(StringTokenizer st) {
        // 데이터 숫자 받기
        return Integer.parseInt(st.nextToken());
    }
}