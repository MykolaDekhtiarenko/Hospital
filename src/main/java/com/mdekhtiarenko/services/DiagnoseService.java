package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.utils.Validator;
import com.mdekhtiarenko.views.commands.Login;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 27.09.17.
 */
public class DiagnoseService {

    private DaoFactory daoFactory;
    private final Logger logger = Logger.getLogger(Login.class.getName());


    public void createDiagnose(Diagnose diagnose){
        DiagnoseDao diagnoseDao = daoFactory.createDiagnoseDAO();
        if(!diagnoseDao.create(diagnose)){
            logger.info("Database error!");
        }
    }


    private DiagnoseService(DaoFactory instance){
        daoFactory = instance;
    }

    //singelton pattern
    private static class Holder{
        private static DiagnoseService INSTANCE = new DiagnoseService(DaoFactory.getInstance());
    }

    public static DiagnoseService getInstance(){
        return DiagnoseService.Holder.INSTANCE;
    }


}
