


DROP TABLE IF EXISTS cclone_bench_bellon;
DROP TABLE IF EXISTS cclone_bench_krutz_le;


-- INSERT INTO cclone_type_bellon("type_code", "name", "description") VALUES
--   (1, 'Type 1', 'Type 1 is an exact copy without modifications (except for white space and comments).'),
--   (2, 'Type 2', 'Type 2 is a syntactically identical copy; only variable, type, or function identifiers were changed.'),
--   (3, 'Type 3', 'Type 3 is a copy with further modifications; statements were changed, added, or removed.');

-- Bellon benchmark data
-- http://www.bauhaus-stuttgart.de/clones/
DROP TABLE IF EXISTS cclone_bench_bellon;
CREATE TABLE cclone_bench_bellon (
  func1_id INT          NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  func2_id INT          NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  "type"   SMALLINT     NOT NULL,
  PRIMARY KEY (func1_id, func2_id)
);


-- INSERT INTO cclone_type_krutz("type_code", "name", "description") VALUES
--   (1, 'Type 1', 'Type-1: Identical code fragments except for variations in whitespace, layout and comments.'),
--   (2, 'Type 2', 'Type-2: Syntactically identical fragments except for variations in identifiers, literals, types, whitespace, layout and comments.'),
--   (3, 'Type 3', 'Type-3: Copied fragments with further modifications such as changed, added or removed statements, in addition to variations in identifiers, literals, types, whitespace, layout and comments.'),
--   (4, 'Type 4', 'Type-4: Two or more code fragments that perform the same computation but are implemented by different syntactic variants.');
--

-- A Code Clone Oracle
-- http://dl.acm.org/citation.cfm?id=2597127
-- http://www.se.rit.edu/~dkrutz/cloneoracle/index.html
DROP TABLE IF EXISTS cclone_bench_krutz_le;
CREATE TABLE cclone_bench_krutz_le (
  func1_id INT            NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  func2_id INT            NOT NULL REFERENCES "procedure" (id) ON DELETE CASCADE,
  "type"   SMALLINT       NOT NULL,
  PRIMARY KEY (func1_id, func2_id)
);



