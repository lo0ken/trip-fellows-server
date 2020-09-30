create table point (
  id serial not null primary key,
  x double precision not null,
  y double precision not null,
  address varchar(500) not null
);

create table account (
  id serial not null primary key,
  name varchar(500) not null,
  phone_number varchar(500) not null
);