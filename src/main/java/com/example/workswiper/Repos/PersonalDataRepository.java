package com.example.workswiper.Repos;

import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    @Query("SELECT p FROM PersonalData p WHERE p.user_id = ?1")
    PersonalData findByUser_Id(User user);

}