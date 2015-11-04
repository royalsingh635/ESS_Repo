package hcmdedprfsetupapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.exception.ADFUtilsException;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.Calendar;

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
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class hcmdedprfsetup {
    private RichSelectOneChoice pagebinddedprf;
    private RichSelectOneChoice pagebinddedname;
    private RichPopup pagebindpop;
    //private RichInputText taxPerc;
    //private RichInputText rangStart;
    //private RichInputText rangEnd;
    private RichSelectOneChoice dedId;
    private RichInputDate dedValistrtDt;
    private RichInputDate dedValidEndDt;
    private RichInputText taxNM;
    private RichSelectOneChoice dedProfId;
    private RichSelectOneRadio taxNature;
    private RichInputText deducLoanDesc;
    private RichInputText maxLnLmt;
    private RichInputText deducMiscName;
    private RichInputText deducMiscEmployeePer;
    private RichInputText maxMiscDeducLimit;
    private RichInputText deducMiscEmployeerPer;
    private RichSelectOneChoice deduSalComp;
    private RichInputText fixDeduAmount;
    private RichSelectOneRadio computationMethod;
    private RichPopup taxPop;
    private RichSelectOneChoice deduSalCompfrTax;
    private RichSelectOneChoice miscNmDedBinding;
    private RichSelectOneChoice taxNmBinding;

    String localModeFrEndDt = "C";
    String localModefrDlt = "D";
    String localModeFrFieldsOnEdit = "E";
    String glLink = "N";
    private RichInputText coaIdBind;
    private RichSelectOneChoice pfRuleBinding;
    private RichInputText miscRangeStartBinding;
    private RichInputText miscRangeEndBinding;
    private RichInputText miscDedPerBinding;
    private RichSelectOneChoice dedAmtPerBinding;
    private RichSelectOneRadio miscDedNatureBinding;
    private RichSelectOneChoice amtRoundOffBinding;
    private RichSelectOneChoice locationIdBinding;
    private RichInputText transStartAmtBinding;
    private RichInputText transEndAmtBinding;
    private RichInputText transTaxValueBinding;
    private RichInputListOfValues bindLocationName;

    public void setGlLink(String glLink) {
        this.glLink = glLink;
    }

    public String getGlLink() {
        return getBindings().getOperationBinding("isGlLinkined").execute().toString();
        //   return glLink;
    }

    public void setLocalModeFrFieldsOnEdit(String localModeFrFieldsOnEdit) {
        this.localModeFrFieldsOnEdit = localModeFrFieldsOnEdit;
    }

    public String getLocalModeFrFieldsOnEdit() {
        return localModeFrFieldsOnEdit;
    }

    public void setLocalModefrDlt(String localModefrDlt) {
        this.localModefrDlt = localModefrDlt;
    }

    public String getLocalModefrDlt() {
        return localModefrDlt;
    }

    public void setLocalModeFrEndDt(String localModeFrEndDt) {
        this.localModeFrEndDt = localModeFrEndDt;
    }

    public String getLocalModeFrEndDt() {
        return localModeFrEndDt;
    }

    String localMode = "V";
    private RichInputText epfPerc;
    private RichInputText fpfPerc;

    public void setLocalMode(String localMode) {
        this.localMode = localMode;
    }

    public String getLocalMode() {
        return localMode;
    }

    public hcmdedprfsetup() {
    }

    public void searchdedname(ActionEvent actionEvent) {
        if (pagebinddedprf.getValue() != null || pagebinddedname.getValue() != null) {
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("searchdedname");
            operationBinding.getParamsMap().put("dedprof", pagebinddedprf.getValue());
            operationBinding.getParamsMap().put("dedname", pagebinddedname.getValue());
            operationBinding.getParamsMap().put("LocationId", locationIdBinding.getValue());
            System.out.println("locationIdBinding" + locationIdBinding.getValue());

            operationBinding.execute();
        }
    }

    public void resetdedname(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("reset");
        ob.execute();
        System.out.println(ob.getErrors());
    }

    public void setPagebinddedprf(RichSelectOneChoice pagebinddedprf) {
        this.pagebinddedprf = pagebinddedprf;
    }

    public RichSelectOneChoice getPagebinddedprf() {
        return pagebinddedprf;
    }

    public void setPagebinddedname(RichSelectOneChoice pagebinddedname) {
        this.pagebinddedname = pagebinddedname;
    }

    public RichSelectOneChoice getPagebinddedname() {
        return pagebinddedname;
    }

    public void addded(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("generateDocId");
        operationBinding.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
    }

    public void cancelded(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
        setLocalModeFrEndDt("C");
        setLocalModefrDlt("D");
        setLocalModeFrFieldsOnEdit("E");
    }

    public void saveded(ActionEvent actionEvent) {
        System.out.println("In Sop Method");
        boolean result = true;
        String mssg = "";
        RequestContext context = RequestContext.getCurrentInstance();
        result = chkDeductionValidation();
        if (result == true) {
            result = chkFldsForMiscNssfAndNhif();
            System.out.println("chkFldsForMiscNssfAndNhif()");
        }
        if (result == true) {
            String profileId = dedProfId.getValue().toString();
            result = chkDeductionFrTaxvalidation(profileId);
            System.out.println("chkDeductionFrTaxvalidation(profileId)()");
        }
        if (result == true) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDeduCtionId");
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                System.out.println("chkDeduCtionId");
                String chkDeduIdStatus = (String) ob.getResult();
                if (chkDeduIdStatus != "exist") {
                    OperationBinding operationBinding1 = ADFBeanUtils.getOperationBinding("generateDedId");
                    operationBinding1.execute();
                    if (operationBinding1.getErrors().isEmpty()) {
                        ADFBeanUtils.getOperationBinding("Commit").execute();
                        System.out.println("Commit");
                        setLocalModeFrEndDt("C");
                        setLocalModefrDlt("D");
                        setLocalModeFrFieldsOnEdit("E");
                        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
                        mssg = "MSG.91";
                        showFacesMessage(mssg, "I", true, "F");
                    } else {
                        mssg = "MSG.1361";
                        showFacesMessage(mssg, "E", true, "F");
                        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
                    }
                } else if (chkDeduIdStatus == "exist") {
                    ADFBeanUtils.getOperationBinding("Commit").execute();
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
                    setLocalModeFrEndDt("C");
                    setLocalModefrDlt("D");
                    setLocalModeFrFieldsOnEdit("E");
                    mssg = "MSG.91";
                    showFacesMessage(mssg, "I", true, "F");
                }
            } else {
                mssg = "MSG.1361";
                showFacesMessage(mssg, "E", true, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            }

        }
        this.getTransStartAmtBinding().setValue("");
        this.getTransEndAmtBinding().setValue("");
        this.getTransTaxValueBinding().setValue("");
    }

    public void popupcall(ActionEvent actionEvent) {
        // Add event code here..
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("showpopdata");
        operationBinding.execute();
        showPopup(pagebindpop, true);
    }

    public void showpopupFrTax(ActionEvent actionEvent) {
        // Add event code here..
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("showpopdata");
        operationBinding.execute();
        showPopup(taxPop, true);
    }


    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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


    public void setPagebindpop(RichPopup pagebindpop) {
        this.pagebindpop = pagebindpop;
    }

    public RichPopup getPagebindpop() {
        return pagebindpop;
    }

    public void addpopup(ActionEvent actionEvent) {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedsalO1Iterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkDeductionSalComponent();
        }
        if (result == true) {
            ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        }


    }

    public void addpopupFrTax(ActionEvent actionEvent) {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedsalO1Iterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkDeductionSalComponentFrTax();
        }
        if (result == true) {
            setLocalMode("S");
            ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        }


    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addtaxslab(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("postCha").execute();
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedslab1Iterator"); //hcmdedslab1Iterator
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkTaxvalidation();
        }

        if (result == true) {
            ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
            ADFBeanUtils.getOperationBinding("setSeqNumInTaxRange").execute();
        }


    }

  public boolean chkTaxvalidation() {
        /* String message = "";
        if (taxPerc.getValue() == null || taxPerc.getValue() == "") {
            String cid = taxPerc.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1362']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (rangStart.getValue() == null || rangStart.getValue() == "") {
            String cid = rangStart.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1363']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (rangEnd.getValue() == null || rangEnd.getValue() == "") {
            String cid = rangEnd.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1364']}").toString();
            showMessage(message, cid);
            return false;
        } */
        return true;
    } 

    public boolean chkDeductionValidation() {
        String message = "";
        if (dedProfId.getValue() == null || dedProfId.getValue() == "") {
            String cid = dedProfId.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1365']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (dedValistrtDt.getValue() == null || dedValistrtDt.getValue() == "") {
            String cid = dedValistrtDt.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1366']}").toString();
            showMessage(message, cid);
            return false;
        }
        return true;
    }

    public void setTaxNmBinding(RichSelectOneChoice taxNmBinding) {
        this.taxNmBinding = taxNmBinding;
    }

    public RichSelectOneChoice getTaxNmBinding() {
        return taxNmBinding;
    }


    public boolean chkDeductionFrTaxvalidation(String profileId) {
        String message = "";
        boolean result = true;

        System.out.println(taxNature.getValue());
        if (profileId.equals("48")) {


            if (taxNmBinding.getValue() == null || taxNmBinding.getValue() == "") {
                String cid = taxNmBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1367']}").toString();
                showMessage(message, cid);
                result = false;
            }
            if (result == true)
                if (taxNature.getValue() == null || taxNature.getValue() == "") {
                    String cid = taxNature.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1368']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
            if (result == true)
                if (computationMethod.getValue() == null || computationMethod.getValue() == "") {
                    String cid = computationMethod.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1369']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
            if (result == true) {
                DCIteratorBinding di = (DCIteratorBinding) getBindings().get("hcmdedsalO1Iterator");
                if (di.getEstimatedRowCount() == 0) {
                    message = resolvElDCMsg("#{bundle['MSG.1370']}").toString();
                    showMessage(message, null);
                    result = false;
                }
                if (result == true) {
                    if (taxNature.getValue() != null || taxNature.getValue() != "") {
                        String newtxNature = (String) taxNature.getValue();
                        if (newtxNature.equals("S")) {
                            DCIteratorBinding difrSlab =
                                ADFBeanUtils.findIterator("hcmdedslab1Iterator"); //hcmdedslab1Iterator
                            if (difrSlab.getEstimatedRowCount() > 0) {
                                result = chkTaxvalidation();
                            } else {
                                message = resolvElDCMsg("#{bundle['MSG.1371']}").toString();
                                showMessage(message, null);
                                result = false;
                            }
                        }
                        if (newtxNature.equals("F")) {
                            if (fixDeduAmount.getValue() == null || fixDeduAmount.getValue() == "") {
                                String cid = fixDeduAmount.getClientId();
                                message = resolvElDCMsg("#{bundle['MSG.1372']}").toString();
                                showMessage(message, cid);
                                result = false;
                            }
                        }
                    }
                }

            }

        }
        if (profileId.equals("49")) {
            if (deducLoanDesc.getValue() == null || deducLoanDesc.getValue() == "" ||
                (deducLoanDesc.getValue().toString().length() > 0 &&
                 deducLoanDesc.getValue().toString().trim().length() == 0)) {
                String cid = deducLoanDesc.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1373']}").toString();
                showMessage(message, cid);
                result = false;
            }
            //            if (maxLnLmt.getValue() == null || maxLnLmt.getValue() == "") {
            //                String cid = maxLnLmt.getClientId();
            //                message =  resolvElDCMsg("#{bundle['MSG.1374']}").toString();
            //                showMessage(message, cid);
            //                result = false;
            //            }
        }
        if (profileId.equals("50")) {
            if (amtRoundOffBinding.getValue() == null || amtRoundOffBinding.getValue() == "") {
                String cid = amtRoundOffBinding.getClientId();
                message = "Amount round off can not be left blank!";
                showMessage(message, cid);
                result = false;
            }
            if (miscNmDedBinding.getValue() == null || miscNmDedBinding.getValue() == "") {
                String cid = miscNmDedBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1375']}").toString();
                showMessage(message, cid);
                result = false;
            }
            if (result == true)
                if (miscNmDedBinding.getValue() != null || miscNmDedBinding.getValue() != "") {
                    Integer dedNm = (Integer) miscNmDedBinding.getValue();
                    if (dedNm == 66) {
                        if (epfPerc.getValue() == null || epfPerc.getValue() == "") {
                            String cid = epfPerc.getClientId();
                            message = resolvElDCMsg("#{bundle['MSG.1614']}").toString();
                            showMessage(message, cid);
                            result = false;
                        }
                        if (result == true)
                            if (fpfPerc.getValue() == null || fpfPerc.getValue() == "") {
                                String cid = fpfPerc.getClientId();
                                message = resolvElDCMsg("#{bundle['MSG.1613']}").toString();
                                showMessage(message, cid);
                                result = false;
                            }
                    }

                }
            if (result == true && (miscNmDedBinding.getValue().equals(66) || miscNmDedBinding.getValue().equals(67)))
                if (deducMiscEmployeePer.getValue() == null || deducMiscEmployeePer.getValue() == "") {
                    String cid = deducMiscEmployeePer.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1376']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
            if (result == true &&
                (miscNmDedBinding.getValue().equals(66) || miscNmDedBinding.getValue().equals(67) ||
                 miscNmDedBinding.getValue().equals(94)))
                if (maxMiscDeducLimit.getValue() == null || maxMiscDeducLimit.getValue() == "") {
                    String cid = maxMiscDeducLimit.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1377']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
            if (result == true &&
                (miscNmDedBinding.getValue().equals(66) || miscNmDedBinding.getValue().equals(67) ||
                 miscNmDedBinding.getValue().equals(94)))
                if (deducMiscEmployeerPer.getValue() == null || deducMiscEmployeerPer.getValue() == "") {
                    String cid = deducMiscEmployeerPer.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1378']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
            if (result == true &&
                (miscNmDedBinding.getValue().equals(66) || miscNmDedBinding.getValue().equals(67) ||
                 miscNmDedBinding.getValue().equals(94))) {
                DCIteratorBinding di = (DCIteratorBinding) getBindings().get("hcmdedsalO1Iterator");
                if (di.getEstimatedRowCount() == 0) {
                    message = resolvElDCMsg("#{bundle['MSG.1370']}").toString();
                    showMessage(message, null);
                    result = false;
                }
            }
        }
        return result;
    }

    /**Validate fields for NSSF and NHIF*/
    public boolean chkFldsForMiscNssfAndNhif() {

        if (miscNmDedBinding.getValue().equals(88) || miscNmDedBinding.getValue().equals(89)) {
            if (dedAmtPerBinding.getValue() == null) {
                String cid = dedAmtPerBinding.getClientId();
                String message = "Please select Amount or Percent!";
                showMessage(message, cid);
                return false;
            }
            if (miscDedNatureBinding.getValue() == null || miscDedNatureBinding.getValue() == "") {
                String cid = miscDedNatureBinding.getClientId();
                String message = "Please select dedution nature!";
                showMessage(message, cid);
                return false;
            } else {
                if (miscDedNatureBinding.getValue().toString().equalsIgnoreCase("F")) {
                    if (deducMiscEmployeePer.getValue() == null || deducMiscEmployeePer.getValue() == "") {
                        String cid = deducMiscEmployeePer.getClientId();
                        String message = resolvElDCMsg("#{bundle['MSG.1376']}").toString();
                        showMessage(message, cid);
                        return false;
                    }

                    if (deducMiscEmployeerPer.getValue() == null || deducMiscEmployeerPer.getValue() == "") {
                        String cid = deducMiscEmployeerPer.getClientId();
                        String message = resolvElDCMsg("#{bundle['MSG.1378']}").toString();
                        showMessage(message, cid);
                        return false;
                    }
                }
                if (dedAmtPerBinding.getValue().toString().equalsIgnoreCase("P")) {
                    DCIteratorBinding di = (DCIteratorBinding) getBindings().get("hcmdedsalO1Iterator");
                    if (di.getEstimatedRowCount() == 0) {
                        String message = resolvElDCMsg("#{bundle['MSG.1370']}").toString();
                        showMessage(message, null);
                        return false;
                    }
                }
                if (dedAmtPerBinding.getValue().toString().equalsIgnoreCase("A") &&
                    miscDedNatureBinding.getValue().toString().equalsIgnoreCase("S")) {
                    DCIteratorBinding di = (DCIteratorBinding) getBindings().get("hcmdedsalO1Iterator");
                    if (di.getEstimatedRowCount() == 0) {
                        String message = resolvElDCMsg("#{bundle['MSG.1370']}").toString();
                        showMessage(message, null);
                        return false;
                    }
                }
                if (dedAmtPerBinding.getValue().toString().equalsIgnoreCase("A") &&
                    (!miscDedNatureBinding.getValue().toString().equalsIgnoreCase("S"))) {
                    DCIteratorBinding di = (DCIteratorBinding) getBindings().get("hcmdedsalO1Iterator");
                    if (di.getEstimatedRowCount() > 0) {
                        String message =
                            "Please remove salary components in case of type is Amount and nature is fixed!";
                        showMessage(message, null);
                        return false;
                    }
                }

                if (miscDedNatureBinding.getValue().toString().equalsIgnoreCase("S")) {
                    DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedslab1Iterator");
                    if (di.getEstimatedRowCount() == 0) {
                        String message = "Please add slab to this deduction!";
                        showMessage(message, null);
                        return false;
                    } else {
                        boolean rslt = chkMiscvalidation();
                        return rslt;
                    }
                }
            }
        }
        return true;
    }

    public void setMiscNmDedBinding(RichSelectOneChoice miscNmDedBinding) {
        this.miscNmDedBinding = miscNmDedBinding;
    }

    public RichSelectOneChoice getMiscNmDedBinding() {
        return miscNmDedBinding;
    }

    public void editAllFields(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDeducUsageInEmpDed");
        ob.execute();
        if (ob.getErrors().size() > 0 || ob.getResult() == null) {
            System.out.println("Error on check duplicate=" + ob.getErrors());
        }

        else {
            if (ob.getResult().toString().equals("N")) {
                if (dedValidEndDt.getValue() == "" || dedValidEndDt.getValue() == null) {
                    setLocalModeFrEndDt("E");
                    String message = "MSG.1617";
                    showFacesMessage(message, "I", true, "F");
                } else {
                    String message = "MSG.1379";
                    showFacesMessage(message, "W", true, "F");
                }

            } else {


                if (dedValidEndDt.getValue() == "" || dedValidEndDt.getValue() == null) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
                    setLocalModeFrEndDt("C");
                    setLocalModefrDlt("C");
                } else {
                    setLocalModeFrFieldsOnEdit("D");
                    setLocalModeFrEndDt("C");
                    setLocalModefrDlt("C");
                }


            }

        }

    }

    public String showMessage(String message, String cid) {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void setTaxPerc(RichInputText taxPerc) {
        //this.taxPerc = taxPerc;
    }
    
    public void setDedId(RichSelectOneChoice dedId) {
        this.dedId = dedId;
    }

    public RichSelectOneChoice getDedId() {
        return dedId;
    }

    public void setDedValistrtDt(RichInputDate dedValistrtDt) {
        this.dedValistrtDt = dedValistrtDt;
    }

    public RichInputDate getDedValistrtDt() {
        return dedValistrtDt;
    }

    public void setDedValidEndDt(RichInputDate dedValidEndDt) {
        this.dedValidEndDt = dedValidEndDt;
    }

    public RichInputDate getDedValidEndDt() {
        return dedValidEndDt;
    }

    public void setTaxNM(RichInputText taxNM) {
        this.taxNM = taxNM;
    }

    public RichInputText getTaxNM() {
        return taxNM;
    }

    public void setDedProfId(RichSelectOneChoice dedProfId) {
        this.dedProfId = dedProfId;
    }

    public RichSelectOneChoice getDedProfId() {
        return dedProfId;
    }

    public void setTaxNature(RichSelectOneRadio taxNature) {
        this.taxNature = taxNature;
    }

    public RichSelectOneRadio getTaxNature() {
        return taxNature;
    }

    public void setDeducLoanDesc(RichInputText deducLoanDesc) {
        this.deducLoanDesc = deducLoanDesc;
    }

    public RichInputText getDeducLoanDesc() {
        return deducLoanDesc;
    }

    public void setMaxLnLmt(RichInputText maxLnLmt) {
        this.maxLnLmt = maxLnLmt;
    }

    public RichInputText getMaxLnLmt() {
        return maxLnLmt;
    }

    public void setDeducMiscName(RichInputText deducMiscName) {
        this.deducMiscName = deducMiscName;
    }

    public RichInputText getDeducMiscName() {
        return deducMiscName;
    }

    public void setDeducMiscEmployeePer(RichInputText deducMiscEmployeePer) {
        this.deducMiscEmployeePer = deducMiscEmployeePer;
    }

    public RichInputText getDeducMiscEmployeePer() {
        return deducMiscEmployeePer;
    }

    public void setMaxMiscDeducLimit(RichInputText maxMiscDeducLimit) {
        this.maxMiscDeducLimit = maxMiscDeducLimit;
    }

    public RichInputText getMaxMiscDeducLimit() {
        return maxMiscDeducLimit;
    }

    public void setDeducMiscEmployeerPer(RichInputText deducMiscEmployeerPer) {
        this.deducMiscEmployeerPer = deducMiscEmployeerPer;
    }

    public RichInputText getDeducMiscEmployeerPer() {
        return deducMiscEmployeerPer;
    }

    public void validateEndDt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dedValistrtDt.getValue() != null && dedValistrtDt.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) dedValistrtDt.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        String message = "MSG.1380";
                        showFacesMessage(message, "E", true, "V");
                    }
                }
            }
        }
    }


    public boolean chkDeductionSalComponent() {
        String message = "";
        boolean result = true;
        if (deduSalComp.getValue() == null || deduSalComp.getValue() == "") {
            String cid = deduSalComp.getClientId();
            //message = "Please select salary component.";
            message = resolvElDCMsg("#{bundle['MSG.1381']}").toString();
            showMessage(message, cid);
            result = false;

        }
        return result;
    }

    public boolean chkDeductionSalComponentFrTax() {
        String message = "";
        boolean result = true;
        if (deduSalCompfrTax.getValue() == null || deduSalCompfrTax.getValue() == "") {
            String cid = deduSalCompfrTax.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1381']}").toString();
            showMessage(message, cid);
            result = false;

        }
        return result;
    }

    public void chkValidationFrTaxRangeStart(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                //String msg = "Invalid start range.";
                String message = resolvElDCMsg("#{bundle['MSG.1382']}").toString();
                showFacesMessage(message, "E", false, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    //String msg = "Please enter valid start range.";
                    String message = resolvElDCMsg("#{bundle['MSG.1384']}").toString();
                    showFacesMessage(message, "E", true, "V");
                } else {
                    /* OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkMaxSal");
                    opChk.execute();
                    String val = (String) opChk.getResult();
                    System.out.println("Bean : " + val);
                    if (val.contains(":")) {
                        String[] split = val.split(":");
                        BigDecimal startL = new BigDecimal(split[0]);
                        BigDecimal endL = new BigDecimal(split[1]);
                        if (value.compareTo(startL) <= 0 || value.compareTo(endL) >= 0) {
                            String message =
                                "Please Insert value in range " + startL.add(BigDecimal.ONE) + " and " +
                                endL.subtract(BigDecimal.ONE);
                            showFacesMessage(message, "E", false, "V");
                        }
                    } else {
                        String message = null;
                        BigDecimal vue = new BigDecimal(val);
                        if (vue.compareTo(new BigDecimal(0)) != 0) {
                            if (value.compareTo(vue) >= 0) {
                                message = "Value must be less than " + vue;
                                showFacesMessage(message, "E", false, "V");
                            }
                        }
                    } */
                }
                //              else if (newvalue==0)
                //                 {
                //                    String msg = "Please enter valid start range.";
                //                      showFacesMessage(msg, "E", false, "V");
                //                }
            }

        }
    }

    public void chkValidateTaxPerc(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                //String msg = "Please enter valid percentage."; MSG.1386
                String msg = "MSG.1386";
                showFacesMessage(msg, "E", true, "V");
            } else {
                if (taxNmBinding.getValue() != null && taxNmBinding.getValue() != "") {
                    if (taxNmBinding.getValue().toString().equals("64")) {

                        BigDecimal frCmpreZro = BigDecimal.ZERO;
                        BigDecimal value = new BigDecimal(object.toString());
                        BigDecimal cmpreWdHund = new BigDecimal(100);
                        int newvalue = value.compareTo(frCmpreZro);
                        int newvalueFrHund = value.compareTo(cmpreWdHund);
                        System.out.println("compare result==" + newvalue);
                        if (newvalue == -1) {
                            String msg = "MSG.1386";
                            showFacesMessage(msg, "E", true, "V");
                        } else if (newvalueFrHund == 0 || newvalueFrHund == +1) {
                            String msg = "MSG.1386";
                            showFacesMessage(msg, "E", true, "V");
                        }

                    }
                }

            }

        }

    }

