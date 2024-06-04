package com.example.visioners.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class ProhibitedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // getters and setters
}
