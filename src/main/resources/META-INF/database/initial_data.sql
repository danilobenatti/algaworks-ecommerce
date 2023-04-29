INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (1, 'Eletrônicos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (2, 'Informática', 1);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (3, 'Escritório', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (4, 'Literatura', null);
--
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 499.5, date_sub(now(), interval 1 day));
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1506.72, date_sub(now(), interval 2 day));
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (4, 'Fita Adesiva Alta Aderência','Fital ColaTudo', 1, 5.5, date_sub(now(), interval 3 day));
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (5, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval 4 day));
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (3, 'First attribute', 'Powerfull');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (4, 'Manufacturer', '6M');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (5, 'Max load', '432Kg');
--
INSERT INTO tbl_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tbl_product_category (product_id, category_id) VALUES (1, 4);
INSERT INTO tbl_product_category (product_id, category_id) VALUES (3, 2);
INSERT INTO tbl_product_category (product_id, category_id) VALUES (4, 3);
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES (1, 'Luiz Fernando', '21470959828', date_sub(now(), interval 3 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (1, '1958-10-05', 'MALE');
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(2, 'João Marcos', '54254667817', date_sub(now(), interval 4 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (2, '1974-05-17', 'MALE');
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(3, 'Maria Paula', '53558795008', date_sub(now(), interval 8 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (3, '1963-08-21', 'FEMALE');
--
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(1, date_sub(now(), interval 5 day), null, null, 1, 2006.22, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 499.5, 1, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 1, 4);
INSERT INTO tbl_payments (order_id, col_status) VALUES (1, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (6, 1);
--
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES (2, date_sub(now(), interval 6 day), null, null, 1, 1512.22, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 1506.72, 2, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 2, 4);
--
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(3, date_sub(now(), interval 7 day), null, null, 1, 11.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (2, 11.0, 3, 4);
INSERT INTO tbl_payments (order_id, col_status) VALUES (3, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (2, 3);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (3, sysdate(), '<xml />');
