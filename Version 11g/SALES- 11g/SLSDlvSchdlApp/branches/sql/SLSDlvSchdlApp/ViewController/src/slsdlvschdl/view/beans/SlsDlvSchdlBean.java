package slsdlvschdl.view.beans;


import java.sql.SQLException;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;


public class SlsDlvSchdlBean {
    private RichInputListOfValues eoNameVar;
    private RichInputListOfValues docIdDispVar;
    private RichSelectOneChoice ordStatusVar;
    private RichSelectOneChoice ordTypeVar;
    private RichInputListOfValues usrNmVar;
    private RichInputDate dateFromVar;
    private RichInputDate dateToVar;
    private RichInputText amtFromVar;
    private RichInputText amtToVar;
    private RichSelectOneChoice whidBind;
    private RichInputText balanceQtyValue;
    private static ADFLogger _log = ADFLogger.createADFLogger(SlsDlvSchdlBean.class);
    private String amdFlg = "N";
    private Integer amd_No;
    private String app_mode = "V";
    private RichInputDate deliveryDatePgBind;
    private RichSelectOneChoice itemIdPgBind;
    private RichInputDate orderDatePgBind;
    private String itm_mode = "V";

    private oracle.jbo.domain.Number Amt;
    private StringBuffer WfIdForDlvSchdl;
    private StringBuffer doc_txn_id;
    private String actn = "edit"; // Normal Edit State
    private String status = "N";
    private RichSelectOneChoice orgidsrcPopUpBind;
    private RichSelectOneChoice orgIdDescPopUpBind;
    private RichSelectOneChoice whIdSrcPopUpBind;
    private RichSelectOneChoice whIdDescPopUpBind;
    private RichInputText avlQtyPopUpBind;
    private RichSelectOneChoice itemIdPopUpBind;
    private RichPopup popUpBind;

    private RichOutputText orderTypePgBinding;
    private Boolean addbtn = false; // For Popup add button for visable property
    private RichCommandImageLink addBtnPopUpPgBind;
    private RichInputText itmTransferQtyPgBind; // For Popup add button to enable or disable
    private Boolean poperrormsg = false;
    private RichMessage popUpErrorMsgPgBind;
    private RichTable popUpTablePgBinding;
    private RichInputText validUptoPgBind;
    private Boolean assignRetVal = false; // For Transfer Order precision Chk
    private RichTable allOrderTablePgBind;
    private RichTable rateContractTablePgBind;
    private String showerrormsgonPopup = null;
    private RichInputText schdlQtyTransPgBind;
    private RichSelectBooleanCheckbox allOrderCBPgBind;
    private RichCommandImageLink saveButtonPgBind;
    private RichCommandImageLink saveAsButtonPgBind;
    private RichTable itemTablePgBind;
    private RichInputText itmAmtSpPgBind;
    private RichInputText itemAmtBsPgBind;
    private RichCommandImageLink addItmPgBind;
    private RichCommandButton amendButtonPgBind;
    private RichInputListOfValues itemNamePgBind;
    private RichCommandImageLink deletePgBind;
    private RichSelectOneChoice namePgBind;
    private RichPanelBox completeBoxPgBind;
    private RichInputText itmPriceBind;
    private RichSelectOneChoice contractBasisPgBind;
    private RichOutputText itemSrNoPgBind;
    private RichInputText itemQtyPgBind;
    private RichPanelGroupLayout itemPopUpPgBind;
    private String chkerror = "no";


    public SlsDlvSchdlBean() {
    }

    public void setEoNameVar(RichInputListOfValues eoNameVar) {
        this.eoNameVar = eoNameVar;
    }

    public RichInputListOfValues getEoNameVar() {
        return eoNameVar;
    }

    public void setDocIdDispVar(RichInputListOfValues docIdDispVar) {
        this.docIdDispVar = docIdDispVar;
    }

    public RichInputListOfValues getDocIdDispVar() {
        return docIdDispVar;
    }

    public void setOrdStatusVar(RichSelectOneChoice ordStatusVar) {
        this.ordStatusVar = ordStatusVar;
    }

    public RichSelectOneChoice getOrdStatusVar() {
        return ordStatusVar;
    }

    public void setOrdTypeVar(RichSelectOneChoice ordTypeVar) {
        this.ordTypeVar = ordTypeVar;
    }

    public RichSelectOneChoice getOrdTypeVar() {
        return ordTypeVar;
    }

    public void setUsrNmVar(RichInputListOfValues usrNmVar) {
        this.usrNmVar = usrNmVar;
    }

    public RichInputListOfValues getUsrNmVar() {
        return usrNmVar;
    }

    public void setDateFromVar(RichInputDate dateFromVar) {
        this.dateFromVar = dateFromVar;
    }

    public RichInputDate getDateFromVar() {
        return dateFromVar;
    }

    public void setDateToVar(RichInputDate dateToVar) {
        this.dateToVar = dateToVar;
    }

    public RichInputDate getDateToVar() {
        return dateToVar;
    }

    public void setAmtFromVar(RichInputText amtFromVar) {
        this.amtFromVar = amtFromVar;
    }

    public RichInputText getAmtFromVar() {
        return amtFromVar;
    }

    public void setAmtToVar(RichInputText amtToVar) {
        this.amtToVar = amtToVar;
    }

