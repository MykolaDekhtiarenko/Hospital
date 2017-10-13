package com.mdekhtiarenko.views.commands;

import com.mdekhtiarenko.models.entities.User;
import com.mdekhtiarenko.models.enums.Role;
import com.mdekhtiarenko.services.AssignmentService;
import com.mdekhtiarenko.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;
import static com.mdekhtiarenko.views.Constants.ERROR_PAGE;
import static com.mdekhtiarenko.views.Constants.USER;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by mykola.dekhtiarenko on 13.10.17.
 */
@RunWith(MockitoJUnitRunner.class)

public class CreateAssignmentForDiagnoseTest {
    @Mock
    private AssignmentService assignmentService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private UserService userService;
    @InjectMocks
    private CreateAssignmentForDiagnose command;



    @Test
    public void execute() throws Exception {
        User user = User
                .builder()
                .id(4)
                .role(Role.NURSE)
                .build();
        String[] surgeries = {"real message", "", "<script>dangerous code</script>"};

        when(req.getParameter("diagnoseId")).thenReturn("7");
        when(req.getParameterValues("surgery")).thenReturn(surgeries);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("userId")).thenReturn("4");
        when(userService.getFullPatientInfo(anyInt())).thenReturn(Optional.of(user));
        when(session.getAttribute(USER)).thenReturn(user);

        assertEquals("User should be returned to error page.", ERROR_PAGE, command.execute(req, resp));

    }

}