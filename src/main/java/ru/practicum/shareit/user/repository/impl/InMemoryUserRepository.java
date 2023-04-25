package ru.practicum.shareit.user.repository.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.empty();
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> updateUser(Long userId, User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteUser(Long userId) {
        return Optional.empty();
    }
}
