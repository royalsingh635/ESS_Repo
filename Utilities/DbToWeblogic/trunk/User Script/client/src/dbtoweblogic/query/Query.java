package dbtoweblogic.query;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Query {
    public Query() {
        super();
    }
    
    
    
   public  String query="SELECT usr_id u_id,usr_name u_nm,pkg_sec.dcrypt(usr_pwd) u_pwd from app$sec$usr order by 1";
   
   
   
   
   
   public ArrayList<Map<String,String>> getUserList(Connection conn) throws SQLException {
       ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
       Map<String,String> map=new HashMap<String,String>();
       String uName=null;
       String uPwd=null;
       String uId=null;
                Statement stmt = conn.createStatement();
                   ResultSet rs;
        
                   rs = stmt.executeQuery(query);
                   while ( rs.next() ) {
                        uId = rs.getString("u_id");
                        uName=rs.getString("u_nm");
                        uPwd=rs.getString("u_pwd");;
                              
                      
                       map=new HashMap<String,String>();
                       map.put("Id", uId);
                       map.put("Pwd", uPwd);
                       map.put("Name", uName);
                       list.add(map);
                       
                       System.out.println("User Detail is "+map);
                   }
                   
        return list;
   }
   
   
   public void updatePwd(ArrayList<Integer> list,Connection conn) {
       Statement stmt;
       Iterator iter=list.iterator();
       Integer id=null;

        try {
            stmt = conn.createStatement();
              
            while(iter.hasNext()){
                id = Integer.parseInt( iter.next().toString());
               stmt.executeUpdate("update app$sec$usr set usr_pwd=pkg_sec.ncrypt'12345678') where usr_id="+id);
            }
        } catch (SQLException e) {
            if(id==null)
               System.out.println("Error while creating connection"); 
            else
                  System.out.println("error occured while updating user id :"+ id);
        
        }

   }
}
