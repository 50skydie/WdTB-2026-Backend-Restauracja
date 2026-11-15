# Schemat bazy danych

Plik generują reguły projektu (panel Maven → Lifecycle → verify). Nie edytujcie go ręcznie.

Tabele: 6 · kolumny: 38 · relacje: 4 × @ManyToOne, 1 × @OneToMany, 1 × @OneToOne, 0 × @ManyToMany

| Tabela | Encja | Kolumny | Klucze obce |
|---|---|---|---|
| customer | Customer | 8 | user_account_id |
| dining_table | DiningTable | 6 | — |
| menu_item | MenuItem | 5 | — |
| reservation | Reservation | 9 | customer_id, dining_table_id |
| reservation_item | ReservationItem | 5 | menu_item_id, reservation_id |
| user_account | UserAccount | 5 | — |

```mermaid
erDiagram
    customer {
        Long id PK
        String email
        String first_name
        String last_name
        Integer loyalty_points
        String phone
        LocalDate registered_on
        Long user_account_id FK
    }
    dining_table {
        Long id PK
        Boolean active
        String description
        Integer seats
        Integer table_number
        TableZone zone
    }
    menu_item {
        Long id PK
        String category
        Boolean is_available
        String name
        BigDecimal price
    }
    reservation {
        Long id PK
        Long customer_id FK
        BigDecimal deposit
        Long dining_table_id FK
        String notes
        Integer party_size
        String reservation_code
        LocalDateTime starts_at
        ReservationStatus status
    }
    reservation_item {
        Long id PK
        Long menu_item_id FK
        String notes
        Integer quantity
        Long reservation_id FK
    }
    user_account {
        Long id PK
        LocalDateTime created_at
        String display_name
        String email
        AccountRole role
    }
    customer ||--o{ reservation : "customer_id"
    dining_table ||--o{ reservation : "dining_table_id"
    menu_item ||--o{ reservation_item : "menu_item_id"
    reservation ||--o{ reservation_item : "reservation_id"
    user_account |o--o| customer : "user_account_id"
```
