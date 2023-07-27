package implement.p25372;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 성택이의 은밀한 비밀번호".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/25372">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p25372/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream( IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());

        for (int i = 0; i < testCase; i++) {
            String data = br.readLine();
            if (6 <= data.length() & data.length() <= 9) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
        br.close();
    }
}