
DROP TABLE IF EXISTS cclone_type;
DROP TABLE IF EXISTS cclone_bench_bellon;
DROP TABLE IF EXISTS cclone_bench_Krutz_Le;


DROP TABLE IF EXISTS cclone_type;
CREATE TABLE cclone_type (
  "type_code"   SERIAL  NOT NULL PRIMARY KEY,
  "name"        VARCHAR(512),
  "description" TEXT
);

-- Bellon benchmark data
-- http://www.bauhaus-stuttgart.de/clones/
DROP TABLE IF EXISTS bellon_test;
CREATE TABLE cclone_bench_bellon (
  func1_id INT      NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  func2_id INT      NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  "type"   INT      NOT NULL REFERENCES cclone_type(type_code),
  PRIMARY KEY (func1_id, func2_id)
);

-- A Code Clone Oracle
-- http://dl.acm.org/citation.cfm?id=2597127
-- http://www.se.rit.edu/~dkrutz/cloneoracle/index.html
DROP TABLE IF EXISTS cclone_bench_Krutz_Le;
CREATE TABLE cclone_bench_Krutz_Le (
  func1_id INT      NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  func2_id INT      NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  "type"   INT      NOT NULL REFERENCES cclone_type(type_code),
  PRIMARY KEY (func1_id, func2_id)
);



