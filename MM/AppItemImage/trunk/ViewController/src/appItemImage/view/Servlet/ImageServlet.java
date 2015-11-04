package appItemImage.view.Servlet;


import appItemImage.model.service.AppItemImageAMImpl;

import appItemImage.view.bean.ItemImageAddBean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.sql.DataSource;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;

public class ImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static ADFLogger adf = ADFLogger.createADFLogger(ItemImageAddBean.class);
    private String path=null;
    private Connection conn=null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
//        String imageId = (request.getParameter("id"));
//        String imagetype=(request.getParameter("type"));
        try {
            
adf.info("the try block");
        String imageId = (request.getParameter("id"));
        String imagetype=(request.getParameter("type"));
            adf.info(imageId +" "+imagetype );
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
           conn = ds.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("select distinct srvr_loc_img_attach_path from APP.App$Servr$Loc");
            ResultSet rs = ps.executeQuery();
            String path = null;
            
            while (rs.next()) {
                path = rs.getString(1);
            }
        //String path=(String)callStoredFunction( Types.VARCHAR, "APP.fn_get_app_img_path()", new Object[] { });
        adf.info("Parameter :"+imageId+"Path :"+path+"imagetype:"+imagetype);
      if(imagetype !=null ){
          adf.info(" in the block");
       if(!(imagetype.equalsIgnoreCase("jpeg") ||
                imagetype.equalsIgnoreCase("png") ||
                imagetype.equalsIgnoreCase("bmp") ||
                imagetype.equalsIgnoreCase("gif")||imagetype.equals(""))
               )
          
           imageId=path+"default_image.jpg";
       
      }
        if(imageId.length() <= 0)
          {return;}

           //adf.info("imgId : "+imageId +" and imgType: "+imagetype);
        OutputStream os = response.getOutputStream();

        InputStream inputStream;

        try { System.out.println("t6");
               adf.info("image id in image app------"+imageId);
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
            adf.info(e);
        }
        adf.info("t7");
        
    }
        catch (NamingException e) {
        } 
        catch (SQLException e) {
        }
        //    public Object resolvElDC(String data) {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        Application app = fc.getApplication();
//        ExpressionFactory elFactory = app.getExpressionFactory();
//        ELContext elContext = fc.getELContext();
//        ValueExpression valueExp =
//            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
//        return valueExp.getValue(elContext);
//    }
//    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
//        CallableStatement st = null;
//        try {
//
//            // 1. Create a JDBC CallabledStatement
//            AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
//            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
//            // 2. Register the first bind variable for the return value
//            st.registerOutParameter(1, sqlReturnType);
//            if (bindVars != null) {
//                // 3. Loop over values for the bind variables passed in, if any
//                for (int z = 0; z < bindVars.length; z++) {
//                    // 4. Set the value of user-supplied bind vars in the stmt
//                    st.setObject(z + 2, bindVars[z]);
//                }
//            }
//            // 5. Set the value of user-supplied bind vars in the stmt
//            st.executeUpdate();
//            // 6. Return the value of the first bind variable
//            return st.getObject(1);
//        } catch (SQLException e) {
//            throw new JboException(e);
//        } finally {
//            if (st != null) {
//                try {
//                    // 7. Close the statement
//                    st.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//    }
}
}
