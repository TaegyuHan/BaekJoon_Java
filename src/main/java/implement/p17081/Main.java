package implement.p17081;

import java.io.*;
import java.util.*;

import static implement.Path.IMPLEMENT_PATH;

/**
 * Solution code for "BaekJoon RPG Extreme".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/p17081">Link</a>
 * 작성자: gksxorb147
 * BigO: O() :
 */

enum Move {
    U(new int[]{-1, 0}),
    D(new int[]{1, 0}),
    L(new int[]{0, -1}),
    R(new int[]{0, 1});

    private final int[] value;

    Move(int[] value) {
        this.value = value;
    }

    public int[] getValue() {
        return this.value;
    }

    public static Move fromString(String moveString) {
        switch (moveString) {
            case "U":
                return Move.U;
            case "D":
                return Move.D;
            case "L":
                return Move.L;
            case "R":
                return Move.R;
            default:
                throw new IllegalArgumentException("Invalid move: " + moveString);
        }
    }

    @Override
    public String toString() {
        return name();
    }
}

enum OrnamentEnum {
    HR,
    RE,
    CO,
    EX,
    DX,
    HU,
    CU;

    public static OrnamentEnum fromString(String ornamentString) {
        switch (ornamentString) {
            case "HR":
                return HR;
            case "RE":
                return RE;
            case "CO":
                return CO;
            case "EX":
                return EX;
            case "DX":
                return DX;
            case "HU":
                return HU;
            case "CU":
                return CU;
            default:
                throw new IllegalArgumentException("Invalid ornamentEnum: " + ornamentString);
        }
    }

    @Override
    public String toString() {
        return name();
    }
}

enum Item {
    W,  // 무기
    A,  // 방어구
    O;  // 악세사리

    public static Item fromString(String itemString) {
        if (itemString.equalsIgnoreCase("W")) {
            return W;
        } else if (itemString.equalsIgnoreCase("A")) {
            return A;
        } else if (itemString.equalsIgnoreCase("O")) {
            return O;
        }
        throw new IllegalArgumentException("Invalid item: " + itemString);
    }

    @Override
    public String toString() {
        return name();
    }
}

class Equipment {
    private final Item itemType;

    public Equipment(Item itemType) {
        this.itemType = itemType;
    }

    public Item getItemType() {
        return itemType;
    }
}

class Weapon extends Equipment {

    private final int attack;

    public Weapon(String attack) {
        super(Item.W);
        this.attack = Integer.parseInt(attack);
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return String.valueOf(attack);
    }
}

class Armor extends Equipment {

    private final int defense;

    public Armor(String defense) {
        super(Item.A);
        this.defense = Integer.parseInt(defense);
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return String.valueOf(defense);
    }
}

class Ornament extends Equipment {

    private final OrnamentEnum ornamentType;

    public Ornament(String ornamentType) {
        super(Item.O);
        this.ornamentType = OrnamentEnum.fromString(ornamentType);
    }

    public OrnamentEnum getOrnamentType() {
        return ornamentType;
    }

    @Override
    public String toString() {
        return ornamentType.toString();
    }
}

class Space {
    private int row;

    private int col;

    private final String type;

    public Space(int row, int col, String type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}

class MainCharacter extends Space {
    public static final String TYPE = "@";
    public static final int START_LEVEL = 1;
    public static final int START_HP = 20;
    public static final int START_ATT = 2;
    public static final int START_DEF = 2;
    private final int startRow;
    private final int startCol;
    private boolean survival;
    private int level;
    private int currentExp;
    private int levelUpExp;
    private int currentHp;
    private int maxHp;
    private int defaultAtt;
    private int plusAtt;
    private int defaultDef;
    private int plusDef;
    private int attackCombo;
    private int hitCombo;
    private final Queue<Move> moveDatas;
    private Weapon weapon;
    private Armor armor;
    private final Set<OrnamentEnum> ornaments;
    private String deathBy;

    public MainCharacter(int row, int col) {
        super(row, col, TYPE);
        this.startRow = row;
        this.startCol = col;

        this.survival = true;

        this.level = START_LEVEL;

        this.currentHp = START_HP;
        this.maxHp = START_HP;

        this.defaultAtt = START_ATT;
        this.defaultDef = START_DEF;

        this.attackCombo = 0;
        this.hitCombo = 0;

        this.currentExp = 0;
        this.levelUpExp = level * 5;

        this.moveDatas = new LinkedList<>();

        this.weapon = null;
        this.armor = null;
        this.ornaments = new HashSet<>(4);

        this.deathBy = null;
    }
    public boolean ornamentCheck(OrnamentEnum ornamentEnum) {
        return ornaments.contains(ornamentEnum);
    }

