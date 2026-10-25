# Step-by-step guide: LAB 01 + LAB 02 for a restaurant reservation backend

Project idea: a backend that lets guests **reserve a table in a restaurant** and lets the owner manage tables and bookings.

Based on: `pages/01-lab01-start.md`, `pages/02-lab02-entities-and-data.md`, `pages/12-project-rules.md`,
`pages/13-project-idea.md` and the real template files in `WdTB-2026-Backend--projectname--`
(fork `github.com/50skydie/WdTB-2026-Backend--projectname--`).

Conventions used in this guide (change them consistently if you prefer something else):

| Item | Value |
| --- | --- |
| Project name | `restauracja` (one word, lowercase, no Polish characters) |
| Repository | `WdTB-2026-Backend-restauracja` |
| Root package | `pl.wsb.restauracja` |
| Start class | `RestauracjaApplication` |
| `artifactId` | `wdtb-2026-backend-restauracja` |
| Modules (LAB 02) | `seating` (tables), `customer` (guests), `booking` (reservations) |

Replace `<index1>`, `<index2>`, `<index3>` with the real student index numbers (5–7 digits each).

---

## Part 0 · State of the local copy (do this first)

The local clone is currently 2 commits ahead of GitHub, including a "test commit" that added an empty class
`src/main/java/pl/wsb/projekt/account/domain/test.java`. That class is not needed, so delete it:

1. In IntelliJ, right-click `account/domain/test.java` → *Delete*.
2. *Git → Commit* with message `Remove test class`, then *Push* (this also pushes the 2 local commits).

Also make sure **GitHub Actions is enabled** in the fork: GitHub → *Actions* tab →
*I understand my workflows, go ahead and enable them*. Without it, no rules run in CI.

---

## Part 1 · The project idea (write it down before coding)

The idea has to meet the requirements in `pages/13-project-idea.md`. Here's a version that does:

| | |
| --- | --- |
| **Problem** | Restauracja „Pod Lipą” przyjmuje rezerwacje telefonicznie i zapisuje je w zeszycie: zdarzają się podwójne rezerwacje stolików, goście nie przychodzą, a właściciel nie wie, które godziny są najbardziej oblegane. |
| **Klient (persona)** | Marek, właściciel restauracji, oraz jego goście, którzy chcą zarezerwować stolik online. |
| **Funkcje** | przeglądanie wolnych stolików, rezerwacja stolika na wybrany termin, potwierdzenie / odwołanie rezerwacji, raport obłożenia i niestawień dla właściciela. |
| **Dane** | stoliki (numer, liczba miejsc, strefa), goście (imię, nazwisko, kontakt, punkty lojalnościowe), rezerwacje (kod, termin, liczba osób, status, zadatek). |
| **Moduły** | `seating` (stoliki), `customer` (goście), `booking` (rezerwacje). |

Course requirements for the whole semester, and how this idea covers them:

| Requirement | Restaurant project |
| --- | --- |
| Specific persona | Marek, owner of „Pod Lipą” |
| At least 2 modules | `seating`, `customer`, `booking` (later maybe `menu`) |
| 5–6 own tables | LAB 02: `dining_table`, `customer`, `reservation`. Later: `menu_item`, `pre_order_item`, `opening_hours` |
| Object with status and lifecycle | `Reservation`: `PENDING → CONFIRMED → COMPLETED` / `CANCELLED` / `NO_SHOW` |
| Measurable quantities | party size, seats, deposit (money), loyalty points, time |
| Many-to-many with attributes | reservation ↔ dining tables (a large group can take several tables), or reservation ↔ menu items with quantity (pre-order) |
| Five "investor questions" (LAB 03) | see the end of this guide |

---

## Part 2 · LAB 01 (not graded, but everything here is checked by rules R1.01–R1.05)

### Step 1.1 · Team spreadsheet

