package com.nigorojr.typebest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This class handles selecting words depending on the mode.
 * 
 * @author Naoki Mizuno
 * 
 */

public class WordSelector {
    private int currentMode = WordSelector.NORMAL;
    private int minNumOfLetters = 400;

    /* Modes */
    public static final int NORMAL = 0;
    public static final int LETTERS_A_F = 1;
    public static final int LETTERS_G_P = 2;
    public static final int LETTERS_Q_Z = 3;
    public static final int ENDLESS = 4;
    public static final int PROGRAMMER = 5;
    public static final int CUSTOM = 6;

    /* Default dictionary file locations */
    public static final String DEFAULT_DICT_NORMAL = "dic/Word1500.txt";
    public static final String DEFAULT_DICT_A_F = "dic/WordA-F.txt";
    public static final String DEFAULT_DICT_G_P = "dic/WordG-P.txt";
    public static final String DEFAULT_DICT_Q_Z = "dic/WordQ-Z.txt";
    public static final String DEFAULT_DICT_PROGRAMMER = "dic/Programmer.txt";

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     */
    public ArrayList<String> getWords() {
        return getWords(currentMode, minNumOfLetters);
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     */
    public ArrayList<String> getWords(int mode) {
        return getWords(mode, minNumOfLetters);
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     */
    public ArrayList<String> getWords(int mode, int numOfLetters) {
        switch (mode) {
            case NORMAL:
                return getWords(mode, numOfLetters, DEFAULT_DICT_NORMAL);
            case LETTERS_A_F:
                return getWords(mode, numOfLetters, DEFAULT_DICT_A_F);
            case LETTERS_G_P:
                return getWords(mode, numOfLetters, DEFAULT_DICT_G_P);
            case LETTERS_Q_Z:
                return getWords(mode, numOfLetters, DEFAULT_DICT_Q_Z);
            case ENDLESS:
                // TODO
                String[] endlesslocations = {};
                return getWords(mode, numOfLetters, endlesslocations);
            case PROGRAMMER:
                return getWords(mode, numOfLetters, DEFAULT_DICT_PROGRAMMER);
            default:
                return getWords(mode, numOfLetters, DEFAULT_DICT_NORMAL);
        }
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     * @param fileLocation
     *            The location of the dictionary file.
     */
    public ArrayList<String> getWords(int mode, int numOfLetters,
            String fileLocation) {
        return getWords(mode, numOfLetters, new String[] { fileLocation });
    }

    /**
     * Creates a new WordSelector instance for the given mode using the also
     * given total number of letters that needs to be typed. Note that the WPM
     * (words per minute) *cannot* be calculated from the time it took to type
     * everything from the following formula:
     * WPM = ((numOfLetters / timeInSeconds) * 60) / 5
     * because WPM does not include the number of spaces (as far as I know).
     * 
     * @param mode
     *            The current mode defined in this class.
     * @param numOfLetters
     *            The total number of letters that needs to be typed. This
     *            number includes spaces.
     * @param fileLocations
     *            An array of path to the dictionary files. Each path can be
     *            either absolute path or relative path.
     */
    public ArrayList<String> getWords(int mode, int numOfLetters,
            String[] fileLocations) {
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> allWords = new ArrayList<String>();
        Random rand = new Random();

        try {
            for (String fileLocation : fileLocations) {
                File dictionaryFile = new File(fileLocation);
                Scanner scanner = new Scanner(dictionaryFile);

                // Read everything from the files
                while (scanner.hasNext())
                    allWords.add(scanner.nextLine());
            }

            int letterCount = 0;
            while (letterCount < numOfLetters) {
                int random = rand.nextInt(allWords.size());

                // Add the word to the ArrayList and increment letter count
                words.add(allWords.get(random));
                letterCount += allWords.get(random).length();

                // Add 1 more taking account of the space character
                if (letterCount + 1 < numOfLetters)
                    letterCount++;
            }
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Dictionary file was not found!", "Missing File",
                    JOptionPane.ERROR_MESSAGE);
        }

        return words;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public int getMinNumOfLetters() {
        return minNumOfLetters;
    }

    public void setCurrentMode(int mode) {
        currentMode = mode;
    }

    public void setMinNumOfLetters(int minNumOfLetters) {
        this.minNumOfLetters = minNumOfLetters;
    }
}
