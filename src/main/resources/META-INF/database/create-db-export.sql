
    create table tbl_categories (
       id bigint not null auto_increment,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_name varchar(100) not null,
       parent_category_id bigint,
       primary key (id)
    );

    create table tbl_invoices (
       order_id bigint not null,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_xml mediumblob,
       primary key (order_id)
    );

    create table tbl_order_items (
       order_id bigint not null,
       product_id bigint not null,
       col_quantity double default 0 not null,
       col_subtotal decimal(38,2) not null,
       primary key (order_id, product_id)
    );

    create table tbl_orders (
       id bigint not null auto_increment,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_city varchar(20),
       col_complement varchar(20),
       col_district varchar(20),
       col_estate varchar(2),
       col_number varchar(10),
       col_street varchar(50),
       col_zipcode varchar(10),
       col_execution_date timestamp null,
       col_status tinyint not null,
       col_total decimal(38,2) not null,
       person_id bigint not null,
       primary key (id)
    );

    create table tbl_payments (
       order_id bigint not null,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_status tinyint,
       primary key (order_id)
    );

    create table tbl_payments_bankslip (
       col_expirationdate date,
       col_payday date,
       order_id bigint not null,
       primary key (order_id)
    );

    create table tbl_payments_creditcard (
       col_number_installments integer,
       order_id bigint not null,
       primary key (order_id)
    );

    create table tbl_person_detail (
       col_birthday date,
       col_gender varchar(255),
       person_id bigint not null,
       primary key (person_id)
    );

    create table tbl_person_phones (
       person_id bigint not null,
       col_number varchar(255),
       col_type varchar(255) not null,
       primary key (person_id, col_type)
    );

    create table tbl_persons (
       id bigint not null auto_increment,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_firstname varchar(100) not null,
       col_taxidnumber varchar(14) not null,
       primary key (id)
    );

    create table tbl_product_attribute (
       product_id bigint not null,
       col_description varchar(100) not null,
       col_value varchar(255)
    );

    create table tbl_product_category (
       product_id bigint not null,
       category_id bigint not null
    );

    create table tbl_product_stocks (
       product_id bigint not null,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_quantity double default 0 not null,
       primary key (product_id)
    );

    create table tbl_product_tag (
       product_id bigint not null,
       col_tag varchar(50) not null
    );

    create table tbl_products (
       id bigint not null auto_increment,
       col_date_create timestamp null,
       col_date_update timestamp null,
       col_description mediumtext not null,
       col_image mediumblob,
       col_name varchar(150) not null,
       col_unit tinyint,
       col_unitprice decimal(12,2) unsigned not null default 0,
       primary key (id)
    );

    alter table tbl_categories 
       add constraint uk_categories_name unique (col_name);
create index idx_person__firstname on tbl_persons (col_firstname);

    alter table tbl_persons 
       add constraint uk_person__taxidnumber unique (col_taxidnumber);

    alter table tbl_product_category 
       add constraint uk_productid_categoryid unique (product_id, category_id);
create index idx_product__name on tbl_products (col_name);

    alter table tbl_products 
       add constraint uk_product__name unique (col_name);

    alter table tbl_categories 
       add constraint fk_category__category_id 
       foreign key (parent_category_id) 
       references tbl_categories (id);

    alter table tbl_invoices 
       add constraint fk_invoice__order_id 
       foreign key (order_id) 
       references tbl_orders (id);

    alter table tbl_order_items 
       add constraint fk_orderitem__order_id 
       foreign key (order_id) 
       references tbl_orders (id);

    alter table tbl_order_items 
       add constraint fk_orderitem__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_orders 
       add constraint fk_order__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_payments 
       add constraint fk_payments__order_id 
       foreign key (order_id) 
       references tbl_orders (id);

    alter table tbl_payments_bankslip 
       add constraint fk_paymentsbankslip__order_id 
       foreign key (order_id) 
       references tbl_payments (order_id);

    alter table tbl_payments_creditcard 
       add constraint fk_paymentscreditcard__order_id 
       foreign key (order_id) 
       references tbl_payments (order_id);

    alter table tbl_person_detail 
       add constraint fk_persondetail__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_person_phones 
       add constraint fk_personphone__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_product_attribute 
       add constraint fk_productattribute__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_category 
       add constraint fk_productcategory__category_id 
       foreign key (category_id) 
       references tbl_categories (id);

    alter table tbl_product_category 
       add constraint fk_productcategory__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_stocks 
       add constraint fk_productstock__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_tag 
       add constraint fk_producttag__product_id 
       foreign key (product_id) 
       references tbl_products (id);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (1, 'Eletrônicos', null);
INSERT INTO tbl_categories (id, col_name, parent_category_id) VALUES (2, 'Informática', 1);
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (1, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 499.5, date_sub(now(), interval 1 day));
INSERT INTO tbl_products (id, col_description, col_name, col_unit, col_unitprice, col_date_create) VALUES (3, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1506.72, date_sub(now(), interval 2 day));
INSERT INTO tbl_product_attribute (product_id, col_description, col_value) VALUES (3, 'First attribute', 'Powerfull');
INSERT INTO tbl_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tbl_product_category (product_id, category_id) VALUES (3, 2);
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES (1, 'Luiz Fernando', '21470959828', date_sub(now(), interval 3 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (1, '1958-10-05', 'MALE');
INSERT INTO tbl_persons (id, col_firstname, col_taxidnumber, col_date_create) VALUES(2, 'João Marcos', '54254667817', date_sub(now(), interval 4 day));
INSERT INTO tbl_person_detail (person_id, col_birthday, col_gender) VALUES (2, '1974-05-17', 'MALE');
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES(1, date_sub(now(), interval 5 day), null, null, 1, 1998.0, 1);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (2, 999.0, 1, 1);
INSERT INTO tbl_payments (order_id, col_status) VALUES (1, 1);
INSERT INTO tbl_payments_creditcard (col_number_installments, order_id) VALUES (6, 1);
INSERT INTO tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) VALUES (2, date_sub(now(), interval 6 day), null, null, 1, 1506.72, 2);
INSERT INTO tbl_order_items (col_quantity, col_subtotal, order_id, product_id) VALUES (1, 1506.72, 2, 3);
