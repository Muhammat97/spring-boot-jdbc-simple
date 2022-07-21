CREATE DATABASE cooperative;

CREATE SCHEMA test;

-- test.cust definition

CREATE TABLE test.cust (
	cust_id serial4 NOT NULL,
	cust_uuid bpchar(36) NOT NULL,
	full_name varchar(60) NOT NULL,
	identity_num varchar(20) NULL,
	birth_date date NULL,
	birth_place varchar(40) NULL,
	address varchar(250) NULL,
	neighbourhood_num varchar(3) NULL,
	hamlet_num varchar(3) NULL,
	village varchar(40) NULL,
	subdistrict varchar(40) NULL,
	city varchar(40) NULL,
	province varchar(40) NULL,
	zip_code bpchar(5) NULL,
	created_at timestamp NULL DEFAULT now(),
	created_by varchar(100) NULL,
	updated_at timestamp NULL,
	updated_by varchar(100) NULL,
	CONSTRAINT cust_pk PRIMARY KEY (cust_id),
	CONSTRAINT cust_un UNIQUE (cust_uuid)
);
CREATE INDEX cust_cust_uuid_idx ON test.cust USING btree (cust_uuid);


-- test.acct definition

CREATE TABLE test.acct (
	acct_id serial4 NOT NULL,
	acct_name varchar(20) NOT NULL,
	acct_type varchar(20) NOT NULL,
	currency varchar(5) NULL DEFAULT 'IDR',
	CONSTRAINT acct_pk PRIMARY KEY (acct_id),
	CONSTRAINT acct_un UNIQUE (acct_name)
);


-- test.cust_acct definition

CREATE TABLE test.cust_acct (
	cust_acct_id serial4 NOT NULL,
	cust_id int4 NOT NULL,
	acct_id int4 NOT NULL,
	balance numeric(18, 2) NULL DEFAULT 0,
	created_at timestamp NULL DEFAULT now(),
	created_by varchar(100) NULL,
	updated_at timestamp NULL,
	updated_by varchar(100) NULL,
	CONSTRAINT cust_acct_pk PRIMARY KEY (cust_acct_id)
);


-- test.cust_acct foreign keys

ALTER TABLE test.cust_acct ADD CONSTRAINT cust_acct_fk FOREIGN KEY (cust_id) REFERENCES test.cust(cust_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE test.cust_acct ADD CONSTRAINT cust_acct_fk_1 FOREIGN KEY (acct_id) REFERENCES test.acct(acct_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- test.tran definition

CREATE TABLE test.tran (
	tran_uuid bpchar(36) NOT NULL,
	cust_acct_id int4 NOT NULL,
	tran_type varchar(20) NOT NULL,
	tran_amount numeric(18, 2) NOT NULL,
	tran_date timestamp NULL DEFAULT now(),
	created_by varchar(100) NULL,
	CONSTRAINT tran_pk PRIMARY KEY (tran_uuid)
);
CREATE INDEX cust_tran_tran_date_idx ON test.tran USING btree (tran_date);


-- test.tran foreign keys

ALTER TABLE test.tran ADD CONSTRAINT tran_fk FOREIGN KEY (cust_acct_id) REFERENCES test.cust_acct(cust_acct_id) ON DELETE CASCADE ON UPDATE CASCADE;