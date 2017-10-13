package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.exceptions.ForbiddenException;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.services.AssignmentService;
import com.mdekhtiarenko.utils.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mdekhtiarenko.views.Constants.USER;


/**
 * Created by mykola.dekhtiarenko on 28.09.17.
 */
public class CreateAssignmentForDiagnose implements Command{
    private AssignmentService service;
    private  ResourceBundle bundle;
    private final Logger logger = Logger.getLogger(Login.class.getName());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int diagnoseId = Integer.valueOf(req.getParameter("diagnoseId"));
        List<Assignment> assignmentList = processAllTypes(req);
        List<Assignment> checkedAssignmentList = validatedAssignments(assignmentList);
        if(assignmentList.size()!=checkedAssignmentList.size())
            req.setAttribute("error", bundle.getString("patient_detailed_info.inappropriate_assignment"));

        try {
            service.createAssignmentList(checkedAssignmentList, diagnoseId, (User)req.getSession().getAttribute(USER));
        } catch (ForbiddenException e) {
            req.setAttribute("error", bundle.getString("general.forbidden_exeption"));
            return Error404.getInstance().execute(req, res);
        }

        logger.info(checkedAssignmentList.size()+" out of "+assignmentList.size()+" assignments were created.");
        return GetPatientDetailedInfo.getInstance().execute(req, res);
    }


    private List<Assignment> validatedAssignments(List<Assignment> assignmentList){
        List<Assignment> checkedAssignmentList = new ArrayList<>();
        for(Assignment assignment:assignmentList) {
            if (Validator.isFine(assignment.getDescription(), Validator.TEXT)) {
                checkedAssignmentList.add(assignment);
            }
        }
        return checkedAssignmentList;
    }

    private List<Assignment> retrieveAssignments(String[] assigment, AssignmentType type){
        List<Assignment> assignmentList = new ArrayList<>();
        if(assigment!=null)
            for (int i=0; i<assigment.length; i++) {
                    assignmentList.add(Assignment
                            .builder()
                            .description(assigment[i])
                            .type(type)
                            .build()
                    );
            }
        return assignmentList;
    }

    private List<Assignment> processAllTypes(HttpServletRequest req){
        List<Assignment> assignmentList = new ArrayList<>();
        assignmentList.addAll(retrieveAssignments(req.getParameterValues("surgery"), AssignmentType.SURGERY));
        assignmentList.addAll(retrieveAssignments(req.getParameterValues("procedure"), AssignmentType.PROCEDURE));
        assignmentList.addAll(retrieveAssignments(req.getParameterValues("medicine"), AssignmentType.MEDICINE));
        return assignmentList;
    }



    private CreateAssignmentForDiagnose() {
        bundle = ResourceBundle.getBundle("Labels");
        service = AssignmentService.getInstance();
    }

    private static class Holder{
        private static CreateAssignmentForDiagnose INSTANCE = new CreateAssignmentForDiagnose();
    }

    public static CreateAssignmentForDiagnose getInstance(){
        return CreateAssignmentForDiagnose.Holder.INSTANCE;
    }
}
