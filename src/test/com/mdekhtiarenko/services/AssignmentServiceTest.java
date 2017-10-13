package com.mdekhtiarenko.services;

import com.mdekhtiarenko.exceptions.ForbiddenException;
import com.mdekhtiarenko.models.dao.AssignmentDao;
import com.mdekhtiarenko.models.dao.DaoFactory;
import com.mdekhtiarenko.models.dao.impl.AssignmentDaoImpl;
import com.mdekhtiarenko.models.dao.impl.JDBCDaoFactory;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.models.enums.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by mykola.dekhtiarenko on 13.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssignmentServiceTest {
    @Mock()
    private DaoFactory daoFactory;
    @Mock
    private AssignmentDao assignmentDao;
    @InjectMocks
    private AssignmentService assignmentService;

    @Test(expected = ForbiddenException.class)
    public void createAssignmentList() throws Exception {
        User user = User
                .builder()
                .role(Role.NURSE)
                .build();

        List<Assignment> assignmentList = new ArrayList<Assignment>(){{
            add(Assignment.builder().build());
        }};

        when(daoFactory.createAssignmentDAO()).thenReturn(assignmentDao);
        when(assignmentDao.create(assignmentList.get(0))).thenReturn(true);
        assignmentService.createAssignmentList(assignmentList, 7, user);
    }

    @Test(expected = ForbiddenException.class)
    public void executeAssignment() throws Exception {
        User user = User
                .builder()
                .role(Role.NURSE)
                .build();

       Assignment assignment = Assignment
               .builder()
               .id(4)
               .type(AssignmentType.SURGERY)
               .build();
        when(daoFactory.createAssignmentDAO()).thenReturn(assignmentDao);
        when(assignmentDao.findById(4)).thenReturn(assignment);
        when(assignmentDao.update(assignment)).thenReturn(assignment);
        assignmentService.executeAssignment(assignment.getId(), user);
    }

}