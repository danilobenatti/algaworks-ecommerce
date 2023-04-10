
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
       foreign key fk_paymentsbankslip__order_id;

    alter table tbl_payments_creditcard 
       drop 
       foreign key fk_paymentscreditcard__order_id;

    alter table tbl_person_detail 
       drop 
       foreign key fk_persondetail__person_id;

    alter table tbl_person_phones 
       drop 
       foreign key fk_personphone__person_id;

    alter table tbl_product_attribute 
       drop 
       foreign key fk_productattribute__product_id;

    alter table tbl_product_category 
       drop 
       foreign key fk_productcategory__category_id;

    alter table tbl_product_category 
       drop 
       foreign key fk_productcategory__product_id;

    alter table tbl_product_stocks 
       drop 
       foreign key fk_productstock__product_id;

    alter table tbl_product_tag 
       drop 
       foreign key fk_producttag__product_id;

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

    drop table if exists tbl_product_stocks;

    drop table if exists tbl_product_tag;

    drop table if exists tbl_products;
