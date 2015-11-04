

/**
 * This Class is Created By Mayank Shukla to create user in weblogic
 */

package appSecUser.view.utill;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Hashtable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.ObjectName;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;


public class WeblogicUser {
    
    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String MBEAN_SERVER = "weblogic.management.mbeanservers.domainruntime";
    public static final String JNDI_ROOT = "/jndi/";
    public static final String DEFAULT_PROTOCOL = "t3";
    public static final String PROTOCOL_PROVIDER_PACKAGES = "weblogic.management.remote";
    //This how we get our DomainRuntimeService, this is where DomainConfigurationMBeans exists
    public static final String DOMAIN_MBEAN_NAME = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
    private static JMXConnector connector=null;
    private static MBeanServerConnection connection;
    private static ObjectName defaultAuthenticator;
    private static ObjectName[] authenticationProviders;
    private static String authenticatorName="DefaultAuthenticator";
    private static Connection conn=null;
    private static Integer sLoc=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    private static String host = "hp4420-35";
    private static String port = "7101";
    private static String username = "weblogic";
    private static String password = "weblogic1";
    private static Hashtable h = new Hashtable();
    
    
    public WeblogicUser() {
        super();
    }   
    public static void createConnection(){
        
        try {
          
          
          // following code is used to create  a remote connection with weblogic server.
        
            serverLocation();

            JMXServiceURL serviceURL;

        
        
          /** Following Obeject is used to create a connection with MBean Server as javax.management.remote.JMXServiceURL where protocol may be t3, t3s, http, https, iiop, iiops
           * and jndi name must start with /jndi/ followd by on of the list 
           * 
           * MBean Serve                                   JNDI Name
             Domain Runtime MBean Server                   weblogic.management.mbeanservers.domainruntime
             Runtime MBean Server                          weblogic.management.mbeanservers.runtime 
             Edit MBean Server                             weblogic.management.mbeanservers.edit 

            **/
            
            
            serviceURL =
                    new JMXServiceURL(DEFAULT_PROTOCOL, host, Integer.valueOf(port).intValue(),
                                      "/jndi/weblogic.management.mbeanservers.domainruntime");

           
            h.put("java.naming.security.principal", username);
            h.put("java.naming.security.credentials", password);
            h.put("jmx.remote.protocol.provider.pkgs",
                  "weblogic.management.remote");

            //Creating a JMXConnector to connect to JMX
           //The constructor method for JMXConnector is: javax.management.remote.JMXConnectorFactory.
           // connector(JMXServiceURL serviceURL, Map<String,?> environment)
            
           connector =
                JMXConnectorFactory.connect(serviceURL, h);

           
           
            connection = connector.getMBeanServerConnection();

            
            
            
            /****
              We Get Objects by creating ObjectName with it's Qualified name.
              The constructor take a String of the full Qualified name of the MBean
              We then use connection to get Attribute out of this ObjectName but specifying a String of
              this Attribute
              *****/

         
         
            ObjectName configurationMBeans=
                new ObjectName(DOMAIN_MBEAN_NAME);
           
           
            ObjectName domain =
                (ObjectName)connection.getAttribute(configurationMBeans, "DomainConfiguration");

            ObjectName security =
                (ObjectName)connection.getAttribute(domain, "SecurityConfiguration");

            ObjectName realm =
                (ObjectName)connection.getAttribute(security, "DefaultRealm");

            authenticationProviders =
                    (ObjectName[])connection.getAttribute(realm,
                                                          "AuthenticationProviders");

            for (int i = 0; i < authenticationProviders.length; i++) {
                String name =
                    (String)connection.getAttribute(authenticationProviders[i],
                                                    "Name");

                if (name.equals(authenticatorName))
                    defaultAuthenticator = authenticationProviders[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }
    
    
    public static boolean addUser(String username, String psw, String desc) {
            try {
                /** As of connection.getAttribute you can use connection.invoke to invoke an action
                    It Takes ObjectName, String OperationName, Object[] Parameters, and String[] Parameters
                    Definition
                **/
                connection.invoke(defaultAuthenticator, "createUser",
                                  new Object[] { username, psw, desc },
                                  new String[] { "java.lang.String",
                                                 "java.lang.String",
                                                 "java.lang.String" });

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
                //throw new RuntimeException(e);
            }
        }

        public static boolean removeUser(String username) {
            try {
                if (!username.equalsIgnoreCase("weblogic")) {
                    connection.invoke(defaultAuthenticator, "removeUser",
                                      new Object[] { username },
                                      new String[] { "java.lang.String" });
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public static boolean resetUserPassword(String username,
                                                String newPassword) {
            try {
                if (!username.equalsIgnoreCase("weblogic")) {
                    connection.invoke(defaultAuthenticator, "resetUserPassword",
                                      new Object[] { username, newPassword },
                                      new String[] { "java.lang.String",
                                                     "java.lang.String" });
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    /** As of connection.getAttribute you can use connection.invoke to invoke an action
         It Takes ObjectName, String OperationName, Object[] Parameters, and String[] Parameters
         Definition, It returns an Object we cast it to Boolean, you can know all about function from
         MBeans Reference
     **/
    public static boolean isUserExists(String currentUser) throws RuntimeException {
    try {
     boolean userExists =
         ((Boolean)connection.invoke(defaultAuthenticator, "userExists",
                                     new Object[] { currentUser },
                                     new String[] { "java.lang.String" })).booleanValue();

     return userExists;
    } catch (Exception ex) {
    throw new RuntimeException(ex);
    }
    }
    
    
    public static  ResultSet callSql(String stmt){
        
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        System.out.println("executing sql");
               
             try {      
                    Context ctx = new InitialContext();
                    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/APPDS");
                    conn = ds.getConnection();


                    ps =conn.prepareStatement(stmt);
            
                   rs = ps.executeQuery();
                 
           
                }
             
             catch(Exception e){
                 e.printStackTrace();
             }
    
        return rs;
    }
    
    
    
    private static void serverLocation(){
        
        
        String stmt="select * from app$servr$loc where servr_loc_id="+sLoc;
         
         
        
         ResultSet rs= callSql(stmt);
     
        
        
        if(rs!=null){
        
        System.out.println("evaluating resultset     ");
            try{
              
          
                if(rs.next()){
                   
                   System.out.println("tere is a row."); 
                     host = rs.getString("srvr_loc_as_ip");
                     port = rs.getString("srvr_loc_as_port");
                     username = rs.getString("srvr_loc_as_usr_nm");
                     password =rs.getString("srvr_loc_as_usr_pwd");
                     
                     System.out.println("ip"+host +"port"+port+" username" + username+"password"+password);
                    
                    
                }
                else{
                
                }
                
                
                conn.close();
                
            }catch(Exception e){
              e.printStackTrace();  
            }
        }
        else{
            
            System.out.println("There is error in getting connection");
        }



    }
    
    
    public static String resolvEl(String data) {
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
       String Message = valueExp.getValue(elContext).toString();
       return Message;
    }
    
  //close the client
    
    public static void closeConnection(){
        try {
            connector.close();
        } catch (IOException e) {
            
            System.out.println("In closing connection to server ");
            
            e.printStackTrace();
        }
    }
    
}