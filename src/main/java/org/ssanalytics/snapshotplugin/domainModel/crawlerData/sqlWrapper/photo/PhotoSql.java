package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.photo;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhoto;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoImage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.photo.IPhotoTag;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractTimedDomainSql;

public class PhotoSql extends AbstractTimedDomainSql implements IPhoto {

    private String link;
    private Long updated_time;
    private Integer height;
    private Integer width;
    private Integer position;
    private String icon;
    private String picture;
    private String source;
    private List<IPhotoImage> imageList;
    private ISharedFrom from;
    private ISharedLike likes;
    private IPhotoTag tags;
    private ISharedPlace place;
    private ISharedComments comments;

    public PhotoSql(String id, String name, Long created_time, String link, Long updated_time, Integer height, Integer width, Integer position, String icon, String picture, String source, List<IPhotoImage> imageList, ISharedFrom from, ISharedLike likes, IPhotoTag tags, ISharedPlace place, ISharedComments comments) {
        super(id, name, created_time);

        this.comments = comments;
        this.link = link;
        this.updated_time = updated_time;
        this.height = height;
        this.width = width;
        this.position = position;
        this.icon = icon;
        this.picture = picture;
        this.source = source;
        this.imageList = imageList;
        this.from = from;
        this.likes = likes;
        this.tags = tags;
        this.place = place;
    }

    public String getLink() {
        return link;
    }

    public Long getUpdated_time() {
        return updated_time;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getPosition() {
        return position;
    }

    public String getIcon() {
        return icon;
    }

    public String getPicture() {
        return picture;
    }

    public String getSource() {
        return source;
    }

    public List<IPhotoImage> getImageList() {
        return imageList;
    }

    public ISharedFrom getFrom() {
        return from;
    }

    public ISharedLike getLikes() {
        return likes;
    }

    @Override
    public IPhotoTag getPhotoTags() {
        return this.tags;
    }

    @Override
    public ISharedPlace getPlace() {
        return this.place;
    }

    @Override
    public ISharedComments getComments() {
        return this.comments;
    }
}
