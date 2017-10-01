package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.TreatmentHistory;
import com.mdekhtiarenko.services.TreatmentHistoryService;
import com.mdekhtiarenko.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class CloseTreatmentHistory implements Command {
    private TreatmentHistoryService treatmentHistoryService;
    private ResourceBundle bundle;
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        TreatmentHistory treatmentHistory = TreatmentHistory.builder()
                .conclusion(req.getParameter("conclusion"))
                .id(Integer.valueOf(req.getParameter("treatmentHistoryId")))
                .build();
        String error = null;
        if(Validator.isFine(treatmentHistory.getConclusion(), Validator.TEXT)){
            treatmentHistoryService.closeTreatmentHistory(treatmentHistory);
        }
        else{
            error = bundle.getString("patient_detailed_info.no_conclusion_error");
        }

        if(error!=null)
            req.setAttribute("error", error);
        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }

    private CloseTreatmentHistory() {
        treatmentHistoryService = TreatmentHistoryService.getInstance();
        bundle = ResourceBundle.getBundle("Labels");
    }

    //singelton pattern
    private static class Holder{
        private static CloseTreatmentHistory INSTANCE = new CloseTreatmentHistory();
    }

    public static CloseTreatmentHistory getInstance(){
        return CloseTreatmentHistory.Holder.INSTANCE;
    }

}
