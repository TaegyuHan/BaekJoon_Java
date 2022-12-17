package implement.p2960;


import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 에라토스테네스의 체".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2960">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2) : 1_000_000
 */
public class Main {

    public static String TEST_PATH = "/p2960/input/3.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        br.close();
        boolean[] numbers = new boolean[N + 1];

        int check = 0;
        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i]) continue; // 이미 방문함

            for (int j = i; j < numbers.length; j += i) {
                if (numbers[j]) continue; // 이미 방문함
                check += 1;
                numbers[j] = true;

                if (check == K) {
                    System.out.println(j);
                    return;
                }
            }
        }
    }
}