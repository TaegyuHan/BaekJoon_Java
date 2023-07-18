package segmentTree.p10868;

import java.io.*;
import java.util.Arrays;

import static segmentTree.Path.SEGMENT_TREE_PATH;

/**
 * Solution code for "BaekJoon 문제".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/10868">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class SemgmentTree {
    private final int treeHeight;
    private final int treeSize;
    private final int[] leafNodevalues;
    private final int[] tree;
    private final int size;
    private final StringBuilder sb;

    SemgmentTree(int size) {
        this.size = size;
        this.treeHeight = (int) Math.ceil(Math.log(size) / Math.log(2));
        this.treeSize = (int) Math.pow(2, this.treeHeight + 1);
        this.tree = new int[this.treeSize];
        this.leafNodevalues = new int[this.size + 1];
        this.sb = new StringBuilder();
    }

    public void setLeafNodeValues(int index, int value) {
        this.leafNodevalues[index] = value;
    }

    private int init(int node, int start, int end) {

        if (start == end) {
            this.tree[node] = this.leafNodevalues[start];
            return this.tree[node];
        }

        int mid = (start + end) / 2;
        this.tree[node] = Math.min(
                init(node * 2, start, mid),
                init((node * 2) + 1, mid + 1, end)
        );
        return this.tree[node];
    }

    public void init() {
        init(1, 1, this.size);
    }

    public void printTreeData() {
        System.out.println("Tree Height: " + treeHeight);
        System.out.println("Tree Size: " + treeSize);

        System.out.println("Leaf Node Values:");
        System.out.println(Arrays.toString(leafNodevalues));

        System.out.println("Tree:");
        System.out.println(Arrays.toString(tree));

        System.out.println("Size: " + size);
    }

    private void showRange(int start, int end) {
        System.out.println("(" + start + ", " + end + ")");
    }

    public int min(int node, int startRange, int endRange, int start, int end) {
//        showRange(startRange, endRange);

        if (endRange < start || end < startRange) { // 범위에 포함 안됌
            return Integer.MAX_VALUE;
        }

        if (start <= startRange && endRange <= end) {
            return this.tree[node];
        }

        int mid = (startRange + endRange) / 2;
        return Math.min(
                min(node * 2, startRange, mid, start, end),
                min((node * 2) + 1, mid + 1, endRange, start, end)
        );
    }

    public void min(String[] data) {
        int start = Integer.parseInt(data[0]);
        int end = Integer.parseInt(data[1]);
        this.sb.append(min(1, 1, this.size, start, end))
                .append("\n");
    }

    public void answer() {
        System.out.print(this.sb);
    }
}

public class Main {

    private final static String TEST_PATH = "/p10868/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(SEGMENT_TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] data = br.readLine().split(" ");
        int size = Integer.parseInt(data[0]);
        int rangeCount = Integer.parseInt(data[1]);

        SemgmentTree semgmentTree = new SemgmentTree(size);
        for (int i = 1; i <= size; i++) {
            semgmentTree.setLeafNodeValues(i, Integer.parseInt(br.readLine()));
        }
        semgmentTree.init();

        for (int i = 0; i < rangeCount; i++) {
            semgmentTree.min(br.readLine().split(" "));
        }
//        semgmentTree.printTreeData();
        semgmentTree.answer();

        br.close();
    }
}