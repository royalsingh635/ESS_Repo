package mnfworkcenterapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelAccordion;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.jbo.domain.Date;

import java.util.List;

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

import mnfworkcenterapp.model.module.MNFWorkcenterAMImpl;

import mnfworkcenterapp.model.views.MnfWcAttchVORowImpl;
import mnfworkcenterapp.model.views.MnfWcVORowImpl;

import mnfworkcenterapp.view.utils.ADFUtils;

import mnfworkcenterapp.view.utils.JSFUtils;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.util.ResetUtils;


import oracle.adfinternal.view.faces.context.AdfFacesContextImpl;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class WorkcenterAddBean {
    OperationBinding ob = null;
    private String mode = JSFUtils.resolveExpression("#{pageFlowScope.WCMOD}").toString();
    public String fMode = JSFUtils.resolveExpression("#{pageFlowScope.WCMOD}").toString();
    public String wsMode = JSFUtils.resolveExpression("#{pageFlowScope.WCMOD}").toString();
    private RichTable imgTable;

    public void setWsMode(String wsMode) {
        this.wsMode = wsMode;
    }

    public String getWsMode() {
        return wsMode;
    }
    private RichSelectOneChoice basisBind;
    private RichSelectOneChoice wcidBind;
    private RichInputText legacyBind;
    private RichSelectBooleanCheckbox activeBind;
    private RichInputDate actdDateBind;
    private RichInputText inactvResBind;
    private RichInputText descBind;

    private RichInputText remarksBind;
    private RichInputListOfValues manuFacParamBind;
    private UploadedFile _uploadFile;
    private RichInputText attachFilePathBind;
    private RichPanelSplitter panelSplitBind;
    private RichPanelSplitter empPanelSplitterBind;
    private RichPanelSplitter parameterSplitterBind;
    private RichPanelTabbed childTabBind;
    private RichOutputText bindParamType;
    private RichPanelFormLayout parameterFormBinding;
    private RichOutputText shiftNameBindVar;
    private RichTable shiftEmployeeTableBind;
    private RichTable organizationTableBind;
    private RichTable parameterTableBind;
    private RichLink addManpwerBindVar;
    private RichSelectBooleanCheckbox shiftInchargeBind;
    private RichInputListOfValues shiftDescBind;
    private RichLink addParamBindVar;
    private RichPanelAccordion generateBindvar;

    public void setWsGeneratedOnce(Boolean wsGeneratedOnce) {
        this.wsGeneratedOnce = wsGeneratedOnce;
    }

    public Boolean getWsGeneratedOnce() {
        return wsGeneratedOnce;
    }
    private RichButton generateWSBind;
    private RichLink generatedWSId;
    private RichOutputText bindattachFilePath;
    private List<UploadedFile> uploadedFile;
    private Boolean addParamClicked = false;
    private RichInputText paramValueBind;
    private RichLink replicateLinkBinding;
    private Boolean wsGeneratedOnce = false;

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

    public void setWsGenerated(Boolean wsGenerated) {
        this.wsGenerated = wsGenerated;
    }

    public Boolean getWsGenerated() {
        return wsGenerated;
    }

    private RichSelectOneChoice deptidBind;
    private RichInputListOfValues selectManufacParaBind;
    private RichInputText newWcCodeBind;
    private RichInputDate inactiveDateOrgBind;
    private RichButton copyPreviousBind;
    private RichInputText pathBind;
    private RichInputText workStationBind;
    public String workStationId;
    private RichSelectBooleanCheckbox replicateOrgBind;
    private RichSelectBooleanCheckbox orgActiveBind;
    private RichInputText inactiveReasonOrgBind;
    private RichInputListOfValues referenceWCIdBind;
    private Boolean wsGenerated = false;
    private Boolean wsGenAllow =
        ADFContext.getCurrent().getPageFlowScope().get("isWSGenAllowed").toString().equalsIgnoreCase("N") ? false :
        true;

    public void setWsGenAllow(Boolean wsGenAllow) {
        this.wsGenAllow = wsGenAllow;
    }

    public Boolean getWsGenAllow() {
        return wsGenAllow;
    }

    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }

    public String getWorkStationId() {
        return workStationId;
    }

    public void setUploadFile(UploadedFile _uploadFile) {
        this._uploadFile = _uploadFile;
    }

    public UploadedFile getUploadFile() {
        return _uploadFile;
    }


    private UploadedFile uploadfile;

    public void setUploadfile(UploadedFile uploadfile) {
        this.uploadfile = uploadfile;
    }

    public UploadedFile getUploadfile() {
        return uploadfile;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {

        System.out.println("Mode" + mode);
        return mode;
    }

    public WorkcenterAddBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Method to Save WorkCenters
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void saveACE(ActionEvent actionEvent) {
        if ((descBind.getValue() == null) || deptidBind.getValue() == null) {
            if ((descBind.getValue() == null)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getDescBind().getClientId());
            }
            if ((deptidBind.getValue() == null)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getDeptidBind().getClientId());
            }
            if (activeBind.getValue() != null && orgActiveBind.getValue() != null) {
                System.out.println("activeBind.getValue().toString() : " + activeBind.getValue().toString());
                System.out.println("orgActiveBind.getValue().toString() : " + orgActiveBind.getValue().toString());
            }
            if ((activeBind.getValue() != null && activeBind.getValue().toString().equalsIgnoreCase("N") &&
                 inactvResBind.getValue() == null)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getInactvResBind().getClientId());
            }
            if ((orgActiveBind.getValue() != null && orgActiveBind.getValue().toString().equalsIgnoreCase("N") &&
                 inactiveReasonOrgBind.getValue() == null)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR,
                                      this.getInactiveReasonOrgBind().getClientId());
            }
        } else {
                OperationBinding op= ADFBeanUtils.findOperation("checkDisplayIdInDatabase");
                op.execute();
                String result = op.getResult().toString();
                if(result.equals("Y")) {
                    ADFModelUtils.showFormattedFacesMessage("This Display Id has been already exist so please go back and enter again.", " ",
                                                                FacesMessage.SEVERITY_ERROR); 
                      
                }
            else {
                if (!this.getFMode().equalsIgnoreCase("E")) {
                    AdfFacesContext.getCurrentInstance().addPartialTarget(replicateLinkBinding);
                    ob = ADFUtils.findOperation("excuteFnMnfPrfOrgReplication");
                    ob.execute();
                }
                this.setFMode("V");
                this.setWsMode("V");
                ob = ADFUtils.findOperation("beforeSave");
                ob.execute();
                ob = ADFUtils.findOperation("Commit");
                Object execute = ob.execute();
                if (execute == null) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(descBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(deptidBind);
                }
            }
            
    }

    /**
     * Method to Add WorkCenter
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void addACE(ActionEvent actionEvent) {
        this.setFMode("A");
        this.setWsMode("A");
        ob = ADFUtils.findOperation("WcCreateWithParams");
        ob.execute();
    }

    /**
     * Method to Edit Existing WorkCenter
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void editACE(ActionEvent actionEvent) {
        this.setAddParamClicked(false);
        this.setFMode("E");
        this.setWsMode("E");
        AdfFacesContext.getCurrentInstance().addPartialTarget(generateWSBind);
    }

    /**
     * Method to Check the WorkCenter Basis Value
     */
    public void basisVCE(ValueChangeEvent valueChangeEvent) {
        Object obj1 = basisBind.getValue();
        int wcb = Integer.parseInt(obj1.toString());
    }

    public void setBasisBind(RichSelectOneChoice basisBind) {
        this.basisBind = basisBind;
    }

    public RichSelectOneChoice getBasisBind() {
        return basisBind;
    }

    public void setWcidBind(RichSelectOneChoice wcidBind) {
        this.wcidBind = wcidBind;
    }

    public RichSelectOneChoice getWcidBind() {
        return wcidBind;
    }

    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Method to Search WorkCenters
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void searchWcVCE(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ob = ADFUtils.findOperation("enableReservedMode");
        ob.execute();
        ob = ADFUtils.findOperation("searchWC");
        ob.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(childTabBind);
        referenceWCIdBind.setDisabled(true);
        basisBind.setDisabled(true);
    }

    public void setLegacyBind(RichInputText legacyBind) {
        this.legacyBind = legacyBind;
    }

    public RichInputText getLegacyBind() {
        return legacyBind;
    }

    public void setActiveBind(RichSelectBooleanCheckbox activeBind) {
        this.activeBind = activeBind;
    }

    public RichSelectBooleanCheckbox getActiveBind() {
        return activeBind;
    }

    public void setActdDateBind(RichInputDate actdDateBind) {
        this.actdDateBind = actdDateBind;
    }

    public RichInputDate getActdDateBind() {
        return actdDateBind;
    }

    public void setInactvResBind(RichInputText inactvResBind) {
        this.inactvResBind = inactvResBind;
    }

    public RichInputText getInactvResBind() {
        return inactvResBind;
    }

    public void setDescBind(RichInputText descBind) {
        this.descBind = descBind;
    }

    public RichInputText getDescBind() {
        return descBind;
    }


    public void setRemarksBind(RichInputText remarksBind) {
        this.remarksBind = remarksBind;
    }

    public RichInputText getRemarksBind() {
        return remarksBind;
    }

    /**
     * Method to Check WorkCenter Active
     */
    public void activeVCE(ValueChangeEvent valueChangeEvent) throws SQLException {
        System.out.println("vcl value---" + valueChangeEvent.getNewValue());
        System.out.println("binding value---" + activeBind.getValue().toString());
        if (activeBind.isSelected()) {
            System.out.println("activeVCE" + activeBind);
            inactvResBind.setRequired(false);
        } else {
            Date dte = new Date(Date.getCurrentDate());
            this.actdDateBind.setValue(new oracle.jbo.domain.Timestamp(dte));
            System.out.println("activeVCE_else" + activeBind);
            inactvResBind.setRequired(true);
        }


    }

    public void changeModeAction() {
        // Add event code here...
    }

    public void basisVCL(ValueChangeEvent valueChangeEvent) {
        Object objbas = basisBind.getValue();
        int basis = Integer.parseInt(objbas.toString());
    }

    /**
     * Method to Add parameters to WorkCenter
     */
    public void addManParamVCE(ActionEvent actionEvent) {
        this.setAddParamClicked(true);
        ADFUtils.findOperation("CreateWithParams").execute();
    }

    /**
     * Method to Delete Row from Parameter
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void deleteParamRowACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("isDeletionAllowed");
        ob.getParamsMap().put("workCenterId",
                              this.getNewWcCodeBind().getValue() != null ?
                              this.getNewWcCodeBind().getValue().toString() : "");
        ob.getParamsMap().put("del_type", "P");
        ob.execute();
        Object res = ob.getResult();
        if (res != null) {
            String flag = res.toString();
            if (flag.equalsIgnoreCase("N")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1383']}");
                StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg + "</b></p></body></html>");
                ADFUtils.showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_INFO, null);
            } else {
                Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("opKey");
                System.out.println("Key is : " + key);
                removeRow(key, "MnfWcPram1Iterator");
                this.setAddParamClicked(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(parameterFormBinding);
    }

    private static int STRING = Types.VARCHAR;

    /**
     * Method to get the File Path From Server
     */
    public String getWcAttachFilePath(int slocid) {
        return (String) callStoredFunction(STRING, "app.fn_get_app_doc_attach_path(?)", new Object[] { slocid });
    }

    /*****Generic Method to Get BindingContainer**/
    public BindingContainer getBindingsCont() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;

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

    /** helper method for executing DB function
     * @param sqlReturnType return type of database function
     * @param stmt statement of executing function in database quary
     * @param bindVars input and out parameter of database function.
     * @return
     */
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            MNFWorkcenterAMImpl am = (MNFWorkcenterAMImpl) resolvElDC("MNFWorkcenterAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e.getMessage().substring(11, end));

        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }

    public void setAttachFilePathBind(RichInputText attachFilePathBind) {
        this.attachFilePathBind = attachFilePathBind;
    }

    public RichInputText getAttachFilePathBind() {
        return attachFilePathBind;
    }

    public void addOrganisationACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateWithParamsOrg").execute();
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

    public void setPanelSplitBind(RichPanelSplitter panelSplitBind) {
        this.panelSplitBind = panelSplitBind;
    }

    public RichPanelSplitter getPanelSplitBind() {
        return panelSplitBind;
    }

    public void addEmpACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateWithParamsRes").execute();
    }

    public void setEmpPanelSplitterBind(RichPanelSplitter empPanelSplitterBind) {
        this.empPanelSplitterBind = empPanelSplitterBind;
    }

    public RichPanelSplitter getEmpPanelSplitterBind() {
        return empPanelSplitterBind;
    }

    public void setParameterSplitterBind(RichPanelSplitter parameterSplitterBind) {
        this.parameterSplitterBind = parameterSplitterBind;
    }

    public RichPanelSplitter getParameterSplitterBind() {
        return parameterSplitterBind;
    }

    public void editParameterACE(ActionEvent actionEvent) {
        parameterSplitterBind.setCollapsed(false);
    }

    /**
     * Function to delete Row from Employee VO
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void DeleteEmpRowACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("isDeletionAllowed");
        ob.getParamsMap().put("workCenterId",
                              this.getNewWcCodeBind().getValue() != null ?
                              this.getNewWcCodeBind().getValue().toString() : "");
        ob.getParamsMap().put("del_type", "M");
        ob.execute();
        Object res = ob.getResult();
        if (res != null) {
            String flag = res.toString();
            if (flag.equalsIgnoreCase("N")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1385']}");
                StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg + "</b></p></body></html>");
                ADFUtils.showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_INFO, null);
            } else {
                Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("empKey");
                System.out.println("Key is : " + key);
                removeRow(key, "MnfWcRes1Iterator");
            }
        }
    }

    /**
     * Function to delete Row from Organization VO
     */
    public void deleteOrgRowACE(ActionEvent actionEvent) {
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("orgKey");
        System.out.println("Key is : " + key);
        removeRow(key, "OrgMnfWc1Iterator");
    }

    public void editOrgnisationACE(ActionEvent actionEvent) {
        panelSplitBind.setCollapsed(false);
    }

    public void editEmployeeACE(ActionEvent actionEvent) {
        empPanelSplitterBind.setCollapsed(false);
    }


    public void setDeptidBind(RichSelectOneChoice deptidBind) {
        this.deptidBind = deptidBind;
    }

    public RichSelectOneChoice getDeptidBind() {
        return deptidBind;
    }

    public void setSelectManufacParaBind(RichInputListOfValues selectManufacParaBind) {
        this.selectManufacParaBind = selectManufacParaBind;
    }

    public RichInputListOfValues getSelectManufacParaBind() {
        return selectManufacParaBind;
    }

    public void setNewWcCodeBind(RichInputText newWcCodeBind) {
        this.newWcCodeBind = newWcCodeBind;
    }

    public RichInputText getNewWcCodeBind() {
        return newWcCodeBind;
    }

    public void setInactiveDateOrgBind(RichInputDate inactiveDateOrgBind) {
        this.inactiveDateOrgBind = inactiveDateOrgBind;
    }

    public RichInputDate getInactiveDateOrgBind() {
        return inactiveDateOrgBind;
    }

    public void orgActiveVCL(ValueChangeEvent valueChangeEvent) {
        if (orgActiveBind.isSelected()) {
        } else {
            Date dte = new Date(Date.getCurrentDate());
            this.inactiveDateOrgBind.setValue(new oracle.jbo.domain.Timestamp(dte));
        }
    }

    public void setCopyPreviousBind(RichButton copyPreviousBeind) {
        this.copyPreviousBind = copyPreviousBind;
    }

    public RichButton getCopyPreviousBind() {
        return copyPreviousBind;
    }

    public void setPathBind(RichInputText pathBind) {
        this.pathBind = pathBind;
    }

    public RichInputText getPathBind() {
        return pathBind;
    }

    public void setWorkStationBind(RichInputText workStationBind) {
        this.workStationBind = workStationBind;
    }

    public RichInputText getWorkStationBind() {
        return workStationBind;
    }

    /**
     * Function to Generate WorkStation From WorkCenter Screen
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void generateWorkstationACL(ActionEvent actionEvent) {
        this.setWsMode("D");
        AdfFacesContext.getCurrentInstance().addPartialTarget(generateWSBind);
        String cldid = JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer slocid = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoorgid = JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String orgid = JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer usrid = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());
        try {
            workStationId = this.callStoredFunction(Types.VARCHAR, "MNF.FN_MNF_GET_PRF_ID(?,?,?,?,?,?,?,?)", new Object[] {
                                                    slocid, cldid, hoorgid, hoorgid, 25505, null, "MNF$WS", 0
            }).toString();
        } catch (Exception e) {
            System.out.println("ERROR IN CALLING FUNCTION :" + e.getMessage());
        }

        ob = ADFUtils.findOperation("chkForExistingWs");
        ob.getParamsMap().put("wcId",
                              this.getNewWcCodeBind().getValue() != null ?
                              this.getNewWcCodeBind().getValue().toString() : "");
        ob.execute();
        String res = (String) ob.getResult();
        if (res.equals("Y")) {
            FacesMessage message = new FacesMessage("Default Work Station is already generated For Work Center");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            setWorkStationId(workStationId);
            ob = ADFUtils.findOperation("generateWorkStation");
            ob.getParamsMap().put("wsId", workStationId);
            ob.getParamsMap().put("wcId",
                                  this.getNewWcCodeBind().getValue() != null ?
                                  this.getNewWcCodeBind().getValue().toString() : "");
            ob.execute();

            //ADFUtils.findOperation("Commit").execute();
            String msg1 = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1355']}");
            String msg2 = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1356']}");
            System.out.println("Result Found : " + res);

            FacesMessage message = new FacesMessage(msg1 + " " + workStationId + " " + msg2);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setReplicateOrgBind(RichSelectBooleanCheckbox replicateOrgBind) {
        this.replicateOrgBind = replicateOrgBind;
    }

    public RichSelectBooleanCheckbox getReplicateOrgBind() {
        return replicateOrgBind;
    }

    public void setOrgActiveBind(RichSelectBooleanCheckbox orgActiveBind) {
        this.orgActiveBind = orgActiveBind;
    }

    public RichSelectBooleanCheckbox getOrgActiveBind() {
        return orgActiveBind;
    }

    public void setInactiveReasonOrgBind(RichInputText inactiveReasonOrgBind) {
        this.inactiveReasonOrgBind = inactiveReasonOrgBind;
    }

    public RichInputText getInactiveReasonOrgBind() {
        return inactiveReasonOrgBind;
    }

    /**
     * Function to check duplicate values
     */
    private boolean duplicateValue(String iter, String attrsNm, Object val) {
        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        dcIter.setRangeSize(-1);
        Integer countVal = 0;
        System.out.println("Before For loop " + val);
        for (Row r : dcIter.getAllRowsInRange()) {
            System.out.println("Attribute value is " + r.getAttribute(attrsNm));
            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        Row currentRow = dcIter.getCurrentRow();
        System.out.println("Current row is attribute value " + currentRow.getAttribute(attrsNm) + " ---------- " + val);
        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }
        return countVal == 1 ? true : false;
    }

    /**
     * Function to check Organization duplicacy
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void orgValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ob = ADFUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "OrgDesc");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                Boolean flag = duplicateValue("OrgMnfWc1Iterator", "OrgId", ob.getResult());
                if (flag) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1348']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    /**
     * Function to check duplicate Parameters attached
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void paramValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ob = ADFUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "Param");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                Boolean flag = duplicateValue("MnfWcPram1Iterator", "ParamId", ob.getResult());
                if (flag) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1352']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    public void setReferenceWCIdBind(RichInputListOfValues referenceWCIdBind) {
        this.referenceWCIdBind = referenceWCIdBind;
    }

    public RichInputListOfValues getReferenceWCIdBind() {
        return referenceWCIdBind;
    }

    /**
     * Function to check Employee duplicacy
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void EmployeeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    public void setGenerateWSBind(RichButton generateWSBind) {
        this.generateWSBind = generateWSBind;
    }

    public RichButton getGenerateWSBind() {
        return generateWSBind;
    }

    public void setGeneratedWSId(RichLink generatedWSId) {
        this.generatedWSId = generatedWSId;
    }

    public RichLink getGeneratedWSId() {
        return generatedWSId;
    }

    /**
     * Function to add file Listener
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void AddFileActionListener(ActionEvent actionEvent) {
        System.out.println("Inside Add File Action Listener");
        OperationBinding op = null;
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                try {
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
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
                    ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        setUploadedFile(null);
    }

    public void setBindattachFilePath(RichOutputText bindattachFilePath) {
        this.bindattachFilePath = bindattachFilePath;
    }

    public RichOutputText getBindattachFilePath() {
        return bindattachFilePath;
    }

    /**
     * Function to download attached file
     */
    public void DownloadFileActionListener(ActionEvent actionEvent) throws FileNotFoundException, IOException {
        File filed = new File(JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}"));
        OutputStream outputStream = new FileOutputStream(filed);
        ;
        FileInputStream fis;
        byte[] b;
        try {
            fis = new FileInputStream(filed);
            int n = fis.available();
            while ((n) > 0) {
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

    /**
     * Function to delete attached file
     */
    public void fileDeleteActionListener(ActionEvent actionEvent) {
        //actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Object filePath = actionEvent.getComponent().getAttributes().get("pathWithName");
        //String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        //System.out.println("Key Value : "+rowKey);
        if (filePath !=
            null) {
            //System.out.println("File Path : "+filePath.toString());
            OperationBinding binding =
    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteAttachFileRow");
            binding.getParamsMap().put("path", filePath.toString());
            binding.execute();
            // ADFUtils.findOperation("DeleteAttach").execute();
            ADFUtils.findOperation("Commit").execute();
            //ADFUtils.findOperation("ExecuteAttach").execute();
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void workCenterNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Object Value : " + object.toString());
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else {
                ob = ADFUtils.findOperation("duplicateWCName");
                ob.getParamsMap().put("wcId", this.getNewWcCodeBind().getValue().toString());
                ob.getParamsMap().put("wcName", object.toString());
                ob.execute();
                String result = ob.getResult() != null ? ob.getResult().toString() : "";
                if (result.equalsIgnoreCase("yes")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Provided Work Center name already exist!!", null));
                }
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory Field!!", null));
        }
    }

    /**
     * Function to download attached file
     */
    public void downloadActionListenerMethod(FacesContext facesContext, OutputStream outputStream) throws IOException {
        String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        System.out.println(path);
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

    /**
     * Function to check precision of Parameter Value
     */
    public void paramValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            Boolean isValid = isPrecisionValid(26, 6, value);
            Boolean isPerc = isPrecisionValid(5, 2, value);
            if (getBindParamType().getValue().toString().equalsIgnoreCase("OVERHEAD")) {
                if (isPerc == false) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.263']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }

                if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.247']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            } else if (getBindParamType().getValue().toString().equalsIgnoreCase("COSTING")) {
                if (isValid == false) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1354']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }

                if (value.compareTo(0) < 0) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1058']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }

            } else {
                if (isValid == false) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.57']}");
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);

                }
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    /**
     * Function to check Parameter Set Selection
     */
    public void paramSetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    public void setParamValueBind(RichInputText paramValueBind) {
        this.paramValueBind = paramValueBind;
    }

    public RichInputText getParamValueBind() {
        return paramValueBind;
    }

    /**
     * Function to check precision of Number fields
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**
     * Function to validate Shift selection
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void shiftValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object empId = getAttrsVal("MnfWcRes1Iterator", "WcEmpId");
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

    }

    /**
     * Function to replicate Organization to WorkCenter
     */
    public void replicateValueChangeListener(ValueChangeEvent valueChangeEvent) {
    }

    public void setReplicateLinkBinding(RichLink replicateLinkBinding) {
        this.replicateLinkBinding = replicateLinkBinding;
    }

    public RichLink getReplicateLinkBinding() {
        return replicateLinkBinding;
    }

    public void replicateActionListener(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(replicateLinkBinding);
        ob = ADFUtils.findOperation("excuteFnMnfPrfOrgReplication");
        ob.execute();
    }

    public void remarkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void inactiveReasonValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...





    }

    public void legacyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void orgInactiveReasonValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void setFMode(String fMode) {
        this.fMode = fMode;
    }

    public String getFMode() {
        return fMode;
    }

    public void setChildTabBind(RichPanelTabbed childTabBind) {
        this.childTabBind = childTabBind;
    }

    public RichPanelTabbed getChildTabBind() {
        return childTabBind;
    }

    public String cancelActionEvent() {
        generateWSBind.setVisible(true);
        return "back";
    }

    private void refreshParamValue() {
        setAttrsVal("MnfWcPram1Iterator", "ParamId", null);
        setAttrsVal("MnfWcPram1Iterator", "TransParamDesc", null);
        setAttrsVal("MnfWcPram1Iterator", "ParamType", null);
        setAttrsVal("MnfWcPram1Iterator", "ParamVal", null);
        setAttrsVal("MnfWcPram1Iterator", "transParamBasis", null);
        setAttrsVal("MnfWcPram1Iterator", "transParamUomName", null);
    }

    public void paramSetValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            refreshParamValue();
        }
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(parameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addParamBindVar);
    }

    public void setBindParamType(RichOutputText bindParamType) {
        this.bindParamType = bindParamType;
    }

    public RichOutputText getBindParamType() {
        return bindParamType;
    }

    public void setParameterFormBinding(RichPanelFormLayout parameterFormBinding) {
        this.parameterFormBinding = parameterFormBinding;
    }

    public RichPanelFormLayout getParameterFormBinding() {
        return parameterFormBinding;
    }

    public void shiftValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(shiftEmployeeTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addManpwerBindVar);

    }

    public void setShiftNameBindVar(RichOutputText shiftNameBindVar) {
        this.shiftNameBindVar = shiftNameBindVar;
    }

    public RichOutputText getShiftNameBindVar() {
        return shiftNameBindVar;
    }

    public void setShiftEmployeeTableBind(RichTable shiftEmployeeTableBind) {
        this.shiftEmployeeTableBind = shiftEmployeeTableBind;
    }

    public RichTable getShiftEmployeeTableBind() {
        return shiftEmployeeTableBind;
    }

    public void organizationValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(organizationTableBind);
    }

    public void setOrganizationTableBind(RichTable organizationTableBind) {
        this.organizationTableBind = organizationTableBind;
    }

    public RichTable getOrganizationTableBind() {
        return organizationTableBind;
    }

    public void parameterNameValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(parameterTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addParamBindVar);
    }

    public void setParameterTableBind(RichTable parameterTableBind) {
        this.parameterTableBind = parameterTableBind;
    }

    public RichTable getParameterTableBind() {
        return parameterTableBind;
    }

    public void employeeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(shiftEmployeeTableBind);
    }

    public void removeRow(Key key, String iterName) {
        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);
            if (row != null)
                row.remove();
        }
    }

    public void ShiftActiveValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(shiftEmployeeTableBind);
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void ShiftInchargeValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(shiftEmployeeTableBind);
    }

    public void setAddManpwerBindVar(RichLink addManpwerBindVar) {
        this.addManpwerBindVar = addManpwerBindVar;
    }

    public RichLink getAddManpwerBindVar() {
        return addManpwerBindVar;
    }

    public void setShiftInchargeBind(RichSelectBooleanCheckbox shiftInchargeBind) {
        this.shiftInchargeBind = shiftInchargeBind;
    }

    public RichSelectBooleanCheckbox getShiftInchargeBind() {
        return shiftInchargeBind;
    }

    public void setShiftDescBind(RichInputListOfValues shiftDescBind) {
        this.shiftDescBind = shiftDescBind;
    }

    public RichInputListOfValues getShiftDescBind() {
        return shiftDescBind;
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void shiftInchargeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            ob = ADFUtils.findOperation("IsInchargeExist");
            ob.getParamsMap().put("workCenterId",
                                  this.getNewWcCodeBind().getValue() != null ?
                                  this.getNewWcCodeBind().getValue().toString() : "");
            ob.getParamsMap().put("shift",
                                  this.getShiftDescBind().getValue() != null ?
                                  this.getShiftDescBind().getValue().toString() : "");
            ob.execute();
            Object result = ob.getResult();
            if (result != null && result.toString().equalsIgnoreCase("false")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1357']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void activeWorkCenterValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("false")) {
            ob = ADFUtils.findOperation("isPrfInUse");
            ob.getParamsMap().put("workCenterId",
                                  this.getNewWcCodeBind().getValue() != null ?
                                  this.getNewWcCodeBind().getValue().toString() : "");
            ob.execute();
            Object result = ob.getResult();
            if (result != null) {
                String flag = result.toString();
                if (flag.equalsIgnoreCase("Y")) {
                    String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1387']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setAddParamBindVar(RichLink addParamBindVar) {
        this.addParamBindVar = addParamBindVar;
    }

    public RichLink getAddParamBindVar() {
        return addParamBindVar;
    }

    public void setGenerateBindvar(RichPanelAccordion generateBindvar) {
        this.generateBindvar = generateBindvar;
    }

    public RichPanelAccordion getGenerateBindvar() {
        return generateBindvar;
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void setImgTable(RichTable imgTable) {
        this.imgTable = imgTable;
    }

    public RichTable getImgTable() {
        return imgTable;
    }

    public void addOrgFilterVoAction(DisclosureEvent disclosureEvent) {
        // Add event code here...
        ADFUtils.findOperation("filterLovData").execute();
    }
}
