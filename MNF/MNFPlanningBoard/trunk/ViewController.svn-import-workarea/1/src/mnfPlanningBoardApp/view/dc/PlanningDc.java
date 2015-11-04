package mnfPlanningBoardApp.view.dc;

import java.io.Serializable;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import oracle.binding.AttributeContext;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;

public class PlanningDc {
    public PlanningDc() {
        super();
    }

    public Serializable createSnapshot() {
        return null;
    }

    public void restoreSnapshot(Serializable p0) {
    }

    public void removeSnapshot(Serializable p0) {
    }

    public boolean isTransactionDirty() {
        return false;
    }

    public void rollbackTransaction() {
    }

    public void commitTransaction() {
    }

    public boolean setAttributeValue(AttributeContext p0, Object p1) {
        return false;
    }

    public Object createRowData(RowContext p0) {
        return null;
    }

    public Object registerDataProvider(RowContext p0) {
        return null;
    }

    public boolean removeRowData(RowContext p0) {
        return false;
    }

    public void validate() {
    }
    
    private Integer currentTF = 1;
    private String taskFlowId = "/WEB-INF/productTF.xml#productTF";
    //Task flow id 
    /**
     * Product Task flow = 1
     * Customer = 2
     * BOM = 3
     * Raw =4
     * Suplier = 5
     * 
     * @param tf
     */

    public void initCurrentTfId(Integer tf){
        
        currentTF = tf;
        
    }
    public Integer fetchCurrenttfId(){
        refreshPage();
        System.out.println(currentTF + "  000000000000000000000000000000000000000 ");
        return currentTF;
    }
    public void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }
    OperationBinding ob = null;

    public void goToCustomer() {
        
       
            taskFlowId = "/WEB-INF/customerTF.xml#customerTF";

    }

    public void goToSupplier() {
                   
            taskFlowId = "/WEB-INF/supplierTF.xml#supplierTF";

    }

    public void goToBOM() {
        
        
            taskFlowId = "/WEB-INF/bomTF.xml#bomTF";
  
    }

    public void goToRM() {
        

            taskFlowId = "/WEB-INF/rawMaterialTF.xml#rawMaterialTF";
   
    }

    public void goToProduct() { 
       
            taskFlowId = "/WEB-INF/productTF.xml#productTF";

    }
    
}

