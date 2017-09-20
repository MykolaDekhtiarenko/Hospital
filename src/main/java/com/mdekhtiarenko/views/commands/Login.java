package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.models.entities.Staff;
import com.mdekhtiarenko.services.PatientService;
import com.mdekhtiarenko.services.StaffService;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.views.commands.Constants.*;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public class Login implements Command{

    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        if( email != null && password != null ){
            Optional<Staff> staffUser = staffLogin(email, password);
            if(staffUser.isPresent()){
                logger.debug("Staff: "+email+" logged in!");
                request.getSession().setAttribute(STAFF_USER, staffUser.get());
                return request.getD;
            }

            Optional<Patient> patientUser = patientLogin(email, password);
            if(patientUser.isPresent()){
                logger.debug("Patient: "+email+" logged in!");
                request.getSession().setAttribute(PATIENT_USER, patientUser.get());
                return new GetHomePage().execute(request, response);
            }
        }
        ResourceBundle labelsBundle = ResourceBundle.getBundle("Labels");
        request.setAttribute("unableToLogin", labelsBundle.getString("unable_to_login"));
        return new GetLoginPage().execute(request, response);
    }

    private Optional<Staff> staffLogin(String email, String password){
        StaffService service = StaffService.getInstance();
        return service.login(email, password);
    }

    private Optional<Patient> patientLogin(String email, String password){
        PatientService service = PatientService.getInstance();
        return service.login(email, password);
    }


}

