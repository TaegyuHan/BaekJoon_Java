package lca.p1761;

import static lca.Path.LCA_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Solution code for "BaekJoon 정점들의 거리".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/1761">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {
    
    public static String TEST_PATH = "/p1761/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(LCA_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int nodeCount = Integer.parseInt(br.readLine());
        Tree tree = new Tree(nodeCount);
        for (int i = 0; i < nodeCount - 1; ++i) {
            String[] input = br.readLine().split(" ");
            int node1 = Integer.parseInt(input[0]);
            int node2 = Integer.parseInt(input[1]);
            int distance = Integer.parseInt(input[2]);

            if (!tree.hasRoot()) {
                tree.setRoot(node1);
            }

            tree.addEdge(node1, node2, distance);
        }
        tree.buildTree();

        BinaryLifting binaryLifting = new BinaryLifting(tree);

        StringBuilder sb = new StringBuilder();
        int queryCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < queryCount; ++i) {
            String[] input = br.readLine().split(" ");
            int node1 = Integer.parseInt(input[0]);
            int node2 = Integer.parseInt(input[1]);
            int result = binaryLifting.getLCA(node1, node2);
            sb.append(result).append("\n");
        }

        // binaryLifting.showParent();
        // tree.showAdj();
        // System.out.println(tree.getRoot().getNumber());
        System.out.print(sb.toString());
    }
}

class ParentInfoDTO {
    private final int parentNumber;
    private final int distance;

    public ParentInfoDTO(int parentNumber, int distance) {
        this.parentNumber = parentNumber;
        this.distance = distance;
    }

    public int getParentNumber() {
        return this.parentNumber;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return "ParentInfoDTO{" +
                "parentNumber=" + parentNumber +
                ", distance=" + distance +
                '}';
    }   
}

class BinaryLifting {
    private final ParentInfoDTO[][] parent;
    private final int[] depth;
    private int maxDepth;
    private final int nodeCount;

    public BinaryLifting(Tree tree) {
        this.nodeCount = tree.getNodeCount();
        this.maxDepth = 1;

        while ((1 << this.maxDepth) <= this.nodeCount) {
            this.maxDepth++; // 최대 깊이 계산
        }

        this.parent = new ParentInfoDTO[this.nodeCount + 1][this.maxDepth];
        this.depth = new int[this.nodeCount + 1];

        // 1. 깊이와 1번째 부모 노드 채우기
        for (int i = 1; i <= this.nodeCount; i++) {
            Node node = tree.getNode(i);
            // System.out.println(node.getNumber() + " : " + node.getParent() + " " + node.getDistance());
            depth[i] = node.getDepth();
            parent[i][0] = node.getParent() != null ? new ParentInfoDTO(node.getParent().getNumber(), node.getDistance()) : null;
        }

        // 2. 최소 배열 채우기
        for (int depth = 1; depth < this.maxDepth; ++depth) {
            for (int node = 1; node <= this.nodeCount; ++node) {

                ParentInfoDTO prevParentInfo = parent[node][depth - 1];
                if (prevParentInfo == null) { 
                    continue;
                 }

                ParentInfoDTO prevParentInfo2 = parent[prevParentInfo.getParentNumber()][depth - 1];
                if (prevParentInfo2 == null) { 
                    continue;
                 }
                // 2^k번째 부모 = 2^(k-1)번째 부모의 2^(k-1)번째 부모
                // 거리 = 두 구간의 거리 합
                parent[node][depth] = new ParentInfoDTO(
                    prevParentInfo2.getParentNumber(), 
                    prevParentInfo.getDistance() + prevParentInfo2.getDistance()
                );
            }
        }
    }

    public int getLCA(int node1, int node2) {
        int resultDistance = 0;

        // node1이 더 깊은 노드가 되도록 한다.
        if (depth[node1] < depth[node2]) {
            int temp = node1;
            node1 = node2;
            node2 = temp;
        }

        // node1의 깊이를 node2의 깊이와 맞춘다.
        for (int i = maxDepth - 1; i >= 0; i--) {
            if (parent[node1][i] != null && depth[node1] - (1 << i) >= depth[node2]) {
                resultDistance += parent[node1][i].getDistance();
                node1 = parent[node1][i].getParentNumber();
            }
        }

        // 두개의 노드가 같은 노드인 경우
        if (node1 == node2) {
            return resultDistance;
        }

        // LCA를 찾으면서 동시에 위로 올림
        for (int i = maxDepth - 1; 0 <= i; i--) {
            if (parent[node1][i] != null && parent[node2][i] != null &&
                parent[node1][i].getParentNumber() != parent[node2][i].getParentNumber()) {
                resultDistance += parent[node1][i].getDistance();
                resultDistance += parent[node2][i].getDistance();
                node1 = parent[node1][i].getParentNumber();
                node2 = parent[node2][i].getParentNumber();
            }
        }

        return resultDistance + parent[node1][0].getDistance() + parent[node2][0].getDistance();
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
}

class Tree {
    private final int nodeCount;
    private final ArrayList<Node>[] adj;
    private final Node[] nodes;
    private Node root;

    public Tree(int nodeCount) {
        this.nodeCount = nodeCount;
        this.adj = new ArrayList[nodeCount + 1];
        this.nodes = new Node[nodeCount + 1];
        for (int i = 1; i <= nodeCount; ++i) {
            nodes[i] = new Node(i, 0);
            adj[i] = new ArrayList<Node>();
        }
    }

    public void buildTree() {
        boolean[] visited = new boolean[nodeCount + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(root.getNumber());
        visited[root.getNumber()] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (Node next : adj[curr]) {
                if (!visited[next.getNumber()]) {
                    nodes[next.getNumber()].setParent(nodes[curr]);
                    nodes[next.getNumber()].setDepth(nodes[curr].getDepth() + 1);
                    nodes[next.getNumber()].setDistance(next.getDistance());
                    visited[next.getNumber()] = true;
                    queue.add(next.getNumber());
                }
            }
        }
    }

    public Node getNode(int number) {
        return this.nodes[number];
    }

    public int getNodeCount() {
        return this.nodeCount;
    }

    public void setRoot(int node) {
        this.root = new Node(node, 0);
        this.nodes[node] = this.root;
    }

    public Node getRoot() {
        return this.root;
    }

    public boolean hasRoot() {
        return this.root != null;
    }   

    public void addEdge(int node1, int node2, int distance) {
        adj[node1].add(new Node(node2, distance));
        adj[node2].add(new Node(node1, distance));
    }

    public void showAdj() {
        for (int i = 1; i <= nodeCount; ++i) {
            System.out.println(i + " : " + Arrays.toString(adj[i].toArray()));
        }
    }
}

class Node {
    private final int number;
    private Node parent;
    private int depth;
    private int distance;

    public Node(int number, int distance) {
        this.number = number;
        this.distance = distance;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getParent() {
        return this.parent;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getNumber() {
        return this.number;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return String.valueOf(this.number);
    }
}