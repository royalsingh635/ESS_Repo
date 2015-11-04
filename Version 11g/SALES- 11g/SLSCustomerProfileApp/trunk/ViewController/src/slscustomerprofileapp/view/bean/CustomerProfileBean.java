package slscustomerprofileapp.view.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
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
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import slscustomerprofileapp.model.services.CustomerProfileAppAMImpl;


public class CustomerProfileBean {


    private RichShowDetailItem bankBind;
    private RichShowDetailItem addsBind;
    private RichShowDetailItem prfBind;
    private RichSelectBooleanCheckbox actvBind;
    private RichInputDate inactvDtBind;
    private RichSelectBooleanCheckbox actvBankBind;
    private RichInputDate inactvDateBankBind;
    private RichOutputText eoidBind;
    private RichPopup popupbind;
    private RichTable tableBind;
    private RichInputText tableColBiind;
    private RichQuery searchbind;
    private RichInputText eoNmBind;
    private RichSelectBooleanCheckbox onHoldBInd;
    private RichSelectBooleanCheckbox blackListedBind;
    private RichInputText inactvRsnBind;
    private RichInputText inactvrsnBankBind;
    private RichSelectBooleanCheckbox onHoldPrflBind;
    private RichInputText onHoldRsnBind;
    private RichInputDate onHoldFrmDateBind;
    private RichInputDate onHoldToDateBind;
    private RichSelectBooleanCheckbox blackListBind;
    private RichInputText blackListRsn;
    private RichInputDate blackListFromDt;
    private RichInputDate blackListToDt;
    private RichInputListOfValues transEoNameBVar;
    private RichInputText crdaysPgBind;
    private RichInputText creditLimitPgBind;
    private RichInputText dlvDaysPgBind;
    private RichInputListOfValues addressPgBind;
    private RichSelectBooleanCheckbox dfltBillAddsCB;
    private RichSelectBooleanCheckbox billAddsCB;
    private RichSelectBooleanCheckbox shipAddressCB;
    private RichSelectBooleanCheckbox dfltShipAddressCB;
    private UploadedFile file;
    private String fileDisplayname = "";
    private RichInputFile attchDocBVar;
    private RichInputText fileNameBVar;
    private RichShowDetailItem psnlDtlTabBVar;
    private RichShowDetailItem attachDocTabBVar;
    private RichPopup prsnlDtlPopupBVar;
    private RichPopup prsnlDtlPopUpBVar;
    private RichPopup bankPopUpBVar;
    private RichTable bankTblBVar;
    private RichPopup deleteAttchFilePopUp;


