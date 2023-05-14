package ru.practicum.shareit.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
        User createdUser = userRepository.createUser(UserMapper.toUser(userDto));
        logger.info("Создан пользователь {}", createdUser.getId());

        return UserMapper.toUserDto(createdUser);
    }

    public UserDto getUser(Long userId) {
        Optional<User> user = userRepository.getUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Запрошен пользователь {}", userId);

        return UserMapper.toUserDto(user.get());
    }

    public Collection<UserDto> getAllUsers() {
        Collection<User> users = userRepository.getAllUsers();
        logger.info("Запрошено {} пользователей", users.size());

        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        Optional<User> updatedUser = userRepository.updateUser(userId, UserMapper.toUser(userDto));

        if (updatedUser.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Запрошен пользователь {}", userId);

        return UserMapper.toUserDto(updatedUser.get());
    }

    public UserDto deleteUser(Long userId) {
        Optional<User> user = userRepository.deleteUser(userId);

        if (user.isEmpty()) {
            throwUserNotFoundError(userId);
        }

        logger.info("Удалён пользователь {}", userId);

        return UserMapper.toUserDto(user.get());
    }

    private void throwUserNotFoundError(Long userId) {
        throw new UserNotFoundException("Пользователь " + userId + " не существует");
    }
}
