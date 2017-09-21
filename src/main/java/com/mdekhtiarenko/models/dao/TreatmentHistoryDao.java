package com.mdekhtiarenko.models.dao;

import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.TreatmentHistory;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 09.09.17.
 */
public interface TreatmentHistoryDao extends GenericDao<TreatmentHistory> {
    List<Diagnose> getDiagnoseListForTreatmentHistory(Integer treatmentHistoryId, DiagnoseDao diagnoseDao);
    }
