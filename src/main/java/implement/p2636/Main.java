package implement.p2636;

import java.io.*;
import java.util.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 치즈".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2636">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class Space {

    private final int row;
    private final int col;

    private final int type;

    Space(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

class Empty extends Space {

    public static final int TYPE = 0;

    Empty(int row, int col) {
        super(row, col, Empty.TYPE);
    }

    @Override
    public String toString() {
        return String.valueOf(Empty.TYPE);
    }
}

class Cheese extends Space {

    public static final int TYPE = 1;

    Cheese(int row, int col) {
        super(row, col, Cheese.TYPE);
    }

    @Override
    public String toString() {
        return String.valueOf(Cheese.TYPE);
    }
}

class Board {
    public static final int[][] nextTmp = {
            {-1, 0},
            {1, 0},
            {0, 1},
            {0, -1},
    };
    private final int rowSize;
    private final int colSize;
    private int meltCount;
    private Space[][] board;
    private boolean[][] bfsCheck;
    private List<Space> beforeMeltPositions;
    private List<Space> currentMeltPositions;

    Board(int row, int col) {
        this.rowSize = row;
        this.colSize = col;
        this.board = new Space[row][col];
        this.bfsCheck = new boolean[row][col];
        this.currentMeltPositions = new ArrayList<>();
        this.beforeMeltPositions = new ArrayList<>();
        this.meltCount = 0;
    }

    private void bfsCheckReset() {
        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                bfsCheck[r][c] = false;
            }
        }
        beforeMeltPositions = currentMeltPositions;
        currentMeltPositions = new ArrayList<>();  // 현재 녹일 수 있는 치즈
    }

    private boolean currentMeltCheck() {
        bfsCheckReset();
        Queue<Space> queue = new LinkedList<>();
        queue.add(board[0][0]);

        while (queue.size() != 0) {
            Space cSpace = queue.remove();
            int cRow = cSpace.getRow();
            int cCol = cSpace.getCol();
            if (bfsCheck[cRow][cCol]) { continue; } // 이미 방문한 장소
            bfsCheck[cRow][cCol] = true; // 방문 처리

            if (cSpace.getType() == Cheese.TYPE) { // 치즈인 경우
                currentMeltPositions.add(cSpace);
                continue;
            }

            for (int[] tmp : nextTmp) {
                int nRow = tmp[0] + cRow;
                int nCol = tmp[1] + cCol;
                if (!((0 <= nRow && nRow < rowSize) && (0 <= nCol && nCol < colSize))) { continue; }
                if (bfsCheck[nRow][nCol]) { continue; } // 이미 방문한 장소
                queue.add(board[nRow][nCol]);
            }
        }

        return currentMeltPositions.size() != 0;
    }

    private void currentMeltApplication() {
        for (Space space : currentMeltPositions) {
            int row = space.getRow();
            int col = space.getCol();
            board[row][col] = new Empty(row, col);
        }
    }

    public void meltStart() {
        while (currentMeltCheck()) {
            currentMeltApplication();
            meltCount++;
        }
    }

    public void answer() {
        System.out.println(meltCount);
        System.out.print(beforeMeltPositions.size());
    }

    public void setBoard(int rowIndex, String[] rowData) {
        Space[] spaceDatas = new Space[colSize];

        for (int i = 0; i < rowData.length; i++) {

            int dataType = Integer.parseInt(rowData[i]);
            Space data = null;

            switch (dataType) {
                case Empty.TYPE:
                    data = new Empty(rowIndex, i);
                    break;
                case Cheese.TYPE:
                    data = new Cheese(rowIndex, i);
                    break;
            }
            spaceDatas[i] = data;
        }
        board[rowIndex] = spaceDatas;
    }

    public void showBoard() {
        for (int r = 0; r < rowSize; r++) {
            System.out.print(Arrays.toString(board[r]));
            System.out.print(" ");
            System.out.println(Arrays.toString(bfsCheck[r]));
        }
        System.out.println(Arrays.toString(currentMeltPositions.toArray()));
        System.out.println(Arrays.toString(beforeMeltPositions.toArray()));
    }
}

public class Main {

    private final static String TEST_PATH = "/p2636/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] boardSize = br.readLine().split(" ");
        int row = Integer.parseInt(boardSize[0]);
        int col = Integer.parseInt(boardSize[1]);
        Board board = new Board(row, col);

        for (int i = 0; i < row; i++) {
            board.setBoard(i, br.readLine().split(" "));
        }
        br.close();
        board.meltStart();
//        board.showBoard();
        board.answer();
    }
}