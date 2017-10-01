package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.services.DiagnoseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mdekhtiarenko.views.Constants.USER;

/**
 * Created by mykola.dekhtiarenko on 27.09.17.
 */
public class CreateDiagnose implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int treatmentHistoryId = Integer.valueOf(req.getParameter("treatmentHistoryId"));
        String diagnoseText = req.getParameter("diagnose");
        User user = (User)req.getSession().getAttribute(USER);
        Diagnose diagnose = Diagnose
                .builder()
                .diagnose(diagnoseText)
                .treatmentHistoryId(treatmentHistoryId)
                .creatorId(user.getId())
                .build();
        DiagnoseService service = DiagnoseService.getInstance();
        Response response = service.createDiagnose(diagnose);
        if(response.hasErrors())
            req.setAttribute("error", response.getErrorMessage());

        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }




    private CreateDiagnose() {
    }

    //singelton pattern
    private static class Holder{
        private static CreateDiagnose INSTANCE = new CreateDiagnose();
    }

    public static CreateDiagnose getInstance(){
        return CreateDiagnose.Holder.INSTANCE;
    }
}
