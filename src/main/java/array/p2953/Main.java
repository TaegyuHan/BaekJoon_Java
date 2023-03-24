package array.p2953;

import java.io.*;

import static array.Path.ARRAY_PATH;
import static java.lang.Integer.MIN_VALUE;

/**
 * Solution code for "BaekJoon 나는 요리사다".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2953">Link</a>
 * 작성자: gksxorb147
 * BigO: O(5 * 4) :
 */
public class Main {

    private final static String TEST_PATH = "/p2953/input/2.txt";
    private final static int MEMBER_COUNT = 5;
    private final static int POINT_COUNT = 4;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(ARRAY_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int index = 0;
        int maxPoint = MIN_VALUE;
        for (int i = 1; i <= MEMBER_COUNT; i++) {
            String[] points = br.readLine().split(" ");
            int sumPoint = 0;
            for (int j = 0; j < POINT_COUNT; j++) {
                sumPoint += Integer.parseInt(points[j]);
            }

            if (maxPoint < sumPoint) {
                index = i;
                maxPoint = sumPoint;
            }
        }
        br.close();
        System.out.println(index + " " + maxPoint);
    }
}