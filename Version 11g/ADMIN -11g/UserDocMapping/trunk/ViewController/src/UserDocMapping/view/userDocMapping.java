package UserDocMapping.view;

import UserDocMapping.model.AppModule.UserDocMappingAppModuleImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class userDocMapping {
    private RichPopup pop;
    private RichPopup popup;
    private  Integer  count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichInputText searchBind;
    private RichCommandImageLink searchbind;

    public userDocMapping() {
    }
    private String Flag = "V"; 
    

    public void create(ActionEvent actionEvent) {
      System.out.println("create---");
       // System.out.println("create btn called =======================>");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute(); 
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        System.out.println("ordid______"+orgid);
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
       /*  String hoorgid = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"); */
        Integer usrid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        UserDocMappingAppModuleImpl am = (UserDocMappingAppModuleImpl)resolvElDC("UserDocMappingAppModuleDataControl");
        ViewObjectImpl vo = am.getAppWfUsrDocMapView2();
        Row row = vo.getCurrentRow();
        row.setAttribute("SlocId", slocid);
        row.setAttribute("OrgId", orgid);
        row.setAttribute("CldId",cldid);
/*         row.setAttribute("HoOrgId",hoorgid); */
        row.setAttribute("UsrIdCreate",usrid);
        
   
        Flag = "A";
       
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
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public void edit(ActionEvent actionEvent) {
        Flag = "E";
    }

    public void save(ActionEvent actionEvent) {
        
        if 
      (Flag.equalsIgnoreCase("E")) 
               {
        Integer usrid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        UserDocMappingAppModuleImpl am = (UserDocMappingAppModuleImpl)resolvElDC("UserDocMappingAppModuleDataControl");
        ViewObjectImpl vo = am.getAppWfUsrDocMapView2();
        Row row = vo.getCurrentRow();
        row.setAttribute("UsrIdMod",usrid);
               }
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute(); 
        Flag = "V";
    }

    public void cancel(ActionEvent actionEvent) {
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute(); 
        Flag = "V";
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }
  
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void setFlag(String Flag) {
        this.Flag = Flag;
    }

    public String getFlag() {
        return Flag;
    }

    public void actvChkBoxlistner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String value = valueChangeEvent.getNewValue().toString();
            
            if (value.equals("false")) {
                System.out.println("bean entr---------------------gfhknkldsfds--------");
                Date dt = (Date)Date.getCurrentDate();
                UserDocMappingAppModuleImpl am = (UserDocMappingAppModuleImpl)resolvElDC("UserDocMappingAppModuleDataControl");
                ViewObjectImpl v = am.getAppWfUsrDocMapView2();
                Row re = v.getCurrentRow();
                re.setAttribute("InactvDt", dt);
               /*  inactiveDate.setValue(dt); */
             

    }
}
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setSearchBind(RichInputText searchBind) {
        this.searchBind = searchBind;
    }

    public RichInputText getSearchBind() {
        return searchBind;
    }

    public void SearchAction(ActionEvent actionEvent) {
        UserDocMappingAppModuleImpl am = (UserDocMappingAppModuleImpl)resolvElDC("UserDocMappingAppModuleDataControl");
        ViewObjectImpl v = am.getAppDocVO1();
        ViewCriteria vc = v.getViewCriteria("AppDocVOCriteria")  ;
        v.applyViewCriteria(vc);
        v.setNamedWhereClauseParam("DocName", searchBind.getValue());
       v.executeQuery();
       
    }

    public void setSearchbind(RichCommandImageLink searchbind) {
        this.searchbind = searchbind;
    }

    public RichCommandImageLink getSearchbind() {
        return searchbind;
    }

    public void Reset(ActionEvent actionEvent) {
        
        UserDocMappingAppModuleImpl am = (UserDocMappingAppModuleImpl)resolvElDC("UserDocMappingAppModuleDataControl");
        ViewObjectImpl v = am.getAppDocVO1();
        v.setNamedWhereClauseParam("DocName", null);
        this.searchBind.setValue(null);
        v.executeQuery();
        
    }
}
