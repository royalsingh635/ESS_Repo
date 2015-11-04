package slsintimationslipapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import org.apache.myfaces.trinidad.util.Service;

import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;

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
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

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
    private RichInputText serialFlagBind;
    private RichPopup serialPopUpBind;
    private Boolean enableFlex = null;

    public Boolean getEnableFlex() {
        if (enableFlex == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isFlexApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableFlex = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }
       
        return enableFlex;
    }

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

    /**
     * ActionEvent to insert Items into IntimationSlip Item
     * @param actionEvent
     */
    public void insertIntoItemAction(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("autoPopulateShipmntItm");
        binding.execute();
        if (binding.getResult() != null) {
            ADFBeanUtils.getOperationBinding("Execute").execute();
            ADFBeanUtils.getOperationBinding("Execute1").execute();
        }
    }

    /**
     * Method to create new Intimation
     * @param actionEvent
     */
    public void createIntimationSlipAction(ActionEvent actionEvent) {
        setMode("A");
        setIsSoldDisable(false);
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
        operationBinding.execute();
        ADFBeanUtils.findOperation("createFlexiField").execute();
    }

    /**
     *
     * @param actionEvent
     */
    public void SaveButtonAL(ActionEvent actionEvent) {
        OperationBinding a = ADFBeanUtils.findOperation("isflexFieldNull");
        a.execute();

        if ((Boolean) a.getResult()) {
            ADFModelUtils.showFormattedFacesMessage("Mandatory Field(s) cannnot be Empty",
                                                    "Please enter values for Mandatory fields in Other Details ", //The function for Generating Warranty returned
                                                    FacesMessage.SEVERITY_ERROR);
        } else {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("saveIntimation");
            binding.getParamsMap().put("mode", mode);
            binding.execute();
            Object o = binding.getResult();
            Integer i = (o == null ? -1 : (Integer) o);
            System.out.println("I : " + i);
            if (i == 0) {
                mode = "V";
            }
        }

        /* if (mode.equalsIgnoreCase("E")) {
            Object execute = getBindings().getOperationBinding("checkForLessBalQty").execute();
            if (execute != null) {
                int value = Integer.parseInt(execute.toString());
                if (value == 1) {
                    String msg = "Sold Quantity can not be less than or equal to zero !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                } else if (value == 2) {
                    String msg = "Balance quantity can not be empty !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                } else if (value == 3) {
                    String msg = "Sold quantity can not be greater than balance quantity !!";
                    showMessage(msg, FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
        }
        if (chkToGoFurtherOrNot()) {
            if (mode.equalsIgnoreCase("A") || true) {
                setMode("E");
                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
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
        } */
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

            operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute2");
            operationBinding.execute();

        } else {
            parentIter = (DCIteratorBinding) getBindings().get("SlsIntmSlipIterator");
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
                        //parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
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
        OperationBinding binding = getBindings().getOperationBinding("isUserAllowedToEdit");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        if (i == 1) {
            OperationBinding operationBinding1 = getBindings().getOperationBinding("refreshVo");
            getBindings().getOperationBinding("updateIntmBalQty").execute();
            this.setMode("E");
        }

        /* if (execute == null) {
            String msg =
                resolvElDCMsg("Document is already Pending for another user, can not Edit !!").toString(); //Please Enter the value
            FacesMessage facesMsg = new FacesMessage(msg);
            facesMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return;
        } */
    }

    public void saveFunction() {
        // System.out.println("************");
        {
            OperationBinding operationBinding = null;
            if (docTxnIdBinding.getValue() != null) {
                docHexId = docTxnIdBinding.getValue().toString(); //SlsIntmSlipVOCriteria1
                System.out.println("Doc value is " + docHexId);
            }

            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsIntmSlipIterator");
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

                    //  String getRes=this.saveToWorkFlow();

                    operationBinding = getBindings().getOperationBinding("isIsAllItmZero");
                    operationBinding.execute();
                    Boolean isVal = (Boolean) operationBinding.getResult();
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

            docDate = (Timestamp) docDtBinding.getValue();
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
            Number soldQty = (Number) object;
            Number balQty = new Number(0);

            if (uIComponent.getAttributes().get("balQty") != null) {
                Object object_2 = uIComponent.getAttributes().get("balQty");
                balQty = (Number) object_2;
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

    public String saveToWorkFlow() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("passWorkFlowEntries");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        if (i == 1) {
            return "Y";
        } else {
            return "N";
        }
    }

    public void soldQtyValueChngListener(ValueChangeEvent vce) {

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

    /**
     * Method to save and forwards Intimation
     * @return
     */
    public String saveAndForwardAction() {
        Object object2 = null;
        OperationBinding a = ADFBeanUtils.findOperation("isflexFieldNull");
        a.execute();

        if ((Boolean) a.getResult()) {
            ADFModelUtils.showFormattedFacesMessage("Mandetory Field(s) cannnot be Empty",
                                                    "Please enter values for Mandetory fields in Other Details ", //The function for Generating Warranty returned
                                                    FacesMessage.SEVERITY_ERROR);
            return null;
        } else {
            /*  OperationBinding serialBind = ADFBeanUtils.getOperationBinding("checkSerialValidation");
        serialBind.execute();
        Boolean flg=(Boolean)serialBind.getResult();
        System.out.println("checkSerialValidation ::::"+flg); */

            OperationBinding binding1 = ADFBeanUtils.getOperationBinding("getWorkFlowId");
            if (binding1 != null) {
                binding1.execute();
                Object object = binding1.getResult();
                wfNo = (object == null ? null : object.toString());
            }
            OperationBinding binding = ADFBeanUtils.getOperationBinding("saveAndForward");
            binding.getParamsMap().put("mode", mode);
            binding.execute();
            object2 = binding.getResult();
            if (object2 != null) {
                mode = "V";
            }


            return (object2 == null ? null : object2.toString());
        }
    }


    public Boolean chkToGoFurtherOrNot() {
        OperationBinding ob = getBindings().getOperationBinding("chkSoldQtyEntererorNot");
        ob.execute();
        return (Boolean) ob.getResult();
    }

    public void setWfNo(String wfNo) {
        this.wfNo = wfNo;
    }

    public String getWfNo() {
        return wfNo;
    }

    /**
     * Set Create mode to View
     */
    public void setModeToCreate() {
        mode = "A";
    }


    public String cancelACTION() {
        Key parentKey = null;
        DCIteratorBinding parentIter = null;
        if (mode.equalsIgnoreCase("A")) {
            getBindings().getOperationBinding("deleteIntimationOnCancel").execute();
            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();

            operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute2");
            operationBinding.execute();

        } else {
            parentIter = (DCIteratorBinding) getBindings().get("SlsIntmSlipIterator");
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
                        //parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
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
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        setMode("V");
        return "goBack";
    }

    public void setSerialFlagBind(RichInputText serialFlagBind) {
        this.serialFlagBind = serialFlagBind;
    }

    public RichInputText getSerialFlagBind() {
        return serialFlagBind;
    }


    public void SerialPopUpAL(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("filterShipmentSRVOOnShipId");
        ob.execute();
        showPopup(serialPopUpBind, true);
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

    public void setSerialPopUpBind(RichPopup serialPopUpBind) {
        this.serialPopUpBind = serialPopUpBind;
    }

    public RichPopup getSerialPopUpBind() {
        return serialPopUpBind;
    }

    public void addSerialDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            System.out.println("In Ok -----");
            OperationBinding ob = getBindings().getOperationBinding("checkForSoldQty");
            ob.execute();
            Boolean res = (Boolean) ob.getResult();
            System.out.println("Res of checkForSoldQty ::" + res);
            if (res != null) {
                if (res.equals(true)) {
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2242"),
                                                            //"The Selected quantity can not be more or less than the sold Quantity!"
                        ADFModelUtils.resolvRsrc("MSG.2243"),
                        //"Please Select the Serials in accordance with the sold quantity!"
                        FacesMessage.SEVERITY_ERROR);
                } else {
                    OperationBinding obSet = getBindings().getOperationBinding("setValuesFromShipSrTOIntmSr");
                    obSet.execute();
                    Boolean resGet = (Boolean) obSet.getResult();
                    if (resGet != null) {
                        if (resGet.equals(true)) {

                        } else {
                            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2244"),
                                                                    ADFModelUtils.resolvRsrc("MSG.2245"),
                                                                    FacesMessage.SEVERITY_ERROR); //"There Must be some Error in setValuesFromShipSrTOIntmSr"
                            //Must Be Some Error in Parameters
                        }
                    }
                }
            }
        }
    }
}
