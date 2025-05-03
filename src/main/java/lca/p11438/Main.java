package lca.p11438;

import static lca.Path.LCA_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Solution code for "BaekJoon LCA 2".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/11438">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {
    
    public static String TEST_PATH = "/p11438/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(LCA_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int nodeCount = Integer.parseInt(br.readLine());
        Tree tree = new Tree(nodeCount);

        for (int i = 0; i < nodeCount - 1; ++i) {
            String[] input = br.readLine().split(" ");
            int node1 = Integer.parseInt(input[0]);
            int node2 = Integer.parseInt(input[1]);   
            tree.addEdge(node1, node2);
        }
        tree.buildTree(1);

        BinaryLifting binaryLifting = new BinaryLifting(tree);
        // binaryLifting.showParent();

        StringBuilder sb = new StringBuilder();

        int queryCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < queryCount; ++i) {
            String[] input = br.readLine().split(" ");
            int node1 = Integer.parseInt(input[0]);
            int node2 = Integer.parseInt(input[1]);
            int result = binaryLifting.getLCA(node1, node2);
            sb.append(result).append("\n");
        }

        System.out.println(sb.toString());
    }
}

class BinaryLifting {
    private int[][] parent;
    private int[] depth;
    private int maxDepth;
    private int nodeCount;

    public BinaryLifting(Tree tree) {
        this.nodeCount = tree.getNodeCount();
        this.maxDepth = 1;

        while ((1 << this.maxDepth) <= this.nodeCount) {
            this.maxDepth++; // 최대 깊이 계산
        }

        this.parent = new int[this.nodeCount + 1][this.maxDepth];
        this.depth = new int[this.nodeCount + 1];
        
        // 1. 깊이와 1번째 부모 노드 채우기
        for (int i = 1; i <= this.nodeCount; i++) {
            Node node = tree.getNode(i);
            depth[i] = node.getDepth();
            parent[i][0] = node.getParent() != null ? node.getParent().getNumber() : 0;
        }

        // 2. 최소 배열 채우기
        for (int depth = 1; depth < this.maxDepth; ++depth) {
            for (int node = 1; node <= this.nodeCount; ++node) {
                if (parent[node][depth - 1] != 0) {
                    parent[node][depth] = parent[parent[node][depth - 1]][depth - 1];
                }
            }
        }
    }

    public void showParent() {
        for (int j = 1; j <= this.nodeCount; j++) {
            System.out.print(j + " : ");
            for (int i = 0; i < this.maxDepth; i++) {
                System.out.print(this.parent[j][i] + " ");
            }
            System.out.println();
        }
    }

    public int getLCA(int node1, int node2) {    

        // node1이 더 깊은 노드가 되도록 한다.
        if (this.depth[node1] < this.depth[node2]) {
            int temp = node1;
            node1 = node2;
            node2 = temp;
        }
        
        // node1의 깊이를 node2의 깊이와 맞춘다.
        for (int i = this.maxDepth - 1; 0 <= i; i--) {
            if (this.depth[node1] - (1 << i) >= this.depth[node2]) {
                node1 = this.parent[node1][i];
            }
        }

        // node1과 node2의 조상이 같아질 때까지 위로 올라간다.
        if (node1 == node2) {
            return node1;
        }

        for (int i = this.maxDepth - 1; 0 <= i; i--) {
            if (this.parent[node1][i] != this.parent[node2][i]) {
                node1 = this.parent[node1][i];
                node2 = this.parent[node2][i];
            }
        }

        return this.parent[node1][0];
    }
}

class Tree {
    private int nodeCount;
    private ArrayList<Integer>[] adj;
    private Node[] nodes;

    public Tree(int nodeCount) {
        this.nodeCount = nodeCount;
        this.nodes = new Node[nodeCount + 1];
        this.adj = new ArrayList[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            nodes[i] = new Node(i);
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int a, int b) {
        adj[a].add(b);
        adj[b].add(a);
    }

    public void buildTree(int rootNum) {
        boolean[] visited = new boolean[nodeCount + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(rootNum);
        nodes[rootNum].setDepth(0);
        visited[rootNum] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : adj[curr]) {
                if (!visited[next]) {
                    nodes[next].setParent(nodes[curr]);
                    nodes[next].setDepth(nodes[curr].getDepth() + 1);
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }
    }

    public Node getNode(int number) {
        return nodes[number];
    }

    public int getNodeCount() {
        return nodeCount;
    }
}

class Node {
    private int number;
    private Node parent;
    private int depth;

    public Node(int number) {
        this.number = number;
        this.parent = null;
    }

    public int getNumber() {
        return this.number;
    }

    public Node getParent() {
        return this.parent;
    }

    public boolean isTree() {
        return this.parent != null || this.number == 1;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return this.depth;
    }
}