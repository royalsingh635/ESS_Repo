package glApp.view.bean;

import glApp.model.module.GlAppAMImpl;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;


public class GlSearchBean {
    private RichPanelFormLayout searchForm;
    private RichSelectOneChoice bindVouId;
    private RichSelectOneChoice bindOrgId;
    private RichInputDate bindFromDt;
    private RichInputDate bindToDt;
    private RichSelectOneChoice bindVouTp;
    private RichSelectOneChoice bindVouSubTp;
    private RichSelectOneChoice bindCurrId;  
    private RichTable searchTab;
    private RichCommandButton searchButton;
    private RichInputText bindAmtFrom;
    private RichInputText bindAmtTo;
    private RichPanelFormLayout amtForm;
    private Integer count ;
    private String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    private String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
    private Integer sloc_id =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")) ;
    GlAppAMImpl am = (GlAppAMImpl)resolvElDC("GlAppAMDataControl");
    private RichInputListOfValues vouidBind;

    public GlSearchBean() {
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
            }

        }
    }

    public void searchAction(ActionEvent actionEvent) {
        
        ViewObject v = am.getGlSearchView();
        ViewObject vw = am.getGlSearchFieldVO();
        
        
        v.setNamedWhereClauseParam("BindCldId", cld_id);
        v.setNamedWhereClauseParam("BindSlocId", sloc_id);
        System.out.println("valuee of>>>>vw.first().getAttribute(\"GlVouIdDisp\")"+vw.first().getAttribute("GlVouIdDisp"));
        //System.out.println("value of via bind cvariable=<<<"+vouidBind.getValue());
        v.setNamedWhereClauseParam("BindDispID", vw.first().getAttribute("GlVouIdDisp"));
        v.setNamedWhereClauseParam("BindOrgId", org_id);
        v.setNamedWhereClauseParam("BindFromDate", bindFromDt.getValue());
        v.setNamedWhereClauseParam("BindToDate", bindToDt.getValue());
        v.setNamedWhereClauseParam("BindVoutype", bindVouTp.getValue());
        v.setNamedWhereClauseParam("BindVouSubType", bindVouSubTp.getValue());
        v.setNamedWhereClauseParam("BindCurrIdBs", null);
        v.setNamedWhereClauseParam("BindVouStat", null);
        v.setNamedWhereClauseParam("BindAmtFrom", bindAmtFrom.getValue());
        v.setNamedWhereClauseParam("BindAmtTo", bindAmtTo.getValue());
        v.setNamedWhereClauseParam("BindCoaId", vw.first().getAttribute("GlCoaId"));
        v.setNamedWhereClauseParam("BindCogId", vw.first().getAttribute("GlCogId"));
        v.setNamedWhereClauseParam("BindNaId", null);
        v.setNamedWhereClauseParam("BindEoId", vw.first().getAttribute("GlEoId"));
        v.setNamedWhereClauseParam("BindEoMstid", vw.first().getAttribute("GlEoMstId"));
        v.executeQuery();

//        
//        BindingContext bindingctx1 = BindingContext.getCurrent();
//        BindingContainer bindings1 = bindingctx1.getCurrentBindingsEntry();
//        OperationBinding createOpBAddress1 = bindings1.getOperationBinding("ExecuteWithParams");
//        createOpBAddress1.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtForm);
    }

    public String resetActionTF() {
        
        ViewObject v = am.getGlSearchView();

        //System.out.println("reset action starts");
        
        v.setNamedWhereClauseParam("BindCldId", cld_id);
        v.setNamedWhereClauseParam("BindSlocId", sloc_id);
        //System.out.println("sloc_id-------->" + sloc_id);
        v.setNamedWhereClauseParam("BindDispID", null);
        v.setNamedWhereClauseParam("BindOrgId", org_id);
        v.setNamedWhereClauseParam("BindFromDate", null);
        v.setNamedWhereClauseParam("BindToDate", null);
        v.setNamedWhereClauseParam("BindVoutype", -1);
        v.setNamedWhereClauseParam("BindVouSubType", null);
        v.setNamedWhereClauseParam("BindCurrIdBs", null);
        v.setNamedWhereClauseParam("BindVouStat", null);
        v.setNamedWhereClauseParam("BindAmtFrom", null);
        v.setNamedWhereClauseParam("BindAmtTo", null);
        v.setNamedWhereClauseParam("BindCoaId", null);
        v.setNamedWhereClauseParam("BindCogId", null);
        v.setNamedWhereClauseParam("BindNaId", null);
        v.setNamedWhereClauseParam("BindEoId", null);
        v.setNamedWhereClauseParam("BindEoMstid", null);
        v.executeQuery();

        /* BindingContext bindingctx2 = BindingContext.getCurrent();
        BindingContainer bindings2 = bindingctx2.getCurrentBindingsEntry();
        OperationBinding createOpBAddress2 = bindings2.getOperationBinding("ExecuteWithParams");
        createOpBAddress2.execute(); */

        // ResetUtils.reset(searchForm);


        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(adfFacesContext, searchForm);
        resetValueInputItems(adfFacesContext, amtForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchButton);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtForm);
        return "goReset";
    }

   

    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
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
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    
    public void setBindVouId(RichSelectOneChoice bindVouId) {
        this.bindVouId = bindVouId;
    }

    public RichSelectOneChoice getBindVouId() {
        return bindVouId;
    }

    public void setBindOrgId(RichSelectOneChoice bindOrgId) {
        this.bindOrgId = bindOrgId;
    }

    public RichSelectOneChoice getBindOrgId() {
        return bindOrgId;
    }

    public void setBindFromDt(RichInputDate bindFromDt) {
        this.bindFromDt = bindFromDt;
    }

    public RichInputDate getBindFromDt() {
        return bindFromDt;
    }

    public void setBindToDt(RichInputDate bindToDt) {
        this.bindToDt = bindToDt;
    }

    public RichInputDate getBindToDt() {
        return bindToDt;
    }

    public void setBindVouTp(RichSelectOneChoice bindVouTp) {
        this.bindVouTp = bindVouTp;
    }

    public RichSelectOneChoice getBindVouTp() {
        return bindVouTp;
    }

    public void setBindVouSubTp(RichSelectOneChoice bindVouSubTp) {
        this.bindVouSubTp = bindVouSubTp;
    }

    public RichSelectOneChoice getBindVouSubTp() {
        return bindVouSubTp;
    }

    public void setBindCurrId(RichSelectOneChoice bindCurrId) {
        this.bindCurrId = bindCurrId;
    }

    public RichSelectOneChoice getBindCurrId() {
        return bindCurrId;
    }

    public void setSearchTab(RichTable searchTab) {
        this.searchTab = searchTab;
    }

    public RichTable getSearchTab() {
        return searchTab;
    }

    public void setSearchButton(RichCommandButton searchButton) {
        this.searchButton = searchButton;
    }

    public RichCommandButton getSearchButton() {
        return searchButton;
    }

    public void setBindAmtFrom(RichInputText bindAmtFrom) {
        this.bindAmtFrom = bindAmtFrom;
    }

    public RichInputText getBindAmtFrom() {
        return bindAmtFrom;
    }

    public void setBindAmtTo(RichInputText bindAmtTo) {
        this.bindAmtTo = bindAmtTo;
    }

    public RichInputText getBindAmtTo() {
        return bindAmtTo;
    }

    public void setAmtForm(RichPanelFormLayout amtForm) {
        this.amtForm = amtForm;
    }

    public RichPanelFormLayout getAmtForm() {
        return amtForm;
    }

    public void amtToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number amtTo = (Number)object;     

        Number amtFm;
        Object x = bindAmtFrom.getValue();
        if (x != null) {
            amtFm = (Number)x;
        } else {
            amtFm = new Number(0);
        }

        System.out.println("VALUES:" + amtTo + "---" + amtFm);
        if (amtFm == null) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.425']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if (amtFm.compareTo(amtTo) == 1) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.428']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
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
    public void vouTypeVCE(ValueChangeEvent vce) {
        
        ViewObject v = am.getLovVouId1();
        
        if(vce.getNewValue()!= null){        
            
        Integer type = (Integer)vce.getNewValue(); 
        v.setWhereClause("GL_TYPE_ID = " + type);
        v.executeQuery();        
        
        }
        else {
                v.setWhereClause(null);
                v.executeQuery();
            }
        
    }


    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOrg_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}") != null) {
            return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        } else
            return org_id;
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

    public void setSloc_id(Integer sloc_id) {
        this.sloc_id = sloc_id;
    }

    public Integer getSloc_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}") != null) {
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        } else
            return sloc_id;
    }

    public void setCld_id(String cld_id) {
        this.cld_id = cld_id;
    }

    public String getCld_id() {
        if (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}") != null) {
            return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        } else
            return cld_id;
    }

    public Integer getCount() {
        if (count== null){
            try {
                count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
            } catch (Exception e) {
                System.out.println("error in on_load_page");
                e.printStackTrace();
            }
        }
        return count;
    }

    public void setVouidBind(RichInputListOfValues vouidBind) {
        this.vouidBind = vouidBind;
    }

    public RichInputListOfValues getVouidBind() {
        return vouidBind;
    }
}
