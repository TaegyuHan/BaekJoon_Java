package implement.p25314;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 코딩은 체육과목 입니다.".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/25314">Link</a>
 * 작성자: gksxorb147
 * BigO: O(N) : N / 4
 */
public class Main {

    private final static String TEST_PATH = "/p25314/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 4; i < number; i+=4) {
            sb.append("long ");
        }
        sb.append("long int");

        System.out.print(sb);

        br.close();
    }
}