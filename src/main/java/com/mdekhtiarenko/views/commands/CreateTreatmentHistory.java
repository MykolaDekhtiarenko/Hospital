package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.services.TreatmentHistoryService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mdekhtiarenko.utils.UserUtils.hasDoctorUser;
import static com.mdekhtiarenko.views.Constants.ERROR_PAGE;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class CreateTreatmentHistory implements Command {
    private TreatmentHistoryService treatmentHistoryService;
    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int userId = Integer.valueOf(req.getParameter("userId"));
        if(hasDoctorUser(req.getSession())) {
            treatmentHistoryService.create(userId);
            logger.info("Created new treatment history for user wit id "+ userId);
            return GetPatientDetailedInfo.getInstance().execute(req, res);
        }
        return ERROR_PAGE;
    }

    private CreateTreatmentHistory() {
        treatmentHistoryService = TreatmentHistoryService.getInstance();
    }

    //singelton pattern
    private static class Holder{
        private static CreateTreatmentHistory INSTANCE = new CreateTreatmentHistory();
    }

    public static CreateTreatmentHistory getInstance(){
        return CreateTreatmentHistory.Holder.INSTANCE;
    }

}
