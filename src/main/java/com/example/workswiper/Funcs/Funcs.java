package com.example.workswiper.Funcs;

import com.example.workswiper.Domains.Link;
import com.example.workswiper.Domains.Task;
import com.example.workswiper.Domains.Techstack;
import com.example.workswiper.Domains.UserFullData;
import com.example.workswiper.Services.LinkService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.Services.TaskService;
import com.example.workswiper.Services.TechStackService;
import com.example.workswiper.User.User;
import com.example.workswiper.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcs {

    public final UserRepository userRepository;

    public final TechStackService techStackService;

    final TaskService taskService;
    final PersonalDataService personalDataService;
    final LinkService linkService;

    public Funcs(UserRepository userRepository, TechStackService techStackService, TaskService taskService,
                 PersonalDataService personalDataService, LinkService linkService) {
        this.userRepository = userRepository;
        this.techStackService = techStackService;
        this.taskService = taskService;
        this.personalDataService = personalDataService;
        this.linkService = linkService;
    }

    public User getUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Matcher matcher = Pattern.compile("(Username=)([^,]+)").matcher(email);
        if (matcher.find()) {
            email = matcher.group(2);
        }
        return userRepository.findByEmail(email);
    }

    public List<Techstack> getTechsFromPage(HttpServletRequest request, String name) {
        String[] techs = request.getParameterValues(name);
        List<Techstack> techstacks = new ArrayList<>();
        if (techs != null) {
            for (String tech : techs) {
                techstacks.add(techStackService.findByTechnology(tech).get());
            }
        }
        return techstacks;
    }

    public UserFullData getUserFullData(Task task, User userToShow) {
            UserFullData userFullData = new UserFullData(userToShow);
            userFullData.setTaskLiked(task);
            userFullData.setPersonalData(personalDataService.findByUser_Id(userToShow));
            String links = String.join(" ", linkService.findByUser_Id(userToShow).stream().map(Link::getLink).toList());
            userFullData.setLinkList(links);
            String techs = String.join(" ", userToShow.getTechstacks().stream().map(Techstack::getTechnology).toList());
            userFullData.setTechstackList(techs);
            return userFullData;
    }
}
