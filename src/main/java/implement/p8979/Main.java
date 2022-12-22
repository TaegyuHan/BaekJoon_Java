package implement.p8979;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 올림픽".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/8979">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^2) : 1ranking += 1_000_000
 */
public class Main {
    public static String TEST_PATH = "/p8979/input/4.txt";
    private static int[][] data;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = readLine(br);

        int cityCount = readInteger(st);
        int cityNumber = readInteger(st);

        data = new int[cityCount][4];
        for (int i = 0; i < cityCount; i++) {
            st = readLine(br);
            int[] tmp = new int[4];
            for (int j = 0; j < 4; j++) {
                tmp[j] = readInteger(st);
            }
            data[i] = tmp;
        }
        br.close();

        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount - (i + 1); j++) {
                compare(j, j + 1);
            }
        }

//        showBoard(cityCount);

        int ranking = 1;
        for (int i = 0; i < cityCount; i++) {
            if (i != 0 && compare(i - 1, i)) {
                ranking = i + 1;
            }
            if (data[i][0] == cityNumber) {
                System.out.print(ranking);
                break;
            }
        }
    }

    public static void showBoard(int cityCount) {
        for (int i = 0; i < cityCount; i++) {
            System.out.println(Arrays.toString(data[i]));
        }
    }

    public static boolean compare(int idx1, int idx2) {
        for (int i = 1; i < 4; i++) {
            // 금매달 비교
            if (data[idx1][i] < data[idx2][i]) {
                change(idx1, idx2);
                return true;
            } else if (data[idx1][i] > data[idx2][i]) {
                return true;
            }
        }
        return false;
    }

    public static void change(int idx1, int idx2) {
        int[] tmp = data[idx2];
        data[idx2] = data[idx1];
        data[idx1] = tmp;
    }

    public static StringTokenizer readLine (BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer readInteger (StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }
}