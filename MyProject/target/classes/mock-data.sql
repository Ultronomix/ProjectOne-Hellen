--create new user
insert into ers_users(user_id, username, email, _password, given_name, surname, is_active, role_id)
values ('20', 'Hellen', 'hellie@gmail.com', 'yeso125', 'Hellen', 'Namukuve', True,'137');

insert into ers_users(user_id, username, email, _password, given_name, surname, is_active, role_id)
values('15', 'Nancy', 'nancy120@yahoo.com', 'fifty5', 'Norah', 'Sanders', True, '110'),
values('16', 'Joe', 'johnie@gmail.com', '$b1ll1ng', 'John', 'Simple', True, '101'),
values('19', 'Sue', 'susan65@gmail.com', 'paused@38', 'Susan', 'Bell', true, '130');

insert into ers_user_roles(role_id, role)
values('137', 'admin');
values('110', 'employee');
values('101', 'employee');
values('115', 'finance manager');




select * from ers_users;

CREATE ROLE "Hellen" NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'yeso125';
GRANT ALL ON SCHEMA "Project1" TO "Hellen";
