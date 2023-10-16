package com.example.workswiper.Services;

import com.example.workswiper.Domains.Techstack;
import com.example.workswiper.Repos.TechStackRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@Service
public class TechStackService {

    private final TechStackRepository techStackRepository;

    @Autowired
    public TechStackService(TechStackRepository techStackRepository) {
        this.techStackRepository = techStackRepository;
    }

    public Optional<Techstack> findByTechnology(String technology) {
        return techStackRepository.findByTechnology(technology);
    }

    public List<Techstack> findAll() {
        return techStackRepository.findAll(Sort.by(Sort.Direction.ASC, "technology"));
    }

    public void save(Techstack techstack){
        techStackRepository.save(techstack);
    }

    public void delete(Long id){
        techStackRepository.deleteById(id);
    }

    public Techstack get(Long id){
        return techStackRepository.findById(id).get();
    }

}
