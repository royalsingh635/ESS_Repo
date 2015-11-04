package appEoAddress.view.bean;


import appEoAddress.model.service.AppEoAddressAMImpl;

import java.io.Serializable;

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
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.LaunchPopupEvent;


//import oracle.adfinternal.view.faces.model.binding.FacesCtrlLOVBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.ReturnEvent;


public class AppEoAddressBean implements Serializable {
    private RichInputText activeReason;
    private RichInputDate activeDate;
    private RichInputText transAddBindVar;
    private RichTable addressTable;
    private String mode = null;
    private String addsFlag = "F";
    private RichInputListOfValues addIdQueryLov;
    private Integer count = 0;
    private Integer countadds = 0;
    private RichSelectBooleanCheckbox defaultbillingAddress;
    private RichSelectBooleanCheckbox defaultShippingAddress;
    AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");


    public AppEoAddressBean() {
        /*  if (executeBinding("on_load_page")!= null) {
            count = (Integer)executeBinding("on_load_page").execute();
        } */
    }
    private String formDisabled = "true";
    private String createButton = "false";
    private String editButton = "false";
    private String saveButton = "true";
    private String cancelButton = "true";

    public String CreateAction() {
        this.setFormDisabled("false");
        this.setCreateButton("true");
        this.setCancelButton("false");
        this.setEditButton("true");
        this.setSaveButton("false");
        this.setMode("A");
        am.setModeToEntity("A");

        //   addressTable.setRowSelection(RichTable.ROW_SELECTION_NONE);
        BindingContainer bc = getBindings();
        OperationBinding binding = bc.getOperationBinding("CreateInsert");
        binding.execute();
        return null;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String SaveAction() {
        countadds = 0;
        ViewObjectImpl v = am.getAppEoAdds();
        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw = itr.next();
            System.out.println("++address idd in each row is==  " + rw.getAttribute("AddsId"));
            if (rw.getAttribute("AddsId") == null || rw.getAttribute("AddsId").toString() == "") {
                countadds = countadds + 1;
            } else {
            }
        }
        if (countadds > 0) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("please select the Address");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, msg);
        } else {
            countadds = 0;
            if (v != null) {
                Integer EoIdparam = Integer.parseInt(resolvEl("#{pageFlowScope.EO_ID}").toString());
                Row[] filteredRows = v.getFilteredRows("EoId", EoIdparam);
                System.out.println("filteredRows.length = " + filteredRows.length + "  EoIdparam = " + EoIdparam);
                int bill = 0;
                int ship = 0;
                if (filteredRows.length > 0) {
                    System.out.println("defaultbillingAddress.getValue() = " + defaultbillingAddress.getValue() +
                                       " defaultShippingAddress.getValue = " + defaultShippingAddress.getValue());
                    if (defaultbillingAddress.getValue() != null || defaultShippingAddress.getValue() != null) {
                        for (int i = 0; i < filteredRows.length; i++) {
                            String BillAddsDflt = (String)filteredRows[i].getAttribute("BillAddsDflt");
                            String ShipAddsDflt = (String)filteredRows[i].getAttribute("ShipAddsDflt");

                            if (BillAddsDflt.toString().equalsIgnoreCase("Y")) {
                                bill = 1;
                            }
                            if (ShipAddsDflt.toString().equalsIgnoreCase("Y")) {
                                ship = 1;
                            }
                        }
                    }
                }
                if (!(bill == 1 && ship == 1)) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1237']}").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, msg);
                } else {
                    Row re = v.getCurrentRow();
                    String addsIdval = null;
                    int count = 0;
                    if (re.getAttribute("trans_AddsId") != null && addsFlag.equalsIgnoreCase("H")) {
                        addsIdval = re.getAttribute("trans_AddsId").toString();
                    }
                    Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                    String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
                    String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                    String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();

                    // String p_adds_id = (String)addsRow.getAttribute("AddsId");
                    String p_adds_desc = (String)re.getAttribute("TransAddName");
                    Integer p_eo_id = null;
                    if (re.getAttribute("EoId") != null) {
                        p_eo_id = Integer.parseInt(re.getAttribute("EoId").toString());
                    }

                    if (addsIdval != null && p_eo_id != null) {
                        v.setRangeSize(-1);
                        v.getAllRowsInRange();
                        RowQualifier rowQualifier = new RowQualifier(v);
                        rowQualifier.setWhereClause("OrgId= '" + orgId + "' and SlocId=" + p_sloc_id +
                                                    " and CldId= '" + cldId + "' and HoOrgId= '" + hoOrgId +
                                                    "' and EoId=" + p_eo_id + " and AddsId= '" + addsIdval + "'");
                        Row[] rows = v.getFilteredRows(rowQualifier);


                        if (re.getAttribute("AddsId") == null && rows.length > 0) {

                            count = 1;
                            // setAddsFlag("B");
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.279']}").toString(),
                                                 null);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        } else if (re.getAttribute("AddsId") != null && rows.length > 1) {

                        } else {
                            if (re.getAttribute("AddsId") == null) {
                                re.setAttribute("AddsId", addsIdval);
                            }
                            re.setAttribute("trans_AddsId", null);
                        }

                        rowQualifier.setWhereClause(null);
                    }
                    /* if(addsFlag.equalsIgnoreCase("B")){
            setAddsFlag("H");
        } */

                    /*  if(addsIdval != null && count ==0 && addsFlag.equalsIgnoreCase("H")){

            //re.setAttribute("trans_AddsId", "");
        } */
                    if (re.getAttribute("AddsId") != null) {
                        OperationBinding createOB = executeBinding("Commit");
                        createOB.execute();
                        am.setModeToEntity("S");
                        this.setFormDisabled("true");
                        this.setCreateButton("false");
                        this.setCancelButton("true");
                        this.setEditButton("false");
                        this.setSaveButton("true");
                        this.setMode("S");
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.280']}").toString());
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        fc.addMessage(null, msg);
                        //   addressTable.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                    } else if (count == 0) {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.117']}").toString());
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(addIdQueryLov.getClientId(), msg);
                    }
                }

            }
        }
        return null;
    }

    public String CancelAction() {
        // countadds=0;
        OperationBinding createOB = executeBinding("Rollback");
        createOB.execute();
        am.setModeToEntity("S");
        this.setFormDisabled("true");
        this.setCreateButton("false");
        this.setCancelButton("true");
        this.setEditButton("false");
        this.setSaveButton("true");
        this.setMode("C");
        this.setFormDisabled("true");
        //   addressTable.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
        return null;
    }

    public String EditAction() {
        this.setFormDisabled("false");
        this.setCreateButton("true");
        this.setCancelButton("false");
        this.setEditButton("true");
        this.setSaveButton("false");
        //   addressTable.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
        Date dt = (Date)Date.getCurrentDate();
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        am.getAppEoAdds().getCurrentRow().setAttribute("UsrIdModDt", dt);

        am.setModeToEntity("A");
        this.setMode("E");
        return null;
    }

    public void activeChangeListner(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            String value = vce.getNewValue().toString();

            if (value.equals("false")) {
                Date dt = (Date)Date.getCurrentDate();

                activeDate.setValue(dt);


                AdfFacesContext.getCurrentInstance().addPartialTarget(activeReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(activeDate);
                AdfFacesContext.getCurrentInstance().addPartialTarget(defaultbillingAddress);
                AdfFacesContext.getCurrentInstance().addPartialTarget(defaultShippingAddress);

            } else if (value.equals("true")) {

                activeReason.setValue(null);
                activeDate.setValue(null);

                AdfFacesContext.getCurrentInstance().addPartialTarget(activeReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(activeDate);
                AdfFacesContext.getCurrentInstance().addPartialTarget(defaultbillingAddress);
                AdfFacesContext.getCurrentInstance().addPartialTarget(defaultShippingAddress);
            }
        }
    }

    public void addressChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(transAddBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addressTable);
        }
    }

    public void defaultShipingAddressValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean)object;

        String value = "";

        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }


        if (val == true) {
            AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");

            ViewObject v2 = am.getAppEoAdds();
            int count = 0;
            String x = "";
            Row[] rowI = v2.getAllRowsInRange();

            for (Row r : rowI) {
                try {
                    x = r.getAttribute("ShipAddsDflt").toString();
                } catch (NullPointerException npe) {

                    x = "";
                }

                if (value.equalsIgnoreCase(x)) {
                    count = count + 1;
                }

            }

            if (mode.equals("A")) {
                if (count > 1) {

                    String msg2 = resolvElDCMsg("#{bundle['MSG.281']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            } else if (mode.equals("E")) {
                if (count > 1) {

                    String msg2 = resolvElDCMsg("#{bundle['MSG.281']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }

        }
    }

    public void defaultBillingAddressValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean)object;

        String value = "";

        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }


        if (val == true) {
            AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");

            ViewObject v2 = am.getAppEoAdds();
            int count = 0;
            String x = "";
            Row[] rowI = v2.getAllRowsInRange();

            for (Row r : rowI) {
                try {
                    x = r.getAttribute("BillAddsDflt").toString();
                } catch (NullPointerException npe) {

                    x = "";
                }

                if (value.equalsIgnoreCase(x)) {
                    count = count + 1;
                }

            }

            if (mode.equals("A")) {
                if (count > 1) {

                    String msg2 = resolvElDCMsg("#{bundle['MSG.281']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            } else if (mode.equals("E")) {
                if (count > 1) {

                    String msg2 = resolvElDCMsg("#{bundle['MSG.281']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
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

    public OperationBinding executeBinding(String var) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(var);
        return operationBinding;
    }

    public void setFormDisabled(String formDisabled) {
        this.formDisabled = formDisabled;
    }

    public String getFormDisabled() {
        return formDisabled;
    }

    public void setCreateButton(String createButton) {
        this.createButton = createButton;
    }

    public String getCreateButton() {
        return createButton;
    }

    public void setEditButton(String editButton) {
        this.editButton = editButton;
    }

    public String getEditButton() {
        return editButton;
    }

    public void setSaveButton(String saveButton) {
        this.saveButton = saveButton;
    }

    public String getSaveButton() {
        return saveButton;
    }

    public void setCancelButton(String cancelButton) {
        this.cancelButton = cancelButton;
    }

    public String getCancelButton() {
        return cancelButton;
    }


    public void setActiveReason(RichInputText activeReason) {
        this.activeReason = activeReason;
    }

    public RichInputText getActiveReason() {
        return activeReason;
    }

    public void setActiveDate(RichInputDate activeDate) {
        this.activeDate = activeDate;
    }

    public RichInputDate getActiveDate() {
        return activeDate;
    }


    public void setTransAddBindVar(RichInputText transAddBindVar) {
        this.transAddBindVar = transAddBindVar;
    }

    public RichInputText getTransAddBindVar() {
        return transAddBindVar;
    }

    public void setAddressTable(RichTable addressTable) {
        this.addressTable = addressTable;
    }

    public RichTable getAddressTable() {
        return addressTable;
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
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

    public void setAddIdQueryLov(RichInputListOfValues addIdQueryLov) {
        this.addIdQueryLov = addIdQueryLov;
    }

    public RichInputListOfValues getAddIdQueryLov() {
        return addIdQueryLov;
    }

    /* public void goToAdds(ActionEvent actionEvent) {
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
                   ViewObjectImpl v = am.getAppEoAdds();
                   Row re=v.getCurrentRow();
                   re.setAttribute("AddsId", "0");
    } */

    public void addressReturnListener(ReturnEvent returnEvent) {

        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        ViewObjectImpl v = am.getAddressForEoAddLOV1();
        v.executeQuery();
    }

    public String setEoAddsTableTF() {
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        ViewObjectImpl v = am.getAppEoAdds();
        Row addsRow = v.getCurrentRow();
        /* System.out.println("inside task flow method");
      //  INS_EO_ADDS(P_SLOCID     IN NUMBER,
                             //  P_EOID       IN APP.APP$EO.EO_ID%TYPE,
                              // P_ADDSID     IN APP.APP$ADDS$BK.ADDS_ID%TYPE,
                              // P_ADDS_TYPE  IN VARCHAR2,
                              // P_USRID      IN NUMBER)
    AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
    ViewObjectImpl v = am.getAppEoAdds();
    Row addsRow = v.getCurrentRow();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
       // String p_adds_id = (String)addsRow.getAttribute("AddsId");
        String p_adds_desc = (String)addsRow.getAttribute("TransAddName");
        Integer p_eo_id =null;
        if (addsRow.getAttribute("EoId") != null) {
            p_eo_id =Integer.parseInt(addsRow.getAttribute("EoId").toString());
        }
    String p_adds_id = null;
       if(p_adds_desc != null){
           System.out.println("vvvvvvvvvvvv");
        Row[] filteredRows = am.getAddressLOV1().getFilteredRows("Address", p_adds_desc);
      System.out.println("length          "+filteredRows.length);
        if (filteredRows.length > 0) {
            p_adds_id = filteredRows[0].getAttribute("AddressId").toString();
            System.out.println("-----------11-------"+p_adds_id);
        }
       }

        System.out.println("p_adds_id   "+p_adds_id+"p_eo_id  "+p_eo_id+"p_sloc_id   "+p_sloc_id+"p_adds_desc  "+p_adds_desc);

        if(p_adds_id != null && p_eo_id != null){
            v.setRangeSize(-1);
            v.getAllRowsInRange();
            RowQualifier rowQualifier =new RowQualifier(v);
            rowQualifier.setWhereClause(" SlocId="+p_sloc_id+" and EoId="+p_eo_id+" and AddsId= '"+p_adds_id+"'" );
            Row[] rows=v.getFilteredRows(rowQualifier);
            System.out.println("length 1111111111111"+rows.length);
            System.out.println("-----------"+rowQualifier.getExprStr());
            if(rows.length>0){
            System.out.println("bharat111");
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Address found for the supplier.", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

            rowQualifier.setWhereClause(null);
        }*/

        return "goToEoAdds";

    }

    public void addAddsIcon(ActionEvent actionEvent) {
       /*  AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        ViewObjectImpl v = am.getAppEoAdds();
        Row addsRow = v.getCurrentRow();
        addsRow.setAttribute("AddsId", null);
        addsRow.setAttribute("TransAddName", null); */
        setAddsFlag("H");

    }

    public void setAddsFlag(String addsFlag) {
        this.addsFlag = addsFlag;
    }

    public String getAddsFlag() {
        return addsFlag;
    }

    public void addPopLaunchListener(LaunchPopupEvent launchPopupEvent) {
        // Add event code here...where a.adds_id not in (select b.adds_ID from APP$EO$ADDS b where b.eo_id=nvl(:EoIdBindVar,b.eo_id) and b.sloc_id=nvl(:SlocIdBindVar,b.sloc_id))
        /*   AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        String eoId=resolvEl("#{pageFlowScope.EO_ID}").toString();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        am.getAddressForEoAddLOV1().setWhereClause("ADDRESS_ID not in (select b.adds_ID from APP$EO$ADDS b where b.eo_id=nvl("+eoId+",b.eo_id) and b.sloc_id=nvl("+p_sloc_id+",b.sloc_id))");
        am.getAddressForEoAddLOV1().executeQuery();  */
        /*     AppEoAddsVORowImpl a=(AppEoAddsVORowImpl)am.getAddressForEoAddLOV1().getCurrentRow();
         a.getAddressForEoAddLOV1().executeQuery();
     */

        /*  RichInputListOfValues lovComp = (RichInputListOfValues)launchPopupEvent.getComponent();
     FacesCtrlLOVBinding.ListOfValuesModelImpl lovModel = null;
      lovModel = (FacesCtrlLOVBinding.ListOfValuesModelImpl) lovComp.getModel();
     lovModel.performQuery(lovModel.getQueryDescriptor()); */

    }

    public void addIdDupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*   AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        String eoId=resolvEl("#{pageFlowScope.EO_ID}").toString();
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        am.getAddressForEoAddLOV1().setWhereClause("ADDRESS_ID not in (select b.adds_ID from APP$EO$ADDS b where b.eo_id=nvl("+eoId+",b.eo_id) and b.sloc_id=nvl("+p_sloc_id+",b.sloc_id))");
        am.getAddressForEoAddLOV1().executeQuery();

        Row curr= am.getAppEoAdds().getCurrentRow();
        RowSetIterator rsi= am.getAddressForEoAddLOV1().createRowSetIterator(null);
        String dup="Y";
        while(rsi.hasNext()){
            Row rw=rsi.next();
            if(rw!=curr){
             if(object.toString().equalsIgnoreCase(rw.getAttribute("AddressId").toString())){
                dup="N";
             }
            }
        }
        rsi.closeRowSetIterator();
        if("Y".equalsIgnoreCase(dup)){
            String msg2 = "Duplicate Address";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }

         */
        String addr = object.toString();
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
        ViewObject v = am.getAppEoAdds();
        int totalCount = v.getRowCount(); //get ALL rows
        int rangeSize = v.getRangeSize(); //all in range
        v.setRangeSize(totalCount);
        Row[] rArray = v.getAllRowsInRange();

        //RowSetIterator rsi=v.createRowSetIterator(null);
        int count = 0;

        String currLang = "";
        Row cRow = v.getCurrentRow();
        for (Row r : rArray) {

            if (!r.equals(cRow)) {
                try {
                    currLang = r.getAttribute("AddsId").toString();
                } catch (NullPointerException npe) {

                    currLang = "";
                }

                if (currLang.equalsIgnoreCase(addr)) {
                    count = count + 1;
                }
            }

        }
        v.setRangeSize(rangeSize); //set to original range


        String msg2 = "";
        if (count > 0) {
            msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }

    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setDefaultbillingAddress(RichSelectBooleanCheckbox defaultbillingAddress) {
        this.defaultbillingAddress = defaultbillingAddress;
    }

    public RichSelectBooleanCheckbox getDefaultbillingAddress() {
        return defaultbillingAddress;
    }

    public void setDefaultShippingAddress(RichSelectBooleanCheckbox defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public RichSelectBooleanCheckbox getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setCountadds(Integer countadds) {
        this.countadds = countadds;
    }

    public Integer getCountadds() {
        return countadds;
    }
}
