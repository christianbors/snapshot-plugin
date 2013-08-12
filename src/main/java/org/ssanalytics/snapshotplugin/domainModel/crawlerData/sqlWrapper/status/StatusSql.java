package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.status;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedFrom;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedLike;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPlace;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.status.IStatus;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractIdentityDomainSql;

public class StatusSql extends AbstractIdentityDomainSql implements IStatus{

	

	private ISharedComments comments;
	private ISharedFrom from;
	private ISharedLike likes;
	private String message;
	private Long updated_time;
        private ISharedPlace place;
	
	public StatusSql(String id, ISharedComments comments, ISharedFrom from, ISharedLike likes, String message, Long updated_time, ISharedPlace place) {
		super(id);

		this.comments = comments;
		this.from = from;
		this.likes = likes;
		this.message = message;
		this.updated_time = updated_time;
                this.place = place;
	}

	public ISharedComments getComments() {
		return comments;
	}

	public ISharedFrom getFrom() {
		return from;
	}

	public ISharedLike getLikes() {
		return likes;
	}

	public String getMessage() {
		return message;
	}

	public Long getUpdated_time() {
		return updated_time;
	}

    @Override
    public ISharedPlace getPlace() {
        return this.place;
    }

}
