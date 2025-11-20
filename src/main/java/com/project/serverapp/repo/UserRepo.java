package com.project.serverapp.repo;

import com.project.serverapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
  Optional<User> findByUsernameOrEmployeeEmail(String username, String email);
}
