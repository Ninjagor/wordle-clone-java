package com.wordle;

import java.util.Scanner;
import java.util.List;

import com.wordle.exceptions.*;

class Main {
    public static void main(String[] args)
    {
        WordleGame game = WordleGame.tryCreatingNewWordleGame("hello");
        Scanner scanner = new Scanner(System.in);
        boolean didWin = false;

        while (!(didWin) && (game.getRemainingGuesses() > 0)) {
            // asking for user input
            Main.clearConsole();
            List<String> wordGuessHistory = game.getWordGuessHistory();
            List<List<GuessTypeEnum>> wordGuessAccuracy = game.getWordGuessAccuracy();
            
            for (int i = 0; i < wordGuessHistory.size(); i++) {
                System.out.println("\n"+wordGuessHistory.get(i));
                Main.printAccuracy(wordGuessAccuracy.get(i));
            }

            System.out.println("\nEnter your guess:");
            String guess = scanner.nextLine();
            try {
                List<GuessTypeEnum> guessResult = game.guessWord(guess);
                didWin = game.didPlayerWin(guessResult);
            } catch(InvalidWordLengthException e) {
                System.out.println(e.getMessage());
            }
        }
        Main.clearConsole();
        if (didWin) {
            System.out.println("Congrats, you won!");
        } else {
            System.out.println("you ran out of guesses");
        }

        scanner.close();
    }

    private static void printAccuracy(List<GuessTypeEnum> guessResult) {
        StringBuilder res = new StringBuilder();
        for ( GuessTypeEnum i : guessResult  ) {
            switch(i) {
                case GuessTypeEnum.INCORRECT:
                res.append('I');
                break;
            case GuessTypeEnum.PARTIAL:
                res.append('P');
                break;
            case GuessTypeEnum.CORRECT:
                res.append('C');
                break;
            }
        }

        System.out.println(res.toString());
    }

    private static void clearConsole() {
        System.out.print("\033\143");
    }
}
