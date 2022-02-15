import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class WordList {

    private static final String WORD_LIST_FILE_PATH = "english.txt";
    private final List<String> wordList;

    public WordList() {
        FileInputStream is = loadFileInputStream();
        wordList = initializeWordList(is);
    }

    private FileInputStream loadFileInputStream() {
        try {
            File file = new File(WORD_LIST_FILE_PATH);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private List<String> initializeWordList(FileInputStream is) {
        List<String> wordList = new ArrayList<>();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }
        return wordList;
    }
}
