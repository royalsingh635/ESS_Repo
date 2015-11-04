package svcservicereqregistration.view.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ServiceReqRegistrationBean {
    private static ADFLogger _log = (ADFLogger) ADFLogger.createADFLogger(ServiceReqRegistrationBean.class);
    private RichTable defctTableBind;
    private String mode = modeGet();
    private RichInputDate callDateBind;
    private List<UploadedFile> uploadedFile;
    private RichInputText fileBindName;
    private RichSelectOneChoice bindCallStat;
    private RichPopup showLocRefId;
    private RichInputText itmSrNoBind;
    private RichInputListOfValues itmSrNoScBind;

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }


    public ServiceReqRegistrationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String modeGet() {
        return resolvEl("#{pageFlowScope.Add_Edit_Mode}").toString();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String createCmAction() {
        setMode("A");
        return "createServReqReq";
    }

    public void editAction(ActionEvent actionEvent) {
        setMode("E");
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public void ServiceReqAddAction(ActionEvent actionEvent) {

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

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void defectItmAdd(ActionEvent actionEvent) {
        OperationBinding checkSup = getBindings().getOperationBinding("chkSupplierNm");
        checkSup.execute();
        String retR = "N";
        OperationBinding obind = getBindings().getOperationBinding("duplicateItmNameChk");
        obind.getParamsMap().put("ItmName", "p");
        obind.execute();
        if (obind.getResult() != null) {
            String ItmNm = obind.getResult().toString();
            _log.info("current detail is " + ItmNm);
            if (ItmNm.equalsIgnoreCase("Y")) {

            } else if (ItmNm.equalsIgnoreCase("N")) {
                // String msg="Duplicate Record found.Another call is pending for this item";
                String msg = resolvElDCMsg("#{bundle['MSG.2474']}").toString();

                showFacesMessage(msg, "W", false, "F", null);
                return;
            }
        }

        if (checkSup.getResult() != null) {
            retR = checkSup.getResult().toString();
        }
        if ("W".equalsIgnoreCase(retR)) {
            // String msg="Customer Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2475']}").toString();

            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("E".equalsIgnoreCase(retR)) {
            // String msg="Customer Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2475']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }

        OperationBinding checkitmParm = getBindings().getOperationBinding("chkDefectItmDetails");
        checkitmParm.execute();
        String ret = "N";
        if (checkitmParm.getResult() != null) {
            ret = checkitmParm.getResult().toString();
        }
        if ("C".equalsIgnoreCase(ret)) {
            // String msg="Contract No is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2476']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("I".equalsIgnoreCase(ret)) {
            // String msg="Item Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.430']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("D".equalsIgnoreCase(ret)) {
            // String msg="Defect or Remarks is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2477']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("Y".equalsIgnoreCase(ret)) {
            // String msg="Duplicate Record found.";
            String msg = resolvElDCMsg("#{bundle['MSG.46']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        Integer tableCount = 0;
        Integer tableR = defctTableBind.getRowCount();
        tableCount = tableR + 1;
        OperationBinding addDefcteditm = getBindings().getOperationBinding("addItmDefectDetails");
        addDefcteditm.getParamsMap().put("count", tableCount);
        addDefcteditm.execute();

    }

    public void setDefctTableBind(RichTable defctTableBind) {
        this.defctTableBind = defctTableBind;
    }

    public RichTable getDefctTableBind() {
        return defctTableBind;
    }

    public void saveAction(ActionEvent actionEvent) {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String tableNm = "SVC$CM";
        String tableNmFTkt = "SVC$CM_TKT";
        if ("A".equalsIgnoreCase(mode)) {
            Integer fyNo = 0;
            OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
            fyIdOp.getParamsMap().put("CldId", p_cldId);
            fyIdOp.getParamsMap().put("OrgId", p_org_id);
            //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
            fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
            fyIdOp.getParamsMap().put("Mode", "A");
            fyIdOp.execute();
            if (fyIdOp.getResult() != null) {
                fyNo = Integer.parseInt(fyIdOp.getResult().toString());
            }
            _log.info("Fy id in bean ---------" + fyNo);
            OperationBinding callNoOp = getBindings().getOperationBinding("genCallNo");
            callNoOp.getParamsMap().put("SlocId", p_sloc_id);
            callNoOp.getParamsMap().put("CldId", p_cldId);
            callNoOp.getParamsMap().put("HoOrgId", p_hoOrgId);
            callNoOp.getParamsMap().put("OrgId", p_org_id);
            callNoOp.getParamsMap().put("TableName", tableNm);
            callNoOp.getParamsMap().put("fyId", fyNo);
            callNoOp.execute();
            String ids = null;
            if (callNoOp.getResult() != null) {
                ids = callNoOp.getResult().toString();
                _log.info("new generated issue id " + ids);
            }
        }

        OperationBinding isAssgndOp = getBindings().getOperationBinding("isStatusAssigned");
        isAssgndOp.execute();
        if (isAssgndOp.getResult() != null) {
            Integer ret = Integer.parseInt(isAssgndOp.getResult().toString());
            if (ret.compareTo(new Integer(1)) == 0) {
                OperationBinding updateStatusOp = getBindings().getOperationBinding("updateStatusAssigned");
                updateStatusOp.execute();
            }
        }
        OperationBinding isPhyOp = getBindings().getOperationBinding("updateStatusPhysical");
        isPhyOp.execute();
        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        obCommit.execute();
        setMode("V");
        // String msg="Service Request Save Successfully.";
        String msg = resolvElDCMsg("#{bundle['MSG.2478']}").toString();
        showFacesMessage(msg, "I", false, "F", null);
    }


    public String cancelAction() {
        OperationBinding obRollback = getBindings().getOperationBinding("Rollback");
        obRollback.execute();
        setMode(" ");
        return "backToSearch";
    }

    public void setCallDateBind(RichInputDate callDateBind) {
        this.callDateBind = callDateBind;
    }

    public RichInputDate getCallDateBind() {
        return callDateBind;
    }

    public void populateItmForAssignAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void assignDefectToEmpAction(ActionEvent actionEvent) {
        OperationBinding checkitmParm = getBindings().getOperationBinding("chkDefectAssignToEmp");
        checkitmParm.execute();
        String ret = "N";
        if (checkitmParm.getResult() != null) {
            ret = checkitmParm.getResult().toString();
        }
        _log.info("ret:::::::" + ret);
        if ("R".equalsIgnoreCase(ret)) {
            // String msg="Department Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2479']}").toString();
            showFacesMessage(msg, "E", false, "F", null);
            return;
        }
        if ("E".equalsIgnoreCase(ret)) {
            // String msg="Employee Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2480']}").toString();
            showFacesMessage(msg, "E", false, "F", null);
            return;
        }
        if ("D".equalsIgnoreCase(ret)) {
            // String msg="Duplicate Record Found";
            String msg = resolvElDCMsg("#{bundle['MSG.46']}").toString();
            showFacesMessage(msg, "E", false, "F", null);
            return;
        }

        OperationBinding assgnDefctT = getBindings().getOperationBinding("addDefectAssignDetails");
        assgnDefctT.execute();
    }

    public void addItmForPhysicalVerAction(ActionEvent actionEvent) {
        OperationBinding checkitmParm = getBindings().getOperationBinding("chkItmForPhy");
        checkitmParm.execute();
        String ret = "N";
        if (checkitmParm.getResult() != null) {
            ret = checkitmParm.getResult().toString();
        }
        if ("I".equalsIgnoreCase(ret)) {
            // String msg="Item Name is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.430']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("D".equalsIgnoreCase(ret)) {
            // String msg="Duplicate Item Name found.";
            String msg = resolvElDCMsg("#{bundle['MSG.2481']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        OperationBinding addItm = getBindings().getOperationBinding("addItem");
        addItm.execute();

    }

    public void populateDataFrmDfctAction(ActionEvent actionEvent) {
        OperationBinding pDefctToItm = getBindings().getOperationBinding("populateDatefrmDfct");
        pDefctToItm.execute();
        if (pDefctToItm.getResult() != null) {
            _log.info(" return value from populate  :  " + pDefctToItm.getResult());
        }
    }

    public void addDefectDetails(ActionEvent actionEvent) {

        OperationBinding checkitmParm = getBindings().getOperationBinding("chkphysicalDefect");
        checkitmParm.execute();
        String ret = "N";
        if (checkitmParm.getResult() != null) {
            ret = checkitmParm.getResult().toString();
        }
        if ("E".equalsIgnoreCase(ret)) {
            // String msg="Employee Name is Required";
            String msg = resolvElDCMsg("#{bundle['MSG.2480']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("D".equalsIgnoreCase(ret)) {
            // String msg="Defect is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2482']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("S".equalsIgnoreCase(ret)) {
            // String msg="Duplicate Defect Found";
            String msg = resolvElDCMsg("#{bundle['MSG.2483']}").toString();
            showFacesMessage(msg, "E", false, "F", null);
            return;
        }
        OperationBinding assgnDefctT = getBindings().getOperationBinding("addPhysicalDefect");
        assgnDefctT.execute();
    }

    public void addDefectRecords(ActionEvent actionEvent) {
        OperationBinding checkitmParm = getBindings().getOperationBinding("chkPhysicalDefectRecords");
        checkitmParm.execute();
        String ret = "N";
        if (checkitmParm.getResult() != null) {
            ret = checkitmParm.getResult().toString();
        }
        if ("E".equalsIgnoreCase(ret)) {
            // String msg="Employee Name is Required";
            String msg = resolvElDCMsg("#{bundle['MSG.2480']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("D".equalsIgnoreCase(ret)) {
            // String msg="Defect is Required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2482']}").toString();
            showFacesMessage(msg, "W", false, "F", null);
            return;
        }
        if ("S".equalsIgnoreCase(ret)) {
            // String msg="Duplicate Defect Found";
            String msg = resolvElDCMsg("#{bundle['MSG.2483']}").toString();
            showFacesMessage(msg, "E", false, "F", null);
            return;
        }
        OperationBinding addDefctT = getBindings().getOperationBinding("addPhysicalDefectRecords");
        addDefctT.execute();

    }

    public void generateTicketNo(ActionEvent actionEvent) {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String tableNmFTkt = "SVC$CM_TKT";
        OperationBinding tktNoOp = getBindings().getOperationBinding("genTktNo");
        tktNoOp.getParamsMap().put("SlocId", p_sloc_id);
        tktNoOp.getParamsMap().put("CldId", p_cldId);
        tktNoOp.getParamsMap().put("HoOrgId", p_hoOrgId);
        tktNoOp.getParamsMap().put("OrgId", p_org_id);
        tktNoOp.getParamsMap().put("TableName", tableNmFTkt);
        tktNoOp.execute();
        String ids = null;
        if (tktNoOp.getResult() != null) {
            ids = tktNoOp.getResult().toString();
            _log.info("new generated issue id " + ids);

        }
        Integer stat1 = (Integer) getBindCallStat().getValue();
        _log.info("Value of DocStat::" + stat1);
        //if(stat1 !=42||stat1!=43||stat1!=48){
        //_log.info(" in the ticket generate block");
        //        OperationBinding status = getBindings().getOperationBinding("updateInsIntoCallTrace");
        //      status.getParamsMap().put("docstat",43);
        //        status.execute();
        // }
        OperationBinding obCommit = getBindings().getOperationBinding("Commit");
        obCommit.execute();
    }

    public void budgetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amt = (Number) object;
            if (amt.compareTo(new Number(0)) == -1) {
                // String msg="Budget must be positive."
                String msg = resolvElDCMsg("#{bundle['MSG.2484']}").toString();
                showFacesMessage(msg, "E", false, "V", null);
            }
        }

    }

    /**
     * Method to save uploaded files in file system and DB tables
     * **/
    public String uploadFiles() throws Exception {
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
                    OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                    //  op = ADFUtils.findOperation("createAttchmntRow");
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
                        // out.flush();
                    }
                    out.flush();

                    //call commit after checking all validations
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    ///   ADFUtils.findOperation("Commit").execute();
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
        OperationBinding obExecute = getBindings().getOperationBinding("Execute");
        obExecute.execute();
        //ADFUtils.findOperation("Execute5").execute();
        return "null";
    }


    /**Method to download file from actual path
     * @param facesContext
     * @param outputStream
     */
    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) {
        //Read file from particular path, path bind is binding of table field that contains path
        //File filed = new File(pathPgBind.getValue().toString());
        // String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
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
                //    JSFUtils.addFacesErrorMessage("Problem in downloading a file");
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
    }

    public void DeleteAttachmentAL(ActionEvent actionEvent) {
        String path = null;

        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null) {
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();

            System.out.println("File path in AMimpl" + path);
            File f = new File(path);
            try {
                boolean success = f.delete();
                if (success) {
                    System.out.println("File Deleted");
                    OperationBinding obDelete = getBindings().getOperationBinding("Delete");
                    obDelete.execute();
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    OperationBinding obExecute = getBindings().getOperationBinding("Execute");
                    obExecute.execute();
                    /*  ADFUtils.findOperation("Delete").execute();
                    ADFUtils.findOperation("Commit").execute();
                    ADFUtils.findOperation("Execute5").execute(); */
                }
            } catch (Exception x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            }
        }
    }


    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }


    public String getPhysicalChBDisable() {
        String ret = "N";
        OperationBinding obDelete = getBindings().getOperationBinding("isPhysicalChkBox");
        obDelete.execute();
        if (obDelete.getResult() != null) {
            ret = obDelete.getResult().toString();
        }

        return ret;
    }

    public void deleteAsgnACTION(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding del = getBindings().getOperationBinding("Delete1");
        del.execute();
        OperationBinding del1 = getBindings().getOperationBinding("Execute1");
        del1.execute();

    }

    public void setBindCallStat(RichSelectOneChoice bindCallStat) {
        this.bindCallStat = bindCallStat;
    }

    public RichSelectOneChoice getBindCallStat() {
        return bindCallStat;
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedAddsDesc");
        binding.getParamsMap().put("addsStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }

    public void locRefDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding obind = getBindings().getOperationBinding("addlocRefIdAndCstmr");
            obind.execute();
        }
    }

    public void AddLocationRefId(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("filterLocationRefId");
        obind.execute();
        showPopup(showLocRefId, true);
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

    public void setShowLocRefId(RichPopup showLocRefId) {
        this.showLocRefId = showLocRefId;
    }

    public RichPopup getShowLocRefId() {
        return showLocRefId;
    }

    public void ItmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /*  OperationBinding obind = getBindings().getOperationBinding("duplicateItmNameChk");
            obind.getParamsMap().put("ItmName", object);
            obind.execute();
            if (obind.getResult() != null) {
                String ItmNm = obind.getResult().toString();
                if (ItmNm.equalsIgnoreCase("Y")) {

                } else if (ItmNm.equalsIgnoreCase("N")) {
                    showFacesMessage("Dupliate Item name found ", "E", false, "V", null);
                }
            } */



        }


    }

    public void deleteSrNoAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding obDelete = getBindings().getOperationBinding("Delete2");
        obDelete.execute();
        OperationBinding obExecute = getBindings().getOperationBinding("Execute2");
        obExecute.execute();

    }

    public void AddItmSerialAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding obind = getBindings().getOperationBinding("checkSrNoEnter");
        obind.execute();
        if (obind.getResult() != null) {
            if ("Y".equalsIgnoreCase(obind.getResult().toString())) {
                // String msg="Please Select Serial No. ";
                String msg = resolvElDCMsg("#{bundle['MSG.2485']}").toString();
                showFacesMessage(msg, "E", false, "F", null);
                return;
            }
        }

        OperationBinding obDepli = getBindings().getOperationBinding("isSerialNoDuplecate");
        obDepli.execute();

        if (obDepli.getResult() != null) {
            Integer ret = (Integer) obDepli.getResult();
            if (ret.compareTo(new Integer(1)) == 0) {
                // String msg="Duplicate Serial No Found. ";
                String msg = resolvElDCMsg("#{bundle['MSG.2486']}").toString();
                showFacesMessage(msg, "E", false, "F", null);
                return;
            }
        }


        OperationBinding obInsetSr = getBindings().getOperationBinding("insSerialNo");
        obInsetSr.execute();
    }

    public void setItmSrNoBind(RichInputText itmSrNoBind) {
        this.itmSrNoBind = itmSrNoBind;
    }

    public RichInputText getItmSrNoBind() {
        return itmSrNoBind;
    }

    public void setItmSrNoScBind(RichInputListOfValues itmSrNoScBind) {
        this.itmSrNoScBind = itmSrNoScBind;
    }

    public RichInputListOfValues getItmSrNoScBind() {
        return itmSrNoScBind;
    }

    public void itmNameVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            OperationBinding checkSup = getBindings().getOperationBinding("setSrNoNull");
            checkSup.execute();
        }
    }
}
