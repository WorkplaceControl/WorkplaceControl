-- Employee table.

/*Atualizado com Active column*/
create table Employee(
                    Id               varchar(10)   primary key , 
                    Name             varchar(100) ,
                    SAP              varchar(10)  not null,
                    join_date        date          not null,
                    Leaving_Date     date ,
                    Workplace        varchar(10) ,
                    extension        integer ,
                    job_id           varchar(10)  not null,
                    Rate_Prf_id      integer  ,
                    Cost_Center_id   varchar(10) not null,
                    WS_Name          varchar(100) not null,
                    Is_Mentor        integer ,
                    IsTutor          integer ,
                    Mentor_id        varchar(10) ,
                    Tutor_id         varchar(10),
                    Active           integer    not null
                   );
           
           
create table Job(
		id        varchar(30)       primary key,
		Title     varchar(30)   not null,
                Position  integer not null,
                Active           integer        not null
		);
        
 
/*Atualizado com Active column*/
create table Rate_Prf(
			id     integer          primary key , 
			Title	varchar(20)     not null
		     );
             
/*Atualizado com Active column*/              
create table Cost_Center(
			id	varchar(10)	primary key,
			Title	varchar(100) 	not null,
                        Active  integer         not null
			);

        
create table Employee_Assignment (
                              id	serial		primary key,
			      Employee_id	varchar(10)     not null,
			      Project_id	integer		not null,
                              Active           integer          not null
			     );


/*Atualizado com Active column*/
create table Project(
			id	Serial		primary key	not null,
			Description		varchar(100)	not null,
			Customer_id	integer		not null,
                        Active          integer         not null
			);

/*Atualizado com Active column*/            
create table Customer (
			id	serial		primary key,
			Description	varchar(100)	not null,
			Unit_id		integer		not null,
                        Active          integer         not null
			);


/*Atualizado com Active column*/            
create table Unit(
		id		serial	primary key,
		Description		varchar(100)	not null,
                Active          integer         not null
		);
        
create table Mentor_History(
                              id	serial		primary key,
                              Employee_Id      varchar(10)     not null,
                              Mentor_id        varchar(10),
                              Tutor_id         varchar(10),
                              Start_date       date,
                              Finish_date      date,
                              Job_id           integer
);


/* Criando table users */

create table users(
		id serial primary key,
		username  varchar(64) unique not null,
		password  varchar(64) not null,
		user_role   int not null,
		enable  integer default 1
		);
ALTER SEQUENCE users_id_seq OWNED BY users.id;


/* Criando table user_role */

		create table user_role(
			role_id integer primary key,
			role_description varchar(20) unique
			); 

insert into user_role values (1, 'ADMINISTRATOR')
insert into user_role values (2, 'USER')
insert into users values (DEFAULT, 'admin','admin','1')
insert into users values (DEFAULT, 'user','user','2')
     
               
-- contraints of the Users table.           
alter table users
add constraint FK_user_role foreign key (user_role)
references user_role(role_id);


-- contraints of the Employee table.           
alter table Employee 
add constraint FK_Rate_Prf foreign key (Rate_Prf_id)
references Rate_Prf(id);


alter table Employee 
add constraint FK_CostCenter foreign key (Cost_Center_id)
references Cost_Center(id);

alter table Employee 
add constraint FK_Job foreign key (Job_id)
references Job(id);


-- contraints of the Employee_Project table.
alter table  Employee_Assignment
add constraint FK_Employee_Proj foreign key (Employee_id)
references Employee(id);

alter table  Employee_Assignment
add constraint FK_Project_Emp foreign key (Project_id)
references Project(id);

-- contraints of the Project table.
alter table  Project
add constraint FK_Customer_Proj foreign key (Customer_id)
references Customer(id);

-- contraints of the Customer table.
alter table  Customer
add constraint FK_Unit_Cus foreign key (Unit_id)
references Unit(id);


-- contraints of the Employee_History table.
alter table  Mentor_History
add constraint FK_Mentor_History foreign key (employee_id)
references Employee(id);

insert into Rate_Prf values (1 , 'DEFAULT');
insert into Rate_Prf values (2 , 'INTK1');
insert into Rate_Prf values (3 , 'INTK2');
insert into Rate_Prf values (4 , 'INTK3');
insert into Rate_Prf values (5 , 'INTK4');
insert into Rate_Prf values (6 , 'INTK5');



-- trigger to call procedure chk_mentor
create TRIGGER verify_IsMentor
after insert on Mentor_History FOR EACH ROW 
execute procedure chk_mentor();



-- function to set is_mentor or istutor = 0 when employee is not mentor or tutor anymore
create or replace function chk_mentor()
RETURNS trigger AS $verify_IsMentor$
Begin
	UPDATE 
		employee 
	SET 
		is_mentor = 0 
	WHERE 
		id 
	NOT IN
		(SELECT distinct mentor_id FROM employee WHERE active = 0 and mentor_id is not null);

	UPDATE 
		employee 
	SET 
		istutor = 0 
	WHERE 
		id 
	NOT IN 
		(SELECT distinct tutor_id FROM employee WHERE active = 0 and tutor_id is not null);
	return null;
end;
$verify_IsMentor$ LANGUAGE plpgsql;


--LOGS (INVIAVEL) --

create table log_users ( log_id integer primary key,
			 old_users_id integer,
			 old_users_username varchar(64),
			 old_users_password varchar(64),
			 old_users_user_role int,
			 old_users_enable int,
			 new_users_id integer,
			 new_users_username varchar(64),
			 new_users_password varchar(64),
			 new_users_user_role int,
			 new_users_enable int,
			 date date,
			 operation varchar(6)
			 )
			 
select * from log_users
drop table log_users
select * from users


drop sequence log_users_id_seq;
create sequence log_users_id_seq;


select * from users
select * from user_role



create or replace function fun_trg_log_users () RETURNS trigger as
$trg_log_users$
BEGIN
if (TG_OP = 'UPDATE') THEN 
insert into log_users select (nextval('log_users_id_seq')), old.*, new.*, current_timestamp, tg_op;
RETURN null;

elsif (TG_OP = 'DELETE') THEN
insert into log_users select (nextval('log_users_id_seq')), old.*, new.*, current_timestamp, tg_op;
RETURN null;
elsif (TG_OP = 'INSERT') THEN
insert into log_users select (nextval('log_users_id_seq')), old.old_users_id = null, old.old_users_username = null, old.old_users_password = null, old.old_users_user_role = null, old.old_users_enable = null, new.*, current_timestamp, tg_op;
RETURN null;
END IF;
RETURN NULL;
END;
$trg_log_users$ LANGUAGE 'plpgsql'



create trigger trg_log_users after insert or update or delete
on users for each row
execute procedure fun_trg_log_users();



insert into users values (default,'boobs','boobs',1)
select * from users
select * from log_users

update users set user_role = '2' where username = 'user'


drop function fun_trg_log_users() cascade








