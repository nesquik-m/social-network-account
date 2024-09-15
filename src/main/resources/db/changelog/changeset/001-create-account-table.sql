-- liquibase formatted sql
-- changeset Anastasia Martova:create-account-table
CREATE TABLE accounts (
  id UUID PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  blocked BOOLEAN NOT NULL,
  deleted BOOLEAN NOT NULL,
  online BOOLEAN NOT NULL,
  city VARCHAR(50),
  country VARCHAR(50),
  phone VARCHAR(255),
  about VARCHAR(255),
  birth_date TIMESTAMP,
  emoji_status VARCHAR(100),
  profile_cover VARCHAR(255),
  photo VARCHAR(255),
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP NOT NULL,
  last_online_time TIMESTAMP
);

-- changeset Anastasia Martova:insert-accounts-data
INSERT INTO account_schema.accounts (id,email,first_name,last_name,"blocked",deleted,online,city,country,phone,about,birth_date,emoji_status,profile_cover,photo,created_on,updated_on,last_online_time) VALUES
	 ('5bd73891-a00e-44e9-954c-f6610d4d1a25'::uuid,'mail1@mail.ru','IVAN','IVANOV',false,false,true,'','','79991234567','Опытный Java-разработчик, увлеченный созданием надежных и масштабируемых приложений. Владею Spring Boot, Hibernate и другими популярными фреймворками.','1998-09-05 00:00:00',NULL,NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('5bd73891-a00e-44e9-954c-f6610d4d1a26'::uuid,'mail2@mail.ru','STEPAN','STEPANOV',false,false,true,'','','79112345678','Заинтересованный Java-разработчик с более чем 20-летним опытом в разработке программного обеспечения. Опытен в создании корпоративных приложений с использованием Java EE технологий.','1979-09-05 00:00:00',NULL,NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('5bd73891-a00e-44e9-954c-f6610d4d1a27'::uuid,'mail3@mail.ru','MARAT','MARATOV',false,false,true,'','','79223456789','Java-разработчик, специализирующийся на создании удобных и производительных веб-приложений.  Имею навыки работы с фронтенд-технологиями, такими как React и Angular.','1989-09-05 00:00:00',NULL,NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('5bd73891-a00e-44e9-954c-f6610d4d1a28'::uuid,'mail4@mail.ru','PETR','PETROV',false,false,true,'','','79334567890','Младший Java-разработчик, стремящийся к обучению и развитию в этой области.  С энтузиазмом отношусь к участию в сложных проектах и развиваю свои навыки в Java и смежных технологиях.','2008-09-05 00:00:00',NULL,NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('f8c2a795-4d54-4e86-b433-78c9446e3d22'::uuid,'mail5@mail.ru','OLGA','PETROVA',false,false,true,'','','79445678901','Страстный Java-разработчик с глубоким пониманием принципов объектно-ориентированного программирования.  Опыт в построении корпоративных приложений с использованием Java EE и Spring.','1969-09-05 00:00:00','1',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('34567890-1234-5678-9012-345678901234'::uuid,'mail6@mail.ru','ANNA','IVANOVA',false,false,true,'','','79556789012','Java-разработчик с прочным фундаментом в области структур данных и алгоритмов. Опыт работы с базами данных и построения REST API.','1988-09-05 00:00:00','5',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('abcdef12-3456-7890-1234-567890abcdef'::uuid,'mail7@mail.ru','ELENA','LENINA',false,false,true,'','','79667890123','Java-разработчик с большой заинтересованностью в изучении новых технологий.  Опыт работы с Spring Boot и микросервисами.','2007-09-05 00:00:00','4',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('abcdef12-3456-7890-1234-567890abcd35'::uuid,'mail8@mail.ru','RUSLAN','RUSLANOV',false,false,true,'','','79778901234','Опытный Java-разработчик с опытом создания высокопроизводительных и масштабируемых приложений. Опыт работы с облачными платформами, такими как AWS и Azure.','1968-09-05 00:00:00','3',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('abcdef12-3456-7890-1234-567890abcd87'::uuid,'mail9@mail.ru','EGOR','EGOROV',false,false,true,'','','79889012345','Java-разработчик с фокусом на создание безопасных и надежных приложений. Опыт работы с фреймворками безопасности и лучшими практиками.','1978-09-05 00:00:00','2',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('abcdef12-3456-7890-1234-567890abcd77'::uuid,'mail10@mail.ru','VICTOR','VICTOROV',false,false,true,'','','79001234567','Java-разработчик, увлеченный созданием инновационных и ориентированных на пользователя приложений.  Опыт работы с методологиями Agile и практиками DevOps.','2017-09-05 00:00:00','1',NULL,NULL,'2024-09-05 00:00:00','2024-09-05 00:00:00',NULL),
	 ('5bd73891-a00e-44e9-954c-f6610d4d1a16'::uuid,'mail@mail.ru','ANASTASIA','MARTOVA',false,true,true,'','','70000000000','ddd','1996-05-16 00:00:00','5',NULL,NULL,'2024-09-05 00:00:00','2024-09-06 23:34:30.448307',NULL),
	 ('60b1f478-ec5a-4cfa-a022-ee9713228a86'::uuid,'tagir@gmail.com','Tagir','Tagir',false,true,true,'','','70000000000','Tagir','2000-01-01 00:00:00','3',NULL,NULL,'2024-09-05 00:00:00','2024-09-06 23:34:30.448307',NULL),
	 ('df68c55b-5909-4096-bec8-b69e174123dd'::uuid,'admin@gmail.com','admin','admin',false,true,true,'','','70000000000','admin','2000-01-01 00:00:00','3',NULL,NULL,'2024-09-05 00:00:00','2024-09-06 23:34:30.448307',NULL);
