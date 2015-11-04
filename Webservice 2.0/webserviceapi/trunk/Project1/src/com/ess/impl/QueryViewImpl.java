package com.ess.impl;

import com.ess.conn.DBConnection;
import com.ess.inter.QueryView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryViewImpl extends AbstractQueryView{
    private String query ;
    private Integer columnCount;
    private DBConnection con = null;

    public QueryViewImpl(String query,Integer columnCount) {
        super();
        this.query = query;
        this.columnCount = columnCount;
    }
    
    public String getQuery() {
        return query;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public ResultSet getResultSet(Connection con) throws SQLException {
        return execute(con, query);
    }

    public ResultSet getResultSetWithGivenClause(Connection con, String clause) throws SQLException {
        return executeQuery(con, query + " " + clause);
    }


    public ResultSet executeQuery(Connection con, String query) throws SQLException {
        // TODO Implement this method
        return null;
    }
}
