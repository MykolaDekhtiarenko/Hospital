package com.mdekhtiarenko.utils;

import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 26.09.17.
 */
public class Validator {
    public final static String TEXT = "^[^<>]{2,}$";
    public static final String ID = "^[0-9]+";



    public static boolean isFine(String value, String regex){
        if (value==null||!value.matches(regex))
            return false;
        return true;
    }

}
