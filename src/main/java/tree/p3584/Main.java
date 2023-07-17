package tree.p3584;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static tree.Path.TREE_PATH;

/**
 * Solution code for "BaekJoon 가장 가까운 공통 조상".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/3584">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
class Node {
    private final int number;

    private boolean visited;

    private int parent;

    private final List<Integer> children;
    private int depth;

    public Node(int number) {
        this(number, -1);
    }

    public Node(int number, int parent) {
        this.visited = false;
        this.number = number;
        this.parent = parent;
        this.depth = -1;
        this.children = new ArrayList<>();
    }

    public void init() {
        this.parent = -1;
        this.depth = -1;
        this.visited = false;
        this.children.clear();
    }

    public List<Integer> getChildren() {
        return this.children;
    }

    public void setChildren(int children) {
        this.children.add(children);
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }

    public int getNumber() {
        return number;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

class Tree {
    private static final int MAX_TREE_SIZE = 10_000 + 1;
    private static final Node[] tree = new Node[MAX_TREE_SIZE];

    static {
        for (int i = 0; i < MAX_TREE_SIZE; i++) {
            Tree.tree[i] = new Node(i);
        }
    }

    private final int treeSize;
    private Node nodeA;
    private Node nodeB;

    public Tree(int size) {
        this.treeSize = size + 1;
        init();
    }

    public void setNode(String data) {
        String[] nodes = data.split(" ");

        int parent = Integer.parseInt(nodes[0]);
        int node = Integer.parseInt(nodes[1]);

        Tree.tree[node].setParent(parent);
        Tree.tree[parent].setChildren(node);
    }

    public void setTwoNode(String data) {
        String[] nodes = data.split(" ");
        this.nodeA = Tree.tree[Integer.parseInt(nodes[0])];
        this.nodeB = Tree.tree[Integer.parseInt(nodes[1])];
    }

    private void init() {
        for (int i = 0; i < MAX_TREE_SIZE; i++) {
            Tree.tree[i].init();
        }
    }

    private Node findRootNode() throws Exception {
        for (int i = 1; i < treeSize; i++) {
            if (Tree.tree[i].getParent() == -1) {
                return Tree.tree[i];
            }
        }
        throw new Exception("Root node does not exist.");
    }

    private void initDepth(Node node, int depth) {
        node.setVisited(true); // 노드 방문
        node.setDepth(depth);
        for (int nextNodeNumber : node.getChildren()) {
            Node nextNode = Tree.tree[nextNodeNumber];
            if (nextNode.isVisited()) { continue; }
            initDepth(nextNode, depth + 1);
        }
    }

    public void nodeStatus() {
        System.out.println("node number : " + this.nodeA.getNumber() + " node depth" + this.nodeA.getDepth());
        System.out.println("node number : " + this.nodeB.getNumber() + " node depth" + this.nodeB.getDepth());
    }

    private boolean isDepthEqual() {
        return this.nodeA.getDepth() == this.nodeB.getDepth();
    }

    private void setNodesAtSameDepth() {
        while (!isDepthEqual()) {
            if (this.nodeA.getDepth() < this.nodeB.getDepth()) {
                this.nodeB = Tree.tree[this.nodeB.getParent()];
            } else {
                this.nodeA = Tree.tree[this.nodeA.getParent()];
            }
        }
    }

    private boolean nodesSameCheck() {
        return this.nodeA.getNumber() == this.nodeB.getNumber();
    }

    private void nodeUp() {
        this.nodeA = Tree.tree[this.nodeA.getParent()];
        this.nodeB = Tree.tree[this.nodeB.getParent()];
    }

    public void getLCANodeNumber() throws Exception {
        Node rootNode = findRootNode();
        initDepth(rootNode, 0);
        setNodesAtSameDepth();

        while (!nodesSameCheck()) {
            nodeUp();
        }
    }

    public void answer() {
        System.out.println(this.nodeA.getNumber());
    }
}

public class Main {

    private final static String TEST_PATH = "/p3584/input/1.txt";

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream(TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        for (int i = 0; i < testCase; i++) {
            int treeSize = Integer.parseInt(br.readLine());
            Tree tree = new Tree(treeSize);
            for (int j = 0; j < treeSize - 1; j++) {
                tree.setNode(br.readLine());
            }
            tree.setTwoNode(br.readLine());
            tree.getLCANodeNumber();
            tree.answer();
        }
        br.close();
    }
}