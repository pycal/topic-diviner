package project;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Observable;
import javax.swing.filechooser.FileFilter;
import java.util.List;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * InputFrame is the first frame shows to user.
 * It allows to user set abstract and add urls which they are going to look for.
 */
public class InputFrame extends javax.swing.JFrame implements Observer {
    
    /** urlListModel is default list model that contains webList's information,
     * will be shown in JList listSites.
     */
    private javax.swing.DefaultListModel urlListModel;
    /** An array of valid protocols. */
    static private String[] procotocols = {"http://", "https://", "ftp://",
    "file://", "jar://"};
    /** The list representation of valid protocols */
    static private List<String> protocolList = Arrays.asList(procotocols); 

    /**
     * Create an empty Input Frame for the user to input information.
     */
    public InputFrame() {
        initComponents();
        urlListModel = new DefaultListModel();
        listSites.setModel(urlListModel);
        Toolkit kit = getToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        Insets in = kit.getScreenInsets(gs[0].getDefaultConfiguration());
        Dimension d = kit.getScreenSize();
        int max_width = (d.width - in.left - in.right);
        int max_height = (d.height - in.top - in.bottom);
        setLocation((int) (max_width - getWidth()) / 2,
                (int) (max_height - getHeight() ) / 2);
        //Set Location to middle of the screen.
    }

    /**
     * Set the Abstract Textbox from user history.
     */
    public void setAbstract(String abs) {
        txtAbstract.setText(abs);
    }

    /**
     * Reset all of the information of urllist and abstract.
     */
   public void reset() {
       Main.app.webList.reset();
       txtAbstract.setText("");
       txtSite.setText("http://");
   }

   /**
    * Add URL to WebsiteList webList from JTextField txtSite.
    */
    public void addURL(){
       boolean protocolStart = false;
       String site = txtSite.getText();
       for (String protocol: protocolList) {
           if (site.startsWith(protocol)) {
               protocolStart = true;
           }
        }
        if (!protocolStart) {
            site = "http://" + site;
        }
        while (site.endsWith("/") && !protocolList.contains(site)) {
            site = site.substring(0,site.length()-1);
        }
        if (!protocolList.contains(site)&& Main.app.webList.add(site)) {
            txtSite.setText("http://");
        } else {
            String message = "The URL Address is not a valid URL or it is " +
                    "already in the URL list.";
            JOptionPane.showMessageDialog(this, message);
        }
    }
   
