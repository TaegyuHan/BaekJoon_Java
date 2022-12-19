package implement.p1244;

import java.io.*;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 스위치 켜고 끄기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1244">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2 + N) :  10100
 */
public class Main {

    public final static String TEST_PATH = "/p1244/input/5.txt";
    public static int[] switchState;
    public static StringTokenizer st;
    public static BufferedReader br;
    public static int N;

    public static void main(String[] arg) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        br = new BufferedReader(new InputStreamReader(System.in));
        st = inputRead(br);

        N = inputInt(st);
        switchState = new int[N + 1];

        st = inputRead(br);
        for (int i = 1; i <= N; i++) {
            int switchType = inputInt(st);
            if (switchType == 1) {
                switchState[i] = 1;
            }
        }

        st = inputRead(br);
        int peopleCount = inputInt(st);
        for (int i = 0; i < peopleCount; i++) { // 100
            st = inputRead(br);
            int gender = inputInt(st);
            int number = inputInt(st);
            if (gender == 1) {
                manChange(number);
            } else {
                womanChange(number);
            }
        }
        br.close();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < switchState.length; i++) { // 100
            sb.append(switchState[i]);
            if (i % 20 == 0) {
                sb.append("\n");
                continue;
            }
            if ( i != switchState.length - 1)
                sb.append(" ");
        }
        System.out.print(sb);
    }

    public static void manChange(int number) {
        for (int i = number; i <= N; i += number) {
            switchChange(i);
        }
    }

    public static void womanChange(int number) {
        int back = number;
        int front = number;
        while (true) { // 100
            back = back - 1;
            front = front + 1;
            if (!(1 <= back && front <= N)
                    || (switchState[back] != switchState[front])) {
                back += 1;
                front -= 1;
                break;
            }
        }
        for (int i = back; i <= front; i++) {
            switchChange(i);
        }
    }

    public static void switchChange(int state) { // 50
        if (switchState[state] == 1) {
            switchState[state] = 0;
        } else {
            switchState[state] = 1;
        }
    }

    public static StringTokenizer inputRead(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer inputInt(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}