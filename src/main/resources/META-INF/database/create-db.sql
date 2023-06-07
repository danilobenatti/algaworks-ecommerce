create function calc_average_invoicing(value double) returns boolean reads sql data return value > (select avg(col_total) from tbl_orders);

create function calc_total_by_person(id long) returns double reads sql data return (select sum(o.col_total) from tbl_orders o where o.person_id = id);

create procedure findname_product_by_id(in product_id integer, out product_name varchar(150)) begin select p.col_name into product_name from tbl_products p where p.id = product_id; end

create procedure purchases_above_average_by_year(in year_date year) begin select p.*, pd.* from tbl_persons p join tbl_person_detail pd on pd.person_id = p.id join tbl_orders o on o.person_id = p.id where o.col_status = 3 and year(o.col_date_create) = year_date group by o.person_id having sum(o.col_total) >= (select avg(total_by_person.sum_total) from (select sum(o1.col_total) as 'sum_total' from tbl_orders o1 where o1.col_status = 3 and year(o1.col_date_create) = year_date group by o1.person_id) as total_by_person); end

create procedure adjust_price_product(in product_id bigint, in percentage double, out newUnitPrice decimal(12,2)) begin update tbl_products p set p.col_unitPrice = p.col_unitPrice * (1 + percentage/100) where p.id = product_id; select p.col_unitprice into newUnitPrice from tbl_products p where p.id = product_id; end

create table tbl_product_shop (id bigint not null auto_increment, col_name varchar(150) not null, col_description mediumtext not null, col_unit tinyint default null, col_unitprice decimal(12,2) not null default '0.00', col_image mediumblob, col_date_create timestamp null default null, col_date_update timestamp null default null, primary key (id), unique key uk_product_shop__name (col_name), key idx_product_shop__name (col_name)) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table tbl_ecm_products (prd_id bigint not null auto_increment, prd_name varchar(150) not null, prd_description mediumtext not null, prd_unit tinyint default null, prd_unitprice decimal(12,2) not null default '0.00', prd_image mediumblob, prd_date_create timestamp null default null, prd_date_update timestamp null default null, primary key (prd_id), unique key uk_ecm_products__name (prd_name), key idx_ecm_products__name (prd_name)) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table tbl_erp_products (id bigint not null auto_increment, col_name varchar(150) not null, col_description mediumtext not null, col_unit tinyint default null, col_unitprice decimal(12,2) not null default '0.00', primary key (id), unique key uk_erp_products__name (col_name), key idx_erp_products__name (col_name)) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;

create table tbl_ecm_category (cat_id bigint not null auto_increment, cat_name varchar(100) not null, cat_parent_category_id bigint default null, cat_date_create timestamp null default null, cat_date_update timestamp null default null, primary key (cat_id), unique key uk_ecm_category__name (cat_name), key fk_ecm_category__category_id (cat_parent_category_id), constraint fk_ecm_category__category_id foreign key (cat_parent_category_id) references tbl_ecm_category (cat_id) on delete cascade) engine=InnoDB auto_increment=0 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci;
