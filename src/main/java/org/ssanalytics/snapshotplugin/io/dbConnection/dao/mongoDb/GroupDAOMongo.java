/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.mongoDb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IGroupDAO;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.group.IGroup;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.superInterfaces.INamedDomain;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.account.RootAccountJsonMongo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.jsonMongoWrapper.group.GroupJsonMongo;

/**
 *
 * @author chw
 */
public class GroupDAOMongo extends AbstractSuperDAOMongo implements IGroupDAO {

    private final String collection_name = "groups";

    private GroupDAOMongo() throws UnknownHostException, MongoException {
        super();
    }
    private static GroupDAOMongo instance = null;

    public static GroupDAOMongo getInstance() throws UnknownHostException, MongoException {
        if (instance == null) {
            instance = new GroupDAOMongo();
        }
        return instance;
    }

    @Override
    public Map<IGroup, List<NamedItem>> getWhoIsInTheSameGroup(String snapshotName, String profileId) throws MongoException, UnknownHostException {

        List<IGroup> groupProfileList = new LinkedList<>();
        Map<IGroup, List<NamedItem>> map = new HashMap<>();

        BasicDBObject params = new BasicDBObject();
        params.append("snapshot", snapshotName);
        params.append("accountId", profileId);

        DBCursor dbc = this.findInCollection(this.collection_name, params);

        RootAccountJsonMongo racson = new RootAccountJsonMongo();
        BasicDBList bdl = new BasicDBList();
        bdl.addAll(dbc.toArray());
        racson.setGroupList(bdl);
        groupProfileList = racson.getGroupList();

        List<NamedItem> accounts;
        for (IGroup g : groupProfileList) {

            params = new BasicDBObject();
            params.append("snapshot", snapshotName);
            params.append("data.id", g.getId());

            dbc = this.findInCollection(collection_name, params);
            accounts = new LinkedList<>();
            while (dbc.hasNext()) {
                String accId = new GroupJsonMongo(dbc.next()).getAccountId();
                if (accId.equals(profileId)) {
                    continue;
                }
                NamedItem ni = new NamedItem(accId, ProfileDAOMongo.getInstance().getNameForAccountId(accId));
                if (!accounts.contains(ni)) {
                    accounts.add(ni);
                }
            }

            if (accounts.size() > 0) {
                map.put(g, accounts);
            }
        }

        return map;

    }
}
