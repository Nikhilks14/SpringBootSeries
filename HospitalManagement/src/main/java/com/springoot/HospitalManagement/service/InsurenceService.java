package com.springoot.HospitalManagement.service;

import com.springoot.HospitalManagement.entity.Insurance;
import com.springoot.HospitalManagement.entity.Patient;
import com.springoot.HospitalManagement.repository.InsuranceRepository;
import com.springoot.HospitalManagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InsurenceService {

    private final InsuranceRepository insurenceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Insurance assignInsurenceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(insurance);

        insurance.setPatient(patient); // optional

        return insurance;
    }

}
