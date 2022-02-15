import seedfactory.ByteToSHA256;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomSeed {
    private static final String ZERO_STRING = "0000000000";
    private static final int BITS_IN_BYTE = 8;
    private static final Random RNG = new Random();
    private final List<String> seed;

    public RandomSeed(List<String> wordList) {
        seed = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            seed.add(RandomWordGenerator.generateRandomWord(wordList));
        }
        seed.add(getRandomChecksumWord(seed, wordList));
    }

    private String getRandomChecksumWord(List<String> seed, List<String> wordList) {
        String firstBits = seed.stream()
                .map(word -> wordList.indexOf(word))
                .map(index -> Integer.toBinaryString(index))
                .map(binaryNumber -> fillMissingBits(binaryNumber, 11))
                .collect(Collectors.joining());
        assert firstBits.length() == 121; //if?
        int randomNumber = RNG.nextInt(128);
        String freeBits = fillMissingBits(Integer.toBinaryString(randomNumber), 11).substring(4);
        String entBits = firstBits + freeBits;
        List<Integer> entBytes = IntStream.range(0, entBits.length())
                .filter(i -> i % BITS_IN_BYTE == 0)
                .mapToObj(i -> entBits.substring(i, i + BITS_IN_BYTE))
                .map(entByte -> Integer.parseInt(entByte, 2))
                .collect(Collectors.toList());
        byte[] entBytesArray = new byte[entBytes.size()];
        for (int i = 0; i < entBytes.size(); i++) {
            entBytesArray[i] = (byte) entBytes.get(i).intValue();
        }

        byte[] hash = ByteToSHA256.byteTableToSHA256(entBytesArray);
        byte csByte = hash[0];
        String csBits = fillMissingBits(Integer.toBinaryString(csByte & 0xff), 8).substring(0, 4);
        String wordBits = freeBits + csBits;
        int wordIndex = Integer.parseInt(wordBits, 2);
        return wordList.get(wordIndex);
    }

    private String fillMissingBits(String binaryNumber, int bitLength) {
        int length = binaryNumber.length();
        int missingBits = bitLength - length;
        return ZERO_STRING.substring(0, missingBits) + binaryNumber;
    }

    public List<String> getPartialSeed() {
        return seed;
    }
}

