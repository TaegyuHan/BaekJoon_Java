package array.p10818;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

/**
 * Solution code for "BaekJoon 최소, 최대".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10818">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    private static final String TEST_PATH = "/p10818/input/1.txt";

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int max = MIN_VALUE;
        int min = MAX_VALUE;

        int arrayLength = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        br.close();

        int tmp;
        for (int i = 0; i < arrayLength; i++) {
            tmp = Integer.parseInt(st.nextToken());
            max = Math.max(max, tmp);
            min = Math.min(min, tmp);
        }

        sb.append(min).append(" ").append(max);
        System.out.println(sb);
    }
}