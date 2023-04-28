package ru.practicum.shareit.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    public User getUser(Long userId) {
        Optional<User> user = userRepository.getUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        return user.get();
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User updateUser(Long userId, User user) {
        Optional<User> updatedUser = userRepository.updateUser(userId, user);

        if (updatedUser.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        return updatedUser.get();
    }

    public User deleteUser(Long userId) {
        Optional<User> user = userRepository.deleteUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        return user.get();
    }

    private void throwUserNotFoundError(Long userId) {
        throw new UserNotFoundException("Пользователь " + userId + " не существует");
    }
}
