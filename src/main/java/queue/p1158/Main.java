package queue.p1158;

import java.io.*;
import java.util.ArrayDeque;

import static queue.Path.QUEUE_PATH;

/**
 * Solution code for "BaekJoon 요세푸스 문제".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1158">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p1158/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(QUEUE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] data = br.readLine().split(" ");

        int N = Integer.parseInt(data[0]);
        int K = Integer.parseInt(data[1]);
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            queue.add(i);
        }

        StringBuilder sb = new StringBuilder();
        sb.append('<');
        int out = 1;
        while (!queue.isEmpty()) {
            if (out == K) {
                sb.append(queue.pop());
                out = 1;
                if (!queue.isEmpty()) {
                    sb.append(", ");
                }
                continue;
            }
            queue.add(queue.pop());
            out++;
        }
        sb.append('>');

        bw.write(sb.toString());
        bw.flush();

        br.close();
        bw.close();
    }
}