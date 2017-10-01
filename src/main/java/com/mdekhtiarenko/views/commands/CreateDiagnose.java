package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.services.DiagnoseService;
import com.mdekhtiarenko.utils.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.views.Constants.USER;

/**
 * Created by mykola.dekhtiarenko on 27.09.17.
 */
public class CreateDiagnose implements Command {
    private DiagnoseService diagnoseService;
    private ResourceBundle bundle;
    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int treatmentHistoryId = Integer.valueOf(req.getParameter("treatmentHistoryId"));
        String diagnoseText = req.getParameter("diagnose");
        User user = (User)req.getSession().getAttribute(USER);

        String error = null;
        if(Validator.isFine(diagnoseText, Validator.TEXT)) {
            Diagnose diagnose = Diagnose
                    .builder()
                    .diagnose(diagnoseText)
                    .treatmentHistoryId(treatmentHistoryId)
                    .creatorId(user.getId())
                    .build();
            diagnoseService.createDiagnose(diagnose);
            logger.info(diagnose.toString() +" was created by "+user.toString());
        }
        else {
            error = bundle.getString("wrong_diagnose");
        }

        if(error!=null)
            req.setAttribute("error", error);

        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }




    private CreateDiagnose() {
        diagnoseService = DiagnoseService.getInstance();
        bundle = ResourceBundle.getBundle("Labels");
    }

    //singelton pattern
    private static class Holder{
        private static CreateDiagnose INSTANCE = new CreateDiagnose();
    }

    public static CreateDiagnose getInstance(){
        return CreateDiagnose.Holder.INSTANCE;
    }
}
