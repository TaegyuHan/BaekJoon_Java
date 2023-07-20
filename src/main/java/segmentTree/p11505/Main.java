package segmentTree.p11505;

import java.io.*;
import java.util.Arrays;

import static segmentTree.Path.SEGMENT_TREE_PATH;


/**
 * Solution code for "BaekJoon 구간 곱 구하기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/11505">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class SegmentTree {

    private final int size;
    private final int treeHeight;
    private final int treeSize;
    private final long[] tree;
    private final long[] data;

    private static final int DIV = 1_000_000_007;
    private static final int CHANGE = 1;
    private static final int MULTI = 2;

    SegmentTree(int size) {
        this.size = size;
        this.treeHeight = (int) Math.ceil(Math.log(this.size) / Math.log(2));
        this.treeSize = (int) Math.pow(2, this.treeHeight + 1);
        this.tree = new long[this.treeSize];
        this.data = new long[this.size + 1];
    }

    public void setData(int index, String data) {
        this.data[index] = Integer.parseInt(data);
    }

    private long initTree(int nodeIndex, int start, int end) {

        if (start == end) {
            this.tree[nodeIndex] = this.data[start];
            return this.tree[nodeIndex] % DIV;
        }

        int mid = (start + end) / 2;
        long left = initTree(nodeIndex * 2, start, mid);
        long right = initTree(nodeIndex * 2 + 1, mid + 1, end);

        this.tree[nodeIndex] = left * right % DIV;
        return this.tree[nodeIndex];
    }

    public void initTree() {
        initTree(1, 1, this.size);
    }

    public void cmd(String[] data) {
        int type = Integer.parseInt(data[0]);

        switch(type) {
            case CHANGE:
                int index = Integer.parseInt(data[1]);
                long num = Integer.parseInt(data[2]);
                changeTree(index, num);
                break;
            case MULTI:
                int start = Integer.parseInt(data[1]);
                int end = Integer.parseInt(data[2]);
                multi(start, end);
                break;
        }
    }

    private long changeTree(int nodeIndex, int startRange, int endRange, int start, int end, long data) {

        if (end < startRange || endRange < start) {
            return this.tree[nodeIndex];
        }

        if (startRange == start && end == endRange) {
            this.data[start] = data;
            this.tree[nodeIndex] = this.data[start];
            return this.tree[nodeIndex] % DIV;
        }

        // 여기 작성중
        int mid = (startRange + endRange) / 2;
        long left = changeTree(nodeIndex * 2, startRange, mid, start, end, data);
        long right = changeTree(nodeIndex * 2 + 1, mid + 1, endRange, start, end, data);


        this.tree[nodeIndex] = left * right % DIV;
        return this.tree[nodeIndex];
    }

    private void changeTree(int index, long data) {
        changeTree(1, 1, this.size, index, index, data);
    }

    private long multi(int nodeIndex, int startRange, int endRange, int start, int end) {
//        System.out.println(start + ", " + end);
        if (end < startRange || endRange < start) {
            return 1;
        }

        if (start <= startRange &&  endRange <= end) {
            return this.tree[nodeIndex] % DIV;
        }

        int mid = (startRange + endRange) / 2;
        long left = multi(nodeIndex * 2, startRange, mid, start, end);
        long right = multi(nodeIndex * 2 + 1, mid + 1, endRange, start, end);

        return left * right % DIV;
    }

    private void multi(int start, int end) {
        long answer = multi(1, 1, this.size, start, end);
        System.out.println(answer);
    }

    public void showData() {
        System.out.println(Arrays.toString(this.data));
        System.out.println(Arrays.toString(this.tree));
    }
}

public class Main {

    private final static String TEST_PATH = "/p11505/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(SEGMENT_TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] data = br.readLine().split(" ");
        int size = Integer.parseInt(data[0]);
        int changeCount = Integer.parseInt(data[1]);
        int calculationCount = Integer.parseInt(data[2]);

        SegmentTree segmentTree = new SegmentTree(size);

        for (int index = 1; index <= size; index++) {
            segmentTree.setData(index, br.readLine());
        }
        segmentTree.initTree();

        for (int i = 1; i <= changeCount + calculationCount; i++) {
            segmentTree.cmd(br.readLine().split(" "));
//            segmentTree.showData();
        }
        br.close();
    }
}