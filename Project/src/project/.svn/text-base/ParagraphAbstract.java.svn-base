package project;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

/**
 * The content of the supplied abstract/paragraph along with the keywords
 * contained in its text.
 */
public class ParagraphAbstract extends Observable
        implements Iterable<Keyword> {

    /** The paragraph given to be compared. */
    private String paragraph;
    /** The list of Keyword associated with the given paragraph. */
    private LinkedList<Keyword> keywordList = new LinkedList<Keyword>();
    /** The priority of all Keywords in a ParagraphAbstract. */
    private int totalPriority;
    /** hasKeywords is true iff there is at least one Keyword in keywordList */
    private boolean hasKeywords;

    /**
     * Initialize the object that keeps track of a paragraph and its
     * associated Keyword list.
     */
    public ParagraphAbstract() {
    }

    /**
     * Set the information of a paragraph and its associated Keyword list.
     * @param abstractText The text from which the Keyword list is made from.
     */
    public void setParagraphInfo(String abstractText) {
        paragraph = abstractText;
        keywordList = keywordFinder(paragraph);
        hasKeywords = (keywordList.size() != 0);
        setTotalPriority(keywordList);
        setChanged();
        notifyObservers();
    }

    /**
     * Return a LinkedList of Keywords that were found in the text.
     * Unimportant words have minimal priority, Maui extracted words have a
     * high priority and the rest have normal priority. A word is considered
     * unimportant if its length is at least one standard deviation less than
     * the mean length all the words in the text.
     * @param text The String that is being searched for keywords.
     * @return A LinkedList of Keywords found in the text.
     */
    private LinkedList<Keyword> keywordFinder(String text) {
        LinkedList<Keyword> keyList = new LinkedList<Keyword>();
        HashSet<String> wordSet = new HashSet<String>();
        double minLength = 0.0;

        // Using Maui keyword extractor, extract the keywords/keyphrases
        // found in text and set their priority to 4.
        LinkedList<String> mauiExtractedTopics = getKeyphrases(text);
        for (String keyPhrase : mauiExtractedTopics) {
            if (!keyPhrase.contentEquals("") && wordSet.add(keyPhrase)) {
                keyList.add(new Keyword(keyPhrase, 4));
            }
        }

        String lowerCaseText = text.toLowerCase();
        // Split the text by non-word characters ie. [^a-zA-Z_0-9].
        String[] wordArray = lowerCaseText.split("\\W");
        // Find the minimum length of a word to be considered important.
        if (wordArray.length > 1) {
            minLength = decideImportance(wordArray);
        }
        for (String word : wordArray) {
            // Don't add the empty string as a keyword. Also, don't add the
            // keyword it it has already been added.
            if (!word.contentEquals("") && wordSet.add(word)) {
                // Check importance of word.
                if (word.length() >= minLength) {
                    keyList.add(new Keyword(word, 3));
                } else {
                    keyList.add(new Keyword(word, 1));
                }
            }
        }
        return keyList;
    }

    /**
     * Decide and return the minimum importance needed for a word to be a
     * keyword. In this case, words that are shorter than one standard
     * deviation of the mean length of all words will be considered not very
     * important.
     * @param wordArray The array of strings containing potential keywords.
     * Note that this array will always contain more than one element.
     * @return The minimum length needed to be an important keyword.
     */
    private double decideImportance(String[] wordArray) {
        // Calculate the mean length of a word in an array.
        double totalLength = 0.0;
        int uselessWords = 0;
        for (String word : wordArray) {
            if (!word.contentEquals("")) {
                totalLength += word.length();
            } else {
                uselessWords += 1;
            }
        }
        double mean = totalLength / (wordArray.length - uselessWords);

        // Calculate the standard deviation of word length in an array.
        double varianceTotal = 0.0;
        for (String word : wordArray) {
            if (!word.contentEquals("")) {
                Double wordLength = new Double(word.length());
                varianceTotal += Math.pow((wordLength - mean), 2);
            }
        }
        double strdDev = Math.pow(varianceTotal / (wordArray.length
                - uselessWords - 1), 0.5);

        // Return The minimum length needed to be important.
        return mean - strdDev;
    }

    /**
     * Set the total priority of a ParagraphAbstract from its list of Keywords.
     * @param keywordList The list of Keywords from which the total priority
     * will be set.
     */
    private void setTotalPriority(LinkedList<Keyword> keywordList) {
        for (Keyword keyword : keywordList) {
            totalPriority += keyword.getPriority();
        }
    }

    /**
     * Set the priority of an existing Keyword with name word.
     * @param word The name of the Keyword whose priority will be changed.
     * @param priority The new priority of the Keyword.
     */
    public void setKeywordPriority(String word, int priority) {
        // Search for the Keyword then change its priority.
        for (Keyword keyword : keywordList) {
            if (keyword.getWord().contentEquals(word)) {
                totalPriority += priority - keyword.getPriority();
                keyword.setPriority(priority);
                setChanged();
                notifyObservers();

            }
        }
    }

    /**
     * Add a Keyword to the list of Keywords.
     * @param word The name of the Keyword to be added.
     * @param priority The priority of the Keyword to be added.
     * @return True iff new Keyword is added.
     */
    public boolean addKeyword(String word, int priority) {
        boolean found = false;
        boolean added = false;
        // Check if word is already a Keyword.
        for (Keyword keyword : keywordList) {
            if (keyword.getWord().contentEquals(word)) {
                found = true;
            }
        }
        // If word is not a Keyword, then add it.
        if (!found) {
            keywordList.add(new Keyword(word, priority));
            totalPriority += priority;
            added = true;
            setChanged();
            notifyObservers();
        }
        return added;

    }

    /**
     * Return the priority of the Keyword with name word.
     * @param word The name of the Keyword.
     * @return The priority of the Keyword with name word.
     */
    public int getKeywordPriority(String word) {
        int priority = 0;
        for (Keyword keyword : keywordList) {
            if (keyword.getWord().contentEquals(word)) {
                priority = keyword.getPriority();
            }
        }
        return priority;
    }

    /**
     * Remove the Keyword named word from the List of Keywords, and reduce the
     * total priority of this ParagraphAbstract object by the removed Keyword's
     * priority.
     * @param word The name of the Keyword to be removed.
     */
    public void deleteKeyword(String word) {
        Keyword key = new Keyword("", 0);
        for (Keyword keyword : keywordList) {
            if (keyword.getWord().equals(word)) {
                totalPriority -= keyword.getPriority();
                key = keyword;
            }
        }
        keywordList.remove(key);
        if (keywordList.isEmpty()) {
            hasKeywords = false;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return the originally used paragraph.
     * @return The originally used paragraph.
     */
    public String getParagraph() {
        return paragraph;
    }

    /**
     * Return the paragraph's associated list of Keywords.
     * @return The paragraph's associated list of Keywords.
     */
    protected LinkedList<Keyword> getKeywordList() {
        return keywordList;
    }

    /**
     * Return an Iterator over ParagraphAbstracts' Keyword list.
     * @return An Iterator over ParagraphAbstracts' Keyword list.
     */
    public Iterator<Keyword> iterator() {
        return keywordList.iterator();
    }

    /**
     * Return the total priority of the ParagraphAbstract.
     * @return The total priority of the ParagraphAbstract.
     */
    public int getTotalPriority() {
        return totalPriority;
    }

    /**
     * Return whether the ParagraphAbstract has Keywords.
     * @return Whether the ParagraphAbstract has Keywords.
     */
    public boolean hasKeywords() {
        return hasKeywords;
    }

    /**
     * Return the LinkedList of keyphrases found by Maui in an abstract.
     * Maui Topic Extractor website : http://code.google.com/p/maui-indexer/
     * @param abstractInput The text to be checked for keyphrases.
     * @return The list of keyphrases in some text.
     */
    public LinkedList<String> getKeyphrases(String abstractInput) {
        Examples exampler = new Examples();
        LinkedList<String> keyphrasesList = new LinkedList<String>();
        try {
            keyphrasesList = exampler.automaticTagging(abstractInput);
        } catch (Exception ex) {
            // Ignore exception and continue finding Keywords normally.
        } finally {
            return keyphrasesList;
        }
    }
}
