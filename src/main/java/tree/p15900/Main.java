package tree.p15900;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static tree.Path.TREE_PATH;


/**
 * Solution code for "BaekJoon 나무 탈출".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/15900">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class Node {
    private final int number;

    private boolean visited;

    private final List<Integer> connectedNodes;

    public Node(int number) {
        this.number = number;
        this.visited = false;
        this.connectedNodes = new ArrayList<>();
    }
    public void visited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public void connect(int number) {
        this.connectedNodes.add(number);
    }

    public List<Integer> connectList() {
        return connectedNodes;
    }

    public boolean isLeafNode() {
        return this.connectedNodes.size() == 1;
    }

    public int getNumber() {
        return number;
    }
}

class Tree {

    private final int treeSize;
    private final List<Node> tree;

    private int moveCount;

    public Tree(int nodeCount) {
        this.treeSize = nodeCount + 1;
        this.tree = new ArrayList<>(treeSize);
        this.moveCount = 0;
        nodeInit();
    }

    private void dfs(int startNode, int moveCount) {
        Node node = this.tree.get(startNode);
        node.visited();

        if (node.isLeafNode() && node.getNumber() != 1) {
            this.moveCount += moveCount;
            return;
        }

        for (Integer nextNode : node.connectList()) {
            if (this.tree.get(nextNode).isVisited()) { continue; }
            dfs(nextNode, moveCount + 1);
        }
    }

    private void nodeInit() {
        for (int i = 0; i < this.treeSize; i++) {
            this.tree.add(new Node(i));
        }
    }

    public void setNode(String nodeData) {
        String[] data = nodeData.split(" ");
        int node0 = Integer.parseInt(data[0]);
        int node1 = Integer.parseInt(data[1]);

        this.tree.get(node0).connect(node1);
        this.tree.get(node1).connect(node0);
    }

    public void gameStart() {
        dfs(1, 0);
    }

    public void answer() {
//        System.out.println(moveCount);
        if (moveCount % 2 == 0) {
            System.out.print("No");
        } else {
            System.out.print("Yes");
        }
    }
}

public class Main {

    private final static String TEST_PATH = "/p15900/input/2.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int nodeCount = Integer.parseInt(br.readLine());
        Tree tree = new Tree(nodeCount);

        for (int i = 0; i < nodeCount - 1; i++) {
            tree.setNode(br.readLine());
        }
        br.close();
        tree.gameStart();
        tree.answer();
    }
}