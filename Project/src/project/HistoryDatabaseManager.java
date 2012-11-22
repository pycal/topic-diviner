package project;

import java.sql.*;
import java.util.LinkedList;

/**
 * Keeps track of all Search History information.
 */
public class HistoryDatabaseManager {

    //------------------------------------------------------------------------//
    //  Connection related variables for accessing a mySQL DB via JDBC
    //------------------------------------------------------------------------//
    private Connection conn = null;
    private boolean connStatus = false;
    private Statement s = null;
    // The username credential for accessing a mySQL DB
    private String dbuserName = "";
    // The password credential for accessing a mySQL DB
    private String dbpassword = "";
    // The address of a mySQL DB
    private String dburl =
            "jdbc:mysql://205.186.161.66:3306/csc207_historydbmgr";
    //------------------------------------------------------------------------//
    //  Error Messages
    //------------------------------------------------------------------------//
    /** The error message when connection cannot be established
     * to the history database. */
    private static final String connectionMessage = "Connection to search "
            + "history database cannot be established at this time. \nPlease "
            + "make sure you are connected to the Internet and have permission "
            + "to access the search history database.";
    /** The error message when connection cannot be established during saving.*/
    private static final String connectionMessageForSave =
            "Cannot save search information to database.\n" + connectionMessage;
    /** The error message displayed when there is an error in the history
     * database. */
    private static final String databaseMessage = "There is an error in "
            + "connecting to the search history database - it cannot be "
            + "reached at this time.\n Please try again later.";
    /** The error message displayed when there is an error during saving. */
    private static final String databaseMessageForSave =
            "Cannot save search information to database. " + databaseMessage;
    //------------------------------------------------------------------------//

    /**
     * Save the text from an abstract to the database.
     * @param abstractText The text to be saved to the database.
     * @throws HistoryDatabaseException Thrown when the history database
     * cannot be reached.
     */
    public void saveAbstractToDB(String abstractText)
            throws HistoryDatabaseException {
        try {
            if (!connStatus) {
                establishDBConnection();
            }
            s.executeUpdate(
                    "INSERT INTO abstract (date, abstractText)"
                    + " VALUES"
                    + "(NOW(), '" + abstractText + "')");
        } catch (SQLException e) {
            throw new HistoryDatabaseException(databaseMessageForSave);
        } catch (NullPointerException e) {
            throw new HistoryDatabaseException(connectionMessageForSave);
        }
        terminateDBConnection();
    }

    /**
     * Save one URL to the database. Helper function for saveWebsitesToDB.
     * @param urlString The URL Address of the URL to be saved.
     * @throws SQLException Thrown when the history database
     * cannot be reached.
     */
    private void saveWebsiteToDB(String urlString) throws SQLException {
        if (!connStatus) {
            establishDBConnection();
        }
        s.executeUpdate(
                "INSERT INTO url (date, urlString)"
                + " VALUES(NOW(), '" + urlString + "')");
    }

    /**
     * Iterate through a LinkedList containing 1 or more Website objects and
     * supplies those Websites as parameter(s) to the saveURLToDB method.
     * @param websiteLinkedList The list of Websites to be saved.
     * @throws HistoryDatabaseException Thrown when a connection to
     * the history database cannot be established.
     */
    public void saveWebsitesToDB(LinkedList<Website> websiteLinkedList)
            throws HistoryDatabaseException {
        try {
            establishDBConnection();
            // Iterate through a LinkedList<Website>, saving each element
            // to the history database
            System.out.println(websiteLinkedList);
            for (int i = 0; i != websiteLinkedList.size()
                    && conn != null; i++) {
                System.out.println(websiteLinkedList.get(i).getUrlAddress());
                saveWebsiteToDB(websiteLinkedList.get(i).getUrlAddress());
            }
        } catch (SQLException e) {
            throw new HistoryDatabaseException(databaseMessageForSave);
        } catch (NullPointerException e) {
            throw new HistoryDatabaseException(connectionMessageForSave);
        }
        terminateDBConnection();
    }

