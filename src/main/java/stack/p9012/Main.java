package stack.p9012;


import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 괄호".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/9012">Link</a>
 * 작성자: gksxorb147
 * BigO: O(T * 50) :
 */
public class Main {

    private final static String TEST_PATH = "/p9012/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tsetCase = Integer.parseInt(br.readLine());

        for (int i = 0; i < tsetCase; i++) {
            String inputData = br.readLine();
            Stack<Character> stack = new Stack<>();

            dataLoop:
            for (int j = 0; j < inputData.length(); j++) {

                switch (inputData.charAt(j)) {
                    case ')':
                        if (stack.empty() || stack.peek() != '(') {
                            stack.push(')');
                            break dataLoop;
                        }
                        stack.pop();
                        break;
                    case '(':
                        stack.push('(');
                        break;
                }
            }

            if (stack.isEmpty()) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        br.close();
    }
}
