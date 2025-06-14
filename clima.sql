-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema clima
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `clima` ;

-- -----------------------------------------------------
-- Schema clima
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clima` DEFAULT CHARACTER SET utf8 ;
USE `clima` ;

-- -----------------------------------------------------
-- Table `clima`.`monitoreo_climatico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `clima`.`monitoreo_climatico` (
  `idMonitoreo` INT NOT NULL AUTO_INCREMENT,
  `ciudad` VARCHAR(45) NULL,
  `fecha` DATE NULL,
  `temperaturaPromedio` DOUBLE NULL,
  `condicionClimaticaEsperada` VARCHAR(45) NULL,
  `temperaturaMaxima` DOUBLE NULL,
  `temperaturaMinima` DOUBLE NULL,
  PRIMARY KEY (`idMonitoreo`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
