package appgraphsetup.view.bean;

import appgraphsetup.model.module.AppGraphSetupAMImpl;

import appgraphsetup.model.view.AppGrphTrndTmpVORowImpl;

import java.io.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class GraphTrendConfigBean {
    private RichTable paramTable;

    public GraphTrendConfigBean() {
    }
    private String mode = modeGet();
    private Long Graphid=getGraphId();
    private Integer SlocId=getslocId();

    public static String modeGet() {
        return (String)resolveElExp("#{pageFlowScope.mode}");
    }
    public static Long getGraphId() {
        return Long.parseLong(resolveElExp("#{pageFlowScope.GRAPH_ID}").toString());
    }
    public static Integer getslocId() {
        return Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
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

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public String CancelAction() {
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        return "Cancel";
    }

    public String SaveAction() {

        String RetVal = null;
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();

        if (operationBinding.getErrors().isEmpty()) {

            AppGraphSetupAMImpl am = (AppGraphSetupAMImpl)resolvElDC("AppGraphSetupAMDataControl");
            ViewObject v1 = am.getAppGrphTrndTmpVO();

            AppGrphTrndTmpVORowImpl curr = (AppGrphTrndTmpVORowImpl)v1.getCurrentRow();

            if (mode.equals("A")) {

                String a = curr.fromTemp("I");
                if (a.equals("Y")) {
                    OperationBinding operationBindingcomm = bindings.getOperationBinding("Commit");
                    operationBindingcomm.execute();
                    if (operationBindingcomm.getErrors().isEmpty()) {
                        RetVal = "Cancel";
                    }
                }
                else{
                    String msg = "Something went wrong.You might giving wrong trend name or  duplicate trend name.Please contact ESS.";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            } else if (mode.equals("V")) {


                if ("Y".equals(curr.getSaveAs())) {
                    String a= curr.fromTemp("S");
                    if(a.equals("Y")){
                        OperationBinding operationBindingcomm = bindings.getOperationBinding("Commit");
                        operationBindingcomm.execute();
                        if (operationBindingcomm.getErrors().isEmpty()) {
                            RetVal = "Cancel";
                        }
                    }
                    
                    else{
                        String msg = "Something went wrong.You might giving wrong trend name or  duplicate trend name.Please contact ESS.";
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    String a= curr.fromTemp("U");
                    if(a.equals("Y")){
                        OperationBinding operationBindingcomm = bindings.getOperationBinding("Commit");
                        operationBindingcomm.execute();
                        if (operationBindingcomm.getErrors().isEmpty()) {
                            RetVal = "Cancel";
                        }
                    }
                    else{
                        String msg = "Something went wrong.You might giving wrong trend name or  duplicate trend name.Please contact ESS.";
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }


            }
        }
        return RetVal;
    }

    public String CreateGraphTrendParamAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTable);
        return null;
    }

    public String DeleteGrphTrndAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTable);
        return null;
    }


    public void trendNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      String trendname=(String)object;
        
      if(trendname.startsWith(" ") || trendname.endsWith(" ")){
          String msg2="White space at start and end is not allowed.";
          FacesMessage message2 = new FacesMessage(msg2);
          message2.setSeverity(FacesMessage.SEVERITY_ERROR);
          throw new ValidatorException(message2);
      }
                    int openB=0;
                    int closeB=0;
                    boolean closeFg=false;
                    
                char[] xx=trendname.toCharArray();
                
                for(char c:xx){
                    
                        if(c=='('){
                            
                            openB=openB+1;
                            
                        }else if(c==')'){
                            
                            closeB=closeB+1;
                            
                        }
                    
                    if(closeB>openB){
                        closeFg=true; //1.no. of closed brackets will not be more than open brackets at any given time.
                    }
                }
               
                //2.if openB=0 then no. of closing and opening brackets equal || 3.opening bracket must always come before closing brackets
                //4.closing brackets must not come before first occurrence of openning bracket
                if(openB!=closeB ||closeFg==true||(trendname.lastIndexOf("(")>trendname.lastIndexOf(")")) ||(trendname.indexOf(")")<trendname.indexOf("(")) ){
                    String msg2="Brackets not closed properly.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
                
                //5.check for empty brackets
                if(trendname.contains("()")){
                        String msg2="Empty Brackets are not allowed.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                }     
                //check for wrong combo
                    if(trendname.contains("(.")||trendname.contains("(-")||trendname.contains("-)")){
                        String msg2="Invalid language name.Check content inside brackets.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
                    
                    openB=0;
                    closeB=0;
                    closeFg=false; 
                     
                   
                
                // RowSet rs=v.getRowSet();
              
                
                //check for valid DESCRIPTION.. Char+Numbers allowed.Can have brackets and hyphen or dot .No two hyphens/dots/hyphen+dots can be adjacent.
                     
                        String expression = "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\-|\\:|\\@|\\/|\\\\|$))?)*$";

                      
                        CharSequence inputStr = trendname;
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr); 
                        String error="Invalid Description";
                        
                        if (matcher.matches()) {  }
                        else {
                             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }  
                     
                //check for duplicate rows
               
        AppGraphSetupAMImpl am = (AppGraphSetupAMImpl)resolvElDC("AppGraphSetupAMDataControl");
        ViewObject v=am.getAppGrphTrnd();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        
        int count=0;
        
        while (createRowSetIterator.hasNext()) {
            Row row = createRowSetIterator.next();
            String getName=row.getAttribute("GrphTrndNm").toString();
            Long getgraphid=Long.parseLong(row.getAttribute("GrphId").toString());
            Integer getslocid=Integer.parseInt(row.getAttribute("GrphSlocId").toString());
            
            
            
            if(getName.equals(trendname) && Graphid==getgraphid && SlocId==getslocid){
                count=count+1;
            }
           
        }
        if(mode.equals("A")&&count>=1){
            String msg2 = "Duplicate Record";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
        else if(mode.equals("V")&&count>=2){
            String msg2 = "Duplicate Record";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
        
    }

    public void setParamTable(RichTable paramTable) {
        this.paramTable = paramTable;
    }

    public RichTable getParamTable() {
        return paramTable;
    }
}
