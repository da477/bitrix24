package com.da.b24tm.web;

import com.da.b24tm.CommandExecutor;
import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.Operation;
import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class BitrixServlet extends HttpServlet {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final String BITRIX_PAGE = "/bitrix24tm.jsp";
    private static final Logger log = getLogger(BitrixServlet.class);

    public static String TimeToString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String result = "";
        log.info("doGet {}", Operation.STATUS);
        try {
            result = CommandExecutor.execute(Operation.STATUS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String forward = BITRIX_PAGE;
        req.setAttribute("status", result);
        req.setAttribute("time", TimeToString(LocalDateTime.now()));
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("Bitrix24 operation {}", req.getParameter("operation"));
        Operation operation = getOperation(req);

        switch (operation) {
            case STATUS:
                break;
            default:
                try {
                    String result = CommandExecutor.execute(operation);
                } catch (Exception e) {
                    ConsoleHelper.writeMessage("Error. Check your data." + e.getMessage());
                }
                break;
        }

        resp.sendRedirect("/");
    }

    private Operation getOperation(HttpServletRequest request) {
        String strCmd = "0";
        try {
            strCmd = Objects.requireNonNull(request.getParameter("operation"));
        } catch (NullPointerException e) {
            //nothing
        }
        return Operation.values()[Integer.valueOf(strCmd)];
    }

}
