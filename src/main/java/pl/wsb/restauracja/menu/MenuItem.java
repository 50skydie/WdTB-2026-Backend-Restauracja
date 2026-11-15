package pl.wsb.restauracja.menu.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    protected MenuItem() {
    }

    public MenuItem(String name, BigDecimal price, String category, Boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public Boolean getIsAvailable() { return isAvailable; }
}