package project;

/**
 * A way to keep track of words of importance along with a measure of their
 * importance.
 */
public class Keyword {

    /** The word of importance. */
    private String word;
    /** The importance/priority of the word. */
    private int priority;

    /**
     * Create a keyword with a priority from a given String and int.
     * @param s The String which denotes the word of importance.
     * @param num The int which denotes how important it is.
     */
    public Keyword(String s, int num) {
        word = s;
        priority = num;
    }

    /**
     * Set the priority of the Keyword to i.
     * @param i The new priority of the Keyword.
     */
    public void setPriority(int i) {
        priority = i;
    }

    /**
     * Return the word of importance.
     * @return The word of importance.
     */
    public String getWord() {
        return word;
    }

    /**
     * Return the importance/priority of the word.
     * @return The importance/priority of the word.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * The String representation of Keyword.
     * The format is "word : priority".
     * @return The String representation of Keyword.
     */
    @Override
    public String toString() {
        return word + " : " + String.valueOf(priority);
    }
}
