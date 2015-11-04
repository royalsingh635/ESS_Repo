package mmbiapp.view.beans;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import mmbiapp.view.dc.BlockRowDC;

import mmbiapp.view.dc.OrgDC;

import oracle.adf.model.BindingContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

public class MMBiOrgDetailBean {
    private StringBuffer labelTopNSuppliers = new StringBuffer("Top 5 Suppliers");
    private StringBuffer labelTopNProducts = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroups = new StringBuffer("Top 5 Product Groups");
    private StringBuffer labelTopNBelowReorder = new StringBuffer("Top 5 Products Below Reorder Level");
    private StringBuffer labelTopNBelowSafty = new StringBuffer("Top 5 Products Below Safety Level");
    private static ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(MMBiOrgDetailBean.class);
    private ArrayList<BlockRowDC> orgList = new ArrayList<BlockRowDC>();
    private StringBuffer selectedItmId = new StringBuffer("");
    public MMBiOrgDetailBean() {
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
    public void topNSuppliersVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNSuppliers = new StringBuffer("");
            labelTopNSuppliers.append("Top ");
            labelTopNSuppliers.append(valueChangeEvent.getNewValue());
            labelTopNSuppliers.append(" Suppliers");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNSupplierVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        } 
    }

    public void topNProductVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(" new value "+valueChangeEvent.getNewValue());
     
        ///
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
            System.out.println("clause    "+clause+"  value -- "+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }

    public void topNProductGroupVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductGroups = new StringBuffer("");
            labelTopNProductGroups.append("Top ");
            labelTopNProductGroups.append(valueChangeEvent.getNewValue());
            labelTopNProductGroups.append(" Products Groups");
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
    
    public void topNBelowReoderLvlVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNBelowReorder = new StringBuffer("");
            labelTopNBelowReorder.append("Top ");
            labelTopNBelowReorder.append(valueChangeEvent.getNewValue());
            labelTopNBelowReorder.append(" Products Below Reorder Level");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductBelowReoderLvlVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        } 
    }
    
    
    public void topNBelowSaftyLvlVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNBelowSafty = new StringBuffer("");
            labelTopNBelowSafty.append("Top ");
            labelTopNBelowSafty.append(valueChangeEvent.getNewValue());
            labelTopNBelowSafty.append(" Products Below Safety Level");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductBelowSaftyLvlVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
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
               adfLog.info("No of rows : "+itr.getRowCount());
                Row next = null;
                while(itr.hasNext()){
                    
                    OrgDC col1 = new OrgDC();
                    OrgDC col2 = new OrgDC();
                    OrgDC col3 = new OrgDC();
                    next = itr.next();
                    if(next != null){
                        col1.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                        col1.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                        }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col2.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                            col2.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                             } 
                    }
                    if(itr.hasNext()){
                        next = itr.next();
                        if(next != null){
                            col3.setAmount(next.getAttribute("Amount") == null ? new StringBuffer("00.00") : new StringBuffer(next.getAttribute("Amount").toString()));
                            col3.setOrgDesc(new StringBuffer(next.getAttribute("OrgDesc").toString()));
                            } 
                    }
                    orgList.add(new BlockRowDC<OrgDC>(col1,col2,col3,null,null));
                    adfLog.info("RowAdded----------------------------");
                    
                }
                itr.closeRowSetIterator();
            }
            
            
        }
        
    }
    
    
    /**
     *  To reflect the changes of the filter
     */
    public void filterChangeReflector(){
       /*  OperationBinding binding = this.getBindings().getOperationBinding("fetchFilterChange");
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
                populateBusinessExecutiveVO();
                
                OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
                newBinding.getParamsMap().put("val", false);
                newBinding.execute();
            }
        }
         */
    }


    public StringBuffer getLabelTopNSuppliers() {
        return labelTopNSuppliers;
    }

    public StringBuffer getLabelTopNProducts() {
        return labelTopNProducts;
    }

    public StringBuffer getLabelTopNProductGroups() {
        return labelTopNProductGroups;
    }

    public void setSelectedItmId(StringBuffer selectedItmId) {
        this.selectedItmId = selectedItmId;
    }

    public StringBuffer getSelectedItmId() {
        return selectedItmId;
    }

    public StringBuffer getLabelTopNBelowReorder() {
        return labelTopNBelowReorder;
    }

    public StringBuffer getLabelTopNBelowSafty() {
        return labelTopNBelowSafty;
    }

    public ArrayList<BlockRowDC> getOrgList() {
        if(orgList.size() == 0){
            populateOrgList();
        }
        filterChangeReflector();
        return orgList;
    }
}
