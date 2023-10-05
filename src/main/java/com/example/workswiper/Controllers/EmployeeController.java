package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.Optional;


@Controller
public class EmployeeController {

    @Autowired
    private final UserServiceImpl userService;

    @Autowired final PersonalDataService personalDataService;

    @Autowired
    public EmployeeController(UserServiceImpl userService, PersonalDataService personalDataService) {
        super();
        this.userService = userService;
        this.personalDataService = personalDataService;
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
            System.out.println(personalData.toString());
            return mav;
        } catch (Exception e) {
            // временно для страницы пользователей без personaldata
            System.out.println(user.toString());
            return mav;
        }
    }
}
