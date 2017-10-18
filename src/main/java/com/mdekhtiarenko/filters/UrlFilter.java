package com.mdekhtiarenko.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mdekhtiarenko.utils.UserUtils.hasDoctorUser;
import static com.mdekhtiarenko.utils.UserUtils.hasStaffUser;
import static com.mdekhtiarenko.utils.UserUtils.hasUser;

/**
 * Created by mykola.dekhtiarenko on 14.09.17.
 */
@WebFilter(filterName = "UrlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {
    private static final String URL_TO_GO = "/rest/login";
    private static final String ACCESS_DENIED = "/rest/error";

    private List<String> authorizationRequiredUrls = new ArrayList<>();
    private List<String> staffOnlyUrls = new ArrayList<>();
    private List<String> doctorOnlyUrls = new ArrayList<>();



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authorizationRequiredUrls.add("GET:/home");

        //Doctor and nurse
        staffOnlyUrls.add("GET:/patient/");
        staffOnlyUrls.add("GET:/patients");
        staffOnlyUrls.add("POST:/assignment/update");
        staffOnlyUrls.add("GET:/user");

        doctorOnlyUrls.add("POST:/treatmentHistory/update");
        doctorOnlyUrls.add("POST:/treatmentHistory/create");
        doctorOnlyUrls.add("POST:/diagnose/create");
        doctorOnlyUrls.add("POST:/assignment/create");
        doctorOnlyUrls.add("POST:/user/create");




    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        path = path.replaceAll(".*/rest", "").replaceAll("\\d+", "");
        String url = method+":"+path;
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();

        if(authorizationRequiredUrls.contains(url)&&!hasUser(session)){
            response.sendRedirect(request.getContextPath() + URL_TO_GO);
        }
        else if (staffOnlyUrls.contains(url)&&!hasStaffUser(session)){
            response.sendRedirect(request.getContextPath() + ACCESS_DENIED);
        }
        else if(doctorOnlyUrls.contains(url)&&!hasDoctorUser(session)){
            response.sendRedirect(request.getContextPath() + ACCESS_DENIED);
        }
        else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }


}
