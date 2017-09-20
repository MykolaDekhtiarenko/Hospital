package com.mdekhtiarenko.filters;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mdekhtiarenko.views.commands.Constants.PATIENT_USER;
import static com.mdekhtiarenko.views.commands.Constants.STAFF_USER;

/**
 * Created by mykola.dekhtiarenko on 14.09.17.
 */
@WebFilter(filterName = "UrlFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {
    private static final String URL_TO_GO = "/rest/login";

    private final Logger logger = Logger.getLogger(UrlFilter.class.getName());
    private List<String> restrictedUrls = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        restrictedUrls.add("GET:/home");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        path = path.replaceAll(".*/rest", "").replaceAll("\\d+", "");
        String url = method+":"+path;
        logger.debug("Asked url: "+url);

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if(restrictedUrls.contains(url)
                &&session.getAttribute(STAFF_USER)==null
                &&session.getAttribute(PATIENT_USER)==null){

            response.sendRedirect(request.getContextPath() + URL_TO_GO);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
