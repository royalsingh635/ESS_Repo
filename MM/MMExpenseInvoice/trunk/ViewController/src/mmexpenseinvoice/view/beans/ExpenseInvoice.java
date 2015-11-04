package mmexpenseinvoice.view.beans;

import adf.utils.bean.ADFBeanUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.domain.Number;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;


public class ExpenseInvoice {
    private RichPanelGroupLayout srcPnlGrpBind;
    private RichInputListOfValues invcSuppBindVar1;
    private RichTreeTable treeTabBind;
    private RichPanelGroupLayout ocSrcPnlbind;
    private RichPopup applyTaxPopup;
    private static ADFLogger _log = (ADFLogger) ADFLogger.createADFLogger(ExpenseInvoice.class);
    Number zero = new Number(0);
    private RichTable trLinesTableBind;

    public ExpenseInvoice() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void searchOcDescAction(ActionEvent actionEvent) {
        System.out.println("----------------------------");
        if (invcSuppBindVar1.getValue() != null && invcSuppBindVar1.getValue().toString().length() > 0) {
            System.out.println(" supp value--------" + invcSuppBindVar1.getValue().toString() + "  " +
                               invcSuppBindVar1.getValue().toString().length());
            OperationBinding obind = getBindings().getOperationBinding("searchOcData");
            obind.execute();
        } else {
            System.out.println(" supp value--------" + invcSuppBindVar1.getValue().toString() + "  " +
                               invcSuppBindVar1.getValue().toString().length());
            showFacesMessage("Supplier is Required.", "E", false, "F");
            return;
        }
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("Rollback");
        obind.execute();
        OperationBinding obind1 = getBindings().getOperationBinding("resetSearchOC");
        obind1.execute();
        System.out.println(" cancel--------" + obind1.getResult());
    }

