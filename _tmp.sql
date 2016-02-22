-- create table jtest(
-- id serial primary key,
-- data json
-- )

-- insert into jtest(data)values('{"k1":123456, "key2":{"k21":5556, "k22":"789"}}')
-- select * from jtest

-- grant all privileges on database aser_codehouse to xkom
-- grant usage on schema public to xkom
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT all privileges on database aser_codehouse to xkom
-- GRANT SELECT ON ALL TABLES IN SCHEMA public TO xkom
-- GRANT CONNECT ON DATABASE aser_codehouse to xkom;

-- GRANT USAGE ON SCHEMA public to xkom; 
-- GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO xkom;
-- GRANT SELECT ON ALL TABLES IN SCHEMA public TO xkom;



-- CREATE TYPE lex_token AS("type" INT, "line" INT, "index" INT, "text" TEXT);
-- alter table jtest add column arr lex_token[]
-- insert into jtest(arr)values(ARRAY['(0, 15, 99, "public")'::lex_token, '(6, 154, 299, "class")'::lex_token])



