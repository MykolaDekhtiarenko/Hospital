package com.mdekhtiarenko.views.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.views.Constants.CREATE_USER_PAGE;

/**
 * Created by mykola.dekhtiarenko on 03.10.17.
 */
public class GetRegistrationPage implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return CREATE_USER_PAGE;
    }

    private GetRegistrationPage() {
    }

    private static class Holder{
        private static GetRegistrationPage INSTANCE = new GetRegistrationPage();
    }

    public static GetRegistrationPage getInstance(){
        return GetRegistrationPage.Holder.INSTANCE;
    }

}
