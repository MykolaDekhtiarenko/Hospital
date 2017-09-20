package com.mdekhtiarenko.views.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mykola.dekhtiarenko on 15.09.17.
 */
public class GetHomePage implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return "/pages/main.jsp";
    }
}
