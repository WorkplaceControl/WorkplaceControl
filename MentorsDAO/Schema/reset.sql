delete from mentor_history;
delete from employee_assignment;
delete from employee;
delete from cost_center;
delete from job;
delete from rate_prf;
delete from project;
delete from customer;
delete from unit;
delete from users;
delete from user_role;

insert into rate_prf values (1 , 'DEFAULT');
insert into rate_prf values (2 , 'INTK1');
insert into rate_prf values (3 , 'INTK2');
insert into rate_prf values (4 , 'INTK3');
insert into rate_prf values (5 , 'INTK4');
insert into rate_prf values (6 , 'INTK5');

insert into user_role values (1, 'ADMINISTRATOR');
insert into user_role values (2, 'USER');

insert into users values (DEFAULT, 'admin','admin', 1);
