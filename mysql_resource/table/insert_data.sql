-- USE
USE dev_training

-- INSERT
load data local infile "./accounts.csv" into table accounts fields terminated by ',' optionally enclosed by '"';
