package queue.p18258;

import java.io.*;
import java.util.Arrays;

import static queue.Path.QUEUE_PATH;

/**
 * Solution code for "BaekJoon 큐2".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/18258">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p18258/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(QUEUE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int inputDataCount = Integer.parseInt(br.readLine());

        Queue queue = new Queue(2_000_000);

        while (0 < inputDataCount--) {
            String[] data = br.readLine().split(" ");

            if (data.length == 2) {
                queue.push(Integer.parseInt(data[1]));
                continue;
            }

            switch (data[0]) {
                case "pop" -> sb.append(queue.pop());
                case "size" -> sb.append(queue.size());
                case "empty" -> sb.append(queue.empty());
                case "front" -> sb.append(queue.front());
                case "back" -> sb.append(queue.back());
            }

            if (inputDataCount != 0) {
                sb.append("\n");
            }
        }
        br.close();
        System.out.println(sb);
    }
}

class Queue {

    private final int[] array;
    private int frontIndex;
    private int backIndex;

    public void print() {
        System.out.println(Arrays.toString(array));
    }

    public Queue(int size) {
        this.array = new int[size];
        this.frontIndex = 0;
        this.backIndex = 0;
    }

    public void push(int data) {
        array[backIndex] = data;
        backIndex++;
    }

    public int pop() {
        if (empty() == 1) {
            return -1;
        }
        return array[frontIndex++];
    }

    public int size() {
        return backIndex - frontIndex;
    }

    public int empty() {
        if (frontIndex == backIndex) {
            return 1;
        }
        return 0;
    }

    public int front() {
        if (empty() == 1) {
            return -1;
        }
        return array[frontIndex];
    }

    public int back() {
        if (empty() == 1) {
            return -1;
        }
        return array[backIndex - 1];
    }
}