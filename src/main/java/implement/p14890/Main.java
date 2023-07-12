package implement.p14890;

import java.io.*;
import java.util.Arrays;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon 경사로".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/14890">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */
abstract class Land {

    private final int height;

    private final int type;

    Land(int height, int type) {
        this.height = height;
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }
}

class FlatLand extends Land {

    public static final int TYPE = 0;

    FlatLand(int height) {
        super(height, 0);
    }

    @Override
    public String toString() {
        return String.valueOf(getHeight());
    }
}

class SlopeLand extends Land {

    public static final int TYPE = 1;

    SlopeLand(int height) {
        super(height, 1);
    }

    @Override
    public String toString() {
        return String.valueOf(getHeight());
    }
}

class Map {

    private final int size;
    private final int slopeSize;
    private Land[][] land;

    Map(int size, int slopeSize) {
        this.size = size;
        this.slopeSize = slopeSize;
        this.land = new Land[size][size];
    }

    public void setMap(int index, String[] rowData) {
        Land[] rowTmp = new Land[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            int height = Integer.parseInt(rowData[i]);
            rowTmp[i] = new FlatLand(height);
        }
        land[index] = rowTmp;
    }

    private boolean check(int index, boolean isRow) {
        Land[] tmp = new Land[size];

        int currentIndex = 0;
        while (currentIndex < size - 1) {
            Land cLand = land[isRow ? index : currentIndex][isRow ? currentIndex : index];
            Land nLand = land[isRow ? index : currentIndex + 1][isRow ? currentIndex + 1 : index];

            int cHeight = cLand.getHeight();
            int nHeight = nLand.getHeight();

            if (cHeight == nHeight) {
                try {
                    if (tmp[currentIndex].getType() != SlopeLand.TYPE) {
                        tmp[currentIndex] = cLand;
                    }
                } catch (NullPointerException e) {
                    tmp[currentIndex] = cLand;
                }
                currentIndex++;
                continue;
            }

            if (1 < Math.abs(cHeight - nHeight)) {
                return false;
            }

            if (nHeight < cHeight) {
                tmp[currentIndex] = cLand;
                for (int n = 1; n <= slopeSize; n++) {
                    int checkIndex = currentIndex + n;

                    if (!(0 < checkIndex && checkIndex < size)) {
                        return false;
                    }

                    if (land[isRow ? index : checkIndex][isRow ? checkIndex : index].getHeight() != nHeight) {
                        return false;
                    }

                    tmp[checkIndex] = new SlopeLand(nHeight);
                }
                currentIndex += slopeSize;
            }

            if (cHeight < nHeight) {
                for (int n = 0; n < slopeSize; n++) {
                    int checkIndex = currentIndex - n;

                    if (!(0 <= checkIndex && checkIndex < size)) {
                        return false;
                    }

                    if (land[isRow ? index : checkIndex][isRow ? checkIndex : index].getHeight() != cHeight) {
                        return false;
                    }

                    try {
                        if (tmp[checkIndex].getType() == SlopeLand.TYPE) {
                            return false;
                        }
                    } catch (NullPointerException e) {
                        // pass
                    }

                    tmp[checkIndex] = new SlopeLand(cHeight);
                }
                currentIndex++;
            }
        }

        return true;
    }

    public int checkRoad() {
        int answer = 0;

        for (int i = 0; i < size; i++) {
            if (check(i, true)) {
                answer++;
            }

            if (check(i, false)) {
                answer++;
            }
        }

        return answer;
    }

    public void showMap() {
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(land[i]));
        }
        System.out.println();
    }
}

public class Main {

    private final static String TEST_PATH = "/p14890/input/19.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] NL = br.readLine().split(" ");
        int size = Integer.parseInt(NL[0]);
        int slopeSize = Integer.parseInt(NL[1]);
        Map map = new Map(size, slopeSize);

        for (int i = 0; i < size; i++) {
            map.setMap(i, br.readLine().split(" "));
        }
        br.close();
//        map.showMap();
        System.out.print(map.checkRoad());

    }
}