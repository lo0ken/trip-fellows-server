truncate account cascade;
alter table account add constraint unique_uid unique (uid)