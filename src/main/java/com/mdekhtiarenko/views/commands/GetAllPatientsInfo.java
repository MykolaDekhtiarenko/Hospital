package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.services.UserService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.mdekhtiarenko.views.Constants.STAFF_HOMEPAGE;

/**
 * Created by mykola.dekhtiarenko on 30.09.17.
 */
public class GetAllPatientsInfo implements Command {
    UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<User> patients = userService.getAll();
        req.setAttribute("patientList", patients);
        return STAFF_HOMEPAGE;
    }

    private GetAllPatientsInfo() {
        userService = UserService.getInstance();
    }

    //singelton pattern
    private static class Holder{
        private static GetAllPatientsInfo INSTANCE = new GetAllPatientsInfo();
    }

    public static GetAllPatientsInfo getInstance(){
        return GetAllPatientsInfo.Holder.INSTANCE;
    }


}
