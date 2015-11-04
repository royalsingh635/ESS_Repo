 /**
  * Author of this bean is Priyank Khare,Mayank Shukla and others.
  * Do not add references to bundled resources in this bean. This restriction has been added to
  * defer its initialization  untill user's preferred language is known which is known id, sloc id, organization id is achieved in the next page.
  * **/


 package ebizframemainapplication.view.bean;

 import ebizframemainapplication.model.AppSecUsrViewImpl;
 import ebizframemainapplication.model.AppSecUsrViewRowImpl;
 import ebizframemainapplication.model.EbizMainAppAMImpl;

 import java.io.IOException;

 import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 import java.sql.Types;

 import java.text.DateFormat;
 import java.text.SimpleDateFormat;

 import java.util.Date;

 import javax.el.ELContext;
 import javax.el.ExpressionFactory;
 import javax.el.ValueExpression;

 import javax.faces.application.Application;
 import javax.faces.application.FacesMessage;
 import javax.faces.component.UIViewRoot;
 import javax.faces.context.ExternalContext;
 import javax.faces.context.FacesContext;

 import javax.faces.event.ValueChangeEvent;

 import javax.security.auth.Subject;
 import javax.security.auth.login.FailedLoginException;
 import javax.security.auth.login.LoginException;

 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 import javax.servlet.http.HttpSession;

import mmrmda.model.service.MMrmdaAMImpl;

import oracle.adf.view.rich.component.rich.input.RichInputText;
 import oracle.adf.view.rich.context.AdfFacesContext;
 import oracle.adf.view.rich.render.ClientEvent;

 import oracle.javatools.resourcebundle.ResourceBundleManagerRT;

 import oracle.jbo.JboException;
 import oracle.jbo.ViewObject;
 import oracle.jbo.client.Configuration;

import oracle.jbo.server.DBTransaction;

import viewcontroller.ebizTemplatePageBundle;

