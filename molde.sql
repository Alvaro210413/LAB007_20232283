-- MOLDE SQL (solo de referencia, no lo usa el proyecto)
-- Tipos: INT (enteros) | VARCHAR(n) (texto corto) | TEXT (texto largo)
-- DOUBLE (decimales) | DECIMAL(10,2) (dinero exacto, 2 decimales)
-- DATE (fecha) | DATETIME (fecha y hora) | TINYINT(1) (true/false)

DROP DATABASE IF EXISTS mibd;
CREATE DATABASE mibd;
USE mibd;

-- 1) Tabla simple (el lado "uno")
CREATE TABLE tabla_uno (
  idtablauno INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  descripcion TEXT NULL,
  precio DECIMAL(10,2) NULL,
  nota DOUBLE NULL,
  fecha DATE NULL,
  creado DATETIME NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (idtablauno)
);

-- 2) Uno a muchos: la FK va en el lado "muchos"
CREATE TABLE tabla_muchos (
  idtablamuchos INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  idtablauno INT NOT NULL,
  PRIMARY KEY (idtablamuchos),
  FOREIGN KEY (idtablauno) REFERENCES tabla_uno (idtablauno)
);

-- 3) Muchos a muchos: tabla intermedia con las dos FK
CREATE TABLE tabla_uno_x_muchos (
  idtablauno INT NOT NULL,
  idtablamuchos INT NOT NULL,
  cantidad INT NULL,
  PRIMARY KEY (idtablauno, idtablamuchos),
  FOREIGN KEY (idtablauno) REFERENCES tabla_uno (idtablauno),
  FOREIGN KEY (idtablamuchos) REFERENCES tabla_muchos (idtablamuchos)
);

-- Datos de prueba
INSERT INTO tabla_uno (nombre, precio) VALUES ('Ejemplo', 19.90);
INSERT INTO tabla_muchos (nombre, idtablauno) VALUES ('Hijo 1', 1);

-- Equivalencia en Java (entity):
-- INT -> Integer | VARCHAR / TEXT -> String | DOUBLE -> Double
-- DECIMAL -> BigDecimal (o Double si el curso lo usa así)
-- DATE -> LocalDate | DATETIME -> LocalDateTime | TINYINT(1) -> Boolean
-- FK en el lado muchos -> @ManyToOne + @JoinColumn(name = "idtablauno")
