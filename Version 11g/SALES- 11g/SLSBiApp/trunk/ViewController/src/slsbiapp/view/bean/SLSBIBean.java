package slsbiapp.view.bean;

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

import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;

import oracle.jbo.RowSetIterator;

import slsbiapp.view.dc.TagDC;
import slsbiapp.view.dc.TagForItmDC;
import slsbiapp.view.dc.TickerDC;
import slsbiapp.view.dc.TimeSelectorDC;

public class SLSBIBean {
    private static ADFLogger _log = ADFLogger.createADFLogger(SLSBIBean.class);
    private boolean refresh;
    /*** ArrayList to show selected customers **/
    private ArrayList<TagDC> custList = new ArrayList<TagDC>();
    private String custName;
     /*** ArrayList to hold selected customers when going to one page **/
    private ArrayList<TagDC> TempCustList = new ArrayList<TagDC>();
    
    /*** ArrayList to show selected Organisation **/
    private ArrayList<TagDC> orgList = new ArrayList<TagDC>();
    private String OrgName;
    /*** ArrayList to hold selected Orgaisation when going to one page **/
    private ArrayList<TagDC> TempOrgList = new ArrayList<TagDC>();

    /*** ArrayList to show selected Item **/
    private ArrayList<TagForItmDC> itmList = new ArrayList<TagForItmDC>();
    private String itmName;
    /*** ArrayList to hold selected Item when going to one page **/
    private ArrayList<TagForItmDC> TempItmList = new ArrayList<TagForItmDC>();

    /*** ArrayList to show selected ItemGroup **/
    private ArrayList<TagForItmDC> itmGrp = new ArrayList<TagForItmDC>();
    private String grpNm;
    /*** ArrayList to hold selected ItemGroup when going to one page **/
    private ArrayList<TagForItmDC> TempItmGrp = new ArrayList<TagForItmDC>();


    private ArrayList<TimeSelectorDC> timeSelectorList = new ArrayList<TimeSelectorDC>();
    private ArrayList<TickerDC> tickerList = new ArrayList<TickerDC>();
    private StringBuffer labelTopNCustomers = new StringBuffer("Top 5 Customers");
    private StringBuffer labelTopNProducts = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroups = new StringBuffer("Top 5 Product Groups");
    private RichInputText customerNmBind;
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private StringBuffer  initTfId = new StringBuffer("1");


    public SLSBIBean() {
        timeSelectorList.add(new TimeSelectorDC("1", new StringBuffer("March"), false));
        timeSelectorList.add(new TimeSelectorDC("2", new StringBuffer("Quarter"), true));
        timeSelectorList.add(new TimeSelectorDC("3", new StringBuffer("Half"), false));
        timeSelectorList.add(new TimeSelectorDC("4", new StringBuffer("Year"), false));
        timeSelectorList.add(new TimeSelectorDC("5", new StringBuffer("Time"), false));
        /* tickerList.add(new TickerDC(109, new StringBuffer("$ 33.09 mn"), new StringBuffer("Sales in Mar 2013"), true));
        tickerList.add(new TickerDC(110, new StringBuffer("$ 33.09 mn"), new StringBuffer("Profit in Mar 2013"), false));
        tickerList.add(new TickerDC(111, new StringBuffer("3.03 %"), new StringBuffer("% Order Completed"), false));
        tickerList.add(new TickerDC(113, new StringBuffer("81.09 %"), new StringBuffer("% Opportunity"), false));
        tickerList.add(new TickerDC(112, new StringBuffer("52.09 %"), new StringBuffer("% Quotation Completed"), false));
        tickerList.add(new TickerDC(114, new StringBuffer("03.09 %"), new StringBuffer("% Rejection"), false)); */
        /*  tickerList.add(new TickerDC(7,new StringBuffer("$ 73.09 mn"),new StringBuffer("Sales in Sept 2013"),false));
        tickerList.add(new TickerDC(8,new StringBuffer("$ 03.04 mn"),new StringBuffer("Sales in Sept 2013"),false));
         */ /* tickerList.add(new TickerDC(9,new StringBuffer("$ 80.49 mn"),new StringBuffer("Sales in Sept 2013"),false));
        tickerList.add(new TickerDC(10,new StringBuffer("$ 7.09 mn"),new StringBuffer("Sales in Sept 2013"),false));
        tickerList.add(new TickerDC(11,new StringBuffer("$ 03.04 mn"),new StringBuffer("Sales in Sept 2013"),false));
        tickerList.add(new TickerDC(12,new StringBuffer("$ 80.49 mn"),new StringBuffer("Sales in Sept 2013"),false));
        tickerList.add(new TickerDC(13,new StringBuffer("$ 7.09 mn"),new StringBuffer("Sales in Sept 2013"),false)); */
    }

