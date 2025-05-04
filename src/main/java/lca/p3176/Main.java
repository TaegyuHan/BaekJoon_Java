package lca.p3176;

import static lca.Path.LCA_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Solution code for "BaekJoon 도로 네트워크".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/3176">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
public class Main {

    public static String TEST_PATH = "/p3176/input/3.txt";

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
            int[] result = binaryLifting.getLCA(node1, node2);
            sb.append(result[0]).append(" ").append(result[1]).append("\n");
        }
        System.out.print(sb.toString());
    }
}

class BinaryLifting {
    private int[][] parent;
    private int[][] minDist;
    private int[][] maxDist;
    private int[] depth;
    private int maxDepth;
    private int nodeCount;
    private Tree tree;

    public BinaryLifting(Tree tree) {
        this.tree = tree;
        this.nodeCount = tree.getNodeCount();
        this.maxDepth = 1;
        
        while ((1 << this.maxDepth) <= this.nodeCount) {
            this.maxDepth++;
        }

        parent = new int[nodeCount + 1][maxDepth];
        minDist = new int[nodeCount + 1][maxDepth];
        maxDist = new int[nodeCount + 1][maxDepth];
        depth = new int[nodeCount + 1];

        for (int i = 1; i <= nodeCount; ++i) {
            parent[i][0] = tree.getParent(i);
            minDist[i][0] = (parent[i][0] == 0) ? Integer.MAX_VALUE : tree.getDistToParent(i);
            maxDist[i][0] = (parent[i][0] == 0) ? Integer.MIN_VALUE : tree.getDistToParent(i);
            depth[i] = tree.getDepth(i);
        }

        for (int k = 1; k < maxDepth; ++k) {
            for (int i = 1; i <= nodeCount; ++i) {
                int midParent = parent[i][k - 1];
                if (midParent == 0) {
                    parent[i][k] = 0;
                    minDist[i][k] = minDist[i][k - 1];
                    maxDist[i][k] = maxDist[i][k - 1];
                } else {
                    parent[i][k] = parent[midParent][k - 1];
                    minDist[i][k] = Math.min(minDist[i][k - 1], minDist[midParent][k - 1]);
                    maxDist[i][k] = Math.max(maxDist[i][k - 1], maxDist[midParent][k - 1]);
                }
            }
        }
    }

    // 두 노드 사이의 최소/최대 도로 길이 반환
    public int[] getLCA(int u, int v) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        if (depth[u] < depth[v]) {
            int tmp = u; u = v; v = tmp;
        }

        // 깊이 맞추기
        for (int k = maxDepth - 1; 0 <= k; --k) {
            if (depth[u] - (1 << k) >= depth[v]) {
                min = Math.min(min, minDist[u][k]);
                max = Math.max(max, maxDist[u][k]);
                u = parent[u][k];
            }
        }

        if (u == v) return new int[]{min, max};

        // LCA까지 올리기
        for (int k = maxDepth - 1; 0 <= k; --k) {
            if (parent[u][k] != 0 && parent[u][k] != parent[v][k]) {
                min = Math.min(min, Math.min(minDist[u][k], minDist[v][k]));
                max = Math.max(max, Math.max(maxDist[u][k], maxDist[v][k]));
                u = parent[u][k];
                v = parent[v][k];
            }
        }

        // 마지막으로 부모까지의 거리 반영
        min = Math.min(min, Math.min(minDist[u][0], minDist[v][0]));
        max = Math.max(max, Math.max(maxDist[u][0], maxDist[v][0]));
        return new int[]{min, max};
    }
}

class Tree {
    private ArrayList<Edge>[] adj;
    private int[] depth, parent;
    private int[] distToParent;
    private int nodeCount;

    public Tree(int nodeCount) {
        this.nodeCount = nodeCount;
        adj = new ArrayList[nodeCount + 1];
        for (int i = 1; i <= nodeCount; ++i) adj[i] = new ArrayList<>();
        depth = new int[nodeCount + 1];
        parent = new int[nodeCount + 1];
        distToParent = new int[nodeCount + 1];
    }

    public void addEdge(int u, int v, int d) {
        adj[u].add(new Edge(v, d));
        adj[v].add(new Edge(u, d));
    }

    public void buildTree() {
        boolean[] visited = new boolean[nodeCount + 1];
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        visited[1] = true;
        parent[1] = 0;
        depth[1] = 0;
        distToParent[1] = 0;
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (Edge e : adj[curr]) {
                if (!visited[e.to]) {
                    visited[e.to] = true;
                    parent[e.to] = curr;
                    depth[e.to] = depth[curr] + 1;
                    distToParent[e.to] = e.dist;
                    q.add(e.to);
                }
            }
        }
    }

    public int getNodeCount() { return nodeCount; }
    public int getDepth(int node) { return depth[node]; }
    public int getParent(int node) { return parent[node]; }
    public int getDistToParent(int node) { return distToParent[node]; }
}

class Edge {
    int to, dist;
    Edge(int to, int dist) {
        this.to = to;
        this.dist = dist;
    }
}