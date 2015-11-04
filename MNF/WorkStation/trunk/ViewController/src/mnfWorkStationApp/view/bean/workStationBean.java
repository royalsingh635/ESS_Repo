package mnfWorkStationApp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

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

import mnfWorkStationApp.view.Utils.ADFUtils;

import mnfWorkStationApp.view.Utils.JSFUtils;
import mnfWorkStationApp.view.Utils.wsAttachDocument;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.model.UploadedFile;


public class workStationBean {

    OperationBinding ob = null;
    String ActvModeValChng = "Y";
    String wsCapTypVal = "F";
    String wsCreatnMode = "Manual";
    String wsMode = resolvEl("#{pageFlowScope.WS_MODE}").toString();
    String wsManAttach = resolvEl("#{pageFlowScope.ATTACH_MANPOWER}").toString();

    private List<UploadedFile> uploadedFile;
    private RichTable wsShiftTableBind;
    private RichTable wsCapTableBind;
    private RichTable wsManPowerTableBind;
    private RichTable wsOrgTableBind;
    private RichTable amcTableBindVar;
    private RichSelectOneChoice setTypeIdBindVar;
    private RichInputText paramValueBindVar;
    private RichSelectOneChoice valueTypeBindVar;
    private RichInputText minTolBindVar;
    private RichInputText minValBindVar;
    private RichInputText maxValBindVar;
    private RichInputText maxTolBindVar;
    private RichLink shadowLinkBindVar;
    private RichSelectBooleanCheckbox isPerSelBindVar;
    private RichTable wsParamTable;
    String editWsModeChng = "N";
    private RichSelectBooleanCheckbox wsActvFlag;
    private RichSelectOneChoice wsCapacityTyp;
    private RichInputDate wsInactiveDate;
    private RichInputFile inputFileFieldBind;
    private RichInputText dispFileNameBind;
    private UploadedFile file;
    private InputStream _inputStream;
    String _DirPath = new String();
    StringBuffer _FileName = new StringBuffer();
    wsAttachDocument wsAttachDocument = new wsAttachDocument();
    ArrayList<wsAttachDocument> _FileList = new ArrayList<wsAttachDocument>();
    String displayFileName = "";
    String nm = "";
    private RichInputText wsIdBind;
    String workStationId = "";
    private RichSelectOneChoice wsCreationTyp;
    private RichPanelTabbed wsPanelTabBind;
    private RichShowDetailItem setDisclosedCapacityTab;
    private RichInputText wsInactiveReasonBind;
    String wsOrgActvFlag = "T";
    private RichSelectBooleanCheckbox wsOrgActFlag;
    private RichInputText wsInactiveOrgBind;
    private RichInputDate wsInactiveDateForOrgBind;
    private RichInputText transBaseConBind;
    private RichInputText amcAmtSpBind;
    private RichInputText amcAmtBsBind;
    oracle.jbo.domain.Number totAmt = new Number(0);
    private RichInputText wstationDescBind;
    private RichInputListOfValues workCenterIdBind;
    private RichInputText wsShiftBind;
    private StringBuffer wsAddParamSet = null;
    private RichShowDetailItem disclosedManpowerTab;
    private RichInputText wsParamTypeNmeBind;
    String docVal = "N";
    String amcVal = "N";
    private RichPanelFormLayout hederFormBind;
    private RichInputText amcTotalAmountBindVar;
    private RichInputListOfValues wsRefrencedwsIdBind;
    private RichInputListOfValues wsResNmeBind;
    private RichSelectBooleanCheckbox defaultWsBind;

