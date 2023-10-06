package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Services.LinkService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.List;
import java.util.Optional;


@Controller
public class EmployeeController {

    private final UserServiceImpl userService;

    final PersonalDataService personalDataService;

    final LinkService linkService;

    @Autowired
    public EmployeeController(UserServiceImpl userService, PersonalDataService personalDataService,
                              LinkService linkService) {
        super();
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
    }

    @RequestMapping("/employee")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("employee");
        return mav;
    }

    @RequestMapping("/my_profile/{id}")
    public ModelAndView MyProfile(@PathVariable(name = "id") Long user_id) {
        ModelAndView mav = new ModelAndView("employee");
        Optional<User> user = userService.findByID(user_id);
        try {
            // временно для страницы пользователей с personaldata
            PersonalData personalData = personalDataService.findByUser_Id(user.get());
            List<Link> linkList = linkService.findByUser_Id(user.get());
            System.out.println(personalData.toString());
            System.out.println(user.get().toString());
            System.out.println(linkList);
            return mav;
        } catch (Exception e) {
            // временно для страницы пользователей без personaldata
            System.out.println(user.toString());
            return mav;
        }
    }
}
