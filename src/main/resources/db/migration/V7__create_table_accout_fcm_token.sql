create table account_fcm_token (
    account_id integer primary key references account (id),
    fcm_token varchar(500) not null
);

alter table account_fcm_token add constraint fk_account_id
    foreign key (account_id) references account(id);

alter table account_fcm_token add constraint unique_account UNIQUE (account_id);