package implement.p4673;

/**
 * Solution code for "BaekJoon 셀프 넘버".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/4673">https://www.acmicpc.net/problem/4673</a>
 *
 * <p>BigO : O(2N)
 */
public class Main0 {

    private static final int MAX_NUM = 10_000;

    private static int d(int number) {

        int sumNum = number;
        while (number != 0) {
            sumNum += number % 10;
            number /= 10;
        }
        return sumNum;
    }

    private static void baekJoonAnswer() {
        StringBuilder sb = new StringBuilder();

        boolean[] visited = new boolean[10_001];

        int index;
        for (int i = 1; i <= MAX_NUM; i++) {
            index = d(i);
            if (index <= MAX_NUM) {
                visited[index] = true;
            }
            if (!visited[i]) {
                sb.append(i).append("\n");
            }
        }

        System.out.println(sb);
    }

    public static void main(String[] args) {
        baekJoonAnswer();
    }
}