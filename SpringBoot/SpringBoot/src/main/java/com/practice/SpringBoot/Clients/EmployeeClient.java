package com.practice.SpringBoot.Clients;

import com.practice.SpringBoot.Dto.Employeedto;

import java.util.List;

public interface EmployeeClient {

     List<Employeedto> getAllEmployees();

     Employeedto getEmployeeById(Long id);

     Employeedto createEmployee(Employeedto employeedto);

     // put the data
     // patch data
     // delete the data
}