//    public void chkValidationFrTaxRangeEnd(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        if (object != null && object.toString().length() > 0) {
//
//            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
//            if (flag == false) {
//                //String msg = "Invalid end range."; MSG.1388
//                String msg = "MSG.1388";
//                showFacesMessage(msg, "E", true, "V");
//            } else {
//                BigDecimal frCmpreZro = BigDecimal.ZERO;
//                BigDecimal value = new BigDecimal(object.toString());
//                int newvalue = value.compareTo(frCmpreZro);
//                System.out.println("compare result==" + newvalue);
//                if (newvalue == -1) {
//                    //String msg = "Please enter valid end range.";
//                    String msg = "MSG.1389";
//                    showFacesMessage(msg, "E", true, "V");
//                } else if (newvalue == 0) {
//                    //String msg = "Please enter valid end range.";
//                    String msg = "MSG.1389";
//                    showFacesMessage(msg, "E", true, "V");
//                } else if (newvalue == 1) {
//                    if (rangStart.getValue() != null && rangStart.getValue().toString().length() > 0) {
//                        BigDecimal rangestartvalue = new BigDecimal(rangStart.getValue().toString());
//                        int valuefrrange = value.compareTo(rangestartvalue);
//                        if (valuefrrange <= 0) {
//                            //String msg = "Please enter valid end range:End range should be greater than start range.";MSG.1390
//                            String msg = "MSG.1390";
//                            showFacesMessage(msg, "E", true, "V");
//                        } else {
//                            /* OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkMaxSal");
//                            opChk.execute();
//                            String val = (String) opChk.getResult();
//                            System.out.println("Bean : " + val);
//                            if (val.contains(":")) {
//                                String[] split = val.split(":");
//                                BigDecimal startL = new BigDecimal(split[0]);
//                                BigDecimal endL = new BigDecimal(split[1]);
//                                if (value.compareTo(startL) <= 0 || value.compareTo(endL) >= 0) {
//                                    String message =
//                                       "Please Insert value in range " + (startL.add(BigDecimal.ONE)) + " and " +
//                                        (endL.subtract(BigDecimal.ONE));
//                                  showFacesMessage(message, "E", false, "V");
//                              }
//                            } else {
//                                String message = null;
//                                BigDecimal vue = new BigDecimal(val);
//                                if (vue.compareTo(new BigDecimal(0)) != 0) {
//                                    if (value.compareTo(vue) <= 0) {
//                                        message = "Value must be greater than " + vue;
//                                        showFacesMessage(message, "E", false, "V");
//                                    }
//                                }
//                            } */
//                        }
//                    }
//                }
//
//
//            }
//        }
//    }

    public void chkValidationFrMaxLoanLmt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1391";
                showFacesMessage(msg, "E", true, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1391";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1391";
                    showFacesMessage(msg, "E", true, "V");
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

    public String closePOP() {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedsalO1Iterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkDeductionSalComponent();
            System.out.println("result during close====" + result);
        }
        if (result == true) {
            pagebindpop.hide();
        }
        return null;
    }

    public String closePOPfrTax() {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedsalO1Iterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkDeductionSalComponentFrTax();
            System.out.println("result during close====" + result);
        }
        if (result == true) {
            taxPop.hide();
        }
        return null;
    }

    public void setDeduSalComp(RichSelectOneChoice deduSalComp) {
        this.deduSalComp = deduSalComp;
    }

    public RichSelectOneChoice getDeduSalComp() {
        return deduSalComp;
    }

    public void setFixDeduAmount(RichInputText fixDeduAmount) {
        this.fixDeduAmount = fixDeduAmount;
    }

    public RichInputText getFixDeduAmount() {
        return fixDeduAmount;
    }

    public void chkValidFixAmount(FacesContext facesContext, UIComponent uIComponent,
                                  Object object) throws SQLException {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, new Number(object));
            if (flag == false) {
                String msg = "MSG.1392";
                showFacesMessage(msg, "E", true, "V");

            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1393";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1393";
                    showFacesMessage(msg, "E", true, "V");
                }
            }
        }

    }

    public void chkValidateEmployeePerc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1386";
                showFacesMessage(msg, "E", true, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                BigDecimal cmpreWdHund = new BigDecimal(100);
                int newvalue = value.compareTo(frCmpreZro);
                int newvalueFrHund = value.compareTo(cmpreWdHund);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0 && !(miscNmDedBinding.getValue().equals(94))) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalueFrHund == 0 || newvalueFrHund == +1) {
                    if (dedAmtPerBinding.getValue() == null || dedAmtPerBinding.getValue().equals("P")) {
                        String msg = "MSG.1386";
                        showFacesMessage(msg, "E", true, "V");
                    }
                }
            }

        }

    }

    public void chkValidateEmployerPerc(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1386";
                showFacesMessage(msg, "E", true, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                BigDecimal cmpreWdHund = new BigDecimal(100);
                int newvalue = value.compareTo(frCmpreZro);
                int newvalueFrHund = value.compareTo(cmpreWdHund);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                }

                else if (newvalueFrHund == 0 || newvalueFrHund == +1) {
                    if (dedAmtPerBinding.getValue() == null || dedAmtPerBinding.getValue().equals("P")) {
                        String msg = "MSG.1386";
                        showFacesMessage(msg, "E", true, "V");
                    }
                }
            }

        }

    }

    public void chkValidMiscDeducAmount(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1394";
                showFacesMessage(msg, "E", true, "V");

            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1394";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1394";
                    showFacesMessage(msg, "E", true, "V");
                }
            }
        }

    }

    public void chkValidFPFAmount(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1107";
                showFacesMessage(msg, "E", true, "V");

            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1616";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1394";
                    showFacesMessage(msg, "E", true, "V");
                }
            }
        }

    }

    public void chkValidEPFAmount(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg = "MSG.1107";
                showFacesMessage(msg, "E", true, "V");

            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1615";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    String msg = "MSG.1394";
                    showFacesMessage(msg, "E", true, "V");
                }
            }
        }

    }

    public void chkDuplicateMiscDeductionName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        checkDuplicateDeductionName(object);
    }

    public void chkDuplicateLoanDeductionName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        checkDuplicateDeductionName(object);
    }

    public void chkDuplicateTaxDeductionName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (object.toString().equals("64")) {
                System.out.println("64");
                taxNature.setValue("S");
                computationMethod.setValue("M");
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxNature);
                AdfFacesContext.getCurrentInstance().addPartialTarget(computationMethod);

            }
            if (object.toString().equals("65")) {
                System.out.println("65");
                taxNature.setValue("S");
                computationMethod.setValue("A");
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxNature);
                AdfFacesContext.getCurrentInstance().addPartialTarget(computationMethod);
            }
        }
        checkDuplicateDeductionName(object);
    }

    public void checkDuplicateDeductionName(Object object) {
        if (object != null && object.toString().length() > 0) {
            if (object.toString().trim().length() > 0) {
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateDeduName");
                opChk.getParamsMap().put("DedDesc", object.toString().trim());
                opChk.execute();
                if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                    System.out.println("Error on check duplicate=" + opChk.getErrors());
                else {
                    if (opChk.getResult().toString().equals("Y"))

                        showFacesMessage("MSG.1395", "E", true, "V");
                }
            } else {
                showFacesMessage("MSG.1396", "E", true, "V");

            }
        }
    }

    public void setComputationMethod(RichSelectOneRadio computationMethod) {
        this.computationMethod = computationMethod;
    }

    public RichSelectOneRadio getComputationMethod() {
        return computationMethod;
    }

    public void deletComponent(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
    }


    public void setTaxPop(RichPopup taxPop) {
        this.taxPop = taxPop;
    }

    public RichPopup getTaxPop() {
        return taxPop;
    }

    public void setDeduSalCompfrTax(RichSelectOneChoice deduSalCompfrTax) {
        this.deduSalCompfrTax = deduSalCompfrTax;
    }

    public RichSelectOneChoice getDeduSalCompfrTax() {
        return deduSalCompfrTax;
    }

    public void chkDuplicateslryComp(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateSlryCmponent");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }

        }
    }

    public void deletTxSlab(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void deleteslryCmponentfrmTax(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
        setLocalMode("V");
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setEpfPerc(RichInputText epfPerc) {
        this.epfPerc = epfPerc;
    }

    public RichInputText getEpfPerc() {
        return epfPerc;
    }

    public void setFpfPerc(RichInputText fpfPerc) {
        this.fpfPerc = fpfPerc;
    }

    public RichInputText getFpfPerc() {
        return fpfPerc;
    }

    public void setValues(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            if (valueChangeEvent.getNewValue().toString().equals("64")) {
                System.out.println("64");
                taxNature.setValue("S");
                computationMethod.setValue("M");
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxNature);
                AdfFacesContext.getCurrentInstance().addPartialTarget(computationMethod);

            }
            if (valueChangeEvent.getNewValue().toString().equals("65")) {
                System.out.println("65");
                taxNature.setValue("S");
                computationMethod.setValue("A");
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxNature);
                AdfFacesContext.getCurrentInstance().addPartialTarget(computationMethod);
            }
        }
    }

    public void chkPrevDedStrtDt(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding ob = null;
            Calendar calendar = Calendar.getInstance();
            java.sql.Date todayDate = null;
            try {
                todayDate = new java.sql.Date(calendar.getTime().getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
            ob.getParamsMap().put("dedDCurrDtt", todayDate);
            ob.execute();
            if (ob.getResult().toString() != null) {
                if (ob.getResult().toString().equals("N")) {
                    ob = ADFBeanUtils.getOperationBinding("getDedDate");
                    ob.execute();
                    if (ob.getResult().toString() != "") {

                        String date = ob.getResult().toString();
                        String message =
                            "Can not create new deduction :Previous Deduction will end on " + date +
                            " After this date new deduction can be create.";
                        showFacesMessage(message, "E", false, "V");
                    }

                } else

                {
                    java.sql.Date ValidStrtDt = null;
                    try {
                        ValidStrtDt = ((Timestamp) object).dateValue();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ob = ADFBeanUtils.getOperationBinding("chkPrevDate");
                    ob.getParamsMap().put("dedDCurrDtt", ValidStrtDt);
                    ob.execute();
                    if (ob.getResult().toString().equals("N")) {
                        ob = ADFBeanUtils.getOperationBinding("getDedDate");
                        ob.execute();
                        if (ob.getResult().toString() != "") {

                            String date = ob.getResult().toString();
                            String message =
                                "Last inactive deduction end date is " + date +
                                " After this date new deduction can be create.";
                            showFacesMessage(message, "E", false, "V");
                        }

                    }
                }

            }
        }


    }

    public void deleteDeduction(ActionEvent actionEvent) {
        String mssg = "";
        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDeducUsage");
        ob.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        if (ob.getErrors().size() > 0 || ob.getResult() == null) {
            System.out.println("Error on check duplicate=" + ob.getErrors());
        }

        else {
            if (ob.getResult().toString().equals("N")) {
                mssg = "Can not delete this deduction:This deduction is being used by some employee.";
                showFacesMessage(mssg, "I", false, "F");


            } else {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("delFromChild");
                opchk.execute();
                if (opchk.getResult().toString().equals("success")) {
                    ADFBeanUtils.getOperationBinding("Delete2").execute();
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
                    setLocalModefrDlt("S");

                }

            }

        }
    }

    public void setCoaIdBind(RichInputText coaIdBind) {
        this.coaIdBind = coaIdBind;
    }

    public RichInputText getCoaIdBind() {
        return coaIdBind;
    }

    public void dedTypeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(66)) {
            pfRuleBinding.setValue(86);
        } else {
            pfRuleBinding.setValue(null);
            epfPerc.setValue(null);
            fpfPerc.setValue(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(pfRuleBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(epfPerc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fpfPerc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(deducMiscEmployeerPer);
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setPfRuleBinding(RichSelectOneChoice pfRuleBinding) {
        this.pfRuleBinding = pfRuleBinding;
    }

    public RichSelectOneChoice getPfRuleBinding() {
        return pfRuleBinding;
    }

    public void addMiscSlabAL(ActionEvent actionEvent) {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmdedslab1Iterator"); //hcmdedslab1Iterator
        boolean result = true;
        if (di.getEstimatedRowCount() > 0) {
            result = chkMiscvalidation();
        }

        if (result == true) {
            ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
        }

    }

    public boolean chkMiscvalidation() {
        String message = "";

        if (miscDedPerBinding.getValue() == null || miscDedPerBinding.getValue() == "") {
            String cid = miscDedPerBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1362']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (miscRangeStartBinding.getValue() == null || miscRangeStartBinding.getValue() == "") {
            String cid = miscRangeStartBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1363']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (miscRangeEndBinding.getValue() == null || miscRangeEndBinding.getValue() == "") {
            String cid = miscRangeEndBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1364']}").toString();
            showMessage(message, cid);
            return false;
        }

        return true;
    }

    public void deleteMiscSlabAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void setMiscRangeStartBinding(RichInputText miscRangeStartBinding) {
        this.miscRangeStartBinding = miscRangeStartBinding;
    }

    public RichInputText getMiscRangeStartBinding() {
        return miscRangeStartBinding;
    }

    public void setMiscRangeEndBinding(RichInputText miscRangeEndBinding) {
        this.miscRangeEndBinding = miscRangeEndBinding;
    }

    public RichInputText getMiscRangeEndBinding() {
        return miscRangeEndBinding;
    }

    public void setMiscDedPerBinding(RichInputText miscDedPerBinding) {
        this.miscDedPerBinding = miscDedPerBinding;
    }

    public RichInputText getMiscDedPerBinding() {
        return miscDedPerBinding;
    }

    public void miscRangeStartValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                //String msg = "Invalid start range.";
                String message = resolvElDCMsg("#{bundle['MSG.1382']}").toString();
                showFacesMessage(message, "E", false, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    //String msg = "Please enter valid start range.";
                    String message = resolvElDCMsg("#{bundle['MSG.1384']}").toString();
                    showFacesMessage(message, "E", false, "V");
                }
                //                else if (newvalue==0)
                //                 {
                //                    String msg = "Please enter valid start range.";
                //                      showFacesMessage(msg, "E", false, "V");
                //                }
            }

        }


    }

    public void miscRangeEndValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {

            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                //String msg = "Invalid end range."; MSG.1388
                String msg = "MSG.1388";
                showFacesMessage(msg, "E", true, "V");
            } else {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    //String msg = "Please enter valid end range.";
                    String msg = "MSG.1389";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == 0) {
                    //String msg = "Please enter valid end range.";
                    String msg = "MSG.1389";
                    showFacesMessage(msg, "E", true, "V");
                } else if (newvalue == +1) {
                    if (miscRangeStartBinding.getValue() != null &&
                        miscRangeStartBinding.getValue().toString().length() > 0) {
                        BigDecimal rangestartvalue = new BigDecimal(miscRangeStartBinding.getValue().toString());
                        int valuefrrange = value.compareTo(rangestartvalue);
                        if (valuefrrange == -1) {
                            //String msg = "Please enter valid end range:End range should be greater than start range.";MSG.1390
                            String msg = "MSG.1390";
                            showFacesMessage(msg, "E", true, "V");
                        }
                    }
                }


            }
        }


    }

    public void miscRangeStartVCL(ValueChangeEvent valueChangeEvent) {
        miscRangeEndBinding.setValue(null);
    }

    public void miscDedPercentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                //String msg = "Please enter valid percentage."; MSG.1386
                String msg = "MSG.1386";
                showFacesMessage(msg, "E", true, "V");
            } else {

                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                BigDecimal cmpreWdHund = new BigDecimal(100);
                int newvalue = value.compareTo(frCmpreZro);
                int newvalueFrHund = value.compareTo(cmpreWdHund);
                System.out.println("compare result==" + newvalue);
                if (newvalue == -1) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                }


                else if (newvalueFrHund == 0 || newvalueFrHund == +1) {
                    if (dedAmtPerBinding.getValue() == null || dedAmtPerBinding.getValue().equals("P")) {
                        String msg = "MSG.1386";
                        showFacesMessage(msg, "E", true, "V");
                    }
                }
            }

        }

    }

    public void setDedAmtPerBinding(RichSelectOneChoice dedAmtPerBinding) {
        this.dedAmtPerBinding = dedAmtPerBinding;
    }

    public RichSelectOneChoice getDedAmtPerBinding() {
        return dedAmtPerBinding;
    }

    public void setMiscDedNatureBinding(RichSelectOneRadio miscDedNatureBinding) {
        this.miscDedNatureBinding = miscDedNatureBinding;
    }

    public RichSelectOneRadio getMiscDedNatureBinding() {
        return miscDedNatureBinding;
    }

    public void miscDedNatureVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("S")) {
                deducMiscEmployeePer.setValue(null);
                deducMiscEmployeerPer.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(deducMiscEmployeerPer);
                AdfFacesContext.getCurrentInstance().addPartialTarget(deducMiscEmployeePer);

            }
        }
    }

    public void setAmtRoundOffBinding(RichSelectOneChoice amtRoundOffBinding) {
        this.amtRoundOffBinding = amtRoundOffBinding;
    }

    public RichSelectOneChoice getAmtRoundOffBinding() {
        return amtRoundOffBinding;
    }

    public void setLocationIdBinding(RichSelectOneChoice locationIdBinding) {
        this.locationIdBinding = locationIdBinding;
    }

    public RichSelectOneChoice getLocationIdBinding() {
        return locationIdBinding;
    }

    public void addSalarySlabBean(ActionEvent actionEvent) {
        
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("addSalarySlabAm");
        operationBinding.execute();
        Object o = operationBinding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i == 1) {
            ADFModelUtils.showFacesMessage("salary start range invalid", "salary start range invalid",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it24"));
        } else if (i == 2) {
            ADFModelUtils.showFacesMessage("salary end range invalid", "salary end range invalid",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it25"));
        } else if (i == 3) {
            ADFModelUtils.showFacesMessage("salary start range cannot be greater than  salary end range",
                                           "salary start range cannot be greater than  salary end range",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it24"));
        } else if (i == 4) {
            ADFModelUtils.showFacesMessage("tax value  invalid", "tax value  invalid",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it26"));
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
        
    }

    public void setTransStartAmtBinding(RichInputText transStartAmtBinding) {
        this.transStartAmtBinding = transStartAmtBinding;
    }

    public RichInputText getTransStartAmtBinding() {
        return transStartAmtBinding;
    }

    public void setTransEndAmtBinding(RichInputText transEndAmtBinding) {
        this.transEndAmtBinding = transEndAmtBinding;
    }

    public RichInputText getTransEndAmtBinding() {
        return transEndAmtBinding;
    }

    public void setTransTaxValueBinding(RichInputText transTaxValueBinding) {
        this.transTaxValueBinding = transTaxValueBinding;
    }

    public RichInputText getTransTaxValueBinding() {
        return transTaxValueBinding;
    }

    public void chkDuplicateLocationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
          if (object != null && object.toString().length() > 0) {
             System.out.println("object is ::::: "+object);
            OperationBinding ob = ADFBeanUtils.getOperationBinding("checkDuplicateLocation");
            ob.execute();
            System.out.println("result in duplicate location is" + ob.getResult());
            if(ob.getResult() != null && ob.getResult().equals(true))
            { 
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                            "Error", "ESI is already exixt for this location.")); 
            }

        } 
        
    }

    public void setBindLocationName(RichInputListOfValues bindLocationName) {
        this.bindLocationName = bindLocationName;
    }

    public RichInputListOfValues getBindLocationName() {
        return bindLocationName;
    }
}

