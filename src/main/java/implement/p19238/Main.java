package implement.p19238;

import static implement.Path.IMPLEMENT_PATH;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.List;
import java.util.LinkedList;

/**
 * Solution code for "BaekJoon 스타트 택시".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/19238">Link</a>
 * 작성자: 한태규
 */
public class Main {

    private static final String TEST_PATH = "/p19238/input/9.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int groundSize = Integer.parseInt(st.nextToken());
        int humanCount = Integer.parseInt(st.nextToken());
        int fuel = Integer.parseInt(st.nextToken());

        // 시작 도시 설정
        StartCity startCity = new StartCity(groundSize, humanCount);

        // 지도 설정
        for (int row = 0; row < groundSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < groundSize; ++col) {
                int groundType = Integer.parseInt(st.nextToken());
                startCity.setGround(row, col, groundType);
            }
        }

        // 텍시 설정
        st = new StringTokenizer(br.readLine());
        int taxiX = Integer.parseInt(st.nextToken()) - 1;
        int taxiY = Integer.parseInt(st.nextToken()) - 1;
        startCity.setTaxi(taxiX, taxiY, fuel);

        // 사람 설정
        for (int number = 1; number <= humanCount; ++number) {
            st = new StringTokenizer(br.readLine());
            int humanX = Integer.parseInt(st.nextToken()) - 1;
            int humanY = Integer.parseInt(st.nextToken()) - 1;
            int destinationX = Integer.parseInt(st.nextToken()) - 1;
            int destinationY = Integer.parseInt(st.nextToken()) - 1;
            startCity.setHuman(number, humanX, humanY, destinationX, destinationY);
        }

        // startCity.showGround();
        startCity.runTaxi();
    }
}


