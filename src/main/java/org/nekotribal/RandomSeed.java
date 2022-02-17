package org.nekotribal;

import org.nekotribal.exception.BitsNumberMismatchException;
import lombok.Getter;
import org.nekotribal.seedfactory.ByteToSHA256;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
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
        seed.add(getRandomChecksumWord(wordList));
    }

    private String getRandomChecksumWord(List<String> wordList) {
        String firstBits = calculateFirstBits(wordList);
        validateFirstBits(firstBits);
        String freeBits = calculateFreeBits();
        byte[] entBytesArray = createEntBytesArray(firstBits, freeBits);
        byte[] hash = ByteToSHA256.byteTableToSHA256(entBytesArray);
        String checkSumBits = calculateCheckSumBits(hash);
        int wordIndex = calculateWordIndex(freeBits, checkSumBits);
        return wordList.get(wordIndex);
    }

    private String calculateFirstBits(List<String> wordList) {
        return seed.stream()
                .map(word -> wordList.indexOf(word))
                .map(index -> Integer.toBinaryString(index))
                .map(binaryNumber -> fillMissingBits(binaryNumber, 11))
                .collect(Collectors.joining());
    }

    private void validateFirstBits(String firstBits) {
        if (firstBits.length() != 121) {
            throw new BitsNumberMismatchException();
        }
    }

    private String calculateFreeBits() {
        int randomNumber = RNG.nextInt(128);
        String binaryString = Integer.toBinaryString(randomNumber);
        String formattedBinaryString = fillMissingBits(binaryString, 11);
        return formattedBinaryString.substring(4);
    }

    private byte[] createEntBytesArray(String firstBits, String freeBits) {
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
        return entBytesArray;
    }

    private String calculateCheckSumBits(byte[] hash) {
        byte checkSumByte = hash[0];
        String formattedCheckSumByte = fillMissingBits(Integer.toBinaryString(checkSumByte & 0xff), 8);
        return formattedCheckSumByte.substring(0, 4);
    }

    private int calculateWordIndex(String freeBits, String checkSumBits) {
        String wordBits = freeBits + checkSumBits;
        return Integer.parseInt(wordBits, 2);
    }

    private String fillMissingBits(String binaryNumber, int bitLength) {
        int length = binaryNumber.length();
        int missingBits = bitLength - length;
        return ZERO_STRING.substring(0, missingBits) + binaryNumber;
    }
}

