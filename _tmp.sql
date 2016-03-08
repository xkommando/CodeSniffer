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
-- truncate table "procedure" cascade;
-- alter sequence "procedure_id_seq" RESTART  with 1;


ant 1.4 apache 1 license target jvm 1.1

tokens: 687189
lines: 6220440

 -- select  sum(token_count from "procedure" -- where id > 16080
-- select  sum(token_count) as TKS, sum(upper(lines) - lower(lines))as LNS from "procedure"  --where id > 16080
-- select  upper(lines)  from "procedure" where id > 16080

2469020 - 2388836 = 80184
tks 625236
per line 7.7975157138581263094881771924573

-- select file from "procedure" where id = 4444
 select BF.pathf1, PD.file 
   from __bellon_temp as BF 
    join "procedure" as PD
   on PD.id  > 0
   where position(PD.file in BF.pathf1) > 0

-- select count(1) from cclone_bench_bellon
-- CREATE RULE "my_table_on_duplicate_ignore" AS ON INSERT TO "cclone_bench_bellon"  WHERE EXISTS(SELECT 1 FROM __bellon_temp
 --            WHERE (match_pc_id1, match_pc_id2)=(NEW.func1_id, NEW.func2_id)) DO INSTEAD NOTHING;

-- DROP RULE "my_table_on_duplicate_ignore" ON "cclone_bench_bellon";
--  INSERT INTO cclone_bench_bellon(func1_id, func2_id, "type")SELECT match_pc_id1, match_pc_id2, type FROM __bellon_temp

-- SELECT distinct(match_pc_id1, match_pc_id2) ,"type" FROM __bellon_temp;

INSERT INTO manager.vin_manufacturer
(SELECT * FROM( VALUES
  ('935',' Citroën Brazil','Citroën'),
  ('ABC', 'Toyota', 'Toyota'),
  ('ZOM',' OM','OM')
  ) as tmp (vin_manufacturer_id, manufacturer_desc, make_desc)
  WHERE NOT EXISTS (
    --ignore anything that has already been inserted
    SELECT 1 FROM manager.vin_manufacturer m where m.vin_manufacturer_id = tmp.vin_manufacturer_id)
)

DROP RULE IF EXISTS "my_table_on_duplicate_ignore";
CREATE RULE "my_table_on_duplicate_ignore" AS ON INSERT TO "cclone_bench_bellon"
  WHERE EXISTS(SELECT 1 FROM cclone_bench_bellon 
                WHERE (func1_id, func2_id)=(NEW.func1_id, NEW.func2_id))
  DO INSTEAD NOTHING;
INSERT INTO cclone_bench_bellon(func1_id, func2_id, type) select match_pc_id1, match_pc_id2, type FROM __bellon_temp;
DROP RULE "my_table_on_duplicate_ignore" ON "cclone_bench_bellon";

INSERT INTO cclone_bench_bellon(func1_id, func2_id, type)
select match_pc_id1, match_pc_id2, type FROM __bellon_temp 
WHERE NOT EXISTS (SELECT 1 FROM cclone_bench_bellon as CCB WHERE CCB.func1_id = __bellon_temp.match_pc_id1 and CCB.func2_id = __bellon_temp.match_pc_id2)

DELETE FROM __bellon_temp USING __bellon_temp BT 
  WHERE __bellon_temp.func2_id = BT.func2_id AND __bellon_temp.func1_id = BT.func1_id AND
    __bellon_temp.match_pc_id1 < BT.match_pc_id1

    -- select BF.pathf1, BF.f2_line1, BF.f2_line2, PD.file, PD.lines, BF.distance1 from __bellon_temp  as BF
--	inner join "procedure" as PD on PD.id = BF.match_pc_id2 
-- where BF.id = 81

-- update __bellon_temp set match_pc_id2 = null, match_pc_id1 = null
-- select "type", count(type) from __bellon_temp as BF where BF.match_pc_id2 is not null and match_pc_id1 is not null group by type
-- select "type", count(type) from __bellon_temp as BF group by type

-- select * from __bellon_temp where id = 81
-- eclipse-jdtcore.cpf
-- select * from project
-- drop table __bellon_temp3
-- delete from __bellon_temp where match_pc_id1 is null
-- select count(1) from __bellon_temp -- where distance2 is null -- 45
-- BEGIN;
-- EXCEPTION
 --  WHEN unique_violation THEN
   
--END;
--COMMIT;
-- select count(1) from cclone_bench_bellon
-- CREATE RULE "my_table_on_duplicate_ignore" AS ON INSERT TO "cclone_bench_bellon"  WHERE EXISTS(SELECT 1 FROM __bellon_temp
 --            WHERE (match_pc_id1, match_pc_id2)=(NEW.func1_id, NEW.func2_id)) DO INSTEAD NOTHING;

-- DROP RULE "my_table_on_duplicate_ignore" ON "cclone_bench_bellon";
--  INSERT INTO cclone_bench_bellon(func1_id, func2_id, "type")SELECT match_pc_id1, match_pc_id2, type FROM __bellon_temp

-- SELECT distinct(match_pc_id1, match_pc_id2) ,"type" FROM __bellon_temp;
-- drop table cclone_bench_bellon
-- INSERT INTO cclone_bench_bellon(func1_id, func2_id, type)
-- select match_pc_id1, match_pc_id2, type FROM __bellon_temp 
-- WHERE NOT EXISTS (SELECT 1 FROM cclone_bench_bellon  WHERE cclone_bench_bellon.func1_id = __bellon_temp.match_pc_id1 and cclone_bench_bellon.func2_id = __bellon_temp.match_pc_id2)
--match_pc_id1 != 4386 and match_pc_id2 != 4389
--4744, 4745


-- DROP RULE "my_table_on_duplicate_ignore";


DELETE FROM __bellon_temp USING __bellon_temp BT 
  WHERE __bellon_temp.match_pc_id1 = BT.match_pc_id1 AND __bellon_temp.match_pc_id2 = BT.match_pc_id2 AND
    __bellon_temp.id < BT.id

select cbb.type as cc_type, count(1) as cc_count, poj.name
from cclone_bench_bellon as cbb
inner join "procedure" as pd on pd.id = cbb.func1_id
inner join "project" as poj on poj.id = pd.poj_id
group by (cbb.type, poj.name)
order by poj.name