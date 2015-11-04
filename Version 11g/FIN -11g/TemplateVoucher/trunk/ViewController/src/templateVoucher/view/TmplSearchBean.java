package templateVoucher.view;

import java.util.Iterator;
import java.util.List;
import templateVoucher.view.bundle.JSFUtils;
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
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.LaunchPopupEvent;

import oracle.adf.view.rich.render.ClientEvent;


//import oracle.adfinternal.view.faces.bi.util.JsfUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

import org.apache.myfaces.trinidad.util.Service;

import templateVoucher.model.module.TmplVouAMImpl;


public class TmplSearchBean {
    private RichSelectOneChoice orgId;
    private RichInputListOfValues tmplName;
    private RichSelectOneChoice vouTyp;
    private RichInputDate dateFrom;
    private RichInputText amtFrom;
    private RichInputDate dateTo;
    private RichInputText amtTo;
    private RichInputText cogId;
    private RichInputText coaId;
    private RichInputText voucherId;
    private RichPanelFormLayout searchForm;
    private RichInputListOfValues coaNmTransBInd;
    private String OrgId = "01";
    private String HoOrgId = "01";
    private String CldId = "0000";
    private Integer SlocId = 1;
    Integer count= (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichInputListOfValues transcoaNmBind;

    public TmplSearchBean() {
        

    }
    
    
     
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void searchEvent(ActionEvent actionEvent) {
        
        /** Get TmplSearchVO view object */
        
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        ViewObject v = am.getTmpl();
          // ViewObject view = am.getTmplSearch();

        /** Get Global Parameters from task flow */
        try {
            OrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            HoOrgId = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            CldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        } catch (Exception e) {
            
            e.printStackTrace();
        }
        /**Set values of bind variables. these values are from bind  */
        System.out.println("VoucherId on seqrch page=  "+voucherId.getValue()+"vouTyp = "+vouTyp.getValue()+"dateFrom = "+dateFrom.getValue()+"dateTo = "+dateTo.getValue()+"tmplname= "+tmplName.getValue()+"coaId = "+coaId.getValue());
        v.setNamedWhereClauseParam("BindVouId", voucherId.getValue());
        v.setNamedWhereClauseParam("BindTmplType", vouTyp.getValue());
        v.setNamedWhereClauseParam("BindSlocId", SlocId);
        v.setNamedWhereClauseParam("BindHoOrgId", HoOrgId);
        v.setNamedWhereClauseParam("BindOrgId", OrgId);
        v.setNamedWhereClauseParam("BindFromDt", dateFrom.getValue());
        v.setNamedWhereClauseParam("BindToDt", dateTo.getValue());
        System.out.println("coaId.getValue() = "+coaId.getValue());
        v.setNamedWhereClauseParam("BindCoaId", coaId.getValue());
        v.setNamedWhereClauseParam("BindCogId", cogId.getValue());
        v.setNamedWhereClauseParam("BindNaId", null);
        v.setNamedWhereClauseParam("BindCldId", CldId);
        System.out.println();
        v.setNamedWhereClauseParam("BindTmplNm", tmplName.getValue());
      
        /** Execute view object */
        v.executeQuery();

        BindingContext bindingctx1 = BindingContext.getCurrent();
        BindingContainer bindings1 = bindingctx1.getCurrentBindingsEntry();
        OperationBinding createOpBAddress1 = bindings1.getOperationBinding("ExecuteWithParams");
        createOpBAddress1.execute();

    }


    public String resetAction() {
        /** Get TmplSearchVO view object */
        
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        ViewObject v = am.getTmpl();
        ViewObject view = am.getTmplSearch();
        
        /**Set values of bind variables to null */
        
        v.setNamedWhereClauseParam("BindVouId", null);
        v.setNamedWhereClauseParam("BindTmplType", null);
        v.setNamedWhereClauseParam("BindSlocId", null);
        v.setNamedWhereClauseParam("BindHoOrgId", null);
        v.setNamedWhereClauseParam("BindOrgId", null);
        v.setNamedWhereClauseParam("BindFromDt", null);
        v.setNamedWhereClauseParam("BindToDt", null);
        v.setNamedWhereClauseParam("BindCoaId", null);
        v.setNamedWhereClauseParam("BindCogId", null);
        v.setNamedWhereClauseParam("BindNaId", null);
        //v.setNamedWhereClauseParam("BindCldId", null);
        v.setNamedWhereClauseParam("BindTmplNm", null);
        v.executeQuery();
        
        /** Execute view object*/
        
        view.executeQuery();
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        
        /** Call function to reset input field's values */
        
        resetValueInputItems(fctx, searchForm);
        BindingContext bindingctx2 = BindingContext.getCurrent();
        BindingContainer bindings2 = bindingctx2.getCurrentBindingsEntry();
        OperationBinding createOpBAddress2 = bindings2.getOperationBinding("Execute");
        createOpBAddress2.execute();
        return "reset";
    }

    public void coaNmValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
             ViewObject trPo = am.getLovCoaNew2();
           //OrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String HoOrgId = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String CldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

            trPo.setNamedWhereClauseParam("BindCldId", CldId);
            trPo.setNamedWhereClauseParam("BindHoOrgId", HoOrgId);
            trPo.setNamedWhereClauseParam("BindSlocId", SlocId);

            Row[] filterdRw = trPo.getFilteredRows("CoaNm", valueChangeEvent.getNewValue().toString());
            System.out.println("valueChangeEvent.getNewValue() = " + valueChangeEvent.getNewValue());
            if (filterdRw.length > 0) {
                System.out.println("filterdRw[0].getAttribute(\"CoaId\")) = " + filterdRw[0].getAttribute("CoaId"));
                 coaId.setValue((filterdRw[0].getAttribute("CoaId")));
               // AdfFacesContext.getCurrentInstance().addPartialTarget(searchForm);
            }
        }
    }

    public void cogNmValueChange(ValueChangeEvent valueChangeEvent) {

        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        ViewObject trPo = am.getLovCog();
        Row[] filterdRw = trPo.getFilteredRows("CogNm", valueChangeEvent.getNewValue());
        if (filterdRw.length > 0) {
            cogId.setValue((String)(filterdRw[0].getAttribute("CogId")));
        }

    }

    public void vouIdValueChange(ValueChangeEvent valueChangeEvent) {
        TmplVouAMImpl am = (TmplVouAMImpl)resolvElDC("TmplVouAMDataControl");
        ViewObject trPo = am.getLovVouId();
        Row[] filterdRw = trPo.getFilteredRows("DocTxnIdDisp", valueChangeEvent.getNewValue());
        if (filterdRw.length > 0) {
            voucherId.setValue((String)(filterdRw[0].getAttribute("DocTxnId")));
        }
    }


    public void amtToValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (amtFrom.getValue() != null) {
                Number frmAmt = (Number)amtFrom.getValue();
                Number toAmt = (Number)object;
                if (frmAmt.compareTo(toAmt) == 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount",
                                                                  "Enter Correct range of amount"));
                }
            }
        }
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public String createSearchPageButton() {

        return "create";
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
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void setOrgId(RichSelectOneChoice orgId) {
        this.orgId = orgId;
    }

    public RichSelectOneChoice getOrgId() {
        return orgId;
    }

    public void setTmplName(RichInputListOfValues tmplName) {
        this.tmplName = tmplName;
    }

    public RichInputListOfValues getTmplName() {
        return tmplName;
    }

    public void setVouTyp(RichSelectOneChoice vouTyp) {
        this.vouTyp = vouTyp;
    }

    public RichSelectOneChoice getVouTyp() {
        return vouTyp;
    }

    public void setDateFrom(RichInputDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public RichInputDate getDateFrom() {
        return dateFrom;
    }

    public void setAmtFrom(RichInputText amtFrom) {
        this.amtFrom = amtFrom;
    }

    public RichInputText getAmtFrom() {
        return amtFrom;
    }

    public void setDateTo(RichInputDate dateTo) {
        this.dateTo = dateTo;
    }

    public RichInputDate getDateTo() {
        return dateTo;
    }

    public void setAmtTo(RichInputText amtTo) {
        this.amtTo = amtTo;
    }

    public RichInputText getAmtTo() {
        return amtTo;
    }

    public void setCogId(RichInputText cogId) {
        this.cogId = cogId;
    }

    public RichInputText getCogId() {
        return cogId;
    }

    public void setCoaId(RichInputText coaId) {
        this.coaId = coaId;
    }

    public RichInputText getCoaId() {
        return coaId;
    }

    public void setVoucherId(RichInputText voucherId) {
        this.voucherId = voucherId;
    }

    public RichInputText getVoucherId() {
        return voucherId;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setCoaNmTransBInd(RichInputListOfValues coaNmTransBInd) {
        this.coaNmTransBInd = coaNmTransBInd;
    }

    public RichInputListOfValues getCoaNmTransBInd() {
        return coaNmTransBInd;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }


       public void setTranscoaNmBind(RichInputListOfValues transcoaNmBind) {
        this.transcoaNmBind = transcoaNmBind;
    }

    public RichInputListOfValues getTranscoaNmBind() {
        return transcoaNmBind;
    }
}
