CREATE TABLE member (
  member_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  age INT,
  gender VARCHAR(45),
  role VARCHAR(10),
  dm VARCHAR(10),
  PRIMARY KEY (member_id)
) 
CREATE TABLE service (
  member_id bigint DEFAULT NULL,
  service_cd varchar(10) DEFAULT NULL
)