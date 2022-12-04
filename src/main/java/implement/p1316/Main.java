package implement.p1316;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * Solution code for "BaekJoon 셀프 넘버".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/4673">https://www.acmicpc.net/problem/4673</a>
 * BigO : O(2N)
 */
public class Main {

    private static final String TEST_PATH = "/p1316/input/5.txt";

    private static int checkCount(String word) {

        if (word.length() == 1) {
            return 1;
        }

        Set<Character> set = new HashSet<>();
        char beforeChar = word.charAt(0);
        set.add(beforeChar);
        for (int i = 1; i < word.length(); ++i) {
            char currentChar = word.charAt(i);
            if (beforeChar == currentChar) {
                continue;
            }
            if (set.contains(currentChar)) {
                return 0;
            }
            set.add(currentChar);
            beforeChar = currentChar;
        }
        return 1;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("./testcase" + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int answer = 0;
        int testCount = Integer.parseInt(st.nextToken());
        for (int i = 0; i < testCount; i++) {
            st = new StringTokenizer(br.readLine());
            answer += checkCount(st.nextToken());
        }
        System.out.println(answer);
    }
}