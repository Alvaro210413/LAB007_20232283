-- =====================================================================
-- Base de datos: gameshop3  (Laboratorio 4 - Model Attribute & Validación)
-- Ejecutar todo el script en MySQL Workbench (rayo ⚡) antes de correr la app.
-- OJO: borra la BD gameshop3 si ya existe y la vuelve a crear desde cero.
-- =====================================================================
SET NAMES utf8mb4;
DROP DATABASE IF EXISTS gameshop3;
CREATE DATABASE gameshop3 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE gameshop3;

-- ---------------------------------------------------------------------
-- Tablas "catálogo" (no tienen llaves foráneas)
-- ---------------------------------------------------------------------
CREATE TABLE usuarios (
  idusuario    INT NOT NULL AUTO_INCREMENT,
  apellidos    VARCHAR(45) NOT NULL,
  nombres      VARCHAR(45) NOT NULL,
  correo       VARCHAR(45) NOT NULL,
  password     VARCHAR(65) NOT NULL,
  autorizacion VARCHAR(20) NOT NULL,
  enabled      BIT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (idusuario)
) ENGINE = InnoDB;

CREATE TABLE editoras (
  ideditora   INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(50) NOT NULL,
  descripcion VARCHAR(200) NULL,
  PRIMARY KEY (ideditora)
) ENGINE = InnoDB;

CREATE TABLE generos (
  idgenero    INT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(50) NOT NULL,
  descripcion VARCHAR(200) NULL,
  PRIMARY KEY (idgenero)
) ENGINE = InnoDB;

CREATE TABLE plataformas (
  idplataforma INT NOT NULL AUTO_INCREMENT,
  nombre       VARCHAR(50) NOT NULL,
  descripcion  VARCHAR(200) NULL,
  PRIMARY KEY (idplataforma)
) ENGINE = InnoDB;

