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

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
@WebServlet(name = "AppController", urlPatterns = "/rest/*")
public class AppController extends HttpServlet{

    private static final Logger logger = Logger.getLogger(AppController.class);
    private Map<String, Command> commandMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        commandMap.put("POST:/login", new Login());
        commandMap.put("GET:/login", new Login());
        commandMap.put("GET:/home", new GetHomePage());
        commandMap.put("GET:/error", new Error404());
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
        logger.debug("Asked url in controller: "+url);
        Command command = commandMap.getOrDefault(url, (req, resp)->"/index.jsp" );
        String viewPage = command.execute(request, response);
        request.getRequestDispatcher(viewPage)
                .forward(request, response);
    }

}
