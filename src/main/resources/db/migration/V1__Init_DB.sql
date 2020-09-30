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

create table trip_status (
  id serial not null primary key,
  code varchar(500) not null,
  name varchar(500) not null
);

insert into trip_status (id, code, name)
values (default,'WAITING','Ожидание'),
       (default,'CANCELED','Отменена'),
       (default,'STARTED','Начата'),
       (default,'FINISHED','Завершена');

create table role (
  id serial not null primary key,
  code varchar(500) not null,
  name varchar(500) not null
);

insert into role (id, code, name)
values (default,'DRIVER','Водитель'),
       (default,'PASSENGER','Пассажир');