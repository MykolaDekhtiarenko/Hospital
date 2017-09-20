package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.dao.impl.AssignmentDaoImpl;
import com.mdekhtiarenko.models.entities.Patient;
import com.mdekhtiarenko.models.entities.Staff;
import com.mdekhtiarenko.services.PatientService;
import com.mdekhtiarenko.services.StaffService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public class Login implements Command{

    public static final String PARAM_LOGIN = "email";
    public static final String PARAM_PASSWORD ="password";
    public static final String STAFF_HOMEPAGE ="/pages/staffHome.jsp";
    public static final String PATIENT_HOMEPAGE ="/pages/patientHome.jsp";
    public static final String LOGIN_PAGE ="/pages/login.jsp";


    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        logger.debug(email+" "+password);
        if( email != null && password != null ){
            Optional<Staff> staffUser = staffLogin(email, password);
            if(staffUser.isPresent()){
                logger.debug("Staff: "+email+" logged in!");
                request.getSession().setAttribute("stuffUser", staffUser.get());
                return STAFF_HOMEPAGE;
            }

            Optional<Patient> patientUser = patientLogin(email, password);
            if(patientUser.isPresent()){
                logger.debug("Patient: "+email+" logged in!");
                request.getSession().setAttribute("patientUser", staffUser.get());
                return PATIENT_HOMEPAGE;
            }
            ResourceBundle labelsBundle = ResourceBundle.getBundle("Labels");
            System.out.println(labelsBundle.getLocale()+": "+labelsBundle.getString("unable_to_login"));
            request.setAttribute("unableToLogin", labelsBundle.getString("unable_to_login"));
        }
        return LOGIN_PAGE;
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

