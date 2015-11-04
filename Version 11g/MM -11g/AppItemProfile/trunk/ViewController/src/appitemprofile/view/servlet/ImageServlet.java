package appItemImage.view.Servlet;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

public class ImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String imageId = ( request.getParameter("id"));
                  
              OutputStream os = response.getOutputStream();
              Connection conn = null;
                  InputStream inputStream;
                  String path=null;  
              try {
                                 
                      
                  Context ctx = new InitialContext();
                  //Datasource as defined in <res-ref-name> element of weblogic.xml
                  DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/APPDS");
                  conn = ds.getConnection();
                  PreparedStatement statement =
                      conn.prepareStatement("SELECT A.ATT_NM  FROM APP$DS$ATT A ,app$ds$att$type b "+"WHERE b.att_type_nm='ITEM IMAGE PATH'" + 
                                             "and A.att_type_id=b.att_type_id");
                  
                  ResultSet rs = statement.executeQuery();
                  if (rs.next()) {
                       path=rs.getString("ATT_NM");
                     
                      
                  }
                      System.out.println("path--"+path+" imageid  "+imageId);
                      File outputFile =
                              new File(imageId);
                   //   new File(path+imageId+".png");
                      //Blob blob = rs.getBlob(1);
                      inputStream = new FileInputStream(outputFile);
                      BufferedInputStream in =
                          new BufferedInputStream(inputStream);
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
