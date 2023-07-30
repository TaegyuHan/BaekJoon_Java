package tree.p20364;


import java.util.Random;

public class TestCode {
    public static void main(String[] args) {

        int N = (int) Math.pow(2, 20);
        int Q = 1000;

        System.out.println(N + " " + Q);

        for (int i = 0; i < Q; i++) {
            int duckNumber = getRandomNumberInRange(2, N);
            System.out.println(duckNumber);
        }
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        // nextInt(max - min + 1)은 min부터 max까지의 범위의 랜덤한 정수를 반환합니다.
        return random.nextInt(max - min + 1) + min;
    }
}
