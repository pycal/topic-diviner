package project;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Keeps track of, in order, a list of Websites.
 *
 */
public class WebsiteList extends Observable {

    /** The list of Websites that will be compared with a
     * ParagraphAbstract's Keyword list. */
    private LinkedList<Website> websiteLinkedList;
    /** Keep an identical list that contains only url addresses.
     * This allows for easier searching of websites. */
    private LinkedList<String> addressLinkedList;

    /**
     * Create an empty list of Websites to be manipulated later.
     */
    public WebsiteList() {
        websiteLinkedList = new LinkedList<Website>();
        addressLinkedList = new LinkedList<String>();
    }

    /**
     * Reset websiteLinkedList and addressLinkedList for new search.
     */
    public void reset() {
        websiteLinkedList.clear();
        addressLinkedList.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Create a Website from a urlAddress and adds it to the list of Websites.
     * Returns true if the Website was added.
     * Also stores String addresses for easy access.
     * @param urlAddress The address from which the Website will be created.
     * @return True iff a new Website was added to the list.
     */
    public boolean add(String urlAddress) {
        boolean result = false;
        if (!contains(urlAddress)) {
            Website web = new Website(urlAddress, true, null, null);
            if (web.isValid()) {
                addressLinkedList.addLast(urlAddress);
                websiteLinkedList.addLast(web);
                result = true;
                setChanged();
                notifyObservers();
            }
        }
        return result;
    }

    /**
     * Return true iff urlAddress is already a Website.
     * @param urlAddress the address to be checked if it exists already.
     * @return True iff urlAddress is already a Website.
     */
    private boolean contains(String urlAddress) {
        boolean contains = addressLinkedList.contains(urlAddress);
        if (!contains) {
            String smallAddress = urlAddress;
            if (urlAddress.startsWith("http://")
                    || urlAddress.startsWith("https://")
                    || urlAddress.startsWith("ftp://")
                    || urlAddress.startsWith("file://")
                    || urlAddress.startsWith("jar://")) {
                int index = urlAddress.indexOf("://");
                smallAddress = urlAddress.substring(index + 3);
            }
            if (smallAddress.startsWith("www.")) {
                smallAddress = smallAddress.substring(4);
            }
            if (smallAddress.startsWith("ww.")) {
                smallAddress = smallAddress.substring(3);
            }
            if (smallAddress.startsWith("w.")) {
                smallAddress = smallAddress.substring(2);
            }
            for (String address : addressLinkedList) {
                if (address.endsWith(smallAddress)) {
                    contains = true;
                }
            }
        }
        return contains;
    }

    /**
     * Return true iff WebsiteList contains no Websites.
     * @return True iff WebsiteList contains no Websites.
     */
    public boolean isEmpty() {
        return websiteLinkedList.isEmpty();
    }

    /**
     * Delete Website with the urlAddress from the list of Websites
     * and addresses.
     * @param urlAddress The address of the Website to be deleted.
     */
    public void delete(String urlAddress) {
        if (addressLinkedList.contains(urlAddress)) {
            int index = addressLinkedList.indexOf(urlAddress);
            addressLinkedList.remove(index);
            websiteLinkedList.remove(index);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Return an ordered list of Strings (showing relevancy and the
     * urlAddress) representing the Websites according to their relevancy to
     * the ParagraphAbstract.
     * @param abs The abstract to be compared with the list of Websites.
     * @return An ordered list of Strings representing the Websites.
     */
    public LinkedList<String> compare(ParagraphAbstract abs) {
        LinkedList<String> matchedList = new LinkedList<String>();
        // Make sure there are Websites to check.
        if (!websiteLinkedList.isEmpty()) {
            // Compare the Keywords from the ParagraphAbstract
            // with each Website.
            for (Website web : websiteLinkedList) {
                web.setMatchedKeywordList(abs.getKeywordList(),
                        abs.getTotalPriority());
            }

            // Sort the Website (and address) list based on matched
            // Keyword priorities.
            sort();

            // Turn the Websites and their relevancy into viewable format.
            for (Website web : websiteLinkedList) {
                matchedList.addLast(web.toString());
            }
        }
        return matchedList;
    }

    /**
     * Sort both the list of Websites and addresses from highest relevancy
     * to lowest.
     */
    private void sort() {
        LinkedList<Website> sortedWebsiteList = new LinkedList<Website>();
        LinkedList<String> sortedAddressList = new LinkedList<String>();
        int i = 0;
        // Since websiteLinkedList is not empty, start by adding the
        // first Website.
        sortedWebsiteList.add(websiteLinkedList.pollFirst());
        sortedAddressList.add(addressLinkedList.pollFirst());
        // Creates sortedList by inserting websites according to
        // their priorities until there are no more left to sort.
        while (!websiteLinkedList.isEmpty()) {
            Website web = websiteLinkedList.getFirst();
            String address = addressLinkedList.getFirst();
            if (i >= sortedWebsiteList.size()) {
                sortedWebsiteList.addLast(web);
                websiteLinkedList.removeFirst();
                sortedAddressList.addLast(address);
                addressLinkedList.removeFirst();
                i = 0;
            } else if (web.getTotalPriority()
                    >= sortedWebsiteList.get(i).getTotalPriority()) {
                sortedWebsiteList.add(i, web);
                websiteLinkedList.removeFirst();
                sortedAddressList.add(i, address);
                addressLinkedList.removeFirst();
                i = 0;
            } else {
                i++;
            }
        }
        websiteLinkedList = sortedWebsiteList;
        addressLinkedList = sortedAddressList;
    }

    /**
     * Return the list of Websites.
     * @return The list of Websites.
     */
    public LinkedList<Website> getWebsites() {
        return websiteLinkedList;
    }

    /**
     * Return the list of url addresses.
     * @return The list of url addresses.
     */
    public LinkedList<String> getUrlAddresses() {
        return addressLinkedList;
    }

    /**
     * The String representation of WebsiteList.
     * @return The String representation of WebsiteList.
     */
    @Override
    public String toString() {
        return websiteLinkedList.toString();
    }
}
