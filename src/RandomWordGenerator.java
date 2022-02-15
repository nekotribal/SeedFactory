import java.util.List;
import java.util.Random;

public class RandomWordGenerator {
    private static final Random RNG = new Random();
    private RandomWordGenerator() {
    }

    public static String generateRandomWord(List<String> wordList) {
        return wordList.get(RNG.nextInt(wordList.size()));
    }
}
