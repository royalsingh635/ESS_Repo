package dbtoweblogic;

import dbtoweblogic.conn.ConnectionProvider;

import dbtoweblogic.query.Query;

import dbtoweblogic.weblogic.WeblogicUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class MainClass {
    public MainClass() {
        super();
    }

    public static void main(String[] args) {
         try{
                    
                    System.out.println("Plese enter the databse ip ");
                    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                    String dbIp = bufferRead.readLine();
         
                    System.out.println("Plese Enter sid");
                    String sid=bufferRead.readLine();
             
                    System.out.println("Plese database username");
                    String dbUser=bufferRead.readLine();
             
                    System.out.println("Plese database password");
                    String dbPwd=bufferRead.readLine();
                    
                    Connection conn=ConnectionProvider.getConnection(dbIp,sid,dbUser, dbPwd);
                
                     if(conn==null){
                         System.out.println("Error while creating connection ");
                         return;   
                     }
                
                   System.out.println("------------------Weblogic Details----------------------"); 
                    System.out.println("Weblogic Ip ");
                    String wbIp = bufferRead.readLine();
                    
                    System.out.println("Plese Enter weblogic port");
                    String port=bufferRead.readLine();
                    
                    System.out.println("Plese weblogic username");
                    String wbUser=bufferRead.readLine();
                    
                    System.out.println("Plese weblogic password");
                    String wbPwd=bufferRead.readLine();
             
                   
                    
                    WeblogicUser.setValues(wbIp, port, wbUser, wbPwd);
                    
             
                    System.out.println("Creating dWeblogic connection");
                    
                    WeblogicUser.createConnection();
             
                    System.out.println("Connected");
                    Query q=new Query();
                    
                    ArrayList<Map<String,String>> list=q.getUserList(conn);
                    
                    System.out.println("Inserting User");
                    
                    Map<String,ArrayList> map=WeblogicUser.insertUser(list);
             
                    System.out.println("USer Added to Weblogic");
                    
                   if(!map.get("Id").isEmpty()){
                        q.updatePwd(map.get("Id"), conn);
                        
                        ArrayList<String> name=map.get("Name");
                        Collections.sort(name);
                        
                        Iterator iter=name.iterator();
                        System.out.println("Fallowing user's password has been updated");
                        Integer sr=1;
                        while(iter.hasNext()){
                            System.out.println(sr++ +iter.next().toString());
                        }
                        
                   } 
             
                    if(!map.get("Error").isEmpty()){
                         
                         
                         ArrayList<String> name=map.get("Error");
                         Collections.sort(name);
                         
                         Iterator iter=name.iterator();
                         System.out.println("Fallowing user's password cant br created");
                         Integer sr=1;
                         while(iter.hasNext()){
                             System.out.println(sr++ +iter.next().toString());
                         }
                         
                    }
                
                    WeblogicUser.closeConnection();
                    conn.close();
                } catch(Exception e)
                {
                        e.printStackTrace();
                }
     }
    
}
