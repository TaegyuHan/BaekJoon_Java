package tree.p1135;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static tree.Path.TREE_PATH;

/**
 * Solution code for "BaekJoon 뉴스 전하기".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1135">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class Node implements Comparable<Node> {
    private final int index;

    private final List<Node> children;

    private int time;
    private int nodeCount;

    public Node(int index) {
        this.index = index;
        this.time = 0;
        this.nodeCount = 1;
        this.children = new ArrayList<>();
    }

    public void addChildren(Node node) {
        this.children.add(node);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    @Override
    public String toString() {
        return String.valueOf(this.index);
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public void childrenSort() {
        this.children.sort(Collections.reverseOrder());
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.time, o.time);
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void addNodeCount(int childrenCount) {
        this.nodeCount += childrenCount;
    }

    public Node getChildren(int index) {
        return this.children.get(index - 1);
    }

    public int getIndex() {
        return this.index;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

class Tree {
    private final int size;
    private final Node[] tree;

    public Tree(int size, int[] treeData) {
        this.size = size;
        this.tree = new Node[this.size];
        treeInit(treeData); // O(N)
    }

    private void treeInit(int[] parents) {
        for (int i = 0; i < this.size; i++) {
            this.tree[i] = new Node(i);
            if (i != 0) {
                this.tree[parents[i]].addChildren(this.tree[i]);
            }
        }
    }

    private int treeSort(Node node) {
        if (node.isLeaf()) {
            return 1;
        }

        for (Node childrenNode : node.getChildren()) {
            int count = treeSort(childrenNode);
            node.addNodeCount(count);
        }
        node.childrenSort();
        return node.getNodeCount();
    }

    private int timeCheck(Node node) {
        System.out.println(node.getIndex());
        if (node.isLeaf()) {
            return 0;
        }

        int maxTime = 0;
        for (int time = 1; time <= node.getChildrenSize(); time++) {
            Node childrenNode = node.getChildren(time);
            int childrenTime = timeCheck(childrenNode);
            maxTime = Math.max(maxTime, time + childrenTime);
            childrenNode.setTime(maxTime);
        }

        System.out.println(node.getIndex() + " " + maxTime);
        return maxTime;
    }

    public void answer() {
        Node rootNode = this.tree[0];
        treeSort(rootNode);
        int minTime = timeCheck(rootNode);
        System.out.print(minTime);
    }

    public void showData() {
        System.out.println(Arrays.toString(this.tree));
    }
}

public class Main {

    private final static String TEST_PATH = "/p1135/input/4.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        int[] parents = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        br.close();

        Tree tree = new Tree(size, parents);
        tree.answer();
//        tree.showData();
    }
}