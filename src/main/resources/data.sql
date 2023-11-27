insert into food (name, food_type, price)
values ('짜장면', '중식', 7000);
insert into food (name, food_type, price)
values ('짬뽕', '중식', 8000);
insert into food (name, food_type, price)
values ('비빔밥', '한식', 9000);


insert into member (name, email, password, cell_phone, member_role)
values ('홍길동', 'abcd1@naver.com', 'pwd1111', '010-1111-2222','customer');
insert into member (name, email, password, cell_phone, member_role)
values ('성춘향', 'abcd2@naver.com', 'pwd2222', '010-3333-4444','customer');
insert into member (name, email, password, cell_phone, member_role)
values ('이몽룡', 'abcd3@naver.com', 'pwd3333', '010-5555-6666', 'owner');


insert into orders (order_id, member_id, created_at, create_status, detail_address, postal_code)
values (1, 1, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 101호', '66666');
insert into order_line (food_id, order_id, quantity)
values (1, 1, 3);
insert into order_line (food_id, order_id, quantity)
values (2, 1, 3);
insert into order_line (food_id, order_id, quantity)
values (3, 1, 5);


insert into orders (order_id, member_id, created_at, create_status, detail_address, postal_code)
values (2, 1, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 101호', '66666');
insert into order_line (food_id, order_id, quantity)
values (1, 2, 3);
insert into order_line (food_id, order_id, quantity)
values (2, 2, 3);
insert into order_line (food_id, order_id, quantity)
values (3, 2, 5);


insert into orders (order_id, member_id, created_at, create_status, detail_address, postal_code)
values (3, 1, '2023-11-16 12:34:56.123456', 'accepting', 'xx동 101호', '66666');
insert into order_line (food_id, order_id, quantity)
values (1, 3, 3);
insert into order_line (food_id, order_id, quantity)
values (2, 3, 3);
insert into order_line (food_id, order_id, quantity)
values (3, 3, 5);


insert into orders (order_id, member_id, created_at, create_status, detail_address, postal_code)
values (4, 1, '2023-11-16 12:34:56.123456', 'accepted', 'xx동 101호', '66666');
insert into order_line (food_id, order_id, quantity)
values (1, 4, 3);
insert into order_line (food_id, order_id, quantity)
values (2, 4, 3);
insert into order_line (food_id, order_id, quantity)
values (3, 4, 5);


insert into delivery (delivery_started_at, order_id,  delivery_status)
values ('2023-11-16 12:34:56.123456', 4, 'ready_for_delivery');