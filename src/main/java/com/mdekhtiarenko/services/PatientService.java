package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.PatientDao;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import com.mdekhtiarenko.models.entities.Patient;

import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 18.09.17.
 */
public class PatientService {

    DaoFactory daoFactory;

    PatientService(DaoFactory instance){
        daoFactory = instance;
    }

    public Optional<Patient> login(String email, String password){
        PatientDao dao = daoFactory.createPatientDAO();
        return Optional.ofNullable(dao.getPatientByEmail(email))
                .filter( patient -> SecurityUtils.decode(password).equals(patient.getPassword()));
    }

    //singelton pattern
    private static class Holder{
        private static PatientService INSTANCE = new PatientService(DaoFactory.getInstance());
    }

    public static PatientService getInstance(){
        return PatientService.Holder.INSTANCE;
    }
}