    public void resurrection() {
        this.survival = true;
        this.currentHp = this.maxHp;
        setRow(this.startRow);
        setCol(this.startCol);
        ornaments.remove(OrnamentEnum.RE);
    }

    public void fullHp() {
        this.currentHp = this.maxHp;
    }

    public void healHp(int point) {
        this.currentHp += point;
        if (this.maxHp < this.currentHp) {
            this.currentHp = this.maxHp;
        }
    }

    public void experienceGained(int getExp) {

        if (ornamentCheck(OrnamentEnum.EX)) {
            this.currentExp += (int) (getExp * 1.2);
        } else {
            this.currentExp += getExp;
        }

        if (this.currentExp < this.levelUpExp) { // 레벨업 안함
            return;
        }
        levelUp(); // 레벨업
    }

    private void levelUp() {
        this.currentExp = 0;
        this.level++;
        this.maxHp += 5;
        this.currentHp = this.maxHp;
        this.defaultDef += 2;
        this.defaultAtt += 2;

        this.levelUpExp = level * 5;
    }

    @Override
    public String toString() {
        return TYPE;
    }

    public void movePosition(int nRow, int nCol) {
        setRow(nRow);
        setCol(nCol);
    }

    public void showInfo() {
        System.out.println("Character position : (" + getRow() + ", " + getCol() + ")");

        System.out.println("=====================");

        System.out.println("LV : " + level);
        System.out.println("HP : " + currentHp + "/" + maxHp);
        System.out.println("ATT : " + defaultAtt + "+" + plusAtt);
        System.out.println("DEF : " + defaultDef + "+" + plusDef);
        System.out.println("EXP : " + currentExp + "/" + levelUpExp);

        System.out.println("=====================");

        System.out.println("W : " + weapon);
        System.out.println("A : " + armor);
        System.out.println("O : " + Arrays.toString(ornaments.toArray()));

        System.out.println("next move : " + moveDatas.peek());
        System.out.println(Arrays.toString(moveDatas.toArray()));
    }

    public void answer() {
        System.out.println("LV : " + level);
        System.out.println("HP : " + currentHp + "/" + maxHp);
        System.out.println("ATT : " + defaultAtt + "+" + plusAtt);
        System.out.println("DEF : " + defaultDef + "+" + plusDef);
        System.out.println("EXP : " + currentExp + "/" + levelUpExp);
    }

    public void setMoveData(String moveData) {
        for (int i = 0; i < moveData.length(); i++) {
            Move move = Move.fromString(Character.toString(moveData.charAt(i)));
            moveDatas.add(move);
        }
    }

    private int totaldefensive() {
        return this.defaultDef + this.plusDef;
    }

    public void underAttack(String type, int attack) {
        hitCombo++;
        if (hitCombo == 1
                && type.equals(BossMonster.TYPE)
                && ornamentCheck(OrnamentEnum.HU)) {
            return;
        } else {
            this.currentHp -= Math.max(1, attack - totaldefensive());
        }
        survivalCheck();
    }

    public boolean moveCheck() {
        return moveDatas.size() != 0; // 남은 움직임 확인
    }

    public int[] getNextPosition() {
        Move move = moveDatas.remove();
        int[] tmp = move.getValue();
        return new int[]{getRow() + tmp[0], getCol() + tmp[1]};
    }

    private void survivalCheck() {
        if (!hpCheck()) {
            survival = false;
            currentHp = 0;
        }
    }

    private boolean hpCheck() {
        return 0 < this.currentHp;
    }

    public void trapDamage() {
        if (ornaments.contains(OrnamentEnum.DX)) {
            this.currentHp -= Trap.DX_ATTACK;
        } else {
            this.currentHp -= Trap.ATTACK;
        }
        survivalCheck();
    }

    public void acquireItemBox(ItemBox nextSpace) {
        Equipment equipment = nextSpace.getEquipment();
        switch (equipment.getItemType()) {
            case W:
                setWeapon((Weapon) equipment);
//                System.out.println("Item Type : " + equipment.getItemType() + " | point : " + ((Weapon) equipment).getAttack());
                break;
            case A:
                setArmor((Armor) equipment);
//                System.out.println("Item Type : " + equipment.getItemType() + " | point : " + ((Armor) equipment).getDefense());
                break;
            case O:
                setOrnament((Ornament) equipment);
//                System.out.println("Item Type : " + equipment.getItemType() + " | type : " + ((Ornament) equipment).getOrnamentType());
                break;
        }
    }

