package com.example.workswiper.Services;

import com.example.workswiper.Domains.FirstTime;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Repos.FirstTimeRepository;
import com.example.workswiper.Repos.PersonalDataRepository;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FirstTimeService {

    private final FirstTimeRepository firstTimeRepository;


    @Autowired
    public FirstTimeService(FirstTimeRepository firstTimeRepository) {
        this.firstTimeRepository = firstTimeRepository;
    }

    public void save(FirstTime firstTime){
        firstTimeRepository.save(firstTime);
    }

    public void delete(Long id){
        firstTimeRepository.deleteById(id);
    }

    public FirstTime get(Long id){
        return firstTimeRepository.findById(id).get();
    }

    public FirstTime findByUser_Id(User user) {
        return firstTimeRepository.findByUser_Id(user);
    }

}
