package implement.p1475;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;


/**
 * Solution code for "BaekJoon 방 번호".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1475">Link</a>
 * 작성자: gksxorb159
 * BigO: N + 12
 */
public class Main {

    public final static String TEST_PATH= "/p1475/input/4.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String numbers = br.readLine();
        br.close();

        int[] zeroToNine = new int[10];
        for (int i = 0; i < numbers.length(); i ++) {  // N
            zeroToNine[numbers.charAt(i) - '0'] += 1;
        }

        int sixAndNine = 0;
        int max = 0;
        for (int i = 0; i < zeroToNine.length; i ++) {  // 10
            if (i == 6 || i == 9) {
                sixAndNine = sixAndNine + zeroToNine[i];
                continue;
            }
            max = Math.max(max, zeroToNine[i]);
        }
        max = Math.max(max, Math.round(sixAndNine / 2f));
        System.out.println(max);
    }
}
