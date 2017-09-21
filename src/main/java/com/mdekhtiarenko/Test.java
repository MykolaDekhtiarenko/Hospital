package com.mdekhtiarenko;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.services.UserService;

import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 07.09.17.
 */
public class Test {
    public static void main(String ... arg){
        UserService service = UserService.getInstance();
        Optional<User> patient =  service.getFullPatientInfo(9);
        System.out.println(patient);
    }
}
