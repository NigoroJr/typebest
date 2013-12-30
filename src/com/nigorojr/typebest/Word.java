package com.nigorojr.typebest;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Word extends JPanel {
    private ArrayList<Letter> letters = new ArrayList<Letter>();
    private String rawWord;
    private int letterCount;

    /**
     * Accepts a String which is what will be shown in this JPanel.
     * The constructor will then split the word into letters and create a Letter
     * object for each letter. After that, Letter objects are added to the
     * this JPanel.
     * 
     * @param word
     *            The word that will be shown as a collection of "Letter" class.
     */
    public Word(String word) {
        rawWord = word;
        letterCount = 0;

        split(word);
        for (int i = 0; i < letters.size(); i++)
            this.add(letters.get(i));
    }

    /**
     * Splits up the word into letters and add them to an ArrayList.
     */
    private void split(String word) {
        for (int i = 0; i < rawWord.length(); i++) {
            Letter letter = new Letter(rawWord.charAt(i));
            letters.add(letter);
        }
    }

    /**
     * Checks whether the given letter is the correct letter to be typed.
     * 
     * @param letter
     *            A one-letter character to be checked if it's the correct
     *            letter.
     * @return True if the given letter is correct, false if not.
     */
    public boolean isCorrectLetter(char letter) {
        return letters.get(letterCount).getRawLetter() == letter;
    }
}
