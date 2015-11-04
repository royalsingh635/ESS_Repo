package hcmempprfsetup.view.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ImageServlet", urlPatterns = { "/imageservlet" })
public class ImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = (request.getParameter("id"));
        System.out.println("Path="+imageId);
        
        if(imageId==null || imageId.length() <= 0)
        {
                /*  ServletContext servletContext = this.getServletContext();
                String requestURI = request.getRequestURI();
            String path = servletContext.getRealPath(requestURI);
            System.out.println("servlet path ="+path); */
         //  imageId = "..\\..\\META-INF\\resources\\images\\NoImage.png";
            return;
            }

        OutputStream os = response.getOutputStream();

        InputStream inputStream;

        try { 
            System.out.println("t6");
            File outputFile = new File(imageId);

            inputStream = new FileInputStream(outputFile);
            BufferedInputStream in = new BufferedInputStream(inputStream);
            int b;
             byte[] buffer = new byte[1024];
            while ((b = in.read(buffer, 0, 1024)) != -1) {
                os.write(buffer, 0, b);
            } 
            /* byte[] buffer = new byte[in.available()];
            os.write(buffer); */
            os.flush();
            os.close();
            inputStream.close();
            in.close();

        } catch (Exception e) {
            os.flush();
                       os.close();    
        }
    }

   
}
