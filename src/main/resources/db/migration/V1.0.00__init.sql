CREATE TABLE store (
  store_id BIGINT  NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (store_id)
)
  AUTO_INCREMENT = 1;

CREATE TABLE product (
  product_id BIGINT NOT NULL AUTO_INCREMENT,
  store_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(100) NOT NULL,
  sku VARCHAR(10) NOT NULL,
  price DECIMAL(5, 2) UNSIGNED NOT NULL,
  PRIMARY KEY (product_id),
  FOREIGN KEY (store_id) REFERENCES store (store_id)
)
  AUTO_INCREMENT = 1;

CREATE TABLE stock (
  store_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  count INTEGER NOT NULL,
  PRIMARY KEY (store_id, product_id),
  FOREIGN KEY (store_id) REFERENCES store (store_id),
  FOREIGN KEY (product_id) REFERENCES product (product_id)
)
