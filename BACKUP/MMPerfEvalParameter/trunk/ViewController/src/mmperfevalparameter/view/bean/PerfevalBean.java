package mmperfevalparameter.view.bean;

import java.io.Serializable;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import oracle.jbo.domain.Number;
import mmperfevalparameter.model.services.perfEvalparamAMImpl;
import mmperfevalparameter.model.views.MmEvalParamSetVOImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichShowDetail;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import oracle.sql.NUMBER;


public class PerfevalBean {
    private RichShowDetail showDetailBinding;
    private String SetparamMode = "V";
    private String ParamMode = "V";
    private boolean paramSetDisable = true;
    private boolean paramDisable = true;
    private boolean wtgPageDisable = false;
    private boolean createParamSet = false;
    private boolean editSet = false;
    private boolean setwtg = false;
    private boolean saveSet = true;
    private boolean cancelSet = true;
    private boolean createparam = false;
    private boolean editParam = false;
    private boolean saveParam = true;
    private boolean cancelparam = true;
    private RichCommandLink supplierLink;
    private RichCommandLink customerLinkBind;
    private RichCommandLink quotationAnaLinkBind;
    private RichTable paramSetTableBind;
    private String disableEdit_wtg ="W";
    private String disableSave_wtg ="M";


    public PerfevalBean() {
    }
    private static int VARCHAR = Types.VARCHAR;


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void createParamSetButton(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        MmEvalParamSetVOImpl vo = (MmEvalParamSetVOImpl)am.getMmEvalParamSet1();
        vo.bindVar(null);
        vo.executeQuery();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        this.SetparamMode = "A";
        this.paramSetDisable = false;

        this.createParamSet = true;
        this.editSet = true;
        this.saveSet = false;
        this.setwtg = true;
        this.cancelSet = false;
        this.createparam = true;
        this.editParam = true;
        this.saveParam = true;
        this.cancelparam = true;
        supplierLink.setInlineStyle("font-weight:bold;color:Gray");
        customerLinkBind.setInlineStyle("font-weight:bold;color:Gray");
        quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:Gray");
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierLink);
        AdfFacesContext.getCurrentInstance().addPartialTarget(customerLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotationAnaLinkBind);
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
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
    public String resolvEl(String data){
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           String Message=valueExp.getValue(elContext).toString();
           return Message;
         }
    public void saveButton(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        if (SetparamMode.equalsIgnoreCase("A")) {
            //FUNCTION generate_id(P_SLOCID IN NUMBER, P_CLDID IN VARCHAR2, P_ORGID IN VARCHAR2, p_TableName IN VARCHAR2) RETURN VARCHAR2;
            String tableName = "MM$EVAL$PARAM$SET";
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            
            String id =
                (String)(callStoredFunction(VARCHAR, "MM.FN_MM_GEN_ID(?,?,?,?)", new Object[] {slocid , cldid, orgid, tableName}));

            ViewObject v1 = am.getMmEvalParamSet1();
            Row currRow = v1.getCurrentRow();
            currRow.setAttribute("ParamSetId", id);
            supplierLink.setInlineStyle("font-weight:bold;color:Navy");
            customerLinkBind.setInlineStyle("font-weight:bold;color:Navy");
            quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:Navy");
        }
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.91']}").toString(),
                             null);
        FacesContext fc = FacesContext.getCurrentInstance();
        
