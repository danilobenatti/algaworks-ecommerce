
    alter table tbl_categories 
       drop 
       foreign key fk_category__category_id;

    alter table tbl_invoices 
       drop 
       foreign key fk_invoice__order_id;

    alter table tbl_order_items 
       drop 
       foreign key fk_orderitem__order_id;

    alter table tbl_order_items 
       drop 
       foreign key fk_orderitem__product_id;

    alter table tbl_orders 
       drop 
       foreign key fk_order__person_id;

    alter table tbl_payments 
       drop 
       foreign key fk_payments__order_id;

    alter table tbl_payments_bankslip 
       drop 
       foreign key FKokctom4scoc38u6n0qcbq3ppr;

    alter table tbl_payments_creditcard 
       drop 
       foreign key FK1p3rpfkr2ff924ko8ogrojtvu;

    alter table tbl_person_detail 
       drop 
       foreign key fk_persondetail__person_id;

    alter table tbl_person_phones 
       drop 
       foreign key fk_personphone__person_id;

    alter table tbl_product_attribute 
       drop 
       foreign key fk_product_attribute__product_id;

    alter table tbl_product_category 
       drop 
       foreign key fk_product_category__category_id;

    alter table tbl_product_category 
       drop 
       foreign key fk_product_category__product_id;

    alter table tbl_product_image 
       drop 
       foreign key fk_product_image__product_id;

    alter table tbl_product_stocks 
       drop 
       foreign key fk_productstock__product_id;

    alter table tbl_product_tag 
       drop 
       foreign key fk_product_tag__product_id;

    drop table if exists tbl_categories;

    drop table if exists tbl_invoices;

    drop table if exists tbl_order_items;

    drop table if exists tbl_orders;

    drop table if exists tbl_payments;

    drop table if exists tbl_payments_bankslip;

    drop table if exists tbl_payments_creditcard;

    drop table if exists tbl_person_detail;

    drop table if exists tbl_person_phones;

    drop table if exists tbl_persons;

    drop table if exists tbl_product_attribute;

    drop table if exists tbl_product_category;

    drop table if exists tbl_product_image;

    drop table if exists tbl_product_stocks;

    drop table if exists tbl_product_tag;

    drop table if exists tbl_products;

    drop function if exists calc_average_invoicing;

    drop function if exists calc_total_by_person;

    drop procedure if exists findname_product_by_id;

    drop procedure if exists purchases_above_average_by_year;

    drop procedure if exists adjust_price_product;

    drop table if exists tbl_product_shop;

    drop table if exists tbl_ecm_products;

    drop table if exists tbl_erp_products;

    drop table if exists tbl_ecm_category;
