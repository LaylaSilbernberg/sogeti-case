package com.layla.filmlandbackend.enums;

public enum SubscriptionCategory {
    DUTCH_FILMS("Dutch films", 10, 4.0),
    DUTCH_SERIES("Dutch series", 20, 6.0),
    INTERNATIONAL_FILMS("International films", 10, 8.0);

    private final String name;
    private final Integer availableContent;
    private final Double price;

    SubscriptionCategory(String name, Integer availableContent, Double price) {
        this.name = name;
        this.availableContent = availableContent;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getAvailableContent() {
        return availableContent;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "SubscriptionCategory{" +
                "name='" + name + '\'' +
                ", availableContent=" + availableContent +
                ", price=" + price +
                '}';
    }
}
