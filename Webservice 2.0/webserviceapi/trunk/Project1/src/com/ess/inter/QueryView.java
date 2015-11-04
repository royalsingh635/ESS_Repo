package com.ess.inter;

import com.ess.conn.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryView {
    public String getQuery();

    public int getColumnCount();

    public ResultSet getResultSet(Connection con) throws SQLException ;

    public ResultSet getResultSetWithGivenClause(Connection con, String clause) throws SQLException;

    public ResultSet executeQuery(Connection con, String query) throws SQLException;
}
