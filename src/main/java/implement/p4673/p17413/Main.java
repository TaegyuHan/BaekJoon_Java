package implement.p4673.p17413;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 단어 뒤집기 2".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/17413">Link</a>
 * 작성자: gksxorb159
 * BigO: O(2N) : 200_000
 */
public class Main {
    public final static String TEST_PATH = "/p17413/input/7.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String words = br.readLine();
        br.close();

        StringBuilder answer = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        boolean stackState = false;

        for (int i = 0; i < words.length(); i++) {
            char data = words.charAt(i);

            if (data == '<') {
                answer.append(reverse(tmp.toString())).append(data);
                tmp = new StringBuilder();
                stackState = true;
                continue;
            }

            if (data == '>') {
                answer.append(data);
                stackState = false;
                tmp = new StringBuilder();
                continue;
            }

            if (stackState) {
                answer.append(data);
                continue;
            }

            if (data == ' ') {
                answer.append(reverse(tmp.toString())).append(data);
                tmp = new StringBuilder();
                continue;
            }

            tmp.append(data);
            if (i == words.length() - 1) {
                answer.append(reverse(tmp.toString()));
            }
        }
        System.out.println(answer);
    }

    public static StringBuilder reverse(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = word.length() - 1; 0 <= i; i--) {
            sb.append(word.charAt(i));
        }
        return sb;
    }
}