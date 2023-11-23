package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.*;
import com.example.workswiper.Funcs.Funcs;
import com.example.workswiper.Services.*;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EmployerController {

    final UserServiceImpl userService;

    final PersonalDataService personalDataService;

    final LinkService linkService;

    final UserRepository userRepository;

    final FirstTimeService firstTimeService;

    final TechStackService techStackService;

    final TaskService taskService;

    final Funcs funcs;


    @Autowired
    public EmployerController(UserServiceImpl userService, PersonalDataService personalDataService,
                              LinkService linkService, UserRepository userRepository, FirstTimeService firstTimeService,
                              TechStackService techStackService, TaskService taskService) {
        this.userService = userService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
        this.userRepository = userRepository;
        this.firstTimeService = firstTimeService;
        this.techStackService = techStackService;
        this.taskService = taskService;
        this.funcs = new Funcs(userRepository, techStackService);
    }

    @RequestMapping("/employer")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("employer");
        User me = funcs.getUserByEmail();
        List<UserFullData> userFullDataList = new ArrayList<>();
        for (Task task: taskService.findByUser_Id(me)) {
            for (User user: task.getUsersLiked()) {
                UserFullData userFullData = new UserFullData(user);
                userFullData.setTaskLiked(task);
                userFullData.setPersonalData(personalDataService.findByUser_Id(user));
                String links = String.join(" ", linkService.findByUser_Id(user).stream().map(Link::getLink).toList());
                userFullData.setTechstackList(links);
                String techs = String.join(" ", user.getTechstacks().stream().map(Techstack::getTechnology).toList());
                userFullData.setTechstackList(techs);
                userFullDataList.add(userFullData);
            }
        }
        mav.addObject(userFullDataList);
        return mav;
    }

    @RequestMapping("/new_task")
    public String newTask(Model model) {
        Task task = new Task();
        model.addAttribute("techstack", techStackService.findAll());
        model.addAttribute("task", task);
        return "new_task";
    }

    @RequestMapping(value = "/save_task")
    public String saveTask(@RequestParam String name, @RequestParam String description,
                           @RequestParam String starttime, @RequestParam String endtime,
                           @RequestParam String result, HttpServletRequest request) throws ParseException {
        User user_id = funcs.getUserByEmail();
        Price price_id = new Price(new BigDecimal(request.getParameter("price")),
                                                  request.getParameter("currency"));
        Task task = new Task(name, description, starttime, endtime, result, user_id, price_id);
        List<Techstack> techstacks = funcs.getTechsFromPage(request, "techs");
        task.setTechstacks(techstacks);
        taskService.save(task);
        return "redirect:/employer";
    }

    @RequestMapping("/check_cards")
    public ModelAndView lookForCards(HttpServletRequest request) {
        System.out.println("liked: " + request.getParameter("cardCount2"));
        System.out.println("DISliked: " + request.getParameter("cardCount3"));
        return index();
    }
}
