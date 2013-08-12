package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.tagged;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedAction;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedTo;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITagged;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedApplication;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.tagged.ITaggedProperties;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class TaggedSql extends AbstractTimedDomainSql implements ITagged {

    private List<ISharedAction> actions;
    private ITaggedApplication application;
    private String caption;
    private ISharedComments comments;
    private ISharedFrom from;
    private String icon;
    private String link;
    private String picture;
    private ISharedTo to;
    private String type;
    private Long updated_time;
    private String description;
    private String message;
    private List<ITaggedProperties> propertyList;

    public TaggedSql(String id, String name, Long created_time, List<ISharedAction> actions, ITaggedApplication application, String caption, ISharedComments comments, ISharedFrom from, String icon, String link, String picture, ISharedTo to, String type, Long updated_time, String description, String message, List<ITaggedProperties> propertyList) {
        super(id, name, created_time);

        this.actions = actions;
        this.application = application;
        this.caption = caption;
        this.comments = comments;
        this.from = from;
        this.icon = icon;
        this.link = link;
        this.picture = picture;
        this.to = to;
        this.type = type;
        this.updated_time = updated_time;
        this.description = description;
        this.message = message;
        this.propertyList = propertyList;
    }

    public List<ISharedAction> getActions() {
        return actions;
    }

    public ITaggedApplication getApplication() {
        return application;
    }

    public String getCaption() {
        return caption;
    }

    public ISharedComments getComments() {
        return comments;
    }

    public ISharedFrom getFrom() {
        return from;
    }

    public String getIcon() {
        return icon;
    }

    public String getLink() {
        return link;
    }

    public String getPicture() {
        return picture;
    }

    public ISharedTo getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public Long getUpdated_time() {
        return updated_time;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public List<ITaggedProperties> getProperties() {
        return this.propertyList;
    }
}
