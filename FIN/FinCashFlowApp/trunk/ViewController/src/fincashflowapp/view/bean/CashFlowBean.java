package fincashflowapp.view.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.OperationBinding;

public class CashFlowBean {

    protected List<Filter> filterList;
    private RichTable filterDetailTable;
    private String fy_desc;
    private Integer fy_id;
    private String coa_nm;
    private Integer coa_id;
    private String qtr_desc;
    private String qtr_id;
    private String mon_nm;
    private Integer mon_id;
    private String incExp_desc;
    private UIGraph inFlwOutFlwGraph;
    private RichOutputText filterId;

    public CashFlowBean() {
        populateList();
    }

    public String printAction() {
        
        printDVTComponent(inFlwOutFlwGraph.getClientId());
        return null;
    }


    private void printDVTComponent(String clientId) {
        //find starting component

        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        root.invokeOnComponent(fctx, clientId, new DvtContextCallBack());
    }


    public void setFilterDetailTable(RichTable filterDetailTable) {
        this.filterDetailTable = filterDetailTable;
    }

    public RichTable getFilterDetailTable() {
        return filterDetailTable;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFy_desc(String fy_desc) {
        this.fy_desc = fy_desc;
    }

    public String getFy_desc() {
        return fy_desc;
    }

    public void setCoa_nm(String coa_nm) {
        this.coa_nm = coa_nm;
    }

    public String getCoa_nm() {
        return coa_nm;
    }

    public void setQtr_desc(String qtr_desc) {
        this.qtr_desc = qtr_desc;
    }

    public String getQtr_desc() {
        return qtr_desc;
    }

    public void setMon_nm(String mon_nm) {
        this.mon_nm = mon_nm;
    }

    public String getMon_nm() {
        return mon_nm;
    }

    public void setIncExp_desc(String incExp_desc) {
        this.incExp_desc = incExp_desc;
    }

    public String getIncExp_desc() {
        return incExp_desc;
    }

    public void setFinancialYear(ActionEvent actionEvent) {
        String fy = getCurrentRowAttr("FyCashFlowVO", "OrgFyId");
        setFy_id(Integer.parseInt(fy));
        String setFyNm = getCurrentRowAttr("LovOrgFyVO", "Fy");
        setFy_desc(setFyNm);
        populateList();
    }

    public void setMonthId(ActionEvent actionEvent) {
        String setVal = getCurrentRowAttr("MonthCashFlowVO", "MonthId");
        setMon_id(Integer.parseInt(setVal));
        setMon_nm(setVal);
        populateList();
    }

    public void setQuarter(ActionEvent actionEvent) {
        String setVal = getCurrentRowAttr("QuarterCashFlowVO", "Quarter");

        setQtr_desc(setVal);
        setQtr_id(setVal);
        populateList();
    }

    public void setCoaId(ActionEvent actionEvent) {
        String setVal = getCurrentRowAttr("COACashFlowVO", "CoaId");
        setCoa_id(Integer.parseInt(setVal));
        System.out.println(" setVal " + setVal);
        String setNmVal = getCurrentRowAttr("COACashFlowVO", "CoaNm");
        System.out.println(" setNmVal " + setNmVal);
        setCoa_nm(setNmVal);
        setCoa_id(coa_id);
        populateList();
    }

    public String getCurrentRowAttr(String voNm, String attrNm) {

        OperationBinding op =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getCurrRowAttribute");
        op.getParamsMap().put("voNm", voNm);
        op.getParamsMap().put("attrNm", attrNm);
        op.execute();

        String retVal = op.getResult().toString();

        return retVal;
    }

    public void setFy_id(Integer fy_id) {
        this.fy_id = fy_id;
    }

    public Integer getFy_id() {
        return fy_id;
    }

    public void setCoa_id(Integer coa_id) {
        this.coa_id = coa_id;
    }

    public Integer getCoa_id() {
        return coa_id;
    }

    public void setQtr_id(String qtr_id) {
        this.qtr_id = qtr_id;
    }

    public String getQtr_id() {
        return qtr_id;
    }

    public void setMon_id(Integer mon_id) {
        this.mon_id = mon_id;
    }

    public Integer getMon_id() {
        return mon_id;
    }

    public String resetAction(ActionEvent actionEvent) {

        setFy_id(null);
        setFy_desc(null);

        setCoa_id(null);
        setCoa_nm(null);

        setQtr_desc(null);
        setQtr_id(null);

        setMon_id(null);
        setMon_nm(null);

        populateList();
        
        OperationBinding op =
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setParameters");
        op.execute();
        
        return "reset";
        
        
    }

    public void populateList() {
        filterList = new ArrayList();
        filterList.add(new Filter("FINANCIAL YEAR", getFy_desc(), "", "FY"));
        filterList.add(new Filter("CHART OF ACCOUNT", getCoa_nm(), "", "COA"));
        filterList.add(new Filter("QUARTER", getQtr_desc(), "", "QTR"));
        filterList.add(new Filter("MONTH", getMon_nm(), "", "MON"));
        filterList.add(new Filter("INCOME/EXP A/C", getIncExp_desc(), "", "Income/Exp A/C"));
    }

    public void removeFilter(ActionEvent actionEvent) {
        System.out.println("filterType " + filterId.getValue());
        String filtVal = filterId.getValue().toString();

        if (filtVal.equalsIgnoreCase("FY")) {

            setFy_id(null);
            setFy_desc(null);

        } else if (filtVal.equalsIgnoreCase("COA")) {

            setCoa_id(null);
            setCoa_nm(null);

        } else if (filtVal.equalsIgnoreCase("QTR")) {

            setQtr_desc(null);
            setQtr_id(null);

        } else if (filtVal.equalsIgnoreCase("Month")) {

            setMon_id(null);
            setMon_nm(null);

        } else if (filtVal.equalsIgnoreCase("Income/Exp A/C")) {
        }

        populateList();
    }

    public void setInFlwOutFlwGraph(UIGraph inFlwOutFlwGraph) {
        this.inFlwOutFlwGraph = inFlwOutFlwGraph;
    }

    public UIGraph getInFlwOutFlwGraph() {
        return inFlwOutFlwGraph;
    }

    public void setFilterId(RichOutputText filterId) {
        this.filterId = filterId;
    }

    public RichOutputText getFilterId() {
        return filterId;
    }
}
