COPY USERS
FROM '/home/roshi/code/DataMarket/data/userdata.csv'
WITH DELIMITER ',';

COPY DATA
FROM '/home/roshi/code/DataMarket/data/data.csv'
WITH DELIMITER ',';

COPY PRIVILEGES
FROM '/home/roshi/code/DataMarket/data/privilege.csv'
WITH DELIMITER ',';

COPY SOURCE
FROM '/home/roshi/code/DataMarket/data/source.csv'
WITH DELIMITER ',';

COPY FILE
FROM '/home/roshi/code/DataMarket/data/file.csv'
WITH DELIMITER ',';

COPY DEVICE
FROM '/home/roshi/code/DataMarket/data/devicedata.csv'
WITH DELIMITER ',';
