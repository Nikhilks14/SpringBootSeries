package com.springoot.HospitalManagement;

import com.springoot.HospitalManagement.entity.Appointment;
import com.springoot.HospitalManagement.entity.Doctor;
import com.springoot.HospitalManagement.entity.Insurance;
import com.springoot.HospitalManagement.entity.Patient;
import com.springoot.HospitalManagement.repository.DoctorRepository;
import com.springoot.HospitalManagement.repository.PatientRepository;
import com.springoot.HospitalManagement.service.AppointmentService;
import com.springoot.HospitalManagement.service.InsurenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootTest
public class InsuranceServiceTest {

    @Autowired
    private InsurenceService insurenceService;

    @Autowired
    private AppointmentService  appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testAssignInsuranceToPatient() {

        Patient patient = Patient.builder()
                .name("Nikhil")
                .birthDate(LocalDate.of(2000,12,12))
                .email("nikhil@gmail.com")
                .gender("Male")
                .createdAt(LocalDateTime.of(2021,1,1, 12,01,11))
                .build();

        patientRepository.save(patient);

        Insurance insurance = Insurance.builder()
                .provider("Hdfc")
                .policyNumber("HDFC_123")
                .validUntil(LocalDate.of(2027,1,1))
                .build();

        var updateInsurence = insurenceService.assignInsurenceToPatient (insurance,1L);

        System.out.println(insurance);
    }

    @Test
    public void testCreateAppointment() {

        Patient patient = Patient.builder()
                .name("Nikhil")
                .birthDate(LocalDate.of(2000,12,12))
                .email("nikhil@gmail.com")
                .gender("Male")
                .createdAt(LocalDateTime.of(2021,1,1, 12,01,11))
                .build();

        patientRepository.save(patient);

        Doctor doctor = Doctor.builder()
                .name("Raj")
                .specialization("General Physician")
                .email("raj@gmail.com")
                .build();

        doctorRepository.save(doctor);

        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.now())
                .reason("Checkup")
                .build();

        var updateappointment = appointmentService.createNewAppointment(appointment, 1L, 1L);
        System.out.println(updateappointment);
    }

}
