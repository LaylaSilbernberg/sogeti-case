package com.layla.filmlandbackend.model.entity;


import com.layla.filmlandbackend.enums.SubscriptionCategory;
import jakarta.persistence.*;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class FilmlandUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    private String roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "subscription_users",
    joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id",
            nullable = true, updatable = true),
    },
    inverseJoinColumns = {
            @JoinColumn (name = "subscription_id", referencedColumnName = "id",
            nullable = true, updatable = true)
    })
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    public FilmlandUser() {
    }

    public FilmlandUser(String username, String password, String roles, Set<Subscription> subscriptions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.subscriptions = subscriptions;
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

    public Set<Subscription> getSubscriptions() {
        return new LinkedHashSet<>(subscriptions);
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmlandUser that = (FilmlandUser) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(roles, that.roles) && Objects.equals(subscriptions, that.subscriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, subscriptions);
    }

    @Override
    public String toString() {
        return "FilmlandUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }

}