enum Direction {
    UP(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN(0, 1);

    private int x;
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


class NextMoveDTO {
    private int x;
    private int y;
    private int distance;

    public NextMoveDTO(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public static NextMoveDTO of(int x, int y, int distance) {
        return new NextMoveDTO(x, y, distance);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }
}


class StartCity {
    private int groundSize;
    private Ground[][] grounds;
    private boolean[][] visited;
    private int humanCount;
    private Taxi taxi;

    public StartCity(int groundSize, int humanCount) {
        this.groundSize = groundSize;
        this.humanCount = humanCount;
        this.grounds = new Ground[groundSize][groundSize];
        this.visited = new boolean[groundSize][groundSize];
        this.taxi = new Taxi(0, 0, 0);
    }

    public void setGround(int row, int col, int groundType) {
        this.grounds[row][col] = new Ground(row, col, groundType);
    }

    public void setTaxi(int x, int y, int fuel) {
        this.taxi.move(x, y);
        this.taxi.addFuel(fuel);
    }

    public void setHuman(int number, int humanX, int humanY, int destinationX, int destinationY) {
        this.grounds[humanX][humanY].setHuman(number, humanX, humanY, destinationX, destinationY);
    }

    private void initVisited() {
        for (int row = 0; row < groundSize; ++row) {
            for (int col = 0; col < groundSize; ++col) {
                visited[row][col] = false;
            }
        }
    }

    public void showGround() {

        // 연료 표시
        System.out.println("연료: " + taxi.getFuel());
        // 택시 위치
        System.out.println("택시 위치: " + taxi.getX() + ", " + taxi.getY());

        String[][] ground = new String[groundSize][groundSize];

        for (int row = 0; row < groundSize; ++row) {
            for (int col = 0; col < groundSize; ++col) {
                ground[row][col] = grounds[row][col].isWall() ? "X" : "+";
            }
        }

        // 택시 위치
        ground[taxi.getX()][taxi.getY()] = "T";
        
        if (taxi.isHuman()) { // 사람 목적지 표시
            // 사람 위치
            ground[taxi.getHuman().getDestinationX()][taxi.getHuman().getDestinationY()] = String.valueOf(-taxi.getHuman().getNumber());
        }

        for (int row = 0; row < groundSize; ++row) {
            for (int col = 0; col < groundSize; ++col) {
                if (grounds[row][col].isHuman()) {
                    // 사람 위치
                    ground[row][col] = String.valueOf(grounds[row][col].getHumanNumber());

                    // 목적지 위치
                    Human human = grounds[row][col].getHuman();
                    int humanNumber = human.getNumber();
                    ground[human.getDestinationX()][human.getDestinationY()] = String.valueOf(-humanNumber);
                }
            }
        }

        for (int row = 0; row < groundSize; ++row) {
            for (int col = 0; col < groundSize; ++col) {
                System.out.print(ground[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void runTaxi() {

        // 사람 운반
        for (int i = 1; i <= humanCount; ++i) {
            // 사람 찾기
            int findHumanResult = findClosestHuman();

            if (findHumanResult == -1) { // 사람 못찾으면 종료
                System.out.println(-1);
                return;
            }

            if (taxi.fuelEmpty()) { // 연료 확인
                System.out.println(-1);
                return;
            }

            // showGround();

            // 목적지 찾기
            int moveDistance = findClosestDestination(taxi);

            if (moveDistance == -1) { // 목적지 못찾으면 종료
                System.out.println(-1);
                return;
            }

            if (taxi.fuelEmpty()) { // 연료 확인
                System.out.println(-1);
                return;
            }

            taxi.addFuel(moveDistance * 2);

            // showGround();
        }

        System.out.println(taxi.getFuel());
    }

    // 가장 가까운 사람 찾기
    private int findClosestHuman() {

        // 방문 초기화
        initVisited();

        // 택시 위치 추가
        Queue<NextMoveDTO> nextMoves = new ArrayDeque<>();
        nextMoves.add(NextMoveDTO.of(taxi.getX(), taxi.getY(), 0));
        List<Human> humans = new LinkedList<>();
        int minDistance = -1;

        while (!nextMoves.isEmpty()) {
            NextMoveDTO nextMove = nextMoves.poll();
            int x = nextMove.getX();
            int y = nextMove.getY();
            int distance = nextMove.getDistance();

            if (minDistance != -1 && distance > minDistance) { 
                continue; // 최소 거리보다 크면 패스
             }

            if (visited[x][y]) { continue; } // 방문한 곳이면 패스
            visited[x][y] = true;

            if (grounds[x][y].isHuman()) { // 사람 찾으면 반환
                Human human = grounds[x][y].getHuman();
                human.setDistance(distance);
                humans.add(human);
                minDistance = distance;
                continue;
            }

            // 주변 탐색
            for (Direction direction : Direction.values()) {
                int nx = x + direction.getX();
                int ny = y + direction.getY();

                if (!(0 <= nx && nx < groundSize && 0 <= ny && ny < groundSize)) { continue; }
                if (visited[nx][ny]) { continue; }
                if (grounds[nx][ny].isWall()) { continue; }

                nextMoves.add(NextMoveDTO.of(nx, ny, distance + 1));
            }
        }

        if (humans.isEmpty()) { return -1; }

        // 가장 가까운 사람 찾기
        humans.sort(null);
        Human human = humans.get(0);

        // 사람 탑승
        taxi.move(human.getX(), human.getY());
        taxi.useFuel(human.getDistance());
        taxi.setHuman(grounds[human.getX()][human.getY()].goToTaxi());
        return human.getDistance();
    }

    // 가장 가까운 목적지 찾기
    private int findClosestDestination(Taxi taxi) {

        // 방문 초기화
        initVisited();

        // 택시 위치 추가
        Queue<NextMoveDTO> nextMoves = new ArrayDeque<>();
        nextMoves.add(NextMoveDTO.of(taxi.getX(), taxi.getY(), 0));
        int destinationX = taxi.getHuman().getDestinationX();
        int destinationY = taxi.getHuman().getDestinationY();

        while (!nextMoves.isEmpty()) {
            NextMoveDTO nextMove = nextMoves.poll();
            int x = nextMove.getX();
            int y = nextMove.getY();
            int distance = nextMove.getDistance();

            if (visited[x][y]) { continue; } // 방문한 곳이면 패스
            visited[x][y] = true;

            // 목적지 찾으면 반환
            if (x == destinationX && y == destinationY) {
                taxi.move(x, y);
                taxi.useFuel(distance);
                taxi.setHuman(null);
                return distance;
            }

            // 주변 탐색
            for (Direction direction : Direction.values()) {
                int nx = x + direction.getX();
                int ny = y + direction.getY();

                if (!(0 <= nx && nx < groundSize && 0 <= ny && ny < groundSize)) { continue; }
                if (visited[nx][ny]) { continue; }
                if (grounds[nx][ny].isWall()) { continue; }

                nextMoves.add(NextMoveDTO.of(nx, ny, distance + 1));
            }
        }

        return -1;
    }
}

// 택시
class Taxi {
    private int x;
    private int y;
    private int fuel;
    private Human human;

    public Taxi(int x, int y, int fuel) {
        this.x = x;
        this.y = y;
        this.fuel = fuel;
        this.human = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Human getHuman() {
        return human;
    }

    public boolean fuelEmpty() {
        return fuel < 0;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void useFuel(int distance) {
        this.fuel -= distance;
    }

    public void addFuel(int fuel) {
        this.fuel += fuel;
    }   

    public int getFuel() {
        return fuel;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public boolean isHuman() {
        return human != null;
    }
}

// 사람
class Human implements Comparable<Human> {
    private int number;
    private int destinationX;
    private int destinationY;
    private int distance;
    private int x;
    private int y;

    public Human(int number, int x, int y, int destinationX, int destinationY) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.distance = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getNumber() {
        return number;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    @Override
    public int compareTo(Human o) {
        if (distance != o.distance) {
            return Integer.compare(distance, o.distance);
        }

        if (x != o.x) {
            return Integer.compare(x, o.x);
        }

        return Integer.compare(y, o.y);
    }
}

// 땅
class Ground {
    private int x;
    private int y;
    private boolean isWall;
    private Human human;

    public Ground(int x, int y, int groundType) {
        this.x = x;
        this.y = y;
        this.isWall = groundType == 1;
    }

    public void setHuman(int number, int x, int y, int destinationX, int destinationY) {
        this.human = new Human(number, x, y, destinationX, destinationY);
    }

    public boolean isHuman() {
        return human != null;
    }

    public int getHumanNumber() {
        return human.getNumber();
    }

    public boolean isWall() {
        return isWall;
    }

    public Human getHuman() {
        return human;
    }

    // 택시 탑승
    public Human goToTaxi() {
        Human human = this.human;
        this.human = null;
        return human;
    }
}