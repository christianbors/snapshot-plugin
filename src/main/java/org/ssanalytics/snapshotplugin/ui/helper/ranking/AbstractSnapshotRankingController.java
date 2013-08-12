/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.ranking;

import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.openide.util.Lookup;

/**
 *
 * @author christian
 */
public abstract class AbstractSnapshotRankingController {

    protected RankingController rankingController;

    public AbstractSnapshotRankingController() {
        rankingController = Lookup.getDefault().lookup(RankingController.class);
    }

    protected abstract Map<Ranking, Transformer> determineRankingMap();

    public void transform() {
        Map<Ranking, Transformer> rankingMap = determineRankingMap();

        if (rankingMap.size() > 0) {
            for (Map.Entry<Ranking, Transformer> ranking : rankingMap.entrySet()) {
                rankingController.transform(ranking.getKey(), ranking.getValue());
            }
        }
    }
}