    /****************************************************** CUSTOMER LIST **********************************************************/

    /**
     * Method Action to Add Customer
     * @param actionEvent
     */
    public void addCustomerACTION(ActionEvent actionEvent) {
        OperationBinding bind = this.getBindings().getOperationBinding("getEoIdOnEoNm");
        bind.getParamsMap().put("arg", new StringBuffer(custName));
        bind.execute();
        Object custId = bind.getResult();
        if( custId != null){
            if(!custId.equals("")){
                OperationBinding binding = this.getBindings().getOperationBinding("addCustomer");
                binding.getParamsMap().put("custId", new StringBuffer(custId.toString()));
                binding.getParamsMap().put("custName", new StringBuffer(custName));
                binding.execute();
                custName = "";
                _log.info("Added");
            }
        }else{
            FacesMessage msg = new FacesMessage("Invalid Customer name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(customerNmBind.getClientId(), msg);
        }
        
    }

    /**
     * Method to removeCustomer
     * @param actionEvent
     */
    public void removeCustomerACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer custId = (StringBuffer)ob.getAttributes().get("custId");
        OperationBinding binding = this.getBindings().getOperationBinding("removeCustomer");
        binding.getParamsMap().put("custId", custId);
        binding.execute();
    }

    /**
     * Method Clear Customer list
     * @param actionEvent
     */
    public void clearCustomerListACTION(ActionEvent actionEvent) {
        OperationBinding removeBinding = this.getBindings().getOperationBinding("clearCustomerList");
        removeBinding.execute();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        _log.fine("Id : "+i);
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }

    /**
     * Method to apply filter on the basis of selected Customer
     * @param actionEvent
     */
    public void applyFilterForSelectedCustomerACTION(ActionEvent actionEvent) {
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        _log.fine("Id : "+i);
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
        
    }
    
    /**
     * Method returns custArrayList
     * @return
     */
    public ArrayList<TagDC> getCustList() {
        //fetchCustList
        OperationBinding binding = this.getBindings().getOperationBinding("fetchCustList");
        if(binding != null){
            binding.execute();
            custList = (ArrayList<TagDC>)binding.getResult();
        }
        return custList;
    }
  
    /****************************************************** ORG LIST **********************************************************/

