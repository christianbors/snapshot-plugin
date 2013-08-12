DROP SEQUENCE ssdb_sequence;

CREATE SEQUENCE ssdb_sequence;


DROP TABLE snapshotinfo;
DROP TABLE snapshotinfo_friends;
DROP TABLE friendisfriend;
DROP TABLE friendisfriend_friend;
DROP TABLE account;
DROP TABLE friends;
DROP TABLE profile;
DROP TABLE profile_location;
DROP TABLE profile_hometown;
DROP TABLE profile_work;
DROP TABLE profile_language;
DROP TABLE profile_education;
DROP TABLE profile_education_year;
DROP TABLE profile_education_school;
DROP TABLE profile_education_concentration;
DROP TABLE profile_education_class;
DROP TABLE activity;
DROP TABLE book;
DROP TABLE event;
DROP TABLE feed;
DROP TABLE fb_group;
DROP TABLE home;
DROP TABLE inbox;
DROP TABLE interest;
DROP TABLE fb_like;
DROP TABLE link;
DROP TABLE movie;
DROP TABLE music;
DROP TABLE note;
DROP TABLE outbox;
DROP TABLE photo;
DROP TABLE photo_tag;
DROP TABLE photo_tag_data;
DROP TABLE photo_image;
DROP TABLE post;
DROP TABLE post_application;
DROP TABLE status;
DROP TABLE tagged;
DROP TABLE tagged_application;
DROP TABLE tagged_properties;
DROP TABLE television;
DROP TABLE video;
DROP TABLE video_format;
DROP TABLE video_tag;
DROP TABLE video_tag_data;

DROP TABLE shared_action;
DROP TABLE shared_paging;
DROP TABLE shared_from;
DROP TABLE shared_to;
DROP TABLE shared_comments;
DROP TABLE shared_comment_data;
DROP TABLE shared_like;
DROP TABLE shared_like_data;
DROP TABLE shared_privacy;
DROP TABLE shared_place;
DROP TABLE shared_location;

DROP TABLE expanded_archive;
DROP TABLE ea_previous_name;
DROP TABLE ea_address_book_contact_info;
DROP TABLE ea_address_book_entry;



CREATE TABLE snapshotinfo (id bigint, snapshotname varchar(255), timestamp bigint, root varchar(50), root_db_id bigint, version varchar(50));

CREATE TABLE snapshotinfo_friends (id bigint, snapshotinfo_id bigint, friend_fb_id varchar(50));

CREATE TABLE friendisfriend (id bigint, snapshotinfo_id bigint, friend_fb_id varchar(50));
CREATE TABLE friendisfriend_friend (id bigint, friendisfriend_id bigint, friend_fb_id varchar(50));

CREATE TABLE account (id bigint, fb_id varchar(50), snapshotinfo_id bigint);

CREATE TABLE friends (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255));

CREATE TABLE profile (id bigint, account_id bigint, fb_id varchar(50), birthday varchar(50), firstname varchar(255), lastname varchar(255), name varchar(255), gender varchar(50), link varchar(1000), locale varchar(50), username varchar(255), updated_time bigint);

CREATE TABLE profile_location (id bigint, profile_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_hometown (id bigint, profile_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_work (id bigint, profile_id bigint, start_date varchar(50), end_date varchar(50), employer_name varchar(255), employer_fb_id varchar(50));

CREATE TABLE profile_language (id bigint, profile_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_education (id bigint, profile_id bigint, education_type varchar(255));

CREATE TABLE profile_education_year (id bigint, education_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_education_school (id bigint, education_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_education_concentration (id bigint, education_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE profile_education_class (id bigint, education_id bigint, name varchar(255), fb_id varchar(50));

CREATE TABLE activity (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), category varchar(255), created_time bigint);

CREATE TABLE book (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), category varchar(255), created_time bigint);

CREATE TABLE event (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), start_time bigint, end_time bigint, timezone varchar(255), location varchar(255), rsvp_status varchar(255));

CREATE TABLE feed (id bigint, account_id bigint, fb_id varchar(255), created_time bigint, message varchar(1000), feed_type varchar(255), updated_time bigint);

CREATE TABLE fb_group (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), version bigint);

CREATE TABLE home (id bigint, account_id bigint, fb_id varchar(50), created_time bigint, message varchar, fb_type varchar, updated_time bigint);

CREATE TABLE inbox (id bigint, account_id bigint, fb_id varchar(50), unseen int, unread int, updated_time bigint, message varchar);

CREATE TABLE interest (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), category varchar(255), created_time bigint);

CREATE TABLE fb_like (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar, category varchar, created_time bigint);

CREATE TABLE link (id bigint, account_id bigint, icon varchar, link varchar,  picture varchar, description varchar, message varchar, fb_id varchar(255), fb_name varchar, created_time bigint);

CREATE TABLE movie (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), category varchar(255), created_time bigint);

