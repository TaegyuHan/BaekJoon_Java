package queue.p2164;

import java.io.*;
import java.util.ArrayDeque;

import static queue.Path.QUEUE_PATH;

/**
 * Solution code for "BaekJoon 카드 2".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2164">Link</a>
 * 작성자: gksxorb147
 * BigO: O(n) :
 */
public class Main {

    private final static String TEST_PATH = "/p2164/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(QUEUE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        int n = Integer.parseInt(br.readLine());
        br.close();
        for (int i = 1; i <= n; i++) {
            queue.add(i);
        }

        boolean back = false;
        while (queue.size() != 1) {
            if (back) {
                queue.add(queue.pop());
                back = false;
            } else {
                queue.pop();
                back = true;
            }
        }
        bw.write(queue.pop().toString());
        bw.flush();
        bw.close();
    }
}