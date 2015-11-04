package slsviewdetailsapp.view;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import slsviewdetailsapp.model.module.SLSViewDetailsAppAMImpl;

public class SlsViewDetailsbean {
    private RichTable lotDetailPgBind;

    public SlsViewDetailsbean() {
    }

    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void WareHouseVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding binding=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("filterVo");
            binding.execute();

        }
    }

    public void setLotDetailPgBind(RichTable lotDetailPgBind) {
        this.lotDetailPgBind = lotDetailPgBind;
    }

    public RichTable getLotDetailPgBind() {
        return lotDetailPgBind;
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

}


