package com.project.serverapp.service;

import com.project.serverapp.model.AppUserDetail;
import com.project.serverapp.model.User;
import com.project.serverapp.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepo
      .findByUsernameOrEmployeeEmail(username, username)
      .orElseThrow(() ->
        new UsernameNotFoundException("Username or Email not found!!!")
      );

    return new AppUserDetail(user);
  }
}
