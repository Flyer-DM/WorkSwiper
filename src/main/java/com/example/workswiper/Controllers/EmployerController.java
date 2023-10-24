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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.workswiper.User.UserServiceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
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
}
