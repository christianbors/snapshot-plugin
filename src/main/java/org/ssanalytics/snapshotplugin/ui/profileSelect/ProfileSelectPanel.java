/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.profileSelect;

import java.io.File;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.io.generator.api.GeneratorController;
import org.gephi.project.api.ProjectController;
import org.gephi.ranking.api.RankingController;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.io.dbConnection.timeflow.TimeflowEventExporter;
import org.ssanalytics.snapshotplugin.io.generator.AbstractGenerator;
import org.ssanalytics.snapshotplugin.io.generator.general.AbstractGeneralGenerator;
import org.ssanalytics.snapshotplugin.io.generator.profile.AbstractProfileGenerator;
import org.ssanalytics.snapshotplugin.ui.helper.ComboItemGenerator;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.model.ProfileGeneratorComboBoxModel;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
public class ProfileSelectPanel extends javax.swing.JPanel {

    private GeneratorController genController;
    private ProjectController projectController;
    private RankingController rankingController;
    private AttributeModel attributeModel;

    /**
     * Creates new form ProfileSelectPanel
     */
    public ProfileSelectPanel() {
        genController = Lookup.getDefault().lookup(GeneratorController.class);
        projectController = Lookup.getDefault().lookup(ProjectController.class);
        rankingController = Lookup.getDefault().lookup(RankingController.class);
        attributeModel = Lookup.getDefault().lookup(AttributeModel.class);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbSnapshot = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        profileBoundingPanel = new javax.swing.JPanel();
        profilePanel = new javax.swing.JScrollPane();
        listProfiles = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        cbGen = new javax.swing.JComboBox();
        btnGenerate = new javax.swing.JButton();
        btnRanking = new javax.swing.JButton();
        btnLayout = new javax.swing.JButton();
        panelTimeflowExport = new javax.swing.JPanel();
        cbTimeflowExport = new javax.swing.JComboBox();
        btnExport = new javax.swing.JButton();

        cbSnapshot.setModel(getSnapshotComboBoxModel());
        if(cbSnapshot.getModel().getSize() > 0) {
            cbSnapshot.setSelectedIndex(0);
        }
        cbSnapshot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSnapshotActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.jLabel1.text_1")); // NOI18N

        profileBoundingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.profileBoundingPanel.border.title"))); // NOI18N

        listProfiles.setModel(getProfileListModel());
        profilePanel.setViewportView(listProfiles);

        javax.swing.GroupLayout profileBoundingPanelLayout = new javax.swing.GroupLayout(profileBoundingPanel);
        profileBoundingPanel.setLayout(profileBoundingPanelLayout);
        profileBoundingPanelLayout.setHorizontalGroup(
            profileBoundingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profilePanel)
        );
        profileBoundingPanelLayout.setVerticalGroup(
            profileBoundingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.jLabel2.text")); // NOI18N

        cbGen.setModel(new ProfileGeneratorComboBoxModel());
        cbGen.setSelectedIndex(0);
        cbGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGenActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnGenerate, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.btnGenerate.text")); // NOI18N
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnRanking, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.btnRanking.text")); // NOI18N
        btnRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRankingActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnLayout, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.btnLayout.text")); // NOI18N
        btnLayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayoutActionPerformed(evt);
            }
        });

        panelTimeflowExport.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.panelTimeflowExport.border.title"))); // NOI18N

        cbTimeflowExport.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Events" }));

        org.openide.awt.Mnemonics.setLocalizedText(btnExport, org.openide.util.NbBundle.getMessage(ProfileSelectPanel.class, "ProfileSelectPanel.btnExport.text")); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTimeflowExportLayout = new javax.swing.GroupLayout(panelTimeflowExport);
        panelTimeflowExport.setLayout(panelTimeflowExportLayout);
        panelTimeflowExportLayout.setHorizontalGroup(
            panelTimeflowExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimeflowExportLayout.createSequentialGroup()
                .addComponent(cbTimeflowExport, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExport))
        );
        panelTimeflowExportLayout.setVerticalGroup(
            panelTimeflowExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTimeflowExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbTimeflowExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnExport))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSnapshot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbGen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRanking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(profileBoundingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTimeflowExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbSnapshot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileBoundingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerate)
                    .addComponent(btnRanking)
                    .addComponent(btnLayout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTimeflowExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbSnapshotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSnapshotActionPerformed
        try {
            listProfiles.setModel(getProfileListModel());
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_cbSnapshotActionPerformed

    private void cbGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGenActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        // change eventgenerator to match either profile or snapshot
        if (((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator() instanceof AbstractProfileGenerator) {
            AbstractProfileGenerator gen = (AbstractProfileGenerator) ((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator();
            gen.setProfiles((List<IProfile>) listProfiles.getSelectedValuesList());
            gen.setSnapshot((ISnapshotInfo) cbSnapshot.getSelectedItem());
//            gen.setProfile((List<IProfile>) listProfiles.getSelectedValuesList());

        }
        if (((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator() instanceof AbstractGeneralGenerator) {
            AbstractGeneralGenerator gen = (AbstractGeneralGenerator) ((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator();
            gen.setSnapshot((ISnapshotInfo) cbSnapshot.getSelectedItem());
            gen.setProfileList((List<IProfile>) listProfiles.getSelectedValuesList());

        }
        if (projectController.getCurrentProject() == null) {
            projectController.newProject();
        }
        genController.generate(((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator());
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void btnRankingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRankingActionPerformed
        AbstractSnapshotRankingController snapshotRankingController = ((AbstractGenerator) ((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator()).getGeneratorRanking();
        snapshotRankingController.transform();
    }//GEN-LAST:event_btnRankingActionPerformed

    private void btnLayoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayoutActionPerformed
        AbstractSnapshotLayoutController snapshotLayoutController = ((AbstractGenerator) ((ComboItemGenerator) cbGen.getSelectedItem()).getGenerator()).getGeneratorLayout();
        snapshotLayoutController.execute();
    }//GEN-LAST:event_btnLayoutActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

        if (listProfiles.getSelectedIndex() >= 0) {
            if (cbTimeflowExport.getSelectedItem().toString().equals("Events")) {
                chooser.setSelectedFile(new File("events.timeline"));
                int returnVal = chooser.showSaveDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        String file = chooser.getSelectedFile().toString();
                        String snapshot = ((ISnapshotInfo) cbSnapshot.getSelectedItem()).getValue();
                        String prof = ((IProfile) listProfiles.getSelectedValue()).getId();
                        TimeflowEventExporter.getInstance().exportEventTimelineForProfileAndSnapshotLatestVersion(file, snapshot, prof);
                        JOptionPane.showMessageDialog(btnExport, "Timeflow data file generated successfully");
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Please select a profile to perform this task", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnExportActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnLayout;
    private javax.swing.JButton btnRanking;
    private javax.swing.JComboBox cbGen;
    private javax.swing.JComboBox cbSnapshot;
    private javax.swing.JComboBox cbTimeflowExport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList listProfiles;
    private javax.swing.JPanel panelTimeflowExport;
    private javax.swing.JPanel profileBoundingPanel;
    private javax.swing.JScrollPane profilePanel;
    // End of variables declaration//GEN-END:variables

    private ListModel<IProfile> getProfileListModel() {
        try {
            DefaultListModel<IProfile> defaultListModel = new DefaultListModel<>();

            System.out.println(((ISnapshotInfo) cbSnapshot.getSelectedItem()).getValue());
            for (IProfile info : DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(((ISnapshotInfo) cbSnapshot.getSelectedItem()).getValue())) {
                defaultListModel.addElement(info);
            }
            return defaultListModel;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    private ComboBoxModel<ISnapshotInfo> getSnapshotComboBoxModel() {
        try {
            return new DefaultComboBoxModel(DaoFactory.getSnapshotInfoDAO().getSnapshotInfoList().toArray(new ISnapshotInfo[0]));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
}