package com.ess.impl;

import com.ess.conn.DBConnection;
import com.ess.inter.QueryView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractQueryView implements QueryView {
    public AbstractQueryView() {
        super();
    }
    
    ResultSet execute(Connection con, String query) throws SQLException {
        PreparedStatement prepareStatement = con.prepareStatement(query);
        ResultSet executeQuery = prepareStatement.executeQuery();
        return executeQuery;
    }

}
