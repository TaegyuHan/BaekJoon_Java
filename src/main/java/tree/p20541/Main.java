package tree.p20541;

import static tree.Path.TREE_PATH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Solution code for "BaekJoon 엘범정리".
 * <p>
 * - Problem link: <a href="https://www.acmicpc.net/problem/20541">Link</a>
 * 작성자: gksxorb147
 */
public class Main {

    private static final String TEST_PATH = "/p20541/input/3.txt";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(TREE_PATH + TEST_PATH));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int commandCount = Integer.parseInt(br.readLine());

        FileSystem fileSystem = new FileSystem();

        for (int i = 0; i < commandCount; i++) {
            String command = br.readLine();
            fileSystem.command(command);
        }

        fileSystem.answer();
    }
}

enum Command {
    MKALB("mkalb", "폴더 생성"),
    RMALB("rmalb", "폴더 삭제"),
    INSERT("insert", "파일 삽입"),
    DELETE("delete", "파일 삭제"),
    CA("ca", "폴더 이동");

    private final String command;
    private final String description;

    Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public static Command of(String command) {
        for (Command commandType : Command.values()) {
            if (commandType.getCommand().equals(command)) {
                return commandType;
            }
        }
        throw new IllegalArgumentException("Invalid command: " + command);
    }
}

enum RmalbType {
    MINUS(-1, "이름이 사전순으로 더 앞서는 엘범 삭제"),
    ZERO(0, "현재 앨범에 속해있는 모든 엘범을 삭제"),
    PLUS(1, "이름이 사전순으로 더 뒤에 있는 엘범 삭제"),
    DEFAULT(777, null);

    private final int command;
    private final String description;

    RmalbType(int command, String description) {
        this.command = command;
        this.description = description;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static RmalbType of(String command) {

        if (!Utils.isInteger(command)) {
            return DEFAULT; // 숫자 변환 불가 인경우
        }

        for (RmalbType rmalbType : RmalbType.values()) {
            if (rmalbType.getCommand() == Integer.parseInt(command)) {
                return rmalbType;
            }
        }
        return DEFAULT;
    }
}

enum DeleteType {
    MINUS(-1, "이름이 사전순으로 더 앞서는 사진 삭제"),
    ZERO(0, "현재 앨범에 속해있는 모든 사진을 삭제"),
    PLUS(1, "이름이 사전순으로 더 뒤에 있는 사진 삭제"),
    DEFAULT(777, null);

    private final int command;
    private final String description;

    DeleteType(int command, String description) {
        this.command = command;
        this.description = description;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static DeleteType of(String command) {
        
        if (!Utils.isInteger(command)) {
            return DEFAULT; // 숫자 변환 불가 인경우
        }

        for (DeleteType deleteType : DeleteType.values()) {
            if (deleteType.getCommand() == Integer.parseInt(command)) {
                return deleteType;
            }
        }
        return DEFAULT;
    }
}

enum CaType {
    ROOT("/", "루트 폴더로 이동"),
    UP("..", "상위 폴더로 이동"),
    DEFAULT("", null);

    private final String command;
    private final String description;

    CaType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public static CaType of(String command) {
        for (CaType caType : CaType.values()) {
            if (caType.getCommand().equals(command)) {
                return caType;
            }
        }
        return DEFAULT;
    }
}

class FileSystem {
    private Folder path;
    private final Folder root;
    private final StringBuilder result;

    public FileSystem() {
        this.root = new Folder("album", null);
        this.path = root;
        this.result = new StringBuilder();
    }

    public void command(String command) {
        String[] commandParts = command.split(" ");
        String commandName = commandParts[0];
        String commandArgument = commandParts[1];

        Command commandType = Command.of(commandName);

        switch (commandType) {
            case MKALB: {
                if (!this.path.createFolder(commandArgument)) {
                    this.result.append("duplicated album name").append("\n");
                }
            }
            break;
            case RMALB: {
                int[] deleteCount = this.path.deleteFolder(commandArgument);
                this.result.append(deleteCount[0]).append(" ").append(deleteCount[1]).append("\n");
            }
            break;
            case INSERT: {
                if (!this.path.insertFile(commandArgument)) {
                    this.result.append("duplicated photo name").append("\n");
                }
            }
            break;
            case DELETE: {
                int deleteCount = this.path.deleteFile(commandArgument);
                this.result.append(deleteCount).append("\n");
            }
            break;
            case CA: {
                this.path = ca(commandArgument);
                this.result.append(this.path.getName()).append("\n");
            }
            break;
            default: {
                throw new IllegalArgumentException("Invalid command: " + command);
            }
        }
    }

