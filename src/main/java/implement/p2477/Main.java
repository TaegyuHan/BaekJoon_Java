package implement.p2477;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 참외밭".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2477">Link</a>
 * 작성자: gksxorb159
 * BigO: O() :
 */
public class Main {
    private final static String TEST_PATH = "/p2477/input/3.txt";

    private static StringTokenizer st;

    public static void main(String[] arg) throws IOException  {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = inputInt(inputLine(br));

        int[][] lengths = new int[5][2];
        int[] count = new int[5];

        for (int i = 0; i < 6; i++) {
            st = inputLine(br);
            int direction = inputInt(st);
            int length = inputInt(st);

            if (count[direction] == 0) {
                count[direction] += 1;
                lengths[direction][0] = length;
            } else {
                lengths[direction][1] = length;
            }
        }
        br.close();

        for (int[] lg : lengths) {
            System.out.printf("%d %d\n", lg[0], lg[1]);
        }
    }

    public static StringTokenizer inputLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}