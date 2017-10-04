
DROP SCHEMA IF EXISTS bichomon_go_jdbc;
CREATE SCHEMA bichomon_go_jdbc;

USE bichomon_go_jdbc;

CREATE TABLE especie (
  nombre VARCHAR(255) NOT NULL UNIQUE,
  altura int NOT NULL,
  peso int NOT NULL,
  tipo VARCHAR(255) NOT NULL,
  cantidadBichos int NOT NULL,
  PRIMARY KEY (nombre)
)
ENGINE = InnoDB;