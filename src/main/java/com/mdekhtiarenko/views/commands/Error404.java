package com.mdekhtiarenko.views.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public class Error404 implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return "/pages/404.jsp";
    }

    private Error404() {
    }

    //singelton pattern
    private static class Holder{
        private static Error404 INSTANCE = new Error404();
    }

    public static Error404 getInstance(){
        return Error404.Holder.INSTANCE;
    }

}
