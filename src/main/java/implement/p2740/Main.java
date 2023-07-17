package implement.p2740;

import java.io.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 행렬 곱셈".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/2740">Link</a>
 * 작성자: gksxorb159
 * BigO: O(N^3 + 2N) :
 */
class Matrix {
    private final int row;
    private final int col;
    private final int[][] metrix;
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.metrix = new int[row][col];
    }

    public Matrix(int[][] metrix) {
        this.row = metrix.length;
        this.col = metrix[0].length;
        this.metrix = metrix;
    }

    public int getMetricsData(int row, int col) {
        return  metrix[row][col];
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int rowIndex, String row) {
        String[] rowData = row.split(" ");
        for (int i = 0; i < col; i++) {
            metrix[rowIndex][i] = Integer.parseInt(rowData[i]);
        }
    }

    public Matrix multiplication(Matrix other) throws Exception {

        if (col != other.getRow()) {
            throw new Exception("배열의 길이가 다릅니다.");
        }

        int[][] newMetrics = new int[row][other.getCol()];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < other.getCol(); j++) {
                for (int k = 0; k < col; k++) {
                    newMetrics[i][j] += metrix[i][k] * other.getMetricsData(k, j);
                }
            }
        }

        return new Matrix(newMetrics);
    }

    public void showMatrix() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                System.out.print(metrix[r][c] + " ");
            }
            System.out.println();
        }
    }
}

public class Main {

    public final static String TEST_PATH = "/p2740/input/1.txt";

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] Adata = br.readLine().split(" ");
        int Arow = Integer.parseInt(Adata[0]);
        int Acol = Integer.parseInt(Adata[1]);

        Matrix A = new Matrix(Arow, Acol);

        for (int i = 0; i < Arow; i++) {
            A.setRow(i, br.readLine());
        }

        String[] Bdata = br.readLine().split(" ");
        int Brow = Integer.parseInt(Bdata[0]);
        int Bcol = Integer.parseInt(Bdata[1]);

        Matrix B = new Matrix(Brow, Bcol);

        for (int i = 0; i < Brow; i++) {
            B.setRow(i, br.readLine());
        }

        br.close();

        Matrix C = A.multiplication(B);
        C.showMatrix();
    }
}