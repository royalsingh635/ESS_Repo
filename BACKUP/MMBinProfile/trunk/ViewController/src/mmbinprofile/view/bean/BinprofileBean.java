package mmbinprofile.view.bean;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mmbinprofile.model.services.BinprofileAMImpl;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class BinprofileBean {

    private boolean formReadonly = true;
    private boolean createButton = false;
    private boolean saveButton = true;
   String btnMode="";
    private String mode = "V";
    private static int VARCHAR = Types.VARCHAR;
    private RichShowDetailItem wareHouseshowDetailBind;
    private RichShowDetailItem storageTypShowDetailBind;
    private boolean allBinsLink=false;
    private boolean blockedLinks=false;
    private boolean headerText=true;
    private RichCommandLink blockedLinkBind;
    private RichCommandLink allBinLinkBind;
    private RichPopup deleteCheckPopUp;
    private RichInputText blkResnBind;
    private RichInputDate binFrmBind;
    private RichInputDate blkFrmBind;
    private RichInputText binSearchBind;
    private RichPanelGroupLayout searchPanel;
    private RichPopup addItmPopUp;
    private RichTable itmTableBind;
    private RichPopup itmDeletePopup;
    private RichInputListOfValues itemBind;
    
    private Integer count=-1;

    public BinprofileBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    

    public void createBinButton(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        am.getWhIdLOV1().executeQuery();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        this.setFormReadonly(false);
        this.createButton = true;
        this.saveButton = false;
        this.mode = "A";
        setBtnMode("C");
        
    }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
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


    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveButton(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        if (mode.equalsIgnoreCase("A")) {
            Integer SLOCID=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String ORGID=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String CLDID =resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String tableName ="APP$BIN";
           /* String BinId =
                (String)(callStoredFunction(VARCHAR, "APP.FN_APP_GEN_ID (?,?,?,?,?)", new Object[] {SLOCID,CLDID,null,ORGID,tableName }));
           

            ViewObject v1 = am.getAppBin1();
            Row row = v1.getCurrentRow();
            row.setAttribute("BinId", BinId); */
        }

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        
        if (operationBinding.getErrors().isEmpty()) {
            this.formReadonly = true;
            this.createButton = false;
            this.saveButton = true;
             setBtnMode("S");
            if(mode.equalsIgnoreCase("A")){
            String msg = resolvElDCMsg("#{bundle['MSG.89']}").toString();
            String msg2 = msg.format(msg, "", "", "", "", "");
            FacesMessage saveMsg = new FacesMessage(msg2);
            saveMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, saveMsg);
            }
            else if(mode.equalsIgnoreCase("E")){
                FacesMessage editMsg=new FacesMessage(resolvElDCMsg("#{bundle['MSG.479']}").toString());
                FacesContext.getCurrentInstance().addMessage(null, editMsg);
            }
         }
    }

    public void cancelButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        operationBinding.execute();
        operationBinding1.execute();
        this.formReadonly = true;
        this.createButton = false;
        this.saveButton = false;
       
    }

    public void binNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        String BinNm = null;
        if (object != null) {
            BinNm = object.toString();
            if (BinNm.startsWith(" ") || BinNm.endsWith(" ")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.480']}").toString(), null));
            }

            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = BinNm.toCharArray();
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


            /**if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            /*closing brackets must not come before first occurrence of openning bracket
            **/
            if (openB != closeB || closeFg == true || (BinNm.lastIndexOf("(") > BinNm.lastIndexOf(")")) ||
                (BinNm.indexOf(")") < BinNm.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (BinNm.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (BinNm.contains("(.") || BinNm.contains("(-") || BinNm.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.481']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid Bin name.Allowed- brackets,dots,hyphen
             * */
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
            
         //  String expression = "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
          //  String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = BinNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.481']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.199']}").toString()));
            }


        }
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


    public void setFormReadonly(boolean formReadonly) {
        this.formReadonly = formReadonly;
    }

    public boolean isFormReadonly() {
        return formReadonly;
    }

    public void setCreateButton(boolean createButton) {
        this.createButton = createButton;
    }

    public boolean isCreateButton() {
        return createButton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void editBinButton(ActionEvent actionEvent) {
        this.formReadonly = false;
        this.createButton = true;
        this.saveButton = false;
        this.mode = "E";
        setBtnMode("C");
    }

   

    public void wareHouseTreeSelectionListener(SelectionEvent selectionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        String adfSelectionListener = "#{bindings.WhIdLOV1.treeModel.makeCurrent}";

        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();

        MethodExpression me = null;
        me =
        exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class, new Class[] { SelectionEvent.class });
        me.invoke(elCtx, new Object[] { selectionEvent });
        RichTreeTable tree = (RichTreeTable)selectionEvent.getSource();
        TreeModel model = (TreeModel)tree.getValue();
        //get selected nodes
        RowKeySet rowKeySet = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeySet.iterator();
        while (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding)((CollectionModel)tree.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();
            
            if(nodeBinding.getViewObject().getName().equalsIgnoreCase("WhIdLOV1")){
                am.getAppBin1().setWhereClause(null);
                am.getAppBin1().executeQuery();
               am.getAppBin1().setWhereClause("WH_ID ='"+rw.getAttribute(0)+"'"); 
               am.getAppBin1().executeQuery();
               this.headerText=true;
                this.allBinsLink=false;
                this.blockedLinks=false;
                allBinLinkBind.setInlineStyle("font-weight:bold;color:blue;");
                blockedLinkBind.setInlineStyle("font-weight:bold;color:blue;");
            }

}
    }

    public void storageTypSelectionListener(SelectionEvent selectionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        ADFUtil.invokeEL("#{bindings.StorageTypLOV1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
        Row selectedRow = (Row)ADFUtil.evaluateEL("#{bindings.StorageTypLOV1Iterator.currentRow}");
        Integer stoTyp = (Integer)selectedRow.getAttribute("AttId");
        am.getAppBin1().setWhereClause(null);
        am.getAppBin1().executeQuery();
        am.getAppBin1().setWhereClause("STORAGE_TYPE ='"+stoTyp+"'");
        am.getAppBin1().executeQuery();
        this.headerText=true;
        this.allBinsLink=false;
        this.blockedLinks=false;
        allBinLinkBind.setInlineStyle("font-weight:bold;color:blue;");
        blockedLinkBind.setInlineStyle("font-weight:bold;color:blue;");
    }

    public void allBinsLink(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        am.getAppBin1().setWhereClause(null);
        am.getAppBin1().executeQuery();
        allBinLinkBind.setInlineStyle("font-weight:bold;color:red;");
        blockedLinkBind.setInlineStyle("font-weight:bold;color:blue;");
        this.allBinsLink=true;
        this.blockedLinks=false;
        this.headerText=false;
    }

    public void blockedBinsLink(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        am.getAppBin1().setWhereClause(null);
        am.getAppBin1().executeQuery();
        am.getAppBin1().setWhereClause("BLOCKED ='Y'");
        am.getAppBin1().executeQuery();
        allBinLinkBind.setInlineStyle("font-weight:bold;color:blue;");
        blockedLinkBind.setInlineStyle("font-weight:bold;color:red;");
        this.allBinsLink=false;
        this.blockedLinks=true;
        this.headerText=false;
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
    public String resolvEl(String data){
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           String Message=valueExp.getValue(elContext).toString();
           return Message;
         }
    public void deleteBinLink(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        ViewObject v1=am.getAppBin1();
        String binId=null;
        String whId=null;
        if(v1.getCurrentRow().getAttribute("BinId")!=null){
            binId = (String)v1.getCurrentRow().getAttribute("BinId");
        }
        if(v1.getCurrentRow().getAttribute("WhId")!=null){
            whId=(String)v1.getCurrentRow().getAttribute("WhId");
        }
        Integer SLOCID=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String ORGID=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String CLDID =resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        System.out.println(" parameters are "+ CLDID+" "+SLOCID+" "+ORGID+" "+ whId+ " "+binId);
        String num =
            (String)(callStoredFunction(Types.VARCHAR, "APP.FN_IS_BIN_DELETABLE (?,?,?,?,?)", new Object[] {CLDID,SLOCID,ORGID,whId,binId }));
        System.out.println("function output is "+num);
        if(num.equalsIgnoreCase("Y")){
        showPopup(deleteCheckPopUp, true);
        }
        else{
            FacesMessage errMsg=new FacesMessage(resolvElDCMsg("#{bundle['MSG.483']}").toString());
            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(null, errMsg);
                                                                                                    
        }
    }

    public void setWareHouseshowDetailBind(RichShowDetailItem wareHouseshowDetailBind) {
        this.wareHouseshowDetailBind = wareHouseshowDetailBind;
    }

    public RichShowDetailItem getWareHouseshowDetailBind() {
        return wareHouseshowDetailBind;
    }

    public void setStorageTypShowDetailBind(RichShowDetailItem storageTypShowDetailBind) {
        this.storageTypShowDetailBind = storageTypShowDetailBind;
    }

    public RichShowDetailItem getStorageTypShowDetailBind() {
        return storageTypShowDetailBind;
    }


    public void setAllBinsLink(boolean allBinsLink) {
        this.allBinsLink = allBinsLink;
    }

    public boolean isAllBinsLink() {
        return allBinsLink;
    }

    public void setBlockedLinks(boolean blockedLinks) {
        this.blockedLinks = blockedLinks;
    }

    public boolean isBlockedLinks() {
        return blockedLinks;
    }

    public void setHeaderText(boolean headerText) {
        this.headerText = headerText;
    }

    public boolean isHeaderText() {
        return headerText;
    }

    public void blockedValueChangeList(ValueChangeEvent vce) {
         BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();

            
            ViewObject v1 = am.getAppBin1();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
               
            } else if (newVal.equalsIgnoreCase("false")) {
                blkFrmBind.setValue(null);
                blkResnBind.setValue(null);
                binFrmBind.setValue(null);
               row.setAttribute("BlkResn", null);
                row.setAttribute("BlkDtFrm", null);
                row.setAttribute("BlkDtTo", null); 

                
                AdfFacesContext.getCurrentInstance().addPartialTarget(blkResnBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(blkFrmBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(binFrmBind);
            }
        }
     }

    public void totalCapacityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       oracle.jbo.domain.Number capacity = (oracle.jbo.domain.Number)object;
       if(capacity.compareTo(0)==-1){
           String errMsg=resolvElDCMsg("#{bundle['MSG.485']}").toString();
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,errMsg,resolvElDCMsg("#{bundle['MSG.484']}").toString()));
       }
    }

    public void setBlockedLinkBind(RichCommandLink blockedLinkBind) {
        this.blockedLinkBind = blockedLinkBind;
    }

    public RichCommandLink getBlockedLinkBind() {
        return blockedLinkBind;
    }

    public void setAllBinLinkBind(RichCommandLink allBinLinkBind) {
        this.allBinLinkBind = allBinLinkBind;
    }

    public RichCommandLink getAllBinLinkBind() {
        return allBinLinkBind;
    }

    public void setDeleteCheckPopUp(RichPopup deleteCheckPopUp) {
        this.deleteCheckPopUp = deleteCheckPopUp;
    }

    public RichPopup getDeleteCheckPopUp() {
        return deleteCheckPopUp;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
            ViewObjectImpl binvo = am.getAppBin1();
            Row curr=binvo.getCurrentRow();
            String binId="";
            if(curr!=null){
                binId=curr.getAttribute("BinId").toString();
                String CldId=curr.getAttribute("CldId").toString();
                Integer slocId=Integer.parseInt(curr.getAttribute("SlocId").toString());
                String orgId=curr.getAttribute("OrgId").toString();
                RowQualifier rowQualifier = new RowQualifier(am.getAppBinCap2());
                rowQualifier.setWhereClause("CldId='"+CldId+"' AND SlocId= "+slocId+" AND OrgId='"+orgId+"' AND BinId='"+ binId+"'");
                Row[] filteredRows = (am.getAppBinCap2().getFilteredRows(rowQualifier)); 
                if(filteredRows.length>0){
                    for(Row r:filteredRows){
                        r.remove();
                    }
                }
               curr.remove();
            }
           
             BindingContainer bindings=getBindings();
            /*OperationBinding ob=bindings.getOperationBinding("Delete");
            ob.execute();
            */ OperationBinding ob1=bindings.getOperationBinding("Commit");
            ob1.execute(); 
            OperationBinding ob2=bindings.getOperationBinding("Execute");
            ob2.execute();
        }
        else {
            
        }
    }

    public void setBlkResnBind(RichInputText blkResnBind) {
        this.blkResnBind = blkResnBind;
    }

    public RichInputText getBlkResnBind() {
        return blkResnBind;
    }

    public void setBinFrmBind(RichInputDate binFrmBind) {
        this.binFrmBind = binFrmBind;
    }

    public RichInputDate getBinFrmBind() {
        return binFrmBind;
    }

    public void setBlkFrmBind(RichInputDate blkFrmBind) {
        this.blkFrmBind = blkFrmBind;
    }

    public RichInputDate getBlkFrmBind() {
        return blkFrmBind;
    }

    public void blkDtFrmVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(blkFrmBind);
    }

    public void setBinSearchBind(RichInputText binSearchBind) {
        this.binSearchBind = binSearchBind;
    }

    public RichInputText getBinSearchBind() {
        return binSearchBind;
    }

    public void searchButtonOnBin(ActionEvent actionEvent) {
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        ViewObjectImpl v = am.getAppBin1();
        v.setWhereClause("");
        v.executeQuery();
        if (getBinSearchBind().getValue() != null) {
            String search = getBinSearchBind().getValue().toString().toUpperCase();
            v.setWhereClause("BIN_NM like '%" + search + "%'");
            v.executeQuery();
        }
    }

    public void resetButtonOnBin(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        ViewObjectImpl v = am.getAppBin1();
        v.setWhereClause(null);
        v.executeQuery();
    }
    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }

    public void setAddItmPopUp(RichPopup addItmPopUp) {
        this.addItmPopUp = addItmPopUp;
    }

    public RichPopup getAddItmPopUp() {
        return addItmPopUp;
    }

    public void addItmDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
           int count = 0;
           /**------------For duplicate input item validation-----------------------*/
          /*  String inputItm=itemBind.getValue().toString();
           String itmDesc=null;
           Integer SLOCID=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
           String ORGID=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
           String CLDID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
           BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
           //ViewObjectImpl v = am.getAppBinCap2();
           ViewObjectImpl v = am.getAppBinCap();
           ViewObjectImpl v1 =am.getAppBin1();
           Row currRow = v1.getCurrentRow();
           String whId = (String)currRow.getAttribute("WhId").toString();
           String binId = (String)currRow.getAttribute("BinId").toString();
            String itmId = null;
           if(inputItm != null){
               Row [] xx=am.getLovItmId1().getFilteredRows("ItmDesc", inputItm);
                                       if(xx.length>0){
                                       itmId= xx[0].getAttribute("ItmId").toString();
           }
           }
           System.out.println("vce   SLOCID---"+SLOCID+"ORGID--"+ORGID+"CLDID--"+CLDID+"whId--"+whId+"binId--"+binId+"inputItm--"+inputItm+"itmId--"+itmId);
           if(itmId !=null){
               System.out.println("v.getEstimatedRowCount()    save"+v.getEstimatedRowCount());
             //  v.setRangeSize(-1);
               v.getAllRowsInRange();
               RowQualifier rowQualifier = new RowQualifier(v);
              // rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"' and ItmId= '" + itmId + "'");
               rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"'");
               Row[] rows = v.getFilteredRows(rowQualifier);
               System.out.println("-----------save" + rowQualifier.getExprStr());
               System.out.println("rows.length  save   "+rows.length);
           
                if(rows.length > 0){
                  for(Row rr : rows){
                      if(rr != am.getAppBinCap().getCurrentRow()){
                           String newItem = rr.getAttribute("TransItemDisc").toString();
                      System.out.println("newItem------------"+newItem);
                           if(inputItm.equalsIgnoreCase(newItem)){
                               count = count+1;
                           }
                     }
                   } 
                  */
                 /*    System.out.println("for check duplicate");
                  FacesMessage message = new FacesMessage("Duplicate Record found");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(itemBind.getClientId(), message); */
                                  
             /*  }
              System.out.println("count -----"+count);
              rowQualifier.setWhereClause(null);
           }
            System.out.println("count111111 -----"+count);
          if(count >0){
              System.out.println("for check duplicate");
                               FacesMessage message = new FacesMessage("Duplicate Record found");
                               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                               FacesContext fc = FacesContext.getCurrentInstance();
                               fc.addMessage(itemBind.getClientId(), message);
          }else{ */
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
          //  OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
          //  operationBinding1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
          // }
        }
    }

    public void itmPopupCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppBin1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        System.out.println("key-------"+parentKey);
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
        operationBinding1.execute();
        System.out.println("1key-------"+parentKey);
        if(parentKey != null){
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
    }

    public void addItemAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        setBtnMode("C");
        showPopup(addItmPopUp, true);
    }

    public void editItmAction(ActionEvent actionEvent) {
        setBtnMode("C");
        showPopup(addItmPopUp, true);
    }

    public void itmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /**------------For duplicate input item validation-----------------------*/
        int count = 0; 
        String inputItm=(String)object;
        String itmDesc=null;
        Integer SLOCID=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String ORGID=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String CLDID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
       // ViewObjectImpl v = am.getAppBinCap2();
       ViewObjectImpl v = am.getAppBinCap();      
        ViewObjectImpl v1 =am.getAppBin1();
        Row currRow = v1.getCurrentRow();
        String whId = (String)currRow.getAttribute("WhId").toString();
        String binId = (String)currRow.getAttribute("BinId").toString();
        
       String itmId = null;
        if(inputItm != null){
            Row [] xx=am.getLovItmId1().getFilteredRows("ItmDesc", inputItm);
                                    if(xx.length>0){
                                    itmId= xx[0].getAttribute("ItmId").toString();
            }
        }
      System.out.println("SLOCID---"+SLOCID+"ORGID--"+ORGID+"CLDID--"+CLDID+"whId--"+whId+"binId--"+binId+"inputItm--"+inputItm+"itmId--"+itmId);
        if(itmId !=null){
         System.out.println("v.getEstimatedRowCount()    "+v.getEstimatedRowCount());
          //  v.setRangeSize(-1);
            v.getAllRowsInRange();
            RowQualifier rowQualifier = new RowQualifier(v);
          // rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"' and ItmId= '" + itmId + "'");
         rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"'");
            Row[] rows = v.getFilteredRows(rowQualifier);
            System.out.println("-----------" + rowQualifier.getExprStr());
        System.out.println("rows.length     "+rows.length);
           
         if(rows.length > 0){
              for(Row rr : rows){
                    if(rr != am.getAppBinCap().getCurrentRow()){
                          String newItem = rr.getAttribute("TransItemDisc").toString();
                           System.out.println("newItem------------"+newItem);
                         if(inputItm.equalsIgnoreCase(newItem)){
                            count = count+1;
                        }
                    }
                } 
               
         }
           System.out.println("count -----"+count);
           rowQualifier.setWhereClause(null);
        } 
                    if(count>0){
                        String msg2=resolvElDCMsg("#{bundle['MSG.46']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                  } 
    }

    public void setItmTableBind(RichTable itmTableBind) {
        this.itmTableBind = itmTableBind;
    }

    public RichTable getItmTableBind() {
        return itmTableBind;
    }

    public void deleteItmAction(ActionEvent actionEvent) {
        showPopup(itmDeletePopup, true);
    }

    public void deleteItmDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings=getBindings();
            OperationBinding ob=bindings.getOperationBinding("Delete");
            ob.execute();
            OperationBinding ob1=bindings.getOperationBinding("Commit");
            ob1.execute(); 
            OperationBinding ob2=bindings.getOperationBinding("Execute1");
            ob2.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        }
        else {
            
        }
    }

    public void setItmDeletePopup(RichPopup itmDeletePopup) {
        this.itmDeletePopup = itmDeletePopup;
    }

    public RichPopup getItmDeletePopup() {
        return itmDeletePopup;
    }

    public void itemValueChangeListener(ValueChangeEvent vce) {
        /**------------For duplicate input item validation-----------------------*/
        String inputItm=vce.getNewValue().toString();
        String itmDesc=null;
        Integer SLOCID=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String ORGID=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String CLDID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        BinprofileAMImpl am = (BinprofileAMImpl)resolvElDC("BinprofileAMDataControl");
        ViewObjectImpl v = am.getAppBinCap2();
        ViewObjectImpl v1 =am.getAppBin1();
        Row currRow = v1.getCurrentRow();
        String whId = (String)currRow.getAttribute("WhId").toString();
        String binId = (String)currRow.getAttribute("BinId").toString();
        
        String itmId = null;
        if(inputItm != null){
            Row [] xx=am.getLovItmId1().getFilteredRows("ItmDesc", inputItm);
                                    if(xx.length>0){
                                    itmId= xx[0].getAttribute("ItmId").toString();
        }
        }
        System.out.println("vce   SLOCID---"+SLOCID+"ORGID--"+ORGID+"CLDID--"+CLDID+"whId--"+whId+"binId--"+binId+"inputItm--"+inputItm+"itmId--"+itmId);
        if(itmId !=null){
            System.out.println("v.getEstimatedRowCount()    vce"+v.getEstimatedRowCount());
          //  v.setRangeSize(-1);
            v.getAllRowsInRange();
            RowQualifier rowQualifier = new RowQualifier(v);
            // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
            rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"' and ItmId= '" + itmId + "'");
            //rowQualifier.setWhereClause(" OrgId = '" + ORGID + "' and SlocId=" + SLOCID + " and CldId = '"+CLDID+"' and WhId = '"+whId+"' and BinId = '"+binId+"'");
            Row[] rows = v.getFilteredRows(rowQualifier);
            System.out.println("-----------vce" + rowQualifier.getExprStr());
            System.out.println("rows.length  vce   "+rows.length);
            int count = 0;
             if(rows.length > 0){
                /* for(Row rr : rows){
                    if(rr != am.getAppBinCap().getCurrentRow()){
                        String newItem = rr.getAttribute("ItmId").toString();
                        if(itmId.equalsIgnoreCase(newItem)){
                            count = count+1;
                        }
                    }
                }  */
               FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.46']}").toString());
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(itemBind.getClientId(), message);
                               
           }
           //System.out.println("count -----"+count);
           rowQualifier.setWhereClause(null);
        }
    }

    public void setItemBind(RichInputListOfValues itemBind) {
        this.itemBind = itemBind;
    }

    public RichInputListOfValues getItemBind() {
        return itemBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
         count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }

    public void setBtnMode(String btnMode) {
        this.btnMode = btnMode;
    }

    public String getBtnMode() {
        return btnMode;
    }

    public void viewActionListner(ActionEvent actionEvent) {
        setBtnMode("V");
    }

    public void deleteItemActionListener(ActionEvent actionEvent) {
        //Add code to delete Item here( AppBinCap)
        OperationBinding opDel=getBindings().getOperationBinding("Delete1");
            opDel.execute();
    }
}
