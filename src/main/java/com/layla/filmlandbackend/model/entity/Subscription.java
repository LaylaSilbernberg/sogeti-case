package com.layla.filmlandbackend.model.entity;

import com.layla.filmlandbackend.model.dto.SubscriptionDTO;
import com.layla.filmlandbackend.enums.SubscriptionCategory;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionCategory category;

    private double price;

    private String startDate;

    private int uses;

    @ManyToMany(mappedBy = "subscriptions", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<FilmlandUser> users = new LinkedHashSet<>();

    public Set<FilmlandUser> getUsers() {
        return new LinkedHashSet<>(users);
    }

    public void addUser(FilmlandUser user) {
        users.add(user);
        price = BigDecimal.valueOf(price)
                .divide(BigDecimal.valueOf(users.size()), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }


    public Subscription(){

    }

    public Subscription(SubscriptionCategory category, FilmlandUser... filmlandUsers) {
        this.category = category;
        this.price = this.category.getPrice();
        this.startDate = LocalDate.now().toString();
        users = Arrays
                .stream(filmlandUsers)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Long getId() {
        return id;
    }

    public SubscriptionCategory getCategory() {
        return category;
    }

    public void setCategory(SubscriptionCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public void incrementUses(){
        this.uses++;
    }
    public void resetUses(){
        this.uses = 0;
    }


    public SubscriptionDTO makeDto(){
        return new SubscriptionDTO(
                this.category.getName(),
                this.category.getAvailableContent() - uses,
                this.price,
                startDate
        );
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Subscription that = (Subscription) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
