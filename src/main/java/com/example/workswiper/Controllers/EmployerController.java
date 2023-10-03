package com.example.workswiper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;


@Controller
public class EmployerController {

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    public EmployerController(UserServiceImpl userService) {
        super();
        this.userService = userService;
    }

    @RequestMapping("/employer")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("employer");
        return mav;
    }
}