import weblogic.security.URLCallbackHandler;
 import weblogic.security.services.Authentication;

 import weblogic.servlet.WebLogicServletContextListener;
 import weblogic.servlet.security.ServletAuthentication;


 public class Login {

     private String DisplayDate;
     private String _userid = "0";
    //Next Line is commented by Mayank Shukla because it is used unnecessarily.
    // private String _password = "Password";
     private String user = "User Name";
     private Integer _usrSlocId = null;
     private Boolean isSecret;
     private RichInputText passWd;
     private String usr_name;
     private static int NUMERIC = Types.NUMERIC;
     FacesContext context = FacesContext.getCurrentInstance();
     HttpSession session = (HttpSession)context.getExternalContext().getSession(false);

     //Skins defined in Main Application
     private String skinFamily = _RICH_DEMO_SKIN;
     static private final String _SKIN_TEST_DIRECTORY_INPUT = "/skinningKeys/input";
     static private final String _SKIN_TEST_DIRECTORY_SELECT = "/skinningKeys/select";
     static private final String _SKIN_TEST_DIRECTORY = "/skinningKeys";
     static private final String _TEST_SKIN_INPUT_SELECT = "demoComponentsInputSelect";
     static private final String _TEST_SKIN_INPUT_OTHER = "demoComponentsOther";
     static private final String _RICH_DEMO_SKIN = "Main";

     private static final long serialVersionUID = 1L;

     public Login() {
         // System.out.println(" inside login bean ");
        
     }

     public String getSkinFamily() {

         // If we're in the skin component tests, return the skinComponentTest skin.
         FacesContext context = FacesContext.getCurrentInstance();
         UIViewRoot viewRoot = context.getViewRoot();
         if ((viewRoot != null) && (viewRoot.getViewId().indexOf(_SKIN_TEST_DIRECTORY) >= 0)) {
             String viewId = viewRoot.getViewId();

             if (viewId != null && viewId.indexOf(_SKIN_TEST_DIRECTORY) >= 0) {
                 if ((viewId.indexOf(_SKIN_TEST_DIRECTORY_INPUT) >= 0) ||
                     (viewId.indexOf(_SKIN_TEST_DIRECTORY_SELECT) >= 0))
                     return _TEST_SKIN_INPUT_SELECT;
                 else
                     return _TEST_SKIN_INPUT_OTHER;
             }
         }

         return skinFamily;
     }

     /**
      * Method to authenticate user name with entered password.
      * A user is authenticated only when the entered name exists in APP$SEC$USER table and the corresponding password
      * matches with password in WEBLOGIC security realm.
      * **/
     public String doLogin() {

         ResourceBundleManagerRT rt = (ResourceBundleManagerRT)ResourceBundleManagerRT.getResourceBundleManager();
         rt.flush();

         getUserName();

           String un = _userid;
           
         //  System.out.println(un);
         //String un=this.user;
         byte[] pw = (this.passWd.getValue().toString()).getBytes();
         FacesContext ctx = FacesContext.getCurrentInstance();
         HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();

         URLCallbackHandler handler = new URLCallbackHandler(un, pw);
         try {
             Subject subject = Authentication.login(handler);
             //System.out.println("checking in weblogic");
             weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
             //System.out.println("varified by weblogic");
             String loginUrl = "/adfAuthentication?success_url=/faces/StartPage";
             HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();

             sendForward(request, response, loginUrl);
             usr_name = user;

            

         } catch (FailedLoginException fle) {
             
             System.out.println(this.user);
             System.out.println(this.passWd.getValue().toString());
             this.user="User Name";
             this.passWd.setValue("Password");
           
             this.passWd.setSecret(false);

             FacesMessage msg = new FacesMessage("An Incorrect Username or Password was specified.");
             msg.setSeverity(FacesMessage.SEVERITY_ERROR);
             FacesContext.getCurrentInstance().addMessage(null, msg);

         } catch (LoginException le) {
            
             reportUnexpectedLoginError("LoginException", le);
         }
         return null;
     }

     private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
         FacesContext ctx = FacesContext.getCurrentInstance();

         RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);

         try {
             // System.out.println("inside sendforward try----"+forwardUrl);
             dispatcher.forward(request, response);
             // System.out.println("after forward try----"+forwardUrl);
         } catch (ServletException se) {
             reportUnexpectedLoginError("ServletException", se);
         } catch (IOException ie) {
             reportUnexpectedLoginError("IOException", ie);
         }
         ctx.responseComplete();
     }

     private void reportUnexpectedLoginError(String errType, Exception e) {
         FacesMessage msg = new FacesMessage("Unexpected error during Login.");
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         FacesContext.getCurrentInstance().addMessage(null, msg);
         e.printStackTrace();
     }

     /**
      * Method to get user id based on user name and server location.
      * **/
     public void getUserName() {

         EbizMainAppAMImpl am =
             (EbizMainAppAMImpl)Configuration.createRootApplicationModule("ebizframemainapplication.model.EbizMainAppAMImpl",
                                                                          "EbizMainAppAMLocal");
         AppSecUsrViewImpl appSecUsrVO = (AppSecUsrViewImpl)am.getAppSecUsrView1();
         appSecUsrVO.setWhereClause(null);
         appSecUsrVO.executeQuery();
         appSecUsrVO.setWhereClause("USR_NAME= '" + user + "'");
         appSecUsrVO.executeQuery();

         while (appSecUsrVO.hasNext()) {
             AppSecUsrViewRowImpl appSecUsrRow = (AppSecUsrViewRowImpl)appSecUsrVO.next();

             if (appSecUsrRow.getUsrId() != null)
                 _userid = appSecUsrRow.getUsrId().toString();
               System.out.println(_userid);

         }
         getUsrSlocId(am);
         Configuration.releaseRootApplicationModule(am, true);
     }

     public void setUsername(String _username){
     this._userid = _username.toLowerCase();
     }

     public String getUsername() {
         return _userid;
     }

 //    public void setPassword(String _password) {
 //        this._password = _password;
 //    }
 //
 //    public String getPassword() {
 //        return _password;
 //    }


     public void setIsSecret(Boolean isSecret) {
         this.isSecret = isSecret;
     }

     public Boolean getIsSecret() {
         return isSecret;
     }

     public void setPassWd(RichInputText passWd) {
         this.passWd = passWd;
     }

     public RichInputText getPassWd() {
         return passWd;
     }

     public void setUser(String user) {
         this.user = user;
     }

     public String getUser() {
         return user;
     }


     /**
      * Method to set sloc id in session variable.
      * **/
     public void setUsrSlocId(Integer _usrSlocId) {
         session.setAttribute("sloc_Id", _usrSlocId);
         this._usrSlocId = _usrSlocId;
     }

     public Integer getUsrSlocId(EbizMainAppAMImpl am) {
             try {
                 Integer _usrSlocId =0;
                 System.out.println("getting driver");
                // String driver=getdrivername();
                 String driver="MySQL-AB JDBC Driver";
                 System.out.println("Before entring::driver::"+driver);
                 if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                 System.out.println("Entered in getting server loc id");
                     _usrSlocId=1; 
     //Integer.parseInt(callStoredFunction(NUMERIC, "call     FN_GET_DEF_SERVR_LOC_ID()", new Object[] { }, am).toString()); 
     //System.out.println("Entered after in getting server      loc id:"+_usrSlocId);
                 }
                 else
             {
                _usrSlocId= Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_DEF_SERVR_LOC_ID()", new Object[] { }, am).toString());
             }
             System.out.println("parameter values-->" + " usr_sloc id :" + _usrSlocId);

             setUsrSlocId(_usrSlocId);
         } catch (Exception e) {

             System.out.println("Error @ EbizMainApplication-->LoginBean-->getGlobalParameter()-->APP.FN_GET_DEF_SERVR_LOC_ID(?) ");

             e.printStackTrace();
         }
         return _usrSlocId;
     }

    
     public void setUsr_name(String usr_name) {
         this.usr_name = usr_name;
     }

     public String getUsr_name() {
         return usr_name;
     }

     public void setDisplayDate(String DisplayDate) {
         this.DisplayDate = DisplayDate;
     }

     public String getDisplayDate() {
         DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy ");
         Date date = new Date();
         return dateFormat.format(date);
     }

     public void skinVCE(ValueChangeEvent valueChangeEvent) {
         if (valueChangeEvent.getNewValue() != null) {
             setSkinFamily(valueChangeEvent.getNewValue().toString());
             System.out.println("inside skinVCE" + valueChangeEvent.getNewValue().toString());
             reloadThePage();
         }
     }

     public void setSkinFamily(String skinFamily) {
         this.skinFamily = skinFamily;
     }

     public static void reloadThePage() {
         FacesContext fContext = FacesContext.getCurrentInstance();
         String viewId = fContext.getViewRoot().getViewId();
         String actionUrl = fContext.getApplication().getViewHandler().getActionURL(fContext, viewId);
         try {
             ExternalContext eContext = fContext.getExternalContext();
             String resourceUrl = actionUrl; //eContext.encodeResourceURL(actionUrl);
             // Use the action URL directly since the encoding a resource URL will NPE in isEmailablePage()
             eContext.redirect(resourceUrl);
         } catch (IOException ioe) {

             ioe.printStackTrace();
         }
     }
     
     public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
        elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
        }

    
     public String getdrivername()
         {         String   drivernme ="";
                EbizMainAppAMImpl am = (EbizMainAppAMImpl)resolvElDC("EbizMainAppAMDataControl");
                  DBTransaction dbTransaction = (DBTransaction) am.getTransaction();
                  PreparedStatement prepStatement =
                  dbTransaction.createPreparedStatement("select * from dual", 0);
                  try
                  {
                    System.out.println("Driver name--"+prepStatement.getConnection().getMetaData().getDriverName());
                    drivernme = prepStatement.getConnection().getMetaData().getDriverName();
                  }
                  catch (SQLException e)
                  {
                      e.printStackTrace();
                  }
                  return drivernme;

                  }
    

     protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars, EbizMainAppAMImpl am) {
         CallableStatement st = null;
         try {
             String driver=getdrivername();
             if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                 st = am.getDBTransaction().createCallableStatement("{? = " + stmt + "};", 0);   
             }
             else
             {
             st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
             }
             st.registerOutParameter(1, sqlReturnType);
             if (bindVars != null) {
                 for (int z = 0; z < bindVars.length; z++) {
                     st.setObject(z + 2, bindVars[z]);
                 }
             }
             st.executeUpdate();
             return st.getObject(1);
         } catch (SQLException e) {
             int end = e.getMessage().indexOf("\n");

             throw new JboException(e.getMessage());
         } finally {
             if (st != null) {
                 try {
                     st.close();
                 } catch (SQLException e) {
                     throw new JboException(e.getMessage());
                 }
             }
         }
     }

     /**
      * Generic method to show errors
      * **/
     public void showError(String msg) {

         FacesMessage errMsg = new FacesMessage(msg);
         errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
         context.getCurrentInstance().addMessage(null, errMsg);

     }
 }
