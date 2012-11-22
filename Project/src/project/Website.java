package project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlcleaner.*;

/**
 * Keeps track of a URL and its contained Keywords.
 * 
 */
public class Website implements Runnable {
    
    /** The URL address of the Website. */
    private String urlAddress;
    /** The list of URL addresses that are under the Website's path. */
    private LinkedList<String> subUrlAddressList = new LinkedList<String>();
    /** A set of all URL addresses visited under the Website's path. */
    private HashSet<String> visitedUrlAddresses = new HashSet<String>();
    /** The URL object of the Website. */
    private URL webUrl;
    /** isValid is true iff the Website is exists and is parsable. */
    private boolean isValid;
    /** The visible text on the Website's webpage. */
    private String urlText;
    /** The Keywords the Website has in common with the ParagraphAbstract. */
    private LinkedList<Keyword> matchedKeywordList;
    /** The Keywords (represented as strings) left to be matched. */
    private HashSet<String> wordsLeft = new HashSet<String>();
    /** The priority of the Website. */
    private int totalPriority = 0;
    /**  The percentage that Keywords in a Website match up to the Keywords
     * in a given abstract. */
    private double relevancy = 0;
    /** isOriginal is true iff the Website was given by the user, not by
     * finding it as a sub link. */
    private boolean isOriginal;
    /** The TagNode (tree-like) representation of the Website. */
    private TagNode rootNode;
    /** The Object needed to process the TagNode. */
    private HtmlCleaner cleaner;
    /** A list of invalid urlAddress extension (such as .exe, .mp4, etc). */
    private HashSet<String> invalidExtensions = new HashSet<String>();

    /**
     * Create a Website that, if valid, keeps track of its url and visible
     * text contained in it's HTML source. Also keep track of links that are
     * under the Website's path.
     * @param url The url from which Website will be created.
     * @param isFirst True iff the Website was given by the user, not by
     * finding it as a sub link.
     * @param visitedAddresses A set containing all of the urls visited under
     * the Original Website's path. This parameter will be null when called
     * from outside this class.
     * @param keywordsLeft The Keywords in a given abstract left to be searched.
     */
    public Website(String url, boolean isFirst,
            HashSet<String> visitedAddresses, HashSet<String> keywordsLeft) {
        // Must be done to make the correct URL later.
        if (!url.endsWith("/") && url.lastIndexOf(".") < url.lastIndexOf("/")) {
            url += "/";
        }
        urlAddress = url;
        isOriginal = isFirst;
        setInvalidExtensions();

        // Set the rootNode and cleaner of this Website.
        setRootNodeUsingThreads();

        if (isValid) {
            try {
                urlText = getUrlBodyText(rootNode, cleaner);
            } catch (NullPointerException e) {
                // If the last step does not produce text, then the Website is
                // not valid.
                isValid = !(urlText == null);
            }

            // Set visited urls and remaining Keywords to check only what is
            // needed.
            if (!isOriginal) {
                visitedUrlAddresses = visitedAddresses;
                wordsLeft = keywordsLeft;
            }
            // Store all valid links under the Website's path.
            setSubUrlInfo(rootNode);
        }
    }

    /**
     * Try to set the rootNode and cleaner of this Website. isValid is
     * true iff this can be done.
     * Threading is used so that if link takes too long to be processed (such
     * as a link to a movie file), it is skipped and the program does not stall.
     * Method based off of: http://stackoverflow.com/questions/2496128/
     *     java-stopping-a-thread-that-has-run-for-too-long
     */
    @SuppressWarnings("static-access")
    private void setRootNodeUsingThreads() {
        Thread urlCleanerThread = new Thread(this);
        Long startTime = new Long(System.currentTimeMillis());
        Long maxWaitTime = new Long(7000);
        // Calls the run() method and runs that CONCURRENTLY with this method.
        urlCleanerThread.start();

        boolean runThread = true;
        while (runThread) {
            // Stop running waiting for thread if it is done.
            if (!urlCleanerThread.isAlive()) {
                runThread = false;
            }
            Long currentTime = new Long(System.currentTimeMillis());
            // If thread is not done in enough time, the url is not valid.
            if ((currentTime - startTime) >= maxWaitTime) {
                urlCleanerThread.yield();
                runThread = false;
                isValid = false;
            }
        }
    }

