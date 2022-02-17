package org.nekotribal;

import lombok.Getter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class WordList {

    private static final String WORD_LIST_FILE_PATH = "/english.txt";
    private final List<String> wordList;

    public WordList() {
        InputStream is = loadInputStream();
        wordList = initializeWordList(is);
    }

    private InputStream loadInputStream() {
            return getClass().getResourceAsStream(WORD_LIST_FILE_PATH);
    }

    private List<String> initializeWordList(InputStream is) {
        List<String> wordList = new ArrayList<>();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }
        return wordList;
    }
}