    /**
     * Return a LinkedList<String> of abstracts if a database connection is
     * possible, and there are abstracts in the database.
     * Return an empty list otherwise.
     * @return All abstracts if any, null otherwise.
     * @throws HistoryDatabaseException Thrown when a connection to
     * the history database cannot be established.
     */
    public LinkedList<String> getAbstractHistoryFromDB()
            throws HistoryDatabaseException {
        LinkedList<String> abstractLinkedList = new LinkedList<String>();

        try {
            if (!connStatus) {
                establishDBConnection();
            }
            ResultSet rs = s.executeQuery(
                    "select id, date, abstractText from "
                    + "csc207_historydbmgr.abstract");
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                String abstractText = rs.getString("abstractText");
                abstractLinkedList.add(abstractText);
                System.out.println(id + "\t" + date + "\t" + abstractText);
            }
        } catch (SQLException e) {
            throw new HistoryDatabaseException(databaseMessage);
        } catch (NullPointerException e) {
            throw new HistoryDatabaseException(connectionMessage);
        }
        terminateDBConnection();
        return abstractLinkedList;
    }

    /**
     * Return a LinkedList of URL Addresses contained in the
     * history database, if connection to database is possible.
     * Return an empty list otherwise.
     * @return All URL Addresses if any, null otherwise.
     * @throws HistoryDatabaseException Thrown when a connection to
     * the history database cannot be established.
     */
    public LinkedList<String> getURLHistoryFromDB()
            throws HistoryDatabaseException {
        LinkedList<String> urlLinkedList = new LinkedList<String>();

        try {
            if (!connStatus) {
                establishDBConnection();
            }
            ResultSet rs = s.executeQuery(
                    "select id, date, urlString from "
                    + "csc207_historydbmgr.url");
            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String urlString = rs.getString("urlString");
                urlLinkedList.add(date.substring(0, date.length() - 2)
                        + " " + urlString);
            }
        } catch (SQLException e) {
            throw new HistoryDatabaseException(databaseMessage);
        } catch (NullPointerException e) {
            throw new HistoryDatabaseException(connectionMessage);
        }
        terminateDBConnection();
        return urlLinkedList;
    }

    /**
     * Establish a Connection with the mySQL database.
     */
    public void establishDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(dburl, dbuserName, dbpassword);
            s = conn.createStatement();
            connStatus = true;
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    /**
     * Clear all URL Addresses and Abstracts from the history database.
     * @throws HistoryDatabaseException Thrown when a connection to
     * the history database cannot be established.
     */
    public void clearDatabaseHistory() throws HistoryDatabaseException {
        try {
            establishDBConnection();
            s.executeUpdate("DROP TABLE IF EXISTS abstract");
            s.executeUpdate("DROP TABLE IF EXISTS url");
            s.executeUpdate(
                    "CREATE TABLE abstract ("
                    + "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "PRIMARY KEY (id),"
                    + "date CHAR(40), abstractText TEXT(4000))");
            s.executeUpdate(
                    "CREATE TABLE url ("
                    + "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "PRIMARY KEY (id),"
                    + "date TIMESTAMP(8), urlString CHAR(255))");
        } catch (SQLException e) {
            throw new HistoryDatabaseException(databaseMessage);
        } catch (NullPointerException e) {
            throw new HistoryDatabaseException(connectionMessage);
        }
    }

    /**
     * Return true iff connected.
     * @return True iff connected.
     */
    public boolean getConnectionStatus() {
        return connStatus;
    }

    /**
     * Terminate the connection with the mySQL database and
     * ignore any close errors.
     */
    public void terminateDBConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                // Ignore close errors
            }
        }
        connStatus = false;
    }

    /**
     * Set up username:password credentials for accessing the history database.
     * @param userNameInput The username to be used.
     * @param passwordInput The password to be used.
     */
    public void setDBCredentials(String userNameInput,
            String passwordInput) {
        dbuserName = userNameInput;
        dbpassword = passwordInput;
    }
}
