package glApp.view.bean;


import glApp.model.module.GlAppAMImpl;
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

import oracle.adf.model.BindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GlAdjustmentBean {


    private RichPopup advancePopup;
    private RichTable adjTable;
    private RichSelectBooleanCheckbox selectEntity;
    private Boolean eoCheck;
    private RichTable advTable;
    private Number Sum = new Number(0);
    private RichInputText adjustedSum;
    private RichInputText totalInPopup;
    private RichInputText totalAdjustment;
    private RichInputText spSum;
    private RichPopup postedAmountDtlPopUp;
    private RichPopup postedAdvancePopUp;
    private RichPopup adjadvpopBinding;

    public GlAdjustmentBean() {
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

   

  

   
    public String invoiceDetail() {
        GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
        /* DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = bindings.findIteratorBinding("GlAdjTmp1Iterator");
        RowSetIterator adjRit = it.getRowSetIterator(); */
        ViewObject tVouDtl = am.getGlAdjTmp1();
        System.out.println("Total Rows in ViewObject after first refresh-->"+tVouDtl.getRowCount());
        RowSetIterator adjRit = tVouDtl.createRowSetIterator(null);
        Integer i = 0;
        /** Attribute tableOrQuery is null for database values and have value if comes from ARAP view. */
        while (adjRit.hasNext()) {
            Row newrow = adjRit.next();
            System.out.println("Table or query attribute value-->"+newrow.getAttribute("TableOrQuery"));
            if (newrow.getAttribute("TableOrQuery") != null) {
                
                i = i + 1;
            }
        }
System.out.println("Number of Values --rows from View--->"+i);
        adjRit.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(adjTable);
        if (i > 0)
            return null;
        else
            return "refresh";
    }


    public void setAdvancePopup(RichPopup advancePopup) {
        this.advancePopup = advancePopup;
    }

    public RichPopup getAdvancePopup() {
        return advancePopup;
    }

   /*  public void advanceButton(ActionEvent actionEvent) {       
        showPopup(advancePopup,true);
        //getAdvDetail();
        
    } */

    public void setAdjTable(RichTable adjTable) {
        this.adjTable = adjTable;
    }

    public RichTable getAdjTable() {
        return adjTable;
    }

    public void setSelectEntity(RichSelectBooleanCheckbox selectEntity) {
        this.selectEntity = selectEntity;
    }

    public RichSelectBooleanCheckbox getSelectEntity() {
        return selectEntity;
    }

    public void entityValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")){
            setEoCheck(true);
        }
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("false")) {
            setEoCheck(false);
        }
    }

    public void setEoCheck(Boolean eoCheck) {
        this.eoCheck = eoCheck;
    }

    public Boolean getEoCheck() {
        return eoCheck;
    }

   /*  public String getAdvDetail() {
        System.out.println("---i am in get advance detail method of adjust bean ---");
        GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
        ViewObject tVouDtl = am.getGlAdjTmpDtl1();
        
        RowSetIterator adjDtlrit = tVouDtl.createRowSetIterator(null);
        System.out.println(adjDtlrit.getRowCount());
        Integer i = 0;
        Row newrow = null;
        while (adjDtlrit.hasNext()) {
            if(i==0){
            newrow = adjDtlrit.first();
            }
            else{
                newrow = adjDtlrit.next();
            }
            if (newrow.getAttribute("TableOrQry")!= null && newrow.getAttribute("TableOrQry").equals("Q")) {
                newrow.remove();
                i=0;
            }
            else{
              i=i+1;
            }
        }
        adjDtlrit.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(advTable);
        tVouDtl.executeQuery();

        return "advRefresh";
       
    } */
    
    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setAdvTable(RichTable advTable) {
        this.advTable = advTable;
    }

    public RichTable getAdvTable() {
        return advTable;
    }

    public void setSum(Number Sum) {
        this.Sum = Sum;
    }
    
    private static Number as = new Number();
    
    public Number getSum() {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding it = bindings.findIteratorBinding("GlAdjTmpDtl1Iterator");
        RowSetIterator rit = it.getRowSetIterator();
        GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
        ViewObject v1 = am.findViewObject("GlAdjTmp1");
        Row currRow = v1.getCurrentRow();
        Number spamt = (Number)currRow.getAttribute("RadjAmtSp");

        if (rit.first() != null) {
            as = (Number)(rit.first().getAttribute("RadjAmtAdj"));
        }
        while (rit.hasNext()) {
            as = as.add((Number)(rit.next().getAttribute("RadjAmtAdj")));
        }
        return as;
       
    }

    public static void setAs(Number as) {
        GlAdjustmentBean.as = as;
    }

    public void advanceDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
            ViewObject v1 = am.getGlAdjTmp1();


            Row currRow = v1.getCurrentRow();
            //   Row currRowDt = v2.getCurrentRow();
            Number spamt = (Number)currRow.getAttribute("GlAdjAmtOT");
            Number sumVal = (Number)currRow.getAttribute("DtlSum");
            System.out.println(spamt +"...."+sumVal);
            if (sumVal == null) {
                sumVal = new Number(0);
            }


         
                DCBindingContainer bindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding it = bindings.findIteratorBinding("GlAdjTmpDtl1Iterator");
                RowSetIterator rit = it.getRowSetIterator();
                currRow.setAttribute("RadjAmtAdj", sumVal);

                String vouId = currRow.getAttribute("RadjVouId").toString();
                Date vouDt = (Date)currRow.getAttribute("RadjVouDt");

                if (rit.first() != null && ((Number)rit.first().getAttribute("RadjAmtAdj")).intValue() != 0) {

                    rit.first().setAttribute("AdjVouId", vouId);
                    rit.first().setAttribute("AdjVouDt", vouDt);
                    rit.first().setAttribute("AdjAmtAdj", currRow.getAttribute("RadjAmtAdj"));
                    rit.first().setAttribute("AdjAmtSp", currRow.getAttribute("RadjAmtSp"));
                }

                while (rit.hasNext()) {
                    Row newrow = rit.next();

                    if (((Number)newrow.getAttribute("RadjAmtAdj")).intValue() != 0) {
                        newrow.setAttribute("AdjVouId", vouId);
                        newrow.setAttribute("AdjVouDt", vouDt);
                        newrow.setAttribute("AdjAmtAdj", currRow.getAttribute("RadjAmtAdj"));
                        newrow.setAttribute("AdjAmtSp", currRow.getAttribute("RadjAmtSp"));
                    }
                }
               // v1.executeQuery();
            

        }
    }


    public void selectAllAdv(ValueChangeEvent valueChangeEvent) {
        boolean isSelected = (Boolean)valueChangeEvent.getNewValue();
        GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
        /** Get gladjdtl view */
        ViewObject v1 = am.findViewObject("GlAdjTmpDtl1");
        RowSetIterator rit = v1.createRowSetIterator(null);
        /** Iterate through every visible row of view */
        while (rit.hasNext()) {
            Row row = rit.next();
            if (isSelected) {
                /** if check all box is selected then select check box of every row and fill adjusted amount with outstanding amount.*/
                row.setAttribute("CheckAll", true);
                row.setAttribute("RadjAmtAdj", row.getAttribute("GlAdjAmtOT"));
            } else {
                /** else deselect check box of every row and clear adjusted amount of row. */
                row.setAttribute("CheckAll", false);
                if (((Number)(row.getAttribute("GlAdjAmtOT"))).compareTo((Number)(row.getAttribute("RadjAmtAdj"))) ==0) {
                    row.setAttribute("RadjAmtAdj", new Number(0));
                }
            }
        }
    }

    public void checkAmount(ValueChangeEvent valueChangeEvent) {
        /** if selected then copy outstanding amount in to adjusted amount else set adjusted amount to 0. */
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            adjustedSum.setValue(spSum.getValue());
            // adjustedSum.setReadOnly(true);
        }
        if (valueChangeEvent.getNewValue().toString().equals("false") && spSum.getValue() == adjustedSum.getValue()) {

            adjustedSum.setValue(new Number(0));
            // adjustedSum.setReadOnly(false);
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
    public void validateAdvance(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (((Number)spSum.getValue()).compareTo((object)) == -1) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.431']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);

        } else if (((Number)totalAdjustment.getValue()).compareTo((Number)spSum.getValue()) == 1) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.433']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(null, message);
        }
    }

    public void setAdjustedSum(RichInputText adjustedSum) {
        this.adjustedSum = adjustedSum;
    }

    public RichInputText getAdjustedSum() {
        return adjustedSum;
    }

    public void setTotalInPopup(RichInputText totalInPopup) {
        this.totalInPopup = totalInPopup;
    }

    public RichInputText getTotalInPopup() {
        return totalInPopup;
    }

    public void setTotalAdjustment(RichInputText totalAdjustment) {
        this.totalAdjustment = totalAdjustment;
    }

    public RichInputText getTotalAdjustment() {
        return totalAdjustment;
    }

    public void setSpSum(RichInputText spSum) {
        this.spSum = spSum;
    }

    public RichInputText getSpSum() {
        return spSum;
    }

    public void alAmtDtlLink(ActionEvent actionEvent) {
        showPopup(postedAmountDtlPopUp, true);
    }

    public void setPostedAmountDtlPopUp(RichPopup postedAmountDtlPopUp) {
        this.postedAmountDtlPopUp = postedAmountDtlPopUp;
    }

    public RichPopup getPostedAmountDtlPopUp() {
        return postedAmountDtlPopUp;
    }

    public void advanceamtDtlLink(ActionEvent actionEvent) {
        showPopup(postedAdvancePopUp, true);
    }

    public void setPostedAdvancePopUp(RichPopup postedAdvancePopUp) {
        this.postedAdvancePopUp = postedAdvancePopUp;
    }

    public RichPopup getPostedAdvancePopUp() {
        return postedAdvancePopUp;
    }
   


    public String advanceButtonAction() {
         BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setparmvalue")
        .execute();
        GlAppAMImpl am = (GlAppAMImpl)resolvEl("GlAppAMDataControl");
        ViewObject tVouDtl = am.getGlAdjTmpDtl1();
        
        RowSetIterator adjDtlrit = tVouDtl.createRowSetIterator(null);
        System.out.println(adjDtlrit.getRowCount());
        Integer i = 0;
        Row newrow = null;
        while (adjDtlrit.hasNext()) {
            if(i==0){
            newrow = adjDtlrit.first();
            }
            else{
                newrow = adjDtlrit.next();
            }
            if (newrow.getAttribute("TableOrQry")!= null && newrow.getAttribute("TableOrQry").equals("Q")) {
                newrow.remove();
                i=0;
            }
            else{
              i=i+1;
            }
        }
        adjDtlrit.closeRowSetIterator();
       // AdfFacesContext.getCurrentInstance().addPartialTarget(advTable);
        tVouDtl.executeQuery();
      showPopup(advancePopup, true);
        return "advRefresh";
    }

    public void adjDtlActionListener(ActionEvent actionEvent) {
        showPopup(adjadvpopBinding, true);
    }

    public void setAdjadvpopBinding(RichPopup adjadvpopBinding) {
        this.adjadvpopBinding = adjadvpopBinding;
    }

    public RichPopup getAdjadvpopBinding() {
        return adjadvpopBinding;
    }
}
