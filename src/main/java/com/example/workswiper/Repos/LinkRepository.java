package com.example.workswiper.Repos;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT p FROM Link p WHERE p.user_id = ?1")
    List<Link> findByUser_Id(User user);

}