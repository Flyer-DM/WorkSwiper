package com.example.workswiper.Repos;

import com.example.workswiper.Domains.Techstack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechStackRepository extends JpaRepository<Techstack, Long> {

    @Query("SELECT t FROM Techstack t WHERE t.technology = ?1")
    Optional<Techstack> findByTechnology(String technology);

}