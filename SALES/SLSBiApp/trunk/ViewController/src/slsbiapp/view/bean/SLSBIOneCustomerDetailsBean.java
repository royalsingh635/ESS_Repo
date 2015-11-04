package slsbiapp.view.bean;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import slsbiapp.view.dc.BlockRowDC;
import slsbiapp.view.dc.NavigationDC;
import slsbiapp.view.dc.OrgDC;
import slsbiapp.view.dc.SalesOrderAmtDC;
import slsbiapp.view.dc.SalesmanDC;

public class SLSBIOneCustomerDetailsBean {
    private StringBuffer labelTopNProducts = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroups = new StringBuffer("Top 5 Product Groups");
    private StringBuffer labelTopNSalesOrders = new StringBuffer("Top 5 Sales Order");
    private StringBuffer labelTopNCustomers = new StringBuffer("Top 5 Customers");
    
    /**
     *  To Show Top SalesMan and Top Organisations
     */
    private ArrayList<BlockRowDC> salesManList = new ArrayList<BlockRowDC>();
    private ArrayList<BlockRowDC> orgList = new ArrayList<BlockRowDC>();
    private ArrayList<BlockRowDC> saleOrderAmt = new ArrayList<BlockRowDC>();
    
    private StringBuffer selectedOrganisationDesc = new StringBuffer("Sample");
    private StringBuffer selectedOrganisationId = new StringBuffer("Sample");
    private StringBuffer selectedProductGrpDesc = new StringBuffer("Sample");
    private StringBuffer selectedProductGrpId = new StringBuffer("Sample");
    
    public SLSBIOneCustomerDetailsBean() {
    }
    
