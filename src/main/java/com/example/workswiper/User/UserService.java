package com.example.workswiper.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс сервиса, который вызывает на исполнение различные операции к БД сущности User.
 */
public interface UserService extends UserDetailsService {

    /**
     * Метод сохраняет нового пользователя в БД.
     * @param registrationDto - информация, которая сохраняет при регистрации в БД.
     * @return сохранённый объект класса User.
     */
    User save(UserRegistrationDto registrationDto, Collection<Role> roles);

    /**
     * Метод получает список всех пользователей из БД.
     * @return список объектов представления класса User.
     */
    List<User> getAll();
}