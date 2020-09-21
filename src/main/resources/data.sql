CREATE TABLE track_profile (
  id VARCHAR(250) PRIMARY KEY,
  metadata_id VARCHAR(250) NOT NULL,
  file_version VARCHAR(20) NOT NULL,
  creator VARCHAR(250) NOT NULL,
  creation_time DATETIME,
  modification_time DATETIME,
);

CREATE TABLE metadata (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  author VARCHAR(100) NOT NULL,
  description VARCHAR(max),
  time DATETIME NOT NULL,
  link_id VARCHAR(250) NOT NULL,
);

CREATE TABLE link (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  href VARCHAR(100) NOT NULL,
  text VARCHAR(100)
);

CREATE TABLE waypoint (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  track_profile_id VARCHAR(250) NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  name VARCHAR(250) NOT NULL,
  symbol VARCHAR(100)
);

CREATE TABLE tracking (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  track_profile_id VARCHAR(250) NOT NULL,
);

CREATE TABLE tracking_segment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tracking_id VARCHAR(250) NOT NULL,
);

CREATE TABLE tracking_point (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tracking_segment_id VARCHAR(250) NOT NULL,
  latitude DOUBLE  NOT NULL,
  longitude DOUBLE  NOT NULL,
  elevation DOUBLE  NOT NULL,
  time DATETIME NOT NULL
);