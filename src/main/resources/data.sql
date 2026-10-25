-- Dane startowe aplikacji. Tabele tworzy Hibernate z encji, a ten plik Spring Boot uruchamia zaraz potem.
-- LAB 01: dopiszcie tu konta wszystkich osób z zespołu (reguła R1.03).
-- LAB 02: dopiszcie dane do swoich tabel (kolejność: najpierw tabele, na które wskazują klucze obce).
insert into user_account (email, display_name, role, created_at) values
    ('ola@student.merito.pl',   'Ola',   'USER',    current_timestamp),
    ('kuba@student.merito.pl',  'Kuba',  'USER',    current_timestamp),
    ('firma@example.com',       'Firma', 'PARTNER', current_timestamp),
    ('admin@example.com',       'Admin', 'ADMIN',   current_timestamp),
    ('szymon.romanowski@example.com', 'Szymon', 'USER', current_timestamp),
    ('jakub.tchorzewski@example.com', 'Kuba', 'USER', current_timestamp),
    ('olgierd.owczarczak@example.com', 'Olgierd', 'USER', current_timestamp);

-- LAB 01: Wasze konta (zmieńcie na swoje dane, rola USER)
-- insert into user_account (email, display_name, role, created_at) values
--     ('imie.nazwisko@student.merito.pl', 'Imię', 'USER', current_timestamp);

-- ===== LAB 02 · reservation =====
insert into reservation (reservation_code, starts_at, party_size, status, deposit, notes) values
    ('RES-2026-001', '2026-11-07 18:00:00', 2, 'CONFIRMED',  0.00,   'Rocznica, prośba o stolik przy oknie'),
    ('RES-2026-002', '2026-11-07 19:30:00', 4, 'PENDING',    50.00,  null),
    ('RES-2026-003', '2026-11-08 13:00:00', 8, 'CONFIRMED', 200.00, 'Urodziny, tort własny'),
    ('RES-2026-004', '2026-10-24 20:00:00', 3, 'NO_SHOW',    0.00,   null),
    ('RES-2026-005', '2026-10-18 17:00:00', 2, 'COMPLETED',  0.00,   null);


-- ===== LAB 02 · dining_table =====
insert into dining_table (table_number, seats, zone, active, description) values
(1, 2, 'MAIN_HALL',    true,  'Przy oknie'),
(2, 4, 'MAIN_HALL',    true,  null),
(3, 4, 'TERRACE',      true,  'Pod parasolem'),
(4, 8, 'PRIVATE_ROOM', true,  'Sala na przyjęcia'),
(5, 6, 'TERRACE',      false, 'Sezonowo nieczynny');

-- ===== LAB 02 · customer =====
insert into customer (first_name, last_name, registered_on, loyalty_points, phone, email) values
('Anna',  'Kowalska',    '2026-09-14', 120, '600100200', 'anna.kowalska@example.com'),
('Piotr', 'Nowak',       '2026-10-02',   0, '601222333', null),
('Ewa',   'Wiśniewska',  '2026-10-11',  45, '602444555', 'ewa.w@example.com'),
('Tomasz','Zieliński',   '2026-10-20',  10, '603666777', 'tomasz.z@example.com');