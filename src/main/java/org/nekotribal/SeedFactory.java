package org.nekotribal;

public class SeedFactory {
    public static void main(String[] args) {
        WordList wordList = new WordList();
        RandomSeed seed = new RandomSeed(wordList.getWordList());

        System.out.println(seed.getSeed());
    }
}
