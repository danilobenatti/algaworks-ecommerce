INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (1, 'Eletrônicos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (2, 'Informática', 1);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (3, 'Escritório', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (4, 'Literatura', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (5, 'Eletrodomésticos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (6, 'Notebooks', 2);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (7, 'Smartphones', 1);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (8, 'Câmeras', 1);
--
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image) VALUES (1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null);
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image) VALUES (3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1506.72, date_sub(now(), interval(2) month), null);
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image) VALUES (4, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.5, date_sub(now(), interval(3) day), null);
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image) VALUES (5, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null);
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image) VALUES (6, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'));
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (3, 'First attribute', 'Powerfull');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (4, 'Manufacturer', '6M');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (5, 'Max load', '432Kg');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (6, 'Manufacturer', 'Canon');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (6, 'Type', 'DSLR');
--
INSERT INTO tbl_product_category (product_id, category_id) VALUES (1, 1), (1, 4), (3, 2), (3, 8), (4, 3), (6, 8);
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES (1, 'Luiz Fernando', '21470959828', date_sub(now(), interval(3) day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (1, '1958-10-05', 'MALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (1, '+55(11)97777-6666', 'M');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(2, 'João Marcos', '54254667817', date_sub(now(), interval(4) week));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (2, '1974-05-17', 'MALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (2, '(049)9 4444-3333', 'H');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(3, 'Maria Paula', '53558795008', date_sub(now(), interval(8) month));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (3, '1963-08-21', 'FEMALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (3, '+55(011)3232-4545', 'W');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(4, 'Maria', '53041253046', date_sub(now(), interval(2) year));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (4, '1963-10-05', 'FEMALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (4, '+55(088)9999-7777', 'W');
-- Person: Luiz Fernando
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(1, date_sub(now(), interval 2 day), null, null, 1, 505.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 499.5, 1, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 1, 4);
INSERT INTO tbl_payments (order_id, col_status) VALUES (1, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (6, 1);
-- Person: João Marcos
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES (2, date_sub(now(), interval 2 week), null, null, 2, 1512.22, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 1506.72, 2, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 2, 4);
-- Person: Luiz Fernando
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(3, date_sub(now(), interval 7 month), null, null, 1, 11.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (2, 11.0, 3, 4);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 2.32, 3, 5);
INSERT INTO tbl_payments (order_id, col_status) VALUES (3, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (2, 3);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (3, date_sub(now(), interval 7 month), '<xml />');
-- Person: Maria Paula
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(4, date_sub(now(), interval 1 year), null, date_sub(now(), interval 11 month), 3, 3500.0, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 3500.0, 4, 6);
INSERT INTO tbl_payments (order_id, col_date_create, col_status) VALUES (4, date_sub(now(), interval 11 month), 3);
INSERT INTO tbl_payments_bankslip (col_expirationdate, col_payday, order_id) VALUES (date_add(now(), interval 5 day), date_sub(now(), interval 11 month), 4);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (4, date_sub(now(), interval 1 year), '<xml />');
-- Person: Maria Paula
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(5, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 3, 499.5, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 499.5, 5, 1);
INSERT INTO tbl_payments (order_id, col_date_create, col_status) VALUES (5, date_sub(now(), interval 2 day), 3);
INSERT INTO tbl_payments_bankslip (col_expirationdate, col_payday, order_id) VALUES (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 5);
-- INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (5, sysdate(), '<xml />');
