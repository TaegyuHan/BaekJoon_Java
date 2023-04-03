package queue.p10845;

import java.io.*;
import java.util.ArrayDeque;

import static queue.Path.QUEUE_PATH;

/**
 * Solution code for "BaekJoon 큐".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10845">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p10845/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(QUEUE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int inputDataCount = Integer.parseInt(br.readLine());

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputDataCount; i++) {
            String[] data = br.readLine().split(" ");
            if (data.length == 2) {
                queue.add(Integer.parseInt(data[1]));

            } else {
                switch (data[0]) {
                    case "pop":
                        if (queue.isEmpty()) {
                            sb.append("-1\n");
                        } else {
                            sb.append(queue.pop()).append('\n');
                        }
                        break;
                    case "size":
                        sb.append(queue.size()).append('\n');
                        break;
                    case "empty":
                        if (queue.isEmpty()) {
                            sb.append("1\n");
                        } else {
                            sb.append("0\n");
                        }
                        break;
                    case "front":
                        if (queue.isEmpty()) {
                            sb.append("-1\n");
                        } else {
                            sb.append(queue.peekFirst()).append('\n');
                        }
                        break;
                    case "back":
                        if (queue.isEmpty()) {
                            sb.append("-1\n");
                        } else {
                            sb.append(queue.peekLast()).append('\n');
                        }
                        break;
                }
            }
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}