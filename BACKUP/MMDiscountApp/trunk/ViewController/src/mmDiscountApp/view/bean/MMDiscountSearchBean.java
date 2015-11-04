package mmDiscountApp.view.bean;

import java.io.Serializable;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import mmDiscountApp.model.service.MMDiscountAppAMImpl;

import mmDiscountApp.model.views.MmSchmFreeItmVOImpl;
import mmDiscountApp.model.views.MmSchmItmVOImpl;
import mmDiscountApp.model.views.MmSchmVOImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MMDiscountSearchBean implements Serializable {
    private RichTable searchTable;
    private RichSelectBooleanCheckbox selectSchemeBindVar;
    private RichPopup popupBinding;
    private RichPopup currDelPopup;
    private RichInputText schmName;
    private RichSelectOneChoice schmType;
    private RichInputDate validUpto;
    private RichInputDate validFrom;
    private RichActiveOutputText showOfferCunt;
    private RichInputText offernameBind;
    private RichInputText offerTypBind;
    private RichInputText startDateBind;
    private RichInputText expireDateBind;
    private RichPanelBox searchPannel;

    public MMDiscountSearchBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String DeleteSelectedAction() {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObject schmSrch = am.getMMSchemeSearchView();
        RowSetIterator rsi = schmSrch.createRowSetIterator(null);
        int count = 0;
        while (rsi.hasNext()) {
            Row nxt = rsi.next();
            Object mfd = nxt.getAttribute("Trans_Select");
            //System.out.println("mfd--->"+mfd);
            if (mfd != null) {
                Boolean b = (Boolean)mfd;
                if (b == true) {
                    count = count + 1;

                }
            }
        }
        System.out.println("count----:" + count);
        rsi.closeRowSetIterator();
        if (count > 0) {
            showPopup(popupBinding, true);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resolvEl("#{bundle['MSG.270']}"), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(showOfferCunt);
        return null;
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

    public void deleteDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
            ViewObjectImpl v = am.getMMSchemeSearchView();
            MmSchmVOImpl v1 = am.getMmSchm();
            MmSchmItmVOImpl v2 = am.getMmSchmItm();
            MmSchmFreeItmVOImpl v3 = am.getMmSchmFreeItm();
            v1.setBindVar(null, null, null, null);
            v1.setWhereClause(null);
            v1.executeQuery();
            RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
            while (createRowSetIterator.hasNext()) {

                Row row = createRowSetIterator.next();
                if (row.getAttribute("Trans_Select") != null) {


                    if (Boolean.TRUE.equals(row.getAttribute("Trans_Select"))) {
                        String OrgId = row.getAttribute("OrgId").toString();
                        String SchmId = row.getAttribute("SchmId").toString();
                        Integer SlocId = (Integer)row.getAttribute("SlocId");
                        String CldId = row.getAttribute("CldId").toString();

                        v3.deleteFromScheme_FreeItem(SlocId, OrgId, SchmId, CldId);

                        v2.deleteFromScheme_Itm(SlocId, OrgId, SchmId, CldId);

                        v1.deleteFromScheme(SlocId, OrgId, SchmId, CldId);
                    }

                }

            }

            am.getDBTransaction().commit();
            v.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(showOfferCunt);

        }

    }


    public String DeactivateSelectedAction() {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObject schmSrch = am.getMMSchemeSearchView();
        RowSetIterator rsi = schmSrch.createRowSetIterator(null);
        int count = 0;
        while (rsi.hasNext()) {
            Row nxt = rsi.next();
            Object mfd = nxt.getAttribute("Trans_Select");
            //System.out.println("mfd--->"+mfd);
            if (mfd != null) {
                Boolean b = (Boolean)mfd;
                if (b == true) {
                    count = count + 1;

                }
                 }
        }
        System.out.println("count----:" + count);
        rsi.closeRowSetIterator();
        if (count > 0) {
            ViewObjectImpl v = am.getMMSchemeSearchView();
            MmSchmVOImpl v1 = (MmSchmVOImpl)am.getMmSchm();
            v1.setBindVar(null, null, null, null);
            v1.setWhereClause(null);
            v1.executeQuery();
            RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
            while (createRowSetIterator.hasNext()) {
                Row row = createRowSetIterator.next();
                if (row.getAttribute("Trans_Select") == null) {
                    row.setAttribute("Trans_Select", Boolean.FALSE);
                } else if (Boolean.TRUE.equals(row.getAttribute("Trans_Select"))) {
                    System.out.println("in if else condition in deactivate action method-----");
                    String OrgId = row.getAttribute("OrgId").toString();
                    String SchmId = row.getAttribute("SchmId").toString();
                    String CldId = row.getAttribute("CldId").toString();
                    Integer SlocId = (Integer)row.getAttribute("SlocId");
                    //RowQualifier rowQualifier = new RowQualifier(v1);
                    v1.setNamedWhereClauseParam("SlocIdBindVar", SlocId);
                    v1.setNamedWhereClauseParam("CldIdBindVar", CldId);
                    v1.setNamedWhereClauseParam("OrgIdBindVar", OrgId);
                    v1.setNamedWhereClauseParam("SchemeIdBindVar", SchmId);
                    v1.executeQuery();
                    //  rowQualifier.setWhereClause(" OrgId = '"+OrgId+"' and SlocId="+SlocId+" and SchmId= '"+SchmId+"'");
                    //rowQualifier.setWhereClause("SchmId = '" + SchmId + "'");
                    Row[] rows = v1.getAllRowsInRange();//.getFilteredRows(rowQualifier);
                    System.out.println("total no of rows in set where claude in deactib=vate ==="+rows.length);
                    if (rows.length > 0) {
                        Date dt = (Date)Date.getCurrentDate();
                        rows[0].setAttribute("Actv", "N");
                        rows[0].setAttribute("InactvDt", dt);
                    }
                    /* rowQualifier.setWhereClause(null);
                    Row[] rows1 = v1.getFilteredRows(rowQualifier); */
                }
            }
            createRowSetIterator.closeRowSetIterator();
            am.getDBTransaction().commit();
            v1.executeQuery();
            v.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resolvEl("#{bundle['MSG.271']}"), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        return null;
    }


    public String DeleteAction() {
        showPopup(currDelPopup, true);
        //   AdfFacesContext.getCurrentInstance().addPartialTarget(showOfferCunt);
        return null;
    }

    public void deleteCurrentDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
            ViewObjectImpl v = am.getMMSchemeSearchView();
            MmSchmVOImpl v1 = (MmSchmVOImpl)am.getMmSchm();
            MmSchmItmVOImpl v2 = (MmSchmItmVOImpl)am.getMmSchmItm();
            MmSchmFreeItmVOImpl v3 = (MmSchmFreeItmVOImpl)am.getMmSchmFreeItm();
            v1.setBindVar(null, null, null, null);
            v1.setWhereClause(null);
            v1.executeQuery();
            Row row = v.getCurrentRow();
            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String SchmId = row.getAttribute("SchmId").toString();
            Integer SlocId =
                Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")); //(Integer)row.getAttribute("SlocId");
            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            v3.deleteFromScheme_FreeItem(SlocId, OrgId, SchmId, CldId);

            v2.deleteFromScheme_Itm(SlocId, OrgId, SchmId, CldId);

            v1.deleteFromScheme(SlocId, OrgId, SchmId, CldId);

            am.getDBTransaction().commit();
            v.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(showOfferCunt);
        }
    }

    /*   public String CreateAction() {
        // Add event code here...
        return "Add";
    } */


    public String EditAction() {

        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl v = am.getMMSchemeSearchView();
        System.out.println("[][][][][][][][]" + v.getCurrentRow().getAttribute("SchmId"));
        /* v.setNamedWhereClauseParam("SlocIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        v.setNamedWhereClauseParam("OrgIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        v.setNamedWhereClauseParam("CldIdBindNewVar", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        v.setNamedWhereClauseParam("FromDateBindVar", null);
        v.setNamedWhereClauseParam("UptoDateBindVar", null);
        v.setNamedWhereClauseParam("SchmTypeBindVar", null);
        v.setNamedWhereClauseParam("SchemeNameBindVar", null);
        v.executeQuery(); */
        ViewObjectImpl schmvo = am.getMmSchm();
        schmvo.setNamedWhereClauseParam("SlocIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        schmvo.setNamedWhereClauseParam("CldIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        schmvo.setNamedWhereClauseParam("OrgIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        schmvo.setNamedWhereClauseParam("SchemeIdBindVar", v.getCurrentRow().getAttribute("SchmId"));
        schmvo.executeQuery();
        return "Edit";
    }

    public String SelectAll() {


        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl v = am.getMMSchemeSearchView();

        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {

            Row row = createRowSetIterator.next();
            Boolean s = true;

            row.setAttribute("Trans_Select", s);


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectSchemeBindVar);
        return null;
    }

    public String DeselectAll() {

        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl v = am.getMMSchemeSearchView();

        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {

            Row row = createRowSetIterator.next();
            Boolean s = false;

            row.setAttribute("Trans_Select", s);

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectSchemeBindVar);
        return null;
    }

    public String ResetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPannel);
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl v = am.getMMSchemeSearchView();
        v.setNamedWhereClauseParam("SlocIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        v.setNamedWhereClauseParam("OrgIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        v.setNamedWhereClauseParam("CldIdBindNewVar", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        v.setNamedWhereClauseParam("FromDateBindVar", null);
        v.setNamedWhereClauseParam("UptoDateBindVar", null);
        v.setNamedWhereClauseParam("SchmTypeBindVar", null);
        v.setNamedWhereClauseParam("SchemeNameBindVar", null);
        v.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        return null;
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


    private void reset() {

    }

    public String SearchAction() {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl v = am.getMMSchemeSearchView();
        v.setNamedWhereClauseParam("SlocIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        v.setNamedWhereClauseParam("OrgIdBindVar", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        v.setNamedWhereClauseParam("CldIdBindNewVar", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        v.setNamedWhereClauseParam("FromDateBindVar", validFrom.getValue());
        v.setNamedWhereClauseParam("UptoDateBindVar", validUpto.getValue());
        v.setNamedWhereClauseParam("SchmTypeBindVar", schmType.getValue());
        v.setNamedWhereClauseParam("SchemeNameBindVar", schmName.getValue());
        v.executeQuery();

        /*  BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("ExecuteWithParams");
        createOpBAddress.execute(); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        return null;
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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


    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
    }

    public void setSelectSchemeBindVar(RichSelectBooleanCheckbox selectSchemeBindVar) {
        this.selectSchemeBindVar = selectSchemeBindVar;
    }

    public RichSelectBooleanCheckbox getSelectSchemeBindVar() {
        return selectSchemeBindVar;
    }


    public void setPopupBinding(RichPopup popupBinding) {
        this.popupBinding = popupBinding;
    }

    public RichPopup getPopupBinding() {
        return popupBinding;
    }


    public void setCurrDelPopup(RichPopup currDelPopup) {
        this.currDelPopup = currDelPopup;
    }

    public RichPopup getCurrDelPopup() {
        return currDelPopup;
    }


    public void setSchmName(RichInputText schmName) {
        this.schmName = schmName;
    }

    public RichInputText getSchmName() {
        return schmName;
    }

    public void setSchmType(RichSelectOneChoice schmType) {
        this.schmType = schmType;
    }

    public RichSelectOneChoice getSchmType() {
        return schmType;
    }

    public void setValidUpto(RichInputDate validUpto) {
        this.validUpto = validUpto;
    }

    public RichInputDate getValidUpto() {
        return validUpto;
    }

    public void setValidFrom(RichInputDate validFrom) {
        this.validFrom = validFrom;
    }

    public RichInputDate getValidFrom() {
        return validFrom;
    }


    public void setShowOfferCunt(RichActiveOutputText showOfferCunt) {
        this.showOfferCunt = showOfferCunt;
    }

    public RichActiveOutputText getShowOfferCunt() {
        return showOfferCunt;
    }

    public String newSearchMethod() {
        System.out.println("validFrom.getValue() = " + validFrom.getValue());
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("ExecuteWithParams");
        createOpBAddress.getParamsMap().put("FromDateBindVar", validFrom.getValue());
        createOpBAddress.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
        return null;
    }

    public void setOffernameBind(RichInputText offernameBind) {
        this.offernameBind = offernameBind;
    }

    public RichInputText getOffernameBind() {
        return offernameBind;
    }

    public void setOfferTypBind(RichInputText offerTypBind) {
        this.offerTypBind = offerTypBind;
    }

    public RichInputText getOfferTypBind() {
        return offerTypBind;
    }

    public void setStartDateBind(RichInputText startDateBind) {
        this.startDateBind = startDateBind;
    }

    public RichInputText getStartDateBind() {
        return startDateBind;
    }

    public void setExpireDateBind(RichInputText expireDateBind) {
        this.expireDateBind = expireDateBind;
    }

    public RichInputText getExpireDateBind() {
        return expireDateBind;
    }

    public void setSearchPannel(RichPanelBox searchPannel) {
        this.searchPannel = searchPannel;
    }

    public RichPanelBox getSearchPannel() {
        return searchPannel;
    }
}
