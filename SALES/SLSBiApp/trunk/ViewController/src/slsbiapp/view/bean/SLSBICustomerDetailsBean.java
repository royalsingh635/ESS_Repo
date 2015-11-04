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
import slsbiapp.view.dc.OrgDC;
import slsbiapp.view.dc.SalesOrderAmtDC;
import slsbiapp.view.dc.SalesmanDC;

public class SLSBICustomerDetailsBean {
    private StringBuffer labelTopNCustomers = new StringBuffer("Top 5 Customers");
    private StringBuffer labelTopNProducts = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroups = new StringBuffer("Top 5 Product Groups");
    private ArrayList<BlockRowDC> orgList = new ArrayList<BlockRowDC>();
    private ArrayList<BlockRowDC> salesManList = new ArrayList<BlockRowDC>();
    private ArrayList<BlockRowDC> saleOrderAmt = new ArrayList<BlockRowDC>();
    private StringBuffer selectedItmId = new StringBuffer("");
    private StringBuffer selectedItmDesc = new StringBuffer("");
    private StringBuffer selectedOrgId = new StringBuffer("");
    private StringBuffer selectedOrgDesc = new StringBuffer("");
    
    private StringBuffer selectedProductGrpDesc = new StringBuffer("Sample");
    private StringBuffer selectedProductGrpId = new StringBuffer("Sample");
    public SLSBICustomerDetailsBean() {
    }

    public StringBuffer getLabelTopNCustomers() {
        return labelTopNCustomers;
    }

    public StringBuffer getLabelTopNProducts() {
        return labelTopNProducts;
    }

