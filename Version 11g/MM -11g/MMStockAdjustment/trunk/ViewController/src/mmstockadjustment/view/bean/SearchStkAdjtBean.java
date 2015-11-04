package mmstockadjustment.view.bean;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchStkAdjtBean {


    private RichInputDate frmAdjtDate;
    private RichInputDate toAdjtDate;
    private RichSelectOneChoice bindAdjtStkStat;
    private RichInputComboboxListOfValues bindStkAdjtNo;

    public SearchStkAdjtBean() {
    }


  

    public void setFrmAdjtDate(RichInputDate frmAdjtDate) {
        this.frmAdjtDate = frmAdjtDate;
    }

    public RichInputDate getFrmAdjtDate() {
        return frmAdjtDate;
    }

    public void setToAdjtDate(RichInputDate toAdjtDate) {
        this.toAdjtDate = toAdjtDate;
    }

    public RichInputDate getToAdjtDate() {
        return toAdjtDate;
    }

    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public String searchAction() {
        // Add event code here...
        OperationBinding op=  getBindings().getOperationBinding("searchAction");
        op.getParamsMap().put("stkAdjtNo", bindStkAdjtNo.getValue());
        op.getParamsMap().put("frmDate", frmAdjtDate.getValue());
        op.getParamsMap().put("toDate", toAdjtDate.getValue());
        op.getParamsMap().put("stckStat", bindAdjtStkStat.getValue());
        op.execute();
        return null;
    }

    public String forwardAction() {
        // Add event code here...
         return "viewStkAdjt";
    }

    public void setBindAdjtStkStat(RichSelectOneChoice bindAdjtStkStat) {
        this.bindAdjtStkStat = bindAdjtStkStat;
    }

    public RichSelectOneChoice getBindAdjtStkStat() {
        return bindAdjtStkStat;
    }

    public void setBindStkAdjtNo(RichInputComboboxListOfValues bindStkAdjtNo) {
        this.bindStkAdjtNo = bindStkAdjtNo;
    }

    public RichInputComboboxListOfValues getBindStkAdjtNo() {
        return bindStkAdjtNo;
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        frmAdjtDate.setValue("");
        toAdjtDate.setValue("");
        bindAdjtStkStat.setValue(352);
        bindStkAdjtNo.setValue("");
        
        OperationBinding op=  getBindings().getOperationBinding("resetAction");
        op.execute();
      
    }
}
