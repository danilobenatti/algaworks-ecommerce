INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (1, 'Eletrônicos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (2, 'Informática', 1);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (3, 'Escritório', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (4, 'Literatura', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (5, 'Eletrodomésticos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (6, 'Notebooks', 2);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (7, 'Smartphones', 1);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (8, 'Câmeras', 1);
--
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (4, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (5, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (6, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (7, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (8, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (3, 'First attribute', 'Powerfull');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (4, 'Manufacturer', '6M');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (5, 'Max load', '432Kg');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (6, 'Manufacturer', 'Canon');
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (6, 'Type', 'DSLR');
--
INSERT INTO tbl_product_category (product_id, category_id) VALUES (1, 1), (1, 4), (3, 1), (3, 2), (3, 8), (4, 3), (6, 1), (6, 2), (6, 8), (7, 1), (8, 1);
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES (1, 'Luiz Fernando', '214.709.598-28', date_sub(now(), interval(3) day));
INSERT INTO tbl_person_detail (person_id, col_email, col_birthday, col_gender) VALUES (1, 'luiz_fernando@email.com', date_add(date_sub(current_date(), interval(65) year), interval(120) day), 'MALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (1, '+55(11)97777-6666', 'M');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(2, 'João Marcos', '542.546.678-17', date_sub(now(), interval(4) week));
INSERT INTO tbl_person_detail (person_id, col_email, col_birthday, col_gender) VALUES (2, 'joao@mail.com', date_sub(date_sub(current_date(), interval(49) year), interval(21) day), 'MALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (2, '(049)9 4444-3333', 'H');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(3, 'Maria Paula', '535.587.950-08', date_sub(now(), interval(8) month));
INSERT INTO tbl_person_detail (person_id, col_email, col_birthday, col_gender) VALUES (3, 'mariapaula@send.com', date_add(date_sub(current_date(), interval(60) year), interval(75) day), 'FEMALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (3, '+55(011)3232-4545', 'W');
--
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(4, 'Maria', '530.412.530-46', date_sub(now(), interval(2) year));
INSERT INTO tbl_person_detail (person_id, col_email, col_birthday, col_gender) VALUES (4, 'maria99@email.com', date_add(date_sub(current_date(), interval(60) year), interval(120) day), 'FEMALE');
INSERT INTO tbl_person_phones (person_id, col_number, col_type) VALUES (4, '+55(088)9999-7777', 'W');
-- Person: Luiz Fernando
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(1, date_sub(now(), interval 2 day), null, null, 1, 505.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 499.5, 1, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 1, 4);
INSERT INTO tbl_payments (order_id, col_status, col_payment_type) VALUES (1, 1, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (6, 1);
-- Person: João Marcos
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES (2, date_sub(now(), interval 2 week), null, null, 2, 1512.22, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 1506.72, 2, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 5.5, 2, 4);
-- Person: Luiz Fernando
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(3, date_sub(now(), interval 7 month), null, null, 1, 13.32, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (2, 11.0, 3, 4);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 2.32, 3, 5);
INSERT INTO tbl_payments (order_id, col_status, col_payment_type) VALUES (3, 1, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (2, 3);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (3, date_sub(now(), interval 7 month), '<xml />');
-- Person: Maria Paula
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(4, date_sub(now(), interval 1 year), null, date_sub(now(), interval 11 month), 3, 3500.0, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 3500.0, 4, 6);
INSERT INTO tbl_payments (order_id, col_date_create, col_status, col_payment_type) VALUES (4, date_sub(now(), interval 11 month), 3, 0);
INSERT INTO tbl_payments_bankslip (col_expirationdate, col_payday, order_id) VALUES (date_add(now(), interval 5 day), date_sub(now(), interval 11 month), 4);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (4, date_sub(now(), interval 1 year), '<xml />');
-- Person: Maria Paula
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(5, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 3, 499.5, 3);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 499.5, 5, 1);
INSERT INTO tbl_payments (order_id, col_date_create, col_status, col_payment_type) VALUES (5, date_sub(now(), interval 2 day), 3, 0);
INSERT INTO tbl_payments_bankslip (col_expirationdate, col_payday, order_id) VALUES (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 5);
-- Person: João Marcos
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(6, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 1, 1599.0, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (2, 1599.0, 6, 1);
INSERT INTO tbl_payments (order_id, col_date_create, col_status, col_payment_type) VALUES (6, date_sub(now(), interval 2 day), 1, 0);
INSERT INTO tbl_payments_bankslip (col_expirationdate, col_payday, order_id) VALUES (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 6);
INSERT INTO tbl_invoices (order_id, col_issuedatetime, col_xml) VALUES (6, sysdate(), '<xml />');
--
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (101, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (103, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (104, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (105, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (106, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (107, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
INSERT INTO tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) VALUES (108, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
--
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (201, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (203, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (204, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (205, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (206, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (207, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
INSERT INTO tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) VALUES (208, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
--
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (301, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, 'yes');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (303, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, 'yes');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (304, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, 'yes');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (305, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, 'yes');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (306, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, 'yes');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (307, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, 'no');
INSERT INTO tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) VALUES (308, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, 'no');
--
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (201, 'Eletrônicos', null);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (202, 'Informática', 201);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (203, 'Escritório', null);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (204, 'Literatura', null);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (205, 'Eletrodomésticos', null);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (206, 'Notebooks', 202);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (207, 'Smartphones', 201);
INSERT INTO tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) VALUES (208, 'Câmeras', 201);
