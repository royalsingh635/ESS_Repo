package mnfshopfloorfeedback.view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

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
import javax.faces.validator.ValidatorException;

import mnfshopfloorfeedback.model.views.MnfFdbkAttchVORowImpl;

import mnfshopfloorfeedback.view.Utils.ADFUtils;

import mnfshopfloorfeedback.view.Utils.FdbkAttachDocument;

import mnfshopfloorfeedback.view.Utils.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputFile;

import oracle.jbo.domain.Number;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class shopFloorFeedbackBean {
    OperationBinding ob = null;
    Boolean paramForm = false;
    private List<UploadedFile> uploadedFile;
    private RichInputFile dispFileNameBind;
    private String fdbkMode = resolvEl("#{pageFlowScope.fdbkMode}").toString();
    private String fdbkEditMode = "NE";
    private String _DirPath;
    private String nm;
    private Boolean parameterFlag = false;
    private RichSelectOneChoice fdbkBasisBindVar;
    private RichInputListOfValues jobCardIdBind;
    private RichInputListOfValues routeCardIdBind;
    private RichInputText paramValueBind;
    private RichInputListOfValues paramNameBind;
    private RichSelectOneChoice fdbkTypeBindVar;
    private RichOutputText filePathBinding;
    private RichInputText fileNameBind;
    private Boolean addParamClicked = false;
    private RichPanelTabbed detailTabBinding;
    private RichPanelFormLayout paramFormBinding;
    private RichTable parameterTableBind;
    private RichLink addParamBtnBindVar;
    private RichInputText maxTolBindVar;
    private RichInputText minTolBindVar;
    private RichInputText maxValBindVar;
    private RichInputText minValBindVar;
    private RichSelectBooleanCheckbox tolPercBindVar;
    private RichInputComboboxListOfValues valueTypeBindVar;
    private RichLink shadowLinkBindVar;
    private RichSelectOneChoice valueTypeBind;
    private RichInputText uomBindVar;
    private RichTable imgTable;

    public void setAddParamClicked(Boolean addParamClicked) {
        this.addParamClicked = addParamClicked;
    }

    public Boolean getAddParamClicked() {
        return addParamClicked;
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    public shopFloorFeedbackBean() {
    }

    /**
     * Method to Add Parameter Action Event
     * **/
    public void AddFdbkParamActionListener(ActionEvent actionEvent) {
        this.setAddParamClicked(true);
        setParamForm(true);
        ADFUtils.findOperation("CreateWithParamsFdbkParam").execute();
        setParameterFlag(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramNameBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramValueBind);
    }

    public void setParameterFlag(Boolean parameterFlag) {
        this.parameterFlag = parameterFlag;
    }

    public Boolean getParameterFlag() {
        return parameterFlag;
    }

    public void setFdbkMode(String fdbkMode) {
        this.fdbkMode = fdbkMode;
    }

    public String getFdbkMode() {
        return fdbkMode;
    }

    public void setFdbkEditMode(String fdbkEditMode) {
        this.fdbkEditMode = fdbkEditMode;
    }

    public String getFdbkEditMode() {
        return fdbkEditMode;
    }

    public void setParamForm(Boolean paramForm) {
        this.paramForm = paramForm;
    }

    public Boolean getParamForm() {
        return paramForm;
    }

    /**
     * Method to cancel button to get back from the Create Mode
     * **/
    public void CancelFdbkActionListener(ActionEvent actionEvent) {
        ADFUtils.findOperation("RollbackMainFdbkForm").execute();
    }

    /**
     * Method to Save the form in create mode
     * **/
    public void SaveBtnActionListener(ActionEvent actionEvent) {
        if (Integer.parseInt(this.getFdbkBasisBindVar().getValue().toString()) == 33 &&
            this.getJobCardIdBind().getValue() == null) {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getJobCardIdBind().getClientId());
        } else if (Integer.parseInt(this.getFdbkBasisBindVar().getValue().toString()) == 34 &&
                   this.getRouteCardIdBind().getValue() == null) {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getRouteCardIdBind().getClientId());
        } else {
            ADFUtils.findOperation("Commit").execute();
            setFdbkMode("V");
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void AddFile(UploadedFile file) throws IOException {
    }

    public void setDispFileNameBind(RichInputFile dispFileNameBind) {
        this.dispFileNameBind = dispFileNameBind;
    }

    public RichInputFile getDispFileNameBind() {
        return dispFileNameBind;
    }

    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    private Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    public void setFdbkBasisBindVar(RichSelectOneChoice fdbkBasisBindVar) {
        this.fdbkBasisBindVar = fdbkBasisBindVar;
    }

    public RichSelectOneChoice getFdbkBasisBindVar() {
        return fdbkBasisBindVar;
    }

    public void JobCardidValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    }

    public void routeCardIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    }

    /**
     * Method to validate Reference Feedback Id Selection
     * **/
    public void referenceFdbkIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (this.getFdbkTypeBindVar().getValue().toString() == null ||
            ("").equals(this.getFdbkTypeBindVar().getValue().toString())) {
            String s = this.getRouteCardIdBind().getValue().toString();
            if (s != null && !("").equals(s)) {
                System.out.println("LOV Value is  :  " + s);
            } else {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void setFdbkTypeBindVar(RichSelectOneChoice fdbkTypeBindVar) {
        this.fdbkTypeBindVar = fdbkTypeBindVar;
    }

    public RichSelectOneChoice getFdbkTypeBindVar() {
        return fdbkTypeBindVar;
    }

    /**
     * Method to Edit particular Feedback
     * **/
    public void EditBtnActionListener(ActionEvent actionEvent) {
        this.setAddParamClicked(false);
        setFdbkMode("ED");
        setFdbkEditMode("ED");
    }

    /**
     * Method to Create a new Feedback
     * **/
    public void addActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("CreateWithParams");
        ob.execute();
        setFdbkMode("C");
        //setFdbkEditMode("ED");
    }

    public void setJobCardIdBind(RichInputListOfValues jobCardIdBind) {
        this.jobCardIdBind = jobCardIdBind;
    }

    public RichInputListOfValues getJobCardIdBind() {
        return jobCardIdBind;
    }

    public void setRouteCardIdBind(RichInputListOfValues routeCardIdBind) {
        this.routeCardIdBind = routeCardIdBind;
    }

    public RichInputListOfValues getRouteCardIdBind() {
        return routeCardIdBind;
    }

    /**
     * Method to validate Precision of Number field
     * **/
    public void isPrecisionValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            Boolean isValid = checkPrecisionValid(26, 6, value);
            if (isValid == false) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1107']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    /**
     * Method to validate number field precision
     * **/
    public Boolean checkPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }

    public void setParamValueBind(RichInputText paramValueBind) {
        this.paramValueBind = paramValueBind;
    }

    public RichInputText getParamValueBind() {
        return paramValueBind;
    }

    /**
     * Method used to Copy Data from Previous Feedback
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void referenceFeddbackValueChangeListener(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ob = ADFUtils.findOperation("enableReservedMode");
        ob.execute();
        ob = ADFUtils.findOperation("getReferenceFeedback");
        ob.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailTabBinding);
    }

    /**
     * Method to validate duplicate Parameter Selected
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void paramNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null){
            String value = (String)object;
            System.out.println("Value is : "+ value);
            int flag = 1;
            if (value != null) {
                System.out.println("If");
                OperationBinding binding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkNmDuplicate");
                binding.getParamsMap().put("val",value);
                binding.execute();
                flag = (Integer)binding.getResult();
                System.out.println("In Bean"+flag);
                if(flag == 1){
                    showFacesMessage("Duplicate Value.", "E", false, "V", null);
            }
            }
        }else{
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }     
        /* if (object != null) {
            ob = ADFUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "PARAM");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1352']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } */
    }

    /**
     * Method to validate Duplicacy in VO row
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {
        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        Integer countVal = 0;

        for (Row r : dcIter.getAllRowsInRange()) {
            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        Row currentRow = dcIter.getCurrentRow();
        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }
        return countVal == 1 ? true : false;
    }

    public void setParamNameBind(RichInputListOfValues paramNameBind) {
        this.paramNameBind = paramNameBind;
    }

    public RichInputListOfValues getParamNameBind() {
        return paramNameBind;
    }

    public void fdbkBasisValueChangeListener(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(vce.getComponent().getParent());
    }

    public void fdbkTypeValueChangeActionListener(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(vce.getComponent().getParent());
    }

    /**
     * Method to delete file added
     * **/
    public void fileDeleteActionListener(ActionEvent actionEvent) {
        String path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        System.out.println("File Path : "+path);
        // String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        //System.out.println("Key Value : "+rowKey);
        OperationBinding binding =
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteAttachFileRow");
        binding.getParamsMap().put("path", path);
        binding.execute();
        //ADFUtils.findOperation("DeleteAttach").execute();
        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("ExecuteAtt").execute();
    }

    /**
     * Method to add Document
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void FileAddActionListener(ActionEvent actionEvent) {
        OperationBinding op = null;
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
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


                    InputStream in = files.get(i).getInputStream();
                    FileOutputStream out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    in.close();
                    out.close();

                    //call commit after checking all validations

                    ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        //remove the files to prepare for new uploads
        setUploadedFile(null);
    }

    public void downloadFileActionListener(ActionEvent actionEvent) throws FileNotFoundException, IOException {
    }

    /**
     * Method to get the added file downloaded
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
    public void downloadActionListener(FacesContext facesContext, OutputStream outputStream) throws IOException {
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

    public void setFilePathBinding(RichOutputText filePathBinding) {
        this.filePathBinding = filePathBinding;
    }

    public RichOutputText getFilePathBinding() {
        return filePathBinding;
    }

    public void setFileNameBind(RichInputText fileNameBind) {
        this.fileNameBind = fileNameBind;
    }

    public RichInputText getFileNameBind() {
        return fileNameBind;
    }

    /**
     * Method to validate the selection of parameter set
     * **/
    public void paramSetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    public void setDetailTabBinding(RichPanelTabbed detailTabBinding) {
        this.detailTabBinding = detailTabBinding;
    }

    public RichPanelTabbed getDetailTabBinding() {
        return detailTabBinding;
    }

    public void paramDeleteActionListener(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("delKey");
        System.out.println("Key is : " + key);
        RowSetIterator rSetIter = ADFUtils.findIterator("MnfFdbkParamIterator").getRowSetIterator();
        rSetIter.setCurrentRow(rSetIter.getRow(key));
        rSetIter.removeCurrentRow();
        this.setAddParamClicked(false);
    }

    public void setParamFormBinding(RichPanelFormLayout paramFormBinding) {
        this.paramFormBinding = paramFormBinding;
    }

    public RichPanelFormLayout getParamFormBinding() {
        return paramFormBinding;
    }

    public void paramNameValueChangeListener(ValueChangeEvent valueChangeEvent) {
        /*  String value = valueChangeEvent.getNewValue().toString();
        System.out.println("Value is : "+ value);
        int flag = 1;
        if (value != null) {
            System.out.println("If");
            OperationBinding binding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkNmDuplicate");
            binding.getParamsMap().put("val",value);
            binding.execute();
            flag = (Integer)binding.getResult();
            System.out.println("In Bean"+flag);
        }
        if(flag==0){ */
        ActionEvent a = new ActionEvent(shadowLinkBindVar);
        a.queue();
        callInShadow(a);
        AdfFacesContext.getCurrentInstance().addPartialTarget(parameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addParamBtnBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueTypeBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tolPercBindVar);
        /* }else{
        //paramNameBind.setValue("");
         showFacesMessage("Duplicate Value.", "E", false, "F", null);//paramNameBind.getClientId()
        } */
    }

    public void paramSetValueChangeListener(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(parameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addParamBtnBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramNameBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramValueBind);
    }

    public void setParameterTableBind(RichTable parameterTableBind) {
        this.parameterTableBind = parameterTableBind;
    }

    public RichTable getParameterTableBind() {
        return parameterTableBind;
    }

    public void setAddParamBtnBindVar(RichLink addParamBtnBindVar) {
        this.addParamBtnBindVar = addParamBtnBindVar;
    }

    public RichLink getAddParamBtnBindVar() {
        return addParamBtnBindVar;
    }

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
    }

    public void setMaxValBindVar(RichInputText maxValBindVar) {
        this.maxValBindVar = maxValBindVar;
    }

    public RichInputText getMaxValBindVar() {
        return maxValBindVar;
    }

    public void setMinValBindVar(RichInputText minValBindVar) {
        this.minValBindVar = minValBindVar;
    }

    public RichInputText getMinValBindVar() {
        return minValBindVar;
    }

    public void setTolPercBindVar(RichSelectBooleanCheckbox tolPercBindVar) {
        this.tolPercBindVar = tolPercBindVar;
    }

    public RichSelectBooleanCheckbox getTolPercBindVar() {
        return tolPercBindVar;
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

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void callInShadow(ActionEvent actionEvent) {
        Integer valuTyp = (Integer) valueTypeBind.getValue();
        if ((valuTyp != null) && valuTyp.equals(112)) {
            setAttrsVal("MnfFdbkParamIterator", "TlrncLower", 0);
            setAttrsVal("MnfFdbkParamIterator", "TlrncUpper", 0);
            setAttrsVal("MnfFdbkParamIterator", "TlrncType", "A");
            setAttrsVal("MnfFdbkParamIterator", "UpperLimit", 1);
            setAttrsVal("MnfFdbkParamIterator", "LowerLimit", 1);
            setAttrsVal("MnfFdbkParamIterator", "ParamVal", 1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
        }
    }

    public void setValueTypeBindVar(RichInputComboboxListOfValues valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichInputComboboxListOfValues getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void setShadowLinkBindVar(RichLink shadowLinkBindVar) {
        this.shadowLinkBindVar = shadowLinkBindVar;
    }

    public RichLink getShadowLinkBindVar() {
        return shadowLinkBindVar;
    }

    public void maxTolValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && minTolBindVar.getValue()!=null) {
            Number value = (Number) (object);
            Number minTol = (Number) (minTolBindVar.getValue());
            Number zero = new Number(0);
            Number hundred = new Number(100);
            Boolean isValid = checkPrecisionValid(26, 6, value);
            Boolean isPerc = checkPrecisionValid(5, 2, value);

            if (value.compareTo(minTol) == -1) {
                FacesMessage message = new FacesMessage("Min Tolerance must be lesser than Max Tolerance");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } 
            
            if (tolPercBindVar.isSelected()) {
                if (isPerc == false || value.compareTo(zero) < 0 || value.compareTo(hundred) > 0) {
                    FacesMessage message = new FacesMessage("Enter value in between 0 to 100 with Precision upto 2.2 digit only!");
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

    public void minTolValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);
            Number maxTol = (Number) (maxTolBindVar.getValue());
            Number zero = new Number(0);
            Number hundred = new Number(100);
            Boolean isValid = checkPrecisionValid(26, 6, value);
            Boolean isPerc = checkPrecisionValid(5, 2, value);

            if (tolPercBindVar.isSelected()) {
                if (isPerc == false || value.compareTo(zero) < 0 || value.compareTo(hundred) > 0) {
                    FacesMessage message = new FacesMessage("Enter value in between 0 to 100 with Precision upto 2.2 digit only!");
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

    public void paramValVCE(ValueChangeEvent valueChangeEvent) {
        Integer c = (Integer) valueTypeBind.getValue();
        if (c != null && c == 111) {
            Number a = (Number) paramValueBind.getValue();
            Number e = (Number) paramValueBind.getValue();

            Number b = (Number) minTolBindVar.getValue();
            Number d = (Number) maxTolBindVar.getValue();

            if (a != null && b != null && d != null) {
                if (tolPercBindVar.isSelected()) {
                    b = a.multiply(b.divide(new Number(100)));
                    d = a.multiply(d.divide(new Number(100)));
                }
                Number f = a.subtract(b);
                Number g = e.add(d);
                setAttrsVal("MnfFdbkParamIterator", "UpperLimit", g);
                setAttrsVal("MnfFdbkParamIterator", "LowerLimit", f);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
    }

    public void tolPercVCE(ValueChangeEvent valueChangeEvent) {
        Integer c = (Integer) valueTypeBind.getValue();
        if (c != null && c == 111) {

            Number a = (Number) paramValueBind.getValue();
            Number e = (Number) paramValueBind.getValue();

            Number b = (Number) minTolBindVar.getValue();
            Number d = (Number) maxTolBindVar.getValue();

            if (a != null && b != null && d != null) {
                if (tolPercBindVar.isSelected()) {
                    b = a.multiply(b.divide(new Number(100)));
                    d = a.multiply(d.divide(new Number(100)));

                }
                Number f = a.subtract(b);
                Number g = e.add(d);

                setAttrsVal("MnfFdbkParamIterator", "UpperLimit", g);
                setAttrsVal("MnfFdbkParamIterator", "LowerLimit", f);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
    }

    public void maxTolVCE(ValueChangeEvent valueChangeEvent) {
        Integer c = (Integer) valueTypeBind.getValue();
        if (c != null && c == 111) {
            Number a = (Number) paramValueBind.getValue();
            Number b = (Number) maxTolBindVar.getValue();
            if (a != null && b != null) {
                if (tolPercBindVar.isSelected()) {
                    b = a.multiply(b.divide(new Number(100)));
                    System.out.println(b + "  dcf");
                }
                Number f = a.add(b);
                setAttrsVal("MnfFdbkParamIterator", "UpperLimit", f);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
        }
    }

    public void minTolVCE(ValueChangeEvent valueChangeEvent) {
        Integer c = (Integer) valueTypeBind.getValue();
        if (c != null && c == 111) {

            Number a = (Number) paramValueBind.getValue();
            Number b = (Number) minTolBindVar.getValue();
            if (a != null && b != null) {
                if (tolPercBindVar.isSelected()) {
                    b = a.multiply(b.divide(new Number(100)));
                    System.out.println(b + "  dfd");
                }
                Number f = a.subtract(b);

                setAttrsVal("MnfFdbkParamIterator", "LowerLimit", f);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }
    }

    public void setValueTypeBind(RichSelectOneChoice valueTypeBind) {
        this.valueTypeBind = valueTypeBind;
    }

    public RichSelectOneChoice getValueTypeBind() {
        return valueTypeBind;
    }

    public void setUomBindVar(RichInputText uomBindVar) {
        this.uomBindVar = uomBindVar;
    }

    public RichInputText getUomBindVar() {
        return uomBindVar;
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
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
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

    public void setImgTable(RichTable imgTable) {
        this.imgTable = imgTable;
    }

    public RichTable getImgTable() {
        return imgTable;
    }
}

