/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator;

import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.utils.progress.ProgressTicket;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;

/**
 *
 * @author christian
 */
public abstract class AbstractGenerator implements Generator {

    protected ISnapshotInfo snapshot = null;
    private boolean cancel = false;
    private ProgressTicket progress;

    public abstract AbstractSnapshotRankingController getGeneratorRanking();
    
    public abstract AbstractSnapshotLayoutController getGeneratorLayout();
    
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

    public boolean hasSnapshot() {
        return this.snapshot != null;
    }
}
