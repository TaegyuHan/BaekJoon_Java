package string.p10988;

import java.io.*;

import static string.Path.STRING_PATH;

/**
 * Solution code for "BaekJoon 팰린드롬인지 확인하기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10988">Link</a>
 * 작성자: gksxorb147
 * BigO: O(N) : 100
 */
class Palindrome {
    private final String string;

    public Palindrome(String string) {
        this.string = string;
    }

    private boolean check(String str) {
        int size = str.length();

        for (int i = 0; i < size; i++) {
            if (str.charAt(i) != str.charAt((size - i - 1))) {
                return false;
            }
        }
        return true;
    }

    public void answer() {
        if (check(this.string)) {
            System.out.print("1");
        } else {
            System.out.print("0");
        }
    }
}

public class Main {

    private final static String TEST_PATH = "/p10988/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(STRING_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        br.close();

        Palindrome palindrome = new Palindrome(str);
        palindrome.answer();
    }
}