    private void setOrnament(Ornament equipment) {
        OrnamentEnum type = equipment.getOrnamentType();
        if (4 <= ornaments.size() // 4개 미만 까지 장착 가능
            || ornaments.contains(type)) { // 이미 장착 중이면 장착 불가
            return;
        }
        ornaments.add(type); // 장착
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.plusAtt = this.weapon.getAttack();
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
        this.plusDef = this.armor.getDefense();
    }

    public boolean isSurvival() {
        return survival;
    }

    private int totalAttack() {
        return this.defaultAtt + this.plusAtt;
    }

    public int attack() {
        attackCombo ++;
        if (attackCombo == 1 // 처음 공격
                && ornaments.contains(OrnamentEnum.CO)) { // CO 장신구 착용

            if (ornaments.contains(OrnamentEnum.DX)) { // DX 와 함께 착용
                return totalAttack() * 3;
            }

            return totalAttack() * 2;
            }
        return totalAttack();
    }

    public void attackComboInit() {
        this.attackCombo = 0;
    }

    public void hitComboInit() {
        this.hitCombo = 0;
    }

    public void setDeathBy(String deathBy) {
        this.deathBy = deathBy;
    }

    public String getDeathBy() {
        return deathBy;
    }
}

class Monster extends Space {

    public static final String TYPE = "&";

    private String name;

    private int attack;

    private int defense;

    private int maxHp;
    private int currentHp;

    private int exp;

    private boolean survival;

    public Monster(int row, int col, String type) {
        super(row, col, type);
    }

    public Monster(int row, int col) {
        super(row, col, TYPE);
    }

    public void setMonsterInfo(String[] data) {
        this.survival = true;
        this.name = data[2];
        this.attack = Integer.parseInt(data[3]);
        this.defense = Integer.parseInt(data[4]);
        this.maxHp = Integer.parseInt(data[5]);
        this.currentHp = this.maxHp;
        this.exp = Integer.parseInt(data[6]);
    }

    @Override
    public String toString() {
        return TYPE;
    }

    public boolean isSurvival() {
        return survival;
    }

    public void underAttack(int attack) {
        this.currentHp -= Math.max(1, attack - this.defense);
        survivalCheck();
    }

    public void attackCancel() {
        this.currentHp = this.maxHp;
    }

    public int attack() {
        return this.attack;
    }

    private void survivalCheck() {
        if (!hpCheck()) {
            survival = false;
            currentHp = 0;
        }
    }

    private boolean hpCheck() {
        return 0 < this.currentHp;
    }

    public int getExp() {
        return exp;
    }

    public void showInfo() {
        System.out.println("========== Monster ==========");
        System.out.println("name : " + this.name);
        System.out.println("attack : " + this.attack);
        System.out.println("defense : " + this.defense);
        System.out.println("hp : " + this.currentHp + "/" + this.maxHp);
        System.out.println("exp : " + this.exp);
    }

    public String getName() {
        return this.name;
    }
}

class BossMonster extends Monster {
    public static final String TYPE = "M";

    public BossMonster(int row, int col) {
        super(row, col, TYPE);
    }

    @Override
    public String toString() {
        return TYPE;
    }
}

class Empty extends Space {
    public static final String TYPE = ".";

    public Empty(int row, int col) {
        super(row, col, TYPE);
    }

    @Override
    public String toString() {
        return TYPE;
    }
}

class Wall extends Space {
    public static final String TYPE = "#";

    public Wall(int row, int col) {
        super(row, col, TYPE);
    }

    @Override
    public String toString() {
        return TYPE;
    }
}

class ItemBox extends Space {

    public static final String TYPE = "B";

    private Equipment equipment;


    public ItemBox(int row, int col) {
        super(row, col, TYPE);
        this.equipment = null;
    }

    public void setItemBoxInfo(String[] data) {
        Item itemTupe = Item.fromString(data[2]);
        String itemData = data[3];

        switch (itemTupe) {
            case W:
                equipment = new Weapon(itemData);
                break;
            case A:
                equipment = new Armor(itemData);
                break;
            case O:
                equipment = new Ornament(itemData);
                break;
        }
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    @Override
    public String toString() {
        return TYPE;
    }
}

class Trap extends Space {

