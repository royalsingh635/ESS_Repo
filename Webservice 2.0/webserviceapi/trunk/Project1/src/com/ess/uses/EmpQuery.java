package com.ess.uses;

import com.ess.impl.QueryViewImpl;

public class EmpQuery extends QueryViewImpl {
    private static final String query = "SELECT A.DEPARTMENT_ID, A.DEPARTMENT_NAME FROM DEPARTMENTS A";
    public static int DEPARTMENT_ID = 1;
    public static int DEPARTMENT_NAME = 2;
    
    public EmpQuery() {
        super(query,2);
    }
}
