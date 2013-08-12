/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.ranking;

import java.util.LinkedHashMap;
import java.util.Map;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;

/**
 *
 * @author christian
 */
public class CategorizedEvidenceRankingController extends AbstractSnapshotRankingController {

    @Override
    protected Map<Ranking, Transformer> determineRankingMap() {
        Map<Ranking, Transformer> rankMap = new LinkedHashMap<>();

        Ranking rankLikes = rankingController.getModel().getRanking("nodes", "likes");
        Ranking rankCategories = rankingController.getModel().getRanking("nodes", Ranking.OUTDEGREE_RANKING);
        Ranking rankLabelLikes = rankingController.getModel().getRanking("nodes", "likes");
        AbstractSizeTransformer nodeSizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        AbstractSizeTransformer labelSizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.LABEL_SIZE);
        nodeSizeTransformer.setMinSize(10);
        nodeSizeTransformer.setMaxSize(50);
        labelSizeTransformer.setMinSize(8);
        labelSizeTransformer.setMaxSize(32);
        rankMap.put(rankCategories, nodeSizeTransformer);
        rankMap.put(rankLikes, nodeSizeTransformer);
        rankMap.put(rankLabelLikes, labelSizeTransformer);

        return rankMap;
    }
}
