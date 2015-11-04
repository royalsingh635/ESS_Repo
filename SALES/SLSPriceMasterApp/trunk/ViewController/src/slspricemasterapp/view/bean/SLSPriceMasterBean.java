package slspricemasterapp.view.bean;


import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;

import adf.utils.model.ADFModelUtils;

import com.sun.corba.se.spi.orb.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
//import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.util.IOUtils;

import slspricemasterapp.model.services.SLSPriceMasterAppAMImpl;
import slspricemasterapp.model.views.DistinctPriceMasterVoImpl;
import slspricemasterapp.model.views.DistinctPriceMasterVoRowImpl;

public class SLSPriceMasterBean {
    private RichPopup pop;
    private oracle.jbo.domain.Date date = (oracle.jbo.domain.Date) oracle.jbo.domain.Date.getCurrentDate();
    //  private Date date = new Date(); //

    private RichSelectBooleanCheckbox priceAplyBindVar;
    private RichSelectOneRadio mreTypeBind;
    private String flag = "V";
    private String readMode = "R";
    private RichInputText basePricebVar;
    private RichInputListOfValues partyNmSearchbvar;
    private RichInputText searchCatIdBvar;
    private RichInputText searchPartyIdBvar;
    private RichSelectOneRadio catagNpartyRadioBVar;
    private RichLink srchBtnBVar;
    private RichInputText mrpPriceBVar;
    private Boolean catNpartyEnable = false;
    private RichInputText trnsAddCatgIdBVar;
    private RichInputText trnsAddPartyIdBVar;
    private StringBuffer eoPriceMode = new StringBuffer("V");
    String chk = "Y";
    private RichSelectOneRadio chngPrcFrRadioBVar;
    private RichInputText amountchangeBVar;
    private RichInputDate efctivDateBVar;
    private RichInputListOfValues itemNmBVar;
    private RichInputText itemIdBVar;
    private RichInputText mrpRateBVar;
    private RichSelectOneRadio chnageAmountBVar;
    private RichInputListOfValues transItenIdBVar;
    private RichInputDate effective_DatePopupBVar;

    private RichPopup deleteItemPopUpBVar;
    private RichSelectOneRadio catOrCustWiseRadioBVar;
    private RichPopup selectcatOrEoWisePopUp;
    private RichPopup editEoWiseItemPopUpBVar;
    private RichSelectOneRadio selectChangeTypeBVar;
    private RichInputText newextraPriceValBVar;
    private RichTable priceMaster2TableBVar;
    private RichTable executeDistinctPmTableBVar;
    // private RichLink _checkLinkBind;
    private RichPopup savepopup;
    private RichLink _saveBVar;
    private RichLink _addLinkNBar;
    private RichInputText minPrceBVar;
    private RichLink _deleteBVar;
    private RichLink _editLinkBVar;
    private RichSelectOneRadio flagBVar;
    private RichSelectOneRadio flagEoWiseEdit;
    private RichLink cancelLinkBVar;
    private RichInputListOfValues transItmNameBVar;
    private RichInputText searchedtransItmIdBVar;
    private RichInputListOfValues tsitmNmBVar;
    private RichInputText tsitmIdBVar;
    private RichPanelFormLayout searchPanelBVar;
    private RichPanelGroupLayout catgPnaelformBVar;
    private RichPanelFormLayout sepecifItmFrmBVar;
    private RichPanelFormLayout typeAndAmountFormBVar;
    private RichPanelFormLayout efftvDtpanelFormBVar;
    private RichInputText eowiseMrpPriceBVar;
    private RichInputDate eoWiseEfftvDt;
    private RichSelectOneChoice catgIdInEoPgBVar;
    private RichInputListOfValues eoNmInEoPgBVar;
    private RichLink resetLinkBVar;
    private RichPopup historyPopUp;
    private RichSelectOneChoice catagSearchBVar;
    private RichSelectOneChoice transAddCatagNmBVar;
    private RichInputDate expiryDateBVar;
    private UploadedFile file;
    private RichInputFile inputFileBind;
    private RichCommandButton exceldownloadBind;
    private String showTable = "All";
    private RichSelectOneChoice itmGrpNmBind;
    private RichInputText mrpRateOnAddPopUpBind;
    private RichPopup addPopupBind;

    /**
     * Value should be
     * V : View Mode
     * E : Edit Mode
     */
    private StringBuilder catgPgMode = new StringBuilder("V");
    private Timestamp currentDate = new Timestamp(System.currentTimeMillis() - 60000);
    private RichTable custWiseTable;
    private RichDialog addItemPriceDialogBind;
    private RichSelectOneChoice eoCatgIdPopUpBing;
    public String upload_Mode="A";

