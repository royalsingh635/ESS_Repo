package glApp.view.bean;

import glApp.model.module.GlAppAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

public class DrCrNoteBean {
    public DrCrNoteBean() {
    }

    public void selectAllvalueChange(ValueChangeEvent valueChangeEvent) {
        boolean isSelected = (Boolean)valueChangeEvent.getNewValue();
        GlAppAMImpl am = (GlAppAMImpl)resolveEl("GlAppAMDataControl");
        ViewObject v1 = am.findViewObject("GlAdjTmp1");
        RowSetIterator rit = v1.createRowSetIterator(null);
        

        while (rit.hasNext()) {
            Row row = rit.next();
            if (isSelected) {
                row.setAttribute("selectThis", true);
                row.setAttribute("RadjAmtAdj", row.getAttribute("RadjAmtSp"));
            } else {
                row.setAttribute("selectThis", false);
                if (((Number)(row.getAttribute("RadjAmtSp"))).compareTo((Number)(row.getAttribute("RadjAmtAdj"))) ==
                    0) {
                    row.setAttribute("RadjAmtAdj", new Number(0));
                }
            }
        }
    }

    public void selectThisValueChange(ValueChangeEvent valueChangeEvent) {
        boolean isSelected = (Boolean)valueChangeEvent.getNewValue();
        GlAppAMImpl am = (GlAppAMImpl)resolveEl("GlAppAMDataControl");
        ViewObject v1 = am.findViewObject("GlAdjTmp1");
        Row row = v1.getCurrentRow();
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            row.setAttribute("RadjAmtAdj", row.getAttribute("RadjAmtSp"));
            // adjustedSum.setReadOnly(true);
        }
        if (valueChangeEvent.getNewValue().toString().equals("false") &&
            ((Number)(row.getAttribute("RadjAmtSp"))).compareTo((Number)(row.getAttribute("RadjAmtAdj"))) ==
            0) {

            row.setAttribute("RadjAmtAdj", new Number(0));
            // adjustedSum.setReadOnly(false);
        }
    }
    public Object resolveEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
}
