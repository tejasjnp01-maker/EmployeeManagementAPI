CREATE DATABASE IF NOT EXISTS retail_db;
USE retail_db;
CREATE TABLE IF NOT EXISTS Product (
  product_id INT PRIMARY KEY,
  product_name VARCHAR(100) NOT NULL,
  category VARCHAR(50) NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  price DOUBLE NOT NULL
);
INSERT IGNORE INTO Product VALUES
(101,'Keyboard','Electronics',15,1200.00),
(102,'Mouse','Electronics',8,650.00),
(103,'Notebook','Stationery',30,55.00);