    /**
     * Method to perform action on Customer Number on VCL
     * @param valueChangeEvent
     */
    public void topNCustomersVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNCustomers = new StringBuffer("");
            labelTopNCustomers.append("Top ");
            labelTopNCustomers.append(valueChangeEvent.getNewValue());
            labelTopNCustomers.append(" Customers");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            
            /* if(resolvElO("#{pageFlowScope.WHERE_CLAUSE_FOR_ORG_DETAILS}")!= null){
                clause = (StringBuffer)resolvElO("#{pageFlowScope.WHERE_CLAUSE_FOR_ORG_DETAILS}");
            } */
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustomerVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        } 
    }

    /**
     * VCL to filter No. Of Products on the selected value
     * @param valueChangeEvent
     */
    public void topNProductsVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProducts = new StringBuffer("");
            labelTopNProducts.append("Top ");
            labelTopNProducts.append(valueChangeEvent.getNewValue());
            labelTopNProducts.append(" Products");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }

            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }
    /**
     * VCL to filter No. Of ProductGroup on the selected value
     * @param valueChangeEvent
     */
    public void topNProductGrpVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductGroups = new StringBuffer("");
            labelTopNProductGroups.append("Top ");
            labelTopNProductGroups.append(valueChangeEvent.getNewValue());
            labelTopNProductGroups.append(" Product Groups");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }
    /**
     * VCL to filter No. Of SalesOrder on the selected value
     * @param valueChangeEvent
     */
    public void topNSalesOrderVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNSalesOrders = new StringBuffer("");
            labelTopNSalesOrders.append("Top ");
            labelTopNSalesOrders.append(valueChangeEvent.getNewValue());
            labelTopNSalesOrders.append(" Sales Order");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNSalesOrderVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }

    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**Method to resolve expression- returns String value*/
    public Object resolvElO(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object obj = valueExp.getValue(elContext);
        return obj;
    }
    public void setLabelTopNProducts(StringBuffer labelTopNProducts) {
        this.labelTopNProducts = labelTopNProducts;
    }

    public StringBuffer getLabelTopNProducts() {
        return labelTopNProducts;
    }


    public StringBuffer getLabelTopNProductGroups() {
        return labelTopNProductGroups;
    }


    public StringBuffer getLabelTopNSalesOrders() {
        return labelTopNSalesOrders;
    }
    /**
     * Get the current tickerId to hide or show the graph 
     * @return
     */
    public Integer getCurrentTickerId(){
        OperationBinding binding = this.getBindings().getOperationBinding("fetchCurrentTickerId");
        if(binding != null){
            binding.execute();
            return (Integer)binding.getResult();
        }else{
            return 1;
        }
    }
    /**
     * Method to get the navigation list 
     * @return
     */
    public ArrayList getNavList(){
        ArrayList<NavigationDC> list = new ArrayList<NavigationDC>();
        OperationBinding binding = this.getBindings().getOperationBinding("fetchNavList");
        if(binding != null){
            binding.execute();
            list = (ArrayList<NavigationDC>) binding.getResult();
        }
        return list;
    }
    
    public void populateSalesManList(){
        OperationBinding binding = this.getBindings().getOperationBinding("getBusinessExecutiveRows");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
                RowSetIterator itr = (RowSetIterator)binding.getResult();
                Row next = null;
                while(itr.hasNext()){
                    SalesmanDC col1 = new SalesmanDC();
                    SalesmanDC col2 = new SalesmanDC();
                    SalesmanDC col3 = new SalesmanDC();
                    next = itr.next();
                    if(next != null){
                        col1.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                        col1.setEoId((Integer)next.getAttribute("EoId") == null ? 0 : (Integer)next.getAttribute("EoId"));
                        col1.setDesignation(new StringBuffer("Executive"));
                        col1.setLocation(new StringBuffer("India"));
                        col1.setName(next.getAttribute("SalesExecutive") == null ? new StringBuffer("An Employee") : new StringBuffer(next.getAttribute("SalesExecutive").toString()));
                    }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col2.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                            col2.setEoId((Integer)next.getAttribute("EoId") == null ? 0 : (Integer)next.getAttribute("EoId"));
                            col2.setDesignation(new StringBuffer("Executive"));
                            col2.setLocation(new StringBuffer("India"));
                            col2.setName(next.getAttribute("SalesExecutive") == null ? new StringBuffer("An Employee") : new StringBuffer(next.getAttribute("SalesExecutive").toString()));
                        } 
                    }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col3.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                            col3.setEoId((Integer)next.getAttribute("EoId") == null ? 0 : (Integer)next.getAttribute("EoId"));
                            col3.setDesignation(new StringBuffer("Executive"));
                            col3.setLocation(new StringBuffer("India"));
                            col3.setName(next.getAttribute("SalesExecutive") == null ? new StringBuffer("An Employee") : new StringBuffer(next.getAttribute("SalesExecutive").toString()));
                        } 
                    }
                    salesManList.add(new BlockRowDC<SalesmanDC>(col1,col2,col3,null,null));
                    //System.out.println("RowAdded");
                    
                }
            }
            
        }
        
    }
    /**
     * Getter to get all the sales man rows
     * @return
     */
    public ArrayList<BlockRowDC> getSalesMan(){
        if(salesManList.size() == 0){
            populateSalesManList();
            //
        }
        filterChangeReflector();
        return salesManList;
    }
    
    public void populateOrgList(){
        OperationBinding binding = this.getBindings().getOperationBinding("getTopNOrgRows");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
                RowSetIterator itr = (RowSetIterator)binding.getResult();
                //System.out.println("No of rows : "+itr.getRowCount());
                Row next = null;
                while(itr.hasNext()){
                    
                    OrgDC col1 = new OrgDC();
                    OrgDC col2 = new OrgDC();
                    OrgDC col3 = new OrgDC();
                    next = itr.next();
                    if(next != null){
                        col1.setAmount(next.getAttribute("InvAmt") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("InvAmt").toString()));
                        col1.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                        col1.setOrgId(new StringBuffer(next.getAttribute("OrgId").toString()));
                        }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col2.setAmount(next.getAttribute("InvAmt") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("InvAmt").toString()));
                            col2.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                            col2.setOrgId(new StringBuffer(next.getAttribute("OrgId").toString()));
                             } 
                    }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col3.setAmount(next.getAttribute("InvAmt") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("InvAmt").toString()));
                            col3.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                            col3.setOrgId(new StringBuffer(next.getAttribute("OrgId").toString()));
                            } 
                    }
                    orgList.add(new BlockRowDC<OrgDC>(col1,col2,col3,null,null));
                    //System.out.println("RowAdded");
                    
                }
                itr.closeRowSetIterator();
            }
            
            
        }
        
    }
    
    
    public ArrayList<BlockRowDC> getOrgList(){
        if(orgList.size() == 0){
            populateOrgList();
        }
        filterChangeReflector();
        return orgList;
    }
    /**
     *  Method to populate sales order
     */
    public void populateSalesOrdrList(){
        OperationBinding binding = this.getBindings().getOperationBinding("getSalesOrdrAmtRows");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
                RowSetIterator itr = (RowSetIterator)binding.getResult();
                Row next = null;
                while(itr.hasNext()){
                    
                    SalesOrderAmtDC col1 = new SalesOrderAmtDC();
                    SalesOrderAmtDC col2 = new SalesOrderAmtDC();
                    SalesOrderAmtDC col3 = new SalesOrderAmtDC();
                    next = itr.next();
                    if(next != null){
                        col1.setOdrAmt(next.getAttribute("OrderAmount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("OrderAmount").toString()));
                        col1.setOdrDesc(new StringBuffer(next.getAttribute("OrderType").toString()));
                        }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                                 col2.setOdrAmt(next.getAttribute("OrderAmount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("OrderAmount").toString()));
                                 col2.setOdrDesc(new StringBuffer(next.getAttribute("OrderType").toString()));
                                  } 
                    }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                                 col3.setOdrAmt(next.getAttribute("OrderAmount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("OrderAmount").toString()));
                                 col3.setOdrDesc(new StringBuffer(next.getAttribute("OrderType").toString()));
                                  } 
                    }
                    saleOrderAmt.add(new BlockRowDC<SalesOrderAmtDC>(col1,col2,col3,null,null));
                    //System.out.println("RowAdded");
                }
                itr.closeRowSetIterator();
            }   
        }
    }
    
    public ArrayList<BlockRowDC> getSalesOrderAmtList(){
        if(saleOrderAmt.size() == 0){
            populateSalesOrdrList();
        }
        filterChangeReflector();
        return saleOrderAmt;
    }
    
    /**
     *  To reflect the changes of the filter
     */
    public void filterChangeReflector(){
        OperationBinding binding = this.getBindings().getOperationBinding("fetchFilterChange");
        if(binding != null){
            binding.execute();
            Boolean changed = (Boolean)binding.getResult();
            //System.out.println("Filter changed is "+changed+ " so vo's refreshed");
            if(changed){
                saleOrderAmt.clear();
                populateSalesOrdrList();
                
                orgList.clear();
                populateOrgList();
                
                salesManList.clear();
                populateSalesManList();
                
                OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
                newBinding.getParamsMap().put("val", false);
                newBinding.execute();
            }
        }
    }
    /**
     * Method return facetName to be shown in the page.
     * @return
     */
    public StringBuffer getFirstLovfacetName(){
        StringBuffer facetName = new StringBuffer("");
        OperationBinding secondLovVal = this.getBindings().getOperationBinding("fetchFirstLovfacetName");
        if(secondLovVal != null){
            secondLovVal.execute();
            if(secondLovVal.getResult() != null){
                facetName = (StringBuffer)secondLovVal.getResult();
            }    
        }
        return facetName;
    }
    
    /**
     * Method return facetName to be shown in the page.
     * @return
     */
    public StringBuffer getSecondLovfacetName(){
        StringBuffer facetName = new StringBuffer("");
        OperationBinding firstLovVal = this.getBindings().getOperationBinding("fetchSecondLovfacetName");
        if(firstLovVal != null){
            firstLovVal.execute();
            if(firstLovVal.getResult() != null){
                facetName = (StringBuffer)firstLovVal.getResult();
            }    
        }
        return facetName;
    }
    /**
     * Method to set initial pramas for on Product Page
     * @param actionEvent
     */
    public void goTooneProductACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        String itmId = (String)ob.getAttributes().get("ItmId");
        String itmDesc = (String)ob.getAttributes().get("ItmNm");
        //System.out.println("ItmId : "+itmId+" ItmDesc : "+itmDesc);
        if(itmDesc != null && itmId != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initProductFilterForOnePage");
            binding.getParamsMap().put("itmId", new StringBuffer(itmId));
            binding.getParamsMap().put("itmNm", new StringBuffer(itmDesc));
            binding.execute();    
        }
    }
    
    /**
     * Method to go to one organisation page
     * @param actionEvent
     */
    public void goToSelectedOrgACTION(ActionEvent actionEvent) {
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        StringBuffer orgId = (StringBuffer)ob.getAttributes().get("orgId");
        StringBuffer orgDesc = (StringBuffer)ob.getAttributes().get("orgDesc");
        selectedOrganisationDesc = orgDesc;
        selectedOrganisationId = orgId;
        //System.out.println("OrgId is : "+orgId+" OrgDesc : "+orgDesc);
        if(orgId != null || orgDesc != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initOrgFilterForOnePage");
            binding.getParamsMap().put("orgId", orgId);
            binding.getParamsMap().put("orgNm", orgDesc);
            binding.execute();
        }
    }
    /**
     * Method to set initial pramas for on ProductGrp Page
     * @param actionEvent
     */
    public void goToOneProductGroupACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        String grpId = (String)ob.getAttributes().get("grpId");
        String grpDesc = (String)ob.getAttributes().get("grpNm");
        //System.out.println("GrpId : "+grpId+" GrpDesc : "+grpDesc);
        selectedProductGrpDesc = (grpDesc == null ? new StringBuffer("") : new StringBuffer(grpDesc));
        selectedProductGrpId = (grpId == null ? new StringBuffer("") : new StringBuffer(grpId));
        if(grpDesc != null && grpId != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initProductGroupFilterForOnePage");
            binding.getParamsMap().put("grpId", new StringBuffer(grpId));
            binding.getParamsMap().put("grpNm", new StringBuffer(grpDesc));
            binding.execute();    
        }
    }


    public void setSelectedOrganisationDesc(StringBuffer selectedOrganisationDesc) {
        this.selectedOrganisationDesc = selectedOrganisationDesc;
    }

    public StringBuffer getSelectedOrganisationDesc() {
        return selectedOrganisationDesc;
    }

    public void setSelectedOrganisationId(StringBuffer selectedOrganisationId) {
        this.selectedOrganisationId = selectedOrganisationId;
    }

    public StringBuffer getSelectedOrganisationId() {
        return selectedOrganisationId;
    }

    public void setSelectedProductGrpDesc(StringBuffer selectedProductGrpDesc) {
        this.selectedProductGrpDesc = selectedProductGrpDesc;
    }

    public StringBuffer getSelectedProductGrpDesc() {
        return selectedProductGrpDesc;
    }

    public void setSelectedProductGrpId(StringBuffer selectedProductGrpId) {
        this.selectedProductGrpId = selectedProductGrpId;
    }

    public StringBuffer getSelectedProductGrpId() {
        return selectedProductGrpId;
    }

    public void setLabelTopNProductGroups(StringBuffer labelTopNProductGroups) {
        this.labelTopNProductGroups = labelTopNProductGroups;
    }

    public void setLabelTopNSalesOrders(StringBuffer labelTopNSalesOrders) {
        this.labelTopNSalesOrders = labelTopNSalesOrders;
    }

    public StringBuffer getLabelTopNCustomers() {
        return labelTopNCustomers;
    }
}
