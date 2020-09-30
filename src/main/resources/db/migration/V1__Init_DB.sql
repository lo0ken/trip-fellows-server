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
values (default, 'WAITING', 'Ожидание'),
       (default, 'CANCELED', 'Отменена'),
       (default, 'STARTED', 'Начата'),
       (default, 'FINISHED', 'Завершена');

create table role (
  id serial not null primary key,
  code varchar(500) not null,
  name varchar(500) not null
);

insert into role (id, code, name)
values (default, 'DRIVER', 'Водитель'),
       (default, 'PASSENGER', 'Пассажир');

create table trip (
  id serial not null primary key,
  start_point_id integer not null,
  end_point_id integer not null,
  price numeric,
  create_dt timestamp not null,
  start_dt timestamp not null,
  end_dt timestamp not null,
  comment varchar(500),
  status_id integer not null,
  places_count integer
);

alter table trip add constraint fk_trip_start_point foreign key (start_point_id) references point(id);
alter table trip add constraint fk_trip_end_point foreign key (end_point_id) references point(id);
alter table trip add constraint fk_trip_status foreign key (status_id) references trip_status(id);

create table trip_account (
  id serial not null primary key,
  account_id int not null,
  trip_id int not null,
  role_id int not null
);

alter table trip_account add constraint fk_account_id foreign key (account_id) references account (id);
alter table trip_account add constraint fk_trip_id foreign key (trip_id) references trip (id);