package slsbiapp.view.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.binding.AttributeContext;
import oracle.binding.DataControl;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

import slsbiapp.model.services.SLSBIAMImpl;

import slsbiapp.view.bean.SLSBIBean;
import slsbiapp.view.dc.NavigationDC;
import slsbiapp.view.dc.TagDC;
import slsbiapp.view.dc.TagForItmDC;

public class DataObject implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    private static ADFLogger _log = ADFLogger.createADFLogger(DataObject.class);
    private String taskFlowId;
    private StringBuffer selectedItmId;
    /***  To represent whereClause from the filters in the Main Page ***/
    private StringBuffer whereClause;
    /***  To represent the current visible taskFlow */
    private StringBuffer currentTaskFlowId;
    /*** To make the link Navivgation in the Pages ****/
    private ArrayList<NavigationDC> navList = new ArrayList<NavigationDC>();
    private boolean filterChanged = false;
    /**To get an am instance for communication betweeb datacontrols**/
    SLSBIAMImpl am = null;
    
    /**
     * CurrentTickerId Shows the currently Selected Ticker. ID's are as follows :
     *  114    Rejection MIS
        113    Opportunity MIS
        112    Quotation MIS
        111    Order MIS
        110    Profit MIS
        109    Sales MIS
     */
    private Integer currentTickerId = 109;
    public DataObject() {
        
        super();
        initCurrentTickerId(109);
        
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
        getWhereClauseFromSelectedValues();
        return this.whereClause;
    }
    
    /**
     * To set the where clause which is built on the basis of several filters
     * @param whereClause
     */
    public void initWhereClause(StringBuffer whereClause){
        getWhereClauseFromSelectedValues();
        this.filterChanged = true;
    }
    
    /**
     * To set the value to currentTaskFlowId to identify which taskflow is currently visible on screen
     * @param currentTaskFlowId
     */
    public void initCurrentTaskFlowId(StringBuffer currentTaskFlowId){
        this.getApplicationModule().getTempVO().executeQuery();
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
        _log.fine("Setting Current Ticker Id to  : "+currentTickerId);
        this.currentTickerId = currentTickerId;
        this.filterChanged = true;
        getApplicationModule().executeVOsWidthCurrentTickerId(currentTickerId);
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
        _log.fine("Added Nav, Id : "+count+1+" | TfId :  "+taskFlowId+" | TfDesc : "+taskFlowDesc);
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
                _log.fine("Removed Nav , Id : "+n.getNavId()+" | TfId : "+n.getTaskFlowId()+" | TfDesc : "+n.getNavDesc());
                
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
    
/***** START ************************************************** TOP FILTERS *********************************************************************************/
        /*** ArrayList to show selected customers **/
    private ArrayList<TagDC> custList = new ArrayList<TagDC>();
     /*** ArrayList to hold selected customers when going to one page **/
    private ArrayList<TagDC> TempCustList = new ArrayList<TagDC>();
    
    /*** ArrayList to show selected Organisation **/
    private ArrayList<TagDC> orgList = new ArrayList<TagDC>();
    /*** ArrayList to hold selected Orgaisation when going to one page **/
    private ArrayList<TagDC> TempOrgList = new ArrayList<TagDC>();

    /*** ArrayList to show selected Item **/
    private ArrayList<TagForItmDC> itmList = new ArrayList<TagForItmDC>();
    /*** ArrayList to hold selected Item when going to one page **/
    private ArrayList<TagForItmDC> TempItmList = new ArrayList<TagForItmDC>();

    /*** ArrayList to show selected ItemGroup **/
    private ArrayList<TagForItmDC> itmGrp = new ArrayList<TagForItmDC>();
    /*** ArrayList to hold selected ItemGroup when going to one page **/
    private ArrayList<TagForItmDC> TempItmGrp = new ArrayList<TagForItmDC>();

    /*** ArrayList to show selected SalesExecutive **/
    private ArrayList<TagDC> salesExecutive = new ArrayList<TagDC>();
    /*** ArrayList to hold selected SalesExecutive when going to one page **/
    private ArrayList<TagDC> TempSalesExecutive = new ArrayList<TagDC>();
    
/*** START *************************************************** CUSTOMER LIST **********************************************************/
    /**
     * Method to Add Customer
     * @param custName
     */
    public void addCustomer(StringBuffer custId, StringBuffer custName) {
        _log.info("CustNm : " + custName + " CustId : " + custId);
        Integer i = 1;
        if (custName != null && custName.toString() != "") {
            if (custId != null && !custId.toString().equals("")) {
                for (TagDC dc : custList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("Second Id is -1");
                        StringBuffer newCustNm = new StringBuffer("");
                        if (custName.length() >= 12) {
                            newCustNm = new StringBuffer(custName.substring(0, 12) + "..");
                        } else {
                            newCustNm = new StringBuffer(custName);
                        }
                        dc.setIdSecond(custId);
                        dc.setDescSecond(custName);
                        dc.setValueSecond(newCustNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.fine("First Id is -1");
                        StringBuffer newCustNm = new StringBuffer("");
                        if (custName.length() >= 12) {
                            newCustNm = new StringBuffer(custName.substring(0, 12) + "..");
                        } else {
                            newCustNm = new StringBuffer(custName);
                        }
                        dc.setIdFirst(custId);
                        dc.setDescFirst(custName);
                        dc.setValueFirst(newCustNm);
                        i = 2;
                        break;
                    }
                }
                if (i == 1) {
                    StringBuffer newCustNm = new StringBuffer("");
                    if (custName.length() >= 12) {
                        newCustNm = new StringBuffer(custName.substring(0, 12) + "..");
                    } else {
                        newCustNm = new StringBuffer(custName);
                    }
                    _log.fine("New Id Added.");
                    custList.add(new TagDC(custId, newCustNm, custName, new StringBuffer("-1"), new StringBuffer(""),
                                           new StringBuffer("")));
                }
            }
        } else {
            FacesMessage msg = new FacesMessage("Invalid Customer name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    /**
     * Method to return OrgList
     * @return
     */
    public ArrayList<TagDC>  fetchCustList(){
        return custList;
    }
    
    /**
     * Method to removeCustomer
     * 
     */
    public void removeCustomer(StringBuffer custId) {
        TagDC removeCust = null;
        for (TagDC dc : custList) {
            if (dc.getIdFirst().toString().equals(custId.toString())) {
                _log.fine("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(custId.toString())) {
                _log.fine("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.fine("First & Second both Id's removed.");
                removeCust = dc;
                break;
            }
        }
        if (removeCust != null) {
            _log.fine("Removed Tag is :" + removeCust.getValueFirst() + " " + removeCust.getValueSecond());
            custList.remove(removeCust);
        }
    }
    
    /**
     * Method to clear CustomerList
     */
    public void clearCustomerList(){
        initFilterChange(true);
        custList.clear();
    }
    
    /**
     * Method that returns the in clause for selected customers;
     * @return
     */
    public StringBuffer getCustomerInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.custList.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("A.EO_ID IN (");
            for (TagDC dc : custList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < custList.size()) {
                    clause = clause.append(",");
                }
                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
/**** END **************************************************** CUSTOMER LIST *****************************************************/
/*** START *************************************************** SALES EXECUTIVE LIST **********************************************************/
    /**
     * Method to Add Sales Executive
     * @param execName
     * @param execId
     */
    public void addSalesExecutive(StringBuffer execId, StringBuffer execName) {
        _log.info("CustNm : " + execName + " CustId : " + execId);
        Integer i = 1;
        if (execName != null && execName.toString() != "") {
            if (execId != null && !execId.toString().equals("")) {
                for (TagDC dc : custList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("Second Id is -1");
                        StringBuffer newExecNm = new StringBuffer("");
                        if (execName.length() >= 12) {
                            newExecNm = new StringBuffer(execName.substring(0, 12) + "..");
                        } else {
                            newExecNm = new StringBuffer(execName);
                        }
                        dc.setIdSecond(execId);
                        dc.setDescSecond(execName);
                        dc.setValueSecond(newExecNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.fine("First Id is -1");
                        StringBuffer newExecNm = new StringBuffer("");
                        if (execName.length() >= 12) {
                            newExecNm = new StringBuffer(execName.substring(0, 12) + "..");
                        } else {
                            newExecNm = new StringBuffer(execName);
                        }
                        dc.setIdFirst(execId);
                        dc.setDescFirst(execName);
                        dc.setValueFirst(newExecNm);
                        i = 2;
                        break;
                    }
                }
                if (i == 1) {
                    StringBuffer newCustNm = new StringBuffer("");
                    if (execName.length() >= 12) {
                        newCustNm = new StringBuffer(execName.substring(0, 12) + "..");
                    } else {
                        newCustNm = new StringBuffer(execName);
                    }
                    _log.fine("New Id Added.");
                    custList.add(new TagDC(execId, newCustNm, execName, new StringBuffer("-1"), new StringBuffer(""),
                                           new StringBuffer("")));
                }
            }
        } else {
            FacesMessage msg = new FacesMessage("Invalid Business Executive name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    /**
     * Method to return Sales Executive
     * @return
     */
    public ArrayList<TagDC>  fetchSalesExectiveList(){
        return salesExecutive;
    }
    
    /**
     * Method to Sales Executive
     * 
     */
    public void removeSalesExecutive(StringBuffer execId) {
        TagDC removeCust = null;
        for (TagDC dc : salesExecutive) {
            if (dc.getIdFirst().toString().equals(execId.toString())) {
                _log.fine("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(execId.toString())) {
                _log.fine("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.fine("First & Second both Id's removed.");
                removeCust = dc;
                break;
            }
        }
        if (removeCust != null) {
            _log.fine("Removed Tag is :" + removeCust.getValueFirst() + " " + removeCust.getValueSecond());
            custList.remove(removeCust);
        }
    }
    
    /**
     * Method to clear Sales Executive
     */
    public void clearSalesExecutiveList(){
        initFilterChange(true);
        salesExecutive.clear();
    }
    
    /**
     * Method that returns the in clause for selected Sales Executive;
     * @return
     */
    public StringBuffer getSalesExecutiveInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.salesExecutive.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("A.SALES_EXEC IN (");
            for (TagDC dc : salesExecutive) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < salesExecutive.size()) {
                    clause = clause.append(",");
                }
                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
/**** END **************************************************** SALES EXECUTIVE LIST *****************************************************/

/*** START *************************************************** ORG LIST **********************************************************/
    
    /**
     * Method to Add Customer
     * @param orgId
     * @param orgName
     */
    public void addOrg(StringBuffer orgId, StringBuffer orgName) {
        _log.info("OrgNm : " + orgName + " OrgId : " + orgId);
        Integer i = 1;
        if (orgId != null || orgName != null) {
            if (!orgName.toString().equals("") || !orgId.toString().equals("")) {
                for (TagDC dc : orgList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("Second Id is -1");
                        StringBuffer newOrgNm = new StringBuffer("");
                        if (orgName.length() >= 12) {
                            newOrgNm = new StringBuffer(orgName.substring(0, 12) + "..");
                        } else {
                            newOrgNm = new StringBuffer(orgName);
                        }
                        _log.fine("Second Id is -1 so " + orgId);
                        dc.setIdSecond(orgId);
                        dc.setDescSecond(orgName);
                        dc.setValueSecond(newOrgNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.fine("First Id is -1");
                        StringBuffer newOrgNm = new StringBuffer("");
                        if (orgName.length() >= 12) {
                            newOrgNm = new StringBuffer(orgName.substring(0, 12) + "..");
                        } else {
                            newOrgNm = orgName;
                        }
                        _log.fine("First Id is -1 so " + orgId);
                        dc.setIdFirst(orgId);
                        dc.setDescFirst(orgName);
                        dc.setValueFirst(newOrgNm);
                        i = 2;
                        break;

                    }
                }
                if (i == 1) {
                    _log.fine("New Id Added.");
                    StringBuffer newOrgNm = new StringBuffer("");
                    if (orgName.length() >= 12) {
                        newOrgNm = new StringBuffer(orgName.substring(0, 12) + "..");
                    } else {
                        newOrgNm = new StringBuffer(orgName);
                    }
                    orgList.add(new TagDC(orgId, newOrgNm, orgName, new StringBuffer("-1"), new StringBuffer(""),
                                          new StringBuffer("")));

                }
            }
        }

    }
    
    /**
     * Method to return OrgList
     * @return
     */
    public ArrayList<TagDC>  fetchOrgList(){
        return orgList;
    }
    
    /**
     * Method to removeCustomer
     * 
     */
    public void removeOrg(StringBuffer orgId) {
        TagDC removeOrg = null;
        for (TagDC dc : orgList) {
            if (dc.getIdFirst().toString().equals(orgId.toString())) {
                _log.fine("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeOrg = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(orgId.toString())) {
                _log.fine("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeOrg = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.fine("First & Second both Id's removed.");
                removeOrg = dc;
                break;
            }
        }
        if (removeOrg != null) {
            _log.fine("Removed Tag is :" + removeOrg.getValueFirst() + " " + removeOrg.getValueSecond());
            orgList.remove(removeOrg);
        }
    }
    
    /**
     * Method to clear CustomerList
     */
    public void clearOrgList(){
        this.filterChanged = true;
        orgList.clear();
    }
    
    /**
     * Method that returns the in clause for selected organisation;
     * @return
     */
    public StringBuffer getOrgInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.orgList.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("A.ORG_ID IN (");
            for (TagDC dc : orgList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < orgList.size()) {
                    clause = clause.append(",");
                }

                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
/**** END **************************************************** ORG LIST ************************************************************/
/*** START *************************************************** ITM LIST ************************************************************/
    
    /**
     * Method to Add Itm
     * @param itmId
     * @param itmName
     */
    public void addItm(StringBuffer itmId, StringBuffer itmName) {
        _log.info("ItmNm : " + itmName + " ItmId : " + itmId);
        Integer i = 1;
        if(itmName != null || itmId != null){
            if (!itmName.toString().equals("") || !itmId.toString().equals("")) {
                for (TagForItmDC dc : itmList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("Second Id is -1");

                            _log.fine("Second Id is -1 so " + itmId);
                            dc.setIdSecond(itmId);
                            dc.setValueSecond(itmName);
                            i = 2;
                            break;
                        
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.fine("First Id is -1");
                            _log.fine("First Id is -1 so " + itmId);
                            dc.setIdFirst(itmId);
                            dc.setValueFirst(itmName);
                            i = 2;
                            break;
                    }
                }
                if (i == 1) {
                    _log.fine("New Id Added.");
                        itmList.add(new TagForItmDC(itmId,itmName, 
                                                    new StringBuffer("-1"),new StringBuffer("")));
                    
                }
            }
        }
    }
    
    /**
     * Method to return ItmList
     * @return
     */
    public ArrayList<TagForItmDC>  fetchItmList(){
        return itmList;
    }
    
    /**
     * Method to removeItm
     * 
     */
    public void removeItm(StringBuffer itmId) {
        TagForItmDC removeItm = null;
        if(itmId != null){
            for (TagForItmDC dc : itmList) {
                if (dc.getIdFirst().toString().equals(itmId.toString())) {
                    _log.fine("First Id removed.");
                    dc.setIdFirst(new StringBuffer("-1"));
                    dc.setValueFirst(new StringBuffer(""));
                    if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("First & Second both Id's removed.");
                        removeItm = dc;
                        break;
                    }
                    break;
                }
                if (dc.getIdSecond().equals(itmId)) {
                    _log.fine("Second Id removed.");
                    dc.setIdSecond(new StringBuffer("-1"));
                    dc.setValueSecond(new StringBuffer(""));
                    if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("First & Second both Id's removed.");
                        removeItm = dc;
                        break;
                    }
                    break;
                }
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeItm = dc;
                    break;
                }
            }
            if (removeItm != null) {
                itmList.remove(removeItm);
            }    
        }
    }
    
    /**
     * Method to clear ItmList
     */
    public void clearItmList(){
        this.filterChanged = true;
        itmList.clear();
    }
    
    /**
     * Method that returns the in clause for selected Items;
     * @return
     */
    public StringBuffer getItmInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.itmList.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("A.ITM_ID IN (");
            for (TagForItmDC dc : itmList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < itmList.size()) {
                    clause = clause.append(",");
                }

                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
/**** END **************************************************** ITM LIST *************************************************************/    

/*** START *************************************************** ITMGRP LIST **********************************************************/
   
    /**
     * Method to Add ItmGrp
     * @param itmGrpId
     * @param itmGrpName
     */
    public void addItmGrp(StringBuffer itmGrpId, StringBuffer itmGrpName) {
        _log.info("ItmGrpNm : " + itmGrpName + " ItmGepId : " + itmGrpId);
        Integer i = 1;
        if (itmGrpName != null || itmGrpId != null) {
            if (!itmGrpName.toString().equals("") || !itmGrpId.toString().equals("")) {
                for (TagForItmDC dc : itmGrp) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("Second Id is -1");

                        _log.fine("Second Id is -1 so " + itmGrpName);
                        dc.setIdSecond(itmGrpId);
                        dc.setValueSecond(itmGrpName);
                        i = 2;
                        break;

                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.fine("First Id is -1");
                        _log.fine("First Id is -1 so " + itmGrpId);
                        dc.setIdFirst(itmGrpId);
                        dc.setValueFirst(itmGrpName);
                        i = 2;
                        break;
                    }
                }
            }
            if (i == 1) {
                _log.fine("New Id Added.");
                itmGrp.add(new TagForItmDC(itmGrpId, itmGrpName, new StringBuffer("-1"), new StringBuffer("")));
            }
        }
    }
    
    /**
     * Method to return ItmGrpList
     * @return
     */
    public ArrayList<TagForItmDC>  fetchItmGrpList(){
        return itmGrp;
    }
    
    /**
     * Method to removeItmGrp
     *
     */
    public void removeItmGrp(StringBuffer grpId) {
        TagForItmDC removeItmGrp = null;
        if (grpId != null) {
            for (TagForItmDC dc : itmGrp) {
                if (dc.getIdFirst().toString().equals(grpId.toString())) {
                    _log.fine("First Id removed.");
                    dc.setIdFirst(new StringBuffer("-1"));
                    dc.setValueFirst(new StringBuffer(""));
                    if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("First & Second both Id's removed.");
                        removeItmGrp = dc;
                        break;
                    }
                    break;
                }
                if (dc.getIdSecond().equals(grpId.toString())) {
                    _log.fine("Second Id removed.");
                    dc.setIdSecond(new StringBuffer("-1"));
                    dc.setValueSecond(new StringBuffer(""));
                    if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                        _log.fine("First & Second both Id's removed.");
                        removeItmGrp = dc;
                        break;
                    }
                    break;
                }
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeItmGrp = dc;
                    break;
                }
            }
            if (removeItmGrp != null) {
                itmGrp.remove(removeItmGrp);
            }
        }

    }

    /**
     * Method to clear ItmGrpList
     */
    public void clearItmGrpList(){
        itmGrp.clear();
        this.filterChanged = true;
    }
    
    /**
     * Method that returns the in clause for selected ItemGrp
     * @return
     */
    public StringBuffer getItmGrpClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.itmGrp.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("A.GRP_ID IN (");
            for (TagForItmDC dc : itmGrp) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < itmGrp.size()) {
                    clause = clause.append(",");
                }
                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
    /**
     *  Method to clear all the filters
     */
    public void clearAllFilters(){
        this.filterChanged = true;
        this.orgList.clear();
        this.custList.clear();
        this.itmGrp.clear();
        this.itmList.clear();
        getWhereClauseFromSelectedValues();
    }
    
/**** END **************************************************** ITMGRP LIST **********************************************************/ 
    
        /**
     * Get final where clause string
     * @return
     */
    public StringBuffer getWhereClauseFromSelectedValues() {
        StringBuffer clause = new StringBuffer("");
        if (!getItmInClause().toString().equals("")) {
            clause = clause.append(" AND " + getItmInClause().toString());
        }
        if (!getOrgInClause().toString().equals("")) {
            clause = clause.append(" AND " + getOrgInClause().toString());
        }
        if (!getCustomerInClause().toString().equals("")) {
            clause = clause.append(" AND " + getCustomerInClause().toString());
        }
        if (!getItmGrpClause().toString().equals("")) {
            clause = clause.append(" AND " + getItmGrpClause().toString());
        }
        if (!getSalesExecutiveInClause().toString().equals("")) {
            clause = clause.append(" AND " + getSalesExecutiveInClause().toString());
        }
        
        this.whereClause = clause;
        getApplicationModule().setWhereClause(clause);
        _log.fine("Final where clause is : " + clause);
        return clause;
    }
        
    /**
     * Method return facetName to be shown in the page.
     * @return
     */
    public StringBuffer fetchFirstLovfacetName(){
        StringBuffer facetName = new StringBuffer("");
        Integer lovId = 0;
        Integer firstLovVal = this.getApplicationModule().getFirstLovValue();
        if(firstLovVal != null){
                lovId = (Integer)firstLovVal;    
        }
        if(currentTickerId == 109){
           facetName = new StringBuffer("salesAnalysis");
            switch(lovId){
                case 493 : facetName = new StringBuffer("topProducts");break;
                case 494 : facetName = new StringBuffer("topProductGroup");break;
                case 495 : facetName = new StringBuffer("topCustomer");break;
                case 496 : facetName = new StringBuffer("topSalesExecutive");break;
                case 497 : facetName = new StringBuffer("topRegions");break;
                case 498 : facetName = new StringBuffer("topOrganizations");break;
                case 499 : facetName = new StringBuffer("salesAnalysis");break;
                case 500 : facetName = new StringBuffer("consignmentSalesAnalysis");break;
                case 506 : facetName = new StringBuffer("customerOutstanding");break;
                case 518 : facetName = new StringBuffer("creditControl");break;
            }
        }else if(currentTickerId == 110){
            facetName = new StringBuffer("profitAnalysis");
            switch(lovId){
                case 507 : facetName = new StringBuffer("topProducts");break;
                case 508 : facetName = new StringBuffer("topProductGroup");break;
                case 509 : facetName = new StringBuffer("topCustomer");break;
                case 510 : facetName = new StringBuffer("topSalesExecutive");break;
                case 511 : facetName = new StringBuffer("topRegions");break;
                case 512 : facetName = new StringBuffer("topOrganizations");break;
                case 513 : facetName = new StringBuffer("profitAnalysis");break;
            }
        }else if(currentTickerId == 111){
            facetName = new StringBuffer("orderAnalysis");
            switch(lovId){
                case 514 : facetName = new StringBuffer("orderAnalysis");break;
                case 515 : facetName = new StringBuffer("shipmentAnalysis");break;
                case 516 : facetName = new StringBuffer("deliveryScheduleAnalysis");break;
                case 517 : facetName = new StringBuffer("targetAnalysis");break;
                case 524 : facetName = new StringBuffer("productAgeing");break;
                case 544 : facetName = new StringBuffer("topProducts");break;
                case 545 : facetName = new StringBuffer("topProductGroup");break;
                case 546 : facetName = new StringBuffer("topCustomer");break;
                case 547 : facetName = new StringBuffer("topSalesExecutive");break;
                case 548 : facetName = new StringBuffer("topRegions");break;
                case 559 : facetName = new StringBuffer("topOrganizations");break;
            }
        }else if(currentTickerId == 112){
            facetName = new StringBuffer("quotationAnalysis");
            switch(lovId){
                case 519 : facetName = new StringBuffer("quotationAnalysis");break;
                case 550 : facetName = new StringBuffer("topProducts");break;
                case 551 : facetName = new StringBuffer("topProductGroup");break;
                case 552 : facetName = new StringBuffer("topCustomer");break;
                case 553 : facetName = new StringBuffer("topSalesExecutive");break;
                case 554 : facetName = new StringBuffer("topRegions");break;
                case 555 : facetName = new StringBuffer("topOrganizations");break;
            }
        }else if(currentTickerId == 113){
            facetName = new StringBuffer("opportunityAnalysis");
            switch(lovId){
                case 520 : facetName = new StringBuffer("opportunityAnalysis");break;
                case 521 : facetName = new StringBuffer("targetAnalysis");break;
                case 522 : facetName = new StringBuffer("topSalesExecutive");break;
                case 556 : facetName = new StringBuffer("topProducts");break;
                case 557 : facetName = new StringBuffer("topProductGroup");break;
                case 558 : facetName = new StringBuffer("topCustomer");break;
                case 559 : facetName = new StringBuffer("topSalesExecutive");break;
                case 560 : facetName = new StringBuffer("topRegions");break;
                case 561 : facetName = new StringBuffer("topOrganizations");break;
            }
        }else if(currentTickerId == 114){
            facetName = new StringBuffer("rejectionAnalysis");
            switch(lovId){
                case 523 : facetName = new StringBuffer("rejectionAnalysis");break;
                case 562 : facetName = new StringBuffer("topProducts");break;
                case 563 : facetName = new StringBuffer("topProductGroup");break;
                case 564 : facetName = new StringBuffer("topCustomer");break;
                case 565 : facetName = new StringBuffer("topSalesExecutive");break;
                case 566 : facetName = new StringBuffer("topRegions");break;
                case 567 : facetName = new StringBuffer("topOrganizations");break;
            }
        }
        
        _log.info("FacetName Selected in firstLov is :"+facetName);
        return facetName;
    }
    
    /**
     * Method return facetName to be shown in the page.
     * @return
     */
    public StringBuffer fetchSecondLovfacetName(){
        StringBuffer facetName = new StringBuffer("");
        Integer lovId = 0;
        Integer secLovVal = this.getApplicationModule().getSecondLovValue();
        if(secLovVal != null){
                lovId = (Integer)secLovVal;    
        }
            if(currentTickerId == 109){
               facetName = new StringBuffer("salesAnalysis");
                switch(lovId){
                    case 493 : facetName = new StringBuffer("topProducts");break;
                    case 494 : facetName = new StringBuffer("topProductGroup");break;
                    case 495 : facetName = new StringBuffer("topCustomer");break;
                    case 496 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 497 : facetName = new StringBuffer("topRegions");break;
                    case 498 : facetName = new StringBuffer("topOrganizations");break;
                    case 499 : facetName = new StringBuffer("salesAnalysis");break;
                    case 500 : facetName = new StringBuffer("consignmentSalesAnalysis");break;
                    case 506 : facetName = new StringBuffer("customerOutstanding");break;
                    case 518 : facetName = new StringBuffer("creditControl");break;
                }
            }else if(currentTickerId == 110){
                facetName = new StringBuffer("profitAnalysis");
                switch(lovId){
                    case 507 : facetName = new StringBuffer("topProducts");break;
                    case 508 : facetName = new StringBuffer("topProductGroup");break;
                    case 509 : facetName = new StringBuffer("topCustomer");break;
                    case 510 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 511 : facetName = new StringBuffer("topRegions");break;
                    case 512 : facetName = new StringBuffer("topOrganizations");break;
                    case 513 : facetName = new StringBuffer("profitAnalysis");break;
                }
            }else if(currentTickerId == 111){
                facetName = new StringBuffer("orderAnalysis");
                switch(lovId){
                    case 514 : facetName = new StringBuffer("orderAnalysis");break;
                    case 515 : facetName = new StringBuffer("shipmentAnalysis");break;
                    case 516 : facetName = new StringBuffer("deliveryScheduleAnalysis");break;
                    case 517 : facetName = new StringBuffer("targetAnalysis");break;
                    case 524 : facetName = new StringBuffer("productAgeing");break;
                    case 544 : facetName = new StringBuffer("topProducts");break;
                    case 545 : facetName = new StringBuffer("topProductGroup");break;
                    case 546 : facetName = new StringBuffer("topCustomer");break;
                    case 547 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 548 : facetName = new StringBuffer("topRegions");break;
                    case 559 : facetName = new StringBuffer("topOrganizations");break;
                }
            }else if(currentTickerId == 112){
                facetName = new StringBuffer("quotationAnalysis");
                switch(lovId){
                    case 519 : facetName = new StringBuffer("quotationAnalysis");break;
                    case 550 : facetName = new StringBuffer("topProducts");break;
                    case 551 : facetName = new StringBuffer("topProductGroup");break;
                    case 552 : facetName = new StringBuffer("topCustomer");break;
                    case 553 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 554 : facetName = new StringBuffer("topRegions");break;
                    case 555 : facetName = new StringBuffer("topOrganizations");break;
                }
            }else if(currentTickerId == 113){
                facetName = new StringBuffer("opportunityAnalysis");
                switch(lovId){
                    case 520 : facetName = new StringBuffer("opportunityAnalysis");break;
                    case 521 : facetName = new StringBuffer("targetAnalysis");break;
                    case 522 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 556 : facetName = new StringBuffer("topProducts");break;
                    case 557 : facetName = new StringBuffer("topProductGroup");break;
                    case 558 : facetName = new StringBuffer("topCustomer");break;
                    case 559 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 560 : facetName = new StringBuffer("topRegions");break;
                    case 561 : facetName = new StringBuffer("topOrganizations");break;
                }
            }else if(currentTickerId == 114){
                facetName = new StringBuffer("rejectionAnalysis");
                switch(lovId){
                    case 523 : facetName = new StringBuffer("rejectionAnalysis");break;
                    case 562 : facetName = new StringBuffer("topProducts");break;
                    case 563 : facetName = new StringBuffer("topProductGroup");break;
                    case 564 : facetName = new StringBuffer("topCustomer");break;
                    case 565 : facetName = new StringBuffer("topSalesExecutive");break;
                    case 566 : facetName = new StringBuffer("topRegions");break;
                    case 567 : facetName = new StringBuffer("topOrganizations");break;
                }
            }
            _log.info("FacetName Selected in secondLov is :"+facetName);
        return facetName;
    }
        
/***** END **************************************************** TOP FILTERS *********************************************************/
/*------------------------------------------------------------------------+------------------------------------------------------------------------*/

    /**Method to filter oneCustomer Page
     * @param eoId
     * @param eoNm
     */
    public void initCustomerFilterForOnePage(StringBuffer eoId,StringBuffer eoNm){
        this.TempCustList = new ArrayList<TagDC>(custList);
        _log.info("Tempcustlist count :"+TempCustList.size());
        this.custList.clear();
        this.addCustomer(eoId, eoNm);
        getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
        _log.info("Tempcustlist count :"+TempCustList.size());
        _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
    }
    
    /**
     *  Method to regain the previous filter on return from the 
     */
    public void initPreviousFilterOnReturnFromOneCustPage(){
        _log.info("Tempcustlist count on return :"+TempCustList.size());
        custList = TempCustList;
        getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
        _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
    }
    
    /**Method to filter oneItm Page
         * @param itmId
         * @param itmNm
         */
    public void initProductFilterForOnePage(StringBuffer itmId,StringBuffer itmNm){
            this.TempItmList = new ArrayList<TagForItmDC>(itmList);
            _log.info("Tempcustlist count :"+TempCustList.size());
            this.itmList.clear();
            this.addItm(itmId, itmNm);
            getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
            _log.info("Tempcustlist count :"+TempCustList.size());
            _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
        }
    
    /**
     *  Method to regain the previous filter on return from the 
     */
    public void initPreviousFilterOnReturnFromOneProductPage(){
            _log.info("Tempcustlist count on return :"+TempItmList.size());
            itmList = TempItmList;
            getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
            _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
        }
    
    /**Method to filter oneOrg Page
         * @param orgId
         * @param orgNm
         */
    public void initOrgFilterForOnePage(StringBuffer orgId,StringBuffer orgNm){
            this.TempOrgList = new ArrayList<TagDC>(orgList);
            _log.info("Tempcustlist count :"+TempOrgList.size());
            this.orgList.clear();
            this.addOrg(orgId, orgNm);
            getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
            _log.info("Tempcustlist count :"+TempOrgList.size());
            _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
        }
    
    /**
     *  Method to regain the previous filter on return from the 
     */
    public void initPreviousFilterOnReturnFromOneOrganisation(){
            _log.info("Tempcustlist count on return :"+TempOrgList.size());
            orgList = TempOrgList;
            getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
            _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
        }
    
    /**
     * Method to get ApplicationModule
     * @return
     */
    public SLSBIAMImpl getApplicationModule(){
        if(am == null){
            am =(SLSBIAMImpl)resolvElDC("SLSBIAMDataControl");
        }
        return am;
    }

    /**Method to filter oneProductGroup Page
     * @param grpId
     * @param grpNm
     */
    public void initProductGroupFilterForOnePage(StringBuffer grpId,StringBuffer grpNm){
        this.TempItmGrp = new ArrayList<TagForItmDC>(itmGrp);
        _log.info("Tempcustlist count :"+TempItmGrp.size());
        this.itmGrp.clear();
        this.addItmGrp(grpId, grpNm);
        getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
        _log.info("Tempcustlist count :"+TempCustList.size());
        _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
    }
    
    /**
     *  Method to regain the previous filter on return from the 
     */
    public void initPreviousFilterOnReturnFromOneProductGroup(){
        _log.info("Tempcustlist count on return :"+TempItmGrp.size());
        itmGrp = TempItmGrp;
        getApplicationModule().setWhereClause(getWhereClauseFromSelectedValues());
        _log.info("where clause set to  :"+getWhereClauseFromSelectedValues());
    }
    
    /** Method to Resolve Am **/
    public Object resolvElDC(String data) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    Application app = fc.getApplication();
                    ExpressionFactory elFactory = app.getExpressionFactory();
                    ELContext elContext = fc.getELContext();
                    ValueExpression valueExp =
                            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
                    return valueExp.getValue(elContext);
            }
    


}
