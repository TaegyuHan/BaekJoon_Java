package binsearch.p1300;

import java.io.*;

import static binsearch.Path.BINSEARCH_PATH;

/**
 * Solution code for "BaekJoon K번째 수".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1300">Link</a>
 * 작성자: gksxorb147
 * 
 * 입력 값
 * - N: 1 <= N <= 100_000
 * - K: 1 <= K <= N * N
 * BigO: O(N * log2(N * N)) 이분 탐색 시간 복잡도
 */
public class Main {

    private static final String TEST_PATH = "/p1300/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(BINSEARCH_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        BinarySearch binarySearch = new BinarySearch(N, K);
        System.out.println(binarySearch.binarySearch());
    }
}

class BinarySearch {
    private final int N;
    private final int K;

    public BinarySearch(int N, int K) {
        this.N = N;
        this.K = K;
    }

    public long binarySearch() {
        long left = 1;
        long right = (long) N * N;

        while (left <= right) {
            long mid = (left + right) / 2;
            long count = 0;

            for (int i = 1; i <= N; i++) {
                count += Math.min(mid / i, N);
            }

            if (count < K) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}