import java.util.ArrayList;
import java.util.List;

public class PartialSeed {
    private final List<String> partialSeed;

    public PartialSeed(List<String> wordList) {
        partialSeed = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            partialSeed.add(RandomWordGenerator.generateRandomWord(wordList));
        }
    }

    public List<String> getPartialSeed() {
        return partialSeed;
    }
}