    public workStationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    /**
     * To Save Data in  Table
     *
     * */
    public void onSaveAction(ActionEvent actionEvent) {
        Object refWSId = getAttrsVal("MnfWs1Iterator", "RefWsId");
        Object wcID = getAttrsVal("MnfWs1Iterator", "WcId");

        if (getWstationDescBind().getValue() == null) {

            FacesMessage message = new FacesMessage("Please Provide Description for Work Station !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.getWstationDescBind().getClientId(), message);

        } //else if(getWstationDescBind().getValue() != null) {
        //Validate for unique WS name
        // ob = ADFBeanUtils.findOperation("validateWsNm");
        // ob.execute();
        // if ("Y".equals(ob.getResult())){

        /* ADFUtils.showFacesMsg("Unique Route Name is Required. ",
                                  "Unique Route Name is Required. " + getAttrsVal("MnfRtVOIterator", "RtDesc") +
                                  "is used by another Route. ", FacesMessage.SEVERITY_ERROR, null);

                FacesMessage message = new FacesMessage("Description for Work Station Already exists, Please provide unique Description !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(this.getWstationDescBind().getClientId(), message); */

        //}
        // }
        else if (wcID == null) {
            JSFUtils.addFacesErrorMessage("Select an apropriate Work Center  for Work Station !");
            //            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //            FacesContext fc = FacesContext.getCurrentInstance();
            //            fc.addMessage(this.getWorkCenterIdBind().getClientId(), message);

        } else if ((getWsCreatnMode().equals("CO") && refWSId == null) ||
                   (("CO").equals(getWsCreatnMode())) && refWSId == null) {
            JSFUtils.addFacesErrorMessage("Type of Creation is Copy Previous, Do Select Refrenced Work Station !");
        }else if(getAttrsVal("MnfWsRes2Iterator", "ShiftId") == null){
            JSFUtils.addFacesErrorMessage("Shift Under Manpower Tab is Blank, Do Provide a shift to Attached Employee !");
            
        }else {
            ADFBeanUtils.findOperation("replicateWsToOrganization").execute();
            ADFBeanUtils.findOperation("attachOrganization").execute();
            ADFBeanUtils.findOperation("Commit").execute();

            /*  ADFUtils.findOperation("replicateWsToOrganization").execute();
            ADFUtils.findOperation("attachOrganization").execute();
            ADFUtils.findOperation("Commit").execute();
            */
            JSFUtils.addFacesInformationMessage("Record has been Saved Successfully !");
            setWsMode("V");
            setEditWsModeChng("V");
            setDocVal("N");

        }
    }

    /**
     *
     * Method for Close Button
     *
     * **/
    public void closeActionEvnt(ActionEvent actionEvent) {
        //ADFUtils.findOperation("Rollback").execute();
        ADFBeanUtils.findOperation("Rollback").execute();
        setWsMode("V");
    }


    /**
     *
     * Value Change listener to get mode of Active Flag
     *
     * **/
    public void actvModeVCE(ValueChangeEvent valueChangeEvent) {
        if (wsActvFlag.isSelected()) {
            setActvModeValChng("Y");
            this.wsInactiveReasonBind.setRequired(false);
            this.wsInactiveDate.setValue(null);
            this.wsInactiveReasonBind.setValue(null);
            this.wsInactiveDate.setVisible(false);
            this.getWsInactiveReasonBind().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveDate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveReasonBind);

        } else {
            setActvModeValChng("N");
            this.wsInactiveDate.setVisible(true);
            this.getWsInactiveReasonBind().setVisible(true);
            Date dte = new Date(Date.getCurrentDate());
            this.wsInactiveDate.setValue(new oracle.jbo.domain.Timestamp(dte));
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveDate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveReasonBind);
        }
    }


    /**
     *
     * Value Change listener for Capacity type
     *
     * **/
    public void wsCapacityTypVCE(ValueChangeEvent valueChangeEvent) {

        int x = Integer.parseInt(getWsCapacityTyp().getValue().toString());
        if (x == 14) {
            setWsCapTypVal("F");
            setDisclosedFirstTab(setDisclosedCapacityTab);
        } else {
            setWsCapTypVal("V");
            setDisclosedFirstTab(setDisclosedCapacityTab);
        }
    }


    public void wsCapAction(ActionEvent actionEvent) {

        int x = Integer.parseInt(getWsCapacityTyp().getValue().toString());
        if (x == 14) {

            // DCIteratorBinding dcIter = ADFUtils.findIterator("MnfWsCap2Iterator");
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator("MnfWsCap2Iterator");
            long p = dcIter.getEstimatedRowCount();
            if (p == 1) {
                JSFUtils.addFacesErrorMessage("Work Station Capacity is Fixed , So can not add any more capacity parameters !!");
            } else {

                // ADFUtils.findOperation("CreateWithParamsToMnfWsCap").execute();
                ADFBeanUtils.findOperation("CreateWithParamsToMnfWsCap").execute();
            }
        } else {

            // ADFUtils.findOperation("CreateWithParamsToMnfWsCap").execute();
            ADFBeanUtils.findOperation("CreateWithParamsToMnfWsCap").execute();
        }
    }


    public void capConfigDialogBoxL(DialogEvent dialogEvent) {

        //  ADFUtils.findOperation("DeleteParamForWsCap").execute();
        ADFBeanUtils.findOperation("DeleteParamForWsCap").execute();

    }

    public void editPoupCancelledListener(PopupCanceledEvent popupCanceledEvent) {

        // ADFUtils.findOperation("DeleteParamForWsCap").execute();
        ADFBeanUtils.findOperation("DeleteParamForWsCap").execute();
    }

    public void wsResCreateACE(ActionEvent actionEvent) {

        //DCIteratorBinding dcIter = ADFUtils.findIterator("MnfWsShift2Iterator");
        DCIteratorBinding dcIter = ADFBeanUtils.findIterator("MnfWsShift2Iterator");
        long p = dcIter.getEstimatedRowCount();
        //System.out.println("Total value is : " + p);
        if (p > 0) {
            // ADFUtils.findOperation("CreateWithParamsToMnfWsRes").execute();
            ADFBeanUtils.findOperation("CreateWithParamsToMnfWsRes").execute();
        } else {
            JSFUtils.addFacesErrorMessage("No Shift Available to attach Manpower !!");
        }
    }

    public void wsShiftCreateACE(ActionEvent actionEvent) {
        // ADFUtils.findOperation("CreateWithParamsToMnfWsShift").execute();
        ADFBeanUtils.findOperation("filterShift").execute();
        ADFBeanUtils.findOperation("CreateWithParamsToMnfWsShift").execute();
    }


    /**
     *
     * Method  To upload document
     *
     * */
    public void uploadDocVCE(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent != null) {
            file = (UploadedFile) valueChangeEvent.getNewValue();
            try {
                _inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     *
     * Action Listener on upload document
     *
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void uploadDocumentACE(ActionEvent actionEvent) {

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
                    // ADFUtils.findOperation("Commit").execute();
                    ADFBeanUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        setUploadedFile(null);
    }

    /**
     *
     * Method to save an attached Documents.
     *
     ***/
    public void saveAttachDocu() {

        for (wsAttachDocument list : _FileList) {

            if (ob.getErrors().isEmpty()) {
                if (list.getFileInputStream() != null) {
                    try {
                        InputStream in = list.getFileInputStream();
                        FileOutputStream out = new FileOutputStream(list.getAttchedFilePath() + nm + list.getFileExt());
                        byte[] buffer = new byte[8192];
                        int bytesRead = 0;

                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }

                        out.flush();
                        out.close();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }
        _FileList.clear();


    }


    public void createWithParamToMnfWsParamACE(ActionEvent actionEvent) {
        setWsAddParamSet(new StringBuffer("Set"));
        //ADFUtils.findOperation("createWithParamToMnfWsParam").execute();
        ADFBeanUtils.findOperation("createWithParamToMnfWsParam").execute();
    }


    public void editACE(ActionEvent actionEvent) {
        this.setWsMode("E");
        this.setEditWsModeChng("ED");
        // ob = ADFUtils.findOperation("chkOrgPrf");
        ob = ADFBeanUtils.findOperation("chkOrgPrf");
        ob.execute();
        if (ob.getResult() != null && ("Y").equals(ob.getResult())) {
            this.disclosedManpowerTab.setDisabled(false);
        }
        ob = ADFBeanUtils.findOperation("chkDefaultWsVal");
        String wcid = getAttrsVal("MnfWs1Iterator", "WcId").toString();
        ob.getParamsMap().put("WcId", wcid);
        ob.execute();
         Object result = ob.getResult();
         System.out.println("returned from impl " + result);
        if (result != null && result.toString().equalsIgnoreCase("true")) {
            defaultWsBind.setDisabled(true);
        }
        
        
        setWsAddParamSet(new StringBuffer("NSet"));
    }


    /**
     *
     * Action Listener to Add Work Station
     *
     * */
    public void addWsACE(ActionEvent actionEvent) {

        setWsMode("C");
        setCopyMode("N");
        //ADFUtils.findOperation("CreateInsertWorkStn").execute();
        ADFBeanUtils.findOperation("CreateInsertWorkStn").execute();
        // ob = ADFUtils.findOperation("chkOrgPrf");
        ob = ADFBeanUtils.findOperation("chkOrgPrf");
        ob.execute();
        if (ob.getResult() != null && ("Y").equals(ob.getResult())) {
            this.disclosedManpowerTab.setDisabled(false);
        }
    }


    public void wsCreationTypVCE(ValueChangeEvent valueChangeEvent) {
        if (getWsCreationTyp().getValue() != null) {

            int wsTyp = Integer.parseInt(getWsCreationTyp().getValue().toString());

            if (wsTyp == 15) {

                setWsCreatnMode("CO");
                this.wsRefrencedwsIdBind.setRequired(true);
                this.wsRefrencedwsIdBind.setDisabled(false);
                // this.wsRefrencedWsID.setValue(null);
            } else {
                setWsCreatnMode("Manual");
                /* if (getWstationDescBind().getValue() == null) {
                    FacesMessage message = new FacesMessage("Please Provide Description for Work Station !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(this.getWstationDescBind().getClientId(), message);
                    getWsCreationTyp().setValue(16);
                } */
                this.wsRefrencedwsIdBind.setRequired(false);
                this.wsRefrencedwsIdBind.setDisabled(true);
            }
        }
    }


    public void setDisclosedFirstTab(RichShowDetailItem tabBind) {
        RichPanelTabbed richPanelTabbed = getWsPanelTabBind();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            if (sdi.getClientId().equals(tabBind.getClientId())) {
                sdi.setDisclosed(true);
            } else {
                sdi.setDisclosed(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsPanelTabBind);
    }


    /**
     *
     * Method Attach information for AMC
     *
     * **/
    public void wsAttachAMC(ActionEvent actionEvent) {

        setAmcVal("Y");
        setTotAmt(new Number(0));
        //ADFUtils.findOperation("setParamForAMC").execute();
        ADFBeanUtils.findOperation("setParamForAMC").execute();
    }

    public void wsAttchOrganization(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParamsToWsOrg").execute();
        // ADFUtils.findOperation("CreateWithParamsToWsOrg").execute();
    }

    /**
     *
     * Value change listener for active flag on organization
     *
     * **/
    public void wsActiveOrgModeVCE(ValueChangeEvent valueChangeEvent) {
        if (wsOrgActFlag.isSelected()) {
            setWsOrgActvFlag("T");
            this.wsInactiveOrgBind.setRequired(false);
            this.wsInactiveOrgBind.setVisible(false);
            this.wsInactiveOrgBind.setValue(null);
            this.wsInactiveDateForOrgBind.setVisible(false);
            this.wsInactiveDateForOrgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveDateForOrgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveOrgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsOrgTableBind);

        } else {
            setWsOrgActvFlag("F");
            Date dte = new Date(Date.getCurrentDate());
            this.wsInactiveOrgBind.setValue(null);
            this.wsInactiveOrgBind.setRequired(true);
            this.wsInactiveOrgBind.setVisible(true);
            this.wsInactiveDateForOrgBind.setVisible(true);
            this.wsInactiveDateForOrgBind.setValue(new oracle.jbo.domain.Timestamp(dte));
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveDateForOrgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsInactiveOrgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsOrgTableBind);

            // this.wsInactiveDateForOrgBind.setValue(new oracle.jbo.domain.Timestamp(dte));

        }
    }


    /**
     *
     * Value change listener to get an amount.
     *
     * **/
    public void amcAmtSpVCE(ValueChangeEvent valueChangeEvent) {
        if (getAmcAmtSpBind().getValue() != null) {
            oracle.jbo.domain.Number spAmt = new Number(0);
            oracle.jbo.domain.Number covrAmt = new Number(0);
            try {
                spAmt = new Number(getAmcAmtSpBind().getValue().toString());
                if (getTransBaseConBind().getValue() == null) {
                    covrAmt = new Number(0);
                    amcTotalAmountBindVar.setValue(covrAmt);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amcTotalAmountBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
                } else {
                    covrAmt = new Number(getTransBaseConBind().getValue().toString());
                    oracle.jbo.domain.Number amcAmt = spAmt.multiply(covrAmt);
                    amcTotalAmountBindVar.setValue(amcAmt);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amcTotalAmountBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void workStnDescCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {

            FacesMessage message = new FacesMessage("Provide a Work Station Description !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);

        }

        if (object.toString().contains("  ")) {
            FacesMessage message = new FacesMessage("More than one space is not allowed!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     *
     *@param valueChangeEvent
     *
     *
     */
    String copyMode = "M";

    public void setCopyMode(String copyMode) {
        this.copyMode = copyMode;
    }

    public String getCopyMode() {
        return copyMode;
    }

    public void copyPreviousVCE(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            if (this.getWsIdBind().getValue() != null) {

                String oldWsId = valueChangeEvent.getNewValue().toString();
                String newWsId = this.getWsIdBind().getValue().toString().trim();

                // ob = ADFUtils.findOperation("getpreviousWorstationInf");
                ob = ADFBeanUtils.findOperation("getpreviousWorstationInf");
                ob.getParamsMap().put("newWSId", newWsId);
                ob.getParamsMap().put("oldWsID", oldWsId);
                System.out.println("New Work Station : " + newWsId + "----" + oldWsId);
                ob.execute();
                setCopyMode("F");
                AdfFacesContext.getCurrentInstance().addPartialTarget(wsCreationTyp);
                AdfFacesContext.getCurrentInstance().addPartialTarget(hederFormBind);

            }
        }
    }

    /**
     *
     * To attach default organization
     *      *
     */
    public void attachOrganization() {


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

    public void wsOrgChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            if (duplicateValue("MnfWsOrg1Iterator", "OrgId", object)) {
                //System.out.println("Inside of orgchk validator !!");
                JSFUtils.addFacesErrorMessage("Duplicate Organization is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Organization Selected", null));
            }
        }
    }

    public void wsOptimumCapacityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            //else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }
    }

    public void wsMaxCapacityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }


    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }

    public void wsShiftChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        System.out.println("VAL OF SHIFT : " + object);
        
        if (object != null) {

            //ob = ADFUtils.findOperation("chkDuplicate");
            /*  ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "WS_SHIFT");
            ob.getParamsMap().put("val", object.toString());
            ob.execute(); */
           // if (ob.getResult() != null) {
            //  if (duplicateValue("MnfWsShift2Iterator", "ShiftId", ob.getResult())) {
                    if (duplicateValue("MnfWsShift2Iterator", "TransShiftName", object)) {

                   // FacesMessage message = new FacesMessage("Duplicate Shift is Selected !");
                   // message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //throw new ValidatorException(message);
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Shift is Selected !", null));
                    
                }
           // }
        } else {
            FacesMessage message = new FacesMessage("Mandatory Field !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }


    }

    public void wsAvgCapVarValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            //else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void wsMaxCapVarValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

           // } else if (Integer.parseInt(value.toString()) < 0) {
            }else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void wsDeleteShiftACE(ActionEvent actionEvent) {

        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DelShiftKey");
        //  System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsShift2Iterator");
        //   ob = ADFUtils.findOperation("DeleteWsShift");
        //  ob.execute();
    }


    public void wsChkParamValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // System.out.println("Inside of Parameter Validator !! ");
            // ob = ADFUtils.findOperation("chkDuplicate");
            ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "WS_PARAM");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            // if(ob.getResult() != null)
            if (ob.getResult() != null) {
                if (duplicateValue("MnfWsParam2Iterator", "ParamId", ob.getResult())) {
                    // if (duplicateValue("MnfWsParam2Iterator", "TransParamDesc", object)){
                    FacesMessage message = new FacesMessage("Duplicate Parameter is Selected !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            }
        } else {
            FacesMessage message = new FacesMessage("Mandatory Field !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    public void wsParamSetVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null) {
            FacesMessage message = new FacesMessage("Parameter Set is Required !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    public void wsCapHoursVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);
            Boolean isValid = isPrecisionValid(5, 0, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Numerical values upto 5 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
            // else if (Integer.parseInt(value.toString()) < 0) {
            else if (value.compareTo(0) <= 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }
    Integer typ = 0;

    public Integer getAttTypeId() {
        if (getWsParamTypeNmeBind().getValue() != null) {
            if (setTypeIdBindVar.getValue().toString().equals("COSTING"))
                return 37;
            else if (getWsParamTypeNmeBind().getValue().toString().equals("OVERHEAD"))
                return 39;
        }
        return 0;
    }

    public void wsParamValueVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            typ = (Integer) setTypeIdBindVar.getValue();
            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);
            //System.out.println(typ + " =================== set type");
            if (typ != null) {
                if (typ == 39) {
                    if (isPerc == false) {
                        FacesMessage message = new FacesMessage("Enter value in between 0 to 100!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);

                    }

                    if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {
                        FacesMessage message = new FacesMessage("Precision upto 5.2 digit only!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);

                    }
                } else if (typ == 37) {
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

                } else {
                    if (isValid == false) {
                        FacesMessage message = new FacesMessage("Precision upto 20.2 digit only!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);

                    }
                }
            }
        }

    }


    /**
     * Method to check empty value for rows within an iterator
     *
     * **/
    private boolean emptyValue(String iter, String attrsNm) {

        // DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        Integer countVal = 0;
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;
    }

    public void wsDownloadFile(FacesContext facesContext, OutputStream outputStream) throws IOException {
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

    public void wsDeleteParam(ActionEvent actionEvent) {
        //DelKey
        // Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DelKey");
        // System.out.println("Key is : " + key);
        // RowSetIterator rSetIter = ADFUtils.findIterator("MnfWsParam2Iterator").getRowSetIterator();
        //  rSetIter.setCurrentRow(rSetIter.getRow(key));
        //  rSetIter.removeCurrentRow();


        // setWsAddParamSet(new StringBuffer("NSet"));
        // ob = ADFUtils.findOperation("DeleteParam");
        //ob.execute();
        // DeleteOrganizationKey
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DelKey");
        // System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsParam2Iterator");
    }

    public void rowDelete(Key key, String iterName) {
        if (iterName != null && key != null) {
            // ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            ViewObject vo = ADFBeanUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }

    }

    public void wsChkDuplicateItm(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // System.out.println("Inside of Shift Validator !! ");
            //ob = ADFUtils.findOperation("chkDuplicate");
            ob = ADFBeanUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "WS_ITM");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                if (duplicateValue("MnfWsCap2Iterator", "ItmId", ob.getResult())) {
                    // System.out.println("Inside of orgchk validator !!");
                    FacesMessage message = new FacesMessage("Duplicate Item is Selected !");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            }
        } else {
            FacesMessage message = new FacesMessage("Mandatory Field !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }


    }

    public void wsChkDuplicateEmp(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* if (object != null) {
            if (duplicateValue("MnfWsRes2Iterator", "TransEmpName", object.toString())) {
                FacesMessage message = new FacesMessage("Duplicate Employee is Selected !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        } else {
            FacesMessage message = new FacesMessage("Mandatory Field !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } */


        AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);
    }


    public void wsDelAttachFileACE(ActionEvent actionEvent) {
        /* String path = null;

        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null)
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        File f = new File(path);
        try {
            boolean success = f.delete();
        } catch (Exception x) {

            System.err.format("%s: no such" + " file or directory%n", path);
        }

        ADFUtils.findOperation("DeleteAttach").execute();
        ADFUtils.findOperation("Commit").execute(); */

        String path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        System.out.println("File Path : " + path);
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : " + rowKey);

        if (path != null) {
            System.out.println("File Path : " + path.toString());
            //ob = ADFUtils.findOperation("deleteAttachFileRow");
            ob = ADFBeanUtils.findOperation("deleteAttachFileRow");
            ob.getParamsMap().put("path", path);
            ob.execute();
            // ADFUtils.findOperation("DeleteAttach").execute();
            // ADFUtils.findOperation("Commit").execute();
            ADFBeanUtils.findOperation("Commit").execute();
            //ADFUtils.findOperation("ExecuteAttach").execute();
        }
    }

    public void setWsShiftTableBind(RichTable wsShiftTableBind) {
        this.wsShiftTableBind = wsShiftTableBind;
    }

    public RichTable getWsShiftTableBind() {
        return wsShiftTableBind;
    }

    public void wsTransShiftNmeVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsShiftTableBind);
    }


    public void setWsCapTableBind(RichTable wsCapTableBind) {
        this.wsCapTableBind = wsCapTableBind;
    }

    public RichTable getWsCapTableBind() {
        return wsCapTableBind;
    }

    public void wsCapChkVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsCapTableBind);
    }

    public void setWsManPowerTableBind(RichTable wsManPowerTableBind) {
        this.wsManPowerTableBind = wsManPowerTableBind;
    }

    public RichTable getWsManPowerTableBind() {
        return wsManPowerTableBind;
    }

    public void wsEmpVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);
    }

    public void wsShiftVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //String sftId = valueChangeEvent.getNewValue().toString();
            //System.out.println(" ================= " + sftId);

            // System.out.println(getAttrsVal("MnfWsRes2Iterator", "ShiftId")+ " 00000000000000000000000000");
            //setAttrsVal("iter", "attrs", sftId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);

        /*
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse(); */
    }

    public void wsReplicateToallOrganizationACE(ActionEvent actionEvent) {
        // ADFUtils.findOperation("replicateWsToOrganization").execute();
        ADFBeanUtils.findOperation("replicateWsToOrganization").execute();

    }

    public void setWsOrgTableBind(RichTable wsOrgTableBind) {
        this.wsOrgTableBind = wsOrgTableBind;
    }

    public RichTable getWsOrgTableBind() {
        return wsOrgTableBind;
    }

    public void wsOrgVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsOrgTableBind);
    }


    public void wsDeleteCapConfigACE(ActionEvent actionEvent) {
        // DeleteCapacityKey
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteCapacityKey");
        //System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsCap2Iterator");
    }

    public void wsDeleteManPowerACE(ActionEvent actionEvent) {
        //
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteManPowerKey");
        //System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsRes2Iterator");
    }

    public void wsDeleteOrganizationACE(ActionEvent actionEvent) {
        // DeleteOrganizationKey
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("DeleteOrganizationKey");
        // System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsOrg1Iterator");
    }

    public void WsAmcDeleteVCE(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("deletAMC");
        //System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfWsAMC1Iterator");
    }

    public void uomVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsCapTableBind);
    }

    public void optimumCapVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsCapTableBind);
    }

    public void maxCapVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsCapTableBind);
    }

    public void capHorVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsCapTableBind);
    }

    public void inchargVCE(ValueChangeEvent valueChangeEvent) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);
    }

    public void setAmcTableBindVar(RichTable amcTableBindVar) {
        this.amcTableBindVar = amcTableBindVar;
    }

    public RichTable getAmcTableBindVar() {
        return amcTableBindVar;
    }

    public void amcNameVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void vendrNamVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void amcInvoVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void InvDtVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void servCardNoVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void startDtVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void endDtVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void currncyVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void totlAmntVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void baseAMntVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(amcTableBindVar);
    }

    public void shiftInchargeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            // ob = ADFUtils.findOperation("IsIncharge");
            ob = ADFBeanUtils.findOperation("IsIncharge");
            Object tmp = getAttrsVal("MnfWsRes2Iterator", "ShiftId");
            if (tmp != null) {
                String shId = tmp.toString();
                /* String sh =
                getAttrsVal("MnfWsRes2Iterator", "ShiftId").equals(null) ? null :
                getAttrsVal("MnfWsRes2Iterator", "ShiftId").toString(); */

                ob.getParamsMap().put("shift", shId);
                ob.execute();
            }
            Object result = ob.getResult();

            if (result != null && result.toString().equalsIgnoreCase("false")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1357']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
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
            // DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            // DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }


    public void paramValueVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer x = (Integer) setTypeIdBindVar.getValue();
            if (x == 38) {
                Integer c = (Integer) valueTypeBindVar.getValue();
                if (c != null && c == 111) {
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

                        setAttrsVal("MnfWsParam2Iterator", "UpperLimit", g);
                        setAttrsVal("MnfWsParam2Iterator", "LowerLimit", f);
                    }
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsParamTable);
        } else {
            setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
            setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
            setAttrsVal("MnfWsParam2Iterator", "UpperLimit", 0);
            setAttrsVal("MnfWsParam2Iterator", "LowerLimit", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(wsParamTable);
        }
    }


    public void chkPercentAmountVCE(ValueChangeEvent valueChangeEvent) {
        Integer x = (Integer) setTypeIdBindVar.getValue();
        if (x == 38) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 111) {

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

                    setAttrsVal("MnfWsParam2Iterator", "UpperLimit", g);
                    setAttrsVal("MnfWsParam2Iterator", "LowerLimit", f);
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
        }
    }

    public void lowrToleraVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {

            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 111) {

                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) minTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isPerSelBindVar.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        // System.out.println(b + "  dfd");
                    }
                    Number f = a.subtract(b);

                    setAttrsVal("MnfWsParam2Iterator", "LowerLimit", f);
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);

            }

        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.subtract(0);
            setAttrsVal("MnfWsParam2Iterator", "LowerLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }
    }

    public void uprTolrnVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 111) {
                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) maxTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isPerSelBindVar.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        //System.out.println(b + "  dcf");
                    }
                    Number f = a.add(b);
                    setAttrsVal("MnfWsParam2Iterator", "UpperLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            }
        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.add(0);
            setAttrsVal("MnfWsParam2Iterator", "UpperLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        }
    }

    public void paramNameVCE(ValueChangeEvent valueChangeEvent) {
        ActionEvent a = new ActionEvent(shadowLinkBindVar);
        a.queue();
        callInShadow(a);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsParamTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueTypeBindVar);
    }


    public void callInShadow(ActionEvent actionEvent) {
        if (setTypeIdBindVar.getValue() != null) {
            System.out.println(setTypeIdBindVar.getValue() + "    Inside set");
            Integer x = (Integer) setTypeIdBindVar.getValue();
            if (x == 38) {
                Integer valuTyp = (Integer) valueTypeBindVar.getValue();
                if ((valuTyp != null) && valuTyp.equals(112)) {
                    setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncType", "A");
                    setAttrsVal("MnfWsParam2Iterator", "UpperLimit", 1);
                    setAttrsVal("MnfWsParam2Iterator", "LowerLimit", 1);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(isPerSelBindVar);
                }
            } else {
                if ((valueTypeBindVar.getValue() != null) && (paramValueBindVar.getValue() != null)) {

                    System.out.println(paramValueBindVar.getValue() + "    --------------------");

                    Integer valuTyp = (Integer) valueTypeBindVar.getValue();
                    Number curVal = (Number) paramValueBindVar.getValue();
                    if ((valuTyp != null) && valuTyp.equals(111)) {
                        setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
                        setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
                        setAttrsVal("MnfWsParam2Iterator", "TlrncType", "A");
                        setAttrsVal("MnfWsParam2Iterator", "UpperLimit", curVal);
                        setAttrsVal("MnfWsParam2Iterator", "LowerLimit", curVal);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(isPerSelBindVar);

                    }
                } else if ((valueTypeBindVar.getValue()) != null && (paramValueBindVar.getValue() == null)) {
                    Integer valuTyp = (Integer) valueTypeBindVar.getValue();
                    Number curVal = new Number(0);
                    if ((valuTyp != null) && valuTyp.equals(111)) {
                        setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
                        setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
                        setAttrsVal("MnfWsParam2Iterator", "TlrncType", "A");
                        setAttrsVal("MnfWsParam2Iterator", "UpperLimit", curVal);
                        setAttrsVal("MnfWsParam2Iterator", "LowerLimit", curVal);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(isPerSelBindVar);
                    }
                }

            }

        } else {
            System.out.println(setTypeIdBindVar.getValue() + "    00000000000000000000000000");
            if ((valueTypeBindVar.getValue() != null) && (paramValueBindVar.getValue() != null)) {

                System.out.println(paramValueBindVar.getValue() + "    --------------------");

                Integer valuTyp = (Integer) valueTypeBindVar.getValue();
                Number curVal = (Number) paramValueBindVar.getValue();
                if ((valuTyp != null) && valuTyp.equals(111)) {
                    setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncType", "A");
                    setAttrsVal("MnfWsParam2Iterator", "UpperLimit", curVal);
                    setAttrsVal("MnfWsParam2Iterator", "LowerLimit", curVal);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(isPerSelBindVar);

                }
            } else if ((valueTypeBindVar.getValue()) != null && (paramValueBindVar.getValue() == null)) {
                Integer valuTyp = (Integer) valueTypeBindVar.getValue();
                Number curVal = new Number(0);
                if ((valuTyp != null) && valuTyp.equals(111)) {
                    setAttrsVal("MnfWsParam2Iterator", "TlrncLower", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncUpper", 0);
                    setAttrsVal("MnfWsParam2Iterator", "TlrncType", "A");
                    setAttrsVal("MnfWsParam2Iterator", "UpperLimit", curVal);
                    setAttrsVal("MnfWsParam2Iterator", "LowerLimit", curVal);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(isPerSelBindVar);
                }
            }
        }

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
    }

    public void setShadowLinkBindVar(RichLink shadowLinkBindVar) {
        this.shadowLinkBindVar = shadowLinkBindVar;
    }

    public RichLink getShadowLinkBindVar() {
        return shadowLinkBindVar;
    }

    public void setIsPerSelBindVar(RichSelectBooleanCheckbox isPerSelBindVar) {
        this.isPerSelBindVar = isPerSelBindVar;
    }

    public RichSelectBooleanCheckbox getIsPerSelBindVar() {
        return isPerSelBindVar;
    }

    public void setValueTypeBindVar(RichSelectOneChoice valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichSelectOneChoice getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
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

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void setSetTypeIdBindVar(RichSelectOneChoice setTypeIdBindVar) {
        this.setTypeIdBindVar = setTypeIdBindVar;
    }

    public RichSelectOneChoice getSetTypeIdBindVar() {
        return setTypeIdBindVar;
    }


    public void setWsParamTable(RichTable wsParamTable) {
        this.wsParamTable = wsParamTable;
    }

    public RichTable getWsParamTable() {
        return wsParamTable;
    }

    public void setWsOrgActvFlag(String wsOrgActvFlag) {
        this.wsOrgActvFlag = wsOrgActvFlag;
    }

    public String getWsOrgActvFlag() {
        return wsOrgActvFlag;
    }

    public void setWsOrgActFlag(RichSelectBooleanCheckbox wsOrgActFlag) {
        this.wsOrgActFlag = wsOrgActFlag;
    }

    public RichSelectBooleanCheckbox getWsOrgActFlag() {
        return wsOrgActFlag;
    }

    public void setWsInactiveOrgBind(RichInputText wsInactiveOrgBind) {
        this.wsInactiveOrgBind = wsInactiveOrgBind;
    }

    public RichInputText getWsInactiveOrgBind() {
        return wsInactiveOrgBind;
    }

    public void setWsInactiveDateForOrgBind(RichInputDate wsInactiveDateForOrgBind) {
        this.wsInactiveDateForOrgBind = wsInactiveDateForOrgBind;
    }

    public RichInputDate getWsInactiveDateForOrgBind() {
        return wsInactiveDateForOrgBind;
    }

    public void wsCurrIdVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setTransBaseConBind(RichInputText transBaseConBind) {
        this.transBaseConBind = transBaseConBind;
    }

    public RichInputText getTransBaseConBind() {
        return transBaseConBind;
    }

    public void setAmcAmtSpBind(RichInputText amcAmtSpBind) {
        this.amcAmtSpBind = amcAmtSpBind;
    }

    public RichInputText getAmcAmtSpBind() {
        return amcAmtSpBind;
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    public void setWsManAttach(String wsManAttach) {
        this.wsManAttach = wsManAttach;
    }

    public String getWsManAttach() {
        return wsManAttach;
    }

    public void setWsActvFlag(RichSelectBooleanCheckbox wsActvFlag) {
        this.wsActvFlag = wsActvFlag;
    }

    public RichSelectBooleanCheckbox getWsActvFlag() {
        return wsActvFlag;
    }

    public void setActvModeValChng(String ActvModeValChng) {
        this.ActvModeValChng = ActvModeValChng;
    }

    public String getActvModeValChng() {
        return ActvModeValChng;
    }

    public void setWsCapacityTyp(RichSelectOneChoice wsCapacityTyp) {
        this.wsCapacityTyp = wsCapacityTyp;
    }

    public RichSelectOneChoice getWsCapacityTyp() {
        return wsCapacityTyp;
    }

    public void setWsCapTypVal(String wsCapTypVal) {
        this.wsCapTypVal = wsCapTypVal;
    }

    public String getWsCapTypVal() {
        return wsCapTypVal;
    }

    public void setWsInactiveDate(RichInputDate wsInactiveDate) {
        this.wsInactiveDate = wsInactiveDate;
    }

    public RichInputDate getWsInactiveDate() {
        return wsInactiveDate;
    }

    public void setWsMode(String wsMode) {
        this.wsMode = wsMode;
    }

    public String getWsMode() {
        return wsMode;
    }

    public void setDocVal(String docVal) {
        this.docVal = docVal;
    }

    public String getDocVal() {
        return docVal;
    }

    public void setInputFileFieldBind(RichInputFile inputFileFieldBind) {
        this.inputFileFieldBind = inputFileFieldBind;
    }

    public RichInputFile getInputFileFieldBind() {
        return inputFileFieldBind;
    }

    public void setDispFileNameBind(RichInputText dispFileNameBind) {
        this.dispFileNameBind = dispFileNameBind;
    }

    public RichInputText getDispFileNameBind() {
        return dispFileNameBind;
    }

    public void setFile(UploadedFile file) {

        this.file = file;
    }

    public UploadedFile getFile() {

        return file;
    }

    public void setDisplayFileName(String displayFileName) {
        this.displayFileName = displayFileName;
    }

    public String getDisplayFileName() {
        return displayFileName;
    }

    public void setWsIdBind(RichInputText wsIdBind) {
        this.wsIdBind = wsIdBind;
    }

    public RichInputText getWsIdBind() {
        return wsIdBind;
    }

    public void setWsAddParamSet(StringBuffer wsAddParamSet) {
        this.wsAddParamSet = wsAddParamSet;
    }

    public StringBuffer getWsAddParamSet() {
        return wsAddParamSet;
    }

    public void setEditWsModeChng(String editWsModeChng) {
        this.editWsModeChng = editWsModeChng;
    }

    public String getEditWsModeChng() {
        return editWsModeChng;
    }


    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }

    public String getWorkStationId() {
        return workStationId;
    }

    public void setWsCreationTyp(RichSelectOneChoice wsCreationTyp) {
        this.wsCreationTyp = wsCreationTyp;
    }

    public RichSelectOneChoice getWsCreationTyp() {
        return wsCreationTyp;
    }

    public void setWsCreatnMode(String wsCreatnMode) {
        this.wsCreatnMode = wsCreatnMode;
    }

    public String getWsCreatnMode() {
        return wsCreatnMode;
    }


    public void setWsPanelTabBind(RichPanelTabbed wsPanelTabBind) {
        this.wsPanelTabBind = wsPanelTabBind;
    }

    public RichPanelTabbed getWsPanelTabBind() {
        return wsPanelTabBind;
    }

    public void setSetDisclosedCapacityTab(RichShowDetailItem setDisclosedCapacityTab) {
        this.setDisclosedCapacityTab = setDisclosedCapacityTab;
    }

    public RichShowDetailItem getSetDisclosedCapacityTab() {
        return setDisclosedCapacityTab;
    }

    public void setWsInactiveReasonBind(RichInputText wsInactiveReasonBind) {
        this.wsInactiveReasonBind = wsInactiveReasonBind;
    }

    public RichInputText getWsInactiveReasonBind() {
        return wsInactiveReasonBind;
    }

    public void setDisclosedManpowerTab(RichShowDetailItem disclosedManpowerTab) {
        this.disclosedManpowerTab = disclosedManpowerTab;
    }

    public RichShowDetailItem getDisclosedManpowerTab() {
        return disclosedManpowerTab;
    }

    public void paramSetVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setWsParamTypeNmeBind(RichInputText wsParamTypeNmeBind) {
        this.wsParamTypeNmeBind = wsParamTypeNmeBind;
    }

    public RichInputText getWsParamTypeNmeBind() {
        return wsParamTypeNmeBind;
    }


    public void setAmcVal(String amcVal) {
        this.amcVal = amcVal;
    }

    public String getAmcVal() {
        return amcVal;
    }

    public void setTotAmt(Number totAmt) {
        this.totAmt = totAmt;
    }

    public Number getTotAmt() {
        return totAmt;
    }

    public void setAmcAmtBsBind(RichInputText amcAmtBsBind) {
        this.amcAmtBsBind = amcAmtBsBind;
    }

    public RichInputText getAmcAmtBsBind() {
        return amcAmtBsBind;
    }

    public void setWstationDescBind(RichInputText wstationDescBind) {
        this.wstationDescBind = wstationDescBind;
    }

    public RichInputText getWstationDescBind() {
        return wstationDescBind;
    }

    public void setWorkCenterIdBind(RichInputListOfValues workCenterIdBind) {
        this.workCenterIdBind = workCenterIdBind;
    }

    public RichInputListOfValues getWorkCenterIdBind() {
        return workCenterIdBind;
    }

    public void setWsShiftBind(RichInputText wsShiftBind) {
        this.wsShiftBind = wsShiftBind;
    }

    public RichInputText getWsShiftBind() {
        return wsShiftBind;
    }

    /**
     *
     * Method to resolve page flow scope parameter.
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
     *
     * getting page flow scope parameter methods.
     *
     * */

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    public StringBuffer getCldId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    }

    public StringBuffer getOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    }

    public StringBuffer getHoOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    }

    public Integer getUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    public StringBuffer getWSMode() {
        return new StringBuffer(resolvEl("#{pageFlowScope.WS_MODE}").toString());
    }


    /**
     * End  of Page Flow Parameters
     *
     * */
    public void setHederFormBind(RichPanelFormLayout hederFormBind) {
        this.hederFormBind = hederFormBind;
    }

    public RichPanelFormLayout getHederFormBind() {
        return hederFormBind;
    }


    public void setAmcTotalAmountBindVar(RichInputText amcTotalAmountBindVar) {
        this.amcTotalAmountBindVar = amcTotalAmountBindVar;
    }

    public RichInputText getAmcTotalAmountBindVar() {
        return amcTotalAmountBindVar;
    }

    public void setWsRefrencedwsIdBind(RichInputListOfValues wsRefrencedwsIdBind) {
        this.wsRefrencedwsIdBind = wsRefrencedwsIdBind;
    }

    public RichInputListOfValues getWsRefrencedwsIdBind() {
        return wsRefrencedwsIdBind;
    }

    public void wsMaxEffCapVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

           // } else if (Integer.parseInt(value.toString()) <= 0) {
           } else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void wsMinEffCapVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

          //  } else if (Integer.parseInt(value.toString()) <= 0) {
          } else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void wsAvgEffCapVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

         //   } else if (Integer.parseInt(value.toString()) <= 0) {
         } else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }

    public void empShiftVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Object empId = getAttrsVal("MnfWsRes2Iterator", "WsEmpId");
            Object wcID = getAttrsVal("MnfWs1Iterator", "WsId");
            String shftNme = (String) object;
            /* if (duplicateValue("MnfWsRes2Iterator", "TransShift", object)) {
               System.out.println("Inside of validator !!");
               ob = ADFBeanUtils.findOperation("duplicateempToShift");
              ob.getParamsMap().put("paramName", empNme.toString());
               ob.getParamsMap().put("ShiftNme", shftNme);
              ob.execute();
              Boolean bb = (Boolean)ob.getResult();
               if (!bb) {
               System.out.println("Inside of orgchk validator !!");
               JSFUtils.addFacesErrorMessage("Duplicate Employee is attached to Shift !");
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             "Duplicate Employee is attached to Shift", null));
           }
           } */

            ob = ADFUtils.findOperation("duplicateEmployeeID");
            ob.getParamsMap().put("shiftVal", object.toString());
            ob.getParamsMap().put("empVal", empId != null ? empId : "");
            ob.execute();
            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1353']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Selected Employee has already been attached to this shift!!",
                                                              null));
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsManPowerTableBind);

    }


    public void setWsResNmeBind(RichInputListOfValues wsResNmeBind) {
        this.wsResNmeBind = wsResNmeBind;
    }

    public RichInputListOfValues getWsResNmeBind() {
        return wsResNmeBind;
    }

    public void defaultShiftVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
           // System.out.println("Inside of dfltsft validator !!" + object);
            String val = "N";
            if (object != null && object.toString().equalsIgnoreCase("true")) {
                val = "Y";
            if (duplicateValue("MnfWsShift2Iterator", "ShiftDfltFlg", val)) {
                 //   System.out.println("Inside of dfltsft validator !!");
                    JSFUtils.addFacesErrorMessage("Default Shift !");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Default Shift For WorkStation is already Exists ", null));
                }
            }
        }

    }

    public void defaultShiftVCE(ValueChangeEvent valueChangeEvent) {
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
    }

    public void defalutWsVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
            
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            ob = ADFBeanUtils.findOperation("chkDefaultWsVal");
            String wcid = getAttrsVal("MnfWs1Iterator", "WcId").toString();
           ob.getParamsMap().put("WcId", wcid);
           ob.execute();
             Object result = ob.getResult();
             System.out.println("returned from impl " + result);
            if (result != null && result.toString().equalsIgnoreCase("true")) {
              //  String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1357']}");
                System.out.println("inside validator");
                String msg = "Default Work Station for a select Work Center is already exists !";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void defaultWsVCE(ValueChangeEvent valueChangeEvent) {
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
    }

    public void minRunTimeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(10, 1, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 1 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (value.compareTo(0) <= 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
 }

    public void defaultResVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            ob = ADFBeanUtils.findOperation("IsManPowerInchargeForShift");
            Object tmp = getAttrsVal("MnfWsRes2Iterator", "ShiftId");
            Object empId = getAttrsVal("MnfWsRes2Iterator", "WsEmpId");
            if (tmp != null && empId != null) {
                String shId = tmp.toString();
                ob.getParamsMap().put("shift", shId);
                ob.getParamsMap().put("empId",empId.toString());
                ob.execute();
                
                Object result = ob.getResult();

                if (result != null && result.toString().equalsIgnoreCase("false")) {
                    //String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1357']}");
                    String msg = "Default Employee for a Shift is already Selected !!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }else
            {
                String msg = "Employee or Shift is not Selected !!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    
                
            }
            
        }

    }

    public void setDefaultWsBind(RichSelectBooleanCheckbox defaultWsBind) {
        this.defaultWsBind = defaultWsBind;
    }

    public RichSelectBooleanCheckbox getDefaultWsBind() {
        return defaultWsBind;
    }

    public void wsPurAmtVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

//           } else if (Integer.parseInt(value.toString()) < 0) {
            } else if (value.getValue() < 0) {
                FacesMessage message = new FacesMessage("Value must be greater than zero!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }
        }

    }
}
