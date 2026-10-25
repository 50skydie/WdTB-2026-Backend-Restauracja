-- Dane startowe aplikacji. Tabele tworzy Hibernate z encji, a ten plik Spring Boot uruchamia zaraz potem.
-- LAB 01: dopiszcie tu konta wszystkich osób z zespołu (reguła R1.03).
-- LAB 02: dopiszcie dane do swoich tabel (kolejność: najpierw tabele, na które wskazują klucze obce).
insert into user_account (email, display_name, role, created_at) values
    ('ola@student.merito.pl',   'Ola',   'USER',    current_timestamp),
    ('kuba@student.merito.pl',  'Kuba',  'USER',    current_timestamp),
    ('firma@example.com',       'Firma', 'PARTNER', current_timestamp),
    ('admin@example.com',       'Admin', 'ADMIN',   current_timestamp);


-- LAB 01: Wasze konta (zmieńcie na swoje dane, rola USER)
-- insert into user_account (email, display_name, role, created_at) values
--     ('imie.nazwisko@student.merito.pl', 'Imię', 'USER', current_timestamp);
insert into user_account (email, display_name, role, created_at) values
    ('szymon.romanowski@example.com', 'Szymon', 'USER', current_timestamp),
    ('jakub.tchorzewski@example.com', 'Kuba', 'USER', current_timestamp),
    ('olgierd.owczarczak@example.com', 'Olgierd', 'USER', current_timestamp);

-- ===== LAB 02 · dining_table =====
insert into dining_table (table_number, seats, zone, active, description) values
    (1, 2, 'MAIN_HALL',    true,  'Przy oknie'),
    (2, 4, 'MAIN_HALL',    true,  null),
    (3, 4, 'TERRACE',      true,  'Pod parasolem'),
    (4, 8, 'PRIVATE_ROOM', true,  'Sala na przyjęcia'),
    (5, 6, 'TERRACE',      false, 'Sezonowo nieczynny');
