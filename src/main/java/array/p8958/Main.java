package array.p8958;

import java.io.*;

import static array.Path.ARRAY_PATH;


/**
 * Solution code for "BaekJoon OX 퀴즈".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/8958">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {
    private static final String TEST_PATH = "/p8958/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        for (int i = 1; i <= testCase; i++) {
            System.out.println(testResult(br));
        }
        br.close();
    }

    private static int testResult(BufferedReader br) throws IOException {
        String test = br.readLine();
        int point = 0;
        int before = 0;
        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) == 'X') {
                before = 0;
                continue;
            }
            before += 1;
            point += before;
        }
        return point;
    }
}