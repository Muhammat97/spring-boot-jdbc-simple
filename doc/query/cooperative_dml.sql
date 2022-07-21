CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO test.acct(acct_name, acct_type, currency) VALUES
('SAVINGS', 'DEBIT', 'IDR'),
('LOAN', 'CREDIT', 'IDR');


INSERT INTO test.cust(cust_uuid, full_name, birth_date, address, neighbourhood_num, hamlet_num) VALUES
(gen_random_uuid(), 'Wawan Setiawan', '1990-01-10', 'kompleks Asia Serasi', null, null),
(gen_random_uuid(), 'Teguh Sudibyantoro', '1991-02-10', 'Jalan Pemekaran No 99', null, null),
(gen_random_uuid(), 'Joko Widodo', '1992-03-10', 'Dusun Pisang', '10', '20');


INSERT INTO test.cust_acct(cust_id, acct_id, balance) VALUES
((SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan'), (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'), 0),
((SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan'), (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN'), 0),
((SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro'), (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'), 0),
((SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro'), (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN'), 0),
((SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo'), (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'), 0),
((SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo'), (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN'), 0);


INSERT INTO test.tran(tran_uuid, cust_acct_id, tran_type, tran_amount, tran_date) VALUES
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS')), 'DEPOSIT', 1000000, '2020-08-17'),
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS')), 'DEPOSIT', 5000000, '2020-08-18'),
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN')), 'LOAN', 2000000, '2020-09-30'),
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN')), 'REFUND', 1000000, '2020-11-10'),
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS')), 'DEPOSIT', 5000000, '2020-12-01'),
(gen_random_uuid(), (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS')), 'WITHDRAWAL', 2000000, '2020-12-01');


UPDATE test.cust_acct SET balance = balance + 1000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'));
UPDATE test.cust_acct SET balance = balance + 5000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'));
UPDATE test.cust_acct SET balance = balance + 2000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN'));
UPDATE test.cust_acct SET balance = balance - 1000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Joko Widodo') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'LOAN'));
UPDATE test.cust_acct SET balance = balance + 5000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Wawan Setiawan') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'));
UPDATE test.cust_acct SET balance = balance - 2000000 WHERE cust_acct_id = (SELECT cust_acct_id FROM test.cust_acct WHERE cust_id = (SELECT cust_id FROM test.cust WHERE full_name = 'Teguh Sudibyantoro') AND acct_id = (SELECT acct_id FROM test.acct WHERE acct_name = 'SAVINGS'));