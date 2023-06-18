
    create table tbl_categories (
        col_date_create timestamp null,
        col_date_update timestamp null,
        col_version bigint,
        id bigint not null auto_increment,
        parent_category_id bigint,
        col_name varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table tbl_invoices (
        col_issuedatetime datetime(6) not null,
        order_id bigint not null,
        col_xml mediumblob,
        primary key (order_id)
    ) engine=InnoDB;

    create table tbl_order_items (
        col_quantity double default 0 not null,
        col_subtotal decimal(38,2) not null,
        order_id bigint not null,
        product_id bigint not null,
        primary key (order_id, product_id)
    ) engine=InnoDB;

    create table tbl_orders (
        col_estate varchar(2),
        col_status tinyint not null,
        col_total decimal(38,2) not null,
        col_date_create timestamp null,
        col_date_update timestamp null,
        col_execution_date timestamp null,
        id bigint not null auto_increment,
        person_id bigint not null,
        col_number varchar(10),
        col_zipcode varchar(10),
        col_city varchar(20),
        col_complement varchar(20),
        col_district varchar(20),
        col_street varchar(50),
        primary key (id)
    ) engine=InnoDB;

    create table tbl_payments (
        col_payment_type TINYINT(1) not null,
        col_status tinyint,
        col_date_create timestamp null,
        col_date_update timestamp null,
        col_version bigint,
        order_id bigint not null,
        primary key (order_id)
    ) engine=InnoDB;

    create table tbl_payments_bankslip (
        col_expirationdate date,
        col_payday date,
        order_id bigint not null,
        primary key (order_id)
    ) engine=InnoDB;

    create table tbl_payments_creditcard (
        col_number_installments integer,
        order_id bigint not null,
        primary key (order_id)
    ) engine=InnoDB;

    create table tbl_person_detail (
        col_birthday date,
        col_gender enum ('FEMALE','MALE'),
        person_id bigint not null,
        col_email varchar(150),
        primary key (person_id)
    ) engine=InnoDB;

    create table tbl_person_emails (
        person_id bigint not null,
        col_email varchar(150) not null,
        primary key (person_id, col_email)
    ) engine=InnoDB;

    create table tbl_person_phones (
        col_type char(1) not null,
        person_id bigint not null,
        col_number varchar(20) not null,
        primary key (col_type, person_id)
    ) engine=InnoDB;

    create table tbl_persons (
        col_date_create timestamp null,
        col_date_update timestamp null,
        col_version bigint,
        id bigint not null auto_increment,
        col_taxidnumber varchar(14) not null,
        col_firstname varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table tbl_product_attribute (
        product_id bigint not null,
        col_description varchar(100) not null,
        col_value varchar(255) not null
    ) engine=InnoDB;

    create table tbl_product_category (
        category_id bigint not null,
        product_id bigint not null,
        primary key (category_id, product_id)
    ) engine=InnoDB;

    create table tbl_product_image (
        col_extension tinyint check (col_extension between 0 and 5),
        product_id bigint not null,
        col_filename varchar(100) not null,
        blob_file mediumblob not null
    ) engine=InnoDB;

    create table tbl_product_stocks (
        col_quantity double default 0 not null,
        col_date_create timestamp null,
        col_date_update timestamp null,
        product_id bigint not null,
        primary key (product_id)
    ) engine=InnoDB;

    create table tbl_product_tag (
        product_id bigint not null,
        col_tag varchar(50) not null
    ) engine=InnoDB;

    create table tbl_products (
        col_active varchar(3) not null,
        col_unit tinyint not null,
        col_unitprice decimal(12,2) unsigned not null default 0 not null,
        col_date_create timestamp null,
        col_date_update timestamp null,
        col_version bigint,
        id bigint not null auto_increment,
        col_name varchar(150) not null,
        col_description text not null,
        col_image mediumblob,
        primary key (id)
    ) engine=InnoDB;

    alter table tbl_categories 
       add constraint uk_categories__name unique (col_name);

    create index idx_person__firstname 
       on tbl_persons (col_firstname);

    alter table tbl_persons 
       add constraint uk_person__taxidnumber unique (col_taxidnumber);

    create index idx_product__name 
       on tbl_products (col_name);

    alter table tbl_products 
       add constraint uk_product__name unique (col_name);

    alter table tbl_categories 
       add constraint fk_category__category_id 
       foreign key (parent_category_id) 
       references tbl_categories(id) 
       on delete cascade;

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
       add constraint FKokctom4scoc38u6n0qcbq3ppr 
       foreign key (order_id) 
       references tbl_payments (order_id);

    alter table tbl_payments_creditcard 
       add constraint FK1p3rpfkr2ff924ko8ogrojtvu 
       foreign key (order_id) 
       references tbl_payments (order_id);

    alter table tbl_person_detail 
       add constraint fk_persondetail__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_person_emails 
       add constraint fk_personemail__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_person_phones 
       add constraint fk_personphone__person_id 
       foreign key (person_id) 
       references tbl_persons (id);

    alter table tbl_product_attribute 
       add constraint fk_product_attribute__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_category 
       add constraint fk_product_category__category_id 
       foreign key (category_id) 
       references tbl_categories(id) 
       on delete cascade;

    alter table tbl_product_category 
       add constraint fk_product_category__product_id 
       foreign key (product_id) 
       references tbl_products(id) 
       on delete cascade;

    alter table tbl_product_image 
       add constraint fk_product_image__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_stocks 
       add constraint fk_productstock__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    alter table tbl_product_tag 
       add constraint fk_product_tag__product_id 
       foreign key (product_id) 
       references tbl_products (id);

    create function calc_average_invoicing(value double) returns boolean reads sql data return value > (select avg(col_total) from tbl_orders);

    create function calc_total_by_person(id long) returns double reads sql data return (select sum(o.col_total) from tbl_orders o where o.person_id = id);

    create procedure findname_product_by_id(in product_id integer, out product_name varchar(150)) begin select p.col_name into product_name from tbl_products p where p.id = product_id; end;

    create procedure purchases_above_average_by_year(in year_date year) begin select p.*, pd.* from tbl_persons p join tbl_person_detail pd on pd.person_id = p.id join tbl_orders o on o.person_id = p.id where o.col_status = 3 and year(o.col_date_create) = year_date group by o.person_id having sum(o.col_total) >= (select avg(total_by_person.sum_total) from (select sum(o1.col_total) as 'sum_total' from tbl_orders o1 where o1.col_status = 3 and year(o1.col_date_create) = year_date group by o1.person_id) as total_by_person); end;

    create procedure adjust_price_product(in product_id bigint, in percentage double, out newUnitPrice decimal(12,2)) begin update tbl_products p set p.col_unitPrice = p.col_unitPrice * (1 + percentage/100) where p.id = product_id; select p.col_unitprice into newUnitPrice from tbl_products p where p.id = product_id; end;

    create table tbl_product_shop (
        id bigint not null auto_increment,
        col_version bigint not null default 0,
        col_name varchar(150) not null,
        col_description mediumtext not null,
        col_unit tinyint default null,
        col_unitprice decimal(12,2) not null default '0.00',
        col_image mediumblob,
        col_active varchar(3) not null,
        col_date_create timestamp null default null,
        col_date_update timestamp null default null,
        primary key (id),
        unique key uk_product_shop__name (col_name),
        key idx_product_shop__name (col_name)
    ) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

    create table tbl_ecm_products (
        prd_id bigint not null auto_increment,
        prd_version bigint not null default 0,
        prd_name varchar(150) not null,
        prd_description mediumtext not null,
        prd_unit tinyint default null,
        prd_unitprice decimal(12,2) not null default '0.00',
        prd_image mediumblob,
        prd_active varchar(3) not null,
        prd_date_create timestamp null default null,
        prd_date_update timestamp null default null,
        primary key (prd_id),
        unique key uk_ecm_products__name (prd_name),
        key idx_ecm_products__name (prd_name)
    ) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

    create table tbl_erp_products (
        id bigint not null auto_increment,
        col_version bigint not null default 0,
        col_name varchar(150) not null,
        col_description mediumtext not null,
        col_unit tinyint default null,
        col_unitprice decimal(12,2) not null default '0.00',
        col_active varchar(3) not null,
        primary key (id),
        unique key uk_erp_products__name (col_name),
        key idx_erp_products__name (col_name)
    ) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

    create table tbl_ecm_category (
        cat_id bigint not null auto_increment,
        cat_name varchar(100) not null,
        cat_parent_category_id bigint default null,
        cat_date_create timestamp null default null,
        cat_date_update timestamp null default null,
        primary key (cat_id),
        unique key uk_ecm_category__name (cat_name),
        key fk_ecm_category__category_id (cat_parent_category_id),
        constraint fk_ecm_category__category_id foreign key (cat_parent_category_id) references tbl_ecm_category (cat_id) on delete cascade
    ) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

    create view purchases_above_average_by_current_year as select p.*, pd.* from tbl_persons p join tbl_person_detail pd on pd.person_id = p.id join tbl_orders o on o.person_id = p.id where o.col_status = 3 and year(o.col_date_create) = year(current_date()) group by o.person_id having sum(o.col_total) >= (select avg(total_by_person.sum_total) from (select sum(o1.col_total) as 'sum_total' from tbl_orders o1 where o1.col_status = 3 and year(o1.col_date_create) = year(current_date()) group by o1.person_id) as total_by_person);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (1, 0, 'Eletrônicos', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (2, 0, 'Informática', 1);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (3, 0, 'Escritório', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (4, 0, 'Literatura', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (5, 0, 'Eletrodomésticos', null);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (6, 0, 'Notebooks', 2);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (7, 0, 'Smartphones', 1);
insert into tbl_categories (id, col_version, col_name, parent_category_id) values (8, 0, 'Câmeras', 1);
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
insert into tbl_product_category (product_id, category_id) values (1, 1), (1, 4), (3, 1), (3, 2), (3, 8), (4, 3), (6, 1), (6, 2), (6, 8), (7, 1), (8, 1);
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values (1, 0, 'Luiz Fernando', '214.709.598-28', date_sub(now(), interval(3) day));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (1, 'luiz_fernando@email.com', date_add(date_sub(current_date(), interval(65) year), interval(120) day), 'MALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (1, '+55(11)97777-6666', 'M');
insert into tbl_person_emails (person_id, col_email) values (1, 'luizfernando@mail.com');
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(2, 0, 'João Marcos', '542.546.678-17', date_sub(now(), interval(4) week));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (2, 'joao@mail.com', date_sub(date_sub(current_date(), interval(49) year), interval(21) day), 'MALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (2, '(049)9 4444-3333', 'H');
insert into tbl_person_emails (person_id, col_email) values (2, 'joao_marcos@qmail.org'), (2, 'jmarcos@kmail.us');
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(3, 0, 'Maria Paula', '535.587.950-08', date_sub(now(), interval(8) month));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (3, 'mariapaula@send.com', date_add(date_sub(current_date(), interval(60) year), interval(75) day), 'FEMALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (3, '+55(011)3232-4545', 'W');
insert into tbl_person_emails (person_id, col_email) values (3, 'mariapaula@mail.org');
insert into tbl_persons (id, col_version, col_firstname, col_taxidnumber, col_date_create) values(4, 0, 'Maria', '530.412.530-46', date_sub(now(), interval(2) year));
insert into tbl_person_detail (person_id, col_email, col_birthday, col_gender) values (4, 'maria99@email.com', date_add(date_sub(current_date(), interval(60) year), interval(120) day), 'FEMALE');
insert into tbl_person_phones (person_id, col_number, col_type) values (4, '+55(088)9999-7777', 'W');
insert into tbl_person_emails (person_id, col_email) values (4, 'maria99@hmail.tw'), (4, 'maria99@ymail.es');
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(1, date_sub(now(), interval 2 day), null, null, 1, 505.0, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 499.5, 1, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 5.5, 1, 4);
insert into tbl_payments (order_id, col_version, col_status, col_payment_type) values (1, 0, 1, 1);
insert into tbl_payments_creditcard (col_number_installments, order_id) values (6, 1);
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values (2, date_sub(now(), interval 2 week), null, null, 2, 1512.22, 2);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 1506.72, 2, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 5.5, 2, 4);
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(3, date_sub(now(), interval 7 month), null, null, 1, 13.32, 1);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (2, 11.0, 3, 4);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 2.32, 3, 5);
insert into tbl_payments (order_id, col_version, col_status, col_payment_type) values (3, 0, 1, 1);
insert into tbl_payments_creditcard (col_number_installments, order_id) values (2, 3);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (3, date_sub(now(), interval 7 month), '<xml />');
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(4, date_sub(now(), interval 1 year), null, date_sub(now(), interval 11 month), 3, 3500.0, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 3500.0, 4, 6);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (4, 0, date_sub(now(), interval 11 month), 3, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 5 day), date_sub(now(), interval 11 month), 4);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (4, date_sub(now(), interval 1 year), '<xml />');
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(5, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 3, 499.5, 3);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (1, 499.5, 5, 1);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (5, 0, date_sub(now(), interval 2 day), 3, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 5);
insert into tbl_orders (id, col_date_create, col_date_update, col_execution_date, col_status, col_total, person_id) values(6, date_sub(now(), interval 3 day), null, date_sub(now(), interval 2 day), 1, 1599.0, 2);
insert into tbl_order_items (col_quantity, col_subtotal, order_id, product_id) values (2, 1599.0, 6, 1);
insert into tbl_payments (order_id, col_version, col_date_create, col_status, col_payment_type) values (6, 0, date_sub(now(), interval 2 day), 1, 0);
insert into tbl_payments_bankslip (col_expirationdate, col_payday, order_id) values (date_add(now(), interval 3 day), date_sub(now(), interval 2 day), 6);
insert into tbl_invoices (order_id, col_issuedatetime, col_xml) values (6, sysdate(), '<xml />');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (101, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (103, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (104, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (105, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (106, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (107, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_product_shop (id, col_description, col_name, col_unit, col_unitprice, col_date_create, col_image, col_active) values (108, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (201, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, date_sub(now(), interval(1) year), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (203, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, date_sub(now(), interval(2) month), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (204, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, date_sub(now(), interval(3) day), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (205, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, date_sub(now(), interval(4) week), null, 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (206, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, sysdate(), LOAD_FILE('C:/tmp/canon80d.jpg'), 'yes');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (207, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_ecm_products (prd_id, prd_description, prd_name, prd_unit, prd_unitprice, prd_date_create, prd_image, prd_active) values (208, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, date_sub(now(), interval(12) hour), null, 'no');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (301, 'Conheça o novo Kindle, agora com mais memória', 'Kindle', 1, 799.5, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (303, 'Câmera de ação e alto desempenho', 'Câmera GoPro Hero', 1, 1606.72, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (304, 'Fita Adesiva Alta Aderência','Fita ColaTudo', 1, 5.0, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (305, 'Corda de Tecido Poliester Trançada Reforçada','Corda de Nylon para Varal', 3, 2.32, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (306, 'O melhor ajuste de foco','Câmera Canon 80D', 1, 3500.0, 'yes');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (307, 'Produto Teste 7 Nunca Vendido','Produto Teste 7', 1, 10.0, 'no');
insert into tbl_erp_products (id, col_description, col_name, col_unit, col_unitprice, col_active) values (308, 'Produto Teste 8 Nunca Vendido','Produto Teste 8', 1, 10.0, 'no');
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (201, 'Eletrônicos', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (202, 'Informática', 201);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (203, 'Escritório', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (204, 'Literatura', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (205, 'Eletrodomésticos', null);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (206, 'Notebooks', 202);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (207, 'Smartphones', 201);
insert into tbl_ecm_category (cat_id, cat_name, cat_parent_category_id) values (208, 'Câmeras', 201);
