package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.contract.IProfileDAO;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationClass;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationConcentration;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationSchool;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileEducationYear;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileHometown;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLanguage;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileLocation;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWork;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfileWorkEmployer;
import org.ssanalytics.snapshotplugin.domainModel.daoReturnValues.NamedItem;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileEducationClassSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileEducationConcentrationSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileEducationSchoolSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileEducationSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileEducationYearSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileHomeTownSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileLanguageSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileLocationSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileWorkEmployerSql;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.sqlWrapper.profile.ProfileWorkSql;

public class ProfileDAOSql extends AbstractSuperDAOSql implements IProfileDAO {

    private static ProfileDAOSql instance = null;

    protected ProfileDAOSql() throws SQLException {
        super();
        this.table_name = "profile";
    }

    public static ProfileDAOSql getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProfileDAOSql();
        }
        return instance;
    }

    @Override
    public List<IProfile> getProfileListForSnapshotLatestVersion(String snapshotInfoName) throws Exception {
        return this.getProfileListForSnapshotSpecificVersion(snapshotInfoName, this.getHighestVersion(snapshotInfoName));
    }

    @Override
    public List<IProfile> getProfileListForSnapshotSpecificVersion(String snapshotInfoName, String version) throws Exception {

        PreparedStatement pst = this.prepareStatement("SELECT id, account_id, fb_id, birthday, firstname, lastname, name, gender, link, locale, username, updated_time FROM " + this.table_name + " WHERE account_id IN (SELECT id from account WHERE snapshotinfo_id = (SELECT id FROM snapshotinfo where snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, snapshotInfoName, pst);
        this.setStringOrNull(2, version, pst);

        ResultSet rs = pst.executeQuery();

        List<IProfile> profileList = new LinkedList<>();

        while (rs.next()) {

            Long profile_id = rs.getLong("id");
            String fb_id = rs.getString("fb_id");
            String birthday = rs.getString("birthday");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String link = rs.getString("link");
            String locale = rs.getString("locale");
            String username = rs.getString("username");
            Long updated_time = rs.getLong("updated_time");

            profileList.add(this.fetchProfile(profile_id, fb_id, birthday, firstName, lastName, name, gender, link, locale, username, updated_time));
        }

        return profileList;
    }

    private IProfile fetchProfile(long profile_id, String fb_id, String birthday, String firstName, String lastName, String name, String gender, String link, String locale, String username, Long updated_time) throws SQLException {



        //fetch location
        PreparedStatement pst = this.prepareStatement("SELECT fb_id, name FROM profile_location WHERE profile_id = ?");
        this.setLongOrNull(1, profile_id, pst);

        IProfileLocation location = null;

        ResultSet locationSet = pst.executeQuery();
        if (locationSet.next()) {
            location = new ProfileLocationSql(locationSet.getString("fb_id"), locationSet.getString("name"));
        }


        //fetch hometown
        pst = this.prepareStatement("SELECT fb_id, name FROM profile_hometown WHERE profile_id = ?");
        this.setLongOrNull(1, profile_id, pst);

        IProfileHometown hometown = null;

        ResultSet hometownSet = pst.executeQuery();
        if (hometownSet.next()) {
            hometown = new ProfileHomeTownSql(hometownSet.getString("fb_id"), hometownSet.getString("name"));
        }



        //fetch languageList
        pst = this.prepareStatement("SELECT fb_id, name FROM profile_language WHERE profile_id = ?");
        this.setLongOrNull(1, profile_id, pst);

        ResultSet languageSet = pst.executeQuery();

        List<IProfileLanguage> languageList = new LinkedList<>();

        while (languageSet.next()) {
            languageList.add(new ProfileLanguageSql(languageSet.getString("fb_id"), languageSet.getString("name")));
        }

        //fetch workList
        pst = this.prepareStatement("SELECT start_date, end_date, employer_fb_id, employer_name FROM profile_work WHERE profile_id = ?");
        this.setLongOrNull(1, profile_id, pst);

        ResultSet workSet = pst.executeQuery();
        List<IProfileWork> workList = new LinkedList<>();

        while (workSet.next()) {

            IProfileWorkEmployer employer = new ProfileWorkEmployerSql(workSet.getString("employer_fb_id"), workSet.getString("employer_name"));
            workList.add(new ProfileWorkSql(employer, workSet.getString("start_date"), workSet.getString("end_date")));
        }

        //fetch education
        pst = this.prepareStatement("SELECT id, education_type FROM profile_education WHERE profile_id = ? ");
        this.setLongOrNull(1, profile_id, pst);
        ResultSet educationSet = pst.executeQuery();

        List<IProfileEducation> educationList = new LinkedList<>();

        while (educationSet.next()) {
            String type = educationSet.getString("education_type");
            Long education_id = educationSet.getLong("id");


            //fetch year
            pst = this.prepareStatement("SELECT fb_id, name FROM profile_education_year WHERE education_id = ?");
            this.setLongOrNull(1, education_id, pst);

            ResultSet tempRs = pst.executeQuery();

            ProfileEducationYearSql year = null;

            if (tempRs.next()) {
                year = new ProfileEducationYearSql(tempRs.getString("fb_id"), tempRs.getString("name"));
            }

            //fetch school
            pst = this.prepareStatement("SELECT fb_id, name FROM profile_education_school WHERE education_id = ?");
            this.setLongOrNull(1, education_id, pst);

            tempRs = pst.executeQuery();

            ProfileEducationSchoolSql school = null;

            if (tempRs.next()) {
                school = new ProfileEducationSchoolSql(tempRs.getString("fb_id"), tempRs.getString("name"));
            }

            //fetch classList
            pst = this.prepareStatement("SELECT fb_id, name FROM profile_education_class WHERE education_id = ?");
            this.setLongOrNull(1, education_id, pst);

            tempRs = pst.executeQuery();

            List<IProfileEducationClass> classList = new LinkedList<IProfileEducationClass>();

            while (tempRs.next()) {
                classList.add(new ProfileEducationClassSql(tempRs.getString("fb_id"), tempRs.getString("name")));
            }


            //fetch concentrationList
            pst = this.prepareStatement("SELECT fb_id, name FROM profile_education_concentration WHERE education_id = ?");
            this.setLongOrNull(1, education_id, pst);

            tempRs = pst.executeQuery();

            List<IProfileEducationConcentration> concentrationList = new LinkedList<IProfileEducationConcentration>();

            while (tempRs.next()) {
                concentrationList.add(new ProfileEducationConcentrationSql(tempRs.getString("fb_id"), tempRs.getString("name")));
            }

            educationList.add(new ProfileEducationSql(type, year, school, classList, concentrationList));

        }

        return new ProfileSql(fb_id, birthday, firstName, lastName, name, gender, link, locale, username, updated_time, location, hometown, languageList, workList, educationList);
    }

    public void saveProfile(IProfile profile, long accountId) throws SQLException {

        long profile_id = IdProvider.getInstance().getNextId();


        String stmt = "INSERT INTO " + this.table_name + " (id, account_id, birthday, firstname, lastname, name, gender, link, locale, username, updated_time, fb_id)";
        stmt = stmt + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = this.prepareStatement(stmt);

        this.setLongOrNull(1, profile_id, pst);
        this.setLongOrNull(2, accountId, pst);
        this.setStringOrNull(3, profile.getBirthday(), pst);
        this.setStringOrNull(4, profile.getFirstName(), pst);
        this.setStringOrNull(5, profile.getLastName(), pst);
        this.setStringOrNull(6, profile.getName(), pst);
        this.setStringOrNull(7, profile.getGender(), pst);
        this.setStringOrNull(8, profile.getLink(), pst);
        this.setStringOrNull(9, profile.getLocale(), pst);
        this.setStringOrNull(10, profile.getUsername(), pst);
        this.setLongOrNull(11, profile.getUpdatedTime(), pst);
        this.setStringOrNull(12, profile.getId(), pst);

        pst.execute();


        IProfileLocation loc = profile.getLocation();
        if (loc != null) {
            pst = this.prepareStatement("INSERT INTO profile_location (id, profile_id, name, fb_id) VALUES(?, ?, ?, ?)");

            pst.setLong(1, IdProvider.getInstance().getNextId());
            this.setLongOrNull(2, profile_id, pst);
            this.setStringOrNull(3, loc.getName(), pst);
            this.setStringOrNull(4, loc.getId(), pst);

            pst.execute();
        }


        IProfileHometown home = profile.getHometown();
        if (home != null) {
            pst = this.prepareStatement("INSERT INTO profile_hometown (id, profile_id, name, fb_id) VALUES(?, ?, ?, ?)");

            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
            this.setLongOrNull(2, profile_id, pst);
            this.setStringOrNull(3, profile.getHometown().getName(), pst);
            this.setStringOrNull(4, profile.getHometown().getId(), pst);

            pst.execute();
        }


        //Profile work
        List<IProfileWork> workList = profile.getWorkList();
        if (workList != null) {
            for (IProfileWork work : workList) {
                if (work != null) {
                    IProfileWorkEmployer employer = work.getEmployer();
                    if (employer != null) {
                        pst = this.prepareStatement("INSERT INTO profile_work (id, profile_id, employer_name, employer_fb_id) VALUES (?, ?, ?, ?)");
                        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                        this.setLongOrNull(2, profile_id, pst);
                        this.setStringOrNull(3, employer.getName(), pst);
                        this.setStringOrNull(4, employer.getId(), pst);

                        pst.execute();
                    }
                }
            }
        }

        //Profile langauges
        List<IProfileLanguage> langList = profile.getLanguageList();
        if (langList != null) {
            for (IProfileLanguage lang : langList) {
                if (lang != null) {
                    pst = this.prepareStatement("INSERT INTO profile_language (id, profile_id, name, fb_id) VALUES (?, ?, ?, ?)");

                    this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                    this.setLongOrNull(2, profile_id, pst);
                    this.setStringOrNull(3, lang.getName(), pst);
                    this.setStringOrNull(4, lang.getId(), pst);

                    pst.execute();
                }
            }
        }

        //Profile education

        List<IProfileEducation> eduList = profile.getEducationList();
        if (eduList != null) {
            for (IProfileEducation edu : eduList) {
                if (edu != null) {
                    long educationId = IdProvider.getInstance().getNextId();
                    pst = this.prepareStatement("INSERT INTO profile_education (id, profile_id, education_type) VALUES (?, ?, ?)");
                    this.setLongOrNull(1, educationId, pst);
                    this.setLongOrNull(2, profile_id, pst);
                    this.setStringOrNull(3, edu.getType(), pst);

                    pst.execute();

                    IProfileEducationYear eduYear = edu.getYear();

                    if (eduYear != null) {
                        pst = this.prepareStatement("INSERT INTO profile_education_year (id, education_id, name, fb_id) VALUES (?, ?, ?, ?)");

                        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                        this.setLongOrNull(2, educationId, pst);
                        this.setStringOrNull(3, eduYear.getName(), pst);
                        this.setStringOrNull(4, eduYear.getId(), pst);

                        pst.execute();
                    }

                    IProfileEducationSchool eduSchool = edu.getSchool();

                    if (eduSchool != null) {
                        pst = this.prepareStatement("INSERT INTO profile_education_school (id, education_id, name, fb_id) VALUES (?, ?, ?, ?)");

                        this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                        this.setLongOrNull(2, educationId, pst);
                        this.setStringOrNull(3, eduSchool.getName(), pst);
                        this.setStringOrNull(4, eduSchool.getId(), pst);

                        pst.execute();
                    }

                    List<IProfileEducationConcentration> eduConcentrationList = edu.getConcentrationList();

                    if (eduConcentrationList != null) {
                        for (IProfileEducationConcentration eduConcentration : eduConcentrationList) {
                            pst = this.prepareStatement("INSERT INTO profile_education_concentration (id, education_id, name, fb_id) VALUES (?, ?, ?, ?)");

                            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                            this.setLongOrNull(2, educationId, pst);
                            this.setStringOrNull(3, eduConcentration.getName(), pst);
                            this.setStringOrNull(4, eduConcentration.getId(), pst);

                            pst.execute();
                        }
                    }


                    List<IProfileEducationClass> eduClassList = edu.getClasses();

                    if (eduClassList != null) {
                        for (IProfileEducationClass eduClass : eduClassList) {
                            pst = this.prepareStatement("INSERT INTO profile_education_class (id, education_id, name, fb_id) VALUES (?, ?, ?, ?)");

                            this.setLongOrNull(1, IdProvider.getInstance().getNextId(), pst);
                            this.setLongOrNull(2, educationId, pst);
                            this.setStringOrNull(3, eduClass.getName(), pst);
                            this.setStringOrNull(4, eduClass.getId(), pst);

                            pst.execute();
                        }
                    }
                }
            }
        }
    }

    @Override
    public IProfile getProfileForAccountIdInSnapshotLatestVersion(String accountId, String snapshotInfoName) throws SQLException {
        return this.getProfileForAccountIdInSnapshotSpecificVersion(accountId, snapshotInfoName, this.getHighestVersion(snapshotInfoName));
    }

    @Override
    public IProfile getProfileForAccountIdInSnapshotSpecificVersion(String accountId, String snapshotInfoName, String version) throws SQLException {

        PreparedStatement pst = this.prepareStatement("SELECT id, account_id, fb_id, birthday, firstname, lastname, name, gender, link, locale, username, updated_time FROM " + this.table_name + " WHERE account_id IN (SELECT id from account WHERE fb_id = ? AND snapshotinfo_id = (SELECT id FROM snapshotinfo where snapshotname = ? AND version = ?))");
        this.setStringOrNull(1, accountId, pst);
        this.setStringOrNull(2, snapshotInfoName, pst);
        this.setStringOrNull(3, version, pst);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            Long profile_id = rs.getLong("id");
            String fb_id = rs.getString("fb_id");
            String birthday = rs.getString("birthday");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String link = rs.getString("link");
            String locale = rs.getString("locale");
            String username = rs.getString("username");
            Long updated_time = rs.getLong("updated_time");

            return this.fetchProfile(profile_id, fb_id, birthday, firstName, lastName, name, gender, link, locale, username, updated_time);
        } else {
            return null;
        }

    }

    public NamedItem getNameItemForAccountId(long accountDbId) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT fb_id, name FROM " + this.table_name + " WHERE account_id = ?");
        this.setLongOrNull(1, accountDbId, pst);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new NamedItem(rs.getString("fb_id"), rs.getString("name"));
        }
        return null;
    }

    @Override
    public String getNameForAccountId(String accountId) throws SQLException {
        PreparedStatement pst = this.prepareStatement("SELECT name FROM " + this.table_name + " WHERE fb_id = ?");
        if (accountId == null || accountId.equals("")) {
            return "";
        }
        this.setStringOrNull(1, accountId, pst);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getString("name");
        }
        return "";
    }
}
