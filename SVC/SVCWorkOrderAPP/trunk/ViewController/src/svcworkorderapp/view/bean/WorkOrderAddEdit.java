package svcworkorderapp.view.bean;

//import com.tangosol.net.Service;
import adfmailinterface.view.bean.ServiceInterface;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.util.ArrayList;

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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import svcworkorderapp.model.services.SVCWorkOrderAMImpl;

public class WorkOrderAddEdit {
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(WorkOrderAddEdit.class);
    private RichPopup taxPopup;
    private RichInputListOfValues bindTcktVar;
    private RichInputText itemPriceBind;
    private RichInputText itemQtyBind;
    private RichSelectBooleanCheckbox taxAftDisFlg;
    private RichSelectOneRadio discType;
    private RichInputText discValueBind;
    private RichInputText custNmBind;
    private String f1 = "D";
    private RichInputListOfValues transCustNm;
    private RichSelectOneChoice bindReqAreaVar;
    private RichInputListOfValues bindempnm;
    private RichTable tablebind;
    private RichInputListOfValues transCoaNm;
    private RichSelectOneChoice ocDescBind;
    private RichInputText ocAmtBind;
    private RichPopup ocPopBind;
    private RichInputText ocAMtBind;
    private RichTable ocTableBind;
    private RichPopup popLotBind;
    private RichPopup binPopupBind;
    private RichPopup srPopupBind;
    private UIXSwitcher afsitcherBind;
    private RichInputText srFlgBind;

    Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    String BinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}").toString();
    private RichTable lotTblBind;
    private RichTable binTblBind;
    private RichTable srTblBind;
    private RichInputText binAvlStkBind;
    private RichInputText lotAvlStkBind;
    private RichInputText binTtLStkbind;
    private RichInputText lotTtlStkBind;
    private RichPopup returnLotPopBind;
    private RichPopup returnPopBinBind;
    private RichPopup returnPopSrBind;
    private RichInputText rtrnItmBinBind;
    private RichInputText rtrnItmLotStkBind;
    private RichSelectOneChoice docStatBind;
    private RichSelectOneChoice itmUomBind;
    private RichPopup srViewPop;

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF1() {
        return f1;
    }

    public WorkOrderAddEdit() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public SVCWorkOrderAMImpl getAm() {
        return (SVCWorkOrderAMImpl) resolvElDC("SVCWorkOrderAMDataControl");
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


    public String PopulateItemAction() {
        //Add event code here...
        //  System.out.println("Ticket Number:" + bindTcktVar.getValue().toString());
        if (bindTcktVar.getValue() != null) {
            //  System.out.println("in the if loop");
            OperationBinding operationBinding = getBindings().getOperationBinding("populateDataSrc");
            operationBinding.getParamsMap().put("tcktnm", bindTcktVar.toString());
            operationBinding.execute();
        } else {
            showFacesMessage("Enter the Ticket Number.", "E", false, "F", null);
        }
        return null;


    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    // System.out.println(bindVars[z] + "z");
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

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void setTaxPopup(RichPopup taxPopup) {
        this.taxPopup = taxPopup;
    }

    public RichPopup getTaxPopup() {
        return taxPopup;
    }

    public String selecttaxAction() {
        // Add event code here...
        //        OperationBinding operationBinding = getBindings().getOperationBinding("ApplyTax");
        //        operationBinding.execute();

        OperationBinding istaxApply = getBindings().getOperationBinding("isTaxApplicable");
        istaxApply.execute();
        String exmtdflag = null;
        if (istaxApply.getErrors().isEmpty()) {
            exmtdflag = istaxApply.getResult().toString();
        }
        //   adfLog.info("tax exempted flag value is "+exmtdflag);
        if ("Y".equalsIgnoreCase(exmtdflag)) {
            FacesMessage message2 = new FacesMessage("Tax is not Applicable on this item");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            // OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxPresent");
            // chkTaxOp.execute();
            //  if("Y".equalsIgnoreCase(chkTaxOp.getResult().toString())){

            OperationBinding opr = getBindings().getOperationBinding("ApplyTax");
            opr.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(totAmtBind);
            //  Integer reply = (Integer)opr.getResult();
            //  showPopup(itmTaxPopUp, true);

            // }else{
            //  OperationBinding crTaxOp = getBindings().getOperationBinding("CreateWithParams");
            //  crTaxOp.execute();
            //taxPopBind
        }
        // Add event code here...


        return null;
    }

    public void setBindTcktVar(RichInputListOfValues bindTcktVar) {
        this.bindTcktVar = bindTcktVar;
    }

    public RichInputListOfValues getBindTcktVar() {
        return bindTcktVar;
    }

    public String createItmAction() {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        operationBinding.execute();
        return null;
    }

    public String saveAction() {
        // Add event code here...
        /*    Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();


        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        obCommit.execute();
        showFacesMessage("Work Estimate Save Successfully ", "I", false, "F", null);
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode","V");
      */return null;
    }

    public void setItemPriceBind(RichInputText itemPriceBind) {
        this.itemPriceBind = itemPriceBind;
    }

    public RichInputText getItemPriceBind() {
        return itemPriceBind;
    }

    public void setItemQtyBind(RichInputText itemQtyBind) {
        this.itemQtyBind = itemQtyBind;
    }

    public RichInputText getItemQtyBind() {
        return itemQtyBind;
    }


    public void CustNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("in the value change listener");
        String suppname = valueChangeEvent.getNewValue().toString();
        System.out.println(valueChangeEvent.getNewValue());
        Row[] suprows = getAm().getLovEoId2().getFilteredRows("EoNm", suppname);
        System.out.println(suprows.length);
        if (suprows.length > 0) {
            System.out.println("in the if loop");
            System.out.println(suprows.length);
            Integer currId = (Integer) suprows[0].getAttribute("CurrIdSp");
            Number currConv = (Number) suprows[0].getAttribute("ConvFctr");
            System.out.println("CurrId" + currId + "currConv" + currConv);
            Row currrow = getAm().getSvcCmWo1().getCurrentRow();
            currrow.setAttribute("CurrIdSp", currId);
            currrow.setAttribute("CurrConvFctr", currConv);
        }
    }

    public void setTaxAftDisFlg(RichSelectBooleanCheckbox taxAftDisFlg) {
        this.taxAftDisFlg = taxAftDisFlg;
    }

    public RichSelectBooleanCheckbox getTaxAftDisFlg() {
        return taxAftDisFlg;
    }

    public void setDiscType(RichSelectOneRadio discType) {
        this.discType = discType;
    }

    public RichSelectOneRadio getDiscType() {
        return discType;
    }

    public void setDiscValueBind(RichInputText discValueBind) {
        this.discValueBind = discValueBind;
    }

    public RichInputText getDiscValueBind() {
        return discValueBind;
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void ItmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number ob = (Number) object;
            Number zero = new Number(0);
            if (ob.compareTo(zero) != 1)
                showFacesMessage("Item Price must be greater than zero.", "E", false, "V", null);
        } else {
            showFacesMessage("Item Price is required", "E", false, "V", null);
            // ADFModelUtils.showFacesMessage("Item Price must be greater than zero.", null,FacesMessage.SEVERITY_ERROR  , null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tablebind);
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number ob = (Number) object;
            Number zero = new Number(0);
            if (ob.compareTo(zero) != 1)
                showFacesMessage("Item Quantity must be greater than zero.", "E", false, "V", null);
        } else {
            showFacesMessage("Item Quantity is required", "E", false, "V", null);
        }
        //Item Price must be greater than zero.
    }

    public void DiscValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number ob = (Number) object;
            Number zero = new Number(0);
            if (ob.compareTo(zero) != 1)
                showFacesMessage("Discount Value must be greater than zero.", "E", false, "V", null);
        }
    }

    public void TcktNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("in the TcktNmVCL ");
        System.out.println(valueChangeEvent.getNewValue());
        System.out.println(transCustNm.getValue());
        if (valueChangeEvent.getNewValue() != null && transCustNm.getValue() == null) {
            System.out.println("in the if loop");
            String Tcktnm = valueChangeEvent.getNewValue().toString();
            System.out.println(Tcktnm);
            OperationBinding operationBinding = getBindings().getOperationBinding("CustomerCheck");
            operationBinding.getParamsMap().put("TcktNm", Tcktnm);
            operationBinding.execute();
            Object result = operationBinding.getResult();
            System.out.println("----" + result);
            if (result != null && !result.equals("no")) {
                this.setF1("E");
                //custNmBind.setValue(result);

            }

        }
    }

    public void setCustNmBind(RichInputText custNmBind) {
        this.custNmBind = custNmBind;
    }

    public RichInputText getCustNmBind() {
        return custNmBind;
    }

    public String ResetTaxAction() {
        // Add event code here...

        return null;
    }

    public void DiscTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding operationBinding = getBindings().getOperationBinding("setDiscVal");
            operationBinding.execute();
        }
    }

    public void setTransCustNm(RichInputListOfValues transCustNm) {
        this.transCustNm = transCustNm;
    }

    public RichInputListOfValues getTransCustNm() {
        return transCustNm;
    }

    public void TaxAfterDiscVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        adfLog.info(" in the VCL of TaxAfterDisc");
        String value = valueChangeEvent.getNewValue().toString();
        OperationBinding operationBinding = getBindings().getOperationBinding("UpdateTaxableAmt");
        operationBinding.getParamsMap().put("value", value);
        operationBinding.execute();
    }

    public String deleteACTION() {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
        operationBinding.execute();
        return null;
    }

    public String AsgnAction() {
        // Add event code here...
        if (bindReqAreaVar.getValue() != null && bindReqAreaVar.getValue().toString().length() > 0) {
            if (bindempnm.getValue() != null && bindempnm.getValue().toString().length() > 0) {
                OperationBinding op1 = getBindings().getOperationBinding("AsgnDuplicate");
                op1.execute();
                adfLog.info("duplicate data outptut is " + op1.getResult());
                if ("Y".equalsIgnoreCase(op1.getResult().toString())) {
                    OperationBinding operationBinding = getBindings().getOperationBinding("createAsgn");
                    operationBinding.getParamsMap().put("ReqArea", null);
                    operationBinding.getParamsMap().put("EmpNm", null);
                    operationBinding.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindempnm);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindReqAreaVar);
                } else {
                    showFacesMessage("Duplicate Record Found ", "E", false, "F", bindempnm.getClientId());
                    // return null;
                }

            } else {
                showFacesMessage("Employee Name is required ", "E", false, "F", bindempnm.getClientId());
            }
        } else {
            showFacesMessage("Requirement Area is required ", "E", false, "F", bindReqAreaVar.getClientId());
        }
        return null;
    }

    public void setBindReqAreaVar(RichSelectOneChoice bindReqAreaVar) {
        this.bindReqAreaVar = bindReqAreaVar;
    }

    public RichSelectOneChoice getBindReqAreaVar() {
        return bindReqAreaVar;
    }

    public void setBindempnm(RichInputListOfValues bindempnm) {
        this.bindempnm = bindempnm;
    }

    public RichInputListOfValues getBindempnm() {
        return bindempnm;
    }

    public void ItmNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        adfLog.info("item name validator is " + object);
        if (object != null) {
            OperationBinding op = getBindings().getOperationBinding("itmnmValidator");
            op.getParamsMap().put("nm", object);
            op.execute();
            adfLog.info("reuslr:" + op.getResult());
            if (op.getResult() != null && op.getResult().equals("e")) {
                showFacesMessage("Duplicate Item", "E", false, "V", null);
            }

        }
    }

    public void editActionListener(ActionEvent actionEvent) {
        // Add event code here...
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23003);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This WorkOrder is Pending at [" + name + "]. You can't Edit this Work Order.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public String saveAndFwdAction() {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23003);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {

            OperationBinding chkvalidOp = getBindings().getOperationBinding("chkValidOutput");
            chkvalidOp.execute();
            String chkvalid = null;
            adfLog.info("document valid output " + chkvalidOp.getResult());
            if (chkvalidOp.getErrors().isEmpty()) {
                chkvalid = chkvalidOp.getResult().toString();
            }
            if ("Y".equalsIgnoreCase(chkvalid)) {

            } else if ("N".equalsIgnoreCase(chkvalid)) {
                //showFacesMessage("Not a valid document you can not forward it", "E", false, null, null);
                showFacesMessage("Not a valid document you can not forward it", "E", false, "F", null);

                return null;
            }

            String tableNm = "SVC$CM$WO";
            String tableNmFTkt = "SVC$CM_TKT";
            //if ("A".equalsIgnoreCase(mode)) {
            Integer fyNo = 0;
            OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
            fyIdOp.getParamsMap().put("CldId", cld_Id);
            fyIdOp.getParamsMap().put("OrgId", org_Id);
            //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
            fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
            fyIdOp.getParamsMap().put("Mode", "A");
            fyIdOp.execute();
            if (fyIdOp.getResult() != null) {
                fyNo = Integer.parseInt(fyIdOp.getResult().toString());
            }
            OperationBinding callNoOp = getBindings().getOperationBinding("genCallNo");
            callNoOp.getParamsMap().put("SlocId", sloc_Id);
            callNoOp.getParamsMap().put("CldId", cld_Id);
            callNoOp.getParamsMap().put("HoOrgId", HoOrgId);
            callNoOp.getParamsMap().put("OrgId", org_Id);
            callNoOp.getParamsMap().put("TableName", tableNm);
            callNoOp.getParamsMap().put("fyId", fyNo);
            callNoOp.execute();
            String ids = null;
            if (callNoOp.getResult() != null) {
                ids = callNoOp.getResult().toString();
                System.out.println("new generated issue id " + ids);
            }
            //   OperationBinding geteo = getBindings().getOperationBinding("generateScNo");
            //geteo.execute();
            String action = "I";
            String remark = "A";
            String WfNum = null;
            Integer level = 0;
            Integer res = -1;
            Integer pending = 0;


            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", sloc_Id);
            WfnoOp.getParamsMap().put("CldId", cld_Id);
            WfnoOp.getParamsMap().put("OrgId", org_Id);
            WfnoOp.getParamsMap().put("DocNo", 23003);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                if (WfnoOp.getResult() != null)
                    WfNum = WfnoOp.getResult().toString();
                System.out.println("WfnoOp : " + WfNum);
            }

            if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                //get user level
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                usrLevelOp.getParamsMap().put("CldId", cld_Id);
                usrLevelOp.getParamsMap().put("OrgId", org_Id);
                usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 23003);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                    System.out.println("user level " + level);
                }

                if (level >= 0) {
                    //insert into txn
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                    insTxnOp.getParamsMap().put("CldId", cld_Id);
                    insTxnOp.getParamsMap().put("OrgId", org_Id);
                    insTxnOp.getParamsMap().put("DocNo", 23003);
                    insTxnOp.getParamsMap().put("WfNo", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    //     adflog.info("now the errors in txn is"+insTxnOp.getErrors());
                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        System.out.println("ins ito txn" + res);
                    }


                    //Check Pending
                    OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                    chkUsr.getParamsMap().put("SlocId", sloc_Id);
                    chkUsr.getParamsMap().put("CldId", cld_Id);
                    chkUsr.getParamsMap().put("OrgId", org_Id);
                    chkUsr.getParamsMap().put("DocNo", 23003);
                    chkUsr.execute();

                    if (chkUsr.getResult() != null) {
                        pending = Integer.parseInt(chkUsr.getResult().toString());
                        System.out.println("pending check" + pending);
                    }
                    if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                        OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                        saveOp.execute();
                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                        //showFacesMessage("MRN No "+ids+" Save Successfully", "I", false, "F", null);
                        //no pending
                        return "towf";
                    }
                    /*  else {//pending
                                                         return null;
                                                         } */
                } else {
                    showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E", false,
                                     "F", null);
                    return null;
                }
            } else {
                showFacesMessage("Workflow not Created for Work Order", "E", false, "F", null);
                return null;
            }

        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Work Order is Pending at [" + name + "]. You can't forward this Work Order.";


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        return null;

    }

    public void saveActionListner(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
        resettax.execute();
        String istxble = null;
        if (resettax.getErrors().isEmpty()) {
            istxble = resettax.getResult().toString();
            adfLog.info("txble check result " + istxble);
        }
        if (docStatBind.getValue() != null && docStatBind.getValue().toString().length() > 0) {
            Integer docState = (Integer) docStatBind.getValue();

            if (docState.equals(84)) {

                OperationBinding chkStock = getBindings().getOperationBinding("chkTotAvailableStock");
                chkStock.execute();
                String stkFlag = null;
                if (chkStock.getErrors().isEmpty()) {
                    stkFlag = chkStock.getResult().toString();
                    adfLog.info("stkbl check result " + stkFlag);

                    if ("Y".equalsIgnoreCase(stkFlag)) {
                        adfLog.info("stock is good " + stkFlag);
                    } else {
                        adfLog.info("not available stock for these items " + stkFlag);
                        return;
                    }
                } else {
                    adfLog.info("get errors in total available stock " + stkFlag);
                }

            }
        }


        if ("Y".equalsIgnoreCase(istxble)) {
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            //  String tableNm = "SVC$SC";//saveFwdActn1

            OperationBinding wrkcomp = getBindings().getOperationBinding("workOrderComplete");
            wrkcomp.execute();
            String tableNm = "SVC$CM$WO";
            String tableNmFTkt = "SVC$CM_TKT";
            //if ("A".equalsIgnoreCase(mode)) {
            Integer fyNo = 0;
            OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
            fyIdOp.getParamsMap().put("CldId", p_cldId);
            fyIdOp.getParamsMap().put("OrgId", p_org_id);
            //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
            fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
            fyIdOp.getParamsMap().put("Mode", "A");
            fyIdOp.execute();
            if (fyIdOp.getResult() != null) {
                fyNo = Integer.parseInt(fyIdOp.getResult().toString());
            }
            OperationBinding callNoOp = getBindings().getOperationBinding("genCallNo");
            callNoOp.getParamsMap().put("SlocId", p_sloc_id);
            callNoOp.getParamsMap().put("CldId", p_cldId);
            callNoOp.getParamsMap().put("HoOrgId", p_hoOrgId);
            callNoOp.getParamsMap().put("OrgId", p_org_id);
            callNoOp.getParamsMap().put("TableName", tableNm);
            callNoOp.getParamsMap().put("fyId", fyNo);
            callNoOp.execute();
            String ids = null;
            if (callNoOp.getResult() != null) {
                ids = callNoOp.getResult().toString();
                System.out.println("new generated issue id " + ids);
            }
            String action = "I";
            String remark = "A";
            String WfNum = null;
            Integer level = 0;
            Integer res = -1;
            //int amount=0;
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", p_sloc_id);
            WfnoOp.getParamsMap().put("CldId", p_cldId);
            WfnoOp.getParamsMap().put("OrgId", p_org_id);
            WfnoOp.getParamsMap().put("DocNo", 23003);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                if (WfnoOp.getResult() != null)
                    WfNum = WfnoOp.getResult().toString();
                System.out.println("WfNum is : " + WfNum);
            }

            if (WfNum != null) {
                //get user level
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", p_sloc_id);
                usrLevelOp.getParamsMap().put("CldId", p_cldId);
                usrLevelOp.getParamsMap().put("OrgId", p_org_id);
                usrLevelOp.getParamsMap().put("UsrId", p_userId);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 23003);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                    System.out.println("user level" + level);
                }

                if (level >= 0) { //insert into txn
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", p_sloc_id);
                    insTxnOp.getParamsMap().put("CldId", p_cldId);
                    insTxnOp.getParamsMap().put("OrgId", p_org_id);
                    insTxnOp.getParamsMap().put("DocNo", 23003);
                    insTxnOp.getParamsMap().put("WfNo", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", p_userId);
                    insTxnOp.getParamsMap().put("usr_idTo", p_userId);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        System.out.println("txn save output" + res);
                    }
                    /*    OperationBinding updtstk = getBindings().getOperationBinding("updtStock");
                        updtstk.execute(); */

                    OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                    saveOp.execute();
                    RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                    //setMode("V");
                    showFacesMessage("Work Order Save Successfully", "I", false, "F", null);
                    //  showFacesMessage("Service Contract No "+scno+" Save Successfully", "I", false, "F", null);
                } else {
                    showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E", false,
                                     "F", null);

                }
            } else {
                showFacesMessage("Workflow is not created for Work Order.", "E", false, "F", null);

            }
        }

    }

    public void pendingForCstmrApprvl(ActionEvent actionEvent) {

        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23003);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            OperationBinding saveOp = getBindings().getOperationBinding("pendingFrCstmrAprvl");
            saveOp.execute();
            String temp = null;
            if (saveOp.getErrors().isEmpty()) {
                temp = saveOp.getResult().toString();
            }
            if ("N".equalsIgnoreCase(temp)) {
                showFacesMessage("Work order not approved(Document can't sent for customer approval)", "E", false, "F",
                                 null);
            } else if ("Y".equalsIgnoreCase(temp)) {

                OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                obCommit.execute();
                /*  ServiceInterface bean = (ServiceInterface) resolveExpression("#{pageFlowScope.ServiceInterface}");
                adfLog.info("bean "+bean);
                bean.showPop(actionEvent); */
                OperationBinding op = this.getBindings().getOperationBinding("getTo");
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    ArrayList<String> to = (ArrayList<String>) op.getResult();
                    this.setTo(to);
                    this.setSubject("WorkOrder");
                    this.setMessag("Work Order has been approved");
                    ServiceInterface bean = (ServiceInterface) resolveExpression("#{pageFlowScope.ServiceInterface}");
                    //System.out.println("bean "+bean);
                    bean.showPop(actionEvent);
                }

                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                AdfFacesContext.getCurrentInstance().addPartialTarget(docStatBind);
            }
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This WorkOrder is Pending at [" + name + "]. You can't Edit this Work Order.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public void approveByCstmr(ActionEvent actionEvent) {
        //approveByCustomer
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23003);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            OperationBinding saveOp = getBindings().getOperationBinding("approveByCustomer");
            saveOp.execute();
            String temp = null;
            if (saveOp.getErrors().isEmpty()) {
                temp = saveOp.getResult().toString();
            }
            if ("N".equalsIgnoreCase(temp)) {
                showFacesMessage("This document not pending for customer approval", "E", false, "F", null);
            } else if ("Y".equalsIgnoreCase(temp)) {

                OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                obCommit.execute();
                // docStatBind
                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                AdfFacesContext.getCurrentInstance().addPartialTarget(docStatBind);

            }
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This WorkOrder is Pending at [" + name + "]. You can't Edit this Work Order.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


    }

    public void setTablebind(RichTable tablebind) {
        this.tablebind = tablebind;
    }

    public RichTable getTablebind() {
        return tablebind;
    }

    public void discvalueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Amount must be Greater than 0", "E", false, "V", null);
            }
            if (value.compareTo(new Number(0)) == 1) {
                OperationBinding opr = getBindings().getOperationBinding("discAmtValidator");
                opr.getParamsMap().put("discAmt", value);
                opr.execute();

                String temp = null;
                if (opr.getErrors().isEmpty()) {
                    temp = opr.getResult().toString();
                } else {
                    adfLog.info("disc amount errors is " + opr.getErrors());
                }

                if (temp.equalsIgnoreCase("N")) {
                    showFacesMessage("Discount Amount must be less than Total Amount", "E", false, "V", null);
                } else if (temp.equalsIgnoreCase("P")) {
                    showFacesMessage("Discount Amount must be less than 100", "E", false, "V", null);

                }
                adfLog.info("discAmount return value is " + temp);
            }


        }

    }

    public void OcDeleteActionListner(ActionEvent actionEvent) {
        OperationBinding opr = getBindings().getOperationBinding("ocRemove");
        opr.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
    }

    public String OcAddAction() {
        OperationBinding obindDupl = getBindings().getOperationBinding("dplctOcCheck");
        obindDupl.execute();

        String flag = null;
        if (obindDupl.getErrors().isEmpty()) {
            flag = obindDupl.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(flag)) {
            OperationBinding opr = getBindings().getOperationBinding("addOtherCharges");
            opr.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(transCoaNm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ocDescBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ocAMtBind);
        } else if ("N".equalsIgnoreCase(flag)) {
            showFacesMessage("Duplicate COA Found", "E", false, "F", transCoaNm.getClientId());
            return null;
        }
        return null;
    }

    public void setTransCoaNm(RichInputListOfValues transCoaNm) {
        this.transCoaNm = transCoaNm;
    }

    public RichInputListOfValues getTransCoaNm() {
        return transCoaNm;
    }

    public void setOcDescBind(RichSelectOneChoice ocDescBind) {
        this.ocDescBind = ocDescBind;
    }

    public RichSelectOneChoice getOcDescBind() {
        return ocDescBind;
    }

    public void setOcAmtBind(RichInputText ocAmtBind) {
        this.ocAmtBind = ocAmtBind;
    }

    public RichInputText getOcAmtBind() {
        return ocAmtBind;
    }

    public void setOcPopBind(RichPopup ocPopBind) {
        this.ocPopBind = ocPopBind;
    }

    public RichPopup getOcPopBind() {
        return ocPopBind;
    }

    public void seleOcAction(ActionEvent actionEvent) {
        showPopup(ocPopBind, true);
    }

    public void setOcAMtBind(RichInputText ocAMtBind) {
        this.ocAMtBind = ocAMtBind;
    }

    public RichInputText getOcAMtBind() {
        return ocAMtBind;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void OcAmauntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Amount must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void OcDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding operationBinding = getBindings().getOperationBinding("setOcTotalAmount");
            operationBinding.execute();
        }

    }

    public void rqmtAreaVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        /*  if(valueChangeEvent.getOldValue()!=null){
            OperationBinding operationBinding =getBindings().getOperationBinding("setRqmtAreaId");
            operationBinding.execute();
        } */
    }

    public void lotDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void setPopLotBind(RichPopup popLotBind) {
        this.popLotBind = popLotBind;
    }

    public RichPopup getPopLotBind() {
        return popLotBind;
    }

    public void manualActionListener(ActionEvent actionEvent) {
        String srflg = null;
        OperationBinding filterData = getBindings().getOperationBinding("filterStk");
        filterData.execute();
        //setLotTransQuantity
        /*  OperationBinding setLotQty=getBindings().getOperationBinding("setLotTransQuantity");
        setLotQty.execute();

        OperationBinding obindsrQty = getBindings().getOperationBinding("setsrQuantity");
        obindsrQty.execute();
        */
        // adfLog.info("srialized flag  "+srFlgBind.getValue()+" and bin chk value is "+BinChk);
        if (srFlgBind.getValue() != null && srFlgBind.getValue().toString().length() > 0) {

            srflg = srFlgBind.getValue().toString();
            if ("Y".equalsIgnoreCase(srflg)) {
                showPopup(srPopupBind, true);

            } else if ("N".equalsIgnoreCase(srflg) && "Y".equalsIgnoreCase(BinChk)) {
                showPopup(binPopupBind, true);

            } else if ("N".equalsIgnoreCase(srflg) && "N".equalsIgnoreCase(BinChk)) {
                showPopup(popLotBind, true);
            }
        }
    }

    public String poplotOkActionListener() {
        /*    OperationBinding chkQty = getBindings().getOperationBinding("chkTotalQuantity");
        chkQty.getParamsMap().put("type", "L");
        chkQty.execute();
        String flag = null;
        if (chkQty.getErrors().isEmpty()) {
            flag = chkQty.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(flag)) {
            OperationBinding insrtlot = getBindings().getOperationBinding("lotOkInsert");
            insrtlot.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBind);
            popLotBind.hide();
        } else if ("N".equalsIgnoreCase(flag)) {
            showFacesMessage("Quantity must be equals to item Quantity", "E", false, "F", lotTtlStkBind.getClientId());
        } */
        return null;
    }

    public String PopLotCnclActnListener() {
        //  chkTotalQuantity

        popLotBind.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBind);

        return null;
    }

    public void popOkBinActionListener(ActionEvent actionEvent) {
        /*   OperationBinding chkQty = getBindings().getOperationBinding("chkTotalQuantity");
        chkQty.getParamsMap().put("type", "B");
        chkQty.execute();
        String flag = null;
        if (chkQty.getErrors().isEmpty()) {
            flag = chkQty.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(flag)) {
       OperationBinding binOk=getBindings().getOperationBinding("binOkInsert");
       binOk.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTblBind);
        binPopupBind.hide();
        } else if ("N".equalsIgnoreCase(flag)) {
            showFacesMessage("Quantity must be equals to Item Quantity", "E", false, "F", binTtLStkbind.getClientId());
        } */
    }

    public void popBinCnclActionListener(ActionEvent actionEvent) {
        binPopupBind.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(binTblBind);
    }

    public void setBinPopupBind(RichPopup binPopupBind) {
        this.binPopupBind = binPopupBind;
    }

    public RichPopup getBinPopupBind() {
        return binPopupBind;
    }

    public void okSrActionListener(ActionEvent actionEvent) {
        /* OperationBinding oprBind = getBindings().getOperationBinding("insrtSrNo");
        oprBind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(srFlgBind);
        srPopupBind.hide(); */
    }

    public void cnclSrActionListener(ActionEvent actionEvent) {
        srPopupBind.hide();

        AdfFacesContext.getCurrentInstance().addPartialTarget(srFlgBind);
    }

    public void setSrPopupBind(RichPopup srPopupBind) {
        this.srPopupBind = srPopupBind;
    }

    public RichPopup getSrPopupBind() {
        return srPopupBind;
    }

    public void SrNoSrchActionListener(ActionEvent actionEvent) {
        OperationBinding filterData = getBindings().getOperationBinding("filterStk");
        filterData.execute();
    }

    public void bindSrchActionListener(ActionEvent actionEvent) {
        OperationBinding filterData = getBindings().getOperationBinding("filterStk");
        filterData.execute();
    }

    public void lotSrchActionListener(ActionEvent actionEvent) {
        OperationBinding filterData = getBindings().getOperationBinding("filterStk");
        filterData.execute();
    }

    public void setAfsitcherBind(UIXSwitcher afsitcherBind) {
        this.afsitcherBind = afsitcherBind;
    }

    public UIXSwitcher getAfsitcherBind() {
        return afsitcherBind;
    }

    public void setSrFlgBind(RichInputText srFlgBind) {
        this.srFlgBind = srFlgBind;
    }

    public RichInputText getSrFlgBind() {
        return srFlgBind;
    }

    public void setLotTblBind(RichTable lotTblBind) {
        this.lotTblBind = lotTblBind;
    }

    public RichTable getLotTblBind() {
        return lotTblBind;
    }

    public void setBinTblBind(RichTable binTblBind) {
        this.binTblBind = binTblBind;
    }

    public RichTable getBinTblBind() {
        return binTblBind;
    }

    public void setSrTblBind(RichTable srTblBind) {
        this.srTblBind = srTblBind;
    }

    public RichTable getSrTblBind() {
        return srTblBind;
    }

    public void srDeleteActionListener(ActionEvent actionEvent) {
        /**delete two is use to delete item ->lot cascading delete action
        delete one is use to delete only serialized item**/
        OperationBinding filterData = getBindings().getOperationBinding("Delete1");
        filterData.execute();
        OperationBinding pstObind = getBindings().getOperationBinding("Commit");
        pstObind.execute();
        //  OperationBinding pstObind=getBindings().getOperationBinding("postChanges");
        // pstObind.execute();
    }

    public void setBinAvlStkBind(RichInputText binAvlStkBind) {
        this.binAvlStkBind = binAvlStkBind;
    }

    public RichInputText getBinAvlStkBind() {
        return binAvlStkBind;
    }

    public void TotBinQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Number zero = new Number(0);
            Number obj = (Number) object;
            if (obj.compareTo(zero) == -1) {
                showFacesMessage("Quantity must be Greater than zero", "E", false, "V", null);
                // showFacesMessage("Discount Value cannot be zero", "E", false, "F", discValueBind.getClientId());
            }
            Boolean flag = isPrecisionValid(26, 6, obj);
            if (flag.equals(false)) {
                showFacesMessage("Invalid Precision scale (26,6)", "E", false, "V", null);
            }
            if (binAvlStkBind.getValue() != null && binAvlStkBind.getValue().toString().length() > 0) {
                Number avlStk = (Number) binAvlStkBind.getValue();
                adfLog.info(obj + "  avalilable stk " + avlStk + " comparison is " + obj.compareTo(avlStk));
                if (obj.compareTo(avlStk) == 1) {
                    showFacesMessage("Quantity must be less than or equals to Available Quantity", "E", false, "V",
                                     null);

                }
            }


        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void LotTtlStkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number zero = new Number(0);
        if (object != null) {
            Number obj = (Number) object;
            if (obj.compareTo(zero) == -1) {
                showFacesMessage("Quantity must be Greater than zero", "E", false, "V", null);
                // showFacesMessage("Discount Value cannot be zero", "E", false, "F", discValueBind.getClientId());
            }
            Boolean flag = isPrecisionValid(26, 6, obj);
            if (flag.equals(false)) {
                showFacesMessage("Invalid Precision scale (26,6)", "E", false, "V", null);
            }
            if (lotAvlStkBind.getValue() != null && lotAvlStkBind.getValue().toString().length() > 0) {
                Number avlStk = (Number) lotAvlStkBind.getValue();
                adfLog.info(obj + "  avalilable stk " + avlStk + " comparison is " + obj.compareTo(avlStk));
                if (obj.compareTo(avlStk) == 0 && obj.compareTo(avlStk) == -1) {

                } else if (obj.compareTo(avlStk) == 1) {
                    showFacesMessage("Quantity must be less than or equals to Available Quantity", "E", false, "V",
                                     null);
                }
            }


        }

    }

    public void setLotAvlStkBind(RichInputText lotAvlStkBind) {
        this.lotAvlStkBind = lotAvlStkBind;
    }

    public RichInputText getLotAvlStkBind() {
        return lotAvlStkBind;
    }

    public void setBinTtLStkbind(RichInputText binTtLStkbind) {
        this.binTtLStkbind = binTtLStkbind;
    }

    public RichInputText getBinTtLStkbind() {
        return binTtLStkbind;
    }

    public void setLotTtlStkBind(RichInputText lotTtlStkBind) {
        this.lotTtlStkBind = lotTtlStkBind;
    }

    public RichInputText getLotTtlStkBind() {
        return lotTtlStkBind;
    }

    public void lotpopDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            OperationBinding chkQty = getBindings().getOperationBinding("chkTotalQuantity");
            chkQty.getParamsMap().put("type", "L");
            chkQty.execute();
            Number flag = null;
            Number issuQty = null;
            if (chkQty.getErrors().isEmpty()) {
                flag = (Number) chkQty.getResult();
            }

            if (getLotQty() != null)
                issuQty = getLotQty();
            if (flag != null && issuQty != null && issuQty.compareTo(flag) == 0) {
                /* OperationBinding insrtlot = getBindings().getOperationBinding("lotOkInsert");
            insrtlot.execute();
             */

                OperationBinding insrtlot = getBindings().getOperationBinding("lotInsrtAction");
                insrtlot.execute();
                OperationBinding obind = getBindings().getOperationBinding("Commit");
                obind.execute();
                OperationBinding obind1 = getBindings().getOperationBinding("Execute2");
                obind1.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBind);
                //popLotBind.hide();
            } else {
                showFacesMessage("Quantity must be equals to item Quantity", "E", false, "F",
                                 lotTtlStkBind.getClientId());
                return;
            }
        }
    }

    public void popbinDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding chkQty = getBindings().getOperationBinding("chkTotalQuantity");
            chkQty.getParamsMap().put("type", "B");
            chkQty.execute();
            Number flag = null;
            Number issuQty = null;
            if (chkQty.getErrors().isEmpty()) {
                flag = (Number) chkQty.getResult();
            }

            if (getbinQty() != null)
                issuQty = getbinQty();
            if (flag != null && issuQty != null && issuQty.compareTo(flag) == 0) {
                //  OperationBinding binOk = getBindings().getOperationBinding("binOkInsert");
                //binOk.execute();
                OperationBinding insrtlot = getBindings().getOperationBinding("binInsrtAction");
                insrtlot.execute();
                OperationBinding obind = getBindings().getOperationBinding("Commit");
                obind.execute();
                OperationBinding obind1 = getBindings().getOperationBinding("Execute1");
                obind1.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(binTblBind);
                //binPopupBind.hide();
            } else {
                showFacesMessage("Quantity must be equals to Item Quantity", "E", false, "F", null);
                return; //binTtLStkbind.getClientId()
            }
        }

    }

    public Number getIssuQty() {
        Number zero = new Number(0);
        Number issQtyBal = zero;
        if (itemQtyBind.getValue() != null && itemQtyBind.getValue().toString().length() > 0) {
            Number issuQty = (Number) itemQtyBind.getValue();
            Integer tableCount = srTblBind.getRowCount();
            Number tableValue = zero;

            try {
                tableValue = new Number(tableCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            adfLog.info("issuQty------  " + issuQty + "tableCount-----  " + tableCount + "tableValue----  " +
                        tableValue);
            if (tableValue != null) {
                issQtyBal = (Number) issuQty.minus(tableValue);
            }
        }
        return issQtyBal;
    }

    //binTblBind
    public Number getbinQty() {
        Number zero = new Number(0);
        Number issQtyBal = zero;
        if (itemQtyBind.getValue() != null && itemQtyBind.getValue().toString().length() > 0) {
            Number issuQty = (Number) itemQtyBind.getValue();
            Integer tableCount = binTblBind.getRowCount();

            Number sum = new Number(0);

            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding empIter = bindings.findIteratorBinding("SvcCmWoBin1Iterator");
            RowSetIterator empRSIter = empIter.getViewObject().createRowSetIterator(null);
            while (empRSIter.hasNext()) {
                Row r = empRSIter.next();
                Number Qty = (Number) r.getAttribute("ItmQty");
                // System.out.println(curr.getAttribute("ItmQty"));

                sum = sum.add(Qty);
                //                adfLog.info(sum+"item quanity and sum is "+Qty);
            }

            //  adfLog.info("issuQty------  "+issuQty+"tableCount-----  "+tableCount+"tableValue----  "+sum);
            if (sum != null) {
                issQtyBal = (Number) issuQty.minus(sum);
            }
        }
        return issQtyBal;
    }

    public Number getLotQty() {
        Number zero = new Number(0);
        Number issQtyBal = zero;
        if (itemQtyBind.getValue() != null && itemQtyBind.getValue().toString().length() > 0) {
            Number issuQty = (Number) itemQtyBind.getValue();
            Integer tableCount = binTblBind.getRowCount();

            Number sum = new Number(0);

            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding empIter = bindings.findIteratorBinding("SvcCmWoLotIterator");
            RowSetIterator empRSIter = empIter.getViewObject().createRowSetIterator(null);
            while (empRSIter.hasNext()) {
                Row r = empRSIter.next();
                Number Qty = (Number) r.getAttribute("ItmQty");
                // System.out.println(curr.getAttribute("ItmQty"));

                sum = sum.add(Qty);
                //                adfLog.info(sum+"item quanity and sum is "+Qty);
            }

            //  adfLog.info("issuQty------  "+issuQty+"tableCount-----  "+tableCount+"tableValue----  "+sum);
            if (sum != null) {
                issQtyBal = (Number) issuQty.minus(sum);
            }
        }
        return issQtyBal;
    }

    public void srDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            OperationBinding srChkBind = getBindings().getOperationBinding("chkSerialTotal");
            srChkBind.execute();

            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            Number issQtyBal = getIssuQty();
            if (srChkBind.getErrors().isEmpty()) {
                retTotVal = (Number) srChkBind.getResult();
            }
            adfLog.info("inside popup -- condotion zero" + retTotVal);
            if ((retTotVal.compareTo(issQtyBal) > 0) || (retTotVal.compareTo(issQtyBal) < 0)) { // check conditon more or less quantity issue
                adfLog.info("inside popup -- condition mis match");
                showFacesMessage("Insert Issue Quantity not equals required Quantity", "E", false, "F", null);
                return;

            } else if (retTotVal.compareTo(issQtyBal) == 0) { // if Issue quantity and required quantity same

                adfLog.info("if Issue quantity and required quantity same");
                OperationBinding insrtlot = getBindings().getOperationBinding("srInsrtAction");
                insrtlot.execute();
                OperationBinding obind = getBindings().getOperationBinding("Commit");
                obind.execute();
                OperationBinding obind1 = getBindings().getOperationBinding("Execute");
                obind1.execute();
                //    OperationBinding oprBind = getBindings().getOperationBinding("insrtSrNo");
                //  oprBind.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(srFlgBind);
            }

        }
    }

    public void autoIssueActionListener(ActionEvent actionEvent) {
        OperationBinding autoissue = getBindings().getOperationBinding("autoIssue");
        autoissue.execute();
        OperationBinding obind = getBindings().getOperationBinding("Commit");
        obind.execute();
        OperationBinding obind3 = getBindings().getOperationBinding("Execute");
        obind3.execute();
        OperationBinding obind1 = getBindings().getOperationBinding("Execute1");
        obind1.execute();
        OperationBinding obind2 = getBindings().getOperationBinding("Execute2");
        obind2.execute();

    }

    public void returnStkActionListener(ActionEvent actionEvent) {
        // showPopup(returnLotPopBind, true);
        String srflg = null;
        OperationBinding filterData = getBindings().getOperationBinding("filterStk");
        filterData.execute();
        //setLotTransQuantity
        // OperationBinding setLotQty=getBindings().getOperationBinding("setLotTransQuantity");
        //setLotQty.execute();

        // OperationBinding obindsrQty = getBindings().getOperationBinding("setsrQuantity");
        //obindsrQty.execute();

        // adfLog.info("srialized flag  "+srFlgBind.getValue()+" and bin chk value is "+BinChk);
        if (srFlgBind.getValue() != null && srFlgBind.getValue().toString().length() > 0) {

            srflg = srFlgBind.getValue().toString();
            if ("Y".equalsIgnoreCase(srflg)) {
                showPopup(returnPopSrBind, true);

            } else if ("N".equalsIgnoreCase(srflg) && "Y".equalsIgnoreCase(BinChk)) {
                showPopup(returnPopBinBind, true);

            } else if ("N".equalsIgnoreCase(srflg) && "N".equalsIgnoreCase(BinChk)) {
                showPopup(returnLotPopBind, true);
            }
        }
    }

    public void setReturnLotPopBind(RichPopup returnLotPopBind) {
        this.returnLotPopBind = returnLotPopBind;
    }

    public RichPopup getReturnLotPopBind() {
        return returnLotPopBind;
    }

    public void setReturnPopBinBind(RichPopup returnPopBinBind) {
        this.returnPopBinBind = returnPopBinBind;
    }

    public RichPopup getReturnPopBinBind() {
        return returnPopBinBind;
    }

    public void returnBinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding srChkBind = getBindings().getOperationBinding("binRtrnAction");
            srChkBind.execute();
            OperationBinding obind = getBindings().getOperationBinding("Commit");
            obind.execute();
            OperationBinding obind1 = getBindings().getOperationBinding("Execute1");
            obind1.execute();
        }
    }

    public void returnLotDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding srChkBind = getBindings().getOperationBinding("lotReturnStk");
            srChkBind.execute();
            OperationBinding obind = getBindings().getOperationBinding("Commit");
            obind.execute();
            OperationBinding obind1 = getBindings().getOperationBinding("Execute2");
            obind1.execute();


        }
    }

    public void returnSrDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding srChkBind = getBindings().getOperationBinding("srRtrnAction");
            srChkBind.execute();
            OperationBinding obind = getBindings().getOperationBinding("Commit");
            obind.execute();
            OperationBinding obind1 = getBindings().getOperationBinding("Execute");
            obind1.execute();
        }
    }

    public void setReturnPopSrBind(RichPopup returnPopSrBind) {
        this.returnPopSrBind = returnPopSrBind;
    }

    public RichPopup getReturnPopSrBind() {
        return returnPopSrBind;
    }

    public void setRtrnItmBinBind(RichInputText rtrnItmBinBind) {
        this.rtrnItmBinBind = rtrnItmBinBind;
    }

    public RichInputText getRtrnItmBinBind() {
        return rtrnItmBinBind;
    }

    public void RtrnBinStkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number zero = new Number(0);
        if (object != null) {
            Number obj = (Number) object;
            if (obj.compareTo(zero) == -1) {
                showFacesMessage("Quantity must be Greater than zero", "E", false, "V", null);
                // showFacesMessage("Discount Value cannot be zero", "E", false, "F", discValueBind.getClientId());
            }
            Boolean flag = isPrecisionValid(26, 6, obj);
            if (flag.equals(false)) {
                showFacesMessage("Invalid Precision scale (26,6)", "E", false, "V", null);
            }
            if (rtrnItmBinBind.getValue() != null && rtrnItmBinBind.getValue().toString().length() > 0) {
                Number avlStk = (Number) rtrnItmBinBind.getValue();
                adfLog.info(obj + "  avalilable stk " + avlStk + " comparison is " + obj.compareTo(avlStk));
                if (obj.compareTo(avlStk) == 0 && obj.compareTo(avlStk) == -1) {

                } else if (obj.compareTo(avlStk) == 1) {
                    showFacesMessage("Quantity must be less than or equals to Available Quantity", "E", false, "V",
                                     null);
                }
            }


        }
    }

    public void setRtrnItmLotStkBind(RichInputText rtrnItmLotStkBind) {
        this.rtrnItmLotStkBind = rtrnItmLotStkBind;
    }

    public RichInputText getRtrnItmLotStkBind() {
        return rtrnItmLotStkBind;
    }

    public void RtrnLotTtlStkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number zero = new Number(0);
        if (object != null) {
            Number obj = (Number) object;
            if (obj.compareTo(zero) == -1) {
                showFacesMessage("Quantity must be Greater than zero", "E", false, "V", null);
                // showFacesMessage("Discount Value cannot be zero", "E", false, "F", discValueBind.getClientId());
            }
            Boolean flag = isPrecisionValid(26, 6, obj);
            if (flag.equals(false)) {
                showFacesMessage("Invalid Precision scale (26,6)", "E", false, "V", null);
            }
            if (getRtrnItmLotStkBind().getValue() != null &&
                getRtrnItmLotStkBind().getValue().toString().length() > 0) {
                Number avlStk = (Number) rtrnItmLotStkBind.getValue();
                adfLog.info(obj + "  avalilable stk " + avlStk + " comparison is " + obj.compareTo(avlStk));
                if (obj.compareTo(avlStk) == 0 && obj.compareTo(avlStk) == -1) {

                } else if (obj.compareTo(avlStk) == 1) {
                    showFacesMessage("Quantity must be less than or equals to Available Quantity", "E", false, "V",
                                     null);
                }
            }


        }
    }

    public void setDocStatBind(RichSelectOneChoice docStatBind) {
        this.docStatBind = docStatBind;
    }

    public RichSelectOneChoice getDocStatBind() {
        return docStatBind;
    }


    private ArrayList<String> to = new ArrayList<String>();

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setMessag(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    private ArrayList<String> cc = new ArrayList<String>();
    private ArrayList<String> bcc = new ArrayList<String>();
    private String subject = null;
    private String message = null;


    public Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setItmUomBind(RichSelectOneChoice itmUomBind) {
        this.itmUomBind = itmUomBind;
    }

    public RichSelectOneChoice getItmUomBind() {
        return itmUomBind;
    }

    public void TransItmVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tablebind);
    }

    public void viewSerialNoAction(ActionEvent actionEvent) {
       OperationBinding op = this.getBindings().getOperationBinding("serializedItemView");
       op.execute();
       adfLog.info("executed---serialized-view---");
       showPopup(srViewPop, true);
    }

    public void setSrViewPop(RichPopup srViewPop) {
        this.srViewPop = srViewPop;
    }

    public RichPopup getSrViewPop() {
        return srViewPop;
    }
}