    public SLSPriceMasterBean() {
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

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void addItem_ActionListener(ActionEvent actionEvent) {
        flag = "A";
        /*  if (transAddCatagNmBVar.getValue() == null || transAddCatagNmBVar.getValue().toString().length() <= 0) {
            //FacesMessage message = new FacesMessage("You must select Catagory type");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.971']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(transAddCatagNmBVar.getClientId(), message);
        } else {
             */
        //this.getBindings().getOperationBinding("resetAddVO").execute();
        showPopup(addPopupBind, true);
        currentDate = new Timestamp(System.currentTimeMillis() - 60000);
        // }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void popup_DialogueListener(DialogEvent dialogEvent) {
        //  System.out.println("effective date  in bean " + effective_DatePopupBVar.getValue());
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            Number basePrice = (Number) basePricebVar.getValue();
            Number minPrice = (Number) minPrceBVar.getValue();
            Number mrpRate = (Number) mrpRateBVar.getValue();
            if (basePrice == null || basePrice.compareTo(0) == 0) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.984']}").toString()); //Please Enter Base Price
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(basePricebVar.getClientId(), message);
            } else if (minPrice == null || minPrice.compareTo(0) == 0) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1164']}").toString()); //	Please Enter Minimum Price
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(minPrceBVar.getClientId(), message);

            } else {
                BindingContainer bindings = getBindings();
                System.out.println("Calling function to delete ");
                OperationBinding deleteOldPrc = bindings.getOperationBinding("deleteOldItmPrice");
                deleteOldPrc.execute();
                System.out.println("function called");
                OperationBinding copyItemInAllEo = bindings.getOperationBinding("replicateItemInAllEo");
                copyItemInAllEo.getParamsMap().put("effectiveDate", effective_DatePopupBVar.getValue());
                copyItemInAllEo.execute();
                /*   OperationBinding executeAfterCopyItem = bindings.getOperationBinding("Commit");
                executeAfterCopyItem.execute();
                OperationBinding operationBindings = bindings.getOperationBinding("executeVo");
                operationBindings.execute();
                OperationBinding executeDistinctPnVo = bindings.getOperationBinding("executeDistinctPnVo");
                executeDistinctPnVo.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar); */

                if (copyItemInAllEo.getResult() != null) {
                    String rst = (String) copyItemInAllEo.getResult();
                    //  System.out.println("retunr result  --" + rst);
                    if (rst.equalsIgnoreCase("INSERTED")) {

                        OperationBinding executeAfterCopyItem = bindings.getOperationBinding("Commit");
                        executeAfterCopyItem.execute();
                        OperationBinding operationBindings = bindings.getOperationBinding("executeVo");
                        operationBindings.execute();
                        OperationBinding executeDistinctPnVo = bindings.getOperationBinding("executeDistinctPnVo");
                        executeDistinctPnVo.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);
                    } else {
                        FacesMessage message =
                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1166']}").toString()); //Unable To Insert Item Price for all Customer.!!
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                }
            }
        }
        addMode = false;
        readMode = "R";
        AdfFacesContext.getCurrentInstance().addPartialTarget(_editLinkBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(_deleteBVar);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(exceldownloadBind);
    }


    /**
     * @param popupCanceledEvent
     */
    public void popupCancel_Listener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();

        getBindings().getOperationBinding("Rollback").execute();
        OperationBinding operationBinding = bindings.getOperationBinding("executeVo");
        operationBinding.execute();
        readMode = "R";

        //  _saveBVar.setDisabled(false);
        // _addLinkNBar.setDisabled(false);
    }

    public void edit_ActionListener(ActionEvent actionEvent) {
        /* if (transAddCatagNmBVar.getValue() == null || transAddCatagNmBVar.getValue().toString().length() <= 0) {
            //FacesMessage message = new FacesMessage("You must select Catagory type");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.971']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(transAddCatagNmBVar.getClientId(), message);
        } else { */
            showPopup(selectcatOrEoWisePopUp, true);
       // }
    }

    public void delete_ActionListener(ActionEvent actionEvent) {

        showPopup(deleteItemPopUpBVar, true);


    }


    public void priceApplyChangeListener(ValueChangeEvent valueChangeEvent) {
        Boolean priceapply = (Boolean) priceAplyBindVar.getValue();
        // System.out.println("Applied price value is---------->>" + priceapply);

    }


    public void catNpartyRadio_ChangeListener(ValueChangeEvent valueChangeEvent) {

        String radio = (String) valueChangeEvent.getNewValue();
        if (catagNpartyRadioBVar.getValue().equals("C")) {
            partyNmSearchbvar.setValue("");
            // System.out.println("hello this is in if condition");
            setCatNpartyEnable(false);
        } else {
            //System.out.println("now i am in else condition");
            setCatNpartyEnable(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(partyNmSearchbvar);
    }


    public void searchItem_ActionListener(ActionEvent actionEvent) {
        OperationBinding setValInTranAddVo = getBindings().getOperationBinding("searchDisctinctVo");
        setValInTranAddVo.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getExecuteDistinctPmTableBVar());
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchCatIdBvar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchPartyIdBvar);
    }


    public String resetAction() {
        this.getBindings().getOperationBinding("resetDisctinctVo").execute();
        System.out.println("reset action");
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchPartyIdBvar);
        cancelMode = true;
        addMode = false;
        editMode = true;
        /* AdfFacesContext.getCurrentInstance().addPartialTarget(searchCatIdBvar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchPartyIdBvar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transAddCatagNmBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemNmBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(amountchangeBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(efctivDateBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expiryDateBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(catagNpartyRadioBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(chngPrcFrRadioBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(chnageAmountBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(flagBVar); */
        return null;
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void searchcatg_changeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (catagSearchBVar.getValue().equals(null) || catagSearchBVar.getValue().toString().length() <= 0) {

            transAddCatagNmBVar.setValue("");
            this.partyNmSearchbvar.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(transAddCatagNmBVar);

        } else {
            this.partyNmSearchbvar.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(partyNmSearchbvar);
        }

    }


    String catNewVal = null;
    String eonm = null;
    String ItemNm = null;

    public void specificItemSearch_valueChangeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);


    }

    public void transaddcatag_valueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("New Value : " + valueChangeEvent.getNewValue());
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);
    }


    public void addcatandparty_actionListener(ActionEvent actionEvent) {
        OperationBinding createOB = getBindings().getOperationBinding("resetPriceMaster");
        createOB.execute();
        catagSearchBVar.setValue("");
        partyNmSearchbvar.setValue("");
        trnsAddCatgIdBVar.setValue("");
        trnsAddPartyIdBVar.setValue("");
        transAddCatagNmBVar.setValue("");
        searchPartyIdBvar.setValue("");
        searchCatIdBvar.setValue("");
        readMode = "W";
    }
    boolean itmDisable = true;
    boolean itmGrpDisable = true;

    public void chngPrcFor_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);
        /* if (radioVal.equalsIgnoreCase("A")) {
            //itemNmBVar.setDisabled(true);
            itmGrpDisable = true;
            itmGrpNmBind.setValue("");
            itmDisable = true;
            itemNmBVar.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmGrpNmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemNmBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(efctivDateBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expiryDateBVar);
            chnageRadio.execute();
        } else if (radioVal.equalsIgnoreCase("G")) {
            itmGrpDisable = false;
            itmDisable = true;
            itemNmBVar.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmGrpNmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemNmBVar);

        } else {
            //  System.out.println(" when specific item change radio button is clicked");
            itmDisable = false;
            // itemNmBVar.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemNmBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(efctivDateBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expiryDateBVar);
        } */

    }

    Number extraAmt = new Number(0);

    public void chnageAmount_ValueChnageListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            BigDecimal val = (BigDecimal) vce.getNewValue();
            try {
                extraAmt = new Number(val);
            } catch (SQLException e) {
            }
        }


    }


    public void changeEffectiveDate() {
        Object object = efctivDateBVar.getValue();
        java.util.Date expd = null;
        if (expiryDateBVar.getValue() != null && expiryDateBVar.getValue().toString().length() > 0) {
            expd = (java.util.Date) expiryDateBVar.getValue();
        }
        if (efctivDateBVar.getValue() != null && efctivDateBVar.getValue().toString().length() > 0) {
            java.sql.Date sqlDate = null;
            java.util.Date d = (java.util.Date) efctivDateBVar.getValue();

            /*  if (d != null && d.toString().length() > 0) {
                sqlDate = new java.sql.Date(d.getTime());
            }

            Date eftivDate = new Date(sqlDate); */
            // System.out.println("expiry date is =" + expd);
            oracle.jbo.domain.Timestamp timeStam = new oracle.jbo.domain.Timestamp(d);
            oracle.jbo.domain.Timestamp expTimeStam = null;
            if (expd != null) {
                //  System.out.println("set1010011");
                expTimeStam = new oracle.jbo.domain.Timestamp(expd);
            }

            //  System.out.println("-----------------------" + timeStam + "---------" + expTimeStam);
            //  System.out.println("effective date chage method in ben " + timeStam + "trnsAddCatgIdBVar.getValue()" +
            //    trnsAddCatgIdBVar.getValue() + "trnsAddPartyIdBVar.getValue()" +
            //    trnsAddPartyIdBVar.getValue() + "itemIdBVar.getValue()" + itemIdBVar.getValue());
            OperationBinding updateEffectiveDt = getBindings().getOperationBinding("updateEffectiveDate");
            updateEffectiveDt.getParamsMap().put("newdate", timeStam);
            updateEffectiveDt.getParamsMap().put("expDate", expTimeStam);
            updateEffectiveDt.getParamsMap().put("CategoryId", trnsAddCatgIdBVar.getValue());
            updateEffectiveDt.getParamsMap().put("EoId", trnsAddPartyIdBVar.getValue());
            updateEffectiveDt.getParamsMap().put("ItemId", itemIdBVar.getValue());
            updateEffectiveDt.getParamsMap().put("grpId",
                                                 (itmGrpNmBind.getValue().equals("") ? null : itmGrpNmBind.getValue()));

            updateEffectiveDt.execute();


        }

    }


    public void setSearchCatIdBvar(RichInputText searchCatIdBvar) {
        this.searchCatIdBvar = searchCatIdBvar;
    }

    public RichInputText getSearchCatIdBvar() {
        return searchCatIdBvar;
    }

    public void setSearchPartyIdBvar(RichInputText searchPartyIdBvar) {
        this.searchPartyIdBvar = searchPartyIdBvar;
    }

    public RichInputText getSearchPartyIdBvar() {
        return searchPartyIdBvar;
    }

    public void setCatagNpartyRadioBVar(RichSelectOneRadio catagNpartyRadioBVar) {
        this.catagNpartyRadioBVar = catagNpartyRadioBVar;
    }

    public RichSelectOneRadio getCatagNpartyRadioBVar() {
        return catagNpartyRadioBVar;
    }


    public void setCatNpartyEnable(Boolean catNpartyEnable) {
        this.catNpartyEnable = catNpartyEnable;
    }

    public Boolean getCatNpartyEnable() {
        return catNpartyEnable;
    }

    public void setSrchBtnBVar(RichLink srchBtnBVar) {
        this.srchBtnBVar = srchBtnBVar;
    }

    public RichLink getSrchBtnBVar() {
        return srchBtnBVar;
    }

    public void setMrpPriceBVar(RichInputText mrpPriceBVar) {
        this.mrpPriceBVar = mrpPriceBVar;
    }

    public RichInputText getMrpPriceBVar() {
        return mrpPriceBVar;
    }


    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void setDate(oracle.jbo.domain.Date date) {
        this.date = date;
    }

    public oracle.jbo.domain.Date getDate() {
        return date;
    }

    public void setPriceAplyBindVar(RichSelectBooleanCheckbox priceAplyBindVar) {
        this.priceAplyBindVar = priceAplyBindVar;
    }

    public RichSelectBooleanCheckbox getPriceAplyBindVar() {
        return priceAplyBindVar;
    }

    public void setMreTypeBind(RichSelectOneRadio mreTypeBind) {
        this.mreTypeBind = mreTypeBind;
    }

    public RichSelectOneRadio getMreTypeBind() {
        return mreTypeBind;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setBasePricebVar(RichInputText basePricebVar) {
        this.basePricebVar = basePricebVar;
    }

    public RichInputText getBasePricebVar() {
        return basePricebVar;
    }


    public void setPartyNmSearchbvar(RichInputListOfValues partyNmSearchbvar) {
        this.partyNmSearchbvar = partyNmSearchbvar;
    }

    public RichInputListOfValues getPartyNmSearchbvar() {
        return partyNmSearchbvar;
    }


    public void setTrnsAddCatgIdBVar(RichInputText trnsAddCatgIdBVar) {
        this.trnsAddCatgIdBVar = trnsAddCatgIdBVar;
    }

    public RichInputText getTrnsAddCatgIdBVar() {
        return trnsAddCatgIdBVar;
    }

    public void setTrnsAddPartyIdBVar(RichInputText trnsAddPartyIdBVar) {
        this.trnsAddPartyIdBVar = trnsAddPartyIdBVar;
    }

    public RichInputText getTrnsAddPartyIdBVar() {
        return trnsAddPartyIdBVar;
    }

    public void setReadMode(String readMode) {
        this.readMode = readMode;
    }

    public String getReadMode() {
        return readMode;
    }

    public void setChngPrcFrRadioBVar(RichSelectOneRadio chngPrcFrRadioBVar) {
        this.chngPrcFrRadioBVar = chngPrcFrRadioBVar;
    }

    public RichSelectOneRadio getChngPrcFrRadioBVar() {
        return chngPrcFrRadioBVar;
    }


    public void setAmountchangeBVar(RichInputText amountchangeBVar) {
        this.amountchangeBVar = amountchangeBVar;
    }

    public RichInputText getAmountchangeBVar() {
        return amountchangeBVar;
    }


    public void setEfctivDateBVar(RichInputDate efctivDateBVar) {
        this.efctivDateBVar = efctivDateBVar;
    }

    public RichInputDate getEfctivDateBVar() {
        return efctivDateBVar;
    }

    public void setItemNmBVar(RichInputListOfValues itemNmBVar) {
        this.itemNmBVar = itemNmBVar;
    }

    public RichInputListOfValues getItemNmBVar() {
        return itemNmBVar;
    }

    public void setItemIdBVar(RichInputText itemIdBVar) {
        this.itemIdBVar = itemIdBVar;
    }

    public RichInputText getItemIdBVar() {
        return itemIdBVar;
    }


    public void setMrpRateBVar(RichInputText mrpRateBVar) {
        this.mrpRateBVar = mrpRateBVar;
    }

    public RichInputText getMrpRateBVar() {
        return mrpRateBVar;
    }

    public void setChnageAmountBVar(RichSelectOneRadio chnageAmountBVar) {
        this.chnageAmountBVar = chnageAmountBVar;
    }

    public RichSelectOneRadio getChnageAmountBVar() {
        return chnageAmountBVar;
    }


    public void setTransItenIdBVar(RichInputListOfValues transItenIdBVar) {
        this.transItenIdBVar = transItenIdBVar;
    }

    public RichInputListOfValues getTransItenIdBVar() {
        return transItenIdBVar;
    }

    public void setEffective_DatePopupBVar(RichInputDate effective_DatePopupBVar) {
        this.effective_DatePopupBVar = effective_DatePopupBVar;
    }

    public RichInputDate getEffective_DatePopupBVar() {
        return effective_DatePopupBVar;
    }


    public void popupDeleteOk_DialogueListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("deleteCategoryWiseItem");
            operationBinding.execute();

            operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            OperationBinding operationBindings = bindings.getOperationBinding("executeVo");
            operationBindings.execute();


            OperationBinding executeDistinctPnVo = bindings.getOperationBinding("executeDistinctPnVo");
            executeDistinctPnVo.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);

        }
    }

    public void popupDeleteCancel_Listener(PopupCanceledEvent popupCanceledEvent) {
        // System.out.println("deletre no method");
    }

    public void setDeleteItemPopUpBVar(RichPopup deleteItemPopUpBVar) {
        this.deleteItemPopUpBVar = deleteItemPopUpBVar;
    }

    public RichPopup getDeleteItemPopUpBVar() {
        return deleteItemPopUpBVar;
    }


    public void catOrCustWisePopUp_dialogueListener(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void catOrCustWisePopUp_CancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void catOrCustWisePopUp_cancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void setCatOrCustWiseRadioBVar(RichSelectOneRadio catOrCustWiseRadioBVar) {
        this.catOrCustWiseRadioBVar = catOrCustWiseRadioBVar;
    }

    public RichSelectOneRadio getCatOrCustWiseRadioBVar() {
        return catOrCustWiseRadioBVar;
    }

    public String pageRedirect_Action() {
        //  System.out.println("value of radio button is =" + catOrCustWiseRadioBVar.getValue());
        // System.out.println("catOrCustWiseRadioBVar.getValue() " + catOrCustWiseRadioBVar.getValue());
        if (catOrCustWiseRadioBVar.getValue().equals("P")) {
            BindingContainer bindings = getBindings();
            //OperationBinding setctName = bindings.getOperationBinding("setCatagoryName");
            //setctName.execute();

            selectcatOrEoWisePopUp.hide();
            return "Go";
        } else {
            catgPgMode = new StringBuilder("E");
            OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
            createOB.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(chngPrcFrRadioBVar);
            selectcatOrEoWisePopUp.hide();
            /*  editMode = false;
            addMode = true;
            cancelMode = false; */
            return null;
        }
    }

    public void setSelectcatOrEoWisePopUp(RichPopup selectcatOrEoWisePopUp) {
        this.selectcatOrEoWisePopUp = selectcatOrEoWisePopUp;
    }

    public RichPopup getSelectcatOrEoWisePopUp() {
        return selectcatOrEoWisePopUp;
    }

    public void commitEoWiseItem(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding commitEo = bindings.getOperationBinding("Commit");
        commitEo.execute();

    }
    private Timestamp glblEftDt = null;

    public void openEditOnTableRgtClk_ActionListener(ActionEvent actionEvent) {
        //  System.out.println("calling expiry date method");
        OperationBinding ExpDate = getBindings().getOperationBinding("getExirypDateofItm");
        ExpDate.execute();
        // System.out.println("called successfully");
        OperationBinding fnForDate = getBindings().getOperationBinding("setEffDate");
        fnForDate.execute();
        // System.out.println("value of fnfordate is ==" + fnForDate.getResult());
        if (fnForDate.getResult() != null) {
            glblEftDt = (Timestamp) fnForDate.getResult();
        }
        showPopup(editEoWiseItemPopUpBVar, true);

        // if(pop.equals("editEoWiseItemPopUpBVar")){
        // System.out.println("In Edit method----------");

        // System.out.println("Select Bind var is "+selectChangeTypeBVar);
        //System.out.println("Flag type is "+flagEoWiseEdit.getValue());
        //  selectChangeTypeBVar.setValue("A");
        // flagEoWiseEdit.setValue("A");
        //}
    }


    public void setEditEoWiseItemPopUpBVar(RichPopup editEoWiseItemPopUpBVar) {
        this.editEoWiseItemPopUpBVar = editEoWiseItemPopUpBVar;
    }

    public RichPopup getEditEoWiseItemPopUpBVar() {
        return editEoWiseItemPopUpBVar;
    }

    public void editEoWiseItemPopUp_CancelListener(PopupCanceledEvent popupCanceledEvent) {

    }

    private Integer count = 0;

    public void editEoWiseItemPopUp_DialogueOkListener(DialogEvent dialogEvent) {
        // System.out.println("out come--" + dialogEvent.getOutcome());
        Timestamp afterDate = null;
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            count = 1;
            flag = "E";
            if (newextraPriceValBVar.getValue() != null) {
                OperationBinding updateAmt = getBindings().getOperationBinding("editEoWiseItem");
                updateAmt.getParamsMap().put("amt", newextraPriceValBVar.getValue());
                updateAmt.getParamsMap().put("Flag", flagEoWiseEdit.getValue());
                updateAmt.getParamsMap().put("MrpType", selectChangeTypeBVar.getValue());
                updateAmt.getParamsMap().put("valOrAdd", "A");
                updateAmt.getParamsMap().put("setValOrNot", count);
                updateAmt.execute();
                OperationBinding chnageamount = getBindings().getOperationBinding("Commit");
                chnageamount.execute();
                OperationBinding execute = getBindings().getOperationBinding("executePticeMaster2Vo");
                execute.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
                /*   if (updateAmt.getResult() != null) {
                    Boolean b = (Boolean)updateAmt.getResult();
                    if (b) {
                        //  System.out.println("-------enter----" + b);
                        //FacesMessage message = new FacesMessage("MRP Price can not be less then Minimum Price..");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.973']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(eowiseMrpPriceBVar.getClientId(), message);
                    } else {
                        OperationBinding callFns2 = getBindings().getOperationBinding("fnsToEditEoWise");
                        callFns2.execute();
                        //  System.out.println("Return value from fns after inserting in history table before insertin gin if block --- " +
                        //callFns2.getResult());
                        if (callFns2.getResult() != null) {
                            String rst = (String)callFns2.getResult();
                            //   System.out.println("Return value from fns after inserting in history table --- " + rst);
                            if ("OK".equalsIgnoreCase(rst)) {
                                OperationBinding chnageamount = getBindings().getOperationBinding("Commit");
                                chnageamount.execute();
                                OperationBinding execute = getBindings().getOperationBinding("executePticeMaster2Vo");
                                execute.execute();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
                            } else {
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1167']}").toString()); //Data Alredy Exist in History table.Can Not Insert
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(eowiseMrpPriceBVar.getClientId(),
                                                                             message);
                            }
                        }
                    }
                }
                // }
                // } */
            } else {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1168']}").toString()); //Enter Amount Value..
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(newextraPriceValBVar.getClientId(), message);
            }
        }
        newextraPriceValBVar.setValue(null);
        newextraPriceValBVar.resetValue();
        flagEoWiseEdit.setValue("A");
        selectChangeTypeBVar.setValue("A");
        AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
    }

    public void setSelectChangeTypeBVar(RichSelectOneRadio selectChangeTypeBVar) {
        this.selectChangeTypeBVar = selectChangeTypeBVar;
    }

    public RichSelectOneRadio getSelectChangeTypeBVar() {
        return selectChangeTypeBVar;
    }

    public void setNewextraPriceValBVar(RichInputText newextraPriceValBVar) {
        this.newextraPriceValBVar = newextraPriceValBVar;
    }

    public RichInputText getNewextraPriceValBVar() {
        return newextraPriceValBVar;
    }

    public void setPriceMaster2TableBVar(RichTable priceMaster2TableBVar) {
        this.priceMaster2TableBVar = priceMaster2TableBVar;
    }

    public RichTable getPriceMaster2TableBVar() {
        return priceMaster2TableBVar;
    }

    public void setExecuteDistinctPmTableBVar(RichTable executeDistinctPmTableBVar) {
        this.executeDistinctPmTableBVar = executeDistinctPmTableBVar;
    }

    public RichTable getExecuteDistinctPmTableBVar() {
        return executeDistinctPmTableBVar;
    }

    public String backAction() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding executeDistinctPnVo = bindings.getOperationBinding("executeDistinctPnVo");
        executeDistinctPnVo.execute();
        /*  OperationBinding operationBindings = bindings.getOperationBinding("executeVo");
        operationBindings.execute(); */

        return "Back";
    }

    public void cancel_editpopupListener(ActionEvent actionEvent) {
        editMode = true;
        selectcatOrEoWisePopUp.hide();
    }

    public void save_ActionListener(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("updatePrice");
        binding.execute();
        Object o = binding.getResult();
        if (o.equals((Boolean) true)) {
            showPopup(savepopup, true);
        }
    }

    public void setSavepopup(RichPopup savepopup) {
        this.savepopup = savepopup;
    }

    public RichPopup getSavepopup() {
        return savepopup;
    }

    public void saveEditDatapopOkListener(ActionEvent actionEvent) {
        catgPgMode = new StringBuilder("V");
        this.getBindings().getOperationBinding("saveRecord").execute();
    }

    public void cancelSavePopupListener(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("cancelChanges").execute();
        savepopup.hide();
    }

    public void set_saveBVar(RichLink _saveBVar) {
        this._saveBVar = _saveBVar;
    }

    public RichLink get_saveBVar() {
        return _saveBVar;
    }

    public void set_addLinkNBar(RichLink _addLinkNBar) {
        this._addLinkNBar = _addLinkNBar;
    }

    public RichLink get_addLinkNBar() {
        return _addLinkNBar;
    }

    public void basePriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number d = (oracle.jbo.domain.Number) object;
            if (d.compareTo(0) == -1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Price Can not be Negative",
                                                              null)); */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.979']}").toString(), null));

            } else if (d.compareTo(0) == 0) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Base Price should be greater than zero.", null)); */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.980']}").toString(), null));


            }
        } else {

            // FacesMessage message = new FacesMessage("Please Enter  Price");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.997']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);

        }

    }

    public void setMinPrceBVar(RichInputText minPrceBVar) {
        this.minPrceBVar = minPrceBVar;
    }

    public RichInputText getMinPrceBVar() {
        return minPrceBVar;
    }

    public void set_deleteBVar(RichLink _deleteBVar) {
        this._deleteBVar = _deleteBVar;
    }

    public RichLink get_deleteBVar() {
        return _deleteBVar;
    }

    public void set_editLinkBVar(RichLink _editLinkBVar) {
        this._editLinkBVar = _editLinkBVar;
    }

    public RichLink get_editLinkBVar() {
        return _editLinkBVar;
    }

    public void checkDuplicateItm(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println(object.toString());
        /*  if (object != null) {
            String item_Name = (String)object;
            OperationBinding checkDupteFns = getBindings().getOperationBinding("isDuplicateItm");
            checkDupteFns.getParamsMap().put("itmName", item_Name);
            checkDupteFns.execute();
            Boolean b = false;
            if (checkDupteFns.getResult() != null) {
                b = (Boolean)checkDupteFns.getResult();
            }
            if (b) {
                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item Exist Already",
                                                              null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.983']}").toString(), null));
            }

        } */


    }

    public void setFlagBVar(RichSelectOneRadio flagBVar) {
        this.flagBVar = flagBVar;
    }

    public RichSelectOneRadio getFlagBVar() {
        return flagBVar;
    }

    /* public void MInPriceVLC(ValueChangeEvent valueChangeEvent) {
        System.out.println(minPrceBVar.getValue());
        if (minPrceBVar.getValue() == null || minPrceBVar.getValue().toString().length() <= 0) {
            FacesMessage message = new FacesMessage("Please Enter Min Price");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(minPrceBVar.getClientId(), message);

        }
    }*/

    public void setFlagEoWiseEdit(RichSelectOneRadio flagEoWiseEdit) {
        this.flagEoWiseEdit = flagEoWiseEdit;
    }

    public RichSelectOneRadio getFlagEoWiseEdit() {
        return flagEoWiseEdit;
    }
    private boolean editMode = true;
    private boolean addMode = false;
    private boolean cancelMode = true;

    public void cancelAction(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("cancelChanges").execute();
        catgPgMode = new StringBuilder("V");
    }

    public void setCancelLinkBVar(RichLink cancelLinkBVar) {
        this.cancelLinkBVar = cancelLinkBVar;
    }

    public RichLink getCancelLinkBVar() {
        return cancelLinkBVar;
    }

    public void MrpRateVLC(ValueChangeEvent valueChangeEvent) {
        // System.out.println("MRP RATE VLC");
        if (basePricebVar.getValue() == null || basePricebVar.getValue().toString().length() <= 0) {
            // FacesMessage message = new FacesMessage("Please Enter Base Price");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.984']}").toString());

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(basePricebVar.getClientId(), message);
            if (mrpRateBVar.getValue() != null || mrpRateBVar.getValue().toString().length() > 0) {
                mrpRateBVar.setValue(null);
                mrpRateBVar.resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar);
            }
        } else {

            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("setValuesforPM");
            op.getParamsMap().put("mrptype", mreTypeBind.getValue().toString());
            op.getParamsMap().put("mrprate", (oracle.jbo.domain.Number) mrpRateBVar.getValue());
            op.getParamsMap().put("basePrice", basePricebVar.getValue());
            op.execute();

            if (op.getResult() != null) {
                chk = op.getResult().toString();
                if (chk.equalsIgnoreCase("N")) {
                    /* FacesMessage message =
                        new FacesMessage("MRP price exceeds beyond its database precision limit .Please enter Appropriate  Base Price And MRP RateValue."); */
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.985']}").toString());

                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(mrpRateBVar.getClientId(), message);
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


    public void testVaidator() {

    }

    public void amountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            BigDecimal n = (BigDecimal) object;
            Number amt = new Number(0);
            try {
                amt = new Number(n);
            } catch (SQLException e) {
            }
            // System.out.println("AMLOIUNT ----" + amt);
            boolean precicionCkeck = isPrecisionValid(26, 6, amt);
            if (chnageAmountBVar.getValue().equals("A")) {
                if (amt.compareTo(Number.zero()) == -1) {
                    //   System.out.println("set 1");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1169']}").toString()); //Amount can not negative...
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (!precicionCkeck) {
                    //  System.out.println("set 2");
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1107']}").toString()); //Invalid precision(26,6).
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else {
                    // System.out.println("set 5");
                    //  System.out.println("Enter in els part,.");
                    OperationBinding Amt = getBindings().getOperationBinding("checkAmountVal");
                    Amt.getParamsMap().put("amt", amt);
                    Amt.getParamsMap().put("Flag", flagBVar.getValue());
                    Amt.getParamsMap().put("type", chnageAmountBVar.getValue());
                    Amt.getParamsMap().put("catId", trnsAddCatgIdBVar.getValue());
                    Amt.getParamsMap().put("partyId", searchPartyIdBvar.getValue());
                    Amt.getParamsMap().put("ItemName", ItemNm);
                    Amt.execute();

                    String b = (String) Amt.getResult();
                    if (b != null) {
                        // System.out.println(b);
                        if ("NO".equalsIgnoreCase(b)) {
                            //  System.out.println("rtn val  --" + b);
                            // FacesMessage message = new FacesMessage("MRP Price can not be less then Minimum Price..");
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.973']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        } else if ("I".equalsIgnoreCase(b)) {
                            // System.out.println("rtn val  --" + b);
                            FacesMessage message =
                                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1170']}").toString()); //MRP Price has invalid preccion for an Item.
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        }
                    }
                }

            } else {
                if (amt.compareTo(Number.zero()) == -1) {
                    // System.out.println("set 1");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1169']}").toString()); //Amount can not negative...
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (!precicionCkeck) {
                    // System.out.println("set 2");
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1107']}").toString()); //Invalid precision(26,6).
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (amt.compareTo(99.99) == 1) {
                    // System.out.println("set 4");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1171']}").toString()); //Percentage amount can not greater then 99.99%
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else {
                    // System.out.println("set 5");
                    // System.out.println("Enter in els part,.");
                    OperationBinding Amt = getBindings().getOperationBinding("checkAmountVal");
                    Amt.getParamsMap().put("amt", amt);
                    Amt.getParamsMap().put("Flag", flagBVar.getValue());
                    Amt.getParamsMap().put("type", chnageAmountBVar.getValue());
                    Amt.getParamsMap().put("catId", trnsAddCatgIdBVar.getValue());
                    Amt.getParamsMap().put("partyId", searchPartyIdBvar.getValue());
                    Amt.getParamsMap().put("ItemName", ItemNm);
                    Amt.execute();

                    String b = (String) Amt.getResult();
                    //System.out.println("rtn val  --" + b);
                    if (b != null) {
                        System.out.println(b);
                        if ("NO".equalsIgnoreCase(b)) {
                            // System.out.println("rtn val  --" + b);
                            // FacesMessage message = new FacesMessage("MRP Price can not be less then Minimum Price..");
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.973']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        } else if ("I".equalsIgnoreCase(b)) {
                            System.out.println("rtn val  --" + b);
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1170']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        }
                    }
                }
            }
        }
    }

    public void amoutnValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        count = 2;

        if (object != null && flagEoWiseEdit.getValue() != null && selectChangeTypeBVar.getValue() != null) {
            BigDecimal val = (BigDecimal) object;
            //System.out.println("Inside Validator-" + val);
            Number amt = new Number(0);
            try {
                amt = new Number(val);
            } catch (SQLException e) {
                // System.out.println("IN catch " + e);
            }
            // System.out.println("Parameter isn method--" + amt + "flag-" + flagEoWiseEdit.getValue() + "type-" +
            // selectChangeTypeBVar.getValue());
            boolean checkPreccision = isPrecisionValid(26, 6, amt);
            //  System.out.println("checkPreccision " + checkPreccision);
            if (checkPreccision) {
                if (amt.compareTo(0) == -1) {
                    //FacesMessage message = new FacesMessage("Price Can not be negative..");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.979']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (amt.compareTo(0) == 0) {
                    //FacesMessage message = new FacesMessage("Price should be greater than zero.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }


                else {

                    if (selectChangeTypeBVar.getValue().equals("P")) {
                        //System.out.println("SET 1212");
                        if (amt.compareTo(99.99) == 1) {
                            //  System.out.println("SET 1212");
                            // System.out.println("SET aa1212");
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.991']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);
                        }
                        // System.out.println("SET out");
                    }


                    //   System.out.println("Parameter isn method--" + amt + "flag-" + flagEoWiseEdit.getValue() + "type-" +
                    // selectChangeTypeBVar.getValue());
                    // System.out.println("In else block------ calling am method");
                    OperationBinding updateAmt = getBindings().getOperationBinding("editEoWiseItem");
                    updateAmt.getParamsMap().put("amt", amt);
                    updateAmt.getParamsMap().put("Flag", flagEoWiseEdit.getValue());
                    updateAmt.getParamsMap().put("MrpType", selectChangeTypeBVar.getValue());
                    updateAmt.getParamsMap().put("valOrAdd", "V");
                    updateAmt.getParamsMap().put("setValOrNot", count);
                    updateAmt.execute();

                    if (updateAmt.getResult() != null) {
                        Boolean b = (Boolean) updateAmt.getResult();
                        //System.out.println("Flag from method--" + b);
                        if (b) {
                            //  System.out.println("-------enter----" + b);
                            //FacesMessage message = new FacesMessage("MRP Price can not be less then Minimum Price..");
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.973']}").toString());

                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message);

                        } else {
                            /*   DCIteratorBinding itr2 = (DCIteratorBinding)getBindings().get("PriceMaster2Iterator");
                            ViewObject pmVO2 = itr2.getViewObject();
                            String amtOrPer = (String)selectChangeTypeBVar.getValue();
                            String flag = (String)flagEoWiseEdit.getValue();
                            Number oldPrc = (Number)eowiseMrpPriceBVar.getValue();
                            System.out.println("amtOrPer=" + amtOrPer + "===========" + "flag=" + flag + " amt=" +
                                               amt + "old price " + oldPrc);
                            if ("A".equalsIgnoreCase(amtOrPer)) {
                                if ("A".equalsIgnoreCase(flag)) {
                                    System.out.println("oldPrc.add(value)" + oldPrc.add(amt));
                                    pmVO2.getCurrentRow().setAttribute("MrpPrice", oldPrc.add(amt));
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(eowiseMrpPriceBVar);
                                } else {
                                    System.out.println("oldPrc.add(value)" + oldPrc.subtract(amt));
                                    pmVO2.getCurrentRow().setAttribute("MrpPrice", oldPrc.subtract(amt));
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(eowiseMrpPriceBVar);
                                }
                            } else {
                                if ("A".equalsIgnoreCase(flag)) {
                                    Number a = ((oldPrc.multiply(amt)).divide(new Number(100)));
                                    System.out.println("a " + a);
                                    System.out.println("oldPrc.add(value)" + oldPrc.add(a));
                                    pmVO2.getCurrentRow().setAttribute("MrpPrice", oldPrc.add(a));
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(eowiseMrpPriceBVar);
                                } else {
                                    Number bc = ((oldPrc.multiply(amt)).divide(new Number(100)));
                                    System.out.println("a " + bc);
                                    System.out.println("oldPrc.add(value)" + oldPrc.subtract(bc));
                                    pmVO2.getCurrentRow().setAttribute("MrpPrice", oldPrc.subtract(bc));
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(eowiseMrpPriceBVar);
                                }
                            }
                          */

                        }
                    }
                }
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.57']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }


        } else {
            // FacesMessage message = new FacesMessage("Please enter amount.");resolvElDCMsg("#{bundle['MSG.977']}").toString()
            FacesMessage message =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1172']}").toString()); //You Did not select either Type or Add/Sub or Amount Value
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    public void minPrcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number) object;
            // System.out.println("Amount value---" + n);
            // System.out.println("condition --" + n.compareTo(0));
            if (n.compareTo(0) == -1) {
                //  System.out.println("amout is negative....");
                // FacesMessage message = new FacesMessage("Price can not be negative.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.979']}").toString());

                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            } else if (n.compareTo(0) == 0) {
                // System.out.println("in set 1");
                //FacesMessage message = new FacesMessage("Price should be greater than zero");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1048']}").toString());

                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else {
                /*   OperationBinding oop = getBindings().getOperationBinding("isBasePriceValid");
                oop.getParamsMap().put("_obj", n);
                oop.execute(); */
                Number basePrice = (Number) basePricebVar.getValue();
                //  System.out.println("basePrice " + basePrice);
                if (basePrice != null) {
                    if (n.compareTo(basePrice) == -1) {

                        // System.out.println("value of validator is in the="+validator);
                        /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Min PRice should be greater then Base  price...",
                                                                      null));  */
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.982']}").toString(),
                                                                      null));
                    }
                }
            }
        } else {
            // FacesMessage message = new FacesMessage("Please Enter  Price");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.997']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }


    }

    public void mrpPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number) object;
            Number minPrice = (Number) minPrceBVar.getValue();
            Number basePrice = (Number) basePricebVar.getValue();
            Number finalMrp = new Number(0);
            String mrpType = (String) mreTypeBind.getValue();
            // System.out.println(" mrp type  " + mrpType);
            if ("A".equalsIgnoreCase(mrpType)) {
                if (n.compareTo(0) < 0) {
                    // System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.999']}").toString()); //MRP Rate can not be either Zero or negative.
                    // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else {
                    if (basePrice != null)
                        finalMrp = basePrice.add(n);
                    //   System.out.println("finanal sum  " + finalMrp);
                }
            } else if ("P".equalsIgnoreCase(mrpType)) {
                if (n.compareTo(0) == 0) {
                    //  System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.999']}").toString());
                    // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (n.compareTo(99.99) > 1) {
                    throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1174']}").toString())); //MRP Rate should be less then 99.99%..
                } else {
                    //   System.out.println("set if 99.99 % " + n.compareTo(99.99));
                    finalMrp = basePrice.add((basePrice.multiply(n).divide(new Number(100))));
                    // System.out.println("finanal sum  " + finalMrp);
                }
            }
            if (minPrice != null) {
                if (finalMrp.compareTo(minPrice) ==
                    -1) {
                    //  System.out.println("finanal sum in last  " + finalMrp);
                    throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.253']}").toString(),
                                                                  resolvElDCMsg("#{bundle['MSG.1175']}").toString()));
                }
            } else {
                throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1164']}").toString()));
            }


        }
    }

    public void typeRGVCL(ValueChangeEvent vcl) {
        if (vcl.getNewValue() != null) {
            if (amountchangeBVar.getValue() != null && amountchangeBVar.getValue().toString().length() > 0) {
                amountchangeBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(amountchangeBVar);
            }
            if (efctivDateBVar.getValue() != null && efctivDateBVar.getValue().toString().length() > 0) {
                efctivDateBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(efctivDateBVar);
            }
            if (expiryDateBVar.getValue() != null && expiryDateBVar.getValue().toString().length() > 0) {
                expiryDateBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(expiryDateBVar);
            }

        }
    }

    public void addsubRGVCL(ValueChangeEvent vcl) {
        if (vcl.getNewValue() != null) {
            if (amountchangeBVar.getValue() != null && amountchangeBVar.getValue().toString().length() > 0) {
                amountchangeBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(amountchangeBVar);
            }
            if (efctivDateBVar.getValue() != null && efctivDateBVar.getValue().toString().length() > 0) {
                efctivDateBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(efctivDateBVar);
            }
            if (expiryDateBVar.getValue() != null && expiryDateBVar.getValue().toString().length() > 0) {
                expiryDateBVar.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(expiryDateBVar);
            }
        }
    }

    public void setTransItmNameBVar(RichInputListOfValues transItmNameBVar) {
        this.transItmNameBVar = transItmNameBVar;
    }

    public RichInputListOfValues getTransItmNameBVar() {
        return transItmNameBVar;
    }

    public void setSearchedtransItmIdBVar(RichInputText searchedtransItmIdBVar) {
        this.searchedtransItmIdBVar = searchedtransItmIdBVar;
    }

    public RichInputText getSearchedtransItmIdBVar() {
        return searchedtransItmIdBVar;
    }

    public void setTsitmNmBVar(RichInputListOfValues tsitmNmBVar) {
        this.tsitmNmBVar = tsitmNmBVar;
    }

    public RichInputListOfValues getTsitmNmBVar() {
        return tsitmNmBVar;
    }

    public void setTsitmIdBVar(RichInputText tsitmIdBVar) {
        this.tsitmIdBVar = tsitmIdBVar;
    }

    public RichInputText getTsitmIdBVar() {
        return tsitmIdBVar;
    }

    public void setSearchPanelBVar(RichPanelFormLayout searchPanelBVar) {
        this.searchPanelBVar = searchPanelBVar;
    }

    public RichPanelFormLayout getSearchPanelBVar() {
        return searchPanelBVar;
    }

    public void setCatgPnaelformBVar(RichPanelGroupLayout catgPnaelformBVar) {
        this.catgPnaelformBVar = catgPnaelformBVar;
    }

    public RichPanelGroupLayout getCatgPnaelformBVar() {
        return catgPnaelformBVar;
    }

    public void setSepecifItmFrmBVar(RichPanelFormLayout sepecifItmFrmBVar) {
        this.sepecifItmFrmBVar = sepecifItmFrmBVar;
    }

    public RichPanelFormLayout getSepecifItmFrmBVar() {
        return sepecifItmFrmBVar;
    }

    public void setTypeAndAmountFormBVar(RichPanelFormLayout typeAndAmountFormBVar) {
        this.typeAndAmountFormBVar = typeAndAmountFormBVar;
    }

    public RichPanelFormLayout getTypeAndAmountFormBVar() {
        return typeAndAmountFormBVar;
    }

    public void setEfftvDtpanelFormBVar(RichPanelFormLayout efftvDtpanelFormBVar) {
        this.efftvDtpanelFormBVar = efftvDtpanelFormBVar;
    }

    public RichPanelFormLayout getEfftvDtpanelFormBVar() {
        return efftvDtpanelFormBVar;
    }

    public void setEowiseMrpPriceBVar(RichInputText eowiseMrpPriceBVar) {
        this.eowiseMrpPriceBVar = eowiseMrpPriceBVar;
    }

    public RichInputText getEowiseMrpPriceBVar() {
        return eowiseMrpPriceBVar;
    }

    public void setEoWiseEfftvDt(RichInputDate eoWiseEfftvDt) {
        this.eoWiseEfftvDt = eoWiseEfftvDt;
    }

    public RichInputDate getEoWiseEfftvDt() {
        return eoWiseEfftvDt;
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
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void searchEoWiseItmAction(ActionEvent actionEvent) {
        OperationBinding seAarch = getBindings().getOperationBinding("searchEoWiseItmAction");
        seAarch.execute();
    }

    public void setCatgIdInEoPgBVar(RichSelectOneChoice catgIdInEoPgBVar) {
        this.catgIdInEoPgBVar = catgIdInEoPgBVar;
    }

    public RichSelectOneChoice getCatgIdInEoPgBVar() {
        return catgIdInEoPgBVar;
    }

    public void setEoNmInEoPgBVar(RichInputListOfValues eoNmInEoPgBVar) {
        this.eoNmInEoPgBVar = eoNmInEoPgBVar;
    }

    public RichInputListOfValues getEoNmInEoPgBVar() {
        return eoNmInEoPgBVar;
    }

    public void rsetEoWiseAction(ActionEvent actionEvent) {
        OperationBinding reset = getBindings().getOperationBinding("rsetEoWiseAction");
        reset.execute();
    }

    public void setResetLinkBVar(RichLink resetLinkBVar) {
        this.resetLinkBVar = resetLinkBVar;
    }

    public RichLink getResetLinkBVar() {
        return resetLinkBVar;
    }


    public void typeRadioVCL(ValueChangeEvent valueChangeEvent) {
        /*  if (valueChangeEvent.getNewValue() != null && newextraPriceValBVar.getValue() != null) {
            System.out.println("set1");
            newextraPriceValBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
        } */
    }

    public void addsubRadioVCL(ValueChangeEvent valueChangeEvent) {

        /*  if (valueChangeEvent.getNewValue() != null && newextraPriceValBVar.getValue() != null) {
            System.out.println("set2");
            newextraPriceValBVar.setValue("");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar); */
    }


    public void editCutomerPriceAction(ActionEvent actionEvent) {
        OperationBinding getExpDate = getBindings().getOperationBinding("getExirypDateofItm");
        getExpDate.execute();
        OperationBinding fnForDate = getBindings().getOperationBinding("setEffDate");
        fnForDate.execute();
        // System.out.println("value of fnfordate is ==" + fnForDate.getResult());
        if (fnForDate.getResult() != null) {
            glblEftDt = (Timestamp) fnForDate.getResult();
        }
        showPopup(editEoWiseItemPopUpBVar, true);
        // System.out.println("In Edit method----------");
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setCancelMode(boolean cancelMode) {
        this.cancelMode = cancelMode;
    }

    public boolean isCancelMode() {
        return cancelMode;
    }

    public void basePriceVCL(ValueChangeEvent valueChangeEvent) {
        Number basePrice = (Number) valueChangeEvent.getNewValue();
        DCIteratorBinding itr = (DCIteratorBinding) getBindings().get("PriceMaster1Iterator");
        //   System.out.println("=====" + basePrice);
        ViewObject vo = itr.getViewObject();
        Number minPrice = (Number) minPrceBVar.getValue();
        Number mrprate = (Number) mrpRateBVar.getValue();
        if (basePrice != null) {
            vo.getCurrentRow().setAttribute("MinPrice", basePrice);
            vo.getCurrentRow().setAttribute("MrpRate", new Number(0));
            vo.getCurrentRow().setAttribute("MrpPrice", basePrice);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minPrceBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(mrpPriceBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar);
        }
        /*
        System.out.println("min price and mrp rate " + minPrice + " " + mrprate);
        if (minPrice != null) {
            System.out.println("set 1");
            if (minPrice.compareTo(0) > 0) {
                System.out.println("set 2");
                vo.getCurrentRow().setAttribute("MinPrice", new Number(0));
                vo.getCurrentRow().setAttribute("MrpPrice", new Number(0));
                AdfFacesContext.getCurrentInstance().addPartialTarget(minPrceBVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(mrpPriceBVar);
            }
        }
        if (mrprate != null) {
            System.out.println("set 3");
            if (mrprate.compareTo(0) > 0) {
                System.out.println("set 4");
                vo.getCurrentRow().setAttribute("MrpRate", new Number(0));
                // vo.getCurrentRow().setAttribute("MrpPrice", new Number(0));
                AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar);
                // AdfFacesContext.getCurrentInstance().addPartialTarget(mrpPriceBVar);
            }
        } */

    }

    public void mrpType_ChangeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar);
        //  System.out.println("valueChangeEvent mrpType_ChangeListener " + valueChangeEvent.getNewValue());
        DCIteratorBinding itr = (DCIteratorBinding) getBindings().get("PriceMaster1Iterator");
        //  System.out.println("=====");
        ViewObject vo = itr.getViewObject();
        String mrptype = (String) valueChangeEvent.getNewValue();
        //  System.out.println("mrp type mrpType_ChangeListener" + mrptype);
        String check = "Y";
        Number mrprate = (Number) mrpRateBVar.getValue();
        Number minPrice = (Number) minPrceBVar.getValue();
        Number basePrice = (Number) basePricebVar.getValue();
        Number finalMrpPrice = new Number(0);
        // System.out.println("min price and mrp rate  " + mrprate + " basse price " + basePrice + " minPrice " +
        //                    minPrice);
        if (basePrice != null && basePrice.compareTo(0) > 0) {
            if (mrprate != null && mrprate.compareTo(0) > 0) {
                if ("A".equalsIgnoreCase(mrptype)) {
                    check = "Y";
                    //   System.out.println("a " + basePrice.add(mrprate));
                    finalMrpPrice = basePrice.add(mrprate);
                    //vo.getCurrentRow().setAttribute("MrpPrice", basePrice.add(mrprate));
                } else if ("P".equalsIgnoreCase(mrptype)) {
                    if (mrprate.compareTo(99.99) > 0) {
                        check = "N";
                        /*   FacesMessage message =
                            new FacesMessage("Rate should be less then 99.99 % "); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(mrpRateBVar.getClientId(), message);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar); */
                    } else {
                        check = "Y";
                        Number total = (Number) basePrice.multiply(mrprate).divide(new Number(100));
                        finalMrpPrice = basePrice.add(total);
                        System.out.println("p " + basePrice.add(total) + " total " + total + "finalMrpPrice " +
                                           finalMrpPrice);
                    }

                    //vo.getCurrentRow().setAttribute("MrpPrice", basePrice.add(total));
                }
            }
        }
        if (minPrice != null && minPrice.compareTo(0) > 0) {
            if (mrprate != null && mrprate.compareTo(0) > 0) {
                if (finalMrpPrice.compareTo(minPrice) == -1) {
                    // System.out.println("check ckeck");
                    /*  FacesMessage message =
                        new FacesMessage("Rate should be less then 99.99 % "); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(mrpRateBVar.getClientId(), message);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar); */



                } else if ("Y".equalsIgnoreCase(check)) {
                    // System.out.println("setting attribute " + finalMrpPrice);
                    vo.getCurrentRow().setAttribute("MrpPrice", finalMrpPrice);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(mrpRateBVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(mrpPriceBVar);
                }
            }
        }


    }

    public void setHistoryPopUp(RichPopup historyPopUp) {
        this.historyPopUp = historyPopUp;
    }

    public RichPopup getHistoryPopUp() {
        return historyPopUp;
    }

    public void viewHistPriceAction(ActionEvent actionEvent) {
        OperationBinding oop = getBindings().getOperationBinding("viewOldPrice");
        oop.execute();
        String type = "All";
        if (oop.getResult() != null) {
            type = oop.getResult().toString();
        }
        if (type.equalsIgnoreCase("S")) {
            setShowTable("Single");
        } else if (type.equalsIgnoreCase("A")) {
            setShowTable("All");
        }
        showPopup(historyPopUp, true);
    }


    public void setCatagSearchBVar(RichSelectOneChoice catagSearchBVar) {
        this.catagSearchBVar = catagSearchBVar;
    }

    public RichSelectOneChoice getCatagSearchBVar() {
        return catagSearchBVar;
    }

    public void setTransAddCatagNmBVar(RichSelectOneChoice transAddCatagNmBVar) {
        this.transAddCatagNmBVar = transAddCatagNmBVar;
    }

    public RichSelectOneChoice getTransAddCatagNmBVar() {
        return transAddCatagNmBVar;
    }

    public void setItmDisable(boolean itmDisable) {
        this.itmDisable = itmDisable;
    }

    public boolean isItmDisable() {
        return itmDisable;
    }

    public void setExpiryDateBVar(RichInputDate expiryDateBVar) {
        this.expiryDateBVar = expiryDateBVar;
    }

    public RichInputDate getExpiryDateBVar() {
        return expiryDateBVar;
    }


    private java.util.Date newEftivDtfroItm;


    public void setNewEftivDtfroItm(java.util.Date newEftivDtfroItm) {
        this.newEftivDtfroItm = newEftivDtfroItm;
    }

    public java.util.Date getNewEftivDtfroItm() {
        return newEftivDtfroItm;
    }


    public void catWiseEftvDateVCL(ValueChangeEvent vcl) {
        if (vcl.getNewValue() != null) {
            System.out.println("changed value of date " + vcl.getNewValue());
        }
    }

    public void custWiseTypeVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
        // Number newMrpRate = (Number);
        // System.out.println("new value " + newextraPriceValBVar.getValue());
        if (newextraPriceValBVar.getValue() != null) {
            newextraPriceValBVar.resetValue();
            newextraPriceValBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
        }
    }

    public void addOrSucVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
        //Number newMrpRate = (Number)newextraPriceValBVar.getValue();
        //System.out.println("new value " + newextraPriceValBVar.getValue());
        if (newextraPriceValBVar.getValue() != null) {
            newextraPriceValBVar.resetValue();
            newextraPriceValBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(newextraPriceValBVar);
        }
    }


    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void exportExcelAction(ActionEvent actionEvent) {
        DCBindingContainer container = (DCBindingContainer) getBindings();
        JUCtrlHierBinding obj = (JUCtrlHierBinding) container.findCtrlBinding("DistinctPriceMasterVo1");
        ViewObject object = obj.getViewObject();

        DistinctPriceMasterVoImpl view = (DistinctPriceMasterVoImpl) object;

        Map<String, String> para = new HashMap<String, String>();
        Object[] clause = object.getWhereClauseParams();
        for (Object par : clause) {
            Object[] nameValue = (Object[]) par;
            String name = (String) nameValue[0];
            Object value = nameValue[1];
            if (value == null) {
                para.put(name, null);
            } else {
                para.put(name, value.toString());
            }
        }
        String query = view.getQuery();

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext context = ctx.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) context.getRequest();
        HttpSession httpSession = req.getSession();

        httpSession.setAttribute("param", para);
        httpSession.setAttribute("query", query);

        ExtendedRenderKitService erk = Service.getRenderKitService(ctx, ExtendedRenderKitService.class);
        StringBuilder script = new StringBuilder();
        script.append("window.open(\"ExcelServlet\",'_blank');");
        erk.addScript(ctx, script.toString());
    }

    public void excelImportAction(ActionEvent actionEvent) {
        System.out.println("value is;  " + transAddCatagNmBVar.getValue());
        if (transAddCatagNmBVar.getValue() == null || transAddCatagNmBVar.getValue().toString().equalsIgnoreCase("")) {
            System.out.println("Came in validate");
            FacesMessage message =
                new FacesMessage(resolvEl("#{bundle['MSG.1788']}")); //new FacesMessage("Please Select Category !!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            setFile(null);
            return;
        }
        try {
            UploadedFile file_2 = this.getFile();
            if (file_2 == null) {
                FacesMessage message =
                    new FacesMessage("#{bundle['MSG.1701']}"); //new FacesMessage("Please Select a File..");
                //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(inputFileBind.getClientId(), message);
                return;
            }

            InputStream inputStream = file_2.getInputStream();
            String type = file_2.getContentType();
            String name = file_2.getFilename();
            //            int val = name.lastIndexOf(type);
            int val = name.lastIndexOf(".");
            String fileType = name.substring(val + 1, name.length());
            System.out.println("file type is:  " + fileType);
            System.out.println("file type is:  " + type + "\n" + name);

            if (fileType.equalsIgnoreCase("XLS")) {
                //   System.out.println("File type: xls");
                HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
                HSSFSheet at = workbook.getSheetAt(0);
                int rowCount = at.getPhysicalNumberOfRows();
                //  System.out.println("Value of row and column are:  " + at.getPhysicalNumberOfRows() + "  " +
                //       at.getRow(0).getPhysicalNumberOfCells());

                Map<String, List<Object>> map = new HashMap<String, List<Object>>();

                outer:
                for (int i = 1; i < rowCount; i++) {
                    HSSFRow row = at.getRow(i);
                    List<Object> list = new ArrayList<Object>();

                    if (row != null) {
                        if (row.getPhysicalNumberOfCells() < 6) {
                            callInsertDummy(row);
                            continue outer;
                        }
                        inner:
                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                            Cell cell = row.getCell(j);
                            if ((!(j == (row.getPhysicalNumberOfCells() - 1))) && cell == null) {
                                callInsertDummy(row);
                                continue outer;
                            }

                            if (j == 0 && cell.getCellType() != 1) {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 1 && cell.getCellType() != 0) {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 2 && cell.getCellType() != 0) {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 3 && cell.getCellType() != 0) {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 4 && cell.getCellType() != 1) {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 5 &&
                                (cell.getCellType() != 1 || getConvertDate(cell.getStringCellValue()) == null)) {
                                callInsertDummy(row);
                                continue outer;
                            } else if (j == 5) {
                                String cellValue = cell.getStringCellValue();
                                Date d = new Date(System.currentTimeMillis());
                                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                                String format_2 = format.format(d);
                                if (!cellValue.equalsIgnoreCase(format_2)) {
                                    Date stDate = getConvertDate(cellValue);
                                    if (stDate.compareTo(d) < 0) {
                                        System.out.println("Less start..");
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                            }
                            if (j == 6 &&
                                ((cell.getCellType() != 1 || getConvertDate(cell.getStringCellValue()) == null) &&
                                 cell.getCellType() != 3)) {
                                callInsertDummy(row);
                                continue outer;
                            } else if (j == 6 && cell.getCellType() != 3) {
                                HSSFCell cell_2 = row.getCell(5);

                                if (!(cell_2.toString().equalsIgnoreCase(cell.getStringCellValue()))) {
                                    Date lastDate = this.getConvertDate(cell.getStringCellValue());
                                    Date starttDate = this.getConvertDate(cell_2.getStringCellValue());
                                    System.out.println("comparisio is : " + lastDate.compareTo(starttDate));
                                    if (lastDate.compareTo(starttDate) < 0) {
                                        System.out.println("Came in the greater date...");
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                            }

                            if (j == 0) {

                                if (map.containsKey(cell.getStringCellValue())) {
                                    callInsertDummy(row);
                                    continue outer;
                                }

                                OperationBinding binding = getBindings().getOperationBinding("CheckValidItmId");
                                binding.getParamsMap().put("itmId", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null) {
                                    String obj = (String) binding.getResult();
                                    //   System.out.println("Id is valid : " + obj);
                                } else {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }
                            if (j == 4) {
                                if ((cell.getStringCellValue()).equals("A")) {
                                    Double base = row.getCell(1).getNumericCellValue();
                                    Double minimum = row.getCell(2).getNumericCellValue();
                                    Double mrpRate = row.getCell(3).getNumericCellValue();
                                    //if(row.getCell(6) == null) continue outer;

                                    if (base == 0 || minimum == 0) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                    if (minimum < base) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                    if ((base + mrpRate) < minimum) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                } else if ((cell.getStringCellValue()).equals("P")) {
                                    Double base = row.getCell(1).getNumericCellValue();
                                    Double minimum = row.getCell(2).getNumericCellValue();
                                    Double mrpRate = row.getCell(3).getNumericCellValue();

                                    if (base == 0 || minimum == 0) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                    if (mrpRate > 99.99) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                    if (minimum < base) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }

                                    if ((((base * mrpRate) / 100) + base) < minimum) {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                } else {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }
                        }
                        String keyItmId = "";
                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                            Cell cell = row.getCell(j);

                            switch (cell.getCellType()) {
                            case 0: //numeric
                                list.add(cell.getNumericCellValue());
                                break;
                            case 1: //String
                                if (j == 0) {
                                    keyItmId = cell.getStringCellValue();
                                } else {
                                    list.add(cell.getStringCellValue());
                                }
                                break;
                            case 3: //blank
                                list.add(null);
                                break;
                            }
                        }
                        map.put(keyItmId, list);
                    }
                }
                // System.out.println("Value of map is:  " + map);
                Set<String> keySet = map.keySet();


                for (String key : keySet) {
                    List<Object> list = map.get(key);
                    Map<String, Object> valMap = new HashMap<String, Object>();
                    valMap.put("itemID", key);

                    for (int i = 0; i < list.size(); i++) {
                        switch (i) {

                            /* case 0:
                            valMap.put("itmUom", null); //list.get(i)
                            break; */
                        case 0:
                            valMap.put("basePrice", list.get(i));
                            break;
                        case 1:
                            valMap.put("MinPrice", list.get(i));
                            break;
                        case 2:
                            valMap.put("mrpRate", list.get(i));
                            break;
                        case 3:
                            valMap.put("mrpTyp", list.get(i));
                            break;
                            /*  case 4:
                            valMap.put("mrpPrice", list.get(i));
                            break; */
                        case 4:
                            //   System.out.println("The date is: " + getConvertDate((String)list.get(i)));
                            if (list.get(i) != null)
                                valMap.put("nwtime", getConvertDate((String) list.get(i)));
                            else
                                valMap.put("nwtime", null);
                            // valMap.put("nwtime", new Date());
                            break;
                        case 5:
                            //   System.out.println("The expiry date is: " + getConvertDate((String)list.get(i)));
                            if (list.get(i) != null)
                                valMap.put("ddDate", getConvertDate((String) list.get(i)));
                            else
                                valMap.put("ddDate", null);
                            //valMap.put("ddDate", new Date());
                            break;
                        }
                    }
                    valMap.put("Eoid", new Integer(0));
                    // valMap.put("catagId", transAddCatagNmBVar.getValue().toString());
                    valMap.put("priceApply", "N");

                    OperationBinding binding = getBindings().getOperationBinding("addEntryInDBFromExcel");
                    binding.getParamsMap().putAll(valMap);
                    binding.execute();

                }

                getBindings().getOperationBinding("Commit").execute();
                getBindings().getOperationBinding("executeVo").execute();
                getBindings().getOperationBinding("executeDistinctPnVo").execute(); //executeVo

            } /* else if (fileType.equalsIgnoreCase("XLSX")) {
                //System.out.println("File type: xlsX");
            } */ else {
                FacesMessage message =
                    new FacesMessage(resolvEl("#{bundle['MSG.1789']}")); //new FacesMessage("Invalid File Type!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(mrpPriceBVar.getClientId(), message);
            }

        } catch (Exception e) {
            FacesMessage message =
                new FacesMessage(resolvEl("#{bundle['MSG.1790']}")); // new FacesMessage("Please Select File Again or File is corrupted.."); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(mrpPriceBVar.getClientId(), message);
            e.printStackTrace();

        }
        setFile(null);
    }

    private java.util.Date getConvertDate(String str) {
        java.util.Date d = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            d = format.parse(str);
        } catch (Exception e) {
            // System.out.println("Error coming during parse");
            e.printStackTrace();
        }
        return d;
    }

    public void setInputFileBind(RichInputFile inputFileBind) {
        this.inputFileBind = inputFileBind;
    }

    public RichInputFile getInputFileBind() {
        return inputFileBind;
    }

    public void downloadSampleAction(FacesContext facesContext, OutputStream outputStream) throws IOException {
        Object execute = getBindings().getOperationBinding("getPath").execute();
        if (execute != null) {
            String path = new String((StringBuffer) execute);
            File file = new File(path + "\\sample.xls");
            InputStream in = new FileInputStream(file);
            byte b[] = new byte[in.available()];
            in.read(b);
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
        }
    }

    public void setExceldownloadBind(RichCommandButton exceldownloadBind) {
        this.exceldownloadBind = exceldownloadBind;
    }

    public RichCommandButton getExceldownloadBind() {
        return exceldownloadBind;
    }

    public void setShowTable(String showTable) {
        this.showTable = showTable;
    }

    public String getShowTable() {
        return showTable;
    }
    private String grpNm = null;

    public void ItmGrpVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);
    }

    public void setItmGrpNmBind(RichSelectOneChoice itmGrpNmBind) {
        this.itmGrpNmBind = itmGrpNmBind;
    }

    public RichSelectOneChoice getItmGrpNmBind() {
        return itmGrpNmBind;
    }

    public void setItmGrpDisable(boolean itmGrpDisable) {
        this.itmGrpDisable = itmGrpDisable;
    }

    public boolean isItmGrpDisable() {
        return itmGrpDisable;
    }

    public void ratechangeForVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("filterEoPriceForCustomer").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
    }

    public void ItmGrpEoVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("filterEoPriceForCustomer").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
    }

    public void ItmNmEoVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("filterEoPriceForCustomer").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
    }

    public void setEoPriceMode(StringBuffer eoPriceMode) {
        this.eoPriceMode = eoPriceMode;
    }

    public StringBuffer getEoPriceMode() {
        return eoPriceMode;
    }

    public void editEoPriceACTION(ActionEvent actionEvent) {
        eoPriceMode = new StringBuffer("E");
        custWiseTable.setRowSelection("none");
        currentDate = new Timestamp(System.currentTimeMillis() - 60000);
    }

    public void saveEoPriceACTION(ActionEvent actionEvent) {
        if(upload_Mode.equalsIgnoreCase("A")){
            OperationBinding saveBinding = this.getBindings().getOperationBinding("saveEoWisePrice");
            saveBinding.execute();
            Object ret = saveBinding.getResult();
            if (ret.equals((Object) true)) {
                OperationBinding commitBinding = this.getBindings().getOperationBinding("Commit");
                commitBinding.execute();
                if (commitBinding.getErrors().isEmpty()) {
                    custWiseTable.setRowSelection("single");
                    FacesMessage message =
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1791']}").toString()); // new FacesMessage("Price changed Successfully !"); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    eoPriceMode = new StringBuffer("V");
                    this.getBindings().getOperationBinding("resetValonSaveEoWisePrice").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
                }
            } /* else{
                FacesMessage message =
                    new FacesMessage("There were some unexpected error ! Please try again !"); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }  */   
        }
        else if (upload_Mode.equalsIgnoreCase("V")){
            OperationBinding commitBinding = this.getBindings().getOperationBinding("Commit");
            commitBinding.execute();
            if (commitBinding.getErrors().isEmpty()) {
                custWiseTable.setRowSelection("single");
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1791']}").toString()); // new FacesMessage("Price changed Successfully !"); //resolvElDCMsg("#{bundle['MSG.973']}").toString()
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, message);
                eoPriceMode = new StringBuffer("V");
                upload_Mode="A";
                this.getBindings().getOperationBinding("resetValonSaveEoWisePrice").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
            }
          
        }
       
    }

    public void cancelEoPriceACTION(ActionEvent actionEvent) {
        eoPriceMode = new StringBuffer("V");
        this.getBindings().getOperationBinding("Rollback").execute();
        OperationBinding reset = getBindings().getOperationBinding("rsetEoWiseAction");
        reset.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(priceMaster2TableBVar);
    }

    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void negativeNumberVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            int i = ((Number) object).compareTo((Object) new Number(0));
            if (i ==
                -1) {
                // FacesMessage message = new FacesMessage("Invalid number!","Cannot enter negative value!");
                FacesMessage message =
               new FacesMessage(resolvElDCMsg("#{bundle['MSG.1002']}").toString(),
                                resolvElDCMsg("#{bundle['MSG.1003']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (!isPrecisionValid(26, 6, (Number) object)) {
                // FacesMessage message = new FacesMessage("Invalid number!","Cannot enter negative value!");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1266']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public Timestamp getCurrentDate() {
        return currentDate;
    }

    public void callInsertDummy(HSSFRow row) {
        int count = row.getPhysicalNumberOfCells();
        boolean flag = false;
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 7; i++) {
            HSSFCell cell = row.getCell(i);
            list.add(cell);
            if ((!flag) && cell != null) {
                flag = true;
            }
        }

        /*   ListIterator<Object> iterator = list.listIterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next != null) {
                flag = true;
                break;
            }
        } */
        if (flag) {
            OperationBinding binding = getBindings().getOperationBinding("insertinDumpData");
            binding.getParamsMap().put("list", list);
            binding.execute();
        }
    }


    public void addItemDialougeListnerDL(DialogEvent dialogEvent) {
        // Add event code here...addItemPrice
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {


        }
    }

    public void setMrpRateOnAddPopUpBind(RichInputText mrpRateOnAddPopUpBind) {
        this.mrpRateOnAddPopUpBind = mrpRateOnAddPopUpBind;
    }

    public RichInputText getMrpRateOnAddPopUpBind() {
        return mrpRateOnAddPopUpBind;
    }

    public void addNewItemACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("addItemPrice");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (Integer) object;
        if (i == 11) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc2");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1792']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1793']}").toString()); //"Customer Category is not selected!", "Please select Customer Category for whome price will be Defined !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 1) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("itmNmTransId");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1801']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1802']}").toString()); //Item is not selected!", "Please select an Item for whome price will be Defined !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 2) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("id9");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1803']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1804']}").toString()); //"Effective Date is not selected!", "Please select Effective Date from which price will be Applicable !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 4) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it21");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1805']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1806']}").toString()); //"Base Price should be Greater than zero!", "Please enter valid Base Price !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 5) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it22");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1807']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1808']}").toString()); //"Minimum Price should be Greater than Base Price!", "Please enter valid Minimum Price !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 6) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("sor9");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1809']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1810']}").toString()); //"MRP Rate Type is not selected!", "Please select MRP Rate Type !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 7) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it23");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1811']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1812']}").toString()); //"MRP Rate should be greater than zero!", "Please enter a valid MRP Rate !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 8) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it23");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1813']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1814']}").toString()); //"Invalid Percentage Value !", "Percentage value should be less than or equal to 100!");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 9) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it21");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1815']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1816']}").toString()); //"Calculated MRP have Invalid Precision !", "Please enter a valid MRP Rate and Base Price!");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 10) {
            StringBuilder clientId = new StringBuilder(mrpRateOnAddPopUpBind.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it22");
            FacesMessage errMsg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.1817']}").toString(),
                                 resolvElDCMsg("#{bundle['MSG.1808']}").toString()); //"Minimum Price should be less than or Equal to final MRP!", "Please enter valid Minimum Price !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(clientId.toString(), errMsg);
            System.out.println("Client id is : " + clientId);
        } else if (i == 0) {
            addPopupBind.hide();
            catgPgMode = new StringBuilder("V");
            this.getBindings().getOperationBinding("resetAddVO").execute();
            StringBuilder saveMsg =
                new StringBuilder("<html><body><b><p style='color:red'>" +
                                  resolvElDCMsg("#{bundle['MSG.1818']}").toString() + //"Item Price Saved successfully !" +
                                  "</p></b>");
            saveMsg.append(resolvElDCMsg("#{bundle['MSG.1819']}").toString()); //"<b>New Item price is Defined Successfully !");
            saveMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(saveMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (i ==
                   -1) {
            //addPopupBind.hide();
            StringBuilder saveMsg =
           new StringBuilder("<html><body><b><p style='color:red'>" +
                             resolvElDCMsg("#{bundle['MSG.1820']}").toString() + //"There was an error while inserting records !" +
                             "</p></b>");
            saveMsg.append(resolvElDCMsg("#{bundle['MSG.1821']}").toString()); //"<b>Please try again. If the problem persists Please call ESS !");
            saveMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(saveMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setAddPopupBind(RichPopup addPopupBind) {
        this.addPopupBind = addPopupBind;
    }

    public RichPopup getAddPopupBind() {
        return addPopupBind;
    }

    public void amountVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (!isPrecisionValid(26, 6, (Number) object)) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1266']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void cancelAddPopACTION(ActionEvent actionEvent) {

        this.getBindings().getOperationBinding("resetAddVO").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(addItemPriceDialogBind);
        System.out.println("Popul Closed.");
        addPopupBind.clearCachedClientIds();
        addPopupBind.cancel();

    }

    public void ItmNmCatgVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
    }

    public void setCatgPgMode(StringBuilder catgPgMode) {
        this.catgPgMode = catgPgMode;
    }

    public StringBuilder getCatgPgMode() {
        return catgPgMode;
    }

    public void ItmCatgWiseSecelctVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding createOB = getBindings().getOperationBinding("filterDistinctVO");
        createOB.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(executeDistinctPmTableBVar);
    }

    public void setCustWiseTable(RichTable custWiseTable) {
        this.custWiseTable = custWiseTable;
    }

    public RichTable getCustWiseTable() {
        return custWiseTable;
    }

    public void FileExportAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...

        DCBindingContainer container = (DCBindingContainer) getBindings();
        JUCtrlHierBinding obj = (JUCtrlHierBinding) container.findCtrlBinding("DistinctPriceMasterVo1");
        ViewObject object = obj.getViewObject();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");

        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < 9; i++) {
            Cell cell = createRow.createCell(i);
            // sheet.autoSizeColumn(i);
            // sheet.setColumnWidth(100+i,100);
            switch (i) {
            case 0:
                cell.setCellValue("Item Name");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("Item UOM");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("Base Price");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("Minimum Price");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("MRP Rate");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("MRP Type");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("MRP Price");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("Effective Date");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("Expiry Date");
                cell.setCellStyle(cellStyle);
                break;
            }
        }
        int rownum = 1;
        RowSetIterator itr = object.createRowSetIterator(null);
        while (itr.hasNext()) {
            DistinctPriceMasterVoRowImpl next = (DistinctPriceMasterVoRowImpl) itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            for (int i = 0; i < 9; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0:
                    cell.setCellValue(next.getItmId());
                    break;
                case 1:
                    cell.setCellValue(next.getItmUom() == null ? null : next.getItmUom());
                    break;
                case 2:
                    cell.setCellValue(next.getBasePrice() == null ? null : next.getBasePrice().toString());
                    break;
                case 3:
                    cell.setCellValue(next.getMinPrice() == null ? null : next.getMinPrice().toString());
                    break;
                case 4:
                    cell.setCellValue(next.getTotMrpRate() == null ? null : next.getTotMrpRate().toString());
                    break;
                case 5:

                    cell.setCellValue(next.getMrpTyp());
                    break;
                case 6:
                    cell.setCellValue(next.getMrpPrice() == null ? null : next.getMrpPrice().toString());
                    break;
                case 7:
                    cell.setCellValue(next.getEffectiveDt() == null ? null : next.getEffectiveDt().toString());
                    //
                    break;
                case 8:
                    cell.setCellValue(next.getExpiryDt() == null ? null : next.getExpiryDt().toString());
                    break;
                }
                //   cell.setCellValue((String)rs.getString(i));
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

    public void setAddItemPriceDialogBind(RichDialog addItemPriceDialogBind) {
        this.addItemPriceDialogBind = addItemPriceDialogBind;
    }

    public RichDialog getAddItemPriceDialogBind() {
        return addItemPriceDialogBind;
    }

    SLSPriceMasterAppAMImpl am = (SLSPriceMasterAppAMImpl) resolvElDC("SLSPriceMasterAppAMDataControl");

    public void excelExportListner(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 14; i++) {
            HSSFCell cell = createRow.createCell(i);

            switch (i) {
            case 0:
                cell.setCellValue("Category_Id");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("Category_Name");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("Customer_Id");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("Customer_Name");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("Item_Id");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("Item_Name");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("Item_Uom");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("Base_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("Minimum_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("Total_MRP_Rate");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("MRP_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("Effective_Date");
                cell.setCellStyle(cellStyle);
                //cell.setCellComment("Format the Date Colum to see the date in Desired Format");
                break;
            case 12:
                cell.setCellValue("Expiry_Date");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("Part_No");
                cell.setCellStyle(cellStyle);
                break;

            }

        }
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("PriceMaster2Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);

        rq.setWhereClause("CldId='" + EbizParams.GLBL_APP_CLD_ID() + "' and SlocId=" + EbizParams.GLBL_APP_SERV_LOC() + " and HoOrgId='" + EbizParams.GLBL_HO_ORG_ID() + "' and OrgId='" +
                          EbizParams.GLBL_APP_USR_ORG() + "'");

        Row[] fr = object.getFilteredRows(rq);
        int rownum = 1;

        for (Row next : fr) {

            

            /* StringBuilder catgNmVar = null;
            Integer catgIdObjVar = new Integer(catgIdObj.toString());
            Integer eoIdbjVar = new Integer(eoIdbj.toString());
            StringBuilder itemIdObjVar =
                (itmIdObj == null ? new StringBuilder("") : new StringBuilder(itmIdObj.toString()));
            StringBuilder itemName =
                (itemDescObj == null ? new StringBuilder("") : new StringBuilder(itemDescObj.toString()));
            StringBuilder eoNMVar = new StringBuilder(EbizParams.getEoDescFrmEoId(am, eoIdbjVar));
            StringBuilder partNoObjVar =
                (partNoObj == null ? new StringBuilder("") : new StringBuilder(partNoObj.toString()));
            Timestamp expiryDtobjVar =
                (expiryDtObj == null ? null : (Timestamp) expiryDtObj);
            Timestamp effectiveDtObjVar = (Timestamp) effectiveDtObj;
            
            StringBuilder itmUomObjVar =
                (itmUomObj == null ? new StringBuilder("") : new StringBuilder(itmUomObj.toString()));
            Number basePriceObjVar = new Number(0);
            Number minPriceObjVar = new Number(0);
            Number totMrpRateObjVar = new Number(0);
            Number mrpPriceObjVar = new Number(0); 
            try {
                basePriceObjVar = (basePriceObj == null ? new Number(0) : new Number(basePriceObj.toString()));
                minPriceObjVar = (minPriceObj == null ? new Number(0) : new Number(minPriceObj.toString()));
                totMrpRateObjVar = (totMrpRateObj == null ? new Number(0) : new Number(totMrpRateObj.toString()));
                mrpPriceObjVar = (mrpPriceObj == null ? new Number(0) : new Number(mrpPriceObj.toString()));
            } catch (SQLException e) {
            }
            StringBuilder blank = new StringBuilder(""); */
            
            HSSFRow row = sheet.createRow(rownum++);
            Object catgIdO = next.getAttribute("CatgId");
            Object catgNmO = null;
            ViewObjectImpl catagoryType1 = am.getCatagoryType1();
            Row[] filteredRows = catagoryType1.getFilteredRows("AttId", catgIdO);
            if (filteredRows.length > 0) {
                catgNmO = filteredRows[0].getAttribute("AttNm");
            }
            Object eoIdO = next.getAttribute("EoId");
            Object eoNmO = EbizParams.getEoDescFrmEoId(am, (Integer)eoIdO);
            Object itmIdO = next.getAttribute("ItmId");
            Object itemDescO = next.getAttribute("ItmDesc");
            Object itmUomO = next.getAttribute("ItmUom");
            Object basePriceO = next.getAttribute("BasePrice");
            Object minPriceO = next.getAttribute("MinPrice");
            Object totMrpRateO = next.getAttribute("TotMrpRate");
            Object mrpPriceO = next.getAttribute("MrpPrice");
            Object effectiveDtO = next.getAttribute("EffectiveDt");
            Object expiryDtO = next.getAttribute("ExpiryDt");
            Object partNoO = next.getAttribute("EoPartNo");
            
            Cell cell0 = row.createCell(0);
            cell0.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell1 = row.createCell(1);
            cell1.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell2 = row.createCell(2);
            cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell3 = row.createCell(3);
            cell3.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell4 = row.createCell(4);
            cell4.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell5 = row.createCell(5);
            cell5.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell6 = row.createCell(6);
            cell6.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell7 = row.createCell(7);
            cell7.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell8 = row.createCell(8);
            cell8.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell9 = row.createCell(9);
            cell9.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell10 = row.createCell(10);
            cell10.setCellType(Cell.CELL_TYPE_NUMERIC);
            Cell cell11 = row.createCell(11);
            cell11.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell12 = row.createCell(12);
            cell12.setCellType(Cell.CELL_TYPE_STRING);
            Cell cell13 = row.createCell(13);
            cell13.setCellType(Cell.CELL_TYPE_STRING);
            
            cell0.setCellValue(catgIdO == null ? null : (Integer)catgIdO);
            cell1.setCellValue(catgNmO == null ? null : catgNmO.toString());
            cell2.setCellValue(eoIdO == null ? null : (Integer)eoIdO);
            cell3.setCellValue(eoNmO == null ? null : eoNmO.toString());
            cell4.setCellValue(itmIdO == null ? null : itmIdO.toString());
            cell5.setCellValue(itemDescO == null ? null : itemDescO.toString());
            cell6.setCellValue(itmUomO == null ? null : itmUomO.toString());
            cell7.setCellValue(basePriceO == null ? 0 : new Double(basePriceO.toString()));
            cell8.setCellValue(minPriceO == null ? 0 : new Double(minPriceO.toString()));
            cell9.setCellValue(totMrpRateO== null ? 0 : new Double(totMrpRateO.toString()));
            cell10.setCellValue(mrpPriceO== null ? 0 : new Double(mrpPriceO.toString()));
            cell11.setCellValue(effectiveDtO == null ? null : (effectiveDtO.toString()));
            cell12.setCellValue(expiryDtO == null ? null : (expiryDtO.toString()));//new Date(((Timestamp)expiryDtO).getTime()));
            cell13.setCellValue(partNoO == null ? null : partNoO.toString());
            
            
            
            
            
            /* if (catgIdObjVar != null)
                cell0.setCellValue(new Double(catgIdObjVar.toString()));
            else
                cell0.setCellValue(blank.toString());

            cell1.setCellValue(catgNmVar.toString());

            if (eoIdbjVar != null)
                cell2.setCellValue(new Double(eoIdbjVar.toString()));
            else
                cell2.setCellValue(blank.toString());

            cell3.setCellValue(eoNMVar.toString());

            if (itemIdObjVar != null && itemIdObjVar.toString().length() > 0)
                cell4.setCellValue(itemIdObjVar.toString());
            else
                cell4.setCellValue(blank.toString());

            cell5.setCellValue(itemName.toString());

            cell6.setCellValue(itmUomObjVar.toString());
            cell7.setCellValue(basePriceObjVar.toString());
            cell8.setCellValue(minPriceObjVar.toString());
            cell9.setCellValue(totMrpRateObjVar.toString());
            cell10.setCellValue(mrpPriceObjVar.toString());
            cell11.setCellValue(effectiveDtObjVar.toString());
            cell12.setCellValue(expiryDtobjVar == null ? "" : expiryDtobjVar.toString());
            cell13.setCellValue(partNoObjVar.toString()); */

            System.out.println("Row added ___________________" + rownum);

            for (int i = 0; i <= 14; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private String getConvertTimeStampToStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Format to match actual String to parse
        Date dt = null;
        try {
            dt = format.parse(str);
        } catch (ParseException e) {
            System.out.println("Exception Caught=" + e.getStackTrace());
        } 
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-YYY");
        // System.out.println(newFormat.format(dt));
        return newFormat.format(dt);
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

    public void fileUploadActionListener(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 14; i++) {
            HSSFCell cell = createRow.createCell(i);

            switch (i) {
            case 0:
                cell.setCellValue("Category_Id");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("Category_Name");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("Item_Id");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("Item_Name");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("Item_Uom");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("Base_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("Minimum_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("Total_MRP_Rate");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("MRP_Price");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("Effective_Date");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("Expiry_Date");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("Customer_Id");
                    cell.setCellStyle(cellStyle);
                    break;
            case 12:
                cell.setCellValue("Customer_Or_Category");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("Part_No");
                cell.setCellStyle(cellStyle);
                break;
                case 14:
                    cell.setCellValue("Price_Apply");
                    cell.setCellStyle(cellStyle);
                    break;
            }

        }
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("DistinctPriceMasterVo1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);

        rq.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId + "' and OrgId='" +
                          OrgId + "'");

        Row[] fr = object.getFilteredRows(rq);
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;

        for (Row next : fr) {

            HSSFRow row = sheet.createRow(rownum++);
            Object catgIdObj = next.getAttribute("CatgId");
            Object eoIdObj=next.getAttribute("EoId");
            Object eoNmObj = next.getAttribute("EoNm");
            Object itmIdObj = next.getAttribute("ItmId");
            Object itmUomObj = next.getAttribute("ItmUom");
            Object basePriceObj = next.getAttribute("BasePrice");
            Object minPriceObj = next.getAttribute("MinPrice");
            Object totMrpRateObj = next.getAttribute("TotMrpRate");
            Object mrpPriceObj = next.getAttribute("MrpPrice");
            Object partNoObj = next.getAttribute("EoPartNo");
            Object expiryDtObj = next.getAttribute("ExpiryDt");
            Object effectiveDtObj = next.getAttribute("EffectiveDt");
            Object itemDescObj = next.getAttribute("ItmDesc");

            StringBuilder catgNmVar = null;
            Integer catgIdObjVar = new Integer(catgIdObj.toString());
            StringBuilder eoNMObjVar =
                (eoNmObj == null ? new StringBuilder("") : new StringBuilder(eoNmObj.toString()));
            StringBuilder itemIdObjVar =
                (itmIdObj == null ? new StringBuilder("") : new StringBuilder(itmIdObj.toString()));
            StringBuilder itemName =
                (itemDescObj == null ? new StringBuilder("") : new StringBuilder(itemDescObj.toString()));
           
            StringBuilder partNoObjVar =
                (partNoObj == null ? new StringBuilder("") : new StringBuilder(partNoObj.toString()));
            Timestamp expiryDtobjVar =
                (expiryDtObj == null ? null : (Timestamp) expiryDtObj);
            Timestamp effectiveDtObjVar =(Timestamp) effectiveDtObj;
            ViewObjectImpl catagoryType1 = am.getCatagoryType1();
            Row[] filteredRows = catagoryType1.getFilteredRows("AttId", catgIdObjVar);
            if (filteredRows.length > 0) {
                catgNmVar = new StringBuilder(filteredRows[0].getAttribute("AttNm").toString());
            }
            StringBuilder itmUomObjVar =
                (itmUomObj == null ? new StringBuilder("") : new StringBuilder(itmUomObj.toString()));
            Number basePriceObjVar = new Number(0);
            Number minPriceObjVar = new Number(0);
            Number totMrpRateObjVar = new Number(0);
            Number mrpPriceObjVar = new Number(0);
            Integer eoIdObjVar=Integer.parseInt(eoIdObj.toString());
            String priceApplyObjVar="Y";
            try {
                basePriceObjVar = (basePriceObj == null ? new Number(0) : new Number(basePriceObj.toString()));
                minPriceObjVar = (minPriceObj == null ? new Number(0) : new Number(minPriceObj.toString()));
                totMrpRateObjVar = (totMrpRateObj == null ? new Number(0) : new Number(totMrpRateObj.toString()));
                mrpPriceObjVar = (mrpPriceObj == null ? new Number(0) : new Number(mrpPriceObj.toString()));
            } catch (SQLException e) {
            }
            StringBuilder blank = new StringBuilder("");


            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);
            Cell cell14 = row.createCell(14);
            if (catgIdObjVar != null)
                cell0.setCellValue(new Double(catgIdObjVar.toString()));
            else
                cell0.setCellValue(blank.toString());

            cell1.setCellValue(catgNmVar.toString());

            if (itemIdObjVar != null && itemIdObjVar.toString().length() > 0)
                cell2.setCellValue(itemIdObjVar.toString());
            else
                cell2.setCellValue(blank.toString());

            cell3.setCellValue(itemName.toString());

            cell4.setCellValue(itmUomObjVar.toString());
            cell5.setCellValue(basePriceObjVar.toString());
            cell6.setCellValue(minPriceObjVar.toString());
            cell7.setCellValue(totMrpRateObjVar.toString());
            cell8.setCellValue(mrpPriceObjVar.toString());
            cell9.setCellValue(effectiveDtObjVar.toString());
            cell10.setCellValue(expiryDtobjVar == null ? "" : expiryDtobjVar.toString());
            cell11.setCellValue(eoIdObjVar.toString());
            cell12.setCellValue(eoNMObjVar.toString());
            cell13.setCellValue(partNoObjVar.toString());
            cell14.setCellValue(priceApplyObjVar.toString());
            System.out.println("Row added ___________________" + rownum);

            for (int i = 0; i <= 15; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void popEoNmVAL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding binding = ADFBeanUtils.getOperationBinding("setCatgIdOnEoNm");
            binding.getParamsMap().put("EoNm", valueChangeEvent.getNewValue());
            binding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoCatgIdPopUpBing);
        }
    }

    public void setEoCatgIdPopUpBing(RichSelectOneChoice eoCatgIdPopUpBing) {
        this.eoCatgIdPopUpBing = eoCatgIdPopUpBing;
    }

    public RichSelectOneChoice getEoCatgIdPopUpBing() {
        return eoCatgIdPopUpBing;
    }

    public void eoWiseUploadActionListener(ActionEvent actionEvent) {
       upload_Mode="V";
    }
}
