package implement.p12904;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon A와 B".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/12904">Link</a>
 * 작성자: gksxorb159
 * BigO: O() :
 */
public class Main {

    public static String TEST_PATH = "/p12904/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine(); // 1000
        String T = br.readLine(); // 1000
        br.close();

        while (S.length() != T.length()) {
            char backchar = T.charAt(T.length() - 1);
            switch (backchar) {
                case 'A':
                    T = T.substring(0, T.length() - 1);
                    break;
                case 'B':
                    T = reverse(T.substring(0, T.length() - 1));
                    break;
            }
        }

        int answer = 0;
        if (S.equals(T))
            answer = 1;
        System.out.print(answer);
    }

    public static boolean checkString(String checkString, String T) {
        boolean check = false;
        for (int i = 0; i <= T.length() - checkString.length(); i++) {
            check = true;
            for (int j = 0; j < checkString.length(); j++) {
//                System.out.printf("%d %d\n", i + j, j);
                if (T.charAt(i + j) != checkString.charAt(j)) {
                    check = false;
                    break;
                }
            }
//            System.out.println(check);
            if (check)
                break;
        }
        return check;
    }
    // 문자열 가능여부 확인

    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = s.length() - 1; 0 <= i; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}