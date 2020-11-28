alter table point rename column x to latitude;
alter table point rename column y to longitude;

alter table point alter column latitude type double precision;
alter table point alter column longitude type double precision;