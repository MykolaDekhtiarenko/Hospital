package com.mdekhtiarenko.views.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mykola.dekhtiarenko on 30.09.17.
 */
public class SetLocale implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String localeToSet = req.getParameter("locale");
        req.getSession().setAttribute("language", localeToSet);
        return "";
    }

    private SetLocale() {
    }

    //singelton pattern
    private static class Holder{
        private static SetLocale INSTANCE = new SetLocale();
    }

    public static SetLocale getInstance(){
        return SetLocale.Holder.INSTANCE;
    }
}
