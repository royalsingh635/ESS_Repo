package mmshipmentadviceapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

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

import mmshipmentadviceapp.model.services.MmShipmentAdviceAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

public class MMShipmentAdviceBean {


    public static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(MMShipmentAdviceBean.class);
    private RichInputListOfValues bindPoNumber;
    private RichInputText bindShpQty;
    private RichInputDate etaBinding;

    public MMShipmentAdviceBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void populatePOItems(ActionEvent actionEvent) {
        // Add event code here...
        if (bindPoNumber.getValue() != null && bindPoNumber.getValue() != "") {

            //            adfLog.info("in the if loop");
            if (etaBinding.getValue() != null && etaBinding.getValue() != "") {

                OperationBinding b1 = ADFBeanUtils.getOperationBinding("populatePoitem");
                b1.execute();
            } else {
                String s = ADFModelUtils.resolvRsrc("MSG.2424");
                //               Estimated Date Of Arrival is required.//MSG.2424
                showFacesMessage(s, "E", false, "F", etaBinding.getClientId());
            }

        } else {
            //            adfLog.info("in the else loop");
            //  String msg ="PO No. is required";
            //            String msg = resolvElDCMsg("#{bundle['MSG.1887']}").toString();
            String msg = ADFModelUtils.resolvRsrc("MSG.1887");
            showFacesMessage(msg, "E", false, "F", bindPoNumber.getClientId());

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

    public void saveShipmentAdviceListener(ActionEvent actionEvent) {
        // Add event code here...
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        MmShipmentAdviceAMImpl am = (MmShipmentAdviceAMImpl) resolvElDC("MmShipmentAdviceAMDataControl");
        adfLog.info("Value" + am.getMmShpAdvc1().getCurrentRow().getAttribute("AuthStat"));
        String auth = (String) am.getMmShpAdvc1().getCurrentRow().getAttribute("AuthStat");
        adfLog.info("value of auth::" + auth);
        OperationBinding op1 = ADFBeanUtils.getOperationBinding("dateValidate");
        op1.execute();

        //checkQtyValidator
        OperationBinding QtyVal = ADFBeanUtils.getOperationBinding("checkQtyValidator");
        QtyVal.execute();
        if (QtyVal.getResult() != null) {
            //            Order Quantity and Shipping Quantity Mismatch for some Item
            if (((Integer) QtyVal.getResult()).compareTo(new Integer(1)) == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.2425");
                showFacesMessage(s, "E", false, "F", null);
                return;
            }
        }
        adfLog.info("dateValidate::" + op1.getResult());
        if (op1.getResult() != null && op1.getResult().equals("N")) {
            if (auth.equals("N")) {

                String action = "I";
                String remark = "A";
                String WfNum = null;
                Integer level = 0;
                Integer res = -1;
                //int amount=0;
                OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("SlocId", p_sloc_id);
                WfnoOp.getParamsMap().put("CldId", p_cldId);
                WfnoOp.getParamsMap().put("OrgId", p_org_id);
                WfnoOp.getParamsMap().put("DocNo", 18538);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    if (WfnoOp.getResult() != null)
                        WfNum = WfnoOp.getResult().toString();
                    System.out.println("WfNum is : " + WfNum);
                }

                if (WfNum != null) {
                    //get user level
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", p_sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", p_cldId);
                    usrLevelOp.getParamsMap().put("OrgId", p_org_id);
                    usrLevelOp.getParamsMap().put("UsrId", p_userId);
                    usrLevelOp.getParamsMap().put("WfNo", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 18538);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                        System.out.println("user level" + level);
                    }

                    if (level >= 0) { //insert into txn
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("SlocId", p_sloc_id);
                        insTxnOp.getParamsMap().put("CldId", p_cldId);
                        insTxnOp.getParamsMap().put("OrgId", p_org_id);
                        insTxnOp.getParamsMap().put("DocNo", 18538);
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
                        OperationBinding b1 = ADFBeanUtils.getOperationBinding("genShipNo");
                        b1.execute();

                        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
                        saveOp.execute();
                        //disablebtn = true;
                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                        //setMode("V");
                        // String msg = "Record Saved Successfully";
                        String msg = ADFModelUtils.resolvRsrc("MSG.91");
                        //                        String msg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                        showFacesMessage(msg, "I", false, "F", null);

                    } else {
                        //String msg = "Something went wrong(User Level in Workflow).Please Contact to ESS!";
                        String msg = ADFModelUtils.resolvRsrc("MSG.1875");
                        //                        String msg = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                        showFacesMessage(msg, "E", false, "F", null);

                    }
                } else {
                    //String msg = "Workflow is not created for Shipment Advice.";
                    //                    String msg = resolvElDCMsg("#{bundle['MSG.1876']}").toString();
                    String msg = ADFModelUtils.resolvRsrc("MSG.1876");

                    showFacesMessage(msg, "E", false, "F", null);

                }
            }
        } else {
            //  String msg="Date of Departure  should be less than Date of Arrival.";
            //            String msg = resolvElDCMsg("#{bundle['MSG.1877']}").toString();
            String msg = ADFModelUtils.resolvRsrc("MSG.1877");
            showFacesMessage(msg, "E", false, "F", null);

        }
    }


    //        OperationBinding b1 = ADFBeanUtils.getOperationBinding("genShipNo");
    //        b1.execute();
    //        ADFBeanUtils.findOperation("Commit").execute();
    // }

    public void setBindPoNumber(RichInputListOfValues bindPoNumber) {
        this.bindPoNumber = bindPoNumber;
    }

    public RichInputListOfValues getBindPoNumber() {
        return bindPoNumber;
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


    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void editShipmentAdviceActionListener(ActionEvent actionEvent) {
        // Add event code here...

        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = ADFBeanUtils.getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18538);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = ADFBeanUtils.getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            // String message1 = "This Shipment Advice is Pending at [";
            // String message2 = "]. You can't Edit this  Shipment Advice.";
            //            String message1 = resolvElDCMsg("#{bundle['MSG.1879']}").toString();
            String message1 = ADFModelUtils.resolvRsrc("MSG.1879");
            String message2 = ADFModelUtils.resolvRsrc("MSG.1880");
            //            String message2 = resolvElDCMsg("#{bundle['MSG.1880']}").toString();


            String msg = message1 + name + message2;

            //String msg = resolveEldcMSg(bundle[msg.1879]).toString + name + resolveEldcMsg(bundle[msg.1880]).toString;

            // String msg = resolvElDCMsg("#{bundle['msg.1879']}").toString();

            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public String saveAndForwardActionListener() {
        // Add event code here...


        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = ADFBeanUtils.getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18538);
        check.execute();
        OperationBinding QtyVal = ADFBeanUtils.getOperationBinding("checkQtyValidator");
        QtyVal.execute();
        if (QtyVal.getResult() != null) {
            if (((Integer) QtyVal.getResult()).compareTo(new Integer(1)) == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.2425");
                showFacesMessage(s, "E", false, "F", null);
                return null;
            }
        }
        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }

        //        OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
        //        resettax.execute();
        //        String istxble = null;
        //        if (resettax.getErrors().isEmpty()) {
        //            istxble = resettax.getResult().toString();
        //            adfLog.info("txble check result " + istxble);
        //        }
        //        if ("Y".equalsIgnoreCase(istxble)) {
        //            OperationBinding chkAmt = getBindings().getOperationBinding("chkPmtAmt");
        //            chkAmt.execute();
        //            String chkPmt = null;
        //            if (chkAmt.getResult() != null)
        //                chkPmt = chkAmt.getResult().toString();
        //            if ("Y".equalsIgnoreCase(chkPmt)) {
        if (usr_Id == userId || userId == -1) {
            //            OperationBinding geteo = getBindings().getOperationBinding("genPrNo");
            //            geteo.execute();
            String action = "I";
            String remark = "A";
            String WfNum = null;
            Integer level = 0;
            Integer res = -1;
            Integer pending = 0;


            OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", sloc_Id);
            WfnoOp.getParamsMap().put("CldId", cld_Id);
            WfnoOp.getParamsMap().put("OrgId", org_Id);
            WfnoOp.getParamsMap().put("DocNo", 18538);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                if (WfnoOp.getResult() != null)
                    WfNum = WfnoOp.getResult().toString();
                System.out.println("WfnoOp : " + WfNum);
            }

            if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                //get user level
                OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                usrLevelOp.getParamsMap().put("CldId", cld_Id);
                usrLevelOp.getParamsMap().put("OrgId", org_Id);
                usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 18538);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                    System.out.println("user level " + level);
                }

                if (level >= 0) {
                    //insert into txn
                    OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                    insTxnOp.getParamsMap().put("CldId", cld_Id);
                    insTxnOp.getParamsMap().put("OrgId", org_Id);
                    insTxnOp.getParamsMap().put("DocNo", 18538);
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
                    OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
                    chkUsr.getParamsMap().put("SlocId", sloc_Id);
                    chkUsr.getParamsMap().put("CldId", cld_Id);
                    chkUsr.getParamsMap().put("OrgId", org_Id);
                    chkUsr.getParamsMap().put("DocNo", 18538);
                    chkUsr.execute();

                    if (chkUsr.getResult() != null) {
                        pending = Integer.parseInt(chkUsr.getResult().toString());
                        System.out.println("pending check" + pending);
                    }
                    if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {


                        OperationBinding b1 = ADFBeanUtils.getOperationBinding("genShipNo");
                        b1.execute();

                        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
                        saveOp.execute();

                        //showFacesMessage("Record Saved Successfully", "I", false, "F", null);
                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");

                        //  setMode("V");
                        //AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
                        //no pending
                        return "towf";
                    }
                    /*  else {//pending
                                                 return null;
                                                 } */
                } else {
                    //String msg="Something went wrong(User Level in Workflow).Please Contact to ESS!";
                    String msg = ADFModelUtils.resolvRsrc("MSG.1875");
                    //                    String msg = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                    showFacesMessage(msg, "E", false, "F", null);
                    return null;
                }
            } else {
                // String msg="Workflow not Created for Shipment Advice.";
                String msg = ADFModelUtils.resolvRsrc("MSG.1878");
                //                String msg = resolvElDCMsg("#{bundle['MSG.1878']}").toString();
                showFacesMessage(msg, "E", false, "F", null);
                return null;
            }

        }


        return null;
        //return null;
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

    public void InsuranceAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String msg = "";
        if (object != null) {

            // String msg="Amount must be Greater than Zero";
            msg = ADFModelUtils.resolvRsrc("MSG.2386");
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(message2);
            }


            // String msg="Value","Invalid Precision(26,6)";
            //String message1 = "Value";
            //String message2 = "Invalid Precision(26,6)";
            //            String message3 = resolvElDCMsg("#{bundl'e['MSG.1882']}").toString();
            String message3 = ADFModelUtils.resolvRsrc("MSG.1882");
            String message4 = ADFModelUtils.resolvRsrc("MSG.1883");
            //            String message4 = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            msg = message3 + "," + message4;


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            boolean valid = isPrecisionValid(26, 6, objectNew);
            if (!valid) {
                throw new ValidatorException(message);


            }
        }
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void otherChargesValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String msg = "";
        if (object != null) {
            // String msg = "Amount must be Greator than Zero";
            //            msg = resolvElDCMsg("#{bundle['MSG.1884']}").toString();

            msg = ADFModelUtils.resolvRsrc("MSG.1884");
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(message2);
            }
            //            String message3 = resolvElDCMsg("#{bundle['MSG.1882']}").toString();
            String message3 = ADFModelUtils.resolvRsrc("MSG.1882");
            String message4 = ADFModelUtils.resolvRsrc("MSG.1883");
            //            String message4 = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            msg = message3 + "," + message4;
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            boolean valid = isPrecisionValid(26, 6, objectNew);
            if (!valid) {
                throw new ValidatorException(message);
            }
        }
    }

    public void freightChargeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String msg = "";
        if (object != null) {

            // String msg = "Amount must be Greater than Zero";
            msg = ADFModelUtils.resolvRsrc("MSG.1884");
            //            msg = resolvElDCMsg("#{bundle['MSG.1884']}").toString();
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(message2);
            }
            String message3 = ADFModelUtils.resolvRsrc("MSG.1882");
            String message4 = ADFModelUtils.resolvRsrc("MSG.1883");
            //            String message3 = resolvElDCMsg("#{bundle['MSG.1882']}").toString();
            //            String message4 = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            msg = message3 + "," + message4;
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            boolean valid = isPrecisionValid(26, 6, objectNew);
            if (!valid) {
                throw new ValidatorException(message);
            }
        }

    }

    public void shipQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("method call..");
        if (object != null) {
            /*             OperationBinding op1 = ADFBeanUtils.getOperationBinding("checkDelvrySchfrmPrf");
            op1.execute();
            String result = (String) op1.getResult();
            adfLog.info("Res::" + result); */
            String result = "N";

            if (result != null) {
                if (result.equals("Y")) {
                    OperationBinding op2 = getBindings().getOperationBinding("checkToleranceQuantity");
                    op2.getParamsMap().put("val", object);
                    op2.execute();
                    adfLog.info("Result of Tolearance::" + op2.getResult());
                    if (op2.getResult() != null && op2.getResult().equals("N")) {
                        // String msg="Entered Quantity are not as per the delivery schedule.";
                        String msg = ADFModelUtils.resolvRsrc("MSG.1885");
                        //                        String msg = resolvElDCMsg("#{bundle['MSG.1885']}").toString();
                        showFacesMessage(msg, "E", false, "F", bindShpQty.getClientId());

                    }
                } else {
                    OperationBinding op2 = getBindings().getOperationBinding("checkTolQty");
                    op2.getParamsMap().put("val", object);
                    op2.execute();
                    adfLog.info("Result of Tolearance::" + op2.getResult());
                    if (op2.getResult() != null && op2.getResult().equals("N")) {
                        //String msg="Shipped  Quantity is greater than the Ordered Quantity .";
                        String msg = ADFModelUtils.resolvRsrc("MSG.1886");
                        //                        String msg = resolvElDCMsg("#{bundle['MSG.1886']}").toString();
                        showFacesMessage(msg, "E", false, "F", bindShpQty.getClientId());

                    }

                }

            }
        }

    }

    public void setBindShpQty(RichInputText bindShpQty) {
        this.bindShpQty = bindShpQty;
    }

    public RichInputText getBindShpQty() {
        return bindShpQty;
    }

    public void etdVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(etaBinding);
            System.out.println("give target");
        }
    }

    public void setEtaBinding(RichInputDate etaBinding) {
        this.etaBinding = etaBinding;
    }

    public RichInputDate getEtaBinding() {
        return etaBinding;
    }
}

