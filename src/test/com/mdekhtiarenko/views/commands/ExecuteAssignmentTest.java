package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.exceptions.ForbiddenException;
import com.mdekhtiarenko.models.entities.Assignment;
import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.AssignmentType;
import com.mdekhtiarenko.models.enums.Role;
import com.mdekhtiarenko.services.AssignmentService;
import com.mdekhtiarenko.views.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.runners.MockitoJUnitRunner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.mdekhtiarenko.views.Constants.USER;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by mykola.dekhtiarenko on 13.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExecuteAssignmentTest {

    @Mock
    private AssignmentService assignmentService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse resp;
    @InjectMocks
    private ExecuteAssignment command;


    @Test
    public void execute() throws Exception {
        Assignment assignment = Assignment
                .builder()
                .id(4)
                .type(AssignmentType.SURGERY)
                .build();
        User user = User
                .builder()
                .role(Role.NURSE)
                .build();
        when(req.getParameter("assignmentId")).thenReturn(String.valueOf(assignment.getId()));
        when(assignmentService.getById(assignment.getId())).thenReturn(Optional.of(assignment));
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute(USER)).thenReturn(user);
        doThrow(new ForbiddenException("This action is not supposed to be allowed in this test!")).when(assignmentService).executeAssignment(assignment.getId(), user);
        Assert.assertEquals("", command.execute(req, resp), Constants.ERROR_PAGE);

    }

}