CREATE TABLE paises (
  idpais INT NOT NULL AUTO_INCREMENT,
  iso    CHAR(2) NOT NULL,
  nombre VARCHAR(80) NOT NULL,
  PRIMARY KEY (idpais)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- distribuidoras  (idsede -> paises)
-- ---------------------------------------------------------------------
CREATE TABLE distribuidoras (
  iddistribuidora INT NOT NULL AUTO_INCREMENT,
  nombre          VARCHAR(50) NOT NULL,
  descripcion     VARCHAR(200) NULL,
  fundacion       INT NULL,
  web             VARCHAR(200) NULL,
  idsede          INT NOT NULL,
  PRIMARY KEY (iddistribuidora),
  CONSTRAINT fk_distribuidoras_paises FOREIGN KEY (idsede) REFERENCES paises (idpais)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- juegos  (genero, plataforma, editora, distribuidora)
-- ideditora es NULL porque en el lab no hay entidad Editoras y desde el
-- formulario no se llena; si fuera NOT NULL, los INSERT fallarían.
-- ---------------------------------------------------------------------
CREATE TABLE juegos (
  idjuego         INT NOT NULL AUTO_INCREMENT,
  nombre          VARCHAR(50) NOT NULL,
  descripcion     VARCHAR(448) NULL,
  precio          DOUBLE NOT NULL,
  image           VARCHAR(400) NULL,
  idgenero        INT NOT NULL,
  idplataforma    INT NOT NULL,
  ideditora       INT NULL,
  iddistribuidora INT NOT NULL,
  PRIMARY KEY (idjuego),
  CONSTRAINT fk_juegos_generos        FOREIGN KEY (idgenero)        REFERENCES generos (idgenero),
  CONSTRAINT fk_juegos_plataformas    FOREIGN KEY (idplataforma)    REFERENCES plataformas (idplataforma),
  CONSTRAINT fk_juegos_editoras       FOREIGN KEY (ideditora)       REFERENCES editoras (ideditora),
  CONSTRAINT fk_juegos_distribuidoras FOREIGN KEY (iddistribuidora) REFERENCES distribuidoras (iddistribuidora)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- juegosxusuario (compras) y factura
-- ---------------------------------------------------------------------
CREATE TABLE juegosxusuario (
  idjuegosxusuario INT NOT NULL AUTO_INCREMENT,
  idjuego          INT NOT NULL,
  idusuario        INT NOT NULL,
  cantidad         INT NOT NULL,
  PRIMARY KEY (idjuegosxusuario),
  CONSTRAINT fk_jxu_juegos   FOREIGN KEY (idjuego)   REFERENCES juegos (idjuego),
  CONSTRAINT fk_jxu_usuarios FOREIGN KEY (idusuario) REFERENCES usuarios (idusuario)
) ENGINE = InnoDB;

CREATE TABLE factura (
  idfactura          INT NOT NULL AUTO_INCREMENT,
  fechaEnvio         VARCHAR(50) NULL,
  tarjeta            VARCHAR(50) NULL,
  codigoVerificacion VARCHAR(5) NULL,
  direccion          VARCHAR(500) NULL,
  monto              FLOAT NULL,
  idjuegosxusuario   INT NOT NULL,
  PRIMARY KEY (idfactura),
  CONSTRAINT fk_factura_jxu FOREIGN KEY (idjuegosxusuario) REFERENCES juegosxusuario (idjuegosxusuario)
) ENGINE = InnoDB;

-- =====================================================================
-- DATOS DE PRUEBA
-- =====================================================================
INSERT INTO usuarios (apellidos, nombres, correo, password, autorizacion, enabled) VALUES
('Pimentel Melgar', 'Alvaro', 'a20232283@pucp.edu.pe', '123456', 'ADMIN', 1),
('Perez Rojas', 'Maria', 'maria@pucp.edu.pe', '123456', 'USER', 1);

INSERT INTO editoras (nombre, descripcion) VALUES
('Nintendo EPD', 'Desarrolladora interna de Nintendo'),
('Monolith Soft', 'Desarrolladora japonesa de juegos de rol'),
('Guerrilla Games', 'Estudio neerlandés de Sony'),
('Game Freak', 'Desarrolladora de la saga Pokémon');

INSERT INTO generos (nombre, descripcion) VALUES
('Acción-Aventura', 'Juegos de exploración con combate'),
('RPG', 'Juegos de rol'),
('Deportes', 'Simulación de deportes'),
('Shooter', 'Juegos de disparos'),
('Plataformas', 'Juegos de saltos y niveles');

INSERT INTO plataformas (nombre, descripcion) VALUES
('Nintendo Switch', 'Consola híbrida de Nintendo'),
('PlayStation 5', 'Consola de Sony'),
('Xbox Series X', 'Consola de Microsoft'),
('PC', 'Computadora personal');

INSERT INTO paises (iso, nombre) VALUES
('US', 'Estados Unidos'),
('JP', 'Japón'),
('FR', 'Francia'),
('PE', 'Perú'),
('GB', 'Reino Unido'),
('CA', 'Canadá');

INSERT INTO distribuidoras (nombre, descripcion, fundacion, web, idsede) VALUES
('Activision', 'Distribuidora estadounidense de videojuegos', 2008, 'https://www.activision.com', 1),
('Bandai Namco Entertainment', 'Distribuidora japonesa de videojuegos', 2006, 'https://www.bandainamcoent.com', 2),
('Electronic Arts', 'Distribuidora estadounidense de videojuegos', 1982, 'https://www.ea.com', 1),
('Nintendo', 'Empresa japonesa de videojuegos y consolas', 1889, 'https://www.nintendo.com', 2),
('Sony Interactive Entertainment', 'División de videojuegos de Sony', 1993, 'https://www.playstation.com', 2),
('Ubisoft', 'Distribuidora francesa de videojuegos', 1986, 'https://www.ubisoft.com', 3);

INSERT INTO juegos (nombre, descripcion, precio, image, idgenero, idplataforma, ideditora, iddistribuidora) VALUES
('Xenoblade Chronicles: Definitive Edition', 'Versión remasterizada llamada Xenoblade Chronicles: Definitive Edition para Nintendo Switch con fecha tentativa 2020.', 250.0, NULL, 2, 1, 2, 4),
('Horizon Forbidden West', 'Narra una épica historia de ciencia ficción postapocalíptica en la que la humanidad intenta sobrevivir tras la aparición de una serie de máquinas y robots que han sustituido a los seres vivos como especie dominante en la Tierra.', 210.0, NULL, 2, 2, 3, 5),
('The Legend of Zelda: Breath of the Wild', 'Es un videojuego de acción-aventura de 2017 de la serie The Legend of Zelda. El jugador controla a Link, que despierta en un mundo postapocalíptico después de estar cien años durmiendo para derrotar a Ganon y salvar el reino de Hyrule.', 200.0, NULL, 1, 1, 1, 4),
('Leyendas Pokémon: Arceus', 'Este videojuego de rol, que se desarrollará en Sinnoh, nos llevará a una época pasada de inspiración feudal, apostando por el mundo abierto tridimensional e incorporando nuevas mecánicas de captura y combate que buscan renovar para siempre la saga de cara al futuro.', 160.0, 'https://media.vandal.net/m/96931/leyendas-pokemon-arceus-202152616392725_1.jpg', 2, 1, 4, 4),
('FIFA 22', 'Simulador de fútbol con los equipos y ligas oficiales.', 120.0, NULL, 3, 2, NULL, 3),
('Call of Duty: Vanguard', 'Shooter en primera persona ambientado en la Segunda Guerra Mundial.', 180.0, NULL, 4, 4, NULL, 1);

-- Una compra de ejemplo: el juego 1 no se podrá borrar porque tiene una compra asociada
INSERT INTO juegosxusuario (idjuego, idusuario, cantidad) VALUES (1, 2, 1);