        fc.addMessage(null, msg);
        if (operationBinding.getErrors().isEmpty()) {
            this.paramSetDisable = true;
            this.SetparamMode = "V";

            this.createParamSet = false;
            this.editSet = false;
            this.saveSet = true;
            this.setwtg = false;
            this.cancelSet = true;
            this.createparam = false;
            this.editParam = false;
            this.saveParam = true;
            this.cancelparam = true;
        }
       
    }

    public void addparameterButton(ActionEvent actionEvent) {
        int count = quotationCheck();
        if (count > 0) {
            FacesMessage msg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.108']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
            operationBinding.execute();
            this.ParamMode = "A";
            this.paramDisable = false;
            showDetailBinding.setDisclosed(true);

            this.createParamSet = true;
            this.editSet = true;
            this.saveSet = true;
            this.setwtg = true;
            this.cancelSet = true;
            this.createparam = true;
            this.editParam = true;
            this.saveParam = false;
            this.cancelparam = false;
        }
    }

    public void saveParamButton(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        if (ParamMode.equalsIgnoreCase("A")) {
            //FUNCTION generate_id(P_SLOCID IN NUMBER, P_CLDID IN VARCHAR2, P_ORGID IN VARCHAR2, p_TableName IN VARCHAR2) RETURN VARCHAR2;
            String tableName = "MM$EVAL$PARAM";
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            
            String id =
                (String)(callStoredFunction(VARCHAR, "MM.FN_MM_GEN_ID(?,?,?,?)", new Object[] {slocid , cldid, orgid, tableName}));

            ViewObject v1 = am.getMmEvalParam1();
            Row currRow = v1.getCurrentRow();
            currRow.setAttribute("ParamId", id);
        }
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
        operationBinding.execute();
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, resolvElDCMsg("#{bundle['MSG.91']}").toString(),
                             null);
        FacesContext fc = FacesContext.getCurrentInstance();
        
        fc.addMessage(null, msg);
        
        if (operationBinding.getErrors().isEmpty()) {
            this.paramDisable = true;
            this.ParamMode = "V";

            this.createParamSet = false;
            this.editSet = false;
            this.saveSet = true;
            this.setwtg = false;
            this.cancelSet = true;
            this.createparam = false;
            this.editParam = false;
            this.saveParam = true;
            this.cancelparam = true;
        }
    }


    public void weightagesValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number wtg = (oracle.jbo.domain.Number)object;
        System.out.println("Value of Weightage"+wtg);
        if(wtg.intValue()==0) {
            System.out.println("Zero");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Weightage will not be zero",null));
        }
        if (wtg.intValue() < 0 || wtg.intValue() > 100) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.284']}").toString()
                                                          , null));
        }
    }


    public void saveWeithage(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        ViewObject vo = am.getMmEvalParam1();
        //System.out.println("in the CheckweghtValue");
      //--------------------Code for Checking Paramter Value Greater than   
        
        ViewObjectImpl view=am.getMmEvalParam1();
        RowSetIterator r2=view.createRowSetIterator(null);
        while(r2.hasNext()) {
        Row r1=r2.next();
         Number w =(Number)r1.getAttribute("ParamWtg");
         System.out.println(w);
        oracle.jbo.domain.Number h = new oracle.jbo.domain.Number(0);
         if(w.equals(h)){
             FacesMessage message = new FacesMessage("Parameter Weightage Should be Greater than Zero");   
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, message); 
               return;
         }
        }
