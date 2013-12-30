package com.nigorojr.typebest;

import java.awt.Color;
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

    /**
     * Changes the foreground color of the letter at the current index. Although
     * it is preferred to use this method, another way to change the color of
     * the current letter is to use the <code>getCurrentLetter</code> method and
     * set the color.
     * 
     * @param color
     *            The color of the letter to be changed to.
     */
    public void setLetterColor(Color color) {
        letters.get(letterCount).setForeground(color);
    }

    /**
     * Moves the index to the next letter.
     */
    public void nextLetter() {
        letterCount++;
    }

    /**
     * Returns the current letter that is waiting to be typed.
     * 
     * @return A Letter object of the current letter.
     */
    public Letter getCurrentLetter() {
        return letters.get(letterCount);
    }

    /**
     * Returns the current index of the letter.
     * 
     * @return The current index of the letter.
     */
    public int getLetterCount() {
        return letterCount;
    }
}