    public void editActionListener(ActionEvent actionEvent) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
    }

    public void saveActionListener(ActionEvent actionEvent) {

        OperationBinding obind1 = getBindings().getOperationBinding("generateExpenceNo");
        obind1.execute();

        OperationBinding obind = getBindings().getOperationBinding("Commit");
        obind.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
        showFacesMessage("Record saved successfully.", "I", false, "F");
    }

    public void saveAndFwdActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void proceedActionListener(ActionEvent actionEvent) {
        OperationBinding getcb = getBindings().getOperationBinding("countCkbx");
        getcb.execute();
        Integer countCB = new Integer(0);

        if (getcb.getResult() != null) {
            countCB = (Integer) getcb.getResult();
        }
        System.out.println(" result of count cb " + getcb.getResult() + "---" + countCB);

        if (countCB > 0) {
            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", null);
            geteo.getParamsMap().put("eoName", invcSuppBindVar1.getValue().toString());
            geteo.execute();
            // System.out.println("OUT : "+geteo.getResult());
            if (geteo.getResult() != null) {
                Integer coaId = (Integer) geteo.getResult();
                if (coaId < 0) {
                    String msg = resolvEl("#{bundle['LBL.3107']}").toString();
                    //Cannot select this supplier.Error in COA (Multiple).
                    showFacesMessage(msg, "E", false, "F");
                    return;
                } else {
                    /*  OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                setCoa.getParamsMap().put("coa", coaId);
                setCoa.execute(); */
                }
            } else {
                return;
            }


            OperationBinding obind = getBindings().getOperationBinding("proceedDataToSource");
            obind.execute();
            System.out.println("------------" + obind.getResult());
            ocSrcPnlbind.setVisible(false);
            srcPnlGrpBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ocSrcPnlbind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srcPnlGrpBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabBind);
        } else {
            showFacesMessage("Please Select Document No.", "E", false, "F");
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

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
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
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void setSrcPnlGrpBind(RichPanelGroupLayout srcPnlGrpBind) {
        this.srcPnlGrpBind = srcPnlGrpBind;
    }

    public RichPanelGroupLayout getSrcPnlGrpBind() {
        return srcPnlGrpBind;
    }

    public void setInvcSuppBindVar1(RichInputListOfValues invcSuppBindVar1) {
        this.invcSuppBindVar1 = invcSuppBindVar1;
    }

    public RichInputListOfValues getInvcSuppBindVar1() {
        return invcSuppBindVar1;
    }

    public void setTreeTabBind(RichTreeTable treeTabBind) {
        this.treeTabBind = treeTabBind;
    }

    public RichTreeTable getTreeTabBind() {
        return treeTabBind;
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
        check.getParamsMap().put("DocNo", 18543);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {

            OperationBinding geteo = getBindings().getOperationBinding("generateExpenceNo");
            geteo.execute();
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
            WfnoOp.getParamsMap().put("DocNo", 18543);
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
                usrLevelOp.getParamsMap().put("DocNo", 18543);
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
                    insTxnOp.getParamsMap().put("DocNo", 18543);
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
                    chkUsr.getParamsMap().put("DocNo", 18543);
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
                                     "F");
                    return null;
                }
            } else {
                showFacesMessage("Workflow not Created for Expense Invoice", "E", false, "F");
                return null;
            }
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Expense Invoice is Pending at [" + name + "]. You can't forward this Expense Invoice.";


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        return null;
    }

    public void setOcSrcPnlbind(RichPanelGroupLayout ocSrcPnlbind) {
        this.ocSrcPnlbind = ocSrcPnlbind;
    }

    public RichPanelGroupLayout getOcSrcPnlbind() {
        return ocSrcPnlbind;
    }

    public void taxItemOnAddAction(ActionEvent actionEvent) {
        // Add event code here...

        RichLink ob = (RichLink) actionEvent.getSource();
        String docIdSrc = null;
        String expId = null;
        Number taxableAmt = new Number(0);
        if (ob.getAttributes().get("expId") != null) {
            expId = ob.getAttributes().get("expId").toString();
        }
        if (ob.getAttributes().get("docIdSrc") != null) {
            docIdSrc = ob.getAttributes().get("docIdSrc").toString();
        }

        if (ob.getAttributes().get("expAmtSp") != null) {
            taxableAmt = (Number) ob.getAttributes().get("expAmtSp");
        }


        if (expId == null || docIdSrc == null || taxableAmt.compareTo(new Number(0)) == 0) {

            showFacesMessage("can not proceed tax ", "I", false, "F");
            return;
        }
        _log.info(expId + " expId  " + taxableAmt + "  taxableAmt " + docIdSrc + "   docIdSrc");

        ADFBeanUtils.showPopup(applyTaxPopup, true);
        OperationBinding addTaxOp = ADFBeanUtils.getOperationBinding("createNewTaxRule");
        addTaxOp.getParamsMap().put("docIdSrc", docIdSrc);
        addTaxOp.getParamsMap().put("expId", expId);
        addTaxOp.getParamsMap().put("taxableAmt", taxableAmt);
        addTaxOp.execute();
    }

    public void setApplyTaxPopup(RichPopup applyTaxPopup) {
        this.applyTaxPopup = applyTaxPopup;
    }

    public RichPopup getApplyTaxPopup() {
        return applyTaxPopup;
    }

    public String removeTaxAction() {
        // Add event code here...

        OperationBinding ob = ADFBeanUtils.getOperationBinding("resetTrAndTrLine");
        ob.execute();
        applyTaxPopup.hide();
        return null;
    }

    public void taxRuleVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("populateTaxRuleLines");

            ob.getParamsMap().put("taxRuleNm", vce.getNewValue());
            ob.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(trLinesTableBind);
        }
    }

    public void taxableAmtVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            _log.info("loff " + vce.getComponent().getAttributes().get("expIdV"));
            //           ValueChangeEvent vc= (ValueChangeEvent)vce.getSource();
            //           vce.getComponent().getAttributes().get("expIdV")
            OperationBinding ob = ADFBeanUtils.getOperationBinding("applyTaxDirectLine");
            ob.getParamsMap().put("taxableAmt", vce.getNewValue());
            ob.getParamsMap().put("docId", vce.getComponent().getAttributes().get("docIdV"));
            ob.getParamsMap().put("docIdSrc", vce.getComponent().getAttributes().get("docIdSrcV"));
            ob.getParamsMap().put("expId", vce.getComponent().getAttributes().get("expIdV"));
            ob.getParamsMap().put("expType", vce.getComponent().getAttributes().get("expTypeV"));
            ob.execute();
        }
    }

    public void addManualOcAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding obind = getBindings().getOperationBinding("chkManualOcDetails");
        obind.execute();
        if (obind.getResult() != null) {
            Integer ret = (Integer) obind.getResult();
            if (ret.compareTo(new Integer(1)) == 0) {
                showFacesMessage("Please select file No.", "W", false, "F");
                return;

            } else if (ret.compareTo(new Integer(2)) == 0) {
                showFacesMessage("Please Select OC Name.", "W", false, "F");
                return;

            } else if (ret.compareTo(new Integer(3)) == 0) {
                showFacesMessage("OC Amount Required.", "W", false, "F");
                return;


            } else if (ret.compareTo(new Integer(4)) == 0) {
                showFacesMessage("Oc Amount must be greater than zero.", "W", false, "F");
                return;
            }
        }

        OperationBinding obChkDupli = getBindings().getOperationBinding("checkOcDuplicate");
        obChkDupli.execute();
        if (obChkDupli.getResult() != null && ((Integer) obChkDupli.getResult()).compareTo(new Integer(1)) == 0) {
            showFacesMessage("Duplicate Record Found.", "W", false, "F");
            return;
        }

        OperationBinding obInsert = getBindings().getOperationBinding("addManualOcDetails");
        obInsert.execute();

    }

    public void taxableAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (zero.compareTo((Number) object) == 1) {
                showFacesMessage("Amount must be greater than zero", "E", false, "V");
            }
        }

    }

    public void setTrLinesTableBind(RichTable trLinesTableBind) {
        this.trLinesTableBind = trLinesTableBind;
    }

    public RichTable getTrLinesTableBind() {
        return trLinesTableBind;
    }
}
