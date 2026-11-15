package pl.wsb.restauracja.customer.entity;

import jakarta.persistence.*;
import pl.wsb.restauracja.account.domain.UserAccount;
import pl.wsb.restauracja.booking.entity.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Customer(String firstName, String lastName, LocalDate registeredOn, Integer loyaltyPoints, ContactDetails contact, UserAccount account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredOn = registeredOn;
        this.loyaltyPoints = loyaltyPoints;
        this.contact = contact;
        this.account = account;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getRegisteredOn() { return registeredOn; }
    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public ContactDetails getContact() { return contact; }
    public UserAccount getAccount() { return account; }
    public List<Reservation> getReservations() { return reservations; }


    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount account;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();

}