truncate table account;
truncate table follow;
truncate table target_like;


insert into account(user_id,user_name) values(1,'test');
insert into account(user_id,user_name) values(2,'test2');
insert into account(user_id,user_name) values(3,'test3');
insert into account(user_id,user_name) values(4,'test4');
insert into account(user_id,user_name) values(5,'test5');

insert into follow(from_uid,to_uid) values(1,2),(1,3);