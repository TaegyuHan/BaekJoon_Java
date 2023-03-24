package array.p2562;


import java.io.*;
import static java.lang.Integer.MIN_VALUE;

import static array.Path.ARRAY_PATH;

/**
 * Solution code for "BaekJoon 최대값".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2562">Link</a>
 * 작성자: gksxorb147
 * BigO: O(9) :
 */
public class Main {

    private static final String TEST_PATH = "/p2562/input/1.txt";
    private static int INPUT_NUMBER_COUNT = 9;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int max = MIN_VALUE;
        int tmp = 0;
        int index = 0;
        for (int i = 1; i <= INPUT_NUMBER_COUNT; i ++) {
            tmp = Integer.parseInt(br.readLine());
            if (tmp < max) { continue; }
            max = tmp;
            index = i;
        }
        br.close();

        StringBuilder sb = new StringBuilder();

        sb.append(max).append("\n").append(index);
        System.out.println(sb);
    }
}