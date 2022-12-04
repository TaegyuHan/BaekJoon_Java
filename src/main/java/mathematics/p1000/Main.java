package mathematics.p1000;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Solution code for "BaekJoon A + B".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1000">https://www.acmicpc.net/problem/1000</a>
 *
 * <p>문제</p>
 *
 *      두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.
 *
 * <p>입력</p>
 *      첫째 줄에 A와 B가 주어진다. (0 < A, B < 10)
 *
 * <p>출력</p>
 *      첫째 줄에 A+B를 출력한다.
 *
 * <p>BigO : O(1)
 */
public class Main {

    private static final String TEST_PATH = "/p1000/input/1.txt";

    private static int a, b;

    private static void inputData() throws IOException {
        // 로컬 테스트 파일
        System.setIn(new FileInputStream("./testcase" + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        br.close();
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
    }

    public static void baekJoonAnswer() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(Integer.toString(a + b));
        bw.flush();
    }

    public static void main(String[] args) throws IOException {
        inputData();
        baekJoonAnswer();
    }
}