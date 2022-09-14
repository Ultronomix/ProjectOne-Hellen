create table ers_users(
user_id varchar,
username varchar unique not null,
email varchar unique not null,
_password varchar not null,
given_name varchar not null,
surname varchar not null,
is_active boolean,
role_id varchar,
primary key(user_id)
);

alter table ers_users
add foreign key(role_id)
references ers_user_roles(role_id);

--insert into ers_user_roles("name")
--values('Admin'), ('Finance Manager'), ('Employee');

create table ers_reimbursements(
reimb_id varchar,
amount numeric(6,2) not null,
submitted timestamp not null,
resolved timestamp,
description varchar not null,
payment_id varchar,
author_id varchar not null,
resolver_id varchar,
status_id varchar not null,
type_id varchar not null,
primary key(reimb_id)
);

alter table ers_reimbursements
add foreign key (author_id)
references ers_users;

alter table ers_reimbursements
add foreign key (resolver_id)
references ers_users;

alter table ers_reimbursements 
add foreign key (status_id)
references ers_reimbursement_statuses(status_id);

alter table ers_reimbursements 
add foreign key (type_id)
references ers_reimbursement_types(type_id);