    public StringBuffer getLabelTopNProductGroups() {
        return labelTopNProductGroups;
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
     * Method to perform action on Products Number on VCL
     * @param valueChangeEvent
     */
    public void topNProductsVCL(ValueChangeEvent valueChangeEvent) {
        //System.out.println("new value is : |"+valueChangeEvent.getNewValue()+"| ad old value is : |"+valueChangeEvent.getOldValue()+"|"+(!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString())) );
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProducts = new StringBuffer("");
            labelTopNProducts.append("Top ");
            labelTopNProducts.append(valueChangeEvent.getNewValue());
            labelTopNProducts.append(" Products");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }
    
    /**
     * Method to perform action on Products Group Number on VCL
     * @param valueChangeEvent
     */
    public void topNProductsGroupVCL(ValueChangeEvent valueChangeEvent) {
        //System.out.println("new value is : |"+valueChangeEvent.getNewValue()+"| ad old value is : |"+valueChangeEvent.getOldValue()+"|"+(!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString())) );
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductGroups = new StringBuffer("");
            labelTopNProductGroups.append("Top ");
            labelTopNProductGroups.append(valueChangeEvent.getNewValue());
            labelTopNProductGroups.append(" Products");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
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
    
    /**
     * Method to populate SalesExecuteList
     * @return
     */
    public ArrayList<BlockRowDC> getBusinessExecutiveVO(){  
        if(salesManList.size() == 0){
            populateBusinessExecutiveVO();
        }
        filterChangeReflector();
        return salesManList;
    }
    
    /**
     *  populate Business Executive VO
     */
    public void populateBusinessExecutiveVO(){       
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
     *  Populate OrgList
     */
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
    
    /**
     * Return array list of TopOrgs
     * @return
     */
    public ArrayList<BlockRowDC> getOrgList(){
        if(orgList.size() == 0){
            populateOrgList();
        }
        filterChangeReflector();
        return orgList;
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
                
                orgList.clear();
                populateOrgList();
                
                salesManList.clear();
                populateBusinessExecutiveVO();
                
                OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
                newBinding.getParamsMap().put("val", false);
                newBinding.execute();
            }
        }
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
     *  Method to populate sales order
     */
    public void populateSalesOrdrList(){
        OperationBinding binding = this.getBindings().getOperationBinding("getSalesOrdrAmtRows");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
                RowSetIterator itr = (RowSetIterator)binding.getResult();
                //System.out.println("No of rows : "+itr.getRowCount());
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
    /**
     * Method to return ArrayList to populate SalesOrderAmtList
     * @return
     */
    public ArrayList<BlockRowDC> getSalesOrderAmtList(){
        if(saleOrderAmt.size() == 0){
            populateSalesOrdrList();    
        }
        filterChangeReflector();
        return saleOrderAmt;
    }


    public void goToSelectedCustomerACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer custId = (ob.getAttributes().get("custId") == null ? new StringBuffer("") : new StringBuffer(ob.getAttributes().get("custId").toString()));
        StringBuffer custDesc = (ob.getAttributes().get("custDesc") == null ? new StringBuffer("") : new StringBuffer(ob.getAttributes().get("custDesc").toString()));
        //System.out.println("custId is : "+custId+" custDesc : "+custDesc);
        if(custId != null || custDesc != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initCustomerFilterForOnePage");
            binding.getParamsMap().put("eoId", custId);
            binding.getParamsMap().put("eoNm", custDesc);
            binding.execute();
        }
    }

    public void goToItemDetailsACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer itmId = (ob.getAttributes().get("itmId") == null ? new StringBuffer("") : new StringBuffer(ob.getAttributes().get("itmId").toString()));
        StringBuffer itmDesc = (ob.getAttributes().get("itmDesc") == null ? new StringBuffer("") : new StringBuffer(ob.getAttributes().get("itmDesc").toString()));
        this.selectedItmId = itmId;
        this.selectedItmDesc = itmDesc;
        //System.out.println("itmId is : "+itmId+" itmDesc : "+itmDesc);
        if(itmId != null || itmDesc != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initProductFilterForOnePage");
            binding.getParamsMap().put("itmId", itmId);
            binding.getParamsMap().put("itmNm", itmDesc);
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
        selectedOrgDesc = orgDesc;
        selectedOrgId = orgId;
        //System.out.println("OrgId is : "+orgId+" OrgDesc : "+orgDesc);
        if(orgId != null || orgDesc != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initOrgFilterForOnePage");
            binding.getParamsMap().put("orgId", orgId);
            binding.getParamsMap().put("orgNm", orgDesc);
            binding.execute();
        }
    }

    public void setSelectedItmId(StringBuffer selectedItmId) {
        this.selectedItmId = selectedItmId;
    }

    public StringBuffer getSelectedItmId() {
        return selectedItmId;
    }

    public void setSelectedItmDesc(StringBuffer selectedItmDesc) {
        this.selectedItmDesc = selectedItmDesc;
    }

    public StringBuffer getSelectedItmDesc() {
        return selectedItmDesc;
    }

    public void setSelectedOrgId(StringBuffer selectedOrgId) {
        this.selectedOrgId = selectedOrgId;
    }

    public StringBuffer getSelectedOrgId() {
        return selectedOrgId;
    }

    public void setSelectedOrgDesc(StringBuffer selectedOrgDesc) {
        this.selectedOrgDesc = selectedOrgDesc;
    }

    public StringBuffer getSelectedOrgDesc() {
        return selectedOrgDesc;
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
     * Method to set initial pramas for on Product Page
     * @param actionEvent
     */
    public void goToOneProductGroupACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        String grpId = (String)ob.getAttributes().get("grpId");
        String grpDesc = (String)ob.getAttributes().get("grpNm");
        //System.out.println("GrpId : "+grpId+" GrpDesc : "+grpDesc);
        selectedProductGrpDesc = (grpDesc == null ? new StringBuffer("") : new StringBuffer(grpDesc));
        selectedProductGrpId = (grpId == null ? new StringBuffer("") : new StringBuffer(grpId));
        //System.out.println("SelectedGrpId : "+selectedProductGrpId+" SelectedGrpDesc : "+selectedProductGrpDesc);
        if(grpDesc != null && grpId != null){
            OperationBinding binding = this.getBindings().getOperationBinding("initProductGroupFilterForOnePage");
            binding.getParamsMap().put("grpId", new StringBuffer(grpId));
            binding.getParamsMap().put("grpNm", new StringBuffer(grpDesc));
            binding.execute();    
        }
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
}
