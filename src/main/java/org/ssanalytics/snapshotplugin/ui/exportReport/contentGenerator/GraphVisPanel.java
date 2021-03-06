/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.exportReport.contentGenerator;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.project.api.WorkspaceInformation;
import org.gephi.project.api.WorkspaceProvider;
import org.openide.util.Lookup;

/**
 *
 * @author christian
 */
public class GraphVisPanel extends javax.swing.JPanel {

    private Workspace[] workspaces;
    private List<JRadioButton> listRButton;
    /**
     * Creates new form GraphVisPanel
     */
    public GraphVisPanel() {
        initComponents();
        listRButton = new ArrayList<>();
        panelWorkspaces.add(new JLabel("Choose Workspace:"));

        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        workspaces = pc.getCurrentProject().getLookup().lookup(WorkspaceProvider.class).getWorkspaces();

        for (Workspace workspace : workspaces) {
            JRadioButton rbtnWorkspace = new JRadioButton(workspace.getLookup().lookup(WorkspaceInformation.class).getSource(), workspace.equals(pc.getCurrentWorkspace()));
            rbtnWorkspace.setActionCommand(workspace.getLookup().lookup(WorkspaceInformation.class).getSource());

            panelWorkspaces.add(rbtnWorkspace);
            listRButton.add(rbtnWorkspace);
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

        header = new org.jdesktop.swingx.JXHeader();
        panelWorkspaces = new javax.swing.JPanel();

        setName("Form"); // NOI18N

        header.setDescription(org.openide.util.NbBundle.getMessage(GraphVisPanel.class, "GraphVisPanel.header.description")); // NOI18N
        header.setName("header"); // NOI18N
        header.setTitle(org.openide.util.NbBundle.getMessage(GraphVisPanel.class, "GraphVisPanel.header.title")); // NOI18N

        panelWorkspaces.setName("panelWorkspaces"); // NOI18N
        panelWorkspaces.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(panelWorkspaces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelWorkspaces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXHeader header;
    private javax.swing.JPanel panelWorkspaces;
    // End of variables declaration//GEN-END:variables
    
    public Workspace[] getSelectedWorkspaces() {
        List<Workspace> listWorkspaces = new ArrayList<>();
        for(JRadioButton btn : listRButton) {
            if(btn.isSelected()) {
                for(Workspace wsp : workspaces) {
                    String name = wsp.getLookup().lookup(WorkspaceInformation.class).getSource();
                    if(btn.getText().equals(name)) {
                        listWorkspaces.add(wsp);
                    }
                }
            }
        }
        return listWorkspaces.toArray(new Workspace[0]);
    }
}
