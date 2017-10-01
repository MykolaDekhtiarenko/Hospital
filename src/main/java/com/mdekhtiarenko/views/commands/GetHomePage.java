package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.Role;
import com.mdekhtiarenko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.mdekhtiarenko.views.Constants.*;

/**
 * Created by mykola.dekhtiarenko on 15.09.17.
 */
public class GetHomePage implements Command{
    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        User currentUser = (User) req.getSession().getAttribute(USER);
        if(currentUser!=null) {
            if (currentUser.getRole()== Role.DOCTOR||currentUser.getRole()==Role.NURSE)
                return prossesStaff(req, req.getSession());
            if (currentUser.getRole() == Role.PATIENT)
                return prossesPatient(req, req.getSession());
            else
                return LOGIN_PAGE;
        }
        else { return LOGIN_PAGE; }
    }

    private String prossesPatient(HttpServletRequest req, HttpSession session){
        User patient = (User)session.getAttribute(USER);
        Optional<User> fullPatientInfo = userService.getFullPatientInfo(patient.getId());
        if(fullPatientInfo.isPresent())
            req.setAttribute("fullPatientInfo", fullPatientInfo.get());
        return PATIENT_HOMEPAGE;
    }

    private String prossesStaff(HttpServletRequest req, HttpSession session){
        List<User> sick = userService.getSick();
        req.setAttribute("patientList", sick);
        return STAFF_HOMEPAGE;
    }

    private GetHomePage() {
        userService = UserService.getInstance();
    }

    //singelton pattern
    private static class Holder{
        private static GetHomePage INSTANCE = new GetHomePage();
    }

    public static GetHomePage getInstance(){
        return GetHomePage.Holder.INSTANCE;
    }

}
