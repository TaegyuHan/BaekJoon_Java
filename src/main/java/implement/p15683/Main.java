package implement.p15683;

import java.io.*;
import java.util.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 감시".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/15683">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
abstract class Space {
    private final int row;
    private final int col;

    Space(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

class CCTV extends Space implements Comparable<CCTV> {

    private final int type;

    CCTV(int row, int col, int type) {
        super(row, col);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }

    @Override
    public int compareTo(CCTV other) {
        return Integer.compare(this.type, other.getType());
    }
}

class OneCCTV extends CCTV {

    public static final int[][][] direction = new int[][][]{
            {{-1, 0}}, // ^
            {{1, 0}},  // v
            {{0, 1}},  // >
            {{0, -1}}  // <
    };

    OneCCTV(int row, int col, int type) {
        super(row, col, type);
    }
}

class TwoCCTV extends CCTV {

    public static final int[][][] direction = new int[][][]{
            {{-1, 0}, {1, 0}},  // ^v
            {{0, -1}, {0, 1}}   // <>
    };

    TwoCCTV(int row, int col, int type) {
        super(row, col, type);
    }
}

class ThreeCCTV extends CCTV {

    public static final int[][][] direction = new int[][][]{
            {{-1, 0}, {0, 1}},  // ^>
            {{-1, 0}, {0, -1}}, // <^
            {{1, 0}, {0, 1}},   // v>
            {{1, 0}, {0, -1}}   // <v
    };

    ThreeCCTV(int row, int col, int type) {
        super(row, col, type);
    }
}

class FourCCTV extends CCTV {

    public static final int[][][] direction = new int[][][]{
            {{0, -1}, {-1, 0}, {0, 1}},  // <^>
            {{0, -1}, {1, 0}, {0, 1}},   // <v>

            {{-1, 0}, {0, 1}, {1, 0}},  // ㅏ
            {{-1, 0}, {0, -1}, {1, 0}},   // ㅓ
    };

    FourCCTV(int row, int col, int type) {
        super(row, col, type);
    }
}

class FiveCCTV extends CCTV {

    public static final int[][][] direction = new int[][][]{
            {{0, -1}, {0, 1}, {-1, 0}, {1, 0}},  // <^v>
    };

    FiveCCTV(int row, int col, int type) {
        super(row, col, type);
    }
}

class Watch extends Space {
    Watch(int row, int col) {
        super(row, col);
    }

    @Override
    public String toString() {
        return "#";
    }
}

class Empty extends Space {
    Empty(int row, int col) {
        super(row, col);
    }

    @Override
    public String toString() {
        return "0";
    }
}

class Wall extends Space {
    Wall(int row, int col) {
        super(row, col);
    }

    @Override
    public String toString() {
        return "6";
    }
}

class Room {
    private final int row;
    private final int col;
    private final Space[][] spaces;
    private Space[][] spacesTmp;
    private final List<CCTV> cctvList;
    private int answer;

    public final static int WALL = 6;
    public final static int EMPTY = 0;
    public final static int CCTV_1 = 1;
    public final static int CCTV_2 = 2;
    public final static int CCTV_3 = 3;
    public final static int CCTV_4 = 4;
    public final static int CCTV_5 = 5;

    Room(int row, int col) {
        this.row = row;
        this.col = col;
        this.spaces = new Space[row][col];
        this.cctvList = new ArrayList<>();
        this.answer = row * col;
    }

    public void setRoom(int index, String[] rowData) {
        for (int i = 0; i < rowData.length; i++) {
            int status = Integer.parseInt(rowData[i]);

            if (status == WALL) {
                spaces[index][i] = new Wall(index, i);
                continue;

            } else if (status == EMPTY) {
                spaces[index][i] = new Empty(index, i);
                continue;
            }

            switch (status) {
                case CCTV_1:
                    spaces[index][i] = new OneCCTV(index, i, status);
                    break;
                case CCTV_2:
                    spaces[index][i] = new TwoCCTV(index, i, status);
                    break;
                case CCTV_3:
                    spaces[index][i] = new ThreeCCTV(index, i, status);
                    break;
                case CCTV_4:
                    spaces[index][i] = new FourCCTV(index, i, status);
                    break;
                case CCTV_5:
                    spaces[index][i] = new FiveCCTV(index, i, status);
                    break;
            }
            cctvList.add((CCTV) spaces[index][i]);
        }
    }

    public boolean wallCheck(int row, int col) {
        return spacesTmp[row][col] instanceof Wall;
    }

    public boolean emptyCheck(int row, int col) {
        return spacesTmp[row][col] instanceof Empty;
    }

    public void showRoom() {
        for (int i = 0; i < row; i++) {
            System.out.println(Arrays.toString(spaces[i]));
        }
        System.out.println();
    }

    public void showTmpRoom() {
        for (int i = 0; i < row; i++) {
            System.out.println(Arrays.toString(spacesTmp[i]));
        }
        System.out.println();
    }

    private int watchCount(CCTV cctv, int[][] direction) {
        int watchCount = 0;
        int crow = cctv.getRow();
        int ccol = cctv.getCol();

        for (int[] rowAndCol : direction) {
            int drow = rowAndCol[0];
            int dcol = rowAndCol[1];

            for (int tmp = 1; tmp <= 8; tmp++) {
                int nrow = crow + (drow * tmp);
                int ncol = ccol + (dcol * tmp);

                if (!((0 <= nrow && nrow < row) && (0 <= ncol && ncol < col))) {
                    break;
                }
                if (wallCheck(nrow, ncol)) {
                    break;
                }

                if (emptyCheck(nrow, ncol)) {
                    watchCount += 1;
                }
            }
        }

        return watchCount;
    }

    private void watchApplication(CCTV cctv, int[][] direction) {
        int crow = cctv.getRow();
        int ccol = cctv.getCol();

        for (int[] rowAndCol : direction) {
            int drow = rowAndCol[0];
            int dcol = rowAndCol[1];

            for (int tmp = 1; tmp <= 8; tmp++) {
                int nrow = crow + (drow * tmp);
                int ncol = ccol + (dcol * tmp);

                if (!((0 <= nrow && nrow < row) && (0 <= ncol && ncol < col))) {
                    break;
                }
                if (wallCheck(nrow, ncol)) {
                    break;
                }

                if (emptyCheck(nrow, ncol)) {
                    spacesTmp[nrow][ncol] = new Watch(nrow, ncol);
                }
            }
        }

    }

    public int blindSpotCount() {
        int answer = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (spacesTmp[r][c] instanceof Empty) {
                    answer++;
                }
            }
        }
        return answer;
    }

