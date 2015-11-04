package mmquotationanalysis.view.bean;

import adf.utils.model.ADFModelUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mmquotationanalysis.model.services.quotationAnaAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class QuotResultBean {

    private RichPopup generatePopup;
    private RichSelectBooleanCheckbox quotSelectBind;
    private RichTable quotRstTabBind;
    private RichInputText quotDocIdBind;
    private String selChkDisable;
    String poId = null;
    private RichTable anaRsltTabBind;
    private Boolean editRmkMode = true;

    public void setEditRmkMode(Boolean editRmkMode) {
        this.editRmkMode = editRmkMode;
    }

    public Boolean getEditRmkMode() {
        return editRmkMode;
    }

    public QuotResultBean() {
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

    public String ViewQuotparamLink() {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        ViewObject vo = am.getMmQuotAnaRslt1();
        String rfqId = null;
        String eval_id = null;
        if (vo.getCurrentRow() != null) {
            if (vo.getCurrentRow().getAttribute("RfqDocId") != null)
                rfqId = (String) vo.getCurrentRow().getAttribute("RfqDocId");
            if (vo.getCurrentRow().getAttribute("EvalId") != null)
                eval_id = (String) vo.getCurrentRow().getAttribute("EvalId");

            System.out.println("evaluation id and rf id is " + rfqId + " " + eval_id);
            am.getMmQuot1().setWhereClause("REF_DOC_ID = '" + rfqId + "'");
            am.getMmQuot1().executeQuery();
            am.getMmQuotAna3().setWhereClause("EVAL_ID = '" + eval_id + "'");
            am.getMmQuotAna3().executeQuery();
            /*  ViewObjectImpl quotvo=am.getMmQuot1();
        quotvo.setNamedWhereClauseParam("bindQuotStatus", 235);
        quotvo.executeQuery(); */
        }

        return "Old";
    }

    public void QuotSelValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        Boolean val = (Boolean) object;

        String value = "";
        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }


        if (val == true) {


            ViewObject v2 = am.getMmQuotAnaRslt1();
            int count = 0;
            String x = "";
            int totalCount = v2.getRowCount(); //get ALL rows
            int rangeSize = v2.getRangeSize();
            v2.setRangeSize(totalCount);
            Row[] rowI = v2.getAllRowsInRange();
            for (Row r : rowI) {
                try {
                    x = r.getAttribute("QuotSel").toString();
                } catch (NullPointerException npe) {
                    //System.out.println("NPE" + npe);
                    x = "";
                }

                if (value.equalsIgnoreCase(x)) {
                    count = count + 1;
                }

            }
            v2.setRangeSize(rangeSize);


            if (count > 1) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.318']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else {

                // am.getDBTransaction().commit();
                //am.getMmQuotAnaRslt1().executeQuery();

            }

        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String newAnalysisButton() {


        return "New";
    }

    public void QuotSelectVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            BindingContainer bindings4 = getBindings();
            //DCIteratorBinding parentIter = (DCIteratorBinding)bindings4.get("MmQuotAnaRslt1Iterator");
            /*  Key parentKey = parentIter.getCurrentRow().getKey();
            System.out.println("parentKey ---------------    "+parentKey); */
            String newVal = vce.getNewValue().toString();
            System.out.println("selected value   " + newVal);
            System.out.println("bind ---- " + quotSelectBind.getValue());
            System.out.println("docid   bind--           1- " + quotDocIdBind.getValue());

            DCControlBinding dc = (DCControlBinding) bindings4.get("MmQuotAnaRslt1");
            ViewObject vo = dc.getViewObject();
            Row currRow = vo.getCurrentRow();
            String docId = (String) currRow.getAttribute("QuotDocId");
            System.out.println("value -----------" + docId);
            /*    RowKeySet selectedEmps = getQuotRstTabBind().getSelectedRowKeys();//getEmpTable().getSelectedRowKeys();
                   Iterator selectedEmpIter = selectedEmps.iterator();
                   DCBindingContainer bindings =
                                     (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                   DCIteratorBinding empIter = bindings.findIteratorBinding("MmQuotAnaRslt1Iterator");
                   RowSetIterator empRSIter = empIter.getRowSetIterator();
                    while(selectedEmpIter.hasNext()){
                      Key key = (Key)((List)selectedEmpIter.next()).get(0);
                      Row currentRow = empRSIter.getRow(key);
                      System.out.println("dc binding "+currentRow.getAttribute("QuotDocId"));
                        System.out.println("quot id binding "+currentRow.getAttribute("QuotId"));
                    }  */
            if (newVal.equalsIgnoreCase("true")) {
                System.out.println("selected value   1212");
                showPopup(generatePopup, true);
            }
        }
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setGeneratePopup(RichPopup generatePopup) {
        this.generatePopup = generatePopup;
    }

    public RichPopup getGeneratePopup() {
        return generatePopup;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        PreparedStatement st = null;
        try {
            // 1. Create a JDBC PreparedStatement for
            st = am.getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
            if (bindVars != null) {
                // 2. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 3. Set the value of each bind variable in the statement
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            // 4. Execute the statement
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 5. Close the statement
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

    public void generatePODialogListener(DialogEvent dialogEvent) {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        BindingContainer bindings4 = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding) bindings4.get("MmQuotAnaRslt1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        String P_QUOTDOCID = am.getMmQuotAnaRslt1().getCurrentRow().getAttribute("QuotDocId").toString();
        System.out.println(" docid ------" + P_QUOTDOCID);
        System.out.println("docid   bind--- " + quotDocIdBind.getValue());
        System.out.println("parentKey     " + parentKey);
        if (dialogEvent.getOutcome().name().equals("yes")) {
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            Integer P_USERID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String P_CldID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String P_HOORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            System.out.println("P_SLOCID " + P_SLOCID + "P_USERID " + P_USERID + "P_ORGID " + P_ORGID + "P_CldID " +
                               P_CldID + "P_QUOTDOCID " + P_QUOTDOCID);
            System.out.println(" PO ID before function call " + poId);
            Timestamp dt = new Timestamp(System.currentTimeMillis());
            Integer fyid = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                         P_CldID, P_ORGID, dt
            }));
            poId = (String) callStoredFunction(Types.VARCHAR, "MM.MM_GEN_DRFT_PO_FRM_QUOT(?,?,?,?,?,?,?)", new Object[] {
                                               P_SLOCID, P_CldID, P_ORGID, P_QUOTDOCID, 170, P_USERID, fyid
            });
            // quotSelectBind.setDisabled(true);
            am.getDBTransaction().commit();
            am.getMmQuotAnaRslt1().executeQuery();
            System.out.println(" PO ID after function call " + poId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotRstTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSelectBind);
            if (poId != null) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['LBL.769']}").toString() + poId + " " +
                                     resolvElDCMsg("#{bundle['MSG.319']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

        } else {
            System.out.println("-------else-----------");
            if (parentIter.getRowSetIterator().getRow(parentKey) != null) { //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }

            quotSelectBind.setValue(false);
            quotSelectBind.setValue("N");
            am.getMmQuotAnaRslt1().getCurrentRow().setAttribute("QuotSel", "N");
            System.out.println("value       " + am.getMmQuotAnaRslt1().getCurrentRow().getAttribute("QuotSel"));
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotRstTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSelectBind);
        }
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    System.out.println(bindVars[z] + "z");
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void popupCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        quotSelectBind.setValue(false);
        quotSelectBind.setValue("N");
        am.getMmQuotAnaRslt1().getCurrentRow().setAttribute("QuotSel", "N");
        System.out.println("value       " + am.getMmQuotAnaRslt1().getCurrentRow().getAttribute("QuotSel"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotRstTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotSelectBind);
    }

    public void setQuotSelectBind(RichSelectBooleanCheckbox quotSelectBind) {
        this.quotSelectBind = quotSelectBind;
    }

    public RichSelectBooleanCheckbox getQuotSelectBind() {
        return quotSelectBind;
    }

    public void setQuotRstTabBind(RichTable quotRstTabBind) {
        this.quotRstTabBind = quotRstTabBind;
    }

    public RichTable getQuotRstTabBind() {
        return quotRstTabBind;
    }

    private JUCtrlRangeBinding getSearchView() {
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
        return (JUCtrlRangeBinding) bindings.getControlBinding("MmQuotAnaRslt1");
    }

    public void rowSelected(SelectionEvent se) {
        JUCtrlRangeBinding searchView = getSearchView();
        searchView.getIteratorBinding().setCurrentRowIndexInRange((Integer) se.getAddedSet().toArray()[0]);
    }

    public void setQuotDocIdBind(RichInputText quotDocIdBind) {
        this.quotDocIdBind = quotDocIdBind;
    }

    public RichInputText getQuotDocIdBind() {
        return quotDocIdBind;
    }


    public void setSelChkDisable(String selChkDisable) {
        this.selChkDisable = selChkDisable;
    }

    public String disValue() {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        ViewObject v2 = am.getMmQuotAnaRslt1();
        int count = 0;
        String x = "";
        String retnVal = "";
        int totalCount = v2.getRowCount(); //get ALL rows
        int rangeSize = v2.getRangeSize();
        v2.setRangeSize(totalCount);
        Row[] rowI = v2.getAllRowsInRange();
        for (Row r : rowI) {
            try {
                x = r.getAttribute("QuotSel").toString();
            } catch (NullPointerException npe) {
                //System.out.println("NPE" + npe);
                x = "";
            }
            System.out.println("x------------" + x);
            if (x.equalsIgnoreCase("Y")) {
                count = count + 1;
            }

        }
        System.out.println("count -----------" + count);
        v2.setRangeSize(rangeSize);
        if (count > 0) {
            retnVal = "D";
        } else {
            retnVal = "P";
        }
        System.out.println("retnVal         " + retnVal);
        return retnVal;

    }

    public String getSelChkDisable() {
        quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
        ViewObject v2 = am.getMmQuotAnaRslt1();
        int count = 0;
        String x = "";
        String retnVal = "";
        int totalCount = v2.getRowCount(); //get ALL rows
        int rangeSize = v2.getRangeSize();
        v2.setRangeSize(totalCount);
        Row[] rowI = v2.getAllRowsInRange();
        for (Row r : rowI) {
            try {
                x = r.getAttribute("QuotSel").toString();
            } catch (NullPointerException npe) {
                //System.out.println("NPE" + npe);
                x = "";
            }
            if (x.equalsIgnoreCase("Y")) {
                count = count + 1;
            }

        }
        v2.setRangeSize(rangeSize);
        if (count > 0) {
            retnVal = "D";
        } else {
            retnVal = "P";
        }
        return retnVal;
    }


    public String saveAndForwardAction() {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;
        //PurOrderAMImpl poAM = getAm();
        //-------------------------------------------------------
        System.out.println("sloc_id    " + sloc_id + "cld_id   " + cld_id + "org_id  " + pOrgId);
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingPOCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("QuotAnaDocNo", 18510);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        System.out.println(usr_id + "pending user id=" + pending);
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
            quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
            ViewObject quotAnaRslt = am.getMmQuotAnaRslt1();
            Row currPo = quotAnaRslt.getCurrentRow();
            String quotAnaEvalId = quotAnaRslt.getCurrentRow().getAttribute("EvalId").toString();

            String wf_id = "W037";
            String action = "I";
            String remark = "A";
            // Number amount11 = (Number)currPo.getAttribute("PoAmtBs");

            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_id);
            WfnoOp.getParamsMap().put("cld_id", cld_id);
            WfnoOp.getParamsMap().put("org_id", pOrgId);
            WfnoOp.getParamsMap().put("doc_no", 18510);
            WfnoOp.execute();

            String WfNum = null;
            Integer level = -1;
            Integer res = -1;
            if (WfnoOp.getErrors().size() == 0 && WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                usrLevelOp.getParamsMap().put("CldId", cld_id);
                usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                usrLevelOp.getParamsMap().put("usr_id", usr_id);
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.getParamsMap().put("QuotAnaDocId", 18510);
                usrLevelOp.execute();

                if (usrLevelOp.getErrors().size() == 0 && usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level >= 0) {

                    System.out.println("sloc_id " + sloc_id + "cld_id   " + cld_id + "pOrgId   " + pOrgId + "WfNum " +
                                       WfNum + "poDocId " + quotAnaEvalId + "usr_idFrm  " + usr_id + "usr_idTo   " +
                                       usr_id + "levelFrm " + level + "levelTo  " + level + "action  " + action +
                                       "remark  " + remark);
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                    insTxnOp.getParamsMap().put("PO_DOC_NO", 18510);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
                    insTxnOp.getParamsMap().put("poDocId", quotAnaEvalId);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    am.getDBTransaction().commit();
                    System.out.println(level + "level--res" + res);
                    return "goToWf";
                } else {
//                    Something went wrong with user level in Workflow.Contact to ESS!
                    String s=ADFModelUtils.resolvRsrc("MSG.2403");
                    FacesMessage message2 =
                        new FacesMessage(s);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    return null;
                }
            } else {
//                Workflow not Define for this Organisation.
                String s=ADFModelUtils.resolvRsrc("MSG.2401");
                FacesMessage message2 = new FacesMessage();
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        } else { //pending != usr_id
            quotationAnaAMImpl am = (quotationAnaAMImpl) resolvElDC("quotationAnaAMDataControl");
            Row[] r = am.getLovUsrId1().getFilteredRows("UsrId", pending);
            String name = "Anonymous";
            if (r.length > 0)
                if (r[0].getAttribute("UsrName") != null)
                    name = (String) r[0].getAttribute("UsrName");
//            "This Quotation Analysis is pending at
            String s1=ADFModelUtils.resolvRsrc("MSG.2404");
//            for approval, You can not forward this."
            String s2=ADFModelUtils.resolvRsrc("MSG.2405");
            String msg2 = s1 + name + s2 ;
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }

    }

    public void searchBtn(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchbyRfq").execute();
    }

    public void resetBtn(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetquotAna").execute();
    }

    public void setAnaRsltTabBind(RichTable anaRsltTabBind) {
        this.anaRsltTabBind = anaRsltTabBind;
    }

    public RichTable getAnaRsltTabBind() {
        return anaRsltTabBind;
    }

    public void editRmrkAL(ActionEvent actionEvent) {
      this.setEditRmkMode(false); 
      AdfFacesContext.getCurrentInstance().addPartialTarget(quotRstTabBind);
    }

    public void saveRmkAL(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Commit").execute();
        this.setEditRmkMode(true); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotRstTabBind);
        
    }
}
