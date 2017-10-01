package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.TreatmentHistoryDao;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.TreatmentHistory;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class TreatmentHistoryService {

    private DaoFactory daoFactory;

    private TreatmentHistoryService(DaoFactory instance){
        daoFactory = instance;
    }

    public boolean create(int userId){
        TreatmentHistory treatmentHistory =
                TreatmentHistory.builder()
                .userId(userId)
                .build();
        return daoFactory.createTreatmentHistoryDAO().create(treatmentHistory);
    }

    public TreatmentHistory closeTreatmentHistory(TreatmentHistory treatmentHistory){
        TreatmentHistoryDao dao = daoFactory.createTreatmentHistoryDAO();
        treatmentHistory.setEndDate(new Date(Calendar.getInstance().getTimeInMillis()));
        return dao.update(treatmentHistory);
    }


    //singelton pattern
    private static class Holder{
        private static TreatmentHistoryService INSTANCE = new TreatmentHistoryService(DaoFactory.getInstance());
    }

    public static TreatmentHistoryService getInstance(){
        return TreatmentHistoryService.Holder.INSTANCE;
    }
}
