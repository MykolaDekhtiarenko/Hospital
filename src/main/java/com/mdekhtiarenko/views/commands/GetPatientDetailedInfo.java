package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.mdekhtiarenko.views.Constants.ERROR_PAGE;
import static com.mdekhtiarenko.views.Constants.PATIENT_DETAILED_INFO;

/**
 * Created by mykola.dekhtiarenko on 24.09.17.
 */
public class GetPatientDetailedInfo implements Command {
    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        if(req.getParameter("userId")!=null){
            int id = Integer.valueOf(req.getParameter("userId"));
            Optional<User> user = userService.getFullPatientInfo(id);
            if(user.isPresent())
                req.setAttribute("fullPatientInfo", user.get());
            else return ERROR_PAGE;
        }
        else{
            return ERROR_PAGE;
        }

        return PATIENT_DETAILED_INFO;
    }

    private GetPatientDetailedInfo() {
        userService = UserService.getInstance();
    }

    //singelton pattern
    private static class Holder{
        private static GetPatientDetailedInfo INSTANCE = new GetPatientDetailedInfo();
    }

    public static GetPatientDetailedInfo getInstance(){
        return GetPatientDetailedInfo.Holder.INSTANCE;
    }

}
