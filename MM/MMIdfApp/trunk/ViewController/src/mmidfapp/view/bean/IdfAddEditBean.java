package mmidfapp.view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

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

import mmidfapp.model.services.MmIpoIdfAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class IdfAddEditBean {
    private RichInputListOfValues bindPoNo;
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(IdfAddEditBean.class);
    private RichInputText insramtBind;
    private RichInputText fobamtBind;
    private RichInputText octotamtBind;
    private RichInputText freightamtBind;
    private RichInputText idffeeBind;
    private RichInputText advanceBind;
    private RichInputText ocAmtBindvar;
    private RichSelectOneChoice dstPrtBind;
    private RichSelectOneChoice srcPrtBind;
    private RichInputListOfValues coaNmBind;
    private RichSelectOneChoice ocDescBind;
    private RichInputText bindDetalsVar;
    private RichInputText bindHeaderVAr;
    private RichInputDate dispatchDtBind;
    private List<UploadedFile> uploadedFile;
    private RichInputText fileBindName;
    private RichTable bindIdfItm;
    private RichInputText bindIdfNo;
    private RichInputDate bindIdfDt;
    private RichInputText rcptNoBind;
    private RichInputDate bindrcptDt;
    private RichInputText bindrefNo;
    private RichInputListOfValues bindCntryNm;

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    public IdfAddEditBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String deleteOcAction() {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("Delete");
        binding.execute();
        return null;
    }

    public String addOtherChargesAction() {
        // Add event code here...
        if (coaNmBind.getValue() != null) {
            if (ocDescBind.getValue() != null) {
                if (ocAmtBindvar.getValue() != null) {
                    String ret = "N";
                    OperationBinding dupliChk = getBindings().getOperationBinding("isCOADuplicate");
                    dupliChk.execute();
                    if (dupliChk.getResult() != null) {
                        ret = dupliChk.getResult().toString();
                    }
                    if ("N".equalsIgnoreCase(ret)) {
                        OperationBinding binding1 = getBindings().getOperationBinding("CreateOc");
                        binding1.execute();
                    } else {
                        //String message ="Duplicate Coa Name Found."
                        String msg = resolvElDCMsg("#{bundle['MSG.2600']}").toString();
                        showFacesMessage(msg, "E", false, "F", coaNmBind.getClientId());

                    }
                } else {
                    //String msg ="Other charges Amount  is manadatory"
                    String msg = resolvElDCMsg("#{bundle['MSG.2381']}").toString();
                    showFacesMessage(msg, "E", false, "F", ocAmtBindvar.getClientId());
                }
            }

            else {
                //String msg ="Other Charges Description is manadatory"
                String msg = resolvElDCMsg("#{bundle['MSG.2382']}").toString();
                showFacesMessage(msg, "E", false, "F", ocDescBind.getClientId());

            }
        }

        else {
            //String msg ="Coa Name is Manadatory"
            String msg = resolvElDCMsg("#{bundle['MSG.2383']}").toString();
            showFacesMessage(msg, "E", false, "F", coaNmBind.getClientId());
        }
        return null;
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

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String populateItmAction() {
        // Add event code here...
        System.out.println("Po Number:" + bindPoNo.getValue());
        MmIpoIdfAMImpl am = (MmIpoIdfAMImpl) resolvElDC("MmIpoIdfAMDataControl");
        Integer src = (Integer) am.getMmIpoIdf1().getCurrentRow().getAttribute("CntryIdSrc");
        adfLog.info("Value of Src::" + src);
        if (src != null) {
            adfLog.info(" in the src block");
            if (bindPoNo.getValue() != null && bindPoNo.getValue() != "") {

                OperationBinding bindingpo = getBindings().getOperationBinding("populatePo");
                bindingpo.getParamsMap().put("Po_No", bindPoNo.getValue().toString());
                bindingpo.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(bindIdfItm);
                //String rt =bindingpo.getResult().toString();
                //adfLog.info("return  value :"+rt);
            } else {
                System.out.println(" in the else conditon");
                //String msg ="Enter Po Number."
                String msg = resolvElDCMsg("#{bundle['MSG.2384']}").toString();
                showFacesMessage(msg, "E", false, "F", bindPoNo.getClientId());
                /*  FacesMessage message = new FacesMessage("Please Select the Po Number.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindPoNo.getClientId(), message); */
            }
        } else {
            //String msg ="Country is manadatory."
            String msg = resolvElDCMsg("#{bundle['MSG.2385']}").toString();
            showFacesMessage(msg, "E", false, "F", bindCntryNm.getClientId());
            /*  FacesMessage message = new FacesMessage("Country is manadatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindCntryNm.getClientId(), message); */
        }
        return null;
    }

    public void setBindPoNo(RichInputListOfValues bindPoNo) {
        this.bindPoNo = bindPoNo;
    }

    public RichInputListOfValues getBindPoNo() {
        return bindPoNo;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void InsurAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        //String msg = "Amount must be Greator than Zero";

        String msg = resolvElDCMsg("#{bundle['MSG.2386']}").toString();
        FacesMessage message2 = new FacesMessage("Value", msg);
        message2.setSeverity(FacesMessage.SEVERITY_WARN);
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew == null) {
            throw new ValidatorException(message2);
        } else if (objectNew.compareTo(zero) == -1) {
            throw new ValidatorException(message2);
        } else {

            FacesMessage message = new FacesMessage("Value", resolvElDCMsg("#{bundle['MSG.1883']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);

            boolean valid = isPrecisionValid(26, 6, objectNew);
            if (!valid) {
                throw new ValidatorException(message);
            }
        }
    }

    public void editActionListener(ActionEvent actionEvent) {
        // Add event code here...
        //RequestContext.getCurrentInstance().getPageFlowScope().put("mode","A");

        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18535);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");
        } else {

            // String name = "Anonymous";
            String name = resolvElDCMsg("#{bundle['MSG.2402']}").toString();
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            // String msg1 = "This IDF is Pending at [";
            String msg1 = resolvElDCMsg("#{bundle['MSG.2387']}").toString();
            // String msg2= "]. You can't Edit this IDF.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.2388']}").toString();
            String msg = msg1 + name + msg2;
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
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


    public String saveAction() {
        // Add event code here...

        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        //getBindings().getOperationBinding("genIDFNo").execute();
        //getBindings().getOperationBinding("Commit").execute();
        // showFacesMessage("IDF Saved Successfully ", "I", false, "F", null);
        //getBindings().getOperationBinding("insertDtl").execute();
        //RequestContext.getCurrentInstance().getPageFlowScope().put("mode","V");
        MmIpoIdfAMImpl am = (MmIpoIdfAMImpl) resolvElDC("MmIpoIdfAMDataControl");
        Integer src = (Integer) am.getMmIpoIdf1().getCurrentRow().getAttribute("CntryIdSrc");
        String prtsrc = (String) am.getMmIpoIdf1().getCurrentRow().getAttribute("PortIdSrc");
        String dstsrc = (String) am.getMmIpoIdf1().getCurrentRow().getAttribute("PortIdDest");
        adfLog.info("Value of Src::" + src);
        if (dstPrtBind.getValue() != null) {
            if (srcPrtBind.getValue() != null) {
                if (src != null) {
                    String ret = "N";
                    OperationBinding checkSrcDe = getBindings().getOperationBinding("checkSourceDest");
                    checkSrcDe.execute();
                    if (checkSrcDe.getResult() != null) {
                        ret = checkSrcDe.getResult().toString();
                    }
                    if ("N".equalsIgnoreCase(ret)) {
                        /*  if(bindPoNo.getValue()!=null && bindPoNo.getValue()!="")
      { */
                        OperationBinding geteo = getBindings().getOperationBinding("genIDFNo");
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
                        WfnoOp.getParamsMap().put("DocNo", 18535);
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
                            usrLevelOp.getParamsMap().put("DocNo", 18535);
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
                                insTxnOp.getParamsMap().put("DocNo", 18535);
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
                                //setMode("V");
                                // String msg= "IDF Saved Successfully";
                                String msg = resolvElDCMsg("#{bundle['MSG.2389']}").toString();
                                showFacesMessage(msg, "I", false, "F", null);
                                //  showFacesMessage("Service Contract No "+scno+" Save Successfully", "I", false, "F", null);
                            } else {
                                // String msg2= "Something went wrong(User Level in Workflow).Please Contact to ESS!";
                                String msg2 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                                showFacesMessage(msg2, "E", false, "F", null);

                            }
                        } else {
                            // String msg2= "Workflow is not created for Import Declaration Form.";
                            String msg2 = resolvElDCMsg("#{bundle['MSG.2390']}").toString();
                            showFacesMessage(msg2, "E", false, "F", null);

                        }

                    } else {
                        // String msg2= "Destination Port and Source Port should not be same";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.2391']}").toString();
                        showFacesMessage(msg2, "E", false, "F", null);
                    }
                } else {
                    // String msg2= "Country is manadatory";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.2385']}").toString();
                    showFacesMessage(msg2, "E", false, "F", bindCntryNm.getClientId());
                }
            } else {
                // String msg2= "Source port is manadatory";
                String msg2 = resolvElDCMsg("#{bundle['MSG.2392']}").toString();
                showFacesMessage(msg2, "E", false, "F", srcPrtBind.getClientId());
            }
        }

        else {
            // String msg2= "Destination port is manadatory";
            String msg2 = resolvElDCMsg("#{bundle['MSG.2393']}").toString();
            showFacesMessage(msg2, "E", false, "F", dstPrtBind.getClientId());

        }
        return null;
    }


    public void fobAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew != null && objectNew.compareTo(zero) == -1) {
            // String msg2= "Amount can not be negative";
            String msg2 = resolvElDCMsg("#{bundle['MSG.987']}").toString();
            showFacesMessage(msg2, "E", false, "V", fobamtBind.getClientId());
        }
        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) {
            // String msg= ""Invalid Precision"";
            String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            showFacesMessage(msg, "E", false, "F", fobamtBind.getClientId());
        }

    }

    public void frieghtAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew != null && objectNew.compareTo(zero) == -1) {
            // String msg2= "Amount can not be negative";
            String msg2 = resolvElDCMsg("#{bundle['MSG.987']}").toString();
            showFacesMessage(msg2, "E", false, "V", freightamtBind.getClientId());
        }
        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) {
            // String msg= ""Invalid Precision"";
            String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            showFacesMessage(msg, "E", false, "F", freightamtBind.getClientId());
        }

    }

    public void OcTotAmt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew.compareTo(zero) == -1) {
            // String msg2= "Amount can not be negative";
            String msg2 = resolvElDCMsg("#{bundle['MSG.987']}").toString();
            showFacesMessage(msg2, "E", false, "F", octotamtBind.getClientId());
        }
        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) {
            // String msg= ""Invalid Precision"";
            String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            showFacesMessage(msg, "E", false, "F", octotamtBind.getClientId());
        }

    }

    public void IdfFeeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew != null && objectNew.compareTo(zero) == -1) {
            // String msg2= "Amount can not be negative";
            String msg2 = resolvElDCMsg("#{bundle['MSG.987']}").toString();
            showFacesMessage(msg2, "E", false, "V", idffeeBind.getClientId());
        }
        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) { // String msg= ""Invalid Precision"";
            String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            showFacesMessage(msg, "E", false, "F", idffeeBind.getClientId());
        }

    }

    public void advancefeeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...


        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew != null && objectNew.compareTo(zero) == -1) {
            // String msg2= "Amount can not be negative";
            String msg2 = resolvElDCMsg("#{bundle['MSG.987']}").toString();
            showFacesMessage(msg2, "E", false, "V", advanceBind.getClientId());
        }
        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) {
            // String msg= ""Invalid Precision"";
            String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
            showFacesMessage(msg, "E", false, "F", advanceBind.getClientId());
        }

    }


    public void setInsramtBind(RichInputText insramtBind) {
        this.insramtBind = insramtBind;
    }

    public RichInputText getInsramtBind() {
        return insramtBind;
    }

    public void setFobamtBind(RichInputText fobamtBind) {
        this.fobamtBind = fobamtBind;
    }

    public RichInputText getFobamtBind() {
        return fobamtBind;
    }

    public void setOctotamtBind(RichInputText octotamtBind) {
        this.octotamtBind = octotamtBind;
    }

    public RichInputText getOctotamtBind() {
        return octotamtBind;
    }

    public void setFreightamtBind(RichInputText freightamtBind) {
        this.freightamtBind = freightamtBind;
    }

    public RichInputText getFreightamtBind() {
        return freightamtBind;
    }

    public void setIdffeeBind(RichInputText idffeeBind) {
        this.idffeeBind = idffeeBind;
    }

    public RichInputText getIdffeeBind() {
        return idffeeBind;
    }

    public void setAdvanceBind(RichInputText advanceBind) {
        this.advanceBind = advanceBind;
    }

    public RichInputText getAdvanceBind() {
        return advanceBind;
    }

    public void OcAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number objectNew = (Number) object;
            Number zero = new Number(0);

            if (objectNew.compareTo(zero) == 0 || objectNew.compareTo(zero) == -1) {
                // String msg2= "Amount must be greater than zero.";
                String msg2 = resolvElDCMsg("#{bundle['MSG.265']}").toString();
                showFacesMessage(msg2, "E", false, "V", ocAmtBindvar.getClientId());
            }
            boolean valid = isPrecisionValid(26, 6, objectNew);
            if (!valid) {
                // String msg= ""Invalid Precision"";
                String msg = resolvElDCMsg("#{bundle['MSG.1883']}").toString();
                showFacesMessage(msg, "E", false, "V", ocAmtBindvar.getClientId());
            }
        }

    }

    public void setOcAmtBindvar(RichInputText ocAmtBindvar) {
        this.ocAmtBindvar = ocAmtBindvar;
    }

    public RichInputText getOcAmtBindvar() {
        return ocAmtBindvar;
    }

    public void deliveryDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Integer objectNew = (Integer) object;
        Integer zero = new Integer(0);

        if (objectNew != null && objectNew.compareTo(zero) == -1) {
            // String msg= "Delivery Days Cannot be Negative.";
            String msg = resolvElDCMsg("#{bundle['MSG.2394']}").toString();
            showFacesMessage(msg, "E", false, "V", ocAmtBindvar.getClientId());
        }

    }

    public String saveandFwdAction() {
        // Add event code here...
        MmIpoIdfAMImpl am = (MmIpoIdfAMImpl) resolvElDC("MmIpoIdfAMDataControl");
        Integer src = (Integer) am.getMmIpoIdf1().getCurrentRow().getAttribute("CntryIdSrc");
        String prtsrc = (String) am.getMmIpoIdf1().getCurrentRow().getAttribute("PortIdSrc");
        String dstsrc = (String) am.getMmIpoIdf1().getCurrentRow().getAttribute("PortIdDest");
        adfLog.info("Value of Src::" + src);
        if (dstPrtBind.getValue() != null) {
            if (srcPrtBind.getValue() != null) {
                adfLog.info("Value of Src::" + src);
                if (src != null) {
                    String ret = "N";
                    OperationBinding checkSrcDe = getBindings().getOperationBinding("checkSourceDest");
                    checkSrcDe.execute();
                    if (checkSrcDe.getResult() != null) {
                        ret = checkSrcDe.getResult().toString();
                    }
                    if ("N".equalsIgnoreCase(ret)) {
                        /* if(bindPoNo.getValue()!=null && bindPoNo.getValue()!="")
        { */
                        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                        int userId = 0;
                        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
                        check.getParamsMap().put("SlocId", sloc_Id);
                        check.getParamsMap().put("CldId", cld_Id);
                        check.getParamsMap().put("OrgId", org_Id);
                        check.getParamsMap().put("DocNo", 18535);
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
                            OperationBinding geteo = getBindings().getOperationBinding("genIDFNo");
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
                            WfnoOp.getParamsMap().put("DocNo", 18535);
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
                                usrLevelOp.getParamsMap().put("DocNo", 18535);
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
                                    insTxnOp.getParamsMap().put("DocNo", 18535);
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
                                    chkUsr.getParamsMap().put("DocNo", 18535);
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
                                        return "toWf";
                                    }
                                    /*  else {//pending
                                                         return null;
                                                         } */
                                } else {
                                    // String msg= "Something went wrong(User Level in Workflow).Please Contact to ESS!";
                                    String msg = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                                    showFacesMessage(msg, "E", false, "F", null);
                                    return null;
                                }
                            } else {
                                // String msg= "Workflow is not created for Import Declaration Form.";
                                String msg = resolvElDCMsg("#{bundle['MSG.2390']}").toString();
                                showFacesMessage(msg, "E", false, "F", null);
                                return null;
                            }

                        }
                    } else {
                        // String msg= "Destination Port and Source Port should not be same";
                        String msg = resolvElDCMsg("#{bundle['MSG.2391']}").toString();
                        showFacesMessage(msg, "E", false, "F", null);
                    }
                }

                else {
                    // String msg= "Country is manadatory.";
                    String msg = resolvElDCMsg("#{bundle['MSG.2385']}").toString();
                    showFacesMessage(msg, "E", false, "F", bindCntryNm.getClientId());
                }
            } else {
                // String msg= "Source port is manadatory";
                String msg = resolvElDCMsg("#{bundle['MSG.2392']}").toString();
                showFacesMessage(msg, "E", false, "F", srcPrtBind.getClientId());
            }
        }

        else {
            // String msg= "Destination port is manadatory";
            String msg = resolvElDCMsg("#{bundle['MSG.2393']}").toString();
            showFacesMessage(msg, "E", false, "F", dstPrtBind.getClientId());

        }
        return null;
    }

    public void destprtVCL(ValueChangeEvent valueChangeEvent) {
    }

    public void SourcePrtVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void setDstPrtBind(RichSelectOneChoice dstPrtBind) {
        this.dstPrtBind = dstPrtBind;
    }

    public RichSelectOneChoice getDstPrtBind() {
        return dstPrtBind;
    }

    public void setSrcPrtBind(RichSelectOneChoice srcPrtBind) {
        this.srcPrtBind = srcPrtBind;
    }

    public RichSelectOneChoice getSrcPrtBind() {
        return srcPrtBind;
    }

    public void setCoaNmBind(RichInputListOfValues coaNmBind) {
        this.coaNmBind = coaNmBind;
    }

    public RichInputListOfValues getCoaNmBind() {
        return coaNmBind;
    }

    public void setOcDescBind(RichSelectOneChoice ocDescBind) {
        this.ocDescBind = ocDescBind;
    }

    public RichSelectOneChoice getOcDescBind() {
        return ocDescBind;
    }

    public String addDetailAction() {
        // Add event code here...
        if (bindDetalsVar.getValue() != null && bindDetalsVar.getValue().toString().length() > 0) {
            if (bindHeaderVAr.getValue() != null && bindHeaderVAr.getValue().toString().length() > 0) {
                OperationBinding op1 = getBindings().getOperationBinding("DetailDuplicate");
                op1.execute();
                adfLog.info("duplicate data outptut is " + op1.getResult());
                if ("Y".equalsIgnoreCase(op1.getResult().toString())) {
                    OperationBinding operationBinding = getBindings().getOperationBinding("createDetails");
                    operationBinding.getParamsMap().put("Detail", null);
                    operationBinding.getParamsMap().put("hdr", null);
                    operationBinding.execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindDetalsVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindHeaderVAr);
                } else {
                    // String msg= "Duplicate Record Found.";
                    String msg = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                    showFacesMessage(msg, "E", false, "F", null);
                    // return null;
                }

            } else {
                // String msg= "Information header is required";
                String msg = resolvElDCMsg("#{bundle['MSG.2395']}").toString();
                showFacesMessage(msg, "E", false, "F", bindHeaderVAr.getClientId());
            }
        } else {
            // String msg= "Label Value id required";
            String msg = resolvElDCMsg("#{bundle['MSG.2396']}").toString();
            showFacesMessage(msg, "E", false, "F", bindDetalsVar.getClientId());
        }
        return null;
    }

    public void setBindDetalsVar(RichInputText bindDetalsVar) {
        this.bindDetalsVar = bindDetalsVar;
    }

    public RichInputText getBindDetalsVar() {
        return bindDetalsVar;
    }

    public void setBindHeaderVAr(RichInputText bindHeaderVAr) {
        this.bindHeaderVAr = bindHeaderVAr;
    }

    public RichInputText getBindHeaderVAr() {
        return bindHeaderVAr;
    }

    public String deleteDetailAction() {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("Delete1");
        binding.execute();
        return null;
    }

    public void dispatchDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


        //        adfLog.info("dispatch date validator");
        //        OperationBinding binding=getBindings().getOperationBinding("dateValidator");
        //        binding.execute();
        //        if(binding.getResult()!=null) {
        //            showFacesMessage("Dispatch Date is not less Apply Date", "E", false, "V", dispatchDtBind.getClientId());
        //        }
    }

    public void setDispatchDtBind(RichInputDate dispatchDtBind) {
        this.dispatchDtBind = dispatchDtBind;
    }

    public RichInputDate getDispatchDtBind() {
        return dispatchDtBind;
    }

    public String addAttachAction(ActionEvent actionEvent) throws Exception {
        // Add event code here...
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        InputStream in = null;
        FileOutputStream out = null;
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                try {


                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
                    files.get(i).getFilename();
                    adfLog.info(files.get(i).getFilename() + "Content Type:" + files.get(i).getContentType());
                    adfLog.info("entension--->>" + extn);
                    //Add files to the Table

                    int size = files.get(i).getFilename().length();
                    adfLog.info("Name of File::" + files.get(i).getFilename() + "size::" + size);
                    if (size <= 100) {
                        OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                        //  op = ADFUtils.findOperation("createAttchmntRow");
                        op.getParamsMap().put("fileNm", files.get(i).getFilename());
                        op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                        op.getParamsMap().put("extn", extn);
                        op.execute();

                        if (op.getResult() != null) {
                            path = op.getResult().toString();
                        }
                        System.out.println("extn " + extn);
                        //write files in the file system.

                        in = files.get(i).getInputStream();
                        System.out.println(files.get(i).getInputStream());

                        System.out.println("write file at " + path + extn);
                        out = new FileOutputStream(path + extn);
                        byte[] buffer = new byte[8192];
                        int bytesRead = 0;

                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                            out.write(buffer, 0, bytesRead);
                            // out.flush();
                        }
                        out.flush();

                        //call commit after checking all validations
                        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                        obCommit.execute();
                    } else {
                        // String msg= "File Name should be less 100 characters.";
                        String msg = resolvElDCMsg("#{bundle['MSG.2397']}").toString();
                        showFacesMessage(msg, "E", false, "F", null);
                    }
                    ///   ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            }
        }
        //remove the files to prepare for new uploads
        setUploadedFile(null);
        OperationBinding obExecute = getBindings().getOperationBinding("Execute");
        obExecute.execute();
        //ADFUtils.findOperation("Execute5").execute();
        return "null";
    }

    public void downloadAttachAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...

        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            File file = new File(path);
            FileInputStream fis = null;
            byte[] b;
            try {
                fis = new FileInputStream(file);
                int n;
                while ((n = fis.available()) > 0) {
                    b = new byte[n];
                    int result = fis.read(b);
                    outputStream.write(b, 0, b.length);
                    if (result == -1)
                        break;
                }
                outputStream.flush();
            } catch (IOException e) {
                //    JSFUtils.addFacesErrorMessage("Problem in downloading a file");
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void deleteAttchAction(ActionEvent actionEvent) {
        // Add event code here...
        String path = null;

        //                String key=  actionEvent.getComponent().getAttributes().get("rowKey").toString();
        //                System.out.println(" Value of key in the vo::"+key);
        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null) {
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();

            System.out.println("File path in AMimpl" + path);
            //            File f = new File(path);
            //            try {
            //                boolean success = f.delete();
            //                if (success) {
            //                    System.out.println("File Deleted");
            if (path != null) {


                OperationBinding obDelete = getBindings().getOperationBinding("deleteAttachFileRow");
                obDelete.getParamsMap().put("path", path);
                obDelete.execute();
                OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                obCommit.execute();
                OperationBinding obExecute = getBindings().getOperationBinding("Execute");
                obExecute.execute();
            }
            /*  ADFUtils.findOperation("Delete").execute();
                            ADFUtils.findOperation("Commit").execute();
                            ADFUtils.findOperation("Execute5").execute(); */
            // }
            //            } catch (Exception x) {
            //                System.err.format("%s: no such" + " file or directory%n", path);
            //            }
        }
    }

    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }
    //}
    public void setBindIdfItm(RichTable bindIdfItm) {
        this.bindIdfItm = bindIdfItm;
    }

    public RichTable getBindIdfItm() {
        return bindIdfItm;
    }

    public void ApproveVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        adfLog.info("Value of change event:" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("false")) {
            adfLog.info("in the if  loop");
            bindIdfNo.setValue("");
            bindIdfDt.setValue("");
            idffeeBind.setValue("");
            advanceBind.setValue("");
            bindrefNo.setValue("");
            bindrcptDt.setValue("");
            rcptNoBind.setValue("");
            OperationBinding idfreset = getBindings().getOperationBinding("resetValuesIdf");
            idfreset.execute();


        }


    }

    public void setBindIdfNo(RichInputText bindIdfNo) {
        this.bindIdfNo = bindIdfNo;
    }

    public RichInputText getBindIdfNo() {
        return bindIdfNo;
    }

    public void setBindIdfDt(RichInputDate bindIdfDt) {
        this.bindIdfDt = bindIdfDt;
    }

    public RichInputDate getBindIdfDt() {
        return bindIdfDt;
    }

    public void setRcptNoBind(RichInputText rcptNoBind) {
        this.rcptNoBind = rcptNoBind;
    }

    public RichInputText getRcptNoBind() {
        return rcptNoBind;
    }

    public void setBindrcptDt(RichInputDate bindrcptDt) {
        this.bindrcptDt = bindrcptDt;
    }

    public RichInputDate getBindrcptDt() {
        return bindrcptDt;
    }

    public void setBindrefNo(RichInputText bindrefNo) {
        this.bindrefNo = bindrefNo;
    }

    public RichInputText getBindrefNo() {
        return bindrefNo;
    }

    public void setBindCntryNm(RichInputListOfValues bindCntryNm) {
        this.bindCntryNm = bindCntryNm;
    }

    public RichInputListOfValues getBindCntryNm() {
        return bindCntryNm;
    }
}
