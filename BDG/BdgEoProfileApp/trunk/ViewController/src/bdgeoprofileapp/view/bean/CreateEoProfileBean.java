package bdgeoprofileapp.view.bean;

import bdgeoprofileapp.view.utils.ADFUtils;
import bdgeoprofileapp.view.utils.JSFUtils;

import java.io.IOException;
import java.io.OutputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class CreateEoProfileBean {
    private String CldId = (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    private String HoOrgId = (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    private String OrgId = (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    private Integer SlocId =
        (Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
    private Integer UsrId = (Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString()));
    private Integer DocNo = 34002;
    private Integer DocType = 0;

    private String mode = (String) JSFUtils.resolveExpression("#{pageFlowScope.P_MODE}");
    private String itemMode = "V";
    private RichInputText itemPricePgBind;
    private RichInputText convValuePgBind;
    private RichInputText itemAmtSpPgBind;
    private RichInputText itemAmtBsPgBind;
    private RichInputDate fyStartDtPgBind;
    private RichInputDate fyEndDatePgBind;
    private RichSelectOneRadio bdgSourcePgBind;
    private RichInputDate copyStDatePgBind;
    private RichInputDate copyEndDtPgBind;
    private RichSelectOneRadio copyBdgAmtPgBind;
    private RichInputText copiedBdgAmtPgBind;
    private RichInputText reviseBdgAmtPgBind;
    private RichSelectOneRadio copyBdgTypePgBind;
    private RichSelectOneChoice reviseBdgMethodPgBind;
    private RichSelectOneChoice reviseMethodTypePgBind;
    private RichInputText reviseTypeValuePgBind;
    private RichInputText itemQtyPgBind;
    private RichInputDate prdStDtPgBind;
    private RichInputDate prdEnDtPgBind;
    private RichPopup addAnotherSetPopPgBind;
    private RichPanelFormLayout currFormPgBind;
    private Integer usrId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());
    private RichPanelLabelAndMessage custNamePgBind;
    private RichPanelLabelAndMessage itemGrpPgBind;
    private RichPanelLabelAndMessage itemNamePgBind;
    private RichOutputText custNameOutPgBind;
    private RichOutputText itemNameOutPgBind;
    private RichOutputText itemGrpNameOutPgBind;
    private RichSelectOneRadio includeTypePgBind;
    private RichLink targetAmtLinkPgBind;
    private RichSelectOneChoice budgetStatusPgBind;
    private RichPanelSplitter prdPannelSplitPgBind;
    private Boolean prdPannelSplit = false;
    private RichTable itemDistributionTablePgBind;
    private RichPopup authenticatePopUpPgBind;
    private RichPopup unfreezeDocPopUpPgBind;
    private RichPopup remarksHistoryPopUpPgBind;
    private RichInputText docIdPgBind;
    private RichPopup subordinateItemsPopUpPgBind;
    private RichTable itemDetailTablePgBind;
    private RichPanelLabelAndMessage regionCompPgBind;
    private RichSelectOneRadio distribSelTypePgBind;
    private RichTable bdgDistbAmtTablePgBind;
    private BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    private RichLink unfreezeSubordinateLinkPgBind;
    private RichSelectBooleanCheckbox freezeCbPgBind;
    private RichOutputText totalDistrbPerPgBind;
    private RichOutputText totDistrbAmtPgBind;
    private RichSelectOneChoice itemUnitPgBind;
    private RichOutputText itemDistrbTotAmtPgBind;
    private RichInputText itemAmtTotPgBind;
    private RichInputText itemAmtSpNotaPgBind;
    private RichInputText distrbBudgAmtPgBind;
    private RichOutputText totDistrbAmtNotaPgBind;
    private RichSelectOneChoice copyFyBasisPgBind;
    private RichToolbar toolbarPgBind;
    private RichLink mergeSubordinateLinkPgBind;
    private RichPanelFormLayout copyBdgFormPgBind;
    private RichPanelFormLayout reviseBdgFormPgBind;
    private RichPanelFormLayout distributionBdgFormPgBind;
    private RichInputText remarksPgBind;
    private RichInputText remarksTransPgBind;
    private RichToolbar processBdgToolBarPgBind;
    private RichInputText salesTargetAmtPgBind;
    private RichInputText itemDistbAmtSpPgBind;
    private RichLink productDtlButtonPgBind;
    private RichOutputText amtNotationDescPgBind;
    private RichPanelGroupLayout pannelGroupPgBind;
    private RichPanelGroupLayout itemPanelGroupPgBind;
    private RichOutputText itemAmtNotationDescPgBind;
    private RichInputText minValuePgBind;
    private RichInputText maxValuePgBind;

    public CreateEoProfileBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setItemMode(String itemMode) {
        this.itemMode = itemMode;
    }

    public String getItemMode() {
        return itemMode;
    }

    public void setPrdPannelSplit(Boolean prdPannelSplit) {
        this.prdPannelSplit = prdPannelSplit;
    }

    public Boolean getPrdPannelSplit() {
        return prdPannelSplit;
    }

    public void AddButtonAL(ActionEvent actionEvent) {
        mode = "C";

        OperationBinding ob = ADFUtils.findOperation("filterBdgOnLoad");
        ob.getParamsMap().put("docId", null);
        ob.execute();

        ADFUtils.findOperation("CreateInsert").execute();
        ADFUtils.findOperation("generateOrgBsCurr").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(currFormPgBind);
    }

    public void EditButtonAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("chkBudgetFreezedOrNot");
        ob.execute();
        System.out.println("Result is " + ob.getResult());
        if (ob.getResult() != null && !(Boolean) ob.getResult()) {
            // will check whether user is eligable to create new budget set or not
            ob = ADFUtils.findOperation("chkUserBdgSet");
            ob.execute();
            //System.out.println("Value is " + ob.getResult());
            if (ob != null && (Boolean) ob.getResult()) {
                showPopup(addAnotherSetPopPgBind, true);
            } else {

                ob =
                    ADFUtils.findOperation("chkUserLevelForBdgSet"); // Method which will check whether user is eligable to edit budget set or not
                ob.execute();

                if (ob.getResult() != null && Integer.parseInt(ob.getResult().toString()) == 1) {
                    ob = ADFUtils.findOperation("chkBdgSetEligableToEditOrNot");
                    ob.execute();
                    if (ob.getResult() != null) {
                        if (Integer.parseInt(ob.getResult().toString()) == 1) {
                            JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.1998']}"));
                        } else {
                            mode = "E";
                        }
                    }
                } else {
                    JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.1999']}"));
                }
            }
        } else {
            JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2000']}"));
        }
    }

    public void SaveButtonAL(ActionEvent actionEvent) {
        if (callMethodsBeforSave()) {
            mode = "V";
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage(resolvEl("#{bundle['MSG.91']}"));
        }
    }

    public String saveAndFwdAction() {
        OperationBinding ob1 = ADFUtils.findOperation("chkBudgetAmount");
        ob1.execute();
        if (ob1.getResult() != null && !(Boolean) ob1.getResult()) {
            JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2001']}"));
        } else {
            if (callMethodsBeforSave()) {
                OperationBinding ob = ADFUtils.findOperation("chkSaveAndForwardEligableOrNot");
                ob.execute();
                if (ob.getResult() != null && (Boolean) ob.getResult()) {
                    ob = ADFUtils.findOperation("chkBudgetForwardedStatus");
                    ob.execute();
                    System.out.println("chkBudgetForwardedStatus result is " + ob.getResult());
                    if (ob.getResult() != null && (Boolean) ob.getResult()) {
                        ob = ADFUtils.findOperation("chkDistributionDoneOrNot");
                        ob.execute();
                        if (ob.getResult() != null && Integer.parseInt(ob.getResult().toString()) == 0) {
                            //ADFUtils.findOperation("changeStatusFromApprovedToDraft").execute();

                            ADFUtils.findOperation("changeBdgStatus").execute();
                            JSFUtils.addFacesInformationMessage(resolvEl("#{bundle['MSG.2002']}"));
                            ADFUtils.findOperation("Commit").execute();
                            mode = "V";

                        } else {
                            JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2004']}"));
                        }
                    } else {
                        JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2006']}"));
                    }
                } else {
                    JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2007']}"));
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetStatusPgBind);

        return null;
    }

    public void editForecastAL(ActionEvent actionEvent) {
        //check for pending
        Integer pending = null;

        OperationBinding chkUsr = ADFUtils.findOperation("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", DocNo);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        System.out.println("Pending at=" + pending);
        if (pending.compareTo(-1) != 0 && pending.compareTo(UsrId) != 0) {
            OperationBinding gtUsrNm = ADFUtils.findOperation("getUsrName");
            gtUsrNm.getParamsMap().put("usrId", pending);
            gtUsrNm.execute();
            StringBuffer usrName = new StringBuffer("Anonymous");
            if (gtUsrNm.getResult() != null)
                usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
            String msg2 = resolvEl("#{bundle['MSG.2008']}") + usrName + resolvEl("#{bundle['MSG.2009']}");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {

            OperationBinding ob = ADFUtils.findOperation("chkBudgetFreezedOrNot");
            ob.execute();
            System.out.println("Result is " + ob.getResult());
            if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                mode = "E";
            } else {
                showPopup(addAnotherSetPopPgBind, true);
            }
        }
    }

    public void saveForecastDocAL(ActionEvent actionEvent) {
        if (callMethodsBeforSave()) {

            OperationBinding op = ADFUtils.findOperation("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                mode = "V";
                JSFUtils.addFacesInformationMessage(resolvEl("#{bundle['MSG.91']}"));

                //Workflow Start
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFUtils.findOperation("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", SlocId);
                WfnoOp.getParamsMap().put("cld_id", CldId);
                WfnoOp.getParamsMap().put("org_id", OrgId);
                WfnoOp.getParamsMap().put("doc_no", DocNo);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null) {
                    OperationBinding usrLevelOp = ADFUtils.findOperation("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", SlocId);
                    usrLevelOp.getParamsMap().put("CldId", CldId);
                    usrLevelOp.getParamsMap().put("OrgId", OrgId);
                    usrLevelOp.getParamsMap().put("usr_id", UsrId);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", DocNo);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.2011']}"));
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding insTxnOp = ADFUtils.findOperation("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", SlocId);
                        insTxnOp.getParamsMap().put("cld_id", CldId);
                        insTxnOp.getParamsMap().put("pOrgId", OrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", DocNo);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                        insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1) {
                            OperationBinding operationBinding = ADFUtils.findOperation("Commit");
                            operationBinding.execute();
                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                }
                //Workflow end
            } else {
                JSFUtils.addFacesErrorMessage("Something went wrong. \nError" + op.getErrors());
            }
        }
    }

    public String forwardForecastAction() {
        if (callMethodsBeforSave()) {
            OperationBinding ob = ADFUtils.findOperation("chkSaveAndForwardEligableOrNot");
            ob.execute();
            if (ob.getResult() != null && (Boolean) ob.getResult()) {

                OperationBinding op = ADFUtils.findOperation("Commit");
                op.execute();
                if (op.getErrors().size() == 0) {
                    mode = "V";
                    //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");


                    //Workflow Start
                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = ADFUtils.findOperation("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", SlocId);
                    WfnoOp.getParamsMap().put("cld_id", CldId);
                    WfnoOp.getParamsMap().put("org_id", OrgId);
                    WfnoOp.getParamsMap().put("doc_no", DocNo);
                    WfnoOp.execute();

                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;

                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }
                    if (WfNum != null) {
                        OperationBinding usrLevelOp = ADFUtils.findOperation("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", SlocId);
                        usrLevelOp.getParamsMap().put("CldId", CldId);
                        usrLevelOp.getParamsMap().put("OrgId", OrgId);
                        usrLevelOp.getParamsMap().put("usr_id", UsrId);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("DocNo", DocNo);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.2011']}"));
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            OperationBinding insTxnOp = ADFUtils.findOperation("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", SlocId);
                            insTxnOp.getParamsMap().put("cld_id", CldId);
                            insTxnOp.getParamsMap().put("pOrgId", OrgId);
                            insTxnOp.getParamsMap().put("DOC_NO", DocNo);
                            insTxnOp.getParamsMap().put("WfNum", WfNum);
                            insTxnOp.getParamsMap().put("poDocId", null);
                            insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                            insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.getParamsMap().put("post", "S");
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }
                            if (res == 1) {
                                OperationBinding operationBinding = ADFUtils.findOperation("Commit");
                                operationBinding.execute();
                                return "workFlow";
                            } else {
                                System.out.println("error during insert to WF");
                            }
                            System.out.println(level + "level--res" + res);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1486']}"));
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }


                    //Workflow end

                } else {
                    JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.1478']}") + "\nError" + op.getErrors());
                    return null;
                }
            } else {
                JSFUtils.addFacesErrorMessage(resolvEl("#{bundle['MSG.2007']}"));
            }
        }
        return null;
    }

    public String CancelButtonAction() {
        mode = "V";
        ADFUtils.findOperation("Rollback").execute();

        return "back";
    }

    public Boolean callMethodsBeforSave() {
        Boolean val = true;

        /* OperationBinding ob = ADFUtils.findOperation("chkBudgetAmount");
        ob.execute();
        if (ob.getResult() != null && !(Boolean) ob.getResult()) {
            val = false;
            JSFUtils.addFacesErrorMessage("Without target amount you cannot save the record. Please enter Product details");
        } */

        if (freezeCbPgBind.getValue() != null) {
            OperationBinding operBind = ADFUtils.findOperation("freezeUnfreezeDocument");
            operBind.getParamsMap().put("val", freezeCbPgBind.getValue());
            operBind.execute();
        }

        OperationBinding obChk = ADFUtils.findOperation("chkRevisionAllowedOrNot");
        obChk.execute();
        if (obChk.getResult() != null && !(Boolean) obChk.getResult()) {
            val = false;

            JSFUtils.addFacesErrorMessage("Without proper item amount you cannot forward the Target set.");

        }
        /* ob = ADFUtils.findOperation("chkDistributionBdgAmt");
        ob.execute();
        if (ob.getResult() != null) {
            Integer retVal = (Integer) ob.getResult();
            if (retVal == 1) {
                val = false;
                JSFUtils.addFacesErrorMessage("Distributed Budget Amount is greater than Actual Budget Amount");
            } else if (retVal == -1) {
                val = false;
                JSFUtils.addFacesErrorMessage("Distributed Budget Amount is less than Actual Budget Amount");
            }
        } */
        return val;
    }

    public void AddItemDetailAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("resetItemDetail").execute();

        ADFUtils.findOperation("printEstimatedRows").execute();

        OperationBinding ob = ADFUtils.findOperation("ckhMandatoryFieldsEntered");
        ob.execute();
        if (ob.getResult() != null && (Boolean) ob.getResult()) {

            OperationBinding operBind = ADFUtils.findOperation("ckhItemDistrbDoneOrNot");
            operBind.execute();
            if (operBind.getResult() != null && (Boolean) operBind.getResult()) {
                ADFUtils.findOperation("CreateInsert").execute();
            } else {
                JSFUtils.addFacesErrorMessage("Please Distribute the amount accordingly.");
            }

        } else {
            JSFUtils.addFacesErrorMessage("Please select either Customer Name or Item Group Name or Item Name.");
        }
    }

    public String backButtonAction() {
        ADFUtils.findOperation("updateBdgAmtValue").execute();

        OperationBinding ob = ADFUtils.findOperation("ckhMandatoryFieldsEntered");
        ob.execute();
        if (ob.getResult() != null && (Boolean) ob.getResult()) {
            OperationBinding operBind = ADFUtils.findOperation("ckhItemDistrbDoneOrNot");
            operBind.execute();
            if (operBind.getResult() != null && (Boolean) operBind.getResult()) {

                if (this.getMode().equalsIgnoreCase("C") || this.getMode().equalsIgnoreCase("E")) {
                    // will copy data present in BdgEoBugdetProdPrdDtl To BdgEoBudgetPrdDtl
                    ADFUtils.findOperation("copyItemDtlToBdgDtl").execute();

                    // Will check whether item quantity and amount were enterd or not
                    ob = ADFUtils.findOperation("chkQtyAndAmtEnteredOrNot");
                    ob.execute();
                    if (ob.getResult() != null && (Boolean) ob.getResult()) {

                        //ADFUtils.findOperation("postDataAccordingly").execute();

                        return "back";
                    } else {
                        return null;
                    }
                } else {
                    return "back";
                }
            } else {
                JSFUtils.addFacesErrorMessage("Please Distribute the amount accordingly.");
                return null;
            }
        } else {
            JSFUtils.addFacesErrorMessage("Please select either Customer Name or Item Group Name or Item Name.");
            return null;
        }
    }

    public void DeleteItemDetailAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("deleteDistributionDetailPeriodWise").execute();

        ADFUtils.findOperation("deleteCostCenterItem").execute();

        ADFUtils.findOperation("Delete").execute();

        ADFUtils.findOperation("Execute").execute();

        ADFUtils.findOperation("printEstimatedRows").execute();

        ADFUtils.findOperation("updateBdgAmtValue").execute();

    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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


    public void setItemPricePgBind(RichInputText itemPricePgBind) {
        this.itemPricePgBind = itemPricePgBind;
    }

    public RichInputText getItemPricePgBind() {
        return itemPricePgBind;
    }

    public void itemQtyVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && itemPricePgBind.getValue() != null &&
            vce.getNewValue().toString().length() > 0 && convValuePgBind.getValue() != null) {
            /* Number itmPrice = (Number) itemPricePgBind.getValue();
            Number qty = (Number) vce.getNewValue();
            Number conv = (Number) convValuePgBind.getValue(); */
            OperationBinding ob = ADFUtils.findOperation("setItemAmountDetails");
            ob.getParamsMap().put("qty", vce.getNewValue());
            ob.execute();

            //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);

            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            if (itemAmtSpPgBind.getValue() != null || itemAmtSpNotaPgBind.getValue() != null) {
                ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
                ADFUtils.findOperation("Execute2").execute();

                //ADFUtils.findOperation("updateCostCenterAmt").execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
            }

        }
        ADFUtils.findOperation("updateBdgAmtValue").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void sellingPriceVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        executeItemAmtForm();
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (includeTypePgBind.getValue().equals("I")) {
            if (vce.getNewValue() != null && itemQtyPgBind.getValue() != null &&
                (itemAmtSpPgBind.getValue() != null || itemAmtSpNotaPgBind.getValue() != null)) {
                ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
                ADFUtils.findOperation("Execute2").execute();

                //ADFUtils.findOperation("updateCostCenterAmt").execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
            }
        } else {
            if (vce.getNewValue() != null &&
                (itemAmtSpPgBind.getValue() != null || itemAmtSpNotaPgBind.getValue() != null)) {
                ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
                ADFUtils.findOperation("Execute2").execute();

                //ADFUtils.findOperation("updateCostCenterAmt").execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
            }
        }
    }

    public void setConvValuePgBind(RichInputText convValuePgBind) {
        this.convValuePgBind = convValuePgBind;
    }

    public RichInputText getConvValuePgBind() {
        return convValuePgBind;
    }

    public void setItemAmtSpPgBind(RichInputText itemAmtSpPgBind) {
        this.itemAmtSpPgBind = itemAmtSpPgBind;
    }

    public RichInputText getItemAmtSpPgBind() {
        return itemAmtSpPgBind;
    }

    public void setItemAmtBsPgBind(RichInputText itemAmtBsPgBind) {
        this.itemAmtBsPgBind = itemAmtBsPgBind;
    }

    public RichInputText getItemAmtBsPgBind() {
        return itemAmtBsPgBind;
    }

    /**
     *Method which will execute in all cases of Item or product detail VCL
     */
    public void executeItemAmtForm() {
        if (includeTypePgBind.getValue() != null) {
            if (includeTypePgBind.getValue().equals("I")) {
                if (itemPricePgBind.getValue() != null && convValuePgBind.getValue() != null &&
                    itemQtyPgBind.getValue() != null) {
                    OperationBinding ob = ADFUtils.findOperation("setItemAmountDetails");
                    ob.getParamsMap().put("qty", itemQtyPgBind.getValue());
                    ob.execute();
                }
            } else if (includeTypePgBind.getValue().equals("G")) {
                if (convValuePgBind.getValue() != null && itemAmtSpPgBind.getValue() != null) {
                    OperationBinding ob = ADFUtils.findOperation("setItemAmountDetails");
                    ob.getParamsMap().put("qty", null);
                    ob.execute();
                }
            }
        }

        ADFUtils.findOperation("updateBdgAmtValue").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);

    }

    public void prdStartDtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding opFyIdDt = ADFUtils.findOperation("settingFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", vce.getNewValue());
            opFyIdDt.execute();
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(fyStartDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fyEndDatePgBind);
        }
    }

    public void setFyStartDtPgBind(RichInputDate fyStartDtPgBind) {
        this.fyStartDtPgBind = fyStartDtPgBind;
    }

    public RichInputDate getFyStartDtPgBind() {
        return fyStartDtPgBind;
    }

    public void setFyEndDatePgBind(RichInputDate fyEndDatePgBind) {
        this.fyEndDatePgBind = fyEndDatePgBind;
    }

    public RichInputDate getFyEndDatePgBind() {
        return fyEndDatePgBind;
    }

    public void employeeNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(regionCompPgBind);
    }

    public void setBdgSourcePgBind(RichSelectOneRadio bdgSourcePgBind) {
        this.bdgSourcePgBind = bdgSourcePgBind;
    }

    public RichSelectOneRadio getBdgSourcePgBind() {
        return bdgSourcePgBind;
    }

    public void setCopyStDatePgBind(RichInputDate copyStDatePgBind) {
        this.copyStDatePgBind = copyStDatePgBind;
    }

    public RichInputDate getCopyStDatePgBind() {
        return copyStDatePgBind;
    }

    public void setCopyEndDtPgBind(RichInputDate copyEndDtPgBind) {
        this.copyEndDtPgBind = copyEndDtPgBind;
    }

    public RichInputDate getCopyEndDtPgBind() {
        return copyEndDtPgBind;
    }

    public void setCopyBdgAmtPgBind(RichSelectOneRadio copyBdgAmtPgBind) {
        this.copyBdgAmtPgBind = copyBdgAmtPgBind;
    }

    public RichSelectOneRadio getCopyBdgAmtPgBind() {
        return copyBdgAmtPgBind;
    }

    public void copyBudgetButtonAL(ActionEvent actionEvent) {

        System.out.println("Doc iiiid ---" + docIdPgBind.getValue());
        OperationBinding op = ADFUtils.findOperation("filterBdgOnLoad");
        op.getParamsMap().put("docId", docIdPgBind.getValue());
        op.execute();

        String bdgSourceType = (String) bdgSourcePgBind.getValue();
        if (bdgSourceType != null && bdgSourceType.equalsIgnoreCase("S")) { // SELF Case
            ADFUtils.findOperation("copyPreviousDataUsingFunction").execute();

        } else { /// Subordinate Case
            ADFUtils.findOperation("copyPreviousDataForSubOrd").execute();

        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(copiedBdgAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salesTargetAmtPgBind);
        actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

    }

    public void setCopiedBdgAmtPgBind(RichInputText copiedBdgAmtPgBind) {
        this.copiedBdgAmtPgBind = copiedBdgAmtPgBind;
    }

    public RichInputText getCopiedBdgAmtPgBind() {
        return copiedBdgAmtPgBind;
    }

    public void setReviseBdgAmtPgBind(RichInputText reviseBdgAmtPgBind) {
        this.reviseBdgAmtPgBind = reviseBdgAmtPgBind;
    }

    public RichInputText getReviseBdgAmtPgBind() {
        return reviseBdgAmtPgBind;
    }

    public void setCopyBdgTypePgBind(RichSelectOneRadio copyBdgTypePgBind) {
        this.copyBdgTypePgBind = copyBdgTypePgBind;
    }

    public RichSelectOneRadio getCopyBdgTypePgBind() {
        return copyBdgTypePgBind;
    }

    public void copyPreviousFlagVCL(ValueChangeEvent vce) {
        /* if (vce.getNewValue() != null) {
            System.out.println("Inside Vce");
            OperationBinding ob = null;
            ob = ADFUtils.findOperation("copyDateBasedOnCondition");
            ob.getParamsMap().put("flag", vce.getNewValue());

            ob.execute();
        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(copyStDatePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(copyEndDtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(copyFyBasisPgBind);
    }

    public void refreshSummDateAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("setBindValuesForSummView").execute();
    }

    public void setReviseBdgMethodPgBind(RichSelectOneChoice reviseBdgMethodPgBind) {
        this.reviseBdgMethodPgBind = reviseBdgMethodPgBind;
    }

    public RichSelectOneChoice getReviseBdgMethodPgBind() {
        return reviseBdgMethodPgBind;
    }

    public void setReviseMethodTypePgBind(RichSelectOneChoice reviseMethodTypePgBind) {
        this.reviseMethodTypePgBind = reviseMethodTypePgBind;
    }

    public RichSelectOneChoice getReviseMethodTypePgBind() {
        return reviseMethodTypePgBind;
    }

    public void setReviseTypeValuePgBind(RichInputText reviseTypeValuePgBind) {
        this.reviseTypeValuePgBind = reviseTypeValuePgBind;
    }

    public RichInputText getReviseTypeValuePgBind() {
        return reviseTypeValuePgBind;
    }

    public void reviseBudgetAL(ActionEvent actionEvent) {
        OperationBinding obChk = ADFUtils.findOperation("chkRevisionAllowedOrNot");
        obChk.execute();
        if (obChk.getResult() != null && (Boolean) obChk.getResult()) {
            if (reviseBdgMethodPgBind.getValue() != null && reviseBdgMethodPgBind.getValue().toString().length() > 0) {
                if (reviseMethodTypePgBind.getValue() != null &&
                    reviseMethodTypePgBind.getValue().toString().length() > 0) {
                    if (reviseTypeValuePgBind.getValue() != null) {
                        OperationBinding ob = ADFUtils.findOperation("reviseBudgetAmt");
                        ob.execute();
                        if (ob.getResult() != null) {
                            reviseBdgAmtPgBind.setValue(ob.getResult());
                            JSFUtils.addFacesInformationMessage("Target amount has been revised accordingly.");

                            reviseBdgMethodPgBind.setValue(null);
                            reviseMethodTypePgBind.setValue(null);
                            reviseTypeValuePgBind.setValue(null);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgMethodPgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(reviseMethodTypePgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(reviseTypeValuePgBind);

                            //ADFUtils.findOperation("updateCostCenterAmtAfterRevise").execute();
                        }
                    } else {
                        JSFUtils.addFacesErrorMessage("Please enter value.");
                    }
                } else {
                    JSFUtils.addFacesErrorMessage("Please select revision type.");
                }
            } else {
                JSFUtils.addFacesErrorMessage("Please select revision method.");
            }
        } else {
            JSFUtils.addFacesErrorMessage("You cannot revise the Target.");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salesTargetAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(productDtlButtonPgBind);
    }

    public void itemCurrencyNameVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        executeItemAmtForm();
        ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();

        //ADFUtils.findOperation("updateCostCenterAmt").execute();
    }

    public void setItemQtyPgBind(RichInputText itemQtyPgBind) {
        this.itemQtyPgBind = itemQtyPgBind;
    }

    public RichInputText getItemQtyPgBind() {
        return itemQtyPgBind;
    }

    public void prdStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Timestamp stDt = (Timestamp) object;

            OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", stDt);
            opFyIdDt.execute();
            if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                opFyIdDt.getResult().toString().equals("Y")) {
                OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
                opChkExist.getParamsMap().put("validDt", stDt);
                opChkExist.execute();
                if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                    opChkExist.getResult().toString().equals("N")) {
                } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                    throw new ValidatorException(new FacesMessage("Target for the Selected Date is already exist - " +
                                                                  opChkExist.getResult()));
                }
            } else {
                throw new ValidatorException(new FacesMessage("Invalid Date - Please Check Financial Year"));
            }
        }
    }

    public void prdEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Timestamp strtDt = null;
            Timestamp endDt = null;
            Timestamp fyEndDt = null;
            if (prdStDtPgBind.getValue() != null && prdStDtPgBind.getValue().toString().length() > 0) {
                strtDt = (Timestamp) prdStDtPgBind.getValue();
                endDt = (Timestamp) object;

                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage("Target End Date can not be Before Target Start Date."));
                    }
                }
                OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
                opFyIdDt.getParamsMap().put("stDt", endDt);
                opFyIdDt.execute();
                if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                    opFyIdDt.getResult().toString().equals("Y")) {
                } else {
                    throw new ValidatorException(new FacesMessage("Invalid Date - Please Check Financial Year"));
                }
            }
            if (fyEndDatePgBind.getValue() != null && fyEndDatePgBind.getValue().toString().length() > 0) {

                fyEndDt = (Timestamp) fyEndDatePgBind.getValue();
                endDt = (Timestamp) object;

                if (endDt.compareTo(fyEndDt) > 0) {
                    if (endDt.toString().equals(fyEndDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage("Target End Date can not be After Financial Year End Date."));
                    }
                }
            }
            endDt = (Timestamp) object;

            OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
            opChkExist.getParamsMap().put("validDt", endDt);
            opChkExist.execute();
            if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                opChkExist.getResult().toString().equals("N")) {
            } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                throw new ValidatorException(new FacesMessage("Target for the Selected Date is already exist - " +
                                                              opChkExist.getResult()));
            }
        }
    }

    public void setPrdStDtPgBind(RichInputDate prdStDtPgBind) {
        this.prdStDtPgBind = prdStDtPgBind;
    }

    public RichInputDate getPrdStDtPgBind() {
        return prdStDtPgBind;
    }

    public void setPrdEnDtPgBind(RichInputDate prdEnDtPgBind) {
        this.prdEnDtPgBind = prdEnDtPgBind;
    }

    public RichInputDate getPrdEnDtPgBind() {
        return prdEnDtPgBind;
    }

    public void setAddAnotherSetPopPgBind(RichPopup addAnotherSetPopPgBind) {
        this.addAnotherSetPopPgBind = addAnotherSetPopPgBind;
    }

    public RichPopup getAddAnotherSetPopPgBind() {
        return addAnotherSetPopPgBind;
    }

    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    public void amendBdgPopUpCL(PopupCanceledEvent popupCanceledEvent) {
        OperationBinding ob = ADFUtils.findOperation("chkBdgSetEligableToEditOrNot");
        ob.execute();
        if (ob.getResult() != null) {
            if (Integer.parseInt(ob.getResult().toString()) == 1) {
                JSFUtils.addFacesErrorMessage("Target Set has been approved. You cannot edit this Target Set. Please create new Target Set.");
            } else if (Integer.parseInt(ob.getResult().toString()) == 2) {
                JSFUtils.addFacesErrorMessage("Target Set has been forwarded. You cannot edit this Target Set.");
            } else {
                mode = "E";
            }
        }
        //refreshPage();
    }

    public void addAnotherSetPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("yes")) {
            ADFUtils.findOperation("amendCurrBdgt").execute();
            mode = "C";
            refreshPage();
        } else {
            OperationBinding ob = ADFUtils.findOperation("chkBdgSetEligableToEditOrNot");
            ob.execute();
            if (ob.getResult() != null) {
                if (Integer.parseInt(ob.getResult().toString()) == 1) {
                    JSFUtils.addFacesErrorMessage("Target Set has been approved. You cannot edit this Target Set. Please create new Target Set.");
                } else if (Integer.parseInt(ob.getResult().toString()) == 2) {
                    JSFUtils.addFacesErrorMessage("Target Set has been forwarded. You cannot edit this Target Set.");
                } else if (Integer.parseInt(ob.getResult().toString()) == 3) {
                    JSFUtils.addFacesErrorMessage("You cannot edit this Target Set. Please create new Target Set.");
                } else {
                    mode = "E";
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mergeSubordinateLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(copyBdgFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(distributionBdgFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(processBdgToolBarPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void budgetAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }
        }
    }

    public void budgetRevTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("setRevisionTypeVal");
            ob.getParamsMap().put("value", vce.getNewValue());
            ob.execute();
        }
    }

    public void revisionMethodVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("setRevisionMethod");
            ob.getParamsMap().put("value", vce.getNewValue());
            ob.execute();
        }
    }

    public void revisionTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("setRevisionType");
            ob.getParamsMap().put("value", vce.getNewValue());
            ob.execute();
        }
    }

    public void budgetRevTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }

            Object reviseType = reviseMethodTypePgBind.getValue();
            if (reviseType != null) {
                if (reviseType.toString().equalsIgnoreCase("P")) {
                    if (val.compareTo(100) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Please enter proper percentage.", null));
                    } else if (val.compareTo(100) == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Percentage value should be less than 100.",
                                                                      null));
                    }
                }
            }
        }
    }

    public void sellingPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }
        }
    }

    public void itemQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }

            if (minValuePgBind.getValue() != null && maxValuePgBind.getValue() != null) {
                Number minimumValue = (Number) minValuePgBind.getValue();
                Number maximumValue = (Number) maxValuePgBind.getValue();

                if (val.compareTo(minimumValue) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be greater than or equal to minimum value.",
                                                                  null));
                }

                if (val.compareTo(maximumValue) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be less than or equal to maximum value.",
                                                                  null));
                }
            }
        }
    }

    public void itemAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }

            if (minValuePgBind.getValue() != null && maxValuePgBind.getValue() != null) {
                Number minimumValue = (Number) minValuePgBind.getValue();
                Number maximumValue = (Number) maxValuePgBind.getValue();

                if (val.compareTo(minimumValue) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be greater than or equal to minimum value.",
                                                                  null));
                }

                if (val.compareTo(maximumValue) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be less than or equal to maximum value.",
                                                                  null));
                }
            }
        }
    }

    public void setCurrFormPgBind(RichPanelFormLayout currFormPgBind) {
        this.currFormPgBind = currFormPgBind;
    }

    public RichPanelFormLayout getCurrFormPgBind() {
        return currFormPgBind;
    }

    public void changeOrganizationVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (vce.getNewValue() != null) {
            ADFUtils.findOperation("generateOrgBsCurr").execute();
        }
        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(currFormPgBind);
    }

    public void custNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFUtils.findOperation("chkProductDuplicateValidator");
            ob.getParamsMap().put("custName", object);
            ob.getParamsMap().put("itemGrpName", itemGrpNameOutPgBind.getValue());
            ob.getParamsMap().put("itemName", itemNameOutPgBind.getValue());
            ob.execute();
            System.out.println("Result is " + ob.getResult());
            if (ob.getResult() != null && (Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record.", null));
            }
        }
    }

    public void custNameVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNameOutPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
    }


    public void itemSelectionTypeVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNameOutPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemQtyPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);
    }

    public void setCustNamePgBind(RichPanelLabelAndMessage custNamePgBind) {
        this.custNamePgBind = custNamePgBind;
    }

    public RichPanelLabelAndMessage getCustNamePgBind() {
        return custNamePgBind;
    }

    public void itemGrpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFUtils.findOperation("chkProductDuplicateValidator");
            ob.getParamsMap().put("custName", custNameOutPgBind.getValue());
            ob.getParamsMap().put("itemGrpName", object);
            ob.getParamsMap().put("itemName", itemNameOutPgBind.getValue());
            ob.execute();

            if (ob.getResult() != null && (Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record.", null));
            }
        }
    }

    public void itemGrpVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNameOutPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);

    }

    public void setItemGrpPgBind(RichPanelLabelAndMessage itemGrpPgBind) {
        this.itemGrpPgBind = itemGrpPgBind;
    }

    public RichPanelLabelAndMessage getItemGrpPgBind() {
        return itemGrpPgBind;
    }

    public void itemNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFUtils.findOperation("chkProductDuplicateValidator");
            ob.getParamsMap().put("custName", custNameOutPgBind.getValue());
            ob.getParamsMap().put("itemGrpName", itemGrpNameOutPgBind.getValue());
            ob.getParamsMap().put("itemName", object);
            ob.execute();

            if (ob.getResult() != null && (Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record.", null));
            }
        }

    }

    public void itemNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            OperationBinding ob = ADFUtils.findOperation("getLatestItemPrice");
            ob.getParamsMap().put("itemDesc", vce.getNewValue());
            ob.execute();
            if (ob.getResult() != null) {
                itemPricePgBind.setValue(ob.getResult());
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemPricePgBind);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGrpNameOutPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNameOutPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemUnitPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        executeItemAmtForm();
        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setItemNamePgBind(RichPanelLabelAndMessage itemNamePgBind) {
        this.itemNamePgBind = itemNamePgBind;
    }

    public RichPanelLabelAndMessage getItemNamePgBind() {
        return itemNamePgBind;
    }

    public void itemAmtSpVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            OperationBinding ob = ADFUtils.findOperation("setItemAmtBS");
            ob.getParamsMap().put("amtSp", vce.getNewValue());
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();

            //ADFUtils.findOperation("updateCostCenterAmt").execute();
        }
        ADFUtils.findOperation("updateBdgAmtValue").execute();
        //ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void itemAmtSpNotaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            OperationBinding ob = ADFUtils.findOperation("setItemAmtBS");
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());
            ob.getParamsMap().put("amtSp", notaAmt);
            ob.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);

            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();

            //ADFUtils.findOperation("updateCostCenterAmt").execute();
        }
        ADFUtils.findOperation("updateBdgAmtValue").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);

        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setCustNameOutPgBind(RichOutputText custNameOutPgBind) {
        this.custNameOutPgBind = custNameOutPgBind;
    }

    public RichOutputText getCustNameOutPgBind() {
        return custNameOutPgBind;
    }

    public void setItemNameOutPgBind(RichOutputText itemNameOutPgBind) {
        this.itemNameOutPgBind = itemNameOutPgBind;
    }

    public RichOutputText getItemNameOutPgBind() {
        return itemNameOutPgBind;
    }

    public void setItemGrpNameOutPgBind(RichOutputText itemGrpNameOutPgBind) {
        this.itemGrpNameOutPgBind = itemGrpNameOutPgBind;
    }

    public RichOutputText getItemGrpNameOutPgBind() {
        return itemGrpNameOutPgBind;
    }

    public void setIncludeTypePgBind(RichSelectOneRadio includeTypePgBind) {
        this.includeTypePgBind = includeTypePgBind;
    }

    public RichSelectOneRadio getIncludeTypePgBind() {
        return includeTypePgBind;
    }

    public void budgetMethodVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(targetAmtLinkPgBind);
    }

    public void setTargetAmtLinkPgBind(RichLink targetAmtLinkPgBind) {
        this.targetAmtLinkPgBind = targetAmtLinkPgBind;
    }

    public RichLink getTargetAmtLinkPgBind() {
        return targetAmtLinkPgBind;
    }

    public void setBudgetStatusPgBind(RichSelectOneChoice budgetStatusPgBind) {
        this.budgetStatusPgBind = budgetStatusPgBind;
    }

    public RichSelectOneChoice getBudgetStatusPgBind() {
        return budgetStatusPgBind;
    }

    public void freezeCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            System.out.println("Value is " + vce.getNewValue());
            /* if (vce.getNewValue().toString().equalsIgnoreCase("Y") ||
                vce.getNewValue().toString().equalsIgnoreCase("true")) {
                 */
            /* OperationBinding ob = ADFUtils.findOperation("freezeUnfreezeDocument");
            ob.getParamsMap().put("val", vce.getNewValue());
            ob.execute(); */
            /* } else {
                showPopup(unfreezeDocPopUpPgBind, true);
            } */
            AdfFacesContext.getCurrentInstance().addPartialTarget(unfreezeSubordinateLinkPgBind);
        }
    }

    public void freezeCBValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            OperationBinding ob = ADFUtils.findOperation("chkBudgetAmount");
            ob.execute();
            if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                freezeCbPgBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Without target amount you cannot save the record. Please enter Product details",
                                                              null));
            }

            ob = ADFUtils.findOperation("chkDistributionBdgAmt");
            ob.execute();
            if (ob.getResult() != null) {
                Integer retVal = (Integer) ob.getResult();
                if (retVal == 1) {
                    freezeCbPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Distributed Target Amount is greater than Actual Target Amount",
                                                                  null));
                } else if (retVal == -1) {
                    freezeCbPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Distributed Target Amount is less than Actual Target Amount",
                                                                  null));
                }
            }

            ob = ADFUtils.findOperation("chkDistributionDoneOrNot");
            ob.execute();
            if (ob.getResult() != null && Integer.parseInt(ob.getResult().toString()) == 0) {
            } else {
                freezeCbPgBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Distribute Target Amount before forwarding.", null));
            }
        }
    }

    public void setPrdPannelSplitPgBind(RichPanelSplitter prdPannelSplitPgBind) {
        this.prdPannelSplitPgBind = prdPannelSplitPgBind;
    }

    public RichPanelSplitter getPrdPannelSplitPgBind() {
        return prdPannelSplitPgBind;
    }

    public void showDistributionDetailAL(ActionEvent actionEvent) {
        if (prdPannelSplitPgBind.isCollapsed()) { /// Will hide the pannel splitter component
            ADFUtils.findOperation("updateBdgAmtValue").execute();
            ADFUtils.findOperation("updateProductDtlValue").execute();
            prdPannelSplitPgBind.setCollapsed(false);
        } else { //// Will expand pannel splitter and display all fields
            OperationBinding ob =
                ADFUtils.findOperation("ckhMandatoryFieldsEntered"); // method which will check whether mandatory fields enterd or not
            ob.execute();
            if (ob.getResult() != null && (Boolean) ob.getResult()) {
                ADFUtils.findOperation("setAttributeValues").execute();
                prdPannelSplitPgBind.setCollapsed(true);
            } else {
                JSFUtils.addFacesErrorMessage("Please select either Customer Name or Item Group Name or Item Name.");
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
    }

    public void setItemDistributionTablePgBind(RichTable itemDistributionTablePgBind) {
        this.itemDistributionTablePgBind = itemDistributionTablePgBind;
    }

    public RichTable getItemDistributionTablePgBind() {
        return itemDistributionTablePgBind;
    }

    public void setAuthenticatePopUpPgBind(RichPopup authenticatePopUpPgBind) {
        this.authenticatePopUpPgBind = authenticatePopUpPgBind;
    }

    public RichPopup getAuthenticatePopUpPgBind() {
        return authenticatePopUpPgBind;
    }

    public void authenticateDocAL(ActionEvent actionEvent) {
        if (callMethodsBeforSave()) {
            showPopup(authenticatePopUpPgBind, true);
        }
    }

    public void approveSubOrdAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("changeStatusFromApprovedToDraft").execute();

        OperationBinding ob = ADFUtils.findOperation("authorizeDocument");
        ob.getParamsMap().put("val", 3);
        ob.execute();

        authenticatePopUpPgBind.hide();

        ADFUtils.findOperation("Commit").execute();

        ADFUtils.findOperation("Execute").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetStatusPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(remarksPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(remarksTransPgBind);

        ADFUtils.findOperation("sendDateFromTempCcToSlsSoCc").execute();
    }

    public void rejectSubOrdDocAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("authorizeDocument");
        ob.getParamsMap().put("val", 1);
        ob.execute();

        authenticatePopUpPgBind.hide();

        ADFUtils.findOperation("Commit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetStatusPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(remarksPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(remarksTransPgBind);
    }

    public void processDistributionAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("distributeItemDetailPeriodWise").execute();
    }

    public void unfreezeDocDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            ADFUtils.findOperation("unfreezeDocuments").execute();

            ADFUtils.findOperation("Commit").execute();

            ADFUtils.findOperation("executeHeadView").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(freezeCbPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetStatusPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(unfreezeSubordinateLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
    }

    public void setUnfreezeDocPopUpPgBind(RichPopup unfreezeDocPopUpPgBind) {
        this.unfreezeDocPopUpPgBind = unfreezeDocPopUpPgBind;
    }

    public RichPopup getUnfreezeDocPopUpPgBind() {
        return unfreezeDocPopUpPgBind;
    }

    public void unfreezeDocPopUpFL(PopupFetchEvent popupFetchEvent) {
        ADFUtils.findOperation("setImmediateSubOrdDtlVOBindVar").execute();
    }

    public void unfreezeSubOrdinateAL(ActionEvent actionEvent) {
        showPopup(unfreezeDocPopUpPgBind, true);
    }

    public void distributeBdgAmtAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("distributeBudgetAmtPeriodWise").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
    }

    public void remarksHistoryButtonAL(ActionEvent actionEvent) {
        showPopup(remarksHistoryPopUpPgBind, true);
    }

    public void setRemarksHistoryPopUpPgBind(RichPopup remarksHistoryPopUpPgBind) {
        this.remarksHistoryPopUpPgBind = remarksHistoryPopUpPgBind;
    }

    public RichPopup getRemarksHistoryPopUpPgBind() {
        return remarksHistoryPopUpPgBind;
    }

    public void remarksHistPopupFL(PopupFetchEvent popupFetchEvent) {
        OperationBinding ob = ADFUtils.findOperation("showRemarksHistory");
        ob.getParamsMap().put("docId", docIdPgBind.getValue());
        ob.execute();
    }

    public void setDocIdPgBind(RichInputText docIdPgBind) {
        this.docIdPgBind = docIdPgBind;
    }

    public RichInputText getDocIdPgBind() {
        return docIdPgBind;
    }

    public void subOrdinateItemsDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            ADFUtils.findOperation("insertImmediateSubItemDetails").execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgAmtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesTargetAmtPgBind);

            //ADFUtils.findOperation("distributeBudgetAmtPeriodWise").execute();
        }
        //ADFUtils.findOperation("updateBdgAmtValue").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(reviseBdgAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salesTargetAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bdgDistbAmtTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
    }

    public void setSubordinateItemsPopUpPgBind(RichPopup subordinateItemsPopUpPgBind) {
        this.subordinateItemsPopUpPgBind = subordinateItemsPopUpPgBind;
    }

    public RichPopup getSubordinateItemsPopUpPgBind() {
        return subordinateItemsPopUpPgBind;
    }

    public void subOrdinateItemDetailAL(ActionEvent actionEvent) {
        showPopup(subordinateItemsPopUpPgBind, true);
    }

    public void subOrdinateItemDtlFL(PopupFetchEvent popupFetchEvent) {
        ADFUtils.findOperation("setImmediateEmpDtlBindVar").execute();
    }

    public void distbQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than or equal to zero.", null));
            }
        }
    }

    public void distbQtyVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("changeItemDistbQtyAndAmount");
            ob.getParamsMap().put("qty", vce.getNewValue());
            ob.execute();

            ADFUtils.findOperation("updateCostCenterAmtAfterDistrib").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistrbTotAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistbAmtSpPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);

    }

    public void distbItemAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than or equal to zero.", null));
            }
        }
    }

    public void distbItemAmtVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("changeItemDistAmt");
            ob.getParamsMap().put("amt", vce.getNewValue());
            ob.execute();

            ADFUtils.findOperation("updateCostCenterAmtAfterDistrib").execute();
        }
        ADFUtils.findOperation("updateBdgAmtValue").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistrbTotAmtPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);

    }

    public void distbItemNotaAmtVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("changeItemDistAmt");

            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            ob.getParamsMap().put("amt", notaAmt);
            ob.execute();
        }
        ADFUtils.findOperation("updateBdgAmtValue").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistrbTotAmtPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);
    }

    public void setItemDetailTablePgBind(RichTable itemDetailTablePgBind) {
        this.itemDetailTablePgBind = itemDetailTablePgBind;
    }

    public RichTable getItemDetailTablePgBind() {
        return itemDetailTablePgBind;
    }

    public void setRegionCompPgBind(RichPanelLabelAndMessage regionCompPgBind) {
        this.regionCompPgBind = regionCompPgBind;
    }

    public RichPanelLabelAndMessage getRegionCompPgBind() {
        return regionCompPgBind;
    }

    public void setDistribSelTypePgBind(RichSelectOneRadio distribSelTypePgBind) {
        this.distribSelTypePgBind = distribSelTypePgBind;
    }

    public RichSelectOneRadio getDistribSelTypePgBind() {
        return distribSelTypePgBind;
    }

    public void distbAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(distribSelTypePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bdgDistbAmtTablePgBind);
    }

    public void distbBdgAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than or eqqual to zero.", null));
            }
        }
    }

    public void distbPercentageAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (distribSelTypePgBind.getValue() != null) {
                Number val = new Number(0);
                val = (Number) object;
                if (!(isPrecisionValid(26, 6, val))) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
                if (distribSelTypePgBind.getValue().toString().equalsIgnoreCase("A")) {
                    if (val.compareTo(0) <= 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Value should be greater than zero.", null));
                    }
                } else if (distribSelTypePgBind.getValue().toString().equalsIgnoreCase("P")) {
                    if (val.compareTo(100) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Please enter proper percentage.", null));
                    } else if (val.compareTo(100) == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Percentage value should be less than 100.",
                                                                      null));
                    }
                }
            }
        }
    }

    public void distbPercentageAmtVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("calculateAmtBasedOnPerccent");
            ob.getParamsMap().put("percentage", vce.getNewValue());
            ob.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalDistrbPerPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtNotaPgBind);
    }


    public void bdgDistributionAmountVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtNotaPgBind);

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void bdgDistributionAmountNotaVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            distrbBudgAmtPgBind.setValue(notaAmt);
        }
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(distrbBudgAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtPgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(totDistrbAmtNotaPgBind);
    }

    public void setBdgDistbAmtTablePgBind(RichTable bdgDistbAmtTablePgBind) {
        this.bdgDistbAmtTablePgBind = bdgDistbAmtTablePgBind;
    }

    public RichTable getBdgDistbAmtTablePgBind() {
        return bdgDistbAmtTablePgBind;
    }

    public void bdgSelectionTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(bdgDistbAmtTablePgBind);
    }

    public void itemDistbMethodVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDistributionTablePgBind);
    }

    public void exportExcelActionListener(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 6; i++) {
            Cell cell = createRow.createCell(i);
            switch (i) {
            case 0:
                cell.setCellValue("PRD_START_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("PRD_END_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("PRD_DTL_START_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("PRD_DTL_END_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("EO_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("BUDG_AMT_SP");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("BUDG_AMT_BS");
                cell.setCellStyle(cellStyle);
                break;
            }
        }

        /* DCBindingContainer container = (DCBindingContainer) bindings;
          String OrgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()); */

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("BdgEoBudgetPrdDtlVO1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        object.setRangeSize(-1);
        Row[] fr = object.getAllRowsInRange();
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;
        for (Row next : fr) {
            // Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            Object prdStDtlO = next.getAttribute("PrdStartDt");
            if (prdStDtlO != null && prdStDtlO.toString().length() > 0) {
                prdStDtlO = getConvertTimeStampToStr(prdStDtlO.toString());
            }
            Object prdEndDtlO = next.getAttribute("PrdEndDt");
            if (prdEndDtlO != null && prdEndDtlO.toString().length() > 0) {
                prdEndDtlO = getConvertTimeStampToStr(prdEndDtlO.toString());
            }
            Object prdDtlStDtlO = next.getAttribute("PrdDtlStartDt");
            if (prdDtlStDtlO != null && prdStDtlO.toString().length() > 0) {
                prdDtlStDtlO = getConvertTimeStampToStr(prdDtlStDtlO.toString());
            }
            Object prdDtlEndDtlO = next.getAttribute("PrdDtlEndDt");
            if (prdDtlEndDtlO != null && prdDtlEndDtlO.toString().length() > 0) {
                prdDtlEndDtlO = getConvertTimeStampToStr(prdDtlEndDtlO.toString());
            }
            Object eoIdO = next.getAttribute("EoId");
            Object bdgAmtSpO = next.getAttribute("BudgAmtSp");
            Object bdgAmtBsO = next.getAttribute("BudgAmtBs");

            StringBuilder prdStDtl =
                (prdStDtlO == null ? new StringBuilder("") : new StringBuilder(prdStDtlO.toString()));
            StringBuilder prdEndDtl =
                (prdEndDtlO == null ? new StringBuilder("") : new StringBuilder(prdEndDtlO.toString()));
            StringBuilder prdDtlStDtl =
                (prdDtlStDtlO == null ? new StringBuilder("") : new StringBuilder(prdDtlStDtlO.toString()));
            StringBuilder prdDtlEndDtl =
                (prdDtlEndDtlO == null ? new StringBuilder("") : new StringBuilder(prdDtlEndDtlO.toString()));


            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);

            cell0.setCellValue(prdStDtl.toString());
            cell1.setCellValue(prdEndDtl.toString());
            cell2.setCellValue(prdDtlStDtl.toString());
            cell3.setCellValue(prdDtlEndDtl.toString());
            cell4.setCellValue(eoIdO.toString());
            cell5.setCellValue(new Double(bdgAmtSpO.toString()));
            cell6.setCellValue(new Double(bdgAmtBsO.toString()));

            System.out.println("Row added ___________________");
            for (int i = 0; i <= 6; i++) {
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

    public void itemExportExcelAL(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 19; i++) {
            Cell cell = createRow.createCell(i);
            switch (i) {
            case 0:
                cell.setCellValue("BDG_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("CURR_ID_SP");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("CURR_CONV_RATE");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("PRD_START_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("PRD_END_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("CUST_EO_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("ITM_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("ITM_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("ITM_UOM");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("ITM_PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("ITM_QTY");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("ITM_AMT_SP");
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("ITM_AMT_BS");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("ITM_SEL_TYPE");
                cell.setCellStyle(cellStyle);
                break;
            case 14:
                cell.setCellValue("EO_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 15:
                cell.setCellValue("DISTB_METHD");
                cell.setCellStyle(cellStyle);
                break;
            case 16:
                cell.setCellValue("DISTB_UNIT");
                cell.setCellStyle(cellStyle);
                break;
            case 17:
                cell.setCellValue("DISTB_PRD_BASIS");
                cell.setCellStyle(cellStyle);
                break;
            case 18:
                cell.setCellValue("REGION");
                cell.setCellStyle(cellStyle);
                break;
            case 19:
                cell.setCellValue("BDG_FY_ID");
                cell.setCellStyle(cellStyle);
                break;
            }
        }

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("BdgEoBudgetProdDtlVO1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        object.setRangeSize(-1);
        Row[] fr = object.getAllRowsInRange();
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;
        for (Row next : fr) {
            HSSFRow row = sheet.createRow(rownum++);

            Object bdgGrpIdO = next.getAttribute("BdgGrpId");
            Object currIdSpO = next.getAttribute("CurrIdSp");
            Object currConvRateO = next.getAttribute("CurrConvRate");
            Object prdStartDtO = next.getAttribute("PrdStartDt");
            if (prdStartDtO != null && prdStartDtO.toString().length() > 0) {
                prdStartDtO = getConvertTimeStampToStr(prdStartDtO.toString());
            }
            Object prdEndDtO = next.getAttribute("PrdEndDt");
            if (prdEndDtO != null && prdEndDtO.toString().length() > 0) {
                prdEndDtO = getConvertTimeStampToStr(prdEndDtO.toString());
            }
            Object custEoIdO = next.getAttribute("CustEoId");
            Object itmGrpIdO = next.getAttribute("ItmGrpId");
            Object itmIdO = next.getAttribute("ItmId");
            Object itmUomO = next.getAttribute("ItmUom");
            Object itmPriceO = next.getAttribute("ItmPrice");
            Object itmQtyO = next.getAttribute("ItmQty");
            Object itmAmtSpO = next.getAttribute("ItmAmtSp");
            Object itmAmtBsO = next.getAttribute("ItmAmtBs");
            Object itmSelTypeO = next.getAttribute("ItmSelType");
            Object eoIdO = next.getAttribute("EoId");
            Object distbMethdO = next.getAttribute("DistbMethd");
            Object distbUnitO = next.getAttribute("DistbUnit");
            Object distbPrdBasisO = next.getAttribute("DistbPrdBasis");
            Object regionO = next.getAttribute("Region");
            Object fyIdO = next.getAttribute("BdgFyId");
            StringBuilder custEoId =
                (custEoIdO == null ? new StringBuilder("") : new StringBuilder(custEoIdO.toString()));
            StringBuilder itmGrpId =
                (itmGrpIdO == null ? new StringBuilder("") : new StringBuilder(itmGrpIdO.toString()));
            StringBuilder itmId = (itmIdO == null ? new StringBuilder("") : new StringBuilder(itmIdO.toString()));
            StringBuilder itmPrice =
                (itmPriceO == null ? new StringBuilder("") : new StringBuilder(itmPriceO.toString()));
            StringBuilder itmQty = (itmQtyO == null ? new StringBuilder("") : new StringBuilder(itmQtyO.toString()));
            StringBuilder itmUom = (itmUomO == null ? new StringBuilder("") : new StringBuilder(itmUomO.toString()));
            StringBuilder itmAmtBs =
                (itmAmtBsO == null ? new StringBuilder("") : new StringBuilder(itmAmtBsO.toString()));
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
            Cell cell15 = row.createCell(15);
            Cell cell16 = row.createCell(16);
            Cell cell17 = row.createCell(17);
            Cell cell18 = row.createCell(18);
            Cell cell19 = row.createCell(19);
            if (bdgGrpIdO != null && bdgGrpIdO.toString().length() > 0) {
                cell0.setCellValue(bdgGrpIdO.toString());
            } else {
                cell0.setCellValue(blank.toString());
            }
            cell1.setCellValue(currIdSpO.toString());
            cell2.setCellValue(new Double(currConvRateO.toString()));
            cell3.setCellValue(prdStartDtO.toString());
            cell4.setCellValue(prdEndDtO.toString());

            if (custEoId != null && custEoId.toString().length() > 0) {
                cell5.setCellValue(custEoId.toString());
            } else {
                cell5.setCellValue(blank.toString());
            }
            if (itmGrpId != null && itmGrpId.toString().length() > 0) {
                cell6.setCellValue(itmGrpId.toString());
            } else {
                cell6.setCellValue(blank.toString());
            }
            if (itmId != null && itmId.toString().length() > 0) {
                cell7.setCellValue(itmId.toString());
            } else {
                cell7.setCellValue(blank.toString());
            }
            if (itmUom != null && itmUom.toString().length() > 0) {
                cell8.setCellValue(itmUom.toString());
            } else {
                cell8.setCellValue(blank.toString());
            }
            if (itmPrice != null && itmPrice.toString().length() > 0) {
                cell9.setCellValue(new Double(itmPrice.toString()));
            } else {
                cell9.setCellValue(blank.toString());
            }
            if (itmQty != null && itmQty.toString().length() > 0) {
                cell10.setCellValue(new Double(itmQty.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }
            if (itmAmtSpO != null && itmAmtSpO.toString().length() > 0) {
                cell11.setCellValue(new Double(itmAmtSpO.toString()));
            } else {
                cell11.setCellValue(blank.toString());
            }
            if (itmAmtBs != null && itmAmtBs.toString().length() > 0) {
                cell12.setCellValue(new Double(itmAmtBs.toString()));
            } else {
                cell12.setCellValue(blank.toString());
            }
            if (itmSelTypeO != null && itmSelTypeO.toString().length() > 0) {
                cell13.setCellValue(itmSelTypeO.toString());
            } else {
                cell13.setCellValue(blank.toString());
            }
            if (eoIdO != null && eoIdO.toString().length() > 0) {
                cell14.setCellValue(eoIdO.toString());
            } else {
                cell14.setCellValue(blank.toString());
            }
            if (distbMethdO != null && distbMethdO.toString().length() > 0) {
                cell15.setCellValue(distbMethdO.toString());
            } else {
                cell15.setCellValue(blank.toString());
            }
            if (distbUnitO != null && distbUnitO.toString().length() > 0) {
                cell16.setCellValue(distbUnitO.toString());
            } else {
                cell16.setCellValue(blank.toString());
            }
            if (distbPrdBasisO != null && distbPrdBasisO.toString().length() > 0) {
                cell17.setCellValue(distbPrdBasisO.toString());
            } else {
                cell17.setCellValue(blank.toString());
            }
            if (regionO != null && regionO.toString().length() > 0) {
                cell18.setCellValue(regionO.toString());
            } else {
                cell18.setCellValue(blank.toString());
            }
            if (fyIdO != null && fyIdO.toString().length() > 0) {
                cell19.setCellValue(fyIdO.toString());
            } else {
                cell19.setCellValue(blank.toString());
            }
            System.out.println("Row added ___________________");
            for (int i = 0; i <= 19; i++) {
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

    public void itemDistrbExportExcelAL(FacesContext facesContext, OutputStream outputStream) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 13; i++) {
            Cell cell = createRow.createCell(i);
            switch (i) {
            case 0:
                cell.setCellValue("PRD_START_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("PRD_END_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("PRD_DTL_START_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("PRD_DTL_END_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("CUST_EO_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("ITM_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("ITM_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("ITM_UOM");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("ITM_PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("ITM_QTY");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("ITM_AMT_SP");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("ITM_AMT_BS");
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("EO_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("REGION");
                cell.setCellStyle(cellStyle);
                break;
            }
        }

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("BdgEoBudgetProdPrdDtlVO1Iterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        object.setRangeSize(-1);
        Row[] fr = object.getAllRowsInRange();
        RowSetIterator itr = (RowSetIterator) object.createRowSetIterator(null);
        int rownum = 1;
        for (Row next : fr) {
            HSSFRow row = sheet.createRow(rownum++);

            Object prdStartDtO = next.getAttribute("PrdStartDt");
            if (prdStartDtO != null && prdStartDtO.toString().length() > 0) {
                prdStartDtO = getConvertTimeStampToStr(prdStartDtO.toString());
            }
            Object prdEndDtO = next.getAttribute("PrdEndDt");
            if (prdEndDtO != null && prdEndDtO.toString().length() > 0) {
                prdEndDtO = getConvertTimeStampToStr(prdEndDtO.toString());
            }
            Object prdDtlStartDtO = next.getAttribute("PrdDtlStartDt");
            if (prdDtlStartDtO != null && prdDtlStartDtO.toString().length() > 0) {
                prdDtlStartDtO = getConvertTimeStampToStr(prdDtlStartDtO.toString());
            }
            Object prdDtlEndDtO = next.getAttribute("PrdDtlEndDt");
            if (prdDtlEndDtO != null && prdDtlEndDtO.toString().length() > 0) {
                prdDtlEndDtO = getConvertTimeStampToStr(prdDtlEndDtO.toString());
            }
            Object custEoIdO = next.getAttribute("CustEoId");
            Object itmGrpIdO = next.getAttribute("ItmGrpId");
            Object itmIdO = next.getAttribute("ItmId");
            Object itmUomO = next.getAttribute("ItmUom");
            Object itmPriceO = next.getAttribute("ItmPrice");
            Object itmQtyO = next.getAttribute("ItmQty");
            Object itmAmtSpO = next.getAttribute("ItmAmtSp");
            Object itmAmtBsO = next.getAttribute("ItmAmtBs");
            Object eoIdO = next.getAttribute("EoId");
            Object regionO = next.getAttribute("Region");

            StringBuilder custEoId =
                (custEoIdO == null ? new StringBuilder("") : new StringBuilder(custEoIdO.toString()));
            StringBuilder itmGrpId =
                (itmGrpIdO == null ? new StringBuilder("") : new StringBuilder(itmGrpIdO.toString()));
            StringBuilder itmId = (itmIdO == null ? new StringBuilder("") : new StringBuilder(itmIdO.toString()));
            StringBuilder itmPrice =
                (itmPriceO == null ? new StringBuilder("") : new StringBuilder(itmPriceO.toString()));
            StringBuilder itmQty = (itmQtyO == null ? new StringBuilder("") : new StringBuilder(itmQtyO.toString()));
            StringBuilder itmUom = (itmUomO == null ? new StringBuilder("") : new StringBuilder(itmUomO.toString()));
            StringBuilder itmAmtSp =
                (itmAmtSpO == null ? new StringBuilder("") : new StringBuilder(itmAmtSpO.toString()));
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

            cell0.setCellValue(prdStartDtO.toString());
            cell1.setCellValue(prdEndDtO.toString());
            cell2.setCellValue(prdDtlStartDtO.toString());
            cell3.setCellValue(prdDtlEndDtO.toString());

            if (custEoId != null && custEoId.toString().length() > 0) {
                cell4.setCellValue(custEoId.toString());
            } else {
                cell4.setCellValue(blank.toString());
            }
            if (itmGrpId != null && itmGrpId.toString().length() > 0) {
                cell5.setCellValue(itmGrpId.toString());
            } else {
                cell5.setCellValue(blank.toString());
            }
            if (itmId != null && itmId.toString().length() > 0) {
                cell6.setCellValue(itmId.toString());
            } else {
                cell6.setCellValue(blank.toString());
            }
            if (itmUom != null && itmUom.toString().length() > 0) {
                cell7.setCellValue(itmUom.toString());
            } else {
                cell7.setCellValue(blank.toString());
            }
            if (itmPrice != null && itmPrice.toString().length() > 0) {
                cell8.setCellValue(new Double(itmPrice.toString()));
            } else {
                cell8.setCellValue(blank.toString());
            }
            if (itmQty != null && itmQty.toString().length() > 0) {
                cell9.setCellValue(new Double(itmQty.toString()));
            } else {
                cell9.setCellValue(blank.toString());
            }
            if (itmAmtSp != null && itmAmtSp.toString().length() > 0) {
                cell10.setCellValue(new Double(itmAmtSp.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }
            if (itmAmtSp != null && itmAmtSp.toString().length() > 0) {
                cell10.setCellValue(new Double(itmAmtSp.toString()));
            } else {
                cell10.setCellValue(blank.toString());
            }
            if (itmAmtBsO != null && itmAmtBsO.toString().length() > 0) {
                cell11.setCellValue(new Double(itmAmtBsO.toString()));
            } else {
                cell11.setCellValue(blank.toString());
            }
            if (eoIdO != null && eoIdO.toString().length() > 0) {
                cell12.setCellValue(eoIdO.toString());
            } else {
                cell12.setCellValue(blank.toString());
            }
            if (regionO != null && regionO.toString().length() > 0) {
                cell13.setCellValue(regionO.toString());
            } else {
                cell13.setCellValue(blank.toString());
            }
            System.out.println("Row added ___________________");
            for (int i = 0; i <= 13; i++) {
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

    public String importDistrubBdgAmtAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_TAB_NAME", "BDG$EO$BUDGET$PRD$DTL");
        return "goExcel";
    }

    public String importItemFromExcelAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_TAB_NAME", "BDG$EO$BUDGET$PROD$DTL");
        return "goProductExcel";
    }

    public String importItemDistrbFromExcelAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_TAB_NAME", "BDG$EO$BUDGET$PROD$PRD$DTL");
        return "goProductExcel";
    }

    public void setUnfreezeSubordinateLinkPgBind(RichLink unfreezeSubordinateLinkPgBind) {
        this.unfreezeSubordinateLinkPgBind = unfreezeSubordinateLinkPgBind;
    }

    public RichLink getUnfreezeSubordinateLinkPgBind() {
        return unfreezeSubordinateLinkPgBind;
    }

    public void setFreezeCbPgBind(RichSelectBooleanCheckbox freezeCbPgBind) {
        this.freezeCbPgBind = freezeCbPgBind;
    }

    public RichSelectBooleanCheckbox getFreezeCbPgBind() {
        return freezeCbPgBind;
    }

    public void setTotalDistrbPerPgBind(RichOutputText totalDistrbPerPgBind) {
        this.totalDistrbPerPgBind = totalDistrbPerPgBind;
    }

    public RichOutputText getTotalDistrbPerPgBind() {
        return totalDistrbPerPgBind;
    }

    public void setTotDistrbAmtPgBind(RichOutputText totDistrbAmtPgBind) {
        this.totDistrbAmtPgBind = totDistrbAmtPgBind;
    }

    public RichOutputText getTotDistrbAmtPgBind() {
        return totDistrbAmtPgBind;
    }

    public void setItemUnitPgBind(RichSelectOneChoice itemUnitPgBind) {
        this.itemUnitPgBind = itemUnitPgBind;
    }

    public RichSelectOneChoice getItemUnitPgBind() {
        return itemUnitPgBind;
    }

    public void setItemDistrbTotAmtPgBind(RichOutputText itemDistrbTotAmtPgBind) {
        this.itemDistrbTotAmtPgBind = itemDistrbTotAmtPgBind;
    }

    public RichOutputText getItemDistrbTotAmtPgBind() {
        return itemDistrbTotAmtPgBind;
    }

    public void setItemAmtTotPgBind(RichInputText itemAmtTotPgBind) {
        this.itemAmtTotPgBind = itemAmtTotPgBind;
    }

    public RichInputText getItemAmtTotPgBind() {
        return itemAmtTotPgBind;
    }

    public void setItemAmtSpNotaPgBind(RichInputText itemAmtSpNotaPgBind) {
        this.itemAmtSpNotaPgBind = itemAmtSpNotaPgBind;
    }

    public RichInputText getItemAmtSpNotaPgBind() {
        return itemAmtSpNotaPgBind;
    }

    public void setDistrbBudgAmtPgBind(RichInputText distrbBudgAmtPgBind) {
        this.distrbBudgAmtPgBind = distrbBudgAmtPgBind;
    }

    public RichInputText getDistrbBudgAmtPgBind() {
        return distrbBudgAmtPgBind;
    }

    public void setTotDistrbAmtNotaPgBind(RichOutputText totDistrbAmtNotaPgBind) {
        this.totDistrbAmtNotaPgBind = totDistrbAmtNotaPgBind;
    }

    public RichOutputText getTotDistrbAmtNotaPgBind() {
        return totDistrbAmtNotaPgBind;
    }

    public void setCopyFyBasisPgBind(RichSelectOneChoice copyFyBasisPgBind) {
        this.copyFyBasisPgBind = copyFyBasisPgBind;
    }

    public RichSelectOneChoice getCopyFyBasisPgBind() {
        return copyFyBasisPgBind;
    }

    public void copyPreviousFYBasisVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("setCopyFyDateBasedOnSel");
            ob.getParamsMap().put("fyBasis", vce.getNewValue());
            ob.execute();
        }
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void copyPrdStDateVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void copyPrdEndDateVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void employeeNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFUtils.findOperation("chkUserEligableToAddOrNot");
            ob.getParamsMap().put("name", object);
            ob.execute();

            if (ob != null && !(Boolean) ob.getResult() && mode.equalsIgnoreCase("C")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "You are not allowed to create this Target.", null));
            }
        }
    }

    public void setToolbarPgBind(RichToolbar toolbarPgBind) {
        this.toolbarPgBind = toolbarPgBind;
    }

    public RichToolbar getToolbarPgBind() {
        return toolbarPgBind;
    }

    public void setMergeSubordinateLinkPgBind(RichLink mergeSubordinateLinkPgBind) {
        this.mergeSubordinateLinkPgBind = mergeSubordinateLinkPgBind;
    }

    public RichLink getMergeSubordinateLinkPgBind() {
        return mergeSubordinateLinkPgBind;
    }

    public void setCopyBdgFormPgBind(RichPanelFormLayout copyBdgFormPgBind) {
        this.copyBdgFormPgBind = copyBdgFormPgBind;
    }

    public RichPanelFormLayout getCopyBdgFormPgBind() {
        return copyBdgFormPgBind;
    }

    public void setReviseBdgFormPgBind(RichPanelFormLayout reviseBdgFormPgBind) {
        this.reviseBdgFormPgBind = reviseBdgFormPgBind;
    }

    public RichPanelFormLayout getReviseBdgFormPgBind() {
        return reviseBdgFormPgBind;
    }

    public void setDistributionBdgFormPgBind(RichPanelFormLayout distributionBdgFormPgBind) {
        this.distributionBdgFormPgBind = distributionBdgFormPgBind;
    }

    public RichPanelFormLayout getDistributionBdgFormPgBind() {
        return distributionBdgFormPgBind;
    }

    public void resetToPrvRevisionAL(ActionEvent actionEvent) {
        System.out.println("Inside reset to previous Revision");
        OperationBinding obChk = ADFUtils.findOperation("chkRevisionAllowedOrNot");
        obChk.execute();
        if (obChk.getResult() != null && (Boolean) obChk.getResult()) {
            ADFUtils.findOperation("resetBdgAmtToPrevious").execute();

            //ADFUtils.findOperation("updateCostCenterAmtAfterRevise").execute();
        } else {
            JSFUtils.addFacesErrorMessage("You cannot reset to previous revision ");
        }
    }

    public void setRemarksPgBind(RichInputText remarksPgBind) {
        this.remarksPgBind = remarksPgBind;
    }

    public RichInputText getRemarksPgBind() {
        return remarksPgBind;
    }

    public void setRemarksTransPgBind(RichInputText remarksTransPgBind) {
        this.remarksTransPgBind = remarksTransPgBind;
    }

    public RichInputText getRemarksTransPgBind() {
        return remarksTransPgBind;
    }

    public void copyPrdStDataValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Timestamp strtDt = null;
            Timestamp endDt = null;
            if (copyEndDtPgBind.getValue() != null && copyEndDtPgBind.getValue().toString().length() > 0) {
                strtDt = (Timestamp) copyEndDtPgBind.getValue();
                endDt = (Timestamp) object;

                if (endDt.compareTo(strtDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage("Start Date cannot be after End Date."));
                    }
                }
            }
        }
    }

    public void copyPrdEndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Timestamp strtDt = null;
            Timestamp endDt = null;
            if (copyStDatePgBind.getValue() != null && copyStDatePgBind.getValue().toString().length() > 0) {
                strtDt = (Timestamp) copyStDatePgBind.getValue();
                endDt = (Timestamp) object;

                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage("End Date can not be before Start Date."));
                    }
                }
            }
        }
    }

    public void budgetNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFUtils.findOperation("chkBudgetNameDuplicate");
            ob.getParamsMap().put("name", object);
            ob.execute();

            if (ob != null && (Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Target Name.",
                                                              null));
            }
        }
    }

    public void searchItemDetailsAL(ActionEvent actionEvent) {
        this.setItemMode("S");
        ADFUtils.findOperation("searchItemDetail").execute();
    }

    public void resetItemDetailsAL(ActionEvent actionEvent) {
        this.setItemMode("V");
        ADFUtils.findOperation("resetItemDetail").execute();
        /* DCIteratorBinding itr = ADFUtils.findIterator("BdgEoBudgetProdDtlVO1Iterator");
        itr.executeQuery(); */
    }

    public void setProcessBdgToolBarPgBind(RichToolbar processBdgToolBarPgBind) {
        this.processBdgToolBarPgBind = processBdgToolBarPgBind;
    }

    public RichToolbar getProcessBdgToolBarPgBind() {
        return processBdgToolBarPgBind;
    }

    public void setSalesTargetAmtPgBind(RichInputText salesTargetAmtPgBind) {
        this.salesTargetAmtPgBind = salesTargetAmtPgBind;
    }

    public RichInputText getSalesTargetAmtPgBind() {
        return salesTargetAmtPgBind;
    }

    public void setItemDistbAmtSpPgBind(RichInputText itemDistbAmtSpPgBind) {
        this.itemDistbAmtSpPgBind = itemDistbAmtSpPgBind;
    }

    public RichInputText getItemDistbAmtSpPgBind() {
        return itemDistbAmtSpPgBind;
    }

    public void setProductDtlButtonPgBind(RichLink productDtlButtonPgBind) {
        this.productDtlButtonPgBind = productDtlButtonPgBind;
    }

    public RichLink getProductDtlButtonPgBind() {
        return productDtlButtonPgBind;
    }

    public void amtNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       amtNotationDescPgBind.getValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
        }
    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }

    public void setPannelGroupPgBind(RichPanelGroupLayout pannelGroupPgBind) {
        this.pannelGroupPgBind = pannelGroupPgBind;
    }

    public RichPanelGroupLayout getPannelGroupPgBind() {
        return pannelGroupPgBind;
    }

    public void setItemPanelGroupPgBind(RichPanelGroupLayout itemPanelGroupPgBind) {
        this.itemPanelGroupPgBind = itemPanelGroupPgBind;
    }

    public RichPanelGroupLayout getItemPanelGroupPgBind() {
        return itemPanelGroupPgBind;
    }

    public void itemAmtNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       itemAmtNotationDescPgBind.getValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelGroupPgBind);
        }
    }

    public void setItemAmtNotationDescPgBind(RichOutputText itemAmtNotationDescPgBind) {
        this.itemAmtNotationDescPgBind = itemAmtNotationDescPgBind;
    }

    public RichOutputText getItemAmtNotationDescPgBind() {
        return itemAmtNotationDescPgBind;
    }

    public void minValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }
        }
    }

    public void maxValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }
        }
    }

    public void setMinValuePgBind(RichInputText minValuePgBind) {
        this.minValuePgBind = minValuePgBind;
    }

    public RichInputText getMinValuePgBind() {
        return minValuePgBind;
    }

    public void setMaxValuePgBind(RichInputText maxValuePgBind) {
        this.maxValuePgBind = maxValuePgBind;
    }

    public RichInputText getMaxValuePgBind() {
        return maxValuePgBind;
    }

    public String costCenterAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    public String dtlCostCenterAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "detailCostCenter";
        } else {
            return null;
        }
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }
}
