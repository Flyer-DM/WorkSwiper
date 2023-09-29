package com.example.workswiper.Controllers;

import com.example.workswiper.User.Role;
import com.example.workswiper.User.UserRegistrationDto;
import com.example.workswiper.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * Контроллер страницы регистрации.
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        super();
        this.userService = userService;
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
            userService.save(registrationDto, Arrays.asList(new Role(role)));
            return "redirect:/registration?success";
        } else {
            return "redirect:/registration?failed";
        }
    }
}