CREATE TABLE music (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar(255), category varchar(255), created_time bigint);

CREATE TABLE note (id bigint, account_id bigint, created_time bigint, updated_time bigint, icon varchar, subject varchar, message varchar, fb_id varchar(255));

CREATE TABLE outbox (id bigint, account_id bigint, message varchar, unseen int, unread int, updated_time bigint);

CREATE TABLE photo (id bigint, account_id bigint, fb_id varchar(50), fb_name varchar, created_time bigint, updated_time bigint, link varchar, height int, width int, position int, icon varchar, picture varchar, source varchar);

CREATE TABLE photo_tag (id bigint, photo_id bigint);

CREATE TABLE photo_tag_data (id bigint, tag_id bigint, x double precision, y double precision, fb_id varchar(50), fb_name varchar(255), created_time bigint);

CREATE TABLE photo_image (id bigint, photo_id bigint, height int, width int, source varchar);

CREATE TABLE post (id bigint, account_id bigint, icon varchar, created_time bigint, updated_time bigint, fb_id varchar(50), link varchar, fb_object_id varchar(50), picture varchar, fb_type varchar(255), story varchar, fb_name varchar, source varchar, description varchar, caption varchar);

CREATE TABLE post_application(id bigint, post_id bigint, fb_id varchar(50), fb_name varchar(255));

CREATE TABLE status (id bigint, account_id bigint, fb_id varchar(50), message varchar, updated_time bigint);

CREATE TABLE tagged (id bigint, account_id bigint, caption varchar, icon varchar, link varchar, picture varchar, type varchar, updated_time bigint, description varchar, message varchar, created_time bigint, fb_id varchar(50), fb_name varchar(255));

CREATE TABLE tagged_application (id bigint, tagged_id bigint, fb_id varchar(50), fb_name varchar(255), namespace varchar);

CREATE TABLE tagged_properties (id bigint, tagged_id bigint, href varchar, fb_name varchar, text varchar);

CREATE TABLE television (id bigint, account_id bigint, category varchar(255), created_time bigint, fb_name varchar, fb_id varchar(50));

CREATE TABLE video (id bigint, account_id bigint, updated_time bigint, created_time bigint, fb_id varchar(50), source varchar, picture varchar, icon varchar, embed_html varchar, description varchar);

CREATE TABLE video_format (id bigint, video_id bigint, embed_html varchar, filter varchar, height int, width int, picture varchar);

CREATE TABLE video_tag (id bigint, video_id bigint);

CREATE TABLE video_tag_data (id bigint, video_tag_id bigint, fb_id varchar(50), fb_name varchar, created_time bigint);


CREATE TABLE shared_action (id bigint, papa_id bigint, papa_name varchar(100), name varchar(255), link varchar(1000));

CREATE TABLE shared_paging (id bigint, papa_id bigint, papa_name varchar(100), paging_next varchar, paging_previous varchar);

CREATE TABLE shared_comments (id bigint, papa_id bigint, papa_name varchar(100), comments_count int);

CREATE TABLE shared_comment_data (id bigint, comment_id bigint, message varchar, fb_id varchar(255), created_time bigint, likes int);

CREATE TABLE shared_from (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name varchar(255));
CREATE TABLE shared_to (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name varchar(255));

CREATE TABLE shared_like (id bigint, papa_id bigint, papa_name varchar(100), like_count int);

CREATE TABLE shared_like_data (id bigint, like_id bigint, fb_id varchar(50), name varchar(255));

CREATE TABLE shared_privacy (id bigint, papa_id bigint, papa_name varchar, description varchar, prv_value varchar);

CREATE TABLE shared_place (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name varchar(255));

CREATE TABLE shared_location(id bigint, shared_place_id bigint, longitude double precision, latitude double precision, city varchar, street varchar, country varchar(255), zip varchar(50), location_state varchar(255));

CREATE TABLE expanded_archive(id bigint, snapshot_id bigint);

CREATE TABLE ea_previous_name(id bigint, ea_id bigint, previous_name varchar);

CREATE TABLE ea_address_book_entry(id bigint, ea_id bigint, contact_name varchar);

CREATE TABLE ea_address_book_contact_info(id bigint, address_book_id bigint, contact_info varchar);

