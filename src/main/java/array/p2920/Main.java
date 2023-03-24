package array.p2920;


import java.io.*;
import java.util.StringTokenizer;

import static array.Path.ARRAY_PATH;

/**
 * Solution code for "BaekJoon 문제이름".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/8958">Link</a>
 * 작성자: gksxorb147
 * BigO: O(8) :
 */
public class Main {

    private final static String TEST_PATH = "/p2920/input/1.txt";

    private final static int COUNT = 8;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        br.close();

        int start = Integer.parseInt(st.nextToken());
        if (start != 8 && start != 1) {
            System.out.print("mixed");
            return;
        }

        int tmp = 1;
        if (start == 8) {
            tmp *= -1;
        }

        for (int i = 1; i < COUNT; i++) {
            start += tmp;
            if (start != Integer.parseInt(st.nextToken())) {
                System.out.print("mixed");
                return;
            }
        }

        if (tmp == 1) {
            System.out.print("ascending");
        } else {
            System.out.print("descending");
        }
    }
}