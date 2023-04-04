package queue.p11866;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

import static queue.Path.QUEUE_PATH;

/**
 * Solution code for "BaekJoon 문제".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/p11866">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p11866/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(QUEUE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String[] data = br.readLine().split(" ");
        int memberCount = Integer.parseInt(data[0]);
        int term = Integer.parseInt(data[1]);

        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 1; i <= memberCount; i++) {
            queue.add(i);
        }

        sb.append("<");
        int count = term;
        while (queue.size() != 0) {
            count--;

            if (count != 0) {
                queue.add(queue.poll());
                continue;
            }

            sb.append(queue.poll());
            if (queue.size() != 0) {
                sb.append(", ");
            }
            count = term;
        }
        sb.append(">");
        System.out.print(sb);

        br.close();
    }
}