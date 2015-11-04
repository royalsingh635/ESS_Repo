package com.ess.conn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import javax.management.ReflectionException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DBConnection implements AutoCloseable {
    //private Connection con;
    Connection con;
    /*   static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(" oracle.jdbc.OracleDriver not found.");
        }
    } */

    private DBConnection() {
    }

    private DBConnection(Connection con) {
        this.con = con;
    }

    /**
     * @param dsName
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static DBConnection getDSConnection(final String dsName) throws NamingException, SQLException,
                                                                           MalformedObjectNameException, MBeanException,
                                                                           AttributeNotFoundException,
                                                                           InstanceNotFoundException,
                                                                           ReflectionException {


        InitialContext ctx = new InitialContext();
        ObjectName service;
        service =
            new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
        MBeanServer server = (MBeanServer) ctx.lookup("java:comp/env/jmx/runtime");
        ObjectName rt = (ObjectName) server.getAttribute(service, "ServerRuntime");
        Object port = server.getAttribute(rt, "ListenPort");


        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        if (port != null) {
            env.put(Context.PROVIDER_URL, "t3://localhost:" + port.toString());
        } else {
            env.put(Context.PROVIDER_URL, "t3://localhost:7001");

        }
        javax.naming.Context initialContext = new javax.naming.InitialContext(env);
        javax.sql.DataSource dataSource = (javax.sql.DataSource) initialContext.lookup("jdbc/" + dsName);
        Connection connection = dataSource.getConnection();


        //   System.out;.println("Server Name  : " + server.getAttribute(rt, "Name"));
        ///  System.out.println("details: " + server);
        //  System.out.println("Server Address : " + server.getAttribute(rt, "ListenAddress"));
        //   System.out.println("Server Port : " + server.getAttribute(rt, "ListenPort"));
        //  System.out.println("Server Port : " + server.getAttribute(rt, "Port"));

        ctx.close();

        return new DBConnection(connection);
    }

    public Connection getConnection() {
        return this.con;
    }

    /**
     * @param ip
     * @param sid
     * @param username
     * @param password
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static Connection getDBConnection(String ip, String sid, String username,
                                              String password) throws SQLException {
        // return DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521:" + sid, username, password);
        return null;
    }

    /**
     * Method is used to get connection
     * @return
     */
    /*   public static Connection getConnection() throws SQLException {
        return getDBConnection("192.168.1.230", "TESTDB", "HR", "hr");
    } */

    public Object callStoredFunction(int type, String functionName, Connection con,
                                     Object... params) throws SQLException {
        String name = "{ ?= call " + functionName + "(";
        for (byte i = 0; i < params.length; i++) {
            name += "?";
            if (i != params.length - 1)
                name += ",";
        }
        name += ")}";
        CallableStatement st = con.prepareCall(name);
        for (byte i = 1; i <= params.length; i++) {
            st.setObject(i + 1, params[i - 1]);
        }
        st.registerOutParameter(1, type);
        st.execute();
        Object val = st.getObject(1);
        st.close();
        return val;
    }

    public void callStoredProcedure(String ProcedureName, Connection con, Object... bindVars) throws SQLException {
        String name = "{ call " + ProcedureName + "(";
        for (int i = 0; i < bindVars.length; i++) {
            name += "?";
            if (i != bindVars.length - 1)
                name += ",";
        }
        name += ")}";

        CallableStatement st = con.prepareCall(name);
        for (byte i = 1; i <= bindVars.length; i++) {
            st.setObject(i, bindVars[i - 1]);
        }
        st.execute();
        st.close();
    }

    // CallableStatement stmt=con.prepareCall("{call myprocedure(?,?)}");

    /**
     * @throws SQLException
     */
    public void close() throws SQLException {
        con.close();
    }
}
