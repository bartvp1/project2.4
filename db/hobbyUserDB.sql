-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema user_hobby_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema user_hobby_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `user_hobby_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`HobbyUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`HobbyUser` (
  `id` INT NOT NULL,
  `UserDetails_id` INT NOT NULL,
  `hobbies_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_HobbyUser_UserDetails1_idx` (`UserDetails_id` ASC) VISIBLE,
  INDEX `fk_HobbyUser_hobbies1_idx` (`hobbies_id` ASC) VISIBLE)
ENGINE = InnoDB;

USE `user_hobby_db` ;

-- -----------------------------------------------------
-- Table `user_hobby_db`.`hobbies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_hobby_db`.`hobbies` (
  `idHOBBIES` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idHOBBIES`),
  UNIQUE INDEX `idHOBBIES_UNIQUE` (`idHOBBIES` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `user_hobby_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_hobby_db`.`user` (
  `idUser` INT NOT NULL,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `idHOBBIES` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `idUser_UNIQUE` (`idUser` ASC) VISIBLE,
  INDEX `idHOBBIES_idx` (`idHOBBIES` ASC) VISIBLE,
  CONSTRAINT `idHOBBIES`
    FOREIGN KEY (`idHOBBIES`)
    REFERENCES `user_hobby_db`.`hobbies` (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