    /**
     * Parse the urlAddress and try to set rootNode and cleaner.
     * Mandatory method inherited from Runnable that runs a separate Thread.
     */
    public void run() {
        setRootNode(urlAddress);
    }

    /**
     * Set the TagNode representation of a urladdress and the HtmlCleaner
     * needed to process the TagNode. This method calls createRootNode() which
     * does the actual processing.
     * @param urlAddress The url address of the Website to be processed.
     */
    private void setRootNode(String urlAddress) {
        Object[] processedHtml = null;
        // Try to create URL and make a TagNode out of it.
        try {
            processedHtml = createRootNode(urlAddress);
            isValid = true;
        } catch (MalformedURLException ex) {
            // If the URL does not exist, the Website is not valid.
            isValid = false;
        } catch (IOException ex) {
            // Thrown when URL exists but is not parsable (can be password
            // protected for example), so the Website is not valid.
            isValid = false;
        }

        if (processedHtml != null) {
            rootNode = (TagNode) processedHtml[0];
            cleaner = (HtmlCleaner) processedHtml[1];
        }
    }

    /**
     * Return the TagNode representation of the Website and the HtmlCleaner
     * object needed to process the TagNode, as a tuple in Object[] format.
     * @param urladdress The url address of the Website to be processed.
     * @return The TagNode of the Website and the HtmlCleaner needed as a tuple.
     * @throws MalformedURLException Thrown when urladdress does not represent
     * a valid Website.
     * @throws IOException Thrown when the Website can not be read (such as,
     * it may be password protected).
     */
    private Object[] createRootNode(String urladdress)
            throws MalformedURLException, IOException {
        // Used HTMLCleaner to parse URL and take out relevant text.
        // This is one of cleaners mentioned in the link about url parsers
        // that is found on the project handout. It can also be used to easily
        // look at tags (i.e. looking in anchor tags to find links in our
        // case).
        // HTMLCleaner website:
        //         http://htmlcleaner.sourceforge.net/javause.php

        // Create an instance of HtmlCleaner
        HtmlCleaner htmlCleaner = new HtmlCleaner();

        // Take default cleaner properties
        CleanerProperties props = htmlCleaner.getProperties();

        // Omit tags that are not needed and that may present problems.
        props.setOmitComments(true);
        props.setOmitDoctypeDeclaration(true);

        // Create URL object from given address. MalformedURLException is
        // is thrown if urladdress is invalid.
        URL url = new URL(urladdress);
        // This will be used later when checking subUrlAddresses.
        webUrl = url;

        // Create TagNode object. This object is the root of a tree-like 
        // representation of the given URL. In this way, we can easily skip
        // to any html tag we want and get the html text stored inside.
        TagNode root = htmlCleaner.clean(url);

        Object[] processedHTML = {root, htmlCleaner};
        return processedHTML;
    }

    /**
     * Return the usable HTML source text stored between <body> and </body>
     * tags.
     * @param rootNode The TagNode representation of the Website which will
     * have its body text read.
     * @param cleaner Used to clean and extract information from a TagNode.
     * @return The useable HTML source text in the body tags of the website.
     */
    private String getUrlBodyText(TagNode rootNode, HtmlCleaner cleaner) {
        // Find the <body> tag.
        TagNode bodyNode = rootNode.findElementByName("body", true);

        // Get the text from indside the <body> and </body> tags.
        String bodyText = cleaner.getInnerHtml(bodyNode);

        // Make the text in the URL usable for our purposes.
        return removeHtmlTags(bodyText);
    }

    /**
     * Return the text with all HTML tags removed.
     * @param bodyText The text that will have any HTML tags removed.
     * @return The text with all HTML tags removed.
     */
    private String removeHtmlTags(String bodyText) {
        // A regex pattern that matches any tag ie. "<" followed by
        // anything followed by ">".
        Pattern p = Pattern.compile("<\\s*?[^>]+\\s*?>");

        // Match all occurences of html tags in the website's body text.
        Matcher m = p.matcher(bodyText);

        // Delete all occurences of html tags. A space was used so that
        // words on either side of a tag are kept separate.
        String cleanedBodyText = m.replaceAll(" ");

        // Turn the text to be returned into lowercase because the
        // Keywords we use are also in lowercase.
        String lowercaseCleanedBodyText = cleanedBodyText.toLowerCase();

        // Return the text as lowercase because the Keywords we use are
        // also in lowercase.
        return lowercaseCleanedBodyText;
    }

