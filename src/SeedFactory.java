public class SeedFactory {
    public static void main(String[] args){
        WordList wordList = new WordList();
        PartialSeed seed = new PartialSeed(wordList.getWordList());

        System.out.println(seed.getPartialSeed());

    }
}
