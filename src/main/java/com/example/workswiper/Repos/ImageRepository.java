package com.example.workswiper.Repos;

import com.example.workswiper.Domains.Image;
import com.example.workswiper.User.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.user_id = ?1")
    Image findByUser_Id(User user);


}