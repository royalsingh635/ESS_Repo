package expenseforprd.view.bean;

import expenseforprd.model.module.ExpensePrdAMImpl;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.domain.Number;

public class ExpensePrdBean {
    String dcmode="Dr";
    String prddcmode="Dr";
    oracle.jbo.domain.Number tot = new oracle.jbo.domain.Number(0);
    oracle.jbo.domain.Number prdtot = new oracle.jbo.domain.Number(0);
    private RichPanelFormLayout searchForm;

    public ExpensePrdBean() {
    }

    public String resetExpPrdMstAction() {
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        return "reset";
    }
    public String resetExpPrdDtlAction() {
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        return "reset";
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

                /* Check if SelectOneChoice , reset its value*/
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

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setTot(oracle.jbo.domain.Number tot) {
        this.tot = tot;
    }

    public oracle.jbo.domain.Number getTot() {
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
        ExpensePrdAMImpl am = (ExpensePrdAMImpl)resolvElDC("ExpensePrdAMDataControl");
        ViewObjectImpl vo=am.getExpensePrdMstVO();
        RowSetIterator itr=vo.createRowSetIterator(null);
        while(itr.hasNext()) {
            Row row=itr.next();
            if(row.getAttribute("GlAmtTyp")!=null) {
                if(row.getAttribute("GlAmtTyp").toString().equals("Dr")) {
                   n=n.add((Number)row.getAttribute("GlAmtBs")) ;
                }
                else {
                    m=m.add((Number)row.getAttribute("GlAmtBs"));
                }
            }
        }
        sum=n.subtract(m);
        if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
                      dcmode="Dr";
                       }
                  if(sum.compareTo(0)==-1) {
                      dcmode="Cr";
                      sum=sum.multiply(new Number(-1));
                  }
        System.out.println("sum is ===="+sum);
        return sum;
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

    public void setDcmode(String dcmode) {
        this.dcmode = dcmode;
    }

    public String getDcmode() {
        return dcmode;
    }

    public void setPrdtot(Number prdtot) {
        this.prdtot = prdtot;
    }

    public Number getPrdtot() {
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
        ExpensePrdAMImpl am = (ExpensePrdAMImpl)resolvElDC("ExpensePrdAMDataControl");
        ViewObjectImpl vo=am.getExpensePrd1();
        RowSetIterator itr=vo.createRowSetIterator(null);
        while(itr.hasNext()) {
            Row row=itr.next();
            if(row.getAttribute("GlAmtTyp")!=null) {
                if(row.getAttribute("GlAmtTyp").toString().equals("Dr")) {
                   n=n.add((Number)row.getAttribute("GlAmtBs")) ;
                }
                else {
                    m=m.add((Number)row.getAttribute("GlAmtBs"));
                }
            }
        }
        sum=n.subtract(m);
        if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
                      prddcmode="Dr";
                       }
                  if(sum.compareTo(0)==-1) {
                      prddcmode="Cr";
                      sum=sum.multiply(new Number(-1));
                  }
        System.out.println("sum is ===="+sum);
        return sum;
    }

    public void setPrddcmode(String prddcmode) {
        this.prddcmode = prddcmode;
    }

    public String getPrddcmode() {
        return prddcmode;
    }
}
