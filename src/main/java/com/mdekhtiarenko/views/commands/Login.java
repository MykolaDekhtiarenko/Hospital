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
    private ResourceBundle bundle;
    private UserService userService;
    private final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        if( email != null && password != null ){
//            System.out.println(userService.login(email, password));
            Optional<User> user = userService.login(email, password);
            if(user.isPresent()){
                logger.info("User: "+email+" logged in!");
                request.getSession().setAttribute(USER, user.get());
                return GetHomePage.getInstance().execute(request, response);
            }
        }

        request.setAttribute("unableToLogin", bundle.getString("unable_to_login"));
        return GetLoginPage.getInstance().execute(request, response);
    }
    


    private Login() {
        userService = UserService.getInstance();
        bundle = ResourceBundle.getBundle("Labels");
    }

    //singelton pattern
    private static class Holder{
        private static Login INSTANCE = new Login();
    }

    public static Login getInstance(){
        return Login.Holder.INSTANCE;
    }



}

