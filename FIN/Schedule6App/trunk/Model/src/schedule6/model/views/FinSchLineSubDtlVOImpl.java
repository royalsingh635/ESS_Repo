package schedule6.model.views;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.TransactionEvent;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 17 20:30:49 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FinSchLineSubDtlVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FinSchLineSubDtlVOImpl() {
    }

    /**
     * Returns the bind variable value for BindCoaId.
     * @return bind variable value for BindCoaId
     */
    public Integer getBindCoaId() {
        return (Integer) getNamedWhereClauseParam("BindCoaId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCoaId.
     * @param value value to bind as BindCoaId
     */
    public void setBindCoaId(Integer value) {
        setNamedWhereClauseParam("BindCoaId", value);
    }
}

