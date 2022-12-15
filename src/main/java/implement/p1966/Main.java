package implement.p1966;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 제로".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2941">Link</a>
 * 작성자: gksxorb159
 * BigO: O(10N) : 10 * N
 */
public class Main {

    final static String TEST_PATH = "/p1966/input/1.txt";

    public static int testLogic(String n, String m, String[] priorities) {
        int[] pageCountList = new int[10];
        int numCount = Integer.parseInt(n);
        int findTurn = Integer.parseInt(m);

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < numCount; i++) {
            int[] data = new int[2];
            int priority = Integer.parseInt(priorities[i]);
            pageCountList[priority] += 1;
            data[0] = i;
            data[1] = priority;
            queue.offer(data);
        }

        int answerCount = 1;
        forNum: // 9 부터 확인
        for (int priority = pageCountList.length - 1; priority > 0; priority--) {
            if (pageCountList[priority] == 0) continue; // 0아니면 PASS

            while (!queue.isEmpty()) {
                if (pageCountList[priority] == 0) continue forNum; // 해당 우선순위가 없는 경우
                int[] data = queue.poll();  // 맨 앞의 데이터

                // 우선순위가 다른경우
                if (data[1] != priority) {
                    queue.add(data);
                    continue;
                }

                // 우선순위가 같은경우
                if (data[0] == findTurn) {
                    return answerCount;
                }
                pageCountList[priority] -= 1;
                answerCount += 1;
            }
        }

        return answerCount;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int testCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < testCount; i++) {
            String[] data = br.readLine().split(" ");
            String[] priority = br.readLine().split(" ");
            sb.append(testLogic(data[0], data[1], priority))
                    .append('\n');
        }
        br.close();
        System.out.println(sb);
    }
}