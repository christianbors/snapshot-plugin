/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.io.generator.spi.Generator;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.ICategorizedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;

/**
 *
 * @author christian
 */
@ServiceProviders(value={
@ServiceProvider(service = Generator.class),
@ServiceProvider(service = ProfileGenerator.class)})
public class SameInterestsGenerator extends AbstractSameCategoryGenerator implements ProfileGenerator{

    public static String NAME = "Who has The Same Interests Graph";

    @Override
    protected Map<ICategorizedDomain, List<NamedItem>> getCategoryMap() {
        try {
            Map<ICategorizedDomain, List<NamedItem>> catMap = new HashMap<>();
            for (IProfile profile : this.profiles) {
                catMap.putAll(DaoFactory.getInterestDAO().getWhoLikesTheSameInterestsAsProfileForSnapshotLatestVersion(snapshot.getValue(), profile.getId()));
            }
            return catMap;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
