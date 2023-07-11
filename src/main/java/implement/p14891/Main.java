package implement.p14891;

import java.io.*;
import java.util.Arrays;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 톱니바퀴".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/14891">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class Wheel {

    private int[] status;
    private static final int CLOCK_WISE = 1;
    private static final int COUNTER_CLOCK_WISE = -1;

    Wheel(int[] status) {
        this.status = status;
    }

    public int twelve() {
        return status[0];
    }

    public void showWheelStatus() {
        System.out.println(Arrays.toString(status));
    }

    public int leftStatus() {
        return status[6];
    }

    public int rightStatus() {
        return status[2];
    }

    public void turn(int turnType) {

        if (turnType == CLOCK_WISE) {
            int lastData = status[status.length - 1];
            for (int i = status.length - 1; 0 < i; i--) {
                status[i] = status[i - 1];
            }
            status[0] = lastData;

        } else if (turnType == COUNTER_CLOCK_WISE) {
            int firstData = status[0];
            for (int i = 0; i < status.length - 1; i++) {
                status[i] = status[i + 1];
            }
            status[status.length - 1] = firstData;
        }
    }
}


class WheelBox {

    private Wheel[] box = new Wheel[4];
    private Wheel one;
    private Wheel two;
    private Wheel three;
    private Wheel four;

    public void setWheel(int index, Wheel wheel) {
        box[index] = wheel;
    }

    public void showWheels() {
        for (Wheel wheel: box) {
            wheel.showWheelStatus();
        }
        System.out.println();
    }

    private void initWheel() {
        this.one = box[0];
        this.two = box[1];
        this.three = box[2];
        this.four = box[3];
    }

    private int turnTypeChanged(int turnType) {
        return turnType * -1;
    }

    private int[] turnChecked() {
        return new int[] {0, 0, 0, 0};
    }

    private void goTurn(int[] check) {
//        System.out.println(Arrays.toString(check));
        for (int i = 0; i < check.length; i++) {
            if (check[i] == 0) continue;
            box[i].turn(check[i]);
        }
    }

    public int answer() {
        int sum = 0;
        if (one.twelve() == 1) sum += 1;
        if (two.twelve() == 1) sum += 2;
        if (three.twelve() == 1) sum += 4;
        if (four.twelve() == 1) sum += 8;
        return sum;
    }

    public void turnWheel(int index, int turnType) {
        initWheel();
        switch (index) {
            case 1 -> oneWheel(turnType);
            case 2 -> twoWheel(turnType);
            case 3 -> threeWheel(turnType);
            case 4 -> fourWheel(turnType);
        }
    }

    private void oneWheel(int turnType) {
        int[] check = turnChecked();
        check[0] = turnType;

        if (one.rightStatus() != two.leftStatus()) {
            check[1] = turnTypeChanged(turnType);
            if (two.rightStatus() != three.leftStatus()) {
                if (three.rightStatus() != four.leftStatus()) {
                    check[3] = turnTypeChanged(turnType);
                }
                check[2] = turnType;
            }
        }

        goTurn(check);
    }

    private void twoWheel(int turnType) {
        int[] check = turnChecked();
        check[1] = turnType;

        if (one.rightStatus() != two.leftStatus()) {
            check[0] = turnTypeChanged(turnType);
        }

        if (two.rightStatus() != three.leftStatus()) {
            if (three.rightStatus() != four.leftStatus()) {
                check[3] = turnType;
            }
            check[2] = turnTypeChanged(turnType);
        }

        goTurn(check);
    }

    private void threeWheel(int turnType) {

        int[] check = turnChecked();
        check[2] = turnType;

        if (three.rightStatus() != four.leftStatus()) {
            check[3] = turnTypeChanged(turnType);
        }

        if (two.rightStatus() != three.leftStatus()) {
            if (one.rightStatus() != two.leftStatus()) {
                check[0] = turnType;
            }
            check[1] = turnTypeChanged(turnType);
        }

        goTurn(check);
    }

    private void fourWheel(int turnType) {
        int[] check = turnChecked();
        check[3] = turnType;

        if (three.rightStatus() != four.leftStatus()) {
            check[2] = turnTypeChanged(turnType);
            if (two.rightStatus() != three.leftStatus()) {
                if (one.rightStatus() != two.leftStatus()) {
                    check[0] = turnTypeChanged(turnType);
                }
                check[1] = turnType;
            }
        }

        goTurn(check);
    }
}

public class Main {

    private final static String TEST_PATH = "/p14891/input/7.txt";
    private final static int wheelCount = 4;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        WheelBox wheelBox = new WheelBox();

        for (int i = 0; i < wheelCount; i++) {
            String wheelStatus = br.readLine();
            int[] intWheel = new int[wheelStatus.length()];

            for (int j = 0; j < wheelStatus.length(); j++) {
                intWheel[j] = Character.getNumericValue(wheelStatus.charAt(j));
            }
            Wheel wheel = new Wheel(intWheel);
            wheelBox.setWheel(i, wheel);
        }

        int turnCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < turnCount; i++) {
            String[] data = br.readLine().split(" ");
            int boxIndex = Integer.parseInt(data[0]);
            int turnType = Integer.parseInt(data[1]);
//            System.out.print(boxIndex);
//            System.out.println(turnType);
            wheelBox.turnWheel(boxIndex, turnType);
//            wheelBox.showWheels();
        }
        br.close();

        System.out.print(wheelBox.answer());
    }
}