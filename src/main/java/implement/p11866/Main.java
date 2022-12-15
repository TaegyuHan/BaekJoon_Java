package implement.p11866;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;


/**
 * Solution code for "BaekJoon 요세푸스 문제 0".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/11866">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2) : 1_000_000
 */
public class Main {

    final static String TSET_PATH = "/p11866/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TSET_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer sr = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(sr.nextToken());
        int K = Integer.parseInt(sr.nextToken());
        StringBuilder sb = new StringBuilder();
        sb.append("<");

        // 배열 초기화 : O(N)
        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = i + 1;
        }

        // 전부 반활 할 때까지 돌기
        int idx = 0;
        int outCount = 0;
        while (outCount < N) {
            int tmpCount = 0;

            // 1개 넘어갈때 까지 앞으로 가기
            while (true) {
                if (numbers[idx] != 0) {
                    tmpCount += 1;
                }
                if (K <= tmpCount) {
                    if (outCount == N - 1) {
                        sb.append(numbers[idx]);
                    } else {
                        sb.append(numbers[idx] + ", ");
                    }
                    numbers[idx] = 0;
                    outCount++;
                    break;
                }
                idx += 1;
                idx = idx % N;
            }
        }
        sb.append(">");
        System.out.println(sb);
    }
}