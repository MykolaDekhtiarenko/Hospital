package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.UserDao;
import com.mdekhtiarenko.models.dao.utils.SecurityUtils;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.views.commands.Login;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Created by mykola.dekhtiarenko on 18.09.17.
 */
public class UserService {

    private DaoFactory daoFactory;
    private final Logger logger = Logger.getLogger(Login.class.getName());

    private UserService(DaoFactory instance){
        daoFactory = instance;
    }

    public Optional<User> login(String email, String password){
        logger.info(email+" trying to log in.");
        UserDao dao = daoFactory.createPatientDAO();
        return Optional.ofNullable(dao.getPatientByEmail(email))
                .filter( patient -> SecurityUtils.decode(password).equals(patient.getPassword()));
    }

    public Optional<User> getFullPatientInfo(int id){
        logger.info("Trying to get info about user with id "+id);
        UserDao dao = daoFactory.createPatientDAO();
        Optional<User> patient = Optional.ofNullable(dao.findById(id));
        if(patient.isPresent()) {
            patient.get()
                    .setTreatmentHistoryList(
                            dao.getTreatmentHistoryList(
                                    id,
                                    daoFactory.createTreatmentHistoryDAO(),
                                    daoFactory.createDiagnoseDAO()));
        }
        return patient;
    }

    public List<User> getSick(){
        UserDao dao = daoFactory.createPatientDAO();
        return dao.getSick();
    }

    public List<User> getAll(){
        UserDao dao = daoFactory.createPatientDAO();
        return dao.findAll();
    }

    //singelton pattern
    private static class Holder{
        private static UserService INSTANCE = new UserService(DaoFactory.getInstance());
    }

    public static UserService getInstance(){
        return UserService.Holder.INSTANCE;
    }
}
