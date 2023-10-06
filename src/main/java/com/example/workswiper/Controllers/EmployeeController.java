package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Domains.Techstack;
import com.example.workswiper.Services.LinkService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class EmployeeController {

    final UserServiceImpl userService;

    final PersonalDataService personalDataService;

    final LinkService linkService;

    final UserRepository userRepository;


    @Autowired
    public EmployeeController(UserServiceImpl userService, PersonalDataService personalDataService,
                              LinkService linkService, UserRepository userRepository) {
        super();
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
        this.userRepository = userRepository;
    }

    public User getUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Matcher matcher = Pattern.compile("(Username=)([^,]+)").matcher(email);
        if (matcher.find()) {
            email = matcher.group(2);
        }
        return userRepository.findByEmail(email);
    }

    @RequestMapping("/employee")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("employee");
        User user = getUserByEmail();
        return mav;
    }

    @RequestMapping("/profile")
    public ModelAndView MyProfile() {
        ModelAndView mav = new ModelAndView("employee");
        User user = getUserByEmail();
        PersonalData personalData = personalDataService.findByUser_Id(user);
        List<Link> linkList = linkService.findByUser_Id(user);
        Collection<Techstack> techstackList = user.getTechstacks();
        mav.addObject("firstName", user.getFirstName());
        mav.addObject("lastName", user.getLastName());
        mav.addObject("email", user.getEmail());
        mav.addObject("personalData", personalData);
        mav.addObject("linkList", linkList);
        mav.addObject("techstackList", techstackList);
        return mav;
    }
}
