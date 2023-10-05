package com.example.workswiper.Services;

import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Repos.PersonalDataRepository;
import com.example.workswiper.User.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PersonalDataService {

    private final PersonalDataRepository personalDataRepository;


    @Autowired
    public PersonalDataService(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    public void save(PersonalData personalData){
        personalDataRepository.save(personalData);
    }

    public void delete(Long id){
        personalDataRepository.deleteById(id);
    }

    public PersonalData get(Long id){
        return personalDataRepository.findById(id).get();
    }

    public PersonalData findByUser_Id(User user) {
        return personalDataRepository.findByUser_Id(user);
    }

}
