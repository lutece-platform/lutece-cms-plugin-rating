--
-- Structure for table rating_resource_stat
--
DROP TABLE IF EXISTS rating_resource_stat;
CREATE TABLE rating_resource_stat (
	resource_type varchar(100) NOT NULL,
	id_resource int default 0 NOT NULL,
	vote_count int default 0 NOT NULL,
	score_value int default 0 NOT NULL,
	download_count int default 0 NOT NULL,
	view_count int default 0 NOT NULL
);

--
-- Structure for table rating_resource_conf
--
DROP TABLE IF EXISTS rating_resource_conf;
CREATE TABLE rating_resource_conf (
	resource_type varchar(100) NOT NULL,
	id_resource int default 0 NOT NULL,
	accept_site_votes int default 0 NOT NULL,
	is_email_notified_vote int default 0 NOT NULL,
	id_mailinglist_vote int default 0 NOT NULL
);

--
-- Structure for table rating_resource_types
--
DROP TABLE IF EXISTS rating_resource_types;
CREATE TABLE rating_resource_types (
	resource_type varchar(100) NOT NULL
);
