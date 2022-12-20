package implement.p2161;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 카드 1".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2161">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2) :
 */
public class Main {

    public final static String TEST_PATH = "/p2161/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        br.close();

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            queue.add(i);
        }

        StringBuilder sb = new StringBuilder();
        int type = 0;
        while (!queue.isEmpty()) {
            if (type % 2 == 0) {
                sb.append(queue.poll()).append(" ");
            } else {
                queue.add(queue.poll());
            }
            type += 1;
        }
        System.out.print(sb);
    }
}
