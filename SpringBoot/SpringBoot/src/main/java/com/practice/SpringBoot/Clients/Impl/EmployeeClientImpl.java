package com.practice.SpringBoot.Clients.Impl;

import com.practice.SpringBoot.Clients.EmployeeClient;
import com.practice.SpringBoot.Dto.Employeedto;
import com.practice.SpringBoot.advice.ApiResponse;
import com.practice.SpringBoot.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<Employeedto> getAllEmployees() {

        try {
            ApiResponse<List<Employeedto>> employeedtos = restClient.get()
                    .uri("/employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeedtos.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Employeedto getEmployeeById(Long employeeid) {
       try{
           ApiResponse<Employeedto> employeeResponse = restClient.get()
                   .uri("employees/{employeeId}", employeeid)
                   .retrieve()
                   .body(new ParameterizedTypeReference<>() {});

           return employeeResponse.getData();
       }
       catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Employeedto createEmployee(Employeedto employeedto) {

        try{

            ResponseEntity<ApiResponse<Employeedto>> newEmployee = restClient.post()
                    .uri("employees")
                    .body(employeedto)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res) ->{
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
//                    .body(new ParameterizedTypeReference<>() {});

//            return newEmployee.getData();
            return newEmployee.getBody().getData();

        }
        catch (Exception e) {
        throw new RuntimeException(e);
        }
    }
}
