COPY MENU
FROM '/class/classes/cfalz002/Project/project/data/menu.csv'
WITH DELIMITER ';';

COPY USERS
FROM '/class/classes/cfalz002/Project/project/data/users.csv'
WITH DELIMITER ';';

COPY ORDERS
FROM '/class/classes/cfalz002/Project/project/data/orders.csv'
WITH DELIMITER ';';
ALTER SEQUENCE orders_orderid_seq RESTART 87257;

COPY ITEMSTATUS
FROM '/class/classes/cfalz002/Project/project/data/itemStatus.csv'
WITH DELIMITER ';';

