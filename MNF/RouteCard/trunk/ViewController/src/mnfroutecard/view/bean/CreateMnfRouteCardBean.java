package mnfroutecard.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import mnfroutecardapp.view.utils.ADFUtils;
import mnfroutecardapp.view.utils.JSFUtils;

import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class CreateMnfRouteCardBean {
    private RichSelectOneChoice rcBasisPgBind;
    private List<UploadedFile> uploadedFile;
    private String pgMode = "V"; // Used to set Detail part Mode whether Add/View (A/V)
    private RichInputDate startDataPgBind;
    private RichInputDate machineFromDatePgBind;
    private RichInputText pathPgBind;
    private String wfId = null;
    private RichSelectOneChoice rcCardTypePgBind;
    private RichOutputText docIdPgBind;
    private RichInputDate operationStartDatePgBind;
    private RichTable overHeadsTablePgBind;
    private RichTable machineDownTimeTablePgBind;
    private RichInputDate documentDatePgBind; // Used for Workflow to store its value
    private String availQtyEditOrNot = null; // Used for checking whether available quantity will edit or not
    private String allowWorkStationEntry = "N"; // Used to check whether to edit work station manually or not
    private String addSerialLotOrNot = "N"; // Used for entering Lot Serial or Bin in Table or Not
    private RichTable barcodeTablePgBind;
    private RichInputText itemBracketPgBind;
    private RichButton generateBarcodeButtonPgBind;
    private RichPopup selectSrNoPopUpBind;
    private RichOutputText itmIssueSrBind;
    private RichOutputText itmQtyIssueLblBind;
    private RichOutputText totStkLotBind;
    private RichPopup selectLotPopUpBind;
    private RichOutputText totStkLotBinBind;
    private RichPopup selectLotBinPopUpBind;
    private RichOutputText itmQtyIssueBinLblBind;
    private String facetName = "Lot";
    private RichTable allItemsDispPgBind;
    private RichSelectOneChoice rcStatusPgBind;
    private RichInputText actualItemQuantityPgBind;
    private RichOutputText stockAvailablePgBind;
    private RichInputDate startDateJcPgBind;
    private RichPanelFormLayout generateJcFormPgBind;
    private RichPopup generateJobCardPopPgBind;
    private RichInputListOfValues jcWorkStationPgBind;
    private RichInputListOfValues jcShiftIdPgBind;
    private RichInputListOfValues jcEmployeeIdPgBind;
    private RichInputDate jcEndDatePgBind;
    private RichInputText jcRemarkPgBind;
    private RichSelectOneChoice jcGenerateTypePgBind;
    private RichInputListOfValues inputItemBind;
    private String searchValue;
    private Key key;
    private RichOutputText itemIdPgBind;
    private RichShowDetailItem allItemsTabPgBind;
    private RichShowDetailItem overHeadsTabPgBind;
    private RichShowDetailItem machineDownTabPgBind;
    private RichShowDetailItem coProductsTabPgBind;
    private RichInputText jcOperationSrPgBind;
    private RichSelectOneChoice jcItmNmPgBind;
    private RichInputText jcQtyPgBind;
    private RichInputText jcWorkCeneterPgBind;
    private RichSelectOneChoice jcLocTypePgBind;
    private RichButton fetchDataButtonPgBind;
    private RichShowDetailItem operationItemsTabPgBind;
    private RichInputListOfValues sourceDocBindVar;
    private RichInputListOfValues reqAreaIdBindVar;
    private RichInputListOfValues warehouseIdBindVar;
    private RichInputText actOutputQtyBindVar;
    private RichPanelFormLayout outputItemFormBind;
    private String onReleaseMd = "I";
    private String outputQtyChange = "X";
    private RichPopup addLotPopUp;
    private RichInputText lotIdBindVar;
    private RichInputText lotQtyBindVar;
    private String lotNo = null;
    private String lotQty = null;
    private RichInputText lotIdInSerial;
    private RichInputText serialNoBindVar;
    private RichPanelCollection panelColLotTableBind;
    private RichPanelCollection panelColSerialTableBind;
    private String lotId_EN = null;
    private String itmSeriallized = null;
    private RichPanelTabbed panelTabBinding;
    private RichShowDetailItem showDetailsOutputitmStk;
    private RichButton releaseBTNBind;
    private RichLink saveBTNBind;
    private RichOutputText stockAvailableSFBind;
    private RichPopup selectSrNoPopUpBind_SF;
    private RichOutputText itmIssueSrBind_SF;
    private RichPopup selectLotPopUpBind_SF;
    private RichOutputText itmQtyIssueLblBind_SF;
    private RichOutputText totStkLotBind_SF;
    private RichPopup selectLotBinPopUpBind_SF;
    private RichOutputText itmQtyIssueBinLblBind_SF;
    private RichOutputText totStkLotBinBind_SF;
    private RichInputDate docDateBindVar;
    private RichSelectOneRadio fg_sfgRadioBindVar;
    private RichButton add_editLotBtnBindVar;
    private RichTable lotTableBindVar;
    private UIXSwitcher lotBinSerialSwitcher;
    private RichPanelBox lotBinSerialPanel;
    private RichTable lotBinTable;
    private RichTable lotBinSerialTable;
    private RichTable qcParamTabBind;
    private RichSelectBooleanCheckbox isPerSelBindVar;
    private RichInputText paramValueBindVar;
    private RichInputText maxTolBindVar;
    private RichInputText minValBindVar;
    private RichInputText maxValBindVar;
    private RichInputText minTolBindVar;
    private RichSelectOneChoice valueTypeBindVar;
    private RichInputListOfValues overheadParamNmeBind;
    private RichSelectOneChoice transOpNme;
    private RichLink addQueueBind;
    private RichOutputText qcOpIdBind;
    private RichInputText pendingQty;
    private RichInputText opIdBindVar;
    private RichOutputText opDocIdBindVar;
    String ccChk = resolvEl("#{pageFlowScope.CC_CHK}") == null ? null : resolvEl("#{pageFlowScope.CC_CHK}").toString();
    private String chkProj = "N";
    private RichSelectBooleanCheckbox isSubContracting;
    private RichInputListOfValues transEoNmeBind;
    private RichInputListOfValues curIdSpBind;
    String qcFlagChk = "N";
    private RichSelectBooleanCheckbox isShortCloseBind;
    private RichInputComboboxListOfValues itmToItmStkBind;
    private RichSelectOneChoice itmNmeStkBind;
    private RichInputText lotItmId;
    private String qcFlagChkForItems = "N";
    private RichSelectOneChoice itmMvmntType;
    private RichSelectOneChoice defaultPrjBind;
    private RichTable serialTableBind;
    private RichPanelCollection panelColSrBind;

    public void setQcFlagChkForItems(String qcFlagChkForItems) {
        this.qcFlagChkForItems = qcFlagChkForItems;
    }

    public String getQcFlagChkForItems() {
        return qcFlagChkForItems;
    }

    public void setQcFlagChk(String qcFlagChk) {
        this.qcFlagChk = qcFlagChk;
    }

    public String getQcFlagChk() {
        ob = ADFUtils.findOperation("qcFlagChk");
        ob.execute();
        return ob.getResult().toString();
        //return qcFlagChk;
    }

    public void setChkProj(String chkProj) {
        this.chkProj = chkProj;
    }

    public String getChkProj() {
        if(resolvEl("#{pageFlowScope.GLBL_ORG_PROJ_ALW}")!=null){
        String d = (String)resolvEl("#{pageFlowScope.GLBL_ORG_PROJ_ALW}");
        if(d.equals("Y"))
            return "Y";
        else
            return "N";
        }else 
        return "N";
       //return resolvEl("#{pageFlowScope.GLBL_ORG_PROJ_ALW}") == null ? "N" : "Y";
    }
    public void setItmSeriallized(String itmSeriallized) {
        this.itmSeriallized = itmSeriallized;
    }

    public String getItmSeriallized() {
        ob = ADFUtils.findOperation("ChkOutputItmSerialized");
        ob.execute();
        return ob.getResult().toString();
        // return itmSeriallized;
    }

    public void setLotId_EN(String lotId_EN) {
        this.lotId_EN = lotId_EN;
    }

    public String getLotId_EN() {
        ob = ADFUtils.findOperation("AllowLotIdEditable");
        ob.execute();
        return ob.getResult().toString();
        // return lotId_EN;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getLotQty() {
        return lotQty;
    }

    public void setOutputQtyChange(String outputQtyChange) {
        this.outputQtyChange = outputQtyChange;
    }

    public String getOutputQtyChange() {
        return outputQtyChange;
    }

    public void setOnReleaseMd(String onReleaseMd) {
        this.onReleaseMd = onReleaseMd;
    }

    public String getOnReleaseMd() {
        return onReleaseMd;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setAllowWorkStationEntry(String allowWorkStationEntry) {
        this.allowWorkStationEntry = allowWorkStationEntry;
    }

    public String getAllowWorkStationEntry() {
        return allowWorkStationEntry;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getFacetName() {
        return facetName;
    }

    public void setAddSerialLotOrNot(String addSerialLotOrNot) {
        this.addSerialLotOrNot = addSerialLotOrNot;
    }

    public String getAddSerialLotOrNot() {
        return addSerialLotOrNot;
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    public void setAvailQtyEditOrNot(String availQtyEditOrNot) {
        this.availQtyEditOrNot = availQtyEditOrNot;
    }

    public String getAvailQtyEditOrNot() {
        return availQtyEditOrNot;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setPgMode(String pgMode) {
        this.pgMode = pgMode;
    }

    public String getPgMode() {
        return pgMode;
    }

    public CreateMnfRouteCardBean() {
    }

    public void setRcBasisPgBind(RichSelectOneChoice rcBasisPgBind) {
        this.rcBasisPgBind = rcBasisPgBind;
    }

    public RichSelectOneChoice getRcBasisPgBind() {
        return rcBasisPgBind;
    }

    public void setStartDataPgBind(RichInputDate startDataPgBind) {
        this.startDataPgBind = startDataPgBind;
    }

    public RichInputDate getStartDataPgBind() {
        return startDataPgBind;
    }

    public void setOperationStartDatePgBind(RichInputDate operationStartDatePgBind) {
        this.operationStartDatePgBind = operationStartDatePgBind;
    }

    public RichInputDate getOperationStartDatePgBind() {
        return operationStartDatePgBind;
    }

    public void setPathPgBind(RichInputText pathPgBind) {
        this.pathPgBind = pathPgBind;
    }

    public RichInputText getPathPgBind() {
        return pathPgBind;
    }

    public void setRcCardTypePgBind(RichSelectOneChoice rcCardTypePgBind) {
        this.rcCardTypePgBind = rcCardTypePgBind;
    }

    public RichSelectOneChoice getRcCardTypePgBind() {
        return rcCardTypePgBind;
    }

    public void setDocIdPgBind(RichOutputText docIdPgBind) {
        this.docIdPgBind = docIdPgBind;
    }

    public RichOutputText getDocIdPgBind() {
        return docIdPgBind;
    }

    public void setOverHeadsTablePgBind(RichTable overHeadsTablePgBind) {
        this.overHeadsTablePgBind = overHeadsTablePgBind;
    }

    public RichTable getOverHeadsTablePgBind() {
        return overHeadsTablePgBind;
    }

    public void setMachineDownTimeTablePgBind(RichTable machineDownTimeTablePgBind) {
        this.machineDownTimeTablePgBind = machineDownTimeTablePgBind;
    }

    public RichTable getMachineDownTimeTablePgBind() {
        return machineDownTimeTablePgBind;
    }

    public void AddNewRcAL(ActionEvent actionEvent) {
        pgMode = "A";
        Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
        pageFlowScope.put("RC_MODE", "A");

        OperationBinding binding = ADFUtils.findOperation("CreateInsert");
        OperationBinding bindings = ADFUtils.findOperation("CreateInsertInSRCTab");
        binding.execute();
        bindings.execute();
        operationItemsTabPgBind.setDisclosed(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(operationItemsTabPgBind);
    }

    /*---------------------Check Validation of required feilds--------------------------*/
    public boolean chk_ValidationReqField() {
        Boolean retval = false;
        ob = ADFUtils.findOperation("chkMandatoryEnteredOrNot");
        ob.execute();
        Integer rs = (Integer) ob.getResult();
        if (rs == 1) {
            FacesMessage Message = new FacesMessage("Please Select Source Document Id.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(sourceDocBindVar.getClientId(), Message);
        } else if (rs == 2) {
            FacesMessage Message = new FacesMessage("Please Select Requirement Area Id.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(reqAreaIdBindVar.getClientId(), Message);
        } else if (rs == 3) {
            FacesMessage Message = new FacesMessage("Please Select Warehouse Id.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(warehouseIdBindVar.getClientId(), Message);
        } else if (rs == 4) {
            FacesMessage Message = new FacesMessage("Please Enter Quantity.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(actOutputQtyBindVar.getClientId(), Message);
        } else if (rs == 6) {
            FacesMessage Message = new FacesMessage("Please Select Default Project.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(defaultPrjBind.getClientId(), Message);
        } else if (rs == 5) {
            retval = true;
        } else if (isSubContracting.isSelected()) {
            String eonm = (String) getAttrsVal("MnfRcVOIterator", "TransEoNme");
            if (eonm == null)
                return false;
        }
        return retval;
    }

    public void FetchDataAction(ActionEvent actionEvent) {
        if (chk_ValidationReqField()) {
            if ("R".equals(this.getOnReleaseMd().toString())) {
                this.setOutputQtyChange("X");
                this.fetchDataButtonPgBind.setDisabled(true);
                this.actOutputQtyBindVar.setDisabled(true);
                this.allItemsTabPgBind.setDisabled(false);
                this.showDetailsOutputitmStk.setDisabled(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(fetchDataButtonPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputQtyBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsTabPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                OperationBinding binding = ADFUtils.findOperation("FetchDataToRc");
                binding.execute();
                if (binding.getResult() != null) {
                    if (!(Boolean) binding.getResult()) {
                        JSFUtils.addFacesErrorMessage("Some Problem occured while fetching data...!!!");
                    } else {
                        //  pgMode = "E";
                        //   RequestContext.getCurrentInstance().getPageFlowScope().put("RC_MODE", "V");

                        // Function which tells whether to edit Actual available qty or not
                        binding = ADFUtils.findOperation("ChkActualAvailableQtyEditOrNot");
                        binding.execute();

                        if (binding.getResult().toString().equalsIgnoreCase("N")) {
                            JSFUtils.addFacesErrorMessage("Problem in checking whether actual available quantity will edit or not...!!!");
                        } else {
                            setAvailQtyEditOrNot(binding.getResult().toString());
                            // Above line returns 2 values either 23 (STORE) or 24 (SHOP Floor)
                            // Basing on above line we will place a condition whether to edit actual available quantity or not
                        }

                        binding = ADFUtils.findOperation("ChkAllowWorkStationEntryOrNot");
                        binding.execute();

                        if (binding.getResult() != null) {
                            setAllowWorkStationEntry(binding.getResult().toString());
                            // Above line returns 2 values either Y or N
                            // Basing on above line we will place a condition whether to edit Work Station or Not
                        }

                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcCardTypePgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcBasisPgBind);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsDispPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(fetchDataButtonPgBind);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsTabPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(overHeadsTabPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(machineDownTabPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(coProductsTabPgBind);
                    }
                } else {
                    JSFUtils.addFacesErrorMessage("Unable to populate the Data...");
                }
            } else {
                OperationBinding bind = ADFUtils.findOperation("ChkDocumentStatus");
                bind.execute();
                if (bind.getResult() != null && !(Boolean) bind.getResult()) {
                    // Function which gets the data from PDO or MPP to Route Card Tables
                    OperationBinding binding = ADFUtils.findOperation("FetchDataToRc");
                    binding.execute();
                    if (binding.getResult() != null) {
                        if (!(Boolean) binding.getResult()) {
                            JSFUtils.addFacesErrorMessage("Some Problem occured while fetching data...!!!");
                        } else {
                            pgMode = "E";
                            RequestContext.getCurrentInstance().getPageFlowScope().put("RC_MODE", "V");

                            // Function which tells whether to edit Actual available qty or not
                            binding = ADFUtils.findOperation("ChkActualAvailableQtyEditOrNot");
                            binding.execute();

                            if (binding.getResult().toString().equalsIgnoreCase("N")) {
                                JSFUtils.addFacesErrorMessage("Problem in checking whether actual available quantity will edit or not...!!!");
                            } else {
                                setAvailQtyEditOrNot(binding.getResult().toString());
                                // Above line returns 2 values either 23 (STORE) or 24 (SHOP Floor)
                                // Basing on above line we will place a condition whether to edit actual available quantity or not
                            }

                            binding = ADFUtils.findOperation("ChkAllowWorkStationEntryOrNot");
                            binding.execute();

                            if (binding.getResult() != null) {
                                setAllowWorkStationEntry(binding.getResult().toString());
                                // Above line returns 2 values either Y or N
                                // Basing on above line we will place a condition whether to edit Work Station or Not
                            }

                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcCardTypePgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcBasisPgBind);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsDispPgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(fetchDataButtonPgBind);

                            AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsTabPgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(overHeadsTabPgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(machineDownTabPgBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(coProductsTabPgBind);
                        }
                    } else {
                        JSFUtils.addFacesErrorMessage("Unable to populate the Data...");
                    }
                } else {
                    JSFUtils.addFacesErrorMessage("Operation has been closed already. You cannot use the closed operation for Route Card generation.");
                }
            }
        }
    }

    /**Method to resolve expression- returns String value*/
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void EditButtonAL(ActionEvent actionEvent) {
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer pending = 0;
        OperationBinding ob = null;
        ob = ADFUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();

        if (chkUsr != null) {
            pending = chkUsr;
        }
        System.out.println("Current User-" + usr_Id + "and Order Pending At-" + pending);

        if (pending.compareTo(usr_Id) == 0) {

            pgMode = "E";
            // Function which tells whether to edit Actual available qty or not
            OperationBinding binding = ADFUtils.findOperation("ChkActualAvailableQtyEditOrNot");
            binding.execute();

            if (binding.getResult().toString().equalsIgnoreCase("N")) {
                JSFUtils.addFacesErrorMessage("Problem in checking whether actual available quantity will edit or not...!!!");
            } else {
                // Above line returns 2 values either 23 (STORE) or 24 (SHOP Floor)
                // Basing on above line we will place a condition whether to edit actual available quantity or not
                setAvailQtyEditOrNot(binding.getResult().toString());

            }

            binding = ADFUtils.findOperation("ChkAllowWorkStationEntryOrNot");
            binding.execute();

            if (binding.getResult() != null) {
                setAllowWorkStationEntry(binding.getResult().toString());
                // Above line returns 2 values either Y or N
                // Basing on above line we will place a condition whether to edit Work Station or Not
            }

        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuilder Msg22 = new StringBuilder("<html><body>");
            Msg22.append("<BR><HR><HR><BR>");
            Msg22.append("<p style='font-weight:bold;'>Route Card has been Approved, You can not edit Route Card</p>");
            Msg22.append("</body></html>");
            JSFUtils.addFacesInformationMessage(Msg22.toString());
        } else {
            ob = ADFUtils.findOperation("getUserName");
            ob.getParamsMap().put("usrId", pending);
            ob.execute();
            if (ob.getResult() != null) {
                StringBuilder message1 = new StringBuilder("<html><body>");
                message1.append("<BR><HR><HR><BR>");
                message1.append("<p>This Route Card is pending at other user<b><i> [ " + ob.getResult().toString() +
                                "] </i></b>for approval, You can not edit this.</p>");
                message1.append("</body></html>");
                JSFUtils.addFacesInformationMessage(message1.toString());
            }
        }
    }

    /*---------------------Call WfId Function------------------------------*/
    public void callWfId() {
        OperationBinding ob = ADFUtils.findOperation("getWfId");
        ob.execute();
        if (ob.getResult() != null) {
            setWfId(ob.getResult().toString());
        }
    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        //callWfId();
        OperationBinding ob = ADFUtils.findOperation("callWfFunctions");
        ob.execute();
    }

    /**
     *Code for Save Button
     * @param actionEvent
     */

    public void SaveButtonAL(ActionEvent acntionEvent) {

        if (getShortCloseChk().equals("Y") && isShortCloseBind.isSelected()) {
            setAttrsVal("MnfRcVOIterator", "RcStat", new Integer(151));
            ADFBeanUtils.findOperation("Commit").execute();
            saveBTNBind.setDisabled(true);

        } else {
            ob = ADFUtils.findOperation("chkgetYearFyId");
            ob.execute();
            if (!(ob.getResult() == -1)) {
                if (chk_ValidationReqField()) {
                    if ("Y".equals(this.getOutputQtyChange().toString())) {
                        JSFUtils.addFacesInformationMessage("Please Populate Items.");
                        setDisclosedFirstTab(operationItemsTabPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(operationItemsTabPgBind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                    } else {
                        if ("R".equals(this.getOnReleaseMd().toString())) {
                            String checkLotEntry = CheckLotEntry_Func();
                            if (checkLotEntry.equals("checked")) {
                                //By Nisha to implement Scrap Val
                                //ReleaseStat();
                             //   OperationBinding binding = ADFUtils.findOperation("Commit");
                              //  binding.execute();
                                if (ReleaseRouteCardBtn() == "true") {
                                    relaeseFlag = "Y";
                                    this.setOnReleaseMd("R");
                                }
                                if (relaeseFlag.equals("Y")) {
                                    ADFUtils.findOperation("ReleaseRouteCard").execute();
                                    JSFUtils.addFacesInformationMessage("Route Card has been released.");
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcStatusPgBind);
                                    this.setOnReleaseMd("I");

                                }
                                //this.setOnReleaseMd("R");
                                this.releaseBTNBind.setDisabled(true);
                                this.saveBTNBind.setDisabled(true);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(releaseBTNBind);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);
                            } else {
                                StringBuilder message = new StringBuilder("<html><body>");
                                message.append("<BR><HR><HR><BR>");
                                message.append("<P style='font-weight:bold'>Lot Quantity don't match the quantities allotted for the following Documents:</P>");
                                //    message.append("<P style='font-weight:900'>"+this.jobCardDispIdBind.getValue()+"</P>");
                                message.append("<UL><LI> [ " + checkLotEntry + " ] </LI></UL>");
                                message.append("</body></html>");
                                JSFUtils.addFacesInformationMessage(message.toString());
                                setDisclosedFirstTab(allItemsTabPgBind);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsTabPgBind);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                            }
                        } else {
                            if (ValidateAll("N")) {
                                // System.out.println("inside save fun nisha !!");

                                OperationBinding binding = ADFUtils.findOperation("Commit");
                                binding.execute();
                                if (ccChk.equals("Y") || ccChk == "Y") {
                                    ob = ADFBeanUtils.findOperation("sendDataFromTempCcToRcOpItm");
                                    ob.execute();
                                }
                                ADFUtils.findOperation("stkStatuspdate").execute();

                                JSFUtils.addFacesInformationMessage("Record Saved Successfully..!!");

                            }
                        }
                    }
                }
            } else {
                JSFUtils.addFacesErrorMessage("Financial Year is not Defined  !");
            }

        }
    }


    public String ReleaseRouteCardBtn() {

        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfRcItmStkVOLotIterator");
        ViewObject lotVo = dcIter.getViewObject();
        //RowSetIterator itrLot = lotVo.createRowSetIterator(null);
        Row[] itrLot = lotVo.getAllRowsInRange();

        int Ris = lotVo.getRowCount();

        HashMap hash = new HashMap();

        ob = ADFUtils.findOperation("getItmStkRecord");
        ob.execute();
        Integer stkLot = (Integer) ob.getResult();
        Row next = null;
        System.out.println("----Lot Data length is : " + Ris + " Item at op is : " + stkLot);
        if (Ris == stkLot) {
            System.out.println("inside my loop---" + itrLot);
            // while (itrLot.hasNext()) {
            //  next = itrLot.next();
            for (int i = 0; i < itrLot.length; i++) {
                next = itrLot[i];

                ob = ADFUtils.findOperation("ChkItmSerialized");
                ob.getParamsMap().put("ItmId", (String) next.getAttribute("ItmId"));
                ob.execute();
                Object ser = ob.getResult();

                if (ser.equals("Y") || ser == "Y") {
                    Number totQty = (Number) next.getAttribute("TotQty");
                    int count = 0;
                    ViewObject vo = ADFUtils.findIterator("MnfRcItmStkSrVO1Iterator").getViewObject();
                    RowSetIterator rsetIter = vo.createRowSetIterator(null);
                    Row r = null;
                    while (rsetIter.hasNext()) {
                        r = rsetIter.next();
                        if (r.getAttribute("ItmId").equals((String) next.getAttribute("ItmId"))) {
                            count++;
                        }
                        hash.put((String) next.getAttribute("ItmId"), count);
                    }
                    rsetIter.closeRowSetIterator();
                    Number val = new Number(count);
                    if (totQty.compareTo(val) != 0) {
                        StringBuilder m2 = new StringBuilder("<html><body>");
                        m2.append("<BR><HR><HR><BR>");
                        m2.append("<p style='font-weight:bold;'>Item is Serialized.   ");
                        m2.append(next.getAttribute("ItmId"));
                        m2.append("        Although Serial No. are still to be added </p>");
                        m2.append("</body></html>");
                        JSFUtils.addFacesInformationMessage(m2.toString());
                        setDisclosedFirstTab(showDetailsOutputitmStk);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                        return "false";
                    }
                } else if (ser.equals("N") || ser == "N") {

                    hash.put((String) next.getAttribute("ItmId"), 1);

                }

                //itrLot.closeRowSetIterator();
            }
        } else {
            StringBuilder m2 = new StringBuilder("<html><body>");
            m2.append("<BR><HR><HR><BR>");
            m2.append("<p style='font-weight:bold;'>Lot for Operation Item is Required !!</p>");
            m2.append("</body></html>");
            JSFUtils.addFacesInformationMessage(m2.toString());
            setDisclosedFirstTab(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            return "false";
        }
        return "true";
    }

    public void ReleaseStat() {
        DCIteratorBinding iters = ADFUtils.findIterator("MnfRcItmStkVOLotIterator");
        Row[] arIRs = iters.getAllRowsInRange();
        Integer Ris = (Integer) arIRs.length;
        if (Ris == 0) {
            StringBuilder m1 = new StringBuilder("<html><body>");
            m1.append("<BR><HR><HR><BR>");
            m1.append("<P style='font-weight:bold'>Lot Quantity don't match the quantity of Output Item </P>");
            m1.append("</body></html>");
            JSFUtils.addFacesInformationMessage(m1.toString());
            setDisclosedFirstTab(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);

        } else {
            ob = ADFUtils.findOperation("ChkOutputItmSerialized");
            ob.execute();
            String Schk = (String) ob.getResult();
            if (Schk.equals("Y")) {
                //DCIteratorBinding iter = ADFUtils.findIterator("MnfRcItmStkVOSerialIterator");
                DCIteratorBinding iter = ADFUtils.findIterator("MnfRcItmStkSrVO1Iterator");
                Row[] arIR = iter.getAllRowsInRange();
                Integer Ri = (Integer) arIR.length;
                Integer Qtyi = Integer.parseInt(this.actOutputQtyBindVar.getValue().toString());
                if (Ri.compareTo(Qtyi) == 0) {
                    // ADFUtils.findOperation("deleteStkLot").execute();
                    ADFUtils.findOperation("ReleaseRouteCard").execute();
                    JSFUtils.addFacesInformationMessage("Route Card has been released.");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcStatusPgBind);
                    this.setOnReleaseMd("I");
                } else {
                    StringBuilder m2 = new StringBuilder("<html><body>");
                    m2.append("<BR><HR><HR><BR>");
                    m2.append("<p style='font-weight:bold;'>Output Item is Serialized. Please Enter Serial No. </p>");
                    m2.append("</body></html>");
                    JSFUtils.addFacesInformationMessage(m2.toString());
                    setDisclosedFirstTab(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                }
            } else if (Schk.equals("N")) {
                ob = ADFUtils.findOperation("getItmStkRecord");
                ob.execute();
                Integer stkLot = (Integer) ob.getResult();
                if (Ris == stkLot) {
                    relaeseFlag = "Y";
                    this.setOnReleaseMd("R");

                }
                if (relaeseFlag.equals("Y")) {
                    ADFUtils.findOperation("ReleaseRouteCard").execute();
                    JSFUtils.addFacesInformationMessage("Route Card has been released.");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcStatusPgBind);
                    this.setOnReleaseMd("I");
                } else {
                    StringBuilder m2 = new StringBuilder("<html><body>");
                    m2.append("<BR><HR><HR><BR>");
                    m2.append("<p style='font-weight:bold;'>Lot for Operation Output Item is Required !!</p>");
                    m2.append("</body></html>");
                    JSFUtils.addFacesInformationMessage(m2.toString());
                    setDisclosedFirstTab(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);

                }
            }
        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            //DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public Boolean ValidateAll(String val) {
        Boolean retVal = false;

        callFuncWf();
        pgMode = "V";
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_DOC_ID", docIdPgBind.getValue());
        //ob = ADFUtils.findOperation("UpdateDocumentOpStatus"); // Will change the status of Operation to close
        //  ob.getParamsMap().put("Flag", val);
        //   ob.execute();
        DCIteratorBinding iter = ADFUtils.findIterator("MnfRcOpOutputItmVO1Iterator");
        Row[] allRowsInRange = iter.getAllRowsInRange();
        if (allRowsInRange.length > 0) {
            retVal = true; // Did this if any conditions may come in further situation at the time use this
        } else {
            JSFUtils.addFacesErrorMessage("Route card cannot be created with out Single output item.");
        }

        return retVal;
    }

    /**
     *Code for Save and Forward Function
     * @return
     */

    public String SaveAndForwardAction() {
        ob = ADFUtils.findOperation("chkgetYearFyId");
        ob.execute();
        if (!(ob.getResult() == -1)) {
            if (chk_ValidationReqField() && ValidateAll("Y")) {
                callWfId();
                //if(!(ob.getResult() == -1))
                // {
                if (isSubContracting.isSelected()) {
                    ob = ADFBeanUtils.findOperation("insertIntoSubContrator");
                    ob.execute();
                }
                OperationBinding binding = ADFUtils.findOperation("Commit");
                binding.execute();
                // ADFUtils.findOperation("stkStatuspdate").execute();
                if (ccChk.equals("Y") || ccChk == "Y") {
                    ob = ADFBeanUtils.findOperation("sendDataFromTempCcToRcOpItm");
                    ob.execute();
                }
                JSFUtils.addFacesInformationMessage("Record Saved Successfully..!!");
                //  }

                return "workFlow";
            }
        } else {
            JSFUtils.addFacesErrorMessage("Financial Year is not Defined  !");
        }
        // ADFUtils.findOperation("stkStatuspdate").execute();
        return null;
    }

    /**
     * Method to save uploaded files in file system and DB tables
     * **/
    public String uploadFiles() throws Exception {
        OperationBinding op = null;
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

                    //Add files to the Table
                    op = ADFUtils.findOperation("createAttchmntRow");
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
                    }
                    out.flush();

                    //call commit after checking all validations
                    ADFUtils.findOperation("Commit").execute();
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
        ADFUtils.findOperation("Execute5").execute();
        return "null";
    }

    public void EndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (startDataPgBind.getValue() != null) {
                // System.out.println("From Date is " + startDataPgBind.getValue());
                // System.out.println("To date is " + object);

                Timestamp t1 = (Timestamp) startDataPgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                // System.out.println("T2 Compare to T1 " + t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }

    public void OverHeadParameterValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding op = ADFUtils.findOperation("ChkOverHeadDuplicate");
            op.getParamsMap().put("paramName", object.toString());
            op.execute();

            if ((Boolean) op.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already exist...",
                                                              null));
            }
        }
    }

    public void MachineDownParamValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding op = ADFUtils.findOperation("ChkMachineDownDuplicate");
            op.getParamsMap().put("downName", object.toString());
            op.execute();
            System.out.println("Result is " + op.getResult());
            if ((Boolean) op.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already exist...",
                                                              null));
            }
        }
    }

    public void setMachineFromDatePgBind(RichInputDate machineFromDatePgBind) {
        this.machineFromDatePgBind = machineFromDatePgBind;
    }

    public RichInputDate getMachineFromDatePgBind() {
        return machineFromDatePgBind;
    }

    public void machineToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (machineFromDatePgBind.getValue() != null) {
                // System.out.println("From Date is " + machineFromDatePgBind.getValue());
                // System.out.println("To date is " + object);

                Timestamp t1 = (Timestamp) machineFromDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                // System.out.println("T2 Compare to T1 " + t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }

    /**
     * Code for Precision Check
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void InputAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    public void OutputValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    public void ProductsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            //  System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    /**Method to download file from actual path
     * @param facesContext
     * @param outputStream
     */
    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) {
        //Read file from particular path, path bind is binding of table field that contains path
        //File filed = new File(pathPgBind.getValue().toString());
        String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
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
            JSFUtils.addFacesErrorMessage("Problem in downloading a file");
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

    public void DocumentDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                Timestamp t1 = new Timestamp(System.currentTimeMillis());
                Timestamp t2 = (Timestamp) object;

                Date d1 = t1.dateValue();
                Date d2 = t2.dateValue();

                // System.out.println("Document Date Validator is " + t2.compareTo(t1) + !d1.toString().equals(d2.toString()));
                if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Document Date Should be greater than Current Date.",
                                                                  null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void OperationEndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (operationStartDatePgBind.getValue() != null) {
                // System.out.println("From Date is " + operationStartDatePgBind.getValue());
                //System.out.println("To date is " + object);

                Timestamp t1 = (Timestamp) operationStartDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                // System.out.println("T2 Compare to T1 " + t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }

    public void OverHeadsParamNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(overHeadsTablePgBind);
        }
    }

    public void MachineDownTimeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(machineDownTimeTablePgBind);
        }
    }

    public void setDocumentDatePgBind(RichInputDate documentDatePgBind) {
        this.documentDatePgBind = documentDatePgBind;
    }

    public RichInputDate getDocumentDatePgBind() {
        return documentDatePgBind;
    }

    public void DeleteAttachmentAL(ActionEvent actionEvent) {
        Object filePath = actionEvent.getComponent().getAttributes().get("pathWithName");
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : " + rowKey);
        if (filePath != null) {
            System.out.println("File Path : " + filePath.toString());
            OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteAttachFileRow");
            binding.getParamsMap().put("path", filePath.toString());
            binding.execute();
            ADFUtils.findOperation("Commit").execute();
        }
        /*         String path = null;

        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null) {
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();

            System.out.println("File path in AMimpl" + path);
            File f = new File(path);
            try {
                boolean success = f.delete();
                if (success) {
                    System.out.println("File Deleted");
                    ADFUtils.findOperation("Delete").execute();
                    ADFUtils.findOperation("Commit").execute();
                    ADFUtils.findOperation("Execute5").execute();
                }
            } catch (Exception x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            }
        } */
    }

    public void ItemBracketValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer val = (Integer) object;

            if (val < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }

        }
    }

    public void GenerateBarcodeAL(ActionEvent actionEvent) {
        if (itemBracketPgBind.getValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("GenerateBarCode");
            ob.execute();
            if ((Boolean) ob.getResult()) {
                ADFUtils.findOperation("Commit").execute();
                ADFUtils.findOperation("Execute").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeTablePgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemBracketPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(generateBarcodeButtonPgBind);
            } else {
                JSFUtils.addFacesErrorMessage("Problem in generating Barcode.");
            }
        } else {
            FacesMessage message = new FacesMessage("Please enter mandatory field.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itemBracketPgBind.getClientId(), message);
        }

    }

    public void setBarcodeTablePgBind(RichTable barcodeTablePgBind) {
        this.barcodeTablePgBind = barcodeTablePgBind;
    }

    public RichTable getBarcodeTablePgBind() {
        return barcodeTablePgBind;
    }

    public void setItemBracketPgBind(RichInputText itemBracketPgBind) {
        this.itemBracketPgBind = itemBracketPgBind;
    }

    public RichInputText getItemBracketPgBind() {
        return itemBracketPgBind;
    }

    public void setGenerateBarcodeButtonPgBind(RichButton generateBarcodeButtonPgBind) {
        this.generateBarcodeButtonPgBind = generateBarcodeButtonPgBind;
    }

    public RichButton getGenerateBarcodeButtonPgBind() {
        return generateBarcodeButtonPgBind;
    }

    /**
     * Method to open a popup using binding.
     */
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

    public void autoIssueItemAction(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFUtils.findOperation("IssueAutoItem").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
        }
    }

    public void SerialNoForItmAL(ActionEvent actionEvent) {

        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {

            ADFUtils.findOperation("FilterSrNoAsPerItem").execute();

            showPopup(selectSrNoPopUpBind, true);
        }
    }

    public void setSelectSrNoPopUpBind(RichPopup selectSrNoPopUpBind) {
        this.selectSrNoPopUpBind = selectSrNoPopUpBind;
    }

    public RichPopup getSelectSrNoPopUpBind() {
        return selectSrNoPopUpBind;
    }

    public void setItmIssueSrBind(RichOutputText itmIssueSrBind) {
        this.itmIssueSrBind = itmIssueSrBind;
    }

    public RichOutputText getItmIssueSrBind() {
        return itmIssueSrBind;
    }

    public void SelectLotFrItmAction(ActionEvent actionEvent) {

        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {

            ADFUtils.findOperation("FilterLotWhWise").execute();

            showPopup(selectLotPopUpBind, true);
        }
    }

    public void setItmQtyIssueLblBind(RichOutputText itmQtyIssueLblBind) {
        this.itmQtyIssueLblBind = itmQtyIssueLblBind;
    }

    public RichOutputText getItmQtyIssueLblBind() {
        return itmQtyIssueLblBind;
    }

    public void IssueQtyLotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBind.getValue() != null) {
                lotQty = (Number) totStkLotBind.getValue();
                System.out.println("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    public void setTotStkLotBind(RichOutputText totStkLotBind) {
        this.totStkLotBind = totStkLotBind;
    }

    public RichOutputText getTotStkLotBind() {
        return totStkLotBind;
    }

    public void setSelectLotPopUpBind(RichPopup selectLotPopUpBind) {
        this.selectLotPopUpBind = selectLotPopUpBind;
    }

    public RichPopup getSelectLotPopUpBind() {
        return selectLotPopUpBind;
    }

    public void SelectLotBinFrItmAction(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {

            ADFUtils.findOperation("FilterBinWhWise").execute();

            showPopup(selectLotBinPopUpBind, true);
        }
    }

    public void setTotStkLotBinBind(RichOutputText totStkLotBinBind) {
        this.totStkLotBinBind = totStkLotBinBind;
    }

    public RichOutputText getTotStkLotBinBind() {
        return totStkLotBinBind;
    }

    public void IssueQtyLotBinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBinBind.getValue() != null) {
                lotQty = (Number) totStkLotBinBind.getValue();
                System.out.println("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    public void setSelectLotBinPopUpBind(RichPopup selectLotBinPopUpBind) {
        this.selectLotBinPopUpBind = selectLotBinPopUpBind;
    }

    public RichPopup getSelectLotBinPopUpBind() {
        return selectLotBinPopUpBind;
    }

    public void setItmQtyIssueBinLblBind(RichOutputText itmQtyIssueBinLblBind) {
        this.itmQtyIssueBinLblBind = itmQtyIssueBinLblBind;
    }

    public RichOutputText getItmQtyIssueBinLblBind() {
        return itmQtyIssueBinLblBind;
    }

    public void ViewLotActionListener(ActionEvent actionEvent) {
        this.setFacetName("Lot");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFUtils.findOperation("Execute1").execute();
    }

    public void ViewBinActionListener(ActionEvent actionEvent) {
        this.setFacetName("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFUtils.findOperation("Execute2").execute();
    }

    public void ViewSerialsActionListener(ActionEvent actionEvent) {
        this.setFacetName("SrNo");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFUtils.findOperation("Execute3").execute();
    }

    public void setAllItemsDispPgBind(RichTable allItemsDispPgBind) {
        this.allItemsDispPgBind = allItemsDispPgBind;
    }

    public RichTable getAllItemsDispPgBind() {
        return allItemsDispPgBind;
    }

    public void AllItemsAvailableQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    /*--------------------------Method for disclosed panel tab--------------------*/
    public void setDisclosedFirstTab(RichShowDetailItem tabBind) {
        RichPanelTabbed richPanelTabbed = getPanelTabBinding();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            if (sdi.getClientId().equals(tabBind.getClientId())) {
                sdi.setDisclosed(true);
            } else {
                sdi.setDisclosed(false);
            }
        }
    }

    public void ReleaseButtonAL(ActionEvent actionEvent) {

        if (isSubContracting.isSelected()) {

            JSFUtils.addFacesInformationMessage("Route card is processing under Subcontracting. So cant not Release it !");

        } else {
            //Commented by nisha to provide opwise itm entry in itmstk
            // ADFUtils.findOperation("CreateWithParamsInStkLot").execute();
            this.setOnReleaseMd("R");
            this.setOutputQtyChange("Y");
            this.releaseBTNBind.setDisabled(true);
            this.actOutputQtyBindVar.setDisabled(false);
            this.allItemsTabPgBind.setDisabled(true);
            this.showDetailsOutputitmStk.setDisabled(true);
            setDisclosedFirstTab(operationItemsTabPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(releaseBTNBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputQtyBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemFormBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fetchDataButtonPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(operationItemsTabPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(allItemsTabPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
        }
    }

    public void setRcStatusPgBind(RichSelectOneChoice rcStatusPgBind) {
        this.rcStatusPgBind = rcStatusPgBind;
    }

    public RichSelectOneChoice getRcStatusPgBind() {
        return rcStatusPgBind;
    }

    public void setActualItemQuantityPgBind(RichInputText actualItemQuantityPgBind) {
        this.actualItemQuantityPgBind = actualItemQuantityPgBind;
    }

    public RichInputText getActualItemQuantityPgBind() {
        return actualItemQuantityPgBind;
    }

    public void setStockAvailablePgBind(RichOutputText stockAvailablePgBind) {
        this.stockAvailablePgBind = stockAvailablePgBind;
    }

    public RichOutputText getStockAvailablePgBind() {
        return stockAvailablePgBind;
    }

    /**
     * Function which will check whether mandatory fields entered or not
     */
    public Boolean chkJcMandatorEnteredOrNot() {
        Boolean val = false;
        FacesMessage message2 = null;
        String clientId = null;
        if (jcGenerateTypePgBind.getValue() != null && jcGenerateTypePgBind.getValue().toString().length() > 0) {
            if ((jcGenerateTypePgBind.getValue() != null && jcGenerateTypePgBind.getValue().equals(2)) ||
                (jcWorkStationPgBind.getValue() != null && jcWorkStationPgBind.getValue().toString().length() > 0)) {
                if (jcShiftIdPgBind.getValue() != null && jcShiftIdPgBind.getValue().toString().length() > 0) {
                    if (jcEmployeeIdPgBind.getValue() != null &&
                        jcEmployeeIdPgBind.getValue().toString().length() > 0) {
                        if (startDateJcPgBind.getValue() != null &&
                            startDateJcPgBind.getValue().toString().length() > 0) {
                            if (jcEndDatePgBind.getValue() != null &&
                                jcEndDatePgBind.getValue().toString().length() > 0) {
                                if (jcRemarkPgBind.getValue() != null &&
                                    jcRemarkPgBind.getValue().toString().length() > 0) {
                                    val = true;
                                } else {
                                    message2 = new FacesMessage("Please Enter Remarks.");
                                    clientId = jcRemarkPgBind.getClientId();
                                }
                            } else {
                                message2 = new FacesMessage("Please Select End Date.");
                                clientId = jcEndDatePgBind.getClientId();
                            }
                        } else {
                            message2 = new FacesMessage("Please Select Start Date.");
                            clientId = startDateJcPgBind.getClientId();
                        }
                    } else {
                        message2 = new FacesMessage("Please Select Employee Name.");
                        clientId = jcEmployeeIdPgBind.getClientId();
                    }
                } else {
                    message2 = new FacesMessage("Please Select Shift Id.");
                    clientId = jcShiftIdPgBind.getClientId();
                }
            } else {
                message2 = new FacesMessage("Please Select Work Station.");
                clientId = jcWorkStationPgBind.getClientId();
            }
        } else {
            message2 = new FacesMessage("Please Select Operation Type.");
            clientId = jcGenerateTypePgBind.getClientId();
        }
        if (message2 != null && clientId != null) {
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(clientId, message2);
        }
        return val;
    }

    public void GenerateJobCardDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            if (chkJcMandatorEnteredOrNot()) {
                OperationBinding ob = ADFUtils.findOperation("GenerateJobCardOnType");
                ob.execute();
                if (ob.getResult() != null && (Boolean) ob.getResult()) {
                    JSFUtils.addFacesInformationMessage("Job Card generated Successfully.");
                    ADFUtils.findOperation("Commit").execute();
                }

                ADFUtils.findOperation("RefreshJcVO").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(generateJcFormPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcStatusPgBind);
            }
        }
    }

    public void setStartDateJcPgBind(RichInputDate startDateJcPgBind) {
        this.startDateJcPgBind = startDateJcPgBind;
    }

    public RichInputDate getStartDateJcPgBind() {
        return startDateJcPgBind;
    }

    public void EndDateJcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (startDateJcPgBind.getValue() != null) {
                // System.out.println("From Date is " + startDataPgBind.getValue());
                // System.out.println("To date is " + object);

                Timestamp t1 = (Timestamp) startDateJcPgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                //  System.out.println("T2 Compare to T1 " + t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }

    public void GenerateTypeVCL(ValueChangeEvent valueChangeEvent) {
        ADFUtils.findOperation("RefreshJcVO").execute();
    }

    public void setGenerateJcFormPgBind(RichPanelFormLayout generateJcFormPgBind) {
        this.generateJcFormPgBind = generateJcFormPgBind;
    }

    public RichPanelFormLayout getGenerateJcFormPgBind() {
        return generateJcFormPgBind;
    }

    public void setGenerateJobCardPopPgBind(RichPopup generateJobCardPopPgBind) {
        this.generateJobCardPopPgBind = generateJobCardPopPgBind;
    }

    public RichPopup getGenerateJobCardPopPgBind() {
        return generateJobCardPopPgBind;
    }

    public void GenerateJobCardAL(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("ChkAllowWorkStationEntryOrNot");
        binding.execute();

        if (binding.getResult() != null) {
            setAllowWorkStationEntry(binding.getResult().toString());
            // Above line returns 2 values either Y or N
            // Basing on above line we will place a condition whether to edit Work Station or Not
        }

        ADFUtils.findOperation("RefreshJcVO").execute();

        showPopup(generateJobCardPopPgBind, true);
    }

    public void setJcWorkStationPgBind(RichInputListOfValues jcWorkStationPgBind) {
        this.jcWorkStationPgBind = jcWorkStationPgBind;
    }

    public RichInputListOfValues getJcWorkStationPgBind() {
        return jcWorkStationPgBind;
    }

    public void setJcShiftIdPgBind(RichInputListOfValues jcShiftIdPgBind) {
        this.jcShiftIdPgBind = jcShiftIdPgBind;
    }

    public RichInputListOfValues getJcShiftIdPgBind() {
        return jcShiftIdPgBind;
    }

    public void setJcEmployeeIdPgBind(RichInputListOfValues jcEmployeeIdPgBind) {
        this.jcEmployeeIdPgBind = jcEmployeeIdPgBind;
    }

    public RichInputListOfValues getJcEmployeeIdPgBind() {
        return jcEmployeeIdPgBind;
    }

    public void setJcEndDatePgBind(RichInputDate jcEndDatePgBind) {
        this.jcEndDatePgBind = jcEndDatePgBind;
    }

    public RichInputDate getJcEndDatePgBind() {
        return jcEndDatePgBind;
    }

    public void setJcRemarkPgBind(RichInputText jcRemarkPgBind) {
        this.jcRemarkPgBind = jcRemarkPgBind;
    }

    public RichInputText getJcRemarkPgBind() {
        return jcRemarkPgBind;
    }

    public void setJcGenerateTypePgBind(RichSelectOneChoice jcGenerateTypePgBind) {
        this.jcGenerateTypePgBind = jcGenerateTypePgBind;
    }

    public RichSelectOneChoice getJcGenerateTypePgBind() {
        return jcGenerateTypePgBind;
    }

    public void SearchInputItemAL(ActionEvent actionEvent) {
        if (itemIdPgBind.getValue() != null) {
            String itmName = itemIdPgBind.getValue().toString();
            //System.out.println("item Name " + itmName);
            setSearchValue(itmName);
            DCIteratorBinding it = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = allItemsDispPgBind.getSelectedRowKeys();

            if (rsi.first() != null) {
                //  System.out.println("Inside of the loop");
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFoundInput(r, oldSelection))) {
                    r = rsi.next();
                }
            }
        }
    }

    private boolean matchFoundInput(Row r, RowKeySet oldSelection) {
        setKey(null);
        // System.out.println("inside of the match function");
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = (String) r.getAttribute("ItmId");
        if (rowValue.toString().contains(searchValue)) {
            // System.out.println("now setting key to " + key);
            key = r.getKey();
            lst.add(key);
            allItemsDispPgBind.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrentInput(allItemsDispPgBind, newSelection, oldSelection);
            return true;
        }
        return false;
    }

    private void makeCurrentInput(RichTable outputItemTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, outputItemTableBind);
        selectionEvent.queue();

        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemTableBind);
    }


    public void setInputItemBind(RichInputListOfValues inputItemBind) {
        this.inputItemBind = inputItemBind;
    }

    public RichInputListOfValues getInputItemBind() {
        return inputItemBind;
    }

    public void setItemIdPgBind(RichOutputText itemIdPgBind) {
        this.itemIdPgBind = itemIdPgBind;
    }

    public RichOutputText getItemIdPgBind() {
        return itemIdPgBind;
    }

    public void OperationNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(jcOperationSrPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcItmNmPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcQtyPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcWorkCeneterPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcLocTypePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcWorkStationPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcShiftIdPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcEmployeeIdPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(startDateJcPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcEndDatePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcRemarkPgBind);
            //    String opId = (String)getAttrsVal("MnfGenerateJcDualVO1Iterator", "OperationIdDispTrans");
            //   System.out.println("val " + opIdBindVar.getValue() + " " + opDocIdBindVar.getValue()  );
            ob = ADFUtils.findOperation("getPendingItmForJC");
            ob.getParamsMap().put("op_doc_id", vce.getNewValue());
            // ob.getParamsMap().put("op_doc_id", opDocIdBindVar.getValue());
            ob.getParamsMap().put("jc_rc", 105);
            ob.execute();
            System.out.println(ob.getResult());
            setAttrsVal("MnfGenerateJcDualVO1Iterator", "PendindOpQty", ob.getResult());
        }


    }

    public void OverheadValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    public void setAllItemsTabPgBind(RichShowDetailItem allItemsTabPgBind) {
        this.allItemsTabPgBind = allItemsTabPgBind;
    }

    public RichShowDetailItem getAllItemsTabPgBind() {
        return allItemsTabPgBind;
    }

    public void setOverHeadsTabPgBind(RichShowDetailItem overHeadsTabPgBind) {
        this.overHeadsTabPgBind = overHeadsTabPgBind;
    }

    public RichShowDetailItem getOverHeadsTabPgBind() {
        return overHeadsTabPgBind;
    }

    public void setMachineDownTabPgBind(RichShowDetailItem machineDownTabPgBind) {
        this.machineDownTabPgBind = machineDownTabPgBind;
    }

    public RichShowDetailItem getMachineDownTabPgBind() {
        return machineDownTabPgBind;
    }

    public void setCoProductsTabPgBind(RichShowDetailItem coProductsTabPgBind) {
        this.coProductsTabPgBind = coProductsTabPgBind;
    }

    public RichShowDetailItem getCoProductsTabPgBind() {
        return coProductsTabPgBind;
    }

    public void setJcOperationSrPgBind(RichInputText jcOperationSrPgBind) {
        this.jcOperationSrPgBind = jcOperationSrPgBind;
    }

    public RichInputText getJcOperationSrPgBind() {
        return jcOperationSrPgBind;
    }

    public void setJcItmNmPgBind(RichSelectOneChoice jcItmNmPgBind) {
        this.jcItmNmPgBind = jcItmNmPgBind;
    }

    public RichSelectOneChoice getJcItmNmPgBind() {
        return jcItmNmPgBind;
    }

    public void setJcQtyPgBind(RichInputText jcQtyPgBind) {
        this.jcQtyPgBind = jcQtyPgBind;
    }

    public RichInputText getJcQtyPgBind() {
        return jcQtyPgBind;
    }

    public void setJcWorkCeneterPgBind(RichInputText jcWorkCeneterPgBind) {
        this.jcWorkCeneterPgBind = jcWorkCeneterPgBind;
    }

    public RichInputText getJcWorkCeneterPgBind() {
        return jcWorkCeneterPgBind;
    }

    public void setJcLocTypePgBind(RichSelectOneChoice jcLocTypePgBind) {
        this.jcLocTypePgBind = jcLocTypePgBind;
    }

    public RichSelectOneChoice getJcLocTypePgBind() {
        return jcLocTypePgBind;
    }

    public void setFetchDataButtonPgBind(RichButton fetchDataButtonPgBind) {
        this.fetchDataButtonPgBind = fetchDataButtonPgBind;
    }

    public RichButton getFetchDataButtonPgBind() {
        return fetchDataButtonPgBind;
    }

    public void setOperationItemsTabPgBind(RichShowDetailItem operationItemsTabPgBind) {
        this.operationItemsTabPgBind = operationItemsTabPgBind;
    }

    public RichShowDetailItem getOperationItemsTabPgBind() {
        return operationItemsTabPgBind;
    }

    public void CancelSrNoPopupAL(ActionEvent actionEvent) {
        selectSrNoPopUpBind.hide();
    }

    public void OkSelectItmLotBinSrAL(ActionEvent actionEvent) {

        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtySr");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();

            if (itmIssueSrBind.getValue() != null) {
                pickQty = (Number) itmIssueSrBind.getValue();
            }

            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoPickItmSr").execute();

                //  ADFUtils.findOperation("Execute1").execute();
                //  ADFUtils.findOperation("Execute2").execute();
                //     ADFUtils.findOperation("Execute3").execute();
                //     ADFUtils.findOperation("Execute4").execute();

                if (parentKey != null) {
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectSrNoPopUpBind.hide();
                //AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
            }
        }
    }

    public void CancelItemLotButtonAL(ActionEvent actionEvent) {
        selectLotPopUpBind.hide();
    }

    public void OkItemLotButtonAL(ActionEvent actionEvent) {
        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        System.out.println("Key before lot-" + parentKey);
        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtyLot");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();
            System.out.println("pickqty bind-" + itmQtyIssueLblBind.getValue());
            if (itmQtyIssueLblBind.getValue() != null) {
                pickQty = (Number) itmQtyIssueLblBind.getValue();
            }
            System.out.println("IssueQty-" + issQty + "PickedQty-" + pickQty);
            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoRcItmLot").execute();

                // ADFUtils.findOperation("Execute1").execute();
                //  ADFUtils.findOperation("Execute2").execute();
                //  ADFUtils.findOperation("Execute3").execute();
                //  ADFUtils.findOperation("Execute4").execute();

                if (parentKey != null) {
                    System.out.println("Key before lot-" + parentKey);
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectLotPopUpBind.hide();
                // AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);

                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
            }
        }
    }

    public void CancelItemLotBinButtonAL(ActionEvent actionEvent) {
        selectLotBinPopUpBind.hide();
    }

    public void OkItemLotBinButtonAL(ActionEvent actionEvent) {
        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        System.out.println("Key before Bin-" + parentKey);

        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtyBin");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();
            System.out.println("Orderqty bind-" + itmQtyIssueBinLblBind.getValue());
            if (itmQtyIssueBinLblBind.getValue() != null) {
                pickQty = (Number) itmQtyIssueBinLblBind.getValue();
            }
            System.out.println("IssueQty-" + issQty + "PickedQty-" + pickQty);
            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoRcItmBin").execute();
                /* ADFUtils.findOperation("Execute1").execute();
                ADFUtils.findOperation("Execute2").execute();
                ADFUtils.findOperation("Execute3").execute();
                ADFUtils.findOperation("Execute4").execute(); */

                if (parentKey != null) {
                    System.out.println("Key before lot-" + parentKey);
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectLotBinPopUpBind.hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
            }
        }
    }

    /*----------------------Method for get Binding------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void sourceDocIdVCL(ValueChangeEvent valueChangeEvent) {
        this.actOutputQtyBindVar.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemFormBind);
    }

    public void actualouputItmQtyValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    public void setSourceDocBindVar(RichInputListOfValues sourceDocBindVar) {
        this.sourceDocBindVar = sourceDocBindVar;
    }

    public RichInputListOfValues getSourceDocBindVar() {
        return sourceDocBindVar;
    }

    public void setReqAreaIdBindVar(RichInputListOfValues reqAreaIdBindVar) {
        this.reqAreaIdBindVar = reqAreaIdBindVar;
    }

    public RichInputListOfValues getReqAreaIdBindVar() {
        return reqAreaIdBindVar;
    }

    public void setWarehouseIdBindVar(RichInputListOfValues warehouseIdBindVar) {
        this.warehouseIdBindVar = warehouseIdBindVar;
    }

    public RichInputListOfValues getWarehouseIdBindVar() {
        return warehouseIdBindVar;
    }

    public void setActOutputQtyBindVar(RichInputText actOutputQtyBindVar) {
        this.actOutputQtyBindVar = actOutputQtyBindVar;
    }

    public RichInputText getActOutputQtyBindVar() {
        return actOutputQtyBindVar;
    }

    public void setOutputItemFormBind(RichPanelFormLayout outputItemFormBind) {
        this.outputItemFormBind = outputItemFormBind;
    }

    public RichPanelFormLayout getOutputItemFormBind() {
        return outputItemFormBind;
    }

    public void RouteCardBasisVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("  basis " + valueChangeEvent.getNewValue());
            ob = ADFUtils.findOperation("ChkRcBasisVcl");
            ob.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            ob.execute();
        }
    }

    public void actualOutputQtyVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            this.setOutputQtyChange("Y");
        }
    }

    public void setAddLotPopUp(RichPopup addLotPopUp) {
        this.addLotPopUp = addLotPopUp;
    }

    public RichPopup getAddLotPopUp() {
        return addLotPopUp;
    }

    public void setLotIdBindVar(RichInputText lotIdBindVar) {
        this.lotIdBindVar = lotIdBindVar;
    }

    public RichInputText getLotIdBindVar() {
        return lotIdBindVar;
    }

    public void setLotQtyBindVar(RichInputText lotQtyBindVar) {
        this.lotQtyBindVar = lotQtyBindVar;
    }

    public RichInputText getLotQtyBindVar() {
        return lotQtyBindVar;
    }

    public void setLotIdInSerial(RichInputText lotIdInSerial) {
        this.lotIdInSerial = lotIdInSerial;
    }

    public RichInputText getLotIdInSerial() {
        return lotIdInSerial;
    }

    public void setSerialNoBindVar(RichInputText serialNoBindVar) {
        this.serialNoBindVar = serialNoBindVar;
    }

    public RichInputText getSerialNoBindVar() {
        return serialNoBindVar;
    }

    public void setPanelColLotTableBind(RichPanelCollection panelColLotTableBind) {
        this.panelColLotTableBind = panelColLotTableBind;
    }

    public RichPanelCollection getPanelColLotTableBind() {
        return panelColLotTableBind;
    }

    public void setPanelColSerialTableBind(RichPanelCollection panelColSerialTableBind) {
        this.panelColSerialTableBind = panelColSerialTableBind;
    }

    public RichPanelCollection getPanelColSerialTableBind() {
        return panelColSerialTableBind;
    }

    /*------------------------------Add Lot Popup------------------------------*/
    public void Add_Lot_Action(ActionEvent actionEvent) {
        DCIteratorBinding iter = ADFUtils.findIterator("MnfRcItmStkVOLotIterator");
        Row[] arIR = iter.getAllRowsInRange();
        Integer Ri = (Integer) arIR.length;
        System.out.println("Value of :::::" + Ri);
        if (Ri == 1) {
            ob = ADFUtils.findOperation("GetlotNo");
            ob.execute();
            String lotNo = (String) ob.getResult();
            System.out.println("Lot No :" + lotNo);
            this.setLotNo(lotNo);
            this.setLotQty(this.actOutputQtyBindVar.getValue().toString());
            showPopup(addLotPopUp, true);
        }
    }
    String relaeseFlag = "N";
    /*------------------------Add lot Dialog Listner-------------------------------*/
    public void addLotDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            if (getSerialVal().equals("N")) {
                ob = ADFUtils.findOperation("AddLotEntry");
                ob.getParamsMap().put("lotId", this.lotIdBindVar.getValue().toString());
                ob.getParamsMap().put("lotQty", this.lotQtyBindVar.getValue().toString());
                ob.execute();
                // ADFUtils.findOperation("ExecuteLot").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelColLotTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotIdInSerial);
                this.fg_sfgRadioBindVar.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(fg_sfgRadioBindVar);
                this.add_editLotBtnBindVar.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(add_editLotBtnBindVar);
                relaeseFlag = "Y";
                this.setOnReleaseMd("R");
                this.saveBTNBind.setDisabled(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);

            } else {
                System.out.println("------->>?" + getSerialVal());
                ob = ADFUtils.findOperation("AddSerialLotEntry");
                ob.getParamsMap().put("lotId", this.lotIdBindVar.getValue().toString());
                ob.getParamsMap().put("lotQty", this.lotQtyBindVar.getValue().toString());
                ob.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelColLotTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotIdInSerial);
                this.fg_sfgRadioBindVar.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(fg_sfgRadioBindVar);
                this.add_editLotBtnBindVar.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(add_editLotBtnBindVar);
            }
        }
    }

    /*------------------------------------Add Serial Action Listner--------------------*/
    public void Add_Serial_Action(ActionEvent actionEvent) {

        DCIteratorBinding iter = ADFUtils.findIterator("MnfRcItmStkVOLotIterator");
        Row[] arIR = iter.getAllRowsInRange();
        Row currentRow = iter.getCurrentRow();
        if (currentRow != null) {
            if (this.serialNoBindVar.getValue() != null) {
                ob = ADFUtils.findOperation("ChkItmSerialized");
                ob.getParamsMap().put("ItmId", (String) currentRow.getAttribute("ItmId"));
                ob.execute();
                Object ser = ob.getResult();
                if (ser.equals("Y") || ser == "Y") {
                    int val = 0;
                    ViewObject vo = ADFUtils.findIterator("MnfRcItmStkSrVO1Iterator").getViewObject();
                    RowSetIterator rsetIter = vo.createRowSetIterator(null);
                   Row[] ro = vo.getAllRowsInRange();
                   val = ro.length;
                    /* Row r = null;
                    while (rsetIter.hasNext()) {
                        r = rsetIter.next();
                        if (r.getAttribute("ItmType") == currentRow.getAttribute("ItmType") &&
                            r.getAttribute("ItmId").equals((String) currentRow.getAttribute("ItmId"))) {
                            val++;
                            break;
                        }
                    }
                    rsetIter.closeRowSetIterator(); */
                    Number Ri = new Number(val);
                    Number Qtyi = (Number) (Number) currentRow.getAttribute("TotQty");
                    System.out.println("Value of Absolute :" + Qtyi);
                    if (Ri.compareTo(Qtyi) == 0) {
                        String msg2 = "<html><body> <p>No. of Serial No. Exceeds the Quantity.</p></body></html>";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                        this.setOnReleaseMd("R");
                        this.saveBTNBind.setDisabled(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);
                    } else {
                        System.out.println("LOT ID IS : " + this.lotIdInSerial.getValue());
                        if (this.lotIdInSerial.getValue() != null && this.serialNoBindVar.getValue() != null &&
                            !this.lotIdInSerial.getValue().toString().equals("-")) {
                            ob = ADFUtils.findOperation("AddSerialEntry");
                            ob.getParamsMap().put("lotId", this.lotIdInSerial.getValue().toString());
                            ob.getParamsMap().put("serial", this.serialNoBindVar.getValue().toString());
                            ob.execute();
                            // ADFUtils.findOperation("ExecuteSerial").execute();
                            this.setOnReleaseMd("R");
                            this.saveBTNBind.setDisabled(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelColSrBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(serialTableBind);
                            this.serialNoBindVar.setValue("");
                            serialNoBindVar.setSubmittedValue(null);
                            serialNoBindVar.resetValue();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(serialNoBindVar);
                        } else {
                            String msg2 = "<html><body> <p>Serial No. or Lot Id can't Blank !</p></body></html>";
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message2);
                        }
                    }
                } else {
                    String msg2 = "<html><body> <p>Item is not Serialized !</p></body></html>";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            } else {
                String msg2 = "<html><body> <p>Serial No. is Required !</p></body></html>";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        } else {
            String msg2 = "<html><body> <p>Lot Entry is Required !</p></body></html>";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }

    }


    public void setPanelTabBinding(RichPanelTabbed panelTabBinding) {
        this.panelTabBinding = panelTabBinding;
    }

    public RichPanelTabbed getPanelTabBinding() {
        return panelTabBinding;
    }

    public void setShowDetailsOutputitmStk(RichShowDetailItem showDetailsOutputitmStk) {
        this.showDetailsOutputitmStk = showDetailsOutputitmStk;
    }

    public RichShowDetailItem getShowDetailsOutputitmStk() {
        return showDetailsOutputitmStk;
    }

    public void Validate_Output_Qty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }

            Number vv = (Number) pendingQty.getValue();
            System.out.println("PENDINF" + vv);
            if (val.compareTo(vv) > 0) {
                System.out.println("PENDINF 2" + vv);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not greater than Pending Quantity.", null));
            }

            if (getAttrsVal("MnfRcVOIterator", "OutptItmId") != null) {
                String itmSerial = (String) getAttrsVal("MnfRcVOIterator", "TransSerialFlag");
                if (itmSerial.equals("Y") || itmSerial == "Y") {
                    if (!isPrecisionValid(20, 0, val)) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Item is Serialized, So cant not have qty in decimal !",
                                                                      null));
                    }
                }
            }
        }
    }

    public void output_Qty_VCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            System.out.println("Val is suppossed to be chnge is : " + vce.getNewValue());
            //this.actOutputQtyBindVar.setValue(vce.getNewValue());
            OperationBinding ob = ADFUtils.findOperation("yieldPercActOutPutQty");
            ob.getParamsMap().put("qty", (Number) vce.getNewValue());
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputQtyBindVar);
        }
    }

    public void setReleaseBTNBind(RichButton releaseBTNBind) {
        this.releaseBTNBind = releaseBTNBind;
    }

    public RichButton getReleaseBTNBind() {
        return releaseBTNBind;
    }

    public void setSaveBTNBind(RichLink saveBTNBind) {
        this.saveBTNBind = saveBTNBind;
    }

    public RichLink getSaveBTNBind() {
        return saveBTNBind;
    }

    /*--------Auto Issue Action Listner In Case of ShopFloor------------*/
    public void autoIssueItemAction_SF(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailableSFBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFUtils.findOperation("IssueAutoItem_SF").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
        }
    }

    /*------select item Action listner In Case of ShopFloor------------*/
    public void SerialNoForItmAL_SF(ActionEvent actionEvent) {

        Number stock = (Number) stockAvailableSFBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {

            ADFUtils.findOperation("FilterSrNoAsPerItem_SF").execute();
            showPopup(selectSrNoPopUpBind_SF, true);
        }
    }

    public void OkSelectItmLotBinSrAL_SF(ActionEvent actionEvent) {
        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtySr_SF");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();

            if (itmIssueSrBind_SF.getValue() != null) {
                pickQty = (Number) itmIssueSrBind_SF.getValue();
            }

            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoPickItmSr_SF").execute();

                //  ADFUtils.findOperation("Execute1").execute();
                //  ADFUtils.findOperation("Execute2").execute();
                //   ADFUtils.findOperation("Execute3").execute();
                //    ADFUtils.findOperation("Execute4").execute();

                if (parentKey != null) {
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectSrNoPopUpBind_SF.hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
            }
        }
    }

    public void CancelSrNoPopupAL_SF(ActionEvent actionEvent) {
        selectSrNoPopUpBind_SF.hide();
    }

    public void setItmIssueSrBind_SF(RichOutputText itmIssueSrBind_SF) {
        this.itmIssueSrBind_SF = itmIssueSrBind_SF;
    }

    public RichOutputText getItmIssueSrBind_SF() {
        return itmIssueSrBind_SF;
    }

    /*---------select lot action listner In Case of ShopFloor-------------*/
    public void SelectLotFrItmAction_SF(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailableSFBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFUtils.findOperation("FilterLotWhWise_SF").execute();
            showPopup(selectLotPopUpBind_SF, true);
        }
    }

    /*-----------select lot bin action listner In Case of ShopFloor--------------*/
    public void SelectLotBinFrItmAction_SF(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailableSFBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();

        if (stock.compareTo(avail) < 0) {
            JSFUtils.addFacesErrorMessage("Cannot issue the Item because of less Stock or No Stock.");
        } else {
            ADFUtils.findOperation("FilterBinWhWise_SF").execute();
            showPopup(selectLotBinPopUpBind_SF, true);
        }
    }

    public void setStockAvailableSFBind(RichOutputText stockAvailableSFBind) {
        this.stockAvailableSFBind = stockAvailableSFBind;
    }

    public RichOutputText getStockAvailableSFBind() {
        return stockAvailableSFBind;
    }

    public void setSelectSrNoPopUpBind_SF(RichPopup selectSrNoPopUpBind_SF) {
        this.selectSrNoPopUpBind_SF = selectSrNoPopUpBind_SF;
    }

    public RichPopup getSelectSrNoPopUpBind_SF() {
        return selectSrNoPopUpBind_SF;
    }


    public void setSelectLotPopUpBind_SF(RichPopup selectLotPopUpBind_SF) {
        this.selectLotPopUpBind_SF = selectLotPopUpBind_SF;
    }

    public RichPopup getSelectLotPopUpBind_SF() {
        return selectLotPopUpBind_SF;
    }

    public void OkItemLotButtonAL_SF(ActionEvent actionEvent) {
        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        System.out.println("Key before lot-" + parentKey);
        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtyLot_SF");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();
            System.out.println("pickqty bind-" + itmQtyIssueLblBind_SF.getValue());
            if (itmQtyIssueLblBind_SF.getValue() != null) {
                pickQty = (Number) itmQtyIssueLblBind_SF.getValue();
            }
            System.out.println("IssueQty-" + issQty + "PickedQty-" + pickQty);
            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoRcItmLot_SF").execute();

                //    ADFUtils.findOperation("Execute1").execute();
                //  ADFUtils.findOperation("Execute2").execute();
                // ADFUtils.findOperation("Execute3").execute();
                //ADFUtils.findOperation("Execute4").execute();

                if (parentKey != null) {
                    System.out.println("Key before lot-" + parentKey);
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectLotPopUpBind_SF.hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
            }
        }
    }

    public void CancelItemLotButtonAL_SF(ActionEvent actionEvent) {
        selectLotPopUpBind_SF.hide();
    }

    public void setItmQtyIssueLblBind_SF(RichOutputText itmQtyIssueLblBind_SF) {
        this.itmQtyIssueLblBind_SF = itmQtyIssueLblBind_SF;
    }

    public RichOutputText getItmQtyIssueLblBind_SF() {
        return itmQtyIssueLblBind_SF;
    }

    public void setTotStkLotBind_SF(RichOutputText totStkLotBind_SF) {
        this.totStkLotBind_SF = totStkLotBind_SF;
    }

    public RichOutputText getTotStkLotBind_SF() {
        return totStkLotBind_SF;
    }

    public void IssueQtyLotValidator_SF(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBind_SF.getValue() != null) {
                lotQty = (Number) totStkLotBind_SF.getValue();
                System.out.println("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    public void setSelectLotBinPopUpBind_SF(RichPopup selectLotBinPopUpBind_SF) {
        this.selectLotBinPopUpBind_SF = selectLotBinPopUpBind_SF;
    }

    public RichPopup getSelectLotBinPopUpBind_SF() {
        return selectLotBinPopUpBind_SF;
    }

    public void OkItemLotBinButtonAL_SF(ActionEvent actionEvent) {
        DCIteratorBinding parentIter = ADFUtils.findIterator("MnfRcOpItmDispVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
       // System.out.println("Key before Bin-" + parentKey);

        Number issQty = new Number(0);
        Number pickQty = new Number(0);
        OperationBinding issueQty = ADFUtils.findOperation("getTotalIssueQtyBin_SF");
        issueQty.execute();
        if (issueQty.getResult() != null) {
            issQty = (Number) issueQty.getResult();
          //  System.out.println("Orderqty bind-" + itmQtyIssueBinLblBind_SF.getValue());
            if (itmQtyIssueBinLblBind_SF.getValue() != null) {
                pickQty = (Number) itmQtyIssueBinLblBind_SF.getValue();
            }
          //  System.out.println("IssueQty-" + issQty + "PickedQty-" + pickQty);
            if (issQty.compareTo(0) == 0) {
                JSFUtils.addFacesErrorMessage("Please select quantity to issue");
            } else if (issQty.compareTo(pickQty) != 0) {
                JSFUtils.addFacesErrorMessage("Issued Quantity is not equal to Ordered Quantity");
            } else {
                ADFUtils.findOperation("InsertIntoRcItmBin_SF").execute();
                // ADFUtils.findOperation("Execute1").execute();
                // ADFUtils.findOperation("Execute2").execute();
                //  ADFUtils.findOperation("Execute3").execute();
                //  ADFUtils.findOperation("Execute4").execute();

                if (parentKey != null) {
                    System.out.println("Key before lot-" + parentKey);
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                selectLotBinPopUpBind_SF.hide();
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialPanel);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialSwitcher);
                //  AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSerialTable);
            }
        }
    }

    public void CancelItemLotBinButtonAL_SF(ActionEvent actionEvent) {
        selectLotBinPopUpBind_SF.hide();
    }

    public void setItmQtyIssueBinLblBind_SF(RichOutputText itmQtyIssueBinLblBind_SF) {
        this.itmQtyIssueBinLblBind_SF = itmQtyIssueBinLblBind_SF;
    }

    public RichOutputText getItmQtyIssueBinLblBind_SF() {
        return itmQtyIssueBinLblBind_SF;
    }

    public void setTotStkLotBinBind_SF(RichOutputText totStkLotBinBind_SF) {
        this.totStkLotBinBind_SF = totStkLotBinBind_SF;
    }

    public RichOutputText getTotStkLotBinBind_SF() {
        return totStkLotBinBind_SF;
    }

    public void IssueQtyLotBinValidator_SF(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBinBind_SF.getValue() != null) {
                lotQty = (Number) totStkLotBinBind_SF.getValue();
                System.out.println("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    /*-------------------Function for Lot Check in Input Items------------------*/
    public String CheckLotEntry_Func() {
        ob = ADFUtils.findOperation("CheckforInputItmLot");
        ob.execute();
        Object oj = ob.getResult();
        System.out.println("Value find of Oj =============" + oj.toString());
        return oj.toString();
    }

    /*---------------------Validate Source Doc Id------------------------------*/
    public void sourceDocIdValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String val = (String) object.toString();
            ob = ADFUtils.findOperation("checkSourceDocIdValidate");
            ob.getParamsMap().put("SrcDispId", val);
            ob.execute();
            if (ob.getResult().toString().equals("Y")) {
                throw new ValidatorException(new FacesMessage("PDO already used in Job Card."));
            }
        }
    }

    public void setDocDateBindVar(RichInputDate docDateBindVar) {
        this.docDateBindVar = docDateBindVar;
    }

    public RichInputDate getDocDateBindVar() {
        return docDateBindVar;
    }

    public void setFg_sfgRadioBindVar(RichSelectOneRadio fg_sfgRadioBindVar) {
        this.fg_sfgRadioBindVar = fg_sfgRadioBindVar;
    }

    public RichSelectOneRadio getFg_sfgRadioBindVar() {
        return fg_sfgRadioBindVar;
    }

    public void setAdd_editLotBtnBindVar(RichButton add_editLotBtnBindVar) {
        this.add_editLotBtnBindVar = add_editLotBtnBindVar;
    }

    public RichButton getAdd_editLotBtnBindVar() {
        return add_editLotBtnBindVar;
    }

    public void setLotTableBindVar(RichTable lotTableBindVar) {
        this.lotTableBindVar = lotTableBindVar;
    }

    public RichTable getLotTableBindVar() {
        return lotTableBindVar;
    }

    String serialVal = "N";

    public void setSerialVal(String serialVal) {
        this.serialVal = serialVal;
    }

    public String getSerialVal() {
        return serialVal;
    }

    public void addEditLotForSerialACE(ActionEvent actionEvent) {
        DCIteratorBinding iter = ADFUtils.findIterator("MnfRcItmStkVOSerialIterator");
        Row[] arIR = iter.getAllRowsInRange();
        Integer Ri = (Integer) arIR.length;
        System.out.println("Value of :::::" + Ri);
        if (Ri == 1) {
            ob = ADFUtils.findOperation("GetlotNo");
            ob.execute();
            String lotNo = (String) ob.getResult();
            System.out.println("Lot No :" + lotNo);
            this.setLotNo(lotNo);
            this.setLotQty(this.actOutputQtyBindVar.getValue().toString());
            this.setSerialVal("Y");
            showPopup(addLotPopUp, true);
        }
    }

    public void setLotBinSerialSwitcher(UIXSwitcher lotBinSerialSwitcher) {
        this.lotBinSerialSwitcher = lotBinSerialSwitcher;
    }

    public UIXSwitcher getLotBinSerialSwitcher() {
        return lotBinSerialSwitcher;
    }

    public void setLotBinSerialPanel(RichPanelBox lotBinSerialPanel) {
        this.lotBinSerialPanel = lotBinSerialPanel;
    }

    public RichPanelBox getLotBinSerialPanel() {
        return lotBinSerialPanel;
    }

    public void setLotBinTable(RichTable lotBinTable) {
        this.lotBinTable = lotBinTable;
    }

    public RichTable getLotBinTable() {
        return lotBinTable;
    }

    public void setLotBinSerialTable(RichTable lotBinSerialTable) {
        this.lotBinSerialTable = lotBinSerialTable;
    }

    public RichTable getLotBinSerialTable() {
        return lotBinSerialTable;
    }

    public void chkItemSerializedExistsVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && getLotItmId().getValue() != null && this.serialNoBindVar.getValue() != null) {
            ob = ADFUtils.findOperation("getSerializedItemCount");
            ob.getParamsMap().put("serial", object.toString());
            ob.getParamsMap().put("itmId", getLotItmId().getValue().toString());
            ob.execute();
            Integer value = (Integer) ob.getResult();
            if (value != 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Serial Number is already Exists for an Output Item",
                                                              null));

            }
        }

    }

    public void addOverheadParameterACE(ActionEvent actionEvent) {
        if (getAttrsVal("DualOverHeadParamVO1Iterator", "ParameterSet") == null) {
            showFacesMsg("Parameter Set is Required!", "Please enter Parameter Set Name", FacesMessage.SEVERITY_ERROR,
                         null);
        } else if (getAttrsVal("DualOverHeadParamVO1Iterator", "ParamNme") == null) {
            showFacesMsg("Parameter is Required!", "Please enter Parameter Name", FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualOverHeadParamVO1Iterator", "OperationId") == null) {

            showFacesMsg("Operation is Required", "Please Select Operation Name", FacesMessage.SEVERITY_ERROR, null);

        } else {
            try {
                ADFUtils.findOperation("attachOverhead").execute();
                // ADFBeanUtils.findOperation("attachOverhead").execute();
            } catch (Exception e) {
                showFacesMsg("Duplicated Value!", "Duplicate Value Selected", FacesMessage.SEVERITY_ERROR, null);
                e.printStackTrace();
            }
        }
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

    public void addMachineDowntimeACE(ActionEvent actionEvent) {
        if (getAttrsVal("DualMachineDowntimeVO1Iterator", "RcParameterSetId") == null) {
            showFacesMsg("Parameter Set is Required!", "Please enter Parameter Set Name", FacesMessage.SEVERITY_ERROR,
                         null);
        } else if (getAttrsVal("DualMachineDowntimeVO1Iterator", "RcParameterId") == null) {
            showFacesMsg("Parameter is Required!", "Please enter Parameter Name", FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualMachineDowntimeVO1Iterator", "RcOperationId") == null) {

            showFacesMsg("Operation is Required", "Please Select Operation Name", FacesMessage.SEVERITY_ERROR, null);

        } else if (getAttrsVal("DualMachineDowntimeVO1Iterator", "StartDt") == null) {

            showFacesMsg("Start Date is Required", "Please Select Start Date", FacesMessage.SEVERITY_ERROR, null);

        } else if (getAttrsVal("DualMachineDowntimeVO1Iterator", "EndDate") == null) {

            showFacesMsg("End Date is Required", "Please Select End Date", FacesMessage.SEVERITY_ERROR, null);

        } else {
            try {
                ADFUtils.findOperation("attachMachineDowntime").execute();
                // ADFBeanUtils.findOperation("attachOverhead").execute();
            } catch (Exception e) {
                showFacesMsg("Duplicated Value!", "Duplicate Value Selected", FacesMessage.SEVERITY_ERROR, null);
                e.printStackTrace();
            }
        }
    }

    public void addOpQcParameterACE(ActionEvent actionEvent) {
        // CreateWithParamsToMnfRcOpQcParam
        ADFUtils.findOperation("CreateWithParamsToMnfRcOpQcParam").execute();
        ADFUtils.findOperation("setSrcIdToQcParam").execute();
        setAddOp("Y");
    }
    String addOp = "N";

    public void setAddOp(String addOp) {
        this.addOp = addOp;
    }

    public String getAddOp() {
        return addOp;
    }

    public void deleteOpQcParamACE(ActionEvent actionEvent) {
        //DeleteOpQcParam
        //ADFUtils.findOperation("DeleteOpQcParam").execute();
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteKeyForQcParam");
        System.out.println("KEY IS " + key);
        rowDelete(key, "MnfRcOpQcParamVO1Iterator");
        setAddOp("N");
        //    transOpNme.setDisabled(false);
    }

    public void rowDelete(Key key, String iterName) {
        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            //  ViewObject vo = ADFBeanUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }

    }

    public void setQcParamTabBind(RichTable qcParamTabBind) {
        this.qcParamTabBind = qcParamTabBind;
    }

    public RichTable getQcParamTabBind() {
        return qcParamTabBind;
    }

    public void QcOpNmeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
        //  ADFUtils.findOperation("setItemAccordingToOperation").execute();
        //  transOpNme.setDisabled(true);

    }

    public void minTolValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);

            if (isPerSelBindVar.isSelected()) {
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);

    }

    public void setIsPerSelBindVar(RichSelectBooleanCheckbox isPerSelBindVar) {
        this.isPerSelBindVar = isPerSelBindVar;
    }

    public RichSelectBooleanCheckbox getIsPerSelBindVar() {
        return isPerSelBindVar;
    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void paramValueVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer x = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (x == 274) {

                Number a = new Number(0);
                Number e = new Number(0);
                Number b = new Number(0);
                Number d = new Number(0);

                a = (Number) paramValueBindVar.getValue();
                e = (Number) paramValueBindVar.getValue();
                if (minTolBindVar.getValue() != null) {
                    b = (Number) minTolBindVar.getValue();
                }
                if (maxTolBindVar.getValue() != null) {
                    d = (Number) maxTolBindVar.getValue();
                }
                if (a != null && b != null && d != null) {
                    if (isPerSelBindVar.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));
                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);

                    setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", g);
                    setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
        } else {
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "TlrncLower", 0);
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "TlrncUpper", 0);
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", 0);
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
        }

    }

    public void wsParamValueVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
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

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
    }

    public void maxTolValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);
            Number minV = new Number(0);
            if (minTolBindVar.getValue() != null) {
                minV = (Number) minTolBindVar.getValue();
            }

            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);

            if (isPerSelBindVar.isSelected()) {
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
    }

    public void uprTolrnVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 274) {
                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) maxTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isPerSelBindVar.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        //System.out.println(b + "  dcf");
                    }
                    Number f = a.add(b);
                    setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            }
        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.add(0);
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
    }

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    public void setMinValBindVar(RichInputText minValBindVar) {
        this.minValBindVar = minValBindVar;
    }

    public RichInputText getMinValBindVar() {
        return minValBindVar;
    }

    public void setMaxValBindVar(RichInputText maxValBindVar) {
        this.maxValBindVar = maxValBindVar;
    }

    public RichInputText getMaxValBindVar() {
        return maxValBindVar;
    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
    }

    public void lowrToleraVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {

            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) minTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isPerSelBindVar.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        // System.out.println(b + "  dfd");
                    }
                    Number f = a.subtract(b);

                    setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", f);
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);

            }

        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.subtract(0);
            setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
    }

    public void setValueTypeBindVar(RichSelectOneChoice valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichSelectOneChoice getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void chkPercentAmountVCE(ValueChangeEvent valueChangeEvent) {

        Integer c = (Integer) valueTypeBindVar.getValue();
        System.out.println("Value of Paramtype is : " + c);
        if (c != null && c == 274) {

            Number a = (Number) paramValueBindVar.getValue();
            Number e = (Number) paramValueBindVar.getValue();

            Number b = (Number) minTolBindVar.getValue();
            Number d = (Number) maxTolBindVar.getValue();

            if (a != null && b != null && d != null) {
                if (isPerSelBindVar.isSelected()) {
                    b = a.multiply(b.divide(new Number(100)));
                    d = a.multiply(d.divide(new Number(100)));

                }
                Number f = a.subtract(b);
                Number g = e.add(d);

                setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", g);
                setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", f);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);

    }

    public void qcParameterDupChkVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("Inside of  validator 0000 !!" + getAttrsVal("MnfRcOpQcParamVO1Iterator", "ItmId"));
            String transItmNme = (String) getAttrsVal("MnfRcOpQcParamVO1Iterator", "ItmId");
            System.out.println("Item name : " + transItmNme + "       object is :  " + object);
            /* if (duplicateValue("MnfRcOpQcParamVO1Iterator", "TransItemName", transItmNme) &&
                duplicateValue("MnfRcOpQcParamVO1Iterator", "TransQcParamter", object)) {
                System.out.println("Inside of Dup Check !!");
                // if (duplicateValue("MnfRcOpQcParamVO1Iterator", "TransQcParamter", object)) {
                System.out.println("Inside of  validator !!");
                JSFUtils.addFacesErrorMessage("Duplicate Parameter is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Parameter Selected", null));
            } */
            //}

            //chkNmDuplicate

            ob = ADFUtils.findOperation("chkNmDuplicate");

            ob.getParamsMap().put("val", object.toString());
            ob.getParamsMap().put("itm_id", transItmNme);
            ob.execute();
            if (ob.getResult().toString().equals("true") || ob.getResult().toString() == "true") {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Parameter Selected", null));

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueTypeBindVar);
    }

    public void qcParamDescVCE(ValueChangeEvent valueChangeEvent) {
        setAttrsVal("MnfRcOpQcParamVO1Iterator", "TlrncLower", 0);
        setAttrsVal("MnfRcOpQcParamVO1Iterator", "TlrncUpper", 0);
        setAttrsVal("MnfRcOpQcParamVO1Iterator", "UpperLimit", 0);
        setAttrsVal("MnfRcOpQcParamVO1Iterator", "LowerLimit", 0);
        setAttrsVal("MnfRcOpQcParamVO1Iterator", "StdVal", 0);


    }

    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        // DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
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

    public void setOverheadParamNmeBind(RichInputListOfValues overheadParamNmeBind) {
        this.overheadParamNmeBind = overheadParamNmeBind;
    }

    public RichInputListOfValues getOverheadParamNmeBind() {
        return overheadParamNmeBind;
    }

    public void setTransOpNme(RichSelectOneChoice transOpNme) {
        this.transOpNme = transOpNme;
    }

    public RichSelectOneChoice getTransOpNme() {
        return transOpNme;
    }

    public void opNmeVCE(ValueChangeEvent valueChangeEvent) {
        //  ADFUtils.findOperation("setItemAccordingToOperation").execute();
        /* ActionEvent acc = new ActionEvent(addQueueBind);
        acc.queue();
        addQueueACE(acc);  */
    }

    public void addQueueACE(ActionEvent actionEvent) {
        String opId =
            (String) getAttrsVal("MnfRcOpQcParamVO1Iterator", "TransOperationNme"); //qcOpIdBind.getValue().toString();
        System.out.println("Op id is from bean " + opId);
        ob = ADFUtils.findOperation("setItemAccordingToOperation");
        ob.getParamsMap().put("opId", opId);
        ob.execute();
    }

    public void setAddQueueBind(RichLink addQueueBind) {
        this.addQueueBind = addQueueBind;
    }

    public RichLink getAddQueueBind() {
        return addQueueBind;
    }

    public void setQcOpIdBind(RichOutputText qcOpIdBind) {
        this.qcOpIdBind = qcOpIdBind;
    }

    public RichOutputText getQcOpIdBind() {
        return qcOpIdBind;
    }

    public void setPendingQty(RichInputText pendingQty) {
        this.pendingQty = pendingQty;
    }

    public RichInputText getPendingQty() {
        return pendingQty;
    }

    public void overHeadParamSetVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(overheadParamNmeBind);
    }

    public void outputQtyJcTransVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number itmQty = (Number) object;
            if (!(itmQty.compareTo(0) == 1)) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Item Quantity should be greater than zero. ", null));*/
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.742']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            if (getAttrsVal("MnfGenerateJcDualVO1Iterator", "PendindOpQty") != null &&
                getAttrsVal("MnfGenerateJcDualVO1Iterator", "OutputItmIdJcTrans") != null) {
                Number consumdItmQty = (Number) getAttrsVal("MnfGenerateJcDualVO1Iterator", "PendindOpQty");
                Number itmPrdQty = (Number) getAttrsVal("MnfGenerateJcDualVO1Iterator", "OutputItmQtyJcTrans");

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
        }

    }

    public void outPutItmNmeVCE(ValueChangeEvent vce) {


        ob = ADFUtils.findOperation("getPendingItmForJC");
        ob.getParamsMap().put("jc_rc", 105);
        ob.execute();
        System.out.println(ob.getResult());
        setAttrsVal("MnfGenerateJcDualVO1Iterator", "PendindOpQty", ob.getResult());


    }

    public void setOpIdBindVar(RichInputText opIdBindVar) {
        this.opIdBindVar = opIdBindVar;
    }

    public RichInputText getOpIdBindVar() {
        return opIdBindVar;
    }

    public void setOpDocIdBindVar(RichOutputText opDocIdBindVar) {
        this.opDocIdBindVar = opDocIdBindVar;
    }

    public RichOutputText getOpDocIdBindVar() {
        return opDocIdBindVar;
    }

    public String headCostCenterAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    Timestamp date = new Timestamp(System.currentTimeMillis());

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getDate() {
        return date;
    }


    public void setIsSubContracting(RichSelectBooleanCheckbox isSubContracting) {
        this.isSubContracting = isSubContracting;
    }

    public RichSelectBooleanCheckbox getIsSubContracting() {
        return isSubContracting;
    }

    public void setTransEoNmeBind(RichInputListOfValues transEoNmeBind) {
        this.transEoNmeBind = transEoNmeBind;
    }

    public RichInputListOfValues getTransEoNmeBind() {
        return transEoNmeBind;
    }

    public void subcontractingVCE(ValueChangeEvent vce) {
        if (vce.getNewValue().toString().equals("true") || vce.getNewValue().toString() == "true") {
            transEoNmeBind.setRequired(true);
            // curIdSpBind.setRequired(true);
        } else {
            transEoNmeBind.setRequired(false);
            //  curIdSpBind.setRequired(false);
        }

    }

    public void subContractingVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            if (isSubContracting.isSelected()) {
                transEoNmeBind.setRequired(true);
                //   curIdSpBind.setRequired(true);
            } else {
                transEoNmeBind.setRequired(false);
                //  curIdSpBind.setRequired(false);
            }

        }

    }

    public void setCurIdSpBind(RichInputListOfValues curIdSpBind) {
        this.curIdSpBind = curIdSpBind;
    }

    public RichInputListOfValues getCurIdSpBind() {
        return curIdSpBind;
    }

    public void eoNmeVCE(ValueChangeEvent valueChangeEvent) {
        curIdSpBind.setRequired(true);
    }

    public void shortCloseVCE(ValueChangeEvent valueChangeEvent) {
        if (isShortCloseBind.isSelected()) {
            if (getShortCloseChk().equals("Y")) {
                saveBTNBind.setDisabled(false);
            } else {
                JSFUtils.addFacesErrorMessage("You can not short close this Route Card ! ");
            }

        }
    }

    public void setIsShortCloseBind(RichSelectBooleanCheckbox isShortCloseBind) {
        this.isShortCloseBind = isShortCloseBind;
    }

    public RichSelectBooleanCheckbox getIsShortCloseBind() {
        return isShortCloseBind;
    }

    String shortCloseChk = "N";

    public void setShortCloseChk(String shortCloseChk) {
        this.shortCloseChk = shortCloseChk;
    }

    public String getShortCloseChk() {
        ob = ADFBeanUtils.findOperation("shortcloseChk");
        ob.execute();
        return ob.getResult().toString();
        //  return shortCloseChk;
    }

    public void addOpWiseItemToItmStkACE(ActionEvent actionEvent) {

        if (getAttrsVal("DualItmStkVO1Iterator", "OpName") == null) {
            showFacesMsg("Operation Name is Required!", "Please enter Operation Name", FacesMessage.SEVERITY_ERROR,
                         null);
        } else if (getAttrsVal("DualItmStkVO1Iterator", "ItmName") == null) {
            showFacesMsg("Item Name is Required!", "Please enter Item Name", FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualItmStkVO1Iterator", "MvmntType") == null && getAttrsVal("DualItmStkVO1Iterator", "ItemQcChk").toString().equals("N")) {
            //            if(getQcFlagChk().equals("N"))
            showFacesMsg("Movement Type is Required", "Please Select Movement Type", FacesMessage.SEVERITY_ERROR, null);
        } else {
            try {
                String lotItmId = (String) getAttrsVal("DualItmStkVO1Iterator", "ItmId");
                if (lotItmId != null) {
                    ob = ADFUtils.findOperation("chkOutputItmStk");
                    ob.getParamsMap().put("itmId", lotItmId);
                    ob.execute();
                    Integer outputItmMiss = Integer.parseInt(ob.getResult().toString());
                    //System.out.println("Val of it is ::" + outputItmMiss);
                    if (outputItmMiss == 0) {
                        ob = ADFUtils.findOperation("addToItmStk");
                        ob.execute();
                    } else {
                        showFacesMsg("Duplicate Item !", "Lot for Item is already added.", FacesMessage.SEVERITY_ERROR,
                                     null);
                    }

                    this.saveBTNBind.setDisabled(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveBTNBind);
                } else {
                    showFacesMsg("Item is not Selected!", "Item is not Selected", FacesMessage.SEVERITY_ERROR, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setItmToItmStkBind(RichInputComboboxListOfValues itmToItmStkBind) {
        this.itmToItmStkBind = itmToItmStkBind;
    }

    public RichInputComboboxListOfValues getItmToItmStkBind() {
        return itmToItmStkBind;
    }

    public void outputItmStkDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            ob = ADFUtils.findOperation("defaultDualItmStk");
            ob.execute();
        }
    }

    public void outPutItmStkItemVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null)
        {
            /* ob = ADFUtils.findOperation("qcFlagChkforItems");
            ob.getParamsMap().put("ItemId", object.toString());
            ob.execute();
            String res = ob.getResult().toString();
            System.out.println("---------obj val is :" + res);
            setQcFlagChkForItems(res);
             */
        }
        
    }

    public void setItmNmeStkBind(RichSelectOneChoice itmNmeStkBind) {
        this.itmNmeStkBind = itmNmeStkBind;
    }

    public RichSelectOneChoice getItmNmeStkBind() {
        return itmNmeStkBind;
    }

    public void setLotItmId(RichInputText lotItmId) {
        this.lotItmId = lotItmId;
    }

    public RichInputText getLotItmId() {
        return lotItmId;
    }

    public void opItemStkVCE(ValueChangeEvent vce) {
        if(vce.getNewValue()!= null)
        {
            /* String vceVal = vce.getNewValue().toString();
          String vceVal = getAttrsVal("DualItmStkVO1Iterator", "ItmId").toString();
            System.out.println("---------obj val is :" + vceVal);
            ob = ADFUtils.findOperation("qcFlagChkforItems");
            ob.getParamsMap().put("ItemId", vceVal);
            ob.execute();
            String res = ob.getResult().toString();
            System.out.println("---------obj val is :" + res);
            setQcFlagChkForItems(res);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmMvmntType);
            */
        }
    }

    public void setItmMvmntType(RichSelectOneChoice itmMvmntType) {
        this.itmMvmntType = itmMvmntType;
    }

    public RichSelectOneChoice getItmMvmntType() {
        return itmMvmntType;
    }

    public void setDefaultPrjBind(RichSelectOneChoice defaultPrjBind) {
        this.defaultPrjBind = defaultPrjBind;
    }

    public RichSelectOneChoice getDefaultPrjBind() {
        return defaultPrjBind;
    }

    public void setSerialTableBind(RichTable serialTableBind) {
        this.serialTableBind = serialTableBind;
    }

    public RichTable getSerialTableBind() {
        return serialTableBind;
    }

    public void setPanelColSrBind(RichPanelCollection panelColSrBind) {
        this.panelColSrBind = panelColSrBind;
    }

    public RichPanelCollection getPanelColSrBind() {
        return panelColSrBind;
    }
}


