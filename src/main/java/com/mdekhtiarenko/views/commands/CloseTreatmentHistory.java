package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.TreatmentHistory;
import com.mdekhtiarenko.services.TreatmentHistoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class CloseTreatmentHistory implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        TreatmentHistory treatmentHistory = TreatmentHistory.builder()
                .conclusion(req.getParameter("conclusion"))
                .id(Integer.valueOf(req.getParameter("treatmentHistoryId")))
                .build();
        TreatmentHistoryService service = TreatmentHistoryService.getInstance();
        Response response = service.closeTreatmentHistory(treatmentHistory);
        if(response.hasErrors())
            req.setAttribute("error", response.getErrorMessage());
        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }

    private CloseTreatmentHistory() {
    }

    //singelton pattern
    private static class Holder{
        private static CloseTreatmentHistory INSTANCE = new CloseTreatmentHistory();
    }

    public static CloseTreatmentHistory getInstance(){
        return CloseTreatmentHistory.Holder.INSTANCE;
    }

}
