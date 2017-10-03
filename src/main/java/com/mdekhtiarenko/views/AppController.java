package com.mdekhtiarenko.views;

import com.mdekhtiarenko.views.commands.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mdekhtiarenko.views.Constants.INDEX_PAGE;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
@WebServlet(name = "AppController", urlPatterns = "/rest/*")
public class AppController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(AppController.class);
    private Map<String, Command> urls = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        urls.put("POST:/login", Login.getInstance());
        urls.put("GET:/login", GetLoginPage.getInstance());
        urls.put("GET:/logout", Logout.getInstance());
        urls.put("POST:/logout", Logout.getInstance());

        urls.put("GET:/home", GetHomePage.getInstance());
        urls.put("GET:/patient/", GetPatientDetailedInfo.getInstance());
        urls.put("GET:/error", Error404.getInstance());

        urls.put("POST:/assignment/create", CreateAssignmentForDiagnose.getInstance());
        urls.put("POST:/diagnose/create", CreateDiagnose.getInstance());
        urls.put("POST:/treatmentHistory/create", CreateTreatmentHistory.getInstance());
        urls.put("POST:/treatmentHistory/update", CloseTreatmentHistory.getInstance());
        urls.put("GET:/patients", GetAllPatientsInfo.getInstance());
        urls.put("POST:/locale", SetLocale.getInstance());
        urls.put("POST:/assignment/update", ExecuteAssignment.getInstance());

        urls.put("GET:/user", GetRegistrationPage.getInstance());
        urls.put("POST:/user/create", CreateUser.getInstance());


        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doCommand(req, resp);
    }

    protected void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        path = path.replaceAll(".*/rest", "").replaceAll("\\d+", "");
        String url = method+":"+path;
        Command command = urls.getOrDefault(url, (req, resp)->INDEX_PAGE);
        String viewPage = command.execute(request, response);
        request.getRequestDispatcher(viewPage)
                .forward(request, response);
    }

}
