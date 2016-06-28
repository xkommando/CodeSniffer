
-- useful queries

-- select count(*), poj.id, poj.name from procedure as PD inner join project as POJ on POJ.id = PD.poj_id group by poj.id
-- select count (1) from src_file



select
  count(pd1.id), src.id, SRC.dir || '/' || SRC.name
FROM "procedure" AS PD1
  INNER JOIN "src_file" AS SRC ON SRC.id = PD1.srcfile_id
group by src.id


SELECT func1.id AS func_id,
       (abs(lower(func1.lines) - belo.f1_line1) * 3 + abs(upper(func1.lines) - belo.f1_line2)) AS dist1
FROM "procedure" AS func1
  INNER JOIN "src_file" AS SRC ON func1.id = func1.srcfile_id
  FULL OUTER JOIN __bellon_temp AS belo ON position((SRC.dir || '/' || SRC.name) IN belo.pathf1) > 0
WHERE func1.poj_id = 1
      AND belo.id = 14566
ORDER BY dist1 ASC --LIMIT 10

select CBB.type as cc_type, count(1) as CC_count, poj.name
from cclone_bench_bellon as CBB
  inner join "procedure" as PD on PD.id = CBB.func1_id
  inner join "project" as POJ on poj.id = PD.poj_id
group by (cbb.type, poj.name)
order by (poj.name)

select poj.name, count(func.id) FROM procedure as func INNER JOIN project as poj on poj.id = func.poj_id group by poj.name

select poj.name, count(src.id) FROM src_file as src INNER JOIN project as poj on poj.id = src.poj_id group by poj.name











