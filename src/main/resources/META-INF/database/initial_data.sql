insert into tbl_categories (id, col_version, col_name, parent_category_id) values (1, 0, 'Eletrônicos', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (2, 0, 'Informática', 1);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (3, 0, 'Escritório', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (4, 0, 'Literatura', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (5, 0, 'Eletrodomésticos', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (6, 0, 'Notebooks', 2);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (7, 0, 'Smartphones', 1);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (8, 0, 'Câmeras', 1);
--
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (1, 0, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (3, 0, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (4, 0, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (5, 0, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (6, 0, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (7, 0, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_products (id, col_version, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (8, 0, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_product_attribute (product_id, col_description, col_value) values (3, 'First attribute', 'Powerfull');
insert into tbl_product_attribute (product_id, col_description, col_value) values (4, 'Manufacturer', '6M');
insert into tbl_product_attribute (product_id, col_description, col_value) values (5, 'Max load', '432Kg');
insert into tbl_product_attribute (product_id, col_description, col_value) values (6, 'Manufacturer', 'Canon');
insert into tbl_product_attribute (product_id, col_description, col_value) values (6, 'Type', 'DSLR');
--
insert into tbl_product_category (product_id, category_id) values (1, 1), (1, 4), (3, 1), (3, 2), (3, 8), (4, 3), (6, 1), (6, 2), (6, 8), (7, 1), (8, 1);
--
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values (1, 0, 'Luiz Fernando', '214.709.598-28', date_sub(now(), interval(3) day));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (1, 'luiz_fernando@email.com', date_add(date_sub(current_date(), interval(65) year), interval(120) day), 'MALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (1, '+55(11)97777-6666', 'M');
insert into tbl_person_emails (person_id, col_email) values (1, 'luizfernando@mail.com');
--
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(2, 0, 'João Marcos', '542.546.678-17', date_sub(now(), interval(4) week));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (2, 'joao@mail.com', date_sub(date_sub(current_date(), interval(49) year), interval(21) day), 'MALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (2, '(049)9 4444-3333', 'H');
insert into tbl_person_emails (person_id, col_email) values (2, 'joao_marcos@qmail.org'), (2, 'jmarcos@kmail.us');
--
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(3, 0, 'Maria Paula', '535.587.950-08', date_sub(now(), interval(8) month));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (3, 'mariapaula@send.com', date_add(date_sub(current_date(), interval(60) year), interval(75) day), 'FEMALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (3, '+55(011)3232-4545', 'W');
insert into tbl_person_emails (person_id, col_email) values (3, 'mariapaula@mail.org');
--
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(4, 0, 'Maria', '530.412.530-46', date_sub(now(), interval(2) year));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (4, 'maria99@email.com', date_add(date_sub(current_date(), interval(60) year), interval(120) day), 'FEMALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (4, '+55(088)9999-7777', 'W');
insert into tbl_person_emails (person_id, col_email) values (4, 'maria99@hmail.tw'), (4, 'maria99@ymail.es');
-- Person: Luiz Fernando
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(1, date_sub(now(), interval 2 day), null, null, 1, 505.0, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 499.5, 1, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 5.5, 1, 4);
insert into tbl_payments (order_id, col_version, col_status, col_payment_type) values (1, 0, 1, 1);
insert into tbl_payments_creditcard (col_number_installments, order_id) values (6, 1);
-- Person: João Marcos
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values (2, date_sub(now(), interval 2 week), null, null, 2, 1512.22, 2);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 1506.72, 2, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 5.5, 2, 4);
-- Person: Luiz Fernando
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(3, date_sub(now(), interval 7 month), null, null, 1, 13.32, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (2, 11.0, 3, 4);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 2.32, 3, 5);
insert into tbl_payments (order_id, col_version, col_status, col_payment_type) values (3, 0, 1, 1);
insert into tbl_payments_creditcard (col_number_installments, order_id) values (2, 3);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (3, date_sub(now(), interval 7 month), '<xml />');
-- Person: Maria Paula
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(4, date_sub(now(), interval 1 year), null, date_sub(now(), interval 11 month), 3, 3500.0, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 3500.0, 4, 6);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (4, 0, date_sub(now(), interval 11 month), 3, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 5 day), date_sub(now(), interval 11 month), 4);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (4, date_sub(now(), interval 1 year), '<xml />');
-- Person: Maria Paula
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(5, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 3, 499.5, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 499.5, 5, 1);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (5, 0, date_sub(now(), interval 2 day), 3, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 5);
-- Person: João Marcos
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(6, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 1, 1599.0, 2);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (2, 1599.0, 6, 1);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (6, 0, date_sub(now(), interval 2 day), 1, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 6);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (6, sysdate(), '<xml />');
--
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (101, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (103, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (104, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (105, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (106, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (107, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (108, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
--
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (201, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (203, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (204, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (205, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (206, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (207, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (208, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
--
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (301, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (303, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (304, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (305, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (306, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (307, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, 'no');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (308, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, 'no');
--
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (201, 'Eletrônicos', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (202, 'Informática', 201);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (203, 'Escritório', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (204, 'Literatura', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (205, 'Eletrodomésticos', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (206, 'Notebooks', 202);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (207, 'Smartphones', 201);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (208, 'Câmeras', 201);
