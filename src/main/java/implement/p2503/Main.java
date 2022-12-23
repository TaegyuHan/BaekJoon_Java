package implement.p2503;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 숫자야구".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2503">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N) : 100
 */
public class Main {

    public static String TEST_PATH = "/p2503/input/1.txt";
    public static int NUMBER_COUNT = 3;
    public static List<BallData> inputData = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = readInteger(readLine(br));
        for (int i = 0; i < N; i++) {
            StringTokenizer st = readLine(br);
            while (st.hasMoreTokens()) {
                inputData.add(new BallData(readInteger(st), readInteger(st), readInteger(st)));
            }
        }
        br.close();

        // 번호 확인
        int answer = 0;
        for (int i = 111; i < 1_000; i++) {
            if (checkSameNum(i)) continue;
            if (checkZeroNum(i)) continue;

            String myNumber = Integer.toString(i);
            boolean answerAdd = true;

            for (int idx = 0; idx < N; idx++) {
                int strikeCount = 0;
                int ballCount = 0;
                BallData ballData = inputData.get(idx);
                String checkNumber = ballData.getDataString();

                // strike 확인
                for (int k = 0; k < NUMBER_COUNT; k++) {
                    if (myNumber.charAt(k) == checkNumber.charAt(k)) {
                        strikeCount += 1;
                    }
                }

                // ball 확인
                for (int k = 0; k < NUMBER_COUNT; k++) {
                    for (int j = 0; j < 3; j++) {
                        if (k == j) continue;
                        if (myNumber.charAt(k) == checkNumber.charAt(j)) {
                            ballCount += 1;
                        }
                    }
                }

                if (strikeCount != ballData.getStrike() || ballCount != ballData.getBall()) {
                    answerAdd = false;
                    break;
                }
            }
            if (answerAdd)
                answer += 1;
        }
        System.out.print(answer);
    }

    public static boolean checkZeroNum(int number) {
        String tmp = Integer.toString(number);
        return tmp.charAt(0) == '0' || tmp.charAt(1) == '0' || tmp.charAt(2) == '0';
    }

    public static boolean checkSameNum(int number) {
        String tmp = Integer.toString(number);
        return tmp.charAt(0) == tmp.charAt(1)
                || tmp.charAt(0) == tmp.charAt(2)
                || tmp.charAt(1) == tmp.charAt(2);
    }


    public static StringTokenizer readLine(BufferedReader br) throws IOException {
        return new StringTokenizer(br.readLine());
    }

    public static Integer readInteger(StringTokenizer st) {
        return Integer.parseInt(st.nextToken());
    }

    static class BallData {
        int data;
        int strike;
        int ball;

        BallData(int data, int strike, int ball) {
            this.data = data;
            this.strike = strike;
            this.ball = ball;
        }

        public String getDataString() {
            return Integer.toString(this.data);
        }

        public int getStrike() {
            return this.strike;
        }

        public int getBall() {
            return this.ball;
        }
    }
}