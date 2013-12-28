package com.nigorojr.typebest;

import javax.swing.JLabel;

public class Letter extends JLabel {
    private char rawLetter;

    /**
     * Constructs a new JLabel with the given letter.
     * 
     * @param letter
     *            The one letter that will be used as the content of this
     *            JLabel.
     */
    public Letter(char letter) {
        super(Character.toString(letter));
        rawLetter = letter;
        // TODO: Add color variables (toBeTyped etc...) in TypePanel class and
        // make them protected so that they can be accessed in here.
    }

    /**
     * Creates a new "Letter" JLabel with the given letter. The length of the
     * given String must be at least 1. If a String with length more than 1 is
     * given, only the first letter will be stored.
     * 
     * @param letter
     *            The one letter that will be used as the content of JLabel.
     *            This constructor assumes that the given String has length of
     *            at least 1.
     */
    public Letter(String letter) {
        this(letter.charAt(0));
    }

    /**
     * Getter method for the actual letter contained in this JLabel.
     * 
     * @return The actual letter contained in this JLabel.
     */
    public char getRawLetter() {
        return rawLetter;
    }

    /**
     * Getter method for the actual letter contained in this JLabel.
     * 
     * @return The actual letter contained in this JLabel in String.
     */
    public String getStringRawLetter() {
        return Character.toString(rawLetter);
    }

    /**
     * Checks whether the given keystroke corresponds to the letter shown in the
     * JLabel.
     * 
     * @param keyStroke
     *            The keystroke that the user made.
     * @return True if the given keystroke is a correct keystroke, in other
     *         words, if the given letter is the same as the rawLetter.
     */
    public boolean isCorrectKeyStroke(char keyStroke) {
        return (rawLetter == keyStroke);
    }

    /**
     * Checks whether the given keystroke corresponds to the letter shown in the
     * JLabel.
     * 
     * @param keyStroke
     *            The keystroke that the user made. This variable must have
     *            length 1. If the length is more than 1, only the first letter
     *            is seen.
     * @return True if the given keystroke is a correct keystroke, in other
     *         words, if the given letter is the same as the rawLetter.
     */
    public boolean isCorrectKeyStroke(String keyStroke) {
        return isCorrectKeyStroke(keyStroke.charAt(0));
    }
}
