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