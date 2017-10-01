package com.mdekhtiarenko.services;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.utils.Validator;
import com.mdekhtiarenko.views.commands.Login;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by mykola.dekhtiarenko on 28.09.17.
 */
public class AssignmentService {

    private DaoFactory daoFactory;
    private final Logger logger = Logger.getLogger(Login.class.getName());

    public void createAssignmentList(List<Assignment> assignmentList, int diagnoseId){
        AssignmentDao assignmentDao = daoFactory.createAssignmentDAO();
        for (Assignment assignment: assignmentList){
            assignment.setDiagnoseId(diagnoseId);
            assignmentDao.create(assignment);
            logger.info("Created: "+assignment);
        }
    }

    public void executeAssignment(int assignmentId){
        Assignment assignment = Assignment
                .builder()
                .id(assignmentId)
                .dateOfExecution(new Date(Calendar.getInstance().getTimeInMillis()))
                .build();
        daoFactory.createAssignmentDAO().update(assignment);
    }

    public Optional<Assignment> getById(int assignmentId){
        return Optional.ofNullable(daoFactory.createAssignmentDAO().findById(assignmentId));
    }

    private AssignmentService(DaoFactory instance){
        daoFactory = instance;
    }

    private static class Holder{
        private static AssignmentService INSTANCE = new AssignmentService(DaoFactory.getInstance());
    }

    public static AssignmentService getInstance(){
        return AssignmentService.Holder.INSTANCE;
    }
}
