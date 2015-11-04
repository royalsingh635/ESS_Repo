package pricepolicyapp.view.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.event.DialogEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;
import oracle.jbo.domain.Number;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

public class PriceBean {
    private RichSelectOneChoice discTypeBind;
    private RichTable entitytblBind;
    private RichPopup suppPopupBind;
    private String plc_id;
    private RichInputListOfValues supplierIdBind;

    public PriceBean() {
    }
    String cldid=resolvEl1("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    String orgid=resolvEl1("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    Integer slocid=Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    Integer userid = Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_USR}").toString());
    String hoOrgid=resolvEl1("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    String glbl_mode=resolvEl1("#{pageFlowScope.mode}").toString();
    
    String edit_mode="C";
    public String resolvEl1(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void addActionListner(ActionEvent actionEvent) {
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","C");
            edit_mode="C";
            
        }
    }

    public void editActionListner(ActionEvent actionEvent) {
        // Add event code here...
      OperationBinding obind=getBindings().getOperationBinding("chkEditables");
      obind.execute();
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","C");
            edit_mode="E";

    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 

    public void saveActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
       /*  if(resolvEl1("#{pageFlowScope.PARAM_EO_ID}")!=null){
            if(glbl_mode.equalsIgnoreCase("C") && edit_mode.equalsIgnoreCase("C")){
        Integer eoId=Integer.parseInt(resolvEl1("#{pageFlowScope.PARAM_EO_ID}").toString());
        if(eoId!=null && eoId!=-1){
            OperationBinding ObindSupplier =getBindings().getOperationBinding("insertSupplier");
            ObindSupplier.getParamsMap().put("supplierId",eoId );
            ObindSupplier.execute();
        }
            }
        } */
        OperationBinding operationBinding =getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.818']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        OperationBinding operationBinding =getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void NameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String currName="";
        if(object!=null){
            currName=object.toString();
        }
                        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
                        CharSequence inputStr = object.toString();
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr);
                        String error=resolvElDCMsg("#{bundle['MSG.1056']}").toString();

                        if (matcher.matches()) {
                    
                        OperationBinding operationBinding =getBindings().getOperationBinding("priceNameValid");
                        operationBinding.getParamsMap().put("Name", currName);
                        operationBinding.execute();
                        if(operationBinding.getErrors().isEmpty()){
                            String temp=operationBinding.getResult().toString();
                            if("Y".equalsIgnoreCase(temp)){
                                
                            }else if("N".equalsIgnoreCase(temp)){
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Policy Name Already Exists", null));  
                            }
                        }
                        }
                        else {
                                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }
        

    }
    public String resolvEl(String data)
     {
             FacesContext fc = FacesContext.getCurrentInstance();
             Application app = fc.getApplication();
             ExpressionFactory elFactory = app.getExpressionFactory();
             ELContext elContext = fc.getELContext();
             ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
             String Message = valueExp.getValue(elContext).toString();
             return Message;
         }

    public void actvValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
            if("false".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())){
                OperationBinding operationBinding =getBindings().getOperationBinding("setinacivedt");
                operationBinding.execute();   
            }
        }
    }

    public void SupplierAddActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
        OperationBinding op =getBindings().getOperationBinding("chkEoAvailable");
        op.execute();
       
        String flag=null;
        if(op.getErrors().isEmpty()){
        
            flag=op.getResult().toString();
          // flag.split("-", 2);
          
         //   System.out.println("-------- "+flag);
        }else{
         //   System.out.println(op.getErrors());
            }
     //   System.out.println("check editable value is "+flag);
        if(flag.equalsIgnoreCase("Y")){
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("addSupplier");
        operationBinding.execute();
        }else{
            //showpopup
            plc_id=flag;
            showPopup(suppPopupBind, true);
        }
    }
    
    public String getPlcId(){
        return plc_id;
    }
    private void showPopup(RichPopup pop, boolean visible) {
    try {
    FacesContext context = FacesContext.getCurrentInstance();
    if (context != null && pop != null) {
    String popupId = pop.getClientId(context);
    if (popupId != null) {
    StringBuilder script = new StringBuilder();
    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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
    public void SupplierSaveAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding =getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1225']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void setEdit_mode(String edit_mode) {
        this.edit_mode = edit_mode;
    }

    public String getEdit_mode() {
        return edit_mode;
    }

    public void supplierDelActionListen(ActionEvent actionEvent) {
        // Add event code here...
         OperationBinding operationBinding =getBindings().getOperationBinding("delSupplier");
        operationBinding.execute(); 
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(entitytblBind);
        
    }

    public void suppNameValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        //EoNm
        if(object!=null){
            OperationBinding operationBinding =getBindings().getOperationBinding("supplierNameValid");
            operationBinding.getParamsMap().put("suppName", object.toString());
            operationBinding.execute();
            if(operationBinding.getErrors().isEmpty()){
                String temp=operationBinding.getResult().toString();
              //  System.out.println("supplier out put "+temp);
                String error=resolvElDCMsg("#{bundle['MSG.1226']}").toString();
                if("Y".equalsIgnoreCase(temp)){
                 }else if("N".equalsIgnoreCase(temp)){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }
            }
        }
    }

    public void DiscValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
     //   System.out.println("new object value is "+object);
        if (object != null) {
            Number i = (Number)object;
            if (i.compareTo(new Number(0)) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.484']}").toString(), null));
            }

            boolean flag = isPrecisionValid(26, 6, i);
            if (flag) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.57']}").toString(), null));
            }
//System.out.println("discTypeBind.getValue() "+discTypeBind.getValue()+" object is "+i);
            if (discTypeBind.getValue() != null) {
                String discType = discTypeBind.getValue().toString();
              //  System.out.println("now the disc type value is " + discType);
                if (discType.equalsIgnoreCase("P")) {
                    if(i.compareTo(new Number(100))==1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Percent from 1 to 100", null));
                    }
                }
            }
        }
    }
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
    JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
    vc.setPrecision(precision);
    vc.setScale(scale);
    return vc.validateValue(total);
    }

    public void setDiscTypeBind(RichSelectOneChoice discTypeBind) {
        this.discTypeBind = discTypeBind;
    }

    public RichSelectOneChoice getDiscTypeBind() {
        return discTypeBind;
    }

    public void setEntitytblBind(RichTable entitytblBind) {
        this.entitytblBind = entitytblBind;
    }

    public RichTable getEntitytblBind() {
        return entitytblBind;
    }

    public void entityDltDialogListner(DialogEvent dialogEvent) {
        /* OperationBinding operationBinding =getBindings().getOperationBinding("delSupplier");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(entitytblBind); */
        // Add event code here...
    }

    public void setSuppPopupBind(RichPopup suppPopupBind) {
        this.suppPopupBind = suppPopupBind;
    }

    public RichPopup getSuppPopupBind() {
        return suppPopupBind;
    }

    public void suppDctvDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().name().equals("yes")){
            OperationBinding obind =getBindings().getOperationBinding("setEndDate");
            obind.execute();
            
     
        OperationBinding operationBinding =getBindings().getOperationBinding("addSupplier");
        operationBinding.execute();
        supplierIdBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(entitytblBind);
        }else if(dialogEvent.getOutcome().name().equals("no")){
            supplierIdBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(supplierIdBind);
                
        }
    }

    public void setSupplierIdBind(RichInputListOfValues supplierIdBind) {
        this.supplierIdBind = supplierIdBind;
    }

    public RichInputListOfValues getSupplierIdBind() {
        return supplierIdBind;
    }
}
