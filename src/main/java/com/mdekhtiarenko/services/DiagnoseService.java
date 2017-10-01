package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.utils.Validator;

import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 27.09.17.
 */
public class DiagnoseService {

    private DaoFactory daoFactory;


    public void createDiagnose(Diagnose diagnose){
        DiagnoseDao diagnoseDao = daoFactory.createDiagnoseDAO();
        if(!diagnoseDao.create(diagnose)){
            //TODO log
        }

//        ResourceBundle bundle = ResourceBundle.getBundle("Labels");
//        if(!Validator.isFine(diagnose.getDiagnose(), Validator.TEXT)){
//            return Response
//                    .builder()
//                    .errorMessage(bundle.getString("wrong_diagnose"))
//                    .build();
//        }
//        if(diagnoseDao.create(diagnose))
//            return Response.builder().build();
//        return Response
//                .builder()
//                .errorMessage(bundle.getString("db_problem"))
//                .build();
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
