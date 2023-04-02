INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES(1, 'Eletrônicos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES(2, 'Informática', 1);
INSERT INTO tbl_products (id, col_description, col_name, col_unitprice, col_create_date) VALUES(1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 499.5, date_sub(now(), interval 1 day));
INSERT INTO tbl_products (id, col_description, col_name, col_unitprice, col_create_date) VALUES(3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1506.72, date_sub(now(), interval 2 day));
INSERT INTO tbl_product_category (product_id, category_id) VALUES(1, 1);
INSERT INTO tbl_product_category (product_id, category_id) VALUES(3, 2);
INSERT INTO tbl_persons (id, col_firstname, col_date_create) VALUES(1, 'Luiz Fernando', date_sub(now(), interval 3 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES(1, '1958-10-05', 'MALE');
INSERT INTO tbl_persons (id, col_firstname, col_date_create) VALUES(2, 'João Marcos', date_sub(now(), interval 4 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES(2, '1974-05-17', 'MALE');
INSERT INTO tbl_orders (id, col_execution_date, col_order_date, col_status, col_total, person_id) VALUES(1, null, '2023-03-25 13:30:30', 1, 1998.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES(2, 999.0, 1, 1);
--INSERT INTO tbl_payments (order_id, col_status) VALUES(1, 1);
--INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES(2, 1);
INSERT INTO tbl_orders (id, col_execution_date, col_order_date, col_status, col_total, person_id) VALUES(2, null, '2023-04-15 13:30:30', 1, 1506.72, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES(1, 1506.72, 2, 3);

