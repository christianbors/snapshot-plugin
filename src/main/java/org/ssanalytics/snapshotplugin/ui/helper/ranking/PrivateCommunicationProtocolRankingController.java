/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.ranking;

import java.util.HashMap;
import java.util.Map;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.Transformer;

/**
 *
 * @author christian
 */
public class PrivateCommunicationProtocolRankingController extends AbstractSnapshotRankingController {

    @Override
    protected Map<Ranking, Transformer> determineRankingMap() {
        return new HashMap<>();
    }
    
}
