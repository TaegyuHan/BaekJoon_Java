package stack.p2504;

import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 괄호의 값".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2504">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N) : 30
 */
public class Main {

    public static String TEST_PATH = "/p2504/input/2.txt";

    public static void main(String[] args) throws IOException {

        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String brackets = br.readLine();
        br.close();

        Stack<Character> stack = new Stack<>();
        int answer = 0;
        int tmp = 1;
        boolean error = false;
        bracketsLoop:
        for (int i = 0; i < brackets.length(); i++) {
            char data = brackets.charAt(i);

            switch (data) {
                case ')':
                    if (stack.isEmpty() || stack.peek() != '(') {
                        error = true;
                        break bracketsLoop;
                    }
                    else if (brackets.charAt(i - 1) == '(') {
                        answer += tmp;
                    }
                    stack.pop();
                    tmp /= 2;
                    break;

                case ']':
                    if (stack.isEmpty() || stack.peek() != '[') {
                        error = true;
                        break bracketsLoop;
                    }
                    else if (brackets.charAt(i - 1) == '[') {
                        answer += tmp;
                    }
                    stack.pop();
                    tmp /= 3;
                    break;

                case '(':
                    stack.push(data);
                    tmp *= 2;
                    break;

                case '[':
                    stack.push(data);
                    tmp *= 3;
                    break;
            }
        }

        if (!stack.isEmpty() || error) {
            System.out.println(0);
        } else {
            System.out.println(answer);
        }
    }
}