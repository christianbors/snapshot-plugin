
CREATE TABLE ssdb_sequence(val bigint);
INSERT INTO ssdb_sequence(val) VALUES (1);


DROP FUNCTION IF EXISTS nextval;

DELIMITER $
CREATE FUNCTION nextval (amount INT)
RETURNS BIGINT
BEGIN
	DECLARE value BIGINT;
   UPDATE ssdb_sequence SET val = val + 1;
   SELECT val INTO value FROM ssdb_sequence;
   UPDATE ssdb_sequence SET val = val + amount;
   RETURN value;
END$
DELIMITER ;





CREATE TABLE snapshotinfo (id bigint, snapshotname text, timestamp bigint, root varchar(50), root_db_id bigint, version varchar(50));

CREATE TABLE snapshotinfo_friends (id bigint, snapshotinfo_id bigint, friend_fb_id varchar(50));

CREATE TABLE friendisfriend (id bigint, snapshotinfo_id bigint, friend_fb_id varchar(50));
CREATE TABLE friendisfriend_friend (id bigint, friendisfriend_id bigint, friend_fb_id varchar(50));

CREATE TABLE account (id bigint, fb_id varchar(50), snapshotinfo_id bigint);

CREATE TABLE friends (id bigint, account_id bigint, fb_id varchar(50), fb_name text);

CREATE TABLE profile (id bigint, account_id bigint, fb_id varchar(50), birthday varchar(50), firstname text, lastname text, name text, gender varchar(50), link text, locale varchar(50), username text, updated_time bigint);

CREATE TABLE profile_location (id bigint, profile_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_hometown (id bigint, profile_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_work (id bigint, profile_id bigint, start_date varchar(50), end_date varchar(50), employer_name text, employer_fb_id varchar(50));

CREATE TABLE profile_language (id bigint, profile_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_education (id bigint, profile_id bigint, education_type text);

CREATE TABLE profile_education_year (id bigint, education_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_education_school (id bigint, education_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_education_concentration (id bigint, education_id bigint, name text, fb_id varchar(50));

CREATE TABLE profile_education_class (id bigint, education_id bigint, name text, fb_id varchar(50));

CREATE TABLE activity (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE book (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE event (id bigint, account_id bigint, fb_id varchar(50), fb_name text, start_time bigint, end_time bigint, timezone text, location text, rsvp_status text);

CREATE TABLE feed (id bigint, account_id bigint, fb_id text, created_time bigint, message text, feed_type text, updated_time bigint);

CREATE TABLE fb_group (id bigint, account_id bigint, fb_id varchar(50), fb_name text, version bigint);

CREATE TABLE home (id bigint, account_id bigint, fb_id varchar(50), created_time bigint, message text, fb_type text, updated_time bigint);

CREATE TABLE inbox (id bigint, account_id bigint, fb_id varchar(50), unseen int, unread int, updated_time bigint, message text);

CREATE TABLE interest (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE fb_like (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE link (id bigint, account_id bigint, icon text, link text,  picture text, description text, message text, fb_id text, fb_name text, created_time bigint);

CREATE TABLE movie (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE music (id bigint, account_id bigint, fb_id varchar(50), fb_name text, category text, created_time bigint);

CREATE TABLE note (id bigint, account_id bigint, created_time bigint, updated_time bigint, icon text, subject text, message text, fb_id text);

CREATE TABLE outbox (id bigint, account_id bigint, message text, unseen int, unread int, updated_time bigint);

CREATE TABLE photo (id bigint, account_id bigint, fb_id varchar(50), fb_name text, created_time bigint, updated_time bigint, link text, height int, width int, position int, icon text, picture text, source text);

CREATE TABLE photo_tag (id bigint, photo_id bigint);

CREATE TABLE photo_tag_data (id bigint, tag_id bigint, x double precision, y double precision, fb_id varchar(50), fb_name text, created_time bigint);

CREATE TABLE photo_image (id bigint, photo_id bigint, height int, width int, source text);

CREATE TABLE post (id bigint, account_id bigint, icon text, created_time bigint, updated_time bigint, fb_id varchar(50), link text, fb_object_id varchar(50), picture text, fb_type text, story text, fb_name text, source text, description text, caption text);

CREATE TABLE post_application(id bigint, post_id bigint, fb_id varchar(50), fb_name text);

CREATE TABLE status (id bigint, account_id bigint, fb_id varchar(50), message text, updated_time bigint);

CREATE TABLE tagged (id bigint, account_id bigint, caption text, icon text, link text, picture text, type text, updated_time bigint, description text, message text, created_time bigint, fb_id varchar(50), fb_name text);

CREATE TABLE tagged_application (id bigint, tagged_id bigint, fb_id varchar(50), fb_name text, namespace text);

CREATE TABLE tagged_properties (id bigint, tagged_id bigint, href text, fb_name text, text text);

CREATE TABLE television (id bigint, account_id bigint, category text, created_time bigint, fb_name text, fb_id varchar(50));

CREATE TABLE video (id bigint, account_id bigint, updated_time bigint, created_time bigint, fb_id varchar(50), source text, picture text, icon text, embed_html text, description text);

CREATE TABLE video_format (id bigint, video_id bigint, embed_html text, filter text, height int, width int, picture text);

CREATE TABLE video_tag (id bigint, video_id bigint);

CREATE TABLE video_tag_data (id bigint, video_tag_id bigint, fb_id varchar(50), fb_name text, created_time bigint);


CREATE TABLE shared_action (id bigint, papa_id bigint, papa_name varchar(100), name text, link text);

CREATE TABLE shared_paging (id bigint, papa_id bigint, papa_name varchar(100), paging_next text, paging_previous text);

CREATE TABLE shared_comments (id bigint, papa_id bigint, papa_name varchar(100), comments_count int);

CREATE TABLE shared_comment_data (id bigint, comment_id bigint, message text, fb_id text, created_time bigint, likes int);

CREATE TABLE shared_from (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name text);
CREATE TABLE shared_to (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name text);

CREATE TABLE shared_like (id bigint, papa_id bigint, papa_name varchar(100), like_count int);

CREATE TABLE shared_like_data (id bigint, like_id bigint, fb_id varchar(50), name text);

CREATE TABLE shared_privacy (id bigint, papa_id bigint, papa_name varchar(100), description text, prv_value text);

CREATE TABLE shared_place (id bigint, papa_id bigint, papa_name varchar(100), fb_id varchar(50), fb_name text);

CREATE TABLE shared_location(id bigint, shared_place_id bigint, longitude double precision, latitude double precision, city text, street text, country text, zip varchar(50), location_state text);


