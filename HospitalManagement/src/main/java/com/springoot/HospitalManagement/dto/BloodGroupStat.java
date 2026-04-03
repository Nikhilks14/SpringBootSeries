package com.springoot.HospitalManagement.dto;

import com.springoot.HospitalManagement.entity.type.BloodGroupType;
import lombok.Data;

@Data
public class BloodGroupStat {
    private final BloodGroupType bloodGroupStat;
    private final Long count;
}
