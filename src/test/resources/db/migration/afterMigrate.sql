DELETE FROM order_product;
DELETE FROM product_order;
DELETE FROM stock;
DELETE FROM product;
DELETE FROM store;

-- Stores
INSERT INTO store (name)
  VALUES ('First Test Store');
INSERT INTO store (name)
  VALUES ('Second Test Store');
INSERT INTO store (name)
  VALUES ('Store');

-- Products
INSERT INTO product (store_id, name, description, sku, price)
  VALUES (1, 'First product', 'First product description', 'AAA111', 123.45);
INSERT INTO product (store_id, name, description, sku, price)
  VALUES (1, 'Product', 'Second product description', 'AAA112', 678.99);

-- Stock
INSERT INTO stock (store_id, product_id, count)
  VALUES (1, 1, 100);
INSERT INTO stock (store_id, product_id, count)
  VALUES (1, 2, 50);

-- Order
INSERT INTO product_order (store_id, first_name, last_name, email, phone, status)
  VALUES (1, 'John', 'Doe', 'email@domain.com', '1234567891', 'ORDERED');

INSERT INTO order_product (order_id, product_id, count)
  VALUES (1, 1, 20);