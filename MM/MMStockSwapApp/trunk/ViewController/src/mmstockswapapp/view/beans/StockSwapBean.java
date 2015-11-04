
package mmstockswapapp.view.beans;


import adf.utils.bean.ADFBeanUtils;

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

import mmstockswapapp.model.services.AppModuleAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class StockSwapBean {
    private RichInputText bindQty;
    private boolean flag = false;
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(StockSwapBean.class);
    private RichInputText lotQtyBind;
    private String LotEnable = "N";
    private String mode1 = "N";
    private String mode = modeGet();
    private RichPanelGroupLayout panelGrpBind;
    private RichInputComboboxListOfValues bindItmNm;
    private RichInputText bindLotMvQty;
    private String var1 = "E";
    private RichSelectOneChoice bindWhNm;
    private RichSelectOneChoice bindItmUom;
    private RichSelectOneChoice bindItmUoMMv;

    public void setLotEnable(String LotEnable) {
        this.LotEnable = LotEnable;
    }

    public String getLotEnable() {
        return LotEnable;
    }

    public StockSwapBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void swapAction() {
        //checkWhNm
        OperationBinding a = ADFBeanUtils.findOperation("checkWhNm");
        //OperationBinding a = getBindings().getOperationBinding("checkWhNm");
        a.execute();
        adfLog.info("value:::" + a.getResult());
        if (a.getResult() != null && a.getResult().equals("N")) {
            OperationBinding a1 = null;
            if (flag == true) {
                a1 = getBindings().getOperationBinding("ValidateItmUom");
                a1.execute();
                if (a1.getResult() != null && a1.getResult().equals("N"))
                    return;
                else if(a1.getResult() != null && a1.getResult().equals("P"))
                {
                showFacesMessage("UoM is required.", "E", false, "F", bindItmUom.getClientId());
                return;
                }
                else if(a1.getResult() != null && a1.getResult().equals("Q"))
                {
                showFacesMessage("UoM is required.", "E", false, "F", bindItmUoMMv.getClientId());
                return;
                }
            }
            var1 = "D";
            OperationBinding a2 = getBindings().getOperationBinding("CreateInsert");
            a2.execute();
            flag = true;
        } else {
            showFacesMessage("Warehouse Name is required", "E", false, "F", bindWhNm.getClientId());
        }
    }

    public void QuantityVCL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...


        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew == null) {
            showFacesMessage("Enter the Quantity", "I", false, "V", bindQty.getClientId());

        } else if (objectNew.compareTo(zero) == -1 || objectNew.compareTo(zero) == 0) {
            showFacesMessage("Quantity should be greater than zero.", "E", false, "V", bindQty.getClientId());

        } else {
            Number a = (Number) object;
            OperationBinding stk = getBindings().getOperationBinding("checkItmStk");
            stk.getParamsMap().put("stk", a);
            stk.execute();

            if (stk.getResult() != null && stk.getResult().equals("N")) {
                showFacesMessage("Available Quantity is less than the Entered Quantity", "E", false, "V",
                                 bindQty.getClientId());

            }

        }
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

    public void setBindQty(RichInputText bindQty) {
        this.bindQty = bindQty;
    }

    public RichInputText getBindQty() {
        return bindQty;
    }

    public String saveSwapAL(ActionEvent actionEvent) {
        // Add event code here...

        //        OperationBinding comm = getBindings().getOperationBinding("Commit");
        //        comm.execu
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        AppModuleAMImpl am = (AppModuleAMImpl) resolvElDC("AppModuleAMDataControl");
        String auth = (String) am.getMmStkSwap1().getCurrentRow().getAttribute("AuthStat");
        adfLog.info("value of auth::" + auth);
        if (auth.equals("N")) {
            OperationBinding a1 = getBindings().getOperationBinding("ValidateItmUom");
            a1.execute();
            if (a1.getResult() != null && a1.getResult().equals("Y")) {
                OperationBinding geteo = getBindings().getOperationBinding("genSwapNo");
                geteo.execute();
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
                WfnoOp.getParamsMap().put("DocNo", 18536);
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
                    usrLevelOp.getParamsMap().put("DocNo", 18536);
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
                        insTxnOp.getParamsMap().put("DocNo", 18536);
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

                        OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                        saveOp.execute();
                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                        setMode("V");
                        showFacesMessage(" Record Saved Successfully", "I", false, "F", null);

                    } else {
                        showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                         false, "F", null);

                    }
                } else {
                    showFacesMessage("Workflow is not created for Stock Swapping.", "E", false, "F", null);

                }
            }
        }


        //
        else {
            OperationBinding auto = getBindings().getOperationBinding("checkAutoLot");
            auto.execute();
            if (auto.getResult() != null && auto.getResult().equals("Y")) {
                OperationBinding lot = getBindings().getOperationBinding("LotCreate");
                lot.execute();

                if (lot.getResult() != null && lot.getResult().equals("Y")) {
                    OperationBinding quantity1 = getBindings().getOperationBinding("ValidateItemQuantity");
                    quantity1.execute();

                    if (quantity1.getResult().equals("Y")) {
                        OperationBinding stock = getBindings().getOperationBinding("updateStock");
                        stock.execute();
                        if (stock.getResult() != null) {
                            if (stock.getResult().equals("Y")) {

                                OperationBinding stk = getBindings().getOperationBinding("Commit");
                                stk.execute();
                                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                setMode("V");
                                AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
                                adfLog.info("page flow value 12  " +
                                            RequestContext.getCurrentInstance().getPageFlowScope().get("mode"));
                                showFacesMessage("Stock Updated successfully", "I", false, "F", null);
                                this.mode1 = "Y";
                            }
                        }
                    } else {
                        showFacesMessage("Lot Quantities doesn't Allocated for all the items", "E", false, "F", null);
                    }
                }
            }

            else {

                OperationBinding quantity = getBindings().getOperationBinding("ValidateItemQuantity");
                quantity.execute();
                if (quantity.getResult().equals("Y")) {
                    OperationBinding stock = getBindings().getOperationBinding("updateStock");
                    stock.execute();
                    if (stock.getResult() != null) {
                        if (stock.getResult().equals("Y")) {
                            OperationBinding stk = getBindings().getOperationBinding("Commit");
                            stk.execute();
                            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                            setMode("V");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
                            adfLog.info("page flow value " +
                                        RequestContext.getCurrentInstance().getPageFlowScope().get("mode"));
                            showFacesMessage("Stock Updated successfully", "I", false, "F", null);
                            this.mode1 = "Y";
                        }

                    }
                } else
                    showFacesMessage("Lot Quantities doesn't Allocated for all the items", "E", false, "F", null);
            }

        }


        return null;
    }

    public String saveAndFwdAction() {
        // Add event code here...

        OperationBinding a1 = getBindings().getOperationBinding("ValidateItmUom");
        a1.execute();
        if (a1.getResult() != null && a1.getResult().equals("Y")) {
            Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            int userId = 0;
            OperationBinding check = getBindings().getOperationBinding("pendingCheck");
            check.getParamsMap().put("SlocId", sloc_Id);
            check.getParamsMap().put("CldId", cld_Id);
            check.getParamsMap().put("OrgId", org_Id);
            check.getParamsMap().put("DocNo", 18536);
            check.execute();

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
                OperationBinding geteo = getBindings().getOperationBinding("genSwapNo");
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
                WfnoOp.getParamsMap().put("DocNo", 18536);
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
                    usrLevelOp.getParamsMap().put("DocNo", 18536);
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
                        insTxnOp.getParamsMap().put("DocNo", 18536);
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
                        chkUsr.getParamsMap().put("DocNo", 18536);
                        chkUsr.execute();

                        if (chkUsr.getResult() != null) {
                            pending = Integer.parseInt(chkUsr.getResult().toString());
                            System.out.println("pending check" + pending);
                        }
                        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                            OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                            saveOp.execute();
                            //showFacesMessage("Record Saved Successfully", "I", false, "F", null);
                            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                            setMode("V");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
                            //no pending
                            return "toWf";
                        }
                        /*  else {//pending
                                                         return null;
                                                         } */
                    } else {
                        showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                         false, "F", null);
                        return null;
                    }
                } else {
                    showFacesMessage("Workflow not Created for Item Swapping", "E", false, "F", null);
                    return null;
                }

            }
        }
        // RequestContext.getCurrentInstance().getPageFlowScope().put("mode","V");
        //setMode("V");
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
        return null;
    }

    public String editSwapAction() {
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
        check.getParamsMap().put("DocNo", 18536);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");
            setMode("A");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            adfLog.info("Value returned from function::"+getusrname.getResult());
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Stock Swap is Pending at [" + name + "]. You can't Edit this Stock Swapping.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
        return null;
    }

    public String deleteItemAction() {
        // Add event code here...
        var1="E";
        OperationBinding a2 = getBindings().getOperationBinding("Delete");
        a2.execute();
        return null;
    }

    public String addLotAction() {
        // Add event code here...
        OperationBinding insert = getBindings().getOperationBinding("CreateInsert1");
        insert.execute();
        return null;
    }

    public void BinNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding bin = getBindings().getOperationBinding("setBinNm");
        bin.execute();

    }

    public void LotBinQntyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew == null) {
            showFacesMessage("Enter the Quantity", "I", false, "V", bindQty.getClientId());

        } else if (objectNew.compareTo(zero) == 0 || objectNew.compareTo(zero) == -1) {
            showFacesMessage("Quantity should be greater than zero.", "E", false, "V", null);

        }
        if (object != null) {
            Number qty = (Number) object;
            adfLog.info("Value of qty::" + qty);
            OperationBinding a1 = getBindings().getOperationBinding("checkLotBinQuantity");
            a1.getParamsMap().put("qty", qty);
            a1.execute();
            if (a1.getResult() != null) {
                String result = (String) a1.getResult();
                adfLog.info("result::" + result);
                if (result.equals("N")) {
                    /*  OperationBinding a2= getBindings().getOperationBinding("setItmBinMvQty");
                a2.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getLotQtyBind()); */

                } else {
                    showFacesMessage("Entered Quantity is greater than available quantity", "E", false, "V",
                                     lotQtyBind.getClientId());
                }
            }
        }

    }

    public void setLotQtyBind(RichInputText lotQtyBind) {
        this.lotQtyBind = lotQtyBind;
    }

    public RichInputText getLotQtyBind() {
        return lotQtyBind;
    }

    public void updateStockAL(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding auto = getBindings().getOperationBinding("checkAutoLot");
        auto.execute();
        if (auto.getResult() != null && auto.getResult().equals("Y")) {
            OperationBinding lot = getBindings().getOperationBinding("LotCreate");
            lot.execute();
            if (lot.getResult() != null && lot.equals("Y")) {
                OperationBinding stock = getBindings().getOperationBinding("updateStock");
                stock.execute();
                if (stock.getResult().equals("Y")) {
                    OperationBinding stk = getBindings().getOperationBinding("Commit");
                    stk.execute();
                    showFacesMessage("Stock Updated successfully", "E", false, "F", null);
                }
            }
        } else {
            adfLog.info("in the else block");
            OperationBinding stock = getBindings().getOperationBinding("updateStock");
            stock.execute();
            if (stock.getResult() != null) {
                if (stock.getResult().equals("Y")) {
                    OperationBinding stk = getBindings().getOperationBinding("Commit");
                    stk.execute();
                    showFacesMessage("Stock Updated successfully", "I", false, "F", null);
                }
            }
        }
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

    public void LotBinTabDisclosure(DisclosureEvent disclosureEvent) {

        OperationBinding stk = getBindings().getOperationBinding("checkAutoLot");
        stk.execute();
        if (stk.getResult() != null) {
            if (stk.getResult().equals("Y")) {
                adfLog.info(" in the checkAutoLot");
                this.LotEnable = "Y";
            }

        }
    }


    public void ItemQuantityVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());


    }

    public void LotBinQtyVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here..
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

    }

    public void setPanelGrpBind(RichPanelGroupLayout panelGrpBind) {
        this.panelGrpBind = panelGrpBind;
    }

    public RichPanelGroupLayout getPanelGrpBind() {
        return panelGrpBind;
    }

    public void ItmNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        System.out.println(" Validator call.");
        if (object != null) {
            String itmnm = (String) object;
            OperationBinding nm = getBindings().getOperationBinding("ItemNameDuplicate");
            nm.getParamsMap().put("itmnm", itmnm);
            nm.execute();
            if (nm.getResult() != null && nm.getResult().equals("Y"))
                showFacesMessage("Duplicate Item found", "I", false, "V", bindItmNm.getClientId());
        }

    }

    public void setBindItmNm(RichInputComboboxListOfValues bindItmNm) {
        this.bindItmNm = bindItmNm;
    }

    public RichInputComboboxListOfValues getBindItmNm() {
        return bindItmNm;
    }

    public void setBindLotMvQty(RichInputText bindLotMvQty) {
        this.bindLotMvQty = bindLotMvQty;
    }

    public RichInputText getBindLotMvQty() {
        return bindLotMvQty;
    }

    public String modeGet() {
        return resolvEl("#{pageFlowScope.mode}").toString();
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

    public String cancalAction() {
        OperationBinding stk = getBindings().getOperationBinding("Rollback");
        stk.execute();
        setMode(" ");
        return "back";
    }

    public void swapItemChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        var1 = "E";
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar1() {
        return var1;
    }

    public void setBindWhNm(RichSelectOneChoice bindWhNm) {
        this.bindWhNm = bindWhNm;
    }

    public RichSelectOneChoice getBindWhNm() {
        return bindWhNm;
    }

    public void binIdChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        adfLog.info("in the Vcl" + valueChangeEvent.getNewValue());
        // ADFBeanUtils.findOperation("setAvalQty");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding aval = getBindings().getOperationBinding("setAvalQty");
            aval.getParamsMap().put("binid", valueChangeEvent.getNewValue());
            aval.execute();
        }

    }

    public void deleteLotActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding a1 = getBindings().getOperationBinding("delUnwantedItem");
        a1.execute();        
    }

    public void setBindItmUom(RichSelectOneChoice bindItmUom) {
        this.bindItmUom = bindItmUom;
    }

    public RichSelectOneChoice getBindItmUom() {
        return bindItmUom;
    }

    public void setBindItmUoMMv(RichSelectOneChoice bindItmUoMMv) {
        this.bindItmUoMMv = bindItmUoMMv;
    }

    public RichSelectOneChoice getBindItmUoMMv() {
        return bindItmUoMMv;
    }
}

