package com.example.workswiper.Funcs;

import com.example.workswiper.Domains.Techstack;
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

    public Funcs(UserRepository userRepository, TechStackService techStackService) {
        this.userRepository = userRepository;
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
}
