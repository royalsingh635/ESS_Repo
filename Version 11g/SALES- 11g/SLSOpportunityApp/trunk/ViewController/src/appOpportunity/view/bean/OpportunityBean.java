package appOpportunity.view.bean;


//import appOpportunity.model.services.AppOpportunityAMImpl;

import adf.utils.bean.ADFBeanUtils;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class OpportunityBean {

    private StringBuffer showItmDesc = null;
    private RichInputText eoNameBinding;
    private RichSelectOneChoice eoTypeBinding;
    private RichPopup createCustomerPopup;
    private RichDialog createNewItmPopup;
    private Boolean readOnlyAssigned;
    private RichInputText itmDescBinding;
    private RichPopup createNewItemPopup;
    private RichInputText itmPrefixBinding;
    private RichInputText searchString;
    private RichSelectOneChoice lovBinding;
    private RichInputText currIdSpBinding;

    private RichInputText baseAmountBinding;
    private RichInputText currencyRateBinding;
    private RichInputText currRateItmBinding;
    private RichInputText itemPriceBinding;
    private RichInputText itemQuantityBinding;
    private RichInputText specificAmountItemBinding;
    private Number currencyRate;
    private Number itemQuantity;
    private Number itemPrice;
    private Number totalSpecificAmount;
    private RichInputText baseAmtBinding;
    //private RichSelectOneChoice assignedToBinding;
    private Boolean isButtonEnb;
    private String modeOpp = "V";
    private String modeItm = "V";
    private String renderOpp = "single";
    private RichTable mainTabBinding;
    private RichTable itemTabBinding;
    private RichInputDate docDtBinding;
    private String editmode = "V";
    private boolean customername = true;
    private boolean currencydisable = false;
    private String view = "N";
    private Boolean itmpriceusevalidator = true;
    private Boolean quantityusevalidato = true;

    private static ADFLogger adfLog = ADFLogger.createADFLogger(OpportunityBean.class);
    private RichInputText financialYrBinding;
    private RichInputDate docDtSearchBind;
    private RichSelectOneChoice currIdSearchBind;
    private RichSelectOneChoice eoNmSearchBind;
    private RichInputText noOfItemsBind;
    private RichInputText totOppAmtSearchBind;
    private RichSelectOneChoice categorySearchBind;
    private RichSelectOneChoice docIdSearchBind;
    private RichInputDate fromDateBinding;
    private RichInputDate toDateBinding;
    private RichSelectOneChoice catgIdBinding;
    private RichInputText exptdPriceBinding;
    private RichSelectOneRadio assignedTypeBinding;

    private RichInputText docHexaIdBinding;
    private Number zero = new Number(0);
    private RichCommandImageLink addItemBinding;
    private RichCommandImageLink editItmBinding;
    private RichInputText dispDocIdBinding;
    private RichSelectOneChoice itemUomBinding;
    private RichInputText currencyBinding;
    /* private RichInputListOfValues docIdSearchBindVar; */
    private RichInputListOfValues eoNmSearchBindVar;
    private RichInputListOfValues eoNmBindValue;
    private RichInputText totalAmountBind;
    private RichInputText totalAmountBaseBind;
    private RichOutputText totalAmountSPFooterBind;
    private RichOutputText totalAmountBSFooterBind;
    private RichSelectBooleanCheckbox lostCheckStatusBind;
    private RichSelectOneChoice lovStatusBind;
    private boolean lostMode = true;
    private RichInputText lostRemarkbind;
    private RichInputListOfValues assignedToBinding;
    private String decimalVal;
    private RichCommandImageLink itemaddBinding;
    private RichInputComboboxListOfValues dcIdBind;
    private RichInputComboboxListOfValues eoIdTransBind;
    private RichInputListOfValues dcallDcIdBind;
    private RichPanelFormLayout pf3Binding;
    private RichCommandLink importExcelLinkBind;

    public OpportunityBean() {

    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public String createOppAction() {
        // Add event code here...
        /*  mainTabBinding.setRowSelection("single");
        AdfFacesContext.getCurrentInstance().addPartialTarget(mainTabBinding); */

        //lostCheckStatusBind.setDisabled(true);
        String cldId = null;
        String orgId = null;
        customername = true;
        setModeOpp("A");
        lostMode = true;
        currencydisable = false;
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
        operationBinding.execute();
        return null;
    }


    /**
     *
     * @param actionEvent
     */
    public void createItmForOppActionListener(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("areItemValuesValid");
        binding.execute();
        binding.getResult();
        // System.out.println("Are Items Valid : "+binding.getResult());
        if (binding.getResult().equals((Boolean)false)) {

        } else if (catgIdBinding.getValue() != null && catgIdBinding.getValue() != "") {

            OperationBinding operationBinding = getBindings().getOperationBinding("chkCustomerNameExistOrNot");
            operationBinding.execute();
            Boolean retValfromAmImpl = (Boolean)operationBinding.getResult();
            //System.out.println("Return Value is :"+retValfromAmImpl);
            if (retValfromAmImpl != null && retValfromAmImpl) {
                if (currencyBinding.getValue() != null && currencyBinding.getValue() != "") {
                    itemTabBinding.setRowSelection("single");
                    setModeItm("A");
                    editmode = "C";
                    operationBinding = getBindings().getOperationBinding("CreateInsert");
                    operationBinding.execute();
                } else {
                    //showFacesMessage("MSG.951", "E", true, "V", eoIdOppBinding.getClientId()); //This field is mandatory


                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.21']}").toString()); //Currency is Required
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                    setModeItm("V");
                }
            } else {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1018']}").toString()); //Customer Name is Required
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

                setModeItm("V");

            }

        } else {
            //showFacesMessage("MSG.951", "E", true, "V", catgIdBinding.getClientId()); //This field is mandatory

            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.951']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(catgIdBinding.getClientId(), message);

            setModeItm("V");
        }

    }


    public String editOppAction() {
        // Add event code here...
        //mainTabBinding.setRowSelection("none");


        if (itemTabBinding.getValue() != null) {
            itemTabBinding.setRowSelection("single");
        }
        setModeItm("E");
        editmode = "E";
        setModeOpp("E");
        lostMode = false;
        //  lostCheckStatusBind.setDisabled(false);
        /*  OperationBinding op = getBindings().getOperationBinding("Execute");
        op.execute();
       */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemaddBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(importExcelLinkBind);
        return null;
    }

    /*  public String editItmForOppAction() {
        // Add event code here...

        if (itemTabBinding.getValue() != null) {
            itemTabBinding.setRowSelection("single");
        }
        setModeItm("E");
        editmode = "E";
          OperationBinding op = getBindings().getOperationBinding("Execute1");
        op.execute();

    }    */

    public String saveOppAction() {
        // Add event code here...
        lostMode = true;
        if (itemQuantityBinding.getValue() == null || itemQuantityBinding.getValue() == "" ||
            itemQuantityBinding.getValue().toString().length() == 0) {
            System.out.println("inside if of quantity");
            String msg = resolvElDCMsg("quantity can not be null").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return "";
        }
        OperationBinding binding = this.getBindings().getOperationBinding("areItemValuesValid");
        binding.execute();
        binding.getResult();
        System.out.println("Are Items Valid : " + binding.getResult());
        if (binding.getResult().equals((Boolean)false)) {

        } else if (itmpriceusevalidator && quantityusevalidato) {
            String cldId = null;
            String orgId = null;
            String docId = null;
            if (dispDocIdBinding.getValue() != null) {

                OperationBinding operationBinding = getBindings().getOperationBinding("chkItenNameExistOrNot");
                operationBinding.execute();
                Boolean retchkval = (Boolean)operationBinding.getResult();

                if (retchkval) {
                    cldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
                    orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
                    if (docHexaIdBinding.getValue() != null) {
                        docId = docHexaIdBinding.getValue().toString();
                        adfLog.info("docHexaIdBinding.getValue : " + docId);
                    }
                    if ((!modeItm.equalsIgnoreCase("V")) || (modeOpp.equalsIgnoreCase("E"))) {
                        adfLog.info("Save");
                        OperationBinding ob = getBindings().getOperationBinding("getTotalAmt");
                        ob.execute();
                        if (Integer.parseInt(ob.getResult().toString()) == 1 ||
                            Integer.parseInt(ob.getResult().toString()) == 2) {

                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1134']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                        } else {
                            ob = getBindings().getOperationBinding("Commit");
                            ob.execute();
                            ob = getBindings().getOperationBinding("Execute1");
                            ob.execute();
                            setModeOpp("V");
                            setModeItm("V");
                            editmode = "V";
                            String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }

                        return null;
                    } else {
                        String msg =
                            resolvElDCMsg("#{bundle['MSG.952']}").toString(); //At least one Item detail is necessary
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }

                } else {
                    String msg =
                        resolvElDCMsg("#{bundle['MSG.952']}").toString(); ////At least one Item detail is necessary
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {

                // System.out.println("Mode : " + getModeOpp());
                cldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
                orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
                adfLog.info("cldId : " + cldId + "orgId : " + orgId);
                /*  OperationBinding ob = getBindings().getOperationBinding("getFYidOrg");
            ob.getParamsMap().put("P_CldId", cldId);
            ob.getParamsMap().put("P_OrgId", orgId);
            ob.execute();
            adfLog.info("FyId : " + ob.getResult());
            financialYrBinding.setValue(ob.getResult());*/
                adfLog.info("financialYrbinding : " + financialYrBinding.getValue());
                OperationBinding ob = getBindings().getOperationBinding("getDispDocId");
                ob.execute();
                adfLog.info("disp doc id : " + ob.getResult());

                OperationBinding operationBinding = getBindings().getOperationBinding("chkItenNameExistOrNot");
                operationBinding.execute();
                Boolean retchkval = (Boolean)operationBinding.getResult();

                if (retchkval) {
                    cldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
                    orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
                    if (docHexaIdBinding.getValue() != null) {
                        docId = docHexaIdBinding.getValue().toString();
                        adfLog.info("docHexaIdBinding.getValue : " + docId);
                    }
                    if ((!modeItm.equalsIgnoreCase("V")) || (modeOpp.equalsIgnoreCase("E"))) {
                        adfLog.info("Save");
                        ob = getBindings().getOperationBinding("getTotalAmt");
                        ob.execute();
                        ob = getBindings().getOperationBinding("Commit");
                        ob.execute();
                        ob = getBindings().getOperationBinding("Execute1");
                        ob.execute();
                        setModeOpp("V");
                        setModeItm("V");
                        editmode = "V";
                        String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);


                        return null;
                    } else {
                        String msg =
                            resolvElDCMsg("#{bundle['MSG.952']}").toString(); //At least one Item detail is necessary
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }

                } else {
                    String msg =
                        resolvElDCMsg("#{bundle['MSG.952']}").toString(); //At least one Item detail is necessary
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                if (mainTabBinding.getValue() != null && itemTabBinding != null) {
                    mainTabBinding.setRowSelection("single");
                    itemTabBinding.setRowSelection("Single");
                }
            }
            if (mainTabBinding.getValue() != null && itemTabBinding != null) {
                mainTabBinding.setRowSelection("single");
                itemTabBinding.setRowSelection("Single");
            }


            setModeItm("V");
            setModeOpp("V");
        } else {
            if (!itmpriceusevalidator) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1133']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itemPriceBinding.getClientId(), message);

            } else if (!quantityusevalidato) {

                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1133']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itemQuantityBinding.getClientId(), message);
            }
        }

        return null;
    }


    public String cancelOppAction() {
        // Add event code here...
        lostMode = true;
        view = "N";
        OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding resOppOB = getBindings().getOperationBinding("getResOpportunity");
        resOppOB.getParamsMap().put("docTxnIdRes", "R");
        resOppOB.execute();
        setModeOpp("V");
        setModeItm("V");
        editmode = "V";
        return "backToSeachOpp";
    }

    public String deleteItmForOppAction() {
        // Add event code here...
        //if (!modeItm.equalsIgnoreCase("V")) {
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
        operationBinding.execute();
        setModeOpp("E");

        currencydisable = true;

        /*   System.out.println("in If part");

        } else {

            OperationBinding operationBinding = getBindings().getOperationBinding("SlsOppItmExecute");
            operationBinding.execute();

            System.out.println("In else part");
        } */
        return null;
    }


    public String createCustomerLinkAction() {
        // Add event code here...
        //eoNameBinding.setRequired(false);
        adfLog.info("link Action");
        System.out.println("Cat ID Value--->" + catgIdBinding.getValue());

        // edited on May 21 for removing the null pointer exception .

        OperationBinding ob = getBindings().getOperationBinding("getEOCatId");
        ob.execute();

        if (ob.getResult() != null) {
            System.out.println("eocatid is--> from AMImpl" + ob.getResult());
            Integer eocatid = Integer.parseInt(ob.getResult().toString());
            OperationBinding obj = getBindings().getOperationBinding("setEOCatId");
            obj.getParamsMap().put("eocatid", eocatid);
            obj.execute();
            //eoTypeBinding.setValue(eocatid);
            // eoNameBinding.setValue(null);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(eoTypeBinding);
        showPopup(createCustomerPopup, true);

        return null;
    }


    /**
     *
     * @param dialogEvent
     */
    public void createNewCustDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        // System.out.println("_________________");
        //System.out.println("Inside Dialog Listener");
        //System.out.println(lovBinding.getValue().toString());
        adfLog.info("PopupDialogListener");
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            //enter code hereasdf
            //  eoNameBinding.setRequired(true);
            Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String cldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            String orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
            Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR }").toString());
            String hoOrgId = (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            adfLog.info("hoOrgId : " + hoOrgId);
            adfLog.info("slocId:  " + slocId);
            adfLog.info("cldId:  " + cldId);
            adfLog.info("orgId:  " + orgId);
            adfLog.info("usrId:  " + usrId);

            // System.out.println("Inside Yes block");
            if (eoNameBinding.getValue() != null) {
                adfLog.info("eoNameBinding.getValue():  " + eoNameBinding.getValue());
                System.out.println("eoNameBinding.getValue():" + eoNameBinding.getValue());
            }
            adfLog.info("eoTypeBinding.getValue():  " + eoTypeBinding.getValue());
            System.out.println("eoTypeBinding.getValue(): " + eoTypeBinding.getValue());


            if ((eoNameBinding.getValue() != null) && (eoTypeBinding.getValue() != null) &&
                eoNameBinding.getValue().toString().length() > 0) {
                System.out.println("Inside EoName Binding");
                OperationBinding ob = getBindings().getOperationBinding("getNewEoId");
                ob.getParamsMap().put("p_SlocId", slocId);
                ob.getParamsMap().put("p_CldId", cldId);
                ob.getParamsMap().put("p_OrgId", orgId);
                ob.getParamsMap().put("p_Name", eoNameBinding.getValue());
                ob.getParamsMap().put("p_Type", eoTypeBinding.getValue());
                ob.getParamsMap().put("p_UsrId", usrId);
                ob.execute();
                if (ob.getResult() != null) {
                    //  System.out.println("Inside if of EoBinding");
                    String eoidnew = ob.getResult().toString();


                    if (eoidnew.equalsIgnoreCase("true")) {

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1180']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(eoNameBinding.getClientId(), message);

                        // showFacesMessage("Customer Already Exist", "E", true, "F", eoNameBinding.getClientId());
                    }

                    adfLog.info("eoidnew: " + eoidnew);
                    // eoIdOppBinding.setValue(eoidnew);
                    //eoIdOppBinding.setValue(eoNameBinding.getValue());

                    //AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdOppBinding);
                }


            } else {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.117']}").toString()); //You must enter a value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(eoNameBinding.getClientId(), message);
            }
            adfLog.info("sucessfully executed");


        }

    }


    private void showPopup(RichPopup pop, boolean visible) {
        try {
            adfLog.info("show Popup");
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                adfLog.info(popupId);
                if (popupId != null) {
                    adfLog.info("id is not null");
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =);AdfPage.PAGE.findComponent('").append(popupId).append("');");
                    ;
                    if (visible) {
                        // System.out.println("visible");
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


    /*  public void itmExpPriceValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
            if(ispositiveNum((Number)valueChangeEvent.getNewValue())){

            }else{
                showFacesMessage("Number must positive and of valid precision (26,6)", "E", true, "V",
                                 exptdPriceBinding.getClientId());
            }
        }
    } */


    /* public boolean categoryValidate() {
        Object obj=null;
        if(catgIdBinding.getValue()!=null && catgIdBinding.getValue()==""){
            obj=catgIdBinding.getValue();
            adfLog.info("catgId"+obj);
            return true;
        }else{
            showFacesMessage("This field is mandatory", "E",true, "F", catgIdBinding.getClientId());
            return false;
        }
    }

    public boolean entityValidate() {
        Object obj=null;
        if(eoIdOppBinding.getValue()!=null && eoIdOppBinding.getValue()==""){
            obj=eoIdOppBinding.getValue();
            adfLog.info("catgId"+obj);
            return true;
        }else{
            showFacesMessage("This field is mandatory", "E",true, "F", eoIdOppBinding.getClientId());
            return false;
        }
    } */

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


    public void createNewItemDialogListener(DialogEvent dialogEvent) {
        // Add event code here...

        OperationBinding obn = getBindings().getOperationBinding("getDispDocId");
        Object sal = obn.execute();
        adfLog.info(" sal : " + sal);
        adfLog.info("lovBinding.getValue() : " + lovBinding.getValue());
        adfLog.info("PopupDialogListener");
        if (dialogEvent.getOutcome().name().equals("yes")) {
            //enter code here
            Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String cldId = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            String orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
            Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR }").toString());
            String hoOrgId = (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            adfLog.info("hoOrgId : " + hoOrgId);
            adfLog.info(" slocId: " + slocId);
            adfLog.info("cldId : " + cldId);
            adfLog.info("orgId : " + orgId);
            adfLog.info(" usrId: " + usrId);
            adfLog.info(" eoNameBinding: " + eoNameBinding.getValue());
            adfLog.info(" eoTypeBinding: " + eoTypeBinding.getValue());
            OperationBinding ob = getBindings().getOperationBinding("getNewItmId");
            if ((eoNameBinding.getValue() != null) || (eoTypeBinding.getValue() != null)) {
                ob.getParamsMap().put("p_SlocId", slocId);
                ob.getParamsMap().put("p_CldId", cldId);
                ob.getParamsMap().put("p_OrgId", orgId);
                ob.getParamsMap().put("p_Name", eoNameBinding.getValue());
                ob.getParamsMap().put("p_Type", eoTypeBinding.getValue());
                ob.getParamsMap().put("p_UsrId", usrId);
                ob.execute();
                Object retObj = ob.getResult();
                if (retObj != null) {
                    //eoIdOppBinding.setValue(retObj);
                }
                // System.out.println("sucessfully executed");


            } else if (dialogEvent.getOutcome().name().equals("no")) {
                //enter code here
            }
        }
    }


    /**
     *
     * @param actionEvent
     */
    public void searchActionListener(ActionEvent actionEvent) {
        // Add event code here...
        /*     if (searchString.getValue() != null) { */
        OperationBinding searchOB = getBindings().getOperationBinding("searchOpportunity");

        /* searchOB.getParamsMap().put("searchString", searchString.getValue()); */
        searchOB.execute();
        //System.currentTimeMillis();
        /*   } else {
            String msg = resolvElDCMsg("#{bundle['MSG.956']}").toString(); //Please select the Attribute to search!
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */
    }

    /**
     *
     * @param actionEvent
     */
    public void resetSearchStringActionListener(ActionEvent actionEvent) {
        // Add event code here...
        this.resetMasterTable();

    }

    public void resetMasterTable() {
        searchString.setValue("");
        OperationBinding resetOB = getBindings().getOperationBinding("resetOpportunity");
        resetOB.execute();
    }


    public void specificAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }

    public void specificAmountChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            Integer idSp = new Integer(0);
            Number currRate = new Number(0);
            Integer idBs = new Integer(0);
            idSp = (Integer)valueChangeEvent.getNewValue();

            currRate = (Number)currencyRateBinding.getValue();

            //AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            //ViewObjectImpl gl_lines = am.getGlLines();
            //Row rw = gl_lines.getCurrentRow();
            // if (rw.getAttribute("GlAmtSp") != null && rw.getAttribute("GlCc") != null) {
            // System.out.println("#{pageFlowScope.GLBL_AMT_DIGIT}");
            if (resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString() != null) {
                // System.out.println("Entered into val chng listnr");
                //amtBs = amtSp.multiply(currRate);
                // System.out.println(idBs);
                Integer i = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
                baseAmountBinding.setValue(idBs);
                //  System.out.println("changed");
            } else {
                //   System.out.println("else");
            }

        }
    }


    /**
     *
     * @param valueChangeEvent
     */
    public void currencyChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        //System.out.println("Currency id is:"+valueChangeEvent.getNewValue().toString());

        Number currRate = new Number(0);
        Integer idSp = new Integer(0);
        Integer idBs = new Integer(0);
        adfLog.info("currencyChangeListener");
        if (valueChangeEvent.getNewValue() != null) {
            idSp = (Integer)currIdSpBinding.getValue();
            adfLog.info("Specific Currency Id New : " + idSp);
            OperationBinding currencyOB = getBindings().getOperationBinding("getCurrency");
            currencyOB.getParamsMap().put("currIdSpDescVCE", valueChangeEvent.getNewValue());
            currencyOB.execute();
            currRate = (Number)currencyOB.getResult();
            Number amtSp = new Number(0);
            Number amtBs = new Number(0);
            if (specificAmountItemBinding.getValue() != null) {
                amtSp = (Number)specificAmountItemBinding.getValue();

                adfLog.info("amtSp : " + amtSp);
                adfLog.info("currRate : " + currRate);
                setCurrencyRate(currRate);
                amtBs = amtSp.multiply(currRate);
                if (ispositiveNum(amtBs)) {
                    //if(isPrecisionValid(26, 6, amtBs))
                    adfLog.info("amtBs : " + amtBs);
                    baseAmountBinding.setValue(amtBs.round(2));
                } else {
                    /*  showFacesMessage("MSG.954", "E", true, "V",
                                     currIdSpBinding.getClientId()); //"Number must positive and of valid precision (26,6)" */
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.954']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(currIdSpBinding.getClientId(), message);

                }
                adfLog.info("currRate" + currRate);
                baseAmountBinding.setValue(idBs);

            }
        }
    }


    /**
     * @Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void currencyRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("currencyRateValidator : " + object + facesContext);
        if (object != null) {

        } else {
            showFacesMessage("MSG.951", "E", true, "V", uIComponent.getClientId()); //This field is Mandatory
        }

    }


    /**
     *
     * @param valueChangeEvent
     */
    public void currencyRateChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        adfLog.info("currencyRateChangeListener");
        if (valueChangeEvent.getNewValue() != null) {
            Number amtSp = new Number(0);
            Number currRate = new Number(0);
            Number amtBs = new Number(0);
            amtSp = (Number)currIdSpBinding.getValue();
            adfLog.info("amtSp : " + amtSp);
            currRate = (Number)valueChangeEvent.getNewValue();
            adfLog.info("currRate : " + currRate);
            setCurrencyRate(currRate);
            amtBs = amtSp.multiply(currRate);
            adfLog.info("amtBs : " + amtBs);
            baseAmountBinding.setValue(amtBs.round(2));
        }
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }


    public void setCurrencyRate(Number currencyRate) {
        this.currencyRate = currencyRate;
    }

    public Number getCurrencyRate() {
        return currencyRate;
    }

    public void itemPriceChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            if ((itemQuantityBinding.getValue() != null) && (currRateItmBinding.getValue() != null)) {
                itemPrice = (Number)valueChangeEvent.getNewValue();
                itemQuantity = (Number)itemQuantityBinding.getValue();
                currencyRate = (Number)currRateItmBinding.getValue();
                if (isPrecisionValid(26, 6, itemPrice) && isPrecisionValid(26, 6, itemQuantity) &&
                    itemQuantityBinding.getValue() != null && itemPrice != null) {
                    System.out.println("Item quantity-->" + itemQuantity);
                    System.out.println("Item Price----->" + itemPrice);
                    totalSpecificAmount = ((itemPrice.multiply(itemQuantity)).multiply(currencyRate));
                    System.out.println("totalSpecificAmount--->" + totalSpecificAmount);
                    if (isPrecisionValid(26, 6, totalSpecificAmount)) {
                        itmpriceusevalidator = true;

                        specificAmountItemBinding.setValue(itemPrice.multiply(itemQuantity));
                        baseAmtBinding.setValue(totalSpecificAmount);

                        //totalAmountBind.setValue(itemPrice.multiply(itemQuantity));

                        // totalAmountBaseBind.setValue(totalSpecificAmount);


                    } else {
                        //  showFacesMessage("MSG.1133", "E", true, "F", itemPriceBinding.getClientId());

                        itmpriceusevalidator = false;

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1133']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(itemPriceBinding.getClientId(), message);
                    }
                    //System.out.println(specificAmountItemBinding.getValue());
                }

            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);

    }


    public void itemQtyValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        //   OperationBinding chkItmVar = getBindings().getOperationBinding("chkItm");
        //    chkItmVar.execute();
        //  String var=null;
        //if (chkItmVar!=null) {
        //    var = chkItmVar.getResult().toString();
        // }

        //     System.out.println("Value of VAr is-->"+var.toString());

        if (valueChangeEvent.getNewValue() != null) {

            if ((itemPriceBinding.getValue() != null) && (currRateItmBinding.getValue() != null)) {
                itemPrice = (Number)itemPriceBinding.getValue();
                itemQuantity = (Number)valueChangeEvent.getNewValue();
                currencyRate = (Number)currRateItmBinding.getValue();
                System.out.println("if va equals N --->itemPrice----" + itemPrice + "----itemQuantity-----" +
                                   itemQuantity + "-----currencyRate----" + currencyRate);

                if (isPrecisionValid(26, 6, itemPrice) && isPrecisionValid(26, 6, itemQuantity)) {
                    totalSpecificAmount = ((itemPrice.multiply(itemQuantity)).multiply(currencyRate));
                    if (isPrecisionValid(26, 6, totalSpecificAmount)) {

                        quantityusevalidato = true;

                        specificAmountItemBinding.setValue(itemPrice.multiply(itemQuantity));
                        baseAmtBinding.setValue(totalSpecificAmount);
                    } else {

                        quantityusevalidato = false;
                        //showFacesMessage("MSG.1133", "E", true, "F", itemQuantityBinding.getClientId());

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1133']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(itemQuantityBinding.getClientId(), message);
                    }
                    // System.out.println(specificAmountItemBinding.getValue());
                }

            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);


        if (valueChangeEvent.getNewValue() != null) {

            if ((itemPriceBinding.getValue() != null) && (currRateItmBinding.getValue() != null)) {
                itemPrice = (Number)itemPriceBinding.getValue();
                itemQuantity = (Number)valueChangeEvent.getNewValue();
                currencyRate = (Number)currRateItmBinding.getValue();
                System.out.println("else ---->itemPrice----" + itemPrice + "----itemQuantity-----" + itemQuantity +
                                   "-----currencyRate----" + currencyRate);

                if (isPrecisionValid(26, 6, itemPrice) && isPrecisionValid(26, 6, itemQuantity)) {
                    totalSpecificAmount = ((itemPrice.multiply(itemQuantity)).multiply(currencyRate));
                    if (isPrecisionValid(26, 6, totalSpecificAmount)) {

                        quantityusevalidato = true;

                        specificAmountItemBinding.setValue(itemPrice.multiply(itemQuantity));
                        baseAmtBinding.setValue(totalSpecificAmount);
                    } else {

                        quantityusevalidato = false;
                        //showFacesMessage("MSG.1133", "E", true, "F", itemQuantityBinding.getClientId());

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1133']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(itemQuantityBinding.getClientId(), message);
                    }
                    // System.out.println(specificAmountItemBinding.getValue());
                }

            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);


    }

    public void currRateVCListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            if ((itemPriceBinding.getValue() != null) && (itemQuantityBinding.getValue() != null)) {
                itemPrice = (Number)itemPriceBinding.getValue();
                itemQuantity = (Number)valueChangeEvent.getNewValue();
                currencyRate = (Number)currRateItmBinding.getValue();
                totalSpecificAmount = (itemPrice.multiply(itemQuantity)).multiply(currencyRate);
                specificAmountItemBinding.setValue(itemPrice.multiply(itemQuantity));
                baseAmtBinding.setValue(totalSpecificAmount);
            }

        }

    }


    public void itmAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("itmAmtSpValidator : " + object + facesContext);
        if (object != null) {
            Number tot = (Number)object;
            if (this.isPrecisionValid(26, 6, tot)) {

            } else {
                showFacesMessage("MSG.958", "E", true, "V", uIComponent.getClientId()); //Incorrect Precision
            }
        }
    }


    /* public void currencyIdSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String msg ="MSG.959";//Please Enter the value
        if (object != null) {
            adfLog.info("currencyIdSpValidator :" + object + facesContext);
        } else {
            showFacesMessage(msg, "E", true, "V", uIComponent.getClientId());
        }

    } */


    public void eoIdTransValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        /* DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("SlsOppIterator"); */
        /*
        System.out.println("validator object :" + object);
            //if (modeOpp.equalsIgnoreCase("A")) {
            if (object != null) {
                String eoIdTransVal = object.toString();
                Timestamp docDtOpp=(Timestamp)docDtBinding.getValue();
                System.out.println("eoIdTransVal :"+eoIdTransVal);
                System.out.println(docDtOpp);
                OperationBinding ob = getBindings().getOperationBinding("getDuplicate");
                ob.getParamsMap().put("strEoId", eoIdTransVal);
                ob.getParamsMap().put("docDtOpp", docDtOpp);
                ob.execute();
                System.out.println("result"+ob.getResult());
                String value = ob.getResult().toString();
                if (value.equalsIgnoreCase("Y")) {
                    //validation message 1
                    String msg = "Duplicate Entity within the same category ";
                    FacesMessage facesMsg = new FacesMessage(msg);
                    facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                ****    facesContext.addMessage(eoIdOppBinding.getClientId(), facesMsg);
                }
            }else{

            }  */
        adfLog.info("value :" + object + facesContext);
        String msg = "MSG.960"; //This field is Mandatory to fill ,You can't left this field blank or null
        //  System.out.println("value :" + object);
        if (object != null) {
            adfLog.info("value :" + object);
        } else {
            adfLog.info("value :" + object);
            showFacesMessage(msg, "E", true, "V", uIComponent.getClientId());
        }
    }


    public void assignedTypValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            setreadOnlyAssigned(false);

            // assignedToBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(assignedToBinding);

        } else {
            setreadOnlyAssigned(true);
        }
        System.out.println("Invcl__________________________");
    }


    /**
     *
     * @param data
     * @return
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    /*  public String createItemForOppAction() {
         // Add event code here...
             setModeItm("A");
             //BindingContainer bindings = getBindings();
             System.out.println("create Item");
             OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
             operationBinding.execute();
             return null;

     } */

    /**
     * getters and setters(Accessors)
     *  =================================================>
     *
     *
     */

    /*  public void setAssignedToBinding(RichSelectOneChoice assignedToBinding) {
        this.assignedToBinding = assignedToBinding;
    }

    public RichSelectOneChoice getAssignedToBinding() {
        return assignedToBinding;
    } */


    public void setEoNameBinding(RichInputText eoNameBinding) {
        this.eoNameBinding = eoNameBinding;
    }

    public RichInputText getEoNameBinding() {
        return eoNameBinding;
    }

    public void setEoTypeBinding(RichSelectOneChoice eoTypeBinding) {
        this.eoTypeBinding = eoTypeBinding;
    }

    public RichSelectOneChoice getEoTypeBinding() {
        return eoTypeBinding;
    }

    public void setCreateCustomerPopup(RichPopup createCustomerPopup) {
        this.createCustomerPopup = createCustomerPopup;
    }

    public RichPopup getCreateCustomerPopup() {
        return createCustomerPopup;
    }


    public void setModeOpp(String modeOpp) {
        this.modeOpp = modeOpp;
    }

    public String getModeOpp() {
        return modeOpp;
    }

    public void setModeItm(String modeItm) {
        this.modeItm = modeItm;
    }

    public String getModeItm() {
        return modeItm;
    }

    public void setSearchString(RichInputText searchString) {
        this.searchString = searchString;
    }

    public RichInputText getSearchString() {
        return searchString;
    }

    public void setLovBinding(RichSelectOneChoice lovBinding) {
        this.lovBinding = lovBinding;
    }

    public RichSelectOneChoice getLovBinding() {
        return lovBinding;
    }

    public void setCurrIdSpBinding(RichInputText currIdSpBinding) {
        this.currIdSpBinding = currIdSpBinding;
    }

    public RichInputText getCurrIdSpBinding() {
        return currIdSpBinding;
    }


    public void setBaseAmountBinding(RichInputText baseAmountBinding) {
        this.baseAmountBinding = baseAmountBinding;
    }

    public RichInputText getBaseAmountBinding() {
        return baseAmountBinding;
    }

    public void setCurrencyRateBinding(RichInputText currencyRateBinding) {
        this.currencyRateBinding = currencyRateBinding;
    }

    public RichInputText getCurrencyRateBinding() {
        return currencyRateBinding;
    }

    public void setCreateNewItmPopup(RichDialog createNewItmPopup) {
        this.createNewItmPopup = createNewItmPopup;
    }

    public RichDialog getCreateNewItmPopup() {
        return createNewItmPopup;
    }

    public void setItmDescBinding(RichInputText itmDescBinding) {
        this.itmDescBinding = itmDescBinding;
    }

    public RichInputText getItmDescBinding() {
        return itmDescBinding;
    }

    public void setCreateNewItemPopup(RichPopup createNewItemPopup) {
        this.createNewItemPopup = createNewItemPopup;
    }

    public RichPopup getCreateNewItemPopup() {
        return createNewItemPopup;
    }

    public void setItmPrefixBinding(RichInputText itmPrefixBinding) {
        this.itmPrefixBinding = itmPrefixBinding;
    }

    public RichInputText getItmPrefixBinding() {
        return itmPrefixBinding;
    }

    public void setCurrRateItmBinding(RichInputText currRateItmBinding) {
        this.currRateItmBinding = currRateItmBinding;
    }

    public RichInputText getCurrRateItmBinding() {
        return currRateItmBinding;
    }

    public void setItemPriceBinding(RichInputText itemPriceBinding) {
        this.itemPriceBinding = itemPriceBinding;
    }

    public RichInputText getItemPriceBinding() {
        return itemPriceBinding;
    }

    public void setItemQuantityBinding(RichInputText itemQuantityBinding) {
        this.itemQuantityBinding = itemQuantityBinding;
    }

    public RichInputText getItemQuantityBinding() {
        return itemQuantityBinding;
    }

    public void setSpecificAmountItemBinding(RichInputText specificAmountItemBinding) {
        this.specificAmountItemBinding = specificAmountItemBinding;
    }

    public RichInputText getSpecificAmountItemBinding() {
        return specificAmountItemBinding;
    }

    public void setItemQuantity(Number itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Number getItemQuantity() {
        return itemQuantity;
    }

    public void setItemPrice(Number itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Number getItemPrice() {
        return itemPrice;
    }

    public void setTotalSpecificAmount(Number totalSpecificAmount) {
        this.totalSpecificAmount = totalSpecificAmount;
    }

    public Number getTotalSpecificAmount() {
        return totalSpecificAmount;
    }

    public void setreadOnlyAssigned(Boolean readOnlyAssigned) {
        this.readOnlyAssigned = readOnlyAssigned;
    }

    public Boolean getreadOnlyAssigned() {
        return readOnlyAssigned;
    }

    public void setBaseAmtBinding(RichInputText baseAmtBinding) {
        this.baseAmtBinding = baseAmtBinding;
    }

    public RichInputText getBaseAmtBinding() {
        return baseAmtBinding;
    }

    public void setIsButtonEnb(Boolean isButtonEnb) {
        this.isButtonEnb = isButtonEnb;
    }

    private Boolean isButtonDisb;

    public Boolean getIsButtonEnb() {
        if (modeOpp.equalsIgnoreCase("V")) {
            setRenderOpp("single");
            return true;

        } else if (modeOpp.equalsIgnoreCase("A")) {
            setRenderOpp("none");
            return false;

        } else if (modeOpp.equalsIgnoreCase("E")) {
            setRenderOpp("none");
            return false;
        }

        return false;

    }

    public void setIsButtonDisb(Boolean isButtonDisb) {
        this.isButtonDisb = isButtonDisb;
    }

    public Boolean getIsButtonDisb() {
        return isButtonDisb;
    }


    public void setRenderOpp(String renderOpp) {
        this.renderOpp = renderOpp;
    }

    public String getRenderOpp() {
        return renderOpp;
    }

    public void setMainTabBinding(RichTable mainTabBinding) {
        this.mainTabBinding = mainTabBinding;
    }

    public RichTable getMainTabBinding() {
        return mainTabBinding;
    }

    public void setItemTabBinding(RichTable itemTabBinding) {
        this.itemTabBinding = itemTabBinding;
    }

    public RichTable getItemTabBinding() {
        return itemTabBinding;
    }


    public void setDocDtBinding(RichInputDate docDtBinding) {
        this.docDtBinding = docDtBinding;
    }

    public RichInputDate getDocDtBinding() {
        return docDtBinding;
    }


    public void setFinancialYrBinding(RichInputText financialYrBinding) {
        this.financialYrBinding = financialYrBinding;
    }

    public RichInputText getFinancialYrBinding() {
        return financialYrBinding;
    }

    public void setDocDtSearchBind(RichInputDate docDtSearchBind) {
        this.docDtSearchBind = docDtSearchBind;
    }

    public RichInputDate getDocDtSearchBind() {
        return docDtSearchBind;
    }

    public void setCurrIdSearchBind(RichSelectOneChoice currIdSearchBind) {
        this.currIdSearchBind = currIdSearchBind;
    }

    public RichSelectOneChoice getCurrIdSearchBind() {
        return currIdSearchBind;
    }

    public void setEoNmSearchBind(RichSelectOneChoice eoNmSearchBind) {
        this.eoNmSearchBind = eoNmSearchBind;
    }

    public RichSelectOneChoice getEoNmSearchBind() {
        return eoNmSearchBind;
    }

    public void setNoOfItemsBind(RichInputText noOfItemsBind) {
        this.noOfItemsBind = noOfItemsBind;
    }

    public RichInputText getNoOfItemsBind() {
        return noOfItemsBind;
    }

    public void setTotOppAmtSearchBind(RichInputText totOppAmtSearchBind) {
        this.totOppAmtSearchBind = totOppAmtSearchBind;
    }

    public RichInputText getTotOppAmtSearchBind() {
        return totOppAmtSearchBind;
    }

    public void setCategorySearchBind(RichSelectOneChoice categorySearchBind) {
        this.categorySearchBind = categorySearchBind;
    }

    public RichSelectOneChoice getCategorySearchBind() {
        return categorySearchBind;
    }

    public String searchAction() {
        // Add event code here...
        Timestamp fromDate = null;
        Timestamp toDate = null;
        Integer currId = null;
        String eoNm = null;
        Number noOfItems = null;
        Number totOppAmt = null;
        Integer category = null;
        String docId = null;

        /*   if (fromDateBinding.getValue() != null) {
            fromDate = (Timestamp)fromDateBinding.getValue();
        }
        if (toDateBinding.getValue() != null) {
            toDate = (Timestamp)toDateBinding.getValue();
        }
        if ((currIdSearchBind.getValue() != null) && (currIdSearchBind.getValue() != "")) {
            currId = Integer.parseInt(currIdSearchBind.getValue().toString());
        }
        if ((eoNmBindValue.getValue() != null) && (eoNmBindValue.getValue() != "")) {
            eoNm = eoNmBindValue.getValue().toString();
        }
        if ((noOfItemsBind.getValue() != null) && (noOfItemsBind.getValue() != "")) {
            noOfItems = (Number)noOfItemsBind.getValue();
        }
        if ((totOppAmtSearchBind.getValue() != null) && (totOppAmtSearchBind.getValue() != "")) {
            totOppAmt = (Number)totOppAmtSearchBind.getValue();
        }
        if ((categorySearchBind.getValue() != null) && (categorySearchBind.getValue() != "")) {
            category = Integer.parseInt(categorySearchBind.getValue().toString());
        } */
        /* if ((docIdSearchBindVar.getValue() != null) && (docIdSearchBindVar.getValue() != "")) {
            docId = docIdSearchBindVar.getValue().toString();
        } */
        OperationBinding searchOB = getBindings().getOperationBinding("getSearchOpportunity");
        searchOB.getParamsMap().put("fromDt", fromDate);
        searchOB.getParamsMap().put("toDt", toDate);
        searchOB.getParamsMap().put("currId", currId);
        searchOB.getParamsMap().put("eoNm", eoNm);
        searchOB.getParamsMap().put("noOfItems", noOfItems);
        searchOB.getParamsMap().put("totOppAmt", totOppAmt);
        searchOB.getParamsMap().put("category", category);
        searchOB.getParamsMap().put("docId", docId);
        searchOB.execute();
        return null;
    }

    public String resetAction() {
        // Add event code here...getResetOpportunity
        OperationBinding resetOB = getBindings().getOperationBinding("getResetOpportunity");
        resetOB.execute();
        return null;
    }

    public void setDocIdSearchBind(RichSelectOneChoice docIdSearchBind) {
        this.docIdSearchBind = docIdSearchBind;
    }

    public RichSelectOneChoice getDocIdSearchBind() {
        return docIdSearchBind;
    }

    public String backToSearchAction() {
        // Add event code here...
        /* OperationBinding resetOB = getBindings().getOperationBinding("getResetOpportunity");
        resetOB.execute(); */
        view = "N";
        lostMode = true;

        OperationBinding resetOB = getBindings().getOperationBinding("Rollback");
        resetOB.execute(); //Execute2

        OperationBinding resOppOB = getBindings().getOperationBinding("getResOpportunity");
        resOppOB.getParamsMap().put("docTxnIdRes", "R");
        resOppOB.execute();
        setModeOpp("V");
        setModeItm("V");
        editmode = "V";
        return "backToSeachOpp";
    }

    public void setFromDateBinding(RichInputDate fromDateBinding) {
        this.fromDateBinding = fromDateBinding;
    }

    public RichInputDate getFromDateBinding() {
        return fromDateBinding;
    }

    public void setToDateBinding(RichInputDate toDateBinding) {
        this.toDateBinding = toDateBinding;
    }

    public RichInputDate getToDateBinding() {
        return toDateBinding;
    }

    public void categoryValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        // System.out.println("value :" + object);
        adfLog.info("value :" + object);
        if (object != null) {
            adfLog.info("value :" + object);
        } else {
            adfLog.info("value :" + object);
            FacesMessage message =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.960']}").toString()); //This field is Mandatory to fill ,You can't left this field blank or null
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(uIComponent.getClientId(), message);
        }
    }

    public void setCatgIdBinding(RichSelectOneChoice catgIdBinding) {
        this.catgIdBinding = catgIdBinding;
    }

    public RichSelectOneChoice getCatgIdBinding() {
        return catgIdBinding;
    }


    public String goToOppViewAction() {
        // Add event code here...
        adfLog.info("goToOppViewAction");
        view = "Y";
        OperationBinding filterOppOB = getBindings().getOperationBinding("getFilteredOpportunity");
        filterOppOB.execute();
        setModeOpp("V");
        lostMode = true;
        return "viewOpp";
    }

    public void ItemNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("u are in validator of name:-==-=-=------");
            RichInputListOfValues u = (RichInputListOfValues)uIComponent;
            //  if (u.isValid()) {
            System.out.println("if part of validator---------");
            OperationBinding itemDupOb = getBindings().getOperationBinding("itemExist");
            itemDupOb.getParamsMap().put("item", object.toString());
            itemDupOb.execute();
            Object res = itemDupOb.getResult();
            System.out.println("result is: " + res);
            //adfLog.info("Return" + res);
            if ("Y".equals(res)) {
                System.out.println("Quantity after adding dupliocate item=-=-===" + itemQuantity.getValue());
                // String resStr = res.toString();
                showFacesMessage("MSG.1319", "E", true, "V", uIComponent.getClientId());
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.46']}").toString(), null));
            }
            /*  } else {
                System.out.println("else part of validddd=============");
                // showFacesMessage("Invalid Value !", "E", true, "V", uIComponent.getClientId());
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.253']}").toString(), null));
            } */
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(pf3Binding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPriceBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemQuantityBinding);
    }

    public boolean ispositiveNum(Number num) {
        Number zero = new Number(0);
        if (num.compareTo(zero) == -1) {
            if (isPrecisionValid(26, 6, num)) {
                return true;
            } else {
                String Msg = "Precision Invalid";
                return false;
            }
        }
        return false;
    }

    public void setExptdPriceBinding(RichInputText exptdPriceBinding) {
        this.exptdPriceBinding = exptdPriceBinding;
    }

    public RichInputText getExptdPriceBinding() {
        return exptdPriceBinding;
    }

    public void exptdPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {

            Number num = (Number)object;
            if (num.compareTo(zero) == -1 || num.compareTo(0) == 0) {
                showFacesMessage("MSG.382", "E", true, "V",
                                 uIComponent.getClientId()); //Amount Must be greater than zero

            } else {
                if (isPrecisionValid(26, 6, num)) {

                } else {
                    showFacesMessage("MSG.1062", "E", true, "V",
                                     uIComponent.getClientId()); //Invalid Precision.Please put the corrert precision.
                }

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);
    }

    public void itemPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        adfLog.info("itemPriceValidator");
        // Add event code here...
        if (object != null) {
            adfLog.info("object is not null");
            Number num = (Number)object;
            if (num.compareTo(zero) == -1 || num.compareTo(0) == 0) {
                adfLog.info("if zero or less ");
                showFacesMessage("MSG.1063", "E", true, "V",
                                 uIComponent.getClientId()); //Value Must be positive Number


            } else {
                adfLog.info("else to check Precision");
                if (isPrecisionValid(26, 6, num)) {

                    /* if(itmpriceusevalidator){

                    }else{
                        showFacesMessage("MSG.1133", "E", true, "V",
                                         uIComponent.getClientId());
                    } */


                } else {
                    showFacesMessage("MSG.1062", "E", true, "V",
                                     uIComponent.getClientId()); //Invalid Precision.Please put the corrert precision.
                }

            }
        }
    }

    public void quantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("quantityValidator :");
        if (object != null) {
            adfLog.info("Object :" + object);
            Number num = (Number)object;
            if (num.compareTo(zero) == -1 || num.compareTo(zero) == 0) {
                adfLog.info("quantityValidator :");
                showFacesMessage("MSG.1063", "E", true, "V",
                                 uIComponent.getClientId()); //Value Must be greater than Zero .

            } else {
                if (isPrecisionValid(26, 6, num)) { //edit for precision on 5th may 2014
                    /* if(quantityusevalidato){

                    }else{
                        showFacesMessage("MSG.1133", "E", true, "V",
                                         uIComponent.getClientId());
                    } */

                } else {
                    showFacesMessage("MSG.1062", "E", true, "V",
                                     uIComponent.getClientId()); //Invalid Precision.Please put the corrert precision.
                }

            }
        } else {
            adfLog.info("Object :" + object);
            showFacesMessage("MSG.1064", "E", true, "V", uIComponent.getClientId()); //Item Quantity is Required.
        }
    }

    public void setAssignedTypeBinding(RichSelectOneRadio assignedTypeBinding) {
        this.assignedTypeBinding = assignedTypeBinding;
    }

    public RichSelectOneRadio getAssignedTypeBinding() {
        return assignedTypeBinding;
    }


    public void setDocHexaIdBinding(RichInputText docHexaIdBinding) {
        this.docHexaIdBinding = docHexaIdBinding;
    }

    public RichInputText getDocHexaIdBinding() {
        return docHexaIdBinding;
    }

    public void setAddItemBinding(RichCommandImageLink addItemBinding) {
        this.addItemBinding = addItemBinding;
    }

    public RichCommandImageLink getAddItemBinding() {
        return addItemBinding;
    }

    public void setEditItmBinding(RichCommandImageLink editItmBinding) {
        this.editItmBinding = editItmBinding;
    }

    public RichCommandImageLink getEditItmBinding() {
        return editItmBinding;
    }

    public void setDispDocIdBinding(RichInputText dispDocIdBinding) {
        this.dispDocIdBinding = dispDocIdBinding;
    }

    public RichInputText getDispDocIdBinding() {
        return dispDocIdBinding;
    }

    public String createOppFromSearch() {
        // Add event code here...
        setModeOpp("A");
        customername = true;

        lostMode = true;
        currencydisable = false;

        return "createOpp";
    }

    public void setItemUomBinding(RichSelectOneChoice itemUomBinding) {
        this.itemUomBinding = itemUomBinding;
    }

    public RichSelectOneChoice getItemUomBinding() {
        return itemUomBinding;
    }

    public void setEditmode(String editmode) {
        this.editmode = editmode;
    }

    public String getEditmode() {
        return editmode;
    }

    public void FromDateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            toDateBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toDateBinding);
        }
    }


    public void CategoryNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null || vce.getNewValue().toString().length() == 0) {

            OperationBinding ob = getBindings().getOperationBinding("setCustomerNameOnVCE");
            ob.execute();
            /*  eoIdOppBinding.setValue(null);
             AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdOppBinding); */


            currencyBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(currencyBinding);
            //eoIdOppBinding.setPartialTriggers("soc5");
            customername = false;

        } else if (vce.getNewValue() == "") {
            customername = true;
        } else if (vce.getNewValue().toString().length() > 0) {
            /*  eoIdOppBinding.setValue(null);
             AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdOppBinding); */
            OperationBinding ob = getBindings().getOperationBinding("setCustomerNameOnVCE");
            ob.execute();

            currencyBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(currencyBinding);
        }

    }

    public void setCustomername(boolean customername) {
        this.customername = customername;
    }

    public boolean isCustomername() {
        return customername;
    }

    public void expectedDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date ed = null;
            Date od = null;

            ed = (Date)object;
            od = (Date)docDtBinding.getValue();


            System.out.println("In validator");
            if (od != null) {
                // System.out.println("Inside Ed Validator of Od Loop");

                if (ed.compareTo(od) == -1) {
                    // System.out.println("Inside date");
                    System.out.println("mSG 1259" + 1259);
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.1259']}").toString(),
                                                                  null));


                }

            }
        }
    }


    public void setCurrencydisable(boolean currencydisable) {
        this.currencydisable = currencydisable;
    }

    public boolean isCurrencydisable() {
        return currencydisable;
    }


    public void CustomerNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle
        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*"; //String with space in middle and end
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);


        if (matcher.matches()) {
        } else {


            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.1056']}").toString(),
                                                          null)); //Special Character Not Allowed
        }
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }


    public void popUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        eoNameBinding.setValue(null);
        eoNameBinding.resetValue();
    }

    public void setCurrencyBinding(RichInputText currencyBinding) {
        this.currencyBinding = currencyBinding;
    }

    public RichInputText getCurrencyBinding() {
        return currencyBinding;
    }


    /*  public void setDocIdSearchBindVar(RichInputListOfValues docIdSearchBindVar) {
        this.docIdSearchBindVar = docIdSearchBindVar;
    }

    public RichInputListOfValues getDocIdSearchBindVar() {
        return docIdSearchBindVar;
    } */

    public void setEoNmSearchBindVar(RichInputListOfValues eoNmSearchBindVar) {
        this.eoNmSearchBindVar = eoNmSearchBindVar;
    }

    public RichInputListOfValues getEoNmSearchBindVar() {
        return eoNmSearchBindVar;
    }

    public void setEoNmBindValue(RichInputListOfValues eoNmBindValue) {
        this.eoNmBindValue = eoNmBindValue;
    }

    public RichInputListOfValues getEoNmBindValue() {
        return eoNmBindValue;
    }

    public void setTotalAmountBind(RichInputText totalAmountBind) {
        this.totalAmountBind = totalAmountBind;
    }

    public RichInputText getTotalAmountBind() {
        return totalAmountBind;
    }

    public void setTotalAmountBaseBind(RichInputText totalAmountBaseBind) {
        this.totalAmountBaseBind = totalAmountBaseBind;
    }

    public RichInputText getTotalAmountBaseBind() {
        return totalAmountBaseBind;
    }

    public void setTotalAmountSPFooterBind(RichOutputText totalAmountSPFooterBind) {
        this.totalAmountSPFooterBind = totalAmountSPFooterBind;
    }

    public RichOutputText getTotalAmountSPFooterBind() {
        return totalAmountSPFooterBind;
    }

    public void setTotalAmountBSFooterBind(RichOutputText totalAmountBSFooterBind) {
        this.totalAmountBSFooterBind = totalAmountBSFooterBind;
    }

    public RichOutputText getTotalAmountBSFooterBind() {
        return totalAmountBSFooterBind;
    }

    public void setLostCheckStatusBind(RichSelectBooleanCheckbox lostCheckStatusBind) {
        this.lostCheckStatusBind = lostCheckStatusBind;
    }

    public RichSelectBooleanCheckbox getLostCheckStatusBind() {
        return lostCheckStatusBind;
    }

    public void LostStatusVL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Status is --->" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().equals(true)) {

            OperationBinding ob = getBindings().getOperationBinding("setLostStatus");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lovStatusBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lostRemarkbind);
        } else {
            OperationBinding ob = getBindings().getOperationBinding("setStatus");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lovStatusBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lostRemarkbind);
        }
    }

    public void setLovStatusBind(RichSelectOneChoice lovStatusBind) {
        this.lovStatusBind = lovStatusBind;
    }

    public RichSelectOneChoice getLovStatusBind() {
        return lovStatusBind;
    }

    public void setLostMode(boolean lostMode) {
        this.lostMode = lostMode;
    }

    public boolean getLostMode() {
        return lostMode;
    }

    public void setLostRemarkbind(RichInputText lostRemarkbind) {
        this.lostRemarkbind = lostRemarkbind;
    }

    public RichInputText getLostRemarkbind() {
        return lostRemarkbind;
    }

    public void setAssignedToBinding(RichInputListOfValues assignedToBinding) {
        this.assignedToBinding = assignedToBinding;
    }

    public RichInputListOfValues getAssignedToBinding() {
        return assignedToBinding;
    }

    public void setDecimalVal(String decimalVal) {
        this.decimalVal = decimalVal;
    }

    public String getDecimalVal() {
        return decimalVal;
    }

    public void setItemaddBinding(RichCommandImageLink itemaddBinding) {
        this.itemaddBinding = itemaddBinding;
    }

    public RichCommandImageLink getItemaddBinding() {
        return itemaddBinding;
    }

    public void AssignedToVL(ValueChangeEvent valueChangeEvent) {
        /* OperationBinding ob = getBindings().getOperationBinding("setSalesManName");
        ob.execute(); */
    }

    public void setDcIdBind(RichInputComboboxListOfValues dcIdBind) {
        this.dcIdBind = dcIdBind;
    }

    public RichInputComboboxListOfValues getDcIdBind() {
        return dcIdBind;
    }

    public void addDcButtonAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("addDailyCall").execute();
        getBindings().getOperationBinding("Execute5").execute();


    }

    public void setEoIdTransBind(RichInputComboboxListOfValues eoIdTransBind) {
        this.eoIdTransBind = eoIdTransBind;
    }

    public RichInputComboboxListOfValues getEoIdTransBind() {
        return eoIdTransBind;
    }

    public void dcIdDeleteAL(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("Delete1");
        binding.execute();
    }

    public void setDcallDcIdBind(RichInputListOfValues dcallDcIdBind) {
        this.dcallDcIdBind = dcallDcIdBind;
    }

    public RichInputListOfValues getDcallDcIdBind() {
        return dcallDcIdBind;
    }

    public void ItemIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding binding = getBindings().getOperationBinding("ValidateItmId");
        binding.getParamsMap().put("itmId", object.toString());
        Object execute = binding.execute();
        if (execute == null || execute.toString().equalsIgnoreCase("Y")) {
            FacesMessage msg = new FacesMessage("Item Already Exist");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    public StringBuffer getShowItmDesc() {
        System.out.println("ShowItmDesc: " + showItmDesc);
        if (showItmDesc == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("ENT_ITM_ID_IN_SO"));
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer)object;
                }
                if (i.toString().equals("Y")) {
                    showItmDesc = new StringBuffer("Y");
                } else {
                    showItmDesc = new StringBuffer("N");
                }
            }

        }
        return showItmDesc;
    }

    public void ItemIdValuechangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String itmId = vce.getNewValue().toString();
            OperationBinding ob = executeOperation("getItmpricefromitmId");
            ob.getParamsMap().put("itmid", itmId);
            ob.execute();
            Object res = ob.getResult();
            System.out.println("item id res==" + res);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pf3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPriceBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemQuantityBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemUomBinding);

        }
    }

    public void ItemNameValueChangeListner(ValueChangeEvent vce) {
        System.out.println("------------------------1");
        if (vce.getNewValue() != null) {
            System.out.println("------------------------2");
            System.out.println(" itm val chang list   " + vce.getNewValue());

            OperationBinding ob = getBindings().getOperationBinding("getMaxItemPrice");
            System.out.println("------------------------3");
            OperationBinding ob1 = getBindings().getOperationBinding("itemExist");
            System.out.println("------------------------4");
            ob1.getParamsMap().put("item", vce.getNewValue().toString());
            ob.getParamsMap().put("itmid", vce.getNewValue().toString());
            System.out.println("------------------------5");
            ob.execute();
            System.out.println("------------------------6");
            ob1.execute();
            System.out.println("------------------------7");
            Object res = ob.getResult();
            Object res1 = ob1.getResult();
            System.out.println("------------------------8");
            if (res1 != null) {
                System.out.println("------------------------9");
                String resStr = res1.toString();
                adfLog.info("return value    " + resStr);
                if (resStr.equalsIgnoreCase("N")) {
                    System.out.println("------------------------10");
                } else if (resStr.equalsIgnoreCase("Z")) {

                    System.out.println("------------------------11");
                    showFacesMessage("MSG.151", "E", true, "F", null);
                } else {
                    System.out.println("------------------------12");
                    showFacesMessage("MSG.46", "E", true, "F", null);
                }
                System.out.println("------------------------13");
            }
            //     Number itmqty = (Number)itemQuantityBinding.getValue();
            //     Number itmprice = (Number)itemPriceBinding.getValue();
            System.out.println("in Bean is :" + res);
            System.out.println("------------------------14");
            if (res != null) {
                System.out.println("------------------------15");
                if (res.equals(-1)) {
                    System.out.println("------------------------16");
                    itemPriceBinding.setValue(0);
                    itemQuantityBinding.setValue(0);
                    System.out.println("------------------------17");
                } else if (isPrecisionValid(26, 6, (Number)res)) {
                    itemPriceBinding.setValue(res);
                    itemQuantityBinding.setValue(0);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(pf3Binding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemPriceBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemQuantityBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTabBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemUomBinding);

            }
        }

    }

    public void setPf3Binding(RichPanelFormLayout pf3Binding) {
        this.pf3Binding = pf3Binding;
    }

    public RichPanelFormLayout getPf3Binding() {
        return pf3Binding;
    }

    public void exportAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        DCBindingContainer bindings2 = (DCBindingContainer)getBindings();
        JUCtrlHierBinding obj = (JUCtrlHierBinding)bindings2.findCtrlBinding("SlsOppItm");
        ViewObject vo = obj.getViewObject();
        RowSetIterator itr = vo.createRowSetIterator(null);
        System.out.println("Rows are: " + itr.getRowCount());


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < 8; i++) {
            Cell cell = createRow.createCell(i);
            // sheet.autoSizeColumn(i);
            // sheet.setColumnWidth(100+i,100);
            switch (i) {
            case 0:
                cell.setCellValue("ITM_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("REMARKS");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("ITM_UOM");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("QUANTITY");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("EXPECTED PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("ACTUAL PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("SPECIFIC AMOUNT");
                cell.setCellStyle(cellStyle);
                break;

            case 7:
                cell.setCellValue("BASE AMOUNT");
                cell.setCellStyle(cellStyle);
                break;

            }
        }
        int rownum = 1;
        while (itr.hasNext()) {
            Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            for (int i = 0; i <= 8; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0:
                    if (next.getAttribute("ItmId") != null) {
                        cell.setCellValue(next.getAttribute("ItmId").toString());
                    }
                    break;
                case 1:
                    if (next.getAttribute("Remarks") == null) {
                    }
                    //      cell.setCellValue(null);
                    else
                        cell.setCellValue(next.getAttribute("Remarks").toString());
                    break;
                case 2:
                    if (next.getAttribute("ItmUom") != null) {
                        cell.setCellValue(next.getAttribute("ItmUom").toString());
                    }
                    break;
                case 3:
                    if (next.getAttribute("ItmQty") != null) {
                        Double val1 = new Double(next.getAttribute("ItmQty").toString());
                        cell.setCellValue(val1);
                    }
                    break;
                case 4:
                    if (next.getAttribute("ItmExpPrice") == null) {
                    }
                    //  cell.setCellValue(null);
                    else {
                        Double val2 = new Double(next.getAttribute("ItmExpPrice").toString());
                        cell.setCellValue(val2);
                    }
                    break;
                case 5:
                    if (next.getAttribute("ItmPrice") != null) {
                        Double val3 = new Double(next.getAttribute("ItmPrice").toString());
                        cell.setCellValue(val3);
                    }
                    break;
                case 6:
                    if (next.getAttribute("ItmAmtSp") != null) {
                        Double val4 = new Double(next.getAttribute("ItmAmtSp").toString());
                        cell.setCellValue(val4);
                    }
                    break;
                case 7:
                    if (next.getAttribute("ItmAmtBs") != null) {
                        Double val5 = new Double(next.getAttribute("ItmAmtBs").toString());
                        cell.setCellValue(val5);
                    }
                    break;
                }
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadExcelAction() {
        if (currencyBinding.getValue() != null && currencyBinding.getValue() != "") {
            setModeItm("A");
            editmode = "C";
            return "upload_Excel";
        } else {
            FacesMessage message =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.21']}").toString()); //Currency is Required
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        System.out.println("Input Item value is---->"+input);
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>)object) {
                items.add(new SelectItem(item));
            }
        }
       

        // return the matching items
        return items;
    }

    public void setModeAfterExcelUpload() {
        // Add event code here...
    }

    public void setImportExcelLinkBind(RichCommandLink importExcelLinkBind) {
        this.importExcelLinkBind = importExcelLinkBind;
    }

    public RichCommandLink getImportExcelLinkBind() {
        return importExcelLinkBind;
    }
}