    public Folder ca(String argument) {
        CaType caType = CaType.of(argument);

        switch (caType) {
            case ROOT: {
                return root;
            }
            case UP: {
                return isRoot() ? path : path.getParent();
            }
            default: {
                return path.moveFolder(argument);
            }
        }
    }

    private boolean isRoot() {
        return this.path.getParent() == null;
    }

    public void answer() {
        System.out.print(this.result);
    }
}

class Folder implements Comparable<Folder> {
    private final Folder parent;
    private final String name;
    private final Map<String, Folder> folders;
    private final Map<String, File> files;

    public Folder(String name, Folder parent) {
        this.name = name;
        this.parent = parent;
        this.folders = new TreeMap<>();
        this.files = new TreeMap<>();
    }

    public Folder getParent() {
        return parent;
    }

    public boolean createFolder(String name) {

        // 이미 파일이 있는지 확인
        if (folders.containsKey(name)) {
            return false;
        }

        folders.put(name, new Folder(name, this));
        return true;
    }

    public int[] deleteFolder(String argument) {
        RmalbType rmalbType = RmalbType.of(argument);
        int[] result = {0, 0};

        if (folders.size() == 0) {
            return result; // 폴더 없으면 0 반환
        }

        switch (rmalbType) {
            case MINUS: {
                Folder folder = findFirstFolderByAlphabet();
                int[] subResult = folder.remove();
                result[0] += subResult[0] + 1;
                result[1] += subResult[1];
                folders.remove(folder.getName());
            }   
            break;
            case ZERO: {
                for (Folder folder : folders.values()) {
                    int[] subResult = folder.remove();
                    result[0] += subResult[0];
                    result[1] += subResult[1];
                }
                result[0] += folders.size();
                folders.clear();
            }
            break;
            case PLUS: {
                Folder folder = findLastFolderByAlphabet();
                int[] subResult = folder.remove();
                result[0] += subResult[0] + 1;
                result[1] += subResult[1];
                folders.remove(folder.getName());
            }
            break;
            default: { // 폴더 이름 삭제

                if (!folders.containsKey(argument)) {
                    return result; // 폴더 없으면 0 반환
                }

                Folder folder = folders.get(argument);
                int[] subResult = folder.remove();
                result[0] += subResult[0] + 1;
                result[1] += subResult[1];
                folders.remove(argument);   
            }
        }

        return result;
    }

    private Folder findFirstFolderByAlphabet() {
        String key = ((TreeMap<String, Folder>)folders).firstKey();
        return folders.get(key);
    }

    private Folder findLastFolderByAlphabet() {
        String key = ((TreeMap<String, Folder>)folders).lastKey();
        return folders.get(key);
    }

    public boolean insertFile(String name) {
        if (files.containsKey(name)) {
            return false;
        }

        files.put(name, new File(name));
        return true;
    }

    public int deleteFile(String argument) {
        DeleteType deleteType = DeleteType.of(argument);

        if (files.size() == 0) {
            return 0;
        }

        switch (deleteType) {
            case MINUS: {
                File file = findFirstFileByAlphabet();
                files.remove(file.getName());
                return 1;
            }
            case ZERO: {
                int fileCount = files.size();
                files.clear();
                return fileCount;
            }
            case PLUS: {
                File file = findLastFileByAlphabet();
                files.remove(file.getName());
                return 1;
            }
            default: {
                if (!files.containsKey(argument)) {
                    return 0; // 파일 없으면 0 반환
                }
                files.remove(argument);
                return 1;
            }
        }
    }

    private File findFirstFileByAlphabet() {
        String key = ((TreeMap<String, File>)files).firstKey();
        return files.get(key);
    }

    private File findLastFileByAlphabet() {
        String key = ((TreeMap<String, File>)files).lastKey();
        return files.get(key);
    }

    public Folder moveFolder(String argument) {
        if (!folders.containsKey(argument)) {
            return this;
        }
        return folders.get(argument);
    }

    public String getName() {
        return name;
    }

    public int[] remove() {
        int[] result = {0, 0};

        // 모든 폴더 제거
        result[0] += folders.size();
        for (Folder folder : folders.values()) {
            int[] subResult = folder.remove();
            result[0] += subResult[0];
            result[1] += subResult[1];
        }

        // 모든 파일 제거
        int fileCount = files.size();
        files.clear();
        result[1] += fileCount;
        return result;
    }

    @Override
    public int compareTo(Folder o) {
        return this.name.compareTo(o.name);
    }
}

class File implements Comparable<File> {
    private final String name;

    public File(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(File o) {
        return this.name.compareTo(o.name);
    }
}

class Utils {
    public static boolean isInteger(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}