    public static final String TYPE = "^";
    public static final int ATTACK = 5;
    public static final int DX_ATTACK = 1;

    public Trap(int row, int col) {
        super(row, col, TYPE);
    }

    @Override
    public String toString() {
        return TYPE;
    }
}

class Map {

    private final int rowSize;
    private final int colSize;

    Space[][] map;

    private MainCharacter mainCharacter;
    private BossMonster bossMonster;
    private int gameTurn;
    private int monsterCount;

    private int itemBoxCount;

    private String endMessage;

    Map(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.mainCharacter = null;
        this.bossMonster = null;
        this.monsterCount = 0;
        this.itemBoxCount = 0;
        this.gameTurn = 0;
        this.endMessage = null;
        this.map = new Space[rowSize][colSize];
    }

    public void setMap(int rowIndex, String rowData) {
        for (int col = 0; col < rowData.length(); col++) {
            String type = Character.toString(rowData.charAt(col));
            Space space = null;
            switch (type) {
                case MainCharacter.TYPE:
                    space = new Empty(rowIndex, col);
                    mainCharacter = new MainCharacter(rowIndex, col);
                    break;
                case Monster.TYPE:
                    space = new Monster(rowIndex, col);
                    monsterCount++;
                    break;
                case BossMonster.TYPE:
                    space = new BossMonster(rowIndex, col);
                    bossMonster = (BossMonster) space;
                    monsterCount++;
                    break;
                case Empty.TYPE:
                    space = new Empty(rowIndex, col);
                    break;
                case Wall.TYPE:
                    space = new Wall(rowIndex, col);
                    break;
                case ItemBox.TYPE:
                    space = new ItemBox(rowIndex, col);
                    itemBoxCount++;
                    break;
                case Trap.TYPE:
                    space = new Trap(rowIndex, col);
                    break;
            }
            map[rowIndex][col] = space;
        }
    }

    public void setMainCharacterMove(String moveData) {
        mainCharacter.setMoveData(moveData);
    }

    public void setMonsterInfo(String info) {
        String[] data = info.split(" ");
        int row = Integer.parseInt(data[0]) - 1;
        int col = Integer.parseInt(data[1]) - 1;

        Monster monster = (Monster) map[row][col];
        monster.setMonsterInfo(data);
    }

    public void setItemBoxInfo(String info) {
        String[] data = info.split(" ");
        int row = Integer.parseInt(data[0]) - 1;
        int col = Integer.parseInt(data[1]) - 1;

        ItemBox itemBox = (ItemBox) map[row][col];
        itemBox.setItemBoxInfo(data);

    }

    public void showMapStats() {
        int mRow = mainCharacter.getRow();
        int mCol = mainCharacter.getCol();

        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                System.out.print("" + map[r][c] + " ");
            }
            System.out.print(" | ");

