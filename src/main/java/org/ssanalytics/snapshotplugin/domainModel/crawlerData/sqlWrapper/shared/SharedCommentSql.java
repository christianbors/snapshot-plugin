package org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.shared;

import java.util.List;

import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedComments;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedCommentData;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.shared.ISharedPaging;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.superClasses.AbstractBaseDomainSql;

public class SharedCommentSql extends AbstractBaseDomainSql implements ISharedComments {
	
	private ISharedPaging paging;
	private List<ISharedCommentData> commentData;
	private Integer count;
	
	public SharedCommentSql(ISharedPaging paging, List<ISharedCommentData> commentData, Integer count) {
		super();
		this.paging = paging;
		this.commentData = commentData;
		this.count = count;
	}

	public ISharedPaging getPaging() {
		return this.paging;
	}

	public List<ISharedCommentData> getDataList() {
		return this.commentData;
	}

	public Integer getCount() {
		return count;
	}
}
