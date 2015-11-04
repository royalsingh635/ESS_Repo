package dbtoweblogic.conn;

import java.sql.Connection;
import java.sql.DriverManager;
 
    public class ConnectionProvider
    {
        public static Connection getConnection(String ip,String sid,String u_name, String password)
        {
            System.out.println("Trying to connect to databse");
            
            Connection con=null;
            try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:"+sid,u_name, password);
            }
            catch(Exception e)
            {
            System.out.println(e);
            }
            
            System.out.println("Connection Successfull");
            
            return con;
            }
 
    }
