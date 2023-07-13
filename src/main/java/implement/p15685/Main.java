package implement.p15685;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 드래곤 커브".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/15685">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
enum Direction {
    RIGHT(0, 1),
    UP(-1, 0),
    LEFT(0, -1),
    DOWN(1, 0);

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public static Direction turn90Angle(Direction direction) {
        switch (direction) {
            case RIGHT:
                return DOWN;
            case UP:
                return RIGHT;
            case LEFT:
                return UP;
            case DOWN:
                return LEFT;
        }
        throw new IllegalArgumentException("No direction for given direction");
    }

    public static Direction getByIndex(int index) {
        return Direction.values()[index];
    }

    public static Direction getByRowAndCol(int row, int col) {
        for (Direction direction : Direction.values()) {
            if (direction.row == row && direction.col == col) {
                return direction;
            }
        }
        throw new IllegalArgumentException("No direction for given row and col");
    }

}

class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

class DragonCurve {

    private final int row;
    private final int col;
    private final int direction;
    private final int generation;

    private List<Position> positionsList;
    private List<Direction> directionsList;

    DragonCurve(String datas) {
        String[] strData = datas.split(" ");
        this.row = Integer.parseInt(strData[1]);
        this.col = Integer.parseInt(strData[0]);
        this.direction = Integer.parseInt(strData[2]);
        this.generation = Integer.parseInt(strData[3]);
        this.positionsList = new ArrayList<>();
        generationInit();
    }

    private void directionsListInit() {
        this.directionsList = new ArrayList<>();
        for (int i = positionsList.size() - 1; 0 < i; i--) {
            Position beforePosition = positionsList.get(i);
            Position afterPosition = positionsList.get(i - 1);
            int tmpRow = afterPosition.getRow() - beforePosition.getRow();
            int tmpCol = afterPosition.getCol() - beforePosition.getCol();
            directionsList.add(Direction.turn90Angle(Direction.getByRowAndCol(tmpRow, tmpCol)));
        }
    }

    private void createCurve() {
        Position position = positionsList.get(positionsList.size() - 1);
        int currentRow = position.getRow();
        int currentCol = position.getCol();

        for (Direction direction : directionsList) {
            currentRow = currentRow + direction.getRow();
            currentCol = currentCol + direction.getCol();
            positionsList.add(new Position(currentRow, currentCol));
        }
    }

    private void generationInit() {
        for (int i = 0; i <= generation; i++) {
            if (i == 0) {
                Direction tmp = Direction.getByIndex(direction);
                int trow = tmp.getRow();
                int tcol = tmp.getCol();
                positionsList.add(new Position(row, col));
                positionsList.add(new Position(row + trow, col + tcol));
            } else {
                createCurve();
            }
            directionsListInit();
        }
    }

    public void showStats() {
        System.out.println(Arrays.toString(directionsList.toArray()));
        System.out.println(Arrays.toString(positionsList.toArray()));
        System.out.println();
    }

    public List<Position> getPositionsList() {
        return positionsList;
    }
}

class Grid {
    public static final int ROW_SIZE = 100;
    public static final int COL_SIZE = 100;
    private int[][] grid;

    Grid() {
        this.grid = new int[ROW_SIZE][COL_SIZE];
    }

    private boolean checkCount(int row, int col) {
        return grid[row][col] == 1
                && grid[row][col + 1] == 1
                && grid[row + 1][col] == 1
                && grid[row + 1][col + 1] == 1;
    }

    public int answer() {
        int answer = 0;
        for (int r = 0; r < ROW_SIZE - 1; r++) {
            for (int c = 0; c < COL_SIZE - 1; c++) {
                if (checkCount(r, c)) {
                    answer++;
                }
            }
        }
        return answer;
    }

    public void setDragonCurve(DragonCurve dragonCurve) {
        List<Position> positions = dragonCurve.getPositionsList();
        for (Position pos : positions) {
            grid[pos.getRow()][pos.getCol()] = 1;
        }
    }

    public void showGrid() {
        for (int r = 0; r < ROW_SIZE; r++) {
            System.out.println(Arrays.toString(grid[r]));
        }
    }
}

public class Main {

    private final static String TEST_PATH = "/p15685/input/4.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int dragonCurveCount = Integer.parseInt(br.readLine());
        Grid grid = new Grid();

        for (int i = 0; i < dragonCurveCount; i++) {
            DragonCurve dragonCurve = new DragonCurve(br.readLine());
            grid.setDragonCurve(dragonCurve);
        }
        System.out.print(grid.answer());

//        grid.showGrid();
        br.close();
    }
}