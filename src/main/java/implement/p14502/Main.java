package implement.p14502;

import java.io.*;
import java.util.*;

import static implement.Path.IMPLEMENT_PATH;

public class Main {

    final static String TEST_PATH = "/p14502/input/1.txt";
    final static int VIRUS = 2;
    final static int WALL = 1;
    final static int EMPTY = 0;
    final static int ADD_WALL = 3;
    private static int row;
    private static int col;
    private static int[][] board;
    private static Set<int[]> virusSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer sr = new StringTokenizer(br.readLine());
        row = Integer.parseInt(sr.nextToken());
        col = Integer.parseInt(sr.nextToken());
        board = new int[row][col];

        // 데이터 받기
        for (int i = 0; i < row; i++) {
            int[] rowTmp = new int[col];
            StringTokenizer sRow = new StringTokenizer(br.readLine());
            for (int j = 0; j < col; j++) {
                rowTmp[j] = Integer.parseInt(sRow.nextToken());
                if (rowTmp[j] == VIRUS) {
                    virusSet.add(new int[]{i, j});
                }
            }
            board[i] = rowTmp;
        }

        // 추가적인 벽 선택
        

        br.close();
    }
}
