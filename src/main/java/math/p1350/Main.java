package math.p1350;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static math.Path.MATH_PATH;

/**
 * Solution code for "진짜 공간"
 * 
 * Problem link: https://www.acmicpc.net/problem/1350
 * 작성자: gksxorb147
 * 
 * BigO : O(N)
 */
public class Main {

    private static final String TEST_PATH = "/p1350/input/2.txt";


    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(MATH_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Long inputFileCount = Long.parseLong(br.readLine());

        FileSystem fileSystem = new FileSystem(inputFileCount);

        String[] inputFileSizes = br.readLine().split(" ");
        for (Long i = 0L; i < inputFileCount; i++) {
            Long fileSize = Long.parseLong(inputFileSizes[i.intValue()]);
            fileSystem.addFile(fileSize);
        }

        fileSystem.setClusterSize(Long.parseLong(br.readLine()));
        System.out.println(fileSystem.totalSize());

        br.close();
    }
}

class FileSystem {
    private ArrayList<Cluster> clusters;
    private Long clusterCount;
    private Long clusterSize;
    
    public FileSystem(Long clusterCount) {
        this.clusterCount = clusterCount;
        this.clusters = new ArrayList<>();
    }

    public void addFile(Long fileSize) {
        clusters.add(new Cluster(fileSize));
    }

    public void setClusterSize(Long clusterSize) {
        this.clusterSize = clusterSize;
    }

    public Long totalSize() {
        Long totalSize = 0L;
        for (Cluster cluster : clusters) {
            totalSize += (cluster.getSize() / clusterSize) * clusterSize;
            if (cluster.getSize() % clusterSize != 0) {
                totalSize += clusterSize; // 나머지 클러스터 크기만큼 추가
            }
        }
        return totalSize;
    }
}

class Cluster {
    private Long size;

    public Cluster(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }
}