package com.mdekhtiarenko.models;

import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.DiagnoseDao;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.Diagnose;
import com.mdekhtiarenko.models.enums.AssignmentType;

import java.util.List;

/**
 * Created by mykola.dekhtiarenko on 07.09.17.
 */
public class Test {
    public static void main(String ... arg){
        DaoFactory daoFactory = DaoFactory.getInstance();
        try (AssignmentDao assignmentDAO = daoFactory.createAssignmentDAO()){
            boolean p = assignmentDAO.create(
                    Assignment.builder()
                    .description("11")
                    .staffId(1)
                    .type(AssignmentType.PROCEDURE)
                    .diagnoseId(4)
                    .build()
            );
            if(p) {
                List<Assignment> list = assignmentDAO.findAll();
                Assignment last = list.get(list.size()-1);
                last.setDescription("New d 111");
                System.out.println("Updated: " + assignmentDAO.update(last));
                System.out.println("Found by id: " + assignmentDAO.findById(last.getId()));
                System.out.println(assignmentDAO.findAll());
                assignmentDAO.delete(last.getId());
                System.out.println(assignmentDAO.findAll());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
