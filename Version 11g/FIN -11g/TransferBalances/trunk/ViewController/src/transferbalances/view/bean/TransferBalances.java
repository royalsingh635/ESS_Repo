package transferbalances.view.bean;

import java.math.BigDecimal;

import java.math.MathContext;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import transferbalances.model.services.TransferBalancesAMImpl;


public class TransferBalances {

    private String iebaltype;
    private String albaltype;
    private Boolean refresh = false;
    private String amName = "TransferBalancesAMDataControl";
    private BigDecimal Sum = new BigDecimal(0);
    private BigDecimal as = new BigDecimal(0);
    private BigDecimal asCr = new BigDecimal(0);
    private String vouid;
    private Integer docid = 56;
    private Date voudt;
    private Date asondt;
    private String narration;
    private String type;
    private Date fystart;
    private Date fyend;
    private Date asOnDate;
    private RichInputDate asOnDt;
    private RichInputDate txnDt;
    private int VARCHAR = Types.VARCHAR;


    public TransferBalances() {
        super();
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }


    public void refreshDetail(ActionEvent actionEvent) {
        
        if(getAsOnDate() == null)
        {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage msg =
                    new FacesMessage(resolvEl("#{bundle['MSG.373']}"));
                msg.setSeverity(msg.SEVERITY_ERROR);
                fc.addMessage(asOnDt.getClientId(), msg);
            
            }
        else
        {
      System.out.println(getAsOnDate()+"---------------as on date");
        Object slocid = resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        Object orgid = resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}");

        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);

        ViewObject lovview = am.getLovOrgFy();
        Row lovrow = lovview.getCurrentRow();
        ViewObject ieview = am.getIncomeExpence();
  
        ViewObject alview = am.getAssetsLiability();
       
       
        ieview.setWhereClause("");
        alview.setWhereClause("");
        ieview.executeQuery();
        alview.executeQuery();

System.out.println(slocid +"---"+orgid+"------"+lovrow.getAttribute("OrgFyId"));



        ieview.setWhereClause("COA_SLOC_ID =" + slocid + "AND ORG_ID =" + orgid + "AND ORG_FY_ID =" +
                              lovrow.getAttribute("OrgFyId"));
        alview.setWhereClause("COA_SLOC_ID =" + slocid + "AND ORG_ID =" + orgid + "AND ORG_FY_ID =" +
                              lovrow.getAttribute("OrgFyId"));

        ieview.executeQuery();
        alview.executeQuery();

