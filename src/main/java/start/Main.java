package start;

import java.io.*;

/**
 * Solution code for "BaekJoon 문제".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/숫자">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/number/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("" + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        br.close();
    }
}