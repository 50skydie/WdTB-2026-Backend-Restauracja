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
