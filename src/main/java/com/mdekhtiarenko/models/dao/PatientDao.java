package com.mdekhtiarenko.models.dao;

import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.models.entities.TreatmentHistory;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public interface PatientDao extends GenericDao<Patient> {
    List<TreatmentHistory> getTreatmentHistoryListForPatient(Integer patientId);
    Patient getPatientByEmail(String email);
}
