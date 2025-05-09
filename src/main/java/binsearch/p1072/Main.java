package binsearch.p1072;

import java.io.*;

import static binsearch.Path.BINSEARCH_PATH;

/**
 * Solution code for "BaekJoon 게임".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1072">Link</a>
 * 작성자: gksxorb147
 * BigO: O(log2(1_000_000_000)) 이분 탐색 시간 복잡도
 */
public class Main {

    private static final String TEST_PATH = "/p1072/input/5.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(BINSEARCH_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] input = br.readLine().split(" ");
        long gameCount = Long.parseLong(input[0]);
        long winCount = Long.parseLong(input[1]);

        BinarySearch binarySearch = new BinarySearch(gameCount, winCount);
        System.out.println(binarySearch.binarySearch());
    }
}

class BinarySearch {
    private final long gameCount;
    private final long winCount;
    private final long winRate;

    public BinarySearch(long gameCount, long winCount) {
        this.gameCount = gameCount;
        this.winCount = winCount;
        this.winRate = (winCount * 100) / gameCount;
    }

    public long binarySearch() {
        if (winRate >= 99) {
            return -1;
        }

        long left = 0;
        long right = 1_000_000_000;

        while (left <= right) {
            long mid = (left + right) / 2;
            if (isWinRateChanged(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean isWinRateChanged(long mid) {
        return ((winCount + mid) * 100) / (gameCount + mid) > winRate;
    }
}