    /**
     * Import an Abstract from a text file.
     */
    public void abstractFromFile() throws FileNotFoundException, IOException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File(""));

        FileFilter txtfilter = new FileFilter() {

            public boolean accept(File file) {
                //if the file extension is .txt return true, else false
                if (file.getName().toLowerCase().endsWith(".txt") ||
                        file.isDirectory()) {
                    return true;
                }
                return false;
            }
        
            @Override
            public String getDescription() {
                return "Text file (.txt)";
            }
        };
        fc.addChoosableFileFilter((javax.swing.filechooser.FileFilter)
                txtfilter);
        fc.showOpenDialog(null);
        File f = fc.getSelectedFile();
        if (f != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader
                    (new FileInputStream(f)));
            String text , res = "";
            while ((text = in.readLine()) != null) {
                res += text;
                res += "\n";
            }
            txtAbstract.setText(res);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listSites = new javax.swing.JList();
        txtSite = new javax.swing.JTextField();
        btnURLHistory = new javax.swing.JButton();
        btnURLAdd = new javax.swing.JButton();
        lblURLList = new javax.swing.JLabel();
        lblURLAddress = new javax.swing.JLabel();
        lblAbstract = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAbstract = new javax.swing.JTextArea();
        btnURLDelete = new javax.swing.JButton();
        btnAbstractFile = new javax.swing.JButton();
        btnAbstractHistory = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnClearHIstory = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find Your Professor!");
        setResizable(false);

        jScrollPane1.setViewportView(listSites);

        txtSite.setText("http://");
        txtSite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSiteKeyPressed(evt);
            }
        });

        btnURLHistory.setText("From History");
        btnURLHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnURLHistoryActionPerformed(evt);
            }
        });

        btnURLAdd.setText("Add");
        btnURLAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnURLAddActionPerformed(evt);
            }
        });

        lblURLList.setText("URL List");

        lblURLAddress.setText("URL Address");

        lblAbstract.setText("Abstract");

        txtAbstract.setColumns(10);
        txtAbstract.setLineWrap(true);
        txtAbstract.setRows(5);
        jScrollPane2.setViewportView(txtAbstract);

        btnURLDelete.setText("Delete");
        btnURLDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnURLDeleteActionPerformed(evt);
            }
        });

        btnAbstractFile.setText("From File");
        btnAbstractFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbstractFileActionPerformed(evt);
            }
        });

        btnAbstractHistory.setText("From History");
        btnAbstractHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbstractHistoryActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnClearHIstory.setText("Clear All History");
        btnClearHIstory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearHIstoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                            .addComponent(lblURLList)
                            .addComponent(lblURLAddress)
                            .addComponent(lblAbstract)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAbstractFile, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(btnAbstractHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(btnURLAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(btnURLHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(btnURLDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(561, 561, 561)
                        .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(561, 561, 561)
                        .addComponent(btnClearHIstory, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblURLList)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnURLDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnURLHistory))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(lblURLAddress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnURLAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(lblAbstract)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAbstractFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAbstractHistory))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearHIstory)
                .addGap(17, 17, 17))
        );

        txtSite.getAccessibleContext().setAccessibleName("TxtStie");
        btnURLAdd.getAccessibleContext().setAccessibleName("BtnURLAdd");
        btnURLDelete.getAccessibleContext().setAccessibleName("btnDelete");
        btnClearHIstory.getAccessibleContext().setAccessibleName("btnClearHistory");

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * React to keyevent evt, which is always key pressed in jTextField txtSite.
     * If user pressed "Enter", add url to
     * @param evt the event
     */
    @SuppressWarnings("static-access")
    private void txtSiteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSiteKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER){
            addURL();
        }
    }//GEN-LAST:event_txtSiteKeyPressed
    /**
     * React to event evt, which is always add button clicked.
     * Add url in JTextField txtURL to WebsiteList webList.
     * @param evt the event
     */
    private void btnURLAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnURLAddActionPerformed
        addURL();
    }//GEN-LAST:event_btnURLAddActionPerformed
    /**
     * React to event evt, which is always delete button clicked.
     * Delete URL that selected item from URLListModel in WebsiteList webList.
     * @param evt the event
     */
    private void btnURLDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnURLDeleteActionPerformed
        int ind=listSites.getSelectedIndex();
        if (ind != -1) {
            Main.app.webList.delete((String) listSites.getSelectedValue());
        }
    }//GEN-LAST:event_btnURLDeleteActionPerformed
    /**
     * React to event evt, which is always from file button clicked.
     * Import abstract from text file to JTextArea txtAbstract.
     * @param evt the event
     */
    private void btnAbstractFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbstractFileActionPerformed
        try {
            abstractFromFile();
        } catch (FileNotFoundException ex) {
            txtAbstract.setText("Text file not found.");
        } catch (IOException ex) {
            txtAbstract.setText("Text file not found.");
        }
    }//GEN-LAST:event_btnAbstractFileActionPerformed

    /**
     * React to event evt, which is always next button clicked.
     * Dispose this frame and goes to next frame which is key priority frame.
     * @param evt the event
     */
    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        //It will have also set the URL list and Abstarct
        if (Main.app.webList.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Please add "
                    + "at least one URL Address.");
        } else if (txtAbstract.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please input an Abstract.");
        } else {
            Main.app.abs.setParagraphInfo(txtAbstract.getText());
            if(!Main.app.abs.hasKeywords()) {
                JOptionPane.showMessageDialog(this,"Please input "
                + "valid abstract.");
            } else {
                dispose();
                Main.app.keyPriorityWindow.setLocation(getLocation());
                Main.app.keyPriorityWindow.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnNextActionPerformed

    /**
     * React to event evt, which is always url from history button clicked.
     * Disable this Frame and show history frame for user to select urls from
     * history database if it's connected successfully.
     * @param evt the event
     */
    private void btnURLHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnURLHistoryActionPerformed
       if(Main.app.historyWindow == null) {
           Main.app.historyWindow = new HistoryFrame();
               try {
                   Main.app.historyWindow.setURLHistory();
                   Main.app.historyWindow.setLocation(getX() + 70, getY() + 70);
                   setEnabled(false);
                   Main.app.historyWindow.setVisible(true);
               } catch (HistoryDatabaseException ex) {
                   Main.app.historyWindow.dispose();
                   JOptionPane.showMessageDialog(this,ex.getMessage());
               }
       } else {
           Main.app.historyWindow.setLocation(getX() + 70, getY() + 70);
           try {
               Main.app.historyWindow.setURLHistory();
               setEnabled(false);
               Main.app.historyWindow.setVisible(true);
           } catch (HistoryDatabaseException ex) {
               Main.app.historyWindow.dispose();
               JOptionPane.showMessageDialog(this,ex.getMessage());
           }
       }
    }//GEN-LAST:event_btnURLHistoryActionPerformed

    /**
     * React to event evt, which is always abstract from history button clicked.
     * Disable this Frame and show history frame for user to select abstract
     * from history database if it's connected successfully.
     * @param evt the event
     */
    private void btnAbstractHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbstractHistoryActionPerformed
        if(Main.app.historyWindow == null) {
            Main.app.historyWindow = new HistoryFrame();
            try {
                Main.app.historyWindow.setAbstractHistory();
                Main.app.historyWindow.setLocation(this.getX()+70,
                        this.getY()+70);
                this.setEnabled(false);
                Main.app.historyWindow.setVisible(true);
            } catch (HistoryDatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            try {
                Main.app.historyWindow.setAbstractHistory();
                Main.app.historyWindow.setLocation(this.getX()+70,
                           this.getY()+70);
                this.setEnabled(false);
                Main.app.historyWindow.setVisible(true);
            } catch (HistoryDatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnAbstractHistoryActionPerformed

    /**
     * React to event evt, which is always btnClearHistory Button.
     * Reset All the database. 
     * @param evt the event
     */
    private void btnClearHIstoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearHIstoryActionPerformed
        try {
            Main.app.HDBM.clearDatabaseHistory();
            JOptionPane.showMessageDialog(this,
                    "The Search History has been cleared.");
        } catch (HistoryDatabaseException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }//GEN-LAST:event_btnClearHIstoryActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbstractFile;
    private javax.swing.JButton btnAbstractHistory;
    private javax.swing.JButton btnClearHIstory;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnURLAdd;
    private javax.swing.JButton btnURLDelete;
    private javax.swing.JButton btnURLHistory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAbstract;
    private javax.swing.JLabel lblURLAddress;
    private javax.swing.JLabel lblURLList;
    private javax.swing.JList listSites;
    private javax.swing.JTextArea txtAbstract;
    private javax.swing.JTextField txtSite;
    // End of variables declaration//GEN-END:variables

    /**
     * Update the urlListModel as the WebsiteList status is updated.
     * @param observable The observed object which has changed.
     * @param args Additional information which may be given by the observed
     * object.
     */
    public void update(Observable observable, Object args) {
       urlListModel.clear();
       for(String URLAddress: Main.app.webList.getUrlAddresses()){
           urlListModel.addElement(URLAddress);
       }
    }
}