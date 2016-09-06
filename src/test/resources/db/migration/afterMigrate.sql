-- DELETE FROM purchase_order_item;
-- DELETE FROM purchase_order;
DELETE FROM stock;
DELETE FROM product;
DELETE FROM store;

-- Stores
INSERT INTO store (name) VALUES ('First Test Store');
INSERT INTO store (name) VALUES ('Second Test Store');
INSERT INTO store (name) VALUES ('Store');

-- Products
INSERT INTO product (store_id, name, description, sku, price)
  VALUES (1, 'First product', 'First product description', 'AAA111', 123.45);
INSERT INTO product (store_id, name, description, sku, price)
  VALUES (1, 'Product', 'Second product description', 'AAA112', 678.99);

-- Stock
INSERT INTO stock (store_id, product_id, count)
  VALUES (1, 1, 100)