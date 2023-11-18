insert into food (price, food_type, name)
values (7000, '중식', '짜장면');
insert into food (price, food_type, name)
values (8000, '중식', '짬뽕');
insert into food (price, food_type, name)
values (9000, '한식', '비빔밥');

insert into member (name, email, password, cell_phone, member_role)
values ("홍길동", 'abcd1@naver.com', 'pwd1111', '010-1111-2222','customer');
insert into member (name, email, password, cell_phone, member_role)
values ("성춘향", 'abcd2@naver.com', 'pwd2222', '010-3333-4444','customer');
insert into member (name, email, password, cell_phone, member_role)
values ("이몽룡", 'abcd3@naver.com', 'pwd3333', '010-5555-6666', 'owner');


insert into order_info (member_id, created_at, create_status, detail_address, postal_code, orderer_cell_phone)
values (1, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 101호', '66666', '010-1111-2222');
insert into order_foods (food_id, order_id, price, quantity)
values (1, 1, 7000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (2, 1, 8000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (3, 1, 9000, 5);

insert into order_info (member_id, created_at, create_status, detail_address, postal_code, orderer_cell_phone)
values (2, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 102호', '66666', '010-1111-2222');
insert into order_foods (food_id, order_id, price, quantity)
values (1, 2, 7000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (2, 2, 8000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (3, 2, 9000, 5);

insert into order_info (member_id, created_at, create_status, detail_address, postal_code, orderer_cell_phone)
values (3, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 103호', '66666', '010-1111-2222');
insert into order_foods (food_id, order_id, price, quantity)
values (1, 3, 7000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (2, 3, 8000, 3);
insert into order_foods (food_id, order_id, price, quantity)
values (3, 3, 9000, 5);
