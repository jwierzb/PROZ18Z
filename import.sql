
PRAGMA foreign_keys = ON;
DROP TABLE user;
DROP TABLE device;
DROP TABLE value;
DROP TABLE variable;

PRAGMA foreign_keys = ON;

CREATE TABLE "user" (
  "user_id" INTEGER PRIMARY KEY AUTOINCREMENT ,
  "user_name" VARCHAR(30) NOT NULL,
  "user_password" VARCHAR(30) NOT NULL,
  "last_login" DATETIME DEFAULT NULL
);

CREATE TABLE "device" (
  "device_id" INTEGER PRIMARY KEY AUTOINCREMENT ,
  "device_name" VARCHAR(200) NOT NULL,
  "description" VARCHAR(200) DEFAULT NULL,
  "enabled" BIT NOT NULL DEFAULT 1,
  "tags" VARCHAR(200) DEFAULT NULL,
  "user_id" INTEGER NOT NULL REFERENCES "user" ("user_id"),
  "created_at" TIMESTAMP DEFAULT (strftime('%s', datetime('now', 'localtime'))),
  "last_activity" TIMESTAMP DEFAULT (strftime('%s', datetime('now', 'localtime'))),
  CONSTRAINT "fk_on" FOREIGN KEY ("user_id") REFERENCES "user" ("user_id")
);



CREATE TABLE "variable" (
  "variable_id" INTEGER PRIMARY KEY AUTOINCREMENT ,
  "variable_name" VARCHAR(200) NOT NULL,
  "description" VARCHAR(200) NOT NULL,
  "tags" VARCHAR(200) NOT NULL,
  "unit" VARCHAR(50) NOT NULL,
  "created_at" TIMESTAMP DEFAULT (strftime('%s', datetime('now', 'localtime'))),
  "last_activity" TIMESTAMP DEFAULT (strftime('%s', datetime('now', 'localtime'))),
  "last_value" REAL DEFAULT NULL,
  "user_id" INTEGER NOT NULL REFERENCES "user" ("user_id"),
  "device_id" INTEGER NOT NULL REFERENCES "device" ("device_id"),
  CONSTRAINT "fk_on" FOREIGN KEY ("user_id") REFERENCES "user" ("user_id"),
  CONSTRAINT "fk_on" FOREIGN KEY ("device_id") REFERENCES "device" ("device_id")


);


CREATE TABLE "value" (
  "timestamp" TIMESTAMP DEFAULT (strftime('%s', datetime('now', 'localtime'))),
  "value" DOUBLE DEFAULT NULL ,
  "variable_id" VARCHAR(32) NOT NULL REFERENCES "variable" ("variable_id") ,
  CONSTRAINT "fk_on" FOREIGN KEY ("variable_id") REFERENCES "variable" ("variable_id"),
  PRIMARY KEY(timestamp, variable_id)

);

CREATE INDEX "idx_value__id" ON "value" ("variable_id");


CREATE INDEX "idx_device__owner_name" ON "device" ("user_id");

CREATE INDEX "idx_variable__device" ON "variable" ("variable_id");