    private void cctvOneOn(CCTV cctv) {
        int[][][] directions = new int[0][][];
        switch (cctv.getType()) {
            case CCTV_1:
                directions = OneCCTV.direction;
                break;
            case CCTV_2:
                directions = TwoCCTV.direction;
                break;
            case CCTV_3:
                directions = ThreeCCTV.direction;
                break;
            case CCTV_4:
                directions = FourCCTV.direction;
                break;
            case CCTV_5:
                directions = FiveCCTV.direction;
                break;
        }

        int maxWatchCount = 0;
        int[][] applicationDirection = new int[0][];
        for (int[][] direction : directions) {
            int tmpWatchCount = watchCount(cctv, direction);
            if (maxWatchCount <= tmpWatchCount) {
                maxWatchCount = tmpWatchCount;
                applicationDirection = direction;
            }
        }

        if (maxWatchCount != 0) {
            watchApplication(cctv, applicationDirection);
        }
    }

    private void spacesTmpInit() {
        Space[][] tmp = new Space[row][col];
        for (int i = 0; i < row; i++) {
            tmp[i] = spaces[i].clone();
        }
        spacesTmp = tmp;
    }

    public void cctvAllOn() {
        List<List<CCTV>> cctvPermutations = generatePermutations(cctvList);
        for (List<CCTV> cctvs : cctvPermutations) {
            spacesTmpInit();
            for (CCTV cctv : cctvs) {
                cctvOneOn(cctv);
            }
            answer = Math.min(answer, blindSpotCount());
        }
    }

    private List<List<CCTV>> generatePermutations(List<CCTV> cctvList) {
        List<List<CCTV>> permutations = new ArrayList<>();
        backtrack(permutations, new ArrayList<>(), cctvList);
        return permutations;
    }

    private void backtrack(List<List<CCTV>> permutations, List<CCTV> tempList, List<CCTV> cctvList) {
        if (tempList.size() == cctvList.size()) {
            permutations.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = 0; i < cctvList.size(); i++) {
            if (tempList.contains(cctvList.get(i))) {
                continue;  // 이미 선택한 요소는 건너뜀
            }
            tempList.add(cctvList.get(i));
            backtrack(permutations, tempList, cctvList);
            tempList.remove(tempList.size() - 1);
        }
    }

    public int getAnswer() {
        return answer;
    }
}

public class Main {

    private final static String TEST_PATH = "/p15683/input/9.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] size = br.readLine().split(" ");

        int row = Integer.parseInt(size[0]);
        int col = Integer.parseInt(size[1]);
        Room room = new Room(row, col);

        for (int i = 0; i < row; i++) {
            String[] rowData = br.readLine().split(" ");
            room.setRoom(i, rowData);
        }
        br.close();
        room.cctvAllOn();
        System.out.print(room.getAnswer());
    }
}