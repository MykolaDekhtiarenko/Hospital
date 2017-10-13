package com.mdekhtiarenko.services;

import com.mdekhtiarenko.exceptions.ForbiddenException;
import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Response;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.models.enums.Role;
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

    public void createAssignmentList(List<Assignment> assignmentList, int diagnoseId, User user) throws ForbiddenException {
        if(user.getRole()!= Role.DOCTOR)
            throw new ForbiddenException("You have no permissions for this!");

        AssignmentDao assignmentDao = daoFactory.createAssignmentDAO();
        for (Assignment assignment: assignmentList){
            assignment.setDiagnoseId(diagnoseId);
            assignmentDao.create(assignment);
            logger.info("Created: "+assignment);
        }
    }

    public void executeAssignment(int assignmentId, User user) throws ForbiddenException {
        AssignmentDao assignmentDao = daoFactory.createAssignmentDAO();
        Assignment assignment = assignmentDao.findById(assignmentId);
        assignment.setDateOfExecution(new Date(Calendar.getInstance().getTimeInMillis()));
        if(user.getRole()==Role.DOCTOR)
            assignmentDao.update(assignment);
        else if(user.getRole()==Role.NURSE&&assignment.getType()!= AssignmentType.SURGERY)
            assignmentDao.update(assignment);
        else
            throw new ForbiddenException("You have no permissions for this!");

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
