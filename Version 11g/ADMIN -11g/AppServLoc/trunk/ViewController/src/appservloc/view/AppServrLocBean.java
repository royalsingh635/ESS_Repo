package appservloc.view;

import appservloc.model.module.AppServrLocAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppServrLocBean {
    private RichPopup addpop;
    private RichPopup deletePopUpSLOC;
    private RichInputText servrLocName;
    private String flag="true";
    private String flag1="true";
    private RichInputText serverLocIp;
    private RichInputText serverLocDbOs;
    private RichInputText serverLocDbVer;
    private RichInputText serverLocResInstallPath;
    private RichSelectBooleanCheckbox serverLocMstSiteFlg;
    private RichSelectOneChoice serverLocDbVern;
    Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    Integer sloc=null;
    Integer slocFlg=null;
    public BindingContainer getBindings()
    {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public AppServrLocBean() {
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
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }

    public void addActionListener(ActionEvent actionEvent) {
        
        AppServrLocAMImpl am = (AppServrLocAMImpl)resolvElDC("AppServrLocAMDataControl");
        ViewObjectImpl appseclocVo = am.getAppServrLoc1();
        Row[] servidcount= appseclocVo.getFilteredRows("ServrLocDef", "Y");
        System.out.println("serv id count = "+servidcount.length);
               if(servidcount.length>0)
               {flag="true";}
               else
               {flag="false";}
        
        Row[] slocMstCount = appseclocVo.getFilteredRows("SrvrLocMstSiteFlg", "Y");
        System.out.println("serv Mst Loc count = "+slocMstCount.length);
               if(slocMstCount.length>0)
               {flag1="true";}
               else
               {flag1="false";}

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert")
        .execute();
        showPopup(addpop,true);
        System.out.println("ADD BUTTON CALLED");
    }

    public void setAddpop(RichPopup addpop) {
        this.addpop = addpop;
    }

    public RichPopup getAddpop() {
        return addpop;
    }

    public void addPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback")
        .execute();
        System.out.println("DIALOG OR POPUP CABNCLE BUTTON CALLED");
    }

    public void addPopDListener(DialogEvent dialogEvent) {
        if(DialogEvent.Outcome.ok==dialogEvent.getOutcome()){
            if(servrLocName.getValue()!=null && serverLocIp.getValue()!=null && serverLocDbOs.getValue()!=null 
               && serverLocDbVern.getValue()!=null && serverLocResInstallPath.getValue()!=null){
                
                AppServrLocAMImpl am = (AppServrLocAMImpl)resolvElDC("AppServrLocAMDataControl");
                ViewObject appservrlocvo = am.getAppServrLoc1();
                System.out.println("---------------------"+serverLocDbVern.getValue());
                String s = serverLocDbVern.getValue().toString();
                appservrlocvo.getCurrentRow().setAttribute("SrvrLocDbVer", s);
                System.out.println("SrvrLocDbVer = "+s);
                //----------------------------------------for default
                if(appservrlocvo.getCurrentRow().getAttribute("ServrLocDef").equals("Y") && sloc !=null){
                   Row[] r=appservrlocvo.getFilteredRows("ServrLocId",sloc); 
                   r[0].setAttribute("ServrLocDef","N");
                   }
                //----------------------------------------for mst site flg
                if(appservrlocvo.getCurrentRow().getAttribute("SrvrLocMstSiteFlg").equals("Y") && slocFlg !=null){
                   Row[] r1=appservrlocvo.getFilteredRows("ServrLocId",slocFlg); 
                   r1[0].setAttribute("SrvrLocMstSiteFlg","N");
                   }
                //--------------------------------------------for name 
                String snm = servrLocName.getValue().toString();
                System.out.println("geted value from pop up  snm-------------"+snm);
                Row[] snme = appservrlocvo.getFilteredRows("ServrLocNm", snm);
                System.out.println("Server name count = "+snme.length);
                System.out.println(snme[0]);
                if(snme.length>1)
                {
                    System.out.println("name present");
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.254']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(servrLocName.getClientId(), message);
                }
                else
                {
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                    //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                    System.out.println("commit");
                    
                    
//                    DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppServrLoc1Iterator");
//                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
//                    Key parentKey = parentIter.getCurrentRow().getKey();
//                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
//                    appservrlocvo.executeQuery();
//                    System.out.println("parentKey ===== "+parentKey);
//                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    
                    
                }
                
                
                //appservrlocvo.executeQuery();
                System.out.println("SAVED");
                
            }
            else if(servrLocName.getValue()==null){
                System.out.println("plz enter value for server location name."); 
                     FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.508']}"));
                     message.setSeverity(FacesMessage.SEVERITY_ERROR);
                     FacesContext fc = FacesContext.getCurrentInstance();
                     fc.addMessage(servrLocName.getClientId(), message);
                 
            }
            else if(serverLocIp.getValue()==null)
            {
               System.out.println("plz enter value for sever loc ip."); 
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.508']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(serverLocIp.getClientId(), message);
                }
             else if(serverLocDbOs.getValue()==null)
             {
                System.out.println("plz enter value for serverLocDbOs."); 
                     FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.508']}"));
                     message.setSeverity(FacesMessage.SEVERITY_ERROR);
                     FacesContext fc = FacesContext.getCurrentInstance();
                     fc.addMessage(serverLocDbOs.getClientId(), message);
                 }
             else if(serverLocDbVern.getValue()==null)
             {
                System.out.println("plz enter value for serverLocDbVer."); 
                     FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.508']}"));
                     message.setSeverity(FacesMessage.SEVERITY_ERROR);
                     FacesContext fc = FacesContext.getCurrentInstance();
                     fc.addMessage(serverLocDbVern.getClientId(), message);
                 }
             else if(serverLocResInstallPath.getValue()==null)
             {
                System.out.println("plz enter value for serverLocResInstallPath."); 
                     FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.508']}"));
                     message.setSeverity(FacesMessage.SEVERITY_ERROR);
                     FacesContext fc = FacesContext.getCurrentInstance();
                     fc.addMessage(serverLocResInstallPath.getClientId(), message);
                 }
             
             System.out.println("DIALOG OK BUTTON CALLED");
         }
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

    public void editActionListener(ActionEvent actionEvent) {
        AppServrLocAMImpl am = (AppServrLocAMImpl)resolvElDC("AppServrLocAMDataControl");
        ViewObjectImpl appseclocVo = am.getAppServrLoc1();
        Row[] servidcount= appseclocVo.getFilteredRows("ServrLocDef", "Y");
        System.out.println("serv id count = "+servidcount.length);
               if(servidcount.length>0)
               {flag="false";
                    sloc = (Integer)servidcount[0].getAttribute("ServrLocId");
                     System.out.println("SLOC ID For DEFAULT "+sloc);
                }
               else
               {sloc=null;
                   flag="true";}
        
        Row[] slocMstCount = appseclocVo.getFilteredRows("SrvrLocMstSiteFlg", "Y");
        System.out.println("serv Mst Loc count = "+slocMstCount.length);
               if(slocMstCount.length>0)
               {flag1="false";
                slocFlg = (Integer)slocMstCount[0].getAttribute("ServrLocId");
                    System.out.println("SLOC ID For FLAG "+slocFlg);
                }
               else
               {flag1="true";}
        showPopup(addpop,true);
        System.out.println("EDIT BUTTON CALLED");
    }

    public void deleteActionListener(ActionEvent actionEvent) {
        showPopup(deletePopUpSLOC,true);
        System.out.println("DELETE BUTTON CALLED");
    }

    public void setDeletePopUpSLOC(RichPopup deletePopUpSLOC) {
        this.deletePopUpSLOC = deletePopUpSLOC;
    }

    public RichPopup getDeletePopUpSLOC() {
        return deletePopUpSLOC;
    }

    public void deleteSLOCPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        System.out.println("DIALOG OR POPUP CANCLE BUTTON CALLED");
    }

    public void DeleteSLOCDialogListener(DialogEvent dialogEvent) {
        if(DialogEvent.Outcome.ok==dialogEvent.getOutcome()){
             BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete")
             .execute();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit")
            .execute();
             
             System.out.println("DIALOG OK BUTTON CALLED TO delete ServerLOC from AppServrLoc1 ");
        }
    }

    public void setServrLocName(RichInputText servrLocName) {
        this.servrLocName = servrLocName;
    }

    public RichInputText getServrLocName() {
        return servrLocName;
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setServerLocIp(RichInputText serverLocIp) {
        this.serverLocIp = serverLocIp;
    }

    public RichInputText getServerLocIp() {
        return serverLocIp;
    }

    public void setServerLocDbOs(RichInputText serverLocDbOs) {
        this.serverLocDbOs = serverLocDbOs;
    }

    public RichInputText getServerLocDbOs() {
        return serverLocDbOs;
    }

    public void setServerLocDbVer(RichInputText serverLocDbVer) {
        this.serverLocDbVer = serverLocDbVer;
    }

    public RichInputText getServerLocDbVer() {
        return serverLocDbVer;
    }

    public void setServerLocResInstallPath(RichInputText serverLocResInstallPath) {
        this.serverLocResInstallPath = serverLocResInstallPath;
    }

    public RichInputText getServerLocResInstallPath() {
        return serverLocResInstallPath;
    }

    public void setServerLocMstSiteFlg(RichSelectBooleanCheckbox serverLocMstSiteFlg) {
        this.serverLocMstSiteFlg = serverLocMstSiteFlg;
    }

    public RichSelectBooleanCheckbox getServerLocMstSiteFlg() {
        return serverLocMstSiteFlg;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setServerLocDbVern(RichSelectOneChoice serverLocDbVern) {
        this.serverLocDbVern = serverLocDbVern;
    }

    public RichSelectOneChoice getServerLocDbVern() {
        return serverLocDbVern;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
