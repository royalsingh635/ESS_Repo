package slsintimationslipapp.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.domain.Number;

import javax.faces.validator.*;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;

//import slsintimationslipapp.model.views.SlsIntmSlipVOImpl;

public class SlsIntimationSlipBean {
    private RichInputText docIdNBinding;
    private RichInputListOfValues eoIdNbinding;
    private RichInputText eoIdBinding;
    private RichInputDate docDtBinding;
    private String mode = "V";
    private String docHexId = null;
    private boolean isSoldDisable = true;
    private RichInputText docTxnIdBinding;
    private RichInputText soldQntyBinding;
    private RichInputText balQtyTransBind;
    private RichInputText balItmQtyBinding;
    private String cldIdVar = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private Integer slocIdVar = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String orgIdVar = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    //private String hoOrgIdVar=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    private Integer usrIdVar = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

    public SlsIntimationSlipBean() {
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
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void SearchButtonAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("SearchAction");
        operationBinding.execute();
    }

    public void ResetButtonAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ResetAction");
        operationBinding.execute();
    }

    public void setDocIdNBinding(RichInputText docIdNBinding) {
        this.docIdNBinding = docIdNBinding;
    }

    public RichInputText getDocIdNBinding() {
        return docIdNBinding;
    }

    public void setEoIdNbinding(RichInputListOfValues eoIdNbinding) {
        this.eoIdNbinding = eoIdNbinding;
    }

    public RichInputListOfValues getEoIdNbinding() {
        return eoIdNbinding;
    }

    public void setEoIdBinding(RichInputText eoIdBinding) {
        this.eoIdBinding = eoIdBinding;
    }

    public RichInputText getEoIdBinding() {
        return eoIdBinding;
    }

    public void setDocDtBinding(RichInputDate docDtBinding) {
        this.docDtBinding = docDtBinding;
    }

    public RichInputDate getDocDtBinding() {
        return docDtBinding;
    }

    public void insertIntoItemAction(ActionEvent actionEvent) {

        // Add event code here...

        Timestamp docDate = null;
        Integer eoId = null;
        //Key parentKey = null;
        //DCIteratorBinding parentIter = null;


        String docIdN = docIdNBinding.getValue().toString();

        docDate = (Timestamp)docDtBinding.getValue();
        eoId = Integer.parseInt(eoIdBinding.getValue().toString());

        BindingContainer binding = getBindings();

        OperationBinding operationBinding = getBindings().getOperationBinding("getAutoRowsIntmnSlipItm");
        operationBinding.getParamsMap().put("eoIdAM", eoId);
        operationBinding.getParamsMap().put("docDtAM", docDate);
        operationBinding.getParamsMap().put("docIdAM", docIdN);


        operationBinding.execute();
        Object autoinsertRows = operationBinding.getResult();

        if ((autoinsertRows != null)) {
            System.out.println("returned by function dispDocId" + operationBinding.execute());

            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            //operationBinding = getBindings().getOperationBinding("getRowsIntmSlip");
            //System.out.println(docIdN);
            //operationBinding.getParamsMap().put("docIdN", docIdN);
            //operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
            operationBinding.getParamsMap().put("docId", docIdN);

            operationBinding.execute();
        }
    }

    public void createIntimationSlipAction(ActionEvent actionEvent) {
        OperationBinding operationBinding1 = getBindings().getOperationBinding("refreshVo");
        getBindings().getOperationBinding("setBindVarToNull").execute();
        setMode("A");
        setIsSoldDisable(false);
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
        operationBinding.execute();
    }

    public void SaveButtonAL(ActionEvent actionEvent) {
        /* if (true)
            throw new RuntimeException("This is custom throw excepption.."); */
        if(mode.equalsIgnoreCase("E")) {
            Object execute = getBindings().getOperationBinding("checkForLessBalQty").execute();
            if(execute != null) {
                int value = Integer.parseInt(execute.toString());
                if(value == 1) {
                    String msg ="Sold Quantity can not be less than or equal to zero !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                }
                else if(value == 2) {
                    String msg ="Balance quantity can not be empty !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                }
                else if(value == 3) {
                    String msg ="Sold quantity can not be greater than balance quantity !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
        }
        if (chkToGoFurtherOrNot()) {
            if (mode.equalsIgnoreCase("A") || true) {
                setMode("E");
             /*    OperationBinding operationBindingUpdate = getBindings().getOperationBinding("setStatusUpdBalQuant");
                operationBindingUpdate.execute(); */

                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                /* operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
            logAdf.info("getDeleteRowswSldQty---" + operationBinding);
            operationBinding.execute(); */
                this.saveFunction();
                getBindings().getOperationBinding("Commit").execute();
                getBindings().getOperationBinding("updateTempBalQty").execute();
                getBindings().getOperationBinding("Commit").execute();
            }

            String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            String msg = "Please enter sold quantity..!!!";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
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

    public void CancelButtonAL(ActionEvent actionEvent) {
        Key parentKey = null;
        DCIteratorBinding parentIter = null;
        if (mode.equalsIgnoreCase("A")) {
            getBindings().getOperationBinding("deleteIntimationOnCancel").execute();

            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();
            /* logAdf.info("After Rollback");
            operationBinding = getBindings().getOperationBinding("getIntmSlipVoImplAM");
            operationBinding.execute();
            ViewObject object1 = (ViewObject)operationBinding.getResult();
            object1.executeQuery();
            operationBinding = getBindings().getOperationBinding("getIntmSlipItmVoImplAM");
            operationBinding.execute();
            ViewObject object2 = (ViewObject)operationBinding.getResult();
            object2.executeQuery();
        */operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute2");
            operationBinding.execute();

        } else {
            parentIter = (DCIteratorBinding)getBindings().get("SlsIntmSlipIterator");
            if (parentIter != null) {
                parentKey = parentIter.getCurrentRow().getKey();

                BindingContainer binding = getBindings();
                System.out.println("binding " + binding);

                OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute2");
                operationBinding.execute();
                try {
                    if (parentIter != null && parentKey != null) {
                        parentIter.executeQuery();
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                } catch (Exception e) {
                    // TODO: Add catch code

                    e.printStackTrace();
                }
            }
        }
        setMode("V");
        AdfFacesContext.getCurrentInstance().addPartialTarget(soldQntyBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdNbinding);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setIsSoldDisable(boolean isSoldDisable) {
        this.isSoldDisable = isSoldDisable;
    }

    public boolean isIsSoldDisable() {
        return isSoldDisable;
    }

    public void EditButtonAL(ActionEvent actionEvent) {
        
        Object execute = getBindings().getOperationBinding("checkForEditDoc").execute();
        System.out.println("Pending check val is:  "+execute);
        if(execute == null) {
            String msg = resolvElDCMsg("Document is already Pending for another user, can not Edit !!").toString(); //Please Enter the value
            FacesMessage facesMsg = new FacesMessage(msg);
            facesMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return;
        }
        
        OperationBinding operationBinding1 = getBindings().getOperationBinding("refreshVo");
        getBindings().getOperationBinding("updateIntmBalQty").execute();
        this.setMode("E");
    }

    public void saveFunction() {
        // System.out.println("************");
        {
            OperationBinding operationBinding = null;
            if (docTxnIdBinding.getValue() != null) {
                docHexId = docTxnIdBinding.getValue().toString(); //SlsIntmSlipVOCriteria1
                System.out.println("Doc value is " + docHexId);
            }

            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("SlsIntmSlipIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            // System.out.println("^^^^^^^^^^");
            if (mode == "A" || true) {
                System.out.println("Inside");

                System.out.println("Parent Key is -->" + parentKey);
                if (parentKey != null) {
                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");

                    operationBinding.execute();

                    this.saveToWorkFlow();
                    operationBinding = getBindings().getOperationBinding("isIsAllItmZero");
                    operationBinding.execute();
                    Boolean isVal = (Boolean)operationBinding.getResult();
                    if (isVal) {
                        operationBinding = getBindings().getOperationBinding("Delete");
                        operationBinding.execute();
                    }

                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute1");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute2");
                    operationBinding.execute();

                    // operationBinding = getBindings().getOperationBinding("setBalanceQty");
                    //  operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();

                }
            }
            setMode("V");

            operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
            operationBinding.getParamsMap().put("docId", docHexId);
            operationBinding.execute();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        System.out.println("------------------");
    }

    public void setDocTxnIdBinding(RichInputText docTxnIdBinding) {
        this.docTxnIdBinding = docTxnIdBinding;
    }

    public RichInputText getDocTxnIdBinding() {
        return docTxnIdBinding;
    }

    public String insertIntoItemAction() {

        // Add event code here...

        Timestamp docDate = null;
        Integer eoId = null;
        //Key parentKey = null;
        //DCIteratorBinding parentIter = null;
        setMode("A");
        if (mode.equalsIgnoreCase("A")) {

            String docIdN = docIdNBinding.getValue().toString();

            docDate = (Timestamp)docDtBinding.getValue();
            eoId = Integer.parseInt(eoIdBinding.getValue().toString());

            BindingContainer binding = getBindings();

            OperationBinding operationBinding = getBindings().getOperationBinding("getAutoRowsIntmnSlipItm");
            operationBinding.getParamsMap().put("eoIdAM", eoId);
            operationBinding.getParamsMap().put("docDtAM", docDate);
            operationBinding.getParamsMap().put("docIdAM", docIdN);
            if ((mode == "A") || (mode == "E")) {

                operationBinding.execute();
                Object autoinsertRows = operationBinding.getResult();

                if ((autoinsertRows != null)) {
                    System.out.println("returned by function dispDocId" + operationBinding.execute());

                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    //operationBinding = getBindings().getOperationBinding("getRowsIntmSlip");
                    //System.out.println(docIdN);
                    //operationBinding.getParamsMap().put("docIdN", docIdN);
                    //operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute1");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Rollback");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
                    operationBinding.getParamsMap().put("docId", docIdN);

                    operationBinding.execute();

                    setMode("A");
                }
            }
        }
        setMode("A");
        return null;
    }

    public void quantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if ((object != null) && (balItmQtyBinding.getValue() != null)) {
            Number zero = new Number(0);
            Number soldQty = (Number)object;
            Number balQty = new Number(0);

            if (uIComponent.getAttributes().get("balQty") != null) {
                Object object_2 = uIComponent.getAttributes().get("balQty");
                balQty = (Number)object_2;
            }
            System.out.println("BalQty is :" + balQty);


            //Number zero = new Number(0);
            if (balQty != zero) {
                if (soldQty.compareTo(zero) == -1) {
                    String msg =
                        resolvElDCMsg("#{bundle['MSG.1006']}").toString(); //Value of Sold quantity cannot be less than zero
                    FacesMessage facesMsg = new FacesMessage(msg);
                    throw new ValidatorException(facesMsg);
                    //new FacesMessage(facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR),msg,null)
                    //facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
                }

                if ((soldQty.compareTo(balQty) == 1)) {
                    String msg =
                        resolvElDCMsg("#{bundle['MSG.1008']}").toString(); //Sold quantity must be less than or equal to Balance quantity
                    FacesMessage facesMsg = new FacesMessage(msg);
                    facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(facesMsg);
                    //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
                }

            }

        } else {
            String msg = resolvElDCMsg("#{bundle['MSG.959']}").toString(); //Please Enter the value
            FacesMessage facesMsg = new FacesMessage(msg);
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
            //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
        }
    }
    String wfNo = "";

    public void saveToWorkFlow() {

        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", slocIdVar);
        WfnoOp.getParamsMap().put("CldId", cldIdVar);
        WfnoOp.getParamsMap().put("OrgId", orgIdVar);
        WfnoOp.getParamsMap().put("DocNo", 21509);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            wfNo = WfnoOp.getResult().toString();
        }
        System.out.println("in bena work flow number " + wfNo);
        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
        usrLevelOp.getParamsMap().put("SlocId", slocIdVar);
        usrLevelOp.getParamsMap().put("CldId", cldIdVar);
        usrLevelOp.getParamsMap().put("OrgId", orgIdVar);
        usrLevelOp.getParamsMap().put("UsrId", usrIdVar);
        usrLevelOp.getParamsMap().put("WfNo", wfNo);
        usrLevelOp.getParamsMap().put("DocNo", 21509);
        usrLevelOp.execute();
        if (usrLevelOp.getResult() != null) {
            level = Integer.parseInt(usrLevelOp.getResult().toString());
        }

        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
        insTxnOp.getParamsMap().put("SlocId", slocIdVar);
        insTxnOp.getParamsMap().put("CldId", cldIdVar);
        insTxnOp.getParamsMap().put("OrgId", orgIdVar);
        insTxnOp.getParamsMap().put("DocNo", 21509);
        insTxnOp.getParamsMap().put("WfNo", wfNo);
        insTxnOp.getParamsMap().put("usr_idFrm", usrIdVar);
        insTxnOp.getParamsMap().put("usr_idTo", usrIdVar);
        insTxnOp.getParamsMap().put("levelFrm", level);
        insTxnOp.getParamsMap().put("levelTo", level);
        insTxnOp.getParamsMap().put("action", action);
        insTxnOp.getParamsMap().put("remark", remark);
        insTxnOp.getParamsMap().put("amount", 0);
        insTxnOp.execute();

        if (insTxnOp.getResult() != null) {
            res = Integer.parseInt(insTxnOp.getResult().toString());
        }
    }

    public void soldQtyValueChngListener(ValueChangeEvent vce) {
        /*     if (vce.getNewValue() != null) {
            Number sldItmQty = new Number(0);
            Number oldbalQty = new Number(0);

            if (balItmQtyBinding.getValue() != null) {
                DCIteratorBinding r = (DCIteratorBinding)getBindings().get("SlsIntmSlipItmIterator");
                Row currentRow = r.getCurrentRow();
                System.out.println("Current row is " + currentRow);
                oldbalQty = (Number)balItmQtyBinding.getValue();
                sldItmQty = (Number)vce.getNewValue();

                Number newbalItmQty = oldbalQty.subtract(sldItmQty);
                System.out.println("balance --> " + newbalItmQty);
                currentRow.setAttribute("BalQtyTrans", newbalItmQty);
                OperationBinding opB = getBindings().getOperationBinding("setQtyBs");
                opB.getParamsMap().put("row", currentRow);
                opB.getParamsMap().put("itmQty", sldItmQty);
                opB.execute();
            }

        } */
    }

    public void setSoldQntyBinding(RichInputText soldQntyBinding) {
        this.soldQntyBinding = soldQntyBinding;
    }

    public RichInputText getSoldQntyBinding() {
        return soldQntyBinding;
    }

    public void setBalQtyTransBind(RichInputText balQtyTransBind) {
        this.balQtyTransBind = balQtyTransBind;
    }

    public RichInputText getBalQtyTransBind() {
        return balQtyTransBind;
    }

    public void setBalItmQtyBinding(RichInputText balItmQtyBinding) {
        this.balItmQtyBinding = balItmQtyBinding;
    }

    public RichInputText getBalItmQtyBinding() {
        return balItmQtyBinding;
    }


    public String saveAndForwardAction() {
        if(mode.equalsIgnoreCase("E")) {
            Object execute = getBindings().getOperationBinding("checkForLessBalQty").execute();
            if(execute != null) {
                int value = Integer.parseInt(execute.toString());
                if(value == 1) {
                    String msg ="Sold Quantity can not be less than or equal to zero !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return "";
                }
                else if(value == 2) {
                    String msg ="Balance quantity can not be empty !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return "";
                }
                else if(value == 3) {
                    String msg ="Sold quantity can not be greater than balance quantity !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return "";
                }
            }
        }
        if (chkToGoFurtherOrNot()) {
            OperationBinding operationBinding = null;
            /* OperationBinding operationBindingUpdate = getBindings().getOperationBinding("setStatusUpdBalQuant");
            operationBindingUpdate.execute(); */
            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
            operationBinding.execute();
            System.out.println("After Executing Delete Rows function");

        //    operationBinding = getBindings().getOperationBinding("updateBalQtyShip");

            /* operationBinding.getParamsMap().put("CldId", cldIdVar);
            operationBinding.getParamsMap().put("OrgId", orgIdVar);
            operationBinding.getParamsMap().put("SlocId", slocIdVar);
            operationBinding.getParamsMap().put("DocId", docTxnIdBinding.getValue().toString());
            operationBinding.execute();
             */
            Number balQty = (Number)operationBinding.getResult();

            this.saveFunction();
            this.saveToWorkFlow();
            Integer pending = null;
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", slocIdVar);
            chkUsr.getParamsMap().put("CldId", cldIdVar);
            chkUsr.getParamsMap().put("OrgId", orgIdVar);
            chkUsr.getParamsMap().put("DocNo", 21509);
            chkUsr.execute();
            setMode("V");
            if (chkUsr.getResult() != null) {
                setMode("V");
                pending = Integer.parseInt(chkUsr.getResult().toString());
                //       System.out.println("Pending"+pending);
            }
             if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usrIdVar) == 0) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.getPageFlowScope().put("Doc_txn_id", docTxnIdBinding.getValue().toString());
                getBindings().getOperationBinding("Commit").execute();
                /* if(true)
                return null; */
                getBindings().getOperationBinding("updateTempBalQty").execute();
                getBindings().getOperationBinding("Commit").execute();
                setMode("V");
                //setIsSoldDisable(true);
                return "goToWF";
            } else {
                //setIsSoldDisable(true);
                String msg2 =
                    resolvElDCMsg("#{bundle['MSG.1054']}").toString(); //This Slip is pending at other user for approval, You can not forward this.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            } 

        } else {
            String msg2 = "Please Enter Sold Quantity..!!";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

            return null;
        }
        //return null;
    }

    public Boolean chkToGoFurtherOrNot() {
        OperationBinding ob = getBindings().getOperationBinding("chkSoldQtyEntererorNot");
        ob.execute();

        return (Boolean)ob.getResult();
    }

    public void setWfNo(String wfNo) {
        this.wfNo = wfNo;
    }

    public String getWfNo() {
        return wfNo;
    }
    public void showMessage(String msg, FacesMessage.Severity severity) {
        FacesMessage message2 = new FacesMessage(msg);
        message2.setSeverity(severity);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message2);
    }
}
