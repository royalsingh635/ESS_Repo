package mnfBillOfMaterialsApp.view.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ItemImageServlet", urlPatterns = { "/itemimageservlet" })
public class ItemImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String image = request.getParameter("id");
        OutputStream os = response.getOutputStream();
        Connection conn = null;
        InputStream inputStream;

        try {
            System.out.println(" path in servlet "+image);
            File outputFile = new File(image);
            //Blob blob = rs.getBlob(1);
            inputStream = new FileInputStream(outputFile);
            BufferedInputStream in = new BufferedInputStream(inputStream);
            int b;
            byte[] buffer = new byte[10240];
            while ((b = in.read(buffer, 0, 10240)) != -1) {
                os.write(buffer, 0, b);
            }
            os.close();

        } catch (Exception e) {

            System.out.println(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("SQLException error");
            }
        }
    }
}
