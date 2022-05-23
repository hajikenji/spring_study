DROP TABLE IF EXISTS spring_study_crud;

CREATE TABLE IF NOT EXISTS spring_study_crud (
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL
  -- password VARCHAR(150)
  -- number INT,
);

DROP TABLE IF EXISTS spring_study_register;

CREATE TABLE IF NOT EXISTS spring_study_register (
  id int PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  password VARCHAR(150)
  -- number INT,
);