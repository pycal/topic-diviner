package project;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *  KeywordPriorityFrame shows keywords to user and helps user to
 *  adjust keyword priority level for their preference.
 */
public class KeywordPriorityFrame extends javax.swing.JFrame implements
        Observer{
    
    /** keywordsModel is DefaultListModel that contains the list of Keywords. */
    private javax.swing.DefaultListModel keywordsModel = new DefaultListModel();
    /** Importance is String array that contians seperator for Keywords by
     * Keywords' priority. */
    static String[] importance = {"--------------- Very High -5- "
                    + "---------------",
                    "--------------- High         -4- "
                    + "---------------", "--------------- Normal "
                    + "    -3- ---------------", "--------------- Low "
                    + "         -2- ----------------", "--------------- "
                    + "Very Low  -1- "+ "---------------"};
    /** The list representation of importance. */
    static private List<String> wordImportance = Arrays.asList(importance);

   /**
    * Create a new KeyPriorityFrame for the user to adjust
    * Keyword priorities.
    */
    public KeywordPriorityFrame() {
        initComponents();
        listKeywords.setModel(keywordsModel);
        dispose();
    }

    /**
     * Return priority level corresponding to the priority level of selected
     * item in combobox.
     * @return The priority level of the selected item in the combobox.
     */
    public int getPriority(){
        int priority = 0;
        switch (cBoxPriorityLevel.getSelectedIndex()) {
            case 0:
                priority = 5;
                break;
            case 1:
                priority = 4;
                break;
            case 2:
                priority = 3;
                break;
            case 3:
                priority = 2;
                break;
            case 4:
                priority =1;
                break;
        }
        return priority;
    }

    /**
     * Add a new Keyword to keywordlist depending on keyword priority level
     * from selected priority level in combobox.
     */
    public void add(){
        String word = textKeyword.getText();
        int priority = getPriority();
        if (!word.equals("")) {
            if (!Main.app.abs.addKeyword(word.toLowerCase(), priority)) {
                JOptionPane.showMessageDialog(this,
                        "This Keyword is already in the list of Keywords.");
            }
            textKeyword.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please input a Keyword.");
        }
    }
    
    /**
     * Change priority of the selected Keyword to the priority level selected
     * in the combobox.
     */
    public void changeKeyPriority(){
        String word = textKeyword.getText();
        int priority = getPriority();
        Main.app.abs.setKeywordPriority(word, priority);        
    }
    
    /**
     * Update keywordsModel as the ParagrpahAbstract's list of Keywords status
     * is updated.
     * @param observable The observed object which has changed.
     * @param args Additional information which may be given by the observed
     * object.
     */
    public void update(Observable observable, Object args) {
        keywordsModel.removeAllElements();
        int highind=2, normalind=3, lowind=4;
        for (int i = 0; i < 5; i++){
            keywordsModel.addElement(wordImportance.get(i));
        }
        for (Keyword keyword : Main.app.abs) {
            switch (keyword.getPriority()) {
                case 5:
                    keywordsModel.add(1, keyword.getWord());
                    highind++;
                    normalind++;
                    lowind++;
                    break;
                case 4:
                    keywordsModel.add(highind, keyword.getWord());
                    normalind++;
                    lowind++;
                    break;
                case 3:
                    keywordsModel.add(normalind, keyword.getWord());
                    lowind++;
                    break;
                case 2:
                    keywordsModel.add(lowind, keyword.getWord());
                    break;
                default:
                    keywordsModel.addElement(keyword.getWord());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAdd = new javax.swing.JButton();
        BtnBack = new javax.swing.JButton();
        lblKeyword = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listKeywords = new javax.swing.JList();
        lblkeyword = new javax.swing.JLabel();
        textKeyword = new javax.swing.JTextField();
        cBoxPriorityLevel = new javax.swing.JComboBox();
        lblprioritylevel = new javax.swing.JLabel();
        BtnSearch = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find Your Professor!");
        setResizable(false);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        BtnBack.setText("Back");
        BtnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBackActionPerformed(evt);
            }
        });

        lblKeyword.setText("Keywords");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        listKeywords.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listKeywords.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listKeywords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listKeywordsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listKeywords);
        listKeywords.getAccessibleContext().setAccessibleName("ListDefaultKeyword");

        lblkeyword.setText("Keyword");

        textKeyword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textKeywordKeyPressed(evt);
            }
        });

        cBoxPriorityLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Very High -5-", "High -4-", "Normal -3-", "Low -2-", "Very Low -1-" }));
        cBoxPriorityLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cBoxPriorityLevelKeyTyped(evt);
            }
        });

        lblprioritylevel.setText("Priority Level");

        BtnSearch.setText("Search");
        BtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSearchActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BtnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKeyword)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblkeyword)
                                    .addComponent(textKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblprioritylevel)
                                    .addComponent(cBoxPriorityLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(178, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(551, Short.MAX_VALUE)
                    .addComponent(BtnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(22, 22, 22)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(lblKeyword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblkeyword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblprioritylevel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cBoxPriorityLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addComponent(BtnBack)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(557, Short.MAX_VALUE)
                    .addComponent(BtnSearch)
                    .addGap(20, 20, 20)))
        );

        btnAdd.getAccessibleContext().setAccessibleName("BtnSearch");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * React to event evt, which is always a back button click.
     * Dispose this frame and show InputFrame.
     * @param evt the event
     */
    private void BtnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBackActionPerformed
        dispose();
        Main.app.inputWindow.setLocation(getLocation());
        Main.app.inputWindow.setVisible(true);
    }//GEN-LAST:event_BtnBackActionPerformed

    /**
     * React to event evt, which is always a delete button click.
     * Delete keyword from keyword list.
     * @param evt the event
     */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int ind = listKeywords.getSelectedIndex();
        if (ind != -1 && !wordImportance.contains(
                (String) listKeywords.getSelectedValue())) {
            Main.app.abs.deleteKeyword(
                    (String) listKeywords.getSelectedValue());
            listKeywords.setSelectedIndex(ind);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * React to event evt, which is always a search button click.
     * Save abstract and url list to history database and search.
     * Then, dispose this frame and show the result by showing output frame.
     * @param evt the event
     */
    private void BtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSearchActionPerformed
        if(Main.app.abs.hasKeywords()){
            setEnabled(false);
            JDialog saveProgressDialog = new JDialog((JFrame)null, "Saving...");
            saveProgressDialog.setEnabled(false);
            saveProgressDialog.setSize(185,20);
            saveProgressDialog.setVisible(true);
            saveProgressDialog.setAlwaysOnTop(true);
            saveProgressDialog.setResizable(false);
            saveProgressDialog.setLocation(getX()+220,getY()+300);
            try {
                Main.app.HDBM.saveAbstractToDB(Main.app.abs.getParagraph());
                Main.app.HDBM.saveWebsitesToDB(Main.app.webList.getWebsites());
            } catch (HistoryDatabaseException e) {
                saveProgressDialog.dispose();
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            saveProgressDialog.dispose();
            toFront();
            JDialog searchProgressDialog = new JDialog((JFrame)null,
                    "Searching...");
            searchProgressDialog.setEnabled(false);
            searchProgressDialog.setSize(185,20);
            searchProgressDialog.setVisible(true);
            searchProgressDialog.setLocation(getX() + 220, getY() + 300);
            searchProgressDialog.setAlwaysOnTop(true);
            searchProgressDialog.setResizable(false);

            if (Main.app.outputWindow == null) {
                Main.app.outputWindow = new OutputFrame();
                searchProgressDialog.dispose();
                setEnabled(true);
                dispose();
                Main.app.outputWindow.setLocation(getLocation());
                Main.app.outputWindow.setVisible(true);
            } else {
                Main.app.outputWindow.updateResult();
                searchProgressDialog.dispose();
                dispose();
                setEnabled(true);
                Main.app.outputWindow.setLocation(getLocation());
                Main.app.outputWindow.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "There is no keywords to find.");
        }
    }//GEN-LAST:event_BtnSearchActionPerformed

    /**
     * React to event evt, which is always a listKeywords mouse click.
     * Change txtKeyword to word that user clicked in the list.
     * Then, change cboxPriorityLevel corresponding to that word's priority and
     * change btnAdd's text to "Change Priority".
     * @param evt the event
     */
    private void listKeywordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listKeywordsMouseClicked
        if (!wordImportance.contains(
                (String) listKeywords.getSelectedValue())) {
            textKeyword.setText((String) listKeywords.getSelectedValue());

            switch(Main.app.abs.getKeywordPriority((String)
                    listKeywords.getSelectedValue())){
                case 5:
                    cBoxPriorityLevel.setSelectedIndex(0);
                    break;
                case 4:
                    cBoxPriorityLevel.setSelectedIndex(1);
                    break;
                case 3:
                    cBoxPriorityLevel.setSelectedIndex(2);
                    break;
                case 2:
                    cBoxPriorityLevel.setSelectedIndex(3);
                    break;
                case 1:
                    cBoxPriorityLevel.setSelectedIndex(4);
                    break;
            }
            btnAdd.setText("Change Priority");
        } else {
            btnAdd.setText("Add");
            textKeyword.setText("");
        }
    }//GEN-LAST:event_listKeywordsMouseClicked

    /**
     * React to event e, which is always a Add or "Change key priority" button
     * click.
     * Add keyword to keyword list if it's "Add" button.
     * Change key priority level if it's "Change key priority" button.
     * @param e the event
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        JButton button = (JButton) evt.getSource();
        if (button.getText().equals("Add")) {
            add();
        } else {
            changeKeyPriority();
        }
}//GEN-LAST:event_btnAddActionPerformed

 
    /**
     * React to event evt, which is always key typed in JComboBox
     * cBoxPriorityLevel.
     * Add keyword to keyword list if it's "Add" button. 
     * Change key priority level if it's "Change key priority" button. 
     * @param evt the event
     */
    private void cBoxPriorityLevelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cBoxPriorityLevelKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnAdd.getText().equals("Add")) {
                add();
            } else {
                changeKeyPriority();
            }
        }
    }//GEN-LAST:event_cBoxPriorityLevelKeyTyped

    /**
     * React to event evt, which is always key typed in jTextField textKeyword.
     * Add keyword to keyword list if it's "Add" button.
     * Change key priority level if it's "Change key priority" button.
     * @param evt the event
     */
    private void textKeywordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textKeywordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(btnAdd.getText().equals("Add")){
                add();
            } else {
                changeKeyPriority();
            }
        } else {
            btnAdd.setText("Add");
        }
    }//GEN-LAST:event_textKeywordKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBack;
    private javax.swing.JButton BtnSearch;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JComboBox cBoxPriorityLevel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblKeyword;
    private javax.swing.JLabel lblkeyword;
    private javax.swing.JLabel lblprioritylevel;
    private javax.swing.JList listKeywords;
    private javax.swing.JTextField textKeyword;
    // End of variables declaration//GEN-END:variables
}