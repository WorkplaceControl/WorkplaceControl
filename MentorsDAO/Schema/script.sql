create table cost_center(
	id varchar(10) primary key,
	title varchar(100) not null,
	active integer not null
);

create table customer (
	id serial primary key,
	description varchar(100) not null,
	unit_id integer not null,
	active integer not null
);

create table employee(
	id varchar(10) primary key, 
	name varchar(100),
	sap varchar(10) not null,
	join_date date not null,
	leaving_date date,
	workplace varchar(10),
	extension integer,
	job_id varchar(10)  not null,
	rate_prf_id integer,
	cost_center_id varchar(10) not null,
	ws_name varchar(100) not null,
	is_mentor integer,
	is_tutor integer,
	mentor_id varchar(10),
	tutor_id varchar(10),
	active integer not null
);

create table employee_assignment (
	id serial primary key,
	employee_id varchar(10) not null,
	project_id integer not null,
	active integer not null
);

create table job(
	id varchar(30) primary key,
	title varchar(30) not null,
	position integer not null,
	active integer not null
);

create table mentor_history(
	id serial primary key,
	employee_id varchar(10) not null,
	mentor_id varchar(10),
	tutor_id varchar(10),
	start_date date,
	finish_date date,
	job_id integer
);

create table project(
	id serial primary key not null,
	description varchar(100) not null,
	customer_id integer not null,
	active integer not null
);

create table rate_prf(
	id integer primary key, 
	title varchar(20) not null
);

insert into rate_prf values (1, 'DEFAULT');
insert into rate_prf values (2, 'INTK1');
insert into rate_prf values (3, 'INTK2');
insert into rate_prf values (4, 'INTK3');
insert into rate_prf values (5, 'INTK4');
insert into rate_prf values (6, 'INTK5');

create table unit(
	id serial primary key,
	description varchar(100) not null,
	active integer not null
);

create table users(
	id serial primary key,
	username varchar(64) unique not null,
	password varchar(64) not null,
	user_role int not null,
	enable integer default 1
);

insert into users values (DEFAULT, 'admin','admin', 1);

create table user_role(
	role_id integer primary key,
	role_description varchar(20) unique
); 

insert into user_role values (1, 'ADMINISTRATOR');
insert into user_role values (2, 'USER');

alter table users
add constraint FK_user_role foreign key (user_role)
references user_role(role_id);

alter table employee 
add constraint FK_Rate_Prf foreign key (rate_prf_id)
references rate_prf(id);

alter table employee 
add constraint FK_CostCenter foreign key (cost_center_id)
references cost_center(id);

alter table employee 
add constraint FK_Job foreign key (job_id)
references job(id);

alter table employee 
add constraint FK_Mentor foreign key (mentor_id)
references employee(id);

alter table employee 
add constraint FK_Tutor foreign key (tutor_id)
references employee(id);

alter table  employee_assignment
add constraint FK_Employee_Proj foreign key (employee_id)
references employee(id);

alter table  employee_assignment
add constraint FK_Project_Emp foreign key (project_id)
references project(id);

alter table  project
add constraint FK_Customer_Proj foreign key (customer_id)
references customer(id);

alter table  customer
add constraint FK_Unit_Cus foreign key (unit_id)
references unit(id);

alter table  mentor_history
add constraint FK_Mentor_History foreign key (employee_id)
references employee(id);

create TRIGGER verify_is_mentor
after insert on mentor_history FOR EACH ROW 
execute procedure chk_mentor();

create or replace function chk_mentor()
RETURNS trigger AS $verify_is_mentor$
begin
	UPDATE employee
	   SET is_mentor = 0
	 WHERE id
	NOT IN (SELECT distinct mentor_id FROM employee WHERE active = 0 and mentor_id is not null);

	UPDATE employee
	   SET is_tutor = 0 
	 WHERE id 
	NOT IN (SELECT distinct tutor_id FROM employee WHERE active = 0 and tutor_id is not null);
	return null;
end;
$verify_is_mentor$ LANGUAGE plpgsql;
