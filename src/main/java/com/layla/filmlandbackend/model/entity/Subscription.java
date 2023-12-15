package com.layla.filmlandbackend.model.entity;

import com.layla.filmlandbackend.enums.SubscriptionCategory;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
    private Set<FilmlandUser> users = new LinkedHashSet<>();

    public Subscription(){

    }

    public Subscription(SubscriptionCategory category, double price, String startDate, Set<FilmlandUser> users) {
        this.category = category;
        this.price = price;
        this.startDate = startDate;
        this.users = users;
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

    public Set<FilmlandUser> getUsers() {
        return users;
    }

    public void setUsers(Set<FilmlandUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && category == that.category && Objects.equals(startDate, that.startDate) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, price, startDate, users);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", category=" + category +
                ", price=" + price +
                ", startDate='" + startDate + '\'' +
                ", users=" + users +
                '}';
    }
}
