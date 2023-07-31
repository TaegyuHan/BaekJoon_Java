package implement.p4470;

/**
 * Solution code for "BaekJoon 줄번호".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/4470">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 1; i <= N; i++) {
            String name = br.readLine();
            System.out.println(i + ". " + name);
        }
        br.close();
    }
}