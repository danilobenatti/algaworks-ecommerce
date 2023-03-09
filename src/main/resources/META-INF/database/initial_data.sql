INSERT INTO tbl_products (id, col_description, col_name, col_price) VALUES(1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 499.5);
INSERT INTO tbl_products (id, col_description, col_name, col_price) VALUES(3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1506.72);
INSERT INTO tbl_persons (id, col_birthday, col_gender, col_name) VALUES(1, '1958-10-05', 'MALE', 'Fernando Medeiros');
INSERT INTO tbl_persons (id, col_birthday, col_gender, col_name) VALUES(2, '1974-05-17', 'MALE', 'Marcos Mariano');
INSERT INTO tbl_orders (id, col_execution_date, id_invoice, col_order_date, col_status, col_total, person_id) VALUES(1, null, null, '2023-03-25', 1, null, 1);
INSERT INTO tbl_order_items (id, col_quantity, col_subtotal, order_id, product_id) VALUES(1, 2, 999.0, 1, 1);