    public RichInputText getAmtToVar() {
        return amtToVar;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    /**
     * @param actionEvent
     */
    public void searchButton(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("searchDeliveryScheduleView");
        operationBinding.execute();
    }

    /**
     * @param actionEvent
     */
    public void resetButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("resetDeliveryScheduleView");
        operationBinding.execute();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    public void saveDeliveryButton(ActionEvent actionEvent) {

        /*  BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkCheckBoxValue");
        operationBinding.execute();

        Boolean val = (Boolean)operationBinding.getResult(); */

        if (true) {

            /* operationBinding = bindings.getOperationBinding("chkMandatoryFieldsEntered");
            operationBinding.execute();
            System.out.println("From Bean "+operationBinding.getResult()); */

            // System.out.println("Inside loop_______________");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("saveDeliverySchedule");
            operationBinding.execute();

            status = "A"; //Amendment State

            if (operationBinding.getResult() != null) {
                String retval = operationBinding.getResult().toString();

                // System.out.println("return value is " + retval);

                if (retval.equalsIgnoreCase("D")) {
                    //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.375']}").toString(), null));

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.375']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (retval.equalsIgnoreCase("W")) {

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.411']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (retval.equalsIgnoreCase("S")) {
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.413']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }


            } else {
                /* operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute(); */
            }
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.252']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

       // AdfFacesContext.getCurrentInstance().addPartialTarget(deletePgBind);
    }

    public void setWhidBind(RichSelectOneChoice whidBind) {
        this.whidBind = whidBind;
    }

    public RichSelectOneChoice getWhidBind() {
        return whidBind;
    }

    public void deleteButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();

        /*  operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute(); */

        actn = "delete"; //Action changed to delete state


        operationBinding = bindings.getOperationBinding("executeBalanceVw");
        operationBinding.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(rateContractTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(allOrderTablePgBind);
    }


    public void selectAllButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("selectAllCheckBox");

        operationBinding.getParamsMap().put("val", true);

        operationBinding.execute();

    }

    public void deSelectAllButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("selectAllCheckBox");

        operationBinding.getParamsMap().put("val", false);

        operationBinding.execute();
    }

    public void balanceQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /*  BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("chkMandatoryFieldsEntered");
            operationBinding.execute();
            System.out.println("Reseul is "+operationBinding.getResult()); */


            oracle.jbo.domain.Number qty = (oracle.jbo.domain.Number)object;

            if (qty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            if (qty.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            }

            oracle.jbo.domain.Number maxvalue = (oracle.jbo.domain.Number)getBalanceQtyValue().getValue();
            if (qty.compareTo(maxvalue) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.964']}").toString(),
                                                              null)); //Value Should be less then or equal to the remaining Quantity
            }

