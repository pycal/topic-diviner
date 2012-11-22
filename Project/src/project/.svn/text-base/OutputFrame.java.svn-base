package project;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import javax.swing.event.HyperlinkEvent;

/**
 * A Window that shows the results to the user.
 */
public class OutputFrame extends javax.swing.JFrame {
    
    /** The user's desktop information to browse result's hyperlink with
        user's default web browser. */
    Desktop desktop = Desktop.getDesktop();

    /** 
     * Create new form OutputFrame.
     */
    public OutputFrame() {
        initComponents();
        updateResult();
    }

    /**
     * Set the result of the search to textResult.
     */
    public void updateResult(){
        LinkedList<String> result = Main.app.webList.compare(Main.app.abs);
        String res = "";
        for (int i = 0; i < result.size(); i++) {
            res += Main.app.webList.getWebsites().get(i).getPercent() + "%";
            res += ": ";
            res += "<a href =\""+result.get(i)+"\">";
            res += result.get(i) + "</a>";
            res += "<br>Keyword Matches: ";
            LinkedList<Keyword> matchedKeywords = Main.app.webList.
                    getWebsites().get(i).getMatchedKeywordList();
            for (Keyword word : matchedKeywords) {
                res += word.getWord();
                if (!matchedKeywords.getLast().equals(word)) {
                    res += ", ";
                }
            }
            res+="<br>";
        }
        resultEditorPanel.setText(res);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnNewSearch = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        resultScrollPanel = new javax.swing.JScrollPane();
        resultEditorPanel = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find Your Professor!");
        setResizable(false);

        jLabel1.setText("Result");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNewSearch.setText("New Search");
        btnNewSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSearchActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        resultEditorPanel.setContentType("text/html");
        resultEditorPanel.setEditable(false);
        resultEditorPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultEditorPanel.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                resultEditorPanelHyperlinkUpdate(evt);
            }
        });
        resultScrollPanel.setViewportView(resultEditorPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNewSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnNewSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /** 
     *  React to event evt, which is always a back button click.
     *  Dispose this frame and show KeyPriority Frame.
     *  @param evt: the event
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        dispose();
        Main.app.keyPriorityWindow.setLocation(getLocation());
        Main.app.keyPriorityWindow.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed
    /**
     *  React to event evt, which is always a Exit button click.
     *  End the program.
     *  @param evt: the event
     */
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed
    
    /**
     * React to event evt, which is always a back button click.
     * New Search dispose this frame and reset search abstarct and urllist
     * and show InputFrame.
     */
    private void btnNewSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSearchActionPerformed
        dispose();
        Main.app.inputWindow.reset();
        Main.app.inputWindow.setLocation(getLocation());
        Main.app.inputWindow.setVisible(true);
    }//GEN-LAST:event_btnNewSearchActionPerformed
    /**
     * React to hyperlink event evt, which is always user clicked site.
     * Open hyperlinked site in new browser.
     * @param evt
     */
    private void resultEditorPanelHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_resultEditorPanelHyperlinkUpdate
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                // Open the page that user click with user's
                // default web browser.
                desktop.browse(new URI(evt.getURL().toString()));
            } catch (URISyntaxException ex) {
                // Do nothing because this error will not happen since we caught
                // in our addURL function in WebsiteList.
            } catch (IOException ex) {
                // Do nothing because this error will not happen since we caught
                // in our addURL function in WebsiteList.
            }
        }
    }//GEN-LAST:event_resultEditorPanelHyperlinkUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNewSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JEditorPane resultEditorPanel;
    private javax.swing.JScrollPane resultScrollPanel;
    // End of variables declaration//GEN-END:variables

}
