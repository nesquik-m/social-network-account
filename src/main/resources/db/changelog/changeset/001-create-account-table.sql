-- liquibase formatted sql
-- changeset Anastasia Martova:create-account-table

CREATE TABLE accounts (
  id UUID PRIMARY KEY,
  email VARCHAR(255),
  city VARCHAR(50),
  country VARCHAR(50),
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  phone VARCHAR(255),
  about VARCHAR(255),
  profile_cover VARCHAR(255),
  birth_date TIMESTAMP,
  emoji_status VARCHAR(100),
  blocked BOOLEAN NOT NULL,
  deleted BOOLEAN NOT NULL,
  online BOOLEAN NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP NOT NULL
);

-- changeset Anastasia Martova:insert-accounts-data
INSERT INTO accounts (id, email, city, country, first_name, last_name, phone, about, profile_cover, birth_date, emoji_status, blocked, deleted, online, created_on, updated_on) VALUES
('5bd73891-a00e-44e9-954c-f6610d4d1a25', 'mail1@mail.ru', '', '', 'Ivan', 'Ivanov', NULL, 'Опытный Java-разработчик, увлеченный созданием надежных и масштабируемых приложений. Владею Spring Boot, Hibernate и другими популярными фреймворками.', NULL, '1998-09-05', NULL, false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('5bd73891-a00e-44e9-954c-f6610d4d1a26', 'mail2@mail.ru', '', '', 'Stepan', 'Stepanov', NULL, 'Заинтересованный Java-разработчик с более чем 20-летним опытом в разработке программного обеспечения. Опытен в создании корпоративных приложений с использованием Java EE технологий.', NULL, '1979-09-05', NULL, false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('5bd73891-a00e-44e9-954c-f6610d4d1a27', 'mail3@mail.ru', '', '', 'Marat', 'Maratov', NULL, 'Java-разработчик, специализирующийся на создании удобных и производительных веб-приложений.  Имею навыки работы с фронтенд-технологиями, такими как React и Angular.', NULL, '1989-09-05', NULL, false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('5bd73891-a00e-44e9-954c-f6610d4d1a28', 'mail4@mail.ru', '', '', 'Petr', 'Petrov', NULL, 'Младший Java-разработчик, стремящийся к обучению и развитию в этой области.  С энтузиазмом отношусь к участию в сложных проектах и развиваю свои навыки в Java и смежных технологиях.', NULL, '2008-09-05', NULL, false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('f8c2a795-4d54-4e86-b433-78c9446e3d22', 'mail5@mail.ru', '', '', 'Olga', 'Petrova', NULL, 'Страстный Java-разработчик с глубоким пониманием принципов объектно-ориентированного программирования.  Опыт в построении корпоративных приложений с использованием Java EE и Spring.', NULL, '1969-09-05', '1', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('34567890-1234-5678-9012-345678901234', 'mail6@mail.ru', '', '', 'Anna', 'Ivanova', NULL, 'Java-разработчик с прочным фундаментом в области структур данных и алгоритмов. Опыт работы с базами данных и построения REST API.', NULL, '1988-09-05', '5', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('abcdef12-3456-7890-1234-567890abcdef', 'mail7@mail.ru', '', '', 'Elena', 'Lenina', NULL, 'Java-разработчик с большой заинтересованностью в изучении новых технологий.  Опыт работы с Spring Boot и микросервисами.', NULL, '2007-09-05', '4', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('abcdef12-3456-7890-1234-567890abcd35', 'mail8@mail.ru', '', '', 'Ruslan', 'Ruslanov', NULL, 'Опытный Java-разработчик с опытом создания высокопроизводительных и масштабируемых приложений. Опыт работы с облачными платформами, такими как AWS и Azure.', NULL, '1968-09-05', '3', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('abcdef12-3456-7890-1234-567890abcd87', 'mail9@mail.ru', '', '', 'Egor', 'Egorov', NULL, 'Java-разработчик с фокусом на создание безопасных и надежных приложений. Опыт работы с фреймворками безопасности и лучшими практиками.', NULL, '1978-09-05', '2', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('abcdef12-3456-7890-1234-567890abcd77', 'mail10@mail.ru', '', '', 'Victor', 'Victorov', NULL, 'Java-разработчик, увлеченный созданием инновационных и ориентированных на пользователя приложений.  Опыт работы с методологиями Agile и практиками DevOps.', NULL, '2017-09-05', '1', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 00:00:00.000'),
('5bd73891-a00e-44e9-954c-f6610d4d1a16', 'mail@mail.ru', '', '', 'Anastasia', 'Martova', '79806372969', 'Обо мне...)', 'https://www.wallpaperflare.com/static/966/37/315/blue-purple-mountains-hexagon-wallpaper.jpg', '1959-09-05', '5', false, false, true, '2024-09-05 00:00:00.000', '2024-09-05 15:15:43.866');
