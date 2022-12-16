package implement.p2108;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 통계학".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2108">Link</a>
 * 작성자: gksxorb159
 * BigO: 2nlogn + 2N
 */
public class Main {

    final static String TEST_PATH = "/p2108/input/4.txt";
    final static int INPUT_NUMBER = 4_000;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] numbers = new int[N];

        int max = -(INPUT_NUMBER + 1);
        int min = INPUT_NUMBER + 1;
        int sum = 0;
        int[] minusCount = new int[INPUT_NUMBER + 1];
        int[] plusCount = new int[INPUT_NUMBER + 1];

        for (int i = 0; i < N; i++) { // BigO(N)
            numbers[i] = Integer.parseInt(br.readLine());
            sum += numbers[i];
            if (max < numbers[i])
                max = numbers[i];

            if (numbers[i] < min)
                min = numbers[i];

            if (0 <= numbers[i]) {
                plusCount[numbers[i]] += 1;
            } else {
                minusCount[-numbers[i]] += 1;
            }
        }
        br.close();
        Arrays.sort(numbers);  // BigO(nlonn)
        // 산술평균
        System.out.println(Math.round(sum / (float) N));
        // 중앙값
        System.out.println(numbers[N / 2]);
        // 최빈값
        System.out.println(findMaxCount(plusCount, minusCount));
        // 범위
        System.out.println(numbers[N - 1] - numbers[0]);
    }

    public static int findMaxCount(int[] plusCount, int[] minusCount) {
        int maxCount = 0;

        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < plusCount.length; i++) {  // BigO(N)
            boolean maxCountChange = false;
            boolean plusChange = false;
            boolean minusChange = false;
            // 더 최빈값을 만난 경우
            if (maxCount < minusCount[i] || maxCount < plusCount[i]) {
                if (!answer.isEmpty()) answer.clear();
                maxCountChange = true;
            }

            if (maxCount < minusCount[i]) {
                answer.add(-i);
                minusChange = true;
            }

            // 더 최빈값을 만난 경우
            if (maxCount < plusCount[i]) {
                answer.add(i);
                plusChange = true;
            }

            if (plusCount[i] != 0 && maxCount == plusCount[i])
                answer.add(i);

            if (minusCount[i] != 0 && maxCount == minusCount[i])
                answer.add(-i);

            if (maxCountChange) {
                if (plusChange)
                    maxCount = plusCount[i];
                if (minusChange)
                    maxCount = minusCount[i];
            }
        }

        Collections.sort(answer); // BigO(nlonn)
        if (2 <= answer.size())
            return answer.get(1);
        return answer.get(0);
    }
}