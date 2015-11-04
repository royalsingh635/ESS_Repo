package mmbillofentryapp.view.beans;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

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

import mmbillofentryapp.model.services.BillOfEntryAMImpl;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

public class BillOfEntryAddEditBean {
    private static ADFLogger _log = (ADFLogger) ADFLogger.createADFLogger(BillOfEntryAddEditBean.class);
    private RichPopup otherChargesPopup;
    private RichPopup applytaxPopup;
    private Integer taxRule = null;
    private Integer taxRuleitem = null;
    private Number taxableAmount = new Number(0);
    private Number itmtaxamt1 = new Number(0);
    private String taxExmpt = "N";
    private String taxFlg = "N";
    private String taxruleflg = "I";
    Number zero = new Number(0);
    private String taxPopulate = "N";
    private String mode = modeGet();
    private RichLink reApplyTaxBind;
    private RichTable taxTabBind;
    private RichInputText taxAmtBind;
    private RichInputListOfValues eoIdBind;
    private RichTable ocTableBind;

    public BillOfEntryAddEditBean() {
    }

    public String create() {
        // Add event code here...
        setMode("A");
        return "createBeoOnPage";
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void editAction(ActionEvent actionEvent) {
        Integer sloc_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        //   Integer usr_Id=EbizParams.GLBL_APP_USR();
        _log.info("sloc_Id " + sloc_Id);
        int userId = 0;
        OperationBinding check = ADFBeanUtils.findOperation("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("RfqDocNo", 18539);
        check.execute();


        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            setMode("A");
            //    RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = ADFBeanUtils.findOperation("getUsrNm");
            getusrname.getParamsMap().put("userId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            //String message1= "This Bill Of Entry is Pending at [";
            // String message2= "]. You can't Edit this Bill Of Entry.";
            String message1 = resolvElDCMsg("#{bundle['MSG.1895']}").toString();
            String message2 = resolvElDCMsg("#{bundle['MSG.1896']}").toString();


            String msg = message1 + name + message2;
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

        // Add event code here...

    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
        Integer ret = 0;

        OperationBinding obchek = ADFBeanUtils.findOperation("checkAllOcApply");
        obchek.execute();
        if (obchek.getResult() != null) {
            ret = Integer.parseInt(obchek.getResult().toString());
        }
        _log.info("ret :::: " + ret);
        if (ret.compareTo(new Integer(1)) == 0) {
            // String message1="All required other charges not added in current Import Purchase Costing.";
            //String message2="All required other charges not added in current Import Purchase Costing.";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1857']}").toString();
            String msg2 = resolvElDCMsg("#{bundle['MSG.1897']}").toString();


            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);


            return;
        }

        String temp = "N";
        // commenet 09-07

        //        OperationBinding obTaxCheck = ADFBeanUtils.findOperation("checkAllTaxApply");
        //        obTaxCheck.execute();
        //        if (obTaxCheck.getResult() != null) {
        //            temp = obTaxCheck.getResult().toString();
        //        } else {
        //            _log.info("error in message call is " + obTaxCheck.getErrors());
        //        }

        if ("Y".equalsIgnoreCase(temp)) {
            return;
        }

        Integer sloc_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        //   Integer usr_Id=EbizParams.GLBL_APP_USR();
        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = 0;
        Integer res = -1;
        //int amount=0;
        OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
        WfnoOp.getParamsMap().put("sloc_id", sloc_Id);
        WfnoOp.getParamsMap().put("cld_id", cld_Id);
        WfnoOp.getParamsMap().put("org_id", org_Id);
        WfnoOp.getParamsMap().put("doc_no", 18539);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            if (WfnoOp.getResult() != null)
                WfNum = WfnoOp.getResult().toString();
            System.out.println("WfNum is : " + WfNum);
        }

        if (WfNum != null) {
            //get user level
            OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
            usrLevelOp.getParamsMap().put("CldId", cld_Id);
            usrLevelOp.getParamsMap().put("OrgId", org_Id);
            usrLevelOp.getParamsMap().put("usr_id", usr_Id);
            usrLevelOp.getParamsMap().put("WfNum", WfNum);
            usrLevelOp.getParamsMap().put("RfqDocId", 18539);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
                System.out.println("user level" + level);
            }

            if (level >= 0) { //insert into txn
                OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                insTxnOp.getParamsMap().put("sloc_id", sloc_Id);
                insTxnOp.getParamsMap().put("cld_id", cld_Id);
                insTxnOp.getParamsMap().put("pOrgId", org_Id);
                insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18539);
                insTxnOp.getParamsMap().put("WfNum", WfNum);
                insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());

                    System.out.println("txn save output" + res);
                    String tableNm = "MM$BOE";
                    Integer fyNo = 0;
                    OperationBinding fyIdOp = ADFBeanUtils.findOperation("getFYid");
                    fyIdOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                    fyIdOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                    //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
                    fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
                    fyIdOp.getParamsMap().put("Mode", "A");
                    fyIdOp.execute();
                    if (fyIdOp.getResult() != null) {
                        fyNo = Integer.parseInt(fyIdOp.getResult().toString());
                    }
                    _log.info("Fy id in bean ---------" + fyNo);
                    OperationBinding boeNoOp = ADFBeanUtils.findOperation("genBeoNo");
                    boeNoOp.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                    boeNoOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                    boeNoOp.getParamsMap().put("HoOrgId", EbizParams.GLBL_HO_ORG_ID());
                    boeNoOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                    boeNoOp.getParamsMap().put("TableName", tableNm);
                    boeNoOp.getParamsMap().put("fyId", fyNo);
                    boeNoOp.execute();
                    String ids = null;
                    if (boeNoOp.getResult() != null) {
                        ids = boeNoOp.getResult().toString();
                        _log.info("new generated issue id " + ids);
                    }
                    //String msg1="Record save successfully";
                    //String msg2="Record save successfully";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                    String msg2 = resolvElDCMsg("#{bundle['MSG.91']}").toString();

                    ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_INFO, null);


                    ADFBeanUtils.getOperationBinding("Commit").execute();
                    setMode("V");

                }


