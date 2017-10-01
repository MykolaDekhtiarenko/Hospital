package com.mdekhtiarenko.models.dao;

import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.entities.TreatmentHistory;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public interface UserDao extends GenericDao<User> {
    List<TreatmentHistory> getTreatmentHistoryList(Integer userId,
                                                   TreatmentHistoryDao treatmentHistoryDao,
                                                   DiagnoseDao diagnoseDao);
    List<Diagnose> getDiagnoseList(Integer userId);
    User getPatientByEmail(String email);
    List<User> getSick();
}
