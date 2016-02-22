
-- schema for ASER code warehouse
-- internal use only
-- Bowen Cai feedback2bowen@outlook.com
-- 2/20/2016


-- CREATE USER $name  WITH ENCRYPTED PASSWORD 'psw';
-- GRANT CONNECT ON DATABASE aser_codehouse to $name;
-- GRANT USAGE ON SCHEMA $public to $name;
-- GRANT all privileges on database aser_codehouse to $name;

DROP TABLE IF EXISTS cc_type;
DROP TABLE IF EXISTS cc_bench_bellon;
DROP TABLE IF EXISTS cc_bench_Krutz_Le;

DROP TABLE IF EXISTS "call_relation";
DROP TABLE IF EXISTS "procedure";
DROP TABLE IF EXISTS "r_poj_lang";
DROP TABLE IF EXISTS "r_poj_tag";
DROP TABLE IF EXISTS "poj_tag";
DROP TABLE IF EXISTS language_src;
DROP TABLE IF EXISTS "project";
DROP TABLE IF EXISTS license;

DROP TABLE IF EXISTS license;
CREATE TABLE "license" (
  id             SERIAL       NOT NULL  PRIMARY KEY,
  "name"         VARCHAR(128) NOT NULL,
  "version"      SMALLINT     NOT NULL,
  "release_date" DATE         NOT NULL DEFAULT now(),
  "url"          TEXT
);

DROP TABLE IF EXISTS language_src;
CREATE TABLE language_src (
  id            SERIAL       NOT NULL  PRIMARY KEY,
  spec          VARCHAR(128),
  "name"        VARCHAR(128) NOT NULL,
  "version"     TEXT         NOT NULL,
  "release_date" DATE        NOT NULL,
  "encoding"    VARCHAR(32),
  license_id    INT          REFERENCES "license" (id) ON DELETE RESTRICT,
  developers    TEXT [],
  "description" TEXT,
  "url"         TEXT
);

COMMENT ON COLUMN language_src."developers" IS 'this is an array of string each represent a developer';
COMMENT ON COLUMN language_src."encoding" IS 'source code encoding';

DROP TABLE IF EXISTS "project";
CREATE TABLE "project" (
  id               SERIAL       NOT NULL  PRIMARY KEY,
  "name"           VARCHAR(128) NOT NULL,
  "branch"         VARCHAR(64),
  "version"        TEXT         NOT NULL,
  build_sys        VARCHAR(128),
  frontend         VARCHAR(128),
  "target"         VARCHAR(128),
  "timestamp"      TIMESTAMP    NOT NULL DEFAULT now(),
  license_id       INT          NOT NULL REFERENCES "license" (id)  ON DELETE RESTRICT ,

  "directory"      JSON,
  "class_hierachy" JSON,
  developers       TEXT [],
  "description"    TEXT,
  "url"            TEXT
);

COMMENT ON COLUMN "project".build_sys IS 'how the project is build, e.g. CMAKE, ANT, Gradle';
COMMENT ON COLUMN "project".frontend IS 'frontend(lexer, syntax analyzer) used in generating data of table "procedure" ';
COMMENT ON COLUMN "project"."target" IS 'target running platfrom, e.g, WIN32, Python 3, .Net';
COMMENT ON COLUMN "project"."timestamp" IS 'time when this project is loaded to this DB, not timestamp of the project itself';
COMMENT ON COLUMN "project"."directory" IS 'json representation of the file system perspective of this project';
COMMENT ON COLUMN "project"."class_hierachy" IS 'json representation of the object relation of this project';

DROP TABLE IF EXISTS "r_poj_lang";
CREATE TABLE "r_poj_lang" (
  id_poj          INT REFERENCES "project" (id)       ON DELETE CASCADE,
  id_lang         INT REFERENCES "language_src" (id)  ON DELETE CASCADE,
  PRIMARY KEY (id_poj, id_lang)
);

DROP TABLE IF EXISTS "poj_tag";
CREATE TABLE "poj_tag" (
  id              SERIAL NOT NULL  PRIMARY KEY,
  "value"         TEXT   NOT NULL
);
-- CREATE INDEX idx_poj_tag

DROP TABLE IF EXISTS "r_poj_tag";
CREATE TABLE "r_poj_tag" (
  id_poj          INT REFERENCES "project" (id) ON DELETE CASCADE,
  id_tag          INT REFERENCES "poj_tag" (id) ON DELETE CASCADE,
  PRIMARY KEY (id_poj, id_tag)
);

DROP TYPE IF EXISTS lex_token;
CREATE TYPE lex_token AS("type" INT, "line" INT, "index" INT, "text" TEXT);
-- to see what exactly the token type is, refer the lexer's vocabulary
-- e.g., for antlr, Java8Lexer.getVocabulary.getSymbolicName(int_type) -> sting

DROP TABLE IF EXISTS "procedure";
CREATE TABLE "procedure" (
  id            SERIAL       NOT NULL  PRIMARY KEY,
  "name"        VARCHAR(512) NOT NULL,
  "name_tokens" VARCHAR(1024),
  poj_id        INT REFERENCES "project" (id) ON DELETE RESTRICT,

  "file"        TEXT         NOT NULL,
  "package"     TEXT,
  "class"       TEXT,
  lines         INT4RANGE    NOT NULL,

  "src_impl"    TEXT         NOT NULL,
  src_comment   TEXT,
  "attributes"  TEXT [],
  "return_t"    TEXT,

  "token_seq"   lex_token[],
  "token_count" INT,
  "ast"         JSON,

  "note"        TEXT
);

-- full-text search
CREATE INDEX idx_fs_proc_name ON "procedure" USING GIN (to_tsvector('english', name_tokens));

COMMENT ON COLUMN "procedure"."name_tokens" IS 'a string of lowercased tokens decomposed from method name, used for full text search';
COMMENT ON COLUMN "procedure".lines IS 'INT4RANGE is a range data-type representing code location within the file';
COMMENT ON COLUMN "procedure"."src_impl" IS 'function implementation, including comments within the function';
COMMENT ON COLUMN "procedure".src_comment IS 'comments of this function';
COMMENT ON COLUMN "procedure"."attributes" IS 'attributes (annotation for Java) of this function';
COMMENT ON COLUMN "procedure"."return_t" IS 'string representation of the return type of this function';
COMMENT ON COLUMN "procedure"."token_seq" IS 'token sequence';
COMMENT ON COLUMN "procedure"."ast" IS 'json representation of the abstract syntax tree';
-- procedure {simple method signature, location information, source information}
-- note that different parser (frontend tool) may be used in generating token seq and ast for different functions in this tables
-- however in this code warehouse, only one kind of parser (parser with compatible interface) is used for each project,
--  and the parser name is store at the "project"(frontend).
--  i.e., for the same source code from a particular project, token seq and AST will not change no matter what parser is used.



DROP TABLE IF EXISTS call_relation;
CREATE TABLE call_relation (
  caller          INT       REFERENCES "procedure" (id) ON DELETE CASCADE,
  callee          INT       REFERENCES "procedure" (id) ON DELETE CASCADE,
  PRIMARY KEY (caller, callee)
);



