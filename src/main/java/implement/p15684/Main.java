package implement.p15684;

import java.io.*;
import java.util.Arrays;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 사다리 조작".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/15684">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */

class Space {
    private final int row;
    private final int col;

    private final int type;

    public Space(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public boolean typeEqualTo(Space other) {
        return other.type == type ;
    }

    public int getType() {
        return type;
    }
}

class Empty extends Space {

    public static final int TYPE = 0;

    public Empty(int row, int col) {
        super(row, col, TYPE);
    }

    @Override
    public String toString() {
        return String.valueOf(TYPE);
    }
}

class WidthLine extends Space {

    public static final int TYPE = 1;
    private final int number;

    public WidthLine(int row, int col, int number) {
        super(row, col, TYPE);
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}


class Ladder {
    private final int rowSize;
    private final int colSize;
    private int widthLineCount;

    private int answer;

    Space[][] ladder;

    public Ladder(int rowSize, int colSize) {
        this.rowSize = rowSize + 1;
        this.colSize = colSize + 1;
        this.widthLineCount = 0;
        this.answer = 0;
        initLadder();
    }

    private void initLadder() {
        this.ladder = new Space[rowSize][colSize];
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                ladder[r][c] = new Empty(r, c);
            }
        }
    }

    private void findAddWidthLinePositions() {
        for (int c = 1; c < colSize; c++) {
            for (int r = 0; r < rowSize; r++) {
                Space space = ladder[r][c];
                if (space.getType() != Empty.TYPE) { continue; }

                int rightCol = c + 1;
                if (!(1 <= rightCol && rightCol < colSize)) { // 설치 못하는 경우
                    continue;
                }
                Space rightSpace = ladder[r][rightCol];
                if (!space.typeEqualTo(rightSpace)) { // 설치 못하는 경우
                    continue;
                }

            }
        }
    }

    private boolean lineEqualCheck() {
        for (int c = 1; c < colSize; c++) {
            int currentCol = c;
            for (int r = 0; r < rowSize; r++) {
                int type = ladder[r][c].getType();
                // 없으면 직진
                if (type == Empty.TYPE) { continue; }

                // 왼쪽 확인
                int left = c - 1;
                if ((1 <= left && left < colSize)) {
                    currentCol = left;
                    continue;
                }

                // 오른쪽 확인
                int right = c + 1;
                if ((1 <= right && right < colSize)) {
                    currentCol = right;
                    continue;
                }

            }
            if (currentCol != c) {
                return false;
            }
        }

        return true;
    }

    public void setWidthLine(int number, String data) {
        String[] datas = data.split(" ");
        int row = Integer.parseInt(datas[0]);
        int col = Integer.parseInt(datas[1]);
        ladder[row][col] = new WidthLine(row, col, number);
        ladder[row][col + 1] = new WidthLine(row, col + 1, number);
        widthLineCount++;
    }

    public void showLadder() {
        for (int r = 0; r < rowSize; r++) {
            System.out.println(Arrays.toString(ladder[r]));
        }
        System.out.println(widthLineCount);
    }

    public void run() {
        findAddWidthLinePositions();

        System.out.println(lineEqualCheck());
        showLadder();
    }

    public int answer() {
        return answer;
    }
}


public class Main {

    private final static String TEST_PATH = "/p15684/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] data = br.readLine().split(" ");

        int lineCount = Integer.parseInt(data[0]);
        int widthLineCount = Integer.parseInt(data[1]);
        int height = Integer.parseInt(data[2]);

        Ladder ladder = new Ladder(height, lineCount);

        for (int i = 1; i <= widthLineCount; i++) {
            ladder.setWidthLine(i, br.readLine());
        }
        ladder.run();
        System.out.println(ladder.answer());

        // 문제 풀이
        // 현재 설치 가능한 위치 찾기 완료 > 리스트 만들어서 백트래킹 완성해야함.
        // 여러가지의 수를 적용해서 실행해 봐야함.


        br.close();
    }
}