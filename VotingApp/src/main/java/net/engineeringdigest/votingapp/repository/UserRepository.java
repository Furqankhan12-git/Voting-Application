package net.engineeringdigest.votingapp.repository;


import net.engineeringdigest.votingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    List<User> findAll();
}

