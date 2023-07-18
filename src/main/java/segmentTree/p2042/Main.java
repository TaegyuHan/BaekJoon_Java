package segmentTree.p2042;

import java.io.*;

import static segmentTree.Path.SEGMENT_TREE_PATH;

/**
 * Solution code for "BaekJoon 구간 합 구하기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2042">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */

class SegmentTree {
    private final long[] tree;

    private final long[] leafNode;
    private final int size;
    private final int treeSize;
    private final int treeHeight;
    private final StringBuilder sb;

    public SegmentTree(int size) {
        this.size = size;
        this.treeHeight = (int) Math.ceil(Math.log(size) / Math.log(2));
        this.treeSize = (int) Math.pow(2, this.treeHeight + 1);
        this.leafNode = new long[size + 1];
        this.tree = new long[this.treeSize];
        this.sb = new StringBuilder();
    }
    
    public void setLeafNode(int index, long leafNode) {
        this.leafNode[index] = leafNode;
    }

    private long init(int start, int end, int node) {
        if (start == end) {
            return tree[node] = this.leafNode[start];
        }

        int mid = (start + end) / 2;
        return tree[node] = init(start, mid, node * 2) + init(mid + 1, end, node * 2 + 1);
    }

    public void init() {
        init(1, this.size, 1);
    }

    public void cmd(String data) {
        String[] datas = data.split(" ");
        int type = Integer.parseInt(datas[0]);

        if (type == 1) {
            int index = Integer.parseInt(datas[1]);
            long value = Long.parseLong(datas[2]);

            long diff = value - this.leafNode[index];
            this.leafNode[index] = value;
            update(1, this.size, 1, index, diff);
        } else  {
            int start = Integer.parseInt(datas[1]);
            int end = Integer.parseInt(datas[2]);
            this.sb.append(sum(1, this.size, 1, start, end))
                    .append("\n");
        }
    }

    private long sum(int start, int end, int node, int left, int right) {
        // 범위 밖에 있는 경우
        if (left > end || right < start) {
            return 0;
        }

        // 범위 안에 있는 경우
        if (left <= start && end <= right) {
            return tree[node];
        }

        // 그렇지 않다면, 두 부분으로 나누어 합을 구하기
        int mid = (start + end) / 2;
        return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
    }

    private void update(int start, int end, int node, int idx, long dif) {
        // 범위 밖에 있는 경우
        if (idx < start || idx > end) {
            return;
        }

        // 범위 안에 있으면 내려가며 다른 원소도 갱신
        tree[node] += dif;
        if (start == end) {
            return;
        }

        int mid = (start + end) / 2;
        update(start, mid, node * 2, idx, dif);
        update(mid + 1, end, node * 2 + 1, idx, dif);
    }

    public void answer() {
        System.out.print(this.sb);
    }
}

public class Main {

    private final static String TEST_PATH = "/p2042/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(SEGMENT_TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] data = br.readLine().split(" ");
        int size = Integer.parseInt(data[0]);
        int changeCount = Integer.parseInt(data[1]);
        int sumQueryCount = Integer.parseInt(data[2]);

        SegmentTree segmentTree = new SegmentTree(size);

        for (int i = 1; i <= size; i++) {
            segmentTree.setLeafNode(i, Long.parseLong(br.readLine()));
        }
        segmentTree.init();
//        segmentTree.showSegmentTree();

        for (int i = 0; i < changeCount + sumQueryCount; i++) {
            segmentTree.cmd(br.readLine());
//            segmentTree.showSegmentTree();
        }

        segmentTree.answer();
        br.close();
    }
}