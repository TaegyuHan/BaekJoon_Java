package tree.p20364;

import java.io.*;
import java.util.Arrays;

import static tree.Path.TREE_PATH;

/**
 * Solution code for "BaekJoon 부동산 다툼".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/20364">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */

class Duck {

    private final int number;

    public Duck(int number) {
        this.number = number;
    }

    public boolean checkNumber(int index) {
        return this.number == index;
    }

    public int getNumber() {
        return this.number;
    }
}

class House {
    private final int index;
    private boolean visited;

    public House(int index) {
        this.index = index;
        this.visited = false;
    }

    @Override
    public String toString() {
        return "" + index;
    }

    public int getIndex() {
        return this.index;
    }

    public void visited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }
}

class Tree {
    private final static int ROOT_INDEX = 1;
    private final static int NO_CONNECTION = -1;
    private final static int CONNECTION = 0;

    private final int size;

    private final int height;
    private final int arraySize;
    private final House[] tree;

    public Tree(int size) {
        this.size = size;
        this.height = (int) Math.ceil(Math.log(Math.pow(2, 20)));
        this.arraySize = (int) Math.pow(2, this.height + 1);
        this.tree = inintTree();
    }

    private House[] inintTree() {
        House[] tree = new House[this.arraySize];
        for (int i = 0; i < this.size + 1; i++) {
            tree[i] = new House(i);
        }
        return tree;
    }

    private int findHouse(int index, Duck duck) {

        if (size < index || duck.getNumber() < index) { return NO_CONNECTION; }

        if (duck.checkNumber(index)) {
            this.tree[index].visited();
            return CONNECTION;
        }

        int left = index * 2;
        int right = (index * 2) + 1;

        int leftTmp = findHouse(left, duck);
        if (leftTmp != NO_CONNECTION) {
            if (this.tree[index].isVisited()) {
                return index;
            }
            return leftTmp;
        }

        int rightTmp = findHouse(right, duck);
        if (rightTmp != NO_CONNECTION) {
            if (this.tree[index].isVisited()) {
                return index;
            }
            return rightTmp;
        }

        return NO_CONNECTION;
    }

    public void goDuck(Duck duck) {
        int result = findHouse(ROOT_INDEX, duck);
        System.out.println(result);
    }

    @Override
    public String toString() {
        return "size=" + size + "\n" +
                "height=" + height + "\n" +
                "arraySize=" + arraySize + "\n" +
                "tree=" + Arrays.toString(tree) + "\n";
    }
}

public class Main {

    private final static String TEST_PATH = "/p20364/input/1.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] data = br.readLine().split(" ");

        int size = Integer.parseInt(data[0]);
        int duckCount = Integer.parseInt(data[1]);

        Tree tree = new Tree(size);
//        System.out.println(tree);
        for (int i = 0; i < duckCount; i++) {
            int number = Integer.parseInt(br.readLine());
            Duck duck = new Duck(number);
            tree.goDuck(duck);
        }

        br.close();
    }
}