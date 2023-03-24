package implement.p22859;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon HTML 파싱".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/22859">Link</a>
 * 작성자: gksxorb159
 * BigO: O() :
 */
public class Main {
    final static String TEST_PATH = "/p22859/input/1.txt";

    public static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String HTML = br.readLine();
        br.close();
        HTML = mainTagCut(HTML);
        divTagParsing(HTML);
//        System.out.println(HTML);
    }

    public static String mainTagCut(String html) {
        return html.substring(6, html.length() - 7);
    }

    public static void divTagParsing(String html) {
        System.out.println(html.indexOf("title=\""));
    }
}