//=---------------------------------------------------------------------------------------  
        oracle.jbo.domain.Number hund = new oracle.jbo.domain.Number(100);
        int totalCount = vo.getRowCount(); //get ALL rows
        int rangeSize = vo.getRangeSize(); //all in range
        vo.setRangeSize(totalCount);

        Row[] rr = vo.getAllRowsInRange();
        oracle.jbo.domain.Number wtag = new oracle.jbo.domain.Number(0);
        FacesContext fc = FacesContext.getCurrentInstance();
        for (Row r : rr) {
            // if("Y".equalsIgnoreCase(r.getAttribute("Actv").toString())){
            Object wt = r.getAttribute("ParamWtg");
            if (wt == null) {
                wt = 0;
            }
            wtag = wtag.add((oracle.jbo.domain.Number)wt);

        }
        if (wtag.compareTo(hund) != 0) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.284']}").toString(),
                                 null);
            fc.addMessage(null, msg);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.91']}").toString(),
                                 null);
            fc.addMessage(null, msg);
            
            if (operationBinding.getErrors().isEmpty()) {
                this.setWtgPageDisable(true);
            }
            setDisableEdit_wtg("X");
            setDisableSave_wtg("N");
        }
     

    }

    public void minRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer minVal = Integer.parseInt(object.toString());
        if (minVal.intValue() <= 0) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.69']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void maxRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        Integer maxVal = Integer.parseInt(object.toString());
        Integer minVal = 0;
        try {
            minVal = Integer.parseInt(am.getMmEvalParamSet1().getCurrentRow().getAttribute("MinRate").toString());

        } catch (NullPointerException npe) {
            minVal = 0;
        }
        if (minVal.intValue() >= maxVal.intValue()) {

            String msg2 = resolvElDCMsg("#{bundle['MSG.66']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
        if (maxVal.intValue() > 100) {

            //String msg2 = "Max Rate should be Less than or Equal to 100.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1187']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }


    public void parameterCancelButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmEvalParamSet1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
        operationBinding1.execute();
        if (parentKey != null) {
            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }
        this.paramDisable = true;

        this.createParamSet = false;
        this.editSet = false;
        this.saveSet = true;
        this.setwtg = false;
        this.cancelSet = true;
        this.createparam = false;
        this.editParam = false;
        this.saveParam = true;
        this.cancelparam = true;
    }

    public void exitButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        if (SetparamMode.equalsIgnoreCase("A")) {
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding1.execute();
            supplierLink.setInlineStyle("font-weight:bold;color:Navy");
            customerLinkBind.setInlineStyle("font-weight:bold;color:Navy");
            quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:Navy");

        } else {
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmEvalParamSet1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding1.execute();
            if (parentKey != null) {
                if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }
        this.paramSetDisable = true;

        this.createParamSet = false;
        this.editSet = false;
        this.saveSet = true;
        this.setwtg = false;
        this.cancelSet = true;
        this.createparam = false;
        this.editParam = false;
        this.saveParam = true;
        this.cancelparam = true;

    }

    public void editparamButton(ActionEvent actionEvent) {
        int count = quotationCheck();
        if (count > 0) {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.285']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {
            showDetailBinding.setDisclosed(true);
            this.paramDisable = false;
            this.ParamMode = "E";

            this.createParamSet = true;
            this.editSet = true;
            this.saveSet = true;
            this.setwtg = true;
            this.cancelSet = true;
            this.createparam = true;
            this.editParam = true;
            this.saveParam = false;
            this.cancelparam = false;
        }
    }

    public void editSetbutton(ActionEvent actionEvent) {
        int count = quotationCheck();
        if (count > 0) {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.285']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {
        this.paramSetDisable = false;
        this.SetparamMode = "E";

        this.createParamSet = true;
        this.editSet = true;
        this.saveSet = false;
        this.setwtg = true;
        this.cancelSet = false;
        this.createparam = true;
        this.editParam = true;
        this.saveParam = true;
        this.cancelparam = true;
    }
    }

    public void setShowDetailBinding(RichShowDetail showDetailBinding) {
        this.showDetailBinding = showDetailBinding;
    }

    public RichShowDetail getShowDetailBinding() {
        return showDetailBinding;
    }

    public void setParamSetDisable(boolean paramSetDisable) {
        this.paramSetDisable = paramSetDisable;
    }

    public boolean isParamSetDisable() {
        return paramSetDisable;
    }

    public void setParamDisable(boolean paramDisable) {
        this.paramDisable = paramDisable;
    }

    public boolean isParamDisable() {
        return paramDisable;
    }

    public void setWtgPageDisable(boolean wtgPageDisable) {
        this.wtgPageDisable = wtgPageDisable;
    }

    public boolean isWtgPageDisable() {
        return wtgPageDisable;
    }


    public void setCreateParamSet(boolean createParamSet) {
        this.createParamSet = createParamSet;
    }

    public boolean isCreateParamSet() {
        return createParamSet;
    }

    public void setEditSet(boolean editSet) {
        this.editSet = editSet;
    }

    public boolean isEditSet() {
        return editSet;
    }

    public void setSetwtg(boolean setwtg) {
        this.setwtg = setwtg;
    }

    public boolean isSetwtg() {
        return setwtg;
    }

    public void setSaveSet(boolean saveSet) {
        this.saveSet = saveSet;
    }

    public boolean isSaveSet() {
        return saveSet;
    }

    public void setCancelSet(boolean cancelSet) {
        this.cancelSet = cancelSet;
    }

    public boolean isCancelSet() {
        return cancelSet;
    }

    public void setCreateparam(boolean createparam) {
        this.createparam = createparam;
    }

    public boolean isCreateparam() {
        return createparam;
    }

    public void setEditParam(boolean editParam) {
        this.editParam = editParam;
    }

    public boolean isEditParam() {
        return editParam;
    }

    public void setSaveParam(boolean saveParam) {
        this.saveParam = saveParam;
    }

    public boolean isSaveParam() {
        return saveParam;
    }

    public void setCancelparam(boolean cancelparam) {
        this.cancelparam = cancelparam;
    }

    public boolean isCancelparam() {
        return cancelparam;
    }

    public void editWtgButton(ActionEvent actionEvent) {
        setDisableEdit_wtg("W");
        setDisableSave_wtg("M");
        this.wtgPageDisable = false;
    }

    public void cancelWtgButton(ActionEvent actionEvent) {
        
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmEvalParamSet1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        am.getMmEvalParam1().setWhereClause(null);
        am.getMmEvalParam1().executeQuery();
       
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        operationBinding1.execute();

        MmEvalParamSetVOImpl vo = (MmEvalParamSetVOImpl)am.getMmEvalParamSet1();
        vo.bindVar(null);
        vo.executeQuery();
        if(parentIter.getRowSetIterator().getRow(parentKey)!=null){  //check condition else gives exception in add mode.
                   parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
              }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramSetTableBind);
        this.wtgPageDisable = true;
        setDisableEdit_wtg("W");
        setDisableSave_wtg("M");
    }

    public void setWtgButton(ActionEvent actionEvent) {

    }

    public void actvValueChangeListener(ValueChangeEvent vce) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();


            ViewObject v1 = am.getMmEvalParamSet1();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date)Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }

        }

    }

    public void actvParamValueChangeListener(ValueChangeEvent vce) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        /** for check waightage value
         * */
        ViewObject vo = am.getMmEvalParam1();
        String oldval = vce.getOldValue().toString();

        String newVal = vce.getNewValue().toString();
        oracle.jbo.domain.Number hund = new oracle.jbo.domain.Number(100);
        int totalCount = vo.getRowCount(); //get ALL rows
        int rangeSize = vo.getRangeSize(); //all in range
        Row row = vo.getCurrentRow();
        vo.setRangeSize(totalCount);

        Row[] rr = vo.getAllRowsInRange();
        Number zero = new Number(0); 
        oracle.jbo.domain.Number wtag = new oracle.jbo.domain.Number(0);
        FacesContext fc = FacesContext.getCurrentInstance();
        for (Row r : rr) {
             if("Y".equalsIgnoreCase(r.getAttribute("Actv").toString())){
            Object wt = r.getAttribute("ParamWtg");
            if (wt == null) {
                wt = 0;
            }
            wtag = wtag.add((oracle.jbo.domain.Number)wt);

        }
        }
        Number currWtgValue = (Number)row.getAttribute("ParamWtg");
        Number value = wtag.add(currWtgValue);
        if(newVal.equalsIgnoreCase("true")){
        if (value.compareTo(hund)== 1) {
          if(zero.compareTo(currWtgValue) == -1 ){
                row.setAttribute("ParamWtg", zero);
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, resolvElDCMsg("#{bundle['MSG.286']}").toString(),
                                     null);
                fc.addMessage(null, msg);
            }
            else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, resolvElDCMsg("#{bundle['MSG.286']}").toString(),
                                 null);
            fc.addMessage(null, msg);
        }
        }
        }else if (vce.getNewValue() != null) {
           


           
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date)Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }

        }

    }

    public void addParamFrmWtgButton(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        am.getMmEvalParam1().setWhereClause(null);
        am.getMmEvalParam1().executeQuery();
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmEvalParamSet1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        operationBinding1.execute();
        if(parentIter.getRowSetIterator().getRow(parentKey)!=null){  //check condition else gives exception in add mode.
                   parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
              }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramSetTableBind);
        setDisableEdit_wtg("W");
        setDisableSave_wtg("M");
        //---------------------------------create from wtg -------------------------
        int count = quotationCheck();
        if (count > 0) {
            FacesMessage msg =
                new FacesMessage(resolvElDCMsg("#{bundle['MSG.108']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {

            this.ParamMode = "A";
            this.paramDisable = false;
            showDetailBinding.setDisclosed(true);

            this.createParamSet = true;
            this.editSet = true;
            this.saveSet = true;
            this.setwtg = true;
            this.cancelSet = true;
            this.createparam = true;
            this.editParam = true;
            this.saveParam = false;
            this.cancelparam = false;

    }
    }

    //To check Quotation Analysis

    public int quotationCheck() {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        int countLength = 0;
        String Cat_Flag = "";
        String paramSetId = "";
        ViewObject v1 = am.getMmEvalParamSet1();
        Row curRow = v1.getCurrentRow();


        Cat_Flag = curRow.getAttribute("CatFlg").toString();

        paramSetId = curRow.getAttribute("ParamSetId").toString();


        // For Quotation Analysis
        if (Cat_Flag.equalsIgnoreCase("Q")) {
            ViewObject v2 = am.getQuotAnaRslt1();
            Row row[] = v2.getFilteredRows("ParamSetId", paramSetId);

            countLength = row.length;
        }
        // For Supplier Analysis
        else if (Cat_Flag.equalsIgnoreCase("S")) {
            ViewObject v3 = am.getmmEoPerfRslt1();
            Row row[] = v3.getFilteredRows("ParamSetId", paramSetId);

            countLength = row.length;
        }
        return countLength;

    }

    public void paramSetNMValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.287']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            ViewObject v = am.getMmEvalParamSet1();


            // RowSet rs=v.getRowSet();
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.288']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                             resolvElDCMsg("#{bundle['MSG.289']}").toString()));
            }


            Row cRow = v.getCurrentRow();
            int count = 0;
            String currLang = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currLang = r.getAttribute("ParamSetNm").toString();
                    } catch (NullPointerException npe) {
                        System.out.println("NPE:" + npe);
                        currLang = "";
                    }
                    if (currLang.equalsIgnoreCase(langDesc)) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range


            if (count > 0) {
                msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

        }
    }

    public void supplierLink(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        MmEvalParamSetVOImpl vo = (MmEvalParamSetVOImpl)am.getMmEvalParamSet1();
        vo.bindVar("S");
        vo.executeQuery();
        supplierLink.setInlineStyle("font-weight:bold;color:red");
        customerLinkBind.setInlineStyle("font-weight:bold;color:blue");
        quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:blue");
    }

    public void customerLik(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        MmEvalParamSetVOImpl vo = (MmEvalParamSetVOImpl)am.getMmEvalParamSet1();
        vo.bindVar("C");
        vo.executeQuery();
        supplierLink.setInlineStyle("font-weight:bold;color:blue");
        customerLinkBind.setInlineStyle("font-weight:bold;color:red");
        quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:blue");
    }

    public void quotAnaLink(ActionEvent actionEvent) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        MmEvalParamSetVOImpl vo = (MmEvalParamSetVOImpl)am.getMmEvalParamSet1();
        vo.bindVar("Q");
        vo.executeQuery();
        supplierLink.setInlineStyle("font-weight:bold;color:blue");
        customerLinkBind.setInlineStyle("font-weight:bold;color:blue");
        quotationAnaLinkBind.setInlineStyle("font-weight:bold;color:red");
    }

    public String setWtgActionButton() {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        int count = quotationCheck();
        if (count > 0) {
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.285']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
            return null;
        } else {
            am.getMmEvalParam1().setWhereClause("ACTV = 'Y'");
            am.getMmEvalParam1().executeQuery();
            this.setWtgPageDisable(false);
            return "setWt";
        }

    }

    public void setSupplierLink(RichCommandLink supplierLink) {
        this.supplierLink = supplierLink;
    }

    public RichCommandLink getSupplierLink() {
        return supplierLink;
    }

    public void setCustomerLinkBind(RichCommandLink customerLinkBind) {
        this.customerLinkBind = customerLinkBind;
    }

    public RichCommandLink getCustomerLinkBind() {
        return customerLinkBind;
    }

    public void setQuotationAnaLinkBind(RichCommandLink quotationAnaLinkBind) {
        this.quotationAnaLinkBind = quotationAnaLinkBind;
    }

    public RichCommandLink getQuotationAnaLinkBind() {
        return quotationAnaLinkBind;
    }

    public void setParamSetTableBind(RichTable paramSetTableBind) {
        this.paramSetTableBind = paramSetTableBind;
    }

    public RichTable getParamSetTableBind() {
        return paramSetTableBind;
    }

    public void paramNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        perfEvalparamAMImpl am = (perfEvalparamAMImpl)resolvElDC("perfEvalparamAMDataControl");
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.287']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;
            ViewObject v = am.getMmEvalParam1();
            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.288']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                             resolvElDCMsg("#{bundle['MSG.289']}").toString()));
            }


            Row cRow = v.getCurrentRow();
            int count = 0;
            String currLang = "";

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                  //  try {
                        currLang = r.getAttribute("ParamNm").toString();
                  //  } catch (NullPointerException npe) {
                 //       System.out.println("NPE:" + npe);
                //        currLang = "";
                  //  }
                    if (currLang.equalsIgnoreCase(langDesc)) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range


            if (count > 0) {
                msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

        }

    }

    public void setDisableEdit_wtg(String disableEdit_wtg) {
        this.disableEdit_wtg = disableEdit_wtg;
    }

    public String getDisableEdit_wtg() {
        return disableEdit_wtg;
    }

    public void setDisableSave_wtg(String disableSave_wtg) {
        this.disableSave_wtg = disableSave_wtg;
    }

    public String getDisableSave_wtg() {
        return disableSave_wtg;
    }

    
   
    
}
