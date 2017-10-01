package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mdekhtiarenko.views.Constants.USER;

/**
 * Created by mykola.dekhtiarenko on 20.09.17.
 */
public class GetLoginPage implements Command {
    public static final String LOGIN_PAGE ="/pages/login.jsp";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(((User)req.getSession().getAttribute(USER))!=null)
            return GetHomePage.getInstance().execute(req, res);
        return LOGIN_PAGE;
    }

    private GetLoginPage() {
    }

    //singelton pattern
    private static class Holder{
        private static GetLoginPage INSTANCE = new GetLoginPage();
    }

    public static GetLoginPage getInstance(){
        return GetLoginPage.Holder.INSTANCE;
    }
}
