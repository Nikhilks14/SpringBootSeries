package com.springoot.HospitalManagement;

import com.springoot.HospitalManagement.dto.BloodGroupStat;
import com.springoot.HospitalManagement.dto.CPatientInfo;
import com.springoot.HospitalManagement.dto.IPatientIno;
import com.springoot.HospitalManagement.entity.Patient;
import com.springoot.HospitalManagement.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient() {
//        List<Patient> patients = patientRepository.findAll();
//        List<IPatientIno> patients = patientRepository.getAllPatientsInfo();
//
//        for(IPatientIno patient : patients) {
//            System.out.println(patient);
//        }

//        List<CPatientInfo> patients = patientRepository.getAllPatientsInfoConcret();

//        List<BloodGroupStat> patients = patientRepository.getBloodStats();
//        for(var patient : patients) {
//            System.out.println(patient);
//        }

        int rowsAffected = patientRepository.updatePatientNameWithId("NIKHIL" , 1L);
        System.out.println(rowsAffected);
    }
}
