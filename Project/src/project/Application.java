package project;

/**
 * An application has all the frames and important information in it.
 * All the variables in it will be public so every frame can access to this
 * information and change them.
 */
public class Application {

    /** JFrame InputFrame window.*/
    public InputFrame inputWindow;
    /** JFrame KeywordPriorityFrame window. */
    public KeywordPriorityFrame keyPriorityWindow;
    /** JFrame OutputFrame window. */
    public OutputFrame outputWindow;
    /** JFrame HistoryFrame window. */
    public HistoryFrame historyWindow;
    /** WebsiteList that has a list of web sites that will used in search. */
    public WebsiteList webList;
    /** ParagrphAbstract that has abstract and keywords that will be used in
    search. */
    public ParagraphAbstract abs = new ParagraphAbstract();
    /** HistoryDatabaseManager to manage history database.*/
    public HistoryDatabaseManager HDBM = new HistoryDatabaseManager();

    /**
     * Make a new application and run the program.
     */
    public Application() {
        HDBM.setDBCredentials("csc207usr", "csc207passw0rd");
        webList = new WebsiteList();
        inputWindow = new InputFrame();
        inputWindow.setVisible(true);
        keyPriorityWindow = new KeywordPriorityFrame();
        abs.addObserver(keyPriorityWindow);
        webList.addObserver(inputWindow);
    }
}
