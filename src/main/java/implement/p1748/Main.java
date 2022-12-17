package implement.p1748;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 수 이어쓰기 1".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1748">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N) :
 */
public class Main {

    public final static String TEST_PATH = "/p1748/input/3.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        br.close();

        int tmp = 10;
        int count = 1;
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            answer += count;
            if (i == tmp - 1) {
                tmp *= 10;
                count += 1;
            }
        }
        System.out.println(answer);
    }
}