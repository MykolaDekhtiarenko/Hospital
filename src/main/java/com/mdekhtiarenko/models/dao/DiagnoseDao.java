package com.mdekhtiarenko.models.dao;

import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public interface DiagnoseDao extends GenericDao<Diagnose> {
    List<Assignment> getAssignmentListForDiagnose(Integer diagnoseId);
}
