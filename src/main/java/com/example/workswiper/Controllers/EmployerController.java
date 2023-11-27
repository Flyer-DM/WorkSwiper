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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


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
        for (Task task : taskService.findByUser_Id(me)) {
            List<User> allUsers = Stream.concat(task.getUsersLikedFromEmployer().stream(),
                    task.getUsersArchivedFromEmployer().stream()).toList();
            List<User> usersToShow = task.getUsersLiked().stream().filter(f -> !allUsers.contains(f)).toList();
            for (User user : usersToShow) {
                UserFullData userFullData = new UserFullData(user);
                userFullData.setTaskLiked(task);
                userFullData.setPersonalData(personalDataService.findByUser_Id(user));
                String links = String.join(" ", linkService.findByUser_Id(user).stream().map(Link::getLink).toList());
                userFullData.setLinkList(links);
                String techs = String.join(" ", user.getTechstacks().stream().map(Techstack::getTechnology).toList());
                userFullData.setTechstackList(techs);
                userFullDataList.add(userFullData);
            }
        }
        mav.addObject(userFullDataList);
        mav.addObject("username", me.getLastName() + " " + me.getFirstName());
        return mav;
    }

    @RequestMapping("/new_task")
    public String newTask(Model model) {
        Task task = new Task();
        model.addAttribute("techstack", techStackService.findAll());
        model.addAttribute("task", task);
        return "new_task";
    }

    @RequestMapping("/my_tasks")
    public String myTasks(Model model) {
        User me = funcs.getUserByEmail();
        model.addAttribute("myTasks", taskService.findByUser_Id(me));
        return "my_tasks";
    }

    @RequestMapping("/delete_task/{id}")
    public String deleteTask(Model model, @PathVariable(name = "id") Long id) {
        taskService.delete(id);
        return myTasks(model);
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

    @RequestMapping("/check_liked_user")
    public ModelAndView updateLikes(HttpServletRequest request) {
        List<String> liked = new ArrayList<>(Arrays.asList(request.getParameter("likedCard").split(",")));
        System.out.println(liked);
        if (!liked.isEmpty()) {
            Task task = taskService.get(Long.valueOf(liked.get(1)));
            List<User> users = task.getUsersLikedFromEmployer();
            users.add(userService.findByID(Long.valueOf(liked.get(0))).get());
            task.setUsersLikedFromEmployer(users);
            taskService.save(task);
        }
        return index();
    }

    @RequestMapping("/check_disliked_user")
    public ModelAndView updateDislikes(HttpServletRequest request) {
        List<String> disliked = new ArrayList<>(Arrays.asList(request.getParameter("dislikedCard").split(",")));
        System.out.println(disliked);
        if (!disliked.isEmpty()) {
            Task task = taskService.get(Long.valueOf(disliked.get(1)));
            List<User> users = task.getUsersArchivedFromEmployer();
            users.add(userService.findByID(Long.valueOf(disliked.get(0))).get());
            task.setUsersArchivedFromEmployer(users);
            taskService.save(task);
        }
        return index();
    }
}
