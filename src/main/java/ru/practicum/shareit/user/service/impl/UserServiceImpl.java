package ru.practicum.shareit.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        User createdUser = userRepository.createUser(user);
        logger.info("Создан пользователь {}", createdUser.getId());

        return createdUser;
    }

    public User getUser(Long userId) {
        Optional<User> user = userRepository.getUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Запрошен пользователь {}", userId);

        return user.get();
    }

    public Collection<User> getAllUsers() {
        Collection<User> users = userRepository.getAllUsers();
        logger.info("Запрошено {} пользователей", users.size());

        return users;
    }

    public User updateUser(Long userId, User user) {
        Optional<User> updatedUser = userRepository.updateUser(userId, user);

        if (updatedUser.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Запрошен пользователь {}", userId);

        return updatedUser.get();
    }

    public User deleteUser(Long userId) {
        Optional<User> user = userRepository.deleteUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Удалён пользователь {}", userId);

        return user.get();
    }

    private void throwUserNotFoundError(Long userId) {
        throw new UserNotFoundException("Пользователь " + userId + " не существует");
    }
}