    /**
     * Method Action to Add Org
     * @param actionEvent
     */
    public void addOrgACTION(ActionEvent actionEvent) {
        OperationBinding bind = this.getBindings().getOperationBinding("getOrgIdOnOrgNm");
        bind.getParamsMap().put("arg", new StringBuffer(OrgName));
        bind.execute();
        Object orgId = bind.getResult();
        if( orgId != null){
            if(!orgId.equals("")){
                OperationBinding binding = this.getBindings().getOperationBinding("addOrg");
                binding.getParamsMap().put("orgId", new StringBuffer(orgId.toString()));
                binding.getParamsMap().put("orgName", new StringBuffer(OrgName));
                binding.execute();
                OrgName = "";
                _log.info("Added");
            }
        }else{
            FacesMessage msg = new FacesMessage("Invalid Org name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
            OrgName = "";
    }

    /**
     * Method to remove org
     * @param actionEvent
     */
    public void removeOrgACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer custId = (StringBuffer)ob.getAttributes().get("custId");
        OperationBinding binding = this.getBindings().getOperationBinding("removeOrg");
        binding.getParamsMap().put("orgId", custId);
        binding.execute();

    }
    
    /**
     * Method to apply filter on the basis of selected Organisation
     * @param actionEvent
     */
    public void applyFilterForSelectedOrganisationsACTION(ActionEvent actionEvent) {
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }
    
    /**
     * method to Clear org list
     * @param actionEvent
     */
    public void clearOrgListACTION(ActionEvent actionEvent) {
        OperationBinding removeBinding = this.getBindings().getOperationBinding("clearOrgList");
        removeBinding.execute();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }
    
    /**
     * Method to return orgList
     * @return
     */
    public ArrayList<TagDC> getOrgList() {
        OperationBinding binding = this.getBindings().getOperationBinding("fetchOrgList");
        if(binding != null){
            binding.execute();
            orgList = (ArrayList<TagDC>)binding.getResult();
        }
        return orgList;
    }
    /****************************************************** ITM LIST **********************************************************/

    /**
     * Method Action to Add Itm
     * @param actionEvent
     */
    public void addItmACTION(ActionEvent actionEvent) {
        OperationBinding bind = this.getBindings().getOperationBinding("getItmIdOnItmNm");
        bind.getParamsMap().put("arg", new StringBuffer(itmName));
        bind.execute();
        Object orgId = bind.getResult();
        if( orgId != null){
            if(!orgId.equals("")){
                OperationBinding binding = this.getBindings().getOperationBinding("addItm");
                binding.getParamsMap().put("itmId", new StringBuffer(orgId.toString()));
                binding.getParamsMap().put("itmName", new StringBuffer(itmName));
                binding.execute();
                _log.info("Added");
            }
        }else{
            FacesMessage msg = new FacesMessage("Invalid Product name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
            itmName = "";
    }

    /**
     * Method to remove itm
     * @param actionEvent
     */
    public void removeItmACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer itmId = (StringBuffer)ob.getAttributes().get("custId");
         OperationBinding binding = this.getBindings().getOperationBinding("removeItm");
        binding.getParamsMap().put("itmId", itmId);
        binding.execute();


    }
    
    /**
     * Method to apply filter on the basis of selected Item
     * @param actionEvent
     */
    public void applyFilterForSelectedItemsACTION(ActionEvent actionEvent) {
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }

    /**
     * Method to Clear itm list
     * @param actionEvent
     */
    public void clearItmListACTION(ActionEvent actionEvent) {
        OperationBinding removeBinding = this.getBindings().getOperationBinding("clearItmList");
        removeBinding.execute();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();

    }
    
    public ArrayList<TagForItmDC> getItmList() {
        OperationBinding binding = this.getBindings().getOperationBinding("fetchItmList");
        if(binding != null){
            binding.execute();
            itmList = (ArrayList<TagForItmDC>)binding.getResult();
        }
        return itmList;
    }
    /****************************************************** ITM GRP LIST **********************************************************/

    /**
     * Method Action to Add ItmGrp
     * @param actionEvent
     */
    public void addItmGrpACTION(ActionEvent actionEvent) {
        OperationBinding bind = this.getBindings().getOperationBinding("getItmGrpIdOnItmNm");
        bind.getParamsMap().put("arg", new StringBuffer(grpNm));
        bind.execute();
        Object orgId = bind.getResult();
        if( orgId != null){
            if(!orgId.equals("")){
                OperationBinding binding = this.getBindings().getOperationBinding("addItmGrp");
                binding.getParamsMap().put("itmGrpId", new StringBuffer(orgId.toString()));
                binding.getParamsMap().put("itmGrpName", new StringBuffer(grpNm));
                binding.execute();
                _log.info("Added");
            }else{
                
            FacesMessage msg = new FacesMessage("Invalid Product Group name!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        }
        grpNm = "";
    }

    /**
     * Method to remove ItmGrp
     * @param actionEvent
     */
    public void removeItmGrpACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer itmGrpId = (StringBuffer)ob.getAttributes().get("custId");
        OperationBinding binding = this.getBindings().getOperationBinding("removeItmGrp");
        binding.getParamsMap().put("grpId", itmGrpId);
        binding.execute();
    }
        
    /**
     * Method to apply filter on the basis of selected ItemGrp
     * @param actionEvent
     */
    public void applyFilterForSelectedItemGrpACTION(ActionEvent actionEvent) {
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();

    }

    /**
     * Methdo to Clear itmGrp list
     * @param actionEvent
     */
    public void clearItmGrpListACTION(ActionEvent actionEvent) {
        OperationBinding removeBinding = this.getBindings().getOperationBinding("clearItmGrpList");
        removeBinding.execute();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }
    
    /**
     * Method to getItmGrpList
     * @return
     */
    public ArrayList<TagForItmDC> getItmGrp() {
        OperationBinding binding = this.getBindings().getOperationBinding("fetchItmGrpList");
        if(binding != null){
            binding.execute();
            itmGrp = (ArrayList<TagForItmDC>)binding.getResult();
        }
        return itmGrp;
    }

    /****************************************************** FOR THE TIMESELECTION FOR TOP LINKS *********************************/
    public ArrayList<TimeSelectorDC> getTimeSelectorTopLinks() {
        return timeSelectorList;
    }

    public void topTimeLinkACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        String linkId = (String)ob.getAttributes().get("linkId");
        _log.fine("LInkID id : " + linkId);
        for (TimeSelectorDC list : timeSelectorList) {
            _log.fine(" matched : " + list.getLinkId().equals(linkId));
            if (list.getLinkId().equals(linkId)) {
                _log.fine("Set to true");
                list.setActive(true);
            } else {
                list.setActive(false);
            }
        }

    }

    /****************************************************** FOR TICKERS *********************************************************/
    public ArrayList<TickerDC> getTickerList() {
        if(tickerList.size() == 0){
            populateTopTickersList();
        }
        return tickerList;
    }
    
    /**
     *  Method to populate top tickers
     */
    
    public void populateTopTickersList(){
        tickerList.clear();
        Boolean i = true;
        OperationBinding binding = this.getBindings().getOperationBinding("getTopTickerRows");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
               RowSetIterator itr = (RowSetIterator) binding.getResult();
               while(itr.hasNext()){
                    Row next = itr.next();
                   /* _log.info(""+next.getAttribute("MisTypeId"));
                   _log.info(next.getAttribute("MisTypeNot").toString());
                   _log.info(next.getAttribute("ItmAmt").toString());
                   _log.info(next.getAttribute("AmtType").toString()); */
                    tickerList.add(new TickerDC((Integer)next.getAttribute("MisTypeId"), 
                                                new StringBuffer(next.getAttribute("MisTypeNot").toString()+" "+next.getAttribute("ItmAmt").toString()+" "+next.getAttribute("AmtType").toString()), 
                                                new StringBuffer(next.getAttribute("MisTypeDesc").toString()), i));
                   i = false;
               }
                itr.closeRowSetIterator();
            }
            
        }
    }

    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setCustList(ArrayList<TagDC> custList) {
        this.custList = custList;
    }

   

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setOrgList(ArrayList<TagDC> orgList) {
        this.orgList = orgList;
    }

    

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setItmList(ArrayList<TagForItmDC> itmList) {
        this.itmList = itmList;
    }

    

    public void setItmName(String itmName) {
        this.itmName = itmName;
    }

    public String getItmName() {
        return itmName;
    }

    public void setCustomerNmBind(RichInputText customerNmBind) {
        this.customerNmBind = customerNmBind;
    }

    public RichInputText getCustomerNmBind() {
        return customerNmBind;
    }

    public void setItmGrp(ArrayList<TagForItmDC> itmGrp) {
        this.itmGrp = itmGrp;
    }

    

    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    public String getGrpNm() {
        return grpNm;
    }

    public StringBuffer getLabelTopNCustomers() {
        return labelTopNCustomers;
    }

    /**
     * VCL to set No Of Customers in TopNCutomers
     * @param valueChangeEvent
     */
    public void topNCustomersVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            labelTopNCustomers = new StringBuffer("");
            labelTopNCustomers.append("Top ");
            labelTopNCustomers.append(valueChangeEvent.getNewValue());
            labelTopNCustomers.append(" Customers");

            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustomerVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.execute();
        }
    }

    public StringBuffer getLabelTopNProducts() {
        return labelTopNProducts;
    }

    /**
     * VCL to set No Of Customers in TopNCutomers
     * @param valueChangeEvent
     */
    public void topNProductsVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            StringBuffer clause = new StringBuffer("");
            OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            whereClauseBinding.execute();
            if(whereClauseBinding.getResult() != null){
                clause = (StringBuffer)whereClauseBinding.getResult();
            }
            labelTopNProducts = new StringBuffer("");
            labelTopNProducts.append("Top ");
            labelTopNProducts.append(valueChangeEvent.getNewValue());
            labelTopNProducts.append(" Products");
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
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
     * Methdod to return refresh to refresh the regionBinding 
     * @return
     */
    public boolean isRefresh(){
        _log.fine("Refreshhed : "+refresh);
        if(refresh == true){
            refresh = false;
            return true;
        }
        return refresh;
    }

    public StringBuffer getInitTfId() {
        return initTfId;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String organisationSelectionACTION() {
        _log.info("Setting TF to 1");
        this.initTfId = new StringBuffer("1");
        refresh = true;
        return null;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String customerSelectionACTION() {
        _log.info("Setting TF to 2");
        this.initTfId = new StringBuffer("2");
        refresh = true;
        return null;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String productSelectionACTION() {
        _log.info("Setting TF to 3");
        this.initTfId = new StringBuffer("3");
        refresh = true;
        return null;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String productGroupSelectionACTION() {
        _log.info("Setting TF to 4");
        this.initTfId = new StringBuffer("4");
        refresh = true;
        return null;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String salesExecutiveSelectionACTION() {
        _log.info("Setting TF to 5");
        this.initTfId = new StringBuffer("5");
        refresh = true;
        return null;
    }
    /**
     * Method to Changes the Page to Organisation
     * @return
     */
    public String regionSelectionACTION() {
        _log.info("Setting TF to 6");
        this.initTfId = new StringBuffer("6");
        refresh = true;
        return null;
    }

    /**
     * Method to Clear All the filters of the header
     * @param actionEvent
     */
    public void clearAllFilterACTION(ActionEvent actionEvent) {
        OperationBinding clearFilterBinding = this.getBindings().getOperationBinding("clearAllFilters");
        clearFilterBinding.execute();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        StringBuffer clause = new StringBuffer("");
        OperationBinding whereClauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
        whereClauseBinding.execute();
        if(whereClauseBinding.getResult() != null){
            clause = (StringBuffer)whereClauseBinding.getResult();
        }
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", clause);
        binding.execute();
        
        OperationBinding newBinding = this.getBindings().getOperationBinding("initFilterChange");
        newBinding.getParamsMap().put("val", true);
        newBinding.execute();
    }
    /**
     * Method to set current tickerId
     * @param actionEvent
     */
    public void setTickerIdACTION(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        Integer lnkd = (Integer)ob.getAttributes().get("linkId");
        OperationBinding binding = this.getBindings().getOperationBinding("initCurrentTickerId");
        binding.getParamsMap().put("currentTickerId", lnkd);
        binding.execute();
        for(TickerDC dc : this.tickerList){
            if(dc.getTickerId().equals(lnkd)){
                dc.setActive(true);
            }else{
                dc.setActive(false);
            }
        }
        OperationBinding newBinding = this.getBindings().getOperationBinding("filterMisLov");
        newBinding.getParamsMap().put("typId", lnkd);
        newBinding.execute();
        
    }
    
    public Integer getOrgListSize(){
       return orgList.size();
    }
    public Integer getCustListSize(){
        return custList.size();
    }
    public Integer getProductListSize(){
        return itmList.size();
    }
    public Integer getProductGrpListSize(){
        return itmGrp.size();
    }
   
}
