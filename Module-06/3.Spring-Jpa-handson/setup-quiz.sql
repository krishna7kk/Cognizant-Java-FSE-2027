-- ============================================================
-- Module 3 - Quiz Attempt schema (Hands On 3)
--
-- The handout points to a quiz.mwb MySQL Workbench model file to generate
-- this schema's SQL. Since that binary model file isn't available here,
-- this script defines an equivalent schema directly, using column-naming
-- conventions consistent with the rest of this project (table-prefix +
-- underscore, e.g. em_*, dp_*, sk_*), and seeds it with data that
-- reproduces the exact sample output shown in the handout for Hands On 3
-- (the 4 HTML quiz questions with their options, scores, and selections).
-- ============================================================
use ormlearn;

drop table if exists attempt_option;
drop table if exists attempt_question;
drop table if exists attempt;
drop table if exists options;
drop table if exists question;
drop table if exists user;

create table user (
    us_id   int auto_increment primary key,
    us_name varchar(100) not null
);

create table question (
    qu_id   int auto_increment primary key,
    qu_text varchar(500) not null
);

create table options (
    op_id    int auto_increment primary key,
    op_text  varchar(200) not null,
    op_score decimal(4,2) not null default 0,
    op_qu_id int not null,
    constraint fk_options_question foreign key (op_qu_id) references question(qu_id)
);

create table attempt (
    at_id    int auto_increment primary key,
    at_date  datetime not null,
    at_us_id int not null,
    constraint fk_attempt_user foreign key (at_us_id) references user(us_id)
);

create table attempt_question (
    aq_id    int auto_increment primary key,
    aq_at_id int not null,
    aq_qu_id int not null,
    constraint fk_aq_attempt foreign key (aq_at_id) references attempt(at_id),
    constraint fk_aq_question foreign key (aq_qu_id) references question(qu_id)
);

create table attempt_option (
    ao_id       int auto_increment primary key,
    ao_aq_id    int not null,
    ao_op_id    int not null,
    ao_selected tinyint(1) not null default 0,
    constraint fk_ao_attempt_question foreign key (ao_aq_id) references attempt_question(aq_id),
    constraint fk_ao_options foreign key (ao_op_id) references options(op_id)
);

-- ---- Sample data: one user, one attempt, four HTML questions ----

insert into user (us_id, us_name) values (1, 'jsmith');

insert into attempt (at_id, at_date, at_us_id) values (1, '2026-06-15 10:30:00', 1);

-- Q1: file extension
insert into question (qu_id, qu_text)
    values (1, 'What is the extension of the hyper text markup language file?');
insert into options (op_id, op_text, op_score, op_qu_id) values (1, '.xhtm', 0.0, 1);
insert into options (op_id, op_text, op_score, op_qu_id) values (2, '.ht', 0.0, 1);
insert into options (op_id, op_text, op_score, op_qu_id) values (3, '.html', 1.0, 1);
insert into options (op_id, op_text, op_score, op_qu_id) values (4, '.htmx', 0.0, 1);

-- Q2: max heading level
insert into question (qu_id, qu_text)
    values (2, 'What is the maximum level of heading tag can be used in a HTML page?');
insert into options (op_id, op_text, op_score, op_qu_id) values (5, '5', 0.0, 2);
insert into options (op_id, op_text, op_score, op_qu_id) values (6, '3', 0.0, 2);
insert into options (op_id, op_text, op_score, op_qu_id) values (7, '4', 0.0, 2);
insert into options (op_id, op_text, op_score, op_qu_id) values (8, '6', 1.0, 2);

-- Q3: true/false on <html> tags
insert into question (qu_id, qu_text)
    values (3, 'The HTML document itself begins with <html> and ends </html>. State True of False');
insert into options (op_id, op_text, op_score, op_qu_id) values (9, 'false', 0.0, 3);
insert into options (op_id, op_text, op_score, op_qu_id) values (10, 'true', 1.0, 3);

-- Q4: storing text in a variable
insert into question (qu_id, qu_text)
    values (4, 'Choose the right option to store text value value in a variable');
insert into options (op_id, op_text, op_score, op_qu_id) values (11, '''John''', 0.5, 4);
insert into options (op_id, op_text, op_score, op_qu_id) values (12, 'John', 0.0, 4);
insert into options (op_id, op_text, op_score, op_qu_id) values (13, '"John"', 0.5, 4);
insert into options (op_id, op_text, op_score, op_qu_id) values (14, '/John/', 0.0, 4);

-- ---- Link the attempt to each question ----
insert into attempt_question (aq_id, aq_at_id, aq_qu_id) values (1, 1, 1);
insert into attempt_question (aq_id, aq_at_id, aq_qu_id) values (2, 1, 2);
insert into attempt_question (aq_id, aq_at_id, aq_qu_id) values (3, 1, 3);
insert into attempt_question (aq_id, aq_at_id, aq_qu_id) values (4, 1, 4);

-- ---- Record every option shown for each question, with the user's selection ----
-- Q1: user selected option 3 (.html) - correct
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (1, 1, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (1, 2, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (1, 3, 1);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (1, 4, 0);

-- Q2: user selected option 5 ('5') - incorrect (correct answer is '6')
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (2, 5, 1);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (2, 6, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (2, 7, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (2, 8, 0);

-- Q3: user selected option 10 (true) - correct
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (3, 9, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (3, 10, 1);

-- Q4: user selected option 13 ("John") - one of two partially-correct options
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (4, 11, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (4, 12, 0);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (4, 13, 1);
insert into attempt_option (ao_aq_id, ao_op_id, ao_selected) values (4, 14, 0);

select * from attempt;
