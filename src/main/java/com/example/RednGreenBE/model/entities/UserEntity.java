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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private BigDecimal money;
    @OneToOne()
    private AddressData address;
    @OneToMany(mappedBy = "author")
    private List<Game> publishedGames;

    @ManyToMany(mappedBy = "owners")
    List<Game> ownedGames;

    public BigDecimal getMoney() {
        return money;
    }

    public UserEntity setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
}
