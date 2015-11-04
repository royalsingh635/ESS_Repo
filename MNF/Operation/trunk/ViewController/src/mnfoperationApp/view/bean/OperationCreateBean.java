package mnfoperationApp.view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import adf.utils.bean.ADFBeanUtils;

import java.util.ArrayList;
import java.util.Map;

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

import mnfoperationApp.view.utils.ADFUtils;
import mnfoperationApp.view.utils.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class OperationCreateBean {
    private RichSelectBooleanCheckbox opActv;
    private RichInputFile attachDocBindVar;
    private RichInputText attachFileNmBindVar;
    private UploadedFile _File;
    private String fileDisplayname = "";
    private RichSelectBooleanCheckbox orgOpActv;
    private String editClick = null;
    private RichInputComboboxListOfValues refOpIdBind;
    private RichSelectBooleanCheckbox subActive;
    private String wfId = null;
    private RichInputText opDescBind;
    private RichInputText inactvReasonBind;
    private RichInputText orgInactvReasnBind;
    private RichInputText subContInactvResnBind;
    private RichInputDate inactvDtBind;
    private RichInputDate subContrctInactvDtBind;
    private RichInputDate orgInactvDateBind;
    private RichInputText opReviseMode;
    private RichInputText legCodeBind;
    private RichInputDate effectvDtBind;
    private RichLink copyPreviousLinkBind;
    private String refOpIdLock = null;
    private RichSelectOneChoice opBasisBind;
    private String revise = null;
    private String lchk = null;
    private RichInputText toolPriceBind;
    private RichInputText toolTotalAmtBind;
    private RichPanelCollection toolPanelBinding;
    private RichPanelTabbed panelTabBinding;
    private RichShowDetailItem showDetailWorkCenterTab;
    private RichShowDetailItem showDetailSubContractorTab;
    private RichShowDetailItem toolAndEquipmntTab;
    private RichSelectOneChoice bindSubContractNm;

    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(OperationCreateBean.class);
    private RichSelectBooleanCheckbox contractTypBind;
    private RichInputText toolQtyBind;
    private RichTable tooltblbind;


    public OperationCreateBean() {
    }

    /*-----------------------------Getter and Setter---------------------------*/
    public void setLchk(String lchk) {
        this.lchk = lchk;
    }

    public String getLchk() {
        return lchk;
    }

    public void setRevise(String revise) {
        this.revise = revise;
    }

    public String getRevise() {
        return revise;
    }

    public void setOpBasisBind(RichSelectOneChoice opBasisBind) {
        this.opBasisBind = opBasisBind;
    }

    public RichSelectOneChoice getOpBasisBind() {
        return opBasisBind;
    }

    public void setRefOpIdLock(String refOpIdLock) {
        this.refOpIdLock = refOpIdLock;
    }

    public String getRefOpIdLock() {
        return refOpIdLock;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setSubActive(RichSelectBooleanCheckbox subActive) {
        this.subActive = subActive;
    }

    public RichSelectBooleanCheckbox getSubActive() {
        return subActive;
    }

    public void setOpActv(RichSelectBooleanCheckbox opActv) {
        this.opActv = opActv;
    }

    public RichSelectBooleanCheckbox getOpActv() {
        return opActv;
    }

    public void setAttachDocBindVar(RichInputFile attachDocBindVar) {
        this.attachDocBindVar = attachDocBindVar;
    }

    public RichInputFile getAttachDocBindVar() {
        return attachDocBindVar;
    }

    public void setAttachFileNmBindVar(RichInputText attachFileNmBindVar) {
        this.attachFileNmBindVar = attachFileNmBindVar;
    }

    public RichInputText getAttachFileNmBindVar() {
        return attachFileNmBindVar;
    }

    public void setFile(UploadedFile _File) {
        this._File = _File;
    }

    public UploadedFile getFile() {
        return _File;
    }

    public void setFileDisplayname(String fileDisplayname) {
        this.fileDisplayname = fileDisplayname;
    }

    public String getFileDisplayname() {
        return fileDisplayname;
    }

    public void setFileList(ArrayList<UploadDoc> fileList) {
        this.fileList = fileList;
    }

    public ArrayList<UploadDoc> getFileList() {
        return fileList;
    }

    public void setOrgOpActv(RichSelectBooleanCheckbox orgOpActv) {
        this.orgOpActv = orgOpActv;
    }

    public RichSelectBooleanCheckbox getOrgOpActv() {
        return orgOpActv;
    }

    public void setEditClick(String editClick) {
        this.editClick = editClick;
    }

    public String getEditClick() {
        return editClick;
    }

    public void setRefOpIdBind(RichInputComboboxListOfValues refOpIdBind) {
        this.refOpIdBind = refOpIdBind;
    }

    public RichInputComboboxListOfValues getRefOpIdBind() {
        return refOpIdBind;
    }

    public void setOpDescBind(RichInputText opDescBind) {
        this.opDescBind = opDescBind;
    }

    public RichInputText getOpDescBind() {
        return opDescBind;
    }

    public void setInactvReasonBind(RichInputText inactvReasonBind) {
        this.inactvReasonBind = inactvReasonBind;
    }

    public RichInputText getInactvReasonBind() {
        return inactvReasonBind;
    }

    public void setOrgInactvReasnBind(RichInputText orgInactvReasnBind) {
        this.orgInactvReasnBind = orgInactvReasnBind;
    }

    public RichInputText getOrgInactvReasnBind() {
        return orgInactvReasnBind;
    }

    public void setSubContInactvResnBind(RichInputText subContInactvResnBind) {
        this.subContInactvResnBind = subContInactvResnBind;
    }

    public RichInputText getSubContInactvResnBind() {
        return subContInactvResnBind;
    }

    public void setInactvDtBind(RichInputDate inactvDtBind) {
        this.inactvDtBind = inactvDtBind;
    }

    public RichInputDate getInactvDtBind() {
        return inactvDtBind;
    }

    public void setSubContrctInactvDtBind(RichInputDate subContrctInactvDtBind) {
        this.subContrctInactvDtBind = subContrctInactvDtBind;
    }

    public RichInputDate getSubContrctInactvDtBind() {
        return subContrctInactvDtBind;
    }

    public void setOrgInactvDateBind(RichInputDate orgInactvDateBind) {
        this.orgInactvDateBind = orgInactvDateBind;
    }

    public RichInputDate getOrgInactvDateBind() {
        return orgInactvDateBind;
    }

    public void setOpReviseMode(RichInputText opReviseMode) {
        this.opReviseMode = opReviseMode;
    }

    public RichInputText getOpReviseMode() {
        return opReviseMode;
    }

    public void setLegCodeBind(RichInputText legCodeBind) {
        this.legCodeBind = legCodeBind;
    }

    public RichInputText getLegCodeBind() {
        return legCodeBind;
    }

    public void setEffectvDtBind(RichInputDate effectvDtBind) {
        this.effectvDtBind = effectvDtBind;
    }

    public RichInputDate getEffectvDtBind() {
        return effectvDtBind;
    }

    public void setToolPanelBinding(RichPanelCollection toolPanelBinding) {
        this.toolPanelBinding = toolPanelBinding;
    }

    public RichPanelCollection getToolPanelBinding() {
        return toolPanelBinding;
    }

    public void setPanelTabBinding(RichPanelTabbed panelTabBinding) {
        this.panelTabBinding = panelTabBinding;
    }

    public RichPanelTabbed getPanelTabBinding() {
        return panelTabBinding;
    }

    public void setShowDetailWorkCenterTab(RichShowDetailItem showDetailWorkCenterTab) {
        this.showDetailWorkCenterTab = showDetailWorkCenterTab;
    }

    public RichShowDetailItem getShowDetailWorkCenterTab() {
        return showDetailWorkCenterTab;
    }

    public void setShowDetailSubContractorTab(RichShowDetailItem showDetailSubContractorTab) {
        this.showDetailSubContractorTab = showDetailSubContractorTab;
    }

    public RichShowDetailItem getShowDetailSubContractorTab() {
        return showDetailSubContractorTab;
    }
    /*---------------------------Get Binding Method-------------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setCopyPreviousLinkBind(RichLink copyPreviousLinkBind) {
        this.copyPreviousLinkBind = copyPreviousLinkBind;
    }

    public RichLink getCopyPreviousLinkBind() {
        return copyPreviousLinkBind;
    }

    public void setToolPriceBind(RichInputText toolPriceBind) {
        this.toolPriceBind = toolPriceBind;
    }

    public RichInputText getToolPriceBind() {
        return toolPriceBind;
    }

    public void setToolTotalAmtBind(RichInputText toolTotalAmtBind) {
        this.toolTotalAmtBind = toolTotalAmtBind;
    }

    public RichInputText getToolTotalAmtBind() {
        return toolTotalAmtBind;
    }

    /*-------------------------Common Method For Faces Message------------------------*/
    public void Add_Faces_Message_RichInputListOfValues(RichInputListOfValues S1, String M1) {
        FacesMessage Message = new FacesMessage(M1);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S1.getClientId(), Message);
    }

    public void Add_Faces_Message_RichInputText(RichInputText S2, String M2) {
        FacesMessage Message = new FacesMessage(M2);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S2.getClientId(), Message);
    }

    public void Add_Faces_Message_RichInputDate(RichInputDate S3, String M3) {
        FacesMessage Message = new FacesMessage(M3);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S3.getClientId(), Message);
    }

    /*----------------------------------Method for open Default Panel Tab---------------------*/
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

    /*--------------------------FINAL COMMIT----------------------------------*/
    public void saveACTION(ActionEvent actionEvent) {
        Integer countwc = WorkCenterNo();
                        if (countwc == 0) {
                            JSFUtils.addFacesInformationMessage("Please add work center..!!");
                            setDisclosedFirstTab(showDetailWorkCenterTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailWorkCenterTab);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                        }else{
        ob = ADFUtils.findOperation("SubContractValidate");
        ob.execute();
        adfLog.info("Value of subContact::"+ob.getResult());
        String result=(String)ob.getResult();
        if(result!=null && result.equalsIgnoreCase("Y")){
        ob = ADFUtils.findOperation("ChkValidateAction");
        ob.execute();
        Integer vl = (Integer) ob.getResult();
        System.out.println("return value in save action :" + vl);
        if (vl == 1) {
            Add_Faces_Message_RichInputText(opDescBind, "Operation description is required.");
        }
        /* else if (vl == 2) {
            Add_Faces_Message_RichInputText(legCodeBind, "legacy Code is required.");
        }   */
        else if (vl == 3) {
            Add_Faces_Message_RichInputDate(effectvDtBind, "Operation effective date is required.");
        } else if (vl == 4) {
            String desc = (String) opDescBind.getValue().toString();
            ob = ADFUtils.findOperation("chkDescDuplicate");
            ob.getParamsMap().put("desc", desc);
            if (("edit").equals(getEditClick())) {
                ob.getParamsMap().put("prfId", "E");
            } else if (("S").equals(getRevise())) {
                ob.getParamsMap().put("prfId", "E");
            } else if (("lchk").equals(getLchk())) {
                ob.getParamsMap().put("prfId", "E");
            } else {
                ob.getParamsMap().put("prfId", "");
            }
            ob.execute();
            String duplicateDesc = (String) ob.getResult();
            if (duplicateDesc.equals("Y")) {
                Add_Faces_Message_RichInputText(opDescBind, "Operation already exist.");
            } else {
//                ob = ADFUtils.findOperation("countEO");
//                ob.execute();
//                String Eo = (String) ob.getResult();
//                System.out.println("value of subcontract" + Eo);
//                if (Eo.equals("SubContract")) {
//                    FacesMessage message = new FacesMessage("Please Enter Sub Contractor Name..!!");
//                    message.setSeverity(FacesMessage.SEVERITY_INFO);
//                    FacesContext fc = FacesContext.getCurrentInstance();
//                    fc.addMessage(null, message);
//                    setDisclosedFirstTab(showDetailSubContractorTab);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailSubContractorTab);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
//                } else {
                    ADFUtils.findOperation("chkOrgExist").execute();
                    ob = ADFUtils.findOperation("Commit");
                    Object execute = ob.execute();
                    if (execute == null) {
                        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                        pageFlowScope.put("OP_MODE", "V");
                        FacesMessage message = new FacesMessage("Operation Saved Successfully!");
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        this.setEditClick(null);
                        setDisclosedFirstTab(toolAndEquipmntTab);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(toolAndEquipmntTab);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                    }
                    callFuncWf();
                    ADFUtils.findOperation("Commit").execute();
              //  }
            }
        }
        }
        else {
            //Add_Faces_Message_RichInputText(bindSubContractNm, "SubContract Name is required.");
            String msg="SubContract Name is required.";
            FacesMessage Message = new FacesMessage(msg);
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(bindSubContractNm.getClientId(), Message);
        }
                        }
    }

    /*-----------------------------RESOLVE EXPRESSION METHOD------------------------*/
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    /*----------------------------Resolve Expression for PafeFlowScope----------------------*/
//    public Object resolvEl(String data) {
//        ADFContext adfCtx = ADFContext.getCurrent();
//        ELContext elContext = adfCtx.getELContext();
//        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
//        Object Message = valueExp.getValue(elContext);
//        return Message;
//    }

    /*----------------------------Get pageFlowScope Values----------------------------------*/

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

    public StringBuffer getTxnId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_GET_TXN_ID}").toString());
    }

    public StringBuffer getOpMode() {
        return new StringBuffer(resolvEl("#{pageFlowScope.OP_MODE}").toString());
    }

    public StringBuffer getOperationID() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_OPRTN_ID}").toString());
    }


    /*-----------------------Operation Active Hide Date/Reason------------------------------*/
    public void INACTIVE_CHECK(ValueChangeEvent valueChangeEvent) {
        if (opActv.isSelected()) {
            inactvDtBind.setValue("");
            this.inactvDtBind.setVisible(false);
            this.inactvReasonBind.setRequired(false);
            this.inactvReasonBind.setValue("");
            this.inactvReasonBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactvReasonBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactvDtBind);
            this.setLchk("lchk");
        } else {
            Date dte = new Date(Date.getCurrentDate());
            this.inactvDtBind.setValue(new Timestamp(dte));
            this.inactvReasonBind.setRequired(true);
            this.inactvReasonBind.setVisible(true);
            this.inactvDtBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactvReasonBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactvDtBind);
            this.setLchk("lchk");
        }
    }

    /*--------------------------------Action for create with Parameter in tools----------------------*/
    public void createWithParamMNFOpTOOLS(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateWithParamsInMnfOpTools").execute();
    }

    /*---------------------------------Variable Declaration for Upload File-------------------------------------*/
    InputStream inputStream = null;
    StringBuffer fileName = new StringBuffer();
    StringBuffer dirPath = getPath(); //new StringBuffer("D:\\DOCS"); //getPath(); //new StringBuffer("D:\\DOCS");
    ArrayList<UploadDoc> fileList = new ArrayList<UploadDoc>();

    /*-----------------------------GetPath for Upload Document-----------------------------*/
    public StringBuffer getPath() {
        /* StringBuffer path = new StringBuffer((String)callStoredFunction(Types.VARCHAR, " APP.FN_GET_APP_DOC_ATTACH_PATH(?)",
                                                            new Object[] { getSlocId() }));
            System.out.println("path " + path);
            if (path != null) {
                System.out.println("return path when fucntion called sucessfully");
                return path;
            } else {
                System.out.println("return when fucntion not called successfully");
                return new StringBuffer("D:\\DOCS");
            } */
        return new StringBuffer("D:\\DOCS");
    }

    /*-----------------------------Attachment Duplicate Name------------------------*/
    public Boolean chkDuplicateInAtachmentValidate(String FileNm) {
        ob = ADFUtils.findOperation("duplicateCheckInDocAttach");
        ob.getParamsMap().put("val", FileNm);
        ob.execute();
        String reslt = ob.getResult().toString();
        System.out.println("Return Result Value ::::: " + reslt);
        if ("fail".equals(reslt)) {
            return true;
        } else {
            return false;
        }
    }

    /*----------------------------Upload file Action  Method------------------------------*/
    public void uploadDocACTION(ActionEvent actionEvent) throws IOException {
        if (fileDisplayname.equals("")) {
            Add_Faces_Message_RichInputText(attachFileNmBindVar, "Please enter Display name.");
        } else if (chkDuplicateInAtachmentValidate(fileDisplayname)) {
            Add_Faces_Message_RichInputText(attachFileNmBindVar, "File Name Already Exist.");
        } else if (_File != null) {
            System.out.println("_File value : " + _File);
            System.out.println("fileDispName : " + fileDisplayname);
            System.out.println("FILE_NAME :" + _File.getFilename());
            System.out.println("File Content : " + _File.getContentType());
            System.out.println("Dir path : " + dirPath + "\\" + fileName);
            System.out.println("Value of inputstream in Block : " + inputStream);

            fileList.add(new UploadDoc(_File.getFilename(), _File.getContentType().toString(),
                                       dirPath + "\\" + fileName,
                                       fileDisplayname +
                                       _File.getFilename().substring(_File.getFilename().lastIndexOf(".")), inputStream,
                                       _File.getFilename().substring(_File.getFilename().lastIndexOf("."))));

            for (UploadDoc list : fileList) {
                System.out.println("----------------***************---------------");
                System.out.println("File Name :" + _File.getFilename());
                System.out.println("File Extension : " + list.getAttchedFileExtension());
                System.out.println("File path : " + list.getAttchedFilePath());
                System.out.println("File Display Name : " + list.getAttchedFileDispName());
                System.out.println("File ext : " + list.getFileExt());
                System.out.println("File input Stream : " + list.getFileInputStream());

                ob = ADFUtils.findOperation("insertInMnfOpAttch");
                ob.getParamsMap().put("AttchFileExtn", list.getAttchedFileExtension());
                ob.getParamsMap().put("AttchFilepath", list.getAttchedFilePath());
                ob.getParamsMap().put("AttchExtn", list.getFileExt());
                ob.getParamsMap().put("DispFlNm", list.getAttchedFileDispName());
                ob.getParamsMap().put("DocId", getTxnId());
                ob.execute();
                String Nm = (String) ob.getResult();
                System.out.println("Value of Nm******** :" + Nm);
                if (list.getFileInputStream() != null) {
                    System.out.println("*******Under List*****");
                    try {
                        System.out.println("Value of inputStream === " + list.getFileInputStream());
                        InputStream in = list.getFileInputStream();
                        System.out.println("FILE PATH : " + list.getAttchedFilePath() + Nm + list.getFileExt());
                        FileOutputStream out = new FileOutputStream(list.getAttchedFilePath() + Nm + list.getFileExt());
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

            }
            attachFileNmBindVar.setValue("");
            _File.dispose();
            _File = null;
            fileList.clear();
            System.out.println("-------------------FILE UPLOADED------------------");

        } else {
            FacesMessage Message = new FacesMessage("Please select a file.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(attachDocBindVar.getClientId(), Message);
        }
    }


    /*-----------------------------Input file change Listner--------------------------*/
    public void inputFileListner(ValueChangeEvent vce) throws IOException {
        if (vce.getNewValue() != null) {
            UploadedFile file = (UploadedFile) vce.getNewValue();
            inputStream = file.getInputStream();
            System.out.println("INPUTSTREAM : " + inputStream);
        }
    }

    /*-----------------------------CANCEL/BACK BUTTON----------------------------------------*/
    public void onCancelAction(ActionEvent actionEvent) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_GET_TXN_ID", null);
        pageFlowScope.put("GLBL_OPRTN_ID", null);
    }


    /*---------------------------EDIT ACTION------------------------------------------*/
    public void onEditACTION(ActionEvent actionEvent) {
        System.out.println("Value of Mode-->>>" + opReviseMode.getValue().toString());
        if (opReviseMode.getValue().toString().equals("INCOMPLETE") ||
            opReviseMode.getValue().toString().equals("HOLD")) {
            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
            pageFlowScope.put("OP_MODE", "C");
            this.setEditClick("edit");
        } else if (opReviseMode.getValue().toString().equals("APPROVED") ||
                   opReviseMode.getValue().toString().equals("FORWARDED")) {
            Integer usr_Id = getUsrId();
            Integer pending = 0;
            ob = ADFUtils.findOperation("getDocUsrFromWF");
            ob.execute();
            Integer chkUsr = (Integer) ob.getResult();

            if (chkUsr != null) {
                pending = chkUsr;
            }
            System.out.println("Current User-" + usr_Id + "and Order Pending At-" + pending);

            if (pending.compareTo(usr_Id) == 0) {
                Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                pageFlowScope.put("OP_MODE", "C");
                this.setEditClick("edit");
            } else if (pending.compareTo(new Integer(-1)) == 0) {
                String Msg22 = "Operation has been Approved, You can not edit Operation";
                FacesMessage message2 = new FacesMessage(Msg22);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {
                ob = ADFUtils.findOperation("getUserName");
                ob.getParamsMap().put("u_Id", pending);
                ob.getParamsMap().put("slc_id", getSlocId());
                ob.execute();
                if (ob.getResult() != null) {

                    String msg2 =
                        "<html><body> <p>This Operation is pending at other user<b><i> [ " + ob.getResult().toString() +
                        " ] </i></b>for approval, You can not edit this.</p></body></html>";
                    //   String msg2 =
                    //       "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}").toString() + "<b><i> [ " + uNm.getResult().toString() +
                    //       " ] </i></b>" + resolvEl("#{bundle['MSG.879']}").toString() + "</p></body></html>";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            }
        }
    }


    /*----------------------------------CREATE WORK CENTER-----------------------------*/
    public void addWorkCenter(ActionEvent actionEvent) {
        Integer mode = (Integer) getAttrsVal("MnfOp1Iterator", "OpBasis");
        if (mode == 15) {
            if (refOpIdBind.getValue() == null) {
                System.out.println("test... ");
                showFacesMessage("Refrenced Doc is required.", "E", false, "F", refOpIdBind.getClientId());
                return;
            }
        }
        else{
        ADFUtils.findOperation("CreateWithParamsInWorkCenter").execute();
        }
    }


    /*---------------------------------CREATE ORGANIZATION-----------------------------*/
    public void addORGNIZATION(ActionEvent actionEvent) {
        /* ob = ADFUtils.findOperation("ChkInactiveReason");
        ob.getParamsMap().put("val", 1);
        ob.execute();
        Integer retVal = (Integer)ob.getResult();
        System.out.println("val in add org :" + retVal);
        if(retVal == 1)
        {
           JSFUtils.addFacesErrorMessage("Please Enter Inactive Reason");
        }
        else
        { */
        ADFUtils.findOperation("CreateWithParamsORG").execute();
        //}
    }

    /*----------------------------Change Listner for organization Activ/Inactibve Hide--------------------*/
    public void inactiveOrgCHECK(ValueChangeEvent valueChangeEvent) {
        if (orgOpActv.isSelected() == true) {
            this.orgInactvDateBind.setValue("");
            this.orgInactvDateBind.setVisible(false);
            this.orgInactvReasnBind.setRequired(false);
            this.orgInactvReasnBind.setValue("");
            this.orgInactvReasnBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgInactvDateBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgInactvReasnBind);
        } else {
            Date dte = new Date(Date.getCurrentDate());
            this.orgInactvDateBind.setValue(new Timestamp(dte));
            this.orgInactvReasnBind.setRequired(true);
            this.orgInactvReasnBind.setVisible(true);
            this.orgInactvDateBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgInactvDateBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgInactvReasnBind);
        }
    }


    /*-----------------------------Change Listner for get Ref Op Id--------------------------------------*/
    public void getRefOpIdFunc(ValueChangeEvent valueChangeEvent) {
        // System.out.println("value of RefOPid----------" + this.refOpIdBind.getValue().toString());
        this.setRefOpIdLock("lock");
        ActionEvent ae = new ActionEvent(copyPreviousLinkBind);
        ae.queue();
        this.opBasisBind.setDisabled(true);
        this.refOpIdBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opBasisBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refOpIdBind);
    }

    /*------------------------------Tools and Items delete------------------------------*/
    public void onDeleteTools(ActionEvent actionEvent) {
        ADFUtils.findOperation("DeleteTools").execute();
    }

    /*------------------------------Attachment delete------------------------------*/
    public void onDeleteAttach(ActionEvent actionEvent) {
        ADFUtils.findOperation("DeleteAttach").execute();
    }

    /*------------------------------Work Center delete------------------------------*/
    public void onDeleteWorkCenter(ActionEvent actionEvent) {
        ADFUtils.findOperation("DeleteWC").execute();
    }

    /*------------------------------Organization delete------------------------------*/
    public void onDeleteOrg(ActionEvent actionEvent) {
        ADFUtils.findOperation("DeleteOrg").execute();
    }

    /*------------------------------Sub Contractor delete------------------------------*/
    public void onDeleteSubContract(ActionEvent actionEvent) {
        ADFUtils.findOperation("DeleteSubContract").execute();
    }


    /*-------------------------------Create Sub contractor------------------------------*/
    public void onAddSubContract(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateWithParamsInSubContract").execute();
    }

    /*------------------------------Sub Contractor HIDE Actve/Inactive date & Reason------------------------------*/
    public void subContractListner(ValueChangeEvent valueChangeEvent) {
        if (subActive.isSelected() == true) {
            this.subContrctInactvDtBind.setValue("");
            this.subContrctInactvDtBind.setVisible(false);
            this.subContInactvResnBind.setRequired(false);
            this.subContInactvResnBind.setValue("");
            this.subContInactvResnBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(subContrctInactvDtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(subContInactvResnBind);

        } else {
            Date dte = new Date(Date.getCurrentDate());
            this.subContrctInactvDtBind.setValue(new Timestamp(dte));
            // this.subContInactvResnBind.setRequired(true);
            //   this.subContInactvResnBind.setVisible(true);
            //   this.subContrctInactvDtBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(subContrctInactvDtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(subContInactvResnBind);
        }
    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        callWfId();
        ADFUtils.findOperation("callWfFunctions").execute();
    }

    /*---------------------Call callWfId Function------------------------------*/
    public void callWfId() {
        ob = ADFUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();
        setWfId(wId);
    }


    /*------------------------------SAVE & FORWARD---------------------------------*/

    public String SaveandForwardAction() {
        ob = ADFUtils.findOperation("SubContractValidate");
        ob.execute();
        adfLog.info("Value of subContact::"+ob.getResult());
        String result=(String)ob.getResult();
        if(result!=null && result.equalsIgnoreCase("Y")){
        ob = ADFUtils.findOperation("ChkValidateAction");
        ob.execute();
        Integer vl = (Integer) ob.getResult();
        System.out.println("return value in save action :" + vl);
        if (vl == 1) {
            Add_Faces_Message_RichInputText(opDescBind, "Operation description is required.");
        } else if (vl == 2) {
            Add_Faces_Message_RichInputText(legCodeBind, "legacy Code is required.");
        } else if (vl == 3) {
            Add_Faces_Message_RichInputDate(effectvDtBind, "Operation effective date is required.");
        } else if (vl == 4) {
            String desc = (String) opDescBind.getValue().toString();
            ob = ADFUtils.findOperation("chkDescDuplicate");
            ob.getParamsMap().put("desc", desc);
            if (("edit").equals(getEditClick())) {
                ob.getParamsMap().put("prfId", "E");
            } else if (("S").equals(getRevise())) {
                ob.getParamsMap().put("prfId", "E");
            } else if (("lchk").equals(getLchk())) {
                ob.getParamsMap().put("prfId", "E");
            } else {
                ob.getParamsMap().put("prfId", "");
            }
            ob.execute();
            String duplicateDesc = (String) ob.getResult();
            if (duplicateDesc.equals("Y")) {
                Add_Faces_Message_RichInputText(opDescBind, "Operation already exist.");
            } else {
                Integer countwc = WorkCenterNo();
                if (countwc == 0) {
                    JSFUtils.addFacesInformationMessage("Please add work center..!!");
                    setDisclosedFirstTab(showDetailWorkCenterTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailWorkCenterTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                } else {
//                    ob = ADFUtils.findOperation("countEO");
//                    ob.execute();
//                    String Eo = (String) ob.getResult();
//                    System.out.println("value of subcontract" + Eo);
//                    if (Eo.equals("SubContract")) {
//                        FacesMessage message = new FacesMessage("Please Enter Sub Contractor Name..!!");
//                        message.setSeverity(FacesMessage.SEVERITY_INFO);
//                        FacesContext fc = FacesContext.getCurrentInstance();
//                        fc.addMessage(null, message);
//                        setDisclosedFirstTab(showDetailSubContractorTab);
//                        AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailSubContractorTab);
//                        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
//                    } else {
                        System.out.println("---------Hi-------");
                        ADFUtils.findOperation("chkOrgExist").execute();
                        ob = ADFUtils.findOperation("Commit");
                        ob.execute();
                        Object execute = ob.execute();
                        if (execute == null) {
                            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                            pageFlowScope.put("OP_MODE", "V");
                            this.setEditClick(null);
                        }
                        callFuncWf();
                        ADFUtils.findOperation("Commit").execute();
                        return "WorkFlow";
                   // }
                }
            }
        }       
        } else {
            //Add_Faces_Message_RichInputText(bindSubContractNm, "SubContract Name is required.");
            String msg="SubContract Name is required.";
            FacesMessage Message = new FacesMessage(msg);
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(bindSubContractNm.getClientId(), Message);
        }
        return "0";
    }

    /*----------------Duplicate in Tools----------------------*/
    public void ToolNmValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String tool = (String) object.toString();
        ob = ADFUtils.findOperation("ChkDuplicateTool");
        ob.getParamsMap().put("DocId", getTxnId());
        ob.getParamsMap().put("toolNm", tool);
        ob.execute();
        String dulicateTool = (String) ob.getResult();
        if (dulicateTool.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tool already exist.", null));
        } else {
            System.out.println("---------------Nice----------------");
        }

    }

    /*--------------------Action Listner for Revision--------------*/
    public void RevisionNoBTN(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateInsertInReviseOrgMnfop").execute();
        ob = ADFUtils.findOperation("GenReviseNo");
        ob.execute();
        this.setRefOpIdLock("lock");
        this.setRevise("S");
        this.opBasisBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opBasisBind);
    }

    /*----------------Count Work center row---------------*/
    public Integer WorkCenterNo() {
        ob = ADFUtils.findOperation("countWC");
        ob.execute();
        Integer i = (Integer) ob.getResult();
        System.out.println("Value of WorkCeneter ::" + i);
        return i;
    }

    /*---------Dplicate Org Validation-----------------------------------*/
    public void DulicateOrgValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String org = (String) object.toString();
        ob = ADFUtils.findOperation("ChkDuplicateOrg");
        ob.getParamsMap().put("DocId", getTxnId());
        ob.getParamsMap().put("OrgNm", org);
        ob.execute();
        String dulicateOrg = (String) ob.getResult();
        if (dulicateOrg.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Organization already exist.",
                                                          null));
        } else {
            System.out.println("---------------Nice----------------");
        }
    }

    /*--------------------Dulicate Validation of Work Center--------------------------*/
    public void WorkCenterValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String wc = (String) object.toString();
        ob = ADFUtils.findOperation("ChkDuplicateWC");
        ob.getParamsMap().put("DocId", getTxnId());
        ob.getParamsMap().put("WcNm", wc);
        ob.execute();
        String dulicateWC = (String) ob.getResult();
        if (dulicateWC.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Work Center already exist.",
                                                          null));
        } else {
            System.out.println("---------------Nice----------------");
        }
    }

    /*-------------------------Duplicate in Sub contractor Name----------------------------------*/
    public void eoNameValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String sc = (String) object.toString();
        ob = ADFUtils.findOperation("chkDuplicateEo");
        ob.getParamsMap().put("DocId", getTxnId());
        ob.getParamsMap().put("ScNm", sc);
        ob.execute();
        String dsc = (String) ob.getResult();
        if (dsc.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name already exist.", null));
        } else {
            System.out.println("--------Workking------");
        }
    }

    /*-------------------------Download file listner----------------------------*/
    public void fileDownloadAction(FacesContext facesContext, OutputStream outputStream) throws IOException {
        //Read file from particular path, path bind is binding of table field that contains path
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

    /*------------------------Create With Parameter in ADD MODE------------------*/
    public void onAddACTION(ActionEvent actionEvent) {
        String TxnId = DocTxnIdFunc();
        String opSno = OperationSNO();
        Date currntDt = CurrentDtFunc();
        System.out.println("Value of TxnIdx : " + TxnId);
        System.out.println("Opeartion Snox : " + opSno);
        System.out.println("Currnt datex : " + currntDt);
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("OP_MODE", "C");
        pageFlowScope.put("GLBL_CURRENT_DT", currntDt);
        pageFlowScope.put("GLBL_GET_TXN_ID", TxnId);
        pageFlowScope.put("GLBL_OPRTN_ID", opSno);
        ADFUtils.findOperation("CreateWithParamsInMnfOp").execute();
        ADFUtils.findOperation("CreateInsertInOrgMnfAdd").execute();
    }

    /*----------------------Function to get DocTxnId---------------------------*/
    public String DocTxnIdFunc() {
        ob = ADFUtils.findOperation("getDocTxnId");
        ob.execute();
        String val = (String) ob.getResult();
        return val;
    }
    /*-----------------------Function to get operation card S. No---------------------*/
    public String OperationSNO() {
        ob = ADFUtils.findOperation("getOperationSNO");
        ob.execute();
        String val1 = (String) ob.getResult();
        return val1;
    }
    /*-----------------------Current Date--------------------------------*/
    public Date CurrentDtFunc() {
        Date convertedDate = new Date(Date.getCurrentDate());
        return convertedDate;
    }

    /*-------------------------validation on effective date------------------------*/
    public void OpEffcetvDateValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date currntDt = null;
            java.sql.Date effectvDt = null;
            try {
                Date dte = new Date(Date.getCurrentDate());
                currntDt = ((Timestamp) new Timestamp(dte)).dateValue();
                effectvDt = ((Timestamp) object).dateValue();
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            if (currntDt.compareTo(effectvDt) > 0) {
                if (currntDt.toString().equals(effectvDt.toString())) {
                } else {
                    Add_Faces_Message_RichInputDate(effectvDtBind, "Please select correct date.");
                }
            }
        }
    }
    /*-------------------------validate on quantity of tools-------------------------*/
    public void ToolQtyValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
            if (val.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Please Enter Tool Quantity.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Tool Quantity.",
                                                          null));
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

    /*--------------------------------Copy Previous Link ACtion------------------------*/
    public void copyPreviousGen(ActionEvent actionEvent) {
        ADFUtils.findOperation("copyPreviousFunc").execute();
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(null);
    }

    /*---------------------Tool Quantity Value change listner-------------------*/
    public void toolQtyVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && this.toolPriceBind.getValue() != null) {
            Number one = (Number) vce.getNewValue();
            //Number one = (Number) this.toolPriceBind.getValue();
            Number two = (Number) this.toolPriceBind.getValue();
            System.out.println("One :" + one + "Two :" + two);
            Number three = one.multiply(two);
            System.out.println("Multiply :" + three);
            this.toolTotalAmtBind.setValue(three);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolTotalAmtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolPanelBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tooltblbind);
        }
    }

    /*----------------------------Work center Checkbox for Default------------------*/
    public void defaultWcVCL(ValueChangeEvent valueChangeEvent) {
        ADFUtils.findOperation("SelectDefaultWorkCenter").execute();
    }

    public void ToolPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Tool Price.",
                                                          null));
        }
    }

    public void setToolAndEquipmntTab(RichShowDetailItem toolAndEquipmntTab) {
        this.toolAndEquipmntTab = toolAndEquipmntTab;
    }

    public RichShowDetailItem getToolAndEquipmntTab() {
        return toolAndEquipmntTab;
    }
