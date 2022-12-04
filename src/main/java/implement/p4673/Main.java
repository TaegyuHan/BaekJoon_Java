package implement.p4673;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solution code for "BaekJoon 셀프 넘버".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/4673">https://www.acmicpc.net/problem/4673</a>
 *
 * <p>BigO : O(3N)
 */
public class Main {

    private static final int MAX_NUM = 10_000;

    private static int d(int number) {

        String stringNum = Integer.toString(number);

        int sumNum = 0;
        String[] numbers = stringNum.split("");
        for (String num : numbers) {
            sumNum += Integer.parseInt(num);
        }
        return number + sumNum;
    }

    private static void baekJoonAnswer() {

        boolean[] visited = new boolean[10_001];
        Arrays.fill(visited, false);

        int index;
        for (int i = 1; i <= MAX_NUM; i++) {
            index = d(i);
            if (index <= MAX_NUM) {
                visited[index] = true;
            }
        }

        List<String> answer = new ArrayList<>();
        for (int i = 1; i <= MAX_NUM; i++) {
            if (!visited[i]) {
                answer.add(Integer.toString(i));
            }
        }
        System.out.println(String.join("\n", answer));
    }

    public static void main(String[] args) {
        baekJoonAnswer();
    }
}