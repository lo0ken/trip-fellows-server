create table auth_user (
  id serial not null primary key,
  username  varchar(100) not null,
  password varchar(100) not null,
  account_id integer not null
);

alter table auth_user add constraint fk_account_id foreign key (account_id) references account (id);

create table user_role (
  id serial primary key,
  user_id integer not null,
  roles varchar not null
);

alter table user_role add constraint fk_user_id foreign key (user_id) references auth_user (id);
