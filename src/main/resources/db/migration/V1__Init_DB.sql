create table point (
  id serial not null primary key,
  x double precision not null,
  y double precision not null,
  address varchar(500) not null
);