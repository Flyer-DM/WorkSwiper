package com.example.workswiper.Repos;

import com.example.workswiper.Domains.FirstTime;
import com.example.workswiper.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstTimeRepository extends JpaRepository<FirstTime, Long> {

    @Query("SELECT f FROM FirstTime f WHERE f.user_id = ?1")
    FirstTime findByUser_Id(User user);

}