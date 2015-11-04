package ebizmobileappwebservice.servlet;

import com.ess.conn.DBConnection;

import ebizmobileappwebservice.utilities.EbizUtilities;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AlertTable", urlPatterns = { "/alertData" })
public class AlertTable extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    String query =
        "SELECT a.ALRT_TITLE,\n" + "  NVL(b.ALRT_TXT,a.alrt_text) alrt_text\n" + "FROM app$alrt a,\n" +
        "  APP$ALRT$USR b,\n" + "  app$alrt$usr$media c ,\n" + "  APP$ALRT$SVRTY$CLR d\n" +
        "WHERE a.CLD_ID             =?\n" + "AND a.sloc_id              =?\n" + "AND a.ho_org_id            =?\n" +
        "AND a.org_id               =?\n" + "AND a.GLBL_APPLI_STRUCT_ID = ? -- for MM\n" +
        "AND a.CLD_ID               =b.cld_id\n" + "AND a.sloc_id              =b.sloc_id\n" +
        "AND a.ho_org_id            =b.HO_ORG_ID\n" + "AND a.org_id               =b.ORG_ID\n" +
        "AND a.doc_id               =b.doc_id\n" + "AND a.doc_id               =?\n" +
        "AND b.USR_ID               =?\n" + "AND b.CLD_ID               =c.Cld_Id\n" +
        "AND b.sloc_id              =c.Sloc_Id\n" + "AND b.ho_org_id            =c.Ho_Org_Id\n" +
        "AND b.org_id               =c.Org_Id\n" + "AND b.USR_ID               =c.Usr_Id\n" +
        "AND b.doc_id               =c.doc_id\n" +
        "AND c.alrt_media           = 783 -- for mobile   --783 for dashboard\n" +
        "AND TRUNC(sysdate) BETWEEN TRUNC(c.disp_strt_dt) AND TRUNC(c.disp_end_dt)\n" + "AND b.SVRTY_LVL=d.SVRTY_LVL\n";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        String cldId = request.getParameter("cldId");
        String slocId = request.getParameter("slocId");
        String hoOrgId = request.getParameter("hoOrgId");
        String orgId = request.getParameter("orgId");
        String usrId = request.getParameter("usrId");
        String modId = request.getParameter("modId");
        String docId = request.getParameter("docId");
        out.println("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n" + "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">\n" +
                    "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>\n" +
                    "  <script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>\n" +
                    "  <style>\n" + "			table, th, td {\n" + "				border: 1px solid #E2E2E0;\n" +
                    "				border-collapse: collapse;\n" + "				color:#FF7800;\n" + "				font-size:10px;\n" + "			}" +
                    ".table-hover>tbody>tr:hover td{\n" + "	background-color:#D1C1B2;\n" + "  color:white;\n" +
                    "  font-weight:bold;\n" + "}\n" + "		</style>\n" + "  \n" + "</head>\n" + "<body>\n" +
                    "  <table class=\"table table-striped table-hover\">\n" + "    <thead>\n" + "    </thead>\n" +
                    "    <tbody>");
        DBConnection bConnection;
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setObject(1, cldId);
                ps.setObject(2, slocId);
                ps.setObject(3, hoOrgId);
                ps.setObject(4, orgId);
                ps.setObject(5, modId);
                ps.setObject(6, docId);
                ps.setObject(7, usrId);
                String txt = "";
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    txt = rs.getString("ALRT_TEXT");
                }
                rs.close();
                ps.close();
                String[] split = txt.split("<BR>");
                String code = "";
                for (String s : split) {
                    //   System.out.println(s);
                    String[] info = s.split(" - ");
                    code += "<tr>";
                    for (String s1 : info) {
                        code += "<td>" + s1.trim() + "</td>";
                    }
                    code += "</tr>";
                }
                out.println(code);
                //   System.out.println(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(" </tbody>\n" + "  </table>\n" + "</body>\n" + "</html>\n");
        out.close();
    }
}
