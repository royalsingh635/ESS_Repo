package mnfProductionOrderApp.view.Bean;

import adf.utils.bean.ADFBeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.adf.view.rich.component.rich.output.RichPanelCollection;

import oracle.jbo.domain.Number;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mnfProductionOrderApp.model.services.MNFProductionorderAppAMImpl;

import mnfProductionOrderApp.view.Utils.ADFUtils;
import mnfProductionOrderApp.view.Utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class productionOrderBean {

    OperationBinding ob = null;
    String pdoMode =
        resolvEl("#{pageFlowScope.PDO_MODE}") == null ? null : resolvEl("#{pageFlowScope.PDO_MODE}").toString();
    String jcAllow =
        resolvEl("#{pageFlowScope.JC_ALLOW}") == null ? null : resolvEl("#{pageFlowScope.JC_ALLOW}").toString();
    oracle.jbo.domain.Number ccChk = new oracle.jbo.domain.Number(0);
    private RichInputListOfValues pdoSrcSalesForBind;
    private RichInputListOfValues pdoSubContractNobind;
    private RichSelectBooleanCheckbox shortcloseChk;

    public void setCcChk(Number ccChk) {
        this.ccChk = ccChk;
    }

    public Number getCcChk() {
        return ccChk;
    }
    private RichInputText pdoBOMIdBind;
    private RichInputText pdoRouteIdBind;
    private RichInputDate pdoDocDateBind;
    private RichInputText pdoOutputQtyBind;
    private RichInputText pdoIdBind;
    String switcheVal = null;
    private RichOutputText custIdBindVar;
    private UIXSwitcher pdoswitcherBind;
    private RichOutputText salesOrderBindVar;
    private String wfId = null;
    String addSrcTyp;
    private RichInputText itmIdBindVar;
    private RichSelectOneChoice pdoSrcTypeBindVar;
    private RichSelectOneChoice pdocreateDateBindVar;
    private RichInputText pdoOldDocIdBindVar;
    private RichInputListOfValues pdoRefDocBindVar;
    private List<UploadedFile> uploadedFile;
    private RichSelectManyChoice pdoSrcTypBindVar;
    private RichPopup popupForJobCardBindVar;
    private RichSelectOneChoice srcJCRCGeneBind;
    private RichInputListOfValues pdoOutputProductBind;
    private RichInputListOfValues pdoBomBind;
    private RichSelectOneChoice pdoBasisBind;
    private RichInputListOfValues pdoSrcSalesOrdrBind;
    private RichInputDate pdoSrcDlvryBind;
    private RichInputText pdosrcManualQtyBind;
    private RichInputText pdoParamValueBind;
    private RichInputListOfValues pdoJcRcWorkStnBind;
    private RichInputListOfValues pdoJcRcEmpBind;
    private RichInputListOfValues pdoJcRcShiftBind;
    private RichInputDate pdoJcRcStrtTimeBind;
    private RichInputDate pdoJcRcEndTimeBind;
    private RichInputListOfValues pdoJcRcReqAreaBind;
    private RichInputListOfValues pdoJcRcWhareHouseBind;
    private RichInputListOfValues pdoJcRcOpBind;
    private RichPanelTabbed dtlTabBindings;
    private RichInputText pdoOpSrNoBind;
    private RichInputText pdoOpItmBind;
    private RichInputText pdoOpItmQtyBind;
    private RichSelectOneChoice pdoOpLocBind;
    private RichSelectOneChoice pdoOpWorkCenterBind;
    private RichInputListOfValues pdoCostingParamBind;
    private RichTable pdoCostingTableBind;
    private RichButton pendingItmQtyBinding;
    private RichLink saveBind;
    private RichLink saveAndFwdBind;
    private RichPanelCollection overheadParameterTableBind;
    private RichTable pdoOverheadTableBind;
    private RichLink addQueueBind;
    private RichTable pdoSrcTableBind;
    private RichInputText transItmProducQtyForJCRCBind;
    private RichSelectBooleanCheckbox isValuePercBinding;
    private RichSelectOneChoice valueTypeBindVar;
    private RichInputText paramValueBindVar;
    private RichInputText minTolBindVar;
    private RichInputText maxTolBindVar;
    private RichInputText minValBindVar;
    private RichInputText maxValBindVar;
    private RichTable qcParamTable;
    private RichInputListOfValues qcParamDescBindVar;
    private RichInputListOfValues qualitychkParamDescBindVar;
    private RichSelectOneChoice qualitychkOpNmeBindVar;
    private RichLink addQcParamBind;
    


    public productionOrderBean() {

    }

    /**
     * Save Action Event
     *
     * */
    public void onSaveAction(ActionEvent actionEvent) {

      if((Integer)getAttrsVal("MnfPDOVO1Iterator", "PdoMode") == 43)
      {
          ADFBeanUtils.findOperation("updateShortCloseStatus").execute();
        //  setAttrsVal("MnfPDOVO1Iterator","PdoStat", new Integer(152));
          ADFBeanUtils.findOperation("Commit").execute();
         // setPdoMode("V");
          saveBind.setDisabled(true);
      
      }else{
        ob = ADFBeanUtils.findOperation("chkgetYearFyId");
        ob.execute();
        if (!(ob.getResult() == -1)) {
            System.out.println("in onSaveAction ");
            if (toCommit()) {
                if (ValueForQc.equals("Y")) {
                    if (getAttrsVal("MnfPdoQcParamVO1Iterator", "TransQcParam") == null) {
                        String msg = "Parameter in QC Parameter Tab is Required !";
                        //   JSFUtils.addFacesInformationMessage(msg);
                        JSFUtils.addFacesErrorMessage(msg);
                        ValueForQc = "Y";
                    } else {
                        // ADFUtils.findOperation("Commit").execute();
                        ADFBeanUtils.findOperation("Commit").execute();
                        // if(ccChk != null && ccChk.compareTo(new Number(0)) == 1 ){
                        ob = ADFBeanUtils.findOperation("sendDataFromTempCcToPdoOpItm");
                        ob.execute();
                        // }
                        String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                        JSFUtils.addFacesInformationMessage(msg);
                        //JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
                        setPdoMode("V");
                        ValueForQc = "N";
                    }
                } else {
                    // ADFUtils.findOperation("Commit").execute();

                    ADFBeanUtils.findOperation("Commit").execute();
                    //   if(ccChk != null && ccChk.compareTo(new Number(0)) == 1){
                    ob = ADFBeanUtils.findOperation("sendDataFromTempCcToPdoOpItm");
                    ob.execute();
                    //  }
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                    JSFUtils.addFacesInformationMessage(msg);
                    //JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
                    setPdoMode("V");
                }
            }
        } else {
            JSFUtils.addFacesErrorMessage("Financial Year is not Defined  !");
        }
      }

    }


    /**
     * Method to save and Forward
     *
     * */
    public String saveAndFwdAction() {
        ob = ADFBeanUtils.findOperation("chkgetYearFyId");
        ob.execute();
        if (!(ob.getResult() == -1)) {
            if (toCommit()) {

                if (ValueForQc.equals("Y")) {
                    if (getAttrsVal("MnfPdoQcParamVO1Iterator", "TransQcParam") == null) {
                        String msg = "Parameter in QC Parameter Tab is Required !";
                        //   JSFUtils.addFacesInformationMessage(msg);
                        JSFUtils.addFacesErrorMessage(msg);
                        ValueForQc = "Y";
                    } else {

                        callWfId();
                        //ob = ADFUtils.findOperation("Commit");
                        ob = ADFBeanUtils.findOperation("Commit");
                        ob.execute();
                        //   if(ccChk != null && ccChk.compareTo(new Number(0)) == 1){
                        ADFBeanUtils.findOperation("sendDataFromTempCcToPdoOpItm").execute();
                        //  ob.execute();
                        //  }
                       ADFBeanUtils.findOperation("updateSoDlvQty").execute();
                       
                        setPdoMode("V");
                        return "wfCall";
                    }

                } else {

                    callWfId();
                    //ob = ADFUtils.findOperation("Commit");
                    ob = ADFBeanUtils.findOperation("Commit");
                    ob.execute();
                    ADFBeanUtils.findOperation("sendDataFromTempCcToPdoOpItm").execute();
                    ADFBeanUtils.findOperation("updateSoDlvQty").execute();
                    setPdoMode("V");
                    return "wfCall";

                }
            } else {
                JSFUtils.addFacesErrorMessage("Error Occur while saving document  !");
            }
        } else
            JSFUtils.addFacesErrorMessage("Financial Year is not Defined  !");

        return "";
    }

    /**
     * Method to be called before save button and SF Button
     *
     * */
    public boolean toCommit() {
        boolean val = true;
        // System.out.println("-------------->>> tocommit");
        if (getSetFlag().equals("Y") ||
            getSetFlag() ==
                          "Y") {
            // System.out.println("-------------->>>");
            JSFUtils.addFacesErrorMessage("OutPut Quantity has been Changed, So Do Add Operation Again to reflect the changes in Rotuing Tab !");
            val = false;
            setOpVal("NOP");
        } else {

            callFuncWf();

            //operationActiveCheck
            System.out.println("in toCommit ");
            //ob = ADFUtils.findOperation("operationActiveCheck");
            ob = ADFBeanUtils.findOperation("operationActiveCheck");
            ob.execute();

            if (ob.getResult() != null && !(ob.getResult()).equals("0")) {

                JSFUtils.addFacesErrorMessage(ob.getResult().toString());

                return false;
            }


            // ob = ADFUtils.findOperation("chkOperationTargetDate");
            // ob.execute();
            /* if ((Integer) ob.getResult() == 0) {
            JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
            setPdoMode("V");
            val = true;
            return val;
        } */


        }
        return val;
    }


    /**
     * Method to resolve page flow scope parameter
     *
     * */
    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    /**
     * Cancel Action Event
     *
     * */
    public void onCancelAction(ActionEvent actionEvent) {
        // ADFUtils.findOperation("Rollback").execute();
        ADFBeanUtils.findOperation("Rollback").execute();
    }
    String opVal = "NOP";

    public void setOpVal(String opVal) {
        this.opVal = opVal;
    }

    public String getOpVal() {
        return opVal;
    }

    /**
     * Method to create Production Operation
     *
     * **/
    public void createPDOOperationAction(ActionEvent actionEvent) {

        // Number compVal = new Number(0);
        Number itmVal = (Number) getAttrsVal("MnfPDOVO1Iterator", "OutptItmQty");

        if (itmVal.compareTo(0) == 0) {

            // if (Integer.parseInt(getPdoOutputQtyBind().getValue().toString()) == 0) {
            /* JSFUtils.addFacesErrorMessage("Output Item Quantity should be greater than zero !");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Output Item Quantity should be greater than zero !", null)); */
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1746']}");
            JSFUtils.addFacesErrorMessage(msg);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } //else if ((getPdoOutputQtyBind().getValue() != null) && Integer.parseInt(getPdoOutputQtyBind().getValue().toString()) > 0) {
        else if (getAttrsVal("MnfPDOVO1Iterator", "OutptItmQty") != null && itmVal.compareTo(0) == 1) {
            try {

                // ob = ADFUtils.findOperation("createPDOOperation");
                ob = ADFBeanUtils.findOperation("createPDOOperation");
                ob.getParamsMap().put("Bomdocid", getPdoBOMIdBind().getValue().toString());
                //   ob.getParamsMap().put("PdoDocId", getPdoDocIdBind().getValue().toString());
                // ob.getParamsMap().put("PdoOutputQty", getPdoOutputQtyBind().getValue().toString());
                ob.getParamsMap().put("PdoOutputQty", itmVal);
                ob.getParamsMap().put("routeid", getPdoRouteIdBind().getValue().toString());
                ob.execute();
                setOpVal("OP");
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);
                System.out.println("OP val is " + getOpVal());
                setSetFlag("N");

            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            /* JSFUtils.addFacesErrorMessage("Output Item Quantity should be greater than zero !");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Output Item Quantity should be greater than zero !", null)); */

            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1746']}");
            // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            JSFUtils.addFacesErrorMessage(msg);
        }
    }


    /** Getting page flow scope parameter methods. */

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    public String getCldId() {
        return new String(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    }

    public String getOrgId() {
        return new String(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    }

    public String getHoOrgId() {
        return new String(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    }

    public Integer getUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    public void setPdoBOMIdBind(RichInputText pdoBOMIdBind) {
        this.pdoBOMIdBind = pdoBOMIdBind;
    }

    public RichInputText getPdoBOMIdBind() {
        return pdoBOMIdBind;
    }

    public void setPdoRouteIdBind(RichInputText pdoRouteIdBind) {
        this.pdoRouteIdBind = pdoRouteIdBind;
    }

    public RichInputText getPdoRouteIdBind() {
        return pdoRouteIdBind;
    }

    public void setPdoDocDateBind(RichInputDate pdoDocDateBind) {
        this.pdoDocDateBind = pdoDocDateBind;
    }

    public RichInputDate getPdoDocDateBind() {
        return pdoDocDateBind;
    }

    public void setPdoOutputQtyBind(RichInputText pdoOutputQtyBind) {
        this.pdoOutputQtyBind = pdoOutputQtyBind;
    }

    public RichInputText getPdoOutputQtyBind() {
        return pdoOutputQtyBind;
    }

    public void setPdoIdBind(RichInputText pdoIdBind) {
        this.pdoIdBind = pdoIdBind;
    }

    public RichInputText getPdoIdBind() {
        return pdoIdBind;
    }

    /**
     * To Add Src Parameter to PDO.
     *
     * **/
    public void selectOrderTypeAction(ActionEvent actionEvent) {

        //  ADFUtils.findOperation("CreateWithParamsToPDOSRC").execute();
        ADFBeanUtils.findOperation("CreateWithParamsToPDOSRC").execute();

        /* pdosrcManualQtyBind.setRequired(false);
        pdosrcManualQtyBind.setDisabled(true);
        pdoSrcSalesOrdrBind.setDisabled(false);
        this.pdoSrcSalesOrdrBind.setRequired(true);
        pdoSrcSalesOrdrBind.setRequiredMessageDetail("Select Sales Order !"); */


    }

    public void setAddSrcTyp(String addSrcTyp) {
        this.addSrcTyp = addSrcTyp;
    }

    public String getAddSrcTyp() {
        return addSrcTyp;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    /**
     * Method to get total output item qty
     *
     * */
    public void addOutpoutQtyAction(ActionEvent actionEvent) {

        //if( (Integer)getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 95 ) {

        // ADFUtils.findOperation("getOutPutQty").execute();
        ADFBeanUtils.findOperation("getOutPutQty").execute();
        pdoOutputQtyBind.setValue(ob.getResult());
    }

    /**
     * Method for switcher
     *
     * */
    public void pdoSearchSwitcherAction(ActionEvent actionEvent) {

        if (this.getPdoBasisBind().getValue().equals(15)) {
            /* ActionEvent acc = new ActionEvent(addQueueBind);
                    acc.queue();
                    addQueueACE(acc); */
            String oldDocPdoId = (String) getAttrsVal("MnfPDOVO1Iterator", "RefPdoId");
            System.out.println("OLD DOC ID IS : " + oldDocPdoId);
            ob = ADFBeanUtils.findOperation("getpreviousProductionOrderInf");
            ob.getParamsMap().put("oldDocPdoId", oldDocPdoId);
            ob.execute();
        }

        if (getItmIdBindVar().getValue() != null) {
            if (getPdoBOMIdBind().getValue() != null) {
                //ADFUtils.findOperation("createPdoSRC").execute();
                ADFBeanUtils.findOperation("createPdoSRC").execute();
                pdoswitcherBind.setFacetName("PDOTabData");
                pdoOutputProductBind.setDisabled(true);
                pdoBasisBind.setDisabled(true);
            } else {
                FacesMessage message = new FacesMessage("Bill of Material(BOM) is Required !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pdoBomBind.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Output Product is Required !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(pdoOutputProductBind.getClientId(), message);

        }


    }

    public void setPdoMode(String pdoMode) {
        this.pdoMode = pdoMode;
    }

    public String getPdoMode() {
        return pdoMode;
    }

    public void setSwitcheVal(String switcheVal) {
        this.switcheVal = switcheVal;
    }

    public String getSwitcheVal() {
        return switcheVal;
    }

    public void pdoSalesOrderAction(ActionEvent actionEvent) {
        setSwitcheVal("SearchO");
    }

    public void pdoSearchAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    /**
     *  Method to Search Sales Order,Sale Forecast
     *
     * */
    public void pdoSearchSalesOrderAction(ActionEvent actionEvent) {


        JUCtrlListBinding listBindings = (JUCtrlListBinding) ADFUtils.getBindingContainer().get("LOVSearchPdoBasisVO1");
        Object str[] = listBindings.getSelectedValues();

        /*  for (int i = 0; i < str.length; i++) {
            System.out.println("Src type Id is :" + str[i].toString());
            selectedVal = selectedVal+str[i].toString()+",";

        } */
        StringBuffer srcToken = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            srcToken.append(str[i].toString());
            if ((i) < (str.length - 1)) {
                srcToken.append(",");
            }
        }

        //ob = ADFUtils.findOperation("getSearchSalesOrder");
        ob = ADFBeanUtils.findOperation("getSearchSalesOrder");

        if (custIdBindVar.getValue() != null)
            ob.getParamsMap().put("custId", Integer.parseInt(custIdBindVar.getValue().toString()));

        if (salesOrderBindVar.getValue() != null)
            ob.getParamsMap().put("salesOrder", salesOrderBindVar.getValue().toString());

        if (srcToken.length() > 0)
            ob.getParamsMap().put("srcTyp", srcToken.toString());


        ob.execute();
        listBindings.clearSelectedIndices();
    }

    public void setCustIdBindVar(RichOutputText custIdBindVar) {
        this.custIdBindVar = custIdBindVar;
    }

    public RichOutputText getCustIdBindVar() {
        return custIdBindVar;
    }

    /**
     *  Method Create with param to PDOSRC
     *
     * */
    /*    public void pdoAddPDOSRCAction(ActionEvent actionEvent) {

        ob = ADFUtils.findOperation("createPdoSRC");
        ob.getParamsMap().put("SrcType", srcVal);
        ob.execute();

    } */

    public void setPdoswitcherBind(UIXSwitcher pdoswitcherBind) {
        this.pdoswitcherBind = pdoswitcherBind;
    }

    public UIXSwitcher getPdoswitcherBind() {
        return pdoswitcherBind;
    }

    public void setSalesOrderBindVar(RichOutputText salesOrderBindVar) {
        this.salesOrderBindVar = salesOrderBindVar;
    }

    public RichOutputText getSalesOrderBindVar() {
        return salesOrderBindVar;
    }

    /**
     * Edit Button Action Event
     *
     * */
    public void pdoEditAction(ActionEvent actionEvent) {
        Integer usr_Id = getUsrId();
        Integer pending = 0;
        // ob = ADFUtils.findOperation("getDocUsrFromWF");
        /*   ob =  ADFBeanUtils.findOperation("getCostCenterChk");
        ob.execute();
        setCcChk((oracle.jbo.domain.Number)ob.getResult()); */

        ob = ADFBeanUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();

        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {

            // DCIteratorBinding dcIter = ADFUtils.findIterator("MnfPdoOpVO2Iterator");
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator("MnfPdoOpVO2Iterator");
            setPdoMode("E");
            if (dcIter.getEstimatedRowCount() > 0)
                setOpVal("OP");


        } else if (pending.compareTo(new Integer(-1)) == 0) {
            String Msg22 = "Production Order has been Approved, You can not edit ProductionOrder";
            FacesMessage message2 = new FacesMessage(Msg22);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            // ob = ADFUtils.findOperation("getUserName");
            ob = ADFBeanUtils.findOperation("getUserNameforDoc");
            ob.getParamsMap().put("u_Id", pending);
            ob.getParamsMap().put("slc_id", getSlocId());
            ob.execute();
            if (ob.getResult() != null) {

                String msg2 =
                    "<html><body> <p>This Production Order is pending at other user<b><i> [ " +
                    ob.getResult().toString() + " ] </i></b>for approval, You can not edit this.</p></body></html>";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }
    }

    /**
     * Method to create with param to PDO
     *
     * */
    public void addAction(ActionEvent actionEvent) {
        setPdoMode("C");
        setOpVal("NOP");
        ADFContext.getCurrent().getPageFlowScope().put("PDO_MODE", "C");
        pdoOutputProductBind.setDisabled(false);
        pdoBasisBind.setDisabled(false);
        //ADFUtils.findOperation("setlovSupplyVOParam").execute();
        //ADFUtils.findOperation("CreateWithParamsToMnfPDO").execute();
        ADFBeanUtils.findOperation("setlovSupplyVOParam").execute();
        ADFBeanUtils.findOperation("CreateWithParamsToMnfPDO").execute();
        // System.out.println("OPVAL IS " + getOpVal());
        /* ob =  ADFBeanUtils.findOperation("getCostCenterChk");
       ob.execute();
       setCcChk((oracle.jbo.domain.Number)ob.getResult()); */
    }


    /**
     * Call Function callWfFunction
     * */
    public void callFuncWf() {

        // ob = ADFUtils.findOperation("callWfFunctions");
        ob = ADFBeanUtils.findOperation("callWfFunctions");
        ob.execute();
    }

    /**
     * Call callWfId Function
     *
     * */
    public void callWfId() {
        // ob = ADFUtils.findOperation("getWfId");
        ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();

        setWfId(wId);
    }
    // Integer srcVal = 0;

    /*  public void pdoSearchSrcBasis(ValueChangeEvent valueChangeEvent) {
        srcVal = Integer.parseInt(getPdoSearchSrcBasisBindVar().getValue().toString());
        if (srcVal == 94) {
             FacesMessage message = new FacesMessage("Do Procceed with Next Button to manually create Data !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
           // fc.addMessage(pdoSearchSrcBasisBindVar.getClientId(), message);
            pdoSearchSrcBasisBindVar.setValue(94);

        }
    }

      public void setPdoSearchSrcBasisBindVar(RichSelectOneChoice pdoSearchSrcBasisBindVar) {
        this.pdoSearchSrcBasisBindVar = pdoSearchSrcBasisBindVar;
    }

    public RichSelectOneChoice getPdoSearchSrcBasisBindVar() {
        return pdoSearchSrcBasisBindVar;
    } */

    public void setItmIdBindVar(RichInputText itmIdBindVar) {
        this.itmIdBindVar = itmIdBindVar;
    }

    public RichInputText getItmIdBindVar() {
        return itmIdBindVar;
    }

    public void setPdoSrcTypeBindVar(RichSelectOneChoice pdoSrcTypeBindVar) {
        this.pdoSrcTypeBindVar = pdoSrcTypeBindVar;
    }

    public RichSelectOneChoice getPdoSrcTypeBindVar() {
        return pdoSrcTypeBindVar;
    }

    /**
     * Method to call CreateWithParamToPDOParam
     *
     * */
    public void addPdoParamACE(ActionEvent actionEvent) {
        // ob = ADFUtils.findOperation("CreateWithParamsToPDOParam");
        // ob.execute();
        if (getAttrsVal("DualCostingParamVO1Iterator", "SetId") == null) {
            showFacesMsg("Parameter Set is Required!", "Please enter Parameter Set Name", FacesMessage.SEVERITY_ERROR,
                         null);
        } else if (getAttrsVal("DualCostingParamVO1Iterator", "ParameterId") == null) {
            showFacesMsg("Parameter is Required!", "Please enter Parameter Name", FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualCostingParamVO1Iterator", "opId") == null) {

            showFacesMsg("Operation is Required", "Please Select Operation Name", FacesMessage.SEVERITY_ERROR, null);

        } else {
            try {
                // ADFUtils.findOperation("attachCosting").execute();
                ADFBeanUtils.findOperation("attachCosting").execute();
            } catch (Exception e) {
                showFacesMsg("Duplicated Value!", "Duplicate Value Selected", FacesMessage.SEVERITY_ERROR, null);
                e.printStackTrace();
            }
        }


    }

    public void setPdocreateDateBindVar(RichSelectOneChoice pdocreateDateBindVar) {
        this.pdocreateDateBindVar = pdocreateDateBindVar;
    }

    public RichSelectOneChoice getPdocreateDateBindVar() {
        return pdocreateDateBindVar;
    }

    public void setPdoOldDocIdBindVar(RichInputText pdoOldDocIdBindVar) {
        this.pdoOldDocIdBindVar = pdoOldDocIdBindVar;
    }

    public RichInputText getPdoOldDocIdBindVar() {
        return pdoOldDocIdBindVar;
    }

    public void setPdoRefDocBindVar(RichInputListOfValues pdoRefDocBindVar) {
        this.pdoRefDocBindVar = pdoRefDocBindVar;
    }

    public RichInputListOfValues getPdoRefDocBindVar() {
        return pdoRefDocBindVar;
    }

    /**
     * Method to call Copy Previous Function
     *
     * */
    public void pdoRedDocIdVCE(ValueChangeEvent vce) {

        //  pdoRefDocBindVar.setDisabled(true);

        /*  ActionEvent acc = new ActionEvent(addQueueBind);
        acc.queue();
        addQueueACE(acc); */

        /*  String oldDocPdoId = vce.getNewValue().toString();
       // String oldDocPdoId = (String) getAttrsVal("MnfPDOVO1Iterator", "TransRefPdoId");
        //  ob = ADFUtils.findOperation("getpreviousProductionOrderInf");
        ob = ADFBeanUtils.findOperation("getpreviousProductionOrderInf");
        //ob.getParamsMap().put("newDocPdoId", newDocPdoId);
        ob.getParamsMap().put("oldDocPdoId", oldDocPdoId);
        ob.execute(); */


        //setOpVal("OP");


        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);
    }

    /**
     * Method to save uploaded files in file system and DB tables
     * **/

    public void pdoAttachFileACE(ActionEvent actionEvent) {
        OperationBinding op = null;
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        //System.out.println("Files size" + files.size());
        if (files == null)
            JSFUtils.addFacesErrorMessage("No Files have been Uploaded yet!");
        else if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                try {
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
                    //op = ADFUtils.findOperation("createAttchmntRow");
                    op = ADFBeanUtils.findOperation("createAttchmntRow");
                    op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    op.getParamsMap().put("extn", extn);
                    op.execute();
                    if (op.getResult() != null) {
                        path = op.getResult().toString();
                    }
                    InputStream in = files.get(i).getInputStream();
                    FileOutputStream out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;
                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    in.close();
                    out.close();
                    //ADFUtils.findOperation("Commit").execute();
                    ADFBeanUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        setUploadedFile(null);
    }


    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    /**
     *  Method to Delete an attached File.
     *
     * */
    public void attachmentDeleteACE(ActionEvent actionEvent) {
        String path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        // System.out.println("File Path : "+path);
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        //  System.out.println("Key Value : "+rowKey);

        if (path != null) {
            System.out.println("File Path : " + path.toString());
            // ob = ADFUtils.findOperation("deleteAttachFileRow");
            ob = ADFBeanUtils.findOperation("deleteAttachFileRow");
            ob.getParamsMap().put("path", path);
            ob.execute();
            // ADFUtils.findOperation("DeleteAttach").execute();
            // ADFUtils.findOperation("Commit").execute();
            ADFBeanUtils.findOperation("Commit").execute();
            //ADFUtils.findOperation("ExecuteAttach").execute();
        }


        /*         if (pathObj != null)
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        File f = new File(path);
        try {
            boolean success = f.delete();
        } catch (Exception x) {

            System.err.format("%s: no such" + " file or directory%n", path);
        }

        //ADFUtils.findOperation("DeleteAttach").execute();
        ADFUtils.findOperation("Commit").execute(); */

    }

    /**
     * Method to call Revision Number for PDO Document.
     *
     * */
    public void pdoRevisionDocACE(ActionEvent actionEvent) {

        ob = ADFBeanUtils.findOperation("pdoRevChk");
        ob.execute();

        if (ob.getResult().toString().equals("N")) {
            setPdoMode("R");
            setOpVal("NOP");
            // ob = ADFUtils.findOperation("revisedPdoDoc");
            ob = ADFBeanUtils.findOperation("revisedPdoDoc");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBindings);
        } else {
            String msg = "Document can not be Revise as it is already existing against Route Card/ Job Card  !";
            JSFUtils.addFacesErrorMessage(msg);

        }

    }

    public void setPdoSrcTypBindVar(RichSelectManyChoice pdoSrcTypBindVar) {
        this.pdoSrcTypBindVar = pdoSrcTypBindVar;
    }

    public RichSelectManyChoice getPdoSrcTypBindVar() {
        return pdoSrcTypBindVar;
    }

    /**
     * POP Up to create JC/RC
     *
     * */
    public void generateJobCardACE(ActionEvent actionEvent) {

        // ADFUtils.findOperation("setpdoId").execute();
        //  ADFUtils.findOperation("chkGenJcID").execute();
        //  ADFUtils.showPopup(popupForJobCardBindVar, true);

        ADFBeanUtils.findOperation("setpdoId").execute();
        ADFBeanUtils.findOperation("chkGenJcID").execute();
        ADFBeanUtils.showPopup(popupForJobCardBindVar, true);
        //ADFUtils.findIterator("LOVGenerateJCRCVO1Iterator").getViewObject().executeQuery();
    }

    public void setPopupForJobCardBindVar(RichPopup popupForJobCardBindVar) {
        this.popupForJobCardBindVar = popupForJobCardBindVar;
    }

    public RichPopup getPopupForJobCardBindVar() {
        return popupForJobCardBindVar;
    }

    /**
     * Dialog Box to create JC/RC
     *
     * */
    /*    public void generateJobCardDL(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            ob = ADFUtils.findOperation("ChkJCExists");
            ob.execute();
            if (ob.getResult().equals("Y")) {

                //check if user is trying to create Route card ?
                if (srcJCRCGeneBind.getValue().equals(106)) {

                    FacesMessage message =
                        new FacesMessage("Route card can not be generated as an Operation exists against a Job Card or Group Production Plan ! !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(srcJCRCGeneBind.getClientId(), message);
                } else {

                    if (this.getSrcJCRCGeneBind().getValue() != null) {

                        if ((getPdoJcRcReqAreaBind().getValue() != null) &&
                            (getPdoJcRcWhareHouseBind().getValue() != null)) {
                            ob = ADFUtils.findOperation("generateJobCard");
                            ob.execute();
                            System.out.println("ob.getResult() " + ob.getResult());

                            if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {

                                ADFUtils.findOperation("Commit").execute();
                                JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !" +
                                                                    ob.getResult());
                                System.out.println(" JC generated ");
                                ADFUtils.findOperation("resetValForJCRC").execute();
                            }

                        } else
                            JSFUtils.addFacesErrorMessage("All Fields are Mandatory !");
                    } else {
                        JSFUtils.addFacesErrorMessage("All Fields are Mandatory !");
                    }

                }
            } else if (this.getSrcJCRCGeneBind().getValue() != null) {
                System.out.println(" inside else if ");
                if ((getPdoJcRcEmpBind().getValue() != null) && (getPdoJcRcShiftBind().getValue() != null) &&
                    (getPdoJcRcStrtTimeBind().getValue() != null) && (getPdoJcRcEndTimeBind().getValue() != null) &&
                    (getPdoJcRcReqAreaBind().getValue() != null) && (getPdoJcRcWhareHouseBind().getValue() != null)) {
                    ob = ADFUtils.findOperation("generateJobCard");
                    ob.execute();
                    System.out.println("ob.getResult().toString() " + ob.getResult().toString());
                    if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {
                        ADFUtils.findOperation("Commit").execute();
                        JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
                        ADFUtils.findOperation("resetValForJCRC").execute();
                    }

                } else
                    JSFUtils.addFacesErrorMessage("All Fields are Mandatory !");
            }

        } else {

            System.out.println("no action");
            ADFUtils.findOperation("resetValForJCRC").execute();
            pdoJcRcWorkStnBind.setRequired(false);
            pdoJcRcOpBind.setRequired(false);
        }
    } */

    /**
     * To create MNF$PDO$SRC Manual
     *
     * */
    public void pdoSrcTypVCE(ValueChangeEvent valueChangeEvent) {
        Integer val = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        if (val == 94) {
            this.pdoSrcSalesOrdrBind.setRequired(false);
            this.pdoSrcSalesOrdrBind.setDisabled(true);
            this.pdoSrcSalesForBind.setRequired(false);
            this.pdoSrcSalesForBind.setDisabled(true);
            this.pdoSubContractNobind.setRequired(false);
            this.pdoSubContractNobind.setDisabled(true);
            //ob = ADFUtils.findOperation("createPdoSRCManual");
            ob = ADFBeanUtils.findOperation("createPdoSRCManual");
            ob.execute();
            pdosrcManualQtyBind.setRequired(true);
            pdosrcManualQtyBind.setDisabled(false);
            setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", val);
            /* setAttrsVal("MnfPdoSRCVO2Iterator", "ItmId", getAttrsVal("MnfPDOVO1Iterator", "OutptItmId"));
            setAttrsVal("MnfPdoSRCVO2Iterator", "TransItemName", getAttrsVal("MnfPDOVO1Iterator", "TransProduct"));
             */
            pdosrcManualQtyBind.setRequiredMessageDetail("Quantity For Source Documentation is Required !");
            //this.pdoSrcDlvryBind.setRequired(true);
            //pdoSrcDlvryBind.setRequiredMessageDetail("Delivery Date For Source Documentation is Required !");

        } else if (val == 95) {
           
            pdosrcManualQtyBind.setRequired(false);
          pdosrcManualQtyBind.setDisabled(true);
            this.pdoSrcSalesForBind.setRequired(false);
            this.pdoSrcSalesForBind.setDisabled(true);
            this.pdoSubContractNobind.setRequired(false);
            this.pdoSubContractNobind.setDisabled(true);

            this.pdoSrcSalesOrdrBind.setRequired(true);
            this.pdoSrcSalesOrdrBind.setDisabled(false);
           // pdosrcManualQtyBind.setRequired(true);
           // pdosrcManualQtyBind.setDisabled(false);
            setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", val);
            /* setAttrsVal("MnfPdoSRCVO2Iterator", "ItmId", getAttrsVal("MnfPDOVO1Iterator", "OutptItmId"));
            setAttrsVal("MnfPdoSRCVO2Iterator", "TransItemName", getAttrsVal("MnfPDOVO1Iterator", "TransProduct"));
             */
            pdoSrcSalesOrdrBind.setRequiredMessageDetail("Select Sales Order !");
            /*  if (duplicateValue("MnfPdoSRCVO2Iterator", "TransSO", object)) {
               FacesMessage message = new FacesMessage("Duplicate Sales Order is Selected !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } */
            // this.pdoSrcDlvryBind.setRequired(true);
            // pdoSrcDlvryBind.setRequiredMessageDetail("Delivery Date For Source Documentation is Required !");
        } else if ((val == 96)) {
           
            this.pdoSubContractNobind.setRequired(false);
            this.pdoSrcSalesOrdrBind.setRequired(false);
             pdosrcManualQtyBind.setRequired(false);
            this.pdoSrcSalesOrdrBind.setDisabled(true);
            this.pdoSubContractNobind.setDisabled(true);
              pdosrcManualQtyBind.setDisabled(true);
            this.pdoSrcSalesForBind.setRequired(true);
            this.pdoSrcSalesForBind.setDisabled(false);
            setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", val);
            /* setAttrsVal("MnfPdoSRCVO2Iterator", "ItmId", getAttrsVal("MnfPDOVO1Iterator", "OutptItmId"));
            setAttrsVal("MnfPdoSRCVO2Iterator", "TransItemName", getAttrsVal("MnfPDOVO1Iterator", "TransProduct"));
             */
            pdoSrcSalesForBind.setRequiredMessageDetail("Select Sales Forecast !");

        } else if ((val == 147)) {
            
            this.pdoSrcSalesOrdrBind.setRequired(false);
            pdosrcManualQtyBind.setRequired(false);
            this.pdoSrcSalesForBind.setRequired(false);
            this.pdoSrcSalesOrdrBind.setDisabled(true);
            pdosrcManualQtyBind.setDisabled(true);
            this.pdoSrcSalesForBind.setDisabled(true);
            this.pdoSubContractNobind.setDisabled(false);
            this.pdoSubContractNobind.setRequired(true);
            setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", val);

            pdoSubContractNobind.setRequiredMessageDetail("Select SubContracting Order !");


        }
    }

    /**
     * Check for Duplicate Manual Entry
     *
     * */
    public void pdoSrcTypVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.equals(94)) {
                this.pdoSubContractNobind.setRequired(false);
                this.pdoSubContractNobind.setDisabled(true);
                pdoSrcSalesOrdrBind.setRequired(false);
                pdoSrcSalesOrdrBind.setDisabled(true);
                pdoSrcSalesForBind.setRequired(false);
                pdoSrcSalesForBind.setDisabled(true);

                if (duplicateValue("MnfPdoSRCVO2Iterator", "SrcType", object)) {
                    /* JSFUtils.addFacesErrorMessage("Manual Entry for Source Documentation is already Exists !");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Can not Add Source Documentaion Manually !", null));*/
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1747']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                //setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", object);

                pdosrcManualQtyBind.setDisabled(false);
                pdosrcManualQtyBind.setRequired(true);

                // System.out.println("inside manual srctype !!");
                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);

            } else if (object.equals(95)) {
                // setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", object);
                this.pdoSubContractNobind.setRequired(false);
                this.pdoSubContractNobind.setDisabled(true);
                pdoSrcSalesOrdrBind.setDisabled(false);
                pdoSrcSalesOrdrBind.setRequired(true);

                pdosrcManualQtyBind.setRequired(false);
                pdosrcManualQtyBind.setDisabled(true);
                pdoSrcSalesForBind.setRequired(false);
                pdoSrcSalesForBind.setDisabled(true);
              
               // pdosrcManualQtyBind.setDisabled(false);
              //  pdosrcManualQtyBind.setRequired(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);

            } else if (object.equals(96)) {
                //setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", object);
                this.pdoSubContractNobind.setRequired(false);
                this.pdoSubContractNobind.setDisabled(true);
                pdoSrcSalesOrdrBind.setRequired(false);
                pdoSrcSalesOrdrBind.setDisabled(true);

                pdosrcManualQtyBind.setRequired(false);
                pdosrcManualQtyBind.setDisabled(true);

                pdoSrcSalesForBind.setDisabled(false);
                pdoSrcSalesForBind.setRequired(true);

                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
            } else if (object.equals(147)) {
                //setAttrsVal("MnfPdoSRCVO2Iterator", "SrcType", object);
                System.out.println("At line no 1206 !!");
                pdoSrcSalesOrdrBind.setRequired(false);
                pdoSrcSalesOrdrBind.setDisabled(true);

                pdosrcManualQtyBind.setRequired(false);
                pdosrcManualQtyBind.setDisabled(true);

                pdoSrcSalesForBind.setRequired(false);
                pdoSrcSalesForBind.setDisabled(true);

                this.pdoSubContractNobind.setDisabled(false);
                this.pdoSubContractNobind.setRequired(true);

                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
            }
        }
        //  System.out.println(getAttrsVal("MnfPdoSRCVO2Iterator", "ItmId"));
        // if(getAttrsVal("MnfPdoSRCVO2Iterator", "ItmId") ==  null){
        //setAttrsVal("MnfPdoSRCVO2Iterator", "ItmId", getAttrsVal("MnfPDOVO1Iterator", "OutptItmId"));
        // setAttrsVal("MnfPdoSRCVO2Iterator", "TransItemName", getAttrsVal("MnfPDOVO1Iterator", "TransProduct"));
        //  }
    }

    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        //DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute(attrsNm));

            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;
    }


    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
    private boolean duplicateValue12(String iter, String attrsNm, Object val, String attrsNm2, Object val2) {

        //DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute(attrsNm));

            if ((r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) &&
                (r.getAttribute(attrsNm2) != null && val2.equals(r.getAttribute(attrsNm2)))) {
                countVal = countVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();

        if ((currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) &&
            (currentRow.getAttribute(attrsNm2) != null && val2.equals(currentRow.getAttribute(attrsNm2)))) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;
    }

    public void setSetFlag(String setFlag) {
        this.setFlag = setFlag;
    }

    public String getSetFlag() {
        return setFlag;
    }

    /**
     *Disclosure event of tab to update OutputItem  Qty
     *
     * */
    String setFlag = "N";

    public void pdoSrcDocumentationDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
        } else {
            Number pp = (Number) getAttrsVal("MnfPDOVO1Iterator", "OutptItmQty");
            //ob = ADFUtils.findOperation("getOutPutQty");
            ob = ADFBeanUtils.findOperation("getOutPutQty");
            ob.execute();
            System.out.println("Pre Qty is : " + pp + " New " + ob.getResult());
            pdoOutputQtyBind.setValue(ob.getResult());
            if (pp.compareTo(ob.getResult()) == 0) {
                if (getPdoMode().equals("V")) {
                    setOpVal("NOP");
                    setSetFlag("N");
                } else {
                    setOpVal("OP");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveAndFwdBind);
                    System.out.println("OP val is " + getOpVal());
                    setSetFlag("N");
                }
            } else if (pp.compareTo(ob.getResult()) != 0) {
                setSetFlag("Y");
                setOpVal("NOP");
            }
        }
    }


    /**
     * Document Date validator
     * Either current date or future date
     *
     * */
    public void DocumentDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                Timestamp t1 = new Timestamp(System.currentTimeMillis());
                Timestamp t2 = (Timestamp) object;
                java.sql.Date d1 = t1.dateValue();
                java.sql.Date d2 = t2.dateValue();
                if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Document Date Should be greater than Current Date.",
                                                                  null)); */
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1748']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSrcJCRCGeneBind(RichSelectOneChoice srcJCRCGeneBind) {
        this.srcJCRCGeneBind = srcJCRCGeneBind;
    }

    public RichSelectOneChoice getSrcJCRCGeneBind() {
        return srcJCRCGeneBind;
    }

    /**
     * VCE for PDO Basis as Manual or Copy Previous
     *
     * */
    public void pdoBasisVCL(ValueChangeEvent valueChangeEvent) {
        if (this.getPdoBasisBind().getValue().equals(15)) {
        } else if (this.getPdoBasisBind().getValue().equals(16)) {
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOutputProductBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoBomBind);
    }

    public void setPdoOutputProductBind(RichInputListOfValues pdoOutputProductBind) {
        this.pdoOutputProductBind = pdoOutputProductBind;
    }

    public RichInputListOfValues getPdoOutputProductBind() {
        return pdoOutputProductBind;
    }

    public void setPdoBomBind(RichInputListOfValues pdoBomBind) {
        this.pdoBomBind = pdoBomBind;
    }

    public RichInputListOfValues getPdoBomBind() {
        return pdoBomBind;
    }

    public void setPdoBasisBind(RichSelectOneChoice pdoBasisBind) {
        this.pdoBasisBind = pdoBasisBind;
    }

    public RichSelectOneChoice getPdoBasisBind() {
        return pdoBasisBind;
    }

    public void setPdoSrcSalesOrdrBind(RichInputListOfValues pdoSrcSalesOrdrBind) {
        this.pdoSrcSalesOrdrBind = pdoSrcSalesOrdrBind;
    }

    public RichInputListOfValues getPdoSrcSalesOrdrBind() {
        return pdoSrcSalesOrdrBind;
    }

    public void setPdoSrcDlvryBind(RichInputDate pdoSrcDlvryBind) {
        this.pdoSrcDlvryBind = pdoSrcDlvryBind;
    }

    public RichInputDate getPdoSrcDlvryBind() {
        return pdoSrcDlvryBind;
    }

    public void setPdosrcManualQtyBind(RichInputText pdosrcManualQtyBind) {
        this.pdosrcManualQtyBind = pdosrcManualQtyBind;
    }

    public RichInputText getPdosrcManualQtyBind() {
        return pdosrcManualQtyBind;
    }

    /**
     * Validator for PDO Parameter
     *
     * */
    public void pdoParamValueVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                /*  FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message); */
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1605']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            } else if (value.getValue() < 0) {
                /* FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message); */
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1604']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }


    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }

    public void setPdoParamValueBind(RichInputText pdoParamValueBind) {
        this.pdoParamValueBind = pdoParamValueBind;
    }

    public RichInputText getPdoParamValueBind() {
        return pdoParamValueBind;
    }

    /**
     * Validator to check duplicate parameter
     *
     * */
    public void pdoParamChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // ob = ADFUtils.findOperation("chkDuplicate");
            ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "PDO_PARAM");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                if (duplicateValue("MnfPdoParamVO1Iterator", "ParamId", ob.getResult())) {
                    /* FacesMessage message = new FacesMessage("Duplicate Parameter is Selected !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message); */



                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1400']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }
            }
        }

    }

    public void setPdoJcRcWorkStnBind(RichInputListOfValues pdoJcRcWorkStnBind) {
        this.pdoJcRcWorkStnBind = pdoJcRcWorkStnBind;
    }

    public RichInputListOfValues getPdoJcRcWorkStnBind() {
        return pdoJcRcWorkStnBind;
    }

    public void setPdoJcRcEmpBind(RichInputListOfValues pdoJcRcEmpBind) {
        this.pdoJcRcEmpBind = pdoJcRcEmpBind;
    }

    public RichInputListOfValues getPdoJcRcEmpBind() {
        return pdoJcRcEmpBind;
    }

    public void setPdoJcRcShiftBind(RichInputListOfValues pdoJcRcShiftBind) {
        this.pdoJcRcShiftBind = pdoJcRcShiftBind;
    }

    public RichInputListOfValues getPdoJcRcShiftBind() {
        return pdoJcRcShiftBind;
    }

    public void setPdoJcRcStrtTimeBind(RichInputDate pdoJcRcStrtTimeBind) {
        this.pdoJcRcStrtTimeBind = pdoJcRcStrtTimeBind;
    }

    public RichInputDate getPdoJcRcStrtTimeBind() {
        return pdoJcRcStrtTimeBind;
    }

    public void setPdoJcRcEndTimeBind(RichInputDate pdoJcRcEndTimeBind) {
        this.pdoJcRcEndTimeBind = pdoJcRcEndTimeBind;
    }

    public RichInputDate getPdoJcRcEndTimeBind() {
        return pdoJcRcEndTimeBind;
    }

    public void setPdoJcRcReqAreaBind(RichInputListOfValues pdoJcRcReqAreaBind) {
        this.pdoJcRcReqAreaBind = pdoJcRcReqAreaBind;
    }

    public RichInputListOfValues getPdoJcRcReqAreaBind() {
        return pdoJcRcReqAreaBind;
    }

    public void setPdoJcRcWhareHouseBind(RichInputListOfValues pdoJcRcWhareHouseBind) {
        this.pdoJcRcWhareHouseBind = pdoJcRcWhareHouseBind;
    }

    public RichInputListOfValues getPdoJcRcWhareHouseBind() {
        return pdoJcRcWhareHouseBind;
    }

    /**
     * Value Change Event to generate JC/RC
     *
     * */
    public void pdoJcRcGenVCE(ValueChangeEvent valueChangeEvent) {

        //Job Card 105, Route Card 106
        if (srcJCRCGeneBind.getValue().equals(105)) {

            pdoJcRcOpBind.setVisible(true);
            pdoJcRcEmpBind.setVisible(true);
            pdoOpWorkCenterBind.setVisible(true);
            pdoJcRcWorkStnBind.setVisible(true);
            pdoOpLocBind.setVisible(true);
            pdoOpSrNoBind.setVisible(true);
            pdoOpWorkCenterBind.setVisible(true);
            pdoJcRcShiftBind.setVisible(true);

            pdoJcRcOpBind.setRequired(true);
            //pdoJcRcWorkStnBind.setRequired(true);
            pdoJcRcWorkStnBind.setDisabled(false);
            pdoJcRcOpBind.setDisabled(false);
            pdoJcRcShiftBind.setDisabled(false);
            pdoJcRcEmpBind.setDisabled(false);

            pdoJcRcShiftBind.setShowRequired(true);
            pdoJcRcEmpBind.setShowRequired(true);
            pdoJcRcStrtTimeBind.setShowRequired(true);
            pdoJcRcEndTimeBind.setShowRequired(true);
            pdoJcRcWorkStnBind.setShowRequired(true);

            pdoJcRcOpBind.setValue("");
            //pdoOpLocBind.setValue("");
            // pdoOpSrNoBind.setValue("");
            pdoOpItmBind.setValue("");
            pdoOpItmQtyBind.setValue("");
            // transItmProducQtyForJCRCBind.setValue("0");
            // setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItmProduceQty", new Number(0));
            ob = ADFBeanUtils.findOperation("getPendingItmForJC");
            ob.getParamsMap().put("jc_rc", 105);
            ob.execute();
            System.out.println(ob.getResult());
            setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItmProduceQty", ob.getResult());
            setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransPendingItmQty", ob.getResult());
         //   transItmProducQtyForJCRCBind.setRequired(true);
           // transItmProducQtyForJCRCBind.setShowRequired(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpLocBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);

        } else if (srcJCRCGeneBind.getValue().equals(106)) {
            // ob = ADFUtils.findOperation("ChkJCExists");
            ob = ADFBeanUtils.findOperation("ChkJCExists");
            ob.execute();
            if (ob.getResult().equals("Y")) {
                FacesMessage message =
                    new FacesMessage("Route card can not be generated as an operation exists against a Job Card  or Group Production Plan !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(srcJCRCGeneBind.getClientId(), message);
            } else {
                pdoJcRcOpBind.setVisible(false);
                pdoJcRcEmpBind.setVisible(false);
                pdoOpWorkCenterBind.setVisible(false);
                pdoJcRcWorkStnBind.setVisible(false);
                pdoOpLocBind.setVisible(false);
                pdoOpSrNoBind.setVisible(false);
                pdoOpWorkCenterBind.setVisible(false);
                pdoJcRcShiftBind.setVisible(false);
                // setAttrsVal("LOVGenerateJCRCVO1Iterator", "Operation","");

                pdoJcRcShiftBind.setShowRequired(false);
                pdoJcRcShiftBind.setDisabled(true);
                pdoJcRcShiftBind.setValue("");

                pdoJcRcEmpBind.setShowRequired(false);
                pdoJcRcEmpBind.setDisabled(true);
                pdoJcRcEmpBind.setValue("");

                pdoJcRcStrtTimeBind.setShowRequired(true);
                pdoJcRcEndTimeBind.setShowRequired(true);
                pdoJcRcWorkStnBind.setShowRequired(false);
                pdoJcRcWorkStnBind.setDisabled(true);
                pdoJcRcWorkStnBind.setValue("");
                pdoJcRcWorkStnBind.setValue("");
                // pdoOpLocBind.setValue("");
                // pdoOpSrNoBind.setValue("");
                pdoOpWorkCenterBind.setValue("");
                // transItmProducQtyForJCRCBind.setValue("0");
                pdoJcRcOpBind.setDisabled(true);
                // pdoJcRcOpBind.resetValue();
                // pdoJcRcOpBind.setValue("");
                //pdoJcRcWorkStnBind.setRequired(false);
                pdoJcRcOpBind.setRequired(false);

                setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItmProduceQty", "");
                pdoOpItmBind.setValue(getAttrsVal("MnfPDOVO1Iterator", "OutptItmId"));
                // pdoOpItmBind.setValue(getAttrsVal("MnfPDOVO1Iterator", "TransProduct"));
                pdoOpItmQtyBind.setValue(getAttrsVal("MnfPDOVO1Iterator", "OutptItmQty"));
                setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransLocTyp", "");
                setAttrsVal("LOVGenerateJCRCVO1Iterator", "OpnSrNo", "");
                // ob = ADFUtils.findOperation("getPendingItmForJC");
                ob = ADFBeanUtils.findOperation("getPendingItmForJC");
                ob.getParamsMap().put("jc_rc", 106);
                ob.execute();
                System.out.println(ob.getResult());
                setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItmProduceQty", ob.getResult());
                setAttrsVal("LOVGenerateJCRCVO1Iterator", "TransPendingItmQty", ob.getResult());
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);
            }
            
            
        }
    }

    public void setPdoJcRcOpBind(RichInputListOfValues pdoJcRcOpBind) {
        this.pdoJcRcOpBind = pdoJcRcOpBind;
    }

    public RichInputListOfValues getPdoJcRcOpBind() {
        return pdoJcRcOpBind;
    }

    public void setDtlTabBindings(RichPanelTabbed dtlTabBindings) {
        this.dtlTabBindings = dtlTabBindings;
    }

    public RichPanelTabbed getDtlTabBindings() {
        return dtlTabBindings;
    }

    public void setJcAllow(String jcAllow) {
        this.jcAllow = jcAllow;
    }

    public String getJcAllow() {
        return jcAllow;
    }

    /**
     * Value Change Listener for JCRCOperation
     *
     * */
    public void pdoJcRcOperationVCE(ValueChangeEvent vce) {
       
        
        if (vce.getNewValue() != null) {
            ActionEvent ace = new ActionEvent(pendingItmQtyBinding);
            ace.queue();
           System.out.println("Inside VCL---------");
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpSrNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpLocBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpWorkCenterBind);
        }
        transItmProducQtyForJCRCBind.setRequired(true);
        
    }

    Integer jc_rc_val = 0;

    public void setJc_rc_val(Integer jc_rc_val) {
        this.jc_rc_val = jc_rc_val;
    }

    public Integer getJc_rc_val() {
        return jc_rc_val;
    }

    /**
     * Method to get the pending qty for jc/rc to generate JC/RC for
     * remaining qty.
     *
     * */
    public void getPendingItmQty(ActionEvent ace) {
        // ob = ADFUtils.findOperation("getPendingItmForJC");
        ob = ADFBeanUtils.findOperation("getPendingItmForJC");
        ob.getParamsMap().put("jc_rc", 105);
        // ob.getParamsMap().put("jc_rc",getJc_rc_val());
        ob.execute();
    }

    public void setPdoOpSrNoBind(RichInputText pdoOpSrNoBind) {
        this.pdoOpSrNoBind = pdoOpSrNoBind;
    }

    public RichInputText getPdoOpSrNoBind() {
        return pdoOpSrNoBind;
    }

    public void setPdoOpItmBind(RichInputText pdoOpItmBind) {
        this.pdoOpItmBind = pdoOpItmBind;
    }

    public RichInputText getPdoOpItmBind() {
        return pdoOpItmBind;
    }

    public void setPdoOpItmQtyBind(RichInputText pdoOpItmQtyBind) {
        this.pdoOpItmQtyBind = pdoOpItmQtyBind;
    }

    public RichInputText getPdoOpItmQtyBind() {
        return pdoOpItmQtyBind;
    }

    public void setPdoOpLocBind(RichSelectOneChoice pdoOpLocBind) {
        this.pdoOpLocBind = pdoOpLocBind;
    }

    public RichSelectOneChoice getPdoOpLocBind() {
        return pdoOpLocBind;
    }

    public void setPdoOpWorkCenterBind(RichSelectOneChoice pdoOpWorkCenterBind) {
        this.pdoOpWorkCenterBind = pdoOpWorkCenterBind;
    }

    public RichSelectOneChoice getPdoOpWorkCenterBind() {
        return pdoOpWorkCenterBind;
    }

    public void setPdoCostingParamBind(RichInputListOfValues pdoCostingParamBind) {
        this.pdoCostingParamBind = pdoCostingParamBind;
    }

    public RichInputListOfValues getPdoCostingParamBind() {
        return pdoCostingParamBind;
    }

    public void pdoCostingParamVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoCostingTableBind);
    }

    public void setPdoCostingTableBind(RichTable pdoCostingTableBind) {
        this.pdoCostingTableBind = pdoCostingTableBind;
    }

    public RichTable getPdoCostingTableBind() {
        return pdoCostingTableBind;
    }

    /**
     * Method to delete Source Document
     *
     * */
    public void pdoDeleteSrcACE(ActionEvent actionEvent) {
        // #{row.TransKey}

        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteSourceKey");
        System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfPdoSRCVO2Iterator");

        pdoSubContractNobind.setRequired(false);
        pdoSrcSalesOrdrBind.setRequired(false);
        pdoSrcSalesForBind.setRequired(false);
        pdosrcManualQtyBind.setRequired(false);
        pdosrcManualQtyBind.setDisabled(true);
        pdoSrcSalesOrdrBind.setDisabled(true);
        pdoSrcSalesForBind.setDisabled(true);
        pdoSubContractNobind.setDisabled(true);
        /* if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") != null) {

            if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 95) {
                pdosrcManualQtyBind.setRequired(false);
                pdosrcManualQtyBind.setDisabled(true);

            } else if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 94) {
                pdoSrcSalesOrdrBind.setDisabled(true);
                pdoSrcSalesOrdrBind.setRequired(false);

            } else if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 96) {
                pdoSrcSalesOrdrBind.setDisabled(false);
                pdoSrcSalesOrdrBind.setRequired(false);
            }
        }  */
    }

    /**
     * Method to delete selected row
     *
     * */
    public void rowDelete(Key key, String iterName) {
        if (iterName != null && key != null) {
            // ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            ViewObject vo = ADFBeanUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }

    }

    /**
     * Method to delete Costing Parameter
     *
     * */
    public void pdoDeleteCostingParamACE(ActionEvent actionEvent) {
        // DeleteCostingParamKey
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteCostingParamKey");
        System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfPdoParamVO1Iterator");
    }

    public void outputItmVCE(ValueChangeEvent valueChangeEvent) {
        //  ADFUtils.findOperation("setlovSupplyVOParam").execute();
        ADFBeanUtils.findOperation("setlovSupplyVOParam").execute();
    }

    public void setPendingItmQtyBinding(RichButton pendingItmQtyBinding) {
        this.pendingItmQtyBinding = pendingItmQtyBinding;
    }

    public RichButton getPendingItmQtyBinding() {
        return pendingItmQtyBinding;
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            //DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    /**
     * Validator for Qty to Produce in JC/RC Dialog.
     *
     * */
    public void qtyToProduceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Number itmQty = (Number) object;
            if (!(itmQty.compareTo(0) == 1)) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Item Quantity should be greater than zero. ", null));*/
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.742']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            if (getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransPendingItmQty") != null &&
                getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItm") != null) {
                Number consumdItmQty = (Number) getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransPendingItmQty");
                Number itmPrdQty = (Number) getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItmQty");

                System.out.println("Pending " + consumdItmQty + "   ItmQty " + itmQty);

                //  if (itmQty.compareTo(itmPrdQty.subtract(consumdItmQty)) == 1) {
                if (consumdItmQty.compareTo(itmQty) == -1) {
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Item Quantity cannot be greater than Remaining Quantity. ",
                                                                  null)); */
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1749']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            
        if(getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItm") != null){
            String Sritm = (String)getAttrsVal("LOVGenerateJCRCVO1Iterator", "TransItm");
            ob=ADFBeanUtils.findOperation("ItemSerializedFlag");
            ob.getParamsMap().put("outItmId", Sritm);
            ob.execute();
        if(ob.getResult()!= null && (ob.getResult().toString().equals("Y") || ob.getResult().toString() =="Y")){
            
            Boolean isValid1 = isPrecisionValid(20, 0, itmQty);
            if (isValid1 == false) {
                FacesMessage message = new FacesMessage("Item is Serialized, So Qty can not be in decimal !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }   
        }
        }
           
    }
    }

    /**
     * Dialog Box to create JC/RC
     *
     * */

    public void generateJobCardDL(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            //check if Job card or Route card selected
            //105 code is job and 106 is for route card
            if (srcJCRCGeneBind.getValue().equals(106)) {

                // ob = ADFUtils.findOperation("ChkJCExists");
                ob = ADFBeanUtils.findOperation("ChkJCExists");
                ob.execute();

                //check if Job Card Exists for this Production Order continue if function returns N.
                if (ob.getResult().equals("Y")) {

                    JSFUtils.addFacesInformationMessage("Route card can not be generated as an Operation exists against a Job Card or Group Production Plan ! !");
                    //String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                    //JSFUtils.addFacesInformationMessage(msg);

                } else if (ob.getResult().equals("N")) {

                    if (getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime") != null &&
                        getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime") != null) {
                        try {
                            oracle.jbo.domain.Timestamp starttime =
                                (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime");
                            oracle.jbo.domain.Timestamp endtime =
                                (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime");
                            java.sql.Date d1 = starttime.dateValue();
                            java.sql.Date d2 = endtime.dateValue();
                            if (endtime.compareTo(starttime) < 0 && !d1.toString().equals(d2.toString())) {
                                JSFUtils.addFacesErrorMessage("Start Date Should be greater than End Date.");
                            } /* else if ((getPdoJcRcReqAreaBind().getValue() != null) &&
                                       (getPdoJcRcWhareHouseBind().getValue() != null)) { */
                            else if ((getAttrsVal("LOVGenerateJCRCVO1Iterator", "ReqAreaNme") != null) &&
                                     (getAttrsVal("LOVGenerateJCRCVO1Iterator", "WHouseNme") != null)) {
                                // ob = ADFUtils.findOperation("generateJobCard");
                                setJc_rc_val(106);
                                ob = ADFBeanUtils.findOperation("generateJobCard");
                                ob.execute();
                                System.out.println("ob.getResult().toString() " + ob.getResult().toString());
                                //Show confirmation message and comit data if operation binding return 1.
                                if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {
                                    // ADFUtils.findOperation("Commit").execute();
                                    ADFBeanUtils.findOperation("Commit").execute();
                                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                                    JSFUtils.addFacesInformationMessage(msg);
                                    // JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
                                    //ADFUtils.findOperation("resetValForJCRC").execute();
                                    ADFBeanUtils.findOperation("resetValForJCRC").execute();
                                } else {
                                    JSFUtils.addFacesErrorMessage("Error in saving records. ");
                                }
                            } else {
                                JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {

                        JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");

                    }
                }

            } else if (srcJCRCGeneBind.getValue().equals(105)) {

                if ((getPdoJcRcEmpBind().getValue() != null) && (getPdoJcRcShiftBind().getValue() != null) &&
                    (getPdoJcRcStrtTimeBind().getValue() != null) && (getPdoJcRcEndTimeBind().getValue() != null) &&
                    (getPdoJcRcReqAreaBind().getValue() != null) && (getPdoJcRcWhareHouseBind().getValue() != null)) {

                    if (getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime") != null &&
                        getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime") != null) {
                        try {
                            oracle.jbo.domain.Timestamp starttime =
                                (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime");
                            oracle.jbo.domain.Timestamp endtime =
                                (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime");
                            java.sql.Date d1 = starttime.dateValue();
                            java.sql.Date d2 = endtime.dateValue();
                            if (endtime.compareTo(starttime) < 0 && !d1.toString().equals(d2.toString())) {
                                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Start Date Should be greater than End Date.",null));

                                JSFUtils.addFacesErrorMessage("Start Date Should be greater than End Date.");

                            } else {
                                //  ob = ADFUtils.findOperation("generateJobCard");
                                setJc_rc_val(105);
                                ob = ADFBeanUtils.findOperation("generateJobCard");
                                ob.execute();
                                System.out.println("ob.getResult().toString() " + ob.getResult().toString());
                                //Show confirmation message and comit data if operation binding return 1.
                                if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {
                                    //  ADFUtils.findOperation("Commit").execute();
                                    ADFBeanUtils.findOperation("Commit").execute();
                                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                                    JSFUtils.addFacesInformationMessage(msg);
                                    //  ADFUtils.findOperation("resetValForJCRC").execute();
                                    ADFBeanUtils.findOperation("resetValForJCRC").execute();
                                } else {
                                    JSFUtils.addFacesErrorMessage("Error in saving records. ");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {

                    JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");

                }
            }
        } else {
            System.out.println("isideofelseofdialogeventfucntionactionevent");
            // ADFUtils.findOperation("resetValForJCRC").execute();
            ADFBeanUtils.findOperation("resetValForJCRC").execute();
            System.out.println("calledrestfunction");

        }
    }


    public void setSaveBind(RichLink saveBind) {
        this.saveBind = saveBind;
    }

    public RichLink getSaveBind() {
        return saveBind;
    }

    public void setSaveAndFwdBind(RichLink saveAndFwdBind) {
        this.saveAndFwdBind = saveAndFwdBind;
    }

    public RichLink getSaveAndFwdBind() {
        return saveAndFwdBind;
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     *
     * **/
    public void pdoPriorityVCE(ValueChangeEvent vce) {
        Integer Po = (Integer) vce.getNewValue();
        setAttrsVal("MnfPdoSRCVO2Iterator", "SoPriority", Po);
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            //  DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * To Add Overhead Parameter.
     *
     * **/
    public void addOverheadParamACE(ActionEvent actionEvent) {
        //CreateWithParamsToOverheadParam
        // ADFUtils.findOperation("CreateWithParamsToOverheadParam").execute();

        if (getAttrsVal("DualOverHeadParamVO1Iterator", "SetId") == null) {
            showFacesMsg("Parameter Set is Required!", "Please enter Parameter Set Name", FacesMessage.SEVERITY_ERROR,
                         null);
        } else if (getAttrsVal("DualOverHeadParamVO1Iterator", "ParameterId") == null) {
            showFacesMsg("Parameter is Required!", "Please enter Parameter Name", FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualOverHeadParamVO1Iterator", "opId") == null) {

            showFacesMsg("Operation is Required", "Please Select Operation Name", FacesMessage.SEVERITY_ERROR, null);

        } else {
            try {
                // ADFUtils.findOperation("attachOverhead").execute();
                ADFBeanUtils.findOperation("attachOverhead").execute();
            } catch (Exception e) {
                showFacesMsg("Duplicated Value!", "Duplicate Value Selected", FacesMessage.SEVERITY_ERROR, null);
                e.printStackTrace();
            }
        }


    }

    public void setOverheadParameterTableBind(RichPanelCollection overheadParameterTableBind) {
        this.overheadParameterTableBind = overheadParameterTableBind;
    }

    public RichPanelCollection getOverheadParameterTableBind() {
        return overheadParameterTableBind;
    }

    public void overheadParamSetVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(overheadParameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOverheadTableBind);
    }

    public void overheadParameterVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(overheadParameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOverheadTableBind);
    }

    /**
     *  Overhead Parameter Name Check
     *
     * **/
    public void overheadParameterVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ob.getResult() != null) {
                if (duplicateValue("MnfPdoOverheadParamVO1Iterator", "TransParam", ob.getResult())) {
                    /* FacesMessage message = new FacesMessage("Duplicate Parameter is Selected !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message); */
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1400']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    /**
     *  Delete Overhead Parameter Value
     *
     * **/
    public void deleteOverheadParamACE(ActionEvent actionEvent) {
        // DeleteOverheadParameter
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteOverheadParameter");
        System.out.println("Key is : " + key);
        rowDelete(key, "MnfPdoOverheadParamVO1Iterator");
    }

    /**
     *  Overhead Parameter Value Validator
     *
     * **/
    public void overheadParamValueVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(5, 2, value);
            if (isValid == false) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1605']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            } else if (value.getValue() < 0) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1604']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if (value.getValue() > 100) {
                //String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1604']}");
                String msg = "Overhead Value can not greater than 100 !";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void costingOpNmeVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoCostingTableBind);
    }

    public void setPdoOverheadTableBind(RichTable pdoOverheadTableBind) {
        this.pdoOverheadTableBind = pdoOverheadTableBind;
    }

    public RichTable getPdoOverheadTableBind() {
        return pdoOverheadTableBind;
    }

    public void overheadOpNmeVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(overheadParameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOverheadTableBind);
    }

    /**
     * Method to call Previous Production Order Information.
     *
     * **/
    public void addQueueACE(ActionEvent actionEvent) {
        // String oldDocPdoId = valueChangeEvent.getNewValue().toString();
        // String oldDocPdoId = (String) getAttrsVal("MnfPDOVO1Iterator", "TransRefPdoId");
        String oldDocPdoId = (String) getAttrsVal("MnfPDOVO1Iterator", "RefPdoId");
        System.out.println("OLD DOC ID IS : " + oldDocPdoId);
        //  ob = ADFUtils.findOperation("getpreviousProductionOrderInf");
        ob = ADFBeanUtils.findOperation("getpreviousProductionOrderInf");
        // ob.getParamsMap().put("newDocPdoId", newDocPdoId);
        ob.getParamsMap().put("oldDocPdoId", oldDocPdoId);
        ob.execute();
        // pdoRefDocBindVar.setDisabled(true);
    }

    public void setAddQueueBind(RichLink addQueueBind) {
        this.addQueueBind = addQueueBind;
    }

    public RichLink getAddQueueBind() {
        return addQueueBind;
    }

    /**
     * Showing an error message.
     *
     * */
    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String clientId) {

        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);

    }

    public void pdoSrcSalesorderVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 95 && object != null) {
            System.out.println("Inside Validator 95!");
            if (duplicateValue("MnfPdoSRCVO2Iterator", "TransSO", object)) {
                FacesMessage message = new FacesMessage("Duplicate Sales Order is Selected !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
                // String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1400']}");
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            }
            pdosrcManualQtyBind.setDisabled(false);
        }

    }


    public void pdoTransSOVCE(ValueChangeEvent valueChangeEvent) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
    }

    public void pdosrcItmQtyVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
    }

    public void setPdoSrcTableBind(RichTable pdoSrcTableBind) {
        this.pdoSrcTableBind = pdoSrcTableBind;
    }

    public RichTable getPdoSrcTableBind() {
        return pdoSrcTableBind;
    }

    public void cancelPdoJCRCGenACE(ActionEvent actionEvent) {
        // System.out.println("isideofelseofdialogeventfucntionactionevent");
        //ADFUtils.findOperation("resetValForJCRC").execute();
        ADFBeanUtils.findOperation("resetValForJCRC").execute();
        // System.out.println("calledrestfunction");
        popupForJobCardBindVar.hide();
    }

    public void pdoGenerateJCRCACE(ActionEvent actionEvent) {

        //check if Job card or Route card selected
        //105 code is job and 106 is for route card
        if (srcJCRCGeneBind.getValue().equals(106)) {

            // ob = ADFUtils.findOperation("ChkJCExists");
            ob = ADFBeanUtils.findOperation("ChkJCExists");
            ob.execute();

            //check if Job Card Exists for this Production Order continue if function returns N.
            if (ob.getResult().equals("Y")) {

                JSFUtils.addFacesInformationMessage("Route card can not be generated as an Operation exists against a Job Card or Group Production Plan ! !");
                //String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                //JSFUtils.addFacesInformationMessage(msg);

            } else if (ob.getResult().equals("N")) {

                if (getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime") != null &&
                    getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime") != null) {
                    try {
                        oracle.jbo.domain.Timestamp starttime =
                            (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime");
                        oracle.jbo.domain.Timestamp endtime =
                            (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime");
                        java.sql.Date d1 = starttime.dateValue();
                        java.sql.Date d2 = endtime.dateValue();
                        if (endtime.compareTo(starttime) < 0 && !d1.toString().equals(d2.toString())) {
                            JSFUtils.addFacesErrorMessage("Start Date Should be greater than End Date.");
                        } /* else if ((getPdoJcRcReqAreaBind().getValue() != null) &&
                                       (getPdoJcRcWhareHouseBind().getValue() != null)) { */
                        else if ((getAttrsVal("LOVGenerateJCRCVO1Iterator", "ReqAreaNme") != null) &&
                                 (getAttrsVal("LOVGenerateJCRCVO1Iterator", "WHouseNme") != null)) {
                            // ob = ADFUtils.findOperation("generateJobCard");
                            ob = ADFBeanUtils.findOperation("generateJobCard");
                            ob.execute();
                            System.out.println("ob.getResult().toString() " + ob.getResult().toString());
                            //Show confirmation message and comit data if operation binding return 1.
                            if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {
                                // ADFUtils.findOperation("Commit").execute();
                                ADFBeanUtils.findOperation("Commit").execute();
                                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                                JSFUtils.addFacesInformationMessage(msg);
                                // JSFUtils.addFacesInformationMessage("Record has been Saved Sucessfully !");
                                // ADFUtils.findOperation("resetValForJCRC").execute();
                                ADFBeanUtils.findOperation("resetValForJCRC").execute();
                            } else {
                                JSFUtils.addFacesErrorMessage("Error in saving records. ");
                            }
                        } else {
                            JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {

                    JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");

                }
            }

        } else if (srcJCRCGeneBind.getValue().equals(105)) {

            if ((getPdoJcRcEmpBind().getValue() != null) && (getPdoJcRcShiftBind().getValue() != null) &&
                (getPdoJcRcStrtTimeBind().getValue() != null) && (getPdoJcRcEndTimeBind().getValue() != null) &&
                (getPdoJcRcReqAreaBind().getValue() != null) && (getPdoJcRcWhareHouseBind().getValue() != null)) {

                if (getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime") != null &&
                    getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime") != null) {
                    try {
                        oracle.jbo.domain.Timestamp starttime =
                            (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "StartTime");
                        oracle.jbo.domain.Timestamp endtime =
                            (oracle.jbo.domain.Timestamp) getAttrsVal("LOVGenerateJCRCVO1Iterator", "EndTime");
                        java.sql.Date d1 = starttime.dateValue();
                        java.sql.Date d2 = endtime.dateValue();
                        if (endtime.compareTo(starttime) < 0 && !d1.toString().equals(d2.toString())) {
                            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Start Date Should be greater than End Date.",null));

                            JSFUtils.addFacesErrorMessage("Start Date Should be greater than End Date.");

                        } else {
                            // ob = ADFUtils.findOperation("generateJobCard");
                            ob = ADFBeanUtils.findOperation("generateJobCard");
                            ob.execute();
                            System.out.println("ob.getResult().toString() " + ob.getResult().toString());
                            //Show confirmation message and comit data if operation binding return 1.
                            if (ob.getResult() != null && ("1").equals(ob.getResult().toString())) {
                                // ADFUtils.findOperation("Commit").execute();
                                ADFBeanUtils.findOperation("Commit").execute();
                                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                                JSFUtils.addFacesInformationMessage(msg);
                                // ADFUtils.findOperation("resetValForJCRC").execute();
                                ADFBeanUtils.findOperation("resetValForJCRC").execute();
                            } else {
                                JSFUtils.addFacesErrorMessage("Error in saving records. ");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {

                JSFUtils.addFacesErrorMessage("Star Marked(*) Field are Mandatory.");

            }
        }
        popupForJobCardBindVar.hide();
    }

    public void setTransItmProducQtyForJCRCBind(RichInputText transItmProducQtyForJCRCBind) {
        this.transItmProducQtyForJCRCBind = transItmProducQtyForJCRCBind;
    }

    public RichInputText getTransItmProducQtyForJCRCBind() {
        return transItmProducQtyForJCRCBind;
    }

    public void qcParamDescVCE(ValueChangeEvent valueChangeEvent) {
        setAttrsVal("MnfPdoQcParamVO1Iterator", "TlrncLower", 0);
        setAttrsVal("MnfPdoQcParamVO1Iterator", "TlrncUpper", 0);
        setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", 0);
        setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", 0);
        setAttrsVal("MnfPdoQcParamVO1Iterator", "StdVal", 0);

        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addQcParamBind);
    }

    public void chkPercentAmountVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null) {
            Integer c = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number e = (Number) paramValueBindVar.getValue();

                Number b = (Number) minTolBindVar.getValue();
                Number d = (Number) maxTolBindVar.getValue();

                if (a != null && b != null && d != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));

                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);

                    setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", g);
                    setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", f);
                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);

    }

    public void setIsValuePercBinding(RichSelectBooleanCheckbox isValuePercBinding) {
        this.isValuePercBinding = isValuePercBinding;
    }

    public RichSelectBooleanCheckbox getIsValuePercBinding() {
        return isValuePercBinding;
    }

    public void setValueTypeBindVar(RichSelectOneChoice valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichSelectOneChoice getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
    }

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    public void lowerTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {

            Integer c = (Integer) valueTypeBindVar.getValue();

            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) minTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        // System.out.println(b + "  dfd");
                    }
                    Number f = a.subtract(b);

                    setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", f);
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);

            }

        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.subtract(0);
            setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }

    }

    public void setMinValBindVar(RichInputText minValBindVar) {
        this.minValBindVar = minValBindVar;
    }

    public RichInputText getMinValBindVar() {
        return minValBindVar;
    }

    public void uprTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 274) {
                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) maxTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        //System.out.println(b + "  dcf");
                    }
                    Number f = a.add(b);
                    setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            }
        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.add(0);
            setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        }

    }

    public void stdValueVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer x = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (x == 274) {

                Number a = new Number(0);
                Number e = new Number(0);
                Number b = new Number(0);
                Number d = new Number(0);

                a = (Number) paramValueBindVar.getValue();
                e = (Number) paramValueBindVar.getValue();
                System.out.println("STD Min Tol " + minTolBindVar);
                System.out.println("STD Min Tol " + maxTolBindVar);

                if (minTolBindVar.getValue() != null) {
                    b = (Number) minTolBindVar.getValue();
                }
                if (maxTolBindVar.getValue() != null) {
                    d = (Number) maxTolBindVar.getValue();
                }
                if (a != null && b != null && d != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));
                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);
                    System.out.println("after STD Min Tol " + f);
                    System.out.println("aftetr STD Min Tol " + g);

                    setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", g);
                    setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        } else {
            setAttrsVal("MnfPdoQcParamVO1Iterator", "TlrncLower", 0);
            setAttrsVal("MnfPdoQcParamVO1Iterator", "TlrncUpper", 0);
            setAttrsVal("MnfPdoQcParamVO1Iterator", "UpperLimit", 0);
            setAttrsVal("MnfPdoQcParamVO1Iterator", "LowerLimit", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        }

    }

    public void standardValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {


            Number value = (Number) (object);


            Boolean isValid = isPrecisionValid(26, 6, value);
            // Boolean isPerc = isPrecisionValid(5, 2, value);
            //System.out.println(typ + " =================== set type");
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 20.2 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }

            if (value.compareTo(0) < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }


            /* oracle.jbo.domain.Number stdNum = (oracle.jbo.domain.Number) object;
                  if (ADFBeanUtils.isNumberNegative(stdNum)) {
                      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                    "Negative Value not allowed.", null));
                  }
                  if (!ADFBeanUtils.isPrecisionValid(stdNum)) {
                      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.", null));
                  } */
        }


    }

    public void lowTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /* Number value = (Number) (object);

                   if (isValuePercBinding.isSelected()) {
                       if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Please provide Percentage Value.", null));
                       }
                       if (ADFBeanUtils.isNumberNegative(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Negative Value not allowed.", null));
                       }
                   } else {
                       if (!ADFBeanUtils.isPrecisionValid(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                         null));
                       }
                       if (ADFBeanUtils.isNumberNegative(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Negative Value not allowed.", null));
                       }
                   } */



            Number value = (Number) (object);

            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);
            if (ADFBeanUtils.isNumberNegative(value)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative Value not allowed.", null));
            }

            if (isValuePercBinding.isSelected()) {
                if (isPerc == false) {
                    FacesMessage message = new FacesMessage("Precision upto 5.2 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
                if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {

                    FacesMessage message = new FacesMessage("Enter value in between 0 to 100!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            } else {
                if (isValid == false) {
                    FacesMessage message = new FacesMessage("Precision upto 20.2 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
        }


    }

    public void uprTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /* Number value = (Number) (object);

                   if (isValuePercBinding.isSelected()) {
                       if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Please provide Percentage Value.", null));
                       }
                       if (ADFBeanUtils.isNumberNegative(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Negative Value not allowed.", null));
                       }
                   } else {
                       if (!ADFBeanUtils.isPrecisionValid(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                         null));
                       }
                       if (ADFBeanUtils.isNumberNegative(value)) {
                           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                         "Negative Value not allowed.", null));
                       }
                   }
                   Number minV = new Number(0);
                   if (minTolBindVar.getValue() != null) {
                       minV = (Number) minTolBindVar.getValue();
                   }

                   if (value.compareTo(minV) == -1) {
                       FacesMessage message = new FacesMessage("Upper Tolerance must be greater than Lower Tolerance!");
                       message.setSeverity(FacesMessage.SEVERITY_ERROR);
                       throw new ValidatorException(message);
                   }
 */

            Number value = (Number) (object);
            Number minV = new Number(0);
            if (minTolBindVar.getValue() != null) {
                minV = (Number) minTolBindVar.getValue();
            }

            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);
            if (ADFBeanUtils.isNumberNegative(value)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative Value not allowed.", null));
            }
            if (isValuePercBinding.isSelected()) {
                if (isPerc == false) {
                    FacesMessage message = new FacesMessage("Precision upto 2.2 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
                if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {

                    FacesMessage message = new FacesMessage("Enter value in between 0 to 100!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            } else {
                if (isValid == false) {
                    FacesMessage message = new FacesMessage("Precision upto 20.2 digit only!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

            }

            if (value.compareTo(minV) == -1) {
                FacesMessage message = new FacesMessage("Upper Tolerance must be greater than Lower Tolerance!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }


        }
    }

    public void setMaxValBindVar(RichInputText maxValBindVar) {
        this.maxValBindVar = maxValBindVar;
    }

    public RichInputText getMaxValBindVar() {
        return maxValBindVar;
    }

    public void setQcParamTable(RichTable qcParamTable) {
        this.qcParamTable = qcParamTable;
    }

    public RichTable getQcParamTable() {
        return qcParamTable;
    }

    public void addMnfPdoQcParamACE(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.findOperation("CreateWithParamsToMnfPdoQcParam").execute();
        //  qualitychkParamDescBindVar.setRequired(true);
        //  qcParamDescBindVar.setRequired(true);
        // qualitychkOpNmeBindVar.setRequired(true);
        ValueForQc = "Y";
    }

    String ValueForQc = "N";

    public void setValueForQc(String ValueForQc) {
        this.ValueForQc = ValueForQc;
    }

    public String getValueForQc() {
        return ValueForQc;
    }

    public void deleteQcParameterACE(ActionEvent actionEvent) {
        // DeleteQcParameter
        //  qualitychkParamDescBindVar.setRequired(false);
        //  qcParamDescBindVar.setRequired(false);
        //   Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteQcParameter");
        //  System.out.println("Key is : " + key);
        //  rowDelete(key, "MnfPdoQcParamVO1Iterator");
        ADFBeanUtils.findOperation("DeleteQcParam").execute();
        ValueForQc = "N";
    }

    public void operationNmeVCE(ValueChangeEvent valueChangeEvent) {
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        //FacesContext.getCurrentInstance().renderResponse();
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        // qcParamDescBindVar.setRequired(true);
    }
    String qcFlag = "N";

    public void setQcFlag(String qcFlag) {
        this.qcFlag = qcFlag;
    }

    public String getQcFlag() {
        return qcFlag;
    }

    public void ducQcParamChkVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object !=
            null) {
            //  System.out.println("Inside of  validator 0000 !!" + object );

            //  if (duplicateValue("MnfPdoQcParamVO1Iterator", "TransOperationName", getAttrsVal("MnfPdoQcParamVO1Iterator", "TransOperationName")) && duplicateValue("MnfPdoQcParamVO1Iterator", "TransQcParam", object)) {
            if (duplicateValue12("MnfPdoQcParamVO1Iterator", "TransOperationName",
                                 getAttrsVal("MnfPdoQcParamVO1Iterator", "TransOperationName"), "TransQcParam",
                                 object)) {
      //   System.out.println("Inside of Dup Check !!");



                // if (duplicateValue("MnfPdoQcParamVO1Iterator", "TransQcParam", object)) {
                //   System.out.println("Inside of  validator !!");
                JSFUtils.addFacesErrorMessage("Duplicate Parameter is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Parameter Selected", null));
            }
            //}
        }
        /*  else
        if(qualitychkParamDescBindVar.getValue() != null){
            qcParamDescBindVar.setRequired(true);

        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueTypeBindVar);


    }

    public void setQcParamDescBindVar(RichInputListOfValues qcParamDescBindVar) {
        this.qcParamDescBindVar = qcParamDescBindVar;
    }

    public RichInputListOfValues getQcParamDescBindVar() {
        return qcParamDescBindVar;
    }

    public void setQualitychkParamDescBindVar(RichInputListOfValues qualitychkParamDescBindVar) {
        this.qualitychkParamDescBindVar = qualitychkParamDescBindVar;
    }

    public RichInputListOfValues getQualitychkParamDescBindVar() {
        return qualitychkParamDescBindVar;
    }

    public void setQualitychkOpNmeBindVar(RichSelectOneChoice qualitychkOpNmeBindVar) {
        this.qualitychkOpNmeBindVar = qualitychkOpNmeBindVar;
    }

    public RichSelectOneChoice getQualitychkOpNmeBindVar() {
        return qualitychkOpNmeBindVar;
    }

    public void transRefPdoDocIdVCE(ValueChangeEvent valueChangeEvent) {


    }

    public void trandPdoRefDocVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* ActionEvent acc = new ActionEvent(addQueueBind);
        acc.queue();
        addQueueACE(acc); */



    }

    public String getSalesTracker() {


        ob = ADFBeanUtils.findOperation("salesTacker");
        ob.execute();
        return "callSalesOrderTracking";
    }

    public void setAddQcParamBind(RichLink addQcParamBind) {
        this.addQcParamBind = addQcParamBind;
    }

    public RichLink getAddQcParamBind() {
        return addQcParamBind;
    }

    public String headCostCenterAction() {

        /* ob =  ADFBeanUtils.findOperation("getCostCenterChk");
        ob.execute();
        setCcChk((oracle.jbo.domain.Number)ob.getResult());
        if(getCcChk().compareTo(new Number(0)) == 1){ */
        ob = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        ob.execute();
        if (ob.getResult() != null && (Boolean) ob.getResult()) {
            return "headCc";
        } else {
            String msg = "No Cost Center Exists !";

            JSFUtils.addFacesErrorMessage(msg);
            return null;
        }
        //}else{

        //  }
        // return null;
    }

    public void downloadAttachmentACE(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void downloadACE(FacesContext facesContext, OutputStream outputStream) throws IOException {
        String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        File file = new File(path);
        FileInputStream fis;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream.flush();

    }

    public void pdoSrcSalesForVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && (Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 96) {
            System.out.println("Inside Validator 96!");
            if (duplicateValue("MnfPdoSRCVO2Iterator", "TransSO", object)) {

                if (duplicateValue("MnfPdoSRCVO2Iterator", "DlvDt", getAttrsVal("MnfPdoSRCVO2Iterator", "DlvDt"))) {

                    FacesMessage message = new FacesMessage("Duplicate Sale Forecast is Selected !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                    // String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1400']}");
                    // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);

    }

    public void pdoTransSFVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
    }

    public void setPdoSrcSalesForBind(RichInputListOfValues pdoSrcSalesForBind) {
        this.pdoSrcSalesForBind = pdoSrcSalesForBind;
    }

    public RichInputListOfValues getPdoSrcSalesForBind() {
        return pdoSrcSalesForBind;
    }

    public void setPdoSubContractNobind(RichInputListOfValues pdoSubContractNobind) {
        this.pdoSubContractNobind = pdoSubContractNobind;
    }

    public RichInputListOfValues getPdoSubContractNobind() {
        return pdoSubContractNobind;
    }

    public void subContractVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if ((Integer) getAttrsVal("MnfPdoSRCVO2Iterator", "SrcType") == 147 && object != null) {
            System.out.println("Inside Validator 147!");
            if (duplicateValue("MnfPdoSRCVO2Iterator", "TransSO", object)) {
                FacesMessage message = new FacesMessage("Duplicate SubContracting Order is Selected !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void subContractVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSrcTableBind);
    }

    public void generatejcRcOperationVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
       if(object != null)
       {
               /*   AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpSrNoBind);
               AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
               AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);
               AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpLocBind);
               AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpWorkCenterBind); */
       }

    }

    public void refreshJCRCACE(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpSrNoBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpLocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpWorkCenterBind);
        
    }

    public void generateJCRCOpSrNoVCE(ValueChangeEvent valueChangeEvent) {
        /*  AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpSrNoBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpItmQtyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpLocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoOpWorkCenterBind);
         */
    }

    public void generateJCRcPendingItmQtyVCE(ValueChangeEvent vce) {
        
        transItmProducQtyForJCRCBind.setRequired(true);
        transItmProducQtyForJCRCBind.setShowRequired(true);
    }

    public void shortCloseVCE(ValueChangeEvent valueChangeEvent) {
       if(shortcloseChk.isSelected())
       {
      /* ob = ADFBeanUtils.findOperation("shortcloseChk");
      ob.execute(); */
       if(getShortCloseChk().equals("Y")){
          saveBind.setDisabled(false);
     }
     else{
            JSFUtils.addFacesErrorMessage("You can not short close this Production Order ! ");
        }
     
    }
    }

    public void setShortcloseChk(RichSelectBooleanCheckbox shortcloseChk) {
        this.shortcloseChk = shortcloseChk;
    }

    public RichSelectBooleanCheckbox getShortcloseChk() {
        return shortcloseChk;
    }
    
    String shortCloseChk ="N";

    public void setShortCloseChk(String shortCloseChk) {
        this.shortCloseChk = shortCloseChk;
    }

    public String getShortCloseChk() {
        ob = ADFBeanUtils.findOperation("shortcloseChk");
        ob.execute();
       // return shortCloseChk;
        return ob.getResult().toString();
    }

    public void pdosrcItmQtyVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            // Boolean isPerc = isPrecisionValid(5, 2, value);
           
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 20,6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) == 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
         String srFlag = (String) getAttrsVal("MnfPDOVO1Iterator", "TransSerialFlag");
          System.out.println(srFlag + " =================== sr flag type");
         if(srFlag.equals("Y") || srFlag=="Y")
         {
              Boolean isValid1 = isPrecisionValid(20, 0, value);
              if (isValid1 == false) {
                  FacesMessage message = new FacesMessage("Item is Serialized, So Qty can not be in decimal !");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(message);
              }
          }

        }


    }
}
