package com.example.workswiper.Controllers;

import com.example.workswiper.Domains.FirstTime;
import com.example.workswiper.Domains.PersonalData;
import com.example.workswiper.Services.FirstTimeService;
import com.example.workswiper.Services.PersonalDataService;
import com.example.workswiper.User.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Контроллер страницы регистрации.
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    final UserRepository userRepository;

    final PersonalDataService personalDataService;

    final FirstTimeService firstTimeService;

    public RegistrationController(UserService userService, UserRepository userRepository,
                                  PersonalDataService personalDataService, FirstTimeService firstTimeService) {
        super();
        this.userService = userService;
        this.userRepository = userRepository;
        this.personalDataService = personalDataService;
        this.firstTimeService = firstTimeService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    /**
     * Маппинг выполняет действия при запросе пользователя главной страницы registration.
     *
     * @return имя html страницы с регистрацией.
     */
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    /**
     * Метод сохраняет нового пользователя в БД и перенаправляет его обратно на страницу авторизации.
     *
     * @param registrationDto введённая пользователем информация.
     * @return перенаправление на странцу регистрации.
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto,
                                      HttpServletRequest request) {
        String role = request.getParameter("role");
        if (role != null) {
            userService.save(registrationDto, List.of(new Role(role)));
            User user = userRepository.findByEmail(registrationDto.getEmail());
            personalDataService.save(new PersonalData(user));
            if (role.equals("ROLE_EMPLOYEE")) firstTimeService.save(new FirstTime(user));
            return "redirect:/registration?success";
        } else {
            return "redirect:/registration?failed";
        }
    }
}