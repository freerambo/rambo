----mysql quartz.sql 2013-9-3 14:58:50 author rambo


DROP TABLE IF EXISTS QRTZ_JOB_LISTENERS; 
DROP TABLE IF EXISTS QRTZ_TRIGGER_LISTENERS; 
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS; 
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS; 
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE; 
DROP TABLE IF EXISTS QRTZ_LOCKS; 
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS; 
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS; 
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS; 
DROP TABLE IF EXISTS QRTZ_TRIGGERS; 
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS; 
DROP TABLE IF EXISTS QRTZ_CALENDARS; 


CREATE TABLE QRTZ_JOB_DETAILS 
( 
JOB_NAME VARCHAR(200) NOT NULL, 
JOB_GROUP VARCHAR(200) NOT NULL, 
DESCRIPTION VARCHAR(250) NULL, 
JOB_CLASS_NAME VARCHAR(250) NOT NULL, 
IS_DURABLE VARCHAR(1) NOT NULL, 
IS_VOLATILE VARCHAR(1) NOT NULL, 
IS_STATEFUL VARCHAR(1) NOT NULL, 
REQUESTS_RECOVERY VARCHAR(1) NOT NULL, 
JOB_DATA BLOB NULL, 
PRIMARY KEY (JOB_NAME,JOB_GROUP) 
); CREATE TABLE QRTZ_JOB_LISTENERS 
( 
JOB_NAME VARCHAR(200) NOT NULL, 
JOB_GROUP VARCHAR(200) NOT NULL, 
JOB_LISTENER VARCHAR(200) NOT NULL, 
PRIMARY KEY (JOB_NAME,JOB_GROUP,JOB_LISTENER), 
FOREIGN KEY (JOB_NAME,JOB_GROUP) 
REFERENCES QRTZ_JOB_DETAILS(JOB_NAME,JOB_GROUP) 
); CREATE TABLE QRTZ_TRIGGERS 
( 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
JOB_NAME VARCHAR(200) NOT NULL, 
JOB_GROUP VARCHAR(200) NOT NULL, 
IS_VOLATILE VARCHAR(1) NOT NULL, 
DESCRIPTION VARCHAR(250) NULL, 
NEXT_FIRE_TIME BIGINT(13) NULL, 
PREV_FIRE_TIME BIGINT(13) NULL, 
PRIORITY INTEGER NULL, 
TRIGGER_STATE VARCHAR(16) NOT NULL, 
TRIGGER_TYPE VARCHAR(8) NOT NULL, 
START_TIME BIGINT(13) NOT NULL, 
END_TIME BIGINT(13) NULL, 
CALENDAR_NAME VARCHAR(200) NULL, 
MISFIRE_INSTR SMALLINT(2) NULL, 
JOB_DATA BLOB NULL, 
PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP), 
FOREIGN KEY (JOB_NAME,JOB_GROUP) 
REFERENCES QRTZ_JOB_DETAILS(JOB_NAME,JOB_GROUP) 
); CREATE TABLE QRTZ_SIMPLE_TRIGGERS 
( 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
REPEAT_COUNT BIGINT(7) NOT NULL, 
REPEAT_INTERVAL BIGINT(12) NOT NULL, 
TIMES_TRIGGERED BIGINT(7) NOT NULL, 
PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP), 
FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP) 
); CREATE TABLE QRTZ_CRON_TRIGGERS 
( 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
CRON_EXPRESSION VARCHAR(200) NOT NULL, 
TIME_ZONE_ID VARCHAR(80), 
PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP), 
FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP) 
); CREATE TABLE QRTZ_BLOB_TRIGGERS 
( 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
BLOB_DATA BLOB NULL, 
PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP), 
FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP) 
); CREATE TABLE QRTZ_TRIGGER_LISTENERS 
( 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
TRIGGER_LISTENER VARCHAR(200) NOT NULL, 
PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER), 
FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP) 
REFERENCES QRTZ_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP) 
); 
CREATE TABLE QRTZ_CALENDARS 
( 
CALENDAR_NAME VARCHAR(200) NOT NULL, 
CALENDAR BLOB NOT NULL, 
PRIMARY KEY (CALENDAR_NAME) 
); CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS 
( 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
PRIMARY KEY (TRIGGER_GROUP) 
); CREATE TABLE QRTZ_FIRED_TRIGGERS 
( 
ENTRY_ID VARCHAR(95) NOT NULL, 
TRIGGER_NAME VARCHAR(200) NOT NULL, 
TRIGGER_GROUP VARCHAR(200) NOT NULL, 
IS_VOLATILE VARCHAR(1) NOT NULL, 
INSTANCE_NAME VARCHAR(200) NOT NULL, 
FIRED_TIME BIGINT(13) NOT NULL, 
PRIORITY INTEGER NOT NULL, 
STATE VARCHAR(16) NOT NULL, 
JOB_NAME VARCHAR(200) NULL, 
JOB_GROUP VARCHAR(200) NULL, 
IS_STATEFUL VARCHAR(1) NULL, 
REQUESTS_RECOVERY VARCHAR(1) NULL, 
PRIMARY KEY (ENTRY_ID) 
); CREATE TABLE QRTZ_SCHEDULER_STATE 
( 
INSTANCE_NAME VARCHAR(200) NOT NULL, 
LAST_CHECKIN_TIME BIGINT(13) NOT NULL, 
CHECKIN_INTERVAL BIGINT(13) NOT NULL, 
PRIMARY KEY (INSTANCE_NAME) 
); CREATE TABLE QRTZ_LOCKS 
( 
LOCK_NAME VARCHAR(40) NOT NULL, 
PRIMARY KEY (LOCK_NAME) 
); 
INSERT INTO QRTZ_LOCKS values('TRIGGER_ACCESS'); 
INSERT INTO QRTZ_LOCKS values('JOB_ACCESS'); 
INSERT INTO QRTZ_LOCKS values('CALENDAR_ACCESS'); 
INSERT INTO QRTZ_LOCKS values('STATE_ACCESS'); 
INSERT INTO QRTZ_LOCKS values('MISFIRE_ACCESS'); 
commit;

---*For those using JDBC JobStore, there is a schema change that requires the addition of a column to the fired_triggers table. You can add the column with a SQL statement such as the examples shown below (with slight variants on data type needed depending upon your database flavor):

---ALTER TABLE QRTZ_FIRED_TRIGGERS ADD COLUMN SCHED_TIME BIGINT(13) NOT NULL;
--执行你的应用就可以看到任务的信息在表qrtz_job_detailsj里。