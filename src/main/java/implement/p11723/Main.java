package implement.p11723;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 집합".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/11723">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N) : 3,000,000
 */
public class Main {

    final static String TEST_PATH = "/p11723/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputDataCount = Integer.parseInt(br.readLine());

        int set = 0;
        int number = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputDataCount; i++) {  // N
            String[] data = br.readLine().split(" ");
            String type = data[0];
            if (data.length == 2) {
                number = Integer.parseInt(data[1]);
            }

            int inputNumber = 1 << number - 1;

            switch (type) {
                case "add":
                    if ((set & inputNumber) == inputNumber)
                        continue;
                    set += inputNumber;
                    break;

                case "remove":
                    if ((set & inputNumber) != inputNumber)
                        continue;
                    set -= inputNumber;
                    break;

                case "check":
                    if ((set & inputNumber) == inputNumber) {
                        sb.append("1\n");
                    } else {
                        sb.append("0\n");
                    }
                    break;

                case "toggle":
                    if ((set & inputNumber) == inputNumber) {
                        set -= inputNumber;
                    } else {
                        set += inputNumber;
                    }
                    break;

                case "all":
                    set = 1048575; // set 모두 채우기
                    break;

                case "empty":
                    set = 0; // set 모두 비우기
                    break;
            }
        }
        br.close();
        System.out.print(sb);
    }
}