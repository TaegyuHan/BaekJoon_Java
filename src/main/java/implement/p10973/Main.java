package implement.p10973;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 이전 순열".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10973">Link</a>
 * 작성자: gksxorb159
 * BigO: O() :
 */
public class Main {

    public static String TEST_PATH = "/p10973/input/2.txt";
    public static int N;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String endNumber = br.readLine().replace(" ", "");
        br.close();

        String endString = Integer.toString(N).repeat(N);
        int endInteger = Integer.parseInt(endString);

        List<String> possibleNumbers = new ArrayList<>();

        for (int i = 1; i <= endInteger; i++) {
            String number = Integer.toString(i);
            if (number.length() != endString.length()) {
                continue;
            }
            if (zeroCheck(number)) continue;
            if (sameCheck(number)) continue;
            if (stop(number, endNumber)) break;

            possibleNumbers.add(number);
        }


        StringBuilder answer = new StringBuilder();
        if (possibleNumbers.isEmpty()) {
            answer.append("-1");
        } else {
            for (char num : possibleNumbers.get(possibleNumbers.size() - 1).toCharArray()) {
                answer.append(num).append(" ");
            }
        }
        System.out.print(answer);
    }

    public static boolean zeroCheck(String number) {
        for (char num : number.toCharArray()) {
            if (num == '0')
                return true;
            if (N + 48 < num )
                return true;
        }
        return false;
    }

    public static boolean stop(String number, String endNumber) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != endNumber.charAt(i))
                return false;
        }
        return true;
    }

    public static boolean sameCheck(String number) {
        for (int i = 0; i < number.length(); i++) {
            for (int j = i + 1; j < number.length(); j++) {
                if (number.charAt(i) == number.charAt(j))
                    return true;
            }
        }
        return false;
    }
}