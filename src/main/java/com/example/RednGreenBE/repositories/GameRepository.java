package com.example.RednGreenBE.repositories;

import com.example.RednGreenBE.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g LEFT JOIN g.owners o GROUP BY g.id ORDER BY COUNT(o) DESC")
    List<Game> findGamesWithMostOwners();
    boolean existsByName(String name);

}
