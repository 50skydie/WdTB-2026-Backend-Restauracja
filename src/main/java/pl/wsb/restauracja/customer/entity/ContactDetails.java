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