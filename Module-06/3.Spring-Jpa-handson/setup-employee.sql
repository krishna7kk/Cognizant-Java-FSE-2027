-- ============================================================
-- Module 3 - Employee / Department / Skill schema (Hands On 2, 4, 5)
-- Run against the ormlearn schema (same schema used for Country).
-- ============================================================
use ormlearn;

drop table if exists employee_skill;
drop table if exists employee;
drop table if exists department;
drop table if exists skill;

create table department (
    dp_id   int auto_increment primary key,
    dp_name varchar(100) not null
);

create table skill (
    sk_id   int auto_increment primary key,
    sk_name varchar(100) not null
);

create table employee (
    em_id            int auto_increment primary key,
    em_name          varchar(100) not null,
    em_date_of_birth date,
    em_permanent     tinyint(1) not null default 0,
    em_salary        decimal(10,2) not null default 0,
    em_dp_id         int,
    constraint fk_employee_department foreign key (em_dp_id) references department(dp_id)
);

create table employee_skill (
    es_em_id int not null,
    es_sk_id int not null,
    primary key (es_em_id, es_sk_id),
    constraint fk_es_employee foreign key (es_em_id) references employee(em_id),
    constraint fk_es_skill foreign key (es_sk_id) references skill(sk_id)
);

-- ---- Sample data ----
insert into department (dp_id, dp_name) values (1, 'Engineering');
insert into department (dp_id, dp_name) values (2, 'Human Resources');
insert into department (dp_id, dp_name) values (3, 'Finance');

insert into skill (sk_id, sk_name) values (1, 'Java');
insert into skill (sk_id, sk_name) values (2, 'Spring Boot');
insert into skill (sk_id, sk_name) values (3, 'SQL');
insert into skill (sk_id, sk_name) values (4, 'Excel');
insert into skill (sk_id, sk_name) values (5, 'Communication');

insert into employee (em_id, em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id)
    values (1, 'Asha Rao', '1990-05-12', 1, 75000.00, 1);
insert into employee (em_id, em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id)
    values (2, 'Vikram Shah', '1988-11-03', 1, 82000.00, 1);
insert into employee (em_id, em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id)
    values (3, 'Priya Nair', '1995-02-21', 0, 45000.00, 2);
insert into employee (em_id, em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id)
    values (4, 'Karan Mehta', '1992-07-09', 1, 68000.00, 3);
insert into employee (em_id, em_name, em_date_of_birth, em_permanent, em_salary, em_dp_id)
    values (5, 'Divya Iyer', '1993-09-30', 0, 51000.00, 1);

-- skill assignments (matches the join-table query style shown in the handout logs)
insert into employee_skill (es_em_id, es_sk_id) values (1, 1);
insert into employee_skill (es_em_id, es_sk_id) values (1, 2);
insert into employee_skill (es_em_id, es_sk_id) values (2, 1);
insert into employee_skill (es_em_id, es_sk_id) values (2, 3);
insert into employee_skill (es_em_id, es_sk_id) values (4, 4);
insert into employee_skill (es_em_id, es_sk_id) values (4, 5);

select * from employee;
