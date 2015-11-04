package slspricevssalesanalysisapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.component.chart.UILineChart;

import oracle.adf.view.faces.bi.component.treemap.UITreemap;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.model.TreeModel;


import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.component.UIXHierarchy;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

public class PriceVsSalesBean {
    private UILineChart priceVsLIneGraphBinding;
    private UITreemap treeMapBinding;
    private RichTable invoicePerItemTableBinding;

    public PriceVsSalesBean() {
    }

    public void setPriceVsLIneGraphBinding(UILineChart priceVsLIneGraphBinding) {
        this.priceVsLIneGraphBinding = priceVsLIneGraphBinding;
    }

    public UILineChart getPriceVsLIneGraphBinding() {
        return priceVsLIneGraphBinding;
    }

    public void treeMapItemSelectionListner(SelectionEvent selectionEvent) {
        ADFBeanUtils.invokeEL("#{bindings.PriceVsDemand2ndLevelItem11.treeModel.makeCurrent}", new Class[] {
                              SelectionEvent.class }, new Object[] { selectionEvent });

        System.out.println(selectionEvent.getAddedSet() + " Inside selection listner for tree map");

        System.out.println(treeMapBinding.getCurrentRowData());
        System.out.println(treeMapBinding.getNodeSelection());


        ADFBeanUtils.findOperation("filterInvoiceFrPriceVsDemand").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(invoicePerItemTableBinding);
    }

    public void setTreeMapBinding(UITreemap treeMapBinding) {
        this.treeMapBinding = treeMapBinding;
    }

    public UITreemap getTreeMapBinding() {
        return treeMapBinding;
    }

    public String toInvoiceAction() {
        
        return "toInvoice";
    }

    public void incoiveLinkACE(ActionEvent actionEvent) {
        Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
        pageFlowScope.put("INVOIC_DOC_ID", actionEvent.getComponent().getAttributes().get("InvoiceDocId"));
       
    }

    public void setInvoicePerItemTableBinding(RichTable invoicePerItemTableBinding) {
        this.invoicePerItemTableBinding = invoicePerItemTableBinding;
    }

    public RichTable getInvoicePerItemTableBinding() {
        return invoicePerItemTableBinding;
    }
}
