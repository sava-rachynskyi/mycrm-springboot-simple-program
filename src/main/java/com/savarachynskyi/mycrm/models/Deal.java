package com.savarachynskyi.mycrm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String category;
    private double price;
    private String status = "IN_PROGRESS";
    private LocalDate createdAt = LocalDate.now();
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id") // Створює правильний зовнішній ключ у таблиці Deal
    @JsonIgnoreProperties("deals")
    private Client client;

    public Deal() {
    }

    public Deal(long id, String title, String description, String category, double price, String status, LocalDate createdAt, LocalDate endDate, Client client) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.endDate = endDate;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}