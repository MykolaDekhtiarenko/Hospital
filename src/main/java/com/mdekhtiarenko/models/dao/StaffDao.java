package com.mdekhtiarenko.models.dao;

import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Staff;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public interface StaffDao extends GenericDao<Staff> {
    List<Diagnose> getDiagnoseListForStaff(Integer staffId);
    List<Assignment> getAssigmentListForStaff(Integer staffId);
    Staff getStaffByEmail(String emai);
}
