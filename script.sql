
CREATE SCHEMA IF NOT EXISTS `lifecellTask` DEFAULT CHARACTER SET utf8 ;
USE `lifecellTask` ;

CREATE TABLE IF NOT EXISTS `lifecellTask`.`Warehouse` (
  `title` VARCHAR(45) NOT NULL,
  `address` VARCHAR(75) NOT NULL,
  `phone` VARCHAR(13) NOT NULL,
  `stuffLimit` INT NOT NULL,
  PRIMARY KEY (`title`))
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lifecellTask`.`Customer` (
  `title` VARCHAR(45) NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(13) NOT NULL,
  `license` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`title`),
  UNIQUE INDEX `license_UNIQUE` (`license` ASC))
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lifecellTask`.`Stuff` (
  `model` VARCHAR(45) NOT NULL,
  `type` ENUM('HDD', 'Memory', 'CPU', 'GPU') NOT NULL,
  `specification` VARCHAR(45) NULL,
  PRIMARY KEY (`model`),
  UNIQUE INDEX `model_UNIQUE` (`model` ASC))
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lifecellTask`.`Records` (
  `idRecords` INT NOT NULL AUTO_INCREMENT,
  `counter` INT NOT NULL,
  `Customer_title` VARCHAR(45) NOT NULL,
  `Warehouse_title` VARCHAR(45) NOT NULL,
  `Stuff_model` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRecords`),
  UNIQUE INDEX `idRecords_UNIQUE` (`idRecords` ASC),
  INDEX `fk_Records_Customer1_idx` (`Customer_title` ASC),
  INDEX `fk_Records_Warehouse1_idx` (`Warehouse_title` ASC),
  INDEX `fk_Records_Stuff1_idx` (`Stuff_model` ASC),
  CONSTRAINT `fk_Records_Customer1`
  FOREIGN KEY (`Customer_title`)
  REFERENCES `lifecellTask`.`Customer` (`title`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Records_Warehouse1`
  FOREIGN KEY (`Warehouse_title`)
  REFERENCES `lifecellTask`.`Warehouse` (`title`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Records_Stuff1`
  FOREIGN KEY (`Stuff_model`)
  REFERENCES `lifecellTask`.`Stuff` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

INSERT INTO `Customer` (`title`, `contact`, `phone`, `license`) VALUES
  ('Everest', 'Иванов Петр Сергеевич', '0630001111', '1112AV');
INSERT INTO `Customer` (`title`, `contact`, `phone`, `license`) VALUES
  ('DiaWest', 'Сидоров Иван Сергеевич', '0630002222', '1212TT');
INSERT INTO `Customer` (`title`, `contact`, `phone`, `license`) VALUES
  ('GigaByte', 'Петров Сергей Сергеевич', '0630003333', '9112XZ');
INSERT INTO `Customer` (`title`, `contact`, `phone`, `license`) VALUES
  ('CityCom', 'Кузнецов Виталий Иванович', '0630004444', '1101WY');


INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('Seagate ST100DM003', 'HDD', 'SATA 100GB');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('Kingston HX421C14FB/8', 'Memory', '8 GB');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('Intel Core i7-4790K BX80646I74790K ', 'CPU', '4x4.0GHz');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('MSI GeForce GTX 970 GAMING 4G', 'GPU', '1140GHz');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('ASUS STRIX-GTX970-DC2OC-4GD5', 'GPU', '1253GHz');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('AMD FX-6300 FD6300WMHKBOX', 'CPU', '6x3.5GHz');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('Intel Core i7-4790K BX80646I74790K ', 'CPU', '4x4.0GHz');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
('WD WD10EZRX', 'HDD', 'SATA 500Gb');
INSERT IGNORE INTO `Stuff` (`model`, `type`, `specification`) VALUES
  ('Seagate ST100DM003', 'HDD', 'SATA 100Gb');


INSERT IGNORE INTO `Warehouse` (`title`, `address`, `phone`, `stuffLimit`) VALUES
('Brovary', 'Кооперативная улица, 9, Украина, Киевская область, Бровары', '0632100000', 1000),
('Kiev 1', 'улица Николая Закревского, 12, Украина, Киев', '0632101111', 2000),
('Kiev 2', 'проспект Героев Сталинграда, 12, Украина, Киев', '0632102222', 500);


INSERT IGNORE INTO `Records` (`counter`, `Customer_title`, `Warehouse_title`, `Stuff_model`) VALUES
  (300, 'Everest', 'Brovary', 'Seagate ST100DM003'),
  (500, 'Everest', 'Brovary', 'Kingston HX421C14FB/8'),
  (100,  'Everest', 'Kiev 1', 'Intel Core i7-4790K BX80646I74790K'),
  (1000, 'Everest', 'Kiev 1', 'MSI GeForce GTX 970 GAMING 4G'),
  (900, 'DiaWest', 'Kiev 1', 'ASUS STRIX-GTX970-DC2OC-4GD5'),
  (200, 'DiaWest', 'Kiev 2', 'AMD FX-6300 FD6300WMHKBOX'),
  (100, 'CityCom', 'Kiev 2', 'Intel Core i7-4790K BX80646I74790K'),
  (50, 'CityCom', 'Kiev 2', 'WD WD10EZRX'),
  (50, 'CityCom', 'Kiev 2', 'Seagate ST100DM003');
