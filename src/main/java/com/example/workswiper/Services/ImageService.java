package com.example.workswiper.Services;

import com.example.workswiper.Domains.Image;
import com.example.workswiper.User.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ImageService {
    public Image create(Image image);
    public void save(Image image);
    public List<Image> viewAll();
    public Image viewById(long id);
    public Image findByUser_Id(User user);
}