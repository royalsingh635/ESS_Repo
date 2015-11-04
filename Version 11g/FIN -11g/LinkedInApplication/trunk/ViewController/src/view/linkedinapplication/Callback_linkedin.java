package view.linkedinapplication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class Callback_linkedin extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    public HttpSession session;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("The authVerifier is ::==>"+request.getParameter("oauth_verifier"));
        session = request.getSession();
        session.setAttribute("oauth_verifier", request.getParameter("oauth_verifier"));
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Callback</title></head>");
        out.println("<body>");
        out.println("<p style='font:Arial;color:red;font-weight:bold'>Authorisation was sucessfull. <br />You can close the tab and return to the main application. <br />");
        out.println("<a href='#' onclick='close_window();return false;'>Click here to close this window</a>");
        out.println("</p></body></html>");
        out.close();
    }
}
