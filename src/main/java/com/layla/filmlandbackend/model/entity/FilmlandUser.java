package com.layla.filmlandbackend.model.entity;


import com.layla.filmlandbackend.enums.SubscriptionCategory;
import com.layla.filmlandbackend.exception.InvalidCategoryException;
import com.layla.filmlandbackend.exception.InvalidSubscriptionException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jshell.JShell;
import org.hibernate.proxy.HibernateProxy;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class FilmlandUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    private String roles;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriptions_id"))
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    public Set<Subscription> getSubscriptions() {
        return new LinkedHashSet<>(subscriptions);
    }

    public void addSubscriptions(Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public Subscription getSubscription(SubscriptionCategory category){
        return subscriptions
                .stream()
                .filter(subscription -> subscription.getCategory().equals(category))
                .findAny()
                .orElseThrow(() ->
                        new InvalidSubscriptionException("User is not subscribed to %s".formatted(category.getName())));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FilmlandUser that = (FilmlandUser) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "roles = " + roles + ")";
    }

    public FilmlandUser() {
    }

    public FilmlandUser(String username, String password, String roles, Subscription... subscriptions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.subscriptions = Arrays
                .stream(subscriptions)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public FilmlandUser(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
