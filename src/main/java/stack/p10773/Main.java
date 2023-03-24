package stack.p10773;

import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 제로".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10773">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private static final String TEST_PATH = "/p10773/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Stack<Integer> stack = new Stack<>();

        int inputDataCount = Integer.parseInt(br.readLine());
        int sum = 0;
        for (int i = 0; i < inputDataCount; i++) {
            int tmp = Integer.parseInt(br.readLine());
            if (tmp == 0) {
                sum -= stack.pop();
            } else {
                stack.push(tmp);
                sum += tmp;
            }
        }
        System.out.println(sum);
        br.close();
    }
}