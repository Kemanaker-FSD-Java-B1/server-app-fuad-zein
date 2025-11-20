package com.project.serverapp.service;

import com.project.serverapp.model.Employee;
import com.project.serverapp.repo.EmployeeRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

  private EmployeeRepo employeeRepo;

  public List<Employee> getAll() {
    return employeeRepo.findAll();
  }

  @SuppressWarnings("null")
  public Employee getById(Integer id) {
    return employeeRepo
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Employee not found!!!"
        )
      );
  }

  // ! not use -> one to one 
  // @SuppressWarnings("null")
  // public Employee create(Employee employee) {
  //   return employeeRepo.save(employee);
  // }

  public Employee update(Integer id, Employee employee) {
    getById(id); // validasi
    employee.setId(id); // set id
    return employeeRepo.save(employee);
  }

  @SuppressWarnings("null")
  public Employee delete(Integer id) {
    Employee employee = getById(id);
    employeeRepo.delete(employee);
    return employee;
  }
}
