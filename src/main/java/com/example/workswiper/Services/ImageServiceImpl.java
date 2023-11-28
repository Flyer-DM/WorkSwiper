package com.example.workswiper.Services;

import com.example.workswiper.Domains.Image;
import com.example.workswiper.Repos.ImageRepository;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }
    @Override
    public void save(Image image){
        imageRepository.save(image);
    }
    @Override
    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }
    @Override
    public Image viewById(long id) {
        return imageRepository.findById(id).get();
    }
    @Override
    public Image findByUser_Id(User user) {
        return imageRepository.findByUser_Id(user);
    }
}