package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    User createUser(User user);

    User getUser(Long userId);

    Collection<User> getAllUsers();

    User updateUser(Long userId, User user);

    User deleteUser(Long userId);
}
