package array.p10809;


import java.io.*;
import java.util.Arrays;

import static array.Path.ARRAY_PATH;

/**
 * Solution code for "BaekJoon 알파벳 찾기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10809">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private final static String TEST_PATH = "/p10809/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String word = br.readLine();

        int[] alphabet = new int[26];
        Arrays.fill(alphabet, -1);

        int tmp = 97;
        for (int i = 0; i < word.length(); i++) {
            int tmpAlpha = (int) word.charAt(i) - tmp;
            if (alphabet[tmpAlpha] != -1) { continue; }
            alphabet[tmpAlpha] = i;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alphabet.length; i++) {
            sb.append(alphabet[i]);
            if (i + 1 == alphabet.length) { break; }
            sb.append(" ");
        }
        br.close();
        System.out.print(sb);
    }
}