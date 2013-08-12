/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi;

import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

/**
 *
 * @author christian
 */
public interface ReportContent {
    
    public void generate(ISnapshotInfo snapshot);
    
    public Content[] getContent();

}
