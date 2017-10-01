package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.views.Constants.*;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public class Login implements Command{

    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        if( email != null && password != null ){
            Optional<User> user = patientLogin(email, password);
            if(user.isPresent()){
                logger.info("User: "+email+" logged in!");
                request.getSession().setAttribute(USER, user.get());
                return GetHomePage.getInstance().execute(request, response);
            }
        }
        ResourceBundle labelsBundle = ResourceBundle.getBundle("Labels");
        request.setAttribute("unableToLogin", labelsBundle.getString("unable_to_login"));
        return GetLoginPage.getInstance().execute(request, response);
    }
    

    private Optional<User> patientLogin(String email, String password){
        UserService service = UserService.getInstance();
        return service.login(email, password);
    }

    private Login() {
    }

    //singelton pattern
    private static class Holder{
        private static Login INSTANCE = new Login();
    }

    public static Login getInstance(){
        return Login.Holder.INSTANCE;
    }



}

