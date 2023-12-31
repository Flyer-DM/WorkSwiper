package com.example.workswiper.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс сервиса, который вызывает на исполнение различные операции к БД сущности User.
 */
public interface UserService extends UserDetailsService {

    List<User> getAll();

    /**
     * Метод сохраняет нового пользователя в БД.
     *
     * @param registrationDto - информация, которая сохраняет при регистрации в БД.
     */
    void save(UserRegistrationDto registrationDto, Collection<Role> roles);

    void save(User user);
}