package com.springoot.HospitalManagement.repository;

import com.springoot.HospitalManagement.dto.BloodGroupStat;
import com.springoot.HospitalManagement.dto.CPatientInfo;
import com.springoot.HospitalManagement.dto.IPatientIno;
import com.springoot.HospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByEmailContaining(String email);

    @Query("select p.id as id, p.name as name, p.email as emeil from Patient p")
    List<IPatientIno> getAllPatientsInfo();

    @Query("select new com.springoot.HospitalManagement.dto.CPatientInfo (p.id , p.name) " +
            "from Patient p")
    List<CPatientInfo> getAllPatientsInfoConcret();

    @Query("select new com.springoot.HospitalManagement.dto.BloodGroupStat( p.bloodGroup , " +
    "COUNT(p)) from Patient p group by p.bloodGroup order by COUNT(p) DESC")
    List<BloodGroupStat> getBloodStats();

    @Transactional
    @Modifying
    @Query("UPDATE Patient p set p.name =:name where p.id=:id")
    int updatePatientNameWithId(@Param("name") String name, @Param("id") Long id);

}
