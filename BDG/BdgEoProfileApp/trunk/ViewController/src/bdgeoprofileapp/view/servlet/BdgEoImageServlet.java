package bdgeoprofileapp.view.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

@WebServlet(name = "BdgEoImageServlet", urlPatterns = { "/bdgeoimageservlet" })
public class BdgEoImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = (request.getParameter("path"));
        //System.out.println("Path=" + imageId);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("come");
        if (imageId == null || imageId.length() <= 0) {

            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BDGDS");
                conn = ds.getConnection();

                ps = conn.prepareStatement("select distinct SRVR_LOC_IMG_ATTACH_PATH from APP.App$Servr$Loc");

                rs = ps.executeQuery();
                while (rs.next()) {
                    imageId = rs.getString(1);
                    imageId = imageId + "no_image.jpg";
                    // System.out.println("Path is :" + rs.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {

                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println("Image is ");
            //imageId = getServletContext().getRealPath("/WEB-INF/classes/META-INF/resources/images/no_image.jpg");
        }

        OutputStream os = response.getOutputStream();

        InputStream inputStream;

        try {
            File outputFile = new File(imageId);

            inputStream = new FileInputStream(outputFile);
            BufferedInputStream in = new BufferedInputStream(inputStream);
            int b;
            byte[] buffer = new byte[1024];
            while ((b = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, b);
            }
            os.flush();
            os.close();
            inputStream.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            os.flush();
            os.close();
        }
    }
}
