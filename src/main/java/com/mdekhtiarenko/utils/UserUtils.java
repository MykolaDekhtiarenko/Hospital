package com.mdekhtiarenko.utils;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.Role;

import javax.servlet.http.HttpSession;

import static com.mdekhtiarenko.views.Constants.USER;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class UserUtils {
    public static boolean hasUser(HttpSession session){
        return !(session.getAttribute(USER)==null);
    }

    public static boolean hasStaffUser(HttpSession session){
        if(hasUser(session)){
            User user = ((User)session.getAttribute(USER));
            return user.getRole() == Role.NURSE||user.getRole() == Role.DOCTOR;
        }
        else {
            return false;
        }
    }

    public static boolean hasDoctorUser(HttpSession session){
        if(hasUser(session)){
            User user = ((User)session.getAttribute(USER));
            return user.getRole() == Role.DOCTOR;
        }
        else {
            return false;
        }
    }
}
