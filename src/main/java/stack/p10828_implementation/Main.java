package stack.p10828_implementation;

import java.io.*;
import java.util.Arrays;

import static stack.Path.STACK_PATH;

/**
 * Solution code for "BaekJoon 스택".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/스택">Link</a>
 * 작성자: gksxorb147
 * BigO: O() : O(N)
 */
public class Main {

    private final static String TEST_PATH = "/p10828_implementation/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STACK_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int inputDataCount = Integer.parseInt(br.readLine());
        Stack stack = new Stack(10_000);

        while (inputDataCount-- != 0) {
            String[] data = br.readLine().split(" ");

            if (data.length == 2) {
                stack.push(Integer.parseInt(data[1]));
                continue;
            }

            switch (data[0]) {
                case "pop" -> sb.append(stack.pop());
                case "size" -> sb.append(stack.size());
                case "empty" -> sb.append(stack.empty());
                case "top" -> sb.append(stack.top());
            }
            if (inputDataCount != 0) {
                sb.append("\n");
            }
        }
        br.close();
        System.out.print(sb);
    }
}

class Stack {

    private final int[] array;
    private int index;

    public void print() {
        System.out.println(Arrays.toString(array));
    }

    public Stack(int size) {
        this.array = new int[size];
        this.index = -1;
    }

    // 데이터 넣기
    public void push(int data) {
        index++;
        array[index] = data;
    }

    // 데이터 출력
    public int pop() {
        if (empty() == 1) {
            return -1;
        }
        return array[index--];
    }

    public int size() {
        if (empty() == 1) {
            return 0;
        }
        return index + 1;
    }

    public int empty() {
        if (index == -1) {
            return 1;
        }
        return 0;
    }

    public int top() {
        if (empty() == 1) {
            return -1;
        }
        return array[index];
    }
}