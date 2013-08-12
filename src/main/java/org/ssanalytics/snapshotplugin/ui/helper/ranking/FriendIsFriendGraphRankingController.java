/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper.ranking;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.Modularity;
import org.openide.util.Lookup;

/**
 *
 * @author christian
 */
public class FriendIsFriendGraphRankingController extends AbstractSnapshotRankingController {

    @Override
    protected Map<Ranking, Transformer> determineRankingMap() {
        GraphController gc = Lookup.getDefault().lookup(GraphController.class);
        GraphModel model = gc.getModel();

        Map<Ranking, Transformer> rankMap = new HashMap<>();

        Modularity modularity = new Modularity();
        modularity.setRandom(true);
        modularity.setUseWeight(false);
        modularity.setResolution(1.0);
        modularity.execute(gc.getModel(), Lookup.getDefault().lookup(AttributeController.class).getModel());

        Ranking cr = rankingController.getModel().getRanking("nodes", Modularity.MODULARITY_CLASS);
        AbstractColorTransformer ct = (AbstractColorTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
        List<Color> colors = new LinkedList<>();
        colors.add(Color.WHITE);
        Random rand = new Random();
        for (int i = 0; i <= (int) cr.getMaximumValue(); i++) {
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            colors.add(new Color(r, g, b));
        }
        
        Map<Integer, Integer> valuesCount = new TreeMap<>();
        for (Node n : model.getGraph().getNodes()) {
            if (!valuesCount.containsKey((int) cr.getValue(n))) {
                valuesCount.put((int) cr.getValue(n), 1);
            } else {
                valuesCount.put((int) cr.getValue(n), valuesCount.get((int) cr.getValue(n)) + 1);
            }
        }
        
        float[] posArray = new float[valuesCount.size()+1];
        posArray[0] = 0f;
        for(Map.Entry<Integer, Integer> entry : valuesCount.entrySet()) {
            
            float ratio = (float) entry.getValue()/(float) model.getGraph().getNodeCount();
            posArray[entry.getKey()+1] = posArray[entry.getKey()] + ratio;
        }
        posArray[valuesCount.size()] = 1f;
        AbstractColorTransformer.LinearGradient grad = new AbstractColorTransformer.LinearGradient(colors.toArray(new Color[0]), posArray);
        ct.setLinearGradient(grad);

        rankMap.put(cr, ct);

        Ranking r0 = rankingController.getModel().getRanking("nodes", Ranking.DEGREE_RANKING);
        AbstractSizeTransformer t0 = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        t0.setMinSize(5);
        t0.setMaxSize(50);
        rankMap.put(r0, t0);

        return rankMap;
    }
}
