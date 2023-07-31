package com.example.RednGreenBE.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private UserEntity author;

    @ManyToMany
    @JoinTable(name = "game_owners",
    joinColumns = @JoinColumn(name = "game_id"),
    inverseJoinColumns = @JoinColumn(name = "user_entity_id"))
    private List<UserEntity> owners;
}
