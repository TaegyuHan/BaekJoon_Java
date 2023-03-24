package stack.p4949;


import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 균형잡힌 세상".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/4949">Link</a>
 * 작성자: gksxorb147
 * BigO: O(T * 100) :
 */
public class Main {

    private static final String TEST_PATH = "/p4949/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String data = br.readLine();
            if (data.equals(".")) {
                break;
            }

            Stack<Character> stack = new Stack<>();
            dataLoop:
            for (char chr : data.toCharArray()) {
                switch (chr) {
                    case '(':
                        stack.push('(');
                        break;
                    case '[':
                        stack.push('[');
                        break;

                    case ')':
                        if (stack.empty() || stack.peek() != '(') {
                            stack.push(')');
                            break dataLoop;
                        }
                        stack.pop();
                        break;

                    case ']':
                        if (stack.empty() || stack.peek() != '[') {
                            stack.push(']');
                            break dataLoop;
                        }
                        stack.pop();
                        break;
                    default:
                        break;
                }
            }

            if (stack.empty()) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

        }
        br.close();
    }
}
