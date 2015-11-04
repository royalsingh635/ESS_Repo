package mmappwhprf.view.bean;

import adf.utils.model.ADFModelUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.event.SelectionListener;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

public class WarehouseBean {
    private RichInputDate inctvRsnDtBinding;
    String mode = "D";
    Key key = null;
    private RichPanelFormLayout panelFormBinding;
    private RichInputText locNmBind;
    private RichTreeTable treeBind;
    private String prjId = "N";

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getPrjId() {
        Object res = getBindings().getOperationBinding("CanChangePrjId").execute();
        //
        System.out.println("bean re" + res);
        if (res != null && res.equals(true)) {
            return "Y";
        }

        //  return prjId;
        return "N";
    }

    public WarehouseBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchWarehouseAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchWarehouse").execute();
    }

    public void resetWarehouseAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetWarehouse").execute();
    }

    public void activeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String value = valueChangeEvent.getNewValue().toString();
            System.out.println("actv value is " + value);
            if (value.equalsIgnoreCase("false")) {
                Timestamp dt = new Timestamp(System.currentTimeMillis());
                inctvRsnDtBinding.setValue(dt);
            } else if (value.equalsIgnoreCase("true")) {
                inctvRsnDtBinding.setValue(null);
            }
        }
    }

    public void setInctvRsnDtBinding(RichInputDate inctvRsnDtBinding) {
        this.inctvRsnDtBinding = inctvRsnDtBinding;
    }

    public RichInputDate getInctvRsnDtBinding() {
        return inctvRsnDtBinding;
    }

    public void wareHouseNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null &&
            object.toString().length() >
                          0) { //(\.|\-(?!\.|\-|$)
            //     |       |
            String expression =
                              "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                              "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
            CharSequence inputStr = object.toString();
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
//            String error = "Special Character Not Allowed";
            String error = ADFModelUtils.resolvRsrc("MSG.1056");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

            //check for duplicate warehouse name
            OperationBinding chkDupli = getBindings().getOperationBinding("CheckDuplicateName");
            chkDupli.getParamsMap().put("whNm", object);
            chkDupli.execute();
            String isdupli = null;
            if (chkDupli.getErrors().size() == 0 && chkDupli.getResult() != null) {
                isdupli = (String) chkDupli.getResult();
            }
            if (isdupli.equals("Y")){
                String s=ADFModelUtils.resolvRsrc("MSG.2373");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            }

        }
    }

    public void addBtnAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert").execute();
        this.mode = "E";
    }

    public void editBtnAL(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("getCurrentRowKey");
        op.execute();

        if (op.getErrors().size() == 0 && op.getResult() != null) {
            key = (Key) op.getResult();
            System.out.println("Key is=" + key);
        }
        this.mode = "E";
    }

    public void saveBtnAL(ActionEvent actionEvent) {
        String chk = "N";
        OperationBinding chkWhAdds = getBindings().getOperationBinding("ChkAdds");
        chkWhAdds.execute();
        if (chkWhAdds.getErrors().size() == 0 && chkWhAdds.getResult() != null)
            chk = (String) chkWhAdds.getResult();
        else
            System.out.println("Error in add call=" + chkWhAdds.getErrors() + " and return=" + chkWhAdds.getResult());
        System.out.println("Chk in bean=" + chk);
        if (chk.equals("N")) {
//            Please select Address
            String s=ADFModelUtils.resolvRsrc("MSG.1798");
            FacesMessage message2 = new FacesMessage(s);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        } else {
            OperationBinding oBind = getBindings().getOperationBinding("Commit");
            oBind.execute();
            this.mode = "D";
        }
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        OperationBinding oBind = getBindings().getOperationBinding("Rollback");
        oBind.execute();
        getBindings().getOperationBinding("Execute").execute();
        OperationBinding op = getBindings().getOperationBinding("setCurrentRow");
        op.getParamsMap().put("key", key);
        op.execute();

        this.mode = "D";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public void AddLocAction(ActionEvent actionEvent) {
        // Add event code here...
        //checkLocNm
        Object res = getBindings().getOperationBinding("checkLocNm").execute();
        if (res == null || res.equals(true)) {
//            Please select Location name
            String s=ADFModelUtils.resolvRsrc("MSG.2374");
            FacesMessage message2 = new FacesMessage(s);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(locNmBind.getClientId(), message2);
            return;
        }
        Object res1 = getBindings().getOperationBinding("checkLocRow").execute();
        System.out.println("check is: " + res1);
        if (res1 != null && res1.equals(true)) {
            getBindings().getOperationBinding("postChange").execute();
        }
        getBindings().getOperationBinding("CreateInsert1").execute();
    }

    public void LocDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        Object obj = getBindings().getOperationBinding("isChildExists").execute();

        /*   OperationBinding binding = getBindings().getOperationBinding("isChildExists");
        Object obj = binding.execute(); */
        if (obj != null && obj.equals(false)) {


            Object res = getBindings().getOperationBinding("deleteLocBin").execute();
            if (res != null && res.equals(false)) {
                getBindings().getOperationBinding("postChange").execute();
            } else {
//                Child can not be deleted reference found in Bin profile !! 
                String s=ADFModelUtils.resolvRsrc("MSG.2375");
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
        } else {
//            Child Record Found Plese Delete Child First !!
            String s=ADFModelUtils.resolvRsrc("MSG.2376");
            FacesMessage message2 = new FacesMessage(s);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    public void setLocNmBind(RichInputText locNmBind) {
        this.locNmBind = locNmBind;
    }

    public RichInputText getLocNmBind() {
        return locNmBind;
    }

    public void paentLocValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            System.out.println("Parent id: " + object);
            OperationBinding binding = getBindings().getOperationBinding("checkLocParent");
            binding.getParamsMap().put("idParent", object.toString());
            Object res = binding.execute();
            System.out.println("res = " + res);

            /*  FacesMessage message2 = new FacesMessage("location can not be same or child !!");
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(facesContext), message2); */
            if ((Boolean) res) {
//                location can not be same or child
                String s=ADFModelUtils.resolvRsrc("MSG.2377");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             s, null));
            }
        }
    }

    public Row getTreeCurrentRow() {
        RichTreeTable treeTable = this.getTreeBind();
        RowKeySet rks = treeTable.getSelectedRowKeys();
        Row rw = null;
        if (rks != null) {
            Iterator keys = rks.iterator();
            while (keys.hasNext()) {
                List key = (List) keys.next();
                treeTable.setRowKey(key);
                JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding) treeTable.getRowData();
                rw = node.getRow();
                String voName = node.getViewObject().getName();

            }
        }
        return rw;
    }

    public void selectionListener(SelectionEvent selectionEvent) {
        Row r = this.getTreeCurrentRow();
        if (r != null) {
            String locId = null;
            locId = r.getAttribute("LocId").toString();
            OperationBinding binding = this.getBindings().getOperationBinding("filterView");
            binding.getParamsMap().put("locId", locId);
            binding.execute();
        }
        /*   UIComponent component = selectionEvent.getComponent();
        if (resolvEl("#{pageFlowScope.PARAM_TF_CALLED}").toString().equals("Y")) {
            Map<String, Object> attributes = component.getAttributes();
            attributes.put("grpId", grpId);
            BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
            JUEventBinding eventBinding = (JUEventBinding) bindingContainer.get("publisherEventBinding");
            SelectionListener selectListener = (SelectionListener) eventBinding.getListener();
            selectListener.processSelection(selectionEvent);
        } else {
               AppGrpdefAMImpl am = (AppGrpdefAMImpl) resolvElDC("AppGrpdefAMDataControl");
            ViewObjectImpl v = am.getFormGrp1();
            v.setWhereClause(null);
            v.executeQuery();
            v.setWhereClause("Grp_Id = '" + grpId + "'");
            v.executeQuery();
            //am.getLovGroupIdParent().executeQuery();

        } */
    }

    public void setTreeBind(RichTreeTable treeBind) {
        this.treeBind = treeBind;
    }

    public RichTreeTable getTreeBind() {
        return treeBind;
    }

    public void warehouseSelectionListener(SelectionEvent selectionEvent) {
        // Add event code here...
        ADFUtil.invokeEL("#{bindings.AppWhOrg1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });

        getBindings().getOperationBinding("resetLocTable").execute();
    }

    public void addLocChildAction(ActionEvent actionEvent) {
        Object res = getBindings().getOperationBinding("checkLocNm").execute();
        if (res == null || res.equals(true)) {
            String s=ADFModelUtils.resolvRsrc("MSG.2374");
            FacesMessage message2 = new FacesMessage(s);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(locNmBind.getClientId(), message2);
            return;
        }
        Row r = this.getTreeCurrentRow();
        if (r != null) {
            String locId = null;
            locId = r.getAttribute("LocId").toString();
            String whId = r.getAttribute("WhId").toString();

            OperationBinding binding1 = getBindings().getOperationBinding("chkIfLocBinExists");
            binding1.getParamsMap().put("whId", whId);
            binding1.getParamsMap().put("locId", locId);
            Object execute = binding1.execute();
            System.out.println("Execute is: " + execute);
            if (execute != null && execute.equals(false)) {
                OperationBinding binding = getBindings().getOperationBinding("insertinLocBin");
                binding.getParamsMap().put("locID", locId);
                binding.execute();
            } else {
//                Child can not be added, reference found in bin profile
                String s=ADFModelUtils.resolvRsrc("MSG.2378");
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
        }
    }

    public void refreshAction(ActionEvent actionEvent) {
        Object res = getBindings().getOperationBinding("checkLocNm").execute();
        if (res == null || res.equals(true)) {
//            Please select Location name
            String s=ADFModelUtils.resolvRsrc("MSG.2374");
            FacesMessage message2 = new FacesMessage(s);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(locNmBind.getClientId(), message2);
            return;
        }
        getBindings().getOperationBinding("postChange").execute();
    }

    public void locNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
//        "Please mention the location name."
        String s=ADFModelUtils.resolvRsrc("MSG.2379");
        System.out.println("validator" + object);
        if (object == null || object.toString().equalsIgnoreCase("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          s, null));
        }
    }

    public void projChkvalidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...





    }
}
