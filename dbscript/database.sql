-- This script creates a database and two user entries for that database.
-- Replace CAPITALIZED TOKENS for other databases, users, passwords, etc.
drop database if exists acumos_CMS;
create database acumos_CMS;
create user 'CMS_USER'@'localhost' identified by 'CMS_PASS';
grant all on acumos_CMS.* to 'CMS_USER'@'localhost';
create user 'CMS_USER'@'%' identified by 'CMS_PASS';
grant all on acumos_CMS.* to 'CMS_USER'@'%';