package com.example.workswiper.Repos;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT l FROM Link l WHERE l.user_id = ?1")
    List<Link> findByUser_Id(User user);

    @Query("SELECT l FROM Link l WHERE l.link = ?1")
    Optional<Link> findByLink(String link);
}