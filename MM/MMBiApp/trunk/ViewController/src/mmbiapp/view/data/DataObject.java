package mmbiapp.view.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mmbiapp.view.beans.MMBiSupplierDetailsBean;

import mmbiapp.view.dc.NavigationDC;

import oracle.adf.share.logging.ADFLogger;

import oracle.binding.AttributeContext;
import oracle.binding.DataControl;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

public class DataObject implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    private String taskFlowId;
    private StringBuffer selectedItmId;
    /***  To represent whereClause from the filters in the Main Page */
    private StringBuffer whereClause;
    /***  To represent the current visible taskFlow */
    private StringBuffer currentTaskFlowId;
    /*** To make the link Navivgation in the Pages ****/
    private ArrayList<NavigationDC> navList = new ArrayList<NavigationDC>();
    private boolean filterChanged = false;
    private Integer currentTickerId = 118;
    private static ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(DataObject.class);
    public DataObject() {
        super();
    }

    public String getName() {
        return null;
    }

    public void release() {
    }

    public Object getDataProvider() {
        return null;
    }

    public boolean invokeOperation(Map p0, OperationBinding p1) {
        return false;
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

    public void beginRequest(HashMap p0) {
    }

    public void endRequest(HashMap p0) {
    }

    public boolean resetState() {
        return false;
    }

    public void setSelectedItmId(StringBuffer selectedItmId) {
        this.selectedItmId = selectedItmId;
    }

    public StringBuffer getSelectedItmId() {
        return selectedItmId;
    }

    public void setTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String getTaskFlowId() {
        return taskFlowId;
    }
    
    public StringBuffer fetchWhereClause(){
        return this.whereClause;
    }
    public void initWhereClause(StringBuffer whereClause){
      adfLog.info("settign where clause to  : "+whereClause);
        this.whereClause = whereClause;
    }
    public void initCurrentTaskFlowId(StringBuffer currentTaskFlowId){
        this.currentTaskFlowId = currentTaskFlowId;
    }
    public StringBuffer fetchCurrentTaskFlowId(){
        return this.currentTaskFlowId;
    }
    
    /**
     * Method to set current TickerId when a ticker is clicked.
     * @param currentTickerId
     */
    public void initCurrentTickerId(Integer currentTickerId){
        adfLog.fine("Setting Current Ticker Id to1  : "+currentTickerId);
        adfLog.info("Setting Current Ticker Id to  : "+currentTickerId);
        this.currentTickerId = currentTickerId;
    }
    /**
     * Fetch the current tickerID to enable or disable the top graphs of the details page.
     * @return
     */
    public Integer fetchCurrentTickerId(){
        //System.out.println("Ticker Id is where clause to  : "+currentTickerId);
        return currentTickerId;
    }
    
    /**
     * To provide NavigationLink facility. This method is used to add navigation to a page.
     * @param taskFlowId
     * @param taskFlowDesc
     */
    public void addNavNode(Integer taskFlowId,StringBuffer taskFlowDesc){
        Integer count = navList.size();
        navList.add(new NavigationDC(count+1,taskFlowId,taskFlowDesc));
        adfLog.fine("Added Nav, Id : "+count+1+" | TfId :  "+taskFlowId+" | TfDesc : "+taskFlowDesc);
    }
    /**
     * Method to remove a naviagtion node from the navigation list on basis of taskFlowId
     * @param taskFlowId
     */
    public void removeNavNode(Integer taskFlowId){
        Integer id = 0;
        for(NavigationDC nav : navList){
            if(nav.getTaskFlowId().equals(taskFlowId)){
                id = nav.getNavId();
                break;
            }
        }
        for(int i = 0; i< navList.size(); i++){
            NavigationDC n = null;
            for(NavigationDC nav : navList){
                if(nav.getNavId() >= id){
                    n = nav;
                    break;
                }
            }
            if(n != null ){
                adfLog.fine("Removed Nav , Id : "+n.getNavId()+" | TfId : "+n.getTaskFlowId()+" | TfDesc : "+n.getNavDesc());
                
                navList.remove(n);
                n = null;
            }
            
        }
    }
    /**
     *  TO remove all the entries form nav List
     */
    public void removeNavList(){
        navList.clear();
    }
    /**
     * To fetch the navigation List
     * @return
     */
    public ArrayList fetchNavList(){
        return navList;
    }
    
    /**
     * Method to Initialize and retrieve the filterr change.
     */
    public void initFilterChange(boolean val){
        filterChanged = val;
    }
    public boolean fetchFilterChange(){
        return this.filterChanged;
    }
}
