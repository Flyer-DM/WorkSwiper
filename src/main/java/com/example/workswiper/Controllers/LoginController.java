package com.example.workswiper.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер страницы авторизации.
 *
 * @version 1.0
 */
@Controller
public class LoginController {

    /**
     * Маппинг выполняет действия при запросе пользователя главной страницы сервиса.
     *
     * @return имя html страницы с авторизацией.
     */
    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return index();
    }

}