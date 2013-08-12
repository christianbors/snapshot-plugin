/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.ranking.api.RankingController;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.ui.generator.profile.PhotoGraphUI;
import org.ssanalytics.snapshotplugin.ui.helper.layout.AbstractSnapshotLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.layout.FriendIsFriendLayoutController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.AbstractSnapshotRankingController;
import org.ssanalytics.snapshotplugin.ui.helper.ranking.FriendIsFriendGraphRankingController;

/**
 *
 * @author christian
 */
public abstract class AbstractPhotoInfoGenerator extends AbstractProfileGenerator {

    protected AttributeModel model;
    protected Map<String, List<String>> friendsMap;
    protected boolean showFriendsOnly = false;

    @Override
    public void generate(ContainerLoader container) {
        GeneratorUI ui = Lookup.getDefault().lookup(PhotoGraphUI.class);
        model = container.getAttributeModel();
        addAttributeColumns();
        if (hasSnapshot() && hasProfiles()) {
            Map<IProfile, List<IPhoto>> photoList = new HashMap<>();
            try {
                for (IProfile profile : this.profiles) {
                    photoList.put(profile, DaoFactory.getPhotoDAO().getPhotoListForProfileInSnapshotLatestVersion(this.snapshot.getValue(), profile.getId()));
                }
                friendsMap = DaoFactory.getRootAccountDAO().getFriendIsFriend(snapshot.getValue(), snapshot.version());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            long startTime = System.currentTimeMillis();
            if (photoList.size() > 0) {
                addGraphElements(container, photoList);
            }
            System.out.println(System.currentTimeMillis() - startTime);
        }

    }

    @Override
    public GeneratorUI getUI() {
        return Lookup.getDefault().lookup(PhotoGraphUI.class);
    }

    public void showFriendsOnly(boolean showFriendsOnly) {
        this.showFriendsOnly = showFriendsOnly;
    }

    @Override
    public AbstractSnapshotRankingController getGeneratorRanking() {
        return new FriendIsFriendGraphRankingController();
    }

    @Override
    public AbstractSnapshotLayoutController getGeneratorLayout() {
        return new FriendIsFriendLayoutController();
    }

    protected abstract void addAttributeColumns();

    protected abstract void addGraphElements(ContainerLoader container, Map<IProfile, List<IPhoto>> photoMap);
}
