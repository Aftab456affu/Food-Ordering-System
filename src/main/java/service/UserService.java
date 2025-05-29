package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public void registerUser(String name, double initialWallet) {
        users.put(name, new User(name, initialWallet));
    }

    public User getUser(String name) {
        User user = users.get(name);
        if (user == null) throw new IllegalArgumentException("User not found");
        return user;
    }
}
