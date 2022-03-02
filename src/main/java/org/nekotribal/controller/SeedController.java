package org.nekotribal.controller;

import org.nekotribal.RandomSeed;
import org.nekotribal.WordList;
import org.springframework.web.bind.annotation.*;

@RestController
public class SeedController {

    @GetMapping(path = "/generate")
    public String getSeed(){
        WordList wordList = new WordList();
        RandomSeed seed = new RandomSeed(wordList.getWordList());
        return String.join(" ", seed.getSeed());
    }
}