    /**
     * Set all the links that are under the Website's path as both a list of
     * String addresses and list of URL objects.
     * @param rootNode The TagNode representation of the Website which will
     * be checked for links under its path.
     */
    private void setSubUrlInfo(TagNode rootNode) {
        // Get all anchor tags.
        TagNode[] anchorNodes = rootNode.getElementsByName("a", true);

        for (TagNode node : anchorNodes) {
            for (Object attribute : node.getAttributes().keySet()) {
                String strAttr = (String) attribute;
                // Look through all attributes, and if it an "href" attribute,
                // store its link if it is under the parent directory. Note
                // that HTMLCleaner takes care of upper/lowercase "href" and
                // different formats of the link (written w/ or w/o quotes).
                if (strAttr.contentEquals("href")) {
                    Object link = node.getAttributes().get(attribute);
                    String strLink = (String) link;
                    while (strLink.startsWith("/")) {
                        strLink = strLink.substring(1, strLink.length());
                    }
                    try {
                        URL subUrl = new URL(webUrl, strLink);
                        // Check if Website is a parent of subUrl.
                        if (isParent(subUrl)) {
                            // Check if subUrl's extension is valid.
                            boolean validExtension = true;
                            for (String ext : invalidExtensions) {
                                if (strLink.endsWith(ext)) {
                                    validExtension = false;
                                }
                            }
                            // Add subUrl if it is valid and is not present.
                            if (validExtension && 
                                    !subUrlAddressList.contains(
                                    subUrl.toString())) {
                                subUrlAddressList.add(subUrl.toString());
                            }
                        }
                    } catch (MalformedURLException ex) {
                        // If URL is not valid, don't add it.
                    }
                }
            }
        }
    }

    /**
     * Return true iff the subUrl under the Website's path.
     * @param subUrl The URL to be check if it is in the Website's path.
     * @return True iff the subUrl under the Website's path.
     */
    private boolean isParent(URL subUrl) {
        boolean isParent = false;
        String originalDomainName = webUrl.getAuthority();
        String subDomainName = subUrl.getAuthority();
        if (subDomainName == null) {
            return false;
        }
        // Compare domain names. Obviously, different ones are not added.
        if (originalDomainName.contentEquals(subDomainName)) {
            String originalPath = webUrl.getPath();
            String subPath = subUrl.getPath();
            // Check if subUrl is a child of the original parent path.
            isParent = subPath.startsWith(originalPath);
            if (!isParent) {
                // We must also take care of the case when the original url is
                // not a parent directory (i.e. it ends with .html or something
                // similar.
                int slashIndex = originalPath.lastIndexOf("/");
                int dotIndex = subPath.lastIndexOf(".");
                if (slashIndex > -1 && dotIndex > -1 && dotIndex > slashIndex) {
                    String originalParent = originalPath.substring(0,
                            slashIndex);
                    isParent = subPath.startsWith(originalParent);
                }
            }
        }
        return isParent;
    }

    /**
     * Return the URL address of the Website.
     * @return The URL address of the Website.
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     * Return true iff the Website is valid.
     * @return True iff the Website is valid.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Return the list of Keywords matched in this Website.
     * @return The list of Keywords matched in this Website.
     */
    public LinkedList<Keyword> getMatchedKeywordList() {
        return matchedKeywordList;
    }

    /**
     * Find the Keywords that occur in the Website's text (and sub links)
     * and stores them in a LinkedList. The Website's total priority is also
     * set from the priority of the Keywords.
     * @param keywordList The Keywords to be compared in the Website's text.
     * @param absTotalPriority The total priority of the keywordList.
     */
    public void setMatchedKeywordList(
            LinkedList<Keyword> keywordList, int absTotalPriority) {
        if (isOriginal) {
            for (Keyword keyword : keywordList) {
                wordsLeft.add(keyword.getWord());
            }
            // Print user given URL being processed and show user that
            // subpages are being processed.
            System.out.println("URL: " + urlAddress);
            System.out.print("Processing Subpages: ");
        } else {
            System.out.print(".");
        }
        LinkedList<Keyword> commonKeywordList = findKeywords(keywordList);
        visitedUrlAddresses.add(urlAddress);

        // Once Keyword searching is done in the current Website, remaining
        // Keywords will be checked in sub urls and the current Website will
        // be informed of new Keywords found.
        searchSubWebsites(keywordList, commonKeywordList, absTotalPriority);

        matchedKeywordList = commonKeywordList;
        setRelevancy(absTotalPriority);
        if (isOriginal) {
            System.out.println();
            visitedUrlAddresses.clear();
        }
    }