    public CustomerProfileBean() {
        //  System.out.println("In bean");
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    private String Mode = "V";
    private String BankMode = "V";
    private String AddrsMode = "V";
    private String createMode = "V";

    // methods for profile

    public String editPrf() {
        Mode = "C";
        addsBind.setDisabled(true);
        bankBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        return null;
    }

    public String deletePrf() {
        showPopup(popupbind, true);
        return null;
    }

    public String savePrf() {


        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        int black = 1, hold = 1;
        System.out.println(blackListBind.getValue() + "-------" + blackListBind.getValue().toString());
        if (blackListBind.getValue().toString().equalsIgnoreCase("true")) {
            //System.out.println("in blacklist of save");


            java.sql.Date from = null;
            java.sql.Date to = null;
            java.util.Date from_dt = null;
            java.util.Date to_dt = null;
            try {
                Timestamp t1 = (Timestamp)blackListFromDt.getValue();
                Timestamp t2 = (Timestamp)blackListToDt.getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (t1 == null || t2 == null) {
                    black = 2;
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1053']}").toString()); //Enter the date
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(blackListToDt.getClientId(), message);

                } else {
                    from = t1.dateValue();
                    to = t2.dateValue();
                    from_dt = sdf.parse(from.toString());
                    to_dt = sdf.parse(to.toString());
                    if (from_dt.compareTo(to_dt) > 0) {
                        black = 2;

                        FacesMessage message =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1052']}").toString()); //To_Date must be greater than From_Date
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(blackListToDt.getClientId(), message);

                    }
                }
            } catch (SQLException e) {
                System.out.println("Eception in Date Conversion->" + e);
            } catch (ParseException e) {
            }

        }

        //System.out.println("@@@@@@@@@@@@@@@@" + onHoldPrflBind.getValue().toString());
        if (onHoldPrflBind.getValue().toString().equalsIgnoreCase("true")) {
            // System.out.println("in onhold of save profile");
            java.sql.Date from1 = null;
            java.sql.Date to1 = null;
            java.util.Date from_dt1 = null;
            java.util.Date to_dt1 = null;

            try {
                Timestamp t1 = (Timestamp)onHoldFrmDateBind.getValue();
                Timestamp t2 = (Timestamp)onHoldToDateBind.getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (t1 == null || t2 == null) {
                    hold = 2;
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1053']}").toString()); //Enter the date
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(onHoldToDateBind.getClientId(), message);

                } else {
                    from1 = t1.dateValue();
                    to1 = t2.dateValue();
                    from_dt1 = sdf.parse(from1.toString());
                    to_dt1 = sdf.parse(to1.toString());
                    if (from_dt1.compareTo(to_dt1) > 0) {
                        hold = 2;

                        FacesMessage message =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1052']}").toString()); //To_Date must be greater than From_Date
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(onHoldToDateBind.getClientId(), message);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in Date Conveersion->" + e);
            } catch (ParseException e) {
            }

        }
        if (black != 2 && hold != 2) {
            Mode = "V";
            addsBind.setDisabled(false);
            bankBind.setDisabled(false);
            attachDocTabBVar.setDisabled(false);
            psnlDtlTabBVar.setDisabled(false);
            tableBind.setRowSelection("single");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                String message1 = resolvElDCMsg("#{bundle['MSG.91']}").toString(); //Record Saved Successfully.
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                String message1 = resolvElDCMsg("#{bundle['MSG.660']}").toString(); //Please Select Supplier.
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }

        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("profileView");
        operationBinding.execute();

        return null;
    }

    public String cancelPrf() {

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        Mode = "V";
        addsBind.setDisabled(false);
        bankBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        psnlDtlTabBVar.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("profileView");
        operationBinding.execute();

        return null;
    }

    public String createPrf() {
        Mode = "C";
        addsBind.setDisabled(true);
        bankBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        return null;
    }

    // Methods for Banks

    public String editBank() {
        addsBind.setDisabled(true);
        prfBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        BankMode = "C";
        return null;
    }

    public String deleteBank() {
        /// BindingContainer bindings = getBindings();
        // OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
        showPopup(bankPopUpBVar, true);
        // operationBinding.execute();
        return null;
    }

    public String saveBank() {
        BankMode = "V";

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        addsBind.setDisabled(false);
        prfBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        psnlDtlTabBVar.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            String message1 = resolvElDCMsg("#{bundle['MSG.91']}").toString(); //Record Saved Successfully.
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            String message1 = resolvElDCMsg("#{bundle['MSG.660']}").toString(); //Please Select Supplier.
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("banksView");
        operationBinding.execute();


        return null;
    }

    public String cancelBank() {

        BankMode = "V";

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        addsBind.setDisabled(false);
        prfBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        psnlDtlTabBVar.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("banksView");
        operationBinding.execute();

        return null;
    }

    public String createBank() {
        BankMode = "C";
        addsBind.setDisabled(true);
        prfBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        return null;
    }


    // Method for Address

    public String saveAddrs() {
        AddrsMode = "V";

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();


        prfBind.setDisabled(false);
        bankBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        psnlDtlTabBVar.setDisabled(false);
        //   tableColBiind.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            String message1 = resolvElDCMsg("#{bundle['MSG.91']}").toString(); //Record Saved Successfully.
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            String message1 = resolvElDCMsg("#{bundle['MSG.660']}").toString(); //Please Select Supplier.
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("refreshAddressView");
        operationBinding.execute();

        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("addressView");
        operationBinding.execute();


        return null;
    }

    public String cancelAddrs() {
        AddrsMode = "V";

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("Customer1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();


        prfBind.setDisabled(false);
        bankBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        psnlDtlTabBVar.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("refreshAddressView");
        operationBinding.execute();


        bindings = getBindings();
        operationBinding = bindings.getOperationBinding("addressView");
        operationBinding.execute();

        return null;
    }

    public String deleteAddrs() {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete2");
        showPopup(popupbind, true);
        operationBinding.execute();
        return null;
    }

    public String editAddrs() {
        AddrsMode = "C";
        prfBind.setDisabled(true);
        bankBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        return null;
    }

    public String createAddrs() {
        AddrsMode = "C";
        prfBind.setDisabled(true);
        bankBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        psnlDtlTabBVar.setDisabled(true);
        tableBind.setRowSelection("none");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        return null;
    }

    // Method for popup

    public void deleteProfileDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            BindingContainer bindings = getBindings();
            OperationBinding dl = bindings.getOperationBinding("Delete");
            dl.execute();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            if (operationBinding.getErrors().isEmpty()) {
                String message1 = resolvElDCMsg("#{bundle['MSG.142']}").toString(); //Record deleted successfully

                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                String message1 =
                    resolvElDCMsg("#{bundle['MSG.662']}").toString(); //Purchase Order selected is not configured to be received in the selected Warehouse.
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        } else {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(addressPgBind);

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("refreshAddressView");
        operationBinding.execute();
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


    public void setMode(String Mode) {
        this.Mode = Mode;

    }

    public String getMode() {
        return Mode;
    }

    public void setBankMode(String BankMode) {
        this.BankMode = BankMode;

    }

    public String getBankMode() {
        return BankMode;
    }


    public void setAddrsMode(String AddrsMode) {

        this.AddrsMode = AddrsMode;

    }

    public String getAddrsMode() {
        return AddrsMode;
    }

    public void setCreateMode(String createMode) {
        this.createMode = createMode;
    }

    public String getCreateMode() {
        return createMode;
    }


    public void ActvVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(valueChangeEvent.getNewValue() + "-:new Value");
        if (valueChangeEvent.getNewValue().toString().equals("false")) {
            inactvDtBind.setValue(new Timestamp(System.currentTimeMillis()));
            // inactvDtBind.setReadOnly(true);

        } else {
            inactvDtBind.setValue(null);
            inactvRsnBind.setValue(null);
            // inactvDtBind.setReadOnly(true);
        }
    }

    public void ShipAddsDfltVCL(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("dfltprfl");
        operationBinding.execute();
    }


    public void BillAddrsDflt(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Billdfltprfl");
        operationBinding.execute();
    }


    public void setBankBind(RichShowDetailItem bankBind) {
        this.bankBind = bankBind;
    }

    public RichShowDetailItem getBankBind() {
        return bankBind;
    }

    public void setAddsBind(RichShowDetailItem addsBind) {
        this.addsBind = addsBind;
    }

    public RichShowDetailItem getAddsBind() {
        return addsBind;
    }

    public void setPrfBind(RichShowDetailItem prfBind) {
        this.prfBind = prfBind;
    }

    public RichShowDetailItem getPrfBind() {
        return prfBind;
    }

    public void setActvBind(RichSelectBooleanCheckbox actvBind) {
        this.actvBind = actvBind;
    }

    public RichSelectBooleanCheckbox getActvBind() {
        return actvBind;
    }

    public void setInactvDtBind(RichInputDate inactvDtBind) {
        this.inactvDtBind = inactvDtBind;
    }

    public RichInputDate getInactvDtBind() {
        return inactvDtBind;
    }

    public void ActvBankVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equals("false")) {
            inactvDateBankBind.setValue(new Timestamp(System.currentTimeMillis()));
            inactvDateBankBind.setReadOnly(true);
        } else {
            inactvDateBankBind.setValue(null);
            inactvrsnBankBind.setValue(null);
            inactvDateBankBind.setReadOnly(true);
        }
    }

    public void setActvBankBind(RichSelectBooleanCheckbox actvBankBind) {
        this.actvBankBind = actvBankBind;
    }

    public RichSelectBooleanCheckbox getActvBankBind() {
        return actvBankBind;
    }

    public void setInactvDateBankBind(RichInputDate inactvDateBankBind) {
        this.inactvDateBankBind = inactvDateBankBind;
    }

    public RichInputDate getInactvDateBankBind() {
        return inactvDateBankBind;
    }


    public void setEoidBind(RichOutputText eoidBind) {
        this.eoidBind = eoidBind;
    }

    public RichOutputText getEoidBind() {
        return eoidBind;
    }

    public void SpecialCharacterValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* String expression = "([a-zA-Z0-9]+)(([a-zA-Z0-9]+))*";
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = resolvElDCMsg("#{bundle['MSG.71']}").toString(); //No special characters allowed

        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
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

    public void bankNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String area = (String)object;


        String exp = "([a-zA-Z]+)(([ ])([a-zA-Z]+))*";

        CharSequence inputStr = area;

        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
        } else {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.679']}").toString(),
                                                          null)); //Invalid Input
        }

    }

    public void setPopupbind(RichPopup popupbind) {
        this.popupbind = popupbind;
    }

    public RichPopup getPopupbind() {
        return popupbind;
    }


    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setTableColBiind(RichInputText tableColBiind) {
        this.tableColBiind = tableColBiind;
    }

    public RichInputText getTableColBiind() {
        return tableColBiind;
    }

    public void setSearchbind(RichQuery searchbind) {
        this.searchbind = searchbind;
    }

    public RichQuery getSearchbind() {
        return searchbind;
    }

    public void setEoNmBind(RichInputText eoNmBind) {
        this.eoNmBind = eoNmBind;
    }

    public RichInputText getEoNmBind() {
        return eoNmBind;
    }

    public void setOnHoldBInd(RichSelectBooleanCheckbox onHoldBInd) {
        this.onHoldBInd = onHoldBInd;
    }

    public RichSelectBooleanCheckbox getOnHoldBInd() {
        return onHoldBInd;
    }

    public void setBlackListedBind(RichSelectBooleanCheckbox blackListedBind) {
        this.blackListedBind = blackListedBind;
    }

    public RichSelectBooleanCheckbox getBlackListedBind() {
        return blackListedBind;
    }

    public String searchAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("searchCriteria");
        // System.out.println("in search");
        //   System.out.println(transEoNameBVar.getValue().toString() + onHoldBInd.getValue().toString() +
        //                    blackListedBind.getValue().toString());
        if (transEoNameBVar.getValue() != null)
            operationBinding.getParamsMap().put("eonm", transEoNameBVar.getValue().toString());

        if (onHoldBInd.getValue().toString().equals("true")) {
            // System.out.println("in if of onhold " + onHoldBInd.getValue());
            operationBinding.getParamsMap().put("onhold", onHoldBInd.getValue());
        } else {
            // System.out.println("in else of unhold " + onHoldBInd.getValue());
            operationBinding.getParamsMap().put("onhold", null);
        }
        if (blackListedBind.getValue().toString().equals("true")) {
            // System.out.println("in if of blacklisted");
            operationBinding.getParamsMap().put("blacklst", blackListedBind.getValue());
        } else {
            // System.out.println("in else of black list");
            operationBinding.getParamsMap().put("blacklst", null);
        }
        operationBinding.execute();
        return null;
    }

    public String resetAction() {
       /*  eoNmBind.setValue(null);
        onHoldBInd.setValue(null);
        blackListedBind.setValue(null); */
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("resetCriteria");
        operationBinding.execute();

        transEoNameBVar.setValue("");
        onHoldBInd.setValue(false);
        blackListedBind.setValue(false);

        AdfFacesContext.getCurrentInstance().addPartialTarget(transEoNameBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldBInd);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListedBind);

        return null;
    }


    public void setInactvRsnBind(RichInputText inactvRsnBind) {
        this.inactvRsnBind = inactvRsnBind;
    }

    public RichInputText getInactvRsnBind() {
        return inactvRsnBind;
    }

    public void setInactvrsnBankBind(RichInputText inactvrsnBankBind) {
        this.inactvrsnBankBind = inactvrsnBankBind;
    }

    public RichInputText getInactvrsnBankBind() {
        return inactvrsnBankBind;
    }

    public void onHoldVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            onHoldFrmDateBind.setValue(new Timestamp(System.currentTimeMillis()));

        }
        if (valueChangeEvent.getNewValue().toString().equals("false")) {
            /*  onHoldToDateBind.setImmediate(true);
            onHoldFrmDateBind.setImmediate(true);

            onHoldToDateBind.setRequired(false);
            onHoldFrmDateBind.setRequired(false); */

            onHoldFrmDateBind.setValue(null);
            onHoldToDateBind.setValue(null);
            onHoldRsnBind.setValue(null);
        }

        /*  onHoldToDateBind.setImmediate(false);
        onHoldFrmDateBind.setImmediate(false);
         */
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldFrmDateBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldToDateBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldRsnBind);

        /*   onHoldToDateBind.setRequired(true);
        onHoldFrmDateBind.setRequired(true); */

    }

    public void setOnHoldPrflBind(RichSelectBooleanCheckbox onHoldPrflBind) {
        this.onHoldPrflBind = onHoldPrflBind;
    }

    public RichSelectBooleanCheckbox getOnHoldPrflBind() {
        return onHoldPrflBind;
    }

    public void setOnHoldRsnBind(RichInputText onHoldRsnBind) {
        this.onHoldRsnBind = onHoldRsnBind;
    }

    public RichInputText getOnHoldRsnBind() {
        return onHoldRsnBind;
    }

    public void setOnHoldFrmDateBind(RichInputDate onHoldFrmDateBind) {
        this.onHoldFrmDateBind = onHoldFrmDateBind;
    }

    public RichInputDate getOnHoldFrmDateBind() {
        return onHoldFrmDateBind;
    }

    public void setOnHoldToDateBind(RichInputDate onHoldToDateBind) {
        this.onHoldToDateBind = onHoldToDateBind;
    }

    public RichInputDate getOnHoldToDateBind() {
        return onHoldToDateBind;
    }

    public void fromDateVCL(ValueChangeEvent valueChangeEvent) {

        try {
            onHoldToDateBind.setValue(null);
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("refreshVo");

        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldToDateBind);

    }

    public void blackListVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            blackListFromDt.setValue(new Timestamp(System.currentTimeMillis()));

        }
        if (valueChangeEvent.getNewValue().toString().equals("false")) {

            /* blackListFromDt.setImmediate(true);
            blackListToDt.setImmediate(true);

            blackListFromDt.setRequired(false);
            blackListToDt.setRequired(false); */


            blackListFromDt.setValue(null);
            blackListToDt.setValue(null);
            blackListRsn.setValue(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListFromDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListToDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListRsn);

        /*  blackListFromDt.setImmediate(false);
        blackListToDt.setImmediate(false);

        blackListFromDt.setRequired(true);
        blackListToDt.setRequired(true); */

    }

    public void setBlackListBind(RichSelectBooleanCheckbox blackListBind) {
        this.blackListBind = blackListBind;
    }

    public RichSelectBooleanCheckbox getBlackListBind() {
        return blackListBind;
    }

    public void setBlackListRsn(RichInputText blackListRsn) {
        this.blackListRsn = blackListRsn;
    }

    public RichInputText getBlackListRsn() {
        return blackListRsn;
    }

    public void setBlackListFromDt(RichInputDate blackListFromDt) {
        this.blackListFromDt = blackListFromDt;
    }

    public RichInputDate getBlackListFromDt() {
        return blackListFromDt;
    }

    public void setBlackListToDt(RichInputDate blackListToDt) {
        this.blackListToDt = blackListToDt;
    }

    public RichInputDate getBlackListToDt() {
        return blackListToDt;
    }

    public void fromdate_Blck_VCL(ValueChangeEvent valueChangeEvent) {
        blackListToDt.setValue(null);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("refreshVo");

        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListToDt);
    }

    public void setTransEoNameBVar(RichInputListOfValues transEoNameBVar) {
        this.transEoNameBVar = transEoNameBVar;
    }

    public RichInputListOfValues getTransEoNameBVar() {
        return transEoNameBVar;
    }

    public void AvgcreditdaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer crdays = Integer.parseInt(object.toString());
            if (crdays < 0) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.553']}").toString()); //Negative value is not allowed
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                // FacesContext.getCurrentInstance().addMessage(crdaysPgBind.getClientId(), message);
                throw new ValidatorException(message);

            }
        }
    }

    public void setCrdaysPgBind(RichInputText crdaysPgBind) {
        this.crdaysPgBind = crdaysPgBind;
    }

    public RichInputText getCrdaysPgBind() {
        return crdaysPgBind;
    }

    public void creditLimitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number crlimit = (oracle.jbo.domain.Number)object;
            if (crlimit.compareTo(0) == -1) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.553']}").toString()); //Negative value is not allowed
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //FacesContext.getCurrentInstance().addMessage(crdaysPgBind.getClientId(), message);
                throw new ValidatorException(message);

            } else {
                if (isPrecisionValid(15, 2, crlimit)) {

                    if (crlimit.compareTo(0) == 0) {

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.514']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        //FacesContext.getCurrentInstance().addMessage(crdaysPgBind.getClientId(), message);
                        throw new ValidatorException(message);

                    }

                } else {

                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.56']}").toString()); //Invalid precision (15,2)
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //FacesContext.getCurrentInstance().addMessage(crdaysPgBind.getClientId(), message);
                    throw new ValidatorException(message);

                }
            }

        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void setCreditLimitPgBind(RichInputText creditLimitPgBind) {
        this.creditLimitPgBind = creditLimitPgBind;
    }

    public RichInputText getCreditLimitPgBind() {
        return creditLimitPgBind;
    }

    public void setDlvDaysPgBind(RichInputText dlvDaysPgBind) {
        this.dlvDaysPgBind = dlvDaysPgBind;
    }

    public RichInputText getDlvDaysPgBind() {
        return dlvDaysPgBind;
    }

    public void DlvDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer dlvdays = Integer.parseInt(object.toString());
            if (dlvdays < 0) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.553']}").toString()); //Negative value is not allowed
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                // FacesContext.getCurrentInstance().addMessage(dlvDaysPgBind.getClientId(), message);
                throw new ValidatorException(message);

            }
        }
    }

    public void BankAccNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String bankaccno = object.toString();
            if (bankaccno.length() > 0) {

                String expression = "([a-zA-Z0-9]+)(([a-zA-Z0-9]+))*"; //String with one space in middle

                //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*";  //String with space in middle and end
                CharSequence inputStr = object.toString();
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);
                //String error = resolvElDCMsg("Special Character Not Allowed").toString();

                if (matcher.matches()) {
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("chkDuplicateBankAccno");
                    operationBinding.getParamsMap().put("object", object.toString());
                    operationBinding.execute();

                    if ((Boolean)operationBinding.getResult()) {
                        FacesMessage message =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.375']}").toString()); //Duplicate Record
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        throw new ValidatorException(message);
                    }

                } else {
                    // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                    //}

                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.817']}").toString()); //Please enter proper Bank Number
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    // FacesContext.getCurrentInstance().addMessage(dlvDaysPgBind.getClientId(), message);
                    throw new ValidatorException(message);

                }

            }
        }
    }

    public void BankNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle

        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*"; //String with space in middle and end

        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error =
            resolvElDCMsg(resolvElDCMsg("#{bundle['MSG.98']}").toString()).toString(); //Special Characters are not allowed other than ''- , () / \ _ :''

        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }
    }

    public void setAddressPgBind(RichInputListOfValues addressPgBind) {
        this.addressPgBind = addressPgBind;
    }

    public RichInputListOfValues getAddressPgBind() {
        return addressPgBind;
    }


    public void setDfltBillAddsCB(RichSelectBooleanCheckbox dfltBillAddsCB) {
        this.dfltBillAddsCB = dfltBillAddsCB;
    }

    public RichSelectBooleanCheckbox getDfltBillAddsCB() {
        return dfltBillAddsCB;
    }

    public void BillAddsVCL(ValueChangeEvent vce) {
        if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
            if (addressPgBind.getValue() != null) {

            } else {
                FacesMessage message = new FacesMessage("Please Select Address");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(billAddsCB.getClientId(), message);

                billAddsCB.setValue("false");
                AdfFacesContext.getCurrentInstance().addPartialTarget(billAddsCB);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(dfltBillAddsCB);
    }

    public void setBillAddsCB(RichSelectBooleanCheckbox billAddsCB) {
        this.billAddsCB = billAddsCB;
    }

    public RichSelectBooleanCheckbox getBillAddsCB() {
        return billAddsCB;
    }

    public void shipAddressVCL(ValueChangeEvent vce) {
        if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
            if (addressPgBind.getValue() != null) {

            } else {
                FacesMessage message = new FacesMessage("Please Select Address");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(shipAddressCB.getClientId(), message);

                shipAddressCB.setValue("false");
                AdfFacesContext.getCurrentInstance().addPartialTarget(shipAddressCB);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(dfltShipAddressCB);
    }

    public void setShipAddressCB(RichSelectBooleanCheckbox shipAddressCB) {
        this.shipAddressCB = shipAddressCB;
    }

    public RichSelectBooleanCheckbox getShipAddressCB() {
        return shipAddressCB;
    }

    public void setDfltShipAddressCB(RichSelectBooleanCheckbox dfltShipAddressCB) {
        this.dfltShipAddressCB = dfltShipAddressCB;
    }

    public RichSelectBooleanCheckbox getDfltShipAddressCB() {
        return dfltShipAddressCB;
    }


    public void AllowRetGoodsVL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Status is --->" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().equals("Y")) {

            OperationBinding ob = getBindings().getOperationBinding("setAllowRetGoodsStatus");
            ob.execute();

        } else {
            OperationBinding ob = getBindings().getOperationBinding("setStatus");
            ob.execute();

        }
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFileDisplayname(String fileDisplayname) {
        this.fileDisplayname = fileDisplayname;
    }

    public String getFileDisplayname() {
        return fileDisplayname;
    }
    private InputStream inputStream;
    StringBuffer fileName = new StringBuffer();
    StringBuffer dirPath = getPath(); //new StringBuffer("D:\\DOCS"); //getPath(); //new StringBuffer("D:\\DOCS");
    ArrayList<UploadDoc> fileList = new ArrayList<UploadDoc>();

    public StringBuffer getPath() {
        System.out.println(getparamSlocId() + "");
        StringBuffer path =
            new StringBuffer((String)callStoredFunction(Types.VARCHAR, " APP.FN_GET_APP_DOC_ATTACH_PATH(?)",
                                                        new Object[] { getparamSlocId() }));
        System.out.println("path " + path);
        if (path != null) {
            System.out.println("return path when fucntion called sucessfully");
            return path;
        } else {
            System.out.println("return when fucntion not called successfully");
            return new StringBuffer("D:\\DOCS");
        }


    }

    /****Methods to get Global Parameter*/
    public Integer getparamSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    }

    public Integer getparamUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
    }

    public String getparamOrgId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    }

    public String getparamCldId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
    }

    public String getparamHoOrgId() {
        return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
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


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        CustomerProfileAppAMImpl am = (CustomerProfileAppAMImpl)resolvElDC("CustomerProfileAppAMDataControl");

        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    //System.out.println(bindVars[z] + "zzzzzzzzzzzzzzzzzzzzz");
                }
            }
            st.executeUpdate();
            //System.out.println("aaaaaaaaaaaaaaaaaaa");
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void uploadDocActionListener(ActionEvent actionEvent) {
        System.out.println("file name and documnet" + file + "====" + fileDisplayname + " path " + dirPath);
        if (fileDisplayname.equals("")) {
            FacesMessage Message = new FacesMessage("Please enter Display name.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(fileNameBVar.getClientId(), Message);
        } else if (file != null) {
            System.out.println("file name and documnet" + file + "====" + fileDisplayname);
            System.out.println();
            //_FileName = new StringBuffer(file.getFilename().toString());
            System.out.println("FILE_NAME :" + file.getFilename());
            System.out.println("FILE_contenet :" + file.getContentType() + "  _FileDisplayName=" + fileDisplayname +
                               "==dirPath  fileName" + dirPath + "\\" + fileName);
            System.out.println(fileName);

            try {
                inputStream = file.getInputStream();
                System.out.println(" inputStream " + inputStream);
                inputStream.close();
            } catch (Exception e) {
                System.out.println("ERROR IN GETTING FILEINPUTSTREAM : " + e.getMessage());
            }
            fileList.add(new UploadDoc(file.getFilename(), file.getContentType().toString(), dirPath + "" + fileName,
                                       fileDisplayname +
                                       file.getFilename().substring(file.getFilename().lastIndexOf(".")), inputStream,
                                       file.getFilename().substring(file.getFilename().lastIndexOf("."))));


            for (UploadDoc list : fileList) {
                System.out.println("in loop");
                System.out.println(file.getFilename() + " : " + list.getAttchedFileExtension() + " : " +
                                   list.getAttchedFilePath() + " : " + list.getAttchedFileDispName() + "::" +
                                   list.getFileExt() + ":: " + list.getFileInputStream());

            }

            fileDisplayname = "";
            file.dispose();
            file = null;

            CustomerProfileAppAMImpl am = (CustomerProfileAppAMImpl)resolvElDC("CustomerProfileAppAMDataControl");
            ViewObjectImpl appEoAttchVo = am.getAppEoAttch2();

            System.out.println("row count before " + appEoAttchVo.getRowCount());
            for (UploadDoc list : fileList) {
                System.out.println("inserted");
                System.out.println("List =" + list);
                getBindings().getOperationBinding("CreateInsert3").execute();
                Row currentRow = appEoAttchVo.getCurrentRow();
                String nm = "";
                try {
                    System.out.println(getparamSlocId() + "");
                    nm =
 callStoredFunction(Types.VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?)", new Object[] { getparamSlocId(), getparamCldId(),
                                                                                  null, getparamOrgId(),
                                                                                  "APP$EO$ATTCH" }).toString();
                    System.out.println("nm" + nm);
                    currentRow.setAttribute("AttchFlNm", nm);
                } catch (Exception e) {
                    System.out.println("ERROR IN CALLING FUNCTION :" + e.getMessage());
                }
                currentRow.setAttribute("CldId", getparamCldId());
                currentRow.setAttribute("SlocId", getparamSlocId());
                currentRow.setAttribute("OrgId", getparamOrgId());
                currentRow.setAttribute("HoOrgId", getparamHoOrgId());
                //currentRow.setAttribute("AttachFl",doc_txn_id);//issme kya jana hai
                System.out.println("eo name is" + am.getCustomer1().getCurrentRow().getAttribute("EoId"));
                currentRow.setAttribute("EoId", am.getCustomer1().getCurrentRow().getAttribute("EoId"));
                currentRow.setAttribute("AttchFlExtn", list.getAttchedFileExtension());
                System.out.println("FILE P :" + list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("AttchFlPath", list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("DispFlNm", list.getAttchedFileDispName());
                currentRow.setAttribute("UsrIdCreate", getparamUsrId());
                currentRow.setAttribute("UsrIdCreateDt", new Timestamp(System.currentTimeMillis()));


                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                System.out.println("operationBinding.getErrors().isEmpty() " + operationBinding.getErrors().isEmpty());
                if (operationBinding.getErrors().isEmpty()) {

                    if (list.getFileInputStream() != null) {
                        System.out.println("FILE_NAME :" + nm + list.getFileExt());
                        System.out.println("FILE_TYPE :" + list.getAttchedFileExtension());
                        //System.out.println(_FileName);

                        try {
                            System.out.println(list.getFileInputStream());
                            InputStream in = list.getFileInputStream();
                            System.out.println("FILE PATH : " + list.getAttchedFilePath() + nm + list.getFileExt());
                            FileOutputStream out =
                                new FileOutputStream(list.getAttchedFilePath() + nm + list.getFileExt());
                            byte[] buffer = new byte[8192];
                            int bytesRead = 0;

                            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }
                            in.close();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                    getBindings().getOperationBinding("Commit").execute();
                }
            }
            System.out.println("row count after " + appEoAttchVo.getRowCount());
            System.out.println("fileList at last before clrsr" + fileList);
            fileList.clear();
            System.out.println("fileList at last after clrsr" + fileList);
            OperationBinding execute = getBindings().getOperationBinding("Execute");
            execute.execute();
        } else {
            FacesMessage Message = new FacesMessage("Please select a file.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(attchDocBVar.getClientId(), Message);
        }
    }

    public void setFileList(ArrayList<UploadDoc> fileList) {
        this.fileList = fileList;
    }

    public ArrayList<UploadDoc> getFileList() {
        return fileList;
    }

    public void deleteFileActionListener(ActionEvent actionEvent) {

        showPopup(deleteAttchFilePopUp, true);

        /* OperationBinding execute = getBindings().getOperationBinding("Execute");
        execute.execute(); */
    }

    public void deleteAttachment(StringBuffer path) {
        StringBuffer filePath = (path == null ? new StringBuffer("") : path);
        File f = new File(filePath.toString());
        Boolean b = f.exists();
        System.out.println("File exists : " + b);
        if (b.equals(true)) {
            f.delete();

            System.out.println("File Deleted.");
        }
    }

    public void setAttchDocBVar(RichInputFile attchDocBVar) {
        this.attchDocBVar = attchDocBVar;
    }

    public RichInputFile getAttchDocBVar() {
        return attchDocBVar;
    }

    public void setFileNameBVar(RichInputText fileNameBVar) {
        this.fileNameBVar = fileNameBVar;
    }

    public RichInputText getFileNameBVar() {
        return fileNameBVar;
    }
    private String prsndtlMode = "V";
    private String prsnldtlLink = "V";

    public void createPrsndtlAction(ActionEvent actionEvent) {
        prsnldtlLink = "C";
        prsndtlMode = "C";
        prfBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        addsBind.setDisabled(true);
        bankBind.setDisabled(true);
        tableBind.setRowSelection("none");
        OperationBinding create = getBindings().getOperationBinding("CreateInsert4");
        create.execute();
    }

    public void editPrsnDtlAction(ActionEvent actionEvent) {
        prsnldtlLink = "C";
        prsndtlMode = "C";
        prfBind.setDisabled(true);
        attachDocTabBVar.setDisabled(true);
        addsBind.setDisabled(true);
        bankBind.setDisabled(true);
        tableBind.setRowSelection("none");
    }

    public void deletePrsnDtlAction(ActionEvent actionEvent) {
        showPopup(prsnlDtlPopupBVar, true);

    }

    public void savePrsnDtlAction(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("MandatoryCheckForPersonalDetails");
        binding.execute();
        if(binding.getResult().equals((Boolean)true)){
            prsnldtlLink = "V";
            prsndtlMode = "V";
            attachDocTabBVar.setDisabled(false);
            prfBind.setDisabled(false);
            addsBind.setDisabled(false);
            bankBind.setDisabled(false);
            tableBind.setRowSelection("single");
            OperationBinding save = getBindings().getOperationBinding("Commit");
            save.execute();    
        }
    }

    public void cancelPrsnDtlAction(ActionEvent actionEvent) {
        prsnldtlLink = "V";
        prsndtlMode = "V";
        prfBind.setDisabled(false);
        attachDocTabBVar.setDisabled(false);
        addsBind.setDisabled(false);
        bankBind.setDisabled(false);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void setPsnlDtlTabBVar(RichShowDetailItem psnlDtlTabBVar) {
        this.psnlDtlTabBVar = psnlDtlTabBVar;
    }

    public RichShowDetailItem getPsnlDtlTabBVar() {
        return psnlDtlTabBVar;
    }

    public void setAttachDocTabBVar(RichShowDetailItem attachDocTabBVar) {
        this.attachDocTabBVar = attachDocTabBVar;
    }

    public RichShowDetailItem getAttachDocTabBVar() {
        return attachDocTabBVar;
    }

    public void setPrsndtlMode(String prsndtlMode) {
        this.prsndtlMode = prsndtlMode;
    }

    public String getPrsndtlMode() {
        return prsndtlMode;
    }

    public void setPrsnlDtlPopupBVar(RichPopup prsnlDtlPopupBVar) {
        this.prsnlDtlPopupBVar = prsnlDtlPopupBVar;
    }

    public RichPopup getPrsnlDtlPopupBVar() {
        return prsnlDtlPopupBVar;
    }

    public void prsnlDtlPopupDeleteAction(ActionEvent actionEvent) {
        OperationBinding delete = getBindings().getOperationBinding("Delete4");
        delete.execute();
        FacesMessage message = new FacesMessage("Record Deleted Successfully"); //Enter the date
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
        prsnlDtlPopupBVar.hide();
    }

    public void prsnlDtlPopupCancelAction(ActionEvent actionEvent) {
        prsnlDtlPopupBVar.hide();
    }

    public void setPrsnldtlLink(String prsnldtlLink) {
        this.prsnldtlLink = prsnldtlLink;
    }

    public String getPrsnldtlLink() {
        return prsnldtlLink;
    }

    public void prsnNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.197']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.172']}").toString()));
            }

        }
    }

    public void prsnDesgValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.197']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.172']}").toString()));
            }

        }
    }


    public void prsnEmailIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String email = object.toString();
        if (email.startsWith(" ") || email.endsWith(" ")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.54']}").toString(),
                                                          resolvElDCMsg("#{bundle['MSG.204']}").toString()));
        }
        String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = resolvElDCMsg("#{bundle['MSG.54']}").toString();

        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                          resolvElDCMsg("#{bundle['MSG.206']}").toString()));
        }


    }

    public void faxNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String phnNo = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = phnNo.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }
            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (phnNo.lastIndexOf("(") > phnNo.lastIndexOf(")")) ||
                (phnNo.indexOf(")") < phnNo.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("(.") || phnNo.contains("(-") || phnNo.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.196']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;
            //check for valid language name.Allowed- brackets,dots,hyphen

            String expression = "([0-9\\-\\+\\(\\)]+)";
            CharSequence inputStr = phnNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.202']}").toString();


            if (matcher.matches()) {
                if (phnNo.contains("++") || phnNo.contains("--")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.201']}").toString()));
                } else if (phnNo.lastIndexOf("+") > 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.203']}").toString()));
                } else if (phnNo.lastIndexOf("+") == 1 && phnNo.charAt(0) != '(') {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.203']}").toString()));
                } else if (phnNo.startsWith(" ") || phnNo.endsWith(" ")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.204']}").toString()));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.205']}").toString()));
            }

        }
    }


    public void defaultBillAddsVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().equalsIgnoreCase("true")) {
                OperationBinding binding = this.getBindings().getOperationBinding("isDefaultBillAddsValid");
                binding.execute();
                if (binding.getResult().equals((Object)false)) {
                    String error = "Another Address already selected as Default Billing Address";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                  error.toString()));
                }
            }
            System.out.println("Object " + object);
        }
    }

    public void defaultShipAddsVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().equalsIgnoreCase("true")) {
                OperationBinding binding = this.getBindings().getOperationBinding("isDefaultShipAddsValid");
                binding.execute();
                if (binding.getResult().equals((Object)false)) {
                    String error = "Another Address already selected as Default Shipping Address";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                  error.toString()));
                }
            }
            System.out.println("Object " + object);
        }
    }

    public void setPrsnlDtlPopUpBVar(RichPopup prsnlDtlPopUpBVar) {
        this.prsnlDtlPopUpBVar = prsnlDtlPopUpBVar;
    }

    public RichPopup getPrsnlDtlPopUpBVar() {
        return prsnlDtlPopUpBVar;
    }

  

    public void prsnlDtlCancelListeener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }


    public void setBankPopUpBVar(RichPopup bankPopUpBVar) {
        this.bankPopUpBVar = bankPopUpBVar;
    }

    public RichPopup getBankPopUpBVar() {
        return bankPopUpBVar;
    }

    public void deleteBankDialogueList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            System.out.println("delete bank");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
            operationBinding.execute();
            OperationBinding ext = bindings.getOperationBinding("Execute2");
            ext.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankTblBVar);
        }

    }

    public void setBankTblBVar(RichTable bankTblBVar) {
        this.bankTblBVar = bankTblBVar;
    }

    public RichTable getBankTblBVar() {
        return bankTblBVar;
    }

    public void setDeleteAttchFilePopUp(RichPopup deleteAttchFilePopUp) {
        this.deleteAttchFilePopUp = deleteAttchFilePopUp;
    }

    public RichPopup getDeleteAttchFilePopUp() {
        return deleteAttchFilePopUp;
    }

    public void deleteFileDialogueList(DialogEvent dialogEvent) {
        
    }

    public void deleteAttchedFileActionList(ActionEvent actionEvent) {
        //if (dialogEvent.getOutcome().name().equals("yes")) {
            CustomerProfileAppAMImpl am = (CustomerProfileAppAMImpl)resolvElDC("CustomerProfileAppAMDataControl");
            Row currRow = am.getAppEoAttch2().getCurrentRow();
            if (currRow != null) {
                if (currRow.getAttribute("AttchFlPath") != null) {

                    StringBuffer attchFilePAth = new StringBuffer((String)currRow.getAttribute("AttchFlPath"));
                    System.out.println("attchFilePAth" + attchFilePAth);
                    deleteAttachment(attchFilePAth);
                }
                OperationBinding deleteFile = getBindings().getOperationBinding("Delete3");
                deleteFile.execute();
                OperationBinding com = getBindings().getOperationBinding("Commit");
                System.out.println("error=" + com.getErrors());
                com.execute();
                
                OperationBinding execute = getBindings().getOperationBinding("Execute3");
                execute.execute();
                System.out.println("in delete");
                deleteAttchFilePopUp.hide();
            }
        deleteAttchFilePopUp.hide();
      //  }
    }

    public void cancelDelteFileActionList(ActionEvent actionEvent) {
        deleteAttchFilePopUp.hide();
    }
}