        setClosingBal();
        setRefresh(true); 
    }
    }

    public void generateTransferVoucher(ActionEvent actionEvent) {
        if(getVoudt() == null)
        {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage msg =
                    new FacesMessage(resolvEl("#{bundle['MSG.373']}"));
                msg.setSeverity(msg.SEVERITY_ERROR);
                fc.addMessage(txnDt.getClientId(), msg);
            
            }
        else
        {
        System.out.println(getAnltotal()+"-------------------"+getInetotal());
        if (getAnltotal().round(new MathContext(3)).compareTo(getInetotal().round(new MathContext(3))) != 0 || !getIebaltype().equals(getAlbaltype())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg =
                new FacesMessage(resolvEl("#{bundle['MSG.374']}"));
            msg.setSeverity(msg.SEVERITY_ERROR);
            fc.addMessage(null, msg);
        } else {
            Object slocid = resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}");
            Object orgid = resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Object userid = resolveElExp("#{pageFlowScope.GLBL_APP_USR}");
            Object ho_org_id = resolveElExp("#{pageFlowScope.GLBL_HO_ORG_ID}");
            Object cld_id = resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}");
            BindingContext bcntxt = BindingContext.getCurrent();
            BindingContainer bcont = bcntxt.getCurrentBindingsEntry();
            OperationBinding opb = bcont.getOperationBinding("callmaxno");
            setVouid(opb.execute().toString());
            System.out.println(vouid+"------");
            System.out.println(getVouid() + " " + this.getVoudt());
            TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
            ViewObject glline = am.getGlLines();
            ViewObject expence = am.getIncomeExpence();
            ViewObject gl = am.getGl();
            RowSetIterator tab1rit = expence.createRowSetIterator(null);
            ViewObjectImpl liab = am.getAssetsLiability();
            RowSetIterator tab2rit = liab.createRowSetIterator(null);
            int i = 0;
            while (tab1rit.hasNext()) {
               i=i+1;
                Row lineRow = tab1rit.next();
                if (lineRow.getAttribute("ClosingBal") != null &&
                    !lineRow.getAttribute("ClosingBal").equals(new BigDecimal(0))) {
                   
                    Row newglline = glline.createRow();
                    newglline.setAttribute("GlHoOrgId", ho_org_id);
                    newglline.setAttribute("GlCldId", cld_id);
                    newglline.setAttribute("GlSlocId", slocid);
                    newglline.setAttribute("GlOrgId", orgid);
                    newglline.setAttribute("GlVouId", getVouid());
                    newglline.setAttribute("GlVouDt", getVoudt());
                    newglline.setAttribute("GlTypeId", 1);
                    newglline.setAttribute("GlSlNo", i);
                    newglline.setAttribute("GlTxnTypMig", "N");
                    newglline.setAttribute("GlCoaId", lineRow.getAttribute("CoaId"));
                    newglline.setAttribute("GlNaId", lineRow.getAttribute("CoaAccId"));
                    newglline.setAttribute("GlCogId", lineRow.getAttribute("CoaCogId"));
                    newglline.setAttribute("GlAmtSp", lineRow.getAttribute("ClosingBal"));
                    newglline.setAttribute("GlAmtBs", lineRow.getAttribute("ClosingBal"));
                    newglline.setAttribute("GlApplInstId", 1);
                    //    newglline.setAttribute("GlEoId", 0); // lineRow.getAttribute("OrgCoaEoId"));
                    //    newglline.setAttribute("GlEomstId", 0); // lineRow.getAttribute("OrgCoaEoMstId"));
                    newglline.setAttribute("GlCurrIdSp", 73);
                   
                    newglline.setAttribute("GlCurrIdBs", 73);
                    newglline.setAttribute("GlStat", "FLC");
                    newglline.setAttribute("UsrIdCreate", userid);
                    newglline.setAttribute("GlNarr", getNarration());
                    if (lineRow.getAttribute("OrgCoaClBalTyp").equals("Dr")) {
                        newglline.setAttribute("GlAmtTyp", "Cr");
                    }
                    if (lineRow.getAttribute("OrgCoaClBalTyp").equals("Cr")) {
                        newglline.setAttribute("GlAmtTyp", "Dr");
                    }
                    glline.insertRow(newglline);
                }
            }
            int j = 0;
            while (tab2rit.hasNext()) {
                i=i+1;
                System.out.println(i+"------");
                Row liaRow = tab2rit.next();
                if (liaRow.getAttribute("TransferredBalance") != null &&
                    liaRow.getAttribute("TransferredBalance") != new BigDecimal(0.0)) {
                    Row newline = glline.createRow();
                    newline.setAttribute("GlHoOrgId", ho_org_id);
                    newline.setAttribute("GlCldId", cld_id);
                    newline.setAttribute("GlSlocId", slocid);
                    newline.setAttribute("GlOrgId", orgid);
                    newline.setAttribute("GlVouId", getVouid());
                    newline.setAttribute("GlVouDt", getVoudt());
                    newline.setAttribute("GlTypeId", 1);
                    newline.setAttribute("GlSlNo", i);
                    newline.setAttribute("GlCoaId", liaRow.getAttribute("CoaId"));
                    newline.setAttribute("GlNaId", liaRow.getAttribute("CoaAccId"));
                    newline.setAttribute("GlCogId", liaRow.getAttribute("CoaCogId"));
                    newline.setAttribute("GlAmtSp", liaRow.getAttribute("TransferredBalance"));
                    newline.setAttribute("GlAmtBs", liaRow.getAttribute("TransferredBalance"));
                    newline.setAttribute("GlApplInstId", 1);
                    //    newline.setAttribute("GlEoId", 0); // lineRow.getAttribute("OrgCoaEoId"));
                    //    newline.setAttribute("GlEomstId", 0); // lineRow.getAttribute("OrgCoaEoMstId"));
                    newline.setAttribute("GlCurrIdSp", 73);
                    newline.setAttribute("GlTxnTypMig", "N");
                    
                    newline.setAttribute("GlCurrIdBs", 73);
                    newline.setAttribute("UsrIdCreate", userid);
                    newline.setAttribute("GlStat", "FLC");
                    newline.setAttribute("GlNarr", getNarration());
                    newline.setAttribute("GlAmtTyp", liaRow.getAttribute("TrafBalType"));
                    glline.insertRow(newline);
                }
            }
            Row glrow = gl.createRow();
            glrow.setAttribute("GlHoOrgId", ho_org_id);
            glrow.setAttribute("GlCldId", cld_id);
            glrow.setAttribute("GlSlocId", slocid);
            glrow.setAttribute("GlApplInstId", 1);
            glrow.setAttribute("GlOrgId", orgid);
            glrow.setAttribute("GlTypeId", 1);
            glrow.setAttribute("GlVouId", getVouid());
            glrow.setAttribute("GlVouDt", getVoudt());
            glrow.setAttribute("GlTypMig", "N");
            glrow.setAttribute("GlCc", new Integer(1));
            glrow.setAttribute("UsrIdCreate", userid);
            glrow.setAttribute("GlDesc", getNarration());
            glrow.setAttribute("GlStat", "FLC");
            gl.insertRow(glrow);
            OperationBinding save = bcont.getOperationBinding("Commit");
            save.execute();
            String ret = callStoredFunction(VARCHAR, "APP.PKG_APP.INS_DISP_DOC_TAB(?,?,?)", new Object[] {getVouid(),userid,getVoudt()}).toString();
           
            save.execute();
            setRefresh(false);
            am.getDBTransaction().closeTransaction();
            
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg =
                new FacesMessage(resolvEl("#{bundle['MSG.376']}"));
            msg.setSeverity(msg.SEVERITY_INFO);
            fc.addMessage(null, msg);
        }
        }
    }

    public BigDecimal getInetotal() {
        setSum(new BigDecimal(0));
        setAs(new BigDecimal(0));
        setAsCr(new BigDecimal(0));

        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObjectImpl expence = am.getIncomeExpence();
        RowSetIterator rit = expence.createRowSetIterator(null);
        if (rit.first() != null && rit.first().getAttribute("OrgCoaClBalTyp").equals("Dr")) {
            if (rit.first().getAttribute("ClosingBal") != null)
                as = (BigDecimal)(rit.first().getAttribute("ClosingBal"));
            else
                as = new BigDecimal(0);
        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("OrgCoaClBalTyp").equals("Dr")) {
                if (lineRow.getAttribute("ClosingBal") != null)
                    as = as.add((BigDecimal)(lineRow.getAttribute("ClosingBal")));
                else
                    as = new BigDecimal(0);

            }
        }
        if (rit.first() != null && rit.first().getAttribute("OrgCoaClBalTyp").equals("Cr")) {
            if (rit.first().getAttribute("ClosingBal") != null)
                asCr = (BigDecimal)(rit.first().getAttribute("ClosingBal"));
            else
                as = new BigDecimal(0);
        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("OrgCoaClBalTyp").equals("Cr")) {
                if (lineRow.getAttribute("ClosingBal") != null)
                    asCr = asCr.add((BigDecimal)(lineRow.getAttribute("ClosingBal")));
                else
                    as = new BigDecimal(0);
            }
        }

        /** calculate sum by getting diffference of credit and debit amounts. */
        if (as.compareTo(asCr) == 1) {
            setSum(as.subtract(asCr));
            setIebaltype("Dr");
        } else if (as.compareTo(asCr) == -1) {
            setSum(asCr.subtract(as));
            setIebaltype("Cr");
        }
        return getSum();
    }

    public void setSum(BigDecimal Sum) {
        this.Sum = Sum;
    }

    public BigDecimal getSum() {
        return Sum;
    }

    public void setAs(BigDecimal as) {
        this.as = as;
    }

    public BigDecimal getAs() {
        return as;
    }

    public void setAsCr(BigDecimal asCr) {
        this.asCr = asCr;
    }

    public BigDecimal getAsCr() {
        return asCr;
    }

    public BigDecimal getAnltotal() {
        setSum(new BigDecimal(0));
        setAs(new BigDecimal(0));
        setAsCr(new BigDecimal(0));
        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObjectImpl liab = am.getAssetsLiability();
        RowSetIterator rit = liab.createRowSetIterator(null);
        if (rit.first() != null && rit.first().getAttribute("TrafBalType") != null &&
            rit.first().getAttribute("TrafBalType").equals("Dr")) {
            if (rit.first().getAttribute("TransferredBalance") != null)
                as = (BigDecimal)(rit.first().getAttribute("TransferredBalance"));
            else
                as = new BigDecimal(0);

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TrafBalType") != null && lineRow.getAttribute("TrafBalType").equals("Dr")) {
                if (lineRow.getAttribute("TransferredBalance") != null)
                    as = as.add((BigDecimal)(lineRow.getAttribute("TransferredBalance")));
                else
                    as = new BigDecimal(0);


            }
        }

        if (rit.first() != null && rit.first().getAttribute("TrafBalType") != null &&
            rit.first().getAttribute("TrafBalType").equals("Cr")) {
            if (rit.first().getAttribute("TransferredBalance") != null)
                asCr = (BigDecimal)(rit.first().getAttribute("TransferredBalance"));
            else
                as = new BigDecimal(0);

        }
        while (rit.hasNext()) {
            Row lineRow = rit.next();
            if (lineRow.getAttribute("TrafBalType") != null && lineRow.getAttribute("TrafBalType").equals("Cr")) {
                if (lineRow.getAttribute("TransferredBalance") != null)
                    asCr = asCr.add((BigDecimal)(lineRow.getAttribute("TransferredBalance")));
                else
                    as = new BigDecimal(0);


            }
        }

        /** calculate sum by getting diffference of credit and debit amounts. */
        if (as.compareTo(asCr) == 1) {
            setSum(as.subtract(asCr));
            setAlbaltype("Dr");
        } else if (as.compareTo(asCr) == -1) {
            setSum(asCr.subtract(as));
            setAlbaltype("Cr");
        }
        return getSum();

    }

    public void fyIdChange(ValueChangeEvent valueChangeEvent) {
        asOnDt.setValue(null);
        txnDt.setValue(null);
        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObject lovView = am.getLovOrgFy();
        lovView.setWhereClause("ORG_FY_ID = " + valueChangeEvent.getNewValue());
        lovView.executeQuery();
        setFystart((Date)lovView.getCurrentRow().getAttribute("FyStrt"));
        setFyend((Date)lovView.getCurrentRow().getAttribute("FyEnd"));
    }

    public void setClosingBal() {

        BindingContext btc = BindingContext.getCurrent();
        BindingContainer bincon = btc.getCurrentBindingsEntry();
        OperationBinding opb = bincon.getOperationBinding("getClosingBalances");
        Object slocid = resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        Object orgid = resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}");

        TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
        ViewObjectImpl ieview = am.getIncomeExpence();
        ViewObjectImpl alview = am.getAssetsLiability();

        RowSetIterator rowsetit = ieview.createRowSetIterator(null);
        RowSetIterator rowsetital = alview.createRowSetIterator(null);
        while (rowsetit.hasNext()) {
            Row irRow = rowsetit.next();
            opb.getParamsMap().put("P_SLOC_ID", slocid);
            opb.getParamsMap().put("P_ORG_ID", orgid);
            opb.getParamsMap().put("P_ASON_DT", getAsOnDate());
            opb.getParamsMap().put("P_COA_ID", irRow.getAttribute("CoaId"));
            opb.getParamsMap().put("P_INC_TEMP", "N");
            opb.getParamsMap().put("P_INC_POST", "P");
            opb.getParamsMap().put("P_INC_OP_BAL", "Y");

            BigDecimal num = (BigDecimal)opb.execute();

            if (num.compareTo(new BigDecimal(0)) == -1) {
                irRow.setAttribute("OrgCoaClBalTyp", "Cr");
            } else
                irRow.setAttribute("OrgCoaClBalTyp", "Dr");

            irRow.setAttribute("ClosingBal", num.abs());

        }
        while (rowsetital.hasNext()) {
            
            System.out.println("-------------------in the al tab----");
            Row irRow = rowsetital.next();
            opb.getParamsMap().put("P_SLOC_ID", slocid);
            opb.getParamsMap().put("P_ORG_ID", orgid);
            opb.getParamsMap().put("P_ASON_DT", getAsOnDate());
            opb.getParamsMap().put("P_COA_ID", irRow.getAttribute("CoaId"));
            opb.getParamsMap().put("P_INC_TEMP", "N");
            opb.getParamsMap().put("P_INC_POST", "P");
            opb.getParamsMap().put("P_INC_OP_BAL", "Y");

            BigDecimal amount = (BigDecimal)opb.execute();

            if (amount.compareTo(new BigDecimal(0)) == -1) {
                irRow.setAttribute("OrgCoaClBalTyp", "Cr");
            } else
                irRow.setAttribute("OrgCoaClBalTyp", "Dr");

            irRow.setAttribute("ClosingBal", amount.abs());
        }
    }

    public void setIebaltype(String iebaltype) {
        this.iebaltype = iebaltype;
    }

    public String getIebaltype() {
        return iebaltype;
    }

    public void setAlbaltype(String albaltype) {
        this.albaltype = albaltype;
    }

    public String getAlbaltype() {
        return albaltype;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public Boolean getRefresh() {
        return refresh;
    }

    public void setVouid(String vouid) {
        this.vouid = vouid;
    }

    public String getVouid() {
        return vouid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setVoudt(Date voudt) {
        this.voudt = voudt;
    }

    public Date getVoudt() {
        return voudt;
    }

    public void setAsondt(Date asondt) {
        this.asondt = asondt;
    }

    public Date getAsondt() {
        return asondt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
    }

    public Date getCurrentDate() {
        return new Date(new java.sql.Date(System.currentTimeMillis()));
    }

    public void setFyend(Date fyend) {
        this.fyend = fyend;
    }

    public Date getFyend() {
        return fyend;
    }

    public void setFystart(Date fystart) {
        this.fystart = fystart;
    }

    public Date getFystart() {
        return fystart;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDt(RichInputDate asOnDt) {
        this.asOnDt = asOnDt;
    }

    public RichInputDate getAsOnDt() {
        return asOnDt;
    }

    public void setTxnDt(RichInputDate txnDt) {
        this.txnDt = txnDt;
    }

    public RichInputDate getTxnDt() {
        return txnDt;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            TransferBalancesAMImpl am = (TransferBalancesAMImpl)resolvElDC(amName);
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage().substring(11, end));
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }




    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
}
