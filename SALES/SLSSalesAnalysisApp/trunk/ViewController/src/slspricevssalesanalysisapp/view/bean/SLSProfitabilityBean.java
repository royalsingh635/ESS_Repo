package slspricevssalesanalysisapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.domain.Timestamp;
import java.util.Iterator;

import java.util.Map;

import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.component.chart.UILineChart;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class SLSProfitabilityBean {
    private UILineChart salesProfitabilityGrphBind;
    private RichTable profitTableBinding;

    public SLSProfitabilityBean() {
    }

    public void slsProfitabilitySL(SelectionEvent selectionEvent) {
        RowKeySet keySet = salesProfitabilityGrphBind.getSelectedRowKeys();
        Iterator<Object> iterator = keySet.iterator();
        Timestamp fromDt = null;
        Timestamp toDt = null;
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o != null) {
                  System.out.println("Key : " + o);
                try {
                    int i = o.toString().indexOf("]]");
                    String time = o.toString().substring(i - 22, i - 1);
                    Timestamp t1 = new Timestamp(time);
                    if (fromDt == null) {
                        fromDt = t1;
                    }
                    if (toDt == null) {
                        toDt = t1;
                    }
                    if (t1.compareTo(fromDt) < 0) {
                        fromDt = t1;
                    } else if (t1.compareTo(toDt) > 0) {
                        toDt = t1;
                    }
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                }
            }
        }

        System.out.println("From Date : " + fromDt);
        System.out.println("To Date : " + toDt);

        if (fromDt != null && toDt != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("setDataFilterOnProfitabilityTable");
            binding.getParamsMap().put("frmDt", fromDt);
            binding.getParamsMap().put("toDt", toDt);
            binding.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(profitTableBinding);
    }

    public void setSalesProfitabilityGrphBind(UILineChart salesProfitabilityGrphBind) {
        this.salesProfitabilityGrphBind = salesProfitabilityGrphBind;
    }

    public UILineChart getSalesProfitabilityGrphBind() {
        return salesProfitabilityGrphBind;
    }

    public String toInvoiceFromProfitAction() {
        return "toInvoice";
    }

    public void profitToInvoiceACE(ActionEvent actionEvent) {
        Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
        pageFlowScope.put("INVOIC_DOC_ID", actionEvent.getComponent().getAttributes().get("invoiceDocId"));
        
    }

    public void setProfitTableBinding(RichTable profitTableBinding) {
        this.profitTableBinding = profitTableBinding;
    }

    public RichTable getProfitTableBinding() {
        return profitTableBinding;
    }
}
