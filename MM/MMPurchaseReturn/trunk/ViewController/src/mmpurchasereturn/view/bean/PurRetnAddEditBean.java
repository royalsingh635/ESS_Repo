package mmpurchasereturn.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import java.util.ArrayList;

import java.util.HashSet;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichToolbar;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class PurRetnAddEditBean {

    public static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(PurRetnAddEditBean.class);
    private RichInputListOfValues mrnNoBinding;
    private RichInputDate mrnDocDtBinding;
    private String mode = resolvEl("#{pageFlowScope.Page_Mode}").toString();
    private RichSelectOneChoice whIdBinding;
    private RichInputListOfValues eoIdBinding;
    private RichPopup viewdetailpop;
    private RichToolbar toolbaarbind;
    private RichSelectOneChoice purchaseRtnBind;
    private RichLink saveAndFwdBind;
    private RichInputListOfValues itemDescSuppBind;
    private RichSelectOneChoice purRtnTypeBind;
    private RichInputText itmUomSuppDescBind;
    private RichTable purRtnSrcTableBind;
    private RichPopup lotBinPopBind;
    private RichPopup lotPopBind;
    private RichPopup lotSerialPopBind;
    private RichTable prItemTableBind;
    private RichTable lotStkTableBind;
    private RichLink saveLinkBinding;

    public PurRetnAddEditBean() {
    }

    public void createButtonAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("CreateInsert");
        op.execute();
        mode = "C";
    }

    public void editButtonAL(ActionEvent actionEvent) {
        Integer pending = 0;
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18529);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {

            mode = "E";
        } else {
            String name = "Anonymous";
            OperationBinding op = getBindings().getOperationBinding("getUserName");
            op.getParamsMap().put("usrId", pending);
            op.execute();
            if (op.getErrors().size() == 0 && op.getResult() != null)
                name = (String) op.getResult();
            //Document is Pending at [" + name + "]. you can't Forward this.
            String msg =
                resolvElDCMsg("#{bundle['MSG.1758']}").toString() + name +
                resolvElDCMsg("#{bundle['MSG.2063']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        }

    }

    public void saveButtonAL(ActionEvent actionEvent) {
        //check stock qty
        OperationBinding stkQty = getBindings().getOperationBinding("checkStockQtyBfrSaved");
        stkQty.execute();
        if (stkQty.getResult() != null) {
            adfLog.info("-----retrn chek-- " + stkQty.getResult());
            if ((Integer) stkQty.getResult() == -1) {
                //There is not Enough Stock for selected Item's in the selected Lot(s) / Bin(s) / Serial(s).
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2064']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return;
            }
        }

        //generate PR no
        OperationBinding genprop = getBindings().getOperationBinding("generatePRNo");
        genprop.execute();

        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //get Wf Id

        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = 0;
        Integer res = -1;

        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", SlocId);
        WfnoOp.getParamsMap().put("CldId", CldId);
        WfnoOp.getParamsMap().put("OrgId", OrgId);
        WfnoOp.getParamsMap().put("DocNo", 18529);
        WfnoOp.execute();
        if (WfnoOp.getErrors().size() == 0 && WfnoOp.getResult() != null) {
            WfNum = WfnoOp.getResult().toString();
        }

        if (WfNum != null) {
            //get user level

            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", SlocId);
            usrLevelOp.getParamsMap().put("CldId", CldId);
            usrLevelOp.getParamsMap().put("OrgId", OrgId);
            usrLevelOp.getParamsMap().put("UsrId", UsrId);
            usrLevelOp.getParamsMap().put("WfNo", WfNum);
            usrLevelOp.getParamsMap().put("DocNo", 18529);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getErrors().size() == 0 && usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
            }

            if (level >= 0) {
                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", SlocId);
                insTxnOp.getParamsMap().put("CldId", CldId);
                insTxnOp.getParamsMap().put("OrgId", OrgId);
                insTxnOp.getParamsMap().put("DocNo", 18529);
                insTxnOp.getParamsMap().put("WfNo", WfNum);
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

                OperationBinding op = getBindings().getOperationBinding("Commit");
                op.execute();
                if (op.getErrors().size() == 0) {
                    mode = "V";
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.802']}").toString()); //Purchase Return Saved Successfully!
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                //Something went wrong (User level in Workflow).Please Contact to ESS!
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1875']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            //Workflow not Created for Purchase Return
            //Workflow not Defined for this Document.
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1486']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void cancelButtonAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("Rollback");
        op.execute();
        mode = "V";
    }


    public void addMrnButtonAL(ActionEvent actionEvent) {
        if (purRtnTypeBind.getValue() != null && purRtnTypeBind.getValue().toString().length() > 0) {
            adfLog.info("----type--------" + purRtnTypeBind.getValue().toString());


            if (purRtnTypeBind.getValue().toString().equalsIgnoreCase("888")) {
                adfLog.info("-----inside MRN type---------");
                OperationBinding opPrRtn = getBindings().getOperationBinding("MrnValid");
                opPrRtn.execute();
                if (opPrRtn.getResult() != null) {
                    String mrnChk = opPrRtn.getResult().toString();
                    if ("N".equalsIgnoreCase(mrnChk)) {
                        //Invoice not created for all the Item in this MRN
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2065']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(mrnNoBinding.getClientId(), message);
                        return;
                    }
                }
                OperationBinding opfy = getBindings().getOperationBinding("GenerateFyId");
                opfy.execute();
                if (opfy.getErrors().size() == 0 && opfy.getResult() != null &&
                    opfy.getResult().toString().equals("Y")) {

                    if (whIdBinding.getValue() != null && !(whIdBinding.getValue().toString().equals(""))) {
                        if (eoIdBinding.getValue() != null && !(eoIdBinding.getValue().toString().equals(""))) {
                            if (mrnNoBinding.getValue() != null && !(mrnNoBinding.getValue().toString().equals(""))) {


                                OperationBinding chkDupli = getBindings().getOperationBinding("ChkDuplicateMrn");
                                chkDupli.execute();
                                String dupli = "N";
                                if (chkDupli.getErrors().size() == 0 && chkDupli.getResult() != null)
                                    dupli = (String) chkDupli.getResult();
                                adfLog.info("Is dupli? " + dupli);
                                if (dupli.equals("N")) {
                                    OperationBinding addMrn = getBindings().getOperationBinding("AddMrnNoToSrc");
                                    addMrn.execute();
                                    adfLog.info("-----after 11111-------------");
                                    OperationBinding addItm = getBindings().getOperationBinding("AddItmToPurRetItm");
                                    addItm.execute();
                                    adfLog.info("-----after 222-------------");
                                    OperationBinding resetTrans = getBindings().getOperationBinding("ResetTrans");
                                    resetTrans.execute();
                                    mrnNoBinding.setValue("");
                                    mrnNoBinding.setValue(null);
                                    mrnNoBinding.resetValue();
                                    mrnDocDtBinding.setValue("");
                                    mrnDocDtBinding.setValue(null);
                                    mrnDocDtBinding.resetValue();
                                    mode = "A";

                                } else if (dupli.equals("Y")) {
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.796']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(mrnNoBinding.getClientId(), message);
                                } else if (dupli.equals("D")) {
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.798']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(mrnNoBinding.getClientId(), message);
                                }
                            } else {
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.799']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(mrnNoBinding.getClientId(), message);
                            }
                        } else {
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(eoIdBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.800']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(whIdBinding.getClientId(), message);
                    }
                } else {
                    if (opfy.getErrors().size() > 0)
                        System.out.println("error calculating Fy=" + opfy.getErrors());
                    else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.801']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            } else if (purRtnTypeBind.getValue().toString().equalsIgnoreCase("887")) {
                adfLog.info("-----inside store type---------");
                OperationBinding opfy = getBindings().getOperationBinding("GenerateFyId");
                opfy.execute();
                if (opfy.getErrors().size() == 0 && opfy.getResult() != null &&
                    opfy.getResult().toString().equals("Y")) {

                    if (whIdBinding.getValue() != null && !(whIdBinding.getValue().toString().equals(""))) {
                        if (eoIdBinding.getValue() != null && !(eoIdBinding.getValue().toString().equals(""))) {
                            if (itemDescSuppBind.getValue() != null &&
                                !(itemDescSuppBind.getValue().toString().equals(""))) {

                                if (purRtnSrcTableBind.getRowCount() == 0) {
                                    OperationBinding genDocIdOp =
                                        getBindings().getOperationBinding("generateTempDocId");
                                    genDocIdOp.execute();
                                }
                                OperationBinding chkDupli = getBindings().getOperationBinding("checkDupItem");
                                chkDupli.execute();
                                String chk = "N";
                                if (chkDupli.getErrors().size() == 0 && chkDupli.getResult() != null)
                                    chk = (String) chkDupli.getResult();
                                if (chk.equals("N")) {
                                    OperationBinding addItmOp = getBindings().getOperationBinding("addPurRtnItm");
                                    addItmOp.execute();
                                    if (addItmOp.getErrors().isEmpty()) {
                                        System.out.println("-------Item added");

                                        itemDescSuppBind.setValue("");

                                        itmUomSuppDescBind.setValue("");

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDescSuppBind);
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomSuppDescBind);
                                    }


                                } else {
                                    //Duplicate Record Found.
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.46']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }


                            } else {
                                //Item name is required.
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.430']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(mrnNoBinding.getClientId(), message);
                            }
                        } else {
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(eoIdBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.800']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(whIdBinding.getClientId(), message);
                    }
                } else {
                    if (opfy.getErrors().size() > 0)
                        System.out.println("error calculating Fy=" + opfy.getErrors());
                    else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.801']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }

            }
        } else {
            //Please Select Purchase Return Type.
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2066']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


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

    public void setMrnNoBinding(RichInputListOfValues mrnNoBinding) {
        this.mrnNoBinding = mrnNoBinding;
    }

    public RichInputListOfValues getMrnNoBinding() {
        return mrnNoBinding;
    }

    public void setMrnDocDtBinding(RichInputDate mrnDocDtBinding) {
        this.mrnDocDtBinding = mrnDocDtBinding;
    }

    public RichInputDate getMrnDocDtBinding() {
        return mrnDocDtBinding;
    }

    public void setWhIdBinding(RichSelectOneChoice whIdBinding) {
        this.whIdBinding = whIdBinding;
    }

    public RichSelectOneChoice getWhIdBinding() {
        return whIdBinding;
    }

    public void setEoIdBinding(RichInputListOfValues eoIdBinding) {
        this.eoIdBinding = eoIdBinding;
    }

    public RichInputListOfValues getEoIdBinding() {
        return eoIdBinding;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String saveAndFwdbuttonAL() {

        //check stock qty
        OperationBinding stkQty = getBindings().getOperationBinding("checkStockQtyBfrSaved");
        stkQty.execute();
        if (stkQty.getResult() != null) {
            adfLog.info("-----retrn chek--" + stkQty.getResult());
            if ((Integer) stkQty.getResult() == -1) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.2064']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
        }


        OperationBinding genprop = getBindings().getOperationBinding("generatePRNo");
        genprop.execute();

        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //get Wf Id

        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = 0;
        Integer res = -1;

        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", SlocId);
        WfnoOp.getParamsMap().put("CldId", CldId);
        WfnoOp.getParamsMap().put("OrgId", OrgId);
        WfnoOp.getParamsMap().put("DocNo", 18529);
        WfnoOp.execute();
        if (WfnoOp.getErrors().size() == 0 && WfnoOp.getResult() != null) {
            WfNum = WfnoOp.getResult().toString();
        }
        if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {

            //get user level

            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", SlocId);
            usrLevelOp.getParamsMap().put("CldId", CldId);
            usrLevelOp.getParamsMap().put("OrgId", OrgId);
            usrLevelOp.getParamsMap().put("UsrId", UsrId);
            usrLevelOp.getParamsMap().put("WfNo", WfNum);
            usrLevelOp.getParamsMap().put("DocNo", 18529);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getErrors().size() == 0 && usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
            }

            if (level >= 0) {
                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", SlocId);
                insTxnOp.getParamsMap().put("CldId", CldId);
                insTxnOp.getParamsMap().put("OrgId", OrgId);
                insTxnOp.getParamsMap().put("DocNo", 18529);
                insTxnOp.getParamsMap().put("WfNo", WfNum);
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

                //Check Pending
                Integer pending = 0;
                OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                chkUsr.getParamsMap().put("SlocId", SlocId);
                chkUsr.getParamsMap().put("CldId", CldId);
                chkUsr.getParamsMap().put("OrgId", OrgId);
                chkUsr.getParamsMap().put("DocNo", 18529);
                chkUsr.execute();

                if (chkUsr.getResult() != null) {
                    pending = Integer.parseInt(chkUsr.getResult().toString());
                }
                if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
                    OperationBinding op = getBindings().getOperationBinding("Commit");
                    op.execute();
                    if (op.getErrors().isEmpty()) {
                        mode = "V";
                    }
                    return "toWf";
                } else {
                    String name = "Anonymous";
                    OperationBinding op = getBindings().getOperationBinding("getUserName");
                    op.getParamsMap().put("usrId", pending);
                    op.execute();
                    if (op.getErrors().size() == 0 && op.getResult() != null)
                        name = (String) op.getResult();
                    //  String msg = "Document is Pending at [" + name + "]. you can't Forward this.";
                    String msg =
                        resolvElDCMsg("#{bundle['MSG.1758']}").toString() + name +
                        resolvElDCMsg("#{bundle['MSG.2063']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    return null;
                }
            } else {
                //Something went wrong (User level in Workflow). Please Contact to ESS!
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1875']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
        } else {
            //Workflow not Created for Purchase Return.
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1486']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
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

    public void viewStockAL(ActionEvent actionEvent) {
        OperationBinding exe = getBindings().getOperationBinding("executeVO");
        exe.execute();
        showPopup(viewdetailpop, true);
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

    public void setViewdetailpop(RichPopup viewdetailpop) {
        this.viewdetailpop = viewdetailpop;
    }

    public RichPopup getViewdetailpop() {
        return viewdetailpop;
    }

    public void setToolbaarbind(RichToolbar toolbaarbind) {
        this.toolbaarbind = toolbaarbind;
    }

    public RichToolbar getToolbaarbind() {
        return toolbaarbind;
    }

    public void setPurchaseRtnBind(RichSelectOneChoice purchaseRtnBind) {
        this.purchaseRtnBind = purchaseRtnBind;
    }

    public RichSelectOneChoice getPurchaseRtnBind() {
        return purchaseRtnBind;
    }

    public void getRefreshlov() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(purchaseRtnBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(purchaseRtnBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);
    }

    public void setSaveAndFwdBind(RichLink saveAndFwdBind) {
        this.saveAndFwdBind = saveAndFwdBind;
    }

    public RichLink getSaveAndFwdBind() {
        return saveAndFwdBind;
    }

    public void setItemDescSuppBind(RichInputListOfValues itemDescSuppBind) {
        this.itemDescSuppBind = itemDescSuppBind;
    }

    public RichInputListOfValues getItemDescSuppBind() {
        return itemDescSuppBind;
    }

    public void setPurRtnTypeBind(RichSelectOneChoice purRtnTypeBind) {
        this.purRtnTypeBind = purRtnTypeBind;
    }

    public RichSelectOneChoice getPurRtnTypeBind() {
        return purRtnTypeBind;
    }

    public void setItmUomSuppDescBind(RichInputText itmUomSuppDescBind) {
        this.itmUomSuppDescBind = itmUomSuppDescBind;
    }

    public RichInputText getItmUomSuppDescBind() {
        return itmUomSuppDescBind;
    }

    public void setPurRtnSrcTableBind(RichTable purRtnSrcTableBind) {
        this.purRtnSrcTableBind = purRtnSrcTableBind;
    }

    public RichTable getPurRtnSrcTableBind() {
        return purRtnSrcTableBind;
    }

    public void selectLotAE(ActionEvent actionEvent) {
        Integer lotTable = lotStkTableBind.getRowCount();
        OperationBinding op = getBindings().getOperationBinding("executeLotSuppVO");
        op.execute();
        if (lotTable == 0) {
            adfLog.info("----lot if length ---" + lotTable);
            showPopup(lotPopBind, true);
        } else {
            OperationBinding opL = getBindings().getOperationBinding("setLotSuppValue");
            opL.execute();
            adfLog.info("----lot else length ---" + lotTable);
            showPopup(lotPopBind, true);
        }
    }

    public void selectBinAE(ActionEvent actionEvent) {
        Integer lotTable = lotStkTableBind.getRowCount();
        OperationBinding op = getBindings().getOperationBinding("executeLotBinSuppVO");
        op.execute();
        if (lotTable == 0) {
            adfLog.info("----lot bin if length ---" + lotTable);
            showPopup(lotBinPopBind, true);
        } else {
            OperationBinding opL = getBindings().getOperationBinding("setLotBinSuppValue");
            opL.execute();
            adfLog.info("----lot bin else length ---" + lotTable);
            showPopup(lotBinPopBind, true);
        }

    }

    public void selectSerialAE(ActionEvent actionEvent) {
        Integer lotTable = lotStkTableBind.getRowCount();
        OperationBinding op = getBindings().getOperationBinding("executeLotSrSuppVO");
        op.execute();
        if (lotTable == 0) {
            adfLog.info("----lot sr if length ---" + lotTable);
            showPopup(lotSerialPopBind, true);
        } else {
            OperationBinding opL = getBindings().getOperationBinding("setLotSRSuppValue");
            opL.execute();
            adfLog.info("----lot sr else length ---" + lotTable);
            showPopup(lotSerialPopBind, true);
        }
    }

    public void lotPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            OperationBinding opL = getBindings().getOperationBinding("validateLotQty");
            opL.execute();
            if (opL.getResult() != null) {
                HashSet retnLot = (HashSet) opL.getResult();
                adfLog.info("-----size array in pop--" + retnLot.size());
                if (retnLot.size() > 0) {
                    //Invalid quantity found in the following Lot's:
                    StringBuffer bf = new StringBuffer(resolvElDCMsg("#{bundle['MSG.2067']}").toString() + " ");
                    bf.append(retnLot);
                    adfLog.info("------suf-------" + bf.toString());
                    FacesMessage message = new FacesMessage(bf.toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    return;
                } else {
                    OperationBinding opD = getBindings().getOperationBinding("deleteAftrUpdateLot");
                    opD.execute();

                    OperationBinding op = getBindings().getOperationBinding("populateItemFromLot");
                    op.execute();
                    adfLog.info("--------------populateItemFromLot-------------");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prItemTableBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotStkTableBind);
                    //for refresh link
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkBinding);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);

                }
            } else {
                adfLog.info("-------error in validateLotQty.........");
            }
        }
    }

    public void lotBinDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            OperationBinding opL = getBindings().getOperationBinding("validateLotBinQty");
            opL.execute();
            if (opL.getResult() != null) {
                HashSet retnLot = (HashSet) opL.getResult();
                adfLog.info("-----size array in pop--" + retnLot.size());
                if (retnLot.size() > 0) {
                    //Invalid quantity found in the following Bin's:
                    StringBuffer bf = new StringBuffer(resolvElDCMsg("#{bundle['MSG.2068']}").toString() + " ");
                    bf.append(retnLot);
                    adfLog.info("------suf-------" + bf.toString());
                    FacesMessage message = new FacesMessage(bf.toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    return;
                } else {
                    OperationBinding opD = getBindings().getOperationBinding("deleteAftrUpdateLot");
                    opD.execute();

                    OperationBinding op = getBindings().getOperationBinding("populateFromLotBin");
                    op.execute();
                    adfLog.info("--------------populateItemFromLotBin-------------");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prItemTableBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotStkTableBind);
                    //for refresh link
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkBinding);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);

                }
            } else {
                adfLog.info("-------error in validateLotBinQty.........");
            }

        }
    }

    public void setLotBinPopBind(RichPopup lotBinPopBind) {
        this.lotBinPopBind = lotBinPopBind;
    }

    public RichPopup getLotBinPopBind() {
        return lotBinPopBind;
    }

    public void setLotPopBind(RichPopup lotPopBind) {
        this.lotPopBind = lotPopBind;
    }

    public RichPopup getLotPopBind() {
        return lotPopBind;
    }

    public void setLotSerialPopBind(RichPopup lotSerialPopBind) {
        this.lotSerialPopBind = lotSerialPopBind;
    }

    public RichPopup getLotSerialPopBind() {
        return lotSerialPopBind;
    }

    public void lotSerialDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            OperationBinding opD = getBindings().getOperationBinding("deleteAftrUpdateLot");
            opD.execute();


            OperationBinding op = getBindings().getOperationBinding("populateFromLotBinSr");
            op.execute();
            adfLog.info("--------------populateItemFromSR-------------");
            AdfFacesContext.getCurrentInstance().addPartialTarget(prItemTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotStkTableBind);
            //for refresh link
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveLinkBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);
        }
    }

    public void setPrItemTableBind(RichTable prItemTableBind) {
        this.prItemTableBind = prItemTableBind;
    }

    public RichTable getPrItemTableBind() {
        return prItemTableBind;
    }

    public void setLotStkTableBind(RichTable lotStkTableBind) {
        this.lotStkTableBind = lotStkTableBind;
    }

    public RichTable getLotStkTableBind() {
        return lotStkTableBind;
    }

    public void stkRowRemoveAE(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("deleteStktableRow");
        op.execute();

    }

    public void setSaveLinkBinding(RichLink saveLinkBinding) {
        this.saveLinkBinding = saveLinkBinding;
    }

    public RichLink getSaveLinkBinding() {
        return saveLinkBinding;
    }

    public String costCenterHeaderAction() {
        OperationBinding ccFinalize = getBindings().getOperationBinding("ccFinalizedCheck");
        ccFinalize.execute();
        adfLog.info("-----------ccFinalizedCheck--------------" + ccFinalize.getResult());
        if (ccFinalize.getResult() != null) {
            if (ccFinalize.getResult().toString().equalsIgnoreCase("N")) {
                //Profit Centre Profile is not setup properly.
                //showFacesMessage(resolvElDCMsg("#{bundle['MSG.2455']}").toString(), "I", false, "F", null);
                ADFModelUtils.showFacesMessage(resolvElDCMsg("#{bundle['MSG.2455']}").toString(),
                                               resolvElDCMsg("#{bundle['MSG.2455']}").toString(),
                                               FacesMessage.SEVERITY_INFO, null);

                return null;
            }
        }

        OperationBinding binding = getBindings().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        adfLog.info("---chkCcApplicableOrNot---" + binding.execute());
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    public String costCenterItemAction() {

        OperationBinding ccFinalize = getBindings().getOperationBinding("ccFinalizedCheck");
        ccFinalize.execute();
        if (ccFinalize.getResult() != null) {
            if (ccFinalize.getResult().toString().equalsIgnoreCase("N")) {
                //Profit Center Profile is not setup properly.
                ADFModelUtils.showFacesMessage(resolvElDCMsg("#{bundle['MSG.2455']}").toString(),
                                               resolvElDCMsg("#{bundle['MSG.2455']}").toString(),
                                               FacesMessage.SEVERITY_INFO, null);
                return null;
            }
        }
        /*       if (itmAmtBind.getValue() != null && ((Number) itmAmtBind.getValue()).compareTo(new Number(0)) == 0) {
            //Amount must be greater than zero
            showFacesMessage(evaluateEL("#{bundle['MSG.382']}").toString(), "E", false, "F", null);
            return null;
        } */
        OperationBinding binding = getBindings().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateCostCenterAmt").execute();
            return "costCenter";
        } else {
            return null;
        }
    }
}
