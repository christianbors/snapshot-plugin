/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.generator;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

/**
 *
 * @author christian
 */
@ServiceProvider(service = Generator.class)
public class TestGenerator implements Generator {

    protected ProgressTicket progress;
    protected boolean cancel = false;
    protected ISnapshotInfo snapshot = null;

    @Override
    public void generate(ContainerLoader container) {

        AttributeModel attModel = container.getAttributeModel();//Lookup.getDefault().lookup(AttributeController.class).newModel();
        attModel.getNodeTable().addColumn("attending", AttributeType.INT);
        attModel.getNodeTable().addColumn("declined", AttributeType.INT);
        attModel.getNodeTable().addColumn("unsure", AttributeType.INT);
        attModel.getNodeTable().addColumn("sum", AttributeType.INT);
        for(AttributeColumn col : attModel.getNodeTable().getColumns()) {
            System.out.println(col.getId());
        }
        
        if (snapshot != null) {
            for (String f : snapshot.getFriends()) {
                NodeDraft n1 = container.factory().newNodeDraft();
                n1.setLabel(f);

                n1.addAttributeValue(attModel.getNodeTable().getColumn("attending"), 3);
                n1.addAttributeValue(attModel.getNodeTable().getColumn("declined"), 0);
                n1.addAttributeValue(attModel.getNodeTable().getColumn("unsure"), 0);
                n1.addAttributeValue(attModel.getNodeTable().getColumn("sum"), 0);
                container.addNode(n1);
                
//                n1.addAttributeValue(new AttributeColumnImpl(), cancel);
            }
        }
    }

    @Override
    public String getName() {
        return "TestGraph";
    }

    @Override
    public GeneratorUI getUI() {
        return null;
    }

    @Override
    public boolean cancel() {
        this.cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }

    public void setSnapshot(ISnapshotInfo snapshot) {
        this.snapshot = snapshot;
    }

    public ISnapshotInfo getSnapshot() {
        return this.snapshot;
    }
}
