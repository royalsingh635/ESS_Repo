/* package com.ess.main;

import com.ess.conn.DBConnection;
import com.ess.uses.EmpQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Class1 {
    public Class1() {
        super();
    }
    public static void main(String[] args) {
        EmpQuery q = new EmpQuery();
        System.out.println(q.getQuery());
        System.out.println(q.getColumnCount());
        Connection con = null;
        try {
         //   con = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet = q.getResultSet(con);
            System.out.println("==========="+resultSet);
            while(resultSet.next()){
                String string = resultSet.getString(EmpQuery.DEPARTMENT_ID);
                String string_2 = resultSet.getString(EmpQuery.DEPARTMENT_NAME);
                System.out.println(string+" | "+string_2 );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        

    }
} */
