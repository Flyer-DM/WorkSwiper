package com.example.workswiper.Services;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.Repos.LinkRepository;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;


    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void save(Link link){
        linkRepository.save(link);
    }

    public void delete(Long id){
        linkRepository.deleteById(id);
    }

    public Link get(Long id){
        return linkRepository.findById(id).get();
    }

    public List<Link> findByUser_Id(User user) {
        return linkRepository.findByUser_Id(user);
    }

}
