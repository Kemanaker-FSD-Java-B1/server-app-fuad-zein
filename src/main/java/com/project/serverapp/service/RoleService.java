package com.project.serverapp.service;

import com.project.serverapp.model.Role;
import com.project.serverapp.repo.RoleRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RoleService {

  private RoleRepo roleRepo;

  public List<Role> getAll() {
    return roleRepo.findAll();
  }

  @SuppressWarnings("null")
  public Role getById(Integer id) {
    return roleRepo
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!!!")
      );
  }

  @SuppressWarnings("null")
  public Role create(Role role) {
    return roleRepo.save(role);
  }

  public Role update(Integer id, Role role) {
    getById(id); // validasi
    role.setId(id); // set id
    return roleRepo.save(role);
  }

  @SuppressWarnings("null")
  public Role delete(Integer id) {
    Role role = getById(id);
    roleRepo.delete(role);
    return role;
  }
}
