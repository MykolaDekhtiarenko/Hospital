package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.services.AssignmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.utils.UserUtils.hasDoctorUser;
import static com.mdekhtiarenko.utils.UserUtils.hasStaffUser;

/**
 * Created by mykola.dekhtiarenko on 01.10.17.
 */
public class ExecuteAssignment implements Command {
    private AssignmentService assignmentService;
    private ResourceBundle bundle;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int assignmentId = Integer.valueOf(req.getParameter("assignmentId"));
        Optional<Assignment> assignmentOptional = assignmentService.getById(assignmentId);

        if(!assignmentOptional.isPresent()) {
            req.setAttribute("error", bundle.getString("assignment.no_assignment_found"));
            return GetPatientDetailedInfo.getInstance().execute(req, res);
        }

        if(currentUserHasPermission(assignmentOptional.get().getType(), req.getSession())){
            assignmentService.executeAssignment(assignmentId);
        }
        else {
            req.setAttribute("error", bundle.getString("assignment.has_not_permission"));
        }
        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }

    private boolean currentUserHasPermission(AssignmentType type, HttpSession session){
        if((type==AssignmentType.MEDICINE||type==AssignmentType.PROCEDURE)&&hasStaffUser(session))
            return true;
        else if(type == AssignmentType.SURGERY&&hasDoctorUser(session))
            return true;
        else
            return false;
    }

    private ExecuteAssignment() {
        assignmentService = AssignmentService.getInstance();
        bundle = ResourceBundle.getBundle("Labels");
    }

    //singelton pattern
    private static class Holder{
        private static ExecuteAssignment INSTANCE = new ExecuteAssignment();
    }

    public static ExecuteAssignment getInstance(){
        return ExecuteAssignment.Holder.INSTANCE;
    }

}
