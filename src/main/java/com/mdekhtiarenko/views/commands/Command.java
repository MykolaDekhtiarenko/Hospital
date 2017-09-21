package com.mdekhtiarenko.views.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mykola.dekhtiarenko on 13.09.17.
 */
public interface Command {
    String execute(HttpServletRequest req,
                   HttpServletResponse res) throws ServletException, IOException;

}
