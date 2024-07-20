package com.wordle;                                                                                                    

import java.util.List;
import java.util.ArrayList;

import com.wordle.exceptions.*;

import org.apache.commons.lang3.ArrayUtils;

class WordleGame {
    private String word;
    private List<String> wordGuessHistory = new ArrayList<>();
    private List<List<GuessTypeEnum>> wordGuessAccuracy = new ArrayList<>();
    private int guessesRemaining = 5;

    public WordleGame(String word) throws InvalidWordLengthException
    {
        if (word.length() != 5) {
            throw new InvalidWordLengthException("Word length for wordle must be 5 letters long");
        }
        this.word = word;
    }

    public List<String> getWordGuessHistory() {
        return this.wordGuessHistory; 
    }

    public List<List<GuessTypeEnum>> getWordGuessAccuracy() {
        return this.wordGuessAccuracy;
    }

    public static WordleGame tryCreatingNewWordleGame(String word) {
        WordleGame game = null;
        try {
            game = new WordleGame(word) ;
        } catch(InvalidWordLengthException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return game;
    }

    public int getRemainingGuesses() {
        return this.guessesRemaining;
    }

    public boolean didPlayerWin(List<GuessTypeEnum> recentGuess) {
        int correctGuesses = 0;

        for (GuessTypeEnum i : recentGuess) {
            if (i == GuessTypeEnum.CORRECT) {
                correctGuesses++;
            }
        }

        return correctGuesses == 5;
    }

    public List<GuessTypeEnum> guessWord(String guess) throws InvalidWordLengthException  {
        if (guess.length() != 5) {
            throw new InvalidWordLengthException("Guess must be 5 letters in length");
        }
        String[] wordSplit = this.word.split("");
        List<GuessTypeEnum> currentGuessData = new ArrayList<>();
        int i = 0;
        for ( String letter : guess.split("")  ) {
            if (ArrayUtils.contains(wordSplit, letter))  {
                if (wordSplit[i].equals(letter)) {
                    currentGuessData.add(GuessTypeEnum.CORRECT);
                } else {
                    currentGuessData.add(GuessTypeEnum.PARTIAL);
                }
            } else {
                currentGuessData.add(GuessTypeEnum.INCORRECT);
            }
            i++;
        }

        this.guessesRemaining -= 1;
        this.wordGuessAccuracy.add(currentGuessData);
        this.wordGuessHistory.add(guess);

        return currentGuessData;
    }
}
