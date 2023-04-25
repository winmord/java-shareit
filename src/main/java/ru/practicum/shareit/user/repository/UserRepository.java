package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    User createUser(User user);

    Optional<User> getUser(Long userId);

    Collection<User> getAllUsers();

    Optional<User> updateUser(Long userId, User user);

    Optional<User> deleteUser(Long userId);
}
