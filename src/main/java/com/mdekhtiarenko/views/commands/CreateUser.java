package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.exceptions.EmailExistsException;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.Role;
import com.mdekhtiarenko.services.UserService;
import com.mdekhtiarenko.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.utils.Validator.TEXT;
import static com.mdekhtiarenko.utils.Validator.isFine;
import static com.mdekhtiarenko.views.Constants.CREATE_USER_PAGE;


/**
 * Created by mykola.dekhtiarenko on 03.10.17.
 */
public class CreateUser implements Command {
    private ResourceBundle bundle;
    private UserService userService;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User userToCreate = User
                .builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .phone(req.getParameter("phone"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();

        Response response = validateUser(userToCreate);
        if (!response.hasErrors()) {
            try {
                userToCreate.setBirthday(retrieveDate(req.getParameter("birthday")));
                userService.createUser(userToCreate);
                return GetHomePage.getInstance().execute(req, res);
            } catch (EmailExistsException e) {
                response.addError(bundle.getString("user.existing_email"));
                req.setAttribute("errorResp", response);
            } catch (ParseException e) {
                response.addError(bundle.getString("user.inappropriate_date"));
                req.setAttribute("errorResp", response);
            }
        } else {
            req.setAttribute("errorResp", response);
        }
        return CREATE_USER_PAGE;
    }

    private Response validateUser(User user){
        Response response = Response
                .builder()
                .result(Optional.ofNullable(user))
                .build();
        if(!isFine(user.getEmail(), Validator.EMAIL))
            response.addError(bundle.getString("user.wrong_email"));
        if(!isFine(user.getPassword(), Validator.PASSWORD))
            response.addError(bundle.getString("user.wrong_password"));
        if(!isFine(user.getFirstName(), TEXT))
            response.addError(bundle.getString("user.wrong_firstname"));
        if(!isFine(user.getLastName(), TEXT))
            response.addError(bundle.getString("user.wrong_lastname"));
        if(!isFine(user.getPhone(), Validator.PHONE))
            response.addError(bundle.getString("user.wrong_phone"));
        return response;
    }

    private Date retrieveDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date uDate  = sdf.parse(date);
        return new Date(uDate.getTime());
    }



    private CreateUser() {
        userService = UserService.getInstance();
        bundle = ResourceBundle.getBundle("Labels");
    }

    private static class Holder{
        private static CreateUser INSTANCE = new CreateUser();
    }

    public static CreateUser getInstance(){
        return CreateUser.Holder.INSTANCE;
    }
}

