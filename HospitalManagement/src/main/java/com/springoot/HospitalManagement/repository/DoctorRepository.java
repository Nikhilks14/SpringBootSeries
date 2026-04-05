package com.springoot.HospitalManagement.repository;

import com.springoot.HospitalManagement.entity.Appointment;
import com.springoot.HospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
