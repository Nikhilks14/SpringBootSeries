package com.practice.SpringBoot;

import com.practice.SpringBoot.Clients.EmployeeClient;
import com.practice.SpringBoot.Dto.Employeedto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void getAllEmployees() {
		List<Employeedto> list = employeeClient.getAllEmployees();

		for (Employeedto employeedto : list) {
			System.out.println(employeedto);
		}
	}

	@Test
	void getEmployeeById() {
		Employeedto employeedto = employeeClient.getEmployeeById(1L);
			System.out.println(employeedto);
	}

	@Test
	void createNewEmployee() {
			Employeedto employeedto = new Employeedto(null, "Anuj",
					"anuj@gmail.com",20,"USER",
					LocalDate.of(2022,10,10),10000
					, true);

			Employeedto employeedto1 = employeeClient.createEmployee(employeedto);
			System.out.println(employeedto1);


	}
}
