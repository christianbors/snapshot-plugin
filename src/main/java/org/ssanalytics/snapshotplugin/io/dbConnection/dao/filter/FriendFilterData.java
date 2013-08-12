package org.ssanalytics.snapshotplugin.io.dbConnection.dao.filter;

import java.util.List;

public class FriendFilterData {

    private Boolean genderIsMale;
    private String relationship;
    private List<String> interests;
    private Boolean interestSearchOR;

    /**
     *
     * @param genderIsMale True = Male, false = female
     * @param relationship
     * @param interests A list of interests, can be AND or OR connected (defined
     * in param friendSearchOR)
     * @param interestSearchOR defines the way the interest search criteria is
     * connected (can be OR or AND)
     */
    public FriendFilterData(Boolean genderIsMale, String relationship, List<String> interests, Boolean interestSearchOR) {
        this.genderIsMale = genderIsMale;
        this.relationship = relationship;
        this.interests = interests;
    }

    public Boolean getGender() {
        return this.genderIsMale;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public List<String> getInterests() {
        return this.interests;
    }

    public Boolean getInterestSearchOR() {
        return this.interestSearchOR;
    }
}
