package ru.practicum.shareit.user.repository.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Long autoIncrementId = 1L;
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();

    @Override
    public User createUser(User user) {
        String email = user.getEmail();

        if (emails.contains(email)) {
            throw new IllegalArgumentException("Пользователь с email " + email + " уже существует");
        }

        user.setId(autoIncrementId++);
        users.put(user.getId(), user);
        emails.add(email);

        return user;
    }

    @Override
    public Optional<User> getUser(Long userId) {
        try {
            return Optional.of(users.get(userId));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public Optional<User> updateUser(Long userId, User user) {
        if (!users.containsKey(userId)) return Optional.empty();

        User existingUser = users.get(userId);

        String name = user.getName();
        if (name != null && !name.isBlank()) {
            existingUser.setName(name);
        }

        String email = user.getEmail();
        if (email != null) {
            if (email.isBlank() || (!Objects.equals(existingUser.getEmail(), email) && emails.contains(email))) {
                throw new IllegalArgumentException("Недопустимый email: " + email);
            }
            emails.remove(existingUser.getEmail());
            emails.add(email);
            existingUser.setEmail(email);
        }

        return Optional.of(existingUser);
    }

    @Override
    public Optional<User> deleteUser(Long userId) {
        if (!users.containsKey(userId)) return Optional.empty();

        emails.remove(users.get(userId).getEmail());
        return Optional.of(users.remove(userId));
    }
}
