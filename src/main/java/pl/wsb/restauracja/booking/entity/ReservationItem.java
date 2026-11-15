package pl.wsb.restauracja.booking.entity;

import jakarta.persistence.*;
import pl.wsb.restauracja.menu.entity.MenuItem;

@Entity
@Table(name = "reservation_item")
public class ReservationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // CELOWY BRAK LAZY
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(optional = false) // CELOWY BRAK LAZY
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "notes", length = 120)
    private String notes;

    protected ReservationItem() {}

    public ReservationItem(Reservation reservation, MenuItem menuItem, Integer quantity, String notes) {
        this.reservation = reservation;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public Reservation getReservation() { return reservation; }
    public MenuItem getMenuItem() { return menuItem; }
    public Integer getQuantity() { return quantity; }
    public String getNotes() { return notes; }
}