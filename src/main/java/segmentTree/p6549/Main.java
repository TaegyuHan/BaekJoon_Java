package segmentTree.p6549;

import java.io.*;
import java.util.Arrays;

import static segmentTree.Path.SEGMENT_TREE_PATH;

/**
 * Solution code for "BaekJoon 히스토그램에서 가장 큰 직사각형".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/6549">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class SegmentTree {

    public static final int SIZE = 100_000;

    private int size;
    private final int treeHeight;
    private final int treeSize;
    private final int[] data;
    private final int[] minIndexTree;

    public SegmentTree() {

        //.트리 크기 지정
        this.size = SegmentTree.SIZE;
        this.treeHeight = (int) Math.ceil(Math.log(SegmentTree.SIZE) / Math.log(2));
        this.treeSize = (int) Math.pow(2, this.treeHeight + 1);

        // 데이터 받는 트리
        this.minIndexTree = new int[this.treeSize];
        this.data = new int[SegmentTree.SIZE + 1];
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setData(int index, String data) {
        this.data[index] = Integer.parseInt(data);
    }

    public void showData() {
        System.out.println("size : " + this.size);
        System.out.println("treeSize : " + this.treeSize);
        System.out.println("treeHeight : " + this.treeHeight);
        System.out.println("data : " + Arrays.toString(this.data));
        System.out.println("minIndexTree : " + Arrays.toString(this.minIndexTree));
    }

    private int initLowestHeightIndex(int nodeIndex, int start, int end) {
        if (start == end) {
            this.minIndexTree[nodeIndex] = start;
            return this.data[start];
        }
        int mid = (start + end) / 2;
        int leftIndex = nodeIndex * 2;
        int rightIndex = leftIndex + 1;

        int left = initLowestHeightIndex(leftIndex, start, mid);
        int right = initLowestHeightIndex(rightIndex, mid + 1, end);

        if (left <= right) { // 왼족이 작은경우
            this.minIndexTree[nodeIndex] = this.minIndexTree[leftIndex];
            return left;
        } else { // 오른쪽이 작은 경우
            this.minIndexTree[nodeIndex] = this.minIndexTree[rightIndex];
            return right;
        }
    }

    private int findLowestHeightIndex(int nodeIndex, int startRange, int endRange, int start, int end) {
        if (end < startRange || endRange < start ) {
            return -1;
        } // 범위에 포함 안됌

        if (start <= startRange && endRange <= end) {
            return this.minIndexTree[nodeIndex];
        } // 범위에 포함

        int mid = (startRange + endRange) / 2;
        int leftMinIndex = findLowestHeightIndex(nodeIndex * 2, startRange, mid, start, end);
        int rightMinIndex = findLowestHeightIndex(nodeIndex * 2 + 1, mid + 1, endRange , start, end);

        if (leftMinIndex == -1) { return rightMinIndex; }
        if (rightMinIndex == -1) { return leftMinIndex; }

        if (this.data[leftMinIndex] <= this.data[rightMinIndex]) { // 왼족이 작은경우
            return leftMinIndex;
        } else { // 오른쪽이 작은 경우
            return rightMinIndex;
        }
    }

    public void initLowestHeightIndex() {
        initLowestHeightIndex(1, 1, this.size);
    }

    private long maxArea(int nodeIndex, int start, int end) {
        if (end < start) { return 0; }

        int heightIndex = findLowestHeightIndex(1, 1, this.size, start, end);
        int height = this.data[heightIndex];
        long tmpArea = (long) (end - start + 1) * height;

//        System.out.println(start + " " + end);
//        System.out.println(tmpArea);

        if (start == end) { return tmpArea; }

        // 왼쪽
        tmpArea = Math.max(tmpArea, maxArea(nodeIndex * 2, start, heightIndex - 1));
        // 오른쪽
        tmpArea = Math.max(tmpArea, maxArea(nodeIndex * 2 + 1, heightIndex + 1, end));

        return tmpArea;
    }

    public void answer() {
        long maxArea = maxArea(1, 1, this.size);
        System.out.println(maxArea);
    }
}

public class Main {

    private final static String TEST_PATH = "/p6549/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(SEGMENT_TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        SegmentTree segmentTree = new SegmentTree();

        do {
            String[] data = br.readLine().split(" ");
            int size = Integer.parseInt(data[0]);
            if (size == 0) { break; }

            segmentTree.setSize(size);
            for (int i = 1; i <= size; i++) {
                segmentTree.setData(i, data[i]);
            }
            segmentTree.initLowestHeightIndex();
            segmentTree.answer();
        } while (true);
        br.close();
    }
}