package stack.p10828;


import java.io.*;
import java.util.Stack;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 스택".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10828">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {
    private static final String TEST_PATH = "/p10828/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Stack<Integer> stack = new Stack<>();
        int inputDataCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < inputDataCount; i++) {
            String[] inputData = br.readLine().split(" ");

            if (inputData.length == 2) { // push
                Integer number = Integer.parseInt(inputData[1]);
                stack.push(number);

            } else {
                switch (inputData[0]) {
                    case "top":
                        if (stack.empty()) {
                            System.out.println(-1);
                        } else {
                            System.out.println(stack.peek());
                        }
                        break;

                    case "size":
                        System.out.println(stack.size());
                        break;

                    case "empty":
                        if (stack.empty()) {
                            System.out.println(1);
                        } else {
                            System.out.println(0);
                        }
                        break;

                    case "pop":
                        if (stack.empty()) {
                            System.out.println(-1);
                        } else {
                            System.out.println(stack.pop());
                        }
                        break;
                }
            }
        }
        br.close();
    }
}