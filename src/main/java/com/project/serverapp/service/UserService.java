package com.project.serverapp.service;

import com.project.serverapp.model.User;
import com.project.serverapp.repo.UserRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepo userRepo;

  public List<User> getAll() {
    return userRepo.findAll();
  }

  @SuppressWarnings("null")
  public User getById(Integer id) {
    return userRepo
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!!")
      );
  }

  // ! not use -> one to one 
  // @SuppressWarnings("null")
  // public User create(User user) {
  //   return userRepo.save(user);
  // }

  public User update(Integer id, User user) {
    getById(id); // validasi
    user.setId(id); // set id
    return userRepo.save(user);
  }

  // ! not use -> one to one
  // @SuppressWarnings("null")
  // public User delete(Integer id) {
  //   User user = getById(id);
  //   userRepo.delete(user);
  //   return user;
  // }
}
