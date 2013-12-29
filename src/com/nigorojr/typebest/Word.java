package com.nigorojr.typebest;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Word {
    private ArrayList<Letter> word = new ArrayList<Letter>();
    private String rawWord;

    /**
     * Accepts a String which is what will be shown in this JLabel.
     * The constructor will then split the word into letters and create a Letter
     * object for each letter.
     * 
     * @param word
     *            The word that will be shown as a collection of "Letter" class.
     */
    public Word(String word) {
        rawWord = word;

        split();
    }

    /**
     * Splits up the word into letters and add them to an ArrayList.
     */
    private void split() {
        for (int i = 0; i < rawWord.length(); i++) {
            Letter letter = new Letter(rawWord.charAt(i));
            word.add(letter);
        }
    }

    /**
     * Returns the width of the word by adding the width of all the letters
     * consisting the word.
     * 
     * @return The width of the word.
     */
    public int getWidth() {
        int width = 0;
        for (int i = 0; i < word.size(); i++)
            width += word.get(i).getWidth();
        return width;
    }
}
