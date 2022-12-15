package implement.p2941;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 크로아티아 알파벳".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2941">Link</a>
 * BigO : O(N)
 */
public class Main {

    private static final String TEST_CASE = "/p2941/input/5.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_CASE));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int answer = 0;
        String word = br.readLine();
        for (int i = 0; i < word.length(); ++i) {
            char alpha = word.charAt(i);
            answer += 1;

            if (i == word.length() - 1) {
                break;
            }

            char nextWord = word.charAt(i + 1);
            switch (alpha) {
                case 'c':
                    if (nextWord == '=' || nextWord == '-') {
                        ++i;
                    }
                    break;
                case 'd':
                    if (nextWord == '-') {
                        ++i;
                    }
                    if (i < word.length() - 2 && nextWord == 'z' && word.charAt(i + 2) == '=') {
                        i += 2;
                    }
                    break;
                case 'l':
                    if (nextWord == 'j') {
                        ++i;
                    }
                    break;
                case 'n':
                    if (nextWord == 'j') {
                        ++i;
                    }
                    break;
                case 's':
                    if (nextWord == '=') {
                        ++i;
                    }
                    break;
                case 'z':
                    if (nextWord == '=') {
                        ++i;
                    }
                    break;
            }
        }
        System.out.println(answer);
    }
}
