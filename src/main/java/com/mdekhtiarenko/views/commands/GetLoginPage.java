package com.mdekhtiarenko.views.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mykola.dekhtiarenko on 20.09.17.
 */
public class GetLoginPage implements Command {
    public static final String LOGIN_PAGE ="/pages/login.jsp";
    private final Logger logger = Logger.getLogger(Login.class.getName());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        logger.debug("GET LOGIN PAGE COMMAND");
        System.out.println();
        return LOGIN_PAGE;
    }
}