One person opens the [team spreadsheet](https://m365ht-my.sharepoint.com/:x:/g/personal/wojciech_koszela_wroclaw_merito_pl/IQCEEPWN_2owS6zMeDfSfeG-AXpOGAC7AgOlj9SAudd8Y0w?e=S2s4H9),
goes to your group's tab, picks a free team number and enters all 3 people: full name, index number, university e-mail.
Mark the host with „tak” in the *Host* column.

### Step 1.2 · Collaborators

GitHub fork → *Settings → Collaborators → Add people* → add both teammates. They accept the invitation by e-mail
or at <https://github.com/notifications>.

### Step 1.3 · Run the template once

1. In IntelliJ open `ProjektApplication` → green arrow next to `main` → *Run 'ProjektApplication'*.
2. In the *Run* window, look for `create table user_account` and `Started ProjektApplication in … seconds`.
3. Open <http://localhost:8080>, which shows the start page.
4. Open <http://localhost:8080/h2-console> → JDBC URL `jdbc:h2:file:./data/wdtb`, user `sa`, empty password →
   `SELECT * FROM user_account;` shows 4 demo accounts.
5. Stop the app (red square in *Run*) before renaming things.

### Step 1.4 · Rename the repository (R1.01)

GitHub fork → *Settings → General → Repository name* → `WdTB-2026-Backend-restauracja` → **Rename**.

Then update the local remote. IntelliJ: *Git → Manage Remotes* → edit `origin` to
`https://github.com/50skydie/WdTB-2026-Backend-restauracja.git`. Or from a terminal in the project folder:

```bash
git remote set-url origin https://github.com/50skydie/WdTB-2026-Backend-restauracja.git
```

(GitHub redirects the old URL, so this isn't strictly required, but it avoids confusion.)

### Step 1.5 · Rename the package and the start class (R1.01)

1. In the *Project* view, right-click package `pl.wsb.projekt` → *Refactor → Rename* → `restauracja` →
   choose **Rename package** (not just the directory) → *Refactor*.
   Result: `src/main/java/pl/wsb/restauracja/...`, and `UserAccount` moves to `pl.wsb.restauracja.account.domain`.
2. Right-click `ProjektApplication` → *Refactor → Rename* → `RestauracjaApplication`.

The rules find the root package by looking for the **single** class annotated with `@SpringBootApplication`
under `pl.wsb`. So there must be exactly one such class, and its package name must not be `projekt`.

Expected result:

```java
package pl.wsb.restauracja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestauracjaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestauracjaApplication.class, args);
    }
}
```

### Step 1.6 · `pom.xml`: artifactId and version (R1.01, R1.05)

```xml
<artifactId>wdtb-2026-backend-restauracja</artifactId>
<version>1.0.0-<index1>-<index2>-<index3></version>
```

For example `1.0.0-123456-123457-123458`. Each index number appears only once. Leave `<lab>1</lab>` as it is for now.
After editing, click the Maven *Reload* icon in IntelliJ.

Optional but tidy: in `application.properties`, set `spring.application.name=wdtb-2026-backend-restauracja`.

### Step 1.7 · Team accounts in `data.sql` (R1.03)

Keep the 4 demo accounts and add one account for each team member (at least 3 non-demo accounts), with role `USER`:

```sql
-- LAB 01: konta zespołu
insert into user_account (email, display_name, role, created_at) values
    ('imie1.nazwisko1@student.merito.pl', 'Imię1', 'USER', current_timestamp),
    ('imie2.nazwisko2@student.merito.pl', 'Imię2', 'USER', current_timestamp),
    ('imie3.nazwisko3@student.merito.pl', 'Imię3', 'USER', current_timestamp);
```

E-mails must be unique (the `email` column is `unique`).

### Step 1.8 · Business card in `static/index.html` (R1.04)

Replace **every** `[uzupełnij…]` placeholder in `src/main/resources/static/index.html`. The rule ignores HTML comments,
so the `<!-- … -->` comment above the section can stay. Suggested content:

```html
<section class="card project">
  <h2>Restauracja – rezerwacje stolików online</h2>
  <p class="pitch">Goście restauracji „Pod Lipą” rezerwują stolik online zamiast przez telefon, a właściciel widzi obłożenie sali i unika podwójnych rezerwacji.</p>
  <table>
    <tr><td>Autorzy</td><td>Imię1 Nazwisko1, Imię2 Nazwisko2, Imię3 Nazwisko3, grupa X</td></tr>
    <tr><td>Dla kogo</td><td>Marek, właściciel restauracji, chce przyjmować rezerwacje stolików online i wiedzieć, kiedy sala jest pełna; jego goście chcą szybko zarezerwować stolik.</td></tr>
    <tr><td>Najważniejsze funkcje</td><td>1) lista wolnych stolików w wybranym terminie, 2) rezerwacja z potwierdzeniem i odwołaniem, 3) raport obłożenia i niestawień dla właściciela</td></tr>
  </table>
  <h3>Zmiany w projekcie</h3>
  <ul>
    <li><b>LAB 01:</b> nazwa projektu (repozytorium, pakiet pl.wsb.restauracja, artifactId), wersja z numerami indeksów, konta zespołu w data.sql, wizytówka projektu.</li>
  </ul>
  <p class="muted">Na kolejnych laboratoriach dopisujcie tu kolejne punkty.</p>
</section>
```

Optionally also change `<title>` to `Restauracja · WdTB-2026-Backend`.

### Step 1.9 · Verify, commit, push

1. Run `RestauracjaApplication`. It should start, and <http://localhost:8080> should show the filled-in card.
2. In the H2 console, `SELECT * FROM user_account;` should show 7 rows (4 demo accounts + 3 team accounts).
3. Run the rules locally: *Maven* panel → *Lifecycle* → double-click **verify**. R1.01–R1.05 should all be green.
4. *Git → Commit*: `LAB 01: nazwa projektu, wersja, konta zespołu, wizytówka` → *Push*.
5. GitHub → *Actions* → the "Testy i reguły" run should be green.
6. Spreadsheet: fill in the project name, the repository link and a 1–2 sentence description.

### LAB 01 done when

- [ ] repo `WdTB-2026-Backend-restauracja`, package `pl.wsb.restauracja`, `RestauracjaApplication`
- [ ] `artifactId` `wdtb-2026-backend-restauracja`, version `1.0.0-<index1>-<index2>-<index3>`
- [ ] 3 team accounts in `data.sql`
- [ ] no `[uzupełnij` left in `index.html`
- [ ] `verify` green locally and in GitHub Actions, spreadsheet filled in

---

## Part 3 · LAB 02 (graded: grade 4 = R2.01–R2.05, grade 5 = also R2.06–R2.07)

Goal: **3 own tables with no relations yet**, each with at least 4 columns besides `id` and at least 1 required column,
and at least 3 realistic rows per table in `data.sql`, checked in the H2 console. For grade 5 you also need an enum column
and an `@Embeddable` class.

### Step 2.0 · Presentation at the start of class (2–3 minutes)

*Git → Pull*, run the app, open <http://localhost:8080>. Present: who you are, the idea (problem, persona,
3 features), and what changed in LAB 01. The card must already be on GitHub, not just on one laptop.

### Step 2.1 · Get the new materials (LAB 02 rules)

The local copy currently only has `src/test/java/pl/wsb/rules/lab01`. The LAB 02 rules come from the template:

1. GitHub fork page → **Sync fork → Update branch**. If GitHub reports a conflict, **do not** click *Discard commits*;
   ask the lecturer instead.
2. IntelliJ: *Git → Pull*. Check that `src/test/java/pl/wsb/rules/lab02` now exists.

### Step 2.2 · Turn on LAB 02 rules

In `pom.xml`, change `<lab>1</lab>` to `<lab>2</lab>`, then Maven *Reload*, then *Commit* `LAB 02: reguły`, then *Push*.
Running *Maven → verify* now runs the LAB 02 rules as well. They'll all be red at first; that's today's to-do list.

### Step 2.3 · Table design

Nothing references another table yet. For example, the reservation does **not** get `customer_id` or `table_id`;
those relations come in LAB 03.

**`dining_table`** (module `seating`, class `DiningTable`; a class named `Table` would clash with the JPA annotation)

| Field | Java type | Column | Constraints |
| --- | --- | --- | --- |
| tableNumber | `Integer` | `table_number` | required, unique |
| seats | `Integer` | `seats` | required |
| zone | `TableZone` (enum) | `zone` | required, `@Enumerated(STRING)` |
| active | `Boolean` | `active` | required |
| description | `String` | `description` | optional, length 120 |

**`customer`** (module `customer`, class `Customer`, plus `@Embeddable ContactDetails`)

| Field | Java type | Column | Constraints |
| --- | --- | --- | --- |
| firstName | `String` | `first_name` | required, 40 |
| lastName | `String` | `last_name` | required, 60 |
| registeredOn | `LocalDate` | `registered_on` | required |
| loyaltyPoints | `Integer` | `loyalty_points` | required |
| contact (embedded) | `ContactDetails` | `phone`, `email` | phone required, email optional |

**`reservation`** (module `booking`, class `Reservation`)

| Field | Java type | Column | Constraints |
| --- | --- | --- | --- |
| reservationCode | `String` | `reservation_code` | required, unique, 12 |
| startsAt | `LocalDateTime` | `starts_at` | required |
| partySize | `Integer` | `party_size` | required |
| status | `ReservationStatus` (enum) | `status` | required, `@Enumerated(STRING)` |
| deposit | `BigDecimal` | `deposit` | required, `precision = 10, scale = 2` |
| notes | `String` | `notes` | optional, 255 |

This design covers every rule: 3 entities (R2.01), each in a `<module>.entity` package (R2.02), at least 4 columns besides `id`
in every table (R2.04), at least one required column in every table (R2.05), two enums stored as STRING (R2.06), and one `@Embedded` field (R2.07).

Avoid SQL keywords as column names (`order`, `user`, `date`, `value`, `year`); that's why the columns have names like `starts_at` and `table_number`.

### Step 2.4 · Packages

Right-click `pl.wsb.restauracja` → *New → Package*, and create these three packages:

```
pl.wsb.restauracja.seating.entity
pl.wsb.restauracja.customer.entity
pl.wsb.restauracja.booking.entity
```

`UserAccount` stays in `account.domain`.

Target structure:

```
src/main/java/pl/wsb/restauracja/
├── RestauracjaApplication.java
├── account/domain/            AccountRole.java, UserAccount.java   (unchanged)
├── seating/entity/            TableZone.java, DiningTable.java
├── customer/entity/           ContactDetails.java, Customer.java
└── booking/entity/            ReservationStatus.java, Reservation.java
```

### Step 2.5 · Entity 1: `DiningTable` (+ enum `TableZone`)

`seating/entity/TableZone.java`:

```java
package pl.wsb.restauracja.seating.entity;

public enum TableZone {
    MAIN_HALL, TERRACE, PRIVATE_ROOM
}
```

`seating/entity/DiningTable.java`:

```java
package pl.wsb.restauracja.seating.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dining_table")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone", nullable = false, length = 20)
    private TableZone zone;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "description", length = 120)
    private String description;

    protected DiningTable() {
    }

    public DiningTable(Integer tableNumber, Integer seats, TableZone zone, Boolean active, String description) {
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.zone = zone;
        this.active = active;
        this.description = description;
    }

    public Long getId() { return id; }
    public Integer getTableNumber() { return tableNumber; }
    public Integer getSeats() { return seats; }
    public TableZone getZone() { return zone; }
    public Boolean getActive() { return active; }
    public String getDescription() { return description; }
}
```

Then *Rerun* the app. The log should contain:

```sql
create table dining_table (
    active boolean not null,
    seats integer not null,
    table_number integer not null unique,
    id bigint generated by default as identity,
    description varchar(120),
    zone enum ('MAIN_HALL','PRIVATE_ROOM','TERRACE') not null,
    primary key (id)
)
```

*Commit* `Encja stolika`, then *Push*.

### Step 2.6 · Entity 2: `Customer` (+ `@Embeddable ContactDetails`)

`customer/entity/ContactDetails.java` must be in the same `entity` package (R2.02). It doesn't get its own table:

```java
package pl.wsb.restauracja.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ContactDetails {

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "email", length = 120)
    private String email;

    protected ContactDetails() {
    }

    public ContactDetails(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
```

`customer/entity/Customer.java`:

```java
package pl.wsb.restauracja.customer.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name = "registered_on", nullable = false)
    private LocalDate registeredOn;

    @Column(name = "loyalty_points", nullable = false)
    private Integer loyaltyPoints;

    @Embedded
    private ContactDetails contact;

    protected Customer() {
    }

    public Customer(String firstName, String lastName, LocalDate registeredOn, Integer loyaltyPoints, ContactDetails contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredOn = registeredOn;
        this.loyaltyPoints = loyaltyPoints;
        this.contact = contact;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getRegisteredOn() { return registeredOn; }
    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public ContactDetails getContact() { return contact; }
}
```

*Rerun* and check that `create table customer` includes the `phone` and `email` columns. *Commit* `Encja gościa`, then *Push*.

### Step 2.7 · Entity 3: `Reservation` (+ enum `ReservationStatus`)

`booking/entity/ReservationStatus.java`:

```java
package pl.wsb.restauracja.booking.entity;

public enum ReservationStatus {
    PENDING, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW
}
```

`booking/entity/Reservation.java`:

```java
package pl.wsb.restauracja.booking.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_code", nullable = false, unique = true, length = 12)
    private String reservationCode;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "party_size", nullable = false)
    private Integer partySize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;

    @Column(name = "deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal deposit;

    @Column(name = "notes", length = 255)
    private String notes;

    protected Reservation() {
    }

    public Reservation(String reservationCode, LocalDateTime startsAt, Integer partySize,
                       ReservationStatus status, BigDecimal deposit, String notes) {
        this.reservationCode = reservationCode;
        this.startsAt = startsAt;
        this.partySize = partySize;
        this.status = status;
        this.deposit = deposit;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public String getReservationCode() { return reservationCode; }
    public LocalDateTime getStartsAt() { return startsAt; }
    public Integer getPartySize() { return partySize; }
    public ReservationStatus getStatus() { return status; }
    public BigDecimal getDeposit() { return deposit; }
    public String getNotes() { return notes; }
}
```

*Rerun* and check that the log shows `deposit numeric(10,2) not null` and `status enum (...)`. *Commit* `Encja rezerwacji`, then *Push*.

Tips:
- If an annotation is red, use *Alt+Enter → Import class* (from `jakarta.persistence`).
- The constructor and getters can be generated with *Code → Generate…* (*Alt+Insert*). Don't generate setters.
- The template has Lombok on the classpath, but the course examples use plain getters. Stick with plain getters so every team member can explain the code.

### Step 2.8 · Seed data in `data.sql` (R2.03)

Add these sections at the **end** of `src/main/resources/data.sql`, below the LAB 01 accounts. Don't include an `id` column;
column names must match `@Column(name)` exactly; enums go in quotes; decimals use a dot.

```sql
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

-- ===== LAB 02 · reservation =====
insert into reservation (reservation_code, starts_at, party_size, status, deposit, notes) values
    ('RES-2026-001', '2026-11-07 18:00:00', 2, 'CONFIRMED',  0.00,   'Rocznica, prośba o stolik przy oknie'),
    ('RES-2026-002', '2026-11-07 19:30:00', 4, 'PENDING',    50.00,  null),
    ('RES-2026-003', '2026-11-08 13:00:00', 8, 'CONFIRMED', 200.00, 'Urodziny, tort własny'),
    ('RES-2026-004', '2026-10-24 20:00:00', 3, 'NO_SHOW',    0.00,   null),
    ('RES-2026-005', '2026-10-18 17:00:00', 2, 'COMPLETED',  0.00,   null);
```

### Step 2.9 · Check in the H2 console

*Rerun*, open <http://localhost:8080/h2-console>, expand each table on the left, then run:

```sql
select * from dining_table;
select * from customer;
select * from reservation;
select status, count(*), sum(deposit) from reservation group by status;
select table_number, seats from dining_table where zone = 'TERRACE' and active = true;
```

Each table should show as many rows as you inserted. If a table is empty, it usually means the section is missing or the table name has a typo.

Common startup errors (read the log from the first `Caused by` line):

| Log message | Fix |
| --- | --- |
| `Column "…" not found` | the column name in `insert` differs from `@Column(name)` |
| `Table "…" not found` | the class is missing `@Entity`, is outside `pl.wsb.restauracja`, or `@Table(name)` differs |
| `NULL not allowed for column "…"` | a required column was left out (e.g. `phone`, `deposit`) |
| `Unique index or primary key violation` | duplicate `table_number` or `reservation_code` |
| `No default constructor for entity` | the `protected` no-arg constructor is missing |
| `Port 8080 was already in use` | a previous run is still active; stop it in *Run* |

### Step 2.10 · Rules, business card, push, show the run

1. *Maven → Lifecycle → verify*: LAB 01 and LAB 02 rules should be green, including the "na ocenę 5" set (R2.06–R2.07).
2. `static/index.html`: add a line to the changes list:

   ```html
   <li><b>LAB 02:</b> encje i dane startowe: dining_table (stoliki, strefa jako enum), customer (goście, dane kontaktowe jako @Embeddable), reservation (rezerwacje ze statusem i zadatkiem).</li>
   ```

3. *Commit* `Poprawki po regułach LAB 02, wizytówka`, then *Push*.
4. GitHub → *Actions* → "Testy i reguły" for the latest commit on `main`: the **Ocena 4** and **Ocena 5** stages should both be green.
   If there's no run, start one manually: *Run workflow* → branch `main`, lab number `2`.
5. Show the lecturer both stages and the *Summary*.
6. Optional: tag `lab-2` (*Git → Log* → right-click the last commit → *New Tag…* → *Push* with *Push Tags* ticked).

### LAB 02 done when

- [ ] `<lab>2</lab>`, `rules/lab02` present after Sync fork
- [ ] `DiningTable`, `Customer`, `Reservation` in `seating/customer/booking.entity` (R2.01, R2.02)
- [ ] ≥ 4 columns besides `id` and ≥ 1 `nullable = false` in each table (R2.04, R2.05)
- [ ] ≥ 3 rows per table from `data.sql`, app starts on a clean DB (R2.03)
- [ ] enums `TableZone`, `ReservationStatus` with `@Enumerated(EnumType.STRING)` (R2.06)
- [ ] `ContactDetails` `@Embeddable` used via `@Embedded` in `Customer` (R2.07)
- [ ] business card updated, Actions run green, shown to the lecturer
- [ ] every team member can explain every entity, the enums and `@Embeddable`

### Questions to prepare for at grading (a random person explains a random fragment)

- Why `BigDecimal` for `deposit` and not `double`? (`0.1 + 0.2 = 0.30000000000000004`; `numeric(10,2)` in the database)
- Why `@Enumerated(EnumType.STRING)`? (with `ORDINAL`, adding or reordering constants silently changes stored data)
- Why the `protected` no-arg constructor? (Hibernate needs it to create objects when reading rows)
- `@Entity` vs `@Embeddable`: does `ContactDetails` get its own table or `id`? (no; its columns live in `customer`)
- Why does data typed into the H2 console disappear after a restart? (`ddl-auto=create-drop` rebuilds the tables, then `data.sql` reloads)
- Why doesn't `reservation` have `customer_id` yet? (LAB 02 has no relations; they come in LAB 03)

---

## Looking ahead (so LAB 02 choices don't need rework)

- **LAB 03:** relations. `Reservation` gets `@ManyToOne Customer` and a link to `DiningTable`; there will also be a many-to-many with attributes, e.g.
  `reservation_table` (reservation + table) or `pre_order_item` (reservation + `menu_item` + quantity). Then repositories and
  the five investor questions, for example:
  1. How many reservations were completed per month, by zone?
  2. Which tables are booked most often on Friday and Saturday evenings?
  3. What is the no-show rate, and how much deposit was kept?
  4. What is the average party size per hour of the day?
  5. Which customers have the most completed visits / loyalty points?
- **LAB 04:** a `booking.application` service with rules, for example: no double booking of a table in the same time slot,
  `partySize <= seats`, cancellation only before `startsAt`, a status-transition check.
- **LAB 05:** REST, for example `GET /api/tables?available=…`, `POST /api/reservations`, `PATCH /api/reservations/{code}/cancel`, report endpoints.
- **LAB 06:** a scheduled job that marks `NO_SHOW` and an event when a reservation is confirmed.
