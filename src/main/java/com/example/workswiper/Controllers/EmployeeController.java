package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.FirstTime;
import com.example.workswiper.Domains.Link;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Domains.Techstack;
import com.example.workswiper.Services.FirstTimeService;
import com.example.workswiper.Services.LinkService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.Services.TechStackService;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.util.ArrayList;
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

    final FirstTimeService firstTimeService;

    final TechStackService techStackService;


    @Autowired
    public EmployeeController(UserServiceImpl userService, PersonalDataService personalDataService,
                              LinkService linkService, UserRepository userRepository,
                              FirstTimeService firstTimeService, TechStackService techStackService) {
        super();
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
        this.userRepository = userRepository;
        this.firstTimeService = firstTimeService;
        this.techStackService = techStackService;
    }

    public User getUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Matcher matcher = Pattern.compile("(Username=)([^,]+)").matcher(email);
        if (matcher.find()) {
            email = matcher.group(2);
        }
        return userRepository.findByEmail(email);
    }

    @RequestMapping("edit_profile")
    public ModelAndView EditProfile() {
        ModelAndView mav = new ModelAndView("edit_profile");
        User user = getUserByEmail();
        List<Techstack> techstackList = techStackService.findAll();
        PersonalData personalData = personalDataService.findByUser_Id(user);
        List<Link> linkList = linkService.findByUser_Id(user);
        List<String> strLinks = linkList.stream().map(Link::getLink).toList();
        List<String> strLinksTexts = linkList.stream().map(Link::getText).toList();
        List<String> template = new ArrayList<>();
        for(int i = 0; i < strLinks.size(); i++) {
            template.add(strLinksTexts.get(i) + "[" + strLinks.get(i) + "]");
        }
        String links = String.join("\n", template);
        mav.addObject(user);
        mav.addObject(techstackList);
        mav.addObject(personalData);
        mav.addObject("links", links);
        return mav;
    }

    @RequestMapping("/employee")
    public ModelAndView Index() {
        ModelAndView mav = new ModelAndView("employee");
        User user = getUserByEmail();
        FirstTime firstTime = firstTimeService.findByUser_Id(user);
        if (firstTime.isFirst_time()) {
            firstTime.setFirst_time(false);
            firstTimeService.save(firstTime);
            return EditProfile();
        }
        return mav;
    }

    @RequestMapping("/profile")
    public ModelAndView MyProfile() {
        ModelAndView mav = new ModelAndView("profile");
        User user = getUserByEmail();
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
        User user = getUserByEmail();
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
        String[] techs = request.getParameterValues("techs");
        List<Techstack> techstacks = new ArrayList<>();
        if (techs != null) {
            for (String tech : techs) {
                techstacks.add(techStackService.findByTechnology(tech).get());
            }
        }
        user.setTechstacks(techstacks);
        userService.save(user);
//        String links = request.getParameter("links");
//        if (links != null) {
//            List<Link> AllUserLinks = linkService.findByUser_Id(user);
//            for (String link : links.split("\n")) {
//                String linkText = link.substring(0, link.indexOf("["));
//                String linkLink = link.substring(link.indexOf("[") + 1, link.indexOf("]"));
//
//                }
//            }
        return MyProfile();
    }
}
