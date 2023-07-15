package implement.p5373;

import java.io.*;
import java.util.Arrays;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 큐빙".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/5373">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
enum Color {
    WHITE("w"),
    YELLOW("y"),
    RED("r"),
    ORANGE("o"),
    GREEN("g"),
    BLUE("b");

    private final String value;

    Color(String value) {
        this.value = value;
    }
}

enum Turn {
    U("U"),
    R("R"),
    L("L"),
    D("D"),
    B("B"),
    F("F");


    Turn(String value) {
        this.value = value;
    }

    private final String value;


}

class Piece {
    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String name = this.color.name().toLowerCase();
        return String.valueOf(name.charAt(0));
    }
}

class Side {
    public final static int ROW_SIZE = 3;
    public final static int COL_SIZE = 3;
    Piece[] state;

    Side(Color color) {
        state = new Piece[ROW_SIZE * COL_SIZE];
        initSide(color);
    }

    public void clockwise() {
        Piece index0 = state[0];
        Piece index1 = state[1];

        state[0] = state[6];
        state[1] = state[3];

        state[6] = state[8];
        state[3] = state[7];

        state[8] = state[2];
        state[7] = state[5];

        state[2] = index0;
        state[5] = index1;
    }

    public void counterClockwise() {
        Piece index0 = state[0];
        Piece index1 = state[1];

        state[0] = state[2];
        state[1] = state[5];

        state[2] = state[8];
        state[5] = state[7];

        state[8] = state[6];
        state[7] = state[3];

        state[6] = index0;
        state[3] = index1;
    }

    public Piece[] getRowTop() {
        return new Piece[]{state[0], state[1], state[2]};
    }

    public void setRowTop(Piece[] datas) {
        state[0] = datas[0];
        state[1] = datas[1];
        state[2] = datas[2];
    }

    public Piece[] getRowMid() {
        return new Piece[]{state[3], state[4], state[5]};
    }

    public void setRowMid(Piece[] datas) {
        state[3] = datas[0];
        state[4] = datas[1];
        state[5] = datas[2];
    }

    public Piece[] getRowBot() {
        return new Piece[]{state[6], state[7], state[8]};
    }

    public void setRowBot(Piece[] datas) {
        state[6] = datas[0];
        state[7] = datas[1];
        state[8] = datas[2];
    }

    public Piece[] getColLeft() {
        return new Piece[]{state[0], state[3], state[6]};
    }

    public void setColLeft(Piece[] datas) {
        state[0] = datas[0];
        state[3] = datas[1];
        state[6] = datas[2];
    }

    public Piece[] getColMid() {
        return new Piece[]{state[1], state[4], state[7]};
    }

    public void setColMid(Piece[] datas) {
        state[1] = datas[0];
        state[4] = datas[1];
        state[7] = datas[2];
    }

    public Piece[] getColRight() {
        return new Piece[]{state[2], state[5], state[8]};
    }

    public void setColRight(Piece[] datas) {
        state[2] = datas[0];
        state[5] = datas[1];
        state[8] = datas[2];
    }

    public void initSide(Color color) {
        for (int index = 0; index < ROW_SIZE * COL_SIZE; index++) {
            state[index] = new Piece(color);
        }
    }

    public void showSide() {
        for (int i = 1; i < (ROW_SIZE * COL_SIZE) + 1; i++) {
            System.out.print(state[i - 1]);
            if (i % 3 == 0) {
                System.out.println();
            }
        }
    }
}

class Cube {

    private static final String MINUS = "-";
    private static final String PLUS = "+";

    private Side up;
    private Side down;
    private Side front;
    private Side back;
    private Side left;
    private Side right;

    private int turnCount;

    private String[] turnInfos;

    Cube() {
        up = new Side(Color.WHITE);
        down = new Side(Color.YELLOW);
        front = new Side(Color.RED);
        back = new Side(Color.ORANGE);
        left = new Side(Color.GREEN);
        right = new Side(Color.BLUE);
    }

