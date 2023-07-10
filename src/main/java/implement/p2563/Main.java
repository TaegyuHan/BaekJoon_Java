package implement.p2563;

import java.io.*;
import java.util.Arrays;

//import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 색종이".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2563">Link</a><br>
 * 작성자: gksxorb147 <br>
 * BigO: O(2N^2 + N) : 100 + (T * 100) + 100 <br>
 * 메모리 : 14196 KB <br>
 * 시간 : 120 ms <br>
 */

class Paper {
    private boolean[][] paper;
    private static final int width = 100;
    private static final int height = 100;

    public Paper() {
        // Big(O) : 10_000
        paper = new boolean[height][width];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                paper[i][j] = false;
            }
        }
    }

    public void placePaper(int widthTemp, int heightTemp) {
        int size = 10;

        for (int i  = widthTemp; i < widthTemp + size; i++) {
            for (int j  = heightTemp; j < heightTemp + size; j++) {
                paper[i][j] = true;
            }
        }
    }

    public void showPaper() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Arrays.toString(paper[i]));
        }
    }

    public int answer() {
        // Big(O) : 10_000
        int answer = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (paper[i][j]) {answer += 1;}
            }
        }

        return answer;
    }
}

public class Main {

    final static String TEST_PATH = "/p2563/input/1.txt";

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Paper paper = new Paper();

        int inputCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < inputCount; i++) {
            String[] paperPosition = br.readLine().split(" ");
            int x = Integer.parseInt(paperPosition[0]);
            int y = Integer.parseInt(paperPosition[1]);
            paper.placePaper(x, y);
        }

        System.out.print(paper.answer());
    }
}

