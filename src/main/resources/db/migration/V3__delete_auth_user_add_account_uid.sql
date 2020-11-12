drop table auth_user cascade;

alter table account add column uid varchar(500) not null default 'old_acc';