            for (int c = 0; c < colSize; c++) {
                if ((r == mRow) && (c == mCol)) {
                    System.out.print("@ ");
                } else {
                    System.out.print("" + map[r][c] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Passed Turns : " + gameTurn);
        mainCharacter.showInfo();
        System.out.println("================================================================");
    }

    public void answer() {
        int mRow = mainCharacter.getRow();
        int mCol = mainCharacter.getCol();

        for (int r = 0; r < rowSize; r++) {
            for (int c = 0; c < colSize; c++) {
                if ((r == mRow) && (c == mCol)
                        && mainCharacter.isSurvival()) {
                    System.out.print("@");
                } else {
                    System.out.print(map[r][c]);
                }
            }
            System.out.println();
        }
        System.out.println("Passed Turns : " + gameTurn);
        mainCharacter.answer();
        System.out.println(endMessage);
    }

    public int getMonsterCount() {
        return this.monsterCount;
    }

    public int getItemBoxCount() {
        return itemBoxCount;
    }

    private boolean wallCheck(int nRow, int nCol) {
        return map[nRow][nCol].getType().equals(Wall.TYPE);
    }

    private boolean trapCheck(int nRow, int nCol) {
        return map[nRow][nCol].getType().equals(Trap.TYPE);
    }

    private boolean bossCheck(int nRow, int nCol) {
        return map[nRow][nCol].getType().equals(BossMonster.TYPE);
    }

    public void moveCharacter() {
        while (true) {

            if (!mainCharacter.isSurvival()) { // 주인공 생존 여부
                endMessage = "YOU HAVE BEEN KILLED BY " + mainCharacter.getDeathBy() + "..";
                break;
            }

            if (!bossMonster.isSurvival()) { // 주인공 생존 여부
                endMessage = "YOU WIN!";
                break;
            }

            if (!mainCharacter.moveCheck()) { // 움직임 여부
                endMessage = "Press any key to continue.";
                break;
            }

//            showMapStats();
            gameTurn++;

            int cRow = mainCharacter.getRow();
            int cCol = mainCharacter.getCol();

            int[] nPosition = mainCharacter.getNextPosition();
            int nRow = nPosition[0];
            int nCol = nPosition[1];

            // 이동 못하는 경우
            if (!((0 <= nRow && nRow < rowSize) && (0 <= nCol && nCol < colSize)) // 이동 못하는 경우
                    || wallCheck(nRow, nCol)) { // 벽이 있는 경우
                if (trapCheck(cRow, cCol)) {
                    mainCharacter.trapDamage();
                    if (!mainCharacter.isSurvival()) {
                        if (mainCharacter.ornamentCheck(OrnamentEnum.RE)) { // 부활
                            mainCharacter.resurrection();
                        } else {
                            mainCharacter.setDeathBy("SPIKE TRAP");
                        }
                    }
                }
                continue;
            }

            // 이동을 하는 경우
            Space nextSpace = map[nRow][nCol];
            switch (nextSpace.getType()) {
                case Empty.TYPE:
                    mainCharacter.movePosition(nRow, nCol);
                    break;

                case Trap.TYPE:
                    mainCharacter.movePosition(nRow, nCol);
                    mainCharacter.trapDamage();
                    if (!mainCharacter.isSurvival()) {
                        if (mainCharacter.ornamentCheck(OrnamentEnum.RE)) { // 부활
                            mainCharacter.resurrection();
                        } else {
                            mainCharacter.setDeathBy("SPIKE TRAP");
                        }
                    }
                    break;

                case ItemBox.TYPE:
                    mainCharacter.movePosition(nRow, nCol);
                    mainCharacter.acquireItemBox((ItemBox) nextSpace);
                    map[nRow][nCol] = new Empty(nRow, nCol);
                    break;

                case Monster.TYPE:
                case BossMonster.TYPE:
                    fightMonster(nRow, nCol);
                    break;
            }
        }
    }

    private void fightMonster(int nRow, int nCol) {
        Monster monster = (Monster) map[nRow][nCol];
//        monster.showInfo();
        mainCharacter.attackComboInit();
        mainCharacter.hitComboInit();

        if (bossCheck(nRow, nCol) && mainCharacter.ornamentCheck(OrnamentEnum.HU)) {
            mainCharacter.fullHp();
        }

        while (true) {
            monster.underAttack(mainCharacter.attack());
            if (!monster.isSurvival()) { // 몬스터 죽음
                mainCharacter.experienceGained(monster.getExp());
                map[nRow][nCol] = new Empty(nRow, nCol); // 몬스터 죽음
                mainCharacter.movePosition(nRow, nCol);

                if (mainCharacter.ornamentCheck(OrnamentEnum.HR)) {
                    mainCharacter.healHp(3);
                }
                break;
            }

            mainCharacter.underAttack(monster.getType(), monster.attack());
            if (!mainCharacter.isSurvival()) { // 사람 죽음

                if (mainCharacter.ornamentCheck(OrnamentEnum.RE)) { // 부활
                    mainCharacter.resurrection();
                    monster.attackCancel();
                }
                mainCharacter.setDeathBy(monster.getName());
                break;
            }
        }
    }
}

public class Main {

    private final static String TEST_PATH = "/p17081/input/6.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(IMPLEMENT_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] size = br.readLine().split(" ");
        int rowSize = Integer.parseInt(size[0]);
        int colSize = Integer.parseInt(size[1]);
        Map map = new Map(rowSize, colSize);

        for (int i = 0; i < rowSize; i++) {
            map.setMap(i, br.readLine());
        }
        map.setMainCharacterMove(br.readLine());

        // 몬스터 데이터
        for (int i = 0; i < map.getMonsterCount(); i++) {
            map.setMonsterInfo(br.readLine());
        }

        // 상자 데이터
        for (int i = 0; i < map.getItemBoxCount(); i++) {
            map.setItemBoxInfo(br.readLine());
        }
        br.close();

        // 캐릭터 움직이기
        map.moveCharacter();
//        map.showMapStats();
        map.answer();
    }
}