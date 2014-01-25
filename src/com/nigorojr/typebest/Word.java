package com.nigorojr.typebest;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Word extends JPanel implements Iterator<Letter> {
    private ArrayList<Letter> letters = new ArrayList<Letter>();
    private Iterator<Letter> lettersIterator;
    private String rawWord;

    /**
     * Accepts a String which is what will be shown in this JPanel.
     * The constructor will then split the word into letters and create a Letter
     * object for each letter. After that, Letter objects are added to the
     * this JPanel.
     * 
     * @param word
     *            The word that will be shown as a collection of "Letter" class.
     * @param pref
     *            A Preferences object that contains information such as the
     *            foreground color, background color and the font of the
     *            letters.
     */
    public Word(String word, Preferences pref) {
        rawWord = word;

        for (int i = 0; i < rawWord.length(); i++) {
            Letter letter = new Letter(rawWord.charAt(i), pref);
            letters.add(letter);
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        for (int i = 0; i < letters.size(); i++)
            this.add(letters.get(i));

        lettersIterator = letters.iterator();
    }

    /**
     * Returns the next letter. This method can be used to avoid confusion when
     * using the <code>next</code> method in the Line class.
     * 
     * @return The next letter as a Letter object.
     */
    public Letter nextLetter() {
        return next();
    }

    @Override
    public boolean hasNext() {
        return lettersIterator.hasNext();
    }

    @Override
    public Letter next() {
        return lettersIterator.next();
    }

    @Override
    public void remove() {
        lettersIterator.remove();
    }
}
