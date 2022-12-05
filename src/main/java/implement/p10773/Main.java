package implement.p10773;

import java.io.*;
import java.util.Stack;

/**
 * Solution code for "BaekJoon 제로".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2941">Link</a>
 * 작성자: gksxorb159
 * BigO: O(2N) : 100_000 * 2 = 200_000
 */
public class Main {

    final static String TEST_PATH = "/p10773/input/1.txt";

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("./testcase" + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 프린트 풀이
        Stack<Integer> stack = new Stack<>();
        int inputCount = Integer.parseInt(br.readLine());
        int tmpNum;
        for (int i = 0; i < inputCount; i++) {
            tmpNum = Integer.parseInt(br.readLine());
            if (tmpNum != 0) {
                stack.push(tmpNum);
            } else {
                stack.pop();
            }
        }

        int sum = 0;
        for (Integer num : stack) {
            sum += num;
        }
        System.out.println(sum);
    }
}
