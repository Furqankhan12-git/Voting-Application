package net.engineeringdigest.votingapp.service;


import net.engineeringdigest.votingapp.model.User;
import net.engineeringdigest.votingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostConstruct
    public void init() {
        // Create an admin user
        if (userRepository.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin");
            adminUser.setEmailId("Admin123@gmail.com");
            adminUser.setPhoneNo("9372355141");
            adminUser.setAdmin(true);
            adminUser.setHasVoted(null);
            userRepository.save(adminUser);
        }
    }
}
