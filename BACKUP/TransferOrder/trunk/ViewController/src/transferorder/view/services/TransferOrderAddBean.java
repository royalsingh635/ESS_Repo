package transferorder.view.services;

import java.math.BigDecimal;
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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.domain.Number;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.binding.BindingContainer;

import oracle.adf.model.BindingContext;

import oracle.binding.OperationBinding;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;

import transferorder.model.services.TransferOrderAMImpl;


public class TransferOrderAddBean {
  
    private String docTxnId = "";
    private String docDtlFlag = "true";
    private RichSelectOneChoice sourceDocBind;
    private Integer docTySrc;
    private RichInputText docIdBind;
    private RichInputText ordQtyBind;
    private RichSelectOneChoice itmIdBind;
    private RichSelectOneChoice itmUomBind;
    private RichSelectOneChoice sourceDocTypeBind;
    private RichInputText trfNumBind;
    private RichCommandImageLink additemmanualbinding;

    private RichSelectOneChoice trfOrdByBind;
    //  private RichSelectOneChoice rqmtAreaBind;
    private RichSelectOneChoice warhouseBind;
    private RichSelectOneChoice trfTypeBind;
    private RichSelectOneChoice trfFrmOrgBind;
    private RichSelectOneChoice trfToOrgBind;
    private RichSelectOneChoice warehouseSrcBind;
    private RichSelectOneChoice warehouseDestBind;
    private RichInputDate dateReqBind;
    private RichInputText reqQtyBind;
    private RichInputListOfValues itemFromMrsBind;
    private RichInputDate trfDateBind;
    private RichInputListOfValues transItmDescBind;
    private RichInputText ordQtyBinding;
    private String mode = "vw";
    private RichSelectBooleanCheckbox cancelTrfBinding;
    private RichSelectBooleanCheckbox authCB;
    private RichInputText ordQtybinding;
    private RichInputText authQtyBinding;
    private RichInputText stkResvQtyBinding;
    private RichColumn cancelColumn;
    private RichSelectBooleanCheckbox cnclItm;
    private RichPopup stockDetailPoup;

    public TransferOrderAddBean() {
        String value = resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        if (value.equals("A"))
            mode = "cr";
        else
            mode = "vw";
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

    public void createSlipActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        mode = "cr";
    }

