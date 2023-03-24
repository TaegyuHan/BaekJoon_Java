package stack.p1874;


import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 스택 수열".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1874">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private static final String TEST_PATH = "/p1874/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = Integer.parseInt(br.readLine());
        }
        br.close();

        int idx = 0;
        int startNumber = 1;
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        while (idx < n) {

            if (!stack.isEmpty() && stack.peek() == result[idx]) {
                stack.pop();
                sb.append("-\n");
                idx++;
                continue;
            }

            if (startNumber > n) {
                break;
            }

            stack.push(startNumber++);
            sb.append("+\n");
        }

        if (idx == n) {
            bw.write("NO");
        } else {
            bw.write(sb.toString());
        }

        bw.flush();
        bw.close();
    }
}