    public void cubeInit() {
        up.initSide(Color.WHITE);
        down.initSide(Color.YELLOW);
        front.initSide(Color.RED);
        back.initSide(Color.ORANGE);
        left.initSide(Color.GREEN);
        right.initSide(Color.BLUE);
    }

    public void setTurnCount(String turnCount) {
        this.turnCount = Integer.parseInt(turnCount);
    }

    public void setTurnInfo(String turnInfo) {
        this.turnInfos = turnInfo.split(" ");
    }

    private Piece[] reverse(Piece[] datas) {
        Piece[] temp = new Piece[datas.length];
        for (int i = 0; i < datas.length; i++) {
            temp[i] = datas[datas.length - 1 - i];
        }
        return temp;
    }

    private void turnUp(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnUpMinus();
        } else if (clockWise.equals(PLUS)) {
            turnUpPlus();
        }
    }

    private void turnRight(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnRightMinus();
        } else if (clockWise.equals(PLUS)) {
            turnRightPlus();
        }
    }

    private void turnLeft(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnLeftMinus();
        } else if (clockWise.equals(PLUS)) {
            turnLeftPlus();
        }
    }

    private void turnDown(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnDownMinus();
        } else if (clockWise.equals(PLUS)) {
            turnDownPlus();
        }
    }

    private void turnBack(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnBackMinus();
        } else if (clockWise.equals(PLUS)) {
            turnBackPlus();
        }
    }

    private void turnFront(String clockWise) {
        if (clockWise.equals(MINUS)) {
            turnFrontMinus();
        } else if (clockWise.equals(PLUS)) {
            turnFrontPlus();
        }
    }

    private void turnUpMinus() {
        up.counterClockwise();
        Piece[] tmp = back.getRowTop();
        back.setRowTop(right.getRowTop());
        right.setRowTop(front.getRowTop());
        front.setRowTop(left.getRowTop());
        left.setRowTop(tmp);
    }

    private void turnUpPlus() {
        up.clockwise();
        Piece[] tmp = back.getRowTop();
        back.setRowTop(left.getRowTop());
        left.setRowTop(front.getRowTop());
        front.setRowTop(right.getRowTop());
        right.setRowTop(tmp);
    }

    private void turnDownMinus() {
        down.counterClockwise();
        Piece[] tmp = back.getRowBot();
        back.setRowBot(left.getRowBot());
        left.setRowBot(front.getRowBot());
        front.setRowBot(right.getRowBot());
        right.setRowBot(tmp);
    }

    private void turnDownPlus() {
        down.clockwise();
        Piece[] tmp = back.getRowBot();
        back.setRowBot(right.getRowBot());
        right.setRowBot(front.getRowBot());
        front.setRowBot(left.getRowBot());
        left.setRowBot(tmp);
    }

    private void turnRightMinus() {
        right.counterClockwise();
        Piece[] tmp = up.getColRight();
        up.setColRight(reverse(back.getColLeft()));
        back.setColLeft(reverse(down.getColRight()));
        down.setColRight(front.getColRight());
        front.setColRight(tmp);
    }

    private void turnRightPlus() {
        right.clockwise();
        Piece[] tmp = up.getColRight();
        up.setColRight(front.getColRight());
        front.setColRight(down.getColRight());
        down.setColRight(reverse(back.getColLeft()));
        back.setColLeft(reverse(tmp));
    }

    private void turnLeftMinus() {
        left.counterClockwise();
        Piece[] tmp = up.getColLeft();
        up.setColLeft(front.getColLeft());
        front.setColLeft(down.getColLeft());
        down.setColLeft(reverse(back.getColRight()));
        back.setColRight(reverse(tmp));
    }

    private void turnLeftPlus() {
        left.clockwise();
        Piece[] tmp = up.getColLeft();
        up.setColLeft(reverse(back.getColRight()));
        back.setColRight(reverse(down.getColLeft()));
        down.setColLeft(front.getColLeft());
        front.setColLeft(tmp);
    }

    private void turnBackMinus() {
        back.counterClockwise();
        Piece[] tmp = up.getRowTop();
        up.setRowTop(reverse(left.getColLeft()));
        left.setColLeft(down.getRowBot());
        down.setRowBot(reverse(right.getColRight()));
        right.setColRight(tmp);
    }

    private void turnBackPlus() {
        back.clockwise();
        Piece[] tmp = up.getRowTop();
        up.setRowTop(right.getColRight());
        right.setColRight(reverse(down.getRowBot()));
        down.setRowBot(left.getColLeft());
        left.setColLeft(reverse(tmp));
    }

    private void turnFrontMinus() {
        front.counterClockwise();
        Piece[] tmp = up.getRowBot();
        up.setRowBot(right.getColLeft());
        right.setColLeft(reverse(down.getRowTop()));
        down.setRowTop(left.getColRight());
        left.setColRight(reverse(tmp));
    }

    private void turnFrontPlus() {
        front.clockwise();
        Piece[] tmp = up.getRowBot();
        up.setRowBot(reverse(left.getColRight()));
        left.setColRight(down.getRowTop());
        down.setRowTop(reverse(right.getColLeft()));
        right.setColLeft(tmp);
    }

    public void turn() {
        for (String info : turnInfos) {
            String direction = info.substring(0, 1);
            String clockWise = info.substring(1, 2);
            if (direction.equals(Turn.U.name())) {
                turnUp(clockWise);
            } else if (direction.equals(Turn.R.name())) {
                turnRight(clockWise);
            } else if (direction.equals(Turn.L.name())) {
                turnLeft(clockWise);
            } else if (direction.equals(Turn.D.name())) {
                turnDown(clockWise);
            } else if (direction.equals(Turn.B.name())) {
                turnBack(clockWise);
            } else if (direction.equals(Turn.F.name())) {
                turnFront(clockWise);
            }
//            System.out.println(info);
//            showCubeInfo();
        }
    }

    public void answer() {
        up.showSide();
    }

    public void showCubeInfo() {
        System.out.println("         " + Arrays.toString(up.getRowTop()));
        System.out.println("         " + Arrays.toString(up.getRowMid()));
        System.out.println("         " + Arrays.toString(up.getRowBot()));

        System.out.print(Arrays.toString(left.getRowTop()));
        System.out.print(Arrays.toString(front.getRowTop()));
        System.out.print(Arrays.toString(right.getRowTop()));
        System.out.println(Arrays.toString(back.getRowTop()));

        System.out.print(Arrays.toString(left.getRowMid()));
        System.out.print(Arrays.toString(front.getRowMid()));
        System.out.print(Arrays.toString(right.getRowMid()));
        System.out.println(Arrays.toString(back.getRowMid()));

        System.out.print(Arrays.toString(left.getRowBot()));
        System.out.print(Arrays.toString(front.getRowBot()));
        System.out.print(Arrays.toString(right.getRowBot()));
        System.out.println(Arrays.toString(back.getRowBot()));

        System.out.println("         " + Arrays.toString(down.getRowTop()));
        System.out.println("         " + Arrays.toString(down.getRowMid()));
        System.out.println("         " + Arrays.toString(down.getRowBot()));

        System.out.println(turnCount);
        System.out.println(Arrays.toString(turnInfos));
        System.out.println("================ > ");
    }
}

public class Main {

    private final static String TEST_PATH = "/p5373/input/6.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Cube cube = new Cube();

        int testCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < testCount; i++) {
            cube.cubeInit();
            cube.setTurnCount(br.readLine());
            cube.setTurnInfo(br.readLine());
//            cube.showCubeInfo();
            cube.turn();
//            cube.showCubeInfo();
            cube.answer();
        }
        br.close();
    }
}