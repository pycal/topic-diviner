package project;

import java.util.LinkedList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The current state; one of 2.  We use an enum ("enumerated type") to define
 * the states.
 * ABSTRACTHISTORY: This frame will display the history of Abstracts.
 * URLHISTORY: This frame will display the history of URL Addresses.
 */
enum State{
    ABSTRACTHISTORY,
    URLHISTORY
}
/**
 * A window that displays peviously searched URL Addresses and Abstracts.
 */
public class HistoryFrame extends javax.swing.JFrame {
    
    /** The state this frame is in. */
    private State frameState;
    /** A model that contains listhistory's information. */
    private javax.swing.DefaultListModel historyModel =
            new javax.swing.DefaultListModel();
    
    /** Create a new form HistoryFrame window. */
    public HistoryFrame() {
        initComponents();
        listhistory.setModel(historyModel);
        dispose();
    }

    /**
     * Set historyModel by information from the Abstract history database.
     * listHistory selection mode is single selection.
     * frame state is now ABSTRACTHISTORY.
     */
    public void setAbstractHistory() throws HistoryDatabaseException {
        historyModel.clear();
        lblHistory.setText("Abstract History");
        lblInstruction.setText("*You can only select one abstract.");
        listhistory.setSelectionMode(javax.swing.ListSelectionModel.
                SINGLE_SELECTION);
        frameState = State.ABSTRACTHISTORY;
        LinkedList<String> historyAbstract = new LinkedList<String>();
        historyAbstract = Main.app.HDBM.getAbstractHistoryFromDB();

        if (!historyAbstract.isEmpty()) {
            for (int i = 0; i<historyAbstract.size();i++) {
             historyModel.addElement(historyAbstract.get(i));
            }
        }
    }

    /**
     * Set historyModel by information from the URL Address history database.
     * listHistory selection mode is multiple interval selection.
     * frame state is now URLHISTORY.
     */
    public void setURLHistory() throws HistoryDatabaseException {
        historyModel.clear();
        lblHistory.setText("URL History");
        lblInstruction.setText("*You can select one or more URL histories by"
                + " Shift key or Ctrl key.");
        listhistory.setSelectionMode(javax.swing.ListSelectionModel.
                MULTIPLE_INTERVAL_SELECTION);
         frameState = State.URLHISTORY;
         LinkedList<String> historyURL = new LinkedList<String>();
         historyURL = Main.app.HDBM.getURLHistoryFromDB();
         
         if(!historyURL.isEmpty()) {
             for (String url : historyURL) {
                 historyModel.addElement(url);
             }
         }
         Main.app.historyWindow.setVisible(true);
    }
    
    
//Generated Code
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listhistory = new javax.swing.JList();
        lblHistory = new javax.swing.JLabel();
        lblInstruction = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("History");
        setResizable(false);

        listhistory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listhistory);

        btnSelect.setText("Select");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInstruction)
                            .addComponent(lblHistory)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(btnSelect)
                        .addGap(66, 66, 66)
                        .addComponent(btnCancel)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInstruction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnCancel))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * React to event evt, which is always a Cancel button click.
     * Dispose this frame and goes back to Inputframe.
     * @param evt the event
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
        Main.app.inputWindow.setEnabled(true);
        Main.app.inputWindow.toFront();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * React to event evt, which is always a Cancel button click.
     * Import selected items from history to WebsiteList or set abstract text in
     * input frame depending on frameState. Then, dispose of this frame once
     * selection is done and go to inputframe.
     * @param evt the event
     */
    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        if (listhistory.getSelectedIndex() != -1) {
            switch(frameState) {
                case URLHISTORY:
                    setEnabled(false);
                    JDialog importProgressDialog = new JDialog((JFrame)null,
                            "Importing...");
                    importProgressDialog.setEnabled(false);
                    importProgressDialog.setSize(175,20);
                    importProgressDialog.setVisible(true);
                    importProgressDialog.setLocation(getX()+180,getY()+200);
                    importProgressDialog.setAlwaysOnTop(true);
                    int[] selectedIndicies = listhistory.getSelectedIndices();
                    for (int i=0; i<selectedIndicies.length; i++) {
                        String urladdress = (String) listhistory.getModel().
                                getElementAt(selectedIndicies[i]).toString().
                                substring(20);
                        Main.app.webList.add(urladdress);
                    }
                    importProgressDialog.dispose();
                    setEnabled(true);
                    break;
                case ABSTRACTHISTORY:
                    Main.app.inputWindow.setAbstract
                           ((String) listhistory.getSelectedValue());
                    break;
            }
            dispose();
            Main.app.inputWindow.setEnabled(true);
            Main.app.inputWindow.toFront();
        } else {
            switch(frameState) {
                case URLHISTORY:
                    JOptionPane.showMessageDialog(this,
                            "Select at least one url site from history.");
                    break;
                    case ABSTRACTHISTORY:
                    JOptionPane.showMessageDialog(this,
                            "Select Abstract from history.");
                    break;
            }
        }
    }//GEN-LAST:event_btnSelectActionPerformed

     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSelect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblInstruction;
    private javax.swing.JList listhistory;
    // End of variables declaration//GEN-END:variables
}