//This is not in use....
    public void subContractVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("Valur Change Listener is invoked");
        DCIteratorBinding jciter = ADFUtils.findIterator("MnfOp1Iterator");
        
        System.out.println("val to be chne is : " + contractTypBind.getValue());
        adfLog.info("Value of checkBox::"+contractTypBind.isSelected());
        /* System.out.println("val is :" + def);
        if(def.equalsIgnoreCase("N")) {
            adfLog.info("in the iff block..");
            jciter.getCurrentRow().setAttribute("OpScType",null); 
            bindSubContractNm.setRequired(true);
            bindSubContractNm.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
        }else {
            adfLog.info("in the else block...");
            bindSubContractNm.setRequired(false);
            bindSubContractNm.setDisabled(true);
        } */
        if(contractTypBind.isSelected()){
        // if ("true".equals(contractTypBind.getValue().toString())) {
            System.out.println("if is called::");
            //bindSubContractNm.setRequired(true);
           // bindSubContractNm.setDisabled(false);
           //jciter.getCurrentRow().setAttribute("OpScType",null); 
            //jciter.getCurrentRow().setAttribute("OpOutsrcFlg",'Y'); 
            setDisclosedFirstTab(showDetailSubContractorTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailSubContractorTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
        } else {
           System.out.println("else is called:");
            //bindSubContractNm.setRequired(false);
            //bindSubContractNm.setValue(" ");
            jciter.getCurrentRow().setAttribute("OpScType",null); 
            //jciter.getCurrentRow().setAttribute("OpOutsrcFlg",'N'); 
            //bindSubContractNm.setDisabled(true);
           
            setDisclosedFirstTab(toolAndEquipmntTab);
             AdfFacesContext.getCurrentInstance().addPartialTarget(toolAndEquipmntTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
       //}
        }
    }

    public void defaultOpVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        ADFUtils.findOperation("SelectDefaultSubContractor").execute();
    }

    public void subContractTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Integer sctype=Integer.parseInt(valueChangeEvent.getNewValue().toString());
        if (sctype==149) {
                    setDisclosedFirstTab(showDetailSubContractorTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailSubContractorTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                } else {
                    setDisclosedFirstTab(toolAndEquipmntTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(toolAndEquipmntTab);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                }
    }

    public void setBindSubContractNm(RichSelectOneChoice bindSubContractNm) {
        this.bindSubContractNm = bindSubContractNm;
    }

    public RichSelectOneChoice getBindSubContractNm() {
        return bindSubContractNm;
    }

    public void subContractTypeVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

    }

    public void setContractTypBind(RichSelectBooleanCheckbox contractTypBind) {
        this.contractTypBind = contractTypBind;
    }

    public RichSelectBooleanCheckbox getContractTypBind() {
        return contractTypBind;
    }

    public void outSrcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("Validator is invoked...");
        
        DCIteratorBinding jciter = ADFUtils.findIterator("MnfOp1Iterator");
        
        
        System.out.println("val to be chne is : " + contractTypBind.getValue());
        
        /* System.out.println("val is :" + def);
        if(def.equalsIgnoreCase("N")) {
            adfLog.info("in the iff block..");
            jciter.getCurrentRow().setAttribute("OpScType",null); 
            bindSubContractNm.setRequired(true);
            bindSubContractNm.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
        }else {
            adfLog.info("in the else block...");
            bindSubContractNm.setRequired(false);
            bindSubContractNm.setDisabled(true);
        } */
        if(contractTypBind.isSelected()){
        // if ("true".equals(contractTypBind.getValue().toString())) {
               adfLog.info("if block.."
                                   +contractTypBind.isSelected());
            bindSubContractNm.setRequired(false);
            bindSubContractNm.setDisabled(true);
           // jciter.getCurrentRow().setAttribute("OpScType",null); 
            jciter.getCurrentRow().setAttribute("OpOutsrcFlg",'Y'); 
            setDisclosedFirstTab(toolAndEquipmntTab);//toolAndEquipmntTab
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolAndEquipmntTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
        } else {
                    adfLog.info("else block"
                                       +contractTypBind.isSelected());
            bindSubContractNm.setRequired(true);
            jciter.getCurrentRow().setAttribute("OpScType",null); 
            jciter.getCurrentRow().setAttribute("OpOutsrcFlg",'N'); 
            bindSubContractNm.setDisabled(false);
            setDisclosedFirstTab(showDetailSubContractorTab);
             AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailSubContractorTab);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
        //}
        }

    }

    public void setUpTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        
        if (object != null) {
                    Integer val = (Integer) object;
        System.out.println("Number is " + val.compareTo(0));
        if (val.compareTo(0) < 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Negative numbers are not allowed.", null));
        }

    }
}

    public void toolPriceVCL(ValueChangeEvent vce) {
        // Add event code here...
        
        if (vce.getNewValue() != null && this.toolPriceBind.getValue() != null) {
            //Number one = (Number) vce.getNewValue();
            Number one = (Number) this.toolQtyBind.getValue();
            Number two = (Number) this.toolPriceBind.getValue();
            System.out.println("One :" + one + "Two :" + two);
            Number three = one.multiply(two);
            System.out.println("Multiply :" + three);
            this.toolTotalAmtBind.setValue(three);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolTotalAmtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolPanelBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tooltblbind);
        }
    }

    public void setToolQtyBind(RichInputText toolQtyBind) {
        this.toolQtyBind = toolQtyBind;
    }

    public RichInputText getToolQtyBind() {
        return toolQtyBind;
    }

    public void setTooltblbind(RichTable tooltblbind) {
        this.tooltblbind = tooltblbind;
    }

    public RichTable getTooltblbind() {
        return tooltblbind;
    }
    
    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
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
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
}
