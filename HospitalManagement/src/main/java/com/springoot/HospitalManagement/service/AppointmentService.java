package com.springoot.HospitalManagement.service;

import com.springoot.HospitalManagement.entity.Appointment;
import com.springoot.HospitalManagement.entity.Doctor;
import com.springoot.HospitalManagement.entity.Patient;
import com.springoot.HospitalManagement.repository.AppointmentRepository;
import com.springoot.HospitalManagement.repository.DoctorRepository;
import com.springoot.HospitalManagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private  final AppointmentRepository appointmentRepository;
    private  final DoctorRepository doctorRepository;
    private  final PatientRepository  patientRepository;

    @Transactional
    public  Appointment createNewAppointment(Appointment appointment, Long patientId, Long doctorId){

        Patient  patient = patientRepository.findById(patientId).orElseThrow();
        Doctor  doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);

        return appointment;
    }

}















