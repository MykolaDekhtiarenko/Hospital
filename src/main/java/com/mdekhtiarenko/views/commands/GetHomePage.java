package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.Role;
import com.mdekhtiarenko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.mdekhtiarenko.views.commands.Constants.*;

/**
 * Created by mykola.dekhtiarenko on 15.09.17.
 */
public class GetHomePage implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User currentUser = (User) req.getSession().getAttribute(USER);
        if(currentUser!=null) {
            if (currentUser.getRole()== Role.DOCTOR||currentUser.getRole()==Role.NURSE)
                return STAFF_HOMEPAGE;
            if (currentUser.getRole() == Role.PATIENT)
                return prossesUser(req, req.getSession());
            else
                return LOGIN_PAGE;
        }
        else { return LOGIN_PAGE; }
    }

    private String prossesUser(HttpServletRequest req, HttpSession session){
        UserService service = UserService.getInstance();
        User patient = (User)session.getAttribute(USER);
        Optional<User> fullPatientInfo = service.getFullPatientInfo(patient.getId());
        if(fullPatientInfo.isPresent())
            req.setAttribute("fullPatientInfo", fullPatientInfo.get());
        return PATIENT_HOMEPAGE;
    }
}
