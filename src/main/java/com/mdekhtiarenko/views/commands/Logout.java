package com.mdekhtiarenko.views.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class Logout implements Command {
    private static final String URL_TO_GO = "/rest/login";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getSession().invalidate();
        return URL_TO_GO;
    }

    private Logout() {
    }

    //singelton pattern
    private static class Holder{
        private static Logout INSTANCE = new Logout();
    }

    public static Logout getInstance(){
        return Logout.Holder.INSTANCE;
    }

}
