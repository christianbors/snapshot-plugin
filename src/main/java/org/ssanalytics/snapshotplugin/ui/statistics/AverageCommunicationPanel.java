/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.statistics;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import org.openide.util.Exceptions;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;

/**
 *
 * @author christian
 */
public class AverageCommunicationPanel extends javax.swing.JPanel {

    private DefaultComboBoxModel<ISnapshotInfo> defaultListModel;

    /**
     * Creates new form AverageCommunicationPanel
     */
    public AverageCommunicationPanel(ISnapshotInfo snapshot) {
        initComponents();
        if (snapshot != null) {
            for (int i = 0; i < defaultListModel.getSize(); ++i) {
                if(defaultListModel.getElementAt(i).getRoot().equals(snapshot.getRoot()))
                    cbSnapshot.setSelectedIndex(i);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        header = new org.jdesktop.swingx.JXHeader();
        cbSnapshot = new javax.swing.JComboBox();

        setName("Form"); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AverageCommunicationPanel.class, "AverageCommunicationPanel.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        header.setDescription(org.openide.util.NbBundle.getMessage(AverageCommunicationPanel.class, "AverageCommunicationPanel.header.description")); // NOI18N
        header.setName("header"); // NOI18N
        header.setTitle(org.openide.util.NbBundle.getMessage(AverageCommunicationPanel.class, "AverageCommunicationPanel.header.title")); // NOI18N

        cbSnapshot.setModel((ComboBoxModel) getSnapshotListModel());
        cbSnapshot.setName("cbSnapshot"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSnapshot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbSnapshot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbSnapshot;
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private ComboBoxModel<ISnapshotInfo> getSnapshotListModel() {
        try {
            defaultListModel = new DefaultComboBoxModel<>();

            for (ISnapshotInfo info : DaoFactory.getSnapshotInfoDAO().getSnapshotInfoList()) {
                defaultListModel.addElement(info);
            }
            return defaultListModel;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    public ISnapshotInfo getSelectedSnapshot() {
        return (ISnapshotInfo) cbSnapshot.getSelectedItem();
    }

    public void setSelectedSnapshot(ISnapshotInfo snapshot) {
        cbSnapshot.setSelectedItem(snapshot);
    }
}
