package implement.p1913;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 달팽이".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1913">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
enum Move {
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int row;
    private final int col;

    Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Move turn() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }
}

class Snail {
    private final int checkNumber;

    private int row;

    private int col;
    private int number;
    private int[] answerPositions;
    private Move currentDirection;

    public Snail(int row, int col, int number) {
        this.row = row;
        this.col = col;
        this.number = 1;
        this.checkNumber = number;
        this.currentDirection = Move.UP;
    }

    public int[] turnMovePosition() {
        Move turn = currentDirection.turn();
        return new int[]{row + turn.getRow(), col + turn.getCol()};
    }

    public void move() {
        row += currentDirection.getRow();
        col += currentDirection.getCol();
    }

    public int[] getPositions() {
        return new int[]{row, col};
    }

    public int getNumber() {
        return number;
    }

    public void check() {
        if (checkNumber == number) {
            answerPositions = new int[]{row, col}; // 체크 숫자 맞으면 정답으로 교체
        }
        number++; // 숫자 증가
    }

    public void turn() {
        this.currentDirection = currentDirection.turn();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void getAnswer() {
        System.out.print((answerPositions[0] + 1) + " " + (answerPositions[1] + 1));
    }
}

class Board {
    private int[][] board;
    private final int size;

    private Snail snail;

    public Board(int size, int number) {
        this.size = size;
        this.board = new int[size][size];
        this.snail = new Snail(size / 2, size / 2, number);
    }

    public void moveSnail() {
        // 현위치 숫자 입력
        while (true) {
            visit(); // 방문
            snail.check(); // 체크 숫자 확인
            snail.move(); // 움직임

            if (!boardOutCheck()) {
                break;
            }
            if (turnCheck(snail.turnMovePosition())) { // 회전 가능 확인
                snail.turn();
            }
        }
    }

    private boolean boardOutCheck() {
        int row = snail.getRow();
        int col = snail.getCol();
        return (0 <= row && row < size) && (0 <= col && col < size);
    }

    private boolean turnCheck(int[] position) {
        return board[position[0]][position[1]] == 0;
    }

    private void visit() {
        board[snail.getRow()][snail.getCol()] = snail.getNumber();
    }

    public void showMap() {
        for (int[] row : board) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public void numberPosition() {
        snail.getAnswer();
    }
}

public class Main {

    private final static String TEST_PATH = "/p1913/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        int number = Integer.parseInt(br.readLine());
        Board board = new Board(size, number);
        board.moveSnail();
        board.showMap();
        board.numberPosition();
        br.close();
    }
}