            if (!isPrecisionValid(15, 2, qty)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.56']}").toString(),
                                                              null)); //Invalid Precision(15,2)
            }

        }
    }


    public void setBalanceQtyValue(RichInputText balanceQtyValue) {
        this.balanceQtyValue = balanceQtyValue;
    }

    public RichInputText getBalanceQtyValue() {
        return balanceQtyValue;
    }

    /**
     * Method to resolve expression.
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    /**Method to amend Sales Order
     * @param actionEvent
     */

    public void amendOrderAction(ActionEvent actionEvent) {

        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

        Integer pending = 0;

        //Check Pending
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_Id);
        chkUsr.getParamsMap().put("CldId", cld_Id);
        chkUsr.getParamsMap().put("OrgId", org_Id);
        chkUsr.getParamsMap().put("DocNo", 21503);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        _log.info("Current User-" + usr_Id + "and Order Pending At-" + pending);
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
            app_mode = "E";

            this.amdFlg = "Y";
            OperationBinding ob = executeOperation("getCurrentAmdNo");
            ob.execute();
            if (ob.getResult() != null) {
                amd_No = Integer.parseInt(ob.getResult().toString());
            }

            deliveryDatePgBind.setValue(new Timestamp(System.currentTimeMillis()));

        } else {

            OperationBinding uNm = executeOperation("getUserName");
            uNm.getParamsMap().put("uid", pending);
            uNm.execute();
            if (uNm.getResult() != null) {

                String msg2 =
                    "<html><body> <p>" + resolvElDCMsg("#{bundle['MSG.824']}").toString() + "<b><i> [ " + uNm.getResult().toString() +
                    " ] </i></b>" + resolvElDCMsg("#{bundle['MSG.1085']}").toString() +
                    "</p></body></html>"; //MSG.824  , MSG.824
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }

        }


    }


    public void saveOrderAction(ActionEvent actionEvent) {

        DCBindingContainer dcbind = (DCBindingContainer)getBindings();

        if (dcbind.getDataControl().isTransactionModified()) {
            if (amdFlg.equalsIgnoreCase("Y") && status.equalsIgnoreCase("A")) {

                _log.info("Amendment Method called");
                OperationBinding setAmd = executeOperation("setAmndmntDtl");
                setAmd.getParamsMap().put("amdNo", amd_No + 1);
                setAmd.execute();


                status = "N";
            }

            if (actn.equalsIgnoreCase("edit")) {

                WfIdForDlvSchdl = null;
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("getWfIdAttachedWithTheDoc");
                operationBinding.execute();

                WfIdForDlvSchdl = (StringBuffer)operationBinding.getResult();

                System.out.println("WF_ID FOR SI : " + WfIdForDlvSchdl);

                //Get Doc_Txn_Id
                // doc_txn_id = (StringBuffer)this.getBindings().getOperationBinding("doctxnid").execute();

                operationBinding = bindings.getOperationBinding("doctxnid");
                operationBinding.execute();
                doc_txn_id = (StringBuffer)operationBinding.getResult();

                System.out.println("Doctxnid in bean is :" + doc_txn_id);

                if (WfIdForDlvSchdl != null) {

                    //To get the level of current user forward to
                    Integer usrLvl = -3;
                    OperationBinding binding1 = this.getBindings().getOperationBinding("getUsrLvl");
                    binding1.getParamsMap().put("WfId", new StringBuffer(WfIdForDlvSchdl));
                    usrLvl = (Integer)binding1.execute();
                    System.out.println("THE CURRENT USER LEVEL IS :" + usrLvl);

                    // Insert line into wfTxn
                    Integer result = -4;
                    OperationBinding insTxn = this.getBindings().getOperationBinding("insIntoTxn");
                    insTxn.getParamsMap().put("WfId", new StringBuffer(WfIdForDlvSchdl));
                    insTxn.getParamsMap().put("level", usrLvl);
                    result = (Integer)insTxn.execute();
                    System.out.println("INS TXN line inserted : " + result);

                    actn = "edit";

                }
            }

            executeOperation("Commit").execute();

            // SaveAndForwardAction();

            this.app_mode = "V";
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.970']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setApp_mode(String app_mode) {
        this.app_mode = app_mode;
    }

    public String getApp_mode() {
        return app_mode;
    }

    public String backToSearchAction() {
        OperationBinding operationBinding = getBindings().getOperationBinding("resetSoView");
        operationBinding.execute();
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Rollback");
        operationBinding1.execute();
        this.app_mode = "V";
        return "Back";
    }


    public void AmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = (oracle.jbo.domain.Number)object;
        if (amt.compareTo(0) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
        }

        /* if (amtToVar.getValue()!=null && amtFromVar.getValue() != null) {
            oracle.jbo.domain.Number amtfrom = (oracle.jbo.domain.Number)amtFromVar.getValue();
            if (amt.compareTo(amtfrom) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.832']}").toString(), null));


            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(amtToVar);
        } */
    }


    public void setDeliveryDatePgBind(RichInputDate deliveryDatePgBind) {
        this.deliveryDatePgBind = deliveryDatePgBind;
    }

    public RichInputDate getDeliveryDatePgBind() {
        return deliveryDatePgBind;
    }

    public void setItemIdPgBind(RichSelectOneChoice itemIdPgBind) {
        this.itemIdPgBind = itemIdPgBind;
    }

    public RichSelectOneChoice getItemIdPgBind() {
        return itemIdPgBind;
    }

    public void ToAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amtto = (oracle.jbo.domain.Number)object;

        if (amtToVar.getValue() != null && amtFromVar.getValue() != null) {
            oracle.jbo.domain.Number amtfrom = (oracle.jbo.domain.Number)amtFromVar.getValue();
            if (amtto.compareTo(amtfrom) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.832']}").toString(), null));


            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(amtToVar);
        }
    }

    public void AmountFromVce(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            amtToVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(amtToVar);
        }
    }

    public void RateContractValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = (oracle.jbo.domain.Number)object;
        if (amt.compareTo(0) == -1 || amt.compareTo(0) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.730']}").toString(), null));
        }
    }


    public void setOrderDatePgBind(RichInputDate orderDatePgBind) {
        this.orderDatePgBind = orderDatePgBind;
    }

    public RichInputDate getOrderDatePgBind() {
        return orderDatePgBind;
    }


    public void deliveryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // System.out.println("Object value is " + object);
            // System.out.println("Order Date is :" + orderDatePgBind.getValue());

            Timestamp ts1;
            Timestamp ts2;
            Timestamp ts3;
            oracle.jbo.domain.Number days;

            ts1 = (Timestamp)orderDatePgBind.getValue();
            ts2 = (Timestamp)object;


            /*  if (ts1 != null && ts2 != null) {
                Integer no = 0;
                try {
                    no = (ts2.dateValue().compareTo(ts1.dateValue()));
                    // System.out.println("No is  using compare to:" + no);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                long ndays = ((ts2.getTime() - ts1.getTime()) / 86400000);
                days = new oracle.jbo.domain.Number(ndays);
                // System.out.println("Days using time sub: " + ndays);
                if (ndays < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1012']}").toString(),
                                                                  null));
                }
            } */


            try {
                //System.out.println("ts2.dateValue().compareTo(ts1.dateValue()) " +ts2.dateValue().compareTo(ts1.dateValue()));
                //System.out.println("ts2.dateValue().equals(ts1.dateValue()) " +ts2.dateValue().equals(ts1.dateValue()));
                if (ts2.dateValue().compareTo(ts1.dateValue()) <= 0) {
                    if (ts2.dateValue().toString().equals(ts1.dateValue().toString())) {
                        //  System.out.println("Dates are same.");

                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1012']}").toString(),
                                                                      null));
                    }


                }
            } catch (SQLException e) {
            }
            if (Integer.parseInt(orderTypePgBinding.getValue().toString()) == 311) {
                ts3 = (Timestamp)validUptoPgBind.getValue();
                //System.out.println("++++++  " + ts2.compareTo(ts3));
                try {
                    if ((ts2.dateValue()).compareTo((ts3.dateValue())) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1117']}").toString(),
                                                                      null)); //"Effective Date must be less then Valid Upto Date...!!",
                    }
                } catch (SQLException e) {
                }
            }

        }
    }


    /**
     *Save and Forward Action
     * @return
     */

    public String SaveAndForwardAction() {

        /**
         * following code is copied from Save Button
        */

        if (amdFlg.equalsIgnoreCase("Y") && status.equalsIgnoreCase("A")) {

            _log.info("Amendment Method called");
            OperationBinding setAmd = executeOperation("setAmndmntDtl");
            setAmd.getParamsMap().put("amdNo", amd_No + 1);
            setAmd.execute();


            status = "N";
        }


        this.app_mode = "V";


        /**
     * Now Save And Forward will be implemented
     */

        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        StringBuffer cldId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        StringBuffer orgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        StringBuffer hoOrgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));

        OperationBinding binding = this.getBindings().getOperationBinding("TotalAmountForWarehouse");
        binding.execute();
        //System.out.println("Error on call is="+binding.getErrors());
        Amt = (oracle.jbo.domain.Number)binding.getResult();

        // System.out.println("Amount value is :" + Amt);

        Integer pendingAtUsr = 0;
        //pendingAtUsr = (Integer)this.getBindings().getOperationBinding("slsDlvPendingAt").execute();

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("slsDlvPendingAt");
        operationBinding.execute();

        pendingAtUsr = (Integer)operationBinding.getResult();

        System.out.println("Pending at User is :" + pendingAtUsr);
        System.out.println("User is :" + userId);

        //Check if the DocID is not being attached in WF. Or if pending at current user
        WfIdForDlvSchdl = null;
        if (pendingAtUsr.compareTo(new Integer(-1)) == 0 || pendingAtUsr.compareTo(userId) == 0) {
            //getWfIdAttachedWithTheDoc


            operationBinding = bindings.getOperationBinding("getWfIdAttachedWithTheDoc");
            operationBinding.execute();

            WfIdForDlvSchdl = (StringBuffer)operationBinding.getResult();

            System.out.println("WF_ID FOR SI : " + WfIdForDlvSchdl);

            //Get Doc_Txn_Id

            operationBinding = bindings.getOperationBinding("doctxnid");
            operationBinding.execute();
            doc_txn_id = (StringBuffer)operationBinding.getResult();

            System.out.println("Doctxnid in bean is :" + doc_txn_id);

            //To get the level of current user forward to
            Integer usrLvl = -3;
            OperationBinding binding1 = this.getBindings().getOperationBinding("getUsrLvl");
            binding1.getParamsMap().put("WfId", new StringBuffer(WfIdForDlvSchdl));
            usrLvl = (Integer)binding1.execute();
            System.out.println("THE CURRENT USER LEVEL IS :" + usrLvl);

            // Insert line into wfTxn
            Integer result = -4;
            OperationBinding insTxn = this.getBindings().getOperationBinding("insIntoTxn");
            insTxn.getParamsMap().put("WfId", new StringBuffer(WfIdForDlvSchdl));
            insTxn.getParamsMap().put("level", usrLvl);
            result = (Integer)insTxn.execute();
            System.out.println("INS TXN line inserted : " + result);


            // Commiting data
            OperationBinding execute = this.getBindings().getOperationBinding("Commit");
            execute.execute();

            if (execute.getErrors().isEmpty()) {
                return "goToWf";
            } else {
                String msg2 =
                    resolvElDCMsg("#{bundle['LBL.2911']}").toString() + "!."; //[LBL.2911 :Error in performing commit after insTxn in saveAndFOrward method
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                System.out.println("Error in performing commit after insTxn");

                return null;
            }
        } else {
            /*  String msg2 = "This Sales Invoice is pending at other user for approval. You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2); */
            return null;
        }


    }

    public void setAmt(oracle.jbo.domain.Number Amt) {
        this.Amt = Amt;
    }

    public oracle.jbo.domain.Number getAmt() {
        return Amt;
    }

    public void setWfIdForDlvSchdl(StringBuffer WfIdForDlvSchdl) {
        this.WfIdForDlvSchdl = WfIdForDlvSchdl;
    }

    public StringBuffer getWfIdForDlvSchdl() {
        return WfIdForDlvSchdl;
    }

    public void setDoc_txn_id(StringBuffer doc_txn_id) {
        this.doc_txn_id = doc_txn_id;
    }

    public StringBuffer getDoc_txn_id() {
        return doc_txn_id;
    }

    public void InsertNewRecordAL(ActionEvent actionEvent) {
        //System.out.println("Inside Create Button");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkRecordExistorNot");
        operationBinding.execute();

        Boolean existval = (Boolean)operationBinding.getResult();
        if (existval) {
            //System.out.println("in loop");
            if (orgidsrcPopUpBind.getValue() != null && orgIdDescPopUpBind.getValue() != null &&
                whIdSrcPopUpBind.getValue() != null && whIdDescPopUpBind.getValue() != null &&
                itmTransferQtyPgBind.getValue() != null) {
                //System.out.println("inside loooop");
                addbtn = true;
                popUpTablePgBinding.setRowSelection("none");
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                poperrormsg = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


                popUpTablePgBinding.setRowSelection("none");

                bindings = getBindings();
                operationBinding = bindings.getOperationBinding("CreateInsert");
                operationBinding.execute();


                operationBinding = bindings.getOperationBinding("InsertFyId");
                operationBinding.execute();


            } else {
                //System.out.println("in else loop");
                poperrormsg = true;
                if (orgidsrcPopUpBind.getValue() == null || orgIdDescPopUpBind.getValue() == null) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1118']}").toString()); //"Please Select Organisation...!!");
                } else if (whIdSrcPopUpBind.getValue() == null || whIdDescPopUpBind.getValue() == null) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.411']}").toString()); //"Please Select WareHouse...!!!");
                } else if (itmTransferQtyPgBind.getValue() == null && !assignRetVal) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1137']}").toString()); //"Please Enter Transfer Quantity...!!!");
                } else if (assignRetVal) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1136']}").toString()); //"Invalid Precision (26 , 6) For Transfer Order. Please Change Its Value");
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


            }
        } else {
            //System.out.println("else loop");

            addbtn = true;
            popUpTablePgBinding.setRowSelection("none");
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

            poperrormsg = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


            popUpTablePgBinding.setRowSelection("none");

            bindings = getBindings();
            operationBinding = bindings.getOperationBinding("CreateInsert");
            operationBinding.execute();


            operationBinding = bindings.getOperationBinding("InsertFyId");
            operationBinding.execute();


        }

        //System.out.println("outside");
    }

    public void setOrgidsrcPopUpBind(RichSelectOneChoice orgidsrcPopUpBind) {
        this.orgidsrcPopUpBind = orgidsrcPopUpBind;
    }

    public RichSelectOneChoice getOrgidsrcPopUpBind() {
        return orgidsrcPopUpBind;
    }

    public void setOrgIdDescPopUpBind(RichSelectOneChoice orgIdDescPopUpBind) {
        this.orgIdDescPopUpBind = orgIdDescPopUpBind;
    }

    public RichSelectOneChoice getOrgIdDescPopUpBind() {
        return orgIdDescPopUpBind;
    }

    public void setWhIdSrcPopUpBind(RichSelectOneChoice whIdSrcPopUpBind) {
        this.whIdSrcPopUpBind = whIdSrcPopUpBind;
    }

    public RichSelectOneChoice getWhIdSrcPopUpBind() {
        return whIdSrcPopUpBind;
    }

    public void setWhIdDescPopUpBind(RichSelectOneChoice whIdDescPopUpBind) {
        this.whIdDescPopUpBind = whIdDescPopUpBind;
    }

    public RichSelectOneChoice getWhIdDescPopUpBind() {
        return whIdDescPopUpBind;
    }

    public void setAvlQtyPopUpBind(RichInputText avlQtyPopUpBind) {
        this.avlQtyPopUpBind = avlQtyPopUpBind;
    }

    public RichInputText getAvlQtyPopUpBind() {
        return avlQtyPopUpBind;
    }

    public void setItemIdPopUpBind(RichSelectOneChoice itemIdPopUpBind) {
        this.itemIdPopUpBind = itemIdPopUpBind;
    }

    public RichSelectOneChoice getItemIdPopUpBind() {
        return itemIdPopUpBind;
    }

    public void OrgIdSrcVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            poperrormsg = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);
            //   orgIdDescPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgIdDescPopUpBind);
            //whIdSrcPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdSrcPopUpBind);
            // whIdDescPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);
        }
    }

    public void OrgIdDescVCL(ValueChangeEvent vce) {


        if (vce.getNewValue() != null && orgidsrcPopUpBind.getValue() != null) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

            poperrormsg = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);


        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1118']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(orgidsrcPopUpBind.getClientId(), message);

            orgIdDescPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgIdDescPopUpBind);
        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);
    }

    public void WhIdSrcVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && orgidsrcPopUpBind.getValue() != null) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

            poperrormsg = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

            whIdDescPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1118']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(orgidsrcPopUpBind.getClientId(), message);

            whIdSrcPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdSrcPopUpBind);
        }
    }

    public void WhIdDescVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && orgidsrcPopUpBind.getValue() != null) {
            if (orgIdDescPopUpBind.getValue() != null) {
                if (whIdSrcPopUpBind.getValue() != null) {

                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                    //System.out.println("*********** "+vce.getNewValue());

                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("setAvailableQtyOnItmTrf");
                    //System.out.println("------------->");
                    operationBinding.getParamsMap().put("whidvalue", vce.getNewValue());
                    //System.out.println("<-------------");
                    operationBinding.execute();
                    //System.out.println("_____"+operationBinding.getResult());
                    avlQtyPopUpBind.setValue(operationBinding.getResult());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(avlQtyPopUpBind);


                    poperrormsg = false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);

                } else {
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.411']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(whIdSrcPopUpBind.getClientId(), message);

                    whIdDescPopUpBind.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);

                    itmTransferQtyPgBind.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);
                }
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1118']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(orgIdDescPopUpBind.getClientId(), message);

                whIdDescPopUpBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);

                itmTransferQtyPgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);
            }
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1118']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(orgidsrcPopUpBind.getClientId(), message);

            whIdDescPopUpBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdDescPopUpBind);

            itmTransferQtyPgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);
        }
    }

    public void DeleteButtonPopUpAL(ActionEvent actionEvent) {

        this.itmTransferQtyPgBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
        operationBinding.execute();

        popUpTablePgBinding.setRowSelection("single");
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

        addbtn = false;


        poperrormsg = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

        assignRetVal = false;
        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("Execute");
        operationBinding.execute();

    }

    public void popupCancelListener(PopupCanceledEvent popupCanceledEvent) {

        addbtn = false;

        popUpTablePgBinding.setRowSelection("single");
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

        poperrormsg = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

        BindingContainer binding = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)binding.get("SearchSoVwIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
    }

    public void popUpOkButtonAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkRecordExistorNot");
        operationBinding.execute();

        Boolean existval = (Boolean)operationBinding.getResult();
        // System.out.println("------->" + assignRetVal);
        if (existval) {
            if (orgidsrcPopUpBind.getValue() != null && orgIdDescPopUpBind.getValue() != null &&
                whIdSrcPopUpBind.getValue() != null && whIdDescPopUpBind.getValue() != null &&
                itemIdPopUpBind.getValue() != null && itmTransferQtyPgBind.getValue() != null) {


                if (!this.assignRetVal) {

                    bindings = getBindings();
                    operationBinding = bindings.getOperationBinding("chkTrfOrderStatus");
                    operationBinding.execute();
                    if ((Boolean)operationBinding.getResult()) {


                        popUpTablePgBinding.setRowSelection("single");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                        addbtn = false;

                        poperrormsg = false;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


                        popUpBind.hide();
                    } else {
                        // poperrormsg = true;
                        // AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


                        /* FacesMessage message =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1135']}").toString()); //"Transfer Order was not done. Please Make details as Transfer Order or delete the item.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance(); */
                        poperrormsg = true;
                        setShowerrormsgonPopup(resolvElDCMsg("#{bundle['MSG.1135']}").toString());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);
                        //fc.addMessage(null, message);


                    }
                } else {
                    poperrormsg = true;
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1136']}").toString()); //"Invalid Precision for Transfer Order. Please change the value");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                    popUpTablePgBinding.setRowSelection("none");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


                    FacesMessage message = new FacesMessage(null);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    //fc.addMessage(null, message);

                }
            } else {

                poperrormsg = true;
                if (orgidsrcPopUpBind.getValue() == null || orgIdDescPopUpBind.getValue() == null) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1118']}").toString()); //"Please Select Organisation...!!");
                } else if (whIdSrcPopUpBind.getValue() == null || whIdDescPopUpBind.getValue() == null) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.411']}").toString()); //"Please Select WareHouse...!!!");
                } else if (itmTransferQtyPgBind.getValue() == null && !assignRetVal) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1137']}").toString()); //"Please Enter Transfer Quantity...!!!");
                } else if (assignRetVal) {
                    displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1136']}").toString()); //"Invalid Precision (26 , 6) For Transfer Order. Please Change Its Value");
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


                FacesMessage message = new FacesMessage(null);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                //fc.addMessage(null, message);

            }
        } else {

            popUpBind.hide();

        }
    }

    public void setPopUpBind(RichPopup popUpBind) {
        this.popUpBind = popUpBind;
    }

    public RichPopup getPopUpBind() {
        return popUpBind;
    }


    public void setOrderTypePgBinding(RichOutputText orderTypePgBinding) {
        this.orderTypePgBinding = orderTypePgBinding;
    }

    public RichOutputText getOrderTypePgBinding() {
        return orderTypePgBinding;
    }


    public void transferQuantityVCL(ValueChangeEvent vce) {
        //System.out.println("Vce :" + vce.getNewValue());
        //        System.out.println("Length :"+vce.getNewValue().toString().length());
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {

            //  System.out.println("in VCL");

            if (orgidsrcPopUpBind.getValue() != null && orgIdDescPopUpBind.getValue() != null &&
                whIdSrcPopUpBind.getValue() != null && whIdDescPopUpBind.getValue() != null) {


                // System.out.println("Inside If of VCL "+vce.getNewValue());
                OperationBinding operationBinding = getBindings().getOperationBinding("setTrfQtyValueFromBean");
                operationBinding.getParamsMap().put("val", vce.getNewValue());
                operationBinding.execute();


                popUpTablePgBinding.setRowSelection("single");
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);
                addbtn = false;
                poperrormsg = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);
                // System.out.println("End of If loop in VCL");
            } else {
                //System.out.println("------------");
                popUpTablePgBinding.setRowSelection("none");
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);


                addbtn = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);
            }
        } else {
            //  System.out.println("In else of VCL");

            itmTransferQtyPgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);

            popUpTablePgBinding.setRowSelection("none");
            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);


            addbtn = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);
        }
    }

    public void setAddbtn(Boolean addbtn) {
        this.addbtn = addbtn;
    }

    public Boolean getAddbtn() {
        return addbtn;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void transferQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // System.out.println("Inside Validator of If");
            oracle.jbo.domain.Number trfqty = (oracle.jbo.domain.Number)object;

            oracle.jbo.domain.Number avlqty = (oracle.jbo.domain.Number)(avlQtyPopUpBind.getValue());


            // System.out.println("Inside Validator of If");
            if (orgidsrcPopUpBind.getValue() != null && orgIdDescPopUpBind.getValue() != null &&
                whIdSrcPopUpBind.getValue() != null && whIdDescPopUpBind.getValue() != null) {

                //    System.out.println("Inside Condition");


                //System.out.println("trfqty.compareTo(avlqty) :" + trfqty.compareTo(avlqty));
                if (trfqty.compareTo(avlqty) >= 0) {
                    if (isPrecisionValid(26, 6, trfqty)) {

                    } else {
                        assignRetVal = true;

                        addbtn = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                        popUpTablePgBinding.setRowSelection("none");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);


                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                                      null)); //"Invalid Precision (26,6)
                    }
                } else if (trfqty.compareTo(0) < 0) {

                    assignRetVal = true;

                    addbtn = true;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                    popUpTablePgBinding.setRowSelection("none");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.653']}").toString(),
                                                                  null)); //Value must be greater than or equals zero!!

                } else {
                    assignRetVal = false;
                }


            } else {
                if (trfqty.compareTo(0) >= 0) {
                    if (isPrecisionValid(26, 6, trfqty)) {

                    } else {
                        assignRetVal = true;


                        addbtn = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                        popUpTablePgBinding.setRowSelection("none");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                        //System.out.println("Value is "+assignRetVal);

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                                      null)); //"Invalid Precision (26,6)
                    }
                }


                if (trfqty.compareTo(0) < 0) {

                    assignRetVal = true;


                    poperrormsg = true;
                    displayErrorMsgOnPoup("Transfer Order value should be Greater then or equal to zero...!!!");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                    addbtn = true;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                    popUpTablePgBinding.setRowSelection("none");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(popUpTablePgBinding);

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.653']}").toString(),
                                                                  null)); //"Value must be greater than or equals zero...!!!"

                }


            }
        }

    }

    public void setAddBtnPopUpPgBind(RichCommandImageLink addBtnPopUpPgBind) {
        this.addBtnPopUpPgBind = addBtnPopUpPgBind;
    }

    public RichCommandImageLink getAddBtnPopUpPgBind() {
        return addBtnPopUpPgBind;
    }

    public void setItmTransferQtyPgBind(RichInputText itmTransferQtyPgBind) {
        this.itmTransferQtyPgBind = itmTransferQtyPgBind;
    }

    public RichInputText getItmTransferQtyPgBind() {
        return itmTransferQtyPgBind;
    }

    public void setPoperrormsg(Boolean poperrormsg) {
        this.poperrormsg = poperrormsg;
    }

    public Boolean getPoperrormsg() {
        return poperrormsg;
    }

    public void setPopUpErrorMsgPgBind(RichMessage popUpErrorMsgPgBind) {
        this.popUpErrorMsgPgBind = popUpErrorMsgPgBind;
    }

    public RichMessage getPopUpErrorMsgPgBind() {
        return popUpErrorMsgPgBind;
    }

    public void setPopUpTablePgBinding(RichTable popUpTablePgBinding) {
        this.popUpTablePgBinding = popUpTablePgBinding;
    }

    public RichTable getPopUpTablePgBinding() {
        return popUpTablePgBinding;
    }


    public void setValidUptoPgBind(RichInputText validUptoPgBind) {
        this.validUptoPgBind = validUptoPgBind;
    }

    public RichInputText getValidUptoPgBind() {
        return validUptoPgBind;
    }

    /*  public void wareHouseSrcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("chkDuplicateWhIdSrc");
            operationBinding.getParamsMap().put("whidFromBean", object.toString());
            operationBinding.execute();

            Boolean val = (Boolean)operationBinding.getResult();

            assignRetVal = val;

            if (val) {
                addbtn = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1120']}").toString(),
                                                              null)); //"Duplicate WareHouse Found..!!"
            }
        }
    } */

    public void transferOrderAL(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTransferQtyPgBind);
        //System.out.println("+++++ "+itmTransferQtyPgBind.getValue());
        /* System.out.println("orgid :"+orgidsrcPopUpBind.getValue()+"  && "+orgIdDescPopUpBind.getValue() +" &&" +
         whIdSrcPopUpBind.getValue()+" &&"+ whIdDescPopUpBind.getValue() +" && " +
        itmTransferQtyPgBind.getValue() +" && "+!assignRetVal); */
        if (orgidsrcPopUpBind.getValue() != null && orgIdDescPopUpBind.getValue() != null &&
            whIdSrcPopUpBind.getValue() != null && whIdDescPopUpBind.getValue() != null &&
            itmTransferQtyPgBind.getValue() != null && !assignRetVal) {
            // System.out.println("Inside if loop");
            Number trfqty = (Number)itmTransferQtyPgBind.getValue();
            // System.out.println("Before condition "+trfqty.compareTo(0));
            if (trfqty.compareTo(0) >= 0) {

                poperrormsg = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);

                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("changeTrfStatusValue");
                operationBinding.execute();
            } else {

                // System.out.println("Inside if Else part");

                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.653']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itmTransferQtyPgBind.getClientId(), message);

            }
        } else {
            // System.out.println("Inside else part");

            /*  Number trfqty = (Number)itmTransferQtyPgBind.getValue();
            if (trfqty!=null && trfqty.compareTo(0) < 0) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.653']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itmTransferQtyPgBind.getClientId(), message);

            } */

            poperrormsg = true;
            if (orgidsrcPopUpBind.getValue() == null || orgIdDescPopUpBind.getValue() == null) {
                displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1118']}").toString()); //"Please Select Organisation...!!");
            } else if (whIdSrcPopUpBind.getValue() == null || whIdDescPopUpBind.getValue() == null) {
                displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.411']}").toString()); //"Please Select WareHouse...!!!");
            } else if (itmTransferQtyPgBind.getValue() == null && !assignRetVal) {
                displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1137']}").toString()); //"Please Enter Transfer Quantity...!!!");
            } else if (assignRetVal) {
                displayErrorMsgOnPoup(resolvElDCMsg("#{bundle['MSG.1136']}").toString()); //"Invalid Precision (26 , 6) For Transfer Order. Please Change Its Value");
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(popUpErrorMsgPgBind);


        }
    }

    public void RemoveTransferOrderAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("removeTrfStatusValue");
        operationBinding.execute();
    }

    public void wareHouseVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (Integer.parseInt(orderTypePgBinding.getValue().toString()) == 311) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(rateContractTablePgBind);
            } else {
                AdfFacesContext.getCurrentInstance().addPartialTarget(allOrderTablePgBind);
            }
        }
    }

    public void setAllOrderTablePgBind(RichTable allOrderTablePgBind) {
        this.allOrderTablePgBind = allOrderTablePgBind;
    }

    public RichTable getAllOrderTablePgBind() {
        return allOrderTablePgBind;
    }

    public void setRateContractTablePgBind(RichTable rateContractTablePgBind) {
        this.rateContractTablePgBind = rateContractTablePgBind;
    }

    public RichTable getRateContractTablePgBind() {
        return rateContractTablePgBind;
    }


    public void whIdDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("chkDuplicateWhIdSrc");
            operationBinding.getParamsMap().put("whidFromBean", object.toString());
            operationBinding.execute();

            Boolean val = (Boolean)operationBinding.getResult();

            assignRetVal = val;

            if (val) {
                addbtn = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnPopUpPgBind);

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1120']}").toString(),
                                                              null)); //"Duplicate WareHouse Found..!!"
            }
        }
    }

    /**
     * Method to Show error message on PopUp
     */
    public void displayErrorMsgOnPoup(String msg) {
        String message = null;

        StringBuilder Msg = new StringBuilder("<html><body><b><p style='color:red'>" + msg + "</p></b></body></html>");


        if (msg != null) {
            message = Msg.toString();
        } else {
            message = "Missing Input Values";
        }

        showerrormsgonPopup = message;

    }


    public void setShowerrormsgonPopup(String showerrormsgonPopup) {
        this.showerrormsgonPopup = showerrormsgonPopup;
    }

    public String getShowerrormsgonPopup() {
        return showerrormsgonPopup;
    }


    public void setSchdlQtyTransPgBind(RichInputText schdlQtyTransPgBind) {
        this.schdlQtyTransPgBind = schdlQtyTransPgBind;
    }

    public RichInputText getSchdlQtyTransPgBind() {
        return schdlQtyTransPgBind;
    }


    public void SchdlQtyTransferVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            allOrderCBPgBind.setValue(true);
        } else if (vce.getNewValue() == null || vce.getNewValue().toString().length() == 0) {
            allOrderCBPgBind.setValue(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(allOrderCBPgBind);
    }

    public void setAllOrderCBPgBind(RichSelectBooleanCheckbox allOrderCBPgBind) {
        this.allOrderCBPgBind = allOrderCBPgBind;
    }

    public RichSelectBooleanCheckbox getAllOrderCBPgBind() {
        return allOrderCBPgBind;
    }


    public void CreateItemAL(ActionEvent actionEvent) {
        this.setItm_mode("C");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkMandatoryEntered");
        operationBinding.execute();

        if (Integer.parseInt(operationBinding.getResult().toString()) == 0 && this.chkerror.equalsIgnoreCase("no")) {

            bindings = getBindings();
            operationBinding = bindings.getOperationBinding("CreateInsert1");
            operationBinding.execute();

            bindings = getBindings();
            operationBinding = bindings.getOperationBinding("setSrNo");
            operationBinding.getParamsMap().put("srNo", Integer.parseInt(itemSrNoPgBind.getValue().toString()));
            operationBinding.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPopUpPgBind);

        } else if (Integer.parseInt(operationBinding.getResult().toString()) == 1) {

            FacesMessage message = new FacesMessage("Please Select Item Name.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itemNamePgBind.getClientId(), message);

        } else if (Integer.parseInt(operationBinding.getResult().toString()) == 2) {

            FacesMessage message = new FacesMessage("Please enter quantity.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itemQtyPgBind.getClientId(), message);

        } else if (Integer.parseInt(operationBinding.getResult().toString()) == 3) {

            FacesMessage message = new FacesMessage("Price field cannot be empty.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else if (this.chkerror.equalsIgnoreCase("yes")) {

            FacesMessage message = new FacesMessage("Please check Customer Price policy .");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPopUpPgBind);
    }

    public void DeleteItemAL(ActionEvent actionEvent) {


        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(namePgBind);


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete2");
        operationBinding.execute();
        // this.setItm_mode("C");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(namePgBind);
    }

    public void ItemDetailsPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok") && this.chkerror.equalsIgnoreCase("no")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("chkMandatoryEntered");
            operationBinding.execute();


            if (Integer.parseInt(operationBinding.getResult().toString()) == 0) {

                this.app_mode = "E";
                System.out.println("Inside ok DL");

                operationBinding = bindings.getOperationBinding("executeBalanceVw");
                operationBinding.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveAsButtonPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(allOrderTablePgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(addItmPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(amendButtonPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(completeBoxPgBind);
            } else if (Integer.parseInt(operationBinding.getResult().toString()) == 1) {

                FacesMessage message = new FacesMessage("Please Select Item Name.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itemNamePgBind.getClientId(), message);

            } else if (Integer.parseInt(operationBinding.getResult().toString()) == 2) {

                FacesMessage message = new FacesMessage("Please enter quantity.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itemQtyPgBind.getClientId(), message);

            } else if (Integer.parseInt(operationBinding.getResult().toString()) == 3) {

                FacesMessage message = new FacesMessage("Price field cannot be empty.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }
        } else {
            FacesMessage message = new FacesMessage("Please check Customer Price policy .");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void ItemDetailsPopUpCL(PopupCanceledEvent popupCanceledEvent) {
        /* BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkMandatoryEntered");
        operationBinding.execute(); */

        //if (Integer.parseInt(operationBinding.getResult().toString()) != 0) {

        /* operationBinding = bindings.getOperationBinding("Delete2");
            operationBinding.execute(); */

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("revertOrremoveRowValues");
        operationBinding.execute();

        /* operationBinding = bindings.getOperationBinding("Execute1");
            operationBinding.execute(); */

        this.chkerror = "no";
        //}
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPopUpPgBind);
    }

    public void PriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            oracle.jbo.domain.Number price = (oracle.jbo.domain.Number)object;

            if (price.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            if (price.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            }


            if (!isPrecisionValid(26, 6, price)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                              null)); //Invalid Precision(26,6)
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);

        }

    }

    public void QuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            oracle.jbo.domain.Number qty = (oracle.jbo.domain.Number)object;

            if (qty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            if (qty.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            }


            if (!isPrecisionValid(26, 6, qty)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                              null)); //Invalid Precision(26,6)
            }


            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);

        }

    }

    public void setSaveButtonPgBind(RichCommandImageLink saveButtonPgBind) {
        this.saveButtonPgBind = saveButtonPgBind;
    }

    public RichCommandImageLink getSaveButtonPgBind() {
        return saveButtonPgBind;
    }

    public void setSaveAsButtonPgBind(RichCommandImageLink saveAsButtonPgBind) {
        this.saveAsButtonPgBind = saveAsButtonPgBind;
    }

    public RichCommandImageLink getSaveAsButtonPgBind() {
        return saveAsButtonPgBind;
    }

    public void setItemTablePgBind(RichTable itemTablePgBind) {
        this.itemTablePgBind = itemTablePgBind;
    }

    public RichTable getItemTablePgBind() {
        return itemTablePgBind;
    }

    public void setItmAmtSpPgBind(RichInputText itmAmtSpPgBind) {
        this.itmAmtSpPgBind = itmAmtSpPgBind;
    }

    public RichInputText getItmAmtSpPgBind() {
        return itmAmtSpPgBind;
    }

    public void setItemAmtBsPgBind(RichInputText itemAmtBsPgBind) {
        this.itemAmtBsPgBind = itemAmtBsPgBind;
    }

    public RichInputText getItemAmtBsPgBind() {
        return itemAmtBsPgBind;
    }

    public void ItemPriceVCL(ValueChangeEvent valueChangeEvent) {


        AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
    }

    public void ItemQtyVCL(ValueChangeEvent valueChangeEvent) {
        /*  BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Execute1");
        operationBinding.execute(); */

        // this.setItm_mode("V");

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(namePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
    }

    public void setAddItmPgBind(RichCommandImageLink addItmPgBind) {
        this.addItmPgBind = addItmPgBind;
    }

    public RichCommandImageLink getAddItmPgBind() {
        return addItmPgBind;
    }

    public void setAmendButtonPgBind(RichCommandButton amendButtonPgBind) {
        this.amendButtonPgBind = amendButtonPgBind;
    }

    public RichCommandButton getAmendButtonPgBind() {
        return amendButtonPgBind;
    }

    public void setItm_mode(String itm_mode) {
        this.itm_mode = itm_mode;
    }

    public String getItm_mode() {
        return itm_mode;
    }

    public void setItemNamePgBind(RichInputListOfValues itemNamePgBind) {
        this.itemNamePgBind = itemNamePgBind;
    }

    public RichInputListOfValues getItemNamePgBind() {
        return itemNamePgBind;
    }

    public void setDeletePgBind(RichCommandImageLink deletePgBind) {
        this.deletePgBind = deletePgBind;
    }

    public RichCommandImageLink getDeletePgBind() {
        return deletePgBind;
    }

    public void setNamePgBind(RichSelectOneChoice namePgBind) {
        this.namePgBind = namePgBind;
    }

    public RichSelectOneChoice getNamePgBind() {
        return namePgBind;
    }

    public void setCompleteBoxPgBind(RichPanelBox completeBoxPgBind) {
        this.completeBoxPgBind = completeBoxPgBind;
    }

    public RichPanelBox getCompleteBoxPgBind() {
        return completeBoxPgBind;
    }

    public void itemNameVCL(ValueChangeEvent vce) {
        System.out.println("Inside " + contractBasisPgBind.getValue());
        if (!contractBasisPgBind.getValue().toString().equalsIgnoreCase("488")) {

            if (vce.getNewValue() != null) {
                String itmId = vce.getNewValue().toString();
                _log.info("Item Id is-->" + itmId);
                OperationBinding ob = executeOperation("getLatestItmPriceSo");
                ob.getParamsMap().put("itmDesc", itmId);
                ob.execute();
                _log.info("Item price is : " + ob.getResult());
                if (ob.getResult() != null && ((Number)ob.getResult()).compareTo(new Number(-2)) != 0) {
                    itmPriceBind.setValue(ob.getResult());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);

                    this.chkerror = "no";
                } else {
                    this.chkerror = "yes";

                    FacesMessage msg =
                        new FacesMessage("Unable to fetch Item Price according to Customer Price policy!",
                                         "Kindly check Customer Price policy !");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(vce.getComponent().getClientId(), msg);
                }
            }
        }
    }

    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void setContractBasisPgBind(RichSelectOneChoice contractBasisPgBind) {
        this.contractBasisPgBind = contractBasisPgBind;
    }

    public RichSelectOneChoice getContractBasisPgBind() {
        return contractBasisPgBind;
    }

    public void ItmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            _log.info("Value of Item is-->" + object);
            OperationBinding ob = executeOperation("itmIdValidator");
            ob.getParamsMap().put("itmName", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                String flg = ob.getResult().toString();
                if ("Y".equalsIgnoreCase(flg)) {
                    String msg2 = resolvEl("#{bundle['MSG.427']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }
        }
    }

    public StringBuffer getPolicyApplied() {
        StringBuffer i = new StringBuffer("N");
        OperationBinding binding = this.getBindings().getOperationBinding("checkPolicyApplied");
        if (binding != null) {
            binding.execute();
            Object object = binding.getResult();
            if (object != null) {
                i = (StringBuffer)object;
            }
        }
        return i;
    }

    public void setItemSrNoPgBind(RichOutputText itemSrNoPgBind) {
        this.itemSrNoPgBind = itemSrNoPgBind;
    }

    public RichOutputText getItemSrNoPgBind() {
        return itemSrNoPgBind;
    }

    public void setItemQtyPgBind(RichInputText itemQtyPgBind) {
        this.itemQtyPgBind = itemQtyPgBind;
    }

    public RichInputText getItemQtyPgBind() {
        return itemQtyPgBind;
    }

    public void setItemPopUpPgBind(RichPanelGroupLayout itemPopUpPgBind) {
        this.itemPopUpPgBind = itemPopUpPgBind;
    }

    public RichPanelGroupLayout getItemPopUpPgBind() {
        return itemPopUpPgBind;
    }
}