                // showFacesMessage("Service Contract  Save Successfully", "I", false, "F", null);


            } else {
                /*  showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                         false, "F", null); */
                //String msg1="Something went wrong(User Level in Workflow).Please Contact to ESS!";
                //String msg2="Something went wrong(User Level in Workflow).Please Contact to ESS!";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                String msg2 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();

                ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);


            }
        } else {
            // String msg1="Workflow not Created for Bill of Entry";
            // String msg1="Workflow not Created for Bill of Entry";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1898']}").toString();
            String msg2 = resolvElDCMsg("#{bundle['MSG.1898']}").toString();
            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);

        }
    }

    public String cancelAction() {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        setMode(" ");
        return "back";
    }

    public void addDocumentAction(ActionEvent actionEvent) {
        // Add event code here...
        _log.info("call function ");
        ADFBeanUtils.findOperation("addSupplierAndCurrency").execute();

        OperationBinding binding = ADFBeanUtils.findOperation("insetDataFromPo");
        binding.execute();
    }

    public String saveAndFarword() {

        Integer ret = 0;
        OperationBinding obchek = ADFBeanUtils.findOperation("checkAllOcApply");
        obchek.execute();
        if (obchek.getResult() != null) {
            ret = Integer.parseInt(obchek.getResult().toString());
        }
        _log.info("ret :::: " + ret);
        if (ret.compareTo(new Integer(1)) == 0) {
            // String msg1="All required other charges not added in current Import Purchase Costing.";
            // String msg2="All required other charges not added in current Import Purchase Costing.";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1897']}").toString();
            String msg2 = resolvElDCMsg("#{bundle['MSG.1897']}").toString();
            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);


            return null;
        }

        String temp = "N";
        // commenet 09-07-2015


                OperationBinding obTaxCheck = ADFBeanUtils.findOperation("checkAllTaxApply");
                obTaxCheck.execute();
                if (obTaxCheck.getResult() != null) {
                    temp = obTaxCheck.getResult().toString();
                } else {
                    _log.info("error in message call is " + obTaxCheck.getErrors());
                }

        if ("Y".equalsIgnoreCase(temp)) {
            return null;
        }
        int userId = 0;
        Integer sloc_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        //   Integer usr_Id=EbizParams.GLBL_APP_USR();
        _log.info("sloc_Id " + sloc_Id);
        OperationBinding check = ADFBeanUtils.findOperation("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("RfqDocNo", 18539);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            String action = "I";
            String remark = "A";
            String WfNum = null;
            Integer level = 0;
            Integer res = -1;
            Integer pending = 0;
            OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_Id);
            WfnoOp.getParamsMap().put("cld_id", cld_Id);
            WfnoOp.getParamsMap().put("org_id", org_Id);
            WfnoOp.getParamsMap().put("doc_no", 18539);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                if (WfnoOp.getResult() != null)
                    WfNum = WfnoOp.getResult().toString();
                System.out.println("WfnoOp : " + WfNum);
            }

            if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                //get user level
                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                usrLevelOp.getParamsMap().put("CldId", cld_Id);
                usrLevelOp.getParamsMap().put("OrgId", org_Id);
                usrLevelOp.getParamsMap().put("usr_id", usr_Id);
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.getParamsMap().put("RfqDocId", 18539);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                    System.out.println("user level " + level);
                }

                if (level >= 0) {
                    //insert into txn
                    OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_Id);
                    insTxnOp.getParamsMap().put("cld_id", cld_Id);
                    insTxnOp.getParamsMap().put("pOrgId", org_Id);
                    insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18539);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
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
                    OperationBinding chkUsr = ADFBeanUtils.findOperation("pendingCheck");
                    chkUsr.getParamsMap().put("SlocId", sloc_Id);
                    chkUsr.getParamsMap().put("CldId", cld_Id);
                    chkUsr.getParamsMap().put("OrgId", org_Id);
                    chkUsr.getParamsMap().put("RfqDocNo", 18539);
                    chkUsr.execute();

                    if (chkUsr.getResult() != null) {
                        pending = Integer.parseInt(chkUsr.getResult().toString());
                        System.out.println("pending check" + pending);
                    }
                    if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                        System.out.println("txn save output" + res);
                        String tableNm = "MM$BOE";
                        Integer fyNo = 0;
                        OperationBinding fyIdOp = ADFBeanUtils.findOperation("getFYid");
                        fyIdOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                        fyIdOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                        //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
                        fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
                        fyIdOp.getParamsMap().put("Mode", "A");
                        fyIdOp.execute();
                        if (fyIdOp.getResult() != null) {
                            fyNo = Integer.parseInt(fyIdOp.getResult().toString());
                        }
                        _log.info("Fy id in bean ---------" + fyNo);
                        OperationBinding boeNoOp = ADFBeanUtils.findOperation("genBeoNo");
                        boeNoOp.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                        boeNoOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                        boeNoOp.getParamsMap().put("HoOrgId", EbizParams.GLBL_HO_ORG_ID());
                        boeNoOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                        boeNoOp.getParamsMap().put("TableName", tableNm);
                        boeNoOp.getParamsMap().put("fyId", fyNo);
                        boeNoOp.execute();
                        String ids = null;
                        if (boeNoOp.getResult() != null) {
                            ids = boeNoOp.getResult().toString();
                            _log.info("new generated issue id " + ids);
                        }

                        OperationBinding saveOp = ADFBeanUtils.findOperation("Commit");
                        saveOp.execute();
                        setMode("V");

                        return "towf";
                    }
                } else {
                    //  ADFModelUtils.showFacesMessage("Record save successfully", "Record save successfully .",FacesMessage.SEVERITY_INFO, null);
                    //String msg1="Something went wrong(User Level in Workflow).Please Contact to ESS!";
                    //String msg2="Something went wrong(User Level in Workflow).Please Contact to ESS!";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();

                    ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);


                    return null;
                }
            } else {
                //showFacesMessage("Workflow not Created for Service Contract", "E", false, "F", null);
                // String msg1="Workflow not Created for Bill of Entry";
                // String msg1="Workflow not Created for Bill of Entry";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1898']}").toString();
                String msg2 = resolvElDCMsg("#{bundle['MSG.1898']}").toString();
                ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);

                return null;
            }


        } else {
            String name = "Anonymous";
            OperationBinding getusrname = ADFBeanUtils.findOperation("getUsrNm");
            getusrname.getParamsMap().put("userId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();

            // String msg1= "This Bill of Entry is Pending at [";
            // String msg2="]. You can't forward this Bill of Entry
            String msg1 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
            String msg2 = resolvElDCMsg("#{bundle['MSG.1899']}").toString();
            String msg = msg1 + name + msg2;


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        return null;

    }

    public void setOtherChargesPopup(RichPopup otherChargesPopup) {
        this.otherChargesPopup = otherChargesPopup;
    }

    public RichPopup getOtherChargesPopup() {
        return otherChargesPopup;
    }

    public void selectOtherChargeAction(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(otherChargesPopup, true);
        // Add event code here...
    }

    public void addOcAction(ActionEvent actionEvent) {
        // Add event code here...
        RichLink ob = (RichLink) actionEvent.getSource();
        // ob.getAttributes().get("taxableAmtSpDefault");
        _log.info(" add oc action ::");
        String thirdParty = "N";
        Integer coaId =0;
        String ocCalcType ="A";
        Number ocCalcVal =new Number(0);
        String ocId = (String) ob.getAttributes().get("ocId");
        if(ob.getAttributes().get("coaId")!=null){
         coaId = (Integer) ob.getAttributes().get("coaId");
        }
        Integer ocCat = (Integer) ob.getAttributes().get("ocCat");
        if (ob.getAttributes().get("thirdParty") != null) {
            thirdParty = (String) ob.getAttributes().get("thirdParty");
        }
        if (ob.getAttributes().get("ocCalcType") != null) {
            ocCalcType = (String) ob.getAttributes().get("ocCalcType");
        }
        if (ob.getAttributes().get("ocCalcVal") != null) {
            ocCalcVal = (Number) ob.getAttributes().get("ocCalcVal");
        }
        Integer isDup = 0;
        OperationBinding opB = ADFBeanUtils.findOperation("isOcDuplicate");
        opB.getParamsMap().put("ocId", ocId);
        opB.execute();
        if (opB.getResult() != null) {
            isDup = Integer.parseInt(opB.getResult().toString());
        }
        if (isDup.compareTo(new Integer(1)) == 0) {
             showFacesMessage("Duplicate OC found", "W", false, "F");
            return;
        }

        _log.info("ocId " + ocId + " coaId " + coaId + " ocCat " + ocCat + " thirdParty " + thirdParty+" ocCalcType "+ocCalcType+" ocCalcVal "+ocCalcVal);
        OperationBinding obAdd = ADFBeanUtils.findOperation("addOthercharges");
        obAdd.getParamsMap().put("ocId", ocId);
        obAdd.getParamsMap().put("coaId", coaId);
        obAdd.getParamsMap().put("ocCat", ocCat);
        obAdd.getParamsMap().put("thirdParty", thirdParty);
        obAdd.getParamsMap().put("ocCalcType", ocCalcType);
        obAdd.getParamsMap().put("ocCalcVal", ocCalcVal);
        obAdd.execute();
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

    public void addAllRequiredOtherCharges(ActionEvent actionEvent) {
        // Add event code here...
        _log.info(" addAllRequiredOtherCharges  ");
        OperationBinding obAdd = ADFBeanUtils.findOperation("addAllRequiredOC");
        obAdd.execute();
    }

    public void ocAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (zero.compareTo((Number) object) == 1) {
             showFacesMessage("Oc amount must be positive", "E", false, "V");
            }
        }
//        else {
//            // showFacesMessage("Oc amount required", "E", false, "V");
//        }

    }

    public void ocCurrencyVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            _log.info("vce.getNewValue() " + vce.getNewValue());
            OperationBinding ob = ADFBeanUtils.findOperation("changeOcCurrncy");
            ob.getParamsMap().put("curr", vce.getNewValue());
            ob.execute();

        }

    }


    public void applyDirectTaxInBoe(ActionEvent actionEvent) {
        // Add event code here...
        // Add event code here...
        String retFlg = "Y";
        _log.info("call direct tax in bean");
        OperationBinding ob = ADFBeanUtils.findOperation("applyDirectTax");
        ob.execute();
        // ADFBeanUtils.findOperation("applyDirectTax").execute();

        if (ob.getResult() != null) {
            retFlg = ob.getResult().toString();
        }

        if ("Y".equalsIgnoreCase(retFlg)) {
            ADFModelUtils.showFacesMessage("Default tax apply successfully . ", "Default tax apply successfully . ",
                                           FacesMessage.SEVERITY_INFO, null);
            // showFacesMessage("Default tax apply successfully . ", "I", false, "F");
            return;
        } else if ("N".equalsIgnoreCase(retFlg)) {
            ADFModelUtils.showFacesMessage("Tax is not applicable on this Item . ",
                                           "Tax is not applicable on this Item . ", FacesMessage.SEVERITY_WARN, null);
            // showFacesMessage("Tax is not applicable on this Item . ", "W", false, "F");

            return;
        } else if ("R".equalsIgnoreCase(retFlg)) {
            ADFModelUtils.showFacesMessage("No Tax Rule define for current supplier. ",
                                           "No Tax Rule define for current supplier.", FacesMessage.SEVERITY_WARN,
                                           null);
            //  showFacesMessage("No Tax Rule define for current supplier.", "W", false, "F");
            return;
        } else if ("E".equalsIgnoreCase(retFlg)) {
            ADFModelUtils.showFacesMessage("Item or supplier may be not enterd.", "Item or supplier may be not enterd.",
                                           FacesMessage.SEVERITY_WARN, null);
            //  showFacesMessage("Item or supplier may be not enterd.", "W", false, "F");
            return;
        }


    }

    public void setApplytaxPopup(RichPopup applytaxPopup) {
        this.applytaxPopup = applytaxPopup;
    }

    public RichPopup getApplytaxPopup() {
        return applytaxPopup;
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

    public BillOfEntryAMImpl getAm() {
        return (BillOfEntryAMImpl) resolvElDC("BillOfEntryAMDataControl");
    }

    public void applyItemWiseTax(ActionEvent actionEvent) {
        Number taxAbleAmt = new Number(0);
        Number purPrice = new Number(0);
        ViewObjectImpl voSrc = getAm().getMmBoeSrc();
        Row rcptSrc = voSrc.getCurrentRow();
        ViewObject itmVo = getAm().getMmBoeItm();
        Row itmCurr = itmVo.getCurrentRow();
        String itmId = itmCurr.getAttribute("ItmId").toString();
        String itmuom = itmCurr.getAttribute("ItmUom").toString();
        if (itmCurr != null) {
            /* if (itmCurr.getAttribute("ItmPrice") != null && itmCurr.getAttribute("ItmPrice") != new Number(0) &&
                 itmCurr.getAttribute("OrdQty") != null && itmCurr.getAttribute("OrdQty") != new Number(0) &&
                 ((Number)itmPriceBind.getValue()).compareTo(zero) == 1 &&
                 ((Number)itmQtyBind.getValue()).compareTo(zero) == 1) { */
            String flg = "N";
            getAm().getLovItmDesc().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
            getAm().getLovItmDesc().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            getAm().getLovItmDesc().setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            getAm().getLovItmDesc().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            getAm().getLovItmDesc().setNamedWhereClauseParam("ItmIdBind", itmId);
            getAm().getLovItmDesc().setNamedWhereClauseParam("ItmDescBind", null);
            getAm().getLovItmDesc().executeQuery();

            Row[] itms = getAm().getLovItmDesc().getFilteredRows("ItmId", itmId);
            _log.info("lenth   " + itms.length);
            if (itms.length > 0) {
                if (itms[0].getAttribute("TaxExmptFlg") != null) {
                    flg = itms[0].getAttribute("TaxExmptFlg").toString();
                }
            }


            // String flg = (String)itmCurr.getAttribute("TransTaxExmptFlg");
            if (flg.equals("N")) {
                ViewObject vo = getAm().getMmBoe();
                String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
                String docIdSrc = rcptSrc.getAttribute("DocIdSrc").toString();
                Integer dlvSchdlNo = Integer.parseInt(rcptSrc.getAttribute("DlvSchdlNo").toString());
                ViewObjectImpl trVo = getAm().getMmBoeTr1();
                RowQualifier rqtr = new RowQualifier(trVo);
                rqtr.setWhereClause("DocId='" + docId + "' and DocIdSrc = '" + docIdSrc + "' and DlvSchdlNo = " +
                                    dlvSchdlNo + " AND ItmId='" + itmId + "' and ItmUom='" + itmuom + "'");
                Row[] r = trVo.getFilteredRows(rqtr);
                _log.info(rqtr.getExprStr() + "    No. of rows in tr of above ids=" + r.length);
                //String tflg = poVo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
                String tflg = "N"; // taxable amount change or not

                if (r.length > 0) {
                  //  getAm().getMmBoeTr().executeQuery();
                    itmtaxamt1 = (Number) (r[0].getAttribute("TaxableAmtSp"));
                    taxRuleitem = (Integer) r[0].getAttribute("TaxRuleId");
                    if (r[0].getAttribute("TaxExmptFlg") != null) { // 12341 change for tax exmpt flg null when rate contranct thourch quotation.19-09-14
                        _log.info("::::::   " + r[0].getAttribute("TaxExmptFlg"));
                        taxExmpt = r[0].getAttribute("TaxExmptFlg").toString();
                    } else {
                        _log.info("tax TaxExmptFlg nulll    ::::::::");
                    }
                    if (r[0].getAttribute("TaxRuleFlg") != null) {
                        _log.info("::::::   " + r[0].getAttribute("TaxRuleFlg"));
                        taxruleflg = r[0].getAttribute("TaxRuleFlg").toString();
                    } else {
                        _log.info("tax TaxRuleFlg nulll    ::::::::");
                    }
                    /*
                    OperationBinding trLines=ADFBeanUtils.findOperation("trLineDelete");
                    trLines.execute();
                    getAm().getMmBoeTrLines().executeQuery();
                    OperationBinding trBind=ADFBeanUtils.findOperation("TrDelete");
                    trBind.execute(); */
                }


                if (r.length > 0 && tflg.equalsIgnoreCase("N")) {
                    _log.info("tr line if part ::: ");
                    ADFBeanUtils.showPopup(applytaxPopup, true);
                } else {
                    _log.info("tr line else part ::: ");
                    tflg = "Y";
                    //remove tax if r.length>0 and tflg is "Y" tflg flage is Y when taxable amount change.
                    if (r.length > 0 && tflg.equalsIgnoreCase("Y")) {
                        _log.info("tr line else part 2::: ");
                        //  ViewObjectImpl voline = getAm().getMmDrftPoTrLines1();
                        ViewObjectImpl voline = getAm().getMmBoeTrLines();

                        RowSetIterator rql = voline.createRowSetIterator(null);
                        while (rql.hasNext()) {
                            Row frline = rql.next();
                            if (frline.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                frline.getAttribute("ItmId").toString().equalsIgnoreCase(itmId)) {
                                //       System.out.println("Removing TRline="+frline.getKey());



                                //    frline.remove();
                            }
                        }
                        rql.closeRowSetIterator();
                        voline.executeQuery();


                        RowSetIterator rstr = trVo.createRowSetIterator(null);
                        while (rstr.hasNext()) {
                            Row rtr = rstr.next();
                            Integer dlvNo = Integer.parseInt(rtr.getAttribute("DlvSchdlNo").toString());
                            if (rtr.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                rtr.getAttribute("DocIdSrc").toString().equalsIgnoreCase(docIdSrc) &&
                                dlvNo.compareTo(dlvSchdlNo) == 0 &&
                                rtr.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) &&
                                rtr.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom)) {
                                _log.info("Removing TR=" + rtr.getKey());
                                rtr.remove();
                            }
                        }
                        _log.info("tr line else part 3::: ");
                        rstr.closeRowSetIterator();
                        trVo.executeQuery();

                    }


                    _log.info("create tax ");
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("CreateWithParams");
                    usrLevelOp.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                    usrLevelOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                    usrLevelOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                    // usrLevelOp.getParamsMap().put("UsrIdCreate", EbizParams.GLBL_APP_USR());
                    usrLevelOp.getParamsMap().put("DocId", docId);
                    usrLevelOp.getParamsMap().put("DocIdSrc", docIdSrc);
                    usrLevelOp.getParamsMap().put("DlvSchdlNo", dlvSchdlNo);
                    usrLevelOp.getParamsMap().put("ItmId", itmId);
                    usrLevelOp.getParamsMap().put("ItmUom", itmuom);
                    usrLevelOp.getParamsMap().put("TaxRuleFlg", "I");
                    usrLevelOp.getParamsMap().put("TaxableAmtSp", (Number) itmCurr.getAttribute("TaxableAmtSp"));
                    usrLevelOp.execute();
                    _log.info("call tax  popup");
                    ADFBeanUtils.showPopup(applytaxPopup, true);
                }
            } else {
                //String msg="Tax is not Applicable on this Item";
                //                String msg = resolvElDCMsg("#{bundle['MSG.1689']}").toString();
                //
                //                FacesMessage message = new FacesMessage(msg);
                //                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                FacesContext fc = FacesContext.getCurrentInstance();
                //                fc.addMessage(null, message);
                showFacesMessage("Tax is not Applicable on this Item", "W", false, "F");
                return;
            }

            /*  } else {
                 FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.408']}"));
                 message.setSeverity(FacesMessage.SEVERITY_INFO);
                 FacesContext fc = FacesContext.getCurrentInstance();
                 fc.addMessage(null, message);
             } */
        }


        // Add event code here...
        ADFBeanUtils.showPopup(applytaxPopup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(reApplyTaxBind);
    }

    public void taxRuleVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            String taxRuleNm = vce.getNewValue().toString();
            _log.info(" taxRuleNm " + taxRuleNm);
            OperationBinding ob = ADFBeanUtils.findOperation("applyItemTax");
            ob.getParamsMap().put("taxRule", taxRuleNm);
            ob.execute();
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmtBind);
        }
    }

    public String modeGet() {


        return (String) ADFModelUtils.resolvEl("#{pageFlowScope.Add_Edit_Mode}");
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public void otherChargesDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            _log.info("call dilog list");
            Integer retV = 0;
            Integer retTP = 0;
            OperationBinding opB = ADFBeanUtils.findOperation("chkAllQcAmtEnter");
            opB.execute();
            if (opB.getResult() != null) {
                retV = Integer.parseInt(opB.getResult().toString());
            }
            OperationBinding opTP = ADFBeanUtils.findOperation("chkAllThirdPartySupplierSelect");
            opTP.execute();
            if (opTP.getResult() != null) {
                retTP = Integer.parseInt(opTP.getResult().toString());
            }

            _log.info(" retV " + retV);
            _log.info(" retV " + retTP);

            if (retV.compareTo(new Integer(1)) == 0) {
                // String msg1="Some Other charges amount zero.";
                // String msg2="Some Other charges amount zero.";

                String msg1 = resolvElDCMsg("#{bundle['MSG.1900']}").toString();
                String msg2 = resolvElDCMsg("#{bundle['MSG.1900']}").toString();

                ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);


            } else if (retTP.compareTo(new Integer(1)) == 0) {
                ADFModelUtils.showFacesMessage("Some supplier not selected for third party other charges. ",
                                               "Some supplier not selected for third party other charges. ",
                                               FacesMessage.SEVERITY_ERROR, null);

            }

            else {

                ADFBeanUtils.findOperation("setTaxAmtfromOc").execute();
            }
        }
    }

    public void deleteOCAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.findOperation("deleteOtherCharge").execute();
    }

    public void taxableAmountVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            Number amt = (Number) vce.getNewValue();
            _log.info("arg0  " + vce.getNewValue());
            OperationBinding ob = ADFBeanUtils.findOperation("applyTaxInTaxableAmtVCL");
            ob.getParamsMap().put("amt", amt);
            ob.execute();
        }
    }

    public void ovelAllReApplyTax(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("reApplyTax");
        ob.execute();
    }

    public void setReApplyTaxBind(RichLink reApplyTaxBind) {
        this.reApplyTaxBind = reApplyTaxBind;
    }

    public RichLink getReApplyTaxBind() {
        return reApplyTaxBind;
    }

    public void taxableAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number taxamt = (Number) object;

            if (zero.compareTo(taxamt) == 1) {
                showFacesMessage("Amount must be positive", "E", false, "V");
                //                ADFModelUtils.showFacesMessage("Amount must be positive ", "Amount must be positive ",
                //                                               FacesMessage.SEVERITY_ERROR, null);
            }
        }

    }

    public void setTaxTabBind(RichTable taxTabBind) {
        this.taxTabBind = taxTabBind;
    }

    public RichTable getTaxTabBind() {
        return taxTabBind;
    }

    public void setTaxAmtBind(RichInputText taxAmtBind) {
        this.taxAmtBind = taxAmtBind;
    }

    public RichInputText getTaxAmtBind() {
        return taxAmtBind;
    }

    public void setEoIdBind(RichInputListOfValues eoIdBind) {
        this.eoIdBind = eoIdBind;
    }

    public RichInputListOfValues getEoIdBind() {
        return eoIdBind;
    }

    public void docNoVCL(ValueChangeEvent vce) {
        if(vce.getNewValue()!=null){
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdBind);
        }
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public String removeTaxAction() {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("resetTrAndTrLine");
        ob.execute();
        
        applytaxPopup.hide();
        return null;
    }

    public void ocCalcValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (zero.compareTo((Number) object) == 1) {
             showFacesMessage("Oc calculation value must be positive", "E", false, "V");
            }
        }

    }

    public void ocQtyValidatior(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (zero.compareTo((Number) object) == 1) {
             showFacesMessage("Oc Quantity must be positive", "E", false, "V");
            }
        }

    }

    public void ocCalcOnPctValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (zero.compareTo((Number) object) == 1) {
             showFacesMessage("Po amount must be positive", "E", false, "V");
            }
        }

    }
}
