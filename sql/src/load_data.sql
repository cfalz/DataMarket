COPY USERS
FROM '/home/divergent/CS/DataMarket/data/userdata.csv'
WITH DELIMITER ',';

COPY SOURCE
FROM '/home/divergent/CS/DataMarket/data/source.csv'
WITH DELIMITER ',';

COPY DATA
FROM '/home/divergent/CS/DataMarket/data/data.csv'
WITH DELIMITER ',';

COPY PRIVILEGES
FROM '/home/divergent/CS/DataMarket/data/privilege.csv'
WITH DELIMITER ',';

COPY FILE
FROM '/home/divergent/CS/DataMarket/data/file.csv'
WITH DELIMITER ',';

COPY DEVICE
FROM '/home/divergent/CS/DataMarket/data/devicedata.csv'
WITH DELIMITER ',';

