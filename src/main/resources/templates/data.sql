-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema imperium
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema imperium
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `imperium` DEFAULT CHARACTER SET utf8mb4;
USE `imperium` ;

-- -----------------------------------------------------
-- Table `imperium`.`tipo_conta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`tipo_conta` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cor` VARCHAR(255) NULL DEFAULT NULL,
  `icone` VARCHAR(255) NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`usuario` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`conta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`conta` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ativo` BIT(1) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `inclui_soma` BIT(1) NOT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `saldo` DECIMAL(19,2) NULL DEFAULT NULL,
  `tipo_id` BIGINT NULL DEFAULT NULL,
  `usuario_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKjo5sytiww1k3k204yu89hjk6r` (`tipo_id` ASC) VISIBLE,
  INDEX `FKmn9bk6wpmnlqyv5i3utmled4c` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FKjo5sytiww1k3k204yu89hjk6r`
    FOREIGN KEY (`tipo_id`)
    REFERENCES `imperium`.`tipo_conta` (`id`),
  CONSTRAINT `FKmn9bk6wpmnlqyv5i3utmled4c`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `imperium`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`emprestimo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `deletado` BIT(1) NOT NULL,
  `natureza` VARCHAR(255) NULL DEFAULT NULL,
  `pessoa` VARCHAR(255) NULL DEFAULT NULL,
  `conta_id` BIGINT NULL DEFAULT NULL,
  `usuario_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKfr15xahd586maerwk1eridk7d` (`conta_id` ASC) VISIBLE,
  INDEX `FKn0k2927f8w348cfiahkff1rhk` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FKfr15xahd586maerwk1eridk7d`
    FOREIGN KEY (`conta_id`)
    REFERENCES `imperium`.`conta` (`id`),
  CONSTRAINT `FKn0k2927f8w348cfiahkff1rhk`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `imperium`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`acao_emprestimo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`acao_emprestimo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `conta_id` BIGINT NULL DEFAULT NULL,
  `emprestimo_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK8lq3fvqy5qbnd2lmkabrenf68` (`conta_id` ASC) VISIBLE,
  INDEX `FK1t7bh7j5c9l97oags8djy7bl9` (`emprestimo_id` ASC) VISIBLE,
  CONSTRAINT `FK1t7bh7j5c9l97oags8djy7bl9`
    FOREIGN KEY (`emprestimo_id`)
    REFERENCES `imperium`.`emprestimo` (`id`),
  CONSTRAINT `FK8lq3fvqy5qbnd2lmkabrenf68`
    FOREIGN KEY (`conta_id`)
    REFERENCES `imperium`.`conta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`bandeira`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`bandeira` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cor` VARCHAR(255) NULL DEFAULT NULL,
  `icone` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`cartao` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `dia_fechamento` INT NOT NULL,
  `dia_vencimento` INT NOT NULL,
  `limite` DECIMAL(19,2) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `bandeira_id` BIGINT NULL DEFAULT NULL,
  `usuario_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK9debgayuh27xmfqea983o3q2q` (`bandeira_id` ASC) VISIBLE,
  INDEX `FKayk1oeujbws7du1dcroipbgoy` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK9debgayuh27xmfqea983o3q2q`
    FOREIGN KEY (`bandeira_id`)
    REFERENCES `imperium`.`bandeira` (`id`),
  CONSTRAINT `FKayk1oeujbws7du1dcroipbgoy`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `imperium`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`categoria` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cor` VARCHAR(255) NULL DEFAULT NULL,
  `icone` VARCHAR(255) NULL DEFAULT NULL,
  `ativo` BIT(1) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `natureza` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`compra_cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`compra_cartao` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `parcelas` INT NOT NULL,
  `valor_parcela` DECIMAL(19,2) NULL DEFAULT NULL,
  `cartao_id` BIGINT NULL DEFAULT NULL,
  `categoria_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK6f5h29f6y6gwnludjahuvxtbl` (`cartao_id` ASC) VISIBLE,
  INDEX `FKdtosw4ko49jakhotij7vw46rw` (`categoria_id` ASC) VISIBLE,
  CONSTRAINT `FK6f5h29f6y6gwnludjahuvxtbl`
    FOREIGN KEY (`cartao_id`)
    REFERENCES `imperium`.`cartao` (`id`),
  CONSTRAINT `FKdtosw4ko49jakhotij7vw46rw`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `imperium`.`categoria` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`despesa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`despesa` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `concluida` BIT(1) NOT NULL,
  `deletado` BIT(1) NOT NULL,
  `categoria_id` BIGINT NULL DEFAULT NULL,
  `conta_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKhhqg5jva3eym0n1hp2gedxsq1` (`categoria_id` ASC) VISIBLE,
  INDEX `FKlngcgagha9t0c6n5kgqw4ut8g` (`conta_id` ASC) VISIBLE,
  CONSTRAINT `FKhhqg5jva3eym0n1hp2gedxsq1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `imperium`.`categoria` (`id`),
  CONSTRAINT `FKlngcgagha9t0c6n5kgqw4ut8g`
    FOREIGN KEY (`conta_id`)
    REFERENCES `imperium`.`conta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`fatura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`fatura` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `abertura` DATE NULL DEFAULT NULL,
  `ano` INT NOT NULL,
  `fechamento` DATE NULL DEFAULT NULL,
  `mes` INT NOT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `vencimento` DATE NULL DEFAULT NULL,
  `cartao_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKawyaors3j84koareebfcjacr1` (`cartao_id` ASC) VISIBLE,
  CONSTRAINT `FKawyaors3j84koareebfcjacr1`
    FOREIGN KEY (`cartao_id`)
    REFERENCES `imperium`.`cartao` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`historia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`historia` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `natureza` VARCHAR(255) NULL DEFAULT NULL,
  `transacao_id` BIGINT NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `usuario_id` BIGINT NULL DEFAULT NULL,
  `usuario` TINYBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK3ewyggy2dlbitrtnoa6qigrf1` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK3ewyggy2dlbitrtnoa6qigrf1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `imperium`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`item_fatura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`item_fatura` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `parcela` VARCHAR(255) NULL DEFAULT NULL,
  `compra_id` BIGINT NULL DEFAULT NULL,
  `fatura_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKtgj63p950kesscrtkd57niicr` (`compra_id` ASC) VISIBLE,
  INDEX `FK5ffnecgnvc6tn9wltov4hfq3q` (`fatura_id` ASC) VISIBLE,
  CONSTRAINT `FK5ffnecgnvc6tn9wltov4hfq3q`
    FOREIGN KEY (`fatura_id`)
    REFERENCES `imperium`.`fatura` (`id`),
  CONSTRAINT `FKtgj63p950kesscrtkd57niicr`
    FOREIGN KEY (`compra_id`)
    REFERENCES `imperium`.`compra_cartao` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`perfil`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`perfil` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`receita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`receita` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `concluida` BIT(1) NOT NULL,
  `deletado` BIT(1) NOT NULL,
  `categoria_id` BIGINT NULL DEFAULT NULL,
  `conta_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKigwe5nkerq6x8yvgirah6yxv8` (`categoria_id` ASC) VISIBLE,
  INDEX `FK1yrwh7lv1g21q5auweoiyiqr1` (`conta_id` ASC) VISIBLE,
  CONSTRAINT `FK1yrwh7lv1g21q5auweoiyiqr1`
    FOREIGN KEY (`conta_id`)
    REFERENCES `imperium`.`conta` (`id`),
  CONSTRAINT `FKigwe5nkerq6x8yvgirah6yxv8`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `imperium`.`categoria` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`transferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`transferencia` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `data` DATE NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `valor` DECIMAL(19,2) NULL DEFAULT NULL,
  `conta_destino_id` BIGINT NULL DEFAULT NULL,
  `conta_origem_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK3vh2c90fn9yn7l4gbvrl32mif` (`conta_destino_id` ASC) VISIBLE,
  INDEX `FKt6v19jub8ij9eh1u98hpbiwbk` (`conta_origem_id` ASC) VISIBLE,
  CONSTRAINT `FK3vh2c90fn9yn7l4gbvrl32mif`
    FOREIGN KEY (`conta_destino_id`)
    REFERENCES `imperium`.`conta` (`id`),
  CONSTRAINT `FKt6v19jub8ij9eh1u98hpbiwbk`
    FOREIGN KEY (`conta_origem_id`)
    REFERENCES `imperium`.`conta` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



-- -----------------------------------------------------
-- Table `imperium`.`usuario_perfis`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `imperium`.`usuario_perfis` (
  `usuario_id` BIGINT NOT NULL,
  `perfis_id` BIGINT NOT NULL,
  INDEX `FK7bhs80brgvo80vhme3u8m6ive` (`perfis_id` ASC) VISIBLE,
  INDEX `FKs91tgiyagbilt959wbufiphgc` (`usuario_id` ASC) VISIBLE,
  CONSTRAINT `FK7bhs80brgvo80vhme3u8m6ive`
    FOREIGN KEY (`perfis_id`)
    REFERENCES `imperium`.`perfil` (`id`),
  CONSTRAINT `FKs91tgiyagbilt959wbufiphgc`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `imperium`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `imperium`.`tipo_conta` (`cor`, `icone`, `nome`, `descricao`) VALUES ('#A6206A','wallet','Dinheiro','para manuseio de dnheiro fisico');
INSERT INTO `imperium`.`tipo_conta` (`cor`, `icone`, `nome`, `descricao`) VALUES ('#2F9395','piggy-bank','Conta Poupança','para contas poupança');
INSERT INTO `imperium`.`tipo_conta` (`cor`, `icone`, `nome`, `descricao`) VALUES ('#EC1C40','bank-transfer','Conta Corrente','para contas correntes');
INSERT INTO `imperium`.`tipo_conta` (`cor`, `icone`, `nome`, `descricao`) VALUES ('#F16A43','home-currency-usd ','Investimento','para ivestimentos e relacionados');
INSERT INTO `imperium`.`tipo_conta` (`cor`, `icone`, `nome`, `descricao`) VALUES ('#F7D969','currency-usd-circle ','Outros','outro que não tenha aqui');

INSERT INTO `imperium`.`bandeira` (`nome`, `cor`, `icone`) VALUES ('Visa', '#00579f', 'credit-card-outline');
INSERT INTO `imperium`.`bandeira` (`nome`, `cor`, `icone`) VALUES ('Mastercard', '#ea001b', 'credit-card-outline');
INSERT INTO `imperium`.`bandeira` (`nome`, `cor`, `icone`) VALUES ('American Express', '#038cd2', 'credit-card-outline');
INSERT INTO `imperium`.`bandeira` (`nome`, `cor`, `icone`) VALUES ('Discover Network', '#f3821c', 'credit-card-outline');
INSERT INTO `imperium`.`bandeira` (`nome`, `cor`, `icone`) VALUES ('Outras', '#ff5f01', 'credit-card-outline	');

INSERT INTO `imperium`.`usuario` (`email`, `nome`, `senha`) VALUES ('je_lionjuda@hotmail.com', 'Jeferson Menezes', '$2a$10$8Rb/uLgSVro6Af4SbIt5YO8Q5VmaHW5f8dBUzjwSJd6x6T802hX7C');

INSERT INTO `imperium`.`tipo_ativo`
(`nome`,
`renda`)
VALUES
('Ações', 'RENDA_VARIAVEL'),
('FII - Fundo de Investimento Imobiliário', 'RENDA_VARIAVEL'),
('Tesouro Direto e Títulos Públicos', 'RENDA_FIXA');