    public void saveTransferOrderActionListener(ActionEvent actionEvent) {
        if (trfDateBind.getValue() != null) {
            if (warhouseBind.getValue() != null && warhouseBind.getValue().toString() != "") {
                if (trfTypeBind.getValue() != null) {
                    if (warehouseSrcBind.getValue() != null) {
                        if (warehouseDestBind.getValue() != null) {
                            if (trfNumBind.getValue() != null) {

                            } else {
                                /* OperationBinding op = getBindings().getOperationBinding("setFyId");
                                op.execute(); */
                                OperationBinding op1 = getBindings().getOperationBinding("setTrfNo");
                                op1.execute();
                            }
                            OperationBinding op2 = getBindings().getOperationBinding("setInvReq");
                            op2.execute();
                            OperationBinding op3 = getBindings().getOperationBinding("setItmUomBsDefVal");
                            op3.getParamsMap().put("docId",null);
                            op3.execute();
                            OperationBinding chkcncl = getBindings().getOperationBinding("checkCancel");               
                            chkcncl.execute(); 
                            Boolean result=false;
                            if(chkcncl.getErrors().size()==0)
                                result = (Boolean)chkcncl.getResult();
                            if(result==true)
                            {
                            OperationBinding setst = getBindings().getOperationBinding("setTrfStat");               
                            setst.getParamsMap().put("stat",378);
                            setst.execute(); 
                            }
                
                            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                            Integer SlocId =
                                Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                            String WfNo =null;
                            String UsrId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
                            Integer level = -1;
                            String action = "I";
                            String remark = "A";
                            Integer res = -1;
                            //get Wf Id
                            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");

                            WfnoOp.getParamsMap().put("SlocId", SlocId);

                            WfnoOp.getParamsMap().put("CldId", CldId);

                            WfnoOp.getParamsMap().put("OrgId", OrgId);

                            WfnoOp.getParamsMap().put("DocNo", 18519);
                            WfnoOp.execute();
                            if (WfnoOp.getErrors().size()==0 && WfnoOp.getResult() != null) {
                                WfNo = WfnoOp.getResult().toString();
                            }
                            if(WfNo!=null && !"0".equalsIgnoreCase(WfNo))
                            {
                            //get user level
                            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");

                            usrLevelOp.getParamsMap().put("SlocId", SlocId);

                            usrLevelOp.getParamsMap().put("CldId", CldId);

                            usrLevelOp.getParamsMap().put("OrgId", OrgId);

                            usrLevelOp.getParamsMap().put("UsrId", UsrId);

                            usrLevelOp.getParamsMap().put("WfNo", WfNo);

                            usrLevelOp.getParamsMap().put("DocNo", 18519);
                            usrLevelOp.execute();
                            if (usrLevelOp.getErrors().size()==0 && usrLevelOp.getResult() != null) {
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                            }
                            if(level>=0)
                            {
                            //insert into txn
                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");

                            insTxnOp.getParamsMap().put("SlocId", SlocId);

                            insTxnOp.getParamsMap().put("CldId", CldId);

                            insTxnOp.getParamsMap().put("OrgId", OrgId);

                            insTxnOp.getParamsMap().put("DocNo", 18519);

                            insTxnOp.getParamsMap().put("WfNo", WfNo);
                            insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                            insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }

                            OperationBinding chkUsr = getBindings().getOperationBinding("Commit");
                            chkUsr.execute();
                            OperationBinding updtstk = getBindings().getOperationBinding("updtStkAftrCncl");
                            updtstk.execute();
                           // OperationBinding chkUsr = getBindings().getOperationBinding("Commit");
                            chkUsr.execute();
                            if (chkUsr.getErrors().size() == 0) {
                                mode = "ss";
                            } else
                                System.out.println("Error on Commit=" + chkUsr.getErrors());
                            }
                            else
                            {
                                    String msg2 = "Something went wrong (user level in workflow).Please contact to ESS!";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(warehouseDestBind.getClientId(), message2);
                                }
                            }
                            else
                            {
                                    String msg2 = "Workflow for this Document is not Created.";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(warehouseDestBind.getClientId(), message2);
                                }
                        } else {
                            String msg2 = "Destination Warehouse Required.";
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(warehouseDestBind.getClientId(), message2);
                        }
                    } else {
                        String msg2 = "Source Warehouse Required.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(warehouseSrcBind.getClientId(), message2);
                    }
                } else {
                    String msg2 = "Transfer Type Required.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(trfTypeBind.getClientId(), message2);
                }
            } else {

                String msg2 = "Warehouse Required.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(warhouseBind.getClientId(), message2);

            }

        } else {
            String msg2 = "Invalid Transfer Date.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    public void editTransferOrderActionListener(ActionEvent actionEvent) {

        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //String WfNo="0";
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        //Check Pending
        Integer pending = null;
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18519);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            System.out.println("EDIT BUTTON CLICK CHK : : Pending = " + pending);
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
            if(authCB.getValue().toString().equals("true"))
            {
            OperationBinding chkusr=getBindings().getOperationBinding("chkUserAuth");
            chkusr.execute();
            String usrauth="N";
            if(chkusr.getErrors().size()==0 && chkusr.getResult()!=null)
            usrauth=chkusr.getResult().toString();
            if(usrauth.equals("Y"))
            mode="eas";
            else
            {
                    String msg2 = "You can not Edit Transfer Order as you are not authorised user.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            }
                else
            {
            mode = "es";
            }
            /*   flag  = "true";
            flag4 = "false"; */
            //return "toWF";
        } else {
            String msg2 = "This Slip is  pending at other user for approval, You can not Edit this.";
            FacesMessage message2 = new FacesMessage(msg2);

            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            //return null;
        }
    }

    public void deleteTransferOrderActionListener(ActionEvent actionEvent) {
        OperationBinding op3 = getBindings().getOperationBinding("delChildRec");
        op3.getParamsMap().put("docId", docIdBind.getValue().toString());
        op3.execute();
        // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete2").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
    }

    public void cancelTransferOrderActionListener(ActionEvent actionEvent) {

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        mode="vw";
        //  BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
    }

    /*  public void transferOrderByValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("Transfer Order BY : "+ valueChangeEvent.getNewValue());

        if(valueChangeEvent.getNewValue().equals(372)){
            flag1="false";
            //flag4="false";
            flag3="true";
            flag9 = "true";
        }
        else if(valueChangeEvent.getNewValue().equals(371)){
            flag1="false";
            //flag4="true";
            flag3="false";
            flag9 = "true";

        }
        System.out.println("flag1 value = "+flag1);
    } */

   /*  public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag1() {
        return flag1;
    } */

    public void trfTypeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(warhouseBind.getValue()!=null && (!warhouseBind.getValue().toString().equals("")))
        {
      //  System.out.println("Transfer Type : " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().equals(373)) {
            OperationBinding op = getBindings().getOperationBinding("setOrgValue");
            op.execute();
        } 
        else if (valueChangeEvent.getNewValue().equals(374)) {
            OperationBinding op = getBindings().getOperationBinding("setOneSidOrgValue");
            op.execute(); 
        }
        }
        else
        {
                String msg2 = "Select Warehouse.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(warhouseBind.getClientId(), message2); 
            }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

   /*  public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag2() {
        return flag2;
    } */

    public void SourceDocTypeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        /*  System.out.println("<<<<== sourceDocTypeBind = "+sourceDocTypeBind.getValue().toString());
        if(valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0){
            docTySrc = (Integer)valueChangeEvent.getNewValue();
            System.out.println("Selected SOURCE DOC Type : "+ docTySrc);
            flag6 = "false";
            flag7 = "false";
            sourceDocBind.processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocBind);
        }
        else{
            OperationBinding op = getBindings().getOperationBinding("delChildRec");
            op.getParamsMap().put("docId",docIdBind.getValue().toString());
            op.execute();
            docTySrc = 0;
            System.out.println("Selected DOC TYPE null = "+docTySrc);
            flag7="true";
            System.out.println("flag 7 = "+flag7);
            flag6="true";
        } */
    }

    public void setSourceDocBind(RichSelectOneChoice sourceDocBind) {
        this.sourceDocBind = sourceDocBind;
    }

    public RichSelectOneChoice getSourceDocBind() {
        return sourceDocBind;
    }

   /*  public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag5(String flag5) {
        this.flag5 = flag5;
    }

    public String getFlag5() {
        return flag5;
    }

    public void setFlag6(String flag6) {
        this.flag6 = flag6;
    }

    public String getFlag6() {
        return flag6;
    }

    public void setFlag7(String flag7) {
        this.flag7 = flag7;
    }

    public String getFlag7() {
        return flag7;
    } */

    public void createSrcDocActionListener(ActionEvent actionEvent) throws SQLException {

        if (trfDateBind.getValue() != null) {
            if (warhouseBind.getValue() != "") {
                if (trfTypeBind.getValue() != null) {
                    if (trfFrmOrgBind.getValue() != null) {
                        if (trfToOrgBind.getValue() != null) {
                            if (warehouseSrcBind.getValue() != null) {
                                if (warehouseDestBind.getValue() != null) {
                                    if (transItmDescBind.getValue() != null &&
                                        (!transItmDescBind.getValue().toString().equals(""))) {
                                        if (reqQtyBind.getValue() != null &&
                                            (!reqQtyBind.getValue().toString().equals(""))) {
                                            Number qty = (Number)reqQtyBind.getValue();
                                            if (qty.compareTo(new Number(0)) > 0) {
                                                OperationBinding chk =
                                                    getBindings().getOperationBinding("CheckDuplicate");
                                                chk.execute();
                                                Boolean dupli = false;
                                                if (chk.getErrors().size() == 0) {
                                                    dupli = (Boolean)chk.getResult();
                                                } else
                                                    System.out.println("Error in check duplicate for item=" +
                                                                       chk.getErrors());
                                                System.out.println("isDupliFound=" + dupli);
                                                if (dupli.booleanValue() == false) {
                                                    OperationBinding opfy = getBindings().getOperationBinding("setFyId");
                                                    opfy.execute();
                                                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
                                                    OperationBinding op =
                                                        getBindings().getOperationBinding("setItmUomBs");
                                                    op.execute();
                                                    if (op.getErrors().size() != 0)
                                                        System.out.println("Error on setitmuombs=" + op.getErrors());
                                                    OperationBinding op1 =
                                                        getBindings().getOperationBinding("setOrdQtyBs");
                                                    Number quantity = new Number(reqQtyBind.getValue().toString());
                                                    op1.getParamsMap().put("ordQty", quantity);
                                                    op1.execute();
                                                    if (op1.getErrors().size() != 0)
                                                        System.out.println("Error on set ordqtybs=" + op1.getErrors());
                                                    transItmDescBind.setValue("");
                                                    reqQtyBind.setValue("");
                                                    transItmDescBind.resetValue();
                                                    reqQtyBind.resetValue();
                                                    OperationBinding resetitm =
                                                        getBindings().getOperationBinding("resetItemAndQty");
                                                    resetitm.execute();
                                                    if (resetitm.getErrors().size() != 0)
                                                        System.out.println("error on reset=" + resetitm.getErrors());
                                                    else
                                                        System.out.println("Created and setted");
                                                    mode="ai";
                                                } else {
                                                    String msg2 = "Duplicate Item for this Transfer Order.";
                                                    FacesMessage message2 = new FacesMessage(msg2);
                                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                    FacesContext fc = FacesContext.getCurrentInstance();
                                                    fc.addMessage(transItmDescBind.getClientId(), message2);
                                                }

                                            } else {
                                                String msg2 = "Order Quantity must be greater than Zero.";
                                                FacesMessage message2 = new FacesMessage(msg2);
                                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(reqQtyBind.getClientId(), message2);
                                            }
                                        }

                                        else {
                                            String msg2 = "Order Quantity Required";
                                            FacesMessage message2 = new FacesMessage(msg2);
                                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(reqQtyBind.getClientId(), message2);
                                        }
                                    } else {
                                        String msg2 = "Item name is required.";
                                        FacesMessage message2 = new FacesMessage(msg2);
                                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(transItmDescBind.getClientId(), message2);
                                    }
                                } else {
                                    String msg2 = "Destination Warehouse Required.";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(warehouseDestBind.getClientId(), message2);
                                }
                            } else {
                                String msg2 = "Source Warehouse Required.";
                                FacesMessage message2 = new FacesMessage(msg2);
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(warehouseSrcBind.getClientId(), message2);
                            }
                        } else {
                            String msg2 = "Destination Organisation Required.";
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(trfToOrgBind.getClientId(), message2);
                        }
                    } else {
                        String msg2 = "Source Organisation Required.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(trfFrmOrgBind.getClientId(), message2);
                    }
                } else {
                    String msg2 = "Transfer Type Required.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(trfTypeBind.getClientId(), message2);
                }
            } else {
                String msg2 = "Warehouse Required.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(warhouseBind.getClientId(), message2);

            }


        } else {
            String msg2 = "Transfer Date Invalid.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(trfDateBind.getClientId(), message2);
        }
    }

    /* public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    } */

    public void srcDocValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (docTySrc == 326) {
            String docSrc = (String)valueChangeEvent.getNewValue();
            //System.out.println("Src Doc MRS_TXN_ID = "+docSrc);
            OperationBinding op = getBindings().getOperationBinding("getSrcDocDtl");
            op.getParamsMap().put("MRSno", docSrc);
            op.getParamsMap().put("docId", docIdBind.getValue().toString());
            op.execute();
        } else if (docTySrc == 0) {
            System.out.println("srcDocValueChangeListener docTySrc ==== " + valueChangeEvent.getNewValue());
        } else {
            System.out.println("other than MRS so Src Doc = ?");
        }
    }

    public void setDocIdBind(RichInputText docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichInputText getDocIdBind() {
        return docIdBind;
    }

    public void setOrdQtyBind(RichInputText ordQtyBind) {
        this.ordQtyBind = ordQtyBind;
    }

    public RichInputText getOrdQtyBind() {
        return ordQtyBind;
    }

    public void ordQtyValueChangeListener(ValueChangeEvent valueChangeEvent) {
        /*   //String ordQty = (String)valueChangeEvent.getNewValue();
        if(valueChangeEvent.getNewValue()!=null){
        Number qty = (Number)valueChangeEvent.getNewValue();
        if(qty.compareTo(new Number(0))>0)
        {}
        else
        {
                String msg2 = "Required Quantity can not be less than zero.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);

            }
        }
        else
        {
                String msg2 = "Required Quantity for any item can not be null.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        System.out.println("req qty = "+valueChangeEvent.getNewValue()); */
    }

    public void cancelSrcDocActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void saveSrcDocActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String fwdtAction() {
        //check if slip is saved or not

        //            binPhyQty.setReadOnly(true);
        //            lotPhyQty.setReadOnly(true);
        //            srPhyQty.setReadOnly(true);
        OperationBinding getDocTxnId = getBindings().getOperationBinding("getDocTxnId");
        getDocTxnId.execute();
        docTxnId = (String)getDocTxnId.getResult();
        System.out.println("doctxnId =========> " + docTxnId);

        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String WfNo = null;
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer level =-1;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        //get Wf Id
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");

        WfnoOp.getParamsMap().put("SlocId", SlocId);

        WfnoOp.getParamsMap().put("CldId", CldId);

        WfnoOp.getParamsMap().put("OrgId", OrgId);

        WfnoOp.getParamsMap().put("DocNo", 18519);
        WfnoOp.execute();
        if (WfnoOp.getErrors().size()==0 && WfnoOp.getResult() != null) {
            WfNo = WfnoOp.getResult().toString();
        }
        //   System.out.println("WfNo="+WfNo);
        if(WfNo!=null){
        //get user level
        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");

        usrLevelOp.getParamsMap().put("SlocId", SlocId);

        usrLevelOp.getParamsMap().put("CldId", CldId);

        usrLevelOp.getParamsMap().put("OrgId", OrgId);

        usrLevelOp.getParamsMap().put("UsrId", UsrId);

        usrLevelOp.getParamsMap().put("WfNo", WfNo);

        usrLevelOp.getParamsMap().put("DocNo", 18519);
        usrLevelOp.execute();
        if (usrLevelOp.getErrors().size()==0 && usrLevelOp.getResult() != null) {
            level = Integer.parseInt(usrLevelOp.getResult().toString());
        }
        //   System.out.println("Level="+level);
        if(level>=0){
        //insert into txn
        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");

        insTxnOp.getParamsMap().put("SlocId", SlocId);

        insTxnOp.getParamsMap().put("CldId", CldId);

        insTxnOp.getParamsMap().put("OrgId", OrgId);

        insTxnOp.getParamsMap().put("DocNo", 18519);

        insTxnOp.getParamsMap().put("WfNo", WfNo);
        insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
        insTxnOp.getParamsMap().put("usr_idTo", UsrId);
        insTxnOp.getParamsMap().put("levelFrm", level);
        insTxnOp.getParamsMap().put("levelTo", level);
        insTxnOp.getParamsMap().put("action", action);
        insTxnOp.getParamsMap().put("remark", remark);
        insTxnOp.getParamsMap().put("amount", 0);
        insTxnOp.execute();

        if (insTxnOp.getResult() != null) {
            res = Integer.parseInt(insTxnOp.getResult().toString());
        }
        //             System.out.println("Res="+res);
        OperationBinding com = getBindings().getOperationBinding("Commit");
        com.execute();
        //Check Pending
        Integer pending = null;

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");

        chkUsr.getParamsMap().put("SlocId", SlocId);

        chkUsr.getParamsMap().put("CldId", CldId);

        chkUsr.getParamsMap().put("OrgId", OrgId);

        chkUsr.getParamsMap().put("DocNo", 18519);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            //
            System.out.println("Pending" + pending);
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
            return "toWF";
        } else {
            String msg2 = "This Slip is  pending at other user for approval, You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);

            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }
        }
        else
        {
                String msg2 = "Something went wrong with user level in workflow.Please contact to ESS!.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            }
        }
        else
        {
                String msg2 = "Workflow not added for this Document.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            
            }

    }

    public void setDocTxnId(String docTxnId) {
        this.docTxnId = docTxnId;
    }

    public String getDocTxnId() {
        return docTxnId;
    }

    /* public void setFlag8(String flag8) {
        this.flag8 = flag8;
    }

    public String getFlag8() {
        return flag8;
    } */

    public void itmIdValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("Item selected = " + valueChangeEvent.getNewValue().toString());
        OperationBinding op = getBindings().getOperationBinding("setTransItmId");
        op.getParamsMap().put("itmname", valueChangeEvent.getNewValue().toString());
        op.execute();
    }
    //code of no use

    public void itmUomValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("Selected ItmUom =" + valueChangeEvent.getNewValue().toString());
        OperationBinding op = getBindings().getOperationBinding("setItmUom");
        op.getParamsMap().put("itmUom", itmUomBind.getValue().toString());
        op.execute();
    }

    public void setItmIdBind(RichSelectOneChoice itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichSelectOneChoice getItmIdBind() {
        return itmIdBind;
    }

    public void setItmUomBind(RichSelectOneChoice itmUomBind) {
        this.itmUomBind = itmUomBind;
    }

    public RichSelectOneChoice getItmUomBind() {
        return itmUomBind;
    }

    public void setSourceDocTypeBind(RichSelectOneChoice sourceDocTypeBind) {
        this.sourceDocTypeBind = sourceDocTypeBind;
    }

    public RichSelectOneChoice getSourceDocTypeBind() {
        return sourceDocTypeBind;
    }

   /*  public void setFlag9(String flag9) {
        this.flag9 = flag9;
    }

    public String getFlag9() {
        return flag9;
    } */

    public void setDocDtlFlag(String docDtlFlag) {
        this.docDtlFlag = docDtlFlag;
    }

    public String getDocDtlFlag() {
        return docDtlFlag;
    }

    public void setTrfNumBind(RichInputText trfNumBind) {
        this.trfNumBind = trfNumBind;
    }

    public RichInputText getTrfNumBind() {
        return trfNumBind;
    }

    public void setAdditemmanualbinding(RichCommandImageLink additemmanualbinding) {
        this.additemmanualbinding = additemmanualbinding;
    }

    public RichCommandImageLink getAdditemmanualbinding() {
        return additemmanualbinding;
    }


    public void setTrfOrdByBind(RichSelectOneChoice trfOrdByBind) {
        this.trfOrdByBind = trfOrdByBind;
    }

    public RichSelectOneChoice getTrfOrdByBind() {
        return trfOrdByBind;
    }

    /*  public void setRqmtAreaBind(RichSelectOneChoice rqmtAreaBind) {
        this.rqmtAreaBind = rqmtAreaBind;
    }

    public RichSelectOneChoice getRqmtAreaBind() {
        return rqmtAreaBind;
    } */

    public void setWarhouseBind(RichSelectOneChoice warhouseBind) {
        this.warhouseBind = warhouseBind;
    }

    public RichSelectOneChoice getWarhouseBind() {
        return warhouseBind;
    }

    public void setTrfTypeBind(RichSelectOneChoice trfTypeBind) {
        this.trfTypeBind = trfTypeBind;
    }

    public RichSelectOneChoice getTrfTypeBind() {
        return trfTypeBind;
    }

    public void setTrfFrmOrgBind(RichSelectOneChoice trfFrmOrgBind) {
        this.trfFrmOrgBind = trfFrmOrgBind;
    }

    public RichSelectOneChoice getTrfFrmOrgBind() {
        return trfFrmOrgBind;
    }

    public void setTrfToOrgBind(RichSelectOneChoice trfToOrgBind) {
        this.trfToOrgBind = trfToOrgBind;
    }

    public RichSelectOneChoice getTrfToOrgBind() {
        return trfToOrgBind;
    }

    public void setWarehouseSrcBind(RichSelectOneChoice warehouseSrcBind) {
        this.warehouseSrcBind = warehouseSrcBind;
    }

    public RichSelectOneChoice getWarehouseSrcBind() {
        return warehouseSrcBind;
    }

    public void setWarehouseDestBind(RichSelectOneChoice warehouseDestBind) {
        this.warehouseDestBind = warehouseDestBind;
    }

    public RichSelectOneChoice getWarehouseDestBind() {
        return warehouseDestBind;
    }

    public void setDateReqBind(RichInputDate dateReqBind) {
        this.dateReqBind = dateReqBind;
    }

    public RichInputDate getDateReqBind() {
        return dateReqBind;
    }

    public void setReqQtyBind(RichInputText reqQtyBind) {
        this.reqQtyBind = reqQtyBind;
    }

    public RichInputText getReqQtyBind() {
        return reqQtyBind;
    }


    public void reqQtyValidatorinTable(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("validating req qty="+object);
        if(object!=null)
        {
        Number qty=new Number(0);

           qty = (Number)object;
            if(qty.compareTo(new Number(0))<= 0)
        {
                System.out.println("negative quantity");
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity must be greater than Zero.",null));
            }
            else
            {
                    Boolean is=isPrecisionValid(26, 6, qty);
                    if(is.toString().equalsIgnoreCase("true"))
                    {}
                    else
                    {
                        throw new ValidatorException(new
                        FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Precision(26,6).",null));
                        }
                }
         
        }
        else
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR , "Order Quantity required." , null));
        /* OperationBinding op=getBindings().getOperationBinding("ExecuteItmVo");
        op.execute(); */
    }

    public void trfFrmOrgVCE(ValueChangeEvent valueChangeEvent) {

        String org = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        System.out.println("OrgId=" + valueChangeEvent.getNewValue() + " PageFlow is=" + org);
        if (valueChangeEvent.getNewValue().toString().equals(org)) {
            OperationBinding setdestorg=getBindings().getOperationBinding("setSrcWhandDestOrgwh");
            setdestorg.execute();
            System.out.println("Achme selected");
        } 
        else
        { 
            OperationBinding setdestorg=getBindings().getOperationBinding("setDestOrgandWh");
            setdestorg.execute();
        }
            
    }

    public void trfToOrgVCE(ValueChangeEvent valueChangeEvent) {
        String org = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();

        if (valueChangeEvent.getNewValue().toString().equals(org)) {
            OperationBinding setdestorg=getBindings().getOperationBinding("setDestWhandSrcOrgwh");
            setdestorg.execute();
        } else
        { 
            OperationBinding setdestorg=getBindings().getOperationBinding("setSrcOrgandWh");
            setdestorg.execute();
        }
    }

    public void addItemForMRS(ActionEvent actionEvent) {

        OperationBinding op = getBindings().getOperationBinding("setCriteriaForItems");
        op.execute();
        OperationBinding op1 = getBindings().getOperationBinding("CreateInsert1");
        op1.execute();
        if (op.getErrors().size() > 0)
            System.out.println("error=" + op.getErrors());

    }

    public void setItemFromMrsBind(RichInputListOfValues itemFromMrsBind) {
        this.itemFromMrsBind = itemFromMrsBind;
    }

    public RichInputListOfValues getItemFromMrsBind() {
        return itemFromMrsBind;
    }

    public void setTrfDateBind(RichInputDate trfDateBind) {
        this.trfDateBind = trfDateBind;
    }

    public RichInputDate getTrfDateBind() {
        return trfDateBind;
    }

    public void setTransItmDescBind(RichInputListOfValues transItmDescBind) {
        this.transItmDescBind = transItmDescBind;
    }

    public RichInputListOfValues getTransItmDescBind() {
        return transItmDescBind;
    }

    public void ordQtyinTableVCE(ValueChangeEvent valueChangeEvent) {
      /*   if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(""))) {
            Number qty = (Number)valueChangeEvent.getNewValue();
            if (qty.compareTo(new Number(0)) < 0) {
                String msg2 = "Order Quantity must be greater than Zero.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(ordQtyBinding.getClientId(), message2);

            }
        } else {
            String msg2 = "Order Quantity is Required.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(ordQtyBinding.getClientId(), message2);
        } */
    }

    public void setOrdQtyBinding(RichInputText ordQtyBinding) {
        this.ordQtyBinding = ordQtyBinding;
    }

    public RichInputText getOrdQtyBinding() {
        return ordQtyBinding;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void warehouseSrcVCE(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null && warehouseSrcBind.getValue()!=null && (!warehouseSrcBind.getValue().toString().equals("")))
        {
            
            String whs = (String)valueChangeEvent.getNewValue();
                System.out.println("ware house serc call"+whs);
               // String orgSrc=trfFrmOrgBind.getValue().toString();
                //String orgdst=trfToOrgBind.getValue().toString();
            String wh=warhouseBind.getValue().toString();
           // if(whs.equals(wh) && orgSrc.equalsIgnoreCase(orgdst))
           if(whs.equals(wh) && trfFrmOrgBind.getValue().equals(trfToOrgBind.getValue()))
            {
            OperationBinding op=getBindings().getOperationBinding("chkDestWhandSet");
            op.execute();
            }
            else
            {
                System.out.println("ware house serc call");
                OperationBinding op=getBindings().getOperationBinding("whSetWithTrfType");
                op.execute();
                /* OperationBinding op=getBindings().getOperationBinding("setDestOrgandWh");
                op.execute();
                 */
                }
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseSrcBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trfFrmOrgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseDestBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trfToOrgBind);
    }

    public void warehouseDestVCE(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null && warehouseDestBind.getValue()!=null && (!warehouseDestBind.getValue().toString().equals("")))
        {
            String whd = (String)valueChangeEvent.getNewValue();
            String wh=warhouseBind.getValue().toString();
           /*  String orgd = (String)trfToOrgBind.getValue();
            String orgs = (String)trfFrmOrgBind.getValue(); */
            if(whd.equals(wh) && trfFrmOrgBind.getValue().equals(trfToOrgBind.getValue()))
            {
            OperationBinding op=getBindings().getOperationBinding("chkSrcWhandSet");
            op.execute();
            }
            else
            {
                    OperationBinding op=getBindings().getOperationBinding("setDestWhTrfType");
                    op.execute();
                /* OperationBinding op=getBindings().getOperationBinding("setSrcOrgandWh");
                op.execute(); */
                }
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseSrcBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trfFrmOrgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseDestBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trfToOrgBind);

    }

    public void warehouseVCE(ValueChangeEvent valueChangeEvent) {
       OperationBinding op=getBindings().getOperationBinding("resetTrfType");
       op.execute();
    }

    public void deleteItemAL(ActionEvent actionEvent) {
       OperationBinding op=getBindings().getOperationBinding("deleteitem");
       op.execute();
    }

    public void cancelTrfVCE(ValueChangeEvent valueChangeEvent) {
     //   System.out.println("In cancel VCE");
        BindingContainer bindings=getBindings();
        /* OperationBinding op1=bindings.getOperationBinding("trfStatus");
         op1.execute();
        Integer status=(Integer)(op1.getResult()); */
        Integer status=null;
        String st=valueChangeEvent.getNewValue().toString();
        OperationBinding op=bindings.getOperationBinding("setItemCancel");
        op.getParamsMap().put("st",st);
        op.getParamsMap().put("oldst",status);
        op.execute();
        if(op.getErrors().size()==0)
            System.out.println("No error");
        else
            System.out.println("Error is="+op.getErrors());
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelColumn); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(cnclItm);
    }

    public void cancelItmVCL(ValueChangeEvent valueChangeEvent) {
        Integer status1=null;
        OperationBinding st = getBindings().getOperationBinding("trfStatus");
                       st.execute(); 
                       if(st.getErrors().size()==0)
                       {
                           status1 = (Integer)st.getResult();
                           }
        if(valueChangeEvent.getNewValue().toString().equals("true"))
        {
        //check for all items in current docid
        OperationBinding ca = getBindings().getOperationBinding("checkAllItemCancel");
                       ca.execute(); 
                       if(ca.getErrors().size()==0)
                       {
                           Boolean bool = (Boolean)ca.getResult();
                           if(bool==true)
                           {
                                   cancelTrfBinding.setValue(true);
                               }
                           else
                           {
                               //set status as before.and uncheck cancel
                               cancelTrfBinding.setValue(false); 
                               }
                           }
            }
        else
        {
                OperationBinding setst = getBindings().getOperationBinding("setTrfStat");
                
                setst.getParamsMap().put("stat",status1);
                setst.execute(); 
                cancelTrfBinding.setValue(false);  
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelTrfBinding);
    }

    public void setCancelTrfBinding(RichSelectBooleanCheckbox cancelTrfBinding) {
        this.cancelTrfBinding = cancelTrfBinding;
    }

    public RichSelectBooleanCheckbox getCancelTrfBinding() {
        return cancelTrfBinding;
    }
    
    public void authQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        ordQtybinding.processUpdates(facesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(ordQtybinding);
        if(object!=null){
        Number Auth = (Number)object;
        if(Auth.compareTo(0)>=0)
        {
            Boolean is=    isPrecisionValid(26, 6, Auth);
            if(is.toString().equalsIgnoreCase("true"))
            {
                OperationBinding chk = getBindings().getOperationBinding("checkAuthQty");
                               chk.getParamsMap().put("Auth", Auth);
                               chk.execute();
                       Integer value=Integer.parseInt(chk.getResult().toString());
                       if(value <= 0)
                       {
                       }else{ 
                               if(value==1)
                               {
                                           throw new ValidatorException(new
                                           FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Authorised Quantity.",null));
                               }
                               else
                                   if(value==2)
                               {
                                       throw new ValidatorException(new
                                       FacesMessage(FacesMessage.SEVERITY_ERROR,"Authorised Quantity can not be given without Order Quantity.",null));
                                   }
                       
                           }  
            }
            else
            {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Precision(26,6).",null));
                }
       
        }
        else
        {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity can not be less than Zero.",null));
            }
        }
        else
        {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity Required.",null));
            }
        /* OperationBinding op=getBindings().getOperationBinding("ExecuteItmVo");
        op.execute(); */
    }

    public void ResQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Caling validator");
        ordQtybinding.processUpdates(facesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(ordQtybinding);
        authQtyBinding.processUpdates(facesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(authQtyBinding);   
        if(object !=null){
        Number Res = (Number)object;
        if(Res.compareTo(0)>=0)
        {
        
            Boolean is=    isPrecisionValid(26, 6, Res);
            if(is.toString().equalsIgnoreCase("true"))
            {
                OperationBinding chk = getBindings().getOperationBinding("checkResQty");
                               chk.getParamsMap().put("Res", Res);
                               chk.execute();
                       Integer value=Integer.parseInt(chk.getResult().toString());
                       if(value <= 0)
                       {
                       }
                       else{
                           if(value==1)
                           {
                               throw new ValidatorException(new
                               FacesMessage(FacesMessage.SEVERITY_ERROR,"Reserved Quantity can not be greater than Available Stock or Authorised Quantity.",null));
                           }
                           else
                               if(value==2)
                           {
                                   throw new ValidatorException(new
                                   FacesMessage(FacesMessage.SEVERITY_ERROR,"Reserve Quantity can not be given without Authorised Quantity",null));
                               }
                           } 
            }
            else
            {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Precision(26,6).",null));
                }
        
        }
        else
        {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity can not be less than Zero.",null));
            }
        }
        else
        {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity Required.",null)); 
            }
       /*  OperationBinding op=getBindings().getOperationBinding("ExecuteItmVo");
        op.execute(); */
    }

    public void setAuthCB(RichSelectBooleanCheckbox authCB) {
        this.authCB = authCB;
    }

    public RichSelectBooleanCheckbox getAuthCB() {
        return authCB;
    }

    public void ordQtyVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
        Number qty=new Number(0);
            try {
                qty = new Number(valueChangeEvent.getNewValue().toString());
            } catch (SQLException e) {
                System.out.println("Error in string to number conversion");
            }
            OperationBinding chk = getBindings().getOperationBinding("ChangeinOrdQty");
                       chk.getParamsMap().put("qty", qty);
                       chk.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(authQtyBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkResvQtyBinding);
    } 
    }

    public void AuthQtyVCL(ValueChangeEvent valueChangeEvent) {
       // ChangeinAuthQty
        OperationBinding chk = getBindings().getOperationBinding("ChangeinAuthQty");
                  chk.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkResvQtyBinding);             
    }

    public void setOrdQtybinding(RichInputText ordQtybinding) {
        this.ordQtybinding = ordQtybinding;
    }

    public RichInputText getOrdQtybinding() {
        return ordQtybinding;
    }

    public void setAuthQtyBinding(RichInputText authQtyBinding) {
        this.authQtyBinding = authQtyBinding;
    }

    public RichInputText getAuthQtyBinding() {
        return authQtyBinding;
    }

    public void setStkResvQtyBinding(RichInputText stkResvQtyBinding) {
        this.stkResvQtyBinding = stkResvQtyBinding;
    }

    public RichInputText getStkResvQtyBinding() {
        return stkResvQtyBinding;
    }

    public void setCancelColumn(RichColumn cancelColumn) {
        this.cancelColumn = cancelColumn;
    }

    public RichColumn getCancelColumn() {
        return cancelColumn;
    }

    public void setCnclItm(RichSelectBooleanCheckbox cnclItm) {
        this.cnclItm = cnclItm;
    }

    public RichSelectBooleanCheckbox getCnclItm() {
        return cnclItm;
    }
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
    JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

    vc.setPrecision(precision);


    vc.setScale(scale);

    return vc.validateValue(total);
    }

    public void ordQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null)
        {
        Number obj = (Number)object;
        if(obj.compareTo(new Number(0))>0)
        {
            Boolean is=    isPrecisionValid(26, 6, obj);
            if(is.toString().equalsIgnoreCase("true"))
            {}
            else
            {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Precision(26,6).",null));
                }
            }
        else
        {
                throw new ValidatorException(new
                FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantity must be greater than Zero.",null));
            }
            }
    }

    public void stockDetailAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding Obinding=getBindings().getOperationBinding("stockFilter");
        Obinding.execute();
        showPopup(stockDetailPoup, true);
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
    public void setStockDetailPoup(RichPopup stockDetailPoup) {
        this.stockDetailPoup = stockDetailPoup;
    }

    public RichPopup getStockDetailPoup() {
        return stockDetailPoup;
    }
}
