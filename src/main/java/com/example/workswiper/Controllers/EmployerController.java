package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Domains.Techstack;
import com.example.workswiper.Services.FirstTimeService;
import com.example.workswiper.Services.LinkService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.Services.TechStackService;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class EmployerController {

    final UserServiceImpl userService;

    final PersonalDataService personalDataService;

    final LinkService linkService;

    final UserRepository userRepository;

    final FirstTimeService firstTimeService;

    final TechStackService techStackService;

    @Autowired
    public EmployerController(UserServiceImpl userService, PersonalDataService personalDataService, LinkService linkService, UserRepository userRepository, FirstTimeService firstTimeService, TechStackService techStackService) {
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
        this.userRepository = userRepository;
        this.firstTimeService = firstTimeService;
        this.techStackService = techStackService;
    }

    @RequestMapping("/employer")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("employer");
        List<User> listUsers = userService.getAll();
        List<PersonalData> personalDataList = new ArrayList<>();
        List<String> TechStacks = new ArrayList<>();
        List<List<Link>> linksList = new ArrayList<>();
        List<User> filteredListUsers = listUsers.stream()
                .filter(u -> Objects.equals(u.getRoles().stream().findFirst().get().getName(), "ROLE_EMPLOYEE"))
                .toList();
        for (User user: filteredListUsers) {
            personalDataList.add(personalDataService.findByUser_Id(user));
            List<String> technologies = user.getTechstacks().stream().map(Techstack::getTechnology).toList();
            TechStacks.add(String.join(" ", technologies));
            linksList.add(linkService.findByUser_Id(user));
        }

        mav.addObject("listUsers", filteredListUsers);
        mav.addObject(personalDataList);
        mav.addObject("TechStacks", TechStacks);
        mav.addObject("linksList", linksList);
        return mav;
    }
}
