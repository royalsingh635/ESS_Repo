package glbltickerapp.view.beans;

import glbltickerapp.model.views.FinGlblTkrTrendVORowImpl;

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
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.server.RowQualifier;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GlblTickerAppBean {
    private RichPopup naTypePopupBinding;
    private RichTable naTypetableBinding;
    private RichPopup popupNaBinding;
    private RichPopup delPopupBinding;
    private RichPopup vouTypePopupBinding;
    private RichPopup trendPopuBinding;
    private RichInputText tickerNameBinding;
    private RichTable tableBinding;
    private RichTable vouTypeTableBinding;
    private RichTable trendTableBinding;
    private RichSelectOneChoice glblFinTkrTrendBinding;

    public GlblTickerAppBean() {
    }
    private String Mode = "V";
    private Integer Flag=1;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String createTicker() {
        Mode = "C";
        tableBinding.setRowSelection("none");
        /* BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("CreateInsert");
        op.execute(); */
        getBindings().getOperationBinding("CreateInsert").execute();
        return null;
    }

    public String editTicker() {
        tableBinding.setRowSelection("none");

        Mode = "C";
        return null;
    }


    public String saveTicker() {
        Mode = "V";
        tableBinding.setRowSelection("single");

        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("Commit");
        op.execute();

       
            String message = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!


            FacesMessage msg = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, msg);
       
        return null;
    }

    public String cancelTicker() {
        Mode = "V";
        tableBinding.setRowSelection("single");

        /* BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("Rollback");
        op.execute(); */
        getBindings().getOperationBinding("Execute").execute();
        getBindings().getOperationBinding("Rollback").execute();
        return null;
    }

    public String addNaTpe() {
        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("CreateInsert1");
        op.execute();
        showPopup(popupNaBinding, true);
        return null;
    }

    public String addVouType() {
        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("CreateInsert2");
        op.execute();
        showPopup(vouTypePopupBinding, true);
        return null;
    }

    public String addtrend() {
        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("CreateInsert3");
        op.execute();


        DCBindingContainer iter = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIter = iter.findIteratorBinding("FinGlblTkrIterator");
        Row currentRow = dcIter.getViewObject().getCurrentRow();
        String trend = (String)currentRow.getAttribute("GlblFinTkrTrend");


        if (trend != null) {
            DCBindingContainer iter1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcIter1 = iter1.findIteratorBinding("FinGlblTkrTrendIterator");
            FinGlblTkrTrendVORowImpl vo = (FinGlblTkrTrendVORowImpl)dcIter1.getViewObject().getCurrentRow();
            RowSet Lovtrend = vo.getLovtkrTrendVO5();
            Lovtrend.setNamedWhereClauseParam("BindFreq", trend);
            Lovtrend.executeQuery();
        }
        showPopup(trendPopuBinding, true);

        return null;
    }

    public String deleteNaType() {
       Flag=2;
        showPopup(delPopupBinding, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(naTypetableBinding);

        return null;
    }

    public String deleteVouType() {
       Flag=3;

        showPopup(delPopupBinding, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(vouTypeTableBinding);

        return null;
    }

    
    public String deleteTrend() {
       Flag=4;

        showPopup(delPopupBinding, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trendTableBinding);

        return null;
    }


    

    public void popupDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (Flag==2) {
                BindingContainer bc = getBindings();
                OperationBinding op = bc.getOperationBinding("Delete");
                op.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(naTypetableBinding);

            }
            if(Flag==3) {
                BindingContainer bc = getBindings();
                OperationBinding op = bc.getOperationBinding("Delete1");
                op.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(vouTypeTableBinding);
                
            }
            if(Flag==4) {
                BindingContainer bc = getBindings();
                OperationBinding op = bc.getOperationBinding("Delete2");
                op.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(trendTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(glblFinTkrTrendBinding);
            }
        }
    }

   
    public void naTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        DCBindingContainer bind = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding binding = bind.findIteratorBinding("FinGlblTkrNaTypIterator");
        ViewObjectImpl vo = (ViewObjectImpl)binding.getViewObject();
        Row row = vo.getCurrentRow();

        DCIteratorBinding dcIter = bind.findIteratorBinding("FinGlblTkrIterator");
        Row currentRow = dcIter.getViewObject().getCurrentRow();
        Integer tkrId = (Integer)currentRow.getAttribute("GlblFinTkrId");
        if (object != null && tkrId != null) {
            Integer na = Integer.valueOf(Integer.parseInt(object.toString()));
            RowQualifier rq = new RowQualifier(vo);
            rq.setWhereClause("GlblFinTkrNaTyp=" + na + " and GlblFinTkrId=" + tkrId);
            Row[] filteredRows = vo.getFilteredRows(rq);
            // Row[] filteredRows = vo.getFilteredRows("GlblFinTkrVouTypId", (Number)vouTypeBinding.getValue());
            if (filteredRows.length > 0 && filteredRows[0] != row) {
                String message = resolvElDCMsg("#{bundle['MSG.46']}").toString(); //duplicate record

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(naTypetableBinding);

    }

    public void vouTypeValidat(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DCBindingContainer bind = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding binding = bind.findIteratorBinding("FinGlblTkrVouTypIterator");
        ViewObjectImpl vo = (ViewObjectImpl)binding.getViewObject();
        Row row = vo.getCurrentRow();
        DCIteratorBinding dcIter = bind.findIteratorBinding("FinGlblTkrIterator");
        Row currentRow = dcIter.getViewObject().getCurrentRow();
        Integer tkrId = (Integer)currentRow.getAttribute("GlblFinTkrId");
        
        if (object != null && tkrId != null) {
            RowQualifier rq = new RowQualifier(vo);
            rq.setWhereClause("GlblFinTkrVouTypId=" + (Integer)object + " and GlblFinTkrId=" + tkrId);
            Row[] filteredRows = vo.getFilteredRows(rq);
            if (filteredRows.length > 0 && filteredRows[0] != row) {
                String message = resolvElDCMsg("#{bundle['MSG.46']}").toString(); //duplicate record

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(vouTypeTableBinding);

    }

    public void trendValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DCBindingContainer bind = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding binding = bind.findIteratorBinding("FinGlblTkrTrendIterator");
        ViewObjectImpl vo = (ViewObjectImpl)binding.getViewObject();
        Row row = vo.getCurrentRow();

        DCIteratorBinding dcIter = bind.findIteratorBinding("FinGlblTkrIterator");
        Row currentRow = dcIter.getViewObject().getCurrentRow();
        Integer tkrId = (Integer)currentRow.getAttribute("GlblFinTkrId");

        if (object != null && tkrId != null) {
            RowQualifier rq = new RowQualifier(vo);
            rq.setWhereClause("GlblFinTkrTrnd='" + object.toString() + "' and GlblFinTkrId=" + tkrId);
            Row[] filteredRows = vo.getFilteredRows(rq);

            if (filteredRows.length > 0 && filteredRows[0] != row) {
                String message = resolvElDCMsg("#{bundle['MSG.46']}").toString(); //duplicate record

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(trendTableBinding);
        }
    }

    public void tickerNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /** For Invalid Syntax */
        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*"; //String with space in middle and end
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        //String error=resolvElDCMsg("Special Character Not Allowed").toString();

        if (matcher.matches()) {
            DCBindingContainer bind = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding binding = bind.findIteratorBinding("FinGlblTkrIterator");
            ViewObjectImpl vo = (ViewObjectImpl)binding.getViewObject();
            Row row = vo.getCurrentRow();
            String name = object.toString().trim();
            Row[] filteredRows = vo.getFilteredRows("GlblFinTkrNm", name);
            if (filteredRows.length > 0 && filteredRows[0] != row) {
                String message = resolvElDCMsg("#{bundle['MSG.46']}").toString(); //duplicate record

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        } else {

            String message = resolvElDCMsg("#{bundle['MSG.1140']}").toString(); //Invalid Syntax

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }


        //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle


    }

  

    public void searchAction(ActionEvent actionEvent) {
        if (tickerNameBinding.getValue() != null) {
            DCBindingContainer iter = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcIter = iter.findIteratorBinding("FinGlblTkrIterator");
            ViewObject vo = dcIter.getViewObject();
            vo.setNamedWhereClauseParam("BindTckrName", tickerNameBinding.getValue().toString());
            vo.executeQuery();
        }

    }

    public void resetAction(ActionEvent actionEvent) {
        tickerNameBinding.setValue("");
        DCBindingContainer iter = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIter = iter.findIteratorBinding("FinGlblTkrIterator");
        ViewObject vo = dcIter.getViewObject();
        vo.setNamedWhereClauseParam("BindTckrName", null);
        vo.executeQuery();
    }

    public void popupCancelListner(PopupCanceledEvent popupCanceledEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
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


    public void setNaTypePopupBinding(RichPopup naTypePopupBinding) {
        this.naTypePopupBinding = naTypePopupBinding;
    }

    public RichPopup getNaTypePopupBinding() {
        return naTypePopupBinding;
    }

    public void setNaTypetableBinding(RichTable naTypetableBinding) {
        this.naTypetableBinding = naTypetableBinding;
    }

    public RichTable getNaTypetableBinding() {
        return naTypetableBinding;
    }

    public void setPopupNaBinding(RichPopup popupNaBinding) {
        this.popupNaBinding = popupNaBinding;
    }

    public RichPopup getPopupNaBinding() {
        return popupNaBinding;
    }

    public void setDelPopupBinding(RichPopup delPopupBinding) {
        this.delPopupBinding = delPopupBinding;
    }

    public RichPopup getDelPopupBinding() {
        return delPopupBinding;
    }

    public void setVouTypePopupBinding(RichPopup vouTypePopupBinding) {
        this.vouTypePopupBinding = vouTypePopupBinding;
    }

    public RichPopup getVouTypePopupBinding() {
        return vouTypePopupBinding;
    }

    public void setTrendPopuBinding(RichPopup trendPopuBinding) {
        this.trendPopuBinding = trendPopuBinding;
    }

    public RichPopup getTrendPopuBinding() {
        return trendPopuBinding;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void setTickerNameBinding(RichInputText tickerNameBinding) {
        this.tickerNameBinding = tickerNameBinding;
    }

    public RichInputText getTickerNameBinding() {
        return tickerNameBinding;
    }

    public void setTableBinding(RichTable tableBinding) {
        this.tableBinding = tableBinding;
    }

    public RichTable getTableBinding() {
        return tableBinding;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setVouTypeTableBinding(RichTable vouTypeTableBinding) {
        this.vouTypeTableBinding = vouTypeTableBinding;
    }

    public RichTable getVouTypeTableBinding() {
        return vouTypeTableBinding;
    }

    public void setTrendTableBinding(RichTable trendTableBinding) {
        this.trendTableBinding = trendTableBinding;
    }

    public RichTable getTrendTableBinding() {
        return trendTableBinding;
    }


    public void setFlag(Integer Flag) {
        this.Flag = Flag;
    }

    public Integer getFlag() {
        return Flag;
    }


    public void setGlblFinTkrTrendBinding(RichSelectOneChoice glblFinTkrTrendBinding) {
        this.glblFinTkrTrendBinding = glblFinTkrTrendBinding;
    }

    public RichSelectOneChoice getGlblFinTkrTrendBinding() {
        return glblFinTkrTrendBinding;
    }
}
