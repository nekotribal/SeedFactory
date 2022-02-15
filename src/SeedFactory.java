import java.nio.charset.StandardCharsets;

import static seedfactory.ByteToSHA256.*;

public class SeedFactory {
    public static void main(String[] args) {
        WordList wordList = new WordList();
        RandomSeed seed = new RandomSeed(wordList.getWordList());

        System.out.println(seed.getSeed());


        String originalString = "doggo";

        byte[] byteHash = byteTableToSHA256(originalString.getBytes(StandardCharsets.UTF_8));
        System.out.println(bytesToHex(byteHash));

    }
}
