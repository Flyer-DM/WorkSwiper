package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.*;
import com.example.workswiper.Funcs.Funcs;
import com.example.workswiper.Services.*;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class EmployeeController {

    final UserServiceImpl userService;

    final PersonalDataService personalDataService;

    final LinkService linkService;

    final UserRepository userRepository;

    final FirstTimeService firstTimeService;

    final TechStackService techStackService;

    final TaskService taskService;

    final Funcs funcs;


    @Autowired
    public EmployeeController(UserServiceImpl userService, PersonalDataService personalDataService,
                              LinkService linkService, UserRepository userRepository, TaskService taskService,
                              FirstTimeService firstTimeService, TechStackService techStackService) {
        super();
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
        this.userRepository = userRepository;
        this.firstTimeService = firstTimeService;
        this.techStackService = techStackService;
        this.taskService = taskService;
        this.funcs = new Funcs(userRepository, techStackService);
    }

    @RequestMapping("edit_profile")
    public ModelAndView EditProfile() {
        ModelAndView mav = new ModelAndView("edit_profile");
        User user = funcs.getUserByEmail();
        List<Techstack> techstackList = techStackService.findAll();
        PersonalData personalData = personalDataService.findByUser_Id(user);
        List<Link> linkList = linkService.findByUser_Id(user);
        List<String> strLinks = linkList.stream().map(Link::getLink).toList();
        String links = String.join(" ", strLinks);
        mav.addObject(user);
        mav.addObject(techstackList);
        mav.addObject(personalData);
        mav.addObject("links", links);
        return mav;
    }

    @RequestMapping("/employee")
    public ModelAndView Index() {
        ModelAndView mav = new ModelAndView("employee");
        User user = funcs.getUserByEmail();
        FirstTime firstTime = firstTimeService.findByUser_Id(user);
        if (firstTime.isFirst_time()) {
            firstTime.setFirst_time(false);
            firstTimeService.save(firstTime);
            return EditProfile();
        }
        List<Task> allTasks = taskService.findAll();
        List<Task> tasksSeen = (List<Task>) user.getTask_seen();
        List<Task> taskStared = (List<Task>) user.getTask_stared();
        List<Task> allTasksSeen = Stream.concat(tasksSeen.stream(), taskStared.stream()).toList();
        List<Task> tasks = allTasks.stream().filter(f -> !allTasksSeen.contains(f)).toList();
        mav.addObject("tasks", tasks);
        mav.addObject("username", user.getLastName() + " " + user.getFirstName());
        return mav;
    }

    @RequestMapping("/profile")
    public ModelAndView MyProfile() {
        ModelAndView mav = new ModelAndView("profile");
        User user = funcs.getUserByEmail();
        PersonalData personalData = personalDataService.findByUser_Id(user);
        List<Link> linkList = linkService.findByUser_Id(user);
        Collection<Techstack> techStackList = user.getTechstacks();
        mav.addObject("firstName", user.getFirstName());
        mav.addObject("lastName", user.getLastName());
        mav.addObject("email", user.getEmail());
        mav.addObject(personalData);
        mav.addObject(linkList);
        mav.addObject("techStackList", techStackList);
        return mav;
    }

    @RequestMapping("/save_profile")
    public ModelAndView SaveProfile(HttpServletRequest request) {
        User user =  funcs.getUserByEmail();
        PersonalData personalData = personalDataService.findByUser_Id(user);
        String age = request.getParameter("age");
        if (age != null) personalData.setAge(Long.valueOf(age));
        String country = request.getParameter("country");
        if (country != null) personalData.setCountry(country);
        String city = request.getParameter("city");
        if (city != null) personalData.setCity(city);
        String education = request.getParameter("education");
        if (education != null) personalData.setEducation(education);
        String aboutme = request.getParameter("aboutme");
        if (aboutme != null) personalData.setAboutme(aboutme);
        String telephone = request.getParameter("telephone");
        if (telephone != null) personalData.setTelephone(telephone);
        personalDataService.save(personalData);
        List<Techstack> techstacks = funcs.getTechsFromPage(request, "techs");
        user.setTechstacks(techstacks);
        userService.save(user);
        String linksTextArea = request.getParameter("links");
        List<Link> linkFromDB = linkService.findByUser_Id(user);
        for (Link linkDB: linkFromDB) {
            linkService.delete(linkDB.getId());
        }
        if (Objects.nonNull(linksTextArea) && !linksTextArea.isEmpty()) {
            linksTextArea = linksTextArea.replace(" ", "\n");
            for (String link : linksTextArea.split("\n")) {
                String linkText = link.substring(link.indexOf("//") + 2, link.indexOf("/", 8));
                linkService.save(new Link(linkText, link, user));
            }
        }
        return MyProfile();
    }
}