    /**
     * Return the list of Keywords in keywordList that occur in the Website.
     * @param keywordList The list of Keywords to be checked for.
     * @return The list of Keywords in keywordList that occur in the Website.
     */
    private LinkedList<Keyword> findKeywords(LinkedList<Keyword> keywordList) {
        LinkedList<Keyword> commonKeywordList = new LinkedList<Keyword>();
        totalPriority = 0;
        for (Keyword keyword : keywordList) {
            if (wordsLeft.contains(keyword.getWord())
                    && urlText.contains(keyword.getWord())) {
                commonKeywordList.add(keyword);
                wordsLeft.remove(keyword.getWord());
                totalPriority += keyword.getPriority();
            }
        }
        return commonKeywordList;
    }

    /**
     * Append Keywords found in subUrls to the list of found Keywords.
     * @param keywordList The list of Keywords to be checked for.
     * @param commonKeywordList The list of Keywords already found.
     * @param absTotalPriority The total priority of the keywordList.
     */
    private void searchSubWebsites(LinkedList<Keyword> keywordList,
            LinkedList<Keyword> commonKeywordList, int absTotalPriority) {
        // Note this method and setMatchedKeywordList recursively call each
        // other, so one prepares sublinks for searching, and the other finds
        // occurrences of Keywords in the prepared links.
        for (String subUrl : subUrlAddressList) {
            if (!wordsLeft.isEmpty()
                    && !visitedUrlAddresses.contains(subUrl)
                    && !visitedUrlAddresses.contains(subUrl + "/")) {
                Website subWeb = new Website(subUrl, false,
                        visitedUrlAddresses, wordsLeft);
                if (subWeb.isValid()) {
                    subWeb.setMatchedKeywordList(keywordList,
                            absTotalPriority);
                    for (Keyword keyword : subWeb.getMatchedKeywordList()) {
                        commonKeywordList.add(keyword);
                    }
                    totalPriority += subWeb.getTotalPriority();
                }
            }
        }
    }

    /**
     * Set the relevancy of the Website, given a maximum priority possible
     * @param absTotalPriority The maximum priority the Website could achieve.
     */
    private void setRelevancy(int maxPriority) {
        relevancy = Double.valueOf(totalPriority)
                / Double.valueOf(maxPriority) * 100;
        relevancy = Double.parseDouble(String.format("%.2f", relevancy));
    }

    /**
     * Return the priority of the Website.
     * @return The priority of the Website.
     */
    public int getTotalPriority() {
        return totalPriority;
    }

    /**
     * Return the percentage of Keywords match to abstract's Keywords.
     * @return The percentage of Keywords match to abstract's Keywords.
     */
    public double getPercent() {
        return relevancy;
    }

    /**
     * Return the String representation of Website.
     * The format is "urlAddress".
     * @return The String representation of Website.
     */
    @Override
    public String toString() {
        return String.valueOf(urlAddress);
    }

    /**
     * Set a set of extensions. If a urlAddress ends with any of these
     * extensions, it will be considered invalid.
     */
    private void setInvalidExtensions() {
        addMultipleElements(invalidExtensions, ".app", ".avi", ".exe", ".fa",
                ".gz", ".m4v", ".mat", ".mov", ".mp3", ".mp4", ".mpeg", ".mpg",
                ".pdf", ".ppt", ".pptx", ".ps", ".rar", ".swf", ".tar", ".tgz",
                ".wmv", ".zip");
    }

    /**
     * Add multiple Strings to a set of Strings.
     * @param stringSet The set of Strings.
     * @param extensions A list of Strings whose elements will be added
     * to stringSet.
     */
    private void addMultipleElements(HashSet<String> stringSet,
            String... extensions) {
        stringSet.addAll(Arrays.